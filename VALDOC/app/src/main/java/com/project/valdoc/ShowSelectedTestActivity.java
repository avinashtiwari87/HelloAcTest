package com.project.valdoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.project.valdoc.utility.Utilityies;

public class ShowSelectedTestActivity extends AppCompatActivity {

    private String userName = "";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_selected_test);

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        userName = sharedpreferences.getString("USERNAME", "");


      findViewById(R.id.go_button).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(ShowSelectedTestActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent(ShowSelectedTestActivity.this, SyncSelectedDataActivity.class);
              intent.putExtra("rows",11);
              intent.putExtra("cols",11);
              startActivity(intent);
          }
      });




        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(ShowSelectedTestActivity.this, mActionBar, userName);


    }




}
