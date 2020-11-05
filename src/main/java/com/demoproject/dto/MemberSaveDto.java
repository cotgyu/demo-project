package com.demoproject.dto;

import com.demoproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MemberSaveDto {

    private Long id;
    private String username;

    public MemberSaveDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
    }

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
