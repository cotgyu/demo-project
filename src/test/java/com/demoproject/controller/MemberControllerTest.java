package com.demoproject.controller;

import com.demoproject.dto.MemberSaveDto;
import com.demoproject.entity.Board;
import com.demoproject.entity.Member;
import com.demoproject.entity.MemberRoles;
import com.demoproject.repository.BoardRepository;
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
import java.util.Optional;
import java.util.Set;

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

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void querydslTest(){

        // given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        Member testMember2 =  Member.builder()
                .username("testMember2")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

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
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

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
                .andExpect(jsonPath("result").value(1));



    }


    @Test
    public void updateMemberRestTest() throws Exception{

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

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
                .andExpect(jsonPath("result").value(saveMember.getId()));



    }

    @Test
    public void member_board_Test() {

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        Board testBoard = new Board("title", "content", testMember);
        testBoard.setMember(testMember);

        Board savedBoard = boardRepository.save(testBoard);

        em.flush();
        em.clear();

        //when
        Optional<Board> optionalBoard = boardRepository.findById(savedBoard.getSeq());
        Board findBoard = optionalBoard.get();

        //then
        assertThat(findBoard.getMember().getUsername()).isEqualTo("testMember");


    }

}
