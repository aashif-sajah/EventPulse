package com.aashif.EventPulse.Config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SystemConfig {
    private int totalTicket;
    private int ticketReleaseRate;
    private int consumerRetrevalRate;
    private int ticketPoolCapacity;

}
