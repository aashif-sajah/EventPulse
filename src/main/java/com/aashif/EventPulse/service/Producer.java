package com.aashif.EventPulse.service;

import com.aashif.EventPulse.model.Ticket;
import com.aashif.EventPulse.repository.TicketRepo;
import com.aashif.EventPulse.util.LoggerUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Producer implements Runnable{
    private final TicketPool  ticketPool;
    private final LoggerUtil logger;
    private final TicketRepo ticketRepo;
    private final Integer producerId;
    private final Integer ticketReleaseRate;

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                if(ticketPool.getCurrentTicketsInthePool()<ticketPool.getTicketPoolCapacity())
                {
                    Ticket ticket = new Ticket();
                    ticketPool.produceTicket(ticket);
                    ticketRepo.save(ticket);
                    logger.log("Producer " + producerId + " added ticket" + ticket.getId());
                }else
                {
                    logger.log("Ticket Pool is full; Producer" + producerId + "is waiting...");
                }
                Thread.sleep(ticketReleaseRate * 1000L);

            }
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
