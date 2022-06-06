package com.batch.batchstudy;

import com.batch.batchstudy.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class BatchStudyApplication {
    private static final Logger logger = LoggerFactory.getLogger(BatchStudyApplication.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job memberCopyJob;

    public static void main(String[] args) {
        SpringApplication.run(BatchStudyApplication.class, args);
    }

    @Scheduled(cron = "0/10 * * * * *")
    public void perform() throws Exception{
        System.out.println();
        logger.info("========= JobLaunched at: {} =========", new Date());
        JobParameters params = new JobParametersBuilder()
                .addString("JobId", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(memberCopyJob, params);
    }

}
