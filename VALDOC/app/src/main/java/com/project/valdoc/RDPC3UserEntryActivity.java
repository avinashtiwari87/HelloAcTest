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

public class RDPC3UserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDPC3";
    TextView headerText;
    //Test 5 View ...
    TableLayout test5_table_layout,test5_table_layout2,test5_table_layout3,test5_table_layout4,
            test5_table_layout5;
    int rows, cols;
    String testType;
    ProgressDialog pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdpc3_user_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this,"Please Wait", "Loading...");

        if(getIntent().hasExtra("rows") && getIntent().hasExtra("cols")){
            rows = getIntent().getIntExtra("rows",0);
            cols = getIntent().getIntExtra("cols",0);
            testType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + testType);
        }

        initRes();
        if ("Test5".equalsIgnoreCase(testType)) {
            BuildTableTest5(rows, cols);
        }
    }
    private void BuildTableTest5(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Location \n   "));
                }else{
                    row.addView(addTextView(" " + i));
                }

            }
            test5_table_layout.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(addTextView("    "));
            test5_table_layout.addView(row);
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
                    row.addView(addTextView(" No. of Particles >0. 5 µm/m³ \n (R1 | R2 | R3) "));
                }else{
                    row.addView(addTextView(" 4434 | 3434 | 1341 "));
                }

            }
            test5_table_layout2.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if(sk==0){
                row.addView(addTextView("   Mean Average   "));
            }if(sk==1){
                row.addView(addTextView("   Standard Deviation   "));
            }if(sk==2){
                row.addView(addTextView("   95% UCL   "));
            }
            test5_table_layout2.addView(row);
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
                    row.addView(addTextView(" Average \n "));
                }else{
                    row.addView(addTextView(" 1919643"+i));
                }

            }
            test5_table_layout3.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if(sk==0){
                row.addView(addTextView("   4878776542   "));
            }if(sk==1){
                row.addView(addTextView("   78441734560   "));
            }if(sk==2){
                row.addView(addTextView("   1129564326705   "));
            }
            test5_table_layout3.addView(row);
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
                    row.addView(addTextView(" No. of Particles > 5 µm/m³ \n (R1 | R2 |R3 )"));
                }else{
                    row.addView(addTextView(" 13123 | 12323 | 1341 "));
                }

            }
            test5_table_layout4.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(addTextView("  "));
            test5_table_layout4.addView(row);
        }

        //Fifth section
        // outer for loop
        for (int i = 1; i <= (rows+3); i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Average \n "));
                }else{
                    row.addView(addTextView(" 8893 "));
                }

            }
            test5_table_layout5.addView(row);
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
        headerText.setText("* Airborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices *");
        //Test5
        test5_table_layout = (TableLayout)findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout)findViewById(R.id.test5_tableLayout2);
        test5_table_layout3 = (TableLayout)findViewById(R.id.test5_tableLayout3);
        test5_table_layout4 = (TableLayout)findViewById(R.id.test5_tableLayout4);
        test5_table_layout5 = (TableLayout)findViewById(R.id.test5_tableLayout5);
        if ("Test5".equalsIgnoreCase(testType)) {
            findViewById(R.id.test_table_5_header_l_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_5_header_2_ll).setVisibility(View.GONE);
        }
    }
}
