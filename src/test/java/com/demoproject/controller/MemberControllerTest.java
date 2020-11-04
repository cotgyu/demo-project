package com.demoproject.controller;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Rollback(false)
public class MemberControllerTest extends BaseControllerTest{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;


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
    public void querydslTest(){

        // given
        Member testMember = new Member("testMember");
        Member testMember2 = new Member("testMember2");

        memberRepository.save(testMember);
        memberRepository.save(testMember2);

        em.flush();
        em.clear();

        // when
        List<Member> allCustomMember = memberRepository.findAllCustomMember();

        // then
        assertThat(allCustomMember.size()).isEqualTo(2);

    }

    @Test
    public void baseEntityTest(){
        //given
        Member testMember = new Member("testMember");

        memberRepository.save(testMember);

        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findById(1L).get();

        System.out.println(findMember.getCreatedDate());

        // then
        assertThat(findMember.getCreatedDate()).isNotNull();

    }
}
