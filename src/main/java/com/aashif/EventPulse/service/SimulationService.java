package com.aashif.EventPulse.service;


import com.aashif.EventPulse.Config.SystemConfig;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {

    public void startSimulation(SystemConfig config) {
        System.out.println("Simulation started");
        System.out.println(config.toString());
    }

    public void getConfig(SystemConfig config) {
    }

    public void stopSimulation() {
    }
}
