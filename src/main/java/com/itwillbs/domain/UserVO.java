package com.itwillbs.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserVO {
    private int id;
    private String userid;
    private String userpw;
    private String email;
    private String name;
    private String phone;
    private String addr;
    private String profileImg;
    private int status;
    private LocalDateTime createdAt;
}