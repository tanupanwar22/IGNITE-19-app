package com.NITK.ignite.ui.schedule;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class eventSchedule {
    private String start_time;
    private String event_type;
    private String event_name;
    private String event_venue;
    private String event_day;
    private String event_date;



    public eventSchedule(){

    }

    public eventSchedule(String time,String type,String name,String venue,String day,String date){

        this.start_time=time;
        this.event_type=type;
        this.event_name=name;
        this.event_venue=venue;
        this.event_day=day;
        this.event_date=date;
    }



    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public void setEvent_venue(String event_venue) {
        this.event_venue = event_venue;
    }

    public String getEvent_day() {
        return event_day;
    }

    public void setEvent_day(String event_day) {
        this.event_day = event_day;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }
}
