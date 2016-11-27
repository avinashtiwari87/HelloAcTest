package com.project.valdoc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SplashActivity";
    private final int SPLASH_DISPLAY_LENGHT = 1500;
    EditText screen1_rowno_et, screen1_colno_et,screen2_rowno_et, screen2_colno_et;
    EditText screen3_rowno_et, screen3_colno_et,screen4_rowno_et, screen4_colno_et;
    EditText screen5_rowno_et, screen5_colno_et,screen6_rowno_et, screen6_colno_et;
    Button build_btn1,build_btn2,build_btn3,build_btn4,build_btn5,build_btn6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        initRes();


/*        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGHT);*/

    }

    @Override
    public void onClick(View view) {
        if (view == build_btn1) {
            String rowstring = screen1_rowno_et.getText().toString();
            String colstring = screen1_colno_et.getText().toString();
            startDynamicActivity(rowstring,colstring,"Test1");

        }
        if (view == build_btn2) {
            String rowstring = screen2_rowno_et.getText().toString();
            String colstring = screen2_colno_et.getText().toString();
            startDynamicActivity(rowstring,colstring,"Test2");

        }
        if (view == build_btn3) {
            String rowstring = screen3_rowno_et.getText().toString();
            String colstring = screen3_colno_et.getText().toString();
            startDynamicActivity(rowstring,colstring,"Test3");
            //showAlert("Coming Soon...");

        }
        if (view == build_btn4) {
            String rowstring = screen4_rowno_et.getText().toString();
            String colstring = screen4_colno_et.getText().toString();
            startDynamicActivity(rowstring,colstring,"Test4");
            //showAlert("Coming Soon...");

        }
        if (view == build_btn5) {
            String rowstring = screen5_rowno_et.getText().toString();
            String colstring = screen5_colno_et.getText().toString();
            startDynamicActivity(rowstring,colstring,"Test5");
            //showAlert("Coming Soon...");

        }
        if (view == build_btn6) {
            String rowstring = screen6_rowno_et.getText().toString();
            String colstring = screen6_colno_et.getText().toString();
            startDynamicActivity(rowstring,colstring,"Test6");
            //showAlert("Coming Soon...");

        }

    }


    private void showAlert(String message){
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setCancelable(false);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();


    }
    private void startDynamicActivity(String rowNo,String colNo, String TestType){
        if (!rowNo.equals("") && !colNo.equals("")) {
            int rows = Integer.parseInt(rowNo);
            int cols = Integer.parseInt(colNo);
            Intent intent = new Intent(SplashActivity.this, DynamicTableActivity.class);
            intent.putExtra("rows",rows);
            intent.putExtra("cols",cols);
            intent.putExtra("testType",TestType);
            startActivity(intent);
            //table_layout.removeAllViews();
            // BuildTable(rows, cols);
        }
        else {
            Toast.makeText(SplashActivity.this,
                    "Please Enter the row and col Numbers",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initRes(){
        screen1_rowno_et = (EditText) findViewById(R.id.screen1_rowno_etv);
        screen1_colno_et = (EditText) findViewById(R.id.screen1_colno_etv);
        build_btn1 = (Button) findViewById(R.id.build_screen1_btn);

        screen2_rowno_et = (EditText) findViewById(R.id.screen2_rowno_etv);
        screen2_colno_et = (EditText) findViewById(R.id.screen2_colno_etv);
        build_btn2 = (Button) findViewById(R.id.build_screen2_btn);


        screen3_rowno_et = (EditText) findViewById(R.id.screen3_rowno_etv);
        screen3_colno_et = (EditText) findViewById(R.id.screen3_colno_etv);
        build_btn3 = (Button) findViewById(R.id.build_screen3_btn);

        screen4_rowno_et = (EditText) findViewById(R.id.screen4_rowno_etv);
        screen4_colno_et = (EditText) findViewById(R.id.screen4_colno_etv);
        build_btn4 = (Button) findViewById(R.id.build_screen4_btn);

        screen5_rowno_et = (EditText) findViewById(R.id.screen5_rowno_etv);
        screen5_colno_et = (EditText) findViewById(R.id.screen5_colno_etv);
        build_btn5 = (Button) findViewById(R.id.build_screen5_btn);

        screen6_rowno_et = (EditText) findViewById(R.id.screen6_rowno_etv);
        screen6_colno_et = (EditText) findViewById(R.id.screen6_colno_etv);
        build_btn6 = (Button) findViewById(R.id.build_screen6_btn);

        build_btn1.setOnClickListener(this);
        build_btn2.setOnClickListener(this);
        build_btn3.setOnClickListener(this);
        build_btn4.setOnClickListener(this);
        build_btn5.setOnClickListener(this);
        build_btn6.setOnClickListener(this);


    }

}
