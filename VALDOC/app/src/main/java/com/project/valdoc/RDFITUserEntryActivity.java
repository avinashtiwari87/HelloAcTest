package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RDFITUserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDFITUser";
    TextView headerText;
    TableLayout test4_table_layout, test4_table_layout2, test4_table_layout3, test4_table_layout4,
            test4_table_layout5, test4_table_layout6, test4_table_layout7, test4_table_layout8;

    int rows, cols;
    String mTestType;
    ProgressDialog pr;

    // bundel data specification
    private String loginUserType = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String ahuNumber;
    private Room room;
    private ArrayList<RoomFilter> filterArrayList;
    private String areaName;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private String userName = "";

    //certificate view id creation
    private TextView instrumentUsed;
    private TextView make;
    private TextView model;
    private TextView aerosolUsed;
    private TextView aerosolGeneratorType;
    private TextView instrumentSerialNo;
    private TextView calibrationOn;
    private TextView calibrationDueOn;
    private TextView testSpecification;
    private TextView plantName;
    private TextView areaOfTest;
    private TextView roomName;
    private TextView occupancyState;
    private TextView testRefrance;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    //    private TextView equipmentNameText;
//    private TextView equipmentNoText;
    private TextView roomNo;
    private TextView ahuNo;
    private TextView infarance;
    private TextView testCundoctor;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;
    private double totalAirFlowRate = 0;
    private double airChangeValue;
    private TextView instrumentNoTextView;
    private TextView testerNameTextView;
    private TextView instrumentUsedTextView;
    private TextView testCunductedByTextView;
    private TextView roomNameLable;
    private TextView instrumentNoLable;
    private TextView roomNameTest;
    private TextView instrument_name;

    ArrayList<TextView> txtViewList;
    ArrayList<TextView> txtPassFailList;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;
    private String mPartnerName;
    HashMap<Integer, Double> likageDataMap;
    HashMap<Integer, Long> PassFailHashMap;
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDFITUserEntryActivity.this);
    SharedPreferences sharedpreferences;
    int testDetailsId = 0;
    private int year;
    private int month;
    private int day;
    private String mTestCode="";
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdfituser_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");

        txtPassFailList = new ArrayList<TextView>();
        txtViewList = new ArrayList<TextView>();

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            mTestType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + mTestType);
        }
        mTestCode=getIntent().getStringExtra("testCode");
        //dynamic data population
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        textViewValueAssignment();
        initRes();
        datePicker();
        if (TestCreateActivity.FIT.equalsIgnoreCase(mTestType)) {
            BuildTableTest4(rows, cols);
        }


        //Receiving User Input Data from Bundle
        likageDataMap = (HashMap<Integer, Double>) getIntent().getSerializableExtra("InputData");
        for (Map.Entry m : likageDataMap.entrySet()) {
            Log.v(TAG, " InputData " + m.getKey() + " " + m.getValue());
        }
        for (int i = 0; i < txtViewList.size(); i++) {
            TextView tvl = txtViewList.get(i);
            tvl.setText(likageDataMap.get(tvl.getId()) + "");
        }
        //Receiving Pass Fail Data from Bundle
        PassFailHashMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("PassFailData");
        for (Map.Entry O : PassFailHashMap.entrySet()) {
            Log.v(TAG, " PassFail " + O.getKey() + " " + O.getValue());
        }
        int passFlag = 1;
        for (int i = 0; i < txtPassFailList.size(); i++) {
            TextView tvl = txtPassFailList.get(i);
            tvl.setText(PassFailHashMap.get(tvl.getId()) + "");
            if ("PASS".equalsIgnoreCase(tvl.getText().toString().trim())) {
                tvl.setTextColor(getResources().getColor(R.color.blue));
            } else {
                passFlag = 0;
                tvl.setTextColor(getResources().getColor(R.color.red));
            }
        }
        if (passFlag == 0) {
            infarance.setText("The HEPA Filter System do not Qualifies for the above leak test.");
        } else if (passFlag == 1) {
            infarance.setText("The HEPA Filter System Qualifies for the above leak test.");
        } else {
            infarance.setText("");
        }

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDFITUserEntryActivity.this, mActionBar, userName);
    }

    private void datePicker() {
        // Get current date by calender
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //raw data no
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        int mon = month + 1;
        certificateNo.setText("IT/" + mon + "/" + year + "/" + formattedDate);

        // Show current date
        String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
        dateTextView.setText(date);
//        new StringBuilder()
//                // Month is 0 based, just add 1
//                .append(year).append("-").append(month + 1).append("-")
//                .append(day).append(" "));
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
            String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
            dateTextView.setText(date);
//            new StringBuilder().append(year)
//                    .append("-").append(month + 1).append("-").append(day)
//                    .append(" "));

        }
    };

    private void textViewValueAssignment() {
        if (loginUserType.equals("CLIENT")) {
            instrumentUsed.setText(clientInstrument.getcInstrumentName());
            make.setText(clientInstrument.getMake());
            model.setText(clientInstrument.getModel());
            instrumentSerialNo.setText("" + clientInstrument.getSerialNo());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getLastCalibrated()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getCalibrationDueDate()));
//            aerosolUsed.setText(clientInstrument.getAerosolUsed());
//            aerosolGeneratorType.setText(clientInstrument.getAerosolGeneratorType());
        } else {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
            make.setText(partnerInstrument.getMake());
            model.setText(partnerInstrument.getModel());
            instrumentSerialNo.setText("" + partnerInstrument.getpInstrumentId());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getLastCalibrationDate()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getCalibrationDueDate()));
//            aerosolUsed.setText(partnerInstrument.getAerosolUsed());
//            aerosolGeneratorType.setText(partnerInstrument.getAerosolGeneratorType());
        }

        testSpecification.setText("Maximum Permiceable leakage " + room.getAcph() + "%");
//                plantName
        areaOfTest.setText(areaName);
        roomName.setText(room.getRoomName());
        occupancyState.setText(room.getOccupancyState().toString());
        Log.d("valdoc", "RDAV5UserEnryActivity 1witness= equipment.getTestReference()=" + room.getTestRef());
        testRefrance.setText("" + room.getTestRef().toString());
//        equipmentNameText.setText(getResources().getString(R.string.room_no));
//        equipmentNoText.setText(getResources().getString(R.string.ahu_no));
        roomNo.setText(room.getRoomNo().toString());
        ahuNo.setText(ahuNumber);
        testCundoctor.setText(userName);
        if (sharedpreferences.getString("USERTYPE", "").equalsIgnoreCase("CLIENT")) {
            testCondoctorOrg.setText("(" + sharedpreferences.getString("CLIENTORG", "") + ")");
            testWitnessOrg.setText("(" + sharedpreferences.getString("CLIENTORG", "") + ")");
        } else {
            testCondoctorOrg.setText("(" + sharedpreferences.getString("PARTNERORG", "") + ")");
            testWitnessOrg.setText("(" + sharedpreferences.getString("CLIENTORG", "") + ")");
        }
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
        instrumentNoTextView = (TextView) findViewById(R.id.instrument_no4);
        instrumentNoTextView.setVisibility(View.GONE);
        testerNameTextView = (TextView) findViewById(R.id.tester_name_test4);
        testerNameTextView.setVisibility(View.GONE);
        instrumentUsedTextView = (TextView) findViewById(R.id.instrument_used);
        instrumentUsedTextView.setVisibility(View.GONE);
        testCunductedByTextView = (TextView) findViewById(R.id.testcunducted_by);
        testCunductedByTextView.setVisibility(View.GONE);
        testerNameTextView = (TextView) findViewById(R.id.tester_name_test4);
        testerNameTextView.setVisibility(View.GONE);
        roomNameLable = (TextView) findViewById(R.id.room_name_lable4);
        roomNameLable.setVisibility(View.GONE);
        instrumentNoLable = (TextView) findViewById(R.id.instrument_no_lable);
        instrumentNoLable.setVisibility(View.GONE);
        roomNameTest = (TextView) findViewById(R.id.room_name4);
        roomNameTest.setVisibility(View.GONE);
        instrument_name = (TextView) findViewById(R.id.instrument_name4);
        instrument_name.setVisibility(View.GONE);

        dateTextView = (TextView) findViewById(R.id.datetextview);
        customerName = (TextView) findViewById(R.id.customer_name);
        certificateNo = (TextView) findViewById(R.id.certificate_no);
        instrumentUsed = (TextView) findViewById(R.id.instrumentused);
        make = (TextView) findViewById(R.id.make);
        model = (TextView) findViewById(R.id.modle);
        aerosolUsed = (TextView) findViewById(R.id.aerosol_used);
        aerosolGeneratorType = (TextView) findViewById(R.id.aerosol_generator_type);
        instrumentSerialNo = (TextView) findViewById(R.id.instrumentserialno);
        calibrationOn = (TextView) findViewById(R.id.calibratedon);
        calibrationDueOn = (TextView) findViewById(R.id.calibrationdueon);
        testSpecification = (TextView) findViewById(R.id.testspecification);
        plantName = (TextView) findViewById(R.id.plantname);
        areaOfTest = (TextView) findViewById(R.id.areaoftest);
        roomName = (TextView) findViewById(R.id.room_name);
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
//        equipmentNameText = (TextView) findViewById(R.id.equipment_name_text);
//        equipmentNoText = (TextView) findViewById(R.id.equipment_no_text);
        roomNo = (TextView) findViewById(R.id.room_no);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        infarance = (TextView) findViewById(R.id.infarance);
        testCundoctor = (TextView) findViewById(R.id.testcunducter);
        testWitness = (TextView) findViewById(R.id.testwitness);
        testCondoctorOrg = (TextView) findViewById(R.id.test_condoctor_org);
        testWitnessOrg = (TextView) findViewById(R.id.testwitness_org);
        submit = (ImageView) findViewById(R.id.submit);
        clear = (ImageView) findViewById(R.id.clear);
        clear.setVisibility(View.INVISIBLE);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mValdocDatabaseHandler.insertTestDetails(ValdocDatabaseHandler.TEST_DETAILS_TABLE_NAME, testDetailsDataCreation())) {
                    if (mValdocDatabaseHandler.insertTestReading(ValdocDatabaseHandler.TESTREADING_TABLE_NAME, testReading())) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("TESTDETAILSID", testDetailsId);
                        editor.commit();

                        Toast.makeText(RDFITUserEntryActivity.this, "Data saved sussessfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RDFITUserEntryActivity.this, TestCreateActivity.class);
                        intent.putExtra(TestCreateActivity.FIT, true);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RDFITUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RDFITUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                }
////                mValdocDatabaseHandler.insertTestSpesificationValue(ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TABLE_NAME, testSpesificationValueDataCreation());
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


    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> testReadingArrayList = new ArrayList<TestReading>();
        int index = 0;
        int hasMapKey = 200;
        int hasStreamBefore = 800;
        int hashstreamAfter = 900;
        int passHasMapKey = 300;
        for (RoomFilter roomFilter : filterArrayList) {

            TestReading testReading = new TestReading();
//            testReading.setTestReadingID(index);
//        TO DO test details id is id of test details table
            testReading.setTest_detail_id(testDetailsId);
            testReading.setEntityName(roomFilter.getFilterCode());
            StringBuilder grilList = new StringBuilder();
            grilList.append(roomFilter.getFilterType()).append(',').append(roomFilter.getEfficiency()).append(",").append(likageDataMap.get(hasStreamBefore)).append(",")
                    .append(likageDataMap.get(hashstreamAfter)).append(",").append(roomFilter.getSpecification()).append(",")
                    .append(likageDataMap.get(hasMapKey)).append(",").append(PassFailHashMap.get(passHasMapKey));
            hasStreamBefore++;
            hashstreamAfter++;
            passHasMapKey++;
            hasMapKey++;
            index++;
            testReading.setValue(grilList.toString());
            testReadingArrayList.add(testReading);
        }
        return testReadingArrayList;
    }


    private TestDetails testDetailsDataCreation() {
        TestDetails testDetails = new TestDetails();
//        TO DO: need to make it dynamic
        testDetails.setTest_detail_id(testDetailsId);
        testDetails.setCustomer(customerName.getText().toString());
        String date = year + "-" + (month + 1) + "-" + day + " ";
        testDetails.setDateOfTest(date);
        testDetails.setRawDataNo(certificateNo.getText().toString());
        testDetails.setPartnerName("" + mPartnerName);
        testDetails.setTestName(TestCreateActivity.FIT);
        if (loginUserType.equals("CLIENT")) {
            testDetails.setInstrumentUsed(clientInstrument.getcInstrumentName());
            testDetails.setMake("" + clientInstrument.getMake());
            testDetails.setModel("" + clientInstrument.getModel());
            testDetails.setInstrumentNo("" + clientInstrument.getSerialNo());
            testDetails.setCalibratedOn("" + clientInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn("" + clientInstrument.getCalibrationDueDate());
            testDetails.setAerosolUsed("" + aerosolUsed.getText());
            testDetails.setAerosolGeneratorType("" + aerosolGeneratorType.getText());
            testDetails.setSamplingFlowRate("");
            testDetails.setSamplingTime("");

        } else {
            testDetails.setInstrumentUsed(""+partnerInstrument.getpInstrumentName());
            testDetails.setMake("" + partnerInstrument.getMake());
            testDetails.setModel("" + partnerInstrument.getModel());
            testDetails.setInstrumentNo("" + partnerInstrument.getpInstrumentId());
            testDetails.setCalibratedOn("" + partnerInstrument.getLastCalibrationDate());
            testDetails.setCalibratedDueOn("" + partnerInstrument.getCalibrationDueDate());
            testDetails.setAerosolUsed("" + aerosolUsed.getText());
            testDetails.setAerosolGeneratorType("" + aerosolGeneratorType.getText());
            testDetails.setSamplingFlowRate("");
            testDetails.setSamplingTime("");
        }

        testDetails.setTestSpecification(testSpecification.getText().toString());
        testDetails.setBlockName(plantName.getText().toString());
        testDetails.setTestArea(areaOfTest.getText().toString());
        testDetails.setRoomName(roomName.getText().toString());
        testDetails.setRoomNo(roomNo.getText().toString());
        testDetails.setOccupencyState(occupancyState.getText().toString());
        testDetails.setTestReference(testRefrance.getText().toString());
        testDetails.setAhuNo(ahuNo.getText().toString());
        testDetails.setTesterName(testCundoctor.getText().toString());
        testDetails.setTestCode(mTestCode);
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testDetails.setWitnessName("" + witness);
        testDetails.setEquipmentName("");
        testDetails.setEquipmentNo("");
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
                filterArrayList = null;
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
                mPartnerName = extras.getString("PRTNERNAME");
                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }

                room = (Room) extras.getSerializable("Room");
                ahuNumber = extras.getString("AhuNumber");
                //take test specification from room filter
                filterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilterList");
                //TO Do testspesification will be shown from room filter spesification

            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
//        headerText.setText("*  Installed HEPA Filter System Leakage Test *");
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Filter No \n         "));
                } else {
                    if (null != filterArrayList && filterArrayList.size() > 0) {
                        RoomFilter roomFilter = filterArrayList.get(i - 2);
                        Log.d("valdoc", "DynamicTableActivity filterArrayList=" + filterArrayList.size() + "i=" + i);
                        row.addView(addTextView(roomFilter.getFilterCode()));
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
                    row.addView(addTextView(" Filter Type  \n         "));
                } else {
                    if (null != filterArrayList && filterArrayList.size() > 0) {
                        RoomFilter roomFilter = filterArrayList.get(i - 2);
                        row.addView(addTextView(roomFilter.getFilterType()));
                    }
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
                    row.addView(addTextView(" Filter Efficiency\n at Particle Size* "));
                } else {
                    if (null != filterArrayList && filterArrayList.size() > 0) {
                        RoomFilter roomFilter = filterArrayList.get(i - 2);
                        row.addView(addTextView(roomFilter.getEfficiency() + "%" + " | " + roomFilter.getParticleSize() + "µm"));
                    }
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Average Up Stream\n Concentration (µg/liter) "));
                } else {
                    row.addView(addInputDataTextViewBeforeStream());
                }

            }
            test4_table_layout4.addView(row);
        }

        //Fifthe section section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Average Up Stream after\nConcentration (µg/liter) "));
                } else {
                    row.addView(addInputDataTextViewAfterStream());
                    //row.addView(addEditTextView(i));
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
                    row.addView(addTextView(" SLP of DL for Tests\n after Installation** "));
                } else {
                    if (null != filterArrayList && filterArrayList.size() > 0) {
                        RoomFilter roomFilter = filterArrayList.get(i - 2);
                        row.addView(addTextView(roomFilter.getSpecification() + "%"));
                    }
                }

            }
            test4_table_layout6.addView(row);
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
                    row.addView(addTextView(" Obtained Test Results\n (% Leakage) "));
                } else {
                    //row.addView(addTextView(" 0.0015 "));
                    row.addView(addInputDataTextView());
                }

            }
            test4_table_layout7.addView(row);
        }

        //Seventh section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Test Status\n    "));
                } else {
                    //row.addView(addTextView(" Pass "));
                    row.addView(addTextPassFail(" ", i));
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

    // Fit before stream
    int idCountEtvBefore = 800;

    private TextView addInputDataTextViewBeforeStream() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setId(idCountEtvBefore);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        idCountEtvBefore++;
        txtViewList.add(tv);
        return tv;
    }


    //Fit After stream
    int idCountEtvAfter = 900;

    private TextView addInputDataTextViewAfterStream() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setId(idCountEtvAfter);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        idCountEtvAfter++;
        txtViewList.add(tv);
        return tv;
    }

    //obtained result
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

    int idPassFailTv = 300;

    private TextView addTextPassFail(String textValue, int tagRows) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setTag(tagRows);
        tv.setId(idPassFailTv);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        idPassFailTv++;
        txtPassFailList.add(tv);
        return tv;
    }

    private void initRes() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
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
        findViewById(R.id.test_table_4_header_2_ll).setVisibility(View.GONE);
    }
}
