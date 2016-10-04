package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.AhuFilter;
import com.project.valdoc.intity.ApplicableTestAhu;
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

public class RDACPHAVUserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDACPHAV";
    TableLayout test2_table_layout, test2_table_layout2, test2_table_layout3, test2_table_layout4,
            test2_table_layout5, test2_table_layout6, test2_table_layout7, test2_table_layout8;

    int rows, cols;
    String mTestType;
    ProgressDialog pr;
    LinearLayout test_table_1_footer, test_table_1_header_l, test_table_1_header_2;
    //Test 2 Ids variable
    int filterSizeIds = 100, airFlowRateIds = 300,
            totalAirFlowRateIds = 400;
    ArrayList<TextView> airFlowRateTxtViewList;
    ArrayList<TextView> totalAirFlowRateTxtList;
    ArrayList<TextView> roomVolumeTxtList;
    ArrayList<TextView> airChangeTxtList;
    SharedPreferences sharedpreferences;
    // bundel data specification
    private String loginUserType = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String ahuNumber;
    private String[] roomDetails;
    private Room room;
    private ApplicableTestAhu mApplicableTestAhu = null;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ArrayList<AhuFilter> mAhuFilterArrayList = null;
    private ArrayList<RoomFilter> mRoomFilterArrayList;
    private ArrayList<Grill> grillAndSizeFromGrill;

    //    private int applicableTestRoomLocation;
    private String areaName;
    private TextView infarance;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    private String userName = "";
    private int newCertificateNo = 0;
    //certificate view id creation
    private TextView instrumentUsed;
    private TextView TFRtv;
    private TextView TFTByRvTv;
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
    private TextView roomNo;
    private TextView ahuNo;
    private TextView ahuNoText;
    private TextView testItemText;
    private TextView testItemValue;

    private TextView roomVolume;
    private TextView roomVolumeText;
    private TextView testCundoctor;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;

    ArrayList<TextView> txtViewList;
    private double totalAirFlowRate = 0;
    private double airChangeValue;
    HashMap<Integer, Long> airFlowRateMap;
    HashMap<Integer, Float> totalAirFlowRateMap;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;
    private String mPartnerName;
    private String mTestCode;
    private String mTestBasedOn;
    private String mGrilFilterType;
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDACPHAVUserEntryActivity.this);
    int testDetailsId = 0;
    private HashMap<Integer, Integer> userEnterdValue;
    double AxAv = 0.0f;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    private ArrayList<Double>arrayList_totalAirFlowRate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdacphavuser_entry);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
        pr.setCanceledOnTouchOutside(true);
        pr.setCancelable(true);

        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);

        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();
        airFlowRateTxtViewList = new ArrayList<TextView>();
        totalAirFlowRateTxtList = new ArrayList<TextView>();
        roomVolumeTxtList = new ArrayList<TextView>();
        airChangeTxtList = new ArrayList<TextView>();


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
        initRes();
        datePicker();
        if (TestCreateActivity.ACPHAV.equalsIgnoreCase(mTestType)) {
            BuildTableTest2(rows, cols);
        }

        //setting the test 2 room volume
        if (roomVolumeTxtList != null && roomVolumeTxtList.size() > 0) {
            if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("" + roomDetails[4]);
            } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("" + room.getVolume());
            }
//            roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("8500");
        }

        //Receiving User Input Data from Bundle key start from 200
        userEnterdValue = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("InputData");
        for (Map.Entry m : userEnterdValue.entrySet()) {
            Log.v(TAG, m.getKey() + " " + m.getValue());
        }

        Log.v(TAG, " txtViewList size: " + txtViewList.size());
        for (int i = 0; i < txtViewList.size(); i++) {
            TextView tvl = txtViewList.get(i);
            tvl.setText(userEnterdValue.get(tvl.getId()) + "");
        }

        //Receiving Result Data from Bundle
        //average of v1 ,v2...where id is 1,2....
        airFlowRateMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("ResultData");
        for (Map.Entry n : airFlowRateMap.entrySet()) {
            Log.v(TAG, " Result: " + n.getKey() + " " + n.getValue());
        }

        for (int i = 0; i < resultTextViewList.size(); i++) {
            TextView tvl = resultTextViewList.get(i);
            tvl.setText(airFlowRateMap.get(tvl.getId()) + "");
            if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                if (!mAhuFilterArrayList.isEmpty()) {
                    //Air Flow Rate(AxAv)
                    TextView tv2 = airFlowRateTxtViewList.get(i);

                    try {

                        AxAv = Double.valueOf(mAhuFilterArrayList.get(i).getEffectiveArea())*airFlowRateMap.get(tvl.getId());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    tv2.setText(Math.round(AxAv)+ "");
                    arrayList_totalAirFlowRate.add(AxAv);
                }
            }
        }

        //Air Flow Rate(AxAv)
/*        totalAirFlowRateMap = (HashMap<Integer, Float>) getIntent().getSerializableExtra("ResultData2");
        for (int i = 0; i < airFlowRateTxtViewList.size(); i++) {
            TextView tvl = airFlowRateTxtViewList.get(i);
            Log.v(TAG, " totalAirFlowRateMap: " + tvl.getId());
            tvl.setText(totalAirFlowRateMap.get(tvl.getId()) + "");
        }*/
        //Total AirFlow Rate (sum of AirFlow Rate)
        double totalAirFlowRateValue = 0.0f;
        if (totalAirFlowRateTxtList != null && totalAirFlowRateTxtList.size() > 0) {
            int middleTxt = totalAirFlowRateTxtList.size() / 2;
            TextView mtvl = totalAirFlowRateTxtList.get(middleTxt);
            totalAirFlowRate = getIntent().getFloatExtra("totalAirFlowRate", 0f);
            if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                if(!arrayList_totalAirFlowRate.isEmpty()&& arrayList_totalAirFlowRate.size()>0){
                    for (int i = 0; i < arrayList_totalAirFlowRate.size(); i++) {
                        totalAirFlowRateValue += arrayList_totalAirFlowRate.get(i);
                    }
                }
                mtvl.setText(Math.round(totalAirFlowRateValue/arrayList_totalAirFlowRate.size())+ "");
            }
            TFRtv.setText(totalAirFlowRate + "");
        }
        //AirFlow Change
        if (airChangeTxtList != null && airChangeTxtList.size() > 0) {
            TextView airChangeTxt = airChangeTxtList.get(airChangeTxtList.size() / 2);
            airChangeValue = getIntent().getIntExtra("AirChangeValue", 0);
            if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                int value = 0;
                try {
                    value = Integer.parseInt(roomDetails[3]);
                } catch (Exception e) {
                    value = 0;
                }
                if (airChangeValue > value) {
                    infarance.setText("The above Airflow Volume Test and Derived No.of Air chanages per hour meets the specificed requirement");
                    TFTByRvTv.setTextColor(ContextCompat.getColor(this, R.color.blue));
                } else {
                    infarance.setText("The above Airflow Volume Test and Derived No.of Air chanages per hour do not meets the specificed requirement");
                    TFTByRvTv.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
            } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                if (airChangeValue > room.getAcph()) {
                    infarance.setText("The above Airflow Volume Test and Derived No.of Air chanages per hour meets the specificed requirement");
                    TFTByRvTv.setTextColor(ContextCompat.getColor(this, R.color.blue));
                } else {
                    infarance.setText("The above Airflow Volume Test and Derived No.of Air chanages per hour do not meets the specificed requirement");
                    TFTByRvTv.setTextColor(ContextCompat.getColor(this, R.color.red));
                }
            }

//            infarance.setText("Obtained Test Result");
            airChangeTxt.setText("" + airChangeValue);
            TFTByRvTv.setText("" + airChangeValue);
        }

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDACPHAVUserEntryActivity.this, mActionBar, userName);
    }


    private void datePicker() {
        // Get current date by calender

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        // create certificate no
//        if (sharedpreferences.getInt("CERTIFICATE_NO", 0) == 0) {
//            newCertificateNo = 0;
//        } else {
//            newCertificateNo += 1;
//        }

        //raw data no
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        int mon = month + 1;
        certificateNo.setText("AV/" + mon + "/" + year + "/" + formattedDate);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putInt("CERTIFICATE_NO", newCertificateNo);
//        editor.commit();

        // Show current date
        String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
        dateTextView.setText(date);
//        new StringBuilder()
//                // Month is 0 based, just add 1
//                .append(year).append("-").append(month + 1).append("-")
//                .append(day).append(" "));
    }


    private void textViewValueAssignment() {

        if (loginUserType.equals("CLIENT")) {
            instrumentUsed.setText(clientInstrument.getcInstrumentName());
//            make.setText(clientInstrument.getMake());
            instrumentSerialNo.setText("" + clientInstrument.getSerialNo());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getLastCalibrated()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getCalibrationDueDate()));
        } else {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
//            make.setText(partnerInstrument.getMake());
//            model.setText(partnerInstrument.getModel());
            instrumentSerialNo.setText("" + partnerInstrument.getpInstrumentId());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getLastCalibrationDate()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getCalibrationDueDate()));
        }

        testCundoctor.setText(userName);

        if (mTestBasedOn.equalsIgnoreCase("AHU")) {
            testSpecification.setText(mApplicableTestAhu.getTestSpecification());
            occupancyState.setText(mApplicableTestAhu.getOccupencyState());
            testRefrance.setText(mApplicableTestAhu.getTestReference());
            areaOfTest.setText(areaName);
            roomName.setText(roomDetails[1]);
            // room no not needed
            roomNo.setText(roomDetails[2]);
            ahuNo.setText(ahuNumber);
            roomVolume.setText("" + roomDetails[4]);
            testItemValue.setText("" + mApplicableTestAhu.getTestItem());
        } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            testSpecification.setText(mApplicableTestRoom.getTestSpecification());
            occupancyState.setText(mApplicableTestRoom.getOccupencyState());
            testRefrance.setText(mApplicableTestRoom.getTestReference());
            areaOfTest.setText(areaName);
            roomName.setText(room.getRoomName().toString());
            roomNo.setText(room.getRoomNo().toString());
            roomVolume.setText("" + room.getVolume());
            ahuNo.setText(ahuNumber);
        }


        String clientOrg = sharedpreferences.getString("CLIENTORG", "");
        String prtnerOrg = sharedpreferences.getString("PARTNERORG", "");
        if (sharedpreferences.getString("USERTYPE", "").equalsIgnoreCase("CLIENT")) {
            testCondoctorOrg.setText("(" + clientOrg + ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText(""+clientOrg);
        } else {
            testCondoctorOrg.setText("(" + prtnerOrg + ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText(""+prtnerOrg);
        }
        plantName.setText("from cofig screen");
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
        roomVolumeText = (TextView) findViewById(R.id.room_volume_text);
        roomVolumeText.setVisibility(View.VISIBLE);
        roomVolume = (TextView) findViewById(R.id.room_volume);
        roomVolume.setVisibility(View.VISIBLE);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        ahuNo.setVisibility(View.VISIBLE);
        ahuNoText = (TextView) findViewById(R.id.ahu_no_text);
        ahuNoText.setVisibility(View.VISIBLE);
        if (mTestBasedOn.equalsIgnoreCase("AHU")) {
            testItemText = (TextView) findViewById(R.id.test_item_text);
            testItemText.setVisibility(View.VISIBLE);
            testItemValue = (TextView) findViewById(R.id.test_item_value);
            testItemValue.setVisibility(View.VISIBLE);
        }
//        roomNameLable = (TextView) findViewById(R.id.room_name_lable2);
//        roomNameLable.setVisibility(View.GONE);
//        instrumentNoLable = (TextView) findViewById(R.id.instrument_no_lable);
//        instrumentNoLable.setVisibility(View.GONE);
//        roomNameTest = (TextView) findViewById(R.id.room_name2);
//        roomNameTest.setVisibility(View.GONE);
//        instrument_name = (TextView) findViewById(R.id.instrument_name2);
//        instrument_name.setVisibility(View.GONE);

        dateTextView = (TextView) findViewById(R.id.datetextview);
        customerName = (TextView) findViewById(R.id.customer_name);
        certificateNo = (TextView) findViewById(R.id.trd_no);
        instrumentUsed = (TextView) findViewById(R.id.instrumentused);
//        make = (TextView) findViewById(R.id.make);
//        model = (TextView) findViewById(R.id.modle);

        instrumentSerialNo = (TextView) findViewById(R.id.instrumentserialno);
        calibrationOn = (TextView) findViewById(R.id.calibratedon);
        calibrationDueOn = (TextView) findViewById(R.id.calibrationdueon);
        testSpecification = (TextView) findViewById(R.id.testspecification);
        plantName = (TextView) findViewById(R.id.plantname);
        areaOfTest = (TextView) findViewById(R.id.areaoftest);
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        equipmentNameText = (TextView) findViewById(R.id.equiment_name_text);
        equipmentNameText.setVisibility(View.INVISIBLE);
        equipmentNoText = (TextView) findViewById(R.id.equiment_no_text);
        equipmentNoText.setVisibility(View.INVISIBLE);
        roomName = (TextView) findViewById(R.id.room_name);
        roomNo = (TextView) findViewById(R.id.room_no);
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
                        if (mValdocDatabaseHandler.insertTestSpesificationValue(ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TABLE_NAME, testSpesificationValue())) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("TESTDETAILSID", testDetailsId);
                            editor.commit();
                            Toast.makeText(RDACPHAVUserEntryActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RDACPHAVUserEntryActivity.this, TestCreateActivity.class);
                            intent.putExtra(TestCreateActivity.ACPHAV, true);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RDACPHAVUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RDACPHAVUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RDACPHAVUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
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
    }


    private ArrayList<TestSpesificationValue> testSpesificationValue() {
        ArrayList<TestSpesificationValue> spesificationValueArrayList = new ArrayList<TestSpesificationValue>();
        TestSpesificationValue testSpesificationValue = new TestSpesificationValue();
//        testSpesificationValue.setTest_specific_id(1);
        testSpesificationValue.setTest_detail_id("" + testDetailsId);
        testSpesificationValue.setFieldName("TFR");
        testSpesificationValue.setFieldValue("" + totalAirFlowRate);
        spesificationValueArrayList.add(testSpesificationValue);

        TestSpesificationValue testSpesificationValue1 = new TestSpesificationValue();
//        testSpesificationValue1.setTest_specific_id(1);
        testSpesificationValue1.setTest_detail_id("" + testDetailsId);
        testSpesificationValue1.setFieldName("RV");
        if (mTestBasedOn.equalsIgnoreCase("AHU")) {
            testSpesificationValue1.setFieldValue("" + roomDetails[4]);
        } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            testSpesificationValue1.setFieldValue("" + room.getVolume());
        }

        spesificationValueArrayList.add(testSpesificationValue1);

        TestSpesificationValue testSpesificationValue2 = new TestSpesificationValue();
//        testSpesificationValue2.setTest_specific_id(1);
        testSpesificationValue2.setTest_detail_id("" + testDetailsId);
        testSpesificationValue2.setFieldName("((TFR/RV)x60))");
        testSpesificationValue2.setFieldValue("" + airChangeValue);
        spesificationValueArrayList.add(testSpesificationValue2);

        return spesificationValueArrayList;
    }

    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> testReadingArrayList = new ArrayList<TestReading>();
        int index = 0;
        int avindex = 300;
        int hasMapKey = 200;
        if (mTestBasedOn.equalsIgnoreCase("AHU")) {
            for (AhuFilter ahuFilter : mAhuFilterArrayList) {
                TestReading testReading = new TestReading();
//            testReading.setTestReadingID(index);
//        TO DO test details id is id of test details table
                testReading.setTest_detail_id(testDetailsId);
                testReading.setEntityName("" + ahuFilter.getFilterCode().toString());
                StringBuilder grilList = new StringBuilder();
                //v1,v2....value cration
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mApplicableTestAhu.getLocation(); i++) {
                    if (i != 0)
                        sb.append(',');
                    if (null != userEnterdValue && userEnterdValue.size() > 0) {
                        Log.d("Avinash", "value=" + userEnterdValue.isEmpty());
                        if (null != userEnterdValue.get(hasMapKey).toString()) {
                            sb.append("" + userEnterdValue.get(hasMapKey).toString());
                            hasMapKey++;
                        }
                    }
                }
                grilList.append("" + ahuFilter.getEffectiveArea()).append(',').append(sb).append(",").append(airFlowRateMap.get(index + 1)).append(",").append(AxAv);
                index++;
                avindex++;
                testReading.setValue(grilList.toString());
                testReadingArrayList.add(testReading);
            }
        } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                for (Grill grill : grillAndSizeFromGrill) {
                    TestReading testReading = new TestReading();
//        TO DO test details id is id of test details table
                    testReading.setTest_detail_id(testDetailsId);
                    testReading.setEntityName("" + grill.getGrillCode().toString());
                    StringBuilder grilList = new StringBuilder();
                    //v1,v2....value cration
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mApplicableTestRoom.getLocation(); i++) {
                        if (i != 0)
                            sb.append(',');
                        if (null != userEnterdValue && userEnterdValue.size() > 0) {
                            Log.d("Avinash", "value=" + userEnterdValue.isEmpty());
                            if (null != userEnterdValue.get(hasMapKey).toString()) {
                                sb.append("" + userEnterdValue.get(hasMapKey).toString());
                                hasMapKey++;
                            }
                        }
                    }

                    grilList.append("" + grill.getEffectiveArea()).append(',').append(sb).append(",").append(airFlowRateMap.get(index + 1)).append(",").append(AxAv);
                    index++;
                    avindex++;
                    testReading.setValue(grilList.toString());
                    testReadingArrayList.add(testReading);
                }


            } else {
                for (RoomFilter roomFilter : mRoomFilterArrayList) {
                    TestReading testReading = new TestReading();
//        TO DO test details id is id of test details table
                    testReading.setTest_detail_id(testDetailsId);
                    testReading.setEntityName("" + roomFilter.getFilterCode().toString());
                    StringBuilder grilList = new StringBuilder();
                    //v1,v2....value cration
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mApplicableTestRoom.getLocation(); i++) {
                        if (i != 0)
                            sb.append(',');
                        if (null != userEnterdValue && userEnterdValue.size() > 0) {
                            Log.d("Avinash", "value=" + userEnterdValue.isEmpty());
                            if (null != userEnterdValue.get(hasMapKey).toString()) {
                                sb.append("" + userEnterdValue.get(hasMapKey).toString());
                                hasMapKey++;
                            }
                        }
                    }

                    grilList.append("" + roomFilter.getEffectiveFilterArea()).append(',').append(sb).append(",").append(airFlowRateMap.get(index + 1)).append(",").append(AxAv);
                    index++;
                    avindex++;
                    testReading.setValue(grilList.toString());
                    testReadingArrayList.add(testReading);
                }
            }
        }

        return testReadingArrayList;
    }


    private TestDetails testDetailsDataCreation() {
        TestDetails testDetails = new TestDetails();
//        TO DO: need to make it dynamic
        testDetails.setTest_detail_id(testDetailsId);
        testDetails.setCustomer(customerName.getText().toString());
        String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
//        String date = String.valueOf(year) + "-" + (String.valueOf(month + 1)) + "-" + (String.valueOf(day)) + " ";
//        testDetails.setDateOfTest(new String(new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString()));
        testDetails.setDateOfTest("" + dateTextView.getText());
        testDetails.setRawDataNo(certificateNo.getText().toString());
        testDetails.setPartnerName("" + mPartnerName);
        testDetails.setTestName(mTestCode);
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

        testDetails.setTestSpecification("" + testSpecification.getText().toString());
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
        testWitness.setText(witness);
        testDetails.setWitnessName("" + witness);
        testDetails.setEquipmentName("");
        testDetails.setEquipmentNo("");

        testDetails.setSamplingFlowRate("");
        testDetails.setSamplingTime("");
        testDetails.setAerosolGeneratorType("");
        testDetails.setAerosolUsed("");
        if(mTestBasedOn.equalsIgnoreCase("ROOM")){
            testDetails.setTestItem("");
        }else{
            testDetails.setTestItem("" + testItemValue.getText());
        }

        testDetails.setRoomVolume("" + roomVolume.getText());
        testDetails.setTestWitnessOrg("" + testWitnessOrg.getText());
        testDetails.setTestCondoctorOrg("" + testCondoctorOrg.getText());
        return testDetails;
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

            // create certificate no
            certificateNo.setText(new StringBuilder().append("AV/" + month + 1)
                    .append("/").append(year)
                    .append("/" + newCertificateNo));

//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putInt("CERTIFICATE_NO", newCertificateNo);
//            editor.commit();

            // Show selected date
            String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
            dateTextView.setText(date);
//            new StringBuilder().append(year)
//                    .append("-").append(month + 1).append("-").append(day)
//                    .append(" "));

        }
    };

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
                mPartnerName = extras.getString("PRTNERNAME");
                witnessFirst = extras.getString("WITNESSFIRST");
                witnessSecond = extras.getString("WITNESSSECOND");
                witnessThird = extras.getString("WITNESSTHIRD");
                //get area based on room area id
                areaName = extras.getString("AREANAME");
                mTestCode = extras.getString("testCode");
                mTestBasedOn = extras.getString("testBasedOn");
//                applicableTestRoomLocation = extras.getInt("LOCATION");
                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }
                if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                    roomDetails = extras.getStringArray("RoomDetails");
                    ahuNumber = extras.getString("AhuNumber");
                    mAhuFilterArrayList = (ArrayList<AhuFilter>) extras.getSerializable("AhuFilter");
                    mApplicableTestAhu = (ApplicableTestAhu) extras.getSerializable("ApplicableTestAhu");

                } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                        grillAndSizeFromGrill = (ArrayList<Grill>) extras.getSerializable("GRILLLIST");
                    } else {
                        mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilter");
                    }
                    mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                }

            }
        }


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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Grille / Filter No\n "));
                } else {
                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        if (null != mAhuFilterArrayList && mAhuFilterArrayList.size() > 0) {
//                            HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                            row.addView(addTextView(mAhuFilterArrayList.get(i - 2).getFilterCode()));
                        } else {
                            row.addView(addTextView("grillAndSizeFromGrill"));
                        }
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                                row.addView(addTextView(grillAndSizeFromGrill.get(i - 2).getGrillCode().toString()));
                            } else {
                                row.addView(addTextView("grillAndSizeFromGrill"));
                            }

                        } else {
                            if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
//                            HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                                row.addView(addTextView(mRoomFilterArrayList.get(i - 2).getFilterCode()));
                            } else {
                                row.addView(addTextView("grillAndSizeFromGrill"));
                            }

                        }

                    }

//                    row.addView(addTextView("AHU 2031/0.3MICRON/" + i));
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Grill / Filter Area\n in ft2(A)"));
                } else {
                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        double filterSize = 0.0f;
                        if (!mAhuFilterArrayList.isEmpty()) {
                            filterSize = mAhuFilterArrayList.get(i - 2).getEffectiveArea();
                            row.addView(addTextView("" + filterSize));
                        }

                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
//                                HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
                                float filterSize = 0.0f;
//                                if (!grill.isEmpty())
                                    filterSize = (float)(grillAndSizeFromGrill.get(i - 2).getEffectiveArea());
                                row.addView(addTextView("" + filterSize));
//                                row.addView(addTextViewWithTagIds(i, filterSizeIds, filterSizeTxtViewList, filterSize + ""));
                            }

                        } else {
                            double filterSize = 0.0f;
                            if (!mRoomFilterArrayList.isEmpty())
                                filterSize = mRoomFilterArrayList.get(i - 2).getEffectiveFilterArea();
//                            row.addView(addTextViewWithTagIds(i, filterSizeIds, filterSizeTxtViewList, filterSize + ""));
                            row.addView(addTextView("" + filterSize));

                        }

                    }
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
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" V " + j + "\n "));
                } else {
                    //row.addView(addEditTextView());
                    row.addView(addInputDataTextView());
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Avg Velocity in\n fpm(AV)"));
                } else {
                    //result data  set
                    row.addView(addResultTextView(i));
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Air Flow Rate\n in cfm(AxAv)"));
                } else {
                    //row.addView(addTextView("490"));
                    row.addView(addTextViewWithTagIds(i, airFlowRateIds, airFlowRateTxtViewList, 0));
                    airFlowRateIds++;
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Total Air Flow Rate\n in cfm (TFR)"));
                } else {
                    //row.addView(addTextViewWithoutBorder("490"));
                    row.addView(addTextViewWithIdsNoBorder(i, totalAirFlowRateIds, totalAirFlowRateTxtList));
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Room Volume in\n ft3(RV)"));
                } else {
                    //row.addView(addTextViewWithoutBorder("490"));
                    row.addView(addTextViewWithIdsNoBorder(i, 0, roomVolumeTxtList));
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView("No. of Air Changes/Hr\n ((TFR/RV)x60))"));
                } else {
                    //row.addView(addTextViewWithoutBorder("490"));
                    row.addView(addTextViewWithIdsNoBorder(i, 0, airChangeTxtList));
                }
            }
            test2_table_layout8.addView(row);
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
        tv.setEms(4);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        idCountEtv++;
        txtViewList.add(tv);
        return tv;
    }

    int idCountTv = 1;

    private TextView addResultTextView(int rowsNo) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border);
        tv.setPadding(5, 6, 5, 6);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setEms(4);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, " idCountTv " + idCountTv);
        tv.setId(idCountTv);
        tv.setTag(rowsNo);
        idCountTv++;
        resultTextViewList.add(tv);
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

    private TextView addTextViewWithIdsNoBorder(int Tag, int ids, ArrayList<TextView> txtViewList) {
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
        Log.d(TAG, "No Border idCountTv " + ids);
        if (ids > 0)
            tv.setId(ids);
        tv.setTag(Tag);
        ids++;
        txtViewList.add(tv);
        return tv;
    }

    private EditText addEditTextView() {
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


    private void initRes() {
        //Test 2
        test2_table_layout = (TableLayout) findViewById(R.id.test2_tableLayout1);
        test2_table_layout2 = (TableLayout) findViewById(R.id.test2_tableLayout2);
        test2_table_layout3 = (TableLayout) findViewById(R.id.test2_tableLayout3);
        test2_table_layout4 = (TableLayout) findViewById(R.id.test2_tableLayout4);
        test2_table_layout5 = (TableLayout) findViewById(R.id.test2_tableLayout5);
        test2_table_layout6 = (TableLayout) findViewById(R.id.test2_tableLayout6);
        test2_table_layout7 = (TableLayout) findViewById(R.id.test2_tableLayout7);
        test2_table_layout7.setVisibility(View.GONE);
        test2_table_layout8 = (TableLayout) findViewById(R.id.test2_tableLayout8);
        test2_table_layout8.setVisibility(View.GONE);
        TFRtv = (TextView) findViewById(R.id.acph_av_tfr_value_tv);
        TFTByRvTv = (TextView) findViewById(R.id.acph_av_tfrby_av_value_tv);
        //Hide view coming form test tabl 1
        test_table_1_header_l = (LinearLayout) findViewById(R.id.test_table_2_header_l_ll);
        test_table_1_header_2 = (LinearLayout) findViewById(R.id.test_table_2_header_2_ll);
        test_table_1_header_l.setVisibility(View.GONE);
        test_table_1_header_2.setVisibility(View.VISIBLE);
        findViewById(R.id.test_interference).setVisibility(View.GONE);
        if(!"AHU".equalsIgnoreCase(mTestBasedOn)){
            findViewById(R.id.acph_av_final_calc_ll).setVisibility(View.VISIBLE);
            test2_table_layout6.setVisibility(View.GONE);
        }
        findViewById(R.id.test2_reading_header).setVisibility(View.VISIBLE);
        TextView TestHeader = (TextView) findViewById(R.id.common_header_tv);
        TestHeader.setText("TEST RAW DATA\n(Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates)");
    }
}
