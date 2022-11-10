package com.virtuallearn.Authentication.AdminPanel.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularCorseInEachCategory
{
    private String courseName;
    private Integer chapterCount;
    private String courseDuration;
    private String previewVideo;
}
