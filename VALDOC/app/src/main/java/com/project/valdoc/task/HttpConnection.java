package com.project.valdoc.task;

import android.net.ConnectivityManager;
import android.webkit.HttpAuthHandler;
import android.widget.Toast;

/**
 * Created by Avinash on 3/14/2016.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class HttpConnection {

    private static final String TAG = "Http Connection";


    private String[] blogTitles;
    HttpUrlConnectionResponce mHttpUrlConnectionResponce = null;

    public HttpConnection(HttpUrlConnectionResponce connectionResponce) {
        mHttpUrlConnectionResponce = connectionResponce;
        Log.d("VALDOC", "avinash HttpConnection");
    }
//            new AsyncHttpTask().execute(url);

    public int getHttpGetConnection(String url) {
        InputStream inputStream = null;
        Log.d("VALDOC", "avinash getHttpGetConnection 1");
        HttpURLConnection httpURLConnection = null;
        int statusCode = 0;
        try {
                /* forming th java.net.URL object */
            URL urlConnection = new URL(url);
            Log.d("VALDOC", "avinash getHttpGetConnection 2");
            httpURLConnection = (HttpURLConnection) urlConnection.openConnection();

                 /* optional request header */
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            Log.d("VALDOC", "avinash getHttpGetConnection 3");
                /* optional request header */
            httpURLConnection.setRequestProperty("Accept", "application/json");

            /* setting http connection time out */
            httpURLConnection.setConnectTimeout(2000);
            Log.d("VALDOC", "avinash getHttpGetConnection 4");
                /* for Get request */
            httpURLConnection.setRequestMethod("GET");
            Log.d("VALDOC", "avinash getHttpGetConnection 5");
            statusCode = httpURLConnection.getResponseCode();
            Log.d("VALDOC", "avinash getHttpGetConnection statusCode="+statusCode);
                /* 200 represents HTTP OK */
            if (statusCode == HttpURLConnection.HTTP_OK) {
                Log.d("VALDOC", "avinash getHttpGetConnection 6");
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                Log.d("VALDOC", "avinash getHttpGetConnection 7");
                String response = convertInputStreamToString(inputStream);
                Log.d("VALDOC", "avinash getHttpGetConnection 8");
                mHttpUrlConnectionResponce.httpResponceResult(response, statusCode);
                Log.d("VALDOC", "avinash getHttpGetConnection 9");
            }

        } catch (Exception e) {
            Log.d("VALDOC", "avinash getHttpGetConnection catch 5 e="+e.getMessage());
            Log.d(TAG, e.getLocalizedMessage());
        }
        return statusCode; //"Failed to fetch data!";
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

//    private void parseResult(String result) {
//
//        try {
//            JSONObject response = new JSONObject(result);
//
//            JSONArray posts = response.optJSONArray("posts");
//
//            blogTitles = new String[posts.length()];
//
//            for (int i = 0; i < posts.length(); i++) {
//                JSONObject post = posts.optJSONObject(i);
//                String title = post.optString("title");
//
//                blogTitles[i] = title;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public interface HttpUrlConnectionResponce {
        public void httpResponceResult(String resultData,int statusCode);
    }
}




