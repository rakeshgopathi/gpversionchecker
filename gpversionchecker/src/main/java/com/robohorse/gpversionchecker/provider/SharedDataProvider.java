package com.robohorse.gpversionchecker.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by robohorse on 06.03.16.
 */
public class SharedDataProvider {
    private static final String GPVCH_TIME = "gpvch_time";

    public void saveCurrentDate(Context context) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences
                .edit()
                .putLong(GPVCH_TIME, formatTodayDate().getTime())
                .apply();
    }

    public boolean needToCheckVersion(Context context) {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final long storedTime = preferences.getLong(GPVCH_TIME, 0L);
        if (storedTime == 0L) {
            return true;
        }

        final Date storedDate = new Date(storedTime);
        final Date today = formatTodayDate();

        return today.after(storedDate);
    }

    private Date formatTodayDate() {
        final Date today = new Date(System.currentTimeMillis());
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT);
            return formatter.parse(formatter.format(today));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }
}
