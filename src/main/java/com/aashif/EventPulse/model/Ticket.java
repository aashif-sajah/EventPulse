package com.aashif.EventPulse.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Long simulationId;

    public Ticket() {}

    public Ticket(Long simulationId) {
        this.simulationId = simulationId;
    }


}
