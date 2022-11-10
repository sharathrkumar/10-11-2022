package com.virtuallearn.Authentication.AdminPanel.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest
{
    private int courseId;
    private MultipartFile coursePhoto;
    private String courseName;
    private MultipartFile previewVideo;
    private int categoryId;
    private int subCategoryId;
    private String courseDuration;
}
