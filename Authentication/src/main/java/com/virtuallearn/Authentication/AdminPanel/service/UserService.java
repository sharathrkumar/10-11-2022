package com.virtuallearn.Authentication.AdminPanel.service;

import com.virtuallearn.Authentication.AdminPanel.entity.*;
import com.virtuallearn.Authentication.AdminPanel.request.EnrollmentRequest;
import com.virtuallearn.Authentication.AdminPanel.request.HomeHeaderRequest;
import com.virtuallearn.Authentication.AdminPanel.response.HomeAllCourse;
import com.virtuallearn.Authentication.AdminPanel.response.HomeResponseTopHeader;
import com.virtuallearn.Authentication.AdminPanel.response.PopularCorseInEachCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // for home top header
    private String GET_OCCUPATION = "SELECT occupation FROM user WHERE userName = ?";
    private String HOME_TOP_BAR = "SELECT coursePhoto, courseName FROM course";
    private  String COURSE_EXISTANCE_IN_SUBCATEGORY = "SELECT coursePhoto, courseName FROM course WHERE subCategoryId= ?";
    private String GET_CATEGORY_FROM_SUBCATEGORY = "Select categoryId from subCategory WHERE subcategoryId = ?";
    private String GET_COURSES = "SELECT * FROM course WHERE categoryId = ?";

    // "All" in home page
    private String GET_ALL_COURSES = "SELECT coursePhoto, courseName,categoryId, chapterCount FROM course,overView WHERE course.courseId = overView.courseId";
   //"popular" in home page
    private String GET_ALL_ENROLLED_COURSES = "SELECT distinct courseId FROM enrollment";
    private String GET_ENROLLMENT_COUNT = "SELECT count(courseId) FROM enrollment WHERE courseId= ?";
    private String GET_ALL_POPULAR_COURSES = "SELECT c.coursePhoto, c.courseName,c.categoryId, o.chapterCount FROM course c,overView o WHERE c.courseId=? and c.courseId = o.courseId";




    // "newest" in home
    private String GET_ALL_NEW_COURSES = "SELECT course.courseId, coursePhoto, courseName,categoryId, chapterCount FROM course,overview WHERE course.courseId = overview.courseId";

    //enrollment(10-11-200)
    private String ALREADY_ENROLLED_STATUS = "SELECT * FROM enrollment WHERE userName= ? and courseId = ?";
    private String GET_ENROLLED = "INSERT INTO enrollment(userName,courseId,joinDate) values(?,?,?)";
    private String ADD_TO_COURSE_PROGRESS = "INSERT INTO courseProgress(userName,courseId) values(?,?)";
    private String GET_ALL_CHAPTERS_UNDER_COURSE = "SELECT * FROM chapter WHERE courseId = ?";
    private String ADD_CHAPTER_PROGRESS = "INSERT INTO chapterProgress(userName,courseId,chapterId,testId) values(?,?,?,?)";
    private String GET_TEST_OF_CHAPTER = "SELECT testId FROM test WHERE chapterId = ?";
    private String GET_ALL_LESSONS_OF_CHAPTER = "SELECT * FROM lesson WHERE chapterId = ?";
    private String ADD_LESSON_PROGRESS = "INSERT INTO lessonProgress(userName,chapterId,lessonId) values(?,?,?)";

    //home page "popular courses in category" part
    private String GET_CATEGORIES = "SELECT * FROM category";
    private String GET_CATEGORY_ENROLLED_COUNT = "SELECT count(c.courseId) FROM enrollment e, course c , category ct WHERE  ct.categoryId = ? and ct.categoryId = c.categoryId and c.courseId = e.courseId";
    private String GET_COURSES_UNDER_EACH_CATEGORY = "SELECT c.courseName,o.chapterCount, c.courseDuration,c.previewVideo from course c, overview o , category ct WHERE ct.categoryId = ? and  ct.categoryId = c.categoryId and c.courseId = o.courseId";

    //if user occupation is student display all courses, if not, based on subcategory matching occupation display courses of subcategory ,if subcategory is not having any courses then tahke
    // category of the sub category and display courses under that category
    public List<HomeResponseTopHeader> HomePageTopBar(HomeHeaderRequest homeHeaderRequest)   // front end should send username when ever they call home api as a response
    {
        User user= jdbcTemplate.queryForObject(GET_OCCUPATION,(rs, rowNum) -> {
            return new User(rs.getInt("occupation"));
        }, homeHeaderRequest.getUserName());
        System.out.println(user.getOccupation());
        if(user.getOccupation() == 0)
        {
            List<HomeResponseTopHeader> courseListForStudent = jdbcTemplate.query(HOME_TOP_BAR,(rs, rowNum) -> {
                return new HomeResponseTopHeader(rs.getString("coursePhoto"), rs.getString("courseName"));
            });
            return courseListForStudent;
        }
        else
        {
            try
            {
                List<HomeResponseTopHeader> course = jdbcTemplate.query(COURSE_EXISTANCE_IN_SUBCATEGORY,(rs, rowNum) -> {
                    return new HomeResponseTopHeader(rs.getString("coursePhoto"), rs.getString("courseName"));
                }, user.getOccupation());
                if(course.size() !=0)
                {
                    return course;
                }
            }
            catch(NullPointerException e)
            {
                int categoryId = jdbcTemplate.queryForObject(GET_CATEGORY_FROM_SUBCATEGORY, new Object[] {user.getOccupation()}, Integer.class);
                List<HomeResponseTopHeader> courses = jdbcTemplate.query(GET_COURSES,(rs, rowNum) -> {
                    return new HomeResponseTopHeader(rs.getString("coursePhoto"), rs.getString("courseName"));
                }, categoryId);
                return courses;
            }
        }
        return null;
    }

    //"All" in home page display all the courses
    public List<HomeAllCourse> getAllCourses()
    {
        List<HomeAllCourse> allCourses = jdbcTemplate.query(GET_ALL_COURSES,(rs, rowNum) -> {
            return new HomeAllCourse(rs.getString("coursePhoto"), rs.getString("courseName"), rs.getInt("categoryId"),rs.getInt("chapterCount"));
        });
        return allCourses;
    }

    //"popular" in home page , filter all the courses with maximum enrollments more than 5
    public List<HomeAllCourse> getPopularCourses()
    {
        List<HomeAllCourse> popularCourseList = new ArrayList<>();
        List<Enrollment> allEnrolledCourses = jdbcTemplate.query(GET_ALL_ENROLLED_COURSES,(rs, rowNum) -> {
            return new Enrollment(rs.getInt("courseId"));
        });
        System.out.println(allEnrolledCourses);
        for(int i=0;i<allEnrolledCourses.size();i++)
        {
                int enrolmentCount = jdbcTemplate.queryForObject(GET_ENROLLMENT_COUNT, new Object[] {allEnrolledCourses.get(i).getCourseId()}, Integer.class);
                if(enrolmentCount >1)
                {
                    HomeAllCourse homeAllCourse= jdbcTemplate.queryForObject(GET_ALL_POPULAR_COURSES,(rs, rowNum) -> {
                        return new HomeAllCourse(rs.getString("coursePhoto"), rs.getString("courseName"), rs.getInt("categoryId"),rs.getInt("chapterCount"));
                    },allEnrolledCourses.get(i).getCourseId());
                    popularCourseList.add(homeAllCourse);
                }
        }
        System.out.println(popularCourseList);
     return popularCourseList;
     }


     public List<HomeAllCourse> getNewCourses()
     {
         List<HomeAllCourse> newCourseList = new ArrayList<>();
         List<HomeAllCourse> allNewCourses = jdbcTemplate.query(GET_ALL_NEW_COURSES,(rs, rowNum) -> {
             return new HomeAllCourse(rs.getInt("courseId"),rs.getString("coursePhoto"), rs.getString("courseName"), rs.getInt("categoryId"),rs.getInt("chapterCount"));
         });
         //System.out.println(allNewCourses.size());
         int size = allNewCourses.size()-1;
         for(int i=size;i>0;i--)
         {
             //System.out.println(allNewCourses.get(i).getCourseId());
             HomeAllCourse homeAllCourse= jdbcTemplate.queryForObject(GET_ALL_POPULAR_COURSES,(rs, rowNum) -> {
                 return new HomeAllCourse(rs.getString("coursePhoto"), rs.getString("courseName"), rs.getInt("categoryId"),rs.getInt("chapterCount"));
             },allNewCourses.get(i).getCourseId());
             newCourseList.add(homeAllCourse);
         }
         return newCourseList;
     }



     //10-11-2022**********************************************************************************

    public List<PopularCorseInEachCategory> popularCoursesInCategory()
    {
        List<PopularCorseInEachCategory> categoryCoursesList = new ArrayList<>();
        List<Category> categoriesList = jdbcTemplate.query(GET_CATEGORIES,(rs, rowNum) -> {
            return new Category(rs.getInt("categoryId"), rs.getString("categoryName"), rs.getString("categoryPhoto"));
        });

        if(categoriesList.size() == 0)
        {
            return null;
        }
        for(int i=0;i<categoriesList.size();i++)
        {
           int enrollmentCount = jdbcTemplate.queryForObject(GET_CATEGORY_ENROLLED_COUNT, new Object[] {categoriesList.get(i).getCategoryId()}, Integer.class);
           if(enrollmentCount >2)
           {
               try
               {
                   PopularCorseInEachCategory popularCourseInEachCategory = jdbcTemplate.queryForObject(GET_COURSES_UNDER_EACH_CATEGORY,(rs, rowNum) -> {
                       return new PopularCorseInEachCategory(rs.getString("courseName"), rs.getInt("chapterCount"), rs.getString("courseDuration"),rs.getString("previewVideo"));
                   },categoriesList.get(i).getCategoryId());
                   categoryCoursesList.add(popularCourseInEachCategory);
               }
               catch(NullPointerException exp)
               {
                   return null;
               }
           }
        }
        return categoryCoursesList;
    }


    public String enrollment(EnrollmentRequest enrollmentRequest)
    {
        try
        {
            Enrollment enrollmentRequest1 = jdbcTemplate.queryForObject(ALREADY_ENROLLED_STATUS,(rs, rowNum) -> {
                return new Enrollment(rs.getString("userName"), rs.getInt("courseId"), rs.getDate("joinDate"),rs.getDate("completedDate"),rs.getInt("courseScore"));
            },enrollmentRequest.getUserName(),enrollmentRequest.getCourseId());
            return "You have already enrolled for this course";
        }
        catch(EmptyResultDataAccessException e)
        {
           jdbcTemplate.update(GET_ENROLLED,enrollmentRequest.getUserName(), enrollmentRequest.getCourseId(), enrollmentRequest.getJoinDate());
           jdbcTemplate.update(ADD_TO_COURSE_PROGRESS, enrollmentRequest.getUserName(), enrollmentRequest.getCourseId());
           List<Chapter> chaptersOfCourse = jdbcTemplate.query(GET_ALL_CHAPTERS_UNDER_COURSE,(rs, rowNum) -> {
               return new Chapter(rs.getInt("chapterId"), rs.getInt("courseId"), rs.getInt("chapterNumber"),rs.getString("chapterName"),rs.getString("chapterDuration"));
           },enrollmentRequest.getCourseId());

           if(chaptersOfCourse.size() == 0)
           {
               return "Chapters are not yet added for this course";
           }
           else
           {
               for(int i=0;i<chaptersOfCourse.size();i++)
               {
                   try
                   {
                       int testIdOfChapter = jdbcTemplate.queryForObject(GET_TEST_OF_CHAPTER, new Object[] {chaptersOfCourse.get(i).getChapterId()}, Integer.class);
                       jdbcTemplate.update(ADD_CHAPTER_PROGRESS, enrollmentRequest.getUserName(), enrollmentRequest.getCourseId(),chaptersOfCourse.get(i).getChapterId(),testIdOfChapter);
                   }
                   catch(EmptyResultDataAccessException ex)
                   {
                       return "Test for this course is not yet been added";
                   }
                   List<Lesson> lessonsOfChapter = jdbcTemplate.query(GET_ALL_LESSONS_OF_CHAPTER,(rs, rowNum) -> {
                       return new Lesson(rs.getInt("lessonId"), rs.getInt("lessonNumber"), rs.getInt("chapterId"),rs.getString("lessonName"), rs.getString("lessonduration"),rs.getString("videoLink"));
                   },chaptersOfCourse.get(i).getChapterId());

                   if(lessonsOfChapter.size() == 0)
                   {
                       return "Lessons are not yet added for this course";
                   }
                   else
                   {
                      for(int j=0;j<lessonsOfChapter.size();j++)
                      {
                         jdbcTemplate.update(ADD_LESSON_PROGRESS, enrollmentRequest.getUserName(),chaptersOfCourse.get(i).getChapterId(),lessonsOfChapter.get(j).getLessonId());
                      }
                   }
               }
           }
        }
        return "You have enrolled to the course successfully";
    }
}
