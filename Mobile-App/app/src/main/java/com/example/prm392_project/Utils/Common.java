package com.example.prm392_project.Utils;

import android.text.format.DateUtils;

import com.example.prm392_project.Application.MyApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Common {
    public static String formatDate(Date date) throws ParseException {
        SimpleDateFormat dt1 = new SimpleDateFormat(MyApp.DATE_FORMAT);
        return dt1.format(date);
    }
    public static String formatDateWithCurrentTime(Date date) {
        return (String)DateUtils.getRelativeTimeSpanString(date.getTime()) ;
    }
}
