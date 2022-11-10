package com.virtuallearn.Authentication.sharath.controller;

import com.virtuallearn.Authentication.sharath.entity.CourseSharath;
import com.virtuallearn.Authentication.sharath.model.Category;
import com.virtuallearn.Authentication.sharath.response.AllCoursesResponse;
import com.virtuallearn.Authentication.sharath.response.CourseResponse;
import com.virtuallearn.Authentication.sharath.response.OngoingResponse;
import com.virtuallearn.Authentication.sharath.response.OverviewResponse;
import com.virtuallearn.Authentication.sharath.SharathService.SharathUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SharathUserController {

    @Autowired
    private SharathUserService userService;

    @GetMapping("/CourseOverView")
    public ResponseEntity<?> getCourseOverview(@RequestBody CourseSharath course) {
        try {
            OverviewResponse overviewResponse = userService.getOverviewOfCourse(course.getCourseId());
            if (overviewResponse != null) {
                return ResponseEntity.of(Optional.of(overviewResponse));
            }
           return new ResponseEntity("Overview For the Course is not Available", HttpStatus.NOT_FOUND);
        }catch (Exception e)
        {
            return new ResponseEntity("Invalid Input", HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("/BasicCourses")
    public ResponseEntity<?> getBeginnerCourses(@RequestBody Category category)
    {
        try {
            List<CourseResponse> courseResponses = userService.getBasicCourses(category.getCategoryId());

            if(courseResponses.isEmpty())
            {
                return new ResponseEntity("Currently No Courses Avaialable in this Category",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.of(Optional.of(courseResponses));

        }catch (Exception e)
        {
            return new ResponseEntity("Invalid Input",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/AdvanceCourses")
    public ResponseEntity<?> getAdvancedCourses(@RequestBody Category category)
    {
        try {
            List<CourseResponse> courseResponses = userService.getAdvanceCourses(category.getCategoryId());

            if(courseResponses.isEmpty())
            {
                return new ResponseEntity("Currently No Courses Avaialable in this Category",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.of(Optional.of(courseResponses));

        }catch (Exception e)
        {
            return new ResponseEntity("Invalid Input",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/AllCoursesOfCategory")
    public ResponseEntity<?> getAllCourses(@RequestBody Category category)
    {
        try {
            List<AllCoursesResponse> allCourseResponses = userService.getAllCoursesOf(category.getCategoryId());

            if(allCourseResponses.isEmpty())
            {
                return new ResponseEntity("Currently No Courses Avaialable in this Category",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.of(Optional.of(allCourseResponses));

        }catch (Exception e)
        {
            return new ResponseEntity("Invalid Input",HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/privacyPolicy")
    public ResponseEntity<?> getPrivacyPolicy() {
        try {
            String privacyPolicy = userService.getPolicy();
                return ResponseEntity.of(Optional.of(privacyPolicy));
        }catch (Exception e)
        {
            return new ResponseEntity("Privacy Policy Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/TermsAndConditions")
    public ResponseEntity<?> getTermsAndConditions() {
        try {
            String termsAndConditions = userService.getTermsAndConditions();
                return ResponseEntity.of(Optional.of(termsAndConditions));
        }catch (Exception e)
        {
            return new ResponseEntity("Terms and Conditions Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/OngoingCourse")
    public ResponseEntity<?> getOngoingCourses(@RequestBody String userName)
    {
        try {
            List<OngoingResponse> ongoingResponses = userService.getOngoingCourses(userName);
            if(ongoingResponses.isEmpty()){
                return new ResponseEntity<>("No Ongoing Courses or The Course You Enrolled has No Chapters yet.",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.of(Optional.of(ongoingResponses));
        }catch (Exception e) {
            return new ResponseEntity<>("Invalid Input ", HttpStatus.BAD_REQUEST);
        }
    }
}
