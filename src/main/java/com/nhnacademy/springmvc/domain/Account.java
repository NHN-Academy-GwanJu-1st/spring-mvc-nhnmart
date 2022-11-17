package com.nhnacademy.springmvc.domain;

import lombok.Value;

@Value
public class Account {

    private String id;
    private String password;
    private String name;
    private Role role;

}
