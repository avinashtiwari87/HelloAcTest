package com.project.valdoc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.project.valdoc.controler.ValdocControler;
import com.project.valdoc.task.HttpConnection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;

public class AfterLoginActivity extends AppCompatActivity implements HttpConnection.HttpUrlConnectionResponce, ValdocControler.ControlerResponse {

    private String userName = "";
    private String loginUserType = "";
    private int appUserId;
    SharedPreferences sharedpreferences;
    ValdocControler mValdocControler;

    //?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        mValdocControler = new ValdocControler();
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            userName = null;
            loginUserType = null;
        } else {
            userName = extras.getString("USERNAME");
            loginUserType = extras.getString("USERTYPE");
            appUserId = extras.getInt("APPUSERID");
        }

        if (getIntent().hasExtra("serviceReport")) {
            getSupportActionBar().hide();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            setContentView(R.layout.service_report_entry);

        } else {
            setContentView(R.layout.activity_after_login);


            findViewById(R.id.service_report_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AfterLoginActivity.this, AfterLoginActivity.class);
                    intent.putExtra("serviceReport", true);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
            });

            findViewById(R.id.test_certificate_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AfterLoginActivity.this, TestCreateActivity.class);
                    intent.putExtra("USERNAME", sharedpreferences.getString("USERNAME", ""));
                    intent.putExtra("USERTYPE", sharedpreferences.getString("USERTYPE", ""));
                    intent.putExtra("APPUSERID", sharedpreferences.getInt("APPUSERID", 0));
                    startActivity(intent);
                    finish();
                }
            });

            findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("login", false);
                    editor.remove("USERNAME"); // will delete key key_name3
                    editor.remove("USERTYPE");
                    editor.remove("APPUSERID");
                    editor.commit();
                    Intent intent = new Intent(AfterLoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });


            findViewById(R.id.sync_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (checkInternetConenction()) {
//                        mValdocControler.getHttpConectionforSync(AfterLoginActivity.this);
//                    } else {
                        aleartDialog("Please check your internate connection !");
//                    }
                }
            });


        }
    }


    public void aleartDialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
//                finish();
            }
        });

//        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void httpResponceResult(String resultData, int statusCode) {
        if (statusCode == HttpURLConnection.HTTP_OK) {
            Log.d("VALDOC", "response data=" + resultData);
//            generateNoteOnSD(AfterLoginActivity.this,resultData);
            mValdocControler.getAllDb(statusCode, resultData);
        }
    }

    @Override
    public void controlerResult(String message) {
        Log.d("VALDOC", "controler response data  message=" + message);
    }


    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            Toast.makeText(this, " Connected ", Toast.LENGTH_LONG).show();
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(this, " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

    public void generateNoteOnSD(Context context, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "json_data.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Log.d("VALDOC", "controler response data= file saved");
        } catch (IOException e) {
            Log.d("VALDOC", "controler response data= file exception=" + e.getMessage());
        }
    }

}
