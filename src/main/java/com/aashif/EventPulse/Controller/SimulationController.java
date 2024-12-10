package com.aashif.EventPulse.Controller;

import com.aashif.EventPulse.Config.SystemConfig;
import com.aashif.EventPulse.service.SimulationService;
import com.aashif.EventPulse.util.LoggerUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void startSimulation(@RequestBody SystemConfig config)
    {
        System.out.println(config.toString());
        simulationService.startSimulation(config);
    }

    @PostMapping("/stop-simulation")
    public void stopSimulation()
    {
         simulationService.stopSimulation();
    }

    @GetMapping("/logs")
    public List<String> getLogs() {
        return loggerUtil.getLogs();
    }


}
