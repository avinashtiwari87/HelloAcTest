package com.project.valdoc.task;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Avinash on 6/1/2016.
 */
public class LocalJsonReader {
    private Context mContext;
    public LocalJsonReader(Context context){
        mContext=context;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("valdoc_db.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
            json = convertInputStreamToString(is);
            Log.d("LocalJsonReader","LocalJsonReader="+json);
//            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d("LocalJsonReader","Exception="+ex.getMessage());
            ex.printStackTrace();
            return null;
        }
        return json;
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
}
