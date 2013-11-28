package com.westudio.wecampus.util;

import android.content.Context;
import android.text.format.DateUtils;

import com.westudio.wecampus.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nankonami on 13-11-28.
 */
public class DateUtil {

    private static SimpleDateFormat serverSource = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

    /**
     * Parse the response time string
     * @param dateStr
     * @return
     */
    public static Date parseDateStr(String dateStr) {
        Date date = new Date();
        try {
            if(dateStr.equals("null")) {
                return null;
            }
            date = serverSource.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Generate the Calendar instance for date
     * @param dateFromServer
     * @return
     */
    public static Calendar parseDateAndTime(Date dateFromServer) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFromServer);
        return calendar;
    }

    /**
     * Generate the date time of activity
     * @param context
     * @param begin
     * @param end
     * @return
     */
    public static String getActivityTime(Context context, Date begin, Date end) {

        Calendar calBegin = parseDateAndTime(begin);
        Calendar calEnd = parseDateAndTime(end);

        StringBuilder sb = new StringBuilder();
        sb.append(calBegin.get(Calendar.MONTH)).append("-").append(calBegin.get(Calendar.DAY_OF_MONTH))
                .append(" ").append(getWeekDay(context, calBegin.get(Calendar.DAY_OF_WEEK)));

        if(isToday(begin)) {
            sb.append("(").append(context.getString(R.string.today)).append(")");
        }

        if(isTomorrow(begin)) {
            sb.append("(").append(context.getString(R.string.tomorrow)).append(")");
        }

        if(isDayAfterTomorrow(begin)) {
            sb.append("(").append(context.getString(R.string.yesterday)).append(")");
        }

        String timeBegin = DateUtils.formatDateTime(context, calBegin.getTimeInMillis(),
                DateUtils.FORMAT_24HOUR|DateUtils.FORMAT_SHOW_TIME);
        String timeEnd = DateUtils.formatDateTime(context, calEnd.getTimeInMillis(),
                DateUtils.FORMAT_24HOUR|DateUtils.FORMAT_SHOW_TIME);
        sb.append(" ").append(timeBegin).append("~").append(timeEnd);

        return sb.toString();
    }

    /**
     * Generate the activity time
     * @param context
     * @param begin
     * @param end
     * @return
     */
    public static String getActivityTime(Context context, String begin, String end) {
        Date beginDate = parseDateStr(begin);
        Date endDate = parseDateStr(end);
        Calendar calBegin = parseDateAndTime(beginDate);
        Calendar calEnd = parseDateAndTime(endDate);

        StringBuilder sb = new StringBuilder();
        sb.append(calBegin.get(Calendar.MONTH)).append("-").append(calBegin.get(Calendar.DAY_OF_MONTH))
                .append(" ").append(getWeekDay(context, calBegin.get(Calendar.DAY_OF_WEEK)));

        if(isToday(beginDate)) {
            sb.append("(").append(context.getString(R.string.today)).append(")");
        }

        if(isTomorrow(beginDate)) {
            sb.append("(").append(context.getString(R.string.tomorrow)).append(")");
        }

        if(isDayAfterTomorrow(beginDate)) {
            sb.append("(").append(context.getString(R.string.yesterday)).append(")");
        }

        String timeBegin = DateUtils.formatDateTime(context, calBegin.getTimeInMillis(),
                DateUtils.FORMAT_24HOUR|DateUtils.FORMAT_SHOW_TIME);
        String timeEnd = DateUtils.formatDateTime(context, calEnd.getTimeInMillis(),
                DateUtils.FORMAT_24HOUR|DateUtils.FORMAT_SHOW_TIME);
        sb.append(" ").append(timeBegin).append("~").append(timeEnd);

        return sb.toString();
    }

    /**
     * Check the time is today
     * @param time
     * @return
     */
    public static boolean isToday(Date time)
    {
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        if((today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) && (today.get(Calendar.DAY_OF_YEAR)
                == cal.get(Calendar.DAY_OF_YEAR)))
        {
            return true;
        }

        return false;
    }

    /**
     * Check the time is tomorrow
     * @param time
     * @return
     */
    public static boolean isTomorrow(Date time)
    {
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        if((today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) && (today.get(Calendar.DAY_OF_YEAR)
                == cal.get(Calendar.DAY_OF_YEAR) - 1))
        {
            return true;
        }

        return false;
    }

    /**
     * Check the time is the day after tomorrow
     * @param time
     * @return
     */
    public static boolean isDayAfterTomorrow(Date time)
    {
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        if((today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) && (today.get(Calendar.DAY_OF_YEAR)
                == cal.get(Calendar.DAY_OF_YEAR) - 2))
        {
            return true;
        }

        return false;
    }

    /**
     * Get the Chinese name of week day
     * @param context
     * @param day
     * @return
     */
    public static String getWeekDay(Context context, int day) {
        switch (day) {
            case 1:
                return context.getString(R.string.sunday);
            case 2:
                return context.getString(R.string.monday);
            case 3:
                return context.getString(R.string.tuesday);
            case 4:
                return context.getString(R.string.wednesday);
            case 5:
                return context.getString(R.string.thursday);
            case 6:
                return context.getString(R.string.friday);
            case 7:
                return context.getString(R.string.saturday);
            default:
                return null;
        }
    }
}
