package com.demoproject.controller;

import com.demoproject.dto.MemberSaveDto;
import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping(value = "/add")
    public ResponseEntity addMember(@RequestBody MemberSaveDto dto) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        long result = memberService.addMember(dto);

        resultMap.put("resultMessage", result);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity updateMember(@PathVariable Long id,
            @RequestBody MemberSaveDto dto) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        long result = memberService.updateMember(id, dto);

        resultMap.put("resultMessage", result);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

}
