package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Grill;
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

public class RDACPHhUserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDACPHhUser";
    TableLayout test3_table_layout, test3_table_layout2, test3_table_layout3, test3_table_layout4,
            test3_table_layout5;

    int rows, cols;
    String mTestType;
    ProgressDialog pr;
    ArrayList<TextView> roomVolumeTxtList;
    ArrayList<TextView> totalAirFlowRateTxtList;
    ArrayList<TextView> airChangeTxtList;
    int totalAirFlowRateIds = 400;

    // bundel data specification
    private String loginUserType = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String ahuNumber;
    private Room room;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ArrayList<RoomFilter> mRoomFilterArrayList;
    private ArrayList<Grill> grillAndSizeFromGrill;
    private String areaName;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private String userName = "";

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
    private TextView ahuNoText;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    private TextView equipmentNameText;
    private TextView equipmentNoText;
    private TextView testCundoctor;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;
    private TextView TFRtv;
    private TextView TFTByRvTv;
    private TextView roomVolume;
    private TextView roomVolumeText;
    private TextView instrumentUsedTextView;
    private TextView testCunductedByTextView;
    private TextView roomNameLable;
    private TextView instrumentNoLable;

    ArrayList<TextView> txtViewList;
    ArrayList<TextView> resultTextViewList;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;
    private String mPartnerName;
    private double totalAirFlowRate = 0;
    private double airChangeValue;
    HashMap<Integer, Integer> supplyAirVelocity;
    HashMap<Integer, Long> averageDataHashMap;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDACPHhUserEntryActivity.this);
    SharedPreferences sharedpreferences;
    int testDetailsId = 0;
    private int year;
    private int month;
    private int day;
    private String mTestCode = "";
    private String mTestBasedOn;
    private String mGrilFilterType;
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdacphh_user_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
        pr.setCanceledOnTouchOutside(true);
        pr.setCancelable(true);

        initRes();

        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();
        roomVolumeTxtList = new ArrayList<TextView>();
        totalAirFlowRateTxtList = new ArrayList<TextView>();
        airChangeTxtList = new ArrayList<TextView>();

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            mTestType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + mTestType);
        }
        mGrilFilterType = getIntent().getStringExtra("GrilFilterType");
        mTestCode = getIntent().getStringExtra("testCode");
        //dynamic data population
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        textViewValueAssignment();
        datePicker();

        //Creating Tables based on rows and columns
        if (rows > 0 && cols > 0) {
            BuildTableTest3(rows, cols);
        }else {
            Utilityies.showAlert(RDACPHhUserEntryActivity.this, "NO Grill or Fillter Data");
        }

        //setting the test 2 room volume
        if (roomVolumeTxtList != null && roomVolumeTxtList.size() > 0)
            roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("" + room.getVolume());

        //Receiving User Input Data from Bundle
        supplyAirVelocity = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("InputData");
        averageDataHashMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("ResultData");
        for (Map.Entry m : supplyAirVelocity.entrySet()) {
            Log.v(TAG, m.getKey() + " " + m.getValue());
        }
        for (Map.Entry k : averageDataHashMap.entrySet()) {
            Log.v(TAG, k.getKey() + " Avg. Data " + k.getValue());
        }
        for (int i = 0; i < txtViewList.size(); i++) {
            TextView tvl = txtViewList.get(i);
            tvl.setText(supplyAirVelocity.get(tvl.getId()) + "");
        }
        for (int i = 0; i < resultTextViewList.size(); i++) {
            //Average
            TextView avgTvl = resultTextViewList.get(i);
            avgTvl.setText(averageDataHashMap.get(avgTvl.getId()) + "");
            Log.v(TAG, "resultTextViewList Id "+avgTvl.getId() + " Avg. Values " + averageDataHashMap.get(avgTvl.getId()));
        }

        int variation = mApplicableTestRoom.getDiffAVbetweenFilter();
        Log.d(TAG, "AxAv_H Validation value: " + variation);
        if(variation != 0){
            getAxAvDataValidationByAverage(variation);
        }

        totalAirFlowRate = getResultAverageData(averageDataHashMap);
        TFRtv.setText("" + Math.round(totalAirFlowRate));

        airChangeValue = getTFTBy60Value(totalAirFlowRate);
        TFTByRvTv.setText("" + Math.round(airChangeValue));

        Boolean textColor = passFailCheckForTfrBy60(
                Double.parseDouble(mApplicableTestRoom.getTestSpecification()),airChangeValue);
        if(textColor){
            TFTByRvTv.setTextColor(Color.BLUE);
        }else{
            TFTByRvTv.setTextColor(Color.RED);
        }

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDACPHhUserEntryActivity.this, mActionBar, userName);

    }

    private double getTFTBy60Value(double totalAirFlowRate) {
        double tftBy60Value = 0.0d;
        double roomVolume = room.getVolume();
        if(roomVolume > 0){
            tftBy60Value = (totalAirFlowRate/roomVolume)*60;
        }
        Log.d(TAG, "TFRBy60_Value : "+tftBy60Value);
        return tftBy60Value;
    }

    private long getResultAverageData(HashMap<Integer, Long> averageDataHashMap) {
        long tfrValue = 0l;
        for (Map.Entry k : averageDataHashMap.entrySet()) {
            tfrValue += (long)k.getValue();
        }
        Log.d(TAG, "TFR_Value : "+tfrValue);
        return tfrValue;
    }

    private boolean passFailCheckForTfrBy60(double testSpesification, double totalairflowrate) {
        Log.d(TAG, "Test Specification : "+testSpesification);
        if(totalairflowrate > testSpesification){
            return true;
        }else{
            return false;
        }
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
//        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        int mon = month + 1;
        certificateNo.setText("HH/" + mon + "/" + year + "/" + formattedDate);

        // Show current date
        String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
        dateTextView.setText(date);
//        new StringBuilder().append(year)
//                .append("-").append(month + 1).append("-").append(day)
//                .append(" "));
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
        if (loginUserType.equalsIgnoreCase("PARTNER")) {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
            instrumentSerialNo.setText("" + partnerInstrument.getpInstrumentId());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getLastCalibrationDate()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getCalibrationDueDate()));
        } else {
            instrumentUsed.setText(clientInstrument.getcInstrumentName());
            instrumentSerialNo.setText("" + clientInstrument.getSerialNo());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getLastCalibrated()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getCalibrationDueDate()));

        }

        testSpecification.setText("Specified Air Change/hr NLT " + room.getAcph());
//                plantName
        areaOfTest.setText(areaName);
        roomName.setText(room.getRoomName());
        occupancyState.setText(mApplicableTestRoom.getOccupencyState().toString());
        Log.d("valdoc", "RDAV5UserEnryActivity 1witness= equipment.getTestReference()=" + room.getTestRef());
        testRefrance.setText("" + mApplicableTestRoom.getTestReference().toString());
//        equipmentNo.setText(room.getRoomNo().toString());
        roomNo.setText(room.getRoomNo().toString());
        roomVolume.setText("" + room.getVolume());
        ahuNo.setText(ahuNumber);
//        equipmentNameText.setText(getResources().getString(R.string.room_no));
//        equipmentNoText.setText(getResources().getString(R.string.ahu_no));


        testCundoctor.setText(userName);
        String clientOrg = sharedpreferences.getString("CLIENTORG", "");
        String prtnerOrg = sharedpreferences.getString("PARTNERORG", "");
        if (sharedpreferences.getString("USERTYPE", "").equalsIgnoreCase("PARTNER")) {
            testCondoctorOrg.setText("(" + prtnerOrg + ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText(""+prtnerOrg);
        } else {
            testCondoctorOrg.setText("(" +clientOrg+ ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText(""+ clientOrg);
        }
         plantName.setText(""+clientOrg);
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
        // instrumentUsedTextView = (TextView) findViewById(R.id.instrument_used);
        // instrumentUsedTextView.setVisibility(View.GONE);
        // testCunductedByTextView = (TextView) findViewById(R.id.testcunducted_by);
        //testCunductedByTextView.setVisibility(View.GONE);

        // roomNameLable= (TextView) findViewById(R.id.room_name_lable);
        //roomNameLable.setVisibility(View.GONE);
        //instrumentNoLable= (TextView) findViewById(R.id.instrument_no_lable);
        //instrumentNoLable.setVisibility(View.GONE);

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

                            Toast.makeText(RDACPHhUserEntryActivity.this, "Data saved sussessfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RDACPHhUserEntryActivity.this, TestCreateActivity.class);
                            intent.putExtra(TestCreateActivity.ACPHH, true);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RDACPHhUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RDACPHhUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RDACPHhUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                }

//                mValdocDatabaseHandler.insertTestSpesificationValue(ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TABLE_NAME, testSpesificationValueDataCreation());
            }
        });

    }

    private ArrayList<TestSpesificationValue> testSpesificationValue() {
        ArrayList<TestSpesificationValue> spesificationValueArrayList = new ArrayList<TestSpesificationValue>();
        TestSpesificationValue testSpesificationValue = new TestSpesificationValue();
//        testSpesificationValue.setTest_specific_id(1);
        testSpesificationValue.setTest_detail_id("" + testDetailsId);
        testSpesificationValue.setFieldName("TFR");
        testSpesificationValue.setFieldValue("" + Math.round(totalAirFlowRate));
        spesificationValueArrayList.add(testSpesificationValue);

        TestSpesificationValue testSpesificationValue1 = new TestSpesificationValue();
//        testSpesificationValue1.setTest_specific_id(1);
        testSpesificationValue1.setTest_detail_id("" + testDetailsId);
        testSpesificationValue1.setFieldName("RV");
        testSpesificationValue1.setFieldValue("" + room.getVolume());
        spesificationValueArrayList.add(testSpesificationValue1);

        TestSpesificationValue testSpesificationValue2 = new TestSpesificationValue();
//        testSpesificationValue2.setTest_specific_id(1);
        testSpesificationValue2.setTest_detail_id("" + testDetailsId);
        testSpesificationValue2.setFieldName("((TFR/RV)x60))");
        testSpesificationValue2.setFieldValue("" + Math.round(airChangeValue));
        spesificationValueArrayList.add(testSpesificationValue2);

        return spesificationValueArrayList;
    }


    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> testReadingArrayList = new ArrayList<TestReading>();
        int index = 0;
        int hasMapKey = 200;
        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
            for (Grill grill : grillAndSizeFromGrill) {
                TestReading testReading = new TestReading();
//            testReading.setTestReadingID(index);
//        TO DO test details id is id of test details table
                testReading.setTest_detail_id(testDetailsId);
                testReading.setEntityName(grill.getGrillCode().toString());
                testReading.setValue(supplyAirVelocity.get(hasMapKey).toString());
                hasMapKey++;
                index++;
                testReadingArrayList.add(testReading);
            }
        } else {
            for (RoomFilter roomFilter : mRoomFilterArrayList) {
                TestReading testReading = new TestReading();
//            testReading.setTestReadingID(index);
//        TO DO test details id is id of test details table
                testReading.setTest_detail_id(testDetailsId);
                testReading.setEntityName(roomFilter.getFilterCode().toString());
                testReading.setValue(supplyAirVelocity.get(hasMapKey).toString());
                hasMapKey++;
                index++;
                testReadingArrayList.add(testReading);
            }
        }
        return testReadingArrayList;
    }

    private TestDetails testDetailsDataCreation() {
        TestDetails testDetails = new TestDetails();
//        TO DO: need to make it dynamic
        testDetails.setTest_detail_id(testDetailsId);
        testDetails.setCustomer(customerName.getText().toString());
        String date = year + "-" + (month + 1) + "-" + day + " ";
//        testDetails.setDateOfTest("" + date);
        testDetails.setDateOfTest("" + dateTextView.getText());
        testDetails.setRawDataNo(certificateNo.getText().toString());
        testDetails.setPartnerName("" + mPartnerName);
        testDetails.setTestName(mTestCode);
        testDetails.setFilterTypeEficiancy("");
        testDetails.setTestLocation("");
        testDetails.setAcceptableRecoveryTime("");
        if (loginUserType.equals("CLIENT")) {
            testDetails.setInstrumentUsed(clientInstrument.getcInstrumentName());
            testDetails.setMake(clientInstrument.getMake());
            testDetails.setModel(clientInstrument.getModel());
            testDetails.setInstrumentNo(clientInstrument.getSerialNo());
            testDetails.setCalibratedOn(clientInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn(clientInstrument.getCalibrationDueDate());
            testDetails.setSamplingFlowRate("");
            testDetails.setSamplingTime("");
        } else {
            testDetails.setInstrumentUsed(partnerInstrument.getpInstrumentName());
            testDetails.setMake(partnerInstrument.getMake());
            testDetails.setModel(partnerInstrument.getModel());
            testDetails.setInstrumentNo("" + partnerInstrument.getpInstrumentId());
            testDetails.setCalibratedOn(partnerInstrument.getLastCalibrationDate());
            testDetails.setCalibratedDueOn(partnerInstrument.getCalibrationDueDate());
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
        testDetails.setDiffAVinFilter(mApplicableTestRoom.getDiffAVinFilter());
        testDetails.setDiffAVbetweenFilter(mApplicableTestRoom.getDiffAVbetweenFilter());
        testDetails.setSamplingFlowRate("");
        testDetails.setSamplingTime("");
        testDetails.setAerosolGeneratorType("");
        testDetails.setAerosolUsed("");
        testDetails.setTestItem("");
        testDetails.setRoomVolume("" + roomVolume.getText());
        testDetails.setTestWitnessOrg("" + testWitnessOrg.getText());
        testDetails.setTestCondoctorOrg("" + testCondoctorOrg.getText());
        testDetails.setTolarance("");
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

                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }

                room = (Room) extras.getSerializable("Room");
                ahuNumber = extras.getString("AhuNumber");
//                grillAndSizeFromGrill = (ArrayList<HashMap<String, String>>) extras.getSerializable("GRILLIST");
                if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                    grillAndSizeFromGrill = (ArrayList<Grill>) extras.getSerializable("GRILLLIST");
                } else {
                    mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilter");
                }
                mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                mTestCode = extras.getString("testCode");
                mTestBasedOn = extras.getString("testBasedOn");
                //get area based on room area id

            }
        }


    }

    private void BuildTableTest3(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    TextView grillTV = addTextView(" Grill/Filter No ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height_h);
                    grillTV.setLayoutParams(params);
                    grillTV.setEms(8+Utilityies.getPctCellWidth(cols));
                    row.addView(grillTV);
                } else {

                    if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                        if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                            TextView textView = addTextView(grillAndSizeFromGrill.get(i - 2).getGrillCode().toString());
                            textView.setEms(8+Utilityies.getPctCellWidth(cols));
                            row.addView(textView);
                        } else {
                            row.addView(addTextView("grillAndSizeFromGrill"));
                        }

                    } else {
                        if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
                            TextView textView = addTextView(mRoomFilterArrayList.get(i - 2).getFilterCode());
                            textView.setEms(8+Utilityies.getPctCellWidth(cols));
                            row.addView(textView);
                        } else {
                            row.addView(addTextView("grillAndSizeFromGrill"));
                        }

                    }

                }
            }
            test3_table_layout.addView(row);
        }

        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    TextView textView = addTextView(" Q " + j);
                    if(cols>4)
                        textView.setEms(4+Utilityies.getPctCellWidth(cols));
                    else
                        textView.setEms(8+Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
                } else {
                    TextView editText = addInputDataTextView();
                    if(cols>4)
                        editText.setEms(4+Utilityies.getPctCellWidth(cols));
                    else
                        editText.setEms(8+Utilityies.getPctCellWidth(cols));
                    row.addView(editText);
                }
            }
            test3_table_layout2.addView(row);
        }

        //Third section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
                if (i == 1) {
                    TextView textView = addTextView(" Average ");
                    if(cols>4)
                        textView.setEms(7+Utilityies.getPctCellWidth(cols));
                    else
                        textView.setEms(9+Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
                }else{
                    TextView textView = addResultTextView(i);
                    if(cols>4)
                        textView.setEms(7+Utilityies.getPctCellWidth(cols));
                    else
                        textView.setEms(9+Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
                }
            test3_table_layout3.addView(row);
        }

        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();


    }


    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        ViewGroup.LayoutParams params = tv.getLayoutParams();
        params.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
        tv.setLayoutParams(params);
        tv.setPadding(15,0,15,10);
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));

        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    int idCountEtv = 200;

    private TextView addInputDataTextView() {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        ViewGroup.LayoutParams params = tv.getLayoutParams();
        params.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(params);

        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
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

    int idAvgtv = 400;
    private TextView addResultTextView(int rowsNo) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        ViewGroup.LayoutParams params = tv.getLayoutParams();
        params.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(params);

        tv.setBackgroundResource(R.drawable.border1);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, " idCountTv " + idAvgtv);
        tv.setId(idAvgtv);
        tv.setTag(rowsNo);
        idAvgtv++;
        resultTextViewList.add(tv);
        return tv;
    }

    private TextView addTextViewWithIdsNoBorder(int Tag, int ids, ArrayList<TextView> txtViewList) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, "No Border idCountTv " + ids);
        if (ids > 0)
            tv.setId(ids);
        tv.setTag(Tag);
        ids++;
        txtViewList.add(tv);
        return tv;
    }

    private void initRes() {
        //Test3
        test3_table_layout = (TableLayout) findViewById(R.id.test3_tableLayout1);
        test3_table_layout2 = (TableLayout) findViewById(R.id.test3_tableLayout2);
        test3_table_layout3 = (TableLayout) findViewById(R.id.test3_tableLayout3);
        findViewById(R.id.room_h_test_tv).setVisibility(View.VISIBLE);
        test3_table_layout4 = (TableLayout) findViewById(R.id.test3_tableLayout4);
        test3_table_layout4.setVisibility(View.GONE);
        test3_table_layout5 = (TableLayout) findViewById(R.id.test3_tableLayout5);
        test3_table_layout5.setVisibility(View.GONE);
        TFRtv = (TextView) findViewById(R.id.acph_h_tfr_value_tv);
        TFTByRvTv = (TextView) findViewById(R.id.acph_h_tfrby_av_value_tv);
        findViewById(R.id.test_table_3_header_l_ll).setVisibility(View.GONE);
        findViewById(R.id.test_table_3_header_2_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.measerd_h_value_tv).setVisibility(View.VISIBLE);
        findViewById(R.id.acph_h_final_calc_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.ahu_no_lable).setVisibility(View.VISIBLE);
        findViewById(R.id.room_volume_table).setVisibility(View.VISIBLE);
        TextView TestHeader = (TextView) findViewById(R.id.common_header_tv);
        TextView TestHeader2 = (TextView) findViewById(R.id.common_header_2_tv);
        TestHeader2.setVisibility(View.VISIBLE);
        TestHeader.setText("TEST RAW DATA");
        TestHeader2.setText("(Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates by Air Capture Hood)");
    }

    int idCountAxAv = 400, txtCountAxAv = 0;
    private void getAxAvDataValidationByAverage(int variation) {
        //check individual AxAv value based on All AxAv average
        //+- of variation %
        long axAvAverage = getTfr(averageDataHashMap)/averageDataHashMap.size();
        Log.d(TAG, " AxAv_H Averge "+axAvAverage);
        for (int i = 1; i <= rows - 1; i++) {
            Log.d(TAG, " idCountAxAv " + idCountAxAv + " txtCountAxAv " + txtCountAxAv);
            boolean results = checkAxAvBasedOnAverage(averageDataHashMap.get(idCountAxAv),
                    axAvAverage, variation);
            idCountAxAv++;
            if (results) {
                resultTextViewList.get(txtCountAxAv).setTextColor(Color.RED);
            } else {
                resultTextViewList.get(txtCountAxAv).setTextColor(Color.BLACK);
            }
            txtCountAxAv++;
        }
    }

    private boolean checkAxAvBasedOnAverage(long inputValue, long axAvAverage, int percentValue) {
        double variance = 0;
        long avg;
        boolean resultValue = true;
        variance = (double) (axAvAverage * percentValue) / 100;
        avg = Math.round(variance);
        if (inputValue >= (axAvAverage - avg) && inputValue <= (axAvAverage + avg)) {
            resultValue = false;
        }
        return resultValue;
    }

    private long getTfr(HashMap<Integer, Long> total_AirFlowRateMap) {
        long sum = 0l;
        for (float sum1 : total_AirFlowRateMap.values())
            sum += sum1;
        return sum;
    }
}
