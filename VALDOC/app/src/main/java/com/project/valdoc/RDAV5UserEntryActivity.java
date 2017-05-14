package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.AhuFilter;
import com.project.valdoc.intity.ApplicableTestAhu;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.EquipmentGrill;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RDAV5UserEntryActivity extends AppCompatActivity {

    private static final String TAG = "RDAV5UserEntryActivity";
    TableLayout table_layout, table_layout2, table_layout3, table_layout4;
    int rows, cols;
    String mTestType;
    ProgressDialog pr;
    LinearLayout test_table_1_footer, test_table_1_header_l, test_table_1_header_2;

    // bundel data specification
    private String loginUserType = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String[] roomDetails;
    private Equipment equipment;
    private String[] filterList;
    private String areaName;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private String userName = "";

    //certificate view id creation
    private TextView instrumentUsed;
    private TextView equipmentName;
    private TextView equipmentNo;
    private TextView instrumentSerialNo;
    private TextView calibrationOn;
    private TextView calibrationDueOn;
    private TextView testSpecification;
    private TextView plantName;
    private TextView areaOfTest;
    private TextView roomName;
    private TextView roomNo;
    private TextView occupancyState;
    private TextView testRefrance;
    private TextView testCundoctor;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;

    ArrayList<TextView> txtViewList;
    ArrayList<TextView> txtPassFailList;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;
    private String mPartnerName;
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDAV5UserEntryActivity.this);
    SharedPreferences sharedpreferences;
    int testDetailsId = 0;
    private int year;
    private int month;
    private int day;
    private String mTestCode = "";
    static final int DATE_PICKER_ID = 1111;
    private String mTestBasedOn;
    private String mGrilFilterType;
    private ArrayList<EquipmentGrill> mEquipmentGrillArrayList = null;
    private ArrayList<EquipmentFilter> mEquipmentFilterArrayList = null;
    private ApplicableTestEquipment applicableTestEquipment = null;
    private Ahu ahu = null;
    private ArrayList<AhuFilter> mAhuFilterArrayList = null;
    private ApplicableTestAhu mApplicableTestAhu = null;
    private HashMap<Integer, Integer> inputDataHashMap;
    private HashMap<Integer, Long> resultDataHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdav5_user_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
        pr.setCanceledOnTouchOutside(true);
        pr.setCancelable(true);

        txtPassFailList = new ArrayList<TextView>();
        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            mTestType = getIntent().getStringExtra("testType");
        }
        Log.d(TAG, "CodeFlow rows : " + rows + " cols " + cols);
        Log.d(TAG, "CodeFlow TestType : " + mTestType);

        mGrilFilterType = getIntent().getStringExtra("GrilFilterType");
        mTestCode = getIntent().getStringExtra("testCode");
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        textViewValueAssignment();
        initRes();
        datePicker();
        if (rows > 0 && cols > 0) {
            BuildTable(rows, cols);
        } else {
            Utilityies.showAlert(RDAV5UserEntryActivity.this,
                    getResources().getString(R.string.table_not_created));
        }

        //Receiving User Input Data from Bundle
        inputDataHashMap= (HashMap<Integer, Integer>) getIntent().getSerializableExtra("InputData");
        for (Map.Entry m : inputDataHashMap.entrySet()) {
            Log.v(TAG, " InputData " + m.getKey() + " " + m.getValue());
        }
        for (int i = 0; i < txtViewList.size(); i++) {
            TextView tvl = txtViewList.get(i);
            tvl.setText(inputDataHashMap.get(tvl.getId()) + "");
        }

        //Receiving Result Data from Bundle
        resultDataHashMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("ResultData");
        for (Map.Entry n : resultDataHashMap.entrySet()) {
            Log.v(TAG, " Result: " + n.getKey() + " " + n.getValue());
        }
        for (int i = 0; i < resultTextViewList.size(); i++) {
            TextView tvl = resultTextViewList.get(i);
            tvl.setText(resultDataHashMap.get(tvl.getId()) + "");
        }

        // Checking individual input base on Average
        if(equipment != null){
            double minValue = equipment.getMinVelocity(), maxValue = equipment.getMaxVelocity();
            Log.d(TAG, " minValue "+minValue+" maxValue "+maxValue);
            if(minValue>0 && maxValue>0){
                getInputDataValidationByTestSpecification((int)minValue, (int)maxValue);
            }
        }

        //Receiving Pass Fail Data from Bundle
        HashMap<Integer, Long> PassFailHashMap = (HashMap<Integer, Long>) getIntent().getSerializableExtra("PassFailData");
        for (Map.Entry O : PassFailHashMap.entrySet()) {
            Log.v(TAG, " PassFail " + O.getKey() + " " + O.getValue());
        }
        for (int i = 0; i < txtPassFailList.size(); i++) {
            TextView tvl = txtPassFailList.get(i);
            tvl.setText(PassFailHashMap.get(tvl.getId()) + "");
            if ("PASS".equalsIgnoreCase(tvl.getText().toString().trim())) {
                tvl.setTextColor(getResources().getColor(R.color.blue));
                TextView tvP = resultTextViewList.get(i);
                tvP.setTextColor(getResources().getColor(R.color.blue));

            } else {
                tvl.setTextColor(getResources().getColor(R.color.red));
                TextView tvF = resultTextViewList.get(i);
                tvF.setTextColor(getResources().getColor(R.color.red));
            }
        }
        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDAV5UserEntryActivity.this, mActionBar, userName);

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
        certificateNo.setText("V5/" + mon + "/" + year + "/" + formattedDate);

        // Show current date
        String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
        // Month is 0 based, just add 1
        dateTextView.setText(date);
    }

    private void initTextView() {
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
        roomNo = (TextView) findViewById(R.id.room_no);
        equipmentName = (TextView) findViewById(R.id.equiment_name);
        equipmentNo = (TextView) findViewById(R.id.equiment_no);
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        testCundoctor = (TextView) findViewById(R.id.testcunducter);
        testCondoctorOrg = (TextView) findViewById(R.id.test_condoctor_org);
        testWitnessOrg = (TextView) findViewById(R.id.testwitness_org);
        testWitness = (TextView) findViewById(R.id.testwitness);
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

                        Toast.makeText(RDAV5UserEntryActivity.this, "Data saved successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RDAV5UserEntryActivity.this, TestCreateActivity.class);
                        intent.putExtra(TestCreateActivity.AV, true);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RDAV5UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RDAV5UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                }
            }
        });
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
            String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
            // Show selected date
            dateTextView.setText(date);

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

        Log.d("valdoc", "RDAV5UserEnryActivity 1witness=" + witnessFirst);
        StringBuilder witness = new StringBuilder();
        witness.append(witnessFirst.toString());
        if (null != witnessSecond && witnessSecond.length() > 0)
            witness.append(",   " + witnessSecond);
        if (null != witnessThird && witnessThird.length() > 0)
            witness.append(",   " + witnessThird);
        testWitness.setText(witness);
        areaOfTest.setText(areaName);
        roomName.setText(roomDetails[1]);
        roomNo.setText(roomDetails[0]);
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

//        testSpecification.setText("Required Air Velocity " + equipment.getMinVelocity() + "-" + equipment.getMaxVelocity() + "fpm");
        if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            testSpecification.setText("" + applicableTestEquipment.getTestSpecification());
            equipmentName.setText(equipment.getEquipmentName().toString());
//            make.setText(equipment.getEquipmentName().toString());
            equipmentNo.setText(equipment.getEquipmentNo().toString());
//            model.setText(equipment.getEquipmentNo().toString());
        } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
            testSpecification.setText("" + mApplicableTestAhu.getTestSpecification());
        }
        occupancyState.setText("" + applicableTestEquipment.getOccupencyState());
        testRefrance.setText("" + applicableTestEquipment.getTestReference());
    }

    private ArrayList<TestReading> testReading() {
        ArrayList<TestReading> readingArrayList = new ArrayList<TestReading>();

//        TO DO test details id is id of test details table
        int grilSize = 0;
        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
            grilSize = mEquipmentGrillArrayList.size();
        } else {
            grilSize = mEquipmentFilterArrayList.size();
        }
        int inputDataSize = txtViewList.size() / grilSize;
        int k = 0;
        for (int i = 0; i < grilSize; i++) {
            TestReading testReading = new TestReading();
            testReading.setTest_detail_id(testDetailsId);
            if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                testReading.setEntityName("" + mEquipmentGrillArrayList.get(i).getGrillCode());
            } else {
                testReading.setEntityName("" + mEquipmentFilterArrayList.get(i).getFilterCode());
            }


            StringBuilder sb = new StringBuilder();
            int index = 0;
            for (int j = 0; j < inputDataSize; j++) {
                TextView testvalue = txtViewList.get(k);
                if (index != 0)
                    sb.append(',');
                index++;
                sb.append(testvalue.getText());
                k++;
            }
            sb.append("," + resultTextViewList.get(i).getText());
//            sb.append("," + txtPassFailList.get(i).getText());
            testReading.setValue(sb.toString());
            readingArrayList.add(testReading);
        }

        return readingArrayList;
    }

    private TestDetails testDetailsDataCreation() {
        TestDetails testDetails = new TestDetails();
//        TO DO: need to make it dynamic
        testDetails.setTest_detail_id(testDetailsId);
        testDetails.setCustomer(customerName.getText().toString());
        int newmonth = month + 1;
//        testDetails.setDateOfTest("" + year + "-" + newmonth + "-" + day);
        testDetails.setDateOfTest("" + dateTextView.getText());
        testDetails.setRawDataNo(certificateNo.getText().toString());
        testDetails.setPartnerName("" + mPartnerName);
        testDetails.setTestName(TestCreateActivity.AV);
        testDetails.setFilterTypeEficiancy("");
        testDetails.setTestLocation("");
        testDetails.setAcceptableRecoveryTime("");
        testDetails.setDiffAVinFilter(0);
        testDetails.setDiffAVbetweenFilter(0);
        if (loginUserType.equals("CLIENT")) {
            Log.d("getCertificateData", "client instrumentUsed.getText()=" + instrumentUsed.getText());
            testDetails.setInstrumentUsed("" + instrumentUsed.getText());
            testDetails.setMake("" + clientInstrument.getMake());
            testDetails.setModel("" + clientInstrument.getModel());
            testDetails.setInstrumentNo("" + clientInstrument.getSerialNo());
            testDetails.setCalibratedOn("" + clientInstrument.getLastCalibrated());
            testDetails.setCalibratedDueOn("" + clientInstrument.getCalibrationDueDate());
            testDetails.setSamplingFlowRate("");
            testDetails.setSamplingTime("");
        } else {
            Log.d("getCertificateData", "instrumentUsed.getText()=" + instrumentUsed.getText());
            testDetails.setInstrumentUsed("" + instrumentUsed.getText());
            testDetails.setMake("" + partnerInstrument.getMake());
            testDetails.setModel("" + partnerInstrument.getModel());
            testDetails.setInstrumentNo("" + partnerInstrument.getpInstrumentId());
            testDetails.setCalibratedOn("" + partnerInstrument.getLastCalibrationDate());
            testDetails.setCalibratedDueOn("" + partnerInstrument.getCalibrationDueDate());
            testDetails.setSamplingFlowRate("");
            testDetails.setSamplingTime("");
        }


        testDetails.setTestSpecification("" + equipment.getMinVelocity() + "-" + equipment.getMaxVelocity());
        testDetails.setBlockName(plantName.getText().toString());
        testDetails.setTestArea("" + areaName);
        testDetails.setRoomName("" + roomName.getText());
        testDetails.setRoomNo("" + roomDetails[0]);
        testDetails.setOccupencyState("" + occupancyState.getText());
        testDetails.setTestReference("" + testRefrance.getText());
        testDetails.setEquipmentName("" + equipment.getEquipmentName());
        testDetails.setEquipmentNo("" + equipment.getEquipmentNo());
        testDetails.setTesterName("" + testCundoctor.getText());
        testDetails.setWitnessName("" + testWitness.getText());
        testDetails.setTestCode(mTestCode);
        testDetails.setAhuNo("");
        testDetails.setSamplingFlowRate("");
        testDetails.setSamplingTime("");
        testDetails.setAerosolGeneratorType("");
        testDetails.setAerosolUsed("");
        testDetails.setTestItem("");
        testDetails.setRoomVolume("");
        testDetails.setTestWitnessOrg("" + testWitnessOrg.getText());
        testDetails.setTestCondoctorOrg("" + testCondoctorOrg.getText());
        testDetails.setTolarance("");
        return testDetails;
    }

    private void getExtraFromTestCreateActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                loginUserType = null;
                clientInstrument = null;
                partnerInstrument = null;
                roomDetails = null;
                equipment = null;
                filterList = null;
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
                mTestBasedOn = extras.getString("testBasedOn");
                mTestCode = extras.getString("testCode");

                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }
                if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                    roomDetails = extras.getStringArray("RoomDetails");
                    equipment = (Equipment) extras.getSerializable("Equipment");
                    //get filter list from equipment filter
//                        filterList = new String[extras.getStringArray("FILTERLIST").length];
                    if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                        mEquipmentGrillArrayList = (ArrayList<EquipmentGrill>) extras.getSerializable("GRILLLIST");
                    } else {
                        mEquipmentFilterArrayList = (ArrayList<EquipmentFilter>) extras.getSerializable("GRILLLIST");
                    }
                    applicableTestEquipment = (ApplicableTestEquipment) extras.getSerializable("ApplicableTestEquipment");
                } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                    roomDetails = extras.getStringArray("RoomDetails");
                    ahu = (Ahu) extras.getSerializable("Ahu");
                    mAhuFilterArrayList = (ArrayList<AhuFilter>) extras.getSerializable("FILTERLIST");
                    mApplicableTestAhu = (ApplicableTestAhu) extras.getSerializable("ApplicableTestAhu");
                }
            }
        }


    }

    private void BuildTable(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = getTableRow(this);
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    //row.addView(addTextView("Grille / Filter ID"));
                    TextView grillTV = addTextView("Grille / Filter ID");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        row.addView(addTextView(mAhuFilterArrayList.get(i - 2).getFilterCode()));
                    } else if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            row.addView(addTextView(mEquipmentGrillArrayList.get(i - 2).getGrillCode()));
                        } else {
                            row.addView(addTextView(mEquipmentFilterArrayList.get(i - 2).getFilterCode()));
                        }
                    }
                }
            }
            table_layout.addView(row);
        }

        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = getTableRow(this);
            // inner for loop
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    TextView grillTV = addTextView(" V " + j);
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    //text view for user input
                    row.addView(addInputDataTextView());
                }
            }
            table_layout2.addView(row);
        }


        //third section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = getTableRow(this);
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    //row.addView(addTextView("Avg. Airflow Velocity(fpm)"));
                    TextView grillTV = addTextView("Avg. Airflow Velocity\n(fpm)");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    //result data  set
                    row.addView(addResultTextView(i));
                }
            }
            table_layout3.addView(row);

        }
        //fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = getTableRow(this);
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    //row.addView(addTextView(" Result "));
                    TextView grillTV = addTextView(" Result ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    row.addView(grillTV);
                } else {
                    //row.addView(addTextView(" PASS "));
                    row.addView(addTextPassFail(" ", i));
                }
            }
            table_layout4.addView(row);
        }

        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();

    }

    private TableRow getTableRow(Context context){
        TableRow row = new TableRow(context);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);
        tv.setPadding(15,0,15,0);
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
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

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        tv.setId(idCountEtv);
        tv.setGravity(Gravity.CENTER);
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

        ViewGroup.LayoutParams layoutParams = tv.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.common_text_cell_height);
        tv.setLayoutParams(layoutParams);

        tv.setPadding(5, 4, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
        tv.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
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

    private TextView addTextViewWithoutBorder(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        return tv;
    }

    private EditText addEditTextView() {
        EditText editTv = new EditText(this);
        editTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        editTv.setBackgroundResource(R.drawable.border);
        editTv.setPadding(5, 5, 5, 5);
        editTv.setTextColor(getResources().getColor(R.color.black));
        editTv.setGravity(Gravity.CENTER);
        editTv.setEms(3);
        editTv.setSingleLine(true);
        editTv.setEllipsize(TextUtils.TruncateAt.END);
        return editTv;
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
        tv.setSingleLine(false);
        tv.setTag(tagRows);
        tv.setId(idPassFailTv);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        tv.setGravity(Gravity.CENTER);
        idPassFailTv++;
        txtPassFailList.add(tv);
        return tv;
    }

    private void initRes() {
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tableLayout2);
        table_layout3 = (TableLayout) findViewById(R.id.tableLayout3);
        table_layout4 = (TableLayout) findViewById(R.id.tableLayout4);
        table_layout4.setVisibility(View.GONE);
        //Hide view coming form test tabl 1
        test_table_1_footer = (LinearLayout) findViewById(R.id.test_table_footer_ll);
        test_table_1_header_l = (LinearLayout) findViewById(R.id.test_table_1_header_l_ll);
        test_table_1_header_2 = (LinearLayout) findViewById(R.id.test_table_1_header_2_ll);
        test_table_1_header_l.setVisibility(View.GONE);
        test_table_1_header_2.setVisibility(View.VISIBLE);
        TextView TestHeader = (TextView) findViewById(R.id.common_header_tv);
        TestHeader.setText("TEST RAW DATA EQUIPMENT\n(Average Air Flow Velocity Testing)");
        findViewById(R.id.test1_reading_header).setVisibility(View.VISIBLE);
        findViewById(R.id.common_header_test1).setVisibility(View.GONE);
    }

    int idCounts = 200, inputTxtCount = 0;
    private void getInputDataValidationByTestSpecification(int minValue, int maxValue) {
        for (int i = 1; i <= rows - 1; i++) {
            for (int j = 0; j < cols; j++) {
                Log.d(TAG, " IdCounts " + idCounts + " inputTxtCount " + inputTxtCount);
                boolean results = checkInputValueBasedOnAverage(inputDataHashMap.get(idCounts),
                        minValue, maxValue);
                idCounts++;
                if (results) {
                    txtViewList.get(inputTxtCount).setTextColor(Color.BLACK);
                } else {
                    txtViewList.get(inputTxtCount).setTextColor(Color.RED);
                }
                inputTxtCount++;
            }
        }
    }

    private boolean checkInputValueBasedOnAverage(int inputValue, int minValue,
                                                  int maxValue) {
        boolean resultValue = false;
        if (inputValue >= minValue && inputValue <= maxValue) {
            resultValue = true;
        }
        return resultValue;
    }
}
