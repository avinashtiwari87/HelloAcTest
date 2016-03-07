package com.project.valdoc;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RDFITUserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDFITUser";
    TextView headerText;
    TableLayout test4_table_layout,test4_table_layout2,test4_table_layout3,test4_table_layout4,
            test4_table_layout5,test4_table_layout6,test4_table_layout7;

    int rows, cols;
    String testType;
    ProgressDialog pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdfituser_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this,"Please Wait", "Loading...");

        if(getIntent().hasExtra("rows") && getIntent().hasExtra("cols")){
            rows = getIntent().getIntExtra("rows",0);
            cols = getIntent().getIntExtra("cols",0);
            testType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + testType);
        }

        initRes();
        if ("Test4".equalsIgnoreCase(testType)) {
            BuildTableTest4(rows, cols);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        headerText.setText("*  Installed HEPA Filter System Leakage Test *");
    }

    private void BuildTableTest4(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Filter No \n         "));
                }else{
                    row.addView(addTextView(" LDU/EN/AHU/20/SG-0" + i));
                }

            }
            test4_table_layout.addView(row);
        }

        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Filter Type  \n         "));
                }else{
                    row.addView(addTextView(" HEPA "));
                }

            }
            test4_table_layout2.addView(row);
        }

        //Third section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Filter Efficiency\n at Particle Size* "));
                }else{
                    row.addView(addTextView(" 99.97% | 0.3µm "));
                }

            }
            test4_table_layout3.addView(row);
        }

        //Fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Average Up Stream\n Concentration (µg/liter) "));
                }else{
                    row.addView(addTextView(" 42 "));
                }

            }
            test4_table_layout4.addView(row);
        }

        //Fifth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" SLP of DL for Tests\n after Installation** "));
                }else{
                    row.addView(addTextView(" 0.01% "));
                }

            }
            test4_table_layout5.addView(row);
        }

        //Sixth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Obtained Test Results\n (% Leakage) "));
                }else{
                    row.addView(addTextView(" 0.0015 "));
                }

            }
            test4_table_layout6.addView(row);
        }

        //Seventh section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Test Status\n    "));
                }else{
                    row.addView(addTextView(" Pass "));
                }

            }
            test4_table_layout7.addView(row);
        }

        //dismiss progressbar
        if(pr.isShowing())
            pr.dismiss();

    }


    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    private TextView addTextViewWithoutBorder(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        //tv.setBackgroundResource(R.drawable.border);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        //tv.setText(textValue);
        return tv;
    }

    private EditText addEditTextView(){
        EditText editTv = new EditText(this);
        editTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        editTv.setBackgroundResource(R.drawable.border);
        editTv.setPadding(5, 5, 5, 5);
        editTv.setTextColor(getResources().getColor(R.color.black));
        // editTv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //editTv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        editTv.setEms(3);
        editTv.setSingleLine(true);
        editTv.setEllipsize(TextUtils.TruncateAt.END);
        return editTv;
    }


    private void initRes(){
        headerText = (TextView)findViewById(R.id.common_header_tv);
        //Test4
        test4_table_layout = (TableLayout)findViewById(R.id.test4_tableLayout1);
        test4_table_layout2 = (TableLayout)findViewById(R.id.test4_tableLayout2);
        test4_table_layout3 = (TableLayout)findViewById(R.id.test4_tableLayout3);
        test4_table_layout4 = (TableLayout)findViewById(R.id.test4_tableLayout4);
        test4_table_layout5 = (TableLayout)findViewById(R.id.test4_tableLayout5);
        test4_table_layout6 = (TableLayout)findViewById(R.id.test4_tableLayout6);
        test4_table_layout7 = (TableLayout)findViewById(R.id.test4_tableLayout7);
        if ("Test4".equalsIgnoreCase(testType)) {
            findViewById(R.id.test_table_4_header_l_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_4_header_2_ll).setVisibility(View.GONE);
        }
    }
}
