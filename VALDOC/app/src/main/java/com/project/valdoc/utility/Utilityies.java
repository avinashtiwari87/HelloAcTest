package com.project.valdoc.utility;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.project.valdoc.R;

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

    public static void setCustomActionBar(Context ctx, ActionBar mActionBar, String userName){
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(ctx);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        TextView mUserName = (TextView) mCustomView.findViewById(R.id.user_name_tv);
        mUserName.setText(userName);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);

    }
}
