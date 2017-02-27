package com.springbatch.demo.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author Adam Hartley
 */
@Component
public class StepNotificationListener implements StepExecutionListener {

    private static Logger logger = LoggerFactory.getLogger(StepNotificationListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info("!!! BEFORE STEP !!!");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ExitStatus exitStatus = stepExecution.getExitStatus();

        if(exitStatus.equals(ExitStatus.COMPLETED)) {
            logger.info("!!! Completed Step Successfully !!!");
        } else {
            logger.info("!!! Step completed with exit code: " + exitStatus);
        }
        return exitStatus;
    }
}
