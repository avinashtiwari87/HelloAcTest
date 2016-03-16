package com.project.valdoc.task;

import android.content.Context;
import android.os.AsyncTask;

import java.net.HttpURLConnection;

/**
 * Created by Avinash on 3/15/2016.
 */
public class HttpConnectionTask extends AsyncTask<String, Void, Integer> {
    Context mContext = null;
    HttpConnection mHttpConnection = null;

    public HttpConnectionTask(Context context) {
        mContext = context;
        mHttpConnection=new HttpConnection((HttpConnection.HttpUrlConnectionResponce)mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
       int statusCode= mHttpConnection.getHttpGetConnection(params[0]);
        if(statusCode== HttpURLConnection.HTTP_OK){

        }
        return statusCode;
    }

    @Override
    protected void onPostExecute(Integer integer) {
    }
}
