package com.aashif.EventPulse.util;

import com.aashif.EventPulse.model.LogEntry;
import com.aashif.EventPulse.repository.LogEntryRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor

public class LoggerUtil {

    @Autowired
    LogEntryRepo logEntryRepo;

    public LoggerUtil() {

    }

    public void log(String message)
    {
        logEntryRepo.save(new LogEntry(message));
        System.out.println("message: " + message);
        
        // Make this return method to return the message so that
        // Controller can create a service obj and one it start the simulation
        // This will return the message
    }


}
