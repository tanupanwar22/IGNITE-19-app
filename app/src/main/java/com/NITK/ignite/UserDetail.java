package com.NITK.ignite;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class UserDetail implements Parcelable {

    String college_name;
    String team_leader;
    String team_member1;
    String team_member2;
    String team_member3;
    String team_member4;

    public UserDetail(){

    }

    public UserDetail(String college_name,String team_leader,String team_member1,String team_member2,String team_member3,String team_member4){
        this.college_name  = college_name;
        this.team_leader = team_leader;
        this.team_member1 = team_member1;
        this.team_member2 = team_member2;
        this.team_member3 = team_member3;
        this.team_member4 = team_member4;
    }

    public String getCollege_name() {
        return college_name;
    }

    public String getTeam_leader() {
        return team_leader;
    }

    public String getTeam_member1() {
        return team_member1;
    }

    public String getTeam_member2() {
        return team_member2;
    }

    public String getTeam_member3() {
        return team_member3;
    }

    public String getTeam_member4() {
        return team_member4;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(college_name);
        parcel.writeString(team_leader);
        parcel.writeString(team_member1);
        parcel.writeString(team_member2);
        parcel.writeString(team_member3);
        parcel.writeString(team_member4);

    }


    public static final Parcelable.Creator<UserDetail> CREATOR
            = new Parcelable.Creator<UserDetail>() {
        public UserDetail createFromParcel(Parcel in) {
            return new UserDetail(in);
        }

        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };

    private UserDetail(Parcel in) {
        college_name = in.readString();
        team_leader = in.readString();
        team_member1 =  in.readString();
        team_member2 = in.readString();
        team_member3 = in.readString();
        team_member4 = in.readString();
    }
}
