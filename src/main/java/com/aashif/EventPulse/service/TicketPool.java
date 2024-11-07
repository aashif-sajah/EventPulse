package com.aashif.EventPulse.service;


import com.aashif.EventPulse.model.Ticket;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class TicketPool
{
    private final int ticketPoolCapacity;
    private final BlockingQueue<Ticket> ticketPool;

    public TicketPool(int ticketPoolCapacity) {
        this.ticketPoolCapacity = ticketPoolCapacity;
        this.ticketPool = new ArrayBlockingQueue<>(ticketPoolCapacity);
    }

    public boolean produceTicket(Ticket ticket)
    {
        return ticketPool.offer(ticket);
    }

    public Ticket consumeTicket()
    {
        return ticketPool.poll();
    }

    public int getCurrentTicketsInthePool()
    {
        return ticketPool.size();
    }

    public int getTicketPoolCapacity() {
        return ticketPoolCapacity;
    }
}
