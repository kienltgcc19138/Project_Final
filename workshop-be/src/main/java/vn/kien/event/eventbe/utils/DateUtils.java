package vn.kien.event.eventbe.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public final static String MINUTE = "minute";
    public final static String HOURLY = "hourly";
    public final static String DAILY = "daily";
    public final static String MONTHLY = "monthly";
    public final static String YEARLY = "yearly";
    public static Date customFromDate(Date fromDate) {
        return customFromDate(fromDate, true);
    }

    public static Date customToDate(Date toDate) {
        return customToDate(toDate, true);
    }
    public static Date customToDate(Date toDate, boolean changeHour) {
        if (null != toDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);
            if (changeHour) {
                cal.set(Calendar.HOUR_OF_DAY, 23);
            }
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            toDate = cal.getTime();
        }
        return toDate;
    }

    public static Date customFromDate(Date fromDate, boolean changeHour) {
        if (null != fromDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            if (changeHour) {
                cal.set(Calendar.HOUR_OF_DAY, 0);
            }
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            fromDate = cal.getTime();
        }
        return fromDate;
    }

    public static Date getDateBeforeNumberTimes(Date date, int number, String type) {
        Calendar cal = Calendar.getInstance();
        if (null != date) {
            cal.setTimeInMillis(date.getTime());
        }
        if (type.equalsIgnoreCase(HOURLY)) {
            cal.add(Calendar.HOUR, -number);
        } else if (type.equalsIgnoreCase(DAILY)) {
            cal.add(Calendar.DAY_OF_YEAR, -number);
        } else if (type.equalsIgnoreCase(MONTHLY)) {
            cal.add(Calendar.MONTH, -number);
        } else if (type.equalsIgnoreCase(YEARLY)) {
            cal.add(Calendar.YEAR, -number);
        }
        return cal.getTime();
    }

    public static Date getDateAfterNumberTimes(Date date, int number, String type) {
        Calendar cal = Calendar.getInstance();
        if (null != date) {
            cal.setTimeInMillis(date.getTime());
        }
        if (type.equalsIgnoreCase(MINUTE)) {
            cal.add(Calendar.MINUTE, +number);
        } else if (type.equalsIgnoreCase(HOURLY)) {
            cal.add(Calendar.HOUR, +number);
        } else if (type.equalsIgnoreCase(DAILY)) {
            cal.add(Calendar.DAY_OF_YEAR, +number);
        } else if (type.equalsIgnoreCase(MONTHLY)) {
            cal.add(Calendar.MONTH, +number);
        } else if (type.equalsIgnoreCase(YEARLY)) {
            cal.add(Calendar.YEAR, +number);
        }
        return cal.getTime();
    }
}
