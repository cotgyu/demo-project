package com.demoproject.service;

import com.demoproject.entity.Member;
import com.demoproject.dto.MemberDto;
import com.demoproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public long addMember(MemberDto memberDto){

        return memberRepository.save(memberDto.toEntity()).getId();
    }
}
