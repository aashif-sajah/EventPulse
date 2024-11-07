package com.aashif.EventPulse.service;


import com.aashif.EventPulse.model.Ticket;
import com.aashif.EventPulse.repository.TicketRepo;
import com.aashif.EventPulse.util.LoggerUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Consumer implements Runnable
{
    private final TicketPool ticketPool;
    private final LoggerUtil logger;
    private final int consumerId;
    private final int consumerRetreivalRate;

    @Override
    public void run()
    {

        while(true) try
        {
            Ticket ticket = ticketPool.consumeTicket();
            if (ticket != null)
            {
                logger.log("Consumer with ID: "+ consumerId + "Consumed Ticket: " + ticket.getId());
            } else
            {
                logger.log("Consumer with ID: "+ consumerId + "Aint consumed Ticket cz Pool is Null");
            }
            Thread.sleep(consumerRetreivalRate * 1000L);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }
}
