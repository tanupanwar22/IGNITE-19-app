package com.example.ignite19;

import android.util.Log;

import androidx.cardview.widget.CardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
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

    public static String convertDateTimeToDate(String dateTimeString){
        String result = null;
        try {
            Date date = sdf.parse(dateTimeString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String mDate,mMonth;
            int mDateInt = calendar.get(Calendar.DAY_OF_MONTH);
            if(mDateInt<9){
              mDate = String.valueOf("0" + mDateInt);
            }
            else{
               mDate = String.valueOf(mDateInt);
            }

            int mMonthInt = calendar.get(Calendar.MONTH) + 1;
            if(mMonthInt < 10){
         mMonth = String.valueOf("0" + mMonthInt);
            }
            else {
            mMonth = String.valueOf(mMonthInt);
            }


            String mYear = String.valueOf(calendar.get(Calendar.YEAR));


            result = mYear + "-" + mMonth  + "-" + mDate;
            Log.d("romeo", "convertDateTimeToDate: " + result);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return result;
    }


    public static String changeDateFormat(String dateTimeString){
        String formattedDateTime = null;
        try {
            Date date = sdf.parse(dateTimeString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(date));
           // formattedDateTime = new SimpleDateFormat("dd/MM/yyyy KK:mm a").format(date);
            formattedDateTime = new SimpleDateFormat("E, dd MMM, KK:mm a").format(date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return formattedDateTime;
    }
}
