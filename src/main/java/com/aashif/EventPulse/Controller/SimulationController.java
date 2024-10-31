package com.aashif.EventPulse.Controller;

import com.aashif.EventPulse.Config.SystemConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimulationController {

    private final SimulationController simulationController;

    public SimulationController(SimulationController simulationController) {
        this.simulationController = simulationController;
    }


    @PostMapping("/start-simulation")
    public void getConfig(SystemConfig config)
    {
        simulationController.getConfig(config);
    }


}
