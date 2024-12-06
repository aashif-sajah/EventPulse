package com.aashif.EventPulse.Controller;

import com.aashif.EventPulse.Config.SystemConfig;
import com.aashif.EventPulse.service.SimulationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event-pulse")
public class SimulationController {

    private final SimulationService  simulationService;

    public SimulationController(SimulationService simulationService)
    {
        this.simulationService = simulationService;
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
    public String startSimulation(@RequestBody SystemConfig config)
    {
        return simulationService.startSimulation(config);
    }

    @PostMapping("/stop-simulation")
    public String stopSimulation()
    {
        return simulationService.stopSimulation();
    }


}
