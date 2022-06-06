package com.batch.batchstudy.processor;

import com.batch.batchstudy.service.MemberService;
import com.batch.batchstudy.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.*;

public class MemberCopyProcessor implements ItemProcessor<Member, Member> {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    MemberService service;

    @Override
    public Member process(Member member) throws Exception {
        Timestamp last_login = new Timestamp(System.currentTimeMillis());
        Member newMember = new Member(member.id, member.name, last_login);

        return newMember;
    }

//    @Override
//    public Member process(Member member) throws Exception {
//        String messageId = String.format("%d", Math.abs(new Random().nextInt(999) + 100));
//        String id = member.id + messageId;
//        String name = member.name + messageId;
//        Member newMember = new Member(id, name);
//
//        return newMember;
//    }
}
