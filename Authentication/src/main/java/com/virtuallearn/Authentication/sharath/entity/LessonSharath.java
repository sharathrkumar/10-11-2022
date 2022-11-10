package com.virtuallearn.Authentication.sharath.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonSharath
{
    private int lessonNumber;
    private int courseId;
    private int chapterId;
    private String lessonName;
    private String lessonDuration;
    private String videoLink;
    private String pauseTime;
    private boolean lessonCompletionStatus;
}
