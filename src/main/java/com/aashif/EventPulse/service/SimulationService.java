package com.aashif.EventPulse.service;


import com.aashif.EventPulse.Config.SystemConfig;
import com.aashif.EventPulse.repository.TicketRepo;
import com.aashif.EventPulse.util.LoggerUtil;
import org.springframework.stereotype.Service;

@Service
public class SimulationService
{
    private TicketPool ticketPool;
    private Thread producerThread;
    private Thread consumerThread;

    private LoggerUtil logger;
    private TicketRepo ticketRepo;

    public SimulationService(LoggerUtil logger,TicketRepo ticketRepo)
    {
        this.logger = logger;
        this.ticketRepo = ticketRepo;
    }



    public void startSimulation(SystemConfig config)
    {
        this.ticketPool = new TicketPool(config.getTicketPoolCapacity());
        producerThread = new Thread(new Producer(ticketPool,logger,ticketRepo,101, config.getTicketReleaseRate()));
        consumerThread = new Thread(new Consumer(ticketPool,logger,101, config.getConsumerRetreivalRate()));

        producerThread.start();
        consumerThread.start();
    }

    public void stopSimulation()
    {
        if(producerThread.isAlive() || consumerThread.isAlive() )
        {
            producerThread.interrupt();
            consumerThread.interrupt();
        }
    }
}
