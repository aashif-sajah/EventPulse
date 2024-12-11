package com.aashif.EventPulse.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aashif.EventPulse.model.LogEntry;

import java.util.List;

@Repository
public interface LogEntryRepo extends JpaRepository<LogEntry, Integer>
{
    List<LogEntry> findBySimulationId(Long simulationId);

    @Query("SELECT DISTINCT l.simulationId FROM LogEntry l")
    List<Long> findDistinctSimulationIds();
}
