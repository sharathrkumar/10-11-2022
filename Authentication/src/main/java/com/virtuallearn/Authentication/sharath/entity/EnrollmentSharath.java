package com.virtuallearn.Authentication.sharath.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentSharath {
    private String userName;
    private Integer courseId;
    private Date joinDate;
    private Date completedDate;
    private Integer courseScore;

    public EnrollmentSharath(Integer courseId) {
        this.courseId = courseId;
    }
}
