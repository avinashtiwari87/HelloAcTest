package com.project.valdoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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
import com.project.valdoc.intity.RoomFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicTableActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DynamicTableActivity";
    //Test 1 View...
    TableLayout table_layout, table_layout2, table_layout3, table_layout4;
    //Test 2 View ...
    TableLayout test2_table_layout, test2_table_layout2, test2_table_layout3, test2_table_layout4,
            test2_table_layout5, test2_table_layout6, test2_table_layout7, test2_table_layout8;
    //Test 3 View ...
    TableLayout test3_table_layout, test3_table_layout2, test3_table_layout3, test3_table_layout4,
            test3_table_layout5;
    //Test 4 View ...
    TableLayout test4_table_layout, test4_table_layout2, test4_table_layout3, test4_table_layout4,
            test4_table_layout5, test4_table_layout6, test4_table_layout7;
    //Test 5 View ...
    TableLayout test5_table_layout, test5_table_layout2, test5_table_layout2_1,
            test5_table_layout3, test5_table_layout4, test5_table_layout4_1,
            test5_table_layout5, test5_table_layout5_1, test5_table_layout3_1;
    //Test 6 View ...
    TableLayout test6A_table_layout, test6A_table_layout2, test6A_table_layout3;

    Button verify_btn, clear,cancel, addRow_Btn, deleteRow_Btn;
    int rows, cols;
    String testType;
    ProgressDialog pr;

    /*Unique Ids for EditText and TextView
           must be defined in Constant Class
    * */
    //Test 1 Ids variable also used in test 2
    int idCountEtv = 200, idCountTv = 1;
    //Test 2 Ids variable
    int filterSizeIds = 100, airFlowRateIds = 300,
            totalAirFlowRateIds = 400;
    //Test 4 Ids & variable
    int test4Id = 700;
    ArrayList<TextView> txtSlpDlpList;
    // Test 5 Variable
    int test5CommonFormulaIds1 = 500, test5CommonFormulaIds2 = 600;
    long meanValue1 = 0l, meanValue2 = 0l;
    double stdDev1 = 0.0, stdDev2 = 0.0;
    //Test 6 Variable
    int rowsCount = 1;

    ArrayList<TextView> txtPassFailList;
    ArrayList<TextView> txtViewList;
    ArrayList<EditText> editTextList;
    ArrayList<TextView> filterSizeTxtViewList;
    ArrayList<TextView> airFlowRateTxtViewList;
    ArrayList<TextView> totalAirFlowRateTxtList;
    ArrayList<TextView> roomVolumeTxtList;
    ArrayList<TextView> airChangeTxtList;
    ArrayList<TextView> RDPC3TxtList, RDPC3TxtList2;

    HashMap<Integer, Integer> inputDataHashMap;
    HashMap<Integer, String> passFailHashMap;
    HashMap<Integer, Long> resultDataHashMap;
    HashMap<Integer, Float> resultDataHashMap2;
    HashMap<Integer, Integer> rowTagHashMap;
    int inputValue = 0,AirChangeValue = 0;
    float totalAirFlowRate = 0f;
    private boolean isClearClicked = false;

    // bundel data specification
    private String loginUserType = "";
    private String userName = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String[] roomDetails;
    private Equipment equipment;
    private String[] filterList;
    private String areaName;
    private String ahuNumber;
    private Room room;
    private ArrayList<HashMap<String, String>> grillAndSizeFromGrill;
    private ArrayList<RoomFilter> filterArrayList;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private int applicableTestEquipmentLocation;
    private int applicableTestRoomLocation;
    private TextView instrumentNo;
    private TextView testerName;
    private int noOfCycle;
    private double mean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_table);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(DynamicTableActivity.this, "Please Wait", "Loading...");

        if (getIntent().hasExtra("rows") && getIntent().hasExtra("cols")) {
            rows = getIntent().getIntExtra("rows", 0);
            cols = getIntent().getIntExtra("cols", 0);
        }

        testType = getIntent().getStringExtra("testType");
        Log.d(TAG, " TestType : " + testType);
        getExtraFromTestCreateActivity(savedInstanceState);

        initRes();

        txtPassFailList = new ArrayList<TextView>();
        txtViewList = new ArrayList<TextView>();
        editTextList = new ArrayList<EditText>();
        filterSizeTxtViewList = new ArrayList<TextView>();
        airFlowRateTxtViewList = new ArrayList<TextView>();
        totalAirFlowRateTxtList = new ArrayList<TextView>();
        roomVolumeTxtList = new ArrayList<TextView>();
        airChangeTxtList = new ArrayList<TextView>();
        RDPC3TxtList = new ArrayList<TextView>();
        RDPC3TxtList2 = new ArrayList<TextView>();
        txtSlpDlpList = new ArrayList<TextView>();


        inputDataHashMap = new HashMap<Integer, Integer>();
        resultDataHashMap = new HashMap<Integer, Long>();
        passFailHashMap = new HashMap<Integer, String>();
        resultDataHashMap2 = new HashMap<Integer, Float>();
        rowTagHashMap = new HashMap<Integer, Integer>();


        createTableRowColum();
        //setting the test 2 room volume
        if (roomVolumeTxtList != null && roomVolumeTxtList.size() > 0)
            if("RD_ACPH_H".equals(testType)) {
                roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText(""+room.getVolume());
            }else{
                roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("8500");
            }
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
                loginUserType = extras.getString("USERTYPE");
                userName = extras.getString("USERNAME");
                Log.d("valdoc", "DynamicTableActivity bundel loginUserType=" + loginUserType);
                Log.d("valdoc", "DynamicTableActivity bundel userName=" + userName);
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
                if ("RD_AV_5".equals(testType)) {
                    roomDetails = extras.getStringArray("RoomDetails");
                    equipment = (Equipment) extras.getSerializable("Equipment");
                    //get filter list from equipment filter
                    filterList = new String[extras.getStringArray("FILTERLIST").length];
                    filterList = extras.getStringArray("FILTERLIST");
                    Log.d("valdoc", "DynamicTableActivity" + "onresume rows=filterList=" + filterList.length);

                    applicableTestEquipmentLocation = extras.getInt("LOCATION");
                    Log.d("valdoc", "DynamicTableActivity" + "onresume rows=applicableTestEquipmentLocation" + applicableTestEquipmentLocation);
                }

                if ("RD_ACPH_AV".equals(testType)) {
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    applicableTestRoomLocation = extras.getInt("LOCATION");
                    grillAndSizeFromGrill = (ArrayList<HashMap<String, String>>) extras.getSerializable("GRILLIST");
                }

                if ("RD_ACPH_H".equals(testType)) {
                    //get room name,roomNo,and area id
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    //get filter list from grill filter
                    grillAndSizeFromGrill = (ArrayList<HashMap<String, String>>) extras.getSerializable("GRILLIST");
                }
                if ("RD_FIT".equals(testType)) {
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    //take test specification from room filter
                    filterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilterList");
                    //TO Do testspesification will be shown from room filter spesification
                }
                if ("RD_PC_3".equals(testType)) {
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    applicableTestRoomLocation = extras.getInt("LOCATION");
                    noOfCycle = extras.getInt("NOOFCYCLE");
                    Log.d("valdoc", "DynamicTableActivity" + "NOOFCYCLE NOOFCYCLE=" + noOfCycle+"location="+applicableTestRoomLocation);
                }
                if ("RD_RCT".equals(testType)) {
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    applicableTestRoomLocation = extras.getInt("LOCATION");
                    noOfCycle = extras.getInt("NOOFCYCLE");//throwing Exception...plz check tiwari jee
                }
            }
        }

    }

    private void createTableRowColum() {
        if ("RD_AV_5".equalsIgnoreCase(testType)) {
            if (filterList != null && filterList.length > 0)
                BuildTable(filterList.length + 1, applicableTestEquipmentLocation);
            else
                BuildTable(3, 4);
        }
        if ("RD_ACPH_AV".equalsIgnoreCase(testType)) {
            if (grillAndSizeFromGrill != null && grillAndSizeFromGrill.size() > 0)
                BuildTableTest2(grillAndSizeFromGrill.size() + 1, applicableTestRoomLocation);
            else
                BuildTableTest2(3, 4);
        }
        if ("RD_ACPH_H".equalsIgnoreCase(testType)) {
            if (grillAndSizeFromGrill != null && grillAndSizeFromGrill.size() > 0)
                BuildTableTest3(grillAndSizeFromGrill.size() + 1, applicableTestRoomLocation);
            else
                BuildTableTest3(3, 4);
        }
        if ("RD_FIT".equalsIgnoreCase(testType)) {
            Log.d("valdoc", "DynamicTableActivity" + "rows=" + filterArrayList.size());
            if (filterArrayList != null && filterArrayList.size() > 0)
                BuildTableTest4(filterArrayList.size() + 1, cols);
            else
                BuildTableTest4(3, 4);
        }
        if ("RD_PC_3".equalsIgnoreCase(testType)) {
            rows = applicableTestRoomLocation + 1;
            BuildTableTest5(applicableTestRoomLocation + 1, noOfCycle);
        }
        if ("RD_RCT".equalsIgnoreCase(testType)) {
            if (grillAndSizeFromGrill != null && grillAndSizeFromGrill.size() > 0)
                BuildTableTest6(grillAndSizeFromGrill.size() + 1, cols);
            else
                BuildTableTest6(4, 6);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //removeView();

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == verify_btn) {
            Intent intent = null;
            if ("RD_AV_5".equalsIgnoreCase(testType)) {
                intent = new Intent(DynamicTableActivity.this, RDAV5UserEntryActivity.class);
                // put bundel data
                intent.putExtra("USERTYPE", loginUserType);
                if (loginUserType.equals("CLIENT")) {
                    intent.putExtra("ClientInstrument", clientInstrument);
                } else {
                    intent.putExtra("PartnerInstrument", partnerInstrument);
                }
                intent.putExtra("USERNAME", userName);
                //get room name,roomNo,and area id
                intent.putExtra("RoomDetails", roomDetails);
                intent.putExtra("Equipment", equipment);
                //get filter list from equipment filter
                intent.putExtra("FILTERLIST", filterList);
                //get area based on room area id
                intent.putExtra("AREANAME", areaName);
                Log.d("valdoc", "DynamicTableActivity 2witness=" + witnessFirst);
                intent.putExtra("WITNESSFIRST", witnessFirst);
                intent.putExtra("WITNESSSECOND", witnessSecond);
                intent.putExtra("WITNESSTHIRD", witnessThird);
                intent.putExtra("LOCATION", applicableTestEquipmentLocation);
                //sending Result Data over Bundle
                intent.putExtra("ResultData", resultDataHashMap);
                intent.putExtra("PassFailData", passFailHashMap);
                //sending Input Data
                intent.putExtra("InputData", inputDataHashMap);
                intent.putExtra("rows", filterList.length + 1);
                intent.putExtra("cols", applicableTestEquipmentLocation);
                intent.putExtra("testType", testType);
                startActivity(intent);
            }
            if ("RD_ACPH_AV".equalsIgnoreCase(testType)) {
                intent = new Intent(DynamicTableActivity.this, RDACPHAVUserEntryActivity.class);
                // put bundel data
                intent.putExtra("USERTYPE", loginUserType);
                intent.putExtra("USERNAME", userName);
                intent.putExtra("WITNESSFIRST", witnessFirst);
                intent.putExtra("WITNESSSECOND", witnessSecond);
                intent.putExtra("WITNESSTHIRD", witnessThird);
                intent.putExtra("testType", testType);
                //get area based on room area id
                intent.putExtra("AREANAME", areaName);

                if (loginUserType.equals("CLIENT")) {
                    intent.putExtra("ClientInstrument", clientInstrument);
                } else {
                    intent.putExtra("PartnerInstrument", partnerInstrument);
                }
                //get room name,roomNo,and area id
                intent.putExtra("Room", room);
                intent.putExtra("AhuNumber", ahuNumber);
                intent.putExtra("LOCATION", applicableTestRoomLocation);
                //get filter list from equipment filter
                intent.putExtra("GRILLIST", grillAndSizeFromGrill);
                intent.putExtra("rows", grillAndSizeFromGrill.size() + 1);
                intent.putExtra("cols", applicableTestRoomLocation);
                //sending Result Data over Bundle
                intent.putExtra("ResultData", resultDataHashMap);
                intent.putExtra("ResultData2", resultDataHashMap2);
                intent.putExtra("totalAirFlowRate", totalAirFlowRate);
                intent.putExtra("AirChangeValue", AirChangeValue);
                //sending Input Data
                intent.putExtra("InputData", inputDataHashMap);
                startActivity(intent);

            }
            if ("RD_ACPH_H".equalsIgnoreCase(testType)) {
                intent = new Intent(DynamicTableActivity.this, RDACPHhUserEntryActivity.class);
                // put bundel data
                intent.putExtra("USERTYPE", loginUserType);
                intent.putExtra("USERNAME", userName);
                intent.putExtra("WITNESSFIRST", witnessFirst);
                intent.putExtra("WITNESSSECOND", witnessSecond);
                intent.putExtra("WITNESSTHIRD", witnessThird);
                intent.putExtra("testType", testType);
                //get area based on room area id
                intent.putExtra("AREANAME", areaName);

                if (loginUserType.equals("CLIENT")) {
                    intent.putExtra("ClientInstrument", clientInstrument);
                } else {
                    intent.putExtra("PartnerInstrument", partnerInstrument);
                }
                intent.putExtra("Room", room);
                intent.putExtra("AhuNumber", ahuNumber);
                intent.putExtra("rows", grillAndSizeFromGrill.size() + 1);
                intent.putExtra("cols", applicableTestRoomLocation);
                intent.putExtra("GRILLIST", grillAndSizeFromGrill);
                //sending Result Data over Bundle
                intent.putExtra("totalAirFlowRate", totalAirFlowRate);
                intent.putExtra("AirChangeValue", AirChangeValue);
                //sending Input Data
                intent.putExtra("InputData", inputDataHashMap);

                startActivity(intent);
            }
            if ("RD_FIT".equalsIgnoreCase(testType)) {
                intent = new Intent(DynamicTableActivity.this, RDFITUserEntryActivity.class);
                // put bundel data
                intent.putExtra("USERTYPE", loginUserType);
                intent.putExtra("USERNAME", userName);
                intent.putExtra("WITNESSFIRST", witnessFirst);
                intent.putExtra("WITNESSSECOND", witnessSecond);
                intent.putExtra("WITNESSTHIRD", witnessThird);
                intent.putExtra("testType", testType);
                //get area based on room area id
                intent.putExtra("AREANAME", areaName);

                if (loginUserType.equals("CLIENT")) {
                    intent.putExtra("ClientInstrument", clientInstrument);
                } else {
                    intent.putExtra("PartnerInstrument", partnerInstrument);
                }
                intent.putExtra("Room", room);
                intent.putExtra("AhuNumber", ahuNumber);
                intent.putExtra("RoomFilterList", filterArrayList);
                intent.putExtra("rows", filterArrayList.size() + 1);
                intent.putExtra("cols", applicableTestRoomLocation);
                //sending Result Data over Bundle
                intent.putExtra("PassFailData", passFailHashMap);
                //sending Input Data
                intent.putExtra("InputData", inputDataHashMap);

                //TO Do testspesification will be shown from room filter spesification
                // location will be the size off rommfilter list
                startActivity(intent);
            }
            if ("RD_PC_3".equalsIgnoreCase(testType)) {
                intent = new Intent(DynamicTableActivity.this, RDPC3UserEntryActivity.class);
                // put bundel data
                intent.putExtra("USERTYPE", loginUserType);
                intent.putExtra("USERNAME", userName);
                intent.putExtra("WITNESSFIRST", witnessFirst);
                intent.putExtra("WITNESSSECOND", witnessSecond);
                intent.putExtra("WITNESSTHIRD", witnessThird);
                intent.putExtra("testType", testType);
                //get area based on room area id
                intent.putExtra("AREANAME", areaName);

                if (loginUserType.equals("CLIENT")) {
                    intent.putExtra("ClientInstrument", clientInstrument);
                } else {
                    intent.putExtra("PartnerInstrument", partnerInstrument);
                }
                intent.putExtra("Room", room);
                intent.putExtra("AhuNumber", ahuNumber);
                intent.putExtra("LOCATION", applicableTestRoomLocation);
                intent.putExtra("NOOFCYCLE", noOfCycle);
                intent.putExtra("rows", applicableTestRoomLocation + 1);
                intent.putExtra("cols", noOfCycle);
                //sending Input Data
                intent.putExtra("InputData", inputDataHashMap);
                //sending Result Data over Bundle
                intent.putExtra("ResultData", resultDataHashMap);
                intent.putExtra("meanValue1", meanValue1);
                intent.putExtra("meanValue2", meanValue2);
                intent.putExtra("stdDev1", stdDev1);
                intent.putExtra("stdDev2", stdDev2);

                startActivity(intent);
            }
            if ("RD_RCT".equalsIgnoreCase(testType)) {
                Toast.makeText(DynamicTableActivity.this, "Under development", Toast.LENGTH_LONG).show();
//                intent = new Intent(DynamicTableActivity.this, RDRCTUserEntryActivity.class);
//                // put bundel data
//                intent.putExtra("USERTYPE", loginUserType);
//                intent.putExtra("USERNAME", userName);
//                intent.putExtra("WITNESSFIRST", witnessFirst);
//                intent.putExtra("WITNESSSECOND", witnessSecond);
//                intent.putExtra("WITNESSTHIRD", witnessThird);
//                intent.putExtra("testType", testType);
//                //get area based on room area id
//                intent.putExtra("AREANAME", areaName);
//
//                if (loginUserType.equals("CLIENT")) {
//                    intent.putExtra("ClientInstrument", clientInstrument);
//                } else {
//                    intent.putExtra("PartnerInstrument", partnerInstrument);
//                }
//                intent.putExtra("Room", room);
//                intent.putExtra("AhuNumber", ahuNumber);
//                intent.putExtra("LOCATION", applicableTestRoomLocation);
//                intent.putExtra("NOOFCYCLE", noOfCycle);
//                intent.putExtra("rows", grillAndSizeFromGrill.size() + 1);
//                intent.putExtra("cols", applicableTestRoomLocation);
//
//                startActivity(intent);

            }
        }

        if (view == clear) {
            isClearClicked = true;
            if (editTextList.size() > 0) {
                for (int i = 0; i < editTextList.size(); i++) {
                    if (editTextList.get(i).getText().toString().trim() != null
                            && !"".equals(editTextList.get(i).getText().toString().trim())) {
                        editTextList.get(i).setText("");
                        inputDataHashMap.put(editTextList.get(i).getId(), 0);
                    }
                }
            }
            if (txtViewList.size() > 0) {
                for (int i = 0; i < txtViewList.size(); i++) {
                    if (txtViewList.get(i).getText().toString().trim() != null
                            && !"".equals(txtViewList.get(i).getText().toString().trim())) {
                        txtViewList.get(i).setText("");
                        resultDataHashMap.put(txtViewList.get(i).getId(), 0l);
                    }
                }
            }
            //Done clicked...hahaa..
            isClearClicked = false;
        }

        if(view == addRow_Btn){
            addRowonButtonClick(rowsCount);
            rowsCount++;
        }
        if(view == deleteRow_Btn){
            if(rowsCount>2){
                deleteRowButtonClick(rowsCount);
                rowsCount--;
            }else{
                Toast.makeText(DynamicTableActivity.this, "Please Add Row First", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void BuildTableTest6(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows + 3; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(""));
                }
                if (i == 2 && j == 1) {
                    row.addView(addTextView(" Initial Reading "));
                }
                if (i == 3 && j == 1) {
                    row.addView(addTextView(" Worst case "));
                } else if (i > 3 && i<4) {
                    row.addView(addTextView(" " + i));
                }

            }
            test6A_table_layout.addView(row);
        }


        //Second section
        // outer for loop
        for (int i = 1; i <= rows + 4; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" 0.5 µm/m³ "));
                } else if( i>1 && i<4){
                    row.addView(addEditTextView(i));
                }

            }
            test6A_table_layout2.addView(row);
        }

/*        //Third section
        // outer for loop
        for (int i = 1; i <= rows + 4; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("   "));
                } else {
                    row.addView(addEditTextView(i + 5));
                }
            }
            test6A_table_layout3.addView(row);
        }*/


        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();


    }

    private void addRowonButtonClick(int rows){
        TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        row1.setTag(rows);
        row1.addView(addTextView(" " + rows));
        test6A_table_layout.addView(row1, rows);

        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        row2.addView(addEditTextView(rows));
        test6A_table_layout2.addView(row2,rows);

    }


    private void deleteRowButtonClick(int rows){
        test6A_table_layout.removeViewAt(rows);
        test6A_table_layout2.removeViewAt(rows);
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
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Location "));
                } else {
                    int position=i-1;
                    row.addView(addTextView(" " + position));
                }

            }
            test5_table_layout.addView(row);
        }
        for (int sk = 0; sk < 3; sk++) {
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
            // inner for loop 1
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" R "+ j));
                } else {
                    //row.addView(addTextView(" 4434 | 3434 | 1341 "));
                    row.addView(addEditTextView(i));
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
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Average "));
                } else {
                    row.addView(addResultTextView(i));
                }

            }
            test5_table_layout3.addView(row);
        }
        //Footer Rows....
        for (int sk = 0; sk < 3; sk++) {
            TableRow rowFooter = new TableRow(this);
            rowFooter.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            rowFooter.addView(addTextViewWithTagIds(sk, test5CommonFormulaIds1, RDPC3TxtList, "0"));
            test5_table_layout3_1.addView(rowFooter);
            test5CommonFormulaIds1++;
        }


        //Fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop 1
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" R " + j));
                } else {
                    row.addView(addEditTextView(rows + i));
                }

            }
            test5_table_layout4_1.addView(row);

        }
        for (int sk = 0; sk < 3; sk++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(addStretchedTextView("  "));
            test5_table_layout4.addView(row);
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
                    row.addView(addTextView(" Average "));
                } else {
                    row.addView(addResultTextView(rows + i));
                }

            }
            test5_table_layout5.addView(row);
        }
        //Footer Rows....
        for (int sk = 0; sk < 3; sk++) {
            TableRow rowFooter = new TableRow(this);
            rowFooter.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            rowFooter.addView(addTextViewWithTagIds(sk, test5CommonFormulaIds2, RDPC3TxtList2, "0"));
            test5_table_layout5_1.addView(rowFooter);
            test5CommonFormulaIds2++;
        }
        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();
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
                        RoomFilter roomFilter =  filterArrayList.get(i - 2);
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
                        RoomFilter roomFilter =  filterArrayList.get(i - 2);
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
                        RoomFilter roomFilter =  filterArrayList.get(i - 2);
                        row.addView(addTextView(roomFilter.getEfficiency()+"%"+" | "+roomFilter.getParticleSize()+"µm"));
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
                    row.addView(addTextView(" 42 "));
                    //row.addView(addEditTextView(i));
                }

            }
            test4_table_layout4.addView(row);
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
                    row.addView(addTextView(" SLP of DL for Tests\n after Installation** "));
                } else {
                    if (null != filterArrayList && filterArrayList.size() > 0) {
                        RoomFilter roomFilter =  filterArrayList.get(i - 2);
                        //row.addView(addTextView(roomFilter.getSpecification()+"%"));
                        row.addView(addTextViewWithTagIds(i,test4Id,txtSlpDlpList,roomFilter.getSpecification()+"%"));
                        test4Id++;
                    }
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
                    row.addView(addTextView(" Obtained Test Results\n (% Leakage) "));
                } else {
                    //row.addView(addTextView(" 0.0015 "));
                    row.addView(addEditTextView(i));
                }

            }
            test4_table_layout6.addView(row);
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
                    row.addView(addTextPassFail("", i));
                }

            }
            test4_table_layout7.addView(row);
        }

        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();

    }


    private void BuildTableTest3(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Grille/Filter ID No\n "));
                } else {
                    if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                        HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
                        Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                        row.addView(addTextView(grill.get(ValdocDatabaseHandler.GRILL_GRILLCODE).toString()));
                    } else {
                        row.addView(addTextView("grillAndSizeFromGrill"));
                    }
                }

            }
            test3_table_layout.addView(row);
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
                    row.addView(addTextView(" Measured Supply Air\n Velocity in cfm (in cfm) "));
                } else {
                    row.addView(addEditTextView(i));
                }
            }
            test3_table_layout2.addView(row);
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
                    row.addView(addTextView(" Total Air Flow Rate\n in cfm (TFR)"));
                } else {
                    //row.addView(addTextViewWithoutBorder("0"));
                    row.addView(addTextViewWithIdsNoBorder(i, totalAirFlowRateIds, totalAirFlowRateTxtList));
                }
            }
            test3_table_layout3.addView(row);
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
                    row.addView(addTextView(" Room Volume in\n ft3(RV)"));
                } else {
                    // row.addView(addTextViewWithoutBorder("490"));
                    row.addView(addTextViewWithIdsNoBorder(i, 0, roomVolumeTxtList));
                }
            }
            test3_table_layout4.addView(row);
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
                    row.addView(addTextView("No. of Air Changes/Hr\n ((TFR/RV)x60))"));
                } else {
                    //row.addView(addTextViewWithoutBorder("490"));
                    row.addView(addTextViewWithIdsNoBorder(i, 0, airChangeTxtList));
                }
            }
            test3_table_layout5.addView(row);
        }

        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();


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
                    row.addView(addTextView(" Grille / Filter ID\n "));
                } else {
                    if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                        HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
                        Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                        row.addView(addTextView(grill.get(ValdocDatabaseHandler.GRILL_GRILLCODE).toString()));
                    } else {
                        row.addView(addTextView("grillAndSizeFromGrill"));
                    }
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
                    row.addView(addTextView(" Grill/Filter Size\n in ft2(A)"));
                } else {
                    // row.addView(addTextView("1.9"));
                    HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
                    float filterSize = 0.0f;
                    if (!grill.isEmpty())
                        filterSize = Float.parseFloat(grill.get(ValdocDatabaseHandler.GRILL_EFFECTIVEAREA).toString());
                    row.addView(addTextViewWithTagIds(i, filterSizeIds, filterSizeTxtViewList, filterSize+""));

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
                    row.addView(addEditTextView(i));
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
                    row.addView(addTextViewWithTagIds(i, airFlowRateIds, airFlowRateTxtViewList, "0"));
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
                    // row.addView(addTextViewWithoutBorder("490"));
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

    private void BuildTable(int rows, int cols) {
        //third section
        // outer for loop
        Log.d("valdoc", "DynamicTableActivity BuildTable" + "rows=" + rows + " cols=" + cols);
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" V "));
                } else {
                    row.addView(addResultTextView(i));
                }
            }
            table_layout3.addView(row);

        }

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
                    //becouse i starts with 1 so that i-2
                    row.addView(addTextView(filterList[i - 2]));
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
                    //tv.setText(84+i+j+"");
                    row.addView(addEditTextView(i));
                }
            }
            table_layout2.addView(row);
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

    int idPassFailTv = 300;
    private TextView addTextPassFail(String textValue,int tagRows) {
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

    private TextView addStretchedTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
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

    private TextView addTextViewWithTagIds(int Tag, int Ids,
                                           ArrayList<TextView> txtViewList, String value) {
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
        tv.setText(value);
        Log.d(TAG, "TAG & idCountTv " + Ids);
        tv.setId(Ids);
        tv.setTag(Tag);
        Ids++;
        txtViewList.add(tv);
        return tv;
    }

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
        Log.d(TAG, "ResultS idCountTv " + idCountTv);
        tv.setId(idCountTv);
        tv.setTag(rowsNo);
        idCountTv++;
        txtViewList.add(tv);
        return tv;
    }

    private EditText addEditTextView(int rowNo) {
        EditText editTv = new EditText(this);
        editTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        editTv.setBackgroundResource(R.drawable.border);
        editTv.setPadding(8, 5, 8, 5);
        editTv.setTextColor(getResources().getColor(R.color.black));
        editTv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        //editTv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        editTv.setEms(4);
        editTv.setSingleLine(true);
        editTv.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, "EditText idCountEtv " + idCountEtv);
        editTv.setId(idCountEtv);
        editTv.setTag(rowNo);
        editTv.addTextChangedListener((new TextValidator(
                DynamicTableActivity.this, idCountEtv)));
        editTextList.add(editTv);
        idCountEtv++;
        return editTv;
    }

    public class TextValidator implements TextWatcher {

        private Context mContext;
        private EditText mEditText;
        private int viewById = 0, tagF = 0;

        public TextValidator(Context mContext, int viewById) {
            this.mContext = mContext;
            //this.mEditText = viewById;
            this.viewById = viewById;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                if (!"".equals(charSequence.toString()))
                    inputValue = inputValue - Integer.parseInt(charSequence.toString());
                Log.d(TAG, " Removed inputValue " + inputValue);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.d(TAG, " EdiTextChange : ii " + viewById + " editable : " + editable.toString());
            try {
                if (!"".equals(editable.toString()))
                    inputValue = Integer.parseInt(editable.toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            Log.d(TAG, " Added inputValue " + inputValue);

            //Add EditText value in ArrayList with position
            for (int i = 0; i < editTextList.size(); i++) {
                if (editTextList.get(i).getId() == viewById) {
                    rowTagHashMap.put(editTextList.get(i).getId(), (int) editTextList.get(i).getTag());
                    inputDataHashMap.put(editTextList.get(i).getId(), inputValue);
                    tagF = (int) editTextList.get(i).getTag();
                }
            }

            //Calculation Test 1 Specific
            if ("RD_AV_5".equals(testType)) {
                //TextView set Value;
                for (int i = 0; i < txtViewList.size(); i++) {
                    Log.d(TAG, " TextView : Tag " + txtViewList.get(i).getTag() + " tagF " + tagF);
                    if (txtViewList.get(i).getTag().equals(tagF)) {
                        TextView tvl = txtViewList.get(i);
                        tvl.setText(getRoundedAverageValue(tagF) + "");
                        resultDataHashMap.put(tvl.getId(), getRoundedAverageValue(tagF));

                        // Pass Fail Calculation
                        TextView txtPassFail = txtPassFailList.get(i);
                        if(tvl.getTag()==txtPassFail.getTag()){
                            if(getRoundedAverageValue(tagF)>72 && getRoundedAverageValue(tagF)<108){
                                txtPassFail.setTextColor(getResources().getColor(R.color.blue));
                                txtPassFail.setText(" PASS ");
                                passFailHashMap.put(txtPassFail.getId()," PASS ");
                            }else{
                                txtPassFail.setTextColor(getResources().getColor(R.color.red));
                                txtPassFail.setText(" FAIL ");
                                passFailHashMap.put(txtPassFail.getId(), " FAIL ");
                            }
                        }

                    }

                }
            }
            //calculation Test 2 specific
            if ("RD_ACPH_AV".equals(testType)) {
                //TextView set Value;
                for (int i = 0; i < txtViewList.size(); i++) {
                    Log.d(TAG, " TextView : Tag " + txtViewList.get(i).getTag() + " tagF " + tagF);
                    if (txtViewList.get(i).getTag().equals(tagF)) {
                        TextView tvl = txtViewList.get(i);
                        tvl.setText(getRoundedAverageValue(tagF) + "");
                        resultDataHashMap.put(tvl.getId(), getRoundedAverageValue(tagF));
                    }
                }
                //AirFlow Rate in cfm(AxAv)
                for (int i = 0; i < airFlowRateTxtViewList.size(); i++) {
                    Log.d(TAG, " AirFlowRateText : Tag " + airFlowRateTxtViewList.get(i).getTag() + " tagF " + tagF);
                    if (airFlowRateTxtViewList.get(i).getTag().equals(tagF)) {
                        TextView tvl = airFlowRateTxtViewList.get(i);
                        tvl.setText(getMultiplicationAirFlow((int) tvl.getTag(), txtViewList.get(i).getText().toString()) + "");
                        resultDataHashMap2.put(tvl.getId(), getMultiplicationAirFlow((int) tvl.getTag(), txtViewList.get(i).getText().toString()));
                    }
                }
                //Total AirFlow Rate (sum of AirFlow Rate)
                if (totalAirFlowRateTxtList != null && totalAirFlowRateTxtList.size() > 0) {
                    int middleTxt = totalAirFlowRateTxtList.size() / 2;
                    TextView mtvl = totalAirFlowRateTxtList.get(middleTxt);
                    totalAirFlowRate = getSumofAirVelocity(airFlowRateTxtViewList);
                    mtvl.setText(totalAirFlowRate + "");
                    Log.d(TAG, " SumofAirVelocity : " +totalAirFlowRate);

                }

                //AirFlow Change calculation
                if (roomVolumeTxtList != null && roomVolumeTxtList.size() > 0) {
                    float roomVolume = 0;
                    float TFR = 0;
                    try {
                        roomVolume = Float.parseFloat(roomVolumeTxtList.get(roomVolumeTxtList.size() / 2)
                                .getText().toString().trim());
                        TFR = Float.parseFloat(totalAirFlowRateTxtList.get(totalAirFlowRateTxtList.size() / 2)
                                .getText().toString().trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (airChangeTxtList != null && airChangeTxtList.size() > 0) {
                        TextView airChangeTxt = airChangeTxtList.get(airChangeTxtList.size() / 2);
                        AirChangeValue = getAirChangeCalculation(TFR, roomVolume);
                        airChangeTxt.setText(AirChangeValue + "");
                        Log.d(TAG, " AirChangeValue : " + AirChangeValue);
                    }
                }
            }
            //Calculation Test 3 specific
            if ("RD_ACPH_H".equals(testType)) {

                //Total AirFlow Rate (sum of AirFlow Rate)
                if (totalAirFlowRateTxtList != null && totalAirFlowRateTxtList.size() > 0) {
                    int middleTxt = totalAirFlowRateTxtList.size() / 2;
                    TextView mtvl = totalAirFlowRateTxtList.get(middleTxt);
                    totalAirFlowRate = getSumOfAirSupply(editTextList);
                    mtvl.setText(totalAirFlowRate + "");
                    Log.d(TAG, " totalAirFlowValue : " + totalAirFlowRate);
                }

                //AirFlow Change calculation
                if (roomVolumeTxtList != null && roomVolumeTxtList.size() > 0) {
                    float roomVolume = 0;
                    float TFR = 0;
                    try {
                        roomVolume = Float.parseFloat(roomVolumeTxtList.get(roomVolumeTxtList.size() / 2)
                                .getText().toString().trim());
                        TFR = Float.parseFloat(totalAirFlowRateTxtList.get(totalAirFlowRateTxtList.size() / 2)
                                .getText().toString().trim());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if (airChangeTxtList != null && airChangeTxtList.size() > 0) {
                        TextView airChangeTxt = airChangeTxtList.get(airChangeTxtList.size() / 2);
                        AirChangeValue = getAirChangeCalculation(TFR, roomVolume);
                        airChangeTxt.setText( AirChangeValue+ "");
                        Log.d(TAG, " AirChangeValue : " + AirChangeValue);
                    }
                }
            }
            //Calculation Test 4 Specific
            if("RD_FIT".equals(testType)){
                for (int i = 0; i < txtSlpDlpList.size(); i++) {
                    //getLeakageValue(tagF);
                    if (txtSlpDlpList.get(i).getTag().equals(tagF)) {
                        if(txtSlpDlpList.get(i).getText().toString().trim()!= null
                                && !"".equals(txtSlpDlpList.get(i).getText().toString().trim())){
                            double slpDlpValue = Double.parseDouble(txtSlpDlpList.get(i).getText().toString().trim().replace("%","0"));
                            // Pass Fail Calculation
                            TextView txtPassFail = txtPassFailList.get(i);
                            if(txtSlpDlpList.get(i).getTag()==txtPassFail.getTag()){
                                if(slpDlpValue>getLeakageValue(tagF)){
                                    txtPassFail.setTextColor(getResources().getColor(R.color.blue));
                                    txtPassFail.setText(" PASS ");
                                    passFailHashMap.put(txtPassFail.getId()," PASS ");
                                }else{
                                    txtPassFail.setTextColor(getResources().getColor(R.color.red));
                                    txtPassFail.setText(" FAIL ");
                                    passFailHashMap.put(txtPassFail.getId(), " FAIL ");
                                }
                            }
                        }

                    }
                }

            }

            //Calculation Test 5 Specific
            if ("RD_PC_3".equals(testType)) {
                //TextView set Value;
                for (int i = 0; i < txtViewList.size(); i++) {
                    Log.d(TAG, " TextView : Tag " + txtViewList.get(i).getTag() + " tagF " + tagF);
                    if (txtViewList.get(i).getTag().equals(tagF)) {
                        TextView tvl = txtViewList.get(i);
                        tvl.setText(getRoundedAverageValue(tagF) + "");

                        resultDataHashMap.put(tvl.getId(), getRoundedAverageValue(tagF));
                        setMean(getRoundedAverageValue(tagF));

                        Log.d(TAG, "TagF : "+tagF+" rows : "+rows);
                        if (tagF <= rows) {
                            meanValue1 = getMeanAverageValue(resultDataHashMap, tagF);
                            TextView txtView = RDPC3TxtList.get(0);
                            txtView.setText(meanValue1 + "");

                            stdDev1 = getStdDev(resultDataHashMap, tagF);
                            TextView txtView2 = RDPC3TxtList.get(1);
                            txtView2.setText(stdDev1 + "");
                        } else {
                            meanValue2 = getMeanAverageValue(resultDataHashMap, tagF);
                            TextView txtView = RDPC3TxtList2.get(0);
                            txtView.setText(meanValue2 + "");

                            stdDev2 = getStdDev(resultDataHashMap, tagF);
                            TextView txtView2 = RDPC3TxtList2.get(1);
                            txtView2.setText(stdDev2 + "");
                        }
                    }
                }
            }

        }
    }

    private long getRoundedAverageValue(int count) {
        long avg = 0;
        int tagCount = 0;
        if (!isClearClicked) {
            try {
                for (Map.Entry m : rowTagHashMap.entrySet()) {
                    if (m.getValue().equals(count)) {
                        if (inputDataHashMap.get(m.getKey()) != null &&
                                !"".equals(inputDataHashMap.get(m.getKey()))) {
                            avg = avg + inputDataHashMap.get(m.getKey());
                            if (inputDataHashMap.get(m.getKey()) > 0)
                                tagCount = tagCount + 1;
                        }
                    }
                    System.out.println(" Avg " + avg);
                    System.out.println(m.getKey() + " " + m.getValue());
                    System.out.println("inputDataHashMap.get(m.getKey())" + "  " + inputDataHashMap.get(m.getKey()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Avg: Method sum: " + avg + " count : " + tagCount);
        double abc = (double) avg / (double) tagCount;
        Log.d(TAG, "Float Value :" + abc);
        avg = Math.round(abc);
        Log.d(TAG, "Avg: Method Avg: " + avg);
        return avg;
    }

    private float getMultiplicationAirFlow(int ids, String avgVelocity) {
        Log.d(TAG, " ids " + ids + " avgVelocity " + avgVelocity);
        int velocity = 0;
        float filterSize = 0;
        if (avgVelocity != null && !"".equals(avgVelocity)) {
            velocity = Integer.parseInt(avgVelocity);
        }
        if (filterSizeTxtViewList != null && filterSizeTxtViewList.size() > 0) {
            for (int i = 0; i < filterSizeTxtViewList.size(); i++) {
                if (filterSizeTxtViewList.get(i).getTag().equals(ids)) {
                    try {
                        filterSize = Float.parseFloat(
                                filterSizeTxtViewList.get(i).getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return (velocity * filterSize);
    }

    private float getSumofAirVelocity(ArrayList<TextView> airFlowRateTxtViewList) {
        float totalSum = 0.0f;
        for (int i = 0; i < airFlowRateTxtViewList.size(); i++) {
            if (airFlowRateTxtViewList.get(i).getText().toString() != null &&
                    !"".equals(airFlowRateTxtViewList.get(i).getText().toString()))
                totalSum = totalSum + Float.parseFloat(airFlowRateTxtViewList.get(i).getText().toString());
        }
        Log.d(TAG, " totalSum formula:" + totalSum);
        return totalSum;
    }

    private float getSumOfAirSupply(ArrayList<EditText> airFlowRateTxtViewList) {
        float totalSum = 0.0f;
        if (airFlowRateTxtViewList != null && airFlowRateTxtViewList.size() > 0) {
            for (int i = 0; i < airFlowRateTxtViewList.size(); i++) {
                if (airFlowRateTxtViewList.get(i).getText().toString() != null &&
                        !"".equals(airFlowRateTxtViewList.get(i).getText().toString()))
                    totalSum = totalSum + Float.parseFloat(airFlowRateTxtViewList.get(i).getText().toString());
            }
        }
        Log.d(TAG, " totalSum formula:" + totalSum);
        return totalSum;
    }

    private int getAirChangeCalculation(float TFR, float RV) {
        Log.d(TAG, " AirChangeCalculation : " + (TFR / RV) * 60 + " int : " + (int) Math.round(((TFR / RV) * 60)));
        return (int) Math.round(((TFR / RV) * 60));
    }

    private long getMeanAverageValue(HashMap<Integer, Long> meanAverageList2, int tagF) {
        long meanAvg = 0;
        int tagCount = 0;
        for (Map.Entry m : meanAverageList2.entrySet()) {
            if (meanAverageList2.get(m.getKey()) != null && !"".equals(meanAverageList2.get(m.getKey()))) {
                if((int)m.getKey()<=rows && tagF<rows){
                    meanAvg = meanAvg + meanAverageList2.get(m.getKey());
                    if (meanAverageList2.get(m.getKey()) > 0)
                        tagCount = tagCount + 1;
                }else if((int)m.getKey()>=rows && tagF>rows){
                    meanAvg = meanAvg + meanAverageList2.get(m.getKey());
                    if (meanAverageList2.get(m.getKey()) > 0)
                        tagCount = tagCount + 1;
                }
                System.out.println(" Mean Avg Add " + meanAvg);
                System.out.println(" Mean Avg Key "+m.getKey() + " Mean Avg Value " + m.getValue());
            }
        }
        Log.d(TAG, "Mean Avg: Method sum: " + meanAvg + " count : " + tagCount);
        double abc = (double) meanAvg / (double) tagCount;
        Log.d(TAG, "Float Value :" + abc);
        meanAvg = Math.round(abc);
        Log.d(TAG, "Avg: Method Avg: " + meanAvg);

        return meanAvg;
    }

    private double getVariance(HashMap<Integer, Long> meanAverageList2, int tagF) {
        double mean = getMean();
        double temp = 0;
        int tagCount = 0;
        try {
            for (Map.Entry m : rowTagHashMap.entrySet()) {
                if (m.getValue().equals(tagF)) {
                    if (inputDataHashMap.get(m.getKey()) != null &&
                            !"".equals(inputDataHashMap.get(m.getKey()))) {
                        temp += (mean -  inputDataHashMap.get(m.getKey())) * (mean -  inputDataHashMap.get(m.getKey()));
                        if (inputDataHashMap.get(m.getKey()) > 0)
                            tagCount = tagCount + 1;
                    }
                }
                System.out.println(" Variance Add " + temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Tag Count .. " + tagCount);

        double resultVariance = 0;
        if(tagCount>1){
            resultVariance = temp / (tagCount-1);
        }else{
            resultVariance = temp /tagCount;
        }

        System.out.println(" Variance Result " + resultVariance + " Rounded Value " + Math.round(resultVariance));
        return Math.round(resultVariance);
    }

   private  double getStdDev(HashMap<Integer, Long> meanAverageList2, int tagF)
    {
        double stdDev = Math.sqrt(getVariance(meanAverageList2, tagF));
        System.out.println(" getStdDev Result " + stdDev+" rounded StdValue "+Math.round(stdDev));
        return Math.round(stdDev);
    }

    //Test 4 Specific
    private double getLeakageValue(int count) {
        double leakageValue = 0.0;
        if (!isClearClicked) {
            try {
                for (Map.Entry m : rowTagHashMap.entrySet()) {
                    if (m.getValue().equals(count)) {
                        if (inputDataHashMap.get(m.getKey()) != null &&
                                !"".equals(inputDataHashMap.get(m.getKey()))) {
                            leakageValue = inputDataHashMap.get(m.getKey());
                        }
                    }
                    System.out.println("inputDataHashMap.get(m.getKey())" + "  " + inputDataHashMap.get(m.getKey()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Leakage Value : " + leakageValue);
        return leakageValue;
    }

    private void initRes() {
        clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        verify_btn = (Button) findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(this);
        addRow_Btn = (Button) findViewById(R.id.add_row_btn);
        addRow_Btn.setOnClickListener(this);
        deleteRow_Btn = (Button) findViewById(R.id.delete_row_btn);
        deleteRow_Btn.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        instrumentNo = (TextView) findViewById(R.id.instrument_no);
        testerName = (TextView) findViewById(R.id.tester_name);
        testerName.setText(userName);
        if (loginUserType.equals("CLIENT")) {
            instrumentNo.setText(clientInstrument.getcInstrumentName());
        } else {
            instrumentNo.setText(partnerInstrument.getpInstrumentName());
        }

        //Test 1
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tableLayout2);
        table_layout3 = (TableLayout) findViewById(R.id.tableLayout3);
        table_layout4 = (TableLayout) findViewById(R.id.tableLayout4);
        if ("RD_AV_5".equalsIgnoreCase(testType)) {
            instrumentNo = (TextView) findViewById(R.id.instrument_no);
            testerName = (TextView) findViewById(R.id.tester_name);
            testerName.setText(userName);
            if (loginUserType.equals("CLIENT")) {
                instrumentNo.setText(clientInstrument.getcInstrumentName());
            } else {
                instrumentNo.setText(partnerInstrument.getpInstrumentName());
            }
            findViewById(R.id.test1_table_ll).setVisibility(View.VISIBLE);
        }

        //Test 2
        test2_table_layout = (TableLayout) findViewById(R.id.test2_tableLayout1);
        test2_table_layout2 = (TableLayout) findViewById(R.id.test2_tableLayout2);
        test2_table_layout3 = (TableLayout) findViewById(R.id.test2_tableLayout3);
        test2_table_layout4 = (TableLayout) findViewById(R.id.test2_tableLayout4);
        test2_table_layout5 = (TableLayout) findViewById(R.id.test2_tableLayout5);
        test2_table_layout6 = (TableLayout) findViewById(R.id.test2_tableLayout6);
        test2_table_layout7 = (TableLayout) findViewById(R.id.test2_tableLayout7);
        test2_table_layout8 = (TableLayout) findViewById(R.id.test2_tableLayout8);
        if ("RD_ACPH_AV".equalsIgnoreCase(testType)) {
            instrumentNo = (TextView) findViewById(R.id.instrument_no_test2);
            testerName = (TextView) findViewById(R.id.tester_name_test2);
            testerName.setText(userName);
            if (loginUserType.equals("CLIENT")) {
                instrumentNo.setText(clientInstrument.getcInstrumentName());
            } else {
                instrumentNo.setText(partnerInstrument.getpInstrumentName());
            }
            findViewById(R.id.test2_table_ll).setVisibility(View.VISIBLE);
        }

        //Test3
        test3_table_layout = (TableLayout) findViewById(R.id.test3_tableLayout1);
        test3_table_layout2 = (TableLayout) findViewById(R.id.test3_tableLayout2);
        test3_table_layout3 = (TableLayout) findViewById(R.id.test3_tableLayout3);
        test3_table_layout4 = (TableLayout) findViewById(R.id.test3_tableLayout4);
        test3_table_layout5 = (TableLayout) findViewById(R.id.test3_tableLayout5);
        if ("RD_ACPH_H".equalsIgnoreCase(testType)) {
            instrumentNo = (TextView) findViewById(R.id.instrument_no_test3);
            testerName = (TextView) findViewById(R.id.tester_name_test3);
            testerName.setText(userName);
            if (loginUserType.equals("CLIENT")) {
                instrumentNo.setText(clientInstrument.getcInstrumentName());
            } else {
                instrumentNo.setText(partnerInstrument.getpInstrumentName());
            }
            findViewById(R.id.test3_table_ll).setVisibility(View.VISIBLE);
        }

        //Test4
        test4_table_layout = (TableLayout) findViewById(R.id.test4_tableLayout1);
        test4_table_layout2 = (TableLayout) findViewById(R.id.test4_tableLayout2);
        test4_table_layout3 = (TableLayout) findViewById(R.id.test4_tableLayout3);
        test4_table_layout4 = (TableLayout) findViewById(R.id.test4_tableLayout4);
        test4_table_layout5 = (TableLayout) findViewById(R.id.test4_tableLayout5);
        test4_table_layout6 = (TableLayout) findViewById(R.id.test4_tableLayout6);
        test4_table_layout7 = (TableLayout) findViewById(R.id.test4_tableLayout7);
        if ("RD_FIT".equalsIgnoreCase(testType)) {
            instrumentNo = (TextView) findViewById(R.id.instrument_no_test4);
            testerName = (TextView) findViewById(R.id.tester_name_test4);
            testerName.setText(userName);
            if (loginUserType.equals("CLIENT")) {
                instrumentNo.setText(clientInstrument.getcInstrumentName());
            } else {
                instrumentNo.setText(partnerInstrument.getpInstrumentName());
            }
            findViewById(R.id.test4_table_ll).setVisibility(View.VISIBLE);
        }

        //Test5
        test5_table_layout = (TableLayout) findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout) findViewById(R.id.test5_tableLayout2);
        test5_table_layout2_1 = (TableLayout) findViewById(R.id.test5_tableLayout2_1);
        test5_table_layout3 = (TableLayout) findViewById(R.id.test5_tableLayout3);
        test5_table_layout3_1 = (TableLayout) findViewById(R.id.test5_tableLayout3_1);
        test5_table_layout4 = (TableLayout) findViewById(R.id.test5_tableLayout4);
        test5_table_layout4_1 = (TableLayout) findViewById(R.id.test5_tableLayout4_1);
        test5_table_layout5 = (TableLayout) findViewById(R.id.test5_tableLayout5);
        test5_table_layout5_1 = (TableLayout) findViewById(R.id.test5_tableLayout5_1);
        if ("RD_PC_3".equalsIgnoreCase(testType)) {
            instrumentNo = (TextView) findViewById(R.id.instrument_no_test5);
            testerName = (TextView) findViewById(R.id.tester_name_test5);
            testerName.setText(userName);
            if (loginUserType.equals("CLIENT")) {
                instrumentNo.setText(clientInstrument.getcInstrumentName());
            } else {
                instrumentNo.setText(partnerInstrument.getpInstrumentName());
            }
            findViewById(R.id.test5_table_ll).setVisibility(View.VISIBLE);
        }

        //Test6
        test6A_table_layout = (TableLayout) findViewById(R.id.test6A_tableLayout1);
        test6A_table_layout2 = (TableLayout) findViewById(R.id.test6A_tableLayout2);
        test6A_table_layout3 = (TableLayout) findViewById(R.id.test6A_tableLayout3);
        if ("RD_RCT".equalsIgnoreCase(testType)) {
            instrumentNo = (TextView) findViewById(R.id.instrument_no_test6);
            testerName = (TextView) findViewById(R.id.tester_name_test6);
            testerName.setText(userName);
            if (loginUserType.equals("CLIENT")) {
                instrumentNo.setText(clientInstrument.getcInstrumentName());
            } else {
                instrumentNo.setText(partnerInstrument.getpInstrumentName());
            }
            findViewById(R.id.test6A_table_ll).setVisibility(View.VISIBLE);
        }
    }

    private void removeView() {
        //Test1
        if ("RD_AV_5".equalsIgnoreCase(testType)) {
            table_layout.removeAllViews();
            table_layout2.removeAllViews();
            table_layout3.removeAllViews();
            table_layout4.removeAllViews();
        }

        //Test 2
        if ("RD_ACPH_AV".equalsIgnoreCase(testType)) {
            test2_table_layout.removeAllViews();
            test2_table_layout2.removeAllViews();
            test2_table_layout3.removeAllViews();
            test2_table_layout4.removeAllViews();
            test2_table_layout5.removeAllViews();
            test2_table_layout6.removeAllViews();
            test2_table_layout7.removeAllViews();
            test2_table_layout8.removeAllViews();
        }

        //Test 3
        if ("RD_ACPH_H".equalsIgnoreCase(testType)) {
            test3_table_layout.removeAllViews();
            test3_table_layout2.removeAllViews();
            test3_table_layout3.removeAllViews();
            test3_table_layout4.removeAllViews();
            test3_table_layout5.removeAllViews();
        }

    }

    public double getMean() {
        return mean;
    }
    public void setMean(double mean) {
        this.mean = mean;
    }
}
