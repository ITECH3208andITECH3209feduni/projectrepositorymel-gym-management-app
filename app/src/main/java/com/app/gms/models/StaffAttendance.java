package com.app.gms.models;

import java.io.Serializable;

public class StaffAttendance implements Serializable {

    String date;
    String attendance;

    public StaffAttendance() {
    }

    public StaffAttendance(String date, String attendance) {
        this.date = date;
        this.attendance = attendance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
