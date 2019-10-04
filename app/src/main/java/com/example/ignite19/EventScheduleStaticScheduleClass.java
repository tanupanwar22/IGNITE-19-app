package com.example.ignite19;

import com.example.ignite19.ui.schedule.eventSchedule;

import java.util.ArrayList;

public class EventScheduleStaticScheduleClass {

    public static ArrayList<eventSchedule> getDay0EventSchedule(){
            ArrayList<eventSchedule> day0CompleteSchedule = new ArrayList<>();
            day0CompleteSchedule.add(new eventSchedule("05:00 pm","Opening","Inauguration","LHC-C Seminar Hall","Friday","11"));
            day0CompleteSchedule.add(new eventSchedule("06:00 pm","Technical","Tech Talk","LHC-C Seminar Hall","Friday","11"));
            day0CompleteSchedule.add(new eventSchedule("07:00 pm","Technical","Krypthon Announcement","LHC-C Seminar Hall","Friday","11"));

        return day0CompleteSchedule;
    }

    public static ArrayList<eventSchedule> getDay1EventSchedule(){
        ArrayList<eventSchedule> day1CompleteSchedule = new ArrayList<>();
        day1CompleteSchedule.add(new eventSchedule("07:30 am","Food","Breakfast","Trishul Block Mess","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("08:30 am","Technical","ORM Master (Prelims)","LHC-C Seminar Hall","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("08:30 am","Technical","C for Codetta (Prelims)","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("09:40 am","Technical","Code Infinity (Prelims)","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("09:40 am","Technical","Agomotto's Amet (Prelims)","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("10:30 am","Non-Technical","Scattegories","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("10:45 am","Technical","Captain Algorika (Prelims)","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("12:00 pm","Meal","Lunch","Trishul Block Mess","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("01:00 pm","Technical" , "Captian Algorika (Mains)","SACA Lab","Saturday","12"));
      //  day1CompleteSchedule.add(new eventSchedule("01:00 pm","Non-Technical","Death's Head","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("01:00 pm","Technical","Agomotto's Amet (Mains)","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("02:00 pm","Non-Technical","Cerebro","LHC-C Seminar Hall","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("02:00 pm","Technical","Code Infinity (Mains)","SACA Lab","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("04:10 pm","Technical","ORM Master (Mains)","LHC-C Seminar Hall","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("05:10 pm","Snacks","Snacks","Trishul Block Mess","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("06:00 pm","Non-Technical","Nexus","Pavilion","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("08:30 pm","Meal","Dinner","Trishul Block Mess","Saturday","12"));
        day1CompleteSchedule.add(new eventSchedule("09:00 pm","Gaming","Gamer's Eve","CCC","Saturday","12"));


        return day1CompleteSchedule;
    }


    public static ArrayList<eventSchedule> getDay2EventSchedule(){
        ArrayList<eventSchedule> day2CompleteSchedule = new ArrayList<>();

        //add day2 events to day2 arraylist
        day2CompleteSchedule.add(new eventSchedule("07:30 am","Breakfast","Breakfast","Trishul Block Mess","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("08:00 am","Technical","Krypthon (Submission)","SACA Lab","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("08:00 am","Non-Technical","The Search of Gauntlet","NITK Campus","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("10:30 am","Technical","Krypthon (Final)","SACA Lab","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("12:00 pm","Meal","Lunch","Trishul Block Mess","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("12:45 pm","Technical","C for Codetta (Mains)","SACA Lab","Sunday","13"));
       // day2CompleteSchedule.add(new eventSchedule("01:50 pm","Technical","Captain Algorika (Mains)","SACA Lab","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("02:00 pm","Non-Technical","Finale","LHC-C Seminar Hall","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("04:00 pm","Cultural","Cultural","LHC-C Seminar Hall","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("05:30 pm","Snacks","Snacks","LHC-C Seminar Hall","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("04:30 pm","Non-Technical","Cultural Fest","LHC-C Seminar Hall","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("06:00 pm","Non-Technical","End Game","LHC-C Seminar Hall","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("07:00 pm","Closing","Award Ceremony","LHC-C Seminar Hall","Sunday","13"));
        day2CompleteSchedule.add(new eventSchedule("08:30 pm","Meal","Dinner","Trishul Block Mess","Sunday","13"));
        return day2CompleteSchedule;
    }
}
