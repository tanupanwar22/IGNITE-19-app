package com.example.ignite19;

import com.example.ignite19.ui.schedule.eventSchedule;

import java.util.ArrayList;

public class EventScheduleStaticScheduleClass {

    public static ArrayList<eventSchedule> getDay0EventSchedule(){
            ArrayList<eventSchedule> day0CompleteSchedule = new ArrayList<>();
            day0CompleteSchedule.add(new eventSchedule("04:00 pm","Opening","Inauguration","LHC-C Seminar Hall","Friday","11"));
            day0CompleteSchedule.add(new eventSchedule("07:00 pm","Opening","Post Inauguration Ceremony","LHC-C Seminar Hall","Friday","11"));
            return day0CompleteSchedule;
    }

    public static ArrayList<eventSchedule> getDay1EventSchedule(){
        ArrayList<eventSchedule> day1CompleteSchedule = new ArrayList<>();

        day1CompleteSchedule.add(new eventSchedule("08:30 am","Opening","Workshop","LHC-C Seminar Hall","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("11:00 am","Technical","Database Prelims","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("11:00 am","Technical","Coding Prelims","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("12:00 pm","Meal","Lunch","Trishul Block Mess","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("01:00 pm","Technical","Relay Coding","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("01:00 pm","Technical","Debugging","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("02:00 pm","Non-Technical","Scattegories","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("02:00 pm","Technical","Agomotto's Amet","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("03:00 pm","Non-Technical","Death's Head","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("03:00 pm","Technical","Code The Problem","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("04:00 pm","Non-Technical","Brain Storm","LHC-C Seminar Hall","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("04:00 pm","Non-Technical","Bidding","LHC-C Seminar Hall","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("05:00 pm","Snacks","Snacks","Trishul Block Mess","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("06:30 pm","Non-Technical","Nexus","Pavilion","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("09:00 pm","Meal","Dinner","Trishul Block Mess","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("10:00 pm","Gaming","Gamer's Eve","CCC","Saturday","12"));


        return day1CompleteSchedule;
    }


    public static ArrayList<eventSchedule> getDay2EventSchedule(){
        ArrayList<eventSchedule> day2CompleteSchedule = new ArrayList<>();

        //add day2 events to day2 arraylist
        day2CompleteSchedule.add(new eventSchedule("08:00 am","Non-Technical","Treasure Hunt","NITK Campus","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("12:00 am","Meal","Lunch","Trishul Block Mess","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("01:00 pm","Technical","Database Finals","SACA Lab","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("01:00 pm","Technical","Coding Finals","SACA Lab","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("01:00 pm","Technical","Code-athon Submission","SACA Lab","Sunday","13"));

        day2CompleteSchedule.add(new eventSchedule("02:00 pm","Technical","Algo Competition","SACA Lab","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("03:00 pm"," Non-Technical","Non Tech Events","LHC-C Seminar Hall","Sunday","13"));

        day2CompleteSchedule.add(new eventSchedule("04:30 pm","Non-Technical","Cultural Fest","LHC-C Seminar Hall","Sunday","13"));

        day2CompleteSchedule.add(new eventSchedule("06:00 pm","Non-Technical","Stress Interview","LHC-C Seminar Hall","Sunday","13"));

        day2CompleteSchedule.add(new eventSchedule("07:00 pm","Closing","Award Ceremony","LHC-C Seminar Hall","Sunday","13"));

        day2CompleteSchedule.add(new eventSchedule("09:00 pm","Meal","Dinner","Trishul Block Mess","Sunday","13"));
        return day2CompleteSchedule;
    }
}
