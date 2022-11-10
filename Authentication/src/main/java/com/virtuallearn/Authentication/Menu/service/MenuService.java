package com.virtuallearn.Authentication.Menu.service;


import com.virtuallearn.Authentication.Menu.dao.MenuDataAccess;
import com.virtuallearn.Authentication.Menu.dto.MyProfileResponse;
import com.virtuallearn.Authentication.Menu.dto.SideBarRequest;
import com.virtuallearn.Authentication.Menu.dto.SideBarResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {


    @Autowired
    MenuDataAccess dataAccess;
    public SideBarResponse getUserDetails(SideBarRequest sideBar) {
        return dataAccess.getDetails(sideBar);
    }

    public MyProfileResponse getMyProfile(SideBarResponse response, SideBarRequest request) {
        return dataAccess.getMyProfile(response,request);
    }
}
