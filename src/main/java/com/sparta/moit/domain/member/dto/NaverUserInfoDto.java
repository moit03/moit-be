package com.sparta.moit.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserInfoDto {
    private String id;
    private String name;
    private String email;

    public NaverUserInfoDto(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
