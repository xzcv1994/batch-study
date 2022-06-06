package com.batch.batchstudy.listener;

import com.batch.batchstudy.service.MemberService;
import com.batch.batchstudy.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

public class JobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
    @Autowired
    MemberService service;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("========== BEFORE JOB ==========");
        logger.info("============ MEMBERS ===========");
        List<Member> list = service.getAllMembers();

        for(Member member : list){
            logger.info("id : {}, name : {}, last_login : {}", member.id, member.name, member.last_login);
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("BATCH JOB COMPLETED SUCCESSFULLY");
            logger.info("============ RESULT ===========");
            List<Member> list = service.getAllMembers();

            for(Member member : list){
                logger.info("id : {}, name : {}, last_login : {}", member.id, member.name, member.last_login);
            }
        }
    }
}