package com.aashif.EventPulse.service;

import com.aashif.EventPulse.model.Ticket;
import com.aashif.EventPulse.repository.TicketRepo;
import com.aashif.EventPulse.util.LoggerUtil;
import lombok.AllArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;


@AllArgsConstructor
public class Producer implements Runnable{
    private final TicketPool  ticketPool;
    private final LoggerUtil logger;
    private final TicketRepo ticketRepo;
    private final int producerId;
    private final AtomicBoolean stopFlag;
    private final int totalTicket;
    private final long simulationId;


    @Override
    public void run()
    {
        if (stopFlag.get())
        {
            logger.log("Producer with ID: " + producerId + " stopping due to User Stopped the Simulation",simulationId);
        } else if (ticketPool.getProducedTicketCount() >= totalTicket)
        {
            logger.log("Producer with ID: " + producerId + " stopping As All tickets are produced.",simulationId);
        }

        if(Thread.currentThread().isInterrupted())
        {
            logger.log("Producer with ID: " + producerId + " stopping due to Interrupted.",simulationId);
            return; // if thread interrupted then leaving out of the run method
        }

        if(ticketPool.getCurrentTicketInThePool() < ticketPool.getTicketPoolCapacity())
        {
            Ticket ticket = new Ticket();
            ticketPool.produceTicket(ticket);
            ticketRepo.save(ticket);
            logger.log("Producer with ID: " + producerId + " has been produced a ticket with ID: " + ticket.getId(),simulationId);
        } else {
            logger.log("Producer with ID: " + producerId + " is waiting because Pool is Full",simulationId);
        }

    }

}
