package com.aha.dishordersystem.data.model.dish;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * 菜类别
 */
public class Category extends LitePalSupport {

    @Column(nullable = false)
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
