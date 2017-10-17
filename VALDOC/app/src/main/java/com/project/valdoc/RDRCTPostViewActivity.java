package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RDRCTPostViewActivity extends AppCompatActivity {

    TextView headerText,headerText2;
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
    private TextView samplingTimeLable;
    private TextView samplingFlowRateLable;
    //private TextView samplingTime;
    private TextView samplingFlowRate;
    private TableRow samplingFlowTable;
    private TableRow samplingTimeTable;
    private TextView samplingFlowRateText;
    private TextView samplingTimeText;
    private TextView samplingTime;

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
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDRCTPostViewActivity.this);
    SharedPreferences sharedpreferences;
    int testDetailsId = 0;
    String mInitialReading;
    String mWorstReading;
    String mFinalReading;
    int mCount;
    private String mPartnerName;
    private String mTestCode = "";
    private String testType;
    private String mTestBasedOn;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    private ArrayList<TestReading> testReadingArrayList;
    private ArrayList<TestSpesificationValue> testSpesificationValueArrayList;
    private TestDetails mTestDetails;
    String spiltValue[] = null;
    private int testDetailId;
    private  TextView recovery_time_tv,aerosol_used_rct_tv,aerosol_gen_rct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdrctuser_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mInputValue = new HashMap<Integer, Integer>();

        // Shared values vial intent
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);
        testDetailId = getIntent().getIntExtra("testDetailId", 1);
        mTestBasedOn = getIntent().getStringExtra("TestBasedOn");
        testType = getIntent().getStringExtra("testType");
        Log.d(TAG, " TestType : " + testType +" mTestBasedOn "+mTestBasedOn + " testDetailId "+testDetailId);
        //text view initialization
        initRes();

        //dynamic data population
        testReadingArrayList = mValdocDatabaseHandler.getTestReadingDataById(testDetailId + "");
        mTestDetails = mValdocDatabaseHandler.getTestDetailById(testDetailId);
        testSpesificationValueArrayList = mValdocDatabaseHandler.getTestSpecificationValueById(testDetailId + "");
        spiltValue = testReadingArrayList.get(0).getValue().split(",");

        initTextView();
        textViewValueAssignment();
        datePicker();

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDRCTPostViewActivity.this, mActionBar, userName);
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
        instrumentUsed.setText(mTestDetails.getInstrumentUsed());
        instrumentSerialNo.setText("" + mTestDetails.getInstrumentNo());
        calibrationOn.setText(mTestDetails.getCalibratedOn());
        calibrationDueOn.setText(mTestDetails.getCalibratedDueOn());
        samplingFlowRate.setText(mTestDetails.getSamplingFlowRate());
        samplingTime.setText(mTestDetails.getSamplingTime());

        initialReading.setText("" + testSpesificationValueArrayList.get(0).getFieldValue());
        worstCase.setText("" + testSpesificationValueArrayList.get(1).getFieldValue());
        finalReading.setText("" + testSpesificationValueArrayList.get(2).getFieldValue());
        recoveryTime.setText("" + testSpesificationValueArrayList.get(3).getFieldValue());

        areaOfTest.setText(mTestDetails.getTestArea());
        samplingFlowRateText.setText("Sampling Flow Rate :");
        samplingTimeText.setText("Sampling Time :");
        recovery_time_tv.setText(mTestDetails.getAcceptableRecoveryTime());
        aerosol_used_rct_tv.setText(mTestDetails.getAerosolUsed());
        aerosol_gen_rct.setText(mTestDetails.getAerosolGeneratorType());

        if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            roomName.setText(mTestDetails.getRoomName());
            testRefrance.setText(mTestDetails.getTestReference());
            roomNo.setText(mTestDetails.getRoomNo());
            ahuNo.setText(mTestDetails.getAhuNo());
            occupancyState.setText(mTestDetails.getOccupencyState().toString());
            samplingTime.setText("" + mTestDetails.getSamplingTime());
            samplingFlowRate.setText("" + mTestDetails.getSamplingFlowRate());
            cleanRoomClass.setText(" " + mTestDetails.getTestSpecification());
        } else if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            equipmentName.setText(mTestDetails.getEquipmentName());
            equipmentNo.setText(mTestDetails.getEquipmentNo());
            roomName.setText(mTestDetails.getRoomName());
            testRefrance.setText(mTestDetails.getTestReference());
            // room no not needed
            roomNo.setText(mTestDetails.getRoomNo());
            ahuNo.setText(mTestDetails.getAhuNo());
            occupancyState.setText(mTestDetails.getOccupencyState().toString());
            samplingTime.setText("" + mTestDetails.getSamplingTime());
            samplingFlowRate.setText("" + mTestDetails.getSamplingFlowRate());
            cleanRoomClass.setText(" " + mTestDetails.getTestSpecification());

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
            plantName.setText(""+mTestDetails.getBlockName());
        }


        testCundoctor.setText(mTestDetails.getTesterName());
        testCondoctorOrg.setText(mTestDetails.getTestCondoctorOrg());
        testWitnessOrg.setText(mTestDetails.getTestWitnessOrg());
        customerName.setText(mTestDetails.getCustomer());
        plantName.setText(mTestDetails.getBlockName());
        testWitness.setText(mTestDetails.getWitnessName());
    }


    private void initTextView() {
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

        samplingFlowRateText = (TextView) findViewById(R.id.aerosol_generator_type_text);
        samplingTimeText = (TextView) findViewById(R.id.aerosol_used_text);
        samplingFlowRate = (TextView) findViewById(R.id.aerosol_generator_type_value);
        samplingTime = (TextView) findViewById(R.id.aerosol_used);

        testspecificationText = (TextView) findViewById(R.id.testspecification_text);
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
        testCundoctor = (TextView) findViewById(R.id.testcunducter);
        testWitness = (TextView) findViewById(R.id.testwitness);
        testCondoctorOrg = (TextView) findViewById(R.id.test_condoctor_org);
        testWitnessOrg = (TextView) findViewById(R.id.testwitness_org);
        submit = (ImageView) findViewById(R.id.submit);
        submit.setVisibility(View.GONE);
        clear = (ImageView) findViewById(R.id.clear);
        clear.setVisibility(View.GONE);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        findViewById(R.id.common_certificate_header_ll).setVisibility(View.VISIBLE);
    }


    private void initRes() {
        headerText = (TextView) findViewById(R.id.common_header_tv);
        headerText2 = (TextView) findViewById(R.id.common_header_2_tv);
        findViewById(R.id.recovery_time_rct).setVisibility(View.VISIBLE);
        findViewById(R.id.aerosol_used_rct).setVisibility(View.VISIBLE);
        findViewById(R.id.aerosol_gen_table_rct).setVisibility(View.VISIBLE);
        recovery_time_tv = (TextView) findViewById(R.id.recovery_time_tv);
        aerosol_used_rct_tv = (TextView) findViewById(R.id.aerosol_used_rct_tv);
        aerosol_gen_rct = (TextView) findViewById(R.id.aerosol_gen_rct);
        equipmentLable = (TextView) findViewById(R.id.equiment_name_text);
        equipmentNoLable = (TextView) findViewById(R.id.equiment_no_text);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            headerText2.setVisibility(View.VISIBLE);
            headerText.setText("TEST RAW DATA (RD_RCT)");
            headerText2.setText("Recovery Performance Test");
            equipmentLable.setVisibility(View.GONE);
            equipmentNoLable.setVisibility(View.GONE);
            findViewById(R.id.ahu_no_lable).setVisibility(View.VISIBLE);
            ahuNoText = (TextView) findViewById(R.id.ahu_no_text);
            ahuNoText.setVisibility(View.VISIBLE);
            ahuNo.setVisibility(View.VISIBLE);
        }else{
            headerText2.setVisibility(View.VISIBLE);
            headerText.setText("TEST RAW DATA EQUIPMENT");
            headerText2.setText("Recovery Performance Test");
            equipmentLable.setVisibility(View.VISIBLE);
            equipmentName = (TextView) findViewById(R.id.equiment_name);
            equipmentName.setVisibility(View.VISIBLE);
            equipmentNoLable.setVisibility(View.VISIBLE);
            equipmentNo = (TextView) findViewById(R.id.equiment_no);
            equipmentNo.setVisibility(View.VISIBLE);
            findViewById(R.id.room_no_ahu_fit).setVisibility(View.GONE);
        }
    }
}
