package com.demoproject.service;

import com.demoproject.controller.BaseControllerTest;
import com.demoproject.dto.MemberSaveDto;
import com.demoproject.entity.Member;
import com.demoproject.entity.MemberRoles;
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

import java.util.Set;

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

    @Test
    public void updateMemberTest(){

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);


        //when
        MemberSaveDto updateDto = MemberSaveDto.builder()
                .username("username1")
                .build();


        memberService.updateMember(1L, updateDto);


        Member resultMember = memberRepository.findById(1L).get();

        assertThat(resultMember.getUsername()).isEqualTo("username1");

    }

}