package com.virtuallearn.Authentication.AdminPanel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment
{
    private String userName;
    private Integer courseId;
    private Date joinDate;
    private Date completedDate;
    private Integer courseScore;

    public Enrollment(int courseId)
    {
        this.courseId = courseId;
    }
}
