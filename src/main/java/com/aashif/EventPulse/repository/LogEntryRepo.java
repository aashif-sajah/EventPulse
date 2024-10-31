package com.aashif.EventPulse.repository;

import com.aashif.EventPulse.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepo extends JpaRepository<LogEntry, Integer>
{
    
}
