package com.demoproject.controller;

import com.demoproject.dto.MemberSaveDto;
import com.demoproject.entity.Member;
import com.demoproject.repository.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Rollback(false)
public class MemberControllerTest extends BaseControllerTest{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;


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

    @Test
    public void addMemberRestTest() throws Exception{

        //given
        MemberSaveDto memberSaveDto = MemberSaveDto.builder()
                .username("addMember")
                .build();


        //when then
        mockMvc.perform(post("/api/member/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(memberSaveDto))
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("resultMessage").value(1));



    }


    @Test
    public void updateMemberRestTest() throws Exception{

        //given
        Member testMember = new Member("testMember");
        Member saveMember = memberRepository.save(testMember);

        MemberSaveDto memberSaveDto = MemberSaveDto.builder()
                .username("updateMember")
                .build();


        //when then
        mockMvc.perform(put("/api/member/update/{id}", saveMember.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(memberSaveDto))
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("resultMessage").value(saveMember.getId()));



    }


}
