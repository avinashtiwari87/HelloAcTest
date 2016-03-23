package com.project.valdoc.task;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.project.valdoc.AfterLoginActivity;

import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Avinash on 3/15/2016.
 */
public class HttpConnectionTask extends AsyncTask<String, Void, Integer> {
    Context mContext = null;
    HttpConnection mHttpConnection = null;
    HttpPostConnection mHttpPostConnection = null;
    private ProgressDialog mDialog;
    private String mMethod;
    private JSONObject mJsonDATA;

    public HttpConnectionTask(Context context,String method,JSONObject JsonDATA) {
        mMethod=method;
        mContext = context;
        mJsonDATA=JsonDATA;
        mHttpConnection = new HttpConnection((HttpConnection.HttpUrlConnectionResponce) mContext);
        mHttpPostConnection=new HttpPostConnection((HttpPostConnection.HttpUrlConnectionPostResponce) mContext);
        mDialog = new ProgressDialog( mContext);
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
        int statusCode=0;
        if(mMethod.equals("POST")){
             statusCode= mHttpPostConnection.httpPostConnection(params[0],mJsonDATA);
        }else {
             statusCode = mHttpConnection.getHttpGetConnection(params[0]);
        }
        return statusCode;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        Log.d("VALDOC", "controler httpPostResponceResult response data11  statusCode=" + integer);
        mDialog.dismiss();
    }
}
