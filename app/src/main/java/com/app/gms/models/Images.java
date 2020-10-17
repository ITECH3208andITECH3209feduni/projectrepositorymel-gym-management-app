package com.app.gms.models;

import java.io.Serializable;

public class Images implements Serializable {
    String url;

    public Images() {
    }

    public Images(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
