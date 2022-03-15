package com.example.mixipe;

public class Model {
    String title, image;

    public Model(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public Model() {
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
