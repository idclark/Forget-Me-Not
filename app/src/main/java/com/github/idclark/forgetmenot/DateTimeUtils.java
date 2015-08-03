package com.github.idclark.forgetmenot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by idclark on 8/2/15.
 */
public class DateTimeUtils {

    public static String formatDueDate(String queryResponse) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z", Locale.US);
        Date result = new Date();
        try {
            result = sdf.parse(queryResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
