package com.project.valdoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ServerSettingActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_setting);

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);

        Toast.makeText(ServerSettingActivity.this, "Hit Save button, to save Server Setting." +
                "This screen will not appear next time", Toast.LENGTH_LONG).show();



        findViewById(R.id.service_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("ServerSetting", true);
                editor.commit();
                Intent intent = new Intent(ServerSettingActivity.this, SplashScreen.class);
                startActivity(intent);
                finish();
            }
        });


    }

}
