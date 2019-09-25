package com.example.ignite19;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserDataAndEventList implements Parcelable {
    private String event_name;
    private String event_date;
    private double event_latitude;
    private double event_longitude;
    private String event_venue;
    private int number_of_participants;
    private String event_description;
    private String event_duration;
    private int event_icon_uri;
    private int event_image_uri;
    private int user_participation;
    private String user_college_name;
    private String event_participant1;
    private String event_participant2;
    private String event_participant3;
    private String event_participant4;
    private String event_participant5;

    UserDataAndEventList(){

    }

    public UserDataAndEventList(Builder builder){
        this.event_name = builder.event_name;
        this.event_date = builder.event_date;
        this.event_latitude = builder.event_latitude;
        this.event_longitude = builder.event_longitude;
        this.event_venue = builder.event_venue;
        this.number_of_participants = builder.number_of_participants;
        this.event_description = builder.event_description;
        this.event_duration = builder.event_duration;
        this.event_icon_uri = builder.event_icon_uri;
        this.event_image_uri = builder.event_image_uri;
        this.user_participation = builder.user_participation;
        this.user_college_name = builder.user_college_name;
        this.event_participant1 = builder.event_participant1;
        this.event_participant2 = builder.event_participant2;
        this.event_participant3 = builder.event_participant3;
        this.event_participant4 = builder.event_participant4;
        this.event_participant5 = builder.event_participant5;
    }




    public String getEvent_name() {
        return event_name;
    }


    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public double getEvent_latitude() {
        return event_latitude;
    }

    public double getEvent_longitude() {
        return event_longitude;
    }

    public String getEvent_venue() {
        return event_venue;
    }

    public int getNumber_of_participants() {
        return number_of_participants;
    }

    public String getEvent_description() {
        return event_description;
    }

    public String getEvent_duration() {
        return event_duration;
    }

    public int getEvent_icon_uri() {
        return event_icon_uri;
    }

    public int getEvent_image_uri() {
        return event_image_uri;
    }


    public int getUser_participation() {
        return user_participation;
    }

    public String getUser_college_name() {
        return user_college_name;
    }

    public String getEvent_participant1() {
        return event_participant1;
    }

    public String getEvent_participant2() {
        return event_participant2;
    }

    public String getEvent_participant3() {
        return event_participant3;
    }

    public String getEvent_participant4() {
        return event_participant4;
    }

    public String getEvent_participant5() {
        return event_participant5;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(event_name);
        parcel.writeString(event_date);
        parcel.writeDouble(event_latitude);
        parcel.writeDouble(event_longitude);
        parcel.writeString(event_venue);
        parcel.writeInt(number_of_participants);
        parcel.writeString(event_description);
        parcel.writeString(event_duration);
        parcel.writeInt(event_icon_uri);
        parcel.writeInt(event_image_uri);
        parcel.writeInt(user_participation);
        parcel.writeString(user_college_name);
        parcel.writeString(event_participant1);
        parcel.writeString(event_participant2);
        parcel.writeString(event_participant3);
        parcel.writeString(event_participant4);
        parcel.writeString(event_participant5);



    }

    public static final Parcelable.Creator<UserDataAndEventList> CREATOR
            = new Parcelable.Creator<UserDataAndEventList>() {
        public UserDataAndEventList createFromParcel(Parcel in) {
            return new UserDataAndEventList(in);
        }

        public UserDataAndEventList[] newArray(int size) {
            return new UserDataAndEventList[size];
        }
    };

    private UserDataAndEventList(Parcel in) {
        event_name = in.readString();
        event_date = in.readString();
        event_latitude = in.readDouble();
        event_longitude = in.readDouble();
        event_venue = in.readString();
        number_of_participants = in.readInt();
        event_description = in.readString();
        event_duration = in.readString();
        event_icon_uri = in.readInt();
        event_image_uri = in.readInt();
        user_participation = in.readInt();
        user_college_name = in.readString();
        event_participant1 = in.readString();
        event_participant2 = in.readString();
        event_participant3 = in.readString() ;
        event_participant4 = in.readString() ;
        event_participant5 = in.readString();

    }







    public static class Builder{
        public String event_name;
        public String event_date;
        public double event_latitude;
        public double event_longitude;
        public String event_venue;
        public int number_of_participants;
        public String event_description;
        public String event_duration;
        public int event_icon_uri;
        public int event_image_uri;
        public int user_participation;
        public String user_college_name;
        public String event_participant1;
        public String event_participant2;
        public String event_participant3;
        public String event_participant4;
        public String event_participant5;
        Builder(){

        }
        public Builder(String event_name, String event_date){
            this.event_name = event_name;
            this.event_date = event_date;
        }

        public String getEvent_name() {
            return event_name;
        }

        public int getNumber_of_participants() {
            return number_of_participants;
        }

        public Builder setUser_participation(int user_participation) {
            this.user_participation = user_participation;
            return this;
        }

        public Builder setEvent_latitude(double event_latitude) {
            this.event_latitude = event_latitude;
            return this;
        }

        public Builder setEvent_longitude(double event_longitude) {
            this.event_longitude = event_longitude;
            return this;
        }

        public Builder setEvent_venue(String event_venue) {
            this.event_venue = event_venue;
            return this;
        }

        public Builder setNumber_of_participants(int number_of_participants) {
            this.number_of_participants = number_of_participants;
            return this;
        }

        public Builder setEvent_description(String event_description) {
            this.event_description = event_description;
            return this;
        }

        public Builder setEvent_duration(String event_duration) {
            this.event_duration = event_duration;
            return this;
        }

        public Builder setEvent_icon_uri(int event_icon_uri) {
            this.event_icon_uri = event_icon_uri;
            return this;
        }

        public Builder setEvent_image_uri(int event_image_uri) {
            this.event_image_uri = event_image_uri;
            return this;
        }

        public Builder setUser_college_name(String user_college_name) {
            this.user_college_name = user_college_name;
            return this;
        }

        public Builder setEvent_participant1(String event_participant1) {
            this.event_participant1 = event_participant1;
            return this;
        }

        public Builder setEvent_participant2(String event_participant2) {
            this.event_participant2 = event_participant2;
            return this;
        }

        public Builder setEvent_participant3(String event_participant3) {
            this.event_participant3 = event_participant3;
            return this;
        }

        public Builder setEvent_participant4(String event_participant4) {
            this.event_participant4 = event_participant4;
            return this;
        }

        public Builder setEvent_participant5(String event_participant5) {
            this.event_participant5 = event_participant5;
            return this;
        }

        public UserDataAndEventList build(){
            return new UserDataAndEventList(this);
        }

    }
}
