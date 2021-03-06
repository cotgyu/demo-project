package com.demoproject.service;

import com.demoproject.dto.BoardRequestDto;
import com.demoproject.dto.BoardSaveDto;
import com.demoproject.dto.MemberRequestDto;
import com.demoproject.dto.MemberSaveDto;
import com.demoproject.entity.Board;
import com.demoproject.entity.Member;
import com.demoproject.repository.BoardRepository;
import com.demoproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;

    public List<BoardRequestDto> findboardpage(int startNum){

        PageRequest pageRequest = PageRequest.of(startNum, 10, Sort.Direction.DESC, "seq");

        return boardRepository.findAll(pageRequest)
                .stream()
                .map(BoardRequestDto::new)
                .collect(Collectors.toList());
    }

    public long addBoard(BoardSaveDto boardSaveDto){
        return boardRepository.save(boardSaveDto.toEntity()).getSeq();
    }

    public long updateBoard(long seq, BoardSaveDto boardSaveDto){

        Optional<Board> findBoard = boardRepository.findById(seq);

        if(!findBoard.isPresent()){
            return -1;
        }

        Board board = findBoard.get();

        modelMapper.map(boardSaveDto, board);


        boardRepository.save(board);

        return board.getSeq();
    }

    public BoardRequestDto viewDetail(long seq){

        Optional<Board> findBoard = boardRepository.findById(seq);

        if (!findBoard.isPresent()){
            BoardRequestDto resultDto = new BoardRequestDto();

            return resultDto;
        }

        Board board = findBoard.get();

        BoardRequestDto resultDto = new BoardRequestDto(board);

        return resultDto;
    }

}
