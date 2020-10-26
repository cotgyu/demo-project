package com.demoproject.controller;

import com.demoproject.entity.Member;
import com.demoproject.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
@ActiveProfiles("test")
class HomeControllerTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;


    @Test
    public void jpaTest(){

        Member testMember = new Member("testMember");

        memberRepository.save(testMember);


        em.flush();
        em.clear();


        Member findMember = memberRepository.findById(testMember.getId()).get();

        assertThat(testMember.getId()).isEqualTo(findMember.getId());


    }

}