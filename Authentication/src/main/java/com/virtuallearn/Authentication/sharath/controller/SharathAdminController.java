package com.virtuallearn.Authentication.sharath.controller;

import com.virtuallearn.Authentication.sharath.entity.OverviewSharath;
import com.virtuallearn.Authentication.sharath.model.Category;
import com.virtuallearn.Authentication.sharath.model.Chapter;
import com.virtuallearn.Authentication.sharath.model.Policy;
import com.virtuallearn.Authentication.sharath.model.SubCategory;
import com.virtuallearn.Authentication.sharath.SharathService.SharathAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class SharathAdminController {

    @Autowired
    private SharathAdminService adminService;


    @PostMapping("/Category")
    public ResponseEntity<?> addCategory(@RequestBody Category category)
    {
        int change = adminService.addCategory(category);

        if(change > 0)
            return ResponseEntity.of(Optional.of("Category " + category.getCategoryName()+ " has been Added SuccessFully"));
        else
            return new ResponseEntity<>("Category Type Already Exists",HttpStatus.ALREADY_REPORTED);
    }

    @GetMapping("/Categories")
    public ResponseEntity<?> getCategories()
    {
        List<Category> categories = adminService.getCategories();

        if((categories) != null)
            return ResponseEntity.of(Optional.of(categories));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/SubCategory")
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategory subcategory)
    {
        int change = adminService.addSubCategory(subcategory);

        if(change > 0)
            return ResponseEntity.of(Optional.of("SubCategory " + subcategory.getSubCategoryName()+ " has been Added SuccessFully"));
        else
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
    }

    @GetMapping("/SubCategories")
    public ResponseEntity<?> getSubcategory(@RequestBody Category category)
    {
        List<SubCategory> subCategories = adminService.getSubCategories(category);

        if((subCategories) != null)
            return ResponseEntity.of(Optional.of(subCategories));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/Chapter")
    public ResponseEntity<?> addChapter(@RequestBody Chapter chapter)
    {
        int change = adminService.addChapter(chapter);

        if(change > 0)
            return ResponseEntity.of(Optional.of("Chapter " + chapter.getChapterName()+ " has been Added SuccessFully"));
        else
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
    }


    @PostMapping("/Overview")
    public ResponseEntity<?> addOverview(@RequestBody OverviewSharath overview)
    {
        if(adminService.addOverView(overview) != 0)
            return ResponseEntity.of(Optional.of("OverView for the Course" + overview.getCourseId() + " has been Added SuccessFully"));
        else
            return new ResponseEntity<>("Overview For the Course "+overview.getCourseId()+"has already is Already Present",HttpStatus.ALREADY_REPORTED);
    }

    @PostMapping("/Policy")
    public ResponseEntity<?> addPolicy(@RequestBody Policy policy){
        if(adminService.addPolicy(policy) != 0){
            return new ResponseEntity<>("Privacy Policy and Terms and Condition Updated",HttpStatus.OK);
        }
        return new ResponseEntity<>("You Didn't changed the Privacy Policy and Terms and Conditions or Failed to Update the Privacy Policy and Terms and Condition",HttpStatus.ALREADY_REPORTED);
    }


}
