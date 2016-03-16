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
    }
//            new AsyncHttpTask().execute(url);

    public int getHttpGetConnection(String url) {
        InputStream inputStream = null;

        HttpURLConnection httpURLConnection = null;
        int statusCode = 0;
        try {
                /* forming th java.net.URL object */
            URL urlConnection = new URL(url);

            httpURLConnection = (HttpURLConnection) urlConnection.openConnection();

                 /* optional request header */
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
            httpURLConnection.setRequestProperty("Accept", "application/json");

            /* setting http connection time out */
            httpURLConnection.setConnectTimeout(2000);
                /* for Get request */
            httpURLConnection.setRequestMethod("GET");

            statusCode = httpURLConnection.getResponseCode();

                /* 200 represents HTTP OK */
            if (statusCode == HttpURLConnection.HTTP_OK) {

                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                String response = convertInputStreamToString(inputStream);

                mHttpUrlConnectionResponce.httpResponceResult(response,statusCode);
            }

        } catch (Exception e) {
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




