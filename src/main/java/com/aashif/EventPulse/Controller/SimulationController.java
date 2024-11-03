package com.aashif.EventPulse.Controller;

import com.aashif.EventPulse.Config.SystemConfig;
import com.aashif.EventPulse.service.SimulationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private final SimulationService  simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }


    @PostMapping("/start-simulation")
    public void getConfig(@RequestBody SystemConfig config)
    {
        simulationService.getConfig(config);
    }


}
