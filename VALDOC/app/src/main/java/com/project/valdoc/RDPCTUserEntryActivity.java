package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.IsoParticleLimits;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RDPCTUserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDPC3";
    TextView headerText, headerText2;
    //Test 5 View ...
    TableLayout test5_table_layout, test5_table_layout2, test5_table_layout2_1,
            test5_table_layout3, test5_tableLayout2_2, test5_table_layout4, test5_tableLayout4_2, test5_table_layout4_1,
            test5_table_layout5, test5_table_layout5_1, test5_table_layout3_1;
    // PCT new footer
    TextView meanValue1_tv, small_particle, iso_class, large_particle, uclId, standerdeviationText, meanValue2_tv, stdDev1_tv, stdDev2_tv, ucl1_tv, ucl2_tv, minimumValue1_tv, minimumValue2_tv, maximumValue1_tv, maximumValue2_tv;
    int rows, cols;
    String mTestType;
    //    ProgressDialog pr;
    //Test 5 Variable
    int test5CommonFormulaIds1 = 500, test5CommonFormulaIds2 = 600;
    long meanValue1 = 0L, meanValue2 = 0L;
    long ucl1 = 0l, ucl2 = 0l;
    double stdDev1 = 0.0, stdDev2 = 0.0;
    ArrayList<TextView> RDPC3TxtList, RDPC3TxtList2;

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

    //certificate view id creation
    private TextView instrumentUsed;
    private TableRow samplingFlowTable;
    private TableRow samplingTimeTable;
    private TextView samplingFlowRateText;
    private TextView samplingTimeText;
    private TextView samplingTime;
    private TextView samplingFlowRate;
    private TextView instrumentSerialNo;
    private TextView calibrationOn;
    private TextView calibrationDueOn;
    private TextView cleanRoomClass;
    private TextView testspecificationText;
    private TextView plantName;
    private TextView areaOfTest;
    private TextView roomName;
    private TextView occupancyState;
    private TextView testRefrance;
    private TextView equipmentLable;
    private TextView equipmentName;
    private TextView equipmentNoLable;
    private TextView equipmentNo;
    private TextView roomNo;
    private TextView ahuNo;
    private TextView ahuNoText;
    private TextView testCundoctor;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    private TextView instrumentNoTextView;
    private TextView testerNameTextView;
    private TextView instrumentUsedTextView;
    private TextView testCunductedByTextView;
    private TextView atrest_one;
    private TextView atrest_two;

    private TextView roomNameLable;
    private TextView instrumentNoLable;
    private TextView roomNameTest;
    private TextView instrument_name;
    private String[] roomDetails;
    private Equipment equipment;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ApplicableTestEquipment mApplicableTestEquipment = null;
    ArrayList<TextView> txtViewList;
    private String mPartnerName;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;
    HashMap<Integer, Integer> rHashMap;
    HashMap<Integer, Long> averageResultHashMap;
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDPCTUserEntryActivity.this);
    SharedPreferences sharedpreferences;
    private String mTestCode = "";
    private String testType;
    private String mTestBasedOn;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    int testDetailsId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdpc3_user_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);
        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();
        RDPC3TxtList = new ArrayList<TextView>();
        RDPC3TxtList2 = new ArrayList<TextView>();

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            mTestType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + mTestType + " rows " + rows + " cols " + cols);
        }
        mTestCode = getIntent().getStringExtra("testCode");
        mTestBasedOn = getIntent().getStringExtra("testBasedOn");
        testType = getIntent().getStringExtra("testType");
        Log.d(TAG, " mTestCode : " + mTestCode + " mTestBasedOn " + mTestBasedOn + " cols " + testType);
        if ("EQUIPMENT".equalsIgnoreCase(mTestBasedOn)) {
            rows = rows + 1;
        }

        //dynamic data population
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        initRes();
        textViewValueAssignment();
        datePicker();

        if (rows > 0 && cols > 0) {
            BuildTableTest5(rows, cols);
        } else {
            Utilityies.showAlert(RDPCTUserEntryActivity.this,
                    getResources().getString(R.string.table_not_created));
        }

        //Receiving User Input Data from Bundle
        rHashMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("InputData");
        int size = rHashMap.size() / 2;
        long smallParticle = Long.parseLong(small_particle.getText().toString().trim());
        long largeParticle = Long.parseLong(large_particle.getText().toString().trim());
        for (int i = 0; i < txtViewList.size(); i++) {
            TextView tvl = txtViewList.get(i);
            //tvl.setText(rHashMap.get(tvl.getId()) + "");
            long inputValue = rHashMap.get(tvl.getId());
            tvl.setText(inputValue + "");

            //Validating input value
            if (tvl.getId() < (200 + size)) {
                if (inputValue > smallParticle)
                    tvl.setTextColor(Color.RED);
            } else {
                if (inputValue > largeParticle)
                    tvl.setTextColor(Color.RED);
            }
        }

        //Finding mean and max value
        ArrayList<Integer> arrayList1 = new ArrayList<Integer>();
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        int k = 200;
        for (int i = 0; i < size; i++) {
            int j = rHashMap.get(k);
            arrayList1.add(j);
            k++;
        }
        int l = 200 + size;
        for (int i = 0; i < size; i++) {
            int j = rHashMap.get(l);
            arrayList2.add(j);
            l++;
        }
        Collections.sort(arrayList1);
        Collections.sort(arrayList2);

        minimumValue1_tv.setText("" + arrayList1.get(0));
        minimumValue2_tv.setText("" + arrayList2.get(0));
        maximumValue1_tv.setText("" + arrayList1.get(arrayList1.size() - 1));
        maximumValue2_tv.setText("" + arrayList2.get(arrayList2.size() - 1));

        //Receiving Result Data from Bundle
        averageResultHashMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("ResultData");
        int resultListSize = resultTextViewList.size();
        for (int i = 0; i < resultListSize; i++) {
            TextView tvl = resultTextViewList.get(i);
            long resultValue = averageResultHashMap.get(tvl.getId());
            tvl.setText(resultValue + "");
//Validating result value
            if (tvl.getId() < 1 + (resultListSize / 2)) {
                if (resultValue > smallParticle)
                    tvl.setTextColor(Color.RED);
            } else {
                if (resultValue > largeParticle)
                    tvl.setTextColor(Color.RED);
            }
        }

        meanValue1 = getIntent().getLongExtra("meanValue1", 0l);
        meanValue2 = getIntent().getLongExtra("meanValue2", 0l);
        stdDev1 = getIntent().getDoubleExtra("stdDev1", 0.0);
        stdDev2 = getIntent().getDoubleExtra("stdDev2", 0.0);
        ucl1 = getIntent().getLongExtra("UCL_V1", 0l);
        ucl2 = getIntent().getLongExtra("UCL_V2", 0l);

        meanValue1_tv.setText(meanValue1 + "");
        meanValue2_tv.setText(meanValue2 + "");
        stdDev1_tv.setText(Math.round(stdDev1) + "");
        stdDev2_tv.setText(Math.round(stdDev2) + "");
        ucl1_tv.setText(ucl1 + "");
        ucl2_tv.setText(ucl2 + "");

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDPCTUserEntryActivity.this, mActionBar, userName);

        //Pass Fail condition based on ISO-8 value from DBs
        if (small_particle.getText().toString().trim() != null) {//No. of Particles ≥ 0. 5 µm/m³
            if (meanValue1 > smallParticle) {
                meanValue1_tv.setTextColor(Color.RED);
            }
            if (Math.round(stdDev1) > smallParticle) {
                stdDev1_tv.setTextColor(Color.RED);
            }
            if (ucl1 > smallParticle) {
                ucl1_tv.setTextColor(Color.RED);
            }
            if (arrayList1.get(0) > smallParticle) {
                minimumValue1_tv.setTextColor(Color.RED);
            }
            if (arrayList1.get(arrayList1.size() - 1) > smallParticle) {
                maximumValue1_tv.setTextColor(Color.RED);
            }
        }

// No. of Particles ≥ 5 µm/m³
        if (large_particle.getText().toString().trim() != null) {
            if (meanValue2 > largeParticle) {
                meanValue2_tv.setTextColor(Color.RED);
            }
            if (Math.round(stdDev2) > largeParticle) {
                stdDev2_tv.setTextColor(Color.RED);
            }
            if (ucl2 > largeParticle) {
                ucl2_tv.setTextColor(Color.RED);
            }
            if (arrayList2.get(0) > largeParticle) {
                minimumValue2_tv.setTextColor(Color.RED);
            }
            if (arrayList2.get(arrayList2.size() - 1) > largeParticle) {
                maximumValue2_tv.setTextColor(Color.RED);
            }
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
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        int mon = month + 1;
        certificateNo.setText("C3/" + mon + "/" + year + "/" + formattedDate);

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
        if (loginUserType.equalsIgnoreCase("PARTNER")) {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
            instrumentSerialNo.setText("" + partnerInstrument.getpInstrumentId());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getLastCalibrationDate()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getCalibrationDueDate()));
            samplingFlowRate.setText(partnerInstrument.getRange());
//            samplingTime.setText(partnerInstrument.getSamplingTime());
        } else {
            instrumentUsed.setText(clientInstrument.getcInstrumentName());
            instrumentSerialNo.setText("" + clientInstrument.getSerialNo());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getLastCalibrated()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(clientInstrument.getCalibrationDueDate()));
            samplingFlowRate.setText(clientInstrument.getRange());
//            samplingTime.setText(clientInstrument.getSamplingTime());

        }
        testCundoctor.setText(userName);
        areaOfTest.setText(areaName);
        samplingFlowRateText.setText("Sampling Flow Rate :");
        samplingTimeText.setText("Sampling Time :");
        if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            roomName.setText(room.getRoomName());
            testRefrance.setText(mApplicableTestRoom.getTestReference());
            roomNo.setText(room.getRoomNo().toString());
            ahuNo.setText(ahuNumber);
            occupancyState.setText(mApplicableTestRoom.getOccupencyState().toString());
            String samplingtime = Utilityies.getSamplingTime(mApplicableTestRoom.getTestSpecification(), samplingFlowRate.getText().toString());
            samplingTime.setText("" + samplingtime);
            atrest_one.setText("" + mApplicableTestRoom.getOccupencyState().toString());
            atrest_two.setText("" + mApplicableTestRoom.getOccupencyState().toString());
            cleanRoomClass.setText(" " + mApplicableTestRoom.getTestSpecification());
            iso_class.setText("" + mApplicableTestRoom.getTestSpecification());
            ///////////////
            IsoParticleLimits isoParticleLimits = mValdocDatabaseHandler.getIsoParticle(mApplicableTestRoom.getTestSpecification());
            if (mApplicableTestRoom.getOccupencyState().equalsIgnoreCase("at-rest")) {
                small_particle.setText("" + isoParticleLimits.getRestSmallParticleLimit());
                large_particle.setText("" + isoParticleLimits.getRestLargeParticleLimit());
            } else if (mApplicableTestRoom.getOccupencyState().equalsIgnoreCase("in-operation")) {
                small_particle.setText("" + isoParticleLimits.getOperationSmallParticleLimit());
                large_particle.setText("" + isoParticleLimits.getOperationLargeParticleLimit());
            }
            ///UCL
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mApplicableTestRoom.getTestProp());
                if (jsonObject.optString("UCL95").equalsIgnoreCase("Applicable")) {
                    uclId.setVisibility(View.VISIBLE);
                    standerdeviationText.setVisibility(View.VISIBLE);
                    stdDev1_tv.setVisibility(View.VISIBLE);
                    stdDev2_tv.setVisibility(View.VISIBLE);
                    ucl1_tv.setVisibility(View.VISIBLE);
                    ucl2_tv.setVisibility(View.VISIBLE);
                } else {
                    uclId.setVisibility(View.GONE);
                    standerdeviationText.setVisibility(View.GONE);
                    stdDev1_tv.setVisibility(View.GONE);
                    stdDev2_tv.setVisibility(View.GONE);
                    ucl1_tv.setVisibility(View.GONE);
                    ucl2_tv.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }
        } else if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            equipmentName.setText(equipment.getEquipmentName());
            equipmentNo.setText(equipment.getEquipmentNo());
            occupancyState.setText(mApplicableTestEquipment.getOccupencyState());
            testRefrance.setText(mApplicableTestEquipment.getTestReference());
            atrest_one.setText("" + mApplicableTestEquipment.getOccupencyState().toString());
            atrest_two.setText("" + mApplicableTestEquipment.getOccupencyState().toString());
            roomName.setText(roomDetails[1]);
            // room no not needed
            roomNo.setText(roomDetails[2]);
            areaOfTest.setText(areaName);

            String samplingtime = Utilityies.getSamplingTime(mApplicableTestEquipment.getTestSpecification(), samplingFlowRate.getText().toString());
            samplingTime.setText("" + samplingtime);
            cleanRoomClass.setText("" + mApplicableTestEquipment.getTestSpecification());
            iso_class.setText("" + mApplicableTestEquipment.getTestSpecification());
            ////////////////////////
            IsoParticleLimits isoParticleLimits = mValdocDatabaseHandler.getIsoParticle(mApplicableTestEquipment.getTestSpecification());
            if (mApplicableTestEquipment.getOccupencyState().equalsIgnoreCase("at-rest")) {
                small_particle.setText("" + isoParticleLimits.getRestSmallParticleLimit());
                large_particle.setText("" + isoParticleLimits.getRestLargeParticleLimit());
            } else if (mApplicableTestEquipment.getOccupencyState().equalsIgnoreCase("in-operation")) {
                small_particle.setText("" + isoParticleLimits.getOperationSmallParticleLimit());
                large_particle.setText("" + isoParticleLimits.getOperationLargeParticleLimit());
            }

            ///UCL
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mApplicableTestEquipment.getTestProp());
                if (jsonObject.optString("UCL95").equalsIgnoreCase("Applicable")) {
                    uclId.setVisibility(View.VISIBLE);
                    standerdeviationText.setVisibility(View.VISIBLE);
                    stdDev1_tv.setVisibility(View.VISIBLE);
                    stdDev2_tv.setVisibility(View.VISIBLE);
                    ucl1_tv.setVisibility(View.VISIBLE);
                    ucl2_tv.setVisibility(View.VISIBLE);
                } else {
                    uclId.setVisibility(View.GONE);
                    standerdeviationText.setVisibility(View.GONE);
                    stdDev1_tv.setVisibility(View.GONE);
                    stdDev2_tv.setVisibility(View.GONE);
                    ucl1_tv.setVisibility(View.GONE);
                    ucl2_tv.setVisibility(View.GONE);
                }
            } catch (Exception e) {

            }
        }
        String clientOrg = sharedpreferences.getString("CLIENTORG", "");
        String prtnerOrg = sharedpreferences.getString("PARTNERORG", "");
        if (sharedpreferences.getString("USERTYPE", "").equalsIgnoreCase("PARTNER")) {
            testCondoctorOrg.setText("(" + prtnerOrg + ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText("" + prtnerOrg);
        } else {
            testCondoctorOrg.setText("(" + clientOrg + ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText("" + clientOrg);

        }
        plantName.setText("" + clientOrg);
        Log.d("valdoc", "RDAV5UserEnryActivity 1witness=" + witnessFirst);
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testWitness.setText(witness);
    }

//    private String getSamplingTime(String testSpecification, String range) {
//        String samplingTime = "";
//        if (testSpecification.contains("5") || testSpecification.contains("6")|| testSpecification.contains("Grade A")||testSpecification.contains("Grade B")) {
//            if (range.contains("28.3")) {
//                samplingTime = "36 Minute";
//            } else if (range.contains("50")) {
//                samplingTime = "20 Minute";
//            } else if (range.contains("75")) {
//                samplingTime = "14 Minute";
//            } else if (range.contains("100")) {
//                samplingTime = "10 Minute";
//            }else {
//                samplingTime = "1 Minute";
//            }
//        } else {
//            samplingTime = "1 Minute";
//        }
//        return samplingTime;
//    }

    private void initTextView() {
        // layout data which is not in use
        atrest_one = (TextView) findViewById(R.id.atrest_one);
        atrest_two = (TextView) findViewById(R.id.atrest_two);
        instrumentNoTextView = (TextView) findViewById(R.id.instrument_no5);
        instrumentNoTextView.setVisibility(View.GONE);
        testerNameTextView = (TextView) findViewById(R.id.tester_name_test5);
        testerNameTextView.setVisibility(View.GONE);
        instrumentUsedTextView = (TextView) findViewById(R.id.instrument_used);
        instrumentUsedTextView.setVisibility(View.GONE);
        testCunductedByTextView = (TextView) findViewById(R.id.testcunducted_by);
        testCunductedByTextView.setVisibility(View.GONE);

        roomNameLable = (TextView) findViewById(R.id.room_name_lable5);
        roomNameLable.setVisibility(View.GONE);
        instrumentNoLable = (TextView) findViewById(R.id.instrument_no_lable);
        instrumentNoLable.setVisibility(View.GONE);
        roomNameTest = (TextView) findViewById(R.id.room_name5);
        roomNameTest.setVisibility(View.GONE);
        instrument_name = (TextView) findViewById(R.id.instrument_name5);
        instrument_name.setVisibility(View.GONE);

        dateTextView = (TextView) findViewById(R.id.datetextview);
        customerName = (TextView) findViewById(R.id.customer_name);
        certificateNo = (TextView) findViewById(R.id.trd_no);
        instrumentUsed = (TextView) findViewById(R.id.instrumentused);
        samplingFlowTable = (TableRow) findViewById(R.id.aerosol_generator_table);
        samplingFlowTable.setVisibility(View.VISIBLE);
        samplingTimeTable = (TableRow) findViewById(R.id.aerosol_used_table);
        samplingTimeTable.setVisibility(View.VISIBLE);

        samplingFlowRateText = (TextView) findViewById(R.id.aerosol_generator_type_text);
        samplingTimeText = (TextView) findViewById(R.id.aerosol_used_text);

        samplingFlowRate = (TextView) findViewById(R.id.aerosol_generator_type_value);
        samplingTime = (TextView) findViewById(R.id.aerosol_used);
        instrumentSerialNo = (TextView) findViewById(R.id.instrumentserialno);
        calibrationOn = (TextView) findViewById(R.id.calibratedon);
        calibrationDueOn = (TextView) findViewById(R.id.calibrationdueon);
        testspecificationText = (TextView) findViewById(R.id.testspecification_text);
        testspecificationText.setText("Cleanroom Class :");
        cleanRoomClass = (TextView) findViewById(R.id.testspecification);
        plantName = (TextView) findViewById(R.id.plantname);
        areaOfTest = (TextView) findViewById(R.id.areaoftest);
        roomName = (TextView) findViewById(R.id.room_name);
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            equipmentLable = (TextView) findViewById(R.id.equiment_name_text);
            equipmentLable.setVisibility(View.INVISIBLE);
            equipmentNoLable = (TextView) findViewById(R.id.equiment_no_text);
            equipmentNoLable.setVisibility(View.INVISIBLE);
            ahuNoText = (TextView) findViewById(R.id.ahu_no_text);
            ahuNoText.setVisibility(View.VISIBLE);
            ahuNo = (TextView) findViewById(R.id.ahu_no);
            ahuNo.setVisibility(View.VISIBLE);
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
        }

//        equipmentNameText = (TextView) findViewById(R.id.equipment_name_text);
//        equipmentNoText = (TextView) findViewById(R.id.equipment_no_text);
        roomNo = (TextView) findViewById(R.id.room_no);
        roomNo.setVisibility(View.GONE);
        findViewById(R.id.room_no_lable).setVisibility(View.GONE);
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

                            Toast.makeText(RDPCTUserEntryActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RDPCTUserEntryActivity.this, TestCreateActivity.class);
                            intent.putExtra(TestCreateActivity.PCT, true);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RDPCTUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(RDPCTUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RDPCTUserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
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
        testSpesificationValue.setFieldName("Mean Average1");
        testSpesificationValue.setFieldValue("" + meanValue1);
        spesificationValueArrayList.add(testSpesificationValue);


        TestSpesificationValue testSpesificationValue1 = new TestSpesificationValue();
        testSpesificationValue1.setTest_detail_id("" + testDetailsId);
        testSpesificationValue1.setFieldName("Mean Average2");
        testSpesificationValue1.setFieldValue("" + meanValue2);
        spesificationValueArrayList.add(testSpesificationValue1);

        TestSpesificationValue testSpesificationValue2 = new TestSpesificationValue();
        testSpesificationValue2.setTest_detail_id("" + testDetailsId);
        testSpesificationValue2.setFieldName("Standard Deviation1");
        testSpesificationValue2.setFieldValue("" + Math.round(stdDev1));
        spesificationValueArrayList.add(testSpesificationValue2);

        TestSpesificationValue testSpesificationValue3 = new TestSpesificationValue();
        testSpesificationValue3.setTest_detail_id("" + testDetailsId);
        testSpesificationValue3.setFieldName("Standard Deviation2");
        testSpesificationValue3.setFieldValue("" + Math.round(stdDev2));
        spesificationValueArrayList.add(testSpesificationValue3);

        TestSpesificationValue testSpesificationValue4 = new TestSpesificationValue();
        testSpesificationValue4.setTest_detail_id("" + testDetailsId);
        testSpesificationValue4.setFieldName("UCL Value1");
        testSpesificationValue4.setFieldValue("" + ucl1);
        spesificationValueArrayList.add(testSpesificationValue4);

        TestSpesificationValue testSpesificationValue5 = new TestSpesificationValue();
        testSpesificationValue5.setTest_detail_id("" + testDetailsId);
        testSpesificationValue5.setFieldName("UCL Value2");
        testSpesificationValue5.setFieldValue("" + ucl2);
        spesificationValueArrayList.add(testSpesificationValue5);

        TestSpesificationValue testSpesificationValue6 = new TestSpesificationValue();
        testSpesificationValue6.setTest_detail_id("" + testDetailsId);
        testSpesificationValue6.setFieldName("Minimum Value1");
        testSpesificationValue6.setFieldValue("" + minimumValue1_tv.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue6);

        TestSpesificationValue testSpesificationValue7 = new TestSpesificationValue();
        testSpesificationValue7.setTest_detail_id("" + testDetailsId);
        testSpesificationValue7.setFieldName("Minimum Value2");
        testSpesificationValue7.setFieldValue("" + minimumValue2_tv.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue7);

        TestSpesificationValue testSpesificationValue8 = new TestSpesificationValue();
        testSpesificationValue8.setTest_detail_id("" + testDetailsId);
        testSpesificationValue8.setFieldName("Maximum Value1");
        testSpesificationValue8.setFieldValue("" + maximumValue1_tv.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue8);

        TestSpesificationValue testSpesificationValue9 = new TestSpesificationValue();
        testSpesificationValue9.setTest_detail_id("" + testDetailsId);
        testSpesificationValue9.setFieldName("Maximum Value2");
        testSpesificationValue9.setFieldValue("" + maximumValue2_tv.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue9);

        //class value
        TestSpesificationValue testSpesificationValue10 = new TestSpesificationValue();
        testSpesificationValue10.setTest_detail_id("" + testDetailsId);
        testSpesificationValue10.setFieldName("Class");
        testSpesificationValue10.setFieldValue("" + iso_class.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue10);

        TestSpesificationValue testSpesificationValue11 = new TestSpesificationValue();
        testSpesificationValue11.setTest_detail_id("" + testDetailsId);
        testSpesificationValue11.setFieldName("SmallParticle");
        testSpesificationValue11.setFieldValue("" + small_particle.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue11);

        TestSpesificationValue testSpesificationValue12 = new TestSpesificationValue();
        testSpesificationValue12.setTest_detail_id("" + testDetailsId);
        testSpesificationValue12.setFieldName("LargeParticle");
        testSpesificationValue12.setFieldValue("" + large_particle.getText().toString());
        spesificationValueArrayList.add(testSpesificationValue12);

        return spesificationValueArrayList;
    }

    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> testReadingArrayList = new ArrayList<TestReading>();
        int hasMapKey = 200;
//        int loopRow = (rows - 1) * 2;
        int loopRow = rHashMap.size();
        int size=(loopRow/cols);
        for (int i = 1; i <= size; i++) {
            TestReading testReading = new TestReading();
//            testReading.setTestReadingID(i);
//        TO DO test details id is id of test details table
            testReading.setTest_detail_id(testDetailsId);
            testReading.setEntityName("" + i);
            StringBuilder grilList = new StringBuilder();
            //v1,v2....value cration
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < cols; j++) {
                if (j != 0 && rHashMap.get(hasMapKey) != null)
                    sb.append(',');
                sb.append("" + rHashMap.get(hasMapKey));
                hasMapKey++;

            }
            grilList.append(sb).append(",").append(averageResultHashMap.get(i));
            testReading.setValue("" + grilList.toString());
            testReadingArrayList.add(testReading);
        }
        return testReadingArrayList;
    }


    private TestDetails testDetailsDataCreation() {
        TestDetails testDetails = new TestDetails();
//        TO DO: need to make it dynamic
        testDetails.setTest_detail_id(testDetailsId);
        testDetails.setCustomer(customerName.getText().toString());
        int mon = month + 1;
        String date = year + "-" + mon + "-" + day;
        testDetails.setDateOfTest("" + dateTextView.getText());
        testDetails.setRawDataNo(certificateNo.getText().toString());
        testDetails.setPartnerName("" + mPartnerName);
        int partnerId = sharedpreferences.getInt("PARTNERID", 0);
        testDetails.setPartnerId(partnerId);
        testDetails.setTestName(TestCreateActivity.PCT);
        testDetails.setFilterTypeEficiancy("");
        testDetails.setTestLocation("");
        testDetails.setAcceptableRecoveryTime("");
        testDetails.setDiffAVinFilter(0);
        testDetails.setDiffAVbetweenFilter(0);
        if (loginUserType.equals("CLIENT")) {
            testDetails.setInstrumentUsed(clientInstrument.getcInstrumentName());
            testDetails.setMake(clientInstrument.getMake());
            testDetails.setModel(clientInstrument.getModel());
            testDetails.setInstrumentNo(clientInstrument.getSerialNo());
            testDetails.setCalibratedOn(clientInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn(clientInstrument.getCalibrationDueDate());
            testDetails.setAerosolUsed("");
            testDetails.setAerosolGeneratorType("");
            testDetails.setSamplingFlowRate("" + samplingFlowRate.getText().toString());
            testDetails.setSamplingTime("" + samplingTime.getText().toString());
        } else {
            testDetails.setInstrumentUsed(partnerInstrument.getpInstrumentName());
            testDetails.setMake(partnerInstrument.getMake());
            testDetails.setModel(partnerInstrument.getModel());
            testDetails.setInstrumentNo("" + partnerInstrument.getSerialNo());
            testDetails.setCalibratedOn(partnerInstrument.getLastCalibrationDate());
            testDetails.setCalibratedDueOn(partnerInstrument.getCalibrationDueDate());
            testDetails.setAerosolUsed("");
            testDetails.setAerosolGeneratorType("");
            testDetails.setSamplingFlowRate("" + samplingFlowRate.getText().toString());
            testDetails.setSamplingTime("" + samplingTime.getText().toString());
        }
        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            testDetails.setEquipmentName("" + equipmentName.getText().toString());
            testDetails.setEquipmentNo("" + equipmentNo.getText().toString());
            testDetails.setAhuNo("");
        } else {
            testDetails.setEquipmentName("");
            testDetails.setEquipmentNo("");
            testDetails.setAhuNo(ahuNo.getText().toString());
        }

        testDetails.setTestSpecification(cleanRoomClass.getText().toString());
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
        testDetails.setAerosolGeneratorType("");
        testDetails.setAerosolUsed("");
        testDetails.setTestItem("");
        testDetails.setTestWitnessOrg("" + testWitnessOrg.getText());
        testDetails.setTestCondoctorOrg("" + testCondoctorOrg.getText());
        testDetails.setRoomVolume("");
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testDetails.setWitnessName("" + witness);
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
            }
        }


    }

    private void BuildTableTest5(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {

                    TextView textView = addTextView(" Location ");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    int position = i - 1;
                    TextView textView = addTextView(" " + position);
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                }

            }
            test5_table_layout.addView(row);
        }

        TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        TextView tvs = addTextView(" No. of Particles ≥ 0.5 µm/m³ ");
        ViewGroup.LayoutParams params11 = tvs.getLayoutParams();
        params11.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
        tvs.setLayoutParams(params11);
        tvs.setEms(Utilityies.getPctCellEms(cols));
        tvs.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        row1.addView(tvs);

        test5_tableLayout2_2.addView(row1);

        for (int i = 1; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop 1
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    TextView textView = addTextView(" R " + j);
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
                    textView.setLayoutParams(layoutParams);
                    textView.setEms(Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
                } else {
                    //row.addView(addTextView(" 4434 | 3434 | 1341 "));
                    TextView textView = addInputDataTextView();
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
                    textView.setLayoutParams(layoutParams);
                    textView.setEms(Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
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
        for (int i = 1; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    TextView textView = addTextView(" Average ");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    TextView textView = addResultTextView(i);
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
                    textView.setLayoutParams(layoutParams);
                    textView.setEms(Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
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
        TextView tvs1 = addTextView(" No. of Particles ≥ 5 µm/m³  ");
        ViewGroup.LayoutParams params1 = tvs1.getLayoutParams();
        params1.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
        tvs1.setLayoutParams(params1);
        tvs1.setEms(Utilityies.getPctCellEms(cols));
        row2.addView(tvs1);
        test5_tableLayout4_2.addView(row2);

        //Fourth section
        // outer for loop
        for (int i = 1; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop 1
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    TextView textView = addTextView(" R " + j);
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
                    textView.setLayoutParams(layoutParams);
                    textView.setEms(Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
                } else {
                    TextView textView = addInputDataTextView();
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
                    textView.setLayoutParams(layoutParams);
                    textView.setEms(Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
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
        for (int i = 1; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    TextView textView = addTextView("Average");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    TextView textView = addResultTextView(rows + i);
                    ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
                    layoutParams.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
                    textView.setLayoutParams(layoutParams);
                    textView.setEms(Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
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
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEms(4);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    private TextView addStretchedTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
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
                                           ArrayList<TextView> txtList, float value) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border);
        tv.setGravity(Gravity.CENTER);
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
        txtList.add(tv);
        return tv;
    }

    int idCountTv = 1;

    private TextView addResultTextView(int rowsNo) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(3, 3, 3, 3);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
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
        tv.setPadding(3, 3, 3, 3);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
        tv.setId(idCountEtv);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEms(4);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        txtViewList.add(tv);
        idCountEtv++;
        return tv;
    }

    private void initRes() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
        headerText2 = (TextView) findViewById(R.id.common_header_2_tv);
        if ("ROOM".equalsIgnoreCase(mTestBasedOn)) {
            headerText2.setVisibility(View.VISIBLE);
            headerText.setText("TEST RAW DATA ");
            headerText2.setText("Airborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices");
        } else {
            headerText2.setVisibility(View.VISIBLE);
            headerText.setText("TEST RAW DATA EQUIPMENT");
            headerText2.setText("Airborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices");
        }
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
        // PCT new footer
        meanValue1_tv = (TextView) findViewById(R.id.pct_mean_value1);
        meanValue2_tv = (TextView) findViewById(R.id.pct_mean_value2);
        uclId = (TextView) findViewById(R.id.ucl_id);
        standerdeviationText = (TextView) findViewById(R.id.standerdeviation_text);
        stdDev1_tv = (TextView) findViewById(R.id.pct_std_dev1);
        stdDev2_tv = (TextView) findViewById(R.id.pct_std_dev2);
        ucl1_tv = (TextView) findViewById(R.id.pct_95_ucl_1);
        ucl2_tv = (TextView) findViewById(R.id.pct_95_ucl_2);
        small_particle = (TextView) findViewById(R.id.small_particle);
        large_particle = (TextView) findViewById(R.id.large_particle);
        iso_class = (TextView) findViewById(R.id.iso_class);
        minimumValue1_tv = (TextView) findViewById(R.id.pct_minimum_value_1);
        minimumValue2_tv = (TextView) findViewById(R.id.pct_minimum_value_2);
        maximumValue1_tv = (TextView) findViewById(R.id.pct_maximum_value_1);
        maximumValue2_tv = (TextView) findViewById(R.id.pct_maximum_value_2);
        if (getIntent().hasExtra("testBasedOn") && "ROOM".equalsIgnoreCase(getIntent().getStringExtra("testBasedOn"))) {
            findViewById(R.id.ahu_no_lable).setVisibility(View.VISIBLE);
            roomNo.setVisibility(View.VISIBLE);
            findViewById(R.id.room_no_lable).setVisibility(View.VISIBLE);
        }

    }
}
