package com.demoproject.service;

import com.demoproject.dto.MemberRequestDto;
import com.demoproject.dto.MemberSaveDto;
import com.demoproject.entity.Member;
import com.demoproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    public List<MemberRequestDto> findAllMember(){

        return memberRepository.findAll()
                .stream()
                .map(MemberRequestDto::new)
                .collect(Collectors.toList());
    }

    public long addMember(MemberSaveDto memberSaveDto){

        return memberRepository.save(memberSaveDto.toEntity()).getId();
    }

    public long updateMember(Long id, MemberSaveDto memberSaveDto){

        Optional<Member> findMember = memberRepository.findById(id);

        Member member = findMember.get();

        modelMapper.map(memberSaveDto, member);


        memberRepository.save(member);

        return member.getId();
    }
}
