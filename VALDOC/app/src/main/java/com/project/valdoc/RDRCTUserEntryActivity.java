package com.project.valdoc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

public class RDRCTUserEntryActivity extends AppCompatActivity {
    TextView headerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdrctuser_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        headerText = (TextView)findViewById(R.id.common_header_tv);
        headerText.setText(" * Recovery Test for Non-Unidirectional Airflow Installations * ");

    }
}
