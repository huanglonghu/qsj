package com.example.qsl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static String getStringToDate(String dateString) {
        //"yyyy-MM-dd'T'HH:mm:ss"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateStr = formatTime(date.getTime());
        return dateStr;
    }

    public static String getStringToDate2(String dateString) {
        //"yyyy-MM-dd'T'HH:mm:ss"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateStr = formatTime2(date.getTime());
        return dateStr;
    }

    public static String getStringToDate3(String dateString) {
        //"yyyy-MM-dd'T'HH:mm:ss"
        LogUtil.log("===========dataBean.getReservationTime()=============="+dateString);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateStr = formatTime3(date.getTime());
        return dateStr;
    }

    public static String formatTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formatTime = sdf.format(new Date(time));
        return formatTime;
    }

    public static String formatTime2(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String formatTime = sdf.format(new Date(time));
        return formatTime;
    }

    public static String formatTime3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatTime = sdf.format(new Date(time));
        return formatTime;
    }


    public static String[] getDate1(int amount) {
        String[] dates = new String[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        dates[0] = simpleDateFormat.format(date);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        dates[1] = simpleDateFormat.format(calendar.getTime());
        return dates;
    }


    public static String[] getDate2(int amount) {
        String[] dates = new String[2];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date date = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        dates[0] = getMonthFirst(year, month);
        dates[1] = getMonthEnd(year, month);
        return dates;
    }


    public static String getMonthFirst(int year, int monthOfYear) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        String str = simpleDateFormat.format(firstDate);
        return str;
    }


    public static String getMonthEnd(int year, int monthOfYear) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        String str = simpleDateFormat.format(lastDate);
        return str;
    }


}
