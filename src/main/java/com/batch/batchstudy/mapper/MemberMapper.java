package com.batch.batchstudy.mapper;

import com.batch.batchstudy.vo.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper{
    Member selectById(String id);
    Member selectByName(String name);
    List<Member> selectAll();
    int insertMember(Member member);
    int updateMember(Member member);
    int deleteMember(Member member);
}

