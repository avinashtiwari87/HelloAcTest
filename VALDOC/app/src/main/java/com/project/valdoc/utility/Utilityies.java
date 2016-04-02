package com.project.valdoc.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Avinash on 4/3/2016.
 */
public class Utilityies {
    private Utilityies() {

    }

    public static String parseDateToddMMyyyy(String time) {
        String str = "";
        Date date = null;
        if (null != time && time.length() > 0) {
            String inputPattern = "yyyy-MM-dd";
            String outputPattern = "dd-MMM-yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            try {
                date = inputFormat.parse(time);
                str = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return str;
    }
}
