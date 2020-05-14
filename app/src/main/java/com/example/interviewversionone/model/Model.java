package com.example.interviewversionone.model;

public class Model {
    String title;
    String image;
    String desc;

    public Model() {
    }

    /*public Model(String title, String image) {
        this.title = title;
        this.image = image;
    }*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
