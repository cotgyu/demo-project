package com.demoproject.controller;

import com.demoproject.dto.MemberSaveDto;
import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/add")
    public Long addMember(@RequestBody MemberSaveDto dto){

        return memberService.addMember(dto);
    }

}
