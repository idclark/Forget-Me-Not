package com.github.idclark.forgetmenot;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by idclark on 8/2/15.
 */
public class DateTimeUtils {
    private Context mContext;

    public DateTimeUtils(Context context) {
        this.mContext = context;
    }

    public String formatDueDate(String queryResponse) {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        Date date = new Date();
        try {
             date = iso8601Format.parse(queryResponse);
        } catch (ParseException e) {
            Log.e("TAG", "Parsing ISO8601 datetime failed", e);
        }

        long when = date.getTime();
        int flags = 0;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
        flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
        flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

       return android.text.format.DateUtils.formatDateTime(mContext,
                when + TimeZone.getDefault().getOffset(when), flags);

    }
}
