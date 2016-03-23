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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

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
            httpURLConnection.setConnectTimeout(30000);
            Log.d("VALDOC", "avinash getHttpGetConnection 4");
                /* for Get request */
            httpURLConnection.setRequestMethod("GET");
            Log.d("VALDOC", "avinash getting status....");
            statusCode = httpURLConnection.getResponseCode();
            Log.d("VALDOC", "avinash getHttpGetConnection statusCode=" + statusCode);
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
            Log.d("VALDOC", "avinash getHttpGetConnection catch 5 e=" + e.getMessage());
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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


    private int httpPostConnection(String url, JSONObject JsonDATA) {
        BufferedReader reader = null;
        String JsonResponse = null;
        HttpsURLConnection conn = null;
        int statusCode = 0;
        try {
            URL urlConnection = new URL(url);
            conn = (HttpsURLConnection) urlConnection.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(JsonDATA.toString());
            // json data
            writer.close();
            InputStream inputStream = conn.getInputStream();
            //input stream
            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                // Nothing to do.
//                return null;
//            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
//            if (buffer.length() == 0) {
//                // Stream was empty. No point in parsing.
//                return null;
//            }
            JsonResponse = buffer.toString();
//response data
//            Log.i(TAG, JsonResponse);
//            try {
////send to post execute
//                return JsonResponse;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;

            statusCode = conn.getResponseCode();
            mHttpUrlConnectionResponce.httpResponceResult(JsonResponse, statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return statusCode;
    }

    public interface HttpUrlConnectionResponce {
        public void httpResponceResult(String resultData, int statusCode);
    }
}




