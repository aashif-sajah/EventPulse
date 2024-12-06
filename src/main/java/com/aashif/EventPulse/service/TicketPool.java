package com.aashif.EventPulse.service;


import com.aashif.EventPulse.model.Ticket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class TicketPool
{
    private final int ticketPoolCapacity;
    private final BlockingQueue<Ticket> ticketPool;

    private final AtomicInteger producedTicketCount = new AtomicInteger(0);
    private final AtomicInteger consumedTicketCount = new AtomicInteger(0);


    public TicketPool(int ticketPoolCapacity) {
        this.ticketPoolCapacity = ticketPoolCapacity;
        this.ticketPool = new ArrayBlockingQueue<>(ticketPoolCapacity);
    }

    // A method to Add Ticket to Ticket Pool
    public synchronized void produceTicket(Ticket ticket)
    {
        ticketPool.add(ticket);
        producedTicketCount.incrementAndGet();
    }

    // A method to Remove ticket From the Ticket Pool
    public synchronized Ticket consumeTicket()
    {
        consumedTicketCount.incrementAndGet();
        return ticketPool.poll();
    }

    public synchronized int getCurrentTicketsInThePool()
    {
        return ticketPool.size();
    }

    public synchronized int getTicketPoolCapacity() {
        return ticketPoolCapacity;
    }

    public synchronized int getProducedTicketCount(){return producedTicketCount.get();}

    public synchronized int getConsumedTicketCount(){return consumedTicketCount.get();}
}
