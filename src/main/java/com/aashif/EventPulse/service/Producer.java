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
    private final int ticketReleaseRate;


    @Override
    public void run()
    {
        while(!stopFlag.get() && ticketPool.getProducedTicketCount() < totalTicket )
        {
            try
            {
                if(ticketPool.getCurrentTicketsInThePool() < ticketPool.getTicketPoolCapacity())
                {
                    Ticket ticket = new Ticket();
                    ticketPool.produceTicket(ticket);
                    ticketRepo.save(ticket);
                    logger.log("Producer with ID: " + producerId + " has been produced a ticket with ID: " + ticket.getId());
                } else {
                    logger.log("Producer with ID: " + producerId + " is waiting because Pool is Full");
                }
                Thread.sleep(ticketReleaseRate*1000L);
            }catch(InterruptedException e)
            {
                logger.log("Producer with ID: " + producerId + " is stopping due to interrupted");
                Thread.currentThread().interrupt();
                break;
            }

            if(stopFlag.get())
            {
                logger.log("Producer with ID: " + producerId + " has been stopped");
            } else if (ticketPool.getProducedTicketCount() >= totalTicket)
            {
                logger.log("Producer with ID: " + producerId + " has been produced all the tickets");
            }

        }

    }

}
