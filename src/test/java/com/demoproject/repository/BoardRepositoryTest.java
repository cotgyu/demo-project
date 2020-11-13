package com.demoproject.repository;

import com.demoproject.controller.BaseControllerTest;
import com.demoproject.dto.BoardRequestDto;
import com.demoproject.entity.Board;
import com.demoproject.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@Rollback(false)
class BoardRepositoryTest extends BaseControllerTest {

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void pageBoardTest(){


        // given
        Member testMember = new Member("username1");

        boardRepository.save(new Board("title1", "content1", testMember));
        boardRepository.save(new Board("title2", "content2", testMember));
        boardRepository.save(new Board("title3", "content3", testMember));

        PageRequest pageRequest = PageRequest.of(0, 2, Sort.Direction.DESC, "seq");


        // when
        Page<Board> findBoards = boardRepository.findAll(pageRequest);

        Page<BoardRequestDto> toMap = findBoards.map(m -> new BoardRequestDto(m));

        List<BoardRequestDto> content = toMap.getContent();
        long totalElements= toMap.getTotalElements();


        assertThat(content.size()).isEqualTo(2);
        assertThat(toMap.getTotalElements()).isEqualTo(3);
        assertThat(toMap.getTotalPages()).isEqualTo(2);



    }
}