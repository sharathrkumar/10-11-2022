package com.virtuallearn.Authentication.sharath.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestSharath
{
    private int testId;
    private String testName;
    private int chapterId;
    private String testDuration;
    private int questionsCount;
    private int passingGrade;
}
