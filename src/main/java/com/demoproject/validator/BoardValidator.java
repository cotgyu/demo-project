package com.demoproject.validator;

import com.demoproject.dto.BoardSaveDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class BoardValidator {

    public void validate(BoardSaveDto boardSaveDto, Errors errors){
        if(boardSaveDto.getTitle().equals("") | boardSaveDto.getTitle() == null){
            // field 에러
            errors.rejectValue("title", "wrongValue","title is wrong.");
            // global 에러
            errors.reject("wrongTitle", "Values for title is wrong");
        }

        if(boardSaveDto.getContent().equals("") | boardSaveDto.getContent() == null){
            errors.rejectValue("content", "wrongValue","content is wrong.");
            errors.reject("wrongContent", "Values for content is wrong");
        }
    }
}
