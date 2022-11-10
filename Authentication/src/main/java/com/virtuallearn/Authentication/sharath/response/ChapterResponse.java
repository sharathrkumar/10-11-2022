package com.virtuallearn.Authentication.sharath.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterResponse {
    private Integer chapterNumber;
    private String chapterName;
    private List<LessonResponse> lessonResponses;
    private Boolean chapterCompletedStatus;
}
