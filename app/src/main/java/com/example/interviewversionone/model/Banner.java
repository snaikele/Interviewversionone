package com.example.interviewversionone.model;

public class Banner {
    private String id, name, images;


    public Banner() {
    }
   /* public Banner(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return images;
    }

    public void setImage(String image) {
        this.images = image;
    }
}
