package ir.appson.sportfeed.util;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by fatemeh on 9/16/2015.
 */
public class JalaliCalendarUtil {
    public static String ToJalaliDateString(Calendar calendar) {
        if (calendar == null)
            return "-";

        JalaliCalendar jalaliCalendar = new JalaliCalendar(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));

        return jalaliCalendar.getIranianDate();
    }

    public static String ToJalaliDateTimeString(Calendar calendar) {
        if (calendar == null)
            return "-";

        //Get the date and time
        String persianNumberFormatResult = ToJalaliDateString(calendar) +
                String.format(" %02d:%02d", calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));

        //Make it persian numbers
        NumberFormat nf = NumberFormat.getInstance(new Locale("ar","EG"));
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<persianNumberFormatResult.length();i++)
        {
            if(Character.isDigit(persianNumberFormatResult.charAt(i)))
            {
                builder.append(nf.format(Character.getNumericValue(persianNumberFormatResult.charAt(i))));
            }
            else
            {
                builder.append(persianNumberFormatResult.charAt(i));
            }
        }
        return builder.toString();

    }
}
