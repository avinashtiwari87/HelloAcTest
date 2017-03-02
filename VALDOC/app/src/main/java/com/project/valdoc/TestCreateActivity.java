package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import com.project.valdoc.intity.Grill;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.utility.Utilityies;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class TestCreateActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "TestCreateActivity";
    private Spinner equipmentAhuOrRoomSpinner, applicableTestSpinner, instrumentSpiner, ahuSpinner, roomSpinner, roomNoSpinner, occupancySpiner;
    private List<String> equipmentAhuOrRoomList, applicableTestList, instrumentList, ahuList, roomNoList, roomList, testItemList, equipmentList, equipmentNoList,
            occupancyList, applicableTestRoomList, applicableTestEquipmentList, applicableTestAhuList;
    private ArrayAdapter<String> equipmentAhuOrRoomAdapter, applicableTestAdapter, instrumentAdapter, equipmentadApter, ahuAdapter, roomAdapter, roomNoAdapter, occupancyAdapter;
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

    private ArrayList<ApplicableTestRoom> mApplicableTestRoomList = new ArrayList<ApplicableTestRoom>();
    private ArrayList<ApplicableTestEquipment> mApplicableTestEquipmentList = new ArrayList<ApplicableTestEquipment>();
    private ArrayList<ApplicableTestAhu> mApplicableTestAhuList = new ArrayList<ApplicableTestAhu>();

    private String[] equipmentAhuOrRoomTestCodeList = new String[]{"ROOM", "AHU", "EQUIPMENT"};
    private String[] roomsTestValues = new String[]{"Select Required Test", "Airchanges/hr Test (ACPH_AV)",
            "Airchanges/hr Test (ACPH_H)", "Filter Integrity Test (FIT)",
            "Particle Count Test (PCT)", "Recovery Test (RCT)"};

    private String[] ahuTestValues = new String[]{"Select Required Test", "Airflow Test (AF_AHU)",
            "Filter Integrity Test (FIT_AHU)"};

    private String[] equipmentTestValues = new String[]{"Select Required Test", "Airvelocity Test (ERD_AV)",
            "Filter Integrity Test (ERD_FIT)", "Particle Count Test (ERD_PCT)",
            "Recovery Test (ERD_RCT)"};

    private String[] roomsTestCode = new String[]{"", "ACPH_AV", "ACPH_H", "FIT", "PCT", "RCT"};
    private String[] ahuTestCode = new String[]{"", "ARD_AF_AHU", "ARD_FIT_AHU"};
    private String[] equipmentTestCode = new String[]{"", "ERD_AV", "ERD_FIT", "ERD_PCT", "ERD_RCT"};

    //testcode to search from db
    private String[] searchRoomsTestCode = new String[]{"", "ACPH_AV", "ACPH_H", "FIT", "PCT", "RCT"};
    private String[] searchAhuTestCode = new String[]{"", "ACPH_AV", "FIT"};
    private String[] searchEquipmentTestCode = new String[]{"", "AV", "FIT", "PCT", "RCT"};

    private ImageView submit;
    private TextView test_date;
    private EditText witnessFirst;
    private EditText witnessSecond;
    private EditText witnessThird;
    ImageView hideWitness_iv, showWitness_iv;
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
    private String mTodaysDate;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    //Storing spinner position to show selected
    // after Certificate submit
    int instrumentSpinerPos = 0, equipmentOrAhuSpinnerPos = 0,
            equipmentSpinnerPos = 0, ahuSpinnerPos = 0,
            roomSpinnerPos = 0, testSpinnerPos = 0;
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
        datePicker();
        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(TestCreateActivity.this, mActionBar, userName);
    }

    private String getPartnerName() {
        String partnerName = "";
        int partnerId = sharedpreferences.getInt("PARTNERID", 0);
        Log.d(TAG, "partnerId=" + partnerId);
        if (partnerId != 0)
            partnerName = mValdocDatabaseHandler.getPartnerNameInfo(partnerId);
        Log.d(TAG, "partnerName=" + partnerName);
        return partnerName;
    }

    private void initButton() {
        int witnessCount = 0;
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

        hideWitness_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (witnessSecond.isShown()) {
                    findViewById(R.id.witnesslayout2).setVisibility(View.GONE);
                } else if (witnessThird.isShown()) {
                    findViewById(R.id.witnesslayout3).setVisibility(View.GONE);
                } else {
                    Toast.makeText(TestCreateActivity.this, "One witness is mandatory.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showWitness_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (witnessSecond.isShown()) {
                    findViewById(R.id.witnesslayout3).setVisibility(View.VISIBLE);
                } else if (!witnessSecond.isShown()) {
                    findViewById(R.id.witnesslayout2).setVisibility(View.VISIBLE);
                } else if (witnessThird.isShown()) {
                    Toast.makeText(TestCreateActivity.this, "Not more than 3 witness allowed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getApplicationTestList() {
        int pos = equipmentAhuOrRoomSpinner.getSelectedItemPosition();
        if (pos > 0) {
            if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("AHU")) {
                int ahuIndex = ahuSpinner.getSelectedItemPosition();
                Ahu ahu = null;
                if (ahuIndex > 0) {
                    ahu = mAhuArrayList.get(ahuIndex - 1);
                }
                int testPosition = applicableTestSpinner.getSelectedItemPosition();
                if (ACPHAV.equals(searchAhuTestCode[testPosition])) {
                    createApplicableTestAhuList(ahu.getAhuId(), searchAhuTestCode[testPosition], roomSpinner.getSelectedItem().toString());
                } else if (FIT.equals(searchAhuTestCode[testPosition])) {
                    createApplicableTestAhuList(ahu.getAhuId(), searchAhuTestCode[testPosition], roomSpinner.getSelectedItem().toString());
                }
                createOccupancySpinner("AHU");

            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("ROOM")) {
                Room room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
                int testPosition = applicableTestSpinner.getSelectedItemPosition();
                if (ACPHAV.equals(searchRoomsTestCode[testPosition])) {
                    createApplicableTestRoomList(room.getRoomId(), searchRoomsTestCode[testPosition]);
                } else if (ACPHH.equals(searchRoomsTestCode[testPosition])) {
                    createApplicableTestRoomList(room.getRoomId(), searchRoomsTestCode[testPosition]);
                } else if (FIT.equals(searchRoomsTestCode[testPosition])) {
                    createApplicableTestRoomList(room.getRoomId(), searchRoomsTestCode[testPosition]);
                } else if (PCT.equals(searchRoomsTestCode[testPosition])) {
                    createApplicableTestRoomList(room.getRoomId(), searchRoomsTestCode[testPosition]);
                } else if (RCT.equals(searchRoomsTestCode[testPosition])) {
                    createApplicableTestRoomList(room.getRoomId(), searchRoomsTestCode[testPosition]);
                }
                createOccupancySpinner("ROOM");

            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("EQUIPMENT")) {
                Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
                int testPosition = applicableTestSpinner.getSelectedItemPosition();
                if (AV.equals(searchEquipmentTestCode[testPosition])) {
                    createApplicableTestEquipmentList(equipment.getEquipmentId(), searchEquipmentTestCode[testPosition]);
                } else if (FIT.equals(searchEquipmentTestCode[testPosition])) {
                    createApplicableTestEquipmentList(equipment.getEquipmentId(), searchEquipmentTestCode[testPosition]);
                } else if (PCT.equals(searchEquipmentTestCode[testPosition])) {
                    createApplicableTestEquipmentList(equipment.getEquipmentId(), searchEquipmentTestCode[testPosition]);
                } else if (RCT.equals(searchEquipmentTestCode[testPosition])) {
                    createApplicableTestEquipmentList(equipment.getEquipmentId(), searchEquipmentTestCode[testPosition]);
                }
                createOccupancySpinner("EQUIPMENT");
            }
        }
    }

    private void createOccupancySpinner(final String testBasedOn) {
        occupancyList = new ArrayList<String>();
        occupancyList.add("Select Occupancy State");
        if (testBasedOn.equalsIgnoreCase("AHU")) {
            if (mApplicableTestAhuList.size() > 1) {
                occupancySpiner.setVisibility(View.VISIBLE);
                for (ApplicableTestAhu applicableTestAhu : mApplicableTestAhuList)
                    occupancyList.add(applicableTestAhu.getOccupencyState().toString());
            } else {
                if (null != mApplicableTestAhuList && mApplicableTestAhuList.size() != 0) {
                    occupancySpiner.setVisibility(View.GONE);
                    mApplicableTestAhu = mApplicableTestAhuList.get(0);
                } else {
                    occupancySpiner.setVisibility(View.VISIBLE);
                }
            }
        } else if (testBasedOn.equalsIgnoreCase("ROOM")) {
            if (mApplicableTestRoomList.size() > 1) {
                occupancySpiner.setVisibility(View.VISIBLE);
                for (ApplicableTestRoom applicableTestRoom : mApplicableTestRoomList)
                    occupancyList.add(applicableTestRoom.getOccupencyState().toString());
            } else {
                if (null != mApplicableTestRoomList && mApplicableTestRoomList.size() != 0) {
                    occupancySpiner.setVisibility(View.GONE);
                    mApplicableTestRoom = mApplicableTestRoomList.get(0);
                } else {
                    occupancySpiner.setVisibility(View.VISIBLE);
                }
            }
        } else if (testBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            if (mApplicableTestEquipmentList.size() > 1) {
                occupancySpiner.setVisibility(View.VISIBLE);
                for (ApplicableTestEquipment applicableTestEquipment : mApplicableTestEquipmentList)
                    occupancyList.add(applicableTestEquipment.getOccupencyState().toString());
            } else {
                if (null != mApplicableTestEquipmentList && mApplicableTestEquipmentList.size() != 0) {
                    occupancySpiner.setVisibility(View.GONE);
                    mApplicableTestEquipment = mApplicableTestEquipmentList.get(0);
                } else {
                    occupancySpiner.setVisibility(View.VISIBLE);
                }
            }
        }

        occupancyAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, occupancyList);
        occupancySpiner.setAdapter(occupancyAdapter);

        occupancySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

                int occupancyPosition = occupancySpiner.getSelectedItemPosition();
                if (occupancyPosition > 0) {
                    if (testBasedOn.equalsIgnoreCase("AHU")) {
                        mApplicableTestAhu = mApplicableTestAhuList.get(pos - 1);
                    } else if (testBasedOn.equalsIgnoreCase("ROOM")) {
                        mApplicableTestRoom = mApplicableTestRoomList.get(pos - 1);
                    } else if (testBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        mApplicableTestEquipment = mApplicableTestEquipmentList.get(pos - 1);
                    }
                }
//                    type = equipmentAhuOrRoomTestCodeList[pos - 1];

//                if (pos > 0)
//                    createRoomNoList(mRoomArrayList.get(pos - 1).getRoomName(), mAhuId);
//

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void createApplicableTestRoomList(int roomId, String testCode) {
        if (null != mApplicableTestRoomList && mApplicableTestRoomList.size() > 0) {
            mApplicableTestRoomList.clear();
        }
        mApplicableTestRoomList = mValdocDatabaseHandler.getApplicableTestRoomInfo(roomId, testCode);
    }

    private void createApplicableTestEquipmentList(int equipmentId, String testcode) {
        if (null != mApplicableTestEquipmentList && mApplicableTestEquipmentList.size() > 0) {
            mApplicableTestEquipmentList.clear();
        }
        mApplicableTestEquipmentList = mValdocDatabaseHandler.getApplicableTestEquipmentInfo(equipmentId, testcode);
    }

    private void createApplicableTestAhuList(int ahuId, String testCode, String testItem) {
        if (null != mApplicableTestAhuList && mApplicableTestAhuList.size() > 0) {
            mApplicableTestAhuList.clear();
        }
        mApplicableTestAhuList = mValdocDatabaseHandler.getApplicableTestAhuInfo(ahuId, testCode, testItem);
//        applicableTestEquipmentList.add("Select Test Type");
    }

    private void redirectOnTestScreen() {

        int pos = equipmentAhuOrRoomSpinner.getSelectedItemPosition();
        if (pos > 0) {
            if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("AHU")) {
//                aleartDialog("Under development");
                performAhuTest("AHU");
            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("EQUIPMENT")) {
                performEquipmentTest("EQUIPMENT");
            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("ROOM")) {
//                aleartDialog("Under development");
                performRoomTest("ROOM");
            }
        }
    }

    private void performEquipmentTest(String testBasedOn) {
        Log.d(TAG, "performEquipmentTest");
        int testPosition = applicableTestSpinner.getSelectedItemPosition();
        if (AV.equals(searchEquipmentTestCode[testPosition])) {
            rdAv5Test(equipmentTestCode[testPosition], testBasedOn);
        } else if (FIT.equals(searchEquipmentTestCode[testPosition])) {
//            aleartDialog("Under development");
            rdFit(equipmentTestCode[testPosition], testBasedOn, FIT);
        } else if (PCT.equals(searchEquipmentTestCode[testPosition])) {
//            aleartDialog("Under development");
            rdPcT(equipmentTestCode[testPosition], testBasedOn);
        } else if (RCT.equals(searchEquipmentTestCode[testPosition])) {
//            aleartDialog("Under development");
            rdRct(equipmentTestCode[testPosition], testBasedOn);
        } else {
            Toast.makeText(TestCreateActivity.this, "Please select the correct test to be performed", Toast.LENGTH_SHORT).show();
        }
    }

    private void performRoomTest(String testBasedOn) {
        Log.d(TAG, "performRoomTest");
        int testPosition = applicableTestSpinner.getSelectedItemPosition();
        if (ACPHAV.equals(searchRoomsTestCode[testPosition])) {
            rdAcphAv(roomsTestCode[testPosition], testBasedOn);
        } else if (ACPHH.equals(searchRoomsTestCode[testPosition])) {
            rdAcphH(roomsTestCode[testPosition], testBasedOn);
        } else if (FIT.equals(searchRoomsTestCode[testPosition])) {
            rdFit(roomsTestCode[testPosition], testBasedOn, FIT);
        } else if (PCT.equals(searchRoomsTestCode[testPosition])) {
            rdPcT(roomsTestCode[testPosition], testBasedOn);
        } else if (RCT.equals(searchRoomsTestCode[testPosition])) {
//            rdRct(roomsTestCode[testPosition], searchRoomsTestCode[testPosition]);
            rdRct(roomsTestCode[testPosition], testBasedOn);
        } else {
            Toast.makeText(TestCreateActivity.this, "Please select the correct test to be performed", Toast.LENGTH_SHORT).show();
        }
    }

    private void performAhuTest(String testBasedOn) {
        int testPosition = applicableTestSpinner.getSelectedItemPosition();
        if (ACPHAV.equals(searchAhuTestCode[testPosition])) {
//            rdAv5Test(ahuTestCode[testPosition],searchAhuTestCode[testPosition]);
            rdAcphAv(ahuTestCode[testPosition], testBasedOn);
        } else if (FIT.equals(searchAhuTestCode[testPosition])) {
//            rdFit(ahuTestCode[te stPosition],searchAhuTestCode[testPosition]);
            rdFit(ahuTestCode[testPosition], testBasedOn, FIT);
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
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        intent.putExtra("PRTNERNAME", mPartnerName);

        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        if (testBasedOn.equalsIgnoreCase("AHU")) {
            String[] roomDetails = null;
            //TO Do testspesification will be shown from room filter spesification
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
            intent.putExtra("testItem", roomSpinner.getSelectedItem().toString());
            int ahuIndex = ahuSpinner.getSelectedItemPosition();
            Ahu ahu = null;
            if (ahuIndex > 0) {
                ahu = mAhuArrayList.get(ahuIndex - 1);
            }
//            intent.putExtra("Ahu", ahu);
            roomDetails = mValdocDatabaseHandler.getRoomByAhu(ahu.getAhuId());
            intent.putExtra("RoomDetails", roomDetails);

            String areaName = getAhuArea(ahu.getAreId());
//                    mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            intent.putExtra("AREANAME", areaName);
            mAhuFilterArrayList = getAhuACPHAVFilterList(ahu, roomSpinner.getSelectedItem().toString());
//            mValdocDatabaseHandler.getFilterFromAhuFilter(ahu.getAhuId());
            intent.putExtra("AhuFilter", mAhuFilterArrayList);
//            ApplicableTestAhu applicableTestAhu = createApplicableTestAhuList(ahu.getAhuId(), ACPHAV);
            intent.putExtra("ApplicableTestAhu", mApplicableTestAhu);
            intent.putExtra("TOLARENCE", getTolarance(ahu,roomSpinner.getSelectedItem().toString()));
        } else if (testBasedOn.equalsIgnoreCase("ROOM")) {
            ArrayList<RoomFilter> roomFilterList = null;
            ArrayList<Grill> grillAndSizeFromGrill = null;
            //get room name,roomNo,and area id
            Log.d(TAG, "TestCreateActivity :equipment:=" + roomNoSpinner.getSelectedItemPosition());
            Room room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
            intent.putExtra("Room", room);
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());

            //get area based on room area id
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
            intent.putExtra("AREANAME", areaName);
            createApplicableTestRoomList(room.getRoomId(), ACPHAV);
//            createApplicableTestRoomList(room.getRoomId(),A);
//            Log.d(TAG, "TestCreateActivity mApplicableTestRoom=" + mApplicableTestRoom.getLocation());
            intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

            grillAndSizeFromGrill = mValdocDatabaseHandler.getRoomAvGrill(room.getRoomId());
            if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                intent.putExtra("GrilFilterType", "Grill");
                intent.putExtra("GRILLLIST", grillAndSizeFromGrill);
            } else {
                roomFilterList = mValdocDatabaseHandler.getFromRoomFilter(room.getRoomId());
                Log.d(TAG, "TestCreateActivity filterArrayList=" + roomFilterList.size());
                intent.putExtra("RoomFilter", roomFilterList);
                intent.putExtra("GrilFilterType", "Filter");
            }
        }
        startActivity(intent);
    }

    private double getTolarance(Ahu ahu, String testItem) {
        double tolarence=0.0;
        if(testItem.equalsIgnoreCase("Fresh Air Filter")){
            tolarence=ahu.getFreshAirflowTolerance();
        }else if(testItem.equalsIgnoreCase("Bleed Filter")){
            tolarence=ahu.getBleedAirflowTolerance();
        }else if(testItem.equalsIgnoreCase("Final Filter")){
            tolarence=ahu.getFinalAirflowTolerance();
        }
        return tolarence;
    }

    private String getAhuArea(String area) {
        Log.d(TAG, "ahu area=" + area);
        String ahuArea = "NA";
        try {
            JSONObject jsonObject = new JSONObject(area);
            ahuArea = jsonObject.getString("areaName");
        } catch (Exception e) {

        }
        return ahuArea;
    }

    public ArrayList<AhuFilter> getAhuACPHAVFilterList(Ahu ahu, String testItem) {
        ArrayList<AhuFilter> ahuFilterList = null;

        if (testItem.equalsIgnoreCase("Fresh Air Filter")) {
//            if(ahu.getFreshFilterType().equalsIgnoreCase("HEPA")){
            ahuFilterList = mValdocDatabaseHandler.getAhuFitFilterFromAhuFilter(ahu.getAhuId(), "Fresh Air Filter");
//                        getFilterFromAhuFilter(ahu.getAhuId());
//            }
        } else if (testItem.equalsIgnoreCase("Bleed Filter")) {
//            if(ahu.getBleedFilterType().equalsIgnoreCase("HEPA")){
            ahuFilterList = mValdocDatabaseHandler.getAhuFitFilterFromAhuFilter(ahu.getAhuId(), "Bleed Air Filter");
//                        getFilterFromAhuFilter(ahu.getAhuId());
//            }
        } else if (testItem.equalsIgnoreCase("Final Filter")) {
//            if(ahu.getFinalFilterType().equalsIgnoreCase("HEPA")){
            ahuFilterList = mValdocDatabaseHandler.getAhuFitFilterFromAhuFilter(ahu.getAhuId(), "Final Air Filter");
//                        getFilterFromAhuFilter(ahu.getAhuId());
//            }
        }

        return ahuFilterList;
    }

    private void rdAcphH(String testCode, String testBasedOn) {
        ArrayList<RoomFilter> roomFilterList = null;
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", ACPHH);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testBasedOn", testBasedOn);
        Log.d(TAG, "TestCreateActivity witness=" + witnessFirst.getText());
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
        Log.d(TAG, "TestCreateActivity :equipment:=" + roomNoSpinner.getSelectedItemPosition());
        Room room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("Room", room);
        intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d(TAG, "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        createApplicableTestRoomList(room.getRoomId(), ACPHH);
        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

        ArrayList<Grill> grillAndSizeFromGrill = mValdocDatabaseHandler.getGrill(room.getRoomId());
        if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
            intent.putExtra("GrilFilterType", "Grill");
            intent.putExtra("GRILLLIST", grillAndSizeFromGrill);
        } else {
            roomFilterList = mValdocDatabaseHandler.getFromRoomFilter(room.getRoomId());
            Log.d("valdoc", "TestCreateActivity filterArrayList=" + roomFilterList.size());
            intent.putExtra("RoomFilter", roomFilterList);
            intent.putExtra("GrilFilterType", "Filter");
        }
        startActivity(intent);
    }

    private void rdFit(String testCode, String testBasedOn, String testType) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", FIT);
        intent.putExtra("testBasedOn", testBasedOn);
        intent.putExtra("testCode", testCode);
        Log.d(TAG, "TestCreateActivity witness=" + witnessFirst.getText());
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
        ArrayList<RoomFilter> roomFilterList = null;
        if (testBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            String[] roomDetails = null;
            //get room name,roomNo,and area id
            Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
            intent.putExtra("RoomDetails", roomDetails);
            intent.putExtra("Equipment", equipment);
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            //get filter list from equipment filter
            mEquipmentFilterArrayList = mValdocDatabaseHandler.getFilterFromEquipmentFilter(equipment.getEquipmentId());
            intent.putExtra("EquipmentFilter", mEquipmentFilterArrayList);
//            ApplicableTestEquipment applicableTestEquipment = createApplicableTestEquipmentList(equipment.getEquipmentId(), testType);
            intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);


        } else if (testBasedOn.equalsIgnoreCase("AHU")) {
            String[] roomDetails = null;
            //TO Do testspesification will be shown from room filter spesification
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
            intent.putExtra("testItem", roomSpinner.getSelectedItem().toString());
            int ahuIndex = ahuSpinner.getSelectedItemPosition();
            Ahu ahu = null;
            if (ahuIndex > 0) {
                ahu = mAhuArrayList.get(ahuIndex - 1);
            }
//            intent.putExtra("Ahu", ahu);
            roomDetails = mValdocDatabaseHandler.getRoomByAhu(ahu.getAhuId());
            intent.putExtra("RoomDetails", roomDetails);
            Log.d(TAG, "TestCreateActivity roomDetails[2]=" + roomDetails[2]);
//            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            String areaName = getAhuArea(ahu.getAreId());
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            mAhuFilterArrayList = getAhuFilterList(ahu, roomSpinner.getSelectedItem().toString());
            intent.putExtra("AhuFilter", mAhuFilterArrayList);
//            ApplicableTestAhu applicableTestAhu = createApplicableTestAhuList(ahu.getAhuId(), testType);
            intent.putExtra("ApplicableTestAhu", mApplicableTestAhu);
            intent.putExtra("TOLARENCE", getTolarance(ahu,roomSpinner.getSelectedItem().toString()));
        } else if (testBasedOn.equalsIgnoreCase("ROOM")) {
            //get room name,roomNo,and area id
            Log.d(TAG, "TestCreateActivity :roomNoSpinner:=" + roomNoSpinner.getSelectedItemPosition());
            int pos = roomNoSpinner.getSelectedItemPosition();
            if (pos > 0) {
                room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
                intent.putExtra("Room", room);
                //take test specification from room filter
                roomFilterList = mValdocDatabaseHandler.getFromRoomFilter(room.getRoomId());
                Log.d(TAG, "TestCreateActivity filterArrayList=" + roomFilterList.size());
                intent.putExtra("RoomFilter", roomFilterList);
            }
            //TO Do testspesification will be shown from room filter spesification
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
            //get area based on room area id
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
//            createApplicableTestRoomList(room.getRoomId(), FIT);
            intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

        }
        // location will be the size off rommfilter list
        startActivity(intent);
    }

    public ArrayList<AhuFilter> getAhuFilterList(Ahu ahu, String testItem) {
        ArrayList<AhuFilter> ahuFilterList = null;

//        if(testItem.equalsIgnoreCase("Fresh Air Filter"))
//        {
//            if(ahu.getFreshFilterType().equalsIgnoreCase("HEPA")){
//                ahuFilterList=mValdocDatabaseHandler.getAhuFitFilterFromAhuFilter(ahu.getAhuId(),"Fresh Air Filter");
////                        getFilterFromAhuFilter(ahu.getAhuId());
//            }
//        }else
        if (testItem.equalsIgnoreCase("Bleed Filter")) {
            if (ahu.getBleedFilterType().equalsIgnoreCase("F5") || ahu.getBleedFilterType().equalsIgnoreCase("F6") || ahu.getBleedFilterType().equalsIgnoreCase("F7")
                    || ahu.getBleedFilterType().equalsIgnoreCase("F8") || ahu.getBleedFilterType().equalsIgnoreCase("F9") || ahu.getBleedFilterType().equalsIgnoreCase("H1")
                    || ahu.getBleedFilterType().equalsIgnoreCase("H11") || ahu.getBleedFilterType().equalsIgnoreCase("H12") || ahu.getBleedFilterType().equalsIgnoreCase("H13")
                    || ahu.getBleedFilterType().equalsIgnoreCase("H14") || ahu.getBleedFilterType().equalsIgnoreCase("U15") || ahu.getBleedFilterType().equalsIgnoreCase("U16")
                    || ahu.getBleedFilterType().equalsIgnoreCase("U17")) {
                ahuFilterList = mValdocDatabaseHandler.getAhuFitFilterFromAhuFilter(ahu.getAhuId(), "Bleed Air Filter");
//                        getFilterFromAhuFilter(ahu.getAhuId());
            }
        } else if (testItem.equalsIgnoreCase("Final Filter")) {
            if (ahu.getBleedFilterType().equalsIgnoreCase("F5") || ahu.getBleedFilterType().equalsIgnoreCase("F6") || ahu.getBleedFilterType().equalsIgnoreCase("F7")
                    || ahu.getBleedFilterType().equalsIgnoreCase("F8") || ahu.getBleedFilterType().equalsIgnoreCase("F9") || ahu.getBleedFilterType().equalsIgnoreCase("H1")
                    || ahu.getBleedFilterType().equalsIgnoreCase("H11") || ahu.getBleedFilterType().equalsIgnoreCase("H12") || ahu.getBleedFilterType().equalsIgnoreCase("H13")
                    || ahu.getBleedFilterType().equalsIgnoreCase("H14") || ahu.getBleedFilterType().equalsIgnoreCase("U15") || ahu.getBleedFilterType().equalsIgnoreCase("U16")
                    || ahu.getBleedFilterType().equalsIgnoreCase("U17")) {
                ahuFilterList = mValdocDatabaseHandler.getAhuFitFilterFromAhuFilter(ahu.getAhuId(), "Final Air Filter");
//                        getFilterFromAhuFilter(ahu.getAhuId());
            }
        }

        return ahuFilterList;
    }

    private void rdPcT(String testCode, String testBasedOn) {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", PCT);
        intent.putExtra("testCode", testCode);
        intent.putExtra("testBasedOn", testBasedOn);
        Log.d(TAG, "TestCreateActivity witness=" + witnessFirst.getText());
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
        if (testBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            String[] roomDetails = null;
            //get room name,roomNo,and area id
            Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
            intent.putExtra("RoomDetails", roomDetails);
            intent.putExtra("Equipment", equipment);
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            //get filter list from equipment filter
//            mEquipmentFilterArrayList = mValdocDatabaseHandler.getFilterFromEquipmentFilter(equipment.getEquipmentId());
//            intent.putExtra("EquipmentFilter", mEquipmentFilterArrayList);
//            ApplicableTestEquipment applicableTestEquipment = createApplicableTestEquipmentList(equipment.getEquipmentId(), PCT);
            intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);


        } else if (testBasedOn.equalsIgnoreCase("ROOM")) {
            //get room name,roomNo,and area id
            Log.d(TAG, "TestCreateActivity :roomNoSpinner:=" + roomNoSpinner.getSelectedItemPosition());
            int pos = roomNoSpinner.getSelectedItemPosition();
            if (pos > 0) {
                room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
                intent.putExtra("Room", room);
                //take test specification from room filter
//                roomFilterList = mValdocDatabaseHandler.getFromRoomFilter(room.getRoomId());
//                Log.d(TAG, "TestCreateActivity filterArrayList=" + roomFilterList.size());
//                intent.putExtra("RoomFilter", roomFilterList);
            }
            //TO Do testspesification will be shown from room filter spesification
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
            //get area based on room area id
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
//            createApplicableTestRoomList(room.getRoomId(), PCT);
            intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

        }

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
        Log.d(TAG, "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        intent.putExtra("PRTNERNAME", mPartnerName);
        Log.d(TAG, "bundle mPartnerName=" + mPartnerName);
        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        Room room = null;
        if (testBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            String[] roomDetails = null;
            //get room name,roomNo,and area id
            Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
            roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
            intent.putExtra("RoomDetails", roomDetails);
            intent.putExtra("Equipment", equipment);
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
            //get filter list from equipment filter
//            mEquipmentFilterArrayList = mValdocDatabaseHandler.getFilterFromEquipmentFilter(equipment.getEquipmentId());
//            intent.putExtra("EquipmentFilter", mEquipmentFilterArrayList);
//            ApplicableTestEquipment applicableTestEquipment = createApplicableTestEquipmentList(equipment.getEquipmentId(), RCT);
            intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);


        } else if (testBasedOn.equalsIgnoreCase("ROOM")) {
            //get room name,roomNo,and area id
            Log.d(TAG, "TestCreateActivity :roomNoSpinner:=" + roomNoSpinner.getSelectedItemPosition());
            int pos = roomNoSpinner.getSelectedItemPosition();
            if (pos > 0) {
                room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
                intent.putExtra("Room", room);
                //take test specification from room filter
//                roomFilterList = mValdocDatabaseHandler.getFromRoomFilter(room.getRoomId());
//                Log.d("valdoc", "TestCreateActivity filterArrayList=" + roomFilterList.size());
//                intent.putExtra("RoomFilter", roomFilterList);
            }
            //TO Do testspesification will be shown from room filter spesification
            intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
            //get area based on room area id
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
//            createApplicableTestRoomList(room.getRoomId(), RCT);
            intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);

        }

//        //get room name,roomNo,and area id
//        Log.d("valdoc", "TestCreateActivity :equipment:=" + roomNoSpinner.getSelectedItemPosition());
//        Room room = mRoomNoArrayList.get(roomNoSpinner.getSelectedItemPosition() - 1);
//        intent.putExtra("Room", room);
//        intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
//        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);
//        //get area based on room area id
//        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
//        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
//        intent.putExtra("AREANAME", areaName);
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
            equipmentTestSelection(intent, AV);
        } else if (testBasedOn.equalsIgnoreCase("AHU")) {
            ahuTestSelection(intent, AV);
        }

        //get area based on room area id
        Log.d(TAG, "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        startActivity(intent);
    }

    private void equipmentTestSelection(Intent intent, String testcode) {

        String[] roomDetails = null;
        //get room name,roomNo,and area id
        Log.d(TAG, "TestCreateActivity :equipment:=" + roomSpinner.getSelectedItemPosition());
        Equipment equipment = mEquipmentArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
        roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
        intent.putExtra("RoomDetails", roomDetails);
        intent.putExtra("Equipment", equipment);
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
        Log.d(TAG, "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        //get filter list from equipment filter
        Log.d(TAG, "TestCreateActivity :equipment id name equipment1:=" + equipment.getEquipmentName());
        mEquipmentGrillArrayList = mValdocDatabaseHandler.getGrillFromEquipmentGrill(equipment.getEquipmentId());
        if (null != mEquipmentGrillArrayList && mEquipmentGrillArrayList.size() > 0) {
            intent.putExtra("GrilFilterType", "Grill");
            intent.putExtra("GRILLLIST", mEquipmentGrillArrayList);
        } else {
            mEquipmentFilterArrayList = mValdocDatabaseHandler.getFilterFromEquipmentFilter(equipment.getEquipmentId());
            intent.putExtra("GrilFilterType", "Filter");
            intent.putExtra("GRILLLIST", mEquipmentFilterArrayList);
        }
//        ApplicableTestEquipment applicableTestEquipment = createApplicableTestEquipmentList(equipment.getEquipmentId(), testcode);
        intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);
//        Log.d("valdoc", "TestCreateActivity :equipment id name getLocation:=" + applicableTestEquipment.getLocation());
    }

    private void ahuTestSelection(Intent intent, String testcode) {
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
            Log.d(TAG, "TestCreateActivity :equipment1:=" + mAhuFilterArrayList.size());

//            ApplicableTestAhu applicableTestAhu = createApplicableTestAhuList(ahu.getAhuId(), testcode);
            intent.putExtra("ApplicableTestAhu", mApplicableTestAhu);
            roomDetails = mValdocDatabaseHandler.getRoomByAhu(ahu.getAhuId());
            intent.putExtra("RoomDetails", roomDetails);
            Log.d(TAG, "TestCreateActivity roomDetails[2]=" + roomDetails[2]);
            String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
            Log.d(TAG, "TestCreateActivity areaName=" + areaName);
            intent.putExtra("AREANAME", areaName);
//            intent.putExtra("TestItem", );
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
                        && ahuSpinner.getSelectedItemPosition() > 0 && roomSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0) {
                    if (mApplicableTestAhuList.size() > 1) {
                        if (occupancySpiner.getSelectedItemPosition() > 0)
                            flag = true;
                    } else if (null != mApplicableTestAhuList && mApplicableTestAhuList.size() != 0) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }

            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("EQUIPMENT")) {
                if (equipmentAhuOrRoomSpinner.getSelectedItemPosition() > 0 && applicableTestSpinner.getSelectedItemPosition() > 0 && instrumentSpiner.getSelectedItemPosition() > 0
                        && ahuSpinner.getSelectedItemPosition() > 0 && roomSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0) {
                    if (mApplicableTestEquipmentList.size() > 1) {
                        if (occupancySpiner.getSelectedItemPosition() > 0)
                            flag = true;
                    } else if (null != mApplicableTestEquipmentList && mApplicableTestEquipmentList.size() != 0) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            } else if (equipmentAhuOrRoomTestCodeList[pos - 1].toString().equals("ROOM")) {
                if (equipmentAhuOrRoomSpinner.getSelectedItemPosition() > 0 && applicableTestSpinner.getSelectedItemPosition() > 0 && instrumentSpiner.getSelectedItemPosition() > 0
                        && ahuSpinner.getSelectedItemPosition() > 0 && roomSpinner.getSelectedItemPosition() > 0 && roomNoSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0) {
                    if (mApplicableTestRoomList.size() > 1) {
                        if (occupancySpiner.getSelectedItemPosition() > 0)
                            flag = true;
                    } else if (null != mApplicableTestRoomList && mApplicableTestRoomList.size() != 0) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            } else {
                flag = false;
            }
        }
        return flag;
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
            mTodaysDate=year+"-"+(month+1)+"-"+day;
            // Show selected date
            String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
            test_date.setText(date);
//            new StringBuilder().append(year)
//                    .append("-").append(month + 1).append("-").append(day)
//                    .append(" "));

        }
    };

    private void datePicker() {
        // Get current date by calender

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //raw data no
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        mTodaysDate=year+"-"+(month+1)+"-"+day;
        // Show current date
        String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
        test_date.setText(date);
    }

    public void spinerInitialization() {
        test_date = (TextView) findViewById(R.id.test_date);
        test_date.setText("");
        test_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
            }
        });
//        test_date.setVisibility(View.GONE);
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

        occupancySpiner = (Spinner) findViewById(R.id.select_occupancy_spinner);
        occupancySpiner.setOnTouchListener(this);


        witnessFirst = (EditText) findViewById(R.id.witnessfirst);
        witnessSecond = (EditText) findViewById(R.id.witnesssecond);
        witnessThird = (EditText) findViewById(R.id.witnessthird);
        hideWitness_iv = (ImageView) findViewById(R.id.hide_witness_iv);
        showWitness_iv = (ImageView) findViewById(R.id.show_witness_iv);

        //witnessFirst.addTextChangedListener(new WitnessNameWatcher(this));
        //witnessSecond.addTextChangedListener(new WitnessNameWatcher(this));
        //witnessThird.addTextChangedListener(new WitnessNameWatcher(this));

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
        Log.d(TAG, "TestCreateActivity : client usertype" + userType + " testCode=" + testCode);
        if (null != userType && userType.equalsIgnoreCase("CLIENT")) {
            clientInstrumentArrayList = mValdocDatabaseHandler.getClientInstrumentInfo(testCode,mTodaysDate);
            for (ClientInstrument clientInstrument : clientInstrumentArrayList) {
                instrumentList.add(clientInstrument.getSerialNo());
                Log.d(TAG, "TestCreateActivity : client instrument list" + clientInstrument.getcInstrumentName());
//                Log.d("valdoc", "TestCreateActivity : client instrument list" + clientInstrument.getSamplingFlowRate());
            }

        } else {
            Log.d(TAG, "TestCreateActivity :vendor" + userPartnerId);
//            loginUserPartnerId = mValdocDatabaseHandler.getPartnerIdFromPartnerUser(appUserId);
//            Log.d("valdoc", "TestCreateActivity :vendor loginUserPartnerId=" + loginUserPartnerId);
            partnerInstrumentArrayList = mValdocDatabaseHandler.getPartnerInstrumentInfo(userPartnerId, testCode,mTodaysDate);
            Log.d(TAG, "TestCreateActivity :vendor partnerInstrumentArrayList" + partnerInstrumentArrayList.size());
            for (PartnerInstrument partnerInstrument : partnerInstrumentArrayList) {
                instrumentList.add(partnerInstrument.getSerialNo());
                Log.d(TAG, "TestCreateActivity :vendor" + partnerInstrument.getpInstrumentName());
            }
        }

        instrumentAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, instrumentList);
        instrumentSpiner.setAdapter(instrumentAdapter);
        instrumentAdapter.notifyDataSetChanged();
    }


    public void listItemCreation() {
        equipmentAhuOrRoomList = new ArrayList<String>();
        equipmentAhuOrRoomList.add("Select  Test at Room, AHU or Equipment");
        equipmentAhuOrRoomList.add("Room");
        equipmentAhuOrRoomList.add("AHU");
        equipmentAhuOrRoomList.add("Equipment");

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

    }

    //create test Item list
    private void createTestItemList() {
        testItemList = new ArrayList<String>();
//        testItemList.add("Select Test Item");
//        new String[]{"Select Required Test", "Airflow Test (AF_AHU)",
//                "Filter Integrity Test (FIT_AHU)"};
        if (testItemList.size() > 0) {
            testItemList.clear();
        }
        if (applicableTestSpinner.getSelectedItem().toString().equalsIgnoreCase("Filter Integrity Test (FIT_AHU)")) {
            Log.d(TAG, "spiner=" + applicableTestSpinner.getSelectedItem().toString() + " fit");
            testItemList.add("Select Test Item");
//            testItemList.add("Fresh Air Filter");
            testItemList.add("Bleed Filter");
            testItemList.add("Final Filter");
//            testItemList.notify();
        } else {
            Log.d(TAG, "spiner=" + applicableTestSpinner.getSelectedItem().toString() + " fit");
            testItemList.add("Select Test Item");
            testItemList.add("Fresh Air Filter");
            testItemList.add("Bleed Filter");
            testItemList.add("Final Filter");
//            testItemList.notify();
        }

        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, testItemList);
        roomSpinner.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
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

                testItemList = new ArrayList<String>();
                if(testItemList.size()>0)
                    testItemList.clear();
                testItemList.add("Select Test Item");
                roomAdapter = new ArrayAdapter<String>(TestCreateActivity.this, R.layout.spiner_text, testItemList);
                roomSpinner.setAdapter(roomAdapter);
                roomAdapter.notifyDataSetChanged();

                witnessThird.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(witnessThird.getWindowToken(), 0);
                occupancySpiner.setVisibility(View.GONE);
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
                    spinerAhuOrEquipment = "AHU";
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
                occupancySpiner.setVisibility(View.GONE);
//                Toast.makeText(TestCreateActivity.this, "spinner creation", Toast.LENGTH_SHORT).show();
                if (spinerAhuOrEquipment.equals("AHU")) {
//                    Log.d("AHU", "pos=" + applicableTestSpinner.getSelectedItemPosition() + " Testcode=" + searchAhuTestCode[applicableTestSpinner.getSelectedItemPosition()]);
                    if (pos > 0) {
                        getInstrumentList(loginUserType, searchAhuTestCode[pos]);
                        if(null!=ahuSpinner&&ahuSpinner.getSelectedItemPosition()>0)
                        createTestItemList();
                    }
                } else if (spinerAhuOrEquipment.equals("EQUIPMENT")) {
//                    Log.d("EQUIPMENT", "pos=" + applicableTestSpinner.getSelectedItemPosition() + " Testcode=" + searchAhuTestCode[applicableTestSpinner.getSelectedItemPosition()]);
                    if (pos > 0)
                        getInstrumentList(loginUserType, searchEquipmentTestCode[pos]);
                } else if (spinerAhuOrEquipment.equals("ROOM")) {
                    Log.d(TAG, "pos=" + pos + " Testcode=" + searchRoomsTestCode[applicableTestSpinner.getSelectedItemPosition()]);
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
                occupancySpiner.setVisibility(View.GONE);
                if (pos > 0) {
                    mAhuId = mAhuArrayList.get(pos - 1).getAhuId();
                    createRoomList(mAhuId);

                }
//                Toast.makeText(TestCreateActivity.this, "room ahu pos=" + pos, Toast.LENGTH_SHORT).show();
//                roomSpinner.setVisibility(View.VISIBLE);
//                roomSpinner.setSelection(0);
//                applicableTestSpinner.setSelection(0);

                ahuSpinnerPos = ahuSpinner.getSelectedItemPosition();
//                Log.d("TestCreateActivity", "ahuspiner :position= " + ahuSpinner.getSelectedItemPosition());

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
                occupancySpiner.setVisibility(View.GONE);
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
                    //Creating occupancy list spiner
                    getApplicationTestList();
//                    createApplicableTestRoomList(mRoomNoArrayList.get(pos - 1).getRoomId(),);
                }


            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void ahuSpinnerCreation() {
//        ahuAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, ahuList);
//        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, testItemList);
//
////        ahuSpinner.setAdapter(ahuAdapter);
//        roomSpinner.setAdapter(roomAdapter);

        ahuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                TextView selectedText = (TextView) parent.getChildAt(0);
                if (null != selectedText) {
                    if (pos == 0) {
                        selectedText.setTextColor(Color.GRAY);

                        testItemList = new ArrayList<String>();
                        if(testItemList.size()>0)
                            testItemList.clear();
                        testItemList.add("Select Test Item");
                        roomAdapter = new ArrayAdapter<String>(TestCreateActivity.this, R.layout.spiner_text, testItemList);
                        roomSpinner.setAdapter(roomAdapter);
                        roomAdapter.notifyDataSetChanged();
                    } else {
                        selectedText.setTextColor(Color.BLACK);
                    }
                }
                if(pos>0)
                createTestItemList();

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

                if (pos > 0) {
                    //Creating occupancy list spiner
                    getApplicationTestList();
//                    createApplicableTestRoomList(mRoomNoArrayList.get(pos - 1).getRoomId(),);
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

//    public void occupancySpinnerCreation() {
//        occupancyList = new ArrayList<String>();
//        occupancyList.add("Select occupancy");
//        occupancyAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, occupancyList);
//        occupancySpiner.setAdapter(occupancyAdapter);
//        occupancySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            public void onItemSelected(AdapterView<?> parent, View arg1,
//                                       int pos, long arg3) {
//                // TODO Auto-generated method stub
//                TextView selectedText = (TextView) parent.getChildAt(0);
//                if (null != selectedText) {
//                    if (pos == 0) {
//                        selectedText.setTextColor(Color.GRAY);
//                    } else {
//                        selectedText.setTextColor(Color.BLACK);
//                    }
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//    }

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
                if (pos > 0) {
                    //Creating occupancy list spiner
                    getApplicationTestList();
//                    createApplicableTestRoomList(mRoomNoArrayList.get(pos - 1).getRoomId(),);
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

        testItemList = new ArrayList<String>();
        testItemList.add("Select Test Item");
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, testItemList);
        roomSpinner.setAdapter(roomAdapter);


        ahuList = new ArrayList<String>();
        ahuList.add("Select ahu");
        mAhuArrayList = mValdocDatabaseHandler.getAhuInfo();
        Log.d(TAG, "TestCreateActivity : ahu size" + mAhuArrayList.size());
        for (Ahu ahu : mAhuArrayList) {
            ahuList.add(ahu.getAhuNo() + "");
            Log.d(TAG, "TestCreateActivity : ahu" + ahu.getAhuType());
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
        Log.d(TAG, "ahuspiner : create room list=");
        if (null != equipmentNoList && equipmentNoList.size() > 0)
            equipmentNoList.clear();
        mEquipmentArrayList = mValdocDatabaseHandler.getEquipmentNoInfoByEquipmentName(equipmentName);
        equipmentNoList.add(0, "Select Equipment No");
        for (Equipment equipment : mEquipmentArrayList) {
            equipmentNoList.add(equipment.getEquipmentNo());
            Log.d(TAG, "TestCreateActivity : ahu" + equipment.getEquipmentName());
        }
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentNoList);
        roomSpinner.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
    }

    private void createRoomList(int ahuNo) {
        roomSpinner.setVisibility(View.VISIBLE);
        Log.d(TAG, "ahuspiner : create room list=");
        if (null != mRoomArrayList && mRoomArrayList.size() > 0)
            mRoomArrayList.clear();
        mRoomArrayList = mValdocDatabaseHandler.getRoomInfoByAhu(ahuNo);
        if (null != roomList && roomList.size() > 1) {
            Log.d(TAG, "TestCreateActivity : room clear");
            roomList.clear();
            roomList.add("Select Room");
        }
        roomSpinner.setSelection(0);
        for (Room room : mRoomArrayList) {
            roomList.add(room.getRoomName());
            Log.d(TAG, "TestCreateActivity : ahu" + room.getRoomName());
        }
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomList);
//        roomSpinner.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
    }

    private void createRoomNoList(String roomName, int ahuNo) {
        roomNoSpinner.setVisibility(View.VISIBLE);
        Log.d(TAG, "ahuspiner : create room list=");
        if (null != mRoomNoArrayList && mRoomNoArrayList.size() > 0)
            mRoomNoArrayList.clear();
        mRoomNoArrayList = mValdocDatabaseHandler.getRoomNoInfoByroomNameAndAhu(roomName, ahuNo);
        if (null != roomNoList && roomNoList.size() > 1) {
            Log.d(TAG, "TestCreateActivity : room clear");
            roomNoList.clear();
            roomNoList.add("Select Room No");
        }
        roomNoSpinner.setSelection(0);
        for (Room room : mRoomNoArrayList) {
            roomNoList.add(room.getRoomNo());
            Log.d(TAG, "TestCreateActivity : ahu" + room.getRoomNo());
        }
        roomNoAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomNoList);
//        roomNoSpinner.setAdapter(roomNoAdapter);
        roomNoAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        Intent afterLoginIntent = new Intent(TestCreateActivity.this, AfterLoginActivity.class);
        startActivity(afterLoginIntent);
        TestCreateActivity.this.finish();
    }

    public void aleartDialog(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
//                finish();
            }
        });

//        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean mWasEdited = false;
    public class WitnessNameWatcher implements TextWatcher {

        private Context mContext;

        public WitnessNameWatcher(Context context){

            this.mContext = context;

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mWasEdited){
                mWasEdited = false;
                return;
            }
            // get entered value (if required)
            String enteredValue  = editable.toString();
            if(enteredValue != null && !enteredValue.trim().equals("")){
                // just replace entered value with whatever you want
                // don't get trap into infinite loop
                mWasEdited = true;
                // just replace entered value with whatever you want
                editable.replace(0, editable.length(), firstLetterCaps(enteredValue));
            }
        }
    }

    public String firstLetterCaps (String data )
    {
            String firstLetter = "";
            String restLetters = "";
            firstLetter = data.substring(0,1).toUpperCase();
            restLetters = data.substring(1).toLowerCase();
            return firstLetter + restLetters;
    }

}
