package com.virtuallearn.Authentication.sharath.SharathService;

import com.virtuallearn.Authentication.sharath.response.AllCoursesResponse;
import com.virtuallearn.Authentication.sharath.response.CourseResponse;
import com.virtuallearn.Authentication.sharath.response.OngoingResponse;
import com.virtuallearn.Authentication.sharath.response.OverviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SharathUserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public OverviewResponse getOverviewOfCourse(int courseId)
    {
        try {
            return jdbcTemplate.queryForObject("SELECT courseName,coursePhoto,categoryName,chapterCount,lessonCount,courseTagLine,previewVideo,overview.description,testCount,courseMaterialId,courseDuration,learningOutCome,requirements,instructorName,url,profilePhoto,instructor.description AS instructorDescription FROM overview INNER JOIN instructor ON overview.instructorId = instructor.instructorId  INNER JOIN course ON overview.courseId = course.courseId AND course.courseId = ? INNER JOIN category ON course.categoryId = category.categoryId",new BeanPropertyRowMapper<>(OverviewResponse.class),courseId);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public List<CourseResponse> getBasicCourses(int categoryId)
    {
        return jdbcTemplate.query("SELECT courseName,previewVideo,chapterCount,courseDuration FROM overView INNER JOIN course ON course.courseId = overview.courseId WHERE categoryId = "+categoryId+" AND difficultyLevel = 'Beginner'",new BeanPropertyRowMapper<>(CourseResponse.class));
    }

    public List<CourseResponse> getAdvanceCourses(int categoryId)
    {
        return jdbcTemplate.query("SELECT courseName,previewVideo,chapterCount,courseDuration FROM overView INNER JOIN course ON course.courseId = overview.courseId WHERE categoryId = "+categoryId+" AND difficultyLevel = 'Advanced'",new BeanPropertyRowMapper<>(CourseResponse.class));
    }

    public List<AllCoursesResponse> getAllCoursesOf(int categoryId)
    {
        return jdbcTemplate.query("SELECT coursePhoto,courseName,chapterCount,categoryName FROM overView INNER JOIN course ON course.courseId = overview.courseId INNER JOIN category ON category.categoryId = course.categoryId WHERE category.categoryId = "+categoryId+" ",new BeanPropertyRowMapper<>(AllCoursesResponse.class));
    }

    public String getPolicy()
    {
        return jdbcTemplate.queryForObject("SELECT privacyPolicy FROM policy WHERE policyId=(SELECT max(policyId) FROM policy)",String.class);
    }

    public String getTermsAndConditions()
    {
        return jdbcTemplate.queryForObject("SELECT termsAndConditions FROM policy WHERE policyId=(SELECT max(policyId) FROM policy)",String.class);
    }

    public List<OngoingResponse> getOngoingCourses(String userName)
    {
        List<OngoingResponse> ongoingResponses = new ArrayList<>();

        List<Integer> courseId = jdbcTemplate.queryForList("SELECT courseId FROM enrollment WHERE  userName = ?", Integer.class,userName);

        for (Integer i: courseId)
        {
            Integer completedChapter = jdbcTemplate.queryForObject("SELECT count(chapterId) FROM chapterProgress WHERE courseId = ? AND userName = ? AND chapterCompletedStatus = true", Integer.class,i,userName);
            Integer totalChapter = jdbcTemplate.queryForObject("SELECT count(chapterId) FROM chapterProgress WHERE courseId = ? AND userName = ?", Integer.class,i,userName);
            if(completedChapter < totalChapter) {
                OngoingResponse ongoingResponse = jdbcTemplate.queryForObject("SELECT courseName,coursePhoto FROM course WHERE courseId = ?", new BeanPropertyRowMapper<>(OngoingResponse.class), i);
                ongoingResponse.setCompletedChapter(completedChapter);
                ongoingResponse.setTotalChapter(totalChapter);
                ongoingResponses.add(ongoingResponse);
            }
        }
        return ongoingResponses;
    }

//
//    public CourseChapterResponse getCourseChapterResponse(Integer courseId,String userName)
//    {
//        return null;
//    }
//
//    public List<LessonResponse> getLessonResponses(Integer courseId,Integer lessonId,String userName)
//    {
//        try {
//            jdbcTemplate.queryForObject("SELECT * FROM enrollment WHERE courseId = ? AND userName = ?",new BeanPropertyRowMapper<>(Enrollment.class),courseId,userName);
//
//            return jdbcTemplate.query("SELECT lessonNumber,lessonName,lessonDuration,videoLink,lessonCompletionStataus FROM lesson INNER JOIN lessonProgress on lesson.lessonId = lessonProgress.lessonId AND lesson.lessonId = ?",new BeanPropertyRowMapper<>(LessonResponse.class),lessonId);
//        }
//        catch (Exception e)
//        {
//            return jdbcTemplate.query("SELECT lessonNumber,lessonName,lessonDuration")
//        }
//        return null;
//    }
}
