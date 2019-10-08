package com.NITK.ignite.ui.notificationModule;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Events {
    private String event_name;
    private String event_date;
    private double event_latitude;
    private double event_longitude;
    private String event_venue;
    private int number_of_participants;
    private String event_description;
    private String event_duration;

    public Events(){

    }
   public Events(String event_name,String event_venue,String event_date,String event_description,int number_of_participants,String event_duration,double event_latitude,double event_longitude){
       this.event_date = event_date;
       this.event_venue = event_venue;
       this.event_name = event_name;
       this.event_latitude = event_latitude;
       this.event_longitude = event_longitude;
       this.event_duration = event_duration;
       this.number_of_participants = number_of_participants;
       this.event_description = event_description;
   }

    public String getEvent_venue() {
        return event_venue;
    }

    public String getEvent_name() {
        return event_name;
    }

    public double getEvent_latitude() {
        return event_latitude;
    }

    public double getEvent_longitude() {
        return event_longitude;
    }

    public String getEvent_duration() {
        return event_duration;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_description() {
        return event_description;
    }

    public int getNumber_of_participants() {
        return number_of_participants;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public void setEvent_latitude(double event_latitude) {
        this.event_latitude = event_latitude;
    }

    public void setEvent_longitude(double event_longitude) {
        this.event_longitude = event_longitude;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public void setNumber_of_participants(int number_of_participants) {
        this.number_of_participants = number_of_participants;
    }

    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }

    public void setEvent_duration(String event_duration) {
        this.event_duration = event_duration;
    }
}
