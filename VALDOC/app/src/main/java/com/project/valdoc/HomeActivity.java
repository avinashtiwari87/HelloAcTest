package com.project.valdoc;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    TableLayout table_layout,table_layout2,table_layout3,table_layout4;
    TableLayout test2_table_layout,test2_table_layout2,test2_table_layout3,test2_table_layout4,
            test2_table_layout5,test2_table_layout6,test2_table_layout7,test2_table_layout8;
    TableLayout test3_table_layout,test3_table_layout2,test3_table_layout3,test3_table_layout4,
            test3_table_layout5;
    TableLayout test4_table_layout,test4_table_layout2,test4_table_layout3,test4_table_layout4,
            test4_table_layout5,test4_table_layout6,test4_table_layout7;
    //Test 5 View ...
    TableLayout test5_table_layout,test5_table_layout2,test5_table_layout3,test5_table_layout4,
            test5_table_layout5;
    int rows, cols;
    String testType;
    ProgressDialog pr;
    LinearLayout test_table_1_footer,test_table_1_header_l,test_table_1_header_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(HomeActivity.this,"Please Wait", "Loading...");

        if(getIntent().hasExtra("rows") && getIntent().hasExtra("cols")){
            rows = getIntent().getIntExtra("rows",0);
            cols = getIntent().getIntExtra("cols",0);
            testType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + testType);
        }

        initRes();


    }

    @Override
    protected void onStart() {
        super.onStart();
        //removeView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if ("Test1".equalsIgnoreCase(testType)) {
            BuildTable(rows, cols);
        }
        if ("Test2".equalsIgnoreCase(testType)) {
            BuildTableTest2(rows, cols);
        }
        if ("Test3".equalsIgnoreCase(testType)) {
            BuildTableTest3(rows, cols);
        }
        if ("Test4".equalsIgnoreCase(testType)) {
            BuildTableTest4(rows, cols);
        }
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

    private void BuildTableTest3(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView("Grille/Filter ID No "));
                }else{
                    row.addView(addTextView(" Filter No " + i));
                }

            }
            test3_table_layout.addView(row);
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
                    row.addView(addTextView(" Measured Supply Air Velocity in cfm (in cfm) "));
                }else{
                    // row.addView(addResultTextView(i));
                    row.addView(addTextView(" "));
                }
            }
            test3_table_layout2.addView(row);
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
                    row.addView(addTextView("Total Air Flow Rate in cfm (TFR)"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextViewWithoutBorder("0"));
                }
            }
            test3_table_layout3.addView(row);
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
                    row.addView(addTextView("Room Volume in ft3(RV)"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextViewWithoutBorder("490"));
                }
            }
            test3_table_layout4.addView(row);
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
                    row.addView(addTextView("No. of Air Changes/Hr((TFR/RV)x60))"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextViewWithoutBorder("490"));
                }
            }
            test3_table_layout5.addView(row);
        }

        //dismiss progressbar
        if(pr.isShowing())
            pr.dismiss();


    }

    private void BuildTableTest2(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView("Grille / Filter ID"));
                }else{
                    row.addView(addTextView("AHU 2031/0.3MICRON/" + i));
                }

            }
            test2_table_layout.addView(row);

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
                    row.addView(addTextView("Grill/Filter Size in ft2(A)"));
                }else{
                    // row.addView(addResultTextView(i));
                    row.addView(addTextView("1.9"));
                }
            }
            test2_table_layout2.addView(row);

        }

        //Third section
        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= cols; j++) {
                if(i==1 && j<=cols){
                    row.addView(addTextView(" V "+j));
                }else{
                    //tv.setText(84+i+j+"");
                    row.addView(addEditTextView());
                }
            }
            test2_table_layout3.addView(row);
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
                    row.addView(addTextView("Avg Velocity in fpm(AV)"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextView("258"));
                }
            }
            test2_table_layout4.addView(row);

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
                    row.addView(addTextView("Air Flow Rate in cfm(AxAv)"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextView("490"));
                }
            }
            test2_table_layout5.addView(row);

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
                    row.addView(addTextView("Total Air Flow Rate in cfm (TFR)"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextViewWithoutBorder("490"));
                }
            }
            test2_table_layout6.addView(row);
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
                    row.addView(addTextView("Room Volume in ft3(RV)"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextViewWithoutBorder("490"));
                }
            }
            test2_table_layout7.addView(row);
        }

        //Eight section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView("No. of Air Changes/Hr((TFR/RV)x60))"));
                }else{
                    //row.addView(addResultTextView(i));
                    row.addView(addTextViewWithoutBorder("490"));
                }
            }
            test2_table_layout8.addView(row);
        }

        //dismiss progressbar
        if(pr.isShowing())
            pr.dismiss();

    }

    private void BuildTable(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("Grille / Filter ID"));
                } else {
                    row.addView(addTextView("QC/DGC/HF/0" + i));
                }

            }

            table_layout.addView(row);

        }

        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" V " + j));
                } else {
                    //tv.setText(84+i+j+"");
                    row.addView(addEditTextView());
                }
            }
            table_layout2.addView(row);
        }


        //third section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" V "));
                } else {
                    row.addView(addEditTextView());
                }
            }
            table_layout3.addView(row);

        }
        //fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {

                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Result "));
                } else {
                    row.addView(addTextView(" PASS "));
                }
            }
            table_layout4.addView(row);
        }

        //dismiss progressbar
        if (pr.isShowing())
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

        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tableLayout2);
        table_layout3 = (TableLayout) findViewById(R.id.tableLayout3);
        table_layout4 = (TableLayout) findViewById(R.id.tableLayout4);
        //Hide view coming form test tabl 1
        test_table_1_footer = (LinearLayout)findViewById(R.id.test_table_footer_ll);
        test_table_1_header_l = (LinearLayout)findViewById(R.id.test_table_1_header_l_ll);
        test_table_1_header_2 = (LinearLayout)findViewById(R.id.test_table_1_header_2_ll);
        if("Test1".equalsIgnoreCase(testType)){
            test_table_1_header_l.setVisibility(View.GONE);
            test_table_1_header_2.setVisibility(View.GONE);
        }

        //Test 2
        test2_table_layout = (TableLayout)findViewById(R.id.test2_tableLayout1);
        test2_table_layout2 = (TableLayout)findViewById(R.id.test2_tableLayout2);
        test2_table_layout3 = (TableLayout)findViewById(R.id.test2_tableLayout3);
        test2_table_layout4 = (TableLayout)findViewById(R.id.test2_tableLayout4);
        test2_table_layout5 = (TableLayout)findViewById(R.id.test2_tableLayout5);
        test2_table_layout6 = (TableLayout)findViewById(R.id.test2_tableLayout6);
        test2_table_layout7 = (TableLayout)findViewById(R.id.test2_tableLayout7);
        test2_table_layout8 = (TableLayout)findViewById(R.id.test2_tableLayout8);
        //Hide view coming form test tabl 1
        test_table_1_header_l = (LinearLayout)findViewById(R.id.test_table_2_header_l_ll);
        test_table_1_header_2 = (LinearLayout)findViewById(R.id.test_table_2_header_2_ll);
        if ("Test2".equalsIgnoreCase(testType)) {
            test_table_1_header_l.setVisibility(View.GONE);
            test_table_1_header_2.setVisibility(View.GONE);
            findViewById(R.id.test1_table_ll).setVisibility(View.GONE);
            findViewById(R.id.test2_table_ll).setVisibility(View.VISIBLE);
        }

        //Test3
        test3_table_layout = (TableLayout)findViewById(R.id.test3_tableLayout1);
        test3_table_layout2 = (TableLayout)findViewById(R.id.test3_tableLayout2);
        test3_table_layout3 = (TableLayout)findViewById(R.id.test3_tableLayout3);
        test3_table_layout4 = (TableLayout)findViewById(R.id.test3_tableLayout4);
        test3_table_layout5 = (TableLayout)findViewById(R.id.test3_tableLayout5);
        //Hide view coming form test tabl 1
        if ("Test3".equalsIgnoreCase(testType)) {
            findViewById(R.id.test_table_3_header_l_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_3_header_2_ll).setVisibility(View.GONE);
            findViewById(R.id.test1_table_ll).setVisibility(View.GONE);
            findViewById(R.id.test3_table_ll).setVisibility(View.VISIBLE);
        }

        //Test4
        test4_table_layout = (TableLayout)findViewById(R.id.test4_tableLayout1);
        test4_table_layout2 = (TableLayout)findViewById(R.id.test4_tableLayout2);
        test4_table_layout3 = (TableLayout)findViewById(R.id.test4_tableLayout3);
        test4_table_layout4 = (TableLayout)findViewById(R.id.test4_tableLayout4);
        test4_table_layout5 = (TableLayout)findViewById(R.id.test4_tableLayout5);
        test4_table_layout6 = (TableLayout)findViewById(R.id.test4_tableLayout6);
        test4_table_layout7 = (TableLayout)findViewById(R.id.test4_tableLayout7);
        if ("Test4".equalsIgnoreCase(testType)) {
            findViewById(R.id.test1_table_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_4_header_l_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_4_header_2_ll).setVisibility(View.GONE);
            findViewById(R.id.test4_table_ll).setVisibility(View.VISIBLE);
        }

        //Test5
        test5_table_layout = (TableLayout)findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout)findViewById(R.id.test5_tableLayout2);
        test5_table_layout3 = (TableLayout)findViewById(R.id.test5_tableLayout3);
        test5_table_layout4 = (TableLayout)findViewById(R.id.test5_tableLayout4);
        test5_table_layout5 = (TableLayout)findViewById(R.id.test5_tableLayout5);
        if ("Test5".equalsIgnoreCase(testType)) {
            findViewById(R.id.test1_table_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_5_header_l_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_5_header_2_ll).setVisibility(View.GONE);
            findViewById(R.id.test5_table_ll).setVisibility(View.VISIBLE);
        }



    }


    private void removeView(){
        if("Test1".equalsIgnoreCase(testType)){
            table_layout.removeAllViews();
            table_layout2.removeAllViews();
            table_layout3.removeAllViews();
            table_layout4.removeAllViews();
        }

        //Test 2
        if("Test2".equalsIgnoreCase(testType)){
            test2_table_layout.removeAllViews();
            test2_table_layout2.removeAllViews();
            test2_table_layout3.removeAllViews();
            test2_table_layout4.removeAllViews();
            test2_table_layout5.removeAllViews();
            test2_table_layout6.removeAllViews();
            test2_table_layout7.removeAllViews();
            test2_table_layout8.removeAllViews();
        }


    }

}
