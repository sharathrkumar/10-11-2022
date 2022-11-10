package com.virtuallearn.Authentication.sharath.SharathService;

import com.virtuallearn.Authentication.sharath.entity.OverviewSharath;
import com.virtuallearn.Authentication.sharath.model.Category;
import com.virtuallearn.Authentication.sharath.model.Chapter;
import com.virtuallearn.Authentication.sharath.model.Policy;
import com.virtuallearn.Authentication.sharath.model.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.EmptyStackException;
import java.util.List;

@Service
public class SharathAdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addCategory(Category category)
    {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM category WHERE categoryName = ?", new BeanPropertyRowMapper<>(Category.class),category.getCategoryName());
            return 0;
        }
        catch (Exception e)
        {
            if(category.getCategoryName().isEmpty())
                throw new EmptyStackException();
            else
                return jdbcTemplate.update("INSERT INTO category(categoryName) VALUES(?)",category.getCategoryName());
        }
    }

    int pages = 2;
    int lowerLimit = 0;
    int upperLimit = pages;

    public List<Category> getCategories()
    {
        List<Category> categories=  jdbcTemplate.query("SELECT * FROM category limit ?,?",(rs, rowNum) ->
        {
            return new Category(rs.getInt("categoryId"),rs.getString("categoryName"));
        },lowerLimit,upperLimit);
        lowerLimit = lowerLimit+pages;

        if(categories.size()==0)
        {
            lowerLimit=0;
            List <Category> categories1= jdbcTemplate.query("SELECT * FROM category limit ?,?", (rs, rowNum) ->
                    new Category(rs.getInt("categoryId"),rs.getString("categoryName")),lowerLimit,upperLimit);
            return categories1;
        }
        return categories;
    }

    public int addSubCategory(SubCategory subCategory)
    {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM subCategory WHERE subCategoryName = ?", new BeanPropertyRowMapper<>(Category.class),subCategory.getSubCategoryName());
            return 0;
        }
        catch (Exception e)
        {
            return jdbcTemplate.update("INSERT INTO subCategory(categoryId,subCategoryName) VALUES(?,?)",subCategory.getCategoryId(),subCategory.getSubCategoryName());
        }
    }

    public List<SubCategory> getSubCategories(Category category)
    {
        List<SubCategory> subCategories=  jdbcTemplate.query("SELECT * FROM subCategory WHERE categoryId = ? limit ?,?",(rs, rowNum) ->
                new SubCategory(rs.getInt("categoryId"),rs.getInt("subCategoryId"),rs.getString("subCategoryName")),category.getCategoryId(),lowerLimit,upperLimit);
        lowerLimit = lowerLimit+pages;
        if(subCategories.size()==0)
        {
            lowerLimit=0;
            List<SubCategory> subCategories1=  jdbcTemplate.query("SELECT * FROM subCategory WHERE categoryId = ? limit ?,?",(rs, rowNum) ->
            {
                return new SubCategory(rs.getInt("categoryId"),rs.getInt("subCategoryId"),rs.getString("subCategoryName"));
            },category.getCategoryId(),lowerLimit,upperLimit);
            return subCategories1;
        }
        return subCategories;
    }

    public int addChapter(Chapter chapter){
        return jdbcTemplate.update("INSERT INTO chapter(courseId,chapterNumber,chapterName) VALUES(?,?,?)",chapter.getCourseId(),chapter.getChapterNumber(),chapter.getChapterName());
    }

    public int addOverView(OverviewSharath overview){

        try{
            jdbcTemplate.queryForObject("SELECT courseId FROM overview where courseId = ?",new BeanPropertyRowMapper<>(OverviewSharath.class),overview.getCourseId());
            return 0;
        }
        catch (Exception e) {
            int chapterCount = jdbcTemplate.queryForObject("SELECT COUNT(courseId) FROM chapter where courseId = ?", Integer.class, overview.getCourseId());
            int lessonCount = jdbcTemplate.queryForObject("SELECT COUNT(courseId) FROM lesson where courseId = ?", Integer.class, overview.getCourseId());


            List<Integer> chapterIDs = jdbcTemplate.queryForList("SELECT chapterId FROM chapter WHERE courseId = ?", Integer.class, overview.getCourseId());

            int testCount = 0;
            for (int i : chapterIDs) {
                testCount += jdbcTemplate.queryForObject("SELECT COUNT(testId) FROM test where chapterId = ?", Integer.class, i);
            }

            return jdbcTemplate.update("INSERT INTO overView(courseId,courseTagLine,description,chapterCount,lessonCount,testCount,learningOutCome,requirements,instructorId,difficultyLevel) VALUES(?,?,?,?,?,?,?,?,?,?)",
                    overview.getCourseId(), overview.getCourseTagLine(), overview.getDescription(), chapterCount, lessonCount, testCount, overview.getLearningOutCome(),overview.getRequirements(), overview.getInstructorId(),overview.getDifficultyLevel());
        }
    }

    public int addPolicy(Policy policy)
    {
        try
        {
            jdbcTemplate.queryForObject("SELECT termsAndConditions FROM policy WHERE termsAndConditions = ? AND privacyPolicy = ?",String.class,policy.getTermsAndConditions(),policy.getPrivacyPolicy());
            return 0;
        }
        catch (Exception e)
        {
            return jdbcTemplate.update("INSERT INTO policy(termsAndConditions,privacyPolicy) VALUES(?,?)",policy.getTermsAndConditions(),policy.getPrivacyPolicy());
        }
    }


}
