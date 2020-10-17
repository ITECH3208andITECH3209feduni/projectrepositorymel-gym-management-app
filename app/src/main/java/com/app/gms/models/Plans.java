package com.app.gms.models;

import java.io.Serializable;

public class Plans implements Serializable {
    String title;
    String description;
    String price;
    String trainer;

    public Plans() {
    }

    public Plans(String title, String description, String price, String trainer) {
        this.title = title;
        this.description = description;
        this.price=price;
        this.trainer=trainer;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
}
