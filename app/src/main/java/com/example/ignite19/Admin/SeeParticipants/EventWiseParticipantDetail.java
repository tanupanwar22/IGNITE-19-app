package com.example.ignite19.Admin.SeeParticipants;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventWiseParticipantDetail implements Parcelable {
    protected EventWiseParticipantDetail(Parcel in) {

        uuid = in.readString();
        collegeName = in.readString();
        eventName = in.readString();
        participant1  = in.readString();
        participant2 = in.readString();
        participant3 = in.readString();
        participant4 = in.readString();
        participant5 = in.readString();
        eventIcon = in.readInt();
    }

    public static final Creator<EventWiseParticipantDetail> CREATOR = new Creator<EventWiseParticipantDetail>() {
        @Override
        public EventWiseParticipantDetail createFromParcel(Parcel in) {
            return new EventWiseParticipantDetail(in);
        }

        @Override
        public EventWiseParticipantDetail[] newArray(int size) {
            return new EventWiseParticipantDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(collegeName);
        parcel.writeString(eventName);
        parcel.writeString(participant1);
        parcel.writeString(participant2);
        parcel.writeString(participant3);
        parcel.writeString(participant4);
        parcel.writeString(participant5);
        parcel.writeInt(eventIcon);
    }



    private String uuid;
    private String eventName;
    private String collegeName;
    private String participant1;
    private String participant2;
    private String participant3;
    private String participant4;
    private String participant5;
    private int eventIcon;

    public EventWiseParticipantDetail(Builder builder){
        uuid = builder.uuid;
        eventName = builder.eventName;
        collegeName = builder.collegeName;
        participant1 = builder.participant1;
        participant2 = builder.participant2;
        participant3 = builder.participant3;
        participant4 = builder.participant4;
        participant5 = builder.participant5;
        eventIcon = builder.eventIcon;
    }


    public String getUuid() {
        return uuid;
    }

    public String getEventName() {
        return eventName;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public String getParticipant1() {
        return participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public String getParticipant3() {
        return participant3;
    }

    public String getParticipant4() {
        return participant4;
    }

    public String getParticipant5() {
        return participant5;
    }

    public static class Builder{
        private String uuid;
        private String eventName;
        private String collegeName;
        private String participant1;
        private String participant2;
        private String participant3;
        private String participant4;
        private String participant5;
        private int eventIcon;


        public Builder(String uuid, String collegeName) {
            this.uuid = uuid;
            this.collegeName = collegeName;
        }

        public String getUuid() {
            return uuid;
        }

        public String getCollegeName() {
            return collegeName;
        }

        public Builder setEventName(String eventName) {
            this.eventName = eventName;
            return this;
        }

        public Builder setParticipant1(String participant1) {
            this.participant1 = participant1;
            return this;
        }

        public Builder setParticipant2(String participant2) {
            this.participant2 = participant2;
            return this;
        }

        public Builder setParticipant3(String participant3) {
            this.participant3 = participant3;
            return this;
        }

        public Builder setParticipant4(String participant4) {
            this.participant4 = participant4;
            return this;
        }

        public Builder setParticipant5(String participant5) {
            this.participant5 = participant5;
            return this;
        }

        public Builder setEventIcon(int eventIcon) {
            this.eventIcon = eventIcon;
            return this;
        }

        public EventWiseParticipantDetail build(){
            return new EventWiseParticipantDetail(this);
        }
    }

}
