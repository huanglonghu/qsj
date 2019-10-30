package com.example.qsl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String getStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTime(date.getTime());
    }

    public static String getStringToDate2(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTime2(date.getTime());
    }

    public static String getStringToDate3(String dateString) {
        LogUtil.log("===========dataBean.getReservationTime()==============" + dateString);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTime3(date.getTime());
    }

    public static String formatTime(long time) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time));
    }

    public static String formatTime2(long time) {
        return new SimpleDateFormat("yyyy年MM月dd日").format(new Date(time));
    }

    public static String formatTime3(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(time));
    }

    public static String[] getDate1(int amount) {
        String[] dates = new String[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        dates[0] = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(2) + 1;
        calendar.setTime(date);
        calendar.add(2, amount);
        dates[1] = simpleDateFormat.format(calendar.getTime());
        return dates;
    }

    public static String[] getDate2(int amount) {
        String[] dates = new String[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, amount);
        int month = calendar.get(2) + 1;
        int year = calendar.get(1);
        dates[0] = getMonthFirst(year, month);
        dates[1] = getMonthEnd(year, month);
        return dates;
    }

    public static String getMonthFirst(int year, int monthOfYear) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, monthOfYear);
        cal.set(5, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.add(5, -1);
        Date lastDate = cal.getTime();
        cal.set(5, 1);
        return simpleDateFormat.format(cal.getTime());
    }

    public static String getMonthEnd(int year, int monthOfYear) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, monthOfYear);
        cal.set(5, 1);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.add(5, -1);
        return simpleDateFormat.format(cal.getTime());
    }
}
