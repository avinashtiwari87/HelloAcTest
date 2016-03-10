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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class RDPC3UserEntryActivity extends AppCompatActivity {
    private static final String TAG = "RDPC3";
    TextView headerText;
    //Test 5 View ...
    TableLayout test5_table_layout,test5_table_layout2,test5_table_layout3,test5_table_layout4,
            test5_table_layout5;
    int rows, cols;
    String testType;
    ProgressDialog pr;

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
        pr = ProgressDialog.show(this,"Please Wait", "Loading...");

        if(getIntent().hasExtra("rows") && getIntent().hasExtra("cols")){
            rows = getIntent().getIntExtra("rows",0);
            cols = getIntent().getIntExtra("cols",0);
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

    private void textViewValueAssignment()  {
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
        equipmentNameText= (TextView) findViewById(R.id.equipment_name_text);
        equipmentNoText= (TextView) findViewById(R.id.equipment_no_text);
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
//                        Toast.makeText(RDAV5UserEntryActivity.this, "Data saved sussessfully", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(RDAV5UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
//                    }
//
//                } else {
//                    Toast.makeText(RDAV5UserEntryActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
//                }
//
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
                if(i==1 && j==1){
                    row.addView(addTextView(" Location \n   "));
                }else{
                    row.addView(addTextView(" " + i));
                }

            }
            test5_table_layout.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
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
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" No. of Particles >0. 5 µm/m³ \n (R1 | R2 | R3) "));
                }else{
                    row.addView(addTextView(" 4434 | 3434 | 1341 "));
                }

            }
            test5_table_layout2.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if(sk==0){
                row.addView(addTextView("   Mean Average   "));
            }if(sk==1){
                row.addView(addTextView("   Standard Deviation   "));
            }if(sk==2){
                row.addView(addTextView("   95% UCL   "));
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
                if(i==1 && j==1){
                    row.addView(addTextView(" Average \n "));
                }else{
                    row.addView(addTextView(" 1919643"+i));
                }

            }
            test5_table_layout3.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            if(sk==0){
                row.addView(addTextView("   4878776542   "));
            }if(sk==1){
                row.addView(addTextView("   78441734560   "));
            }if(sk==2){
                row.addView(addTextView("   1129564326705   "));
            }
            test5_table_layout3.addView(row);
        }


        //Fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" No. of Particles > 5 µm/m³ \n (R1 | R2 |R3 )"));
                }else{
                    row.addView(addTextView(" 13123 | 12323 | 1341 "));
                }

            }
            test5_table_layout4.addView(row);
        }
        for (int sk = 0; sk<3; sk++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(addTextView("  "));
            test5_table_layout4.addView(row);
        }

        //Fifth section
        // outer for loop
        for (int i = 1; i <= (rows+3); i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if(i==1 && j==1){
                    row.addView(addTextView(" Average \n "));
                }else{
                    row.addView(addTextView(" 8893 "));
                }

            }
            test5_table_layout5.addView(row);
        }
        //dismiss progressbar
        if(pr.isShowing())
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

    private EditText addEditTextView(){
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

    private void initRes(){
        headerText = (TextView)findViewById(R.id.common_header_tv);
        headerText.setText("* Airborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices *");
        //Test5
        test5_table_layout = (TableLayout)findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout)findViewById(R.id.test5_tableLayout2);
        test5_table_layout3 = (TableLayout)findViewById(R.id.test5_tableLayout3);
        test5_table_layout4 = (TableLayout)findViewById(R.id.test5_tableLayout4);
        test5_table_layout5 = (TableLayout)findViewById(R.id.test5_tableLayout5);
        if ("RD_PC_3".equalsIgnoreCase(testType)) {
            findViewById(R.id.test_table_5_header_l_ll).setVisibility(View.GONE);
            findViewById(R.id.test_table_5_header_2_ll).setVisibility(View.GONE);
        }
    }
}
