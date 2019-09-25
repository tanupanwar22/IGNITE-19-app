package com.example.ignite19;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateTimeConverter {

    private  static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public   static long convertDateToEpoch(String dateString){
        long epochTime = 0;
        try{
            Date date = sdf.parse(dateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(date));
            epochTime = calendar.getTimeInMillis();
        }catch(ParseException e){
            e.printStackTrace();
        }
        return epochTime;
    }

    public static int convertDateTimeToDayOfMonth(String dateTimeString){
        int dayOfMonth = 0;
        try {
            Date date = sdf.parse(dateTimeString);
            Calendar calendar = Calendar.getInstance();
           calendar.setTime(Objects.requireNonNull(date));
           dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return dayOfMonth;
    }
}
