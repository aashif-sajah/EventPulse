package com.aashif.EventPulse.util;

import com.aashif.EventPulse.model.LogEntry;
import com.aashif.EventPulse.repository.LogEntryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoggerUtil {

    LogEntryRepo logEntryRepo;

    public void log(String message)
    {
        logEntryRepo.save(new LogEntry(message));
        System.out.println("message: " + message);
        
        // make this return method to return the message so that
        // contorller can create a serive obj and one it start the simulation
        // this will return the message
        
    }


}
