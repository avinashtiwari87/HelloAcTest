package com.project.valdoc.task;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.project.valdoc.AfterLoginActivity;

import java.net.HttpURLConnection;

/**
 * Created by Avinash on 3/15/2016.
 */
public class HttpConnectionTask extends AsyncTask<String, Void, Integer> {
    Context mContext = null;
    HttpConnection mHttpConnection = null;
    private ProgressDialog mDialog;

    public HttpConnectionTask(Context context) {
        mContext = context;
        mHttpConnection = new HttpConnection((HttpConnection.HttpUrlConnectionResponce) mContext);
        mDialog = new ProgressDialog((AfterLoginActivity) mContext);
        Log.d("VALDOC", "avinash HttpConnectionTask");
    }

    @Override
    protected void onPreExecute() {
        mDialog.setMessage("Data Synking...");
        mDialog.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        Log.d("VALDOC", "avinash HttpConnectionTask url="+params[0]);
        int statusCode = mHttpConnection.getHttpGetConnection(params[0]);
        if (statusCode == HttpURLConnection.HTTP_OK) {

        }
        return statusCode;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        mDialog.dismiss();
    }
}
