package com.demoproject.controller;

import com.demoproject.dto.BoardSaveDto;
import com.demoproject.dto.MemberSaveDto;
import com.demoproject.service.BoardService;
import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/board")
public class BoardRestController {

    private final BoardService boardService;

    @PostMapping(value = "/add")
    public ResponseEntity addBoard(@RequestBody BoardSaveDto dto) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        long result = boardService.addBoard(dto);

        resultMap.put("resultMessage", result);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{seq}")
    public ResponseEntity updateBoard(@PathVariable Long seq,
            @RequestBody BoardSaveDto dto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        long result = boardService.updateBoard(seq, dto);

        resultMap.put("resultMessage", result);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

}
