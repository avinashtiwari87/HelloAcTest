//package com.project.valdoc;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//import com.project.valdoc.intity.AhuFilter;
//import com.project.valdoc.intity.EquipmentFilter;
//import com.project.valdoc.intity.RoomFilter;
//import com.project.valdoc.utility.Utilityies;
//
//public class RDFITPostViewActivity extends AppCompatActivity {
//    private static final String TAG = "RDFITPostViewActivity";
//    TextView headerText;
//    TableLayout test4_table_layout, test4_table_layout2, test4_table_layout3, test4_table_layout4,
//            test4_table_layout5, test4_table_layout6, test4_table_layout7, test4_table_layout8;
//    SharedPreferences sharedpreferences;
//    private String userName = "";
//    String testType = null;
//    private int rows, cols, testDetailId = 1;
//    ProgressDialog pr;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rdfitpost_view);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
//        pr.setCanceledOnTouchOutside(true);
//        pr.setCancelable(true);
//
//        //init res file from xml
//        initRes();
//
//        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
//        userName = sharedpreferences.getString("USERNAME", "");
//        //Custom Action Bar
//        ActionBar mActionBar = getSupportActionBar();
//        if (mActionBar != null)
//            Utilityies.setCustomActionBar(RDFITPostViewActivity.this, mActionBar, userName);
//
//        testType = getIntent().getStringExtra("TestType");
//        testDetailId = getIntent().getIntExtra("testDetailId", 1);
//        rows = getIntent().getIntExtra("rows", 6);
//        cols = getIntent().getIntExtra("cols", 5);
//        Log.d(TAG, " TestType : " + testType + " testDetailId " + testDetailId);
//
//
//        if (TestCreateActivity.FIT.equalsIgnoreCase(testType)) {
//            buildTestFour(rows, cols);
//        }
//
//    }
//
//    private void buildTestFour(int rows, int column) {
//        //first section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Filter No \n         "));
//                } else {
//                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
//                        if (null != mEquipmentFilterArrayList && mEquipmentFilterArrayList.size() > 0) {
//                            EquipmentFilter equipmentFilter = mEquipmentFilterArrayList.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity filterArrayList=" + mEquipmentFilterArrayList.size() + "i=" + i);
//                            row.addView(addTextView(equipmentFilter.getFilterCode()));
//                        }
//                    } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
//                        if (null != mAhuFilterArrayList && mAhuFilterArrayList.size() > 0) {
//                            AhuFilter ahuFilter = mAhuFilterArrayList.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity filterArrayList=" + mAhuFilterArrayList.size() + "i=" + i);
//                            row.addView(addTextView(ahuFilter.getFilterCode()));
//                        }
//                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
//                        if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
//                            RoomFilter roomFilter = mRoomFilterArrayList.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity filterArrayList=" + mRoomFilterArrayList.size() + "i=" + i);
//                            row.addView(addTextView(roomFilter.getFilterCode()));
//                        }
//                    }
////                    row.addView(addTextView("HF -00" + i));
//                }
//
//            }
//            test4_table_layout.addView(row);
//        }
//
//        //Second section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Average \nbefore Scanning "));
//                } else {
//                    row.addView(addInputDataTextViewBeforeStream());
//                }
//
//            }
//            test4_table_layout2.addView(row);
//        }
//
//        //Third section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Average \nAfter Scanning"));
//                } else {
//                    row.addView(addInputDataTextViewAfterStream());
//                }
//
//            }
//            test4_table_layout3.addView(row);
//        }
//        //fourth section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Variation \nin Concentration*"));
//                } else {
//                    row.addView(addTextView(10 + i + "%"));
//                }
//
//            }
//            test4_table_layout4.addView(row);
//        }
//        //fifth section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Obtained Leakage \n(% Leakage)"));
//                } else {
//                    row.addView(addInputDataTextView());
//                }
//
//            }
//            test4_table_layout5.addView(row);
//        }
//
//        //Sixth section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Test Results\n(Passed / Not Passed)"));
//                } else {
//                    //row.addView(addTextView(" Pass "));
//                    row.addView(addTextPassFail(" ", i));
//                }
//            }
//            test4_table_layout8.addView(row);
//        }
//
//        //dismiss progressbar
//        if (pr.isShowing())
//            pr.dismiss();
//
//    }
//
//    private TextView addTextView(String textValue) {
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        tv.setBackgroundResource(R.drawable.border1);
//        //tv.setPadding(5, 5, 5, 5);
//        tv.setTextColor(getResources().getColor(R.color.black));
//        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
//        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        tv.setSingleLine(false);
//        tv.setMaxLines(3);
//        tv.setEllipsize(TextUtils.TruncateAt.END);
//        tv.setText(textValue);
//        return tv;
//    }
//
//    private TextView addTextViewWithoutBorder(String textValue) {
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        //tv.setBackgroundResource(R.drawable.border);
//        tv.setPadding(5, 5, 5, 5);
//        tv.setTextColor(getResources().getColor(R.color.black));
//        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
//        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        tv.setSingleLine(true);
//        tv.setEllipsize(TextUtils.TruncateAt.END);
//        //tv.setText(textValue);
//        return tv;
//    }
//
//    // Fit before stream
//    int idCountEtvBefore = 800;
//
//    private TextView addInputDataTextViewBeforeStream() {
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        tv.setBackgroundResource(R.drawable.border1);
//        //tv.setPadding(5, 5, 5, 5);
//        tv.setTextColor(getResources().getColor(R.color.black));
//        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
//        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        tv.setId(idCountEtvBefore);
//        tv.setSingleLine(false);
//        tv.setMaxLines(3);
//        tv.setEllipsize(TextUtils.TruncateAt.END);
//        idCountEtvBefore++;
//        txtViewList.add(tv);
//        return tv;
//    }
//
//
//    //Fit After stream
//    int idCountEtvAfter = 900;
//
//    private TextView addInputDataTextViewAfterStream() {
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        tv.setBackgroundResource(R.drawable.border1);
//        //tv.setPadding(5, 5, 5, 5);
//        tv.setTextColor(getResources().getColor(R.color.black));
//        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
//        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        tv.setId(idCountEtvAfter);
//        tv.setSingleLine(false);
//        tv.setMaxLines(3);
//        tv.setEllipsize(TextUtils.TruncateAt.END);
//        idCountEtvAfter++;
//        txtViewList.add(tv);
//        return tv;
//    }
//
//    //obtained result
//    int idCountEtv = 200;
//
//    private TextView addInputDataTextView() {
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        tv.setBackgroundResource(R.drawable.border1);
//        //tv.setPadding(5, 5, 5, 5);
//        tv.setTextColor(getResources().getColor(R.color.black));
//        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
//        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        tv.setId(idCountEtv);
//        tv.setSingleLine(false);
//        tv.setMaxLines(3);
//        tv.setEllipsize(TextUtils.TruncateAt.END);
//        idCountEtv++;
//        txtViewList.add(tv);
//        return tv;
//    }
//
//    int idPassFailTv = 300;
//
//    private TextView addTextPassFail(String textValue, int tagRows) {
//        TextView tv = new TextView(this);
//        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        tv.setBackgroundResource(R.drawable.border1);
//        //tv.setPadding(5, 5, 5, 5);
//        tv.setTextColor(getResources().getColor(R.color.black));
//        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
//        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
//        tv.setSingleLine(false);
//        tv.setTag(tagRows);
//        tv.setId(idPassFailTv);
//        tv.setMaxLines(3);
//        tv.setEllipsize(TextUtils.TruncateAt.END);
//        tv.setText(textValue);
//        idPassFailTv++;
//        txtPassFailList.add(tv);
//        return tv;
//    }
//
//
//    private void initRes() {
//        headerText = (TextView) findViewById(R.id.common_header_tv);
//        //Test4
//        test4_table_layout = (TableLayout) findViewById(R.id.test4_tableLayout1);
//        test4_table_layout2 = (TableLayout) findViewById(R.id.test4_tableLayout2);
//        test4_table_layout3 = (TableLayout) findViewById(R.id.test4_tableLayout3);
//        test4_table_layout4 = (TableLayout) findViewById(R.id.test4_tableLayout4);
//        test4_table_layout5 = (TableLayout) findViewById(R.id.test4_tableLayout5);
//        test4_table_layout6 = (TableLayout) findViewById(R.id.test4_tableLayout6);
//        test4_table_layout7 = (TableLayout) findViewById(R.id.test4_tableLayout7);
//        test4_table_layout8 = (TableLayout) findViewById(R.id.test4_tableLayout8);
//        findViewById(R.id.test_table_4_header_l_ll).setVisibility(View.GONE);
//        findViewById(R.id.test_table_4_header_2_ll).setVisibility(View.VISIBLE);
//        findViewById(R.id.test_interference).setVisibility(View.GONE);
//    }
//}
