package com.project.valdoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.valdoc.utility.Utilityies;

public class ServerSettingActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    private EditText serverUrl;
    private EditText plantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_setting);

        serverUrl=(EditText)findViewById(R.id.service_url_edt_text);
        plantName=(EditText)findViewById(R.id.service_plant_edt_text);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);

        Toast.makeText(ServerSettingActivity.this, "Hit Save button, to save Server Setting." +
                "This screen will not appear next time", Toast.LENGTH_LONG).show();


        findViewById(R.id.service_save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(serverUrl.getText().length()!=0&&plantName.getText().length()!=0) {
                    editor.putBoolean(Utilityies.SERVER_SETTING, true);
                    editor.putString("URL",serverUrl.getText().toString());
                    editor.putString("PLANTNAME",plantName.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(ServerSettingActivity.this, SplashScreen.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ServerSettingActivity.this, "Please enter the service url and plant name.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
