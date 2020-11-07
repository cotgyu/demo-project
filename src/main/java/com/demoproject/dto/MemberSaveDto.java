package com.demoproject.dto;

import com.demoproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberSaveDto {

    private String username;

    @Builder
    public MemberSaveDto(String username){

        this.username = username;
    }

    public Member toEntity(){
        return Member.builder()
                .username(username)
                .build();
    }

}
