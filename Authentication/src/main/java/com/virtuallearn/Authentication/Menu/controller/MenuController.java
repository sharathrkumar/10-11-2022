package com.virtuallearn.Authentication.Menu.controller;

import com.virtuallearn.Authentication.Menu.dto.SideBarRequest;
import com.virtuallearn.Authentication.Menu.dto.SideBarResponse;
import com.virtuallearn.Authentication.Menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MenuController {

    @Autowired
    MenuService menuService;


    @GetMapping("/SideBar")
    public ResponseEntity<?> getSideBar(@RequestBody SideBarRequest sideBar){
        SideBarResponse response = menuService.getUserDetails(sideBar);
        if (response == null)
            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
        return ResponseEntity.of(Optional.of(response));
    }

    @GetMapping("/MyProfile")
    public ResponseEntity<?> getMyProfile(@RequestBody SideBarRequest request){
         SideBarResponse response = menuService.getUserDetails(request);
        if (response == null)
            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
        return ResponseEntity.of(Optional.of(menuService.getMyProfile(response , request)));
    }
}
