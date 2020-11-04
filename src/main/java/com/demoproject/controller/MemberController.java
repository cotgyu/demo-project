package com.demoproject.controller;

import com.demoproject.dto.MemberSaveDto;
import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public String memberList(Model model){

        model.addAttribute("memberList", memberService.findAllMember());

        return "memberListPage";
    }

}
