package com.aashif.EventPulse.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class LogEntry
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String message;
    private LocalDateTime time;
    private long simulationId;


    public LogEntry(String message, Long simulationId)
    {
        this.message = message;
        this.simulationId = simulationId;
        this.time = LocalDateTime.now();
    }
}
