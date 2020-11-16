package com.demoproject.controller;

import com.demoproject.dto.BoardRequestDto;
import com.demoproject.entity.Board;
import com.demoproject.service.BoardService;
import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.OptionalInt;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board")
@Slf4j
public class BoardController {

    private static Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final BoardService boardService;


    @GetMapping("/list/{startNum}")
    public String boardList(Model model, @PathVariable int startNum){

        List<BoardRequestDto> boardList = boardService.findboardpage(startNum);

        model.addAttribute("boardList", boardList);

        return "boardListPage";
    }

}
