package com.demoproject.controller;

import com.demoproject.dto.BoardSaveDto;
import com.demoproject.dto.MemberSaveDto;
import com.demoproject.entity.Member;
import com.demoproject.entity.MemberRoles;
import com.demoproject.repository.MemberRepository;
import com.demoproject.service.BoardService;
import com.demoproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/board")
public class BoardRestController {

    private final BoardService boardService;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

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

        if(result < 0){
            resultMap.put("result", result);
            resultMap.put("resultMessage", "실패");

            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }

        resultMap.put("result", result);
        resultMap.put("resultMessage", "성공");


        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    // jpa create 옵션에 따른 초기데이터 생성 - 운영 시엔 옵션 변경할 것
    @PostConstruct
    public void testInit(){

        Member testMember =  Member.builder()
                .username("testMember")
                .password(passwordEncoder.encode("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        for(int i=1; i<40; i++ ) {

            boardService.addBoard(new BoardSaveDto("title"+i, "content"+i, testMember));

        }

    }

}
