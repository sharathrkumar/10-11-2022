package com.virtuallearn.Authentication.sharath.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSharath
{
    private int questionId;
    private String questionName;
    private int testId;
    private String option_1;
    private String option_2;
    private String option_3;
    private String option_4;
    private String correctAnswer;
}
