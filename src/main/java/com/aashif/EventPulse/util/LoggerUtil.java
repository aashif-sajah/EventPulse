package com.aashif.EventPulse.util;

import com.aashif.EventPulse.model.LogEntry;
import com.aashif.EventPulse.repository.LogEntryRepo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor

public class LoggerUtil {

    private LogEntryRepo logEntryRepo;

    @Getter
    private List<String> logs = new ArrayList<>();

    @Autowired
    public LoggerUtil(LogEntryRepo logEntryRepo) {
        this.logEntryRepo = logEntryRepo;
    }

    public void log(String message)
    {
        logs.add(message);
        logEntryRepo.save(new LogEntry(message));
        System.out.println("Log: " + message);

    }



}
