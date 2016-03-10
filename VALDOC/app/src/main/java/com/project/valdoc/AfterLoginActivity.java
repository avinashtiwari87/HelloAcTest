package com.project.valdoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class AfterLoginActivity extends AppCompatActivity {

    private String userName = "";
    private String loginUserType = "";
    private int appUserId;
    SharedPreferences sharedpreferences;
    //?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
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
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("USERTYPE", loginUserType);
                    intent.putExtra("APPUSERID", appUserId);
                    startActivity(intent);
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

        }
    }
}
