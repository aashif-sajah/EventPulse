package com.aashif.EventPulse.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aashif.EventPulse.model.LogEntry;

@Repository
public interface LogEntryRepo extends JpaRepository<LogEntry, Integer>
{
    
}
