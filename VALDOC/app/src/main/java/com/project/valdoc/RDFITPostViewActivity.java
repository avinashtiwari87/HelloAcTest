
package com.project.valdoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.AhuFilter;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.RoomFilter;
import android.support.v4.content.ContextCompat;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import java.util.ArrayList;

public class RDFITPostViewActivity extends AppCompatActivity {
    private static final String TAG = "RDFITPostViewActivity";
    TextView headerText;
    TableLayout test4_table_layout, test4_table_layout2, test4_table_layout3, test4_table_layout4,
            test4_table_layout5, test4_table_layout6, test4_table_layout7, test4_table_layout8;
    //Header TextView
    TextView roomVolume, roomVolumeText, ahuNo, ahuNoText, equipmentNameText,equipmentNoText, dateTextView;
    TextView plantName, areaOfTest, roomName, occupancyState, testRefrance,roomNo, testCundoctor,testWitness,testCondoctorOrg,testWitnessOrg;
    TextView customerName, certificateNo, instrumentUsed, instrumentSerialNo, calibrationOn,calibrationDueOn, testSpecification;
    private ImageView cancel;
    SharedPreferences sharedpreferences;
    private String userName = "";
    String testType = null;
    private int rows, cols, testDetailId = 1;
    ProgressDialog pr;
    private ValdocDatabaseHandler mValdocDatabaseHandler;
    private ArrayList<TestReading> testReadingArrayList;
    private ArrayList<TestSpesificationValue> testSpecificationValueArrayList;
    private TestDetails mTestDetails;
    String spiltValue[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdfitpost_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
        pr.setCanceledOnTouchOutside(true);
        pr.setCancelable(true);

        mValdocDatabaseHandler = new ValdocDatabaseHandler(RDFITPostViewActivity.this);

        //getting Vale for Table
        testType = getIntent().getStringExtra("TestType");
        testDetailId = getIntent().getIntExtra("testDetailId", 1);
        Log.d(TAG, " TestType " + testType + " testDetailId " + testDetailId);

        //init res file from xml
        initRes();
        //Header text view initialization
        initTextView();

        //Setting User Name
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        userName = sharedpreferences.getString("USERNAME", "");
        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDFITPostViewActivity.this, mActionBar, userName);

        //Reading Data from DB
        testReadingArrayList = mValdocDatabaseHandler.getTestReadingDataById(testDetailId + "");
        mTestDetails = mValdocDatabaseHandler.getTestDetailById(testDetailId);
        testSpecificationValueArrayList = mValdocDatabaseHandler.getTestSpecificationValueById(testDetailId + "");
        spiltValue = testReadingArrayList.get(0).getValue().split(",");
        rows = testReadingArrayList.size()+1;
        cols = (spiltValue.length-2);
        Log.d(TAG, " rows "+rows+" cols "+cols);
        // setting teast header value
        textViewValueAssignment();

        //Create Table for FIT PostView
        if (testType.contains(TestCreateActivity.FIT)) {
            buildTestFour(rows, cols);
        }

    }

    private void buildTestFour(int rows, int column) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Filter No \n         "));
                } else {
                    if (null != testReadingArrayList && testReadingArrayList.size() > 0) {
                        row.addView(addTextView(testReadingArrayList.get(i - 2).getEntityName()));
                    } else {
                        row.addView(addTextView("HF -00" + i));
                    }
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Average \nbefore Scanning "));
                } else {
                    spiltValue = testReadingArrayList.get(i-2).getValue().split(",");
                    row.addView(addTextView(""+spiltValue[2]));
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Average \nAfter Scanning"));
                } else {
                    spiltValue = testReadingArrayList.get(i-2).getValue().split(",");
                    row.addView(addTextView(""+spiltValue[3]));;
                }

            }
            test4_table_layout3.addView(row);
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
                    row.addView(addTextView(" Variation \nin Concentration*"));
                } else {
                    spiltValue = testReadingArrayList.get(i-2).getValue().split(",");
                    row.addView(addTextView(spiltValue[1]+ "%"));
                }

            }
            test4_table_layout4.addView(row);
        }
        //fifth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Obtained Leakage \n(% Leakage)"));
                } else {
                    spiltValue = testReadingArrayList.get(i-2).getValue().split(",");
                    row.addView(addTextView(spiltValue[spiltValue.length-2]+ ""));
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Test Results\n(Passed / Not Passed)"));
                } else {
                    spiltValue = testReadingArrayList.get(i-2).getValue().split(",");
                    row.addView(addTextView(spiltValue[spiltValue.length-1].toString().trim()));
                }
            }
            test4_table_layout8.addView(row);
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
        if("PASS".equalsIgnoreCase(textValue)){
            tv.setTextColor(ContextCompat.getColor(this, R.color.blue));
        }else if("FAIL".equalsIgnoreCase(textValue)){
            tv.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
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

    private void textViewValueAssignment() {
        dateTextView.setText("" + mTestDetails.getDateOfTest());
        instrumentUsed.setText(mTestDetails.getInstrumentUsed());
//            make.setText(clientInstrument.getMake());
        instrumentSerialNo.setText("" + mTestDetails.getInstrumentNo());
        calibrationOn.setText(Utilityies.parseDateToddMMyyyy(mTestDetails.getCalibratedOn()));
        calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(mTestDetails.getCalibratedDueOn()));
        testCundoctor.setText(mTestDetails.getTesterName());
        testSpecification.setText(mTestDetails.getTestSpecification());
        occupancyState.setText(mTestDetails.getOccupencyState());
        testRefrance.setText(mTestDetails.getTestReference());
        areaOfTest.setText(mTestDetails.getTestArea());
        roomName.setText(mTestDetails.getRoomName());
        // room no not needed
        roomNo.setText(mTestDetails.getRoomNo());
        ahuNo.setText(mTestDetails.getAhuNo());
        roomVolume.setText(mTestDetails.getRoomVolume());
        testCondoctorOrg.setText(mTestDetails.getTestCondoctorOrg());
        testWitnessOrg.setText(mTestDetails.getTestWitnessOrg());
        testWitness.setText(mTestDetails.getWitnessName());
        certificateNo.setText("" + mTestDetails.getRawDataNo());
    }


    private void initTextView() {
        roomVolumeText = (TextView) findViewById(R.id.room_volume_text);
        roomVolumeText.setVisibility(View.VISIBLE);
        roomVolume = (TextView) findViewById(R.id.room_volume);
        roomVolume.setVisibility(View.VISIBLE);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        ahuNo.setVisibility(View.VISIBLE);
        ahuNoText = (TextView) findViewById(R.id.ahu_no_text);
        ahuNoText.setVisibility(View.VISIBLE);

        equipmentNameText = (TextView) findViewById(R.id.equiment_name_text);
        equipmentNameText.setVisibility(View.INVISIBLE);
        equipmentNoText = (TextView) findViewById(R.id.equiment_no_text);
        equipmentNoText.setVisibility(View.INVISIBLE);

        dateTextView = (TextView) findViewById(R.id.datetextview);
        customerName = (TextView) findViewById(R.id.customer_name);
        certificateNo = (TextView) findViewById(R.id.trd_no);
        instrumentUsed = (TextView) findViewById(R.id.instrumentused);

        instrumentSerialNo = (TextView) findViewById(R.id.instrumentserialno);
        calibrationOn = (TextView) findViewById(R.id.calibratedon);
        calibrationDueOn = (TextView) findViewById(R.id.calibrationdueon);
        testSpecification = (TextView) findViewById(R.id.testspecification);
        plantName = (TextView) findViewById(R.id.plantname);
        areaOfTest = (TextView) findViewById(R.id.areaoftest);
        roomName = (TextView) findViewById(R.id.room_name);
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        roomNo = (TextView) findViewById(R.id.room_no);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        testCundoctor = (TextView) findViewById(R.id.testcunducter);
        testWitness = (TextView) findViewById(R.id.testwitness);
        testCondoctorOrg = (TextView) findViewById(R.id.test_condoctor_org);
        testWitnessOrg = (TextView) findViewById(R.id.testwitness_org);
    }

    private void initRes() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
        if(testType.contains("ERD_FIT")){
            headerText.setText("TEST RAW DATA EQUIPMENT\nInstalled HEPA Filter System Leakage Test by Aerosol Photometer Method");
        }else if(testType.contains("ARD_FIT_AHU")){
            headerText.setText("TEST RAW DATA AHU\nInstalled HEPA Filter System Leakage Test by Aerosol Photometer Method");
        }else{
            headerText.setText("TEST RAW DATA\nInstalled HEPA Filter System Leakage Test by Aerosol Photometer Method");
        }
        findViewById(R.id.submit).setVisibility(View.GONE);
        findViewById(R.id.clear).setVisibility(View.GONE);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        //Test4
        test4_table_layout = (TableLayout) findViewById(R.id.test4_tableLayout1);
        test4_table_layout2 = (TableLayout) findViewById(R.id.test4_tableLayout2);
        test4_table_layout3 = (TableLayout) findViewById(R.id.test4_tableLayout3);
        test4_table_layout4 = (TableLayout) findViewById(R.id.test4_tableLayout4);
        test4_table_layout5 = (TableLayout) findViewById(R.id.test4_tableLayout5);
        test4_table_layout6 = (TableLayout) findViewById(R.id.test4_tableLayout6);
        test4_table_layout7 = (TableLayout) findViewById(R.id.test4_tableLayout7);
        test4_table_layout8 = (TableLayout) findViewById(R.id.test4_tableLayout8);
        findViewById(R.id.test_table_4_header_l_ll).setVisibility(View.GONE);
        findViewById(R.id.test_table_4_header_2_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.test_interference).setVisibility(View.GONE);
    }
}
