package com.aashif.EventPulse.Controller;

import com.aashif.EventPulse.Config.SystemConfig;
import com.aashif.EventPulse.model.LogEntry;
import com.aashif.EventPulse.service.SimulationService;
import com.aashif.EventPulse.util.LoggerUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

@RestController
@RequestMapping("/event-pulse")
@CrossOrigin
public class SimulationController {

    private final SimulationService  simulationService;
    private final LoggerUtil loggerUtil;

    public SimulationController(SimulationService simulationService, LoggerUtil loggerUtil)
    {
        this.simulationService = simulationService;
        this.loggerUtil = loggerUtil;
    }

    @GetMapping("/")
    public String home()
    {
        return "Hello ling ling!";
    }

    @GetMapping("/getConfig")
    public SystemConfig getConfig()
    {
        return new SystemConfig(500,2,3,100);
    }


    @PostMapping("/start-simulation")
    public ResponseEntity<Map<String, String>> startSimulation(@RequestBody SystemConfig config) {
        System.out.println(config.toString());
        simulationService.startSimulation(config);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Simulation started successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/stop-simulation")
    public ResponseEntity<Map<String, String>> stopSimulation() {
        try {
            simulationService.stopSimulation(simulationService.getSimulationId());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Simulation stopped successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error stopping simulation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    @GetMapping("/logs")  /*used short poll method and changed to Sever sent event method*/
    public BlockingQueue<String> getLogs() {
        return loggerUtil.getLogQueue();
    }

    /*This is to send data from db to front end*/
    @GetMapping("/logs/{simulationId}")
    public List<LogEntry> getLogsBySimulationId(@PathVariable Long simulationId) {
        return simulationService.getLogsBySimulationId(simulationId);
    }

    @GetMapping(value = "/stream-logs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public void streamLogs(jakarta.servlet.http.HttpServletResponse response) {
        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE + "; charset=UTF-8");

        if (!simulationService.waitForTicketPoolInitialization(10, 100)) {
            try {
                response.getWriter().write("data: { \"type\": \"error\", \"message\": \"TicketPool not initialized.\" }\n\n");
                response.getWriter().flush();
            } catch (IOException e) {
                loggerUtil.log(e.getMessage(), simulationService.getSimulationId());
            }
            return;
        }

        try {
            BlockingQueue<String> logQueue = loggerUtil.getLogQueue();
            while (true) {
                // Log message
                String logMessage = logQueue.take(); // Wait for new logs
                response.getWriter().write("data: { \"type\": \"log\", \"message\": \"" + logMessage + "\" }\n\n");

                // Atomic variables
                response.getWriter().write("data: { \"type\": \"stats\", " +
                        "\"ticketsProduced\": " + simulationService.getTicketPool().getProducedTicketCount() + "," +
                        "\"ticketsConsumed\": " + simulationService.getTicketPool().getConsumedTicketCount() + "," +  // Fixed here
                        "\"currentTicketsInPool\": " + simulationService.getTicketPool().getCurrentTicketsInThePool() + " }\n\n");


                response.getWriter().flush();
            }
        } catch (Exception e) {
            loggerUtil.log(e.getMessage(), simulationService.getSimulationId());
        } finally {
            try {
                response.getWriter().close();  // Ensure that the writer is closed when done
            } catch (IOException e) {
                loggerUtil.log(e.getMessage(), simulationService.getSimulationId());
            }
        }
    }

    @GetMapping("/simulation-ids")
    public List<Long> getSimulationIds() {
        return simulationService.getAllSimulationIds();
    }



}

