package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RDRCTUserEntryActivity extends AppCompatActivity {
    TextView headerText,headerText_2;
    int rows, cols;
    String mTestType;
    private static final String TAG = "RDRCT";
    // bundel data specification
    private String loginUserType = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String ahuNumber;
    private Room room;
//    private ArrayList<HashMap<String, String>> grillAndSizeFromGrill;
    private String areaName;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private String userName = "";
    HashMap<Integer, Integer> inputDataHashMap;
    ArrayList<String> mValueList;
    HashMap<Integer, Integer> mInputValue;

    //certificate view id creation
    private TextView instrumentUsed;
    private TextView instrumentSerialNo;
    private TextView calibrationOn;
    private TextView calibrationDueOn;
    private TextView testSpecification;
    private TextView plantName;
    private TextView areaOfTest;
    private TextView roomName;
    private TextView occupancyState;
    private TextView testRefrance;
    private TextView roomNo;
    private TextView ahuNo;
    private TextView testCundoctor;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;
    private TextView aerosol_gen_rct;
    private TextView recovery_time_tv;
    private TextView aerosol_used_rct_tv;
    //private TextView samplingTime;
    private TextView samplingFlowRate;
    private TableRow samplingFlowTable;
    private TableRow samplingTimeTable;
    private TableRow aerosolGenTable;
    private TableRow recovery_time_rct;
    private TableRow aerosol_used_rct;
    private TextView samplingFlowRateText;
    private TextView samplingTimeText;
    private TextView samplingTime;
    private TextView room_no_lable;

    private TextView equipmentLable;
    private TextView equipmentName;
    private TextView equipmentNoLable;
    private TextView equipmentNo;
    private TextView ahuNoText;
    private TextView instrumentNoTextView;
    private TextView testerNameTextView;
    private TextView instrumentUsedTextView;
    private TextView testCunductedByTextView;
    private TextView roomNameLable;
    private TextView instrumentNoLable;
    private TextView roomNameTest;
    private TextView instrument_name;
    private TextView cleanRoomClass;
    private TextView testspecificationText;
    private TextView initialReading;
    private TextView worstCase;
    private TextView finalReading;
    private TextView recoveryTime;

    ArrayList<TextView> txtViewList;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;

    private String[] roomDetails;
    private Equipment equipment;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ApplicableTestEquipment mApplicableTestEquipment = null;

    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDRCTUserEntryActivity.this);
    SharedPreferences sharedpreferences;
    int testDetailsId=0;
    String mInitialReading;
    String mWorstReading;
    String mFinalReading;
    int mCount;
    private String mPartnerName;
    private String  mTestCode="";
    private String testType;
    private String mTestBasedOn;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdrctuser_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mInputValue = new HashMap<Integer, Integer>();
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0)+1);

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            mTestType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + mTestType);
        }
        mTestCode = getIntent().getStringExtra("testCode");
        mTestBasedOn = getIntent().getStringExtra("testBasedOn");
        testType = getIntent().getStringExtra("testType");
        //dynamic data population
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        textViewValueAssignment();
        //initRes();
        datePicker();
//        if ("RD_PC_3".equalsIgnoreCase(testType)) {
//            BuildTableTest5(rows, cols);
//        }

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDRCTUserEntryActivity.this, mActionBar, userName);
    }


//    private void initRes(){
//        headerText = (TextView)findViewById(R.id.common_header_tv);
//        headerText.setText("* Airborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices *");
//        //Test5
//        test5_table_layout = (TableLayout)findViewById(R.id.test5_tableLayout1);
//        test5_table_layout2 = (TableLayout)findViewById(R.id.test5_tableLayout2);
//        test5_table_layout3 = (TableLayout)findViewById(R.id.test5_tableLayout3);
//        test5_table_layout4 = (TableLayout)findViewById(R.id.test5_tableLayout4);
//        test5_table_layout5 = (TableLayout)findViewById(R.id.test5_tableLayout5);
//            findViewById(R.id.test_table_5_header_l_ll).setVisibility(View.GONE);
//            findViewById(R.id.test_table_5_header_2_ll).setVisibility(View.GONE);
//    }

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
        certificateNo.setText("CT/" + mon + "/" + year + "/" + formattedDate);

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
            instrumentSerialNo.setText("" + clientInstrument.getSerialNo());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getLastCalibrated()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getCalibrationDueDate()));
            samplingFlowRate.setText(clientInstrument.getRange());
//            samplingTime.setText(clientInstrument.getSamplingTime());
        } else {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
            //make.setText(partnerInstrument.getMake());
            //model.setText(partnerInstrument.getModel());
            instrumentSerialNo.setText("" + partnerInstrument.getpInstrumentId());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getLastCalibrationDate()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getCalibrationDueDate()));
            samplingFlowRate.setText(partnerInstrument.getRange());
//            samplingTime.setText(partnerInstrument.getSamplingTime());
        }

        initialReading.setText("" + mInitialReading);
        worstCase.setText("" + mWorstReading);
        finalReading.setText("" + mFinalReading);
        if (mCount > 0) {
            int count = mCount + 1;
            recoveryTime.setText("" + count);
        }
        areaOfTest.setText(areaName);
        samplingFlowRateText.setText("Sampling Flow Rate :");
        samplingTimeText.setText("Sampling Time :");
        if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            roomName.setText(room.getRoomName());
            testRefrance.setText(mApplicableTestRoom.getTestReference());
            roomNo.setText(room.getRoomNo().toString());
            ahuNo.setText(ahuNumber);
            occupancyState.setText(mApplicableTestRoom.getOccupencyState().toString());
            String samplingtime = getSamplingTime(mApplicableTestRoom.getTestSpecification(), "");
            samplingTime.setText("" + samplingtime);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mApplicableTestRoom.getTestProp());
                    recovery_time_tv.setText("<= "+jsonObject.optString("ACCEPTABLE_RECOVERY_TIME")+" min");
                }catch(Exception e){
                recovery_time_tv.setText("");
                }
//            samplingFlowRate.setText("under development");
            cleanRoomClass.setText(" " + mApplicableTestRoom.getTestSpecification());
        } else if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            equipmentName.setText(equipment.getEquipmentName());
            equipmentNo.setText(equipment.getEquipmentNo());
            occupancyState.setText(mApplicableTestEquipment.getOccupencyState());
            testRefrance.setText(mApplicableTestEquipment.getTestReference());
            roomName.setText(roomDetails[1]);
            // room no not needed
            roomNo.setText(roomDetails[2]);
            areaOfTest.setText(areaName);
            String samplingtime = getSamplingTime(mApplicableTestEquipment.getTestSpecification(), "");
            samplingTime.setText("" + samplingtime);
            cleanRoomClass.setText("" + mApplicableTestEquipment.getTestSpecification());
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mApplicableTestEquipment.getTestProp());
                recovery_time_tv.setText("<= "+jsonObject.optString("ACCEPTABLE_RECOVERY_TIME")+" min");
            }catch(Exception e){
                recovery_time_tv.setText("");
            }
        }

//        testSpecification.setText("" + room.getAcph());
//                plantName

//        roomName.setText(room.getRoomName());
//        occupancyState.setText(room.getOccupancyState().toString());
//        Log.d("valdoc", "RDAV5UserEnryActivity 1witness= equipment.getTestReference()=" + room.getTestRef());
//        testRefrance.setText("" + room.getTestRef().toString());
//        roomNo.setText(room.getRoomNo().toString());
//        ahuNo.setText(ahuNumber);
        testCundoctor.setText(userName);
        if(sharedpreferences.getString("USERTYPE", "").equalsIgnoreCase("CLIENT")){
            testCondoctorOrg.setText("("+sharedpreferences.getString("CLIENTORG", "")+")");
            testWitnessOrg.setText("("+sharedpreferences.getString("CLIENTORG", "")+")");
            customerName.setText(""+ sharedpreferences.getString("CLIENTORG", ""));
        }else{
            testCondoctorOrg.setText("("+sharedpreferences.getString("PARTNERORG", "")+")");
            testWitnessOrg.setText("("+sharedpreferences.getString("CLIENTORG", "")+")");
            customerName.setText(""+ sharedpreferences.getString("PARTNERORG", ""));
        }
        plantName.setText("from config screen");
        Log.d("valdoc", "RDAV5UserEnryActivity 1witness=" + witnessFirst);
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testWitness.setText(witness);
    }

    private String getSamplingTime(String testSpecification, String range) {
        String samplingTime = "";
        testSpecification = " ";
        if (testSpecification.contains("5") || testSpecification.contains("6")) {
            if (range.contains("28.3")) {
                samplingTime = "36 Minute";
            } else if (range.contains("50")) {
                samplingTime = "20 Minute";
            } else if (range.contains("75")) {
                samplingTime = "14 Minute";
            } else if (range.contains("100")) {
                samplingTime = "10 Minute";
            }
        } else {
            samplingTime = "1 Minute";
        }
        return samplingTime;
    }

    private void initTextView() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
        headerText_2 = (TextView) findViewById(R.id.common_header_2_tv);
        headerText_2.setVisibility(View.VISIBLE);
        headerText.setText("TEST RAW DATA EQUIPMENT");
        headerText_2.setText("Recovery Performance Test");
        if("ROOM".equalsIgnoreCase(mTestBasedOn))
            headerText.setText("TEST RAW DATA ");
        // layout data which is not in use
        instrumentNoTextView = (TextView) findViewById(R.id.instrument_no6);
//        instrumentNoTextView.setVisibility(View.GONE);
        testerNameTextView = (TextView) findViewById(R.id.tester_name_test6);
//        testerNameTextView.setVisibility(View.GONE);
        instrumentUsedTextView = (TextView) findViewById(R.id.instrument_used);
//        instrumentUsedTextView.setVisibility(View.GONE);
        testCunductedByTextView = (TextView) findViewById(R.id.testcunducted_by);
//        testCunductedByTextView.setVisibility(View.GONE);

        samplingFlowTable = (TableRow) findViewById(R.id.aerosol_generator_table);
        samplingFlowTable.setVisibility(View.VISIBLE);
        samplingTimeTable = (TableRow) findViewById(R.id.aerosol_used_table);
        samplingTimeTable.setVisibility(View.VISIBLE);

        aerosolGenTable = (TableRow)findViewById(R.id.aerosol_gen_table_rct);
        aerosolGenTable.setVisibility(View.VISIBLE);
        aerosol_gen_rct = (TextView) findViewById(R.id.aerosol_gen_rct);

        recovery_time_rct = (TableRow)findViewById(R.id.recovery_time_rct);
        recovery_time_rct.setVisibility(View.VISIBLE);
        recovery_time_tv = (TextView) findViewById(R.id.recovery_time_tv);

        aerosol_used_rct = (TableRow)findViewById(R.id.aerosol_used_rct);
        aerosol_used_rct.setVisibility(View.VISIBLE);
        aerosol_used_rct_tv = (TextView) findViewById(R.id.aerosol_used_rct_tv);

        samplingFlowRateText= (TextView) findViewById(R.id.aerosol_generator_type_text);
        samplingTimeText= (TextView) findViewById(R.id.aerosol_used_text);
        samplingFlowRate = (TextView) findViewById(R.id.aerosol_generator_type_value);
        samplingTime = (TextView) findViewById(R.id.aerosol_used);

        testspecificationText=(TextView)findViewById(R.id.testspecification_text);
        testspecificationText.setText("Cleanroom Class :");
        cleanRoomClass = (TextView) findViewById(R.id.testspecification);

        initialReading = (TextView) findViewById(R.id.initial_reading);
        worstCase = (TextView) findViewById(R.id.worst_case);
        finalReading = (TextView) findViewById(R.id.final_reading);
        recoveryTime = (TextView) findViewById(R.id.recovery_time);

        dateTextView = (TextView) findViewById(R.id.datetextview);
        customerName = (TextView) findViewById(R.id.customer_name);
        certificateNo = (TextView) findViewById(R.id.trd_no);
        instrumentUsed = (TextView) findViewById(R.id.instrumentused);
        room_no_lable=(TextView)findViewById(R.id.room_no_lable);
        roomNo = (TextView) findViewById(R.id.room_no);
        if(mTestBasedOn.equalsIgnoreCase("ROOM")){
            equipmentLable = (TextView) findViewById(R.id.equiment_name_text);
            equipmentLable.setVisibility(View.INVISIBLE);
            equipmentNoLable = (TextView) findViewById(R.id.equiment_no_text);
            equipmentNoLable.setVisibility(View.INVISIBLE);
            findViewById(R.id.ahu_no_lable).setVisibility(View.VISIBLE);
        }

        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            equipmentLable = (TextView) findViewById(R.id.equiment_name_text);
            equipmentLable.setVisibility(View.VISIBLE);
            equipmentName = (TextView) findViewById(R.id.equiment_name);
            equipmentName.setVisibility(View.VISIBLE);
            equipmentNoLable = (TextView) findViewById(R.id.equiment_no_text);
            equipmentNoLable.setVisibility(View.VISIBLE);
            equipmentNo = (TextView) findViewById(R.id.equiment_no);
            equipmentNo.setVisibility(View.VISIBLE);
            roomNo.setVisibility(View.GONE);
            room_no_lable.setVisibility(View.GONE);
        }

        instrumentSerialNo = (TextView) findViewById(R.id.instrumentserialno);
        calibrationOn = (TextView) findViewById(R.id.calibratedon);
        calibrationDueOn = (TextView) findViewById(R.id.calibrationdueon);
        testSpecification = (TextView) findViewById(R.id.testspecification);
        plantName = (TextView) findViewById(R.id.plantname);
        areaOfTest = (TextView) findViewById(R.id.areaoftest);
        roomName = (TextView) findViewById(R.id.room_name);
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);

        ahuNo = (TextView) findViewById(R.id.ahu_no);
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
                        if (mValdocDatabaseHandler.insertTestSpesificationValue(ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TABLE_NAME, testSpesificationValue())) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("TESTDETAILSID", testDetailsId);
                            editor.commit();

                            Toast.makeText(RDRCTUserEntryActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RDRCTUserEntryActivity.this, TestCreateActivity.class);
                            intent.putExtra(TestCreateActivity.RCT, true);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RDRCTUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RDRCTUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RDRCTUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                }

//                mValdocDatabaseHandler.insertTestSpesificationValue(ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TABLE_NAME, testSpesificationValueDataCreation());
            }
        });

//        dateTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // On button click show datepicker dialog
//                showDialog(DATE_PICKER_ID);
//            }
//        });
        findViewById(R.id.common_certificate_header_ll).setVisibility(View.VISIBLE);
    }

    private ArrayList<TestSpesificationValue> testSpesificationValue() {
        ArrayList<TestSpesificationValue> spesificationValueArrayList = new ArrayList<TestSpesificationValue>();
        TestSpesificationValue testSpesificationValue = new TestSpesificationValue();
//        testSpesificationValue.setTest_specific_id(1);
        testSpesificationValue.setTest_detail_id(""+testDetailsId);
        testSpesificationValue.setFieldName("Initial Reading");
        testSpesificationValue.setFieldValue("" + initialReading.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue);

        TestSpesificationValue testSpesificationValue1 = new TestSpesificationValue();
//        testSpesificationValue1.setTest_specific_id(1);
        testSpesificationValue1.setTest_detail_id(""+testDetailsId);
        testSpesificationValue1.setFieldName("Worst Case Reading");
        testSpesificationValue1.setFieldValue("" + worstCase.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue1);

        TestSpesificationValue testSpesificationValue2 = new TestSpesificationValue();
//        testSpesificationValue2.setTest_specific_id(1);
        testSpesificationValue2.setTest_detail_id(""+testDetailsId);
        testSpesificationValue2.setFieldName("Final Reading");
        testSpesificationValue2.setFieldValue("" + finalReading.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue2);

        TestSpesificationValue testSpesificationValue3 = new TestSpesificationValue();
//        testSpesificationValue3.setTest_specific_id(1);
        testSpesificationValue3.setTest_detail_id(""+testDetailsId);
        testSpesificationValue3.setFieldName("Recovery Time");
        testSpesificationValue3.setFieldValue("" + recoveryTime.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue3);
        return spesificationValueArrayList;
    }

    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> testReadingArrayList = new ArrayList<TestReading>();
//        for (int i = 0; i < mValueList.size(); i++) {
        TestReading testReading = new TestReading();
//        testReading.setTestReadingID(1);
//        TO DO test details id is id of test details table
        testReading.setTest_detail_id(testDetailsId);
        testReading.setEntityName("RecoveryTest");
        StringBuilder grilList = new StringBuilder();
        //v1,v2....value cration
        StringBuilder sb = new StringBuilder();
        int k = 202;
        int size=mInputValue.size();
        for (int j = 2; j < size; j++) {
            if (j != 2)
                sb.append(',');
            sb.append(mInputValue.get(k).toString());
            k++;

        }
//        sb.append("," + mFinalReading.trim());
        testReading.setValue("" + sb);
        testReadingArrayList.add(testReading);
        return testReadingArrayList;
    }

    private TestDetails testDetailsDataCreation() {
        TestDetails testDetails = new TestDetails();
//        TO DO: need to make it dynamic
        testDetails.setTest_detail_id(testDetailsId);
        testDetails.setCustomer(customerName.getText().toString());
        String date = year+"-"+(month + 1)+"-"+day+" ";
//        testDetails.setDateOfTest(""+date);
        testDetails.setDateOfTest("" + dateTextView.getText());
        testDetails.setRawDataNo(certificateNo.getText().toString());
        testDetails.setTestName(TestCreateActivity.RCT);
        testDetails.setDiffAVinFilter(0);
        testDetails.setDiffAVbetweenFilter(0);
        if (loginUserType.equals("CLIENT")) {
            testDetails.setInstrumentUsed(clientInstrument.getcInstrumentName());
            testDetails.setMake(clientInstrument.getMake());
            testDetails.setModel(clientInstrument.getModel());
            testDetails.setInstrumentNo(""+clientInstrument.getSerialNo());
            testDetails.setCalibratedOn("" + clientInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn("" + clientInstrument.getCalibrationDueDate());
            testDetails.setAerosolUsed("");
            testDetails.setAerosolGeneratorType("");
           // testDetails.setSamplingFlowRate("" + samplingFlowRate.getText().toString());
           // testDetails.setSamplingTime("" + samplingTime.getText().toString());
        } else {
            testDetails.setInstrumentUsed(partnerInstrument.getpInstrumentName());
            testDetails.setMake(partnerInstrument.getMake());
            testDetails.setModel(partnerInstrument.getModel());
            testDetails.setInstrumentNo("" + partnerInstrument.getpInstrumentId());
            testDetails.setCalibratedOn("" + partnerInstrument.getLastCalibrationDate());
            testDetails.setCalibratedDueOn("" + partnerInstrument.getCalibrationDueDate());
            testDetails.setAerosolUsed("");
            testDetails.setAerosolGeneratorType("");
            //testDetails.setSamplingFlowRate("" + samplingFlowRate.getText().toString());
           // testDetails.setSamplingTime("" + samplingTime.getText().toString());
        }
        testDetails.setFilterTypeEficiancy("");
        testDetails.setTestLocation("");
        testDetails.setSamplingFlowRate("" + samplingFlowRate.getText().toString());
        testDetails.setSamplingTime("" + samplingTime.getText().toString());
        testDetails.setTestSpecification(testSpecification.getText().toString());
        testDetails.setBlockName(plantName.getText().toString());
        testDetails.setTestArea(areaOfTest.getText().toString());
        testDetails.setRoomName(roomName.getText().toString());
        testDetails.setRoomNo(roomNo.getText().toString());
        testDetails.setOccupencyState(occupancyState.getText().toString());
        testDetails.setTestReference(testRefrance.getText().toString());
//        testDetails.setAhuNo(ahuNo.getText().toString());
        testDetails.setTesterName(testCundoctor.getText().toString());
        testDetails.setPartnerName("" + mPartnerName);
        testDetails.setTestCode(mTestCode);
        testDetails.setTestSpecification(""+cleanRoomClass.getText().toString());
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testDetails.setTestItem("");
        testDetails.setWitnessName("" + witness);
        testDetails.setTestWitnessOrg("" + testWitnessOrg.getText());
        testDetails.setTestCondoctorOrg("" + testCondoctorOrg.getText());
        testDetails.setRoomVolume("");
        testDetails.setAcceptableRecoveryTime(""+recovery_time_tv.getText().toString()+"");
        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            testDetails.setEquipmentName("" + equipmentName.getText().toString());
            testDetails.setEquipmentNo("" + equipmentNo.getText().toString());
            testDetails.setAhuNo("");
        } else {
            testDetails.setEquipmentName("");
            testDetails.setEquipmentNo("");
            testDetails.setAhuNo(ahuNo.getText().toString());
        }
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
//                grillAndSizeFromGrill = null;
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
                Log.d("Avinash", "RDRCT mPartnerName=" + mPartnerName);
                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }

                if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                    roomDetails = extras.getStringArray("RoomDetails");
                    equipment = (Equipment) extras.getSerializable("Equipment");
                    areaName = extras.getString("AREANAME");
                    mApplicableTestEquipment = (ApplicableTestEquipment) extras.getSerializable("ApplicableTestEquipment");
                } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                }
//                room = (Room) extras.getSerializable("Room");
//                ahuNumber = extras.getString("AhuNumber");
//                grillAndSizeFromGrill = (ArrayList<HashMap<String, String>>) extras.getSerializable("GRILLIST");

                mInitialReading = extras.getString("InitialReading");
                mWorstReading = extras.getString("WorstCaseReading");
                mFinalReading = extras.getString("FinalReading");
                mCount = extras.getInt("RecoveryTime");
                mValueList = extras.getStringArrayList("VALUE");
                mInputValue = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("InputData");
                int j = 200;
                for (int i = 0; i < mInputValue.size(); i++) {
                    Log.d("Avinash", "test value" + mInputValue.get(j));
                    j++;
                }

            }
        }


    }
}
