package com.project.valdoc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCreateActivity extends Activity implements View.OnTouchListener {
    private static final String TAG = "TestCreateActivity";
    private Spinner instrumentSpiner, equipmentOrAhuSpinner, equipmentSpinner, ahuSpinner, roomSpinner, testSpinner;
    private List<String> instrumentList, equipmentOrAhuTestList, ahuTestList, equipmentTestList, roomTestList, applicableTestRoomList, applicableTestEquipmentList;
    private ArrayAdapter<String> instrumentAdapter, equipmentOrAhuAdapter, equipmentadApter, ahuAdapter, roomAdapter, applicableTestRoomAdapter, applicableTestequipmentAdapter;
    private ArrayList<ClientInstrument> clientInstrumentArrayList = null;
    private ArrayList<PartnerInstrument> partnerInstrumentArrayList = null;
    private ArrayList<Ahu> mAhuArrayList = null;
    private ArrayList<Equipment> mEquipmentArrayList = null;
    private ArrayList<Room> mRoomArrayList = null;
    private ArrayList<ApplicableTestRoom> mApplicableTestRoomArrayList = null;
    private ArrayList<ApplicableTestEquipment> mApplicableTestEquipmentArrayList = null;

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
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(TestCreateActivity.this);

    //test name and details
    public static final String AV="AV";
    public static final String ACPHAV="ACPHAV";
    public static final String ACPHH="ACPHH";
    public static final String FIT="FIT";
    public static final String PCT="PCT";
    public static final String RCT="RCT";


    //old
//    public static final String AV = "RD_AV_5";
//    public static final String ACPHAV = "RD_ACPH_AV";
//    public static final String ACPHH = "RD_ACPH_H";
//    public static final String FIT = "RD_FIT";
//    public static final String PCT = "RD_PC_3";
//    public static final String RCT = "RD_RCT";

    //spiner data storage
    private static String spinerInstrument;
    private static String spinerAhuOrEquipment;
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
        // Spinner Initialization
        //instrument spinner creation
        getInstrumentList(loginUserType);
        listItemCreation();
        spinnerCreation();
        initButton();

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
                    if (AV.equals(spinerTestType = testSpinner.getSelectedItem().toString())) {
                        rdAv5Test();
                    } else if (ACPHAV.equals(spinerTestType = testSpinner.getSelectedItem().toString())) {
                        rdAcphAv();
                    } else if (ACPHH.equals(spinerTestType = testSpinner.getSelectedItem().toString())) {
                        rdAcphH();
                    } else if (FIT.equals(spinerTestType = testSpinner.getSelectedItem().toString())) {
                        rdFit();
                    } else if (PCT.equals(spinerTestType = testSpinner.getSelectedItem().toString())) {
//                        Toast.makeText(TestCreateActivity.this,"Under development",Toast.LENGTH_LONG).show();
                        rdPc3();
                    } else if (RCT.equals(spinerTestType = testSpinner.getSelectedItem().toString())) {
                        rdRct();
                    }else{
                        Toast.makeText(TestCreateActivity.this, "Please select the correct test to be performed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TestCreateActivity.this, "Please select the test to be performed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void rdAcphAv() {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", ACPHAV);
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

        ApplicableTestRoom applicableTestRoom = mApplicableTestRoomArrayList.get(testSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("LOCATION", applicableTestRoom.getLocation());

        //get filter list from equipment filter
        Log.d("valdoc", "TestCreateActivity :equipment id equipment1:=" + room.getRoomId());
        ArrayList<HashMap<String, String>> grillAndSizeFromGrill = mValdocDatabaseHandler.getGrillAndSizeFromGrill(room.getRoomId());
        intent.putExtra("GRILLIST", grillAndSizeFromGrill);
        Log.d("valdoc", "TestCreateActivity :grill size:=" + grillAndSizeFromGrill.size());
        startActivity(intent);
    }

    private void rdAcphH() {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", ACPHH);
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

//            ApplicableTestRoom applicableTestRoom = mApplicableTestRoomArrayList.get(testSpinner.getSelectedItemPosition() - 1);
//            intent.putExtra("LOCATION", applicableTestRoom.getLocation());

        //get filter list from equipment filter
        Log.d("valdoc", "TestCreateActivity :equipment id equipment1:=" + room.getRoomId());
        ArrayList<HashMap<String, String>> grillAndSizeFromGrill = mValdocDatabaseHandler.getGrillAndSizeFromGrill(room.getRoomId());
        intent.putExtra("GRILLIST", grillAndSizeFromGrill);
        Log.d("valdoc", "TestCreateActivity :grill size:=" + grillAndSizeFromGrill.size());
        startActivity(intent);
    }

    private void rdFit() {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", FIT);
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

        //take test specification from room filter
        ArrayList<RoomFilter> filterArrayList = mValdocDatabaseHandler.getFromRoomFilter(room.getRoomId());
        Log.d("valdoc", "TestCreateActivity filterArrayList=" + filterArrayList.size());
        intent.putExtra("RoomFilterList", filterArrayList);
        //TO Do testspesification will be shown from room filter spesification
        intent.putExtra("AhuNumber", ahuSpinner.getSelectedItem().toString());
        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        // location will be the size off rommfilter list
        startActivity(intent);
    }

    private void rdPc3() {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", PCT);
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

        ApplicableTestRoom applicableTestRoom = mApplicableTestRoomArrayList.get(testSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("LOCATION", applicableTestRoom.getLocation());
        intent.putExtra("NOOFCYCLE", applicableTestRoom.getNoOfCycle());

        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        // location will be the size off rommfilter list
        startActivity(intent);
    }

    private void rdRct() {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", RCT);
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

        ApplicableTestRoom applicableTestRoom = mApplicableTestRoomArrayList.get(testSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("LOCATION", applicableTestRoom.getLocation());
        intent.putExtra("NOOFCYCLE", applicableTestRoom.getNoOfCycle());

        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId("" + room.getAreaId());
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        // location will be the size off rommfilter list
        startActivity(intent);
    }

    private void rdAv5Test() {
        Intent intent = new Intent(TestCreateActivity.this, DynamicTableActivity.class);
        intent.putExtra("USERTYPE", loginUserType);
        intent.putExtra("USERNAME", userName);
        intent.putExtra("testType", AV);
        intent.putExtra("PRTNERNAME", mPartnerName);
        if (loginUserType.equals("CLIENT")) {
            intent.putExtra("ClientInstrument", clientInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        } else {
            intent.putExtra("PartnerInstrument", partnerInstrumentArrayList.get(instrumentSpiner.getSelectedItemPosition() - 1));
        }

        //get room name,roomNo,and area id
        Log.d("valdoc", "TestCreateActivity :equipment:=" + equipmentSpinner.getSelectedItemPosition());
        Equipment equipment = mEquipmentArrayList.get(equipmentSpinner.getSelectedItemPosition() - 1);
        String[] roomDetails = mValdocDatabaseHandler.getRoomByEquipment(equipment.getRoomId());
        intent.putExtra("RoomDetails", roomDetails);
        intent.putExtra("Equipment", equipment);
        //get filter list from equipment filter
        Log.d("valdoc", "TestCreateActivity :equipment id name equipment1:=" + equipment.getEquipmentName());
        Log.d("valdoc", "TestCreateActivity :equipment id equipment1:=" + equipment.getTestReference());
        String[] filterList = mValdocDatabaseHandler.getFilterFromEquipmentFilter(equipment.getEquipmentId());
        intent.putExtra("FILTERLIST", filterList);
        Log.d("valdoc", "TestCreateActivity :equipment1:=" + filterList.length);
        //get area based on room area id
        String areaName = mValdocDatabaseHandler.getAreaByRoomAreaId(roomDetails[2]);
        Log.d("valdoc", "TestCreateActivity areaName=" + areaName);
        intent.putExtra("AREANAME", areaName);
        Log.d("valdoc", "TestCreateActivity witness=" + witnessFirst.getText());
        intent.putExtra("WITNESSFIRST", witnessFirst.getText().toString());
        intent.putExtra("WITNESSSECOND", witnessSecond.getText().toString());
        intent.putExtra("WITNESSTHIRD", witnessThird.getText().toString());
        ApplicableTestEquipment applicableTestEquipment = mApplicableTestEquipmentArrayList.get(testSpinner.getSelectedItemPosition() - 1);
        intent.putExtra("LOCATION", applicableTestEquipment.getLocation());
        startActivity(intent);
    }

    //spiner data validation
    private boolean validationSpiner() {
        if (instrumentSpiner.getSelectedItemPosition() > 0) {
            if (equipmentOrAhuSpinner.getSelectedItem().toString().equals("AHU")) {
                if (ahuSpinner.getSelectedItemPosition() > 0 && roomSpinner.getSelectedItemPosition() > 0 && testSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0)
                    return true;
            } else if (equipmentOrAhuSpinner.getSelectedItem().toString().equals("EQUIPMENT")) {
                if (equipmentSpinner.getSelectedItemPosition() > 0 && testSpinner.getSelectedItemPosition() > 0 && witnessFirst.getText().length() > 0)
                    return true;
            } else {
                return false;
            }
            return false;
        } else {
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void spinerInitialization() {
        user_name = (TextView) findViewById(R.id.user_name);
        user_name.setText("" + sharedpreferences.getString("USERNAME", ""));

        instrumentSpiner = (Spinner) findViewById(R.id.instrumentspiner);
        instrumentSpiner.setOnTouchListener(this);

        equipmentOrAhuSpinner = (Spinner) findViewById(R.id.equipmentahuspinner);
        equipmentOrAhuSpinner.setOnTouchListener(this);

        equipmentSpinner = (Spinner) findViewById(R.id.equipmentspinner);
        equipmentSpinner.setOnTouchListener(this);

        ahuSpinner = (Spinner) findViewById(R.id.ahuspinner);
        ahuSpinner.setOnTouchListener(this);

        roomSpinner = (Spinner) findViewById(R.id.roomspinner);
        roomSpinner.setOnTouchListener(this);

        testSpinner = (Spinner) findViewById(R.id.testspinner);
        testSpinner.setOnTouchListener(this);

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

    public void listItemCreation() {
        equipmentOrAhuTestList = new ArrayList<String>();
        equipmentOrAhuTestList.add("Select Ahu or Equipment");
        equipmentOrAhuTestList.add("EQUIPMENT");
        equipmentOrAhuTestList.add("AHU");
//create ahu list
        createAhuList();
        createEquipmentList();
        roomTestList = new ArrayList<String>();
        roomTestList.add("Slect room");

        applicableTestRoomList = new ArrayList<String>();
        applicableTestRoomList.add("Select Test Type");
        applicableTestEquipmentList = new ArrayList<String>();
    }

    private void getInstrumentList(String userType) {
//        if(instrumentList.)
//        instrumentList.clear();
//        clientInstrumentArrayList.clear();
        instrumentList = new ArrayList<String>();
        instrumentList.add("Select Instrument");
        Log.d("valdoc", "TestCreateActivity : client usertype" + userType);
        if (null != userType && userType.equalsIgnoreCase("CLIENT")) {
            clientInstrumentArrayList = mValdocDatabaseHandler.getClientInstrumentInfo();

            for (ClientInstrument clientInstrument : clientInstrumentArrayList) {
                instrumentList.add(clientInstrument.getSerialNo());
                Log.d("valdoc", "TestCreateActivity : client instrument list" + clientInstrument.getcInstrumentName());
                Log.d("valdoc", "TestCreateActivity : client instrument list" + clientInstrument.getSamplingFlowRate());
            }

        } else {
            Log.d("valdoc", "TestCreateActivity :vendor" + userPartnerId);
//            loginUserPartnerId = mValdocDatabaseHandler.getPartnerIdFromPartnerUser(appUserId);
//            Log.d("valdoc", "TestCreateActivity :vendor loginUserPartnerId=" + loginUserPartnerId);
            partnerInstrumentArrayList = mValdocDatabaseHandler.getPartnerInstrumentInfo(userPartnerId);
            Log.d("valdoc", "TestCreateActivity :vendor partnerInstrumentArrayList" + partnerInstrumentArrayList.size());
            for (PartnerInstrument partnerInstrument : partnerInstrumentArrayList) {
                instrumentList.add(partnerInstrument.getSerialNo());
                Log.d("valdoc", "TestCreateActivity :vendor" + partnerInstrument.getpInstrumentName());
            }
        }


    }

    public void spinnerCreation() {

        instrumentAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, instrumentList);
        equipmentOrAhuAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentOrAhuTestList);
        equipmentadApter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentTestList);
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomTestList);
        applicableTestRoomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, applicableTestRoomList);
        applicableTestequipmentAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, applicableTestEquipmentList);

        instrumentSpiner.setAdapter(instrumentAdapter);
        equipmentOrAhuSpinner.setAdapter(equipmentOrAhuAdapter);
        equipmentSpinner.setAdapter(equipmentadApter);
        ahuSpinner.setAdapter(ahuAdapter);
        roomSpinner.setAdapter(roomAdapter);
        testSpinner.setAdapter(applicableTestRoomAdapter);

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

        equipmentOrAhuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                witnessThird.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(witnessThird.getWindowToken(), 0);

                ahuSpinner.setVisibility(View.GONE);
                equipmentSpinner.setVisibility(View.GONE);
                equipmentOrAhuSpinnerPos = equipmentOrAhuSpinner.getSelectedItemPosition();
                // TODO Auto-generated method stub
                if (equipmentOrAhuSpinner.getSelectedItem().toString().equals("AHU")) {
//                    createAhuList();
                    ahuSpinner.setVisibility(View.VISIBLE);
                    equipmentSpinner.setVisibility(View.GONE);
                    roomSpinner.setVisibility(View.VISIBLE);
//                    roomTestList.add("Select Room");
                    roomSpinner.setSelection(0);
                    ahuSpinner.setSelection(0);
                    testSpinner.setSelection(0);
                    spinerAhuOrEquipment = "AHU";
                    Log.d("TestCreateActivity", "spiner :");
                } else if (equipmentOrAhuSpinner.getSelectedItem().toString().equals("EQUIPMENT")) {
                    ahuSpinner.setVisibility(View.GONE);
                    equipmentSpinner.setVisibility(View.VISIBLE);
                    roomSpinner.setVisibility(View.GONE);
                    testSpinner.setSelection(0);
                    spinerAhuOrEquipment = "EQUIPMENT";
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        equipmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                equipmentSpinnerPos = equipmentSpinner.getSelectedItemPosition();
                Log.d("TestCreateActivity", "equipment :index= " + equipmentSpinner.getSelectedItemPosition() + "mEquipmentArrayList size=" + mEquipmentArrayList.size());
                if (equipmentSpinner.getSelectedItemPosition() > 0) {
                    Equipment equipment = mEquipmentArrayList.get(equipmentSpinner.getSelectedItemPosition() - 1);
                    createApplicableTestEquipmentList(equipment.getEquipmentId());
                    spinerEquipment = equipment.getEquipmentId();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

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
                roomSpinner.setVisibility(View.VISIBLE);
                roomSpinner.setSelection(0);
                testSpinner.setSelection(0);

                ahuSpinnerPos = ahuSpinner.getSelectedItemPosition();
                Log.d("TestCreateActivity", "ahuspiner :position= " + ahuSpinner.getSelectedItemPosition());

                if (ahuSpinner.getSelectedItemPosition() > 0) {
                    Ahu ahu = mAhuArrayList.get(ahuSpinner.getSelectedItemPosition() - 1);
                    Log.d("TestCreateActivity", "ahuspiner : ahu.getAhuId()=" + ahu.getAhuId());
                    createRoomList(ahu.getAhuId());
                    spinerAhu = ahu.getAhuId();
//                    roomSpinner.setVisibility(View.VISIBLE);
                }
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
                testSpinner.setSelection(0);
                roomSpinnerPos = roomSpinner.getSelectedItemPosition();
                Log.d("TestCreateActivity", "equipment :index= " + roomSpinner.getSelectedItemPosition());
                if (roomSpinner.getSelectedItemPosition() > 0) {
                    Room room = mRoomArrayList.get(roomSpinner.getSelectedItemPosition() - 1);
                    createApplicableTestRoomList(room.getRoomId());
                    Log.d("TestCreateActivity", "room.getRoomId()= " + room.getRoomId());
                    spinerRoom = room.getRoomName();
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        testSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                // TODO Auto-generated method stub
                testSpinnerPos = testSpinner.getSelectedItemPosition();
                spinerTestType = testSpinner.getSelectedItem().toString();
//                Toast.makeText(getBaseContext(), TestList.get(arg2),
//                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


    }


    //create ahu list
    private void createAhuList() {
        ahuTestList = new ArrayList<String>();
        ahuTestList.add("Select ahu");
        mAhuArrayList = mValdocDatabaseHandler.getAhuInfo();
        Log.d("valdoc", "TestCreateActivity : ahu size" + mAhuArrayList.size());
        for (Ahu ahu : mAhuArrayList) {
            ahuTestList.add(ahu.getAhuNo() + "");
            Log.d("valdoc", "TestCreateActivity : ahu" + ahu.getAhuType());
        }
        ahuAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, ahuTestList);
//        ahuSpinner.setAdapter(ahuAdapter1);
        ahuAdapter.notifyDataSetChanged();
    }


    private void createEquipmentList() {
        equipmentTestList = new ArrayList<String>();
        if (null != equipmentTestList && equipmentTestList.size() > 0) {
            equipmentTestList.clear();
        }
        equipmentTestList.add("Select Equipment");
        mEquipmentArrayList = mValdocDatabaseHandler.getEquipmentInfo();

        for (Equipment equipment : mEquipmentArrayList) {
            equipmentTestList.add(equipment.getEquipmentNo());
            Log.d("valdoc", "TestCreateActivity : ahu" + equipment.getEquipmentName());
        }
        equipmentadApter = new ArrayAdapter<String>(this, R.layout.spiner_text, equipmentTestList);
//        equipmentSpinner.setAdapter(equipmentadApter);
        equipmentadApter.notifyDataSetChanged();
    }


    private void createRoomList(int ahuNo) {
        roomSpinner.setVisibility(View.VISIBLE);
        Log.d("TestCreateActivity", "ahuspiner : create room list=");
        if (null != mRoomArrayList && mRoomArrayList.size() > 0)
            mRoomArrayList.clear();
        mRoomArrayList = mValdocDatabaseHandler.getRoomInfoByAhu(ahuNo);
        if (null != roomTestList && roomTestList.size() > 1) {
            Log.d("valdoc", "TestCreateActivity : room clear");
            roomTestList.clear();
            roomTestList.add("Select Room");
        }
        roomSpinner.setSelection(0);
        for (Room room : mRoomArrayList) {
            roomTestList.add(room.getRoomName());
            Log.d("valdoc", "TestCreateActivity : ahu" + room.getRoomName());
        }
        roomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, roomTestList);
//        roomSpinner.setAdapter(roomAdapter);
        roomAdapter.notifyDataSetChanged();
    }


    private void createApplicableTestRoomList(int roomId) {
        Log.d("TestCreateActivity", "ahuspiner : create room list=");
        if (null != mApplicableTestRoomArrayList && mApplicableTestRoomArrayList.size() > 0)
            mApplicableTestRoomArrayList.clear();
        mApplicableTestRoomArrayList = mValdocDatabaseHandler.getApplicableTestRoomInfo(roomId);
        if (null != applicableTestRoomList && applicableTestRoomList.size() > 0) {
            applicableTestRoomList.clear();
            applicableTestRoomAdapter.notifyDataSetChanged();
        }
        applicableTestRoomList.add("Select Test Type");
        for (ApplicableTestRoom applicableTestRoom : mApplicableTestRoomArrayList) {
            applicableTestRoomList.add(applicableTestRoom.getTestName());
            Log.d("valdoc", "TestCreateActivity : ahu" + applicableTestRoom.getTestName());
        }
        applicableTestRoomAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, applicableTestRoomList);
        testSpinner.setAdapter(applicableTestRoomAdapter);
        testSpinner.setSelection(0);
        applicableTestRoomAdapter.notifyDataSetChanged();
    }

    private void createApplicableTestEquipmentList(int equipmentId) {
        Log.d("TestCreateActivity", "ahuspiner : create room list=");
        if (null != mApplicableTestEquipmentArrayList && mApplicableTestEquipmentArrayList.size() > 0)
            mApplicableTestEquipmentArrayList.clear();
        mApplicableTestEquipmentArrayList = mValdocDatabaseHandler.getApplicableTestEquipmentInfo(equipmentId);
        if (null != applicableTestEquipmentList && applicableTestEquipmentList.size() > 0) {
            applicableTestEquipmentList.clear();
            applicableTestRoomAdapter.notifyDataSetChanged();
        }
        applicableTestEquipmentList.add("Select Test Type");
        for (ApplicableTestEquipment applicableTestEquipment : mApplicableTestEquipmentArrayList) {
            applicableTestEquipmentList.add(applicableTestEquipment.getTestName());
            Log.d("valdoc", "TestCreateActivity : applicableTestEquipment" + applicableTestEquipment.getTestName());
        }
        applicableTestequipmentAdapter = new ArrayAdapter<String>(this, R.layout.spiner_text, applicableTestEquipmentList);
        testSpinner.setAdapter(applicableTestequipmentAdapter);
        applicableTestequipmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent afterLoginIntent = new Intent(TestCreateActivity.this, AfterLoginActivity.class);
        startActivity(afterLoginIntent);
        TestCreateActivity.this.finish();
    }
}
