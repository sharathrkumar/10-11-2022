package com.virtuallearn.Authentication.virtualLearn.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    private String userName;
    private String password;
    private String role;
}
