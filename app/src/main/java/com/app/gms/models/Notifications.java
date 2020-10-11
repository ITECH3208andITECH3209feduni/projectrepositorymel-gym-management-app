package com.app.gms.models;

import java.io.Serializable;

public class Notifications implements Serializable {
    String title,description;

    public Notifications() {
    }

    public Notifications(String title, String description) {
        this.title = title;
        this.description = description;
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
}
