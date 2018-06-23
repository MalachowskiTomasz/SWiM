package com.company.swim3;

/**
 * Created by T4riS on 18.03.2018.
 */

public class MovieData {
    private String title;
    private String description;
    private int imageResourceID;

    public MovieData(String title, String description, int imagePath) {
        this.title = title;
        this.description = description;
        this.imageResourceID = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagePath() {
        return imageResourceID;
    }

    public void setImagePath(int imagePath) {
        this.imageResourceID = imagePath;
    }
}
