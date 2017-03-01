package com.springbatch.demo.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @author Adam Hartley
 */
@Component
public class JobNotificationListener extends JobExecutionListenerSupport {

    private static Logger logger = LoggerFactory.getLogger(JobNotificationListener.class);

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        logger.info("!!! JOB STARTING !!!");
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("!!! JOB FINISHED SUCCESSFULLY !!! ");

            JobInstance jobInstance = jobExecution.getJobInstance();

            logger.info("\nJOB NAME: " + jobInstance.getJobName() + "\n"
                    + "\tJOB START TIME: " + jobExecution.getStartTime() + "\n"
                    + "\tJOB END TIME: " + jobExecution.getEndTime() + "\n"
                    + "\tJOB EXIT CODE: " + jobExecution.getExitStatus().getExitCode());

            for(StepExecution stepExecution : jobExecution.getStepExecutions()) {
                logger.info("\nSTEP NAME: " + stepExecution.getStepName() + "\n"
                        + "\tREAD COUNT: "  + stepExecution.getReadCount() + "\n"
                        + "\tWRITE COUNT: " + stepExecution.getWriteCount() + "\n"
                        + "\tSKIP COUNT: " + stepExecution.getSkipCount() + "\n"
                        + "\tSTEP EXIT CODE: " + stepExecution.getExitStatus().getExitCode());
            }

        } else {
            logger.info("!!! JOB FINISHED with exit code: " + jobExecution.getStatus());
        }
    }
}
