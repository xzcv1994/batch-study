package com.batch.batchstudy.processor;

import com.batch.batchstudy.service.MemberService;
import com.batch.batchstudy.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

public class MemberCopyProcessor implements ItemProcessor<Member, Member> {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    MemberService service;

    @Override
    public Member process(Member member) throws Exception {
        String messageId = String.format("%7d", Math.abs(new Random().nextLong()));
        String id = member.id + messageId;
        String name = member.name + messageId;
        Member newMember = new Member(id, name);

        return newMember;
    }
}
