package com.demoproject.dto;

import com.demoproject.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class MemberRequestDto {

    private Long id;
    private String username;
    private String lastModifiedDate;
    private String lastModifiedBy;


    public MemberRequestDto(Member member) {
        id = member.getId();
        username = member.getUsername();
        lastModifiedDate = toStringDateTime(member.getLastModifiedDate());
        lastModifiedBy = member.getLastModifiedBy();

    }

    private String toStringDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS");

        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }


}
