package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RDPC3UserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDPC3";
    TextView headerText;
    //Test 5 View ...
    TableLayout test5_table_layout, test5_table_layout2, test5_table_layout2_1,
            test5_table_layout3, test5_table_layout4, test5_table_layout4_1,
            test5_table_layout5, test5_table_layout5_1, test5_table_layout3_1;
    int rows, cols;
    String testType;
    ProgressDialog pr;
    //Test 5 Variable
    int test5CommonFormulaIds1 = 500, test5CommonFormulaIds2 = 600;
    long meanValue1 = 0l, meanValue2 = 0l;
    double stdDev1 = 0.0, stdDev2 = 0.0;
    ArrayList<TextView> RDPC3TxtList, RDPC3TxtList2;

    // bundel data specification
    private String loginUserType = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String ahuNumber;
    private Room room;
    private ArrayList<HashMap<String, String>> grillAndSizeFromGrill;
    private String areaName;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private String userName = "";

    //certificate view id creation
    private TextView instrumentUsed;
    private TextView make;
    private TextView model;
    private TextView instrumentSerialNo;
    private TextView calibrationOn;
    private TextView calibrationDueOn;
    private TextView testSpecification;
    private TextView plantName;
    private TextView areaOfTest;
    private TextView roomName;
    private TextView occupancyState;
    private TextView testRefrance;
    private TextView equipmentNameText;
    private TextView equipmentNoText;
    private TextView equipmentName;
    private TextView equipmentNo;
    private TextView testCundoctor;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;

    private TextView instrumentNoTextView;
    private TextView testerNameTextView;
    private TextView instrumentUsedTextView;
    private TextView testCunductedByTextView;
    ArrayList<TextView> txtViewList;
    private Button submit;
    private Button clear;
    private Button cancel;
    HashMap<Integer, Integer> rHashMap;
    HashMap<Integer, Long> averageResultHashMap;
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDPC3UserEntryActivity.this);

    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdpc3_user_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");

        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();
        RDPC3TxtList = new ArrayList<TextView>();
        RDPC3TxtList2 = new ArrayList<TextView>();

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            testType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + testType);
        }
//dynamic data population
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        textViewValueAssignment();
        initRes();
        datePicker();
        if ("RD_PC_3".equalsIgnoreCase(testType)) {
            BuildTableTest5(rows, cols);
        }


        //Receiving User Input Data from Bundle
        rHashMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("InputData");
        for (int i = 0; i < txtViewList.size(); i++) {
            TextView tvl = txtViewList.get(i);
            tvl.setText(rHashMap.get(tvl.getId()) + "");
        }
        //Receiving Result Data from Bundle
        averageResultHashMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("ResultData");
        for (int i = 0; i < resultTextViewList.size(); i++) {
            TextView tvl = resultTextViewList.get(i);
            tvl.setText(averageResultHashMap.get(tvl.getId()) + "");
        }

        meanValue1 = getIntent().getLongExtra("meanValue1", 0l);
        meanValue2 = getIntent().getLongExtra("meanValue2", 0l);
        stdDev1 = getIntent().getDoubleExtra("stdDev1", 0.0);
        stdDev2 = getIntent().getDoubleExtra("stdDev2", 0.0);

        TextView txtView = RDPC3TxtList.get(0);
        txtView.setText(meanValue1 + "");
        TextView txtView2 = RDPC3TxtList2.get(0);
        txtView2.setText(meanValue2 + "");

        TextView txtView3 = RDPC3TxtList.get(1);
        txtView3.setText(stdDev1 + "");
        TextView txtView4 = RDPC3TxtList2.get(1);
        txtView4.setText(stdDev2 + "");


    }

    private void datePicker() {
        // Get current date by calender
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

        dateTextView.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // Show selected date
            dateTextView.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

    private void textViewValueAssignment() {
        if (loginUserType.equals("CLIENT")) {
            instrumentUsed.setText(clientInstrument.getcInstrumentName());
            make.setText(clientInstrument.getMake());
            model.setText(clientInstrument.getModel());
            instrumentSerialNo.setText(""+clientInstrument.getSerialNo());
            calibrationOn.setText(clientInstrument.getLastCalibrated());
            calibrationDueOn.setText(clientInstrument.getCalibrationDueDate());
        } else {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
            make.setText(partnerInstrument.getMake());
            model.setText(partnerInstrument.getModel());
            instrumentSerialNo.setText(""+partnerInstrument.getpInstrumentId());
            calibrationOn.setText(partnerInstrument.getLastCalibrated());
            calibrationDueOn.setText(partnerInstrument.getCalibrationDueDate());
        }

        testSpecification.setText("" + room.getAcphNLT());
//                plantName
        areaOfTest.setText(areaName);
        roomName.setText(room.getRoomName());
        occupancyState.setText(room.getOccupancyState().toString());
        Log.d("valdoc", "RDAV5UserEnryActivity 1witness= equipment.getTestReference()=" + room.getTestRef());
        testRefrance.setText("" + room.getTestRef().toString());
        equipmentName.setText(room.getRoomNo().toString());
        equipmentNo.setText(ahuNumber);
        equipmentNameText.setText(getResources().getString(R.string.room_no));
        equipmentNoText.setText(getResources().getString(R.string.ahu_no));
        testCundoctor.setText(userName);
        Log.d("valdoc", "RDAV5UserEnryActivity 1witness=" + witnessFirst);
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testWitness.setText(witness);
    }

    private void initTextView() {
        // layout data which is not in use
        instrumentNoTextView = (TextView) findViewById(R.id.instrument_no_test5);
        instrumentNoTextView.setVisibility(View.GONE);
        testerNameTextView = (TextView) findViewById(R.id.tester_name_test5);
        testerNameTextView.setVisibility(View.GONE);
        instrumentUsedTextView = (TextView) findViewById(R.id.instrument_used);
        instrumentUsedTextView.setVisibility(View.GONE);
        testCunductedByTextView = (TextView) findViewById(R.id.testcunducted_by);
        testCunductedByTextView.setVisibility(View.GONE);

        dateTextView = (TextView) findViewById(R.id.datetextview);
        customerName = (TextView) findViewById(R.id.customer_name);
        certificateNo = (TextView) findViewById(R.id.certificate_no);
        instrumentUsed = (TextView) findViewById(R.id.instrumentused);
        make = (TextView) findViewById(R.id.make);
        model = (TextView) findViewById(R.id.modle);
        instrumentSerialNo = (TextView) findViewById(R.id.instrumentserialno);
        calibrationOn = (TextView) findViewById(R.id.calibratedon);
        calibrationDueOn = (TextView) findViewById(R.id.calibrationdueon);
        testSpecification = (TextView) findViewById(R.id.testspecification);
        plantName = (TextView) findViewById(R.id.plantname);
        areaOfTest = (TextView) findViewById(R.id.areaoftest);
        roomName = (TextView) findViewById(R.id.roomname);
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        equipmentNameText = (TextView) findViewById(R.id.equipment_name_text);
        equipmentNoText = (TextView) findViewById(R.id.equipment_no_text);
        equipmentName = (TextView) findViewById(R.id.equipmentname);
        equipmentNo = (TextView) findViewById(R.id.equipmentno);
        testCundoctor = (TextView) findViewById(R.id.testcunducter);
        testWitness = (TextView) findViewById(R.id.testwitness);
        submit = (Button) findViewById(R.id.submit);
        clear = (Button) findViewById(R.id.clear);
        clear.setVisibility(View.INVISIBLE);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mValdocDatabaseHandler.insertTestDetails(ValdocDatabaseHandler.TEST_DETAILS_TABLE_NAME, testDetailsDataCreation())) {
//                    if (mValdocDatabaseHandler.insertTestReading(ValdocDatabaseHandler.TESTREADING_TABLE_NAME, testReading())) {
//                        if (mValdocDatabaseHandler.insertTestSpesificationValue(ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TABLE_NAME, testSpesificationValue())) {
                            Toast.makeText(RDPC3UserEntryActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RDPC3UserEntryActivity.this, TestCreateActivity.class);
                intent.putExtra("RD_PC_3", true);
                startActivity(intent);
                finish();
//                        } else {
//                            Toast.makeText(RDPC3UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//                        Toast.makeText(RDPC3UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
//                    }
//
//                } else {
//                    Toast.makeText(RDPC3UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
//                }

//                mValdocDatabaseHandler.insertTestSpesificationValue(ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TABLE_NAME, testSpesificationValueDataCreation());
            }
        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
            }
        });
    }


    private ArrayList<TestSpesificationValue> testSpesificationValue() {
        ArrayList<TestSpesificationValue> spesificationValueArrayList = new ArrayList<TestSpesificationValue>();
        TestSpesificationValue testSpesificationValue = new TestSpesificationValue();
        testSpesificationValue.setTest_specific_id(1);
        testSpesificationValue.setTest_detail_id("1");
        testSpesificationValue.setFieldName("Mean Average");
        testSpesificationValue.setFieldValue("" + meanValue1);
        spesificationValueArrayList.add(testSpesificationValue);

        stdDev1 = getIntent().getDoubleExtra("stdDev1", 0.0);
        stdDev2 = getIntent().getDoubleExtra("stdDev2", 0.0);

        TestSpesificationValue testSpesificationValue1 = new TestSpesificationValue();
        testSpesificationValue1.setTest_specific_id(1);
        testSpesificationValue1.setTest_detail_id("1");
        testSpesificationValue1.setFieldName("Mean Average");
        testSpesificationValue1.setFieldValue("" + meanValue2);
        spesificationValueArrayList.add(testSpesificationValue1);

        TestSpesificationValue testSpesificationValue2 = new TestSpesificationValue();
        testSpesificationValue2.setTest_specific_id(1);
        testSpesificationValue2.setTest_detail_id("1");
        testSpesificationValue2.setFieldName("Standard Deviation");
        testSpesificationValue2.setFieldValue("" + stdDev1);
        spesificationValueArrayList.add(testSpesificationValue2);

        TestSpesificationValue testSpesificationValue3 = new TestSpesificationValue();
        testSpesificationValue3.setTest_specific_id(1);
        testSpesificationValue3.setTest_detail_id("1");
        testSpesificationValue3.setFieldName("Standard Deviation");
        testSpesificationValue3.setFieldValue("" + stdDev1);
        spesificationValueArrayList.add(testSpesificationValue3);

        return spesificationValueArrayList;
    }

    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> testReadingArrayList = new ArrayList<TestReading>();
        int hasMapKey = 200;
        for (int i = 0; i < rows; i++) {
                TestReading testReading = new TestReading();
                testReading.setTestReadingID(i);
//        TO DO test details id is id of test details table
                testReading.setTest_detail_id(i);
                testReading.setEntityName("" + i);
                StringBuilder grilList = new StringBuilder();
                //v1,v2....value cration
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < cols; j++) {
                    if (j != 0)
                        sb.append(',');
                    sb.append(rHashMap.get(hasMapKey).toString());
                    hasMapKey++;
                }
                grilList.append(sb).append(",").append(averageResultHashMap.get(i));
                testReading.setValue(grilList.toString());
                testReadingArrayList.add(testReading);
            }
        return testReadingArrayList;
    }


    private TestDetails testDetailsDataCreation() {
        TestDetails testDetails = new TestDetails();
//        TO DO: need to make it dynamic
        testDetails.setTest_detail_id(1);
        testDetails.setCustomer(customerName.getText().toString());
        testDetails.setDateOfTest(dateTextView.getText().toString());
        testDetails.setRawDataNo(certificateNo.getText().toString());
        if (loginUserType.equals("CLIENT")) {
            testDetails.setInstrumentUsed(clientInstrument.getcInstrumentName());
            testDetails.setMake(clientInstrument.getMake());
            testDetails.setModel(clientInstrument.getModel());
            testDetails.setInstrumentNo(clientInstrument.getSerialNo());
            testDetails.setCalibratedOn(clientInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn(clientInstrument.getCalibrationDueDate());
        } else {
            testDetails.setInstrumentUsed(partnerInstrument.getpInstrumentName());
            testDetails.setMake(partnerInstrument.getMake());
            testDetails.setModel(partnerInstrument.getModel());
            testDetails.setInstrumentNo("" + partnerInstrument.getpInstrumentId());
            testDetails.setCalibratedOn(partnerInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn(partnerInstrument.getCalibrationDueDate());
        }


        testDetails.setTestSpecification(testSpecification.getText().toString());
        testDetails.setBlockName(plantName.getText().toString());
        testDetails.setTestArea(areaOfTest.getText().toString());
        testDetails.setRoomName(roomName.getText().toString());
        testDetails.setRoomNo(equipmentName.getText().toString());
        testDetails.setOccupencyState(occupancyState.getText().toString());
        testDetails.setTestReference(testRefrance.getText().toString());
        testDetails.setAhuNo(equipmentNo.getText().toString());
        testDetails.setTesterName(testCundoctor.getText().toString());

        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testWitness.setText(witness);
        return testDetails;
    }


    private void getExtraFromTestCreateActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            Log.d("valdoc", "DynamicTableActivity" + "onresume rows= getextra start");
            if (extras == null) {
                Log.d("valdoc", "DynamicTableActivity" + "onresume rows=extra null");
                loginUserType = null;
                clientInstrument = null;
                partnerInstrument = null;
                ahuNumber = null;
                grillAndSizeFromGrill = null;
                room = null;
                areaName = null;
                witnessFirst = null;
                witnessSecond = null;
                witnessThird = null;
            } else {
                loginUserType = extras.getString("USERTYPE");
                userName = extras.getString("USERNAME");
                witnessFirst = extras.getString("WITNESSFIRST");
                witnessSecond = extras.getString("WITNESSSECOND");
                witnessThird = extras.getString("WITNESSTHIRD");
                //get area based on room area id
                areaName = extras.getString("AREANAME");

                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }

                room = (Room) extras.getSerializable("Room");
                ahuNumber = extras.getString("AhuNumber");
                grillAndSizeFromGrill = (ArrayList<HashMap<String, String>>) extras.getSerializable("GRILLIST");

            }
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
                    row.addView(addTextView(" Location "));
                } else {
                    int position = i - 1;
                    row.addView(addTextView(" " + position));
                }

            }
            test5_table_layout.addView(row);
        }
        for (int sk = 0; sk < 3; sk++) {
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
                    row.addView(addTextView(" Average "));
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
                    // row.addView(addEditTextView(rows + i));
                    row.addView(addInputDataTextView());
                }

            }
            test5_table_layout4_1.addView(row);

        }
        for (int sk = 0; sk < 3; sk++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(addStretchedTextView("  "));
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
                    row.addView(addTextView(" Average "));
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
        tv.setPadding(5, 5, 5, 5);
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
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setId(idCountEtv);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        idCountEtv++;
        txtViewList.add(tv);
        return tv;
    }

    private void initRes() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
        headerText.setText("* Airborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices *");
        //Test5
        test5_table_layout = (TableLayout) findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout) findViewById(R.id.test5_tableLayout2);
        test5_table_layout2_1 = (TableLayout) findViewById(R.id.test5_tableLayout2_1);
        test5_table_layout3 = (TableLayout) findViewById(R.id.test5_tableLayout3);
        test5_table_layout3_1 = (TableLayout) findViewById(R.id.test5_tableLayout3_1);
        test5_table_layout4 = (TableLayout) findViewById(R.id.test5_tableLayout4);
        test5_table_layout4_1 = (TableLayout) findViewById(R.id.test5_tableLayout4_1);
        test5_table_layout5 = (TableLayout) findViewById(R.id.test5_tableLayout5);
        test5_table_layout5_1 = (TableLayout) findViewById(R.id.test5_tableLayout5_1);
        findViewById(R.id.test_table_5_header_l_ll).setVisibility(View.GONE);
        findViewById(R.id.test_table_5_header_2_ll).setVisibility(View.GONE);
    }
}
