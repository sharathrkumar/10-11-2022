package com.virtuallearn.Authentication.AdminPanel.controller;

import com.virtuallearn.Authentication.AdminPanel.request.EnrollmentRequest;
import com.virtuallearn.Authentication.AdminPanel.request.HomeHeaderRequest;
import com.virtuallearn.Authentication.AdminPanel.response.HomeAllCourse;
import com.virtuallearn.Authentication.AdminPanel.response.HomeResponseTopHeader;
import com.virtuallearn.Authentication.AdminPanel.response.PopularCorseInEachCategory;
import com.virtuallearn.Authentication.AdminPanel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/home/course")
    public ResponseEntity<?> homeTopBarData(@RequestBody HomeHeaderRequest homeHeaderRequest)
    {
        List<HomeResponseTopHeader> coursesList= userService.HomePageTopBar(homeHeaderRequest);
        if(coursesList.size() ==0)
        {
            return new ResponseEntity<String>("courses are not available", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(coursesList,HttpStatus.OK);
    }


    @GetMapping("/home/course/all")
    public ResponseEntity<?> homeAllCourses()
    {
        List<HomeAllCourse> allCourses= userService.getAllCourses();
        if(allCourses.size() ==0)
        {
            return new ResponseEntity<String>("courses are not available", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allCourses,HttpStatus.OK);
    }


    @GetMapping("/home/course/popular")
    public ResponseEntity<?> homePopularCourses()
    {
        List<HomeAllCourse> popularCourses= userService.getPopularCourses();
        if(popularCourses.size() ==0)
        {
            return new ResponseEntity<String>("courses are not available", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(popularCourses,HttpStatus.OK);
    }

    @GetMapping("/home/course/newest")
    public ResponseEntity<?> homeNewestCourses() {
        List<HomeAllCourse> newestCourses = userService.getNewCourses();
        if (newestCourses.size() == 0) {
            return new ResponseEntity<String>("courses are not available", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newestCourses, HttpStatus.OK);
    }

       //*********************10-11-20222*****************************************
       @GetMapping("/home/course/category")
       public ResponseEntity<?> homeGetPopularCoursesOfCategory() {
           List<PopularCorseInEachCategory> coursesOfCategory = userService.popularCoursesInCategory();
           if (coursesOfCategory.size() == 0) {
               return new ResponseEntity<String>("courses are not available", HttpStatus.NOT_FOUND);
           }
           return new ResponseEntity<>(coursesOfCategory, HttpStatus.OK);
       }


        //***********************10-11-2022***************************************
    @PostMapping("/enroll")
    public ResponseEntity<String> userEnrollment(@RequestBody EnrollmentRequest enrollmentRequest)
    {
        String enrolResponse = userService.enrollment(enrollmentRequest);
        return new ResponseEntity<>(enrolResponse, HttpStatus.OK);
    }


}


