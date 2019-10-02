package com.example.ignite19;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Participation implements Parcelable {

    private int participation;
    private String event_name;
    private String participant1;
    private String participant2;
    private String participant3;
    private String participant4;
    private String participant5;
    private int eventIconUri;

    private int numberOfParticipants;
    private String eventDesc;

    Participation(){

    }

    public Participation(int participation, String event_name, String participant1, String participant2, String participant3, String participant4, String participant5) {
        this.participation = participation;
        this.event_name = event_name;
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.participant3 = participant3;
        this.participant4 = participant4;
        this.participant5 = participant5;

    }

    public Participation(Builder builder) {
        this.participation = builder.participation;
        this.participant1 = builder.participant1;
        this.participant2 = builder.participant2;
        this.participant3 = builder.participant3;
        this.participant4 = builder.participant4;
        this.participant5 = builder.participant5;
        this.event_name = builder.event_name;
        this.eventIconUri = builder.eventIconURI;
        this.numberOfParticipants = builder.numberOfParticipants;
        this.eventDesc = builder.eventDesc;
    }


    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public int getParticipation() {
        return participation;
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



    public String getEvent_name() {
        return event_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(participation);
        parcel.writeString(event_name);
        parcel.writeString(participant1);
        parcel.writeString(participant2);
        parcel.writeString(participant3);
        parcel.writeString(participant4);
        parcel.writeString(participant5);
        parcel.writeInt(eventIconUri);
    }

    public static final Parcelable.Creator<Participation> CREATOR
            = new Parcelable.Creator<Participation>() {
        public Participation createFromParcel(Parcel in) {
            return new Participation(in);
        }

        public Participation[] newArray(int size) {
            return new Participation[size];
        }
    };

    public int getEventIconUri() {
        return eventIconUri;
    }

    private Participation(Parcel in) {
        participation = in.readInt();
        event_name = in.readString();
        participant1 = in.readString();
        participant2 = in.readString();
        participant3 = in.readString();
        participant4 = in.readString();
        participant5 = in.readString();
        eventIconUri = in.readInt();
    }


    public static class Builder{

        public int participation;
        public String event_name;
        public String participant1;
        public String participant2;
        public String participant3;
        public String participant4;
        public String participant5;
        public int eventIconURI;


        public int numberOfParticipants;
        public String eventDesc;


        public Builder() {
        }

        public Builder(int participation, String event_name) {
            this.participation = participation;
            this.event_name = event_name;
        }

        public int getParticipation() {
            return participation;
        }

        public String getEvent_name() {
            return event_name;
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

        public Builder setNumberOfParticipants(int numberOfParticipants) {
            this.numberOfParticipants = numberOfParticipants;
            return this;
        }


        public Builder setEventDesc(String eventDesc) {
            this.eventDesc = eventDesc;
            return this;
        }

        public Builder setEventIconURI(int eventIconURI) {
            this.eventIconURI = eventIconURI;
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

        public Participation build(){
            return new Participation(this);
        }
    }
}
