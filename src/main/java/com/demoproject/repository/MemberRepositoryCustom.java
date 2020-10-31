package com.demoproject.repository;

import com.demoproject.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findAllCustomMember();
}
