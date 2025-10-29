package com.example.employee_management.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SystemMonitorSchedule {
    private static final Logger logger = LoggerFactory.getLogger(SystemMonitorSchedule.class);

    /**
     * Runs every 30 seconds to log that the system is running.
     */
    @Scheduled(fixedRateString = "${app.scheduling.system-monitor.fixed-rate}")
    public void logSystemRunning() {
        logger.info("System running... (Scheduled Task)");
    }
}
