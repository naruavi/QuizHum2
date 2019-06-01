package com.example.design1.models;

public class CategoryDefinition {
    private int categoryId;
    private String categoryName;
    private String imageURL;

    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "CategoryDefinition{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
