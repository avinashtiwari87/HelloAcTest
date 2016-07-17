package com.project.valdoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.valdoc.utility.Utilityies;

import java.util.ArrayList;

public class RDPCTPostViewActivity extends AppCompatActivity {
    private static final String TAG = "RDPCTPostViewActivity";
    TextView headerText;
    //Test 5 View ...
    TableLayout test5_table_layout, test5_table_layout2, test5_table_layout2_1,
            test5_table_layout3, test5_tableLayout2_2, test5_table_layout4, test5_tableLayout4_2, test5_table_layout4_1,
            test5_table_layout5, test5_table_layout5_1, test5_table_layout3_1;
    // PCT new footer
    TextView meanValue1_tv, meanValue2_tv, stdDev1_tv, stdDev2_tv, ucl1_tv, ucl2_tv, minimumValue1_tv, minimumValue2_tv, maximumValue1_tv, maximumValue2_tv;
    SharedPreferences sharedpreferences;
    private String userName = "";
    private int rows, cols, testDetailId = 1, est5CommonFormulaIds2 = 600;
    String mTestType;
    private String mTestCode = "";
    private String testType;
    private String mTestBasedOn;
    ProgressDialog pr;
    int test5CommonFormulaIds1 = 500, test5CommonFormulaIds2 = 600;
    long meanValue1 = 0l, meanValue2 = 0l;
    double stdDev1 = 0.0, stdDev2 = 0.0;
    ArrayList<TextView> RDPC3TxtList, RDPC3TxtList2;
    ArrayList<TextView> resultTextViewList;
    ArrayList<TextView> txtViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdpctpost_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
        pr.setCanceledOnTouchOutside(true);
        pr.setCancelable(true);

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        userName = sharedpreferences.getString("USERNAME", "");
        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDPCTPostViewActivity.this, mActionBar, userName);

        //arrayList init
        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();
        RDPC3TxtList = new ArrayList<TextView>();
        RDPC3TxtList2 = new ArrayList<TextView>();

        // init view
        initRes();
        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            mTestType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + mTestType);
        }
        mTestCode = getIntent().getStringExtra("testCode");
        mTestBasedOn = getIntent().getStringExtra("testBasedOn");
        testType = getIntent().getStringExtra("testType");
        if (TestCreateActivity.PCT.equalsIgnoreCase(mTestType)) {
            BuildTableTest5(rows, cols);
//            BuildTableTest5(7, 3);
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
                if (i == 1 && j == 1) {

                    TextView textView = addTextView(" Location ");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height2);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    int position = i - 1;
                    row.addView(addTextView(" " + position));
                }

            }
            test5_table_layout.addView(row);
        }
/*        for (int sk = 0; sk < 3; sk++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(addTextView("    "));
            test5_table_layout.addView(row);
        }*/

// adding no of partical text
//        if(i==1) {
        TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        TextView tvs = addTextView(" No. of Particles >= 5 µm/m³  ");
        tvs.setEms(12);
        row1.addView(tvs);
        test5_tableLayout2_2.addView(row1);
//        test5_table_layout2_1.addView(row1);
//        }


        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop 1
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" R " + j));
                } else {
                    //row.addView(addTextView(" 4434 | 3434 | 1341 "));
                    row.addView(addInputDataTextView());
                }
            }
            test5_table_layout2_1.addView(row);
        }
        for (int sk = 0; sk < 3; sk++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if (sk == 0) {
                row.addView(addStretchedTextView(" Mean Average "));
            }
            if (sk == 1) {
                row.addView(addStretchedTextView(" Standard Deviation "));
            }
            if (sk == 2) {
                row.addView(addStretchedTextView(" 95% UCL "));
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
                if (i == 1 && j == 1) {
                    TextView textView = addTextView(" Average ");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height2);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    row.addView(addResultTextView(i));
                }

            }
            test5_table_layout3.addView(row);
        }
        //Footer Rows....
        for (int sk = 0; sk < 3; sk++) {
            TableRow rowFooter = new TableRow(this);
            rowFooter.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            rowFooter.addView(addTextViewWithTagIds(sk, test5CommonFormulaIds1, RDPC3TxtList, 0));
            test5_table_layout3_1.addView(rowFooter);
            test5CommonFormulaIds1++;
        }


        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        TextView tvs1 = addTextView(" No. of Particles >= 5 µm/m³  ");
        tvs1.setEms(12);
        row2.addView(tvs1);
        test5_tableLayout4_2.addView(row2);

        //Fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop 1
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" R " + j));
                } else {
                    row.addView(addInputDataTextView());
                }

            }
            test5_table_layout4_1.addView(row);

        }
        for (int sk = 0; sk < 3; sk++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if (sk == 0) {
                row.addView(addStretchedTextView(" Mean Average "));
            }
            if (sk == 1) {
                row.addView(addStretchedTextView(" Standard Deviation "));
            }
            if (sk == 2) {
                row.addView(addStretchedTextView(" 95% UCL "));
            }
//            row.addView(addStretchedTextView(" Mean Average  "));
            test5_table_layout4.addView(row);
        }

        //Fifth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    TextView textView = addTextView("Average");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height2);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    row.addView(addResultTextView(rows + i));
                }

            }
            test5_table_layout5.addView(row);
        }
        //Footer Rows....
        for (int sk = 0; sk < 3; sk++) {
            TableRow rowFooter = new TableRow(this);
            rowFooter.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            rowFooter.addView(addTextViewWithTagIds(sk, test5CommonFormulaIds2, RDPC3TxtList2, 0));
            test5_table_layout5_1.addView(rowFooter);
            test5CommonFormulaIds2++;
        }

        TextView txtView = RDPC3TxtList.get(0);
        txtView.setText(meanValue1 + "");
        TextView txtView2 = RDPC3TxtList2.get(0);
        txtView2.setText(meanValue2 + "");

        TextView txtView3 = RDPC3TxtList.get(1);
        txtView3.setText(stdDev1 + "");
        TextView txtView4 = RDPC3TxtList2.get(1);
        txtView4.setText(stdDev2 + "");

    }

    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(3, 3, 3, 3);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    private TextView addStretchedTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        if (rows != 0)
            tv.setEms(4 * rows);
        else
            tv.setEms(12);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    private TextView addTextViewWithTagIds(int Tag, int Ids,
                                           ArrayList<TextView> txtViewList, float value) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setEms(4);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(value + "");
        Log.d(TAG, "TAG & idCountTv " + Ids);
        tv.setId(Ids);
        tv.setTag(Tag);
        Ids++;
        txtViewList.add(tv);
        return tv;
    }

    int idCountTv = 1;

    private TextView addResultTextView(int rowsNo) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border);
        tv.setPadding(5, 19, 5, 0);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setEms(4);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, "ResultS idCountTv " + idCountTv);
        tv.setId(idCountTv);
        tv.setTag(rowsNo);
        idCountTv++;
        resultTextViewList.add(tv);
        return tv;
    }

    int idCountEtv = 200;

    private TextView addInputDataTextView() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setId(idCountEtv);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEms(4);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        idCountEtv++;
        txtViewList.add(tv);
        return tv;
    }

    private void initRes() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
        headerText.setText("TEST RAW DATA (RD_PCT)\nAirborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices");
        //Test5
        test5_table_layout = (TableLayout) findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout) findViewById(R.id.test5_tableLayout2);
        test5_table_layout2.setVisibility(View.GONE);
        test5_table_layout2_1 = (TableLayout) findViewById(R.id.test5_tableLayout2_1);
        test5_tableLayout2_2 = (TableLayout) findViewById(R.id.test5_tableLayout2_2);
        test5_table_layout3 = (TableLayout) findViewById(R.id.test5_tableLayout3);
        test5_table_layout3_1 = (TableLayout) findViewById(R.id.test5_tableLayout3_1);
        test5_table_layout3_1.setVisibility(View.GONE);
        test5_table_layout4 = (TableLayout) findViewById(R.id.test5_tableLayout4);
        test5_table_layout4.setVisibility(View.GONE);
        test5_table_layout4_1 = (TableLayout) findViewById(R.id.test5_tableLayout4_1);
        test5_tableLayout4_2 = (TableLayout) findViewById(R.id.test5_tableLayout4_2);
        test5_table_layout5 = (TableLayout) findViewById(R.id.test5_tableLayout5);
        test5_table_layout5_1 = (TableLayout) findViewById(R.id.test5_tableLayout5_1);
        test5_table_layout5_1.setVisibility(View.GONE);
        findViewById(R.id.test_table_5_header_l_ll).setVisibility(View.GONE);
        findViewById(R.id.test_table_5_header_2_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.test_interference).setVisibility(View.GONE);
        // PCT new footer
        meanValue1_tv = (TextView)findViewById(R.id.pct_mean_value1);
        meanValue2_tv = (TextView)findViewById(R.id.pct_mean_value2);
        stdDev1_tv = (TextView)findViewById(R.id.pct_std_dev1);
        stdDev2_tv = (TextView)findViewById(R.id.pct_std_dev2);
        ucl1_tv = (TextView)findViewById(R.id.pct_95_ucl_1);
        ucl2_tv= (TextView)findViewById(R.id.pct_95_ucl_2);
        minimumValue1_tv= (TextView)findViewById(R.id.pct_minimum_value_1);
        minimumValue2_tv= (TextView)findViewById(R.id.pct_minimum_value_2);
        maximumValue1_tv= (TextView)findViewById(R.id.pct_maximum_value_1);
        maximumValue2_tv= (TextView)findViewById(R.id.pct_maximum_value_2);
    }

}
