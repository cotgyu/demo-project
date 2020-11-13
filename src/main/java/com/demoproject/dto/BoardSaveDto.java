package com.demoproject.dto;

import com.demoproject.entity.Board;
import com.demoproject.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardSaveDto {

    private String title;
    private String content;
    private Member member;


    @Builder
    public BoardSaveDto(String title, String content, Member member){

        this.title = title;
        this.content = content;
        this.member = member;

    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }


}
