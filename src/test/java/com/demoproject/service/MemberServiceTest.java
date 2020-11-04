package com.demoproject.service;

import com.demoproject.controller.BaseControllerTest;
import com.demoproject.dto.MemberSaveDto;
import com.demoproject.entity.Member;
import com.demoproject.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@Rollback(false)
class MemberServiceTest extends BaseControllerTest {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;


    @TestConfiguration
    static class TestConfig {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }


    @Test
    public void addMemberTest(){
        //given

        MemberSaveDto dto = MemberSaveDto.builder()
                .username("username1")
                .build();


        //when
        long result = memberService.addMember(dto);

        Member findMember = memberRepository.findById(1L).get();

        assertThat(findMember.getId()).isEqualTo(result);
        assertThat(findMember.getUsername()).isEqualTo("username1");


    }

}