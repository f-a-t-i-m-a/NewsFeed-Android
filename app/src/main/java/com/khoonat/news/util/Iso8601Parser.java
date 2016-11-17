package com.khoonat.news.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class Iso8601Parser {
    /** Transform Calendar to ISO 8601 string. */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /** Get current date and time formatted as ISO 8601 string. */
    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /** Transform ISO 8601 string to Calendar. */
    public static Calendar toCalendar(final String iso8601string)
    {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        s = s.replaceAll("\\.\\d+", "");

        Date date = null;

        try {
            if (s.contains("+")) {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
            } else {
                date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(s);
            }

        } catch (ParseException e) {
        }

        if (date == null)
            return null;

        calendar.setTime(date);
        return calendar;
    }
}