package com.example.firebasetest;

import java.util.List;
import java.util.Map;

public class Meeting {
    private String date;
    private String meetingName;
    private String latitude;
    private String longitude;
    private List<Map<String, Object>> list;

    Meeting(String meetingName, String date, String latitude, String longitude, List<Map<String, Object>> list){
        this.meetingName = meetingName;
        this.date = date;
        this.latitude=latitude;
        this.longitude=longitude;
        this.list = list;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setList(List<Map<String, Object>> list){
        //this.list.clear();
        this.list.addAll(list);
    }

    public List<Map<String, Object>> getList(){
        return this.list;
    }

    public String getMeetingName() {
        return this.meetingName;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getDate(){
        return this.date;
    }
}

