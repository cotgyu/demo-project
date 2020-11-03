package com.demoproject.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseEntity{

    @Id @GeneratedValue
    private Long id;

    private String username;

    @Builder
    public Member(String username) {
        this.username = username;
    }
}
