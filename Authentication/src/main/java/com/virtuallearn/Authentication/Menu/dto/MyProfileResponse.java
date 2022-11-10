package com.virtuallearn.Authentication.Menu.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyProfileResponse {
    private String profilePhoto;
    private String fullName;
    private String occupation;
    private String courseCompleted;
    private String chaptersCompleted;
    private String testsCompleted;
    private String userName;
    private String email;
    private String mobileNumber;
    private Date dateOfBirth;
}
