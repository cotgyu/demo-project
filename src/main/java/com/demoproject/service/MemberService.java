package com.demoproject.service;

import com.demoproject.dto.MemberRequestDto;
import com.demoproject.dto.MemberSaveDto;
import com.demoproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberRequestDto> findAllMember(){

        return memberRepository.findAll()
                .stream()
                .map(MemberRequestDto::new)
                .collect(Collectors.toList());
    }

    public long addMember(MemberSaveDto memberSaveDto){

        return memberRepository.save(memberSaveDto.toEntity()).getId();
    }
}
