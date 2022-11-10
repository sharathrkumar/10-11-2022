package com.virtuallearn.Authentication.AdminPanel.controller;

import com.virtuallearn.Authentication.AdminPanel.entity.Question;
import com.virtuallearn.Authentication.AdminPanel.request.CourseRequest;
import com.virtuallearn.Authentication.AdminPanel.request.InstructorRequest;
import com.virtuallearn.Authentication.AdminPanel.request.LessonRequest;
import com.virtuallearn.Authentication.AdminPanel.request.TestRequest;
import com.virtuallearn.Authentication.AdminPanel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class AdminController
{
    @Autowired
    private AdminService adminService;

    @PostMapping("/course")
    public ResponseEntity<String> saveCourse(@ModelAttribute CourseRequest courseRequest) throws IOException
    {
        String courseResponse = adminService.addCourse(courseRequest);
        if(courseResponse == null)
        {
            return new ResponseEntity<String>("course addition is unsuccessful", HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<String>(courseResponse, HttpStatus.OK);
    }

    @PostMapping("/lesson")
    public ResponseEntity<String> saveLesson(@ModelAttribute LessonRequest lessonRequest) throws IOException, ParseException {
        String lessonResponse= adminService.addLesson(lessonRequest);
        if(lessonResponse == null)
        {
            return new ResponseEntity<String>("lesson addition is unsuccessful", HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(lessonResponse, HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<String> saveTest(@RequestBody TestRequest testRequest)
    {
        String testResponse = adminService.addTest(testRequest);
        if(testResponse == null)
        {
            return new ResponseEntity<String>("test addition is unsuccessful", HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(testResponse,HttpStatus.OK);
    }

    @PostMapping("/question")
    public ResponseEntity<String> saveQuestion(@RequestBody Question question)
    {
        String questionResponse = adminService.addQuestion(question);
        if(questionResponse == null)
        {
            return new ResponseEntity<String>("question addition is unsuccessful", HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(questionResponse,HttpStatus.OK);
    }
    @PostMapping("/instructor")
    public ResponseEntity<String> saveInstructor(@ModelAttribute InstructorRequest instructor) throws IOException {
        String instructorResponse = adminService.addInstructor(instructor);
        if(instructorResponse == null)
        {
            return new ResponseEntity<String>("instructor addition is unsuccessful", HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(instructorResponse,HttpStatus.OK);
    }

}
