package com.example.easycourse.model;

public class Course {
    private String name;
    private String author;
    private String subject;
    private String schoolLevel;
    private int imageResource;
    private boolean isFavorite = false;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }


    public int getImageResource() {
        return imageResource;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
    }

    public void toggleFavorite() {
        this.isFavorite = isFavorite;
    }
}
