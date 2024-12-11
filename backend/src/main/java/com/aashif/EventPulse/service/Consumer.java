package com.aashif.EventPulse.service;

import com.aashif.EventPulse.model.Ticket;
import com.aashif.EventPulse.util.LoggerUtil;
import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
public class Consumer implements Runnable
{
    private final TicketPool ticketPool;
    private final LoggerUtil logger;
    private final int consumerId;
    private final AtomicBoolean stopFlag;
    private final int totalTicket;
    private final long simulationId;


    @Override
    public void run()
    {
        if (stopFlag.get())
        {
            logger.log("Consumer with ID: " + consumerId + " stopping due to User Stopped the Simulation",simulationId);
        } else if (ticketPool.getProducedTicketCount() >= totalTicket)
        {
            logger.log("Consumer with ID: " + consumerId + " stopping As All tickets are consumed.",simulationId);
        }

        if(Thread.currentThread().isInterrupted())
        {
            logger.log("Consumer with ID: " + consumerId + " stopping due to Interrupted.",simulationId);
            return; // if thread interrupted then leaving out of the run method
        }

        Ticket ticket = ticketPool.consumeTicket();
        if (ticket != null)
        {
            logger.log("Consumer with ID: "+ consumerId + " Consumed Ticket: " + ticket.getId(),simulationId);
        } else
        {
            logger.log("Consumer with ID: "+ consumerId + " waiting to consume Ticket because ticket Pool is empty",simulationId);
        }

    }
}
