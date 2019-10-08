package com.NITK.ignite.Admin.AdminUpdateLeaderBoard;

public class LeaderBoardDataPOJO  {
    private int position;
    private String college_name;
    LeaderBoardDataPOJO(){

    }

    public LeaderBoardDataPOJO(int position, String college_name) {
        this.position = position;
        this.college_name = college_name;
    }

    public int getPosition() {
        return position;
    }

    public String  getCollege_name() {
        return college_name;
    }


}
