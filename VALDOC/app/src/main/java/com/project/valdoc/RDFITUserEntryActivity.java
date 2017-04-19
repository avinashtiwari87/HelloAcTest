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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RDFITUserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDFITUser";
    TextView headerText,headerText_2;
    TableLayout test4_table_layout, test4_table_layout2, test4_table_layout3, test4_table_layout4,
            test4_table_layout5, test4_table_layout6, test4_table_layout7, test4_table_layout8;
    LinearLayout common_certificate_header;

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
    private String mTestItem;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private String userName = "";

    //certificate view id creation
    private TextView instrumentUsed;
    private TextView instrumentSerialNo;
    private TextView calibrationOn;
    private TextView calibrationDueOn;
    private TableRow aerosolGeneratorTable;
    private TableRow aerosolUsedTable;
    private TextView testSpecification;
    private String mTestSpecification;
    private String mshowSpesification;
    private TextView aerosolGeneratorType;
    private TextView aerosolUsed;
    private TextView plantName;
    private TextView areaOfTest;
    private TextView roomName;
    private TextView occupancyState;
    private TextView testRefrance;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    private TextView equipmentNameText;
    private TextView equipmentNoText;
    private TextView roomNo;
    private TextView ahuNo;
    private TextView testLocationText;
    private TextView testLocation;
    private TextView filterTypeEficiancyText;
    private TextView filtertypeEficiancy;
    private TextView ahuNoText;
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
    private TextView room_no_lable;
    private TextView roomNameTest;
    private TextView instrument_name;
    private TextView equipmentLable;
    private TextView equipmentName;
    private TextView equipmentNoLable;
    private TextView equipmentNo;
    ArrayList<TextView> txtViewList;
    ArrayList<TextView> txtPassFailList;
    ArrayList<TextView> txtConcentrationVariationList;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;
    private String mPartnerName;
    HashMap<Integer, Double> likageDataMap;
    HashMap<Integer, BigDecimal> showFitInputDataHashMap;
    HashMap<Integer, Long> PassFailHashMap;
    ArrayList<Double>concentrationVariationListData;
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDFITUserEntryActivity.this);
    SharedPreferences sharedpreferences;
    int testDetailsId = 0;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    private String mDate="";

    private String testType;
    private String mTestCode = "";
    private String mTestBasedOn;
    private String[] roomDetails;
    private Equipment equipment;
    private ArrayList<AhuFilter> mAhuFilterArrayList = null;
    private ApplicableTestAhu mApplicableTestAhu = null;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ApplicableTestEquipment mApplicableTestEquipment = null;
    private ArrayList<EquipmentFilter> mEquipmentFilterArrayList = null;
    private ArrayList<RoomFilter> mRoomFilterArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdfituser_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //init res file from xml
        initRes();

        // Init objects
        txtPassFailList = new ArrayList<TextView>();
        txtConcentrationVariationList = new ArrayList<TextView>();
        txtViewList = new ArrayList<TextView>();
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);

        // Get data from test create screen
        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            mTestType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + mTestType);
        }
        mTestCode = getIntent().getStringExtra("testCode");

        //dynamic data population
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        textViewValueAssignment();
        datePicker();

        //Creating Tables based on rows and columns
        //if (rows > 0 && cols > 0) {
            pr = ProgressDialog.show(this, "Please Wait", "Loading...");
            pr.setCanceledOnTouchOutside(true);
            pr.setCancelable(true);

            buildTestFour(rows, cols);


            //Receiving User Input Data from Bundle
            likageDataMap = (HashMap<Integer, Double>) getIntent().getSerializableExtra("InputData");
            int k=0;
            int count=0;
            int constVal=((likageDataMap.size()/3)*2);
            int kk=200;
            for (Map.Entry m : likageDataMap.entrySet()) {
                if(count>=constVal){
                    TextView tvl = txtPassFailList.get(k);
                    Double spesification = getSpecification(mshowSpesification);
                    if (spesification >= Double.parseDouble(likageDataMap.get(kk).toString())) {
                        tvl.setText("PASS");
                    } else {
                        tvl.setText("FAIL");
                    }
                    k++;
                    kk++;
                }
                count++;
                Log.v(TAG, " InputData " + m.getKey() + " " + m.getValue());
            }

            showFitInputDataHashMap = (HashMap<Integer, BigDecimal>) getIntent().getSerializableExtra("ShowInputData");
            for (int i = 0; i < txtViewList.size(); i++) {
                TextView tvl = txtViewList.get(i);
                Log.d("Saurabh", "Saurabh likageValue : "+showFitInputDataHashMap.get(tvl.getId()) );
                if(tvl.getId() >= 800){
                    tvl.setText(showFitInputDataHashMap.get(tvl.getId()) + "");
                }else{
//                tvl.setText(likageDataMap.get(tvl.getId())) + "");
                    tvl.setText(""+showFitInputDataHashMap.get(tvl.getId()));
                }

            }
            //Receiving Pass Fail Data from Bundle
            PassFailHashMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("PassFailData");
            for (Map.Entry O : PassFailHashMap.entrySet()) {
                Log.v(TAG, " PassFail " + O.getKey() + " " + O.getValue());
            }
            int passFlag = 1;
            for (int i = 0; i < txtPassFailList.size(); i++) {
                TextView tvl = txtPassFailList.get(i);
                if ("PASS".equalsIgnoreCase(tvl.getText().toString().trim())) {
                    tvl.setTextColor(getResources().getColor(R.color.blue));
                } else {
                    passFlag = 0;
                    tvl.setTextColor(getResources().getColor(R.color.red));
                }
            }

            // Getting ConcentrationVariation Data
            concentrationVariationListData = (ArrayList<Double>)getIntent().getSerializableExtra("InputDataVariation");
            for (int vr = 0; vr <concentrationVariationListData.size() ; vr++) {
                Log.v(TAG, " InputDataVariation  "+concentrationVariationListData.get(vr).toString());
            }
            for (int i = 0; i < txtConcentrationVariationList.size(); i++) {
                TextView tvl = txtConcentrationVariationList.get(i);
                tvl.setText(concentrationVariationListData.get(i)+ " %");
                String pasFailCheck = String.valueOf(PassFailHashMap.get(300+i));
                Log.d(TAG, "Saurabh PassRedBlack "+pasFailCheck);
                if("PASS".equalsIgnoreCase(pasFailCheck.trim())){
                    tvl.setTextColor(getResources().getColor(R.color.black));
                }else{
                    tvl.setTextColor(getResources().getColor(R.color.red));
                }
            }
/*        }else {
            Utilityies.showAlert(RDFITUserEntryActivity.this, getResources().getString(R.string.table_not_created));
        }*/

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDFITUserEntryActivity.this, mActionBar, userName);
    }

    private double getSpecification(String valueFromDB) {
        double returnValue = 0.1;
        try {
            Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
            Matcher matcher = regex.matcher(valueFromDB);
            while(matcher.find()){
                System.out.println("Specific Value... "+matcher.group(1));
                returnValue = Double.valueOf(matcher.group(1));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private void calSpesifiCation() {
        int len = mTestSpecification.length();
        mshowSpesification = (String) mTestSpecification.toString();
                //.subSequence(28, (len - 1));
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
        ////
        Date d = new Date(year, month, day);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String entered_dob = dateFormatter.format(d);
        // Show current date
         mDate = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
        dateTextView.setText(""+mDate);
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
Date d;
        SimpleDateFormat dateFormatter;
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            d = new Date(year, month, day);
            dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
            String entered_dob = dateFormatter.format(d);
            // Show selected date
             mDate = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
            dateTextView.setText(""+mDate);
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
        } else {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
            instrumentSerialNo.setText("" + partnerInstrument.getpInstrumentId());
            calibrationOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getLastCalibrationDate()));
            calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(partnerInstrument.getCalibrationDueDate()));
        }
        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mApplicableTestEquipment.getTestProp());
                aerosolUsed.setText(jsonObject.optString("AEROSOL_USED"));
                aerosolGeneratorType.setText(jsonObject.optString("AEROSOL_GENERATOR_TYPE"));
            } catch (Exception e) {

            }
            filtertypeEficiancy.setText(mEquipmentFilterArrayList.get(0).getFilterType()+" & "+mEquipmentFilterArrayList.get(0).getEfficiency()+"%");
//            testLocationText.setText(mEquipmentFilterArrayList.get(0).getf());
            equipmentName.setText(equipment.getEquipmentName());
            equipmentNo.setText(equipment.getEquipmentNo());
            mTestSpecification=mApplicableTestEquipment.getTestSpecification();
            calSpesifiCation();
            testSpecification.setText("Max "+mshowSpesification+"%");
            occupancyState.setText(mApplicableTestEquipment.getOccupencyState());
            testRefrance.setText(mApplicableTestEquipment.getTestReference());
            roomName.setText(roomDetails[1]);
            // room no not needed
            roomNo.setText(roomDetails[2]);
            areaOfTest.setText(areaName);
        } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mApplicableTestAhu.getTestProp());
                aerosolUsed.setText(jsonObject.optString("AEROSOL_USED"));
                aerosolGeneratorType.setText(jsonObject.optString("AEROSOL_GENERATOR_TYPE"));
                filtertypeEficiancy.setText(jsonObject.optString("Filter_Type")+" & "+jsonObject.optString("Filter_Efficiency"));
            } catch (Exception e) {

            }

            mTestSpecification=mApplicableTestAhu.getTestSpecification();
            calSpesifiCation();
            testSpecification.setText("Max "+mshowSpesification);
            occupancyState.setText(mApplicableTestAhu.getOccupencyState());
            testRefrance.setText(mApplicableTestAhu.getTestReference());
            areaOfTest.setText(areaName);
            roomName.setText(mTestItem);
            // room no not needed
//            roomNo.setText(roomDetails[2]);
            ahuNo.setText(ahuNumber);
        } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(mApplicableTestRoom.getTestProp());
                aerosolUsed.setText(jsonObject.optString("AEROSOL_USED"));
                aerosolGeneratorType.setText(jsonObject.optString("AEROSOL_GENERATOR_TYPE"));
            } catch (Exception e) {

            }
            filtertypeEficiancy.setText(mRoomFilterArrayList.get(0).getFilterType()+" & "+mRoomFilterArrayList.get(0).getEfficiency()+"%");
            testLocation.setText(mRoomFilterArrayList.get(0).getFilterLocation());
            mTestSpecification=mApplicableTestRoom.getTestSpecification();
            calSpesifiCation();
            testSpecification.setText("Max "+mshowSpesification);
            occupancyState.setText(mApplicableTestRoom.getOccupencyState());
            testRefrance.setText(mApplicableTestRoom.getTestReference());
            areaOfTest.setText(areaName);
            roomName.setText(room.getRoomName().toString());
            roomNo.setText(room.getRoomNo().toString());
            ahuNo.setText(ahuNumber);
        }

        ///Common data
        testCundoctor.setText(userName);
        if (sharedpreferences.getString("USERTYPE", "").equalsIgnoreCase("CLIENT")) {
            testCondoctorOrg.setText("(" + sharedpreferences.getString("CLIENTORG", "") + ")");
            testWitnessOrg.setText("(" + sharedpreferences.getString("CLIENTORG", "") + ")");
            customerName.setText(""+ sharedpreferences.getString("CLIENTORG", ""));
        } else {
            testCondoctorOrg.setText("(" + sharedpreferences.getString("PARTNERORG", "") + ")");
            testWitnessOrg.setText("(" + sharedpreferences.getString("CLIENTORG", "") + ")");
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
        certificateNo = (TextView) findViewById(R.id.trd_no);
        instrumentUsed = (TextView) findViewById(R.id.instrumentused);
        instrumentSerialNo = (TextView) findViewById(R.id.instrumentserialno);
        calibrationOn = (TextView) findViewById(R.id.calibratedon);
        calibrationDueOn = (TextView) findViewById(R.id.calibrationdueon);
        aerosolGeneratorTable= (TableRow) findViewById(R.id.aerosol_generator_table);
        aerosolGeneratorTable.setVisibility(View.VISIBLE);
        aerosolUsedTable= (TableRow) findViewById(R.id.aerosol_used_table);
        aerosolUsedTable.setVisibility(View.VISIBLE);
        aerosolGeneratorType= (TextView) findViewById(R.id.aerosol_generator_type_value);
        aerosolUsed= (TextView) findViewById(R.id.aerosol_used);
        testLocationText=(TextView) findViewById(R.id.room_volume_text);
        testLocationText.setText("Test Location :");
        testLocation=(TextView) findViewById(R.id.room_volume);
        if(mTestBasedOn.equalsIgnoreCase("ROOM")||mTestBasedOn.equalsIgnoreCase("AHU")){
            equipmentNameText = (TextView) findViewById(R.id.equiment_name_text);
            equipmentNameText.setVisibility(View.INVISIBLE);
            equipmentNoText = (TextView) findViewById(R.id.equiment_no_text);
            equipmentNoText.setVisibility(View.INVISIBLE);
            ahuNoText = (TextView) findViewById(R.id.ahu_no_text);
            ahuNoText.setVisibility(View.VISIBLE);
            ahuNo = (TextView) findViewById(R.id.ahu_no);
            ahuNo.setVisibility(View.VISIBLE);
        }

        testSpecification = (TextView) findViewById(R.id.testspecification);
        plantName = (TextView) findViewById(R.id.plantname);
        areaOfTest = (TextView) findViewById(R.id.areaoftest);
        roomName = (TextView) findViewById(R.id.room_name);
        room_no_lable=(TextView)findViewById(R.id.room_no_lable);
        roomNo = (TextView) findViewById(R.id.room_no);
        if(mTestBasedOn.equalsIgnoreCase("AHU")){
            findViewById(R.id.room_volume_table).setVisibility(View.VISIBLE);
            roomNo.setVisibility(View.INVISIBLE);
            testLocationText.setVisibility(View.GONE);
            testLocation.setVisibility(View.GONE);
        }
        if(mTestBasedOn.equalsIgnoreCase("ROOM")){
            testLocationText.setVisibility(View.VISIBLE);
            testLocation.setVisibility(View.VISIBLE);
        }
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            findViewById(R.id.room_volume_table).setVisibility(View.VISIBLE);
            findViewById(R.id.test_item_table).setVisibility(View.VISIBLE);
            equipmentLable = (TextView) findViewById(R.id.equiment_name_text);
            equipmentLable.setVisibility(View.VISIBLE);
            equipmentName = (TextView) findViewById(R.id.equiment_name);
            equipmentName.setVisibility(View.VISIBLE);
            equipmentNoLable = (TextView) findViewById(R.id.equiment_no_text);
            equipmentNoLable.setVisibility(View.VISIBLE);
            equipmentNo = (TextView) findViewById(R.id.equiment_no);
            equipmentNo.setVisibility(View.VISIBLE);
            testLocationText.setVisibility(View.GONE);
            testLocation.setVisibility(View.GONE);
            roomNo.setVisibility(View.GONE);
            room_no_lable.setVisibility(View.GONE);
        }

        filterTypeEficiancyText=(TextView) findViewById(R.id.test_item_text);
        filterTypeEficiancyText.setVisibility(View.VISIBLE);
        filterTypeEficiancyText.setText("Filter Type & Efficiency :");
        filtertypeEficiancy=(TextView) findViewById(R.id.test_item_value);
        filtertypeEficiancy.setVisibility(View.VISIBLE);

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

//        dateTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // On button click show datepicker dialog
//                showDialog(DATE_PICKER_ID);
//            }
//        });
    }


    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> testReadingArrayList = new ArrayList<TestReading>();
        int index = 0;
        int hasMapKey = 200;
        int hasStreamBefore = 800;
        int hashstreamAfter = 900;
        int passHasMapKey = 300;
        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {

            for (EquipmentFilter equipmentFilter : mEquipmentFilterArrayList) {
                TestReading testReading = new TestReading();
//            testReading.setTestReadingID(index);
                testReading.setTest_detail_id(testDetailsId);
                testReading.setEntityName(equipmentFilter.getFilterCode());
                //String likage=BigDecimal.valueOf(likageDataMap.get(hasMapKey)).toPlainString();
                String likage=showFitInputDataHashMap.get(hasMapKey).toPlainString();
                Log.d("likage","likage="+likage);
                StringBuilder grilList = new StringBuilder();
                grilList.append(equipmentFilter.getFilterCode()).append(",").append(showFitInputDataHashMap.get(hasStreamBefore)).append(",")
                        .append(showFitInputDataHashMap.get(hashstreamAfter)).append(",").append(""+concentrationVariationListData.get(index).toString()).append(",")
                        .append(likage).append(",").append(txtPassFailList.get(index).getText().toString());
//                grilList.append(equipmentFilter.getFilterType()).append(',').append(equipmentFilter.getEfficiency()).append(",").append(likageDataMap.get(hasStreamBefore)).append(",")
//                        .append(likageDataMap.get(hashstreamAfter)).append(",")//.append(equipmentFilter.getSpecification()).append(",")
//                        .append(likageDataMap.get(hasMapKey)).append(",").append(PassFailHashMap.get(passHasMapKey));
                hasStreamBefore++;
                hashstreamAfter++;
                passHasMapKey++;
                hasMapKey++;
                index++;
                testReading.setValue(grilList.toString());
                testReadingArrayList.add(testReading);
            }
        } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
            for (AhuFilter ahuFilter : mAhuFilterArrayList) {
                TestReading testReading = new TestReading();
//            testReading.setTestReadingID(index);
                testReading.setTest_detail_id(testDetailsId);
                testReading.setEntityName(ahuFilter.getFilterCode());
                //String likage=BigDecimal.valueOf(likageDataMap.get(hasMapKey)).toPlainString();
                String likage=showFitInputDataHashMap.get(hasMapKey).toPlainString();
                Log.d("likage","likage="+likage);
                StringBuilder grilList = new StringBuilder();
                grilList.append(ahuFilter.getFilterCode()).append(',').append(showFitInputDataHashMap.get(hasStreamBefore)).append(",")
                        .append(showFitInputDataHashMap.get(hashstreamAfter)).append(",").append(""+concentrationVariationListData.get(index).toString()).append(",")
                        .append(likage).append(",").append(txtPassFailList.get(index).getText().toString());
                hasStreamBefore++;
                hashstreamAfter++;
                passHasMapKey++;
                hasMapKey++;
                index++;
                testReading.setValue(grilList.toString());
                testReadingArrayList.add(testReading);
            }
        }else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
            for (RoomFilter roomFilter : mRoomFilterArrayList) {
                TestReading testReading = new TestReading();
//            testReading.setTestReadingID(index);
                testReading.setTest_detail_id(testDetailsId);
                testReading.setEntityName(roomFilter.getFilterCode());
                //String likage=BigDecimal.valueOf(likageDataMap.get(hasMapKey)).toPlainString();
                String likage=showFitInputDataHashMap.get(hasMapKey).toPlainString();
                Log.d("likage","likage="+likage);
                StringBuilder grilList = new StringBuilder();
                Log.d("Saurabh", " getFilterType "+roomFilter.getFilterType());
                Log.d("Saurabh", " Before "+showFitInputDataHashMap.get(hasStreamBefore));
                Log.d("Saurabh", " After "+showFitInputDataHashMap.get(hashstreamAfter));
                grilList.append(roomFilter.getFilterType()).append(',').append(",").append(showFitInputDataHashMap.get(hasStreamBefore)).append(",")
                        .append(showFitInputDataHashMap.get(hashstreamAfter)).append(",").append(""+concentrationVariationListData.get(index).toString()).append(",")
                        .append(likage).append(",").append(txtPassFailList.get(index).getText().toString());

//                grilList.append(roomFilter.getFilterType()).append(',').append(roomFilter.getEfficiency()).append(",").append(likageDataMap.get(hasStreamBefore)).append(",")
//                        .append(likageDataMap.get(hashstreamAfter)).append(",").append(roomFilter.getSpecification()).append(",")
//                        .append(likageDataMap.get(hasMapKey)).append(",").append(PassFailHashMap.get(passHasMapKey));

                hasStreamBefore++;
                hashstreamAfter++;
                passHasMapKey++;
                hasMapKey++;
                index++;
                testReading.setValue(grilList.toString());
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
        String date = Integer.toString(year) + "-" + Integer.toString((month + 1)) + "-" + Integer.toString(day) + " ";
//        testDetails.setDateOfTest(date);
        testDetails.setDateOfTest("" + dateTextView.getText().toString());
//        testDetails.setDateOfTest("" + date);
        testDetails.setPartnerName("" + mPartnerName);
        testDetails.setRawDataNo(certificateNo.getText().toString());
        testDetails.setTestName(mTestCode);
        testDetails.setAcceptableRecoveryTime("");
        if (loginUserType.equals("CLIENT")) {
            testDetails.setInstrumentUsed(instrumentUsed.getText().toString());
            testDetails.setMake("" + clientInstrument.getMake().toString());
            testDetails.setModel("" + clientInstrument.getModel().toString());
            testDetails.setInstrumentNo("" + instrumentSerialNo.getText());
            testDetails.setCalibratedOn("" + clientInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn("" + clientInstrument.getCalibrationDueDate());
        } else {
            testDetails.setInstrumentUsed("" + partnerInstrument.getpInstrumentName());
            testDetails.setMake("" + partnerInstrument.getMake());
            testDetails.setModel("" + partnerInstrument.getModel());
            testDetails.setInstrumentNo("" + partnerInstrument.getpInstrumentId());
            testDetails.setCalibratedOn("" + partnerInstrument.getLastCalibrationDate());
            testDetails.setCalibratedDueOn("" + partnerInstrument.getCalibrationDueDate());

        }
        testDetails.setSamplingFlowRate("");
        testDetails.setSamplingTime("");
        Log.d("setAerosolUsed",""+ aerosolUsed.getText());
        Log.d("setAerosolUsed","aerosolGeneratorType"+""+ aerosolGeneratorType.getText());
        testDetails.setAerosolUsed("" + aerosolUsed.getText());
        testDetails.setAerosolGeneratorType("" + aerosolGeneratorType.getText());

        testDetails.setTestSpecification(testSpecification.getText().toString());
        testDetails.setBlockName(plantName.getText().toString());
        testDetails.setTestArea(areaOfTest.getText().toString());
        testDetails.setFilterTypeEficiancy(""+filtertypeEficiancy.getText().toString());
        testDetails.setOccupencyState(occupancyState.getText().toString());
        testDetails.setTestReference(testRefrance.getText().toString());
        testDetails.setTesterName(testCundoctor.getText().toString());
        testDetails.setTestCode(mTestCode);
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append("," + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append("," + witnessThird);
        testDetails.setWitnessName("" + witness);
        if(mTestBasedOn.equalsIgnoreCase("AHU")){
            testDetails.setTestItem("" + mTestItem);
//            testDetails.setFilterTypeEficiancy(""+filtertypeEficiancy.getText().toString());
            testDetails.setRoomName("");
            testDetails.setRoomNo("");
        }else{
            testDetails.setRoomName(roomName.getText().toString());
            testDetails.setRoomNo(roomNo.getText().toString());
            testDetails.setTestItem("");
//            testDetails.setFilterTypeEficiancy("");
        }
        testDetails.setRoomVolume("");
        testDetails.setTestWitnessOrg("" + testWitnessOrg.getText());
        testDetails.setTestCondoctorOrg("" + testCondoctorOrg.getText());
        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            testDetails.setEquipmentName("" + equipmentName.getText().toString());
            testDetails.setEquipmentNo("" + equipmentNo.getText().toString());
            testDetails.setAhuNo("");
        }else{
            testDetails.setEquipmentName("");
            testDetails.setEquipmentNo("");
            testDetails.setAhuNo(ahuNo.getText().toString());
        }
        if(mTestBasedOn.equalsIgnoreCase("ROOM")){
            testDetails.setTestLocation(""+testLocation.getText().toString());
        }else{
            testDetails.setTestLocation("");
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
                mTestItem= extras.getString("testItem");
                mPartnerName = extras.getString("PRTNERNAME");
                mTestBasedOn = extras.getString("testBasedOn");
                testType = extras.getString("testType");
                mTestCode = extras.getString("testCode");

                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }

                if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                    roomDetails = extras.getStringArray("RoomDetails");
                    equipment = (Equipment) extras.getSerializable("Equipment");
                    areaName = extras.getString("AREANAME");
                    mEquipmentFilterArrayList = (ArrayList<EquipmentFilter>) extras.getSerializable("EquipmentFilter");
                    mApplicableTestEquipment = (ApplicableTestEquipment) extras.getSerializable("ApplicableTestEquipment");
                } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                    ahuNumber = extras.getString("AhuNumber");
                    roomDetails = extras.getStringArray("RoomDetails");
                    areaName = extras.getString("AREANAME");
                    mAhuFilterArrayList = (ArrayList<AhuFilter>) extras.getSerializable("AhuFilter");
                    mApplicableTestAhu = (ApplicableTestAhu) extras.getSerializable("ApplicableTestAhu");
                } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilterList");
                    mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if("AHU".equalsIgnoreCase(mTestBasedOn)){
            headerText.setText("TEST RAW DATA AHU/EQUIPMENT");
            headerText_2.setText("Installed HEPA Filter System Leakage Test by Aerosol Photometer Method");
        }else if("ROOM".equalsIgnoreCase(mTestBasedOn)){
            headerText.setText("TEST RAW DATA");
            headerText_2.setText("Installed HEPA Filter System Leakage Test by Aerosol Photometer Method");
        }else{
            headerText.setText("TEST RAW DATA EQUIPMENT");
            headerText_2.setText("Installed HEPA Filter System Leakage Test by Aerosol Photometer Method");
        }

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
                    //row.addView(addTextView(" Filter No \n         "));
                    TextView grillTV = addTextView(" Filter No");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    if (null != filterArrayList && filterArrayList.size() > 0) {
                        RoomFilter roomFilter = filterArrayList.get(i - 2);
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
                   // row.addView(addTextView(" Filter Type"));
                    TextView grillTV = addTextView(" Filter Type");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
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
                   // row.addView(addTextView(" Filter Efficiency\n at Particle Size* "));
                    TextView grillTV = addTextView(" Filter Efficiency\n at Particle Size* ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
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
                   // row.addView(addTextView(" Average Up Stream\n Concentration (µg/liter) "));
                    TextView grillTV = addTextView(" Average Up Stream\n Concentration (µg/liter) ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
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
                    //row.addView(addTextView(" Average Up Stream after\nConcentration (µg/liter) "));
                    TextView grillTV =addTextView(" Average Up Stream after\nConcentration (µg/liter) ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
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
                   // row.addView(addTextView(" SLP of DL for Tests\n after Installation** "));
                    TextView grillTV = addTextView(" SLP of DL for Tests\n after Installation** ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
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
                    //row.addView(addTextView(" Obtained Test Results\n (% Leakage) "));
                    TextView grillTV = addTextView(" Obtained Test Results\n (% Leakage) ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
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
                    //row.addView(addTextView(" Filter No \n         "));
                    TextView grillTV = addTextView(" Filter No");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        if (null != mEquipmentFilterArrayList && mEquipmentFilterArrayList.size() > 0) {
                            EquipmentFilter equipmentFilter = mEquipmentFilterArrayList.get(i - 2);
                            Log.d("valdoc", "DynamicTableActivity filterArrayList=" + mEquipmentFilterArrayList.size() + "i=" + i);
                            row.addView(addTextView(equipmentFilter.getFilterCode()));
                        }
                    } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        if (null != mAhuFilterArrayList && mAhuFilterArrayList.size() > 0) {
                            AhuFilter ahuFilter = mAhuFilterArrayList.get(i - 2);
                            Log.d("valdoc", "DynamicTableActivity filterArrayList=" + mAhuFilterArrayList.size() + "i=" + i);
                            row.addView(addTextView(ahuFilter.getFilterCode()));
                        }
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
                            RoomFilter roomFilter = mRoomFilterArrayList.get(i - 2);
                            Log.d("valdoc", "DynamicTableActivity filterArrayList=" + mRoomFilterArrayList.size() + "i=" + i);
                            row.addView(addTextView(roomFilter.getFilterCode()));
                        }
                    }
//                    row.addView(addTextView("HF -00" + i));
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
                   // row.addView(addTextView(" Average \nbefore Scanning "));
                    TextView grillTV = addTextView("Average\nbefore Scanning(%) ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    row.addView(addInputDataTextViewBeforeStream());
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
                   // row.addView(addTextView(" Average \nAfter Scanning"));
                    TextView grillTV = addTextView("Average\nAfter Scanning(%)");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    row.addView(addInputDataTextViewAfterStream());
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
                   // row.addView(addTextView(" Variation \nin Concentration"));
                    TextView grillTV = addTextView(" Variation \nin Concentration");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    row.addView(addFITConcentrationVariationTextView(10 + i + "%"));
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
                    //row.addView(addTextView(" Obtained Leakage \n(% Leakage)"));
                    TextView grillTV = addTextView(" Obtained Leakage \n(% Leakage)");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    row.addView(addInputDataTextView());
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
                    //row.addView(addTextView(" Test Results\n(Pass/Fail)"));
                    TextView grillTV = addTextView(" Test Results\n(Pass/Fail)");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
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

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

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

    private TextView addFITConcentrationVariationTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        txtConcentrationVariationList.add(tv);
        return tv;
    }

    private TextView addTextViewWithoutBorder(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
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

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

        tv.setGravity(Gravity.CENTER);
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

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

        tv.setGravity(Gravity.CENTER);
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

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

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

    int idPassFailTv = 300;

    private TextView addTextPassFail(String textValue, int tagRows) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

        tv.setGravity(Gravity.CENTER);
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
        headerText_2 = (TextView) findViewById(R.id.common_header_2_tv);
        headerText_2.setVisibility(View.VISIBLE);
        common_certificate_header = (LinearLayout)findViewById(R.id.common_certificate_header_ll);
        common_certificate_header.setVisibility(View.VISIBLE);

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
        Log.d("Saurabh", " testbased on "+getIntent().getStringExtra("testBasedOn"));
        if(getIntent().hasExtra("testBasedOn") && "ROOM".equalsIgnoreCase(getIntent().getStringExtra("testBasedOn"))){
            findViewById(R.id.room_volume_table).setVisibility(View.VISIBLE);
            findViewById(R.id.ahu_no_lable).setVisibility(View.VISIBLE);
            findViewById(R.id.test_item_table).setVisibility(View.VISIBLE);

        }else if(getIntent().hasExtra("testBasedOn") && "AHU".equalsIgnoreCase(getIntent().getStringExtra("testBasedOn"))){
            findViewById(R.id.room_volume_table).setVisibility(View.VISIBLE);
            findViewById(R.id.test_item_table).setVisibility(View.VISIBLE);
            findViewById(R.id.room_no_ahu_fit).setVisibility(View.GONE);
            findViewById(R.id.room_name_ahu_fit).setVisibility(View.VISIBLE);
            findViewById(R.id.ahu_no_lable).setVisibility(View.VISIBLE);
            TextView ahuNumber = (TextView) findViewById(R.id.ahu_no_text);
            TextView roomName = (TextView) findViewById(R.id.room_name_lable);
            ahuNumber.setText("AHU/Equipment No :");
            roomName.setText("Test Item :");
        }
    }
}
