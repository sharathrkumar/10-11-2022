package com.virtuallearn.Authentication.AdminPanel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter
{
    private int chapterId;
    private int courseId;
    private int chapterNumber;
    private String chapterName;
    private String chapterDuration;

    public Chapter(String chapterDuration)
    {
        this.chapterDuration = chapterDuration;
    }
}
