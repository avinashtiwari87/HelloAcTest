package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RDAV5UserEntryActivity extends AppCompatActivity {

    private static final String TAG = "RDAV5UserEntryActivity";
    TableLayout table_layout, table_layout2, table_layout3, table_layout4;
    int rows, cols;
    String testType;
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
    private int applicableTestEquipmentLocation;

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
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDAV5UserEntryActivity.this);

    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdav5_user_entry);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
            testType = getIntent().getStringExtra("testType");
            Log.d(TAG, " TestType : " + testType);
        }
        getExtraFromTestCreateActivity(savedInstanceState);
        //text view initialization
        initTextView();
        textViewValueAssignment();
        initRes();
        datePicker();
        if ("RD_AV_5".equalsIgnoreCase(testType)) {
            BuildTable(rows, cols);
        }

        //Receiving User Input Data from Bundle
        HashMap<Integer, Integer> hashMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("InputData");
        for (Map.Entry m : hashMap.entrySet()) {
            Log.v(TAG, m.getKey() + " " + m.getValue());
        }
        for (int i = 0; i < txtViewList.size(); i++) {
            TextView tvl = txtViewList.get(i);
            tvl.setText(hashMap.get(tvl.getId()) + "");
        }

        //Receiving Result Data from Bundle
        HashMap<Integer, Integer> resultHashMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("ResultData");
        for (Map.Entry n : resultHashMap.entrySet()) {
            Log.v(TAG, " Result: " + n.getKey() + " " + n.getValue());
        }
        for (int i = 0; i < resultTextViewList.size(); i++) {
            TextView tvl = resultTextViewList.get(i);
            tvl.setText(resultHashMap.get(tvl.getId()) + "");
        }

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

    private void initTextView() {
        // layout data which is not in use
        instrumentNoTextView = (TextView) findViewById(R.id.instrument_no);
        instrumentNoTextView.setVisibility(View.GONE);
        testerNameTextView = (TextView) findViewById(R.id.tester_name);
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
                if (mValdocDatabaseHandler.insertTestDetails(ValdocDatabaseHandler.TEST_DETAILS_TABLE_NAME, testDetailsDataCreation())) {
                    if (mValdocDatabaseHandler.insertTestReading(ValdocDatabaseHandler.TESTREADING_TABLE_NAME, testReading())) {
                        Toast.makeText(RDAV5UserEntryActivity.this, "Data saved sussessfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RDAV5UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RDAV5UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                }

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
            instrumentSerialNo.setText(clientInstrument.getSerialNo());
            calibrationOn.setText(clientInstrument.getLastCalibrated());
            calibrationDueOn.setText(clientInstrument.getCalibrationDueDate());
        } else {
            instrumentUsed.setText(partnerInstrument.getpInstrumentName());
            make.setText(partnerInstrument.getMake());
            model.setText(partnerInstrument.getModel());
            instrumentSerialNo.setText(partnerInstrument.getpInstrumentId());
            calibrationOn.setText(partnerInstrument.getLastCalibrated());
            calibrationDueOn.setText(partnerInstrument.getCalibrationDueDate());
        }

        testSpecification.setText("" + equipment.getVelocity());
//                plantName
        areaOfTest.setText(areaName);
        roomName.setText(roomDetails[1]);
        occupancyState.setText(equipment.getOccupancyState().toString());
        Log.d("valdoc", "RDAV5UserEnryActivity 1witness= equipment.getTestReference()=" + equipment.getTestReference());
        testRefrance.setText(""+equipment.getTestReference().toString());
        equipmentName.setText(equipment.getEquipmentName().toString());
        equipmentNo.setText(equipment.getEquipmentNo().toString());
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

    private TestReading testReading() {
        TestReading testReading = new TestReading();
        testReading.setTestReadingID(1);
//        TO DO test details id is id of test details table
        testReading.setTest_detail_id(1);

//        testReading.setEntityName(filterList);
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (TextView testvalue : txtViewList) {

            if (index != 0)
                sb.append(',');
            index++;
            sb.append(testvalue.getText());
        }

        //Receiving Result Data from Bundle
        HashMap<Integer, Integer> resultHashMap = (HashMap<Integer, Integer>) getIntent().getSerializableExtra("ResultData");
        for (Map.Entry n : resultHashMap.entrySet()) {
            Log.v(TAG, " Result: " + n.getKey() + " " + n.getValue());
        }
        for (int i = 0; i < resultTextViewList.size(); i++) {
            TextView tvl = resultTextViewList.get(i);
            sb.append(',');
            sb.append(tvl.getText().toString());
        }
        testReading.setValue(sb.toString());
        return testReading;
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


        testDetails.setTestSpecification("" + equipment.getVelocity());
        testDetails.setBlockName(plantName.getText().toString());
        testDetails.setTestArea(areaName);
        testDetails.setRoomName(roomDetails[1]);
        testDetails.setRoomNo(roomDetails[0]);
        testDetails.setOccupencyState(equipment.getOccupancyState());
        testDetails.setTestReference(equipment.getTestReference());
        testDetails.setEquipmentName(equipment.getEquipmentName());
        testDetails.setEquipmentNo(equipment.getEquipmentNo());
        testDetails.setTesterName(userName);
        testDetails.setWitnessName(witnessFirst + "," + witnessSecond + "," + witnessThird);
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
                roomDetails = null;
                equipment = null;
                filterList = null;
                areaName = null;
                witnessFirst = null;
                witnessSecond = null;
                witnessThird = null;
                applicableTestEquipmentLocation = 0;
            } else {
                Log.d("valdoc", "DynamicTableActivity" + "onresume rows=extra not null");
                loginUserType = extras.getString("USERTYPE");
                userName = extras.getString("USERNAME");
                if (loginUserType.equals("CLIENT")) {
                    clientInstrument = (ClientInstrument) extras.getSerializable("ClientInstrument");
                } else {
                    partnerInstrument = (PartnerInstrument) extras.getSerializable("PartnerInstrument");
                }
                roomDetails = extras.getStringArray("RoomDetails");
                equipment = (Equipment) extras.getSerializable("Equipment");
                //get filter list from equipment filter
                filterList = new String[extras.getStringArray("FILTERLIST").length];
                filterList = extras.getStringArray("FILTERLIST");
                Log.d("valdoc", "DynamicTableActivity" + "onresume rows=filterList=" + filterList.length);
                //get area based on room area id
                areaName = extras.getString("AREANAME");
                witnessFirst = extras.getString("WITNESSFIRST");
                Log.d("valdoc", "RDAV5UserEnryActivity witness=" + witnessFirst);
                witnessSecond = extras.getString("WITNESSSECOND");
                witnessThird = extras.getString("WITNESSTHIRD");
                applicableTestEquipmentLocation = extras.getInt("LOCATION");
                Log.d("valdoc", "DynamicTableActivity" + "onresume rows=applicableTestEquipmentLocation" + applicableTestEquipmentLocation);
            }
        }


    }

    private void BuildTable(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("Grille / Filter ID"));
                } else {
                    row.addView(addTextView("QC/DGC/HF/0" + i));
                }

            }

            table_layout.addView(row);
        }

        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" V " + j));
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
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" V "));
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
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {

                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Result "));
                } else {
                    row.addView(addTextView(" PASS "));
                }
            }
            table_layout4.addView(row);
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
        tv.setPadding(5, 5, 5, 5);
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

        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tableLayout2);
        table_layout3 = (TableLayout) findViewById(R.id.tableLayout3);
        table_layout4 = (TableLayout) findViewById(R.id.tableLayout4);
        //Hide view coming form test tabl 1
        test_table_1_footer = (LinearLayout) findViewById(R.id.test_table_footer_ll);
        test_table_1_header_l = (LinearLayout) findViewById(R.id.test_table_1_header_l_ll);
        test_table_1_header_2 = (LinearLayout) findViewById(R.id.test_table_1_header_2_ll);
        if ("Test1".equalsIgnoreCase(testType)) {
            test_table_1_header_l.setVisibility(View.GONE);
            test_table_1_header_2.setVisibility(View.GONE);
        }

    }
}
