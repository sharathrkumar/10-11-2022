package com.virtuallearn.Authentication.Menu.dao;


import com.virtuallearn.Authentication.Menu.dto.MyProfileResponse;
import com.virtuallearn.Authentication.Menu.dto.SideBarRequest;
import com.virtuallearn.Authentication.Menu.dto.SideBarResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MenuDataAccess {

    @Autowired
    JdbcTemplate jdbcTemplate;
    public SideBarResponse getDetails(SideBarRequest sideBar) {
        String query;
        String occupation;
        int status = jdbcTemplate.queryForObject("select count(*) from user where userName='" + sideBar.getUserName() + "'", Integer.class);
        if(status == 0)
            return null;
        try{
             occupation = jdbcTemplate.queryForObject("select subCategoryName from subCategory where subCategoryId=(select occupation from user where userName='" + sideBar.getUserName() + "')", String.class);
        } catch (Exception e){
        query = "select profilePhoto,fullName from user where userName='" + sideBar.getUserName() + "'";
        return jdbcTemplate.queryForObject(query,new BeanPropertyRowMapper<>(SideBarResponse.class));
        }
        query = "select profilePhoto,fullName from user where userName='" + sideBar.getUserName() + "'";
        SideBarResponse response =  jdbcTemplate.queryForObject(query,new BeanPropertyRowMapper<>(SideBarResponse.class));
        response.setOccupation(occupation);
        return response;
    }

    public MyProfileResponse getMyProfile(SideBarResponse response, SideBarRequest request) {
        int courseCompleted = jdbcTemplate.queryForObject("select count(*) from enrollment where completedDate>'0000-00-00' and userName='" + request.getUserName() + "'", Integer.class);
        int chapterCompletedStatus = jdbcTemplate.queryForObject("select count(*) from chapterProgress where userName='" + request.getUserName() + "' and courseId=(select courseId from enrollment where userName='" + request.getUserName() + "') and chapterId=(select chapterId from chapter where courseId=(select courseId from enrollment where userName='" + request.getUserName() +"')) and chapterCompletedStatus=true", Integer.class);
        int testCompletedStatus = jdbcTemplate.queryForObject("select count(*) from chapterProgress where userName='" + request.getUserName() + "' and courseId=(select courseId from enrollment where userName='" + request.getUserName() + "') and chapterId=(select chapterId from chapter where courseId=(select courseId from enrollment where userName='"+request.getUserName() +"')) and chapterTestPercentage>=0.00", Integer.class);
        MyProfileResponse myProfileResponse = jdbcTemplate.queryForObject("select email,dateOfBirth,mobileNumber from user where userName='" + request.getUserName() + "'",new BeanPropertyRowMapper<>(MyProfileResponse.class));
        myProfileResponse.setProfilePhoto(response.getProfilePhoto());
        myProfileResponse.setOccupation(response.getOccupation());
        myProfileResponse.setFullName(response.getFullName());
        return myProfileResponse;
    }
}
