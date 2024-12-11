package com.aashif.EventPulse.service;


import com.aashif.EventPulse.Config.SystemConfig;
import com.aashif.EventPulse.model.LogEntry;
import com.aashif.EventPulse.repository.LogEntryRepo;
import com.aashif.EventPulse.repository.TicketRepo;
import com.aashif.EventPulse.util.LoggerUtil;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SimulationService
{
    private final int CONSUMER_THREADS = 5;
    private final int PRODUCER_THREADS = 5;

    private final TicketRepo TICKET_REPO;
    private final LoggerUtil loggerUtil;
    @Getter
    private TicketPool ticketPool;
    private final AtomicBoolean stopFlag = new AtomicBoolean(false);

    private ScheduledExecutorService executor;

    @Getter
    private long simulationId;

    private final LogEntryRepo logEntryRepo;


    public SimulationService(TicketRepo TICKET_REPO, LoggerUtil loggerUtil, LogEntryRepo logEntryRepo)
    {
        this.TICKET_REPO = TICKET_REPO;
        this.loggerUtil = loggerUtil;
        this.logEntryRepo = logEntryRepo;
    }


    public void startSimulation(SystemConfig systemConfig)
    {
        System.out.println("Start simulation gets called");
        simulationId = System.currentTimeMillis();
        loggerUtil.log("Starting simulation with ID: " + simulationId,simulationId);
        ticketPool = new TicketPool(systemConfig.getTicketPoolCapacity());
        stopFlag.set(false);


        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }

        executor = new ScheduledThreadPoolExecutor(CONSUMER_THREADS + PRODUCER_THREADS + 1);
        executeSimulation(systemConfig, simulationId);

        /*return "Simulation started";*/
    }

    private void executeSimulation(SystemConfig systemConfig, long simulationId)
    {
        for(int i = 0; i < CONSUMER_THREADS; i++)
        {
            int consumerId = i+1;
            int initialDelay = i * systemConfig.getConsumerRetrievalRate();
            executor.scheduleAtFixedRate(
                    new Consumer(
                            ticketPool,
                            loggerUtil,
                            consumerId,
                            stopFlag,
                            systemConfig.getTotalTicket(),
                            simulationId),
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
                            loggerUtil,
                            TICKET_REPO,
                            producerId,
                            stopFlag,
                            systemConfig.getTotalTicket(),
                            simulationId),
                    initialDelay,
                    systemConfig.getTicketReleaseRate(),
                    TimeUnit.SECONDS);

        }

        executor.scheduleAtFixedRate(() -> checkAndShutdown(ticketPool, systemConfig.getTotalTicket(),simulationId),
                0,
                1,
                TimeUnit.SECONDS);

    }

    private void checkAndShutdown(TicketPool ticketPool, int totalTicket, long simulationId)
    {
        int producedTickets = ticketPool.getProducedTicketCount();
        int consumedTickets = ticketPool.getConsumedTicketCount();

        if (producedTickets >= totalTicket && consumedTickets >= totalTicket)
        {
            loggerUtil.log("All tickets have been produced and consumed. Stopping simulation...",simulationId);
            stopSimulation(simulationId);
        }
    }

    public void stopSimulation(long simulationId)
    {
        stopFlag.set(true);
        if(executor!=null){
            executor.shutdown();
        }else{
            loggerUtil.log("Executor Already Shut Down... ",simulationId);
        }


        try {
            if(executor != null && !executor.awaitTermination(5,TimeUnit.SECONDS))
            {
                executor.shutdownNow(); //Force Shutting down
            }

        } catch (InterruptedException e) {
             loggerUtil.log("Error While shutting down executor"+e.getMessage(),simulationId);
        }


        ticketPool.getTicketPool().clear();
        stopFlag.set(false);
        loggerUtil.getLogQueue().clear();
        loggerUtil.log("Simulation stopped successfully.",simulationId);
        /*return "Simulation stopped Successfully";*/
    }

    public List<LogEntry> getLogsBySimulationId(Long simulationId) {
        return logEntryRepo.findBySimulationId(simulationId);
    }

    public boolean waitForTicketPoolInitialization(int maxRetries, int retryDelay) {
        int retries = 0;
        while (ticketPool == null && retries < maxRetries) {
            try {
                Thread.sleep(retryDelay);
                retries++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return ticketPool != null;
    }

    public List<Long> getAllSimulationIds() {
        return logEntryRepo.findDistinctSimulationIds();
    }
}
