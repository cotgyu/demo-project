package com.demoproject.controller;

import com.demoproject.dto.MemberDto;
import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/add")
    public Long addMember(@RequestBody MemberDto dto){
        return memberService.addMember(dto);
    }

}
