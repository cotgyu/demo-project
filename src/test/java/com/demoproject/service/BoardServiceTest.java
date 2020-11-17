package com.demoproject.service;

import com.demoproject.controller.BaseControllerTest;
import com.demoproject.dto.BoardRequestDto;
import com.demoproject.dto.BoardSaveDto;
import com.demoproject.entity.Board;
import com.demoproject.entity.Member;
import com.demoproject.repository.BoardRepository;
import com.demoproject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback(false)
class BoardServiceTest extends BaseControllerTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void addBoardTest(){

        //given
        Member testMember = new Member("testName");
        memberRepository.save(testMember);

        BoardSaveDto testDto = new BoardSaveDto("title","content", testMember);

        //when
        Long boardSeq = boardService.addBoard(testDto);
        Optional<Board> findBoard = boardRepository.findById(boardSeq);


        //then
        Board result = findBoard.get();
        assertThat(result.getTitle()).isEqualTo("title");
    }


    @Test
    public void updateBoardTest(){
        Member testMember = new Member("testName");
        memberRepository.save(testMember);

        BoardSaveDto testDto = new BoardSaveDto("title","content", testMember);
        long boardSeq = boardService.addBoard(testDto);


        //when
        BoardSaveDto updateDto = new BoardSaveDto("updateTitle", "updateContent", testMember);
        boardService.updateBoard(boardSeq, updateDto);

        Optional<Board> findBoard = boardRepository.findById(boardSeq);


        //then
        Board result = findBoard.get();
        assertThat(result.getTitle()).isEqualTo("updateTitle");
    }

}