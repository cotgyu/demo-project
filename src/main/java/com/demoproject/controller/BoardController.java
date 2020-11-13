package com.demoproject.controller;

import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board")
@Slf4j
public class BoardController {

    private static Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final MemberService memberService;

    @GetMapping("/list")
    public String memberList(Model model){

        logger.error("MemberController - memberList 조회");

        model.addAttribute("memberList", memberService.findAllMember());

        return "memberListPage";
    }

}
