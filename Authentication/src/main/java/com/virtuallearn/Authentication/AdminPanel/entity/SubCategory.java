package com.virtuallearn.Authentication.AdminPanel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory
{
    private int categoryId;
    private int subCategoryId;
    private String  subCategoryName;
}
