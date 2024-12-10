package com.aashif.EventPulse.service;


import com.aashif.EventPulse.Config.SystemConfig;
import com.aashif.EventPulse.repository.TicketRepo;
import com.aashif.EventPulse.util.LoggerUtil;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SimulationService
{
    private final int CONSUMER_THREADS = 5;
    private final int PRODUCER_THREADS = 5;

    private final LoggerUtil LOGGER;
    private final TicketRepo TICKET_REPO;
    private TicketPool ticketPool;
    private final AtomicBoolean stopFlag = new AtomicBoolean(false);

    private ScheduledExecutorService executor;


    public SimulationService(LoggerUtil LOGGER, TicketRepo TICKET_REPO)
    {
        this.LOGGER = LOGGER;
        this.TICKET_REPO = TICKET_REPO;
    }


    public String startSimulation(SystemConfig systemConfig)
    {
        ticketPool = new TicketPool(systemConfig.getTicketPoolCapacity());

        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }

        executor = new ScheduledThreadPoolExecutor(CONSUMER_THREADS + PRODUCER_THREADS + 1);
        executeSimulation(systemConfig);

        return "Simulation started";
    }

    private void executeSimulation(SystemConfig systemConfig)
    {
        for(int i = 0; i < CONSUMER_THREADS; i++)
        {
            int consumerId = i+1;
            int initialDelay = i * systemConfig.getConsumerRetrievalRate();
            executor.scheduleAtFixedRate(
                    new Consumer(
                            ticketPool,
                            LOGGER,
                            consumerId,
                            stopFlag,
                            systemConfig.getTotalTicket()),
                    initialDelay,
                    systemConfig.getConsumerRetrievalRate(),
                    TimeUnit.SECONDS
            );
        }

        for(int i = 0; i < PRODUCER_THREADS; i++)
        {
            int producerId = i+1;
            int initialDelay = i * systemConfig.getConsumerRetrievalRate();
            executor.scheduleAtFixedRate(
                    new Producer(
                            ticketPool,
                            LOGGER,
                            TICKET_REPO,
                            producerId,
                            stopFlag,
                            systemConfig.getTotalTicket()),
                    initialDelay,
                    systemConfig.getTicketReleaseRate(),
                    TimeUnit.SECONDS);

        }

        executor.scheduleAtFixedRate(() -> checkAndShutdown(ticketPool, systemConfig.getTotalTicket()),
                0,
                1,
                TimeUnit.SECONDS);

    }

    private void checkAndShutdown(TicketPool ticketPool, int totalTicket)
    {
        int producedTickets = ticketPool.getProducedTicketCount();
        int consumedTickets = ticketPool.getConsumedTicketCount();

        if (producedTickets >= totalTicket && consumedTickets >= totalTicket)
        {
            LOGGER.log("All tickets have been produced and consumed. Stopping simulation...");
            stopSimulation();
        }
    }

    public String stopSimulation()
    {
        stopFlag.set(true);
        executor.shutdown();

        try {
            if(!executor.awaitTermination(5,TimeUnit.SECONDS))
            {
                executor.shutdownNow(); //Force Shutting down
            }

        } catch (InterruptedException e) {
            return "Error While shutting down executor"+e.getMessage();
        }

        return "Simulation stopped Successfully";
    }
}
