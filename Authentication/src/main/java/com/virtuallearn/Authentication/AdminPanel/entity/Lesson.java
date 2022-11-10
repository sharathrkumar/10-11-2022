package com.virtuallearn.Authentication.AdminPanel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lesson
{
    private int lessonId;
    private int lessonNumber;
    private int chapterId;
    private String lessonName;
    private String lessonDuration;
    private String videoLink;
}
