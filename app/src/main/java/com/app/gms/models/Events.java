package com.app.gms.models;

import java.io.Serializable;

public class Events implements Serializable {
    String event,venue,timings,details;

    public Events() {
    }

    public Events(String event, String venue, String timings, String details) {
        this.event = event;
        this.venue = venue;
        this.timings = timings;
        this.details = details;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
