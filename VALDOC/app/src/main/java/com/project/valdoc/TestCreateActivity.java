package com.project.valdoc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.AhuFilter;
import com.project.valdoc.intity.ApplicableTestAhu;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.EquipmentGrill;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.utility.Utilityies;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestCreateActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "TestCreateActivity";
    private Spinner equipmentAhuOrRoomSpinner, applicableTestSpinner, instrumentSpiner, ahuSpinner, roomSpinner, roomNoSpinner;
    private List<String> equipmentAhuOrRoomList, applicableTestList, instrumentList, ahuList, roomNoList, roomList, testItemList, equipmentList, equipmentNoList, applicableTestRoomList, applicableTestEquipmentList, applicableTestAhuList;
    private ArrayAdapter<String> equipmentAhuOrRoomAdapter, applicableTestAdapter, instrumentAdapter, equipmentadApter, ahuAdapter, roomAdapter, roomNoAdapter;
    private ArrayList<ClientInstrument> clientInstrumentArrayList = null;
    private ArrayList<PartnerInstrument> partnerInstrumentArrayList = null;
    private ArrayList<Ahu> mAhuArrayList = null;
    private ArrayList<Equipment> mEquipmentArrayList = null;
    private ArrayList<EquipmentGrill> mEquipmentGrillArrayList = null;
    private ArrayList<EquipmentFilter> mEquipmentFilterArrayList = null;
    private ArrayList<AhuFilter> mAhuFilterArrayList = null;
    private ArrayList<Room> mRoomArrayList = null;
    private ArrayList<Room> mRoomNoArrayList = null;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ApplicableTestEquipment mApplicableTestEquipment = null;
    private ApplicableTestAhu mApplicableTestAhu = null;
    private String[] equipmentAhuOrRoomTestCodeList = new String[]{"ROOM", "AHU", "EQUIPMENT"};
    private String[] roomsTestValues = new String[]{"Select Required Test", "Airchanges/hr Test (ACPH_AV)",
            "Airchanges/hr Test (ACPH_H)", "Filter Integrity Test (FIT)",
            "Particle Count Test (PCT)", "Recovery Test (RCT)"};

    private String[] ahuTestValues = new String[]{"Select Required Test", "Airflow Test (ARD_AF_AHU)",
            "Filter Integrity Test (ARD_FIT_AHU)"};

    private String[] equipmentTestValues = new String[]{"Select Required Test", "Airvelocity Test (ERD_AV)",
            "Filter Integrity Test (ERD_FIT)", "Particle Count Test (ERD_PCT)",
            "Recovery Test (ERD_RCT)"};

    private String[] roomsTestCode = new String[]{"", "ACPH_AV", "ACPH_H", "FIT", "PCT", "RCT"};
    private String[] ahuTestCode = new String[]{"", "ARD_AF_AHU", "ARD_FIT_AHU"};
    private String[] equipmentTestCode = new String[]{"", "ERD_AV", "ERD_FIT", "ERD_PCT", "ERD_RCT"};

    //testcode to search from db
    private String[] searchRoomsTestCode = new String[]{"", "ACPH_AV", "ACPH_H", "FIT", "PCT", "RCT"};
    private String[] searchAhuTestCode = new String[]{"", "AV", "FIT"};
    private String[] searchEquipmentTestCode = new String[]{"", "AV", "FIT", "PCT", "RCT"};

    private ImageView submit;
    private TextView user_name;
    private EditText witnessFirst;
    private EditText witnessSecond;
    private EditText witnessThird;
    private String userName = "";
    private String loginUserType = "";
    private String loginUserPartnerId = "";
    private int userPartnerId;
    private int appUserId;
    private int mAhuId = 0;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(TestCreateActivity.this);

    //test name and details
    public static final String AV = "AV";
    public static final String ACPHAV = "ACPH_AV";
    public static final String ACPHH = "ACPH_H";
    public static final String FIT = "FIT";
    public static final String PCT = "PCT";
    public static final String RCT = "RCT";

    //old
//    public static final String AV = "RD_AV_5";
//    public static final String ACPHAV = "RD_ACPH_AV";
//    public static final String ACPHH = "RD_ACPH_H";
//    public static final String FIT = "RD_FIT";
//    public static final String PCT = "RD_PC_3";
//    public static final String RCT = "RD_RCT";

    //spiner data storage
    private static String spinerInstrument;
    private static String spinerAhuOrEquipment = "";
    private static int spinerAhu;
    private static String spinerRoom;
    private static String spinerTestType;
    private static int spinerEquipment;
    private String mPartnerName;

    //Storing spinner position to show selected
    // after Certificate submit
    int instrumentSpinerPos = 0, equipmentOrAhuSpinnerPos = 0,
            equipmentSpinnerPos = 0, ahuSpinnerPos = 0,
            roomSpinnerPos = 0, testSpinnerPos = 0;
    String witness1, witness2, witness3;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);

        //Resource Initialization
        spinerInitialization();

        //get user name from login screen
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            userName = null;
            loginUserType = null;
        }

        //Screen Navigation after submit button hit from Certificate screen
        else if (getIntent().hasExtra(AV) || getIntent().hasExtra(ACPHAV) || getIntent().hasExtra(ACPHH)
                || getIntent().hasExtra(FIT) || getIntent().hasExtra(PCT) || getIntent().hasExtra(RCT)) {

            //Setting User Information
            userName = sharedpreferences.getString("USERNAME", "");
            loginUserType = sharedpreferences.getString("USERTYPE", "");
            appUserId = sharedpreferences.getInt("APPUSERID", 0);
            userPartnerId = sharedpreferences.getInt("PARTNERID", 0);
            //Setting Witness Data
            witnessFirst.setText(sharedpreferences.getString("witness1", ""));
            witnessSecond.setText(sharedpreferences.getString("witness2", ""));
            witnessThird.setText(sharedpreferences.getString("witness3", ""));
        } else {
            userName = extras.getString("USERNAME");
            loginUserType = extras.getString("USERTYPE");
            appUserId = extras.getInt("APPUSERID");
            userPartnerId = extras.getInt("PARTNERID");
        }

        mPartnerName = getPartnerName();
        //table creation
        // insertDataInTable();
//        instrument spinner creation
        listItemCreation();
        spinnerCreation();
        initButton();

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(TestCreateActivity.this, mActionBar, userName);
    }

    private String getPartnerName() {
        String partnerName = "";
        int partnerId = sharedpreferences.getInt("PARTNERID", 0);
        Log.d("Avinash", "partnerId=" + partnerId);
        if (partnerId != 0)
            partnerName = mValdocDatabaseHandler.getPartnerNameInfo(partnerId);
        Log.d("Avinash", "partnerName=" + partnerName);
        return partnerName;
    }

    private void initButton() {
        submit = (ImageView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "equipmentOrAhuSpinner : Pos:submit button clicked");
                if (validationSpiner()) {
                    Log.d(TAG, "equipmentOrAhuSpinner : Pos:submit button clicked validation complete");
                    //Saving Data in Shared Preferences
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("instrumentSpinerPos", instrumentSpinerPos);
                    editor.putInt("equipmentOrAhuSpinnerPos", equipmentOrAhuSpinnerPos);
                    editor.putInt("instrumentSpinerPos", instrumentSpinerPos);
                    editor.putInt("equipmentSpinnerPos", equipmentSpinnerPos);
                    editor.putInt("ahuSpinnerPos", ahuSpinnerPos);
                    editor.putInt("roomSpinnerPos", roomSpinnerPos);
                    editor.putInt("testSpinnerPos", testSpinnerPos);
                    editor.putString("witness1", witnessFirst.getText().toString());
                    editor.putString("witness2", witnessSecond.getText().toString());
                    editor.putString("witness3", witnessThird.getText().toString());
                    editor.commit();
                    //redirect on test screen to perform the test
                    redirectOnTestScreen();

                } else {
                    Toast.makeText(TestCreateActivity.this, "Please select the test to be performed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void redirectOnTestScreen() {

        int pos = equipmentAhuOrRoomSpinner.getSelectedItemPosition();
        if (pos > 0) {
            if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("AHU")) {
                performAhuTest("AHU");
            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("EQUIPMENT")) {
                performEquipmentTest("EQUIPMENT");
            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("ROOM")) {
                performRoomTest("ROOM");
            }
        }
    }

    private void performEquipmentTest(String testBasedOn) {
        Log.d("Avinash", "performEquipmentTest");
        int testPosition = applicableTestSpinner.getSelectedItemPosition();
        if (AV.equals(searchEquipmentTestCode[testPosition])) {
            rdAv5Test(equipmentTestCode[testPosition], testBasedOn);
        } else if (FIT.equals(searchEquipmentTestCode[testPosition])) {
            rdFit(equipmentTestCode[testPosition], testBasedOn);
        } else if (PCT.equals(searchEquipmentTestCode[testPosition])) {
//            rdPc3(equipmentTestCode[testPosition],searchEquipmentTestCode[testPosition]);
            rdPc3(equipmentTestCode[testPosition], testBasedOn);
        } else if (RCT.equals(searchEquipmentTestCode[testPosition])) {
            rdRct(equipmentTestCode[testPosition], testBasedOn);
        } else {
            Toast.makeText(TestCreateActivity.this, "Please select the correct test to be performed", Toast.LENGTH_SHORT).show();
        }
    }

    private void performRoomTest(String testBasedOn) {
        Log.d("Avinash", "performRoomTest");
        int testPosition = applicableTestSpinner.getSelectedItemPosition();
        if (ACPHAV.equals(searchRoomsTestCode[testPosition])) {
            rdAcphAv(roomsTestCode[testPosition], testBasedOn);
        } else if (ACPHH.equals(searchRoomsTestCode[testPosition])) {
            rdAcphH(roomsTestCode[testPosition], testBasedOn);
        } else if (FIT.equals(searchRoomsTestCode[testPosition])) {
            rdFit(roomsTestCode[testPosition], testBasedOn);
        } else if (PCT.equals(searchRoomsTestCode[testPosition])) {
            rdPc3(roomsTestCode[testPosition], testBasedOn);
        } else if (RCT.equals(searchRoomsTestCode[testPosition])) {
//            rdRct(roomsTestCode[testPosition], searchRoomsTestCode[testPosition]);
            rdRct(roomsTestCode[testPosition], testBasedOn);
        } else {
            Toast.makeText(TestCreateActivity.this, "Please select the correct test to be performed", Toast.LENGTH_SHORT).show();
        }
    }

    private void performAhuTest(String testBasedOn) {
        Log.d("Avinash", "performAhuTest");
        int testPosition = applicableTestSpinner.getSelectedItemPosition();
        if (AV.equals(searchAhuTestCode[testPosition])) {
//            rdAv5Test(ahuTestCode[testPosition],searchAhuTestCode[testPosition]);
            rdAv5Test(ahuTestCode[testPosition], testBasedOn);
        } else if (FIT.equals(searchAhuTestCode[testPosition])) {
//            rdFit(ahuTestCode[testPosition],searchAhuTestCode[testPosition]);
            rdFit(ahuTestCode[testPosition], testBasedOn);
        } else {
            Toast.makeText(TestCreateActivity.this, "Please select the correct test to be performed", Toast.LENGTH_SHORT).show();
        }
    }

    private void rdAcphAv(String testCode, String testBasedOn) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", ACPHAV);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testBasedOn", testBasedOn);
        Log.d("valdoc", "TestCreateActivity loginUserType=" + loginUserType);
        Log.d("valdoc", "TestCreateActivity userName=" + userName);

        Log.d("valdoc", "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        intent.putExtra("PRTNERNAME", mPartnerName);

        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        //get room name,roomNo,and area id
        Log.d("valdoc", "TestCreateActivity :equipment:=" + roomSpinner.getSelectedItemPosition());
        Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("Room", room);
        intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());

        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

        //get filter list from equipment filter
        Log.d("valdoc", "TestCreateActivity :equipment id equipment1:=" + room.getRoomId());
        ArrayList<HashMap<String, String>> grillAndSizeFromGrill = mValdocDatabaseHandler.getGrillAndSizeFromGrill(room.getRoomId());
        intent.putExtra("GRILLIST", grillAndSizeFromGrill);
        Log.d("valdoc", "TestCreateActivity :grill size:=" + grillAndSizeFromGrill.size());
        startActivity(intent);
    }

    private void rdAcphH(String testCode, String testBasedOn) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", ACPHH);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testBasedOn", testBasedOn);
        Log.d("valdoc", "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        intent.putExtra("PRTNERNAME", mPartnerName);

        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        //get room name,roomNo,and area id
        Log.d("valdoc", "TestCreateActivity :equipment:=" + roomSpinner.getSelectedItemPosition());
        Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("Room", room);
        intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);

        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

        //get filter list from equipment filter
        Log.d("valdoc", "TestCreateActivity :equipment id equipment1:=" + room.getRoomId());
        ArrayList<HashMap<String, String>> grillAndSizeFromGrill = mValdocDatabaseHandler.getGrillAndSizeFromGrill(room.getRoomId());
        intent.putExtra("GRILLIST", grillAndSizeFromGrill);
        Log.d("valdoc", "TestCreateActivity :grill size:=" + grillAndSizeFromGrill.size());
        startActivity(intent);
    }

    private void rdFit(String testCode, String testBasedOn) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", FIT);
        intent.putExtra("testBasedOn", testBasedOn);
        intent.putExtra("testCode", testCode);
        Log.d("valdoc", "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        intent.putExtra("PRTNERNAME", mPartnerName);

        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }
        Room room = null;
        ArrayList<RoomFilter> filterArrayList = null;
        if (testBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            String[] roomDetails = null;
            //get room name,roomNo,and area id
            Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
            intent.putExtra("RoomDetails", roomDetails);
            intent.putExtra("Equipment", equipment);
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            //get filter list from equipment filter
            mEquipmentFilterArrayList = mValdocDatabaseHandler.getFilterFromEquipmentFilter(equipment.getEquipmentId());
            intent.putExtra("EquipmentFilter", mEquipmentFilterArrayList);
            Log.d("valdoc", "TestCreateActivity :equipment1:=" + mEquipmentGrillArrayList.size());
            ApplicableTestEquipment applicableTestEquipment = createApplicableTestEquipmentList(equipment.getEquipmentId());
            intent.putExtra("ApplicableTestEquipment", applicableTestEquipment);


        } else if (testBasedOn.equalsIgnoreCase("AHU")) {
            String[] roomDetails = null;
            //TO Do testspesification will be shown from room filter spesification
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
            int ahuIndex = ahuSpinner.getSelectedItemPosition();
            Ahu ahu = null;
            if (ahuIndex > 0) {
                ahu = mAhuArrayList.get(ahuIndex - 1);
            }
//            intent.putExtra("Ahu", ahu);
            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(ahu.getAhuId());
            intent.putExtra("RoomDetails", roomDetails);
            Log.d("valdoc", "TestCreateActivity roomDetails[2]=" + roomDetails[2]);
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            mAhuFilterArrayList = mValdocDatabaseHandler.getFilterFromAhuFilter(ahu.getAhuId());
            intent.putExtra("AhuFilter", mAhuFilterArrayList);
            ApplicableTestAhu applicableTestAhu = createApplicableTestAhuList(ahu.getAhuId());
            intent.putExtra("ApplicableTestAhu", applicableTestAhu);

        } else if (testBasedOn.equalsIgnoreCase("ROOM")) {

            //get room name,roomNo,and area id
            Log.d("valdoc", "TestCreateActivity :roomNoSpinner:=" + roomNoSpinner.getSelectedItemPosition());
            int pos = roomNoSpinner.getSelectedItemPosition() - 1;
            if (pos > 0) {
                room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
                intent.putExtra("Room", room);
                //take test specification from room filter
                filterArrayList = mValdocDatabaseHandler.getFromRoomFilter(room.getRoomId());
                Log.d("valdoc", "TestCreateActivity filterArrayList=" + filterArrayList.size());
                intent.putExtra("RoomFilter", filterArrayList);
            }
            //TO Do testspesification will be shown from room filter spesification
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
            //get area based on room area id
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
            Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

        }
        // location will be the size off rommfilter list
        startActivity(intent);
    }

    private void rdPc3(String testCode, String testBasedOn) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", PCT);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testBasedOn", testBasedOn);
        Log.d("valdoc", "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        intent.putExtra("PRTNERNAME", mPartnerName);

        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        //get room name,roomNo,and area id
        Log.d("valdoc", "TestCreateActivity :equipment:=" + roomSpinner.getSelectedItemPosition());
        Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("Room", room);
        intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());

        intent.putExtra("mApplicableTestRoom", mApplicableTestRoom);
        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        // location will be the size off rommfilter list
        startActivity(intent);
    }

    private void rdRct(String testCode, String testBasedOn) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", RCT);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testBasedOn", testBasedOn);
        Log.d("valdoc", "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        intent.putExtra("PRTNERNAME", mPartnerName);
        Log.d("Avinash", "bundle mPartnerName=" + mPartnerName);
        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        //get room name,roomNo,and area id
        Log.d("valdoc", "TestCreateActivity :equipment:=" + roomSpinner.getSelectedItemPosition());
        Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("Room", room);
        intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);
        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        // location will be the size off rommfilter list
        startActivity(intent);
    }


    private void rdAv5Test(String testCode, String testBasedOn) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", AV);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testBasedOn", testBasedOn);
        intent.putExtra("PRTNERNAME", mPartnerName);
        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        String[] roomDetails = null;
        if (testBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            //get room name,roomNo,and area id
//            Log.d("valdoc", "TestCreateActivity :equipment:=" + roomSpinner.getSelectedItemPosition());
//            Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
//            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
//            intent.putExtra("RoomDetails", roomDetails);
//            intent.putExtra("Equipment", equipment);
//            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
//            Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
//            intent.putExtra("AREANAME", areaName);
//            //get filter list from equipment filter
//            Log.d("valdoc", "TestCreateActivity :equipment id name equipment1:=" + equipment.getEquipmentName());
//            mEquipmentGrillArrayList = mValdocDatabaseHandler.getGrillFromEquipmentGrill(equipment.getEquipmentId());
//            intent.putExtra("GRILLLIST", mEquipmentGrillArrayList);
//            Log.d("valdoc", "TestCreateActivity :equipment1:=" + mEquipmentGrillArrayList.size());
//            ApplicableTestEquipment applicableTestEquipment = createApplicableTestEquipmentList(equipment.getEquipmentId());
//            intent.putExtra("ApplicableTestEquipment", applicableTestEquipment);
            equipmentTestSelection(intent);
        } else if (testBasedOn.equalsIgnoreCase("AHU")) {
//            int ahuIndex = ahuSpinner.getSelectedItemPosition();
//            Ahu ahu = null;
//            if (ahuIndex > 0) {
//                ahu = mAhuArrayList.get(ahuIndex - 1);
//            }
//            intent.putExtra("Ahu", ahu);
//            mAhuFilterArrayList = mValdocDatabaseHandler.getFilterFromAhuFilter(ahu.getAhuId());
//            intent.putExtra("FILTERLIST", mAhuFilterArrayList);
//            Log.d("valdoc", "TestCreateActivity :equipment1:=" + mAhuFilterArrayList.size());
//
//            ApplicableTestAhu applicableTestAhu = createApplicableTestAhuList(ahu.getAhuId());
//            intent.putExtra("ApplicableTestAhu", applicableTestAhu);
//            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(ahu.getAhuId());
//            intent.putExtra("RoomDetails", roomDetails);
//            Log.d("valdoc", "TestCreateActivity roomDetails[2]=" + roomDetails[2]);
//            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
//            Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
//            intent.putExtra("AREANAME", areaName);
            ahuTestSelection(intent);
        }

        //get area based on room area id


        Log.d("valdoc", "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        startActivity(intent);
    }

    private void equipmentTestSelection(Intent intent) {

        String[] roomDetails = null;
        //get room name,roomNo,and area id
        Log.d("valdoc", "TestCreateActivity :equipment:=" + roomSpinner.getSelectedItemPosition());
        Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
        roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
        intent.putExtra("RoomDetails", roomDetails);
        intent.putExtra("Equipment", equipment);
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        //get filter list from equipment filter
        Log.d("valdoc", "TestCreateActivity :equipment id name equipment1:=" + equipment.getEquipmentName());
        mEquipmentGrillArrayList = mValdocDatabaseHandler.getGrillFromEquipmentGrill(equipment.getEquipmentId());
        intent.putExtra("GRILLLIST", mEquipmentGrillArrayList);
        Log.d("valdoc", "TestCreateActivity :equipment1:=" + mEquipmentGrillArrayList.size());
        ApplicableTestEquipment applicableTestEquipment = createApplicableTestEquipmentList(equipment.getEquipmentId());
        intent.putExtra("ApplicableTestEquipment", applicableTestEquipment);

    }

    private void ahuTestSelection(Intent intent) {
        {
            String[] roomDetails = null;
            int ahuIndex = ahuSpinner.getSelectedItemPosition();
            Ahu ahu = null;
            if (ahuIndex > 0) {
                ahu = mAhuArrayList.get(ahuIndex - 1);
            }
            intent.putExtra("Ahu", ahu);
            mAhuFilterArrayList = mValdocDatabaseHandler.getFilterFromAhuFilter(ahu.getAhuId());
            intent.putExtra("FILTERLIST", mAhuFilterArrayList);
            Log.d("valdoc", "TestCreateActivity :equipment1:=" + mAhuFilterArrayList.size());

            ApplicableTestAhu applicableTestAhu = createApplicableTestAhuList(ahu.getAhuId());
            intent.putExtra("ApplicableTestAhu", applicableTestAhu);
            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(ahu.getAhuId());
            intent.putExtra("RoomDetails", roomDetails);
            Log.d("valdoc", "TestCreateActivity roomDetails[2]=" + roomDetails[2]);
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            intent.putExtra("TestItem", areaName);
        }
    }

    //spiner data validation
    private boolean validationSpiner() {
        boolean flag = false;
        int pos = equipmentAhuOrRoomSpinner.getSelectedItemPosition();
        if (pos > 0) {
//        if (equipmentAhuOrRoomSpinner.getSelectedItemPosition() > 0) {
            if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("AHU")) {
                if (equipmentAhuOrRoomSpinner.getSelectedItemPosition() > 0 && applicableTestSpinner.getSelectedItemPosition() > 0 && instrumentSpiner.getSelectedItemPosition() > 0
                        && ahuSpinner.getSelectedItemPosition() > 0 && roomSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0)
                    flag = true;
            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("EQUIPMENT")) {
                if (equipmentAhuOrRoomSpinner.getSelectedItemPosition() > 0 && applicableTestSpinner.getSelectedItemPosition() > 0 && instrumentSpiner.getSelectedItemPosition() > 0
                        && ahuSpinner.getSelectedItemPosition() > 0 && roomSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0)
                    flag = true;
            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("ROOM")) {
                if (equipmentAhuOrRoomSpinner.getSelectedItemPosition() > 0 && applicableTestSpinner.getSelectedItemPosition() > 0 && instrumentSpiner.getSelectedItemPosition() > 0
                        && ahuSpinner.getSelectedItemPosition() > 0 && roomSpinner.getSelectedItemPosition() > 0 && roomNoSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0)
                    flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    public void spinerInitialization() {
        user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText("" + sharedpreferences.getString("USERNAME", ""));

        equipmentAhuOrRoomSpinner = (Spinner) findViewById(R.id.equipment_ahu_room_spinner);
        equipmentAhuOrRoomSpinner.setOnTouchListener(this);

        applicableTestSpinner = (Spinner) findViewById(R.id.select_test_spinner);
        applicableTestSpinner.setOnTouchListener(this);

        instrumentSpiner = (Spinner) findViewById(R.id.instrumentspiner);
        instrumentSpiner.setOnTouchListener(this);

        ahuSpinner = (Spinner) findViewById(R.id.ahuspinner);
        ahuSpinner.setOnTouchListener(this);

        roomSpinner = (Spinner) findViewById(R.id.roomspinner);
        roomSpinner.setOnTouchListener(this);

        roomNoSpinner = (Spinner) findViewById(R.id.room_no_spinner);
        roomNoSpinner.setOnTouchListener(this);

        witnessFirst = (EditText) findViewById(R.id.witnessfirst);
        witnessSecond = (EditText) findViewById(R.id.witnesssecond);
        witnessThird = (EditText) findViewById(R.id.witnessthird);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        witnessFirst.clearFocus();
        witnessSecond.clearFocus();
        witnessThird.clearFocus();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    private void getInstrumentList(String userType, String testCode) {
        if (null != instrumentList && instrumentList.size() > 0)
            instrumentList.clear();
        if (null != partnerInstrumentArrayList && partnerInstrumentArrayList.size() > 0)
            partnerInstrumentArrayList.clear();
        if (null != clientInstrumentArrayList && clientInstrumentArrayList.size() > 0)
            clientInstrumentArrayList.clear();

        instrumentList = new ArrayList<String>();
        instrumentList.add("Select Instrument");
        Log.d("valdoc", "TestCreateActivity : client usertype" + userType + " testCode=" + testCode);
        if (null != userType && userType.equalsIgnoreCase("CLIENT")) {
            clientInstrumentArrayList = mValdocDatabaseHandler.getClientInstrumentInfo(testCode);

            for (ClientInstrument clientInstrument : clientInstrumentArrayList) {
                instrumentList.add(clientInstrument.getSerialNo());
                Log.d("valdoc", "TestCreateActivity : client instrument list" + clientInstrument.getcInstrumentName());
//                Log.d("valdoc", "TestCreateActivity : client instrument list" + clientInstrument.getSamplingFlowRate());
            }

        } else {
            Log.d("valdoc", "TestCreateActivity :vendor" + userPartnerId);
//            loginUserPartnerId = mValdocDatabaseHandler.getPartnerIdFromPartnerUser(appUserId);
//            Log.d("valdoc", "TestCreateActivity :vendor loginUserPartnerId=" + loginUserPartnerId);
            partnerInstrumentArrayList = mValdocDatabaseHandler.getPartnerInstrumentInfo(userPartnerId, testCode);
            Log.d("valdoc", "TestCreateActivity :vendor partnerInstrumentArrayList" + partnerInstrumentArrayList.size());
            for (PartnerInstrument partnerInstrument : partnerInstrumentArrayList) {
                instrumentList.add(partnerInstrument.getSerialNo());
                Log.d("valdoc", "TestCreateActivity :vendor" + partnerInstrument.getpInstrumentName());
            }
        }

        instrumentAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, instrumentList);
        instrumentSpiner.setAdapter(instrumentAdapter);
        instrumentAdapter.notifyDataSetChanged();
    }


    public void listItemCreation() {
        equipmentAhuOrRoomList = new ArrayList<String>();
        equipmentAhuOrRoomList.add("Select  Test at Room, AHU or Equipment");
        equipmentAhuOrRoomList.add("Test at Rooms");
        equipmentAhuOrRoomList.add("Test at AHU");
        equipmentAhuOrRoomList.add("Test at Equipment");

        instrumentList = new ArrayList<String>();
        instrumentList.add("Select Instrument");

        //create test list
        applicableTestList = new ArrayList<String>();
//        applicableTestList = Arrays.asList(roomsTestValues);
        String str = new String("Select Required Test");
        applicableTestList.add(str);
        createAhuList();
//        createEquipmentList();
//        roomTestList = new ArrayList<String>();
//        roomTestList.add("Slect room");
//
//        applicableTestRoomList = new ArrayList<String>();
//        applicableTestRoomList.add("Select Test Type");
//        applicableTestEquipmentList = new ArrayList<String>();
    }

    public void roomListItemCreation() {
        createAhuList();
//        ahuList = new ArrayList<String>();
//        ahuList.add("Select AHU for Room");
//        ahuList.add("Select AHU for Room");
//        ahuList.add("Select AHU for Room");
        roomList = new ArrayList<String>();
        roomList.add("Select Room");
        roomNoList = new ArrayList<String>();
        roomNoList.add("Select Room No");

    }

    public void ahuListItemCreation() {
        createAhuList();
        ahuList = new ArrayList<String>();
        ahuList.add("Select AHU");
        testItemList = new ArrayList<String>();
        testItemList.add("Select Test Item");
        testItemList.add("Fresh Air Filter");
        testItemList.add("Bleed HEPA Filter");
        testItemList.add("Final Filter");
    }

    public void equipmentListItemCreation() {
//        equipmentList = new ArrayList<String>();
//        equipmentList.add("Select Equipment");
        createEquipmentNameList();
        equipmentNoList = new ArrayList<String>();
        equipmentNoList.add("Select Equipment No");
    }

    public void spinnerCreation() {
        equipmentAhuOrRoomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentAhuOrRoomList);
        applicableTestAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, applicableTestList);
        instrumentAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, instrumentList);

//        applicableTestequipmentAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, applicableTestEquipmentList);

        equipmentAhuOrRoomSpinner.setAdapter(equipmentAhuOrRoomAdapter);
        applicableTestSpinner.setAdapter(applicableTestAdapter);
        instrumentSpiner.setAdapter(instrumentAdapter);
//        equipmentSpinner.setAdapter(equipmentadApter);

        equipmentAhuOrRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                TextView selectedText = (TextView) parent.getChildAt(0);
                String type = "";

                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
                witnessThird.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(witnessThird.getWindowToken(), 0);

//                ahuSpinner.setVisibility(View.GONE);
//                equipmentSpinner.setVisibility(View.GONE);
                equipmentOrAhuSpinnerPos = equipmentAhuOrRoomSpinner.getSelectedItemPosition();
                if (equipmentOrAhuSpinnerPos > 0)
                    type = equipmentAhuOrRoomTestCodeList[pos - 1];
                // TODO Auto-generated method stub
                if (type.toString().equals("AHU")) {
                    roomNoSpinner.setVisibility(View.GONE);
                    ahuSpinner.setVisibility(View.VISIBLE);
                    roomSpinner.setVisibility(View.VISIBLE);
                    ahuListItemCreation();
                    ahuSpinnerCreation();
                    applicableTestList = Arrays.asList(ahuTestValues);
                    applicableTestAdapter = new ArrayAdapter<String>(TestCreateActivity.this, R.layout.spiner_text, applicableTestList);
                    applicableTestSpinner.setAdapter(applicableTestAdapter);
                    applicableTestAdapter.notifyDataSetChanged();

                    createAhuList();
//                    ahuSpinner.setVisibility(View.VISIBLE);
//                    equipmentSpinner.setVisibility(View.GONE);
//                    roomSpinner.setVisibility(View.VISIBLE);
////                    roomTestList.add("Select Room");
//                    roomSpinner.setSelection(0);
//                    ahuSpinner.setSelection(0);
//                    testSpinner.setSelection(0);
                    spinerAhuOrEquipment = "AHU";
                    Log.d("TestCreateActivity", "spiner :");
                } else if (type.toString().equals("EQUIPMENT")) {
                    roomNoSpinner.setVisibility(View.GONE);
                    ahuSpinner.setVisibility(View.VISIBLE);
                    roomSpinner.setVisibility(View.VISIBLE);
                    equipmentListItemCreation();
                    equipmentSpinnerCreation();
                    applicableTestList = Arrays.asList(equipmentTestValues);
                    applicableTestAdapter = new ArrayAdapter<String>(TestCreateActivity.this, R.layout.spiner_text, applicableTestList);
                    applicableTestSpinner.setAdapter(applicableTestAdapter);
                    applicableTestAdapter.notifyDataSetChanged();
                    spinerAhuOrEquipment = "EQUIPMENT";
                } else if (type.toString().equals("ROOM")) {
                    ahuSpinner.setVisibility(View.VISIBLE);
                    roomSpinner.setVisibility(View.VISIBLE);
                    roomNoSpinner.setVisibility(View.VISIBLE);
                    applicableTestList = Arrays.asList(roomsTestValues);
                    applicableTestAdapter = new ArrayAdapter<String>(TestCreateActivity.this, R.layout.spiner_text, applicableTestList);
                    applicableTestSpinner.setAdapter(applicableTestAdapter);
                    applicableTestAdapter.notifyDataSetChanged();
                    roomListItemCreation();
                    roomSpinnerCreation();
                    spinerAhuOrEquipment = "ROOM";
                } else {
                    spinerAhuOrEquipment = "";
                    roomNoSpinner.setVisibility(View.GONE);
                    ahuSpinner.setVisibility(View.GONE);
                    roomSpinner.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        applicableTestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
//                Toast.makeText(TestCreateActivity.this, "spinner creation", Toast.LENGTH_SHORT).show();
                if (spinerAhuOrEquipment.equals("AHU")) {
                    Log.d("AHU", "pos=" + applicableTestSpinner.getSelectedItemPosition() + " Testcode=" + searchAhuTestCode[applicableTestSpinner.getSelectedItemPosition()]);
                    if (pos > 0)
                        getInstrumentList(loginUserType, searchAhuTestCode[pos]);
                } else if (spinerAhuOrEquipment.equals("EQUIPMENT")) {
                    Log.d("EQUIPMENT", "pos=" + applicableTestSpinner.getSelectedItemPosition() + " Testcode=" + searchAhuTestCode[applicableTestSpinner.getSelectedItemPosition()]);
                    if (pos > 0)
                        getInstrumentList(loginUserType, searchEquipmentTestCode[pos]);
                } else if (spinerAhuOrEquipment.equals("ROOM")) {
                    Log.d("ROOM", "pos=" + pos + " Testcode=" + searchRoomsTestCode[applicableTestSpinner.getSelectedItemPosition()]);
                    if (pos > 0)
                        getInstrumentList(loginUserType, searchRoomsTestCode[pos]);
                }
                // TODO Auto-generated method stub
//                testSpinnerPos = testSpinner.getSelectedItemPosition();
//                spinerTestType = testSpinner.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), TestList.get(arg2),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        instrumentSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
                if (instrumentSpiner.getSelectedItemPosition() > 1)
                    spinerInstrument = instrumentList.get(instrumentSpiner.getSelectedItemPosition() - 1);
                instrumentSpinerPos = instrumentSpiner.getSelectedItemPosition();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void roomSpinnerCreation() {

        ahuAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, ahuList);
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomList);
        roomNoAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomNoList);

        ahuSpinner.setAdapter(ahuAdapter);
        roomSpinner.setAdapter(roomAdapter);
        roomNoSpinner.setAdapter(roomNoAdapter);

        ahuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
                if (pos > 0) {
                    mAhuId = mAhuArrayList.get(pos - 1).getAhuId();
                    createRoomList(mAhuId);
                }
//                Toast.makeText(TestCreateActivity.this, "room ahu pos=" + pos, Toast.LENGTH_SHORT).show();
//                roomSpinner.setVisibility(View.VISIBLE);
//                roomSpinner.setSelection(0);
//                applicableTestSpinner.setSelection(0);

                ahuSpinnerPos = ahuSpinner.getSelectedItemPosition();
                Log.d("TestCreateActivity", "ahuspiner :position= " + ahuSpinner.getSelectedItemPosition());

//                if (ahuSpinner.getSelectedItemPosition() > 0) {
//                    Ahu ahu = mAhuArrayList.get(ahuSpinner.getSelectedItemPosition() - 1);
//                    Log.d("TestCreateActivity", "ahuspiner : ahu.getAhuId()=" + ahu.getAhuId());
//                    createRoomList(ahu.getAhuId());
//                    spinerAhu = ahu.getAhuId();
////                    roomSpinner.setVisibility(View.VISIBLE);
//                }
//                Toast.makeText(getBaseContext(), ahuTestList.get(arg2),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
                if (pos > 0)
                    createRoomNoList(mRoomArrayList.get(pos - 1).getRoomName(), mAhuId);
//                Toast.makeText(TestCreateActivity.this, "room room pos=" + pos, Toast.LENGTH_SHORT).show();
//                applicableTestSpinner.setSelection(0);
//                roomSpinnerPos = roomSpinner.getSelectedItemPosition();
//                Log.d("TestCreateActivity", "equipment :index= " + roomSpinner.getSelectedItemPosition());
//                if (roomSpinner.getSelectedItemPosition() > 0) {
//                    Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
//                    createApplicableTestRoomList(room.getRoomId());
//                    Log.d("TestCreateActivity", "room.getRoomId()= " + room.getRoomId());
//                    spinerRoom = room.getRoomName();
//                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        roomNoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
                if (pos > 0) {
                    createApplicableTestRoomList(mRoomNoArrayList.get(pos - 1).getRoomId());
                }
//                Toast.makeText(TestCreateActivity.this, "room room no pos=" + pos, Toast.LENGTH_SHORT).show();
//                equipmentSpinnerPos = equipmentSpinner.getSelectedItemPosition();
//                Log.d("TestCreateActivity", "equipment :index= " + equipmentSpinner.getSelectedItemPosition() + "mEquipmentArrayList size=" + mEquipmentArrayList.size());
//                if (equipmentSpinner.getSelectedItemPosition() > 0) {
//                    Equipment equipment = mEquipmentArrayList.get(equipmentSpinner.getSelectedItemPosition() - 1);
//                    createApplicableTestEquipmentList(equipment.getEquipmentId());
//                    spinerEquipment = equipment.getEquipmentId();
//                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void ahuSpinnerCreation() {
//        ahuAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, ahuList);
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, testItemList);

//        ahuSpinner.setAdapter(ahuAdapter);
        roomSpinner.setAdapter(roomAdapter);

        ahuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
//                Toast.makeText(TestCreateActivity.this, "ahu ahu pos=" + pos, Toast.LENGTH_SHORT).show();
//                    roomSpinner.setVisibility(View.VISIBLE);
//                    roomSpinner.setSelection(0);
//                    applicableTestSpinner.setSelection(0);
//
//                    ahuSpinnerPos = ahuSpinner.getSelectedItemPosition();
//                    Log.d("TestCreateActivity", "ahuspiner :position= " + ahuSpinner.getSelectedItemPosition());
//
//                    if (ahuSpinner.getSelectedItemPosition() > 0) {
//                        Ahu ahu = mAhuArrayList.get(ahuSpinner.getSelectedItemPosition() - 1);
//                        Log.d("TestCreateActivity", "ahuspiner : ahu.getAhuId()=" + ahu.getAhuId());
//                        createRoomList(ahu.getAhuId());
//                        spinerAhu = ahu.getAhuId();
////                    roomSpinner.setVisibility(View.VISIBLE);
//                    }
//                Toast.makeText(getBaseContext(), ahuTestList.get(arg2),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
//                Toast.makeText(TestCreateActivity.this, "ahu room pos=" + pos, Toast.LENGTH_SHORT).show();
//                    applicableTestSpinner.setSelection(0);
//                    roomSpinnerPos = roomSpinner.getSelectedItemPosition();
//                    Log.d("TestCreateActivity", "equipment :index= " + roomSpinner.getSelectedItemPosition());
//                    if (roomSpinner.getSelectedItemPosition() > 0) {
//                        Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
//                        createApplicableTestRoomList(room.getRoomId());
//                        Log.d("TestCreateActivity", "room.getRoomId()= " + room.getRoomId());
//                        spinerRoom = room.getRoomName();
//                    }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void equipmentSpinnerCreation() {
//        ahuAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentList);
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentNoList);
//        ahuSpinner.setAdapter(ahuAdapter);
        roomSpinner.setAdapter(roomAdapter);
//Equipment name spiner
        ahuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
                if (pos > 0)
                    createEquipmentNoList(ahuSpinner.getSelectedItem().toString());
//                Toast.makeText(TestCreateActivity.this, "equipment equipment pos=" + pos, Toast.LENGTH_SHORT).show();
//                    roomSpinner.setVisibility(View.VISIBLE);
//                    roomSpinner.setSelection(0);
//                    applicableTestSpinner.setSelection(0);
//
//                    ahuSpinnerPos = ahuSpinner.getSelectedItemPosition();
//                    Log.d("TestCreateActivity", "ahuspiner :position= " + ahuSpinner.getSelectedItemPosition());
//
//                    if (ahuSpinner.getSelectedItemPosition() > 0) {
//                        Ahu ahu = mAhuArrayList.get(ahuSpinner.getSelectedItemPosition() - 1);
//                        Log.d("TestCreateActivity", "ahuspiner : ahu.getAhuId()=" + ahu.getAhuId());
//                        createRoomList(ahu.getAhuId());
//                        spinerAhu = ahu.getAhuId();
////                    roomSpinner.setVisibility(View.VISIBLE);
//                    }
//                Toast.makeText(getBaseContext(), ahuTestList.get(arg2),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
//                Toast.makeText(TestCreateActivity.this, "equipment equipment no pos=" + pos, Toast.LENGTH_SHORT).show();
//                    applicableTestSpinner.setSelection(0);
//                    roomSpinnerPos = roomSpinner.getSelectedItemPosition();
//                    Log.d("TestCreateActivity", "equipment :index= " + roomSpinner.getSelectedItemPosition());
//                    if (roomSpinner.getSelectedItemPosition() > 0) {
//                        Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
//                        createApplicableTestRoomList(room.getRoomId());
//                        Log.d("TestCreateActivity", "room.getRoomId()= " + room.getRoomId());
//                        spinerRoom = room.getRoomName();
//                    }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    //create ahu list
    private void createAhuList() {
        ahuList = new ArrayList<String>();
        ahuList.add("Select ahu");
        mAhuArrayList = mValdocDatabaseHandler.getAhuInfo();
        Log.d("valdoc", "TestCreateActivity : ahu size" + mAhuArrayList.size());
        for (Ahu ahu : mAhuArrayList) {
            ahuList.add(ahu.getAhuNo() + "");
            Log.d("valdoc", "TestCreateActivity : ahu" + ahu.getAhuType());
        }
        ahuAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, ahuList);
        ahuSpinner.setAdapter(ahuAdapter);
        ahuAdapter.notifyDataSetChanged();
    }


    private void createEquipmentNameList() {
        equipmentList = new ArrayList<String>();
        if (null != equipmentList && equipmentList.size() > 0) {
            equipmentList.clear();
        }
        equipmentList.add("Select Equipment");
        equipmentList = mValdocDatabaseHandler.getEquipmentName();
        equipmentList.add(0, "Select Equipment");
//        for (Equipment equipment : mEquipmentArrayList) {
//            equipmentList.add(equipment.getEquipmentName());
//            Log.d("valdoc", "TestCreateActivity : ahu" + equipment.getEquipmentName());
//        }

        equipmentadApter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentList);
        ahuSpinner.setAdapter(equipmentadApter);
        equipmentadApter.notifyDataSetChanged();
    }

    private void createEquipmentNoList(String equipmentName) {
        equipmentNoList = new ArrayList<String>();
        equipmentNoList.add("Select Equipment No");
        Log.d("TestCreateActivity", "ahuspiner : create room list=");
        if (null != equipmentNoList && equipmentNoList.size() > 0)
            equipmentNoList.clear();
        mEquipmentArrayList = mValdocDatabaseHandler.getEquipmentNoInfoByEquipmentName(equipmentName);
        equipmentNoList.add(0, "Select Equipment No");
        for (Equipment equipment : mEquipmentArrayList) {
            equipmentNoList.add(equipment.getEquipmentNo());
            Log.d("valdoc", "TestCreateActivity : ahu" + equipment.getEquipmentName());
        }
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentNoList);
        roomSpinner.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
    }

    private void createRoomList(int ahuNo) {
        roomSpinner.setVisibility(View.VISIBLE);
        Log.d("TestCreateActivity", "ahuspiner : create room list=");
        if (null != mRoomArrayList && mRoomArrayList.size() > 0)
            mRoomArrayList.clear();
        mRoomArrayList = mValdocDatabaseHandler.getRoomInfoByAhu(ahuNo);
        if (null != roomList && roomList.size() > 1) {
            Log.d("valdoc", "TestCreateActivity : room clear");
            roomList.clear();
            roomList.add("Select Room");
        }
        roomSpinner.setSelection(0);
        for (Room room : mRoomArrayList) {
            roomList.add(room.getRoomName());
            Log.d("valdoc", "TestCreateActivity : ahu" + room.getRoomName());
        }
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomList);
//        roomSpinner.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
    }

    private void createRoomNoList(String roomName, int ahuNo) {
        roomNoSpinner.setVisibility(View.VISIBLE);
        Log.d("TestCreateActivity", "ahuspiner : create room list=");
        if (null != mRoomNoArrayList && mRoomNoArrayList.size() > 0)
            mRoomNoArrayList.clear();
        mRoomNoArrayList = mValdocDatabaseHandler.getRoomNoInfoByroomNameAndAhu(roomName, ahuNo);
        if (null != roomNoList && roomNoList.size() > 1) {
            Log.d("valdoc", "TestCreateActivity : room clear");
            roomNoList.clear();
            roomNoList.add("Select Room No");
        }
        roomNoSpinner.setSelection(0);
        for (Room room : mRoomNoArrayList) {
            roomNoList.add(room.getRoomNo());
            Log.d("valdoc", "TestCreateActivity : ahu" + room.getRoomNo());
        }
        roomNoAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomNoList);
//        roomNoSpinner.setAdapter(roomNoAdapter);
        roomNoAdapter.notifyDataSetChanged();
    }

    private void createApplicableTestRoomList(int roomId) {
        Log.d("TestCreateActivity", "ahuspiner : create room list=");
        mApplicableTestRoom = mValdocDatabaseHandler.getApplicableTestRoomInfo(roomId);
    }

    private ApplicableTestEquipment createApplicableTestEquipmentList(int equipmentId) {
        mApplicableTestEquipment = mValdocDatabaseHandler.getApplicableTestEquipmentInfo(equipmentId);
        return mApplicableTestEquipment;
    }

    private ApplicableTestAhu createApplicableTestAhuList(int ahuId) {
        mApplicableTestAhu = mValdocDatabaseHandler.getApplicableTestAhuInfo(ahuId);
//        applicableTestEquipmentList.add("Select Test Type");
        return mApplicableTestAhu;
    }

    @Override
    public void onBackPressed() {
        Intent afterLoginIntent = new Intent(TestCreateActivity.this, AfterLoginActivity.class);
        startActivity(afterLoginIntent);
        TestCreateActivity.this.finish();
    }
}
