package com.aashif.EventPulse.Config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class SystemConfig {
    private int totalTicket;
    private int ticketReleaseRate;
    private int consumerRetreivalRate;
    private int ticketPoolCapacity;
}
