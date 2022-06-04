package com.batch.batchstudy.service;

import com.batch.batchstudy.mapper.MemberMapper;
import com.batch.batchstudy.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    MemberMapper mapper;

    public List<Member> getAllMembers(){
        logger.info("getAllMembers is called");
        List<Member> list = mapper.selectAll();

        return list;
    }
}
