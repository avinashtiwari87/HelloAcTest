package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
            test4_table_layout5, test4_table_layout6, test4_table_layout7, test4_table_layout8;
    //Test 5 View ...
    TableLayout test5_table_layout, test5_table_layout2, test5_table_layout2_1, test5_tableLayout2_2,
            test5_table_layout3, test5_table_layout4, test5_tableLayout4_2, test5_table_layout4_1,
            test5_table_layout5, test5_table_layout5_1, test5_table_layout3_1;
    //Test 6 View ...
    TableLayout test6A_table_layout, test6A_table_layout2, test6A_table_layout3,
            test6A_table_layout4;
    //Common Test Header
    TextView test_header1, test_header2, test_header3, test_header4, test_header5, test_header6, test_header7, test_header8, test_header9, test_header10, test_header11, test_header12;
    TextView test_value1, test_value2, test_value3, test_value4, test_value5, test_value6, test_value7, test_value8, test_value9, test_value10, test_value11, test_value12;
    TextView finalReadingTv;
    EditText finalReadingValueTv;
    private TableRow sampling_flow_rate;
    private TableRow sampling_time;
    private TableRow clean_room_class;
    ImageView verify_btn, clear, cancel;
    ImageView addRow_Btn, deleteRow_Btn;
    int rows, cols;
    String testType;
    ProgressDialog pr;
    private String mTestCode = "";
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    /*Unique Ids for EditText and TextView
           must be defined in Constant Class
    * */
    //Test 1 Ids variable also used in test 2
    int idCountEtv = 200, idCountTv = 1, idCountFitStrm = 800, idCountFitAftStrm = 900;
    //Test 2 Ids variable
    int filterSizeIds = 100, airFlowRateIds = 300,
            totalAirFlowRateIds = 400;
    //Test 4 Ids & variable
    int test4Id = 700;
    // Test 5 Variable
    int test5CommonFormulaIds1 = 500, test5CommonFormulaIds2 = 600;
    long meanValue1 = 0L, meanValue2 = 0L;
    double stdDev1 = 0.0, stdDev2 = 0.0;
    //Test 6 Variable
    int rowsCount = 0;
    //Validate boolean array...
    ArrayList<Boolean>validate = new ArrayList<>();

    ArrayList<String> testReadingEditTextList;
    ArrayList<TextView> txtPassFailList;
    ArrayList<TextView> txtConcentrationVariationList;
    ArrayList<TextView> txtViewList;
    ArrayList<EditText> editTextList;
    ArrayList<TextView> filterSizeTxtViewList;
    ArrayList<TextView> airFlowRateTxtViewList;
    ArrayList<TextView> totalAirFlowRateTxtList;
    ArrayList<TextView> roomVolumeTxtList;
    ArrayList<TextView> airChangeTxtList;
    ArrayList<TextView> RDPC3TxtList, RDPC3TxtList2;

    HashMap<Integer, Double> rdFitInputDataHashMap;
    ArrayList<Double>concentrationVariationListData;
    HashMap<Integer, Integer> inputDataHashMap;
    HashMap<Integer, String> passFailHashMap;
    HashMap<Integer, Long> resultDataHashMap;
    HashMap<Integer, Float> resultDataHashMap2;
    HashMap<Integer, Integer> rowTagHashMap;
    int inputValue = 0, AirChangeValue = 0;
    double fitInputValue = 0.0;
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
    private String mTestItem;
    private Room room;
    private ArrayList<Grill> grillAndSizeFromGrill;
    private ArrayList<RoomFilter> mRoomFilterArrayList;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private int applicableTestEquipmentLocation;
    //    private int applicableTestRoomLocation;
    private TextView roomName;
    private TextView instrumentName;
    private TextView instrumentNo;
    private TextView testerName;
    private int noOfCycle;
    private String mPartnerName;
    private String mTestBasedOn;
    private String mGrilFilterType;
    private ArrayList<EquipmentGrill> mEquipmentGrillArrayList = null;
    private Ahu ahu = null;
    private ArrayList<AhuFilter> mAhuFilterArrayList = null;
    private ApplicableTestAhu mApplicableTestAhu = null;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ApplicableTestEquipment mApplicableTestEquipment = null;
    private ArrayList<EquipmentFilter> mEquipmentFilterArrayList = null;

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
        mTestCode = getIntent().getStringExtra("testCode");
        Log.d(TAG, " TestType : " + testType);
        getExtraFromTestCreateActivity(savedInstanceState);

        initRes();

        testReadingEditTextList = new ArrayList<String>();
        txtPassFailList = new ArrayList<TextView>();
        txtConcentrationVariationList = new ArrayList<TextView>();
        txtViewList = new ArrayList<TextView>();
        editTextList = new ArrayList<EditText>();
        filterSizeTxtViewList = new ArrayList<TextView>();
        airFlowRateTxtViewList = new ArrayList<TextView>();
        totalAirFlowRateTxtList = new ArrayList<TextView>();
        roomVolumeTxtList = new ArrayList<TextView>();
        airChangeTxtList = new ArrayList<TextView>();
        RDPC3TxtList = new ArrayList<TextView>();
        RDPC3TxtList2 = new ArrayList<TextView>();

        rdFitInputDataHashMap = new HashMap<Integer, Double>();
        concentrationVariationListData = new ArrayList<Double>();
        inputDataHashMap = new HashMap<Integer, Integer>();
        resultDataHashMap = new HashMap<Integer, Long>();
        passFailHashMap = new HashMap<Integer, String>();
        resultDataHashMap2 = new HashMap<Integer, Float>();
        rowTagHashMap = new HashMap<Integer, Integer>();


        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(DynamicTableActivity.this, mActionBar, userName);
        createTableRowColum();
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
                if (TestCreateActivity.AV.equals(testType)) {
                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        roomDetails = extras.getStringArray("RoomDetails");
                        equipment = (Equipment) extras.getSerializable("Equipment");
                        mGrilFilterType = extras.getString("GrilFilterType");
                        //get filter list from equipment filter
//                        filterList = new String[extras.getStringArray("FILTERLIST").length];

                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            mEquipmentGrillArrayList = (ArrayList<EquipmentGrill>) extras.getSerializable("GRILLLIST");
                        } else {
                            mEquipmentFilterArrayList = (ArrayList<EquipmentFilter>) extras.getSerializable("GRILLLIST");
                        }
                        mApplicableTestEquipment = (ApplicableTestEquipment) extras.getSerializable("ApplicableTestEquipment");
                    } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        roomDetails = extras.getStringArray("RoomDetails");
                        ahu = (Ahu) extras.getSerializable("Ahu");
                        mAhuFilterArrayList = (ArrayList<AhuFilter>) extras.getSerializable("AhuFilter");
                        mApplicableTestAhu = (ApplicableTestAhu) extras.getSerializable("ApplicableTestAhu");
                    }
                }

                if (TestCreateActivity.ACPHAV.equals(testType)) {
                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        roomDetails = extras.getStringArray("RoomDetails");
//                        ahu = (Ahu) extras.getSerializable("Ahu");
                        mTestItem = extras.getString("testItem");
                        ahuNumber = extras.getString("AhuNumber");
                        mAhuFilterArrayList = (ArrayList<AhuFilter>) extras.getSerializable("AhuFilter");
                        mApplicableTestAhu = (ApplicableTestAhu) extras.getSerializable("ApplicableTestAhu");

                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        mGrilFilterType = extras.getString("GrilFilterType");
                        room = (Room) extras.getSerializable("Room");
                        ahuNumber = extras.getString("AhuNumber");
                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            grillAndSizeFromGrill = (ArrayList<Grill>) extras.getSerializable("GRILLLIST");
                        } else {
                            mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilter");
                        }

                        mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                    }

                }

                if (TestCreateActivity.ACPHH.equals(testType)) {
                    //get room name,roomNo,and area id
                    mGrilFilterType = extras.getString("GrilFilterType");
                    room = (Room) extras.getSerializable("Room");
                    ahuNumber = extras.getString("AhuNumber");
                    //get filter list from grill filter
                    if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                        grillAndSizeFromGrill = (ArrayList<Grill>) extras.getSerializable("GRILLLIST");
                    } else {
                        mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilter");
                    }
                    mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
//                    grillAndSizeFromGrill = (ArrayList<HashMap<String, String>>) extras.getSerializable("GRILLIST");
                }
                if (TestCreateActivity.FIT.equals(testType)) {
                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        mEquipmentFilterArrayList = (ArrayList<EquipmentFilter>) extras.getSerializable("EquipmentFilter");
                        roomDetails = extras.getStringArray("RoomDetails");
                        equipment = (Equipment) extras.getSerializable("Equipment");
                        mApplicableTestEquipment = (ApplicableTestEquipment) extras.getSerializable("ApplicableTestEquipment");
                    } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        mTestItem = extras.getString("testItem");
                        ahuNumber = extras.getString("AhuNumber");
                        roomDetails = extras.getStringArray("RoomDetails");
                        Log.d("Dynamictest", "roomDetails=" + roomDetails[1]);
                        mAhuFilterArrayList = (ArrayList<AhuFilter>) extras.getSerializable("AhuFilter");
                        mApplicableTestAhu = (ApplicableTestAhu) extras.getSerializable("ApplicableTestAhu");
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        room = (Room) extras.getSerializable("Room");
                        ahuNumber = extras.getString("AhuNumber");
                        mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilter");
                        mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                    }

                    //take test specification from room filter

                    //TO Do testspesification will be shown from room filter spesification
                }
                if (TestCreateActivity.PCT.equals(testType)) {
                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
//                        mEquipmentFilterArrayList = (ArrayList<EquipmentFilter>) extras.getSerializable("EquipmentFilter");
                        roomDetails = extras.getStringArray("RoomDetails");
                        equipment = (Equipment) extras.getSerializable("Equipment");
                        mApplicableTestEquipment = (ApplicableTestEquipment) extras.getSerializable("ApplicableTestEquipment");
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        room = (Room) extras.getSerializable("Room");
                        ahuNumber = extras.getString("AhuNumber");
//                        mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilter");
                        mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                    }
//                    room = (Room) extras.getSerializable("Room");
//                    ahuNumber = extras.getString("AhuNumber");
//                    applicableTestRoomLocation = extras.getInt("LOCATION");
//                    noOfCycle = extras.getInt("NOOFCYCLE");
//                    Log.d("valdoc", "DynamicTableActivity" + "NOOFCYCLE NOOFCYCLE=" + noOfCycle + "location=" + applicableTestRoomLocation);
                }
                if (TestCreateActivity.RCT.equals(testType)) {
                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
//                        mEquipmentFilterArrayList = (ArrayList<EquipmentFilter>) extras.getSerializable("EquipmentFilter");
                        roomDetails = extras.getStringArray("RoomDetails");
                        equipment = (Equipment) extras.getSerializable("Equipment");
                        mApplicableTestEquipment = (ApplicableTestEquipment) extras.getSerializable("ApplicableTestEquipment");
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        room = (Room) extras.getSerializable("Room");
                        ahuNumber = extras.getString("AhuNumber");
//                        mRoomFilterArrayList = (ArrayList<RoomFilter>) extras.getSerializable("RoomFilter");
                        mApplicableTestRoom = (ApplicableTestRoom) extras.getSerializable("ApplicableTestRoom");
                    }
//                    room = (Room) extras.getSerializable("Room");
//                    ahuNumber = extras.getString("AhuNumber");
//                    applicableTestRoomLocation = extras.getInt("LOCATION");
//                    noOfCycle = extras.getInt("NOOFCYCLE");//throwing Exception...plz check tiwari jee
                }
            }
        }

    }

    public void aleartDialog(String message) {
        verify_btn.setEnabled(false);
        clear.setEnabled(false);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
                verify_btn.setEnabled(true);
                clear.setEnabled(true);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void createTableRowColum() {
        if (TestCreateActivity.AV.equalsIgnoreCase(testType)) {
            if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                if (mAhuFilterArrayList != null && mAhuFilterArrayList.size() > 0)
                    BuildTable(mAhuFilterArrayList.size() + 1, mApplicableTestAhu.getLocation());
                else
                    aleartDialog("There is no filter or equipment location");
            } else if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                    if (mEquipmentGrillArrayList != null && mEquipmentGrillArrayList.size() > 0) {
                        Log.d("avinash", "mApplicableTestEquipment.getLocation()=" + mApplicableTestEquipment.getLocation());
                        BuildTable(mEquipmentGrillArrayList.size() + 1, mApplicableTestEquipment.getLocation());
                    } else
                        aleartDialog("There is no filter or equipment location");
                } else {
                    if (mEquipmentFilterArrayList != null && mEquipmentFilterArrayList.size() > 0) {
                        Log.d("avinash", "mApplicableTestEquipment.getLocation()=" + mApplicableTestEquipment.getLocation());
                        BuildTable(mEquipmentFilterArrayList.size() + 1, mApplicableTestEquipment.getLocation());
                    } else
                        aleartDialog("There is no filter or equipment location");
                }

            }
            setCommonTestHeader(testType, mTestBasedOn);
        } else if (TestCreateActivity.ACPHAV.equalsIgnoreCase(testType)) {
            Log.d("Saurabh ", "CodeFlow testType : " + testType);
            if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                setCommonTestHeader(testType, mTestBasedOn);
                if (mAhuFilterArrayList != null && mAhuFilterArrayList.size() > 0 && mApplicableTestAhu.getLocation() > 0)
                    BuildTableTest2(mAhuFilterArrayList.size() + 1, mApplicableTestAhu.getLocation());
                else
                    aleartDialog("There is no gril or applicable test room location");
            } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                setCommonTestHeader(testType, mTestBasedOn);
                if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                    if (grillAndSizeFromGrill != null && grillAndSizeFromGrill.size() > 0 && mApplicableTestRoom.getLocation() > 0) {
                        BuildTableTest2(grillAndSizeFromGrill.size() + 1, mApplicableTestRoom.getLocation());
                    } else
                        aleartDialog("There is no gril or applicable test room location");
                } else {
                    if (mRoomFilterArrayList != null && mRoomFilterArrayList.size() > 0 && mApplicableTestRoom.getLocation() > 0) {
                        BuildTableTest2(mRoomFilterArrayList.size() + 1, mApplicableTestRoom.getLocation());
                    } else
                        aleartDialog("There is no gril or applicable test room location");
                }

            }
        } else if (TestCreateActivity.ACPHH.equalsIgnoreCase(testType)) {
            if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                if (grillAndSizeFromGrill != null && grillAndSizeFromGrill.size() > 0)
                    BuildTableTest3(grillAndSizeFromGrill.size() + 1, mApplicableTestRoom.getLocation());
                else
                    aleartDialog("There is no gril or applicable test room location");
            } else {
                if (mRoomFilterArrayList != null && mRoomFilterArrayList.size() > 0)
                    BuildTableTest3(mRoomFilterArrayList.size() + 1, mApplicableTestRoom.getLocation());
                else
                    aleartDialog("There is no gril or applicable test room location");
            }

            setCommonTestHeader(testType, mTestBasedOn);
        } else if (TestCreateActivity.FIT.equalsIgnoreCase(testType)) {

            if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                if (mRoomFilterArrayList != null && mRoomFilterArrayList.size() > 0)
                    BuildTableTest4(mRoomFilterArrayList.size() + 1, mApplicableTestRoom.getLocation());
                else
                    aleartDialog("There is no filter ");
            } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                if (mAhuFilterArrayList != null && mAhuFilterArrayList.size() > 0)
                    BuildTableTest4(mAhuFilterArrayList.size() + 1, cols);
                else
                    aleartDialog("There is no filter ");
            } else if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                if (mEquipmentFilterArrayList != null && mEquipmentFilterArrayList.size() > 0)
                    BuildTableTest4(mEquipmentFilterArrayList.size() + 1, cols);
                else
                    aleartDialog("There is no filter ");
            }
            setCommonTestHeader(testType, mTestBasedOn);
        } else if (TestCreateActivity.PCT.equalsIgnoreCase(testType)) {

            if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                rows = mApplicableTestRoom.getLocation() + 1;
                if (rows >= 1) {
                    BuildTableTest5(rows, mApplicableTestRoom.getNoOfCycle());
//                    BuildTableTest5(7, 3);
                } else
                    aleartDialog("There is no noOfCycle or applicable test room location");

            } else if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                rows = mApplicableTestEquipment.getLocation() + 1;
                if (rows >= 1) {
                    BuildTableTest5(rows + 1, mApplicableTestEquipment.getNoOfCycle());
//                    BuildTableTest5(7, 3);
                } else
                    aleartDialog("There is no noOfCycle or applicable test room location");
            }

            setCommonTestHeader(testType, mTestBasedOn);
        } else if (TestCreateActivity.RCT.equalsIgnoreCase(testType)) {
//            if (grillAndSizeFromGrill != null && grillAndSizeFromGrill.size() > 0)
//                BuildTableTest6(grillAndSizeFromGrill.size() + 1, cols);
            BuildTableTest6(3, 1);
//            else
//                aleartDialog("There is no gril");
            setCommonTestHeader(testType, mTestBasedOn);
        }

        if (pr.isShowing())
            pr.dismiss();
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    private void setCommonTestHeader(String testType, String TestBasedOn) {
        test_value3.setText(userName);
        if (TestCreateActivity.AV.equalsIgnoreCase(testType) && TestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
            test_header1.setText("Equipment Name :");
            test_header2.setText("Instrument Used :");
            test_header3.setText("Test Conducted By:");
            test_header4.setText("Eqipment No:");
            test_header5.setText("Instrument Sr. No:");
            test_header6.setText("Occupancy State :");
            test_header7.setText("Date of Test :");
            test_header9.setText("Due :");
            test_header8.setText("Calibrated on :");
            if (loginUserType.equals("CLIENT")) {
                test_value2.setText(clientInstrument.getcInstrumentName());
                test_value5.setText("" + clientInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+clientInstrument.getLastCalibrated()));
                test_value9.setText("" + parseDateToddMMyyyy(""+clientInstrument.getCalibrationDueDate()));
            } else {
                test_value2.setText(partnerInstrument.getpInstrumentName());
                test_value5.setText("" + partnerInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getLastCalibrationDate()));
                test_value9.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getCalibrationDueDate()));
            }

            test_value1.setText("" + equipment.getEquipmentName());
            test_value4.setText("" + equipment.getEquipmentNo());

            test_value6.setText("" + mApplicableTestEquipment.getOccupencyState());
            datePicker();
        } else if (TestCreateActivity.ACPHAV.equalsIgnoreCase(testType)) {
            Log.d("Saurabh", "CodeFlow TestBasedOn" + TestBasedOn);
            test_header1.setText("Room Name :");
            test_header2.setText("Instrument Used :");
            test_header3.setText("Test Conducted By:");
            test_header4.setText("Room No:");
            test_header5.setText("Instrument Sr. No:");
            test_header6.setText("Occupancy State :");
            test_header7.setText("Date of Test :");
            test_header9.setText("Due :");
            test_header8.setText("Calibrated on :");
            if (loginUserType.equals("CLIENT")) {
                test_value2.setText(clientInstrument.getcInstrumentName());
                test_value5.setText("" + clientInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+clientInstrument.getLastCalibrated()));
                test_value9.setText("" + parseDateToddMMyyyy(""+clientInstrument.getCalibrationDueDate()));
            } else {
                test_value2.setText(partnerInstrument.getpInstrumentName());
                test_value5.setText("" + partnerInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getLastCalibrationDate()));
                test_value9.setText("" + parseDateToddMMyyyy(""+ partnerInstrument.getCalibrationDueDate()));
            }
            test_value3.setText(userName);
            if (TestBasedOn.equalsIgnoreCase("AHU")) {
                test_header1.setText("AHU :");
                test_header4.setText("Test Item :");
                test_value1.setText("" + ahuNumber);
                test_value4.setText("" + mTestItem);
                test_value6.setText("" + mApplicableTestAhu.getOccupencyState());
            } else if (TestBasedOn.equalsIgnoreCase("ROOM")) {
                test_value1.setText("" + room.getRoomName());
                test_value4.setText("" + room.getRoomNo());
                test_value6.setText("" + mApplicableTestRoom.getOccupencyState());
            }
//
            datePicker();
        } else if (TestCreateActivity.ACPHH.equalsIgnoreCase(testType)) {
            test_header1.setText("Room Name :");
            test_header2.setText("Instrument Used :");
            test_header3.setText("Test Conducted By:");
            test_header4.setText("Room No:");
            test_header5.setText("Instrument Sr. No:");
            test_header6.setText("Occupancy State :");
            test_header7.setText("Date of Test :");
            test_header9.setText("Due :");
            test_header8.setText("Calibrated on :");
            if (loginUserType.equals("CLIENT")) {
                test_value2.setText(clientInstrument.getcInstrumentName());
                test_value5.setText("" + clientInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+clientInstrument.getLastCalibrated()));
                test_value9.setText("" + parseDateToddMMyyyy(""+clientInstrument.getCalibrationDueDate()));
            } else {
                test_value2.setText(partnerInstrument.getpInstrumentName());
                test_value5.setText("" + partnerInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getLastCalibrationDate()));
                test_value9.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getCalibrationDueDate()));
            }
            test_value3.setText(userName);

            test_value1.setText("" + room.getRoomName());
            test_value4.setText("" + room.getRoomNo());
//            test_value6.setText("" + mApplicableTestRoom.getOccupencyState());
            datePicker();
        } else if (TestCreateActivity.FIT.equalsIgnoreCase(testType)) {
            if (TestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                test_header1.setText("Equipment Name :");
                test_header4.setText("Eqipment No :");
            } else if (TestBasedOn.equalsIgnoreCase("ROOM")) {
                test_header1.setText("Room Name :");
                test_header4.setText("Room No :");
            } else {
                test_header1.setText("AHU :");
                test_header4.setText("Test Item :");
            }
            test_header2.setText("Instrument Used :");
            test_header3.setText("Test Conducted By :");
            test_header5.setText("Instrument Sr. No :");
            test_header6.setText("Occupancy State :");
            test_header7.setText("Date of Test :");
            test_header9.setText("Due :");
            test_header8.setText("Calibrated on :");
            if (loginUserType.equals("CLIENT")) {
                test_value2.setText(clientInstrument.getcInstrumentName());
                test_value5.setText("" + clientInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+clientInstrument.getLastCalibrated()));
                test_value9.setText("" + parseDateToddMMyyyy(""+clientInstrument.getCalibrationDueDate()));
            } else {
                test_value2.setText(partnerInstrument.getpInstrumentName());
                test_value5.setText("" + partnerInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getLastCalibrationDate()));
                test_value9.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getCalibrationDueDate()));
            }
            test_value3.setText(userName);

            if (TestBasedOn.equalsIgnoreCase("AHU")) {
                test_value1.setText("" + ahuNumber);
                test_value4.setText("" + mTestItem);
                test_value6.setText("" + mApplicableTestAhu.getOccupencyState());
            } else if (TestBasedOn.equalsIgnoreCase("ROOM")) {
                test_value1.setText("" + room.getRoomName());
                test_value4.setText("" + room.getRoomNo());
                test_value6.setText("" + mApplicableTestRoom.getOccupencyState());
            } else if (TestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                test_value1.setText("" + equipment.getEquipmentName());
                test_value4.setText("" + equipment.getEquipmentNo());
                test_value6.setText("" + mApplicableTestEquipment.getOccupencyState());
            }
            datePicker();
        } else if (TestCreateActivity.PCT.equalsIgnoreCase(testType)) {
            if (TestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                test_header1.setText("Equipment Name :");
                test_header4.setText("Eqipment No:");
            } else {
                test_header1.setText("Room Name :");
                test_header4.setText("Room No:");
            }
            test_header2.setText("Instrument Used :");
            test_header3.setText("Test Conducted By:");
            test_header5.setText("Instrument Sr. No:");
            test_header6.setText("Occupancy State :");
            test_header7.setText("Date of Test :");
            test_header9.setText("Due :");
            test_header8.setText("Calibrated on :");
            test_header10.setText("Sampling Flow Rate :");
            test_header11.setText("Sampling Time :");
            if (loginUserType.equals("CLIENT")) {
                test_value2.setText(clientInstrument.getcInstrumentName());
                test_value5.setText("" + clientInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+clientInstrument.getLastCalibrated()));
                test_value9.setText("" + parseDateToddMMyyyy(""+clientInstrument.getCalibrationDueDate()));
                test_value10.setText(""+clientInstrument.getRange());
            } else {
                test_value2.setText(partnerInstrument.getpInstrumentName());
                test_value5.setText("" + partnerInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getLastCalibrationDate()));
                test_value9.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getCalibrationDueDate()));
                test_value10.setText(""+partnerInstrument.getRange());
            }
            test_value3.setText(userName);

            if (TestBasedOn.equalsIgnoreCase("ROOM")) {
                test_header12.setText("Cleanroom Class :");
                test_value1.setText("" + room.getRoomName());
                test_value4.setText("" + room.getRoomNo());
                test_value6.setText("" + mApplicableTestRoom.getOccupencyState());

                String samplingTime = getSamplingTime(mApplicableTestRoom.getTestSpecification(),"");
                test_value11.setText(""+samplingTime);
                test_value12.setText("" + mApplicableTestRoom.getTestSpecification());
            } else if (TestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                test_value1.setText("" + equipment.getEquipmentName());
                test_value4.setText("" + equipment.getEquipmentNo());
                test_value6.setText("" + mApplicableTestEquipment.getOccupencyState());

                String samplingTime = getSamplingTime(mApplicableTestEquipment.getTestSpecification(),"");
                test_value11.setText(""+samplingTime);
                test_value12.setText("" + mApplicableTestEquipment.getTestSpecification());
            }
            datePicker();
        } else if (TestCreateActivity.RCT.equalsIgnoreCase(testType)) {
            if (TestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                test_header1.setText("Equipment Name :");
                test_header4.setText("Eqipment No:");
            } else {
                test_header1.setText("Room Name :");
                test_header4.setText("Room No:");
            }
            test_header2.setText("Instrument Used :");
            test_header3.setText("Test Conducted By:");
            test_header5.setText("Instrument Sr. No:");
            test_header6.setText("Occupancy State :");
            test_header7.setText("Date of Test :");
            test_header9.setText("Due :");
            test_header8.setText("Calibrated on :");
            test_header10.setText("Sampling Flow Rate :");
            test_header11.setText("Sampling Time :");
            if (loginUserType.equals("CLIENT")) {
                test_value2.setText(clientInstrument.getcInstrumentName());
                test_value5.setText("" + clientInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+clientInstrument.getLastCalibrated()));
                test_value9.setText("" + parseDateToddMMyyyy(""+clientInstrument.getCalibrationDueDate()));
            } else {
                test_value2.setText(partnerInstrument.getpInstrumentName());
                test_value5.setText("" + partnerInstrument.getSerialNo());
                test_value8.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getLastCalibrationDate()));
                test_value9.setText("" + parseDateToddMMyyyy(""+partnerInstrument.getCalibrationDueDate()));
            }
            test_value3.setText(userName);
            test_value11.setText("1 Minute");
            if (TestBasedOn.equalsIgnoreCase("ROOM")) {
                test_header12.setText("Cleanroom Class :");
                test_value1.setText("" + room.getRoomName());
                test_value4.setText("" + room.getRoomNo());
                test_value6.setText("" + mApplicableTestRoom.getOccupencyState());
            } else if (TestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                test_header12.setText("Cleanroom Class :");
                test_value1.setText("" + equipment.getEquipmentName());
                test_value4.setText("" + equipment.getEquipmentNo());
                test_value6.setText("" + mApplicableTestEquipment.getOccupencyState());
            }
            datePicker();
        }
    }

    private String getSamplingTime(String testSpecification, String range) {
        String samplingTime = "";
        if (testSpecification.contains("5") || testSpecification.contains("6")) {
            if (range.contains("28.3")) {
                samplingTime = "36 Minute";
            }else if(range.contains("50")){
                samplingTime = "20 Minute";
            }else if(range.contains("75")){
                samplingTime = "14 Minute";
            }else if(range.contains("100")){
                samplingTime = "10 Minute";
            }
        } else {
            samplingTime = "1 Minute";
        }
        return samplingTime;
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
//        certificateNo.setText("V5/" + mon + "/" + year + "/" + formattedDate);

        // Show current date
        String date = new StringBuilder().append(day).append("-").append(month + 1).append("-").append(year).append(" ").toString();
        // Month is 0 based, just add 1
        test_value7.setText("" + date);
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
            test_value7.setText("" + date);
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //removeView();

        //setting the test 2 room volume
        if (roomVolumeTxtList != null && roomVolumeTxtList.size() > 0)
            if (TestCreateActivity.ACPHH.equals(testType)) {
                roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("" + room.getVolume());
            } else if (TestCreateActivity.ACPHAV.equals(testType)) {
                if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                    roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("" + roomDetails[4]);
                } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                    roomVolumeTxtList.get((int) (roomVolumeTxtList.size() / 2)).setText("" + room.getVolume());
                }
            }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//
//    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == verify_btn) {
            Intent intent = null;
            if(validateEditTextList()){
                Toast.makeText(DynamicTableActivity.this, "All Edit fields are mandatory.", Toast.LENGTH_SHORT).show();
            }else{
                if (TestCreateActivity.AV.equalsIgnoreCase(testType)) {
                    intent = new Intent(DynamicTableActivity.this, RDAV5UserEntryActivity.class);
                    // put bundel data
                    intent.putExtra("USERTYPE", loginUserType);
                    if (loginUserType.equals("CLIENT")) {
                        intent.putExtra("ClientInstrument", clientInstrument);
                    } else {
                        intent.putExtra("PartnerInstrument", partnerInstrument);
                    }
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("PRTNERNAME", mPartnerName);
                    intent.putExtra("WITNESSFIRST", witnessFirst);
                    intent.putExtra("WITNESSSECOND", witnessSecond);
                    intent.putExtra("WITNESSTHIRD", witnessThird);
                    //get area based on room area id
                    intent.putExtra("AREANAME", areaName);

                    intent.putExtra("testType", testType);
                    intent.putExtra("testCode", mTestCode);
                    intent.putExtra("testBasedOn", mTestBasedOn);
                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        //get room name,roomNo,and area id
                        intent.putExtra("RoomDetails", roomDetails);
                        intent.putExtra("Equipment", equipment);

                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            intent.putExtra("GrilFilterType", "Grill");
                            intent.putExtra("GRILLLIST", mEquipmentGrillArrayList);
                            if (null != mEquipmentGrillArrayList && mEquipmentGrillArrayList.size() > 0) {
//                            rowSize = mEquipmentGrillArrayList.size() + 1;
//                            intent.putExtra("rows", rowSize);
                                intent.putExtra("rows", mEquipmentGrillArrayList.size() + 1);
                            }

                        } else {
                            intent.putExtra("GrilFilterType", "Filter");
                            intent.putExtra("GRILLLIST", mEquipmentFilterArrayList);
//                        rowSize = mEquipmentFilterArrayList.size() + 1;
                            intent.putExtra("rows", mEquipmentFilterArrayList.size() + 1);
                        }
//                    ApplicableTestEquipment
                        intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);

                        intent.putExtra("cols", mApplicableTestEquipment.getLocation());
                    }
                    //sending Result Data over Bundle
                    intent.putExtra("ResultData", resultDataHashMap);
                    intent.putExtra("PassFailData", passFailHashMap);
                    //sending Input Data
                    intent.putExtra("InputData", inputDataHashMap);
                    startActivity(intent);
                }
                if (TestCreateActivity.ACPHAV.equalsIgnoreCase(testType)) {
                    intent = new Intent(DynamicTableActivity.this, RDACPHAVUserEntryActivity.class);
                    // put bundel data
                    intent.putExtra("USERTYPE", loginUserType);
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("PRTNERNAME", mPartnerName);
                    intent.putExtra("WITNESSFIRST", witnessFirst);
                    intent.putExtra("WITNESSSECOND", witnessSecond);
                    intent.putExtra("WITNESSTHIRD", witnessThird);
                    intent.putExtra("testType", testType);
                    intent.putExtra("testCode", mTestCode);
                    intent.putExtra("testBasedOn", mTestBasedOn);
                    //get area based on room area id
                    intent.putExtra("AREANAME", areaName);

                    if (loginUserType.equals("CLIENT")) {
                        intent.putExtra("ClientInstrument", clientInstrument);
                    } else {
                        intent.putExtra("PartnerInstrument", partnerInstrument);
                    }
                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        intent.putExtra("AhuNumber", ahuNumber);
                        intent.putExtra("RoomDetails", roomDetails);
                        intent.putExtra("AhuFilter", mAhuFilterArrayList);
                        intent.putExtra("ApplicableTestAhu", mApplicableTestAhu);
                        intent.putExtra("rows", mAhuFilterArrayList.size() + 1);
                        intent.putExtra("cols", mApplicableTestAhu.getLocation());
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        //get room name,roomNo,and area id
                        intent.putExtra("Room", room);
                        intent.putExtra("AhuNumber", ahuNumber);

                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            intent.putExtra("GrilFilterType", "Grill");
                            intent.putExtra("GRILLLIST", grillAndSizeFromGrill);
                            if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                                intent.putExtra("rows", grillAndSizeFromGrill.size() + 1);
                            }
                        } else {
                            intent.putExtra("GrilFilterType", "Filter");
                            intent.putExtra("RoomFilter", mRoomFilterArrayList);
                            intent.putExtra("rows", mRoomFilterArrayList.size() + 1);
                        }
                        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);
                        intent.putExtra("cols", mApplicableTestRoom.getLocation());
                    }

                    //sending Result Data over Bundle
                    intent.putExtra("ResultData", resultDataHashMap);
                    intent.putExtra("ResultData2", resultDataHashMap2);
                    intent.putExtra("totalAirFlowRate", totalAirFlowRate);
                    intent.putExtra("AirChangeValue", AirChangeValue);
                    //sending Input Data
                    intent.putExtra("InputData", inputDataHashMap);
                    startActivity(intent);

                }
                if (TestCreateActivity.ACPHH.equalsIgnoreCase(testType)) {
                    intent = new Intent(DynamicTableActivity.this, RDACPHhUserEntryActivity.class);
                    // put bundel data
                    intent.putExtra("USERTYPE", loginUserType);
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("PRTNERNAME", mPartnerName);
                    intent.putExtra("WITNESSFIRST", witnessFirst);
                    intent.putExtra("WITNESSSECOND", witnessSecond);
                    intent.putExtra("WITNESSTHIRD", witnessThird);
                    intent.putExtra("testType", testType);
                    intent.putExtra("testCode", mTestCode);
                    //get area based on room area id
                    intent.putExtra("AREANAME", areaName);

                    if (loginUserType.equals("CLIENT")) {
                        intent.putExtra("ClientInstrument", clientInstrument);
                    } else {
                        intent.putExtra("PartnerInstrument", partnerInstrument);
                    }
                    if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                        intent.putExtra("GrilFilterType", "Grill");
                        intent.putExtra("GRILLLIST", grillAndSizeFromGrill);
                        if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                            intent.putExtra("rows", grillAndSizeFromGrill.size() + 1);
                        }
                    } else {
                        intent.putExtra("GrilFilterType", "Filter");
                        intent.putExtra("RoomFilter", mRoomFilterArrayList);
                        intent.putExtra("rows", mRoomFilterArrayList.size() + 1);
                    }

                    intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);
                    intent.putExtra("cols", mApplicableTestRoom.getLocation());
                    intent.putExtra("Room", room);
                    intent.putExtra("AhuNumber", ahuNumber);
                    //sending Result Data over Bundle
                    intent.putExtra("totalAirFlowRate", totalAirFlowRate);
                    intent.putExtra("AirChangeValue", AirChangeValue);
                    //sending Input Data
                    intent.putExtra("InputData", inputDataHashMap);

                    startActivity(intent);
                }
                if (TestCreateActivity.FIT.equalsIgnoreCase(testType)) {
                    // adding ConcentrationVariation data
                    if(txtConcentrationVariationList != null && txtConcentrationVariationList.size()>0) {
                        for (int i = 0; i < txtConcentrationVariationList.size(); i++) {
                            try {
                                concentrationVariationListData.add(Double.valueOf
                                        (txtConcentrationVariationList.get(i).
                                                getText().toString().replace(" %", "").trim()));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    intent = new Intent(DynamicTableActivity.this, RDFITUserEntryActivity.class);
                    // put bundel data
                    intent.putExtra("USERTYPE", loginUserType);
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("PRTNERNAME", mPartnerName);
                    intent.putExtra("WITNESSFIRST", witnessFirst);
                    intent.putExtra("WITNESSSECOND", witnessSecond);
                    intent.putExtra("WITNESSTHIRD", witnessThird);
                    intent.putExtra("testBasedOn", mTestBasedOn);
                    intent.putExtra("testType", testType);
                    intent.putExtra("testCode", mTestCode);
                    //get area based on room area id
                    intent.putExtra("AREANAME", areaName);

                    if (loginUserType.equals("CLIENT")) {
                        intent.putExtra("ClientInstrument", clientInstrument);
                    } else {
                        intent.putExtra("PartnerInstrument", partnerInstrument);
                    }

                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        intent.putExtra("RoomDetails", roomDetails);
                        intent.putExtra("Equipment", equipment);
                        intent.putExtra("AREANAME", areaName);
                        intent.putExtra("EquipmentFilter", mEquipmentFilterArrayList);
                        intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);
                        intent.putExtra("rows", mEquipmentFilterArrayList.size() + 1);
                        intent.putExtra("cols", mApplicableTestEquipment.getLocation());
                    } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        intent.putExtra("AhuNumber", ahuNumber);
                        intent.putExtra("testItem", mTestItem);
                        intent.putExtra("RoomDetails", roomDetails);
                        intent.putExtra("AREANAME", areaName);
                        intent.putExtra("AhuFilter", mAhuFilterArrayList);
                        intent.putExtra("ApplicableTestAhu", mApplicableTestAhu);
                        intent.putExtra("rows", mAhuFilterArrayList.size() + 1);
                        intent.putExtra("cols", mApplicableTestAhu.getLocation());
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        intent.putExtra("Room", room);
                        intent.putExtra("AhuNumber", ahuNumber);
                        intent.putExtra("RoomFilterList", mRoomFilterArrayList);
                        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);
                        intent.putExtra("rows", mRoomFilterArrayList.size() + 1);
                        intent.putExtra("cols", mApplicableTestRoom.getLocation());
                    }
                    //sending Result Data over Bundle
                    intent.putExtra("PassFailData", passFailHashMap);
                    //sending Input Data
                    intent.putExtra("InputData", rdFitInputDataHashMap);
                    //sending ConcentrationVariation data
                    intent.putExtra("InputDataVariation", concentrationVariationListData);
                    //TO Do testspesification will be shown from room filter spesification
                    // location will be the size off rommfilter list
                    startActivity(intent);
                }
                if (TestCreateActivity.PCT.equalsIgnoreCase(testType)) {
                    intent = new Intent(DynamicTableActivity.this, RDPCTUserEntryActivity.class);
                    // put bundel data
                    intent.putExtra("USERTYPE", loginUserType);
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("PRTNERNAME", mPartnerName);
                    intent.putExtra("WITNESSFIRST", witnessFirst);
                    intent.putExtra("WITNESSSECOND", witnessSecond);
                    intent.putExtra("WITNESSTHIRD", witnessThird);
                    intent.putExtra("testType", testType);
                    intent.putExtra("testCode", mTestCode);
                    intent.putExtra("testBasedOn", mTestBasedOn);
                    //get area based on room area id
                    intent.putExtra("AREANAME", areaName);

                    if (loginUserType.equals("CLIENT")) {
                        intent.putExtra("ClientInstrument", clientInstrument);
                    } else {
                        intent.putExtra("PartnerInstrument", partnerInstrument);
                    }

                    if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                        intent.putExtra("RoomDetails", roomDetails);
                        intent.putExtra("Equipment", equipment);
                        intent.putExtra("AREANAME", areaName);
//                    intent.putExtra("EquipmentFilter", mEquipmentFilterArrayList);
                        intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);
                        intent.putExtra("rows", mApplicableTestEquipment.getLocation() + 1);
                        intent.putExtra("cols", mApplicableTestEquipment.getNoOfCycle());
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        intent.putExtra("Room", room);
                        intent.putExtra("AhuNumber", ahuNumber);
//                    intent.putExtra("RoomFilterList", mRoomFilterArrayList);
                        intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);
                        intent.putExtra("rows", mApplicableTestRoom.getLocation() + 1);
                        intent.putExtra("cols", mApplicableTestRoom.getNoOfCycle());
                    }
                    //sending Input Data
                    intent.putExtra("InputData", inputDataHashMap);
                    //sending Result Data over Bundle
                    intent.putExtra("ResultData", resultDataHashMap);
                    intent.putExtra("meanValue1", meanValue1);
                    intent.putExtra("meanValue2", meanValue2);
                    intent.putExtra("stdDev1", stdDev1);
                    intent.putExtra("stdDev2", stdDev2);
                    long ucl1 = getUCLValues(meanValue1,rows, stdDev1);
                    long ucl2 = getUCLValues(meanValue2,rows, stdDev2);
                    intent.putExtra("UCL_V1", ucl1);
                    intent.putExtra("UCL_V2", ucl2);

                    startActivity(intent);
                }
                if (TestCreateActivity.RCT.equalsIgnoreCase(testType)) {

                    String finalvalue = finalReadingValueTv.getText().toString();
                    if (!"".equalsIgnoreCase(finalvalue) && null != finalvalue && !finalvalue.isEmpty() && null != inputDataHashMap && null != inputDataHashMap.get(200)) {
                        if (Double.parseDouble(finalvalue) > inputDataHashMap.get(200)) {
                            aleartDialog("Final reading should be less than or equal to initial value");
                        }else {
                            intent = new Intent(DynamicTableActivity.this, RDRCTUserEntryActivity.class);
                            // put bundel data
                            intent.putExtra("USERTYPE", loginUserType);
                            intent.putExtra("USERNAME", userName);
                            intent.putExtra("PRTNERNAME", mPartnerName);
                            intent.putExtra("WITNESSFIRST", witnessFirst);
                            intent.putExtra("WITNESSSECOND", witnessSecond);
                            intent.putExtra("WITNESSTHIRD", witnessThird);
                            intent.putExtra("testType", testType);
                            intent.putExtra("testCode", mTestCode);
                            intent.putExtra("testBasedOn", mTestBasedOn);
                            //get area based on room area id
                            intent.putExtra("AREANAME", areaName);

                            if (loginUserType.equals("CLIENT")) {
                                intent.putExtra("ClientInstrument", clientInstrument);
                            } else {
                                intent.putExtra("PartnerInstrument", partnerInstrument);
                            }

                            if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
                                intent.putExtra("RoomDetails", roomDetails);
                                intent.putExtra("Equipment", equipment);
                                intent.putExtra("AREANAME", areaName);
//                    intent.putExtra("EquipmentFilter", mEquipmentFilterArrayList);
                                intent.putExtra("ApplicableTestEquipment", mApplicableTestEquipment);
//                        intent.putExtra("rows", mEquipmentFilterArrayList.size() + 1);
//                        intent.putExtra("cols", mApplicableTestEquipment.getLocation());
                            } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                                intent.putExtra("Room", room);
                                intent.putExtra("AhuNumber", ahuNumber);
//                    intent.putExtra("RoomFilterList", mRoomFilterArrayList);
                                intent.putExtra("ApplicableTestRoom", mApplicableTestRoom);
//                        intent.putExtra("rows", mRoomFilterArrayList.size() + 1);
//                        intent.putExtra("cols", mApplicableTestRoom.getLocation());
                            }
                            intent.putExtra("InitialReading", "" + inputDataHashMap.get(200));
                            intent.putExtra("WorstCaseReading", "" + inputDataHashMap.get(201));
                            intent.putExtra("FinalReading", "" + finalReadingValueTv.getText().toString());
                            intent.putExtra("RecoveryTime", rowsCount);
                            intent.putExtra("InputData", inputDataHashMap);
                            intent.putExtra("VALUE", testReadingEditTextList);
//                intent.putExtra("rows", grillAndSizeFromGrill.size() + 1);
//                intent.putExtra("cols", applicableTestRoomLocation);
                            startActivity(intent);
                        }
                    }
                }
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
                        rdFitInputDataHashMap.put(editTextList.get(i).getId(), 0.0);
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
            if (TestCreateActivity.RCT.equalsIgnoreCase(testType)) {
                finalReadingValueTv.setText("");
            }
            //Done clicked...hahaa..
            isClearClicked = false;
        }

        if (view == addRow_Btn) {
            addRowButtonClick(rowsCount);
            rowsCount++;
        }
        if (view == deleteRow_Btn) {
            if (rowsCount > 0) {
                deleteRowButtonClick(rowsCount);
                rowsCount--;
            } else {
                Toast.makeText(DynamicTableActivity.this, "Please Add Row First", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private boolean validateEditTextList() {
        validate.clear();
        Log.d(TAG, "Validate editTextList "+editTextList.size()+" validate size "+validate.size());
        for (int i = 0; i < editTextList.size(); i++) {
            Log.d(TAG, "Validate "+editTextList.get(i).getText().toString());
            if(editTextList.get(i).getText().toString().trim() == null
                    && editTextList.get(i).getText().toString().trim().equals("")){
                validate.add(i,true);
            }
            else{
                validate.add(i,false);
            }
        }
        Log.d(TAG, "validate.contains "+validate.contains(true));
        if(validate.contains(true)){
            return true;
        }else{
            return false;
        }
    }

    private void BuildTableTest6(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    TextView tv = addTextView("");
                    tv.setEms(10);
                    row.addView(tv);
                }
                if (i == 2 && j == 1) {
                    TextView tv = addTextView(" Initial Reading");
                    tv.setEms(10);
                    row.addView(tv);
                }
                if (i == 3 && j == 1) {
                    TextView tv = addTextView(" Worst case");
                    tv.setEms(10);
                    row.addView(tv);
                }

            }
            test6A_table_layout.addView(row);
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
                    TextView tv = addTextView(" 0.5 µm/m³ ");
                    tv.setEms(10);
                    row.addView(tv);
                } else if (i > 1 && i < 4) {
                    if (i == 2) {
                        EditText etv = addEditTextView(i);
                        etv.setFocusable(true);
                        etv.requestFocus();
                        row.addView(etv);
                    }else{
                        row.addView(addEditTextView(i));
                    }
                }
            }
            test6A_table_layout2.addView(row);
        }
        //dismiss progressbar
        if (pr.isShowing())
            pr.dismiss();


    }

    private void addRowButtonClick(int rows) {
        TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        row1.setTag(rows);
        TextView tv = addTextView(rows + 1 + "");
        tv.setEms(10);
        row1.addView(tv);
        test6A_table_layout3.addView(row1, rows);

        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        EditText et = addEditTextView(rows);
        et.setEms(10);
        row2.addView(et);
        testReadingEditTextList.add(et.getText().toString());
        test6A_table_layout4.addView(row2, rows);
        int totalReading = rows + 2;
        finalReadingTv.setText("Final Reading" + totalReading);
    }


    private void deleteRowButtonClick(int rows) {
        test6A_table_layout3.removeViewAt((rows - 1));
        test6A_table_layout4.removeViewAt((rows - 1));
        Log.d(TAG,"Delete Before editTextList size "+editTextList.size());
        editTextList.remove(editTextList.size()-1);
        validate.remove(validate.size()-1);
        Log.d(TAG,"Delete After editTextList size "+editTextList.size());
        int totalReading = rows - 1;
        finalReadingTv.setText("Final Reading" + totalReading);
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

                    TextView textView = addTextView(" Location ");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    int position = i - 1;
                    row.addView(addTextView(" " + position));
                }

            }
            test5_table_layout.addView(row);
        }
/*        for (int sk = 0; sk < 3; sk++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            row.addView(addTextView("    "));
            test5_table_layout.addView(row);
        }*/

// adding no of partical text
//        if(i==1) {
        TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        TextView tvs = addTextView(" No. of Particles >= 5 µm/m³  ");
        tvs.setEms(12);
        row1.addView(tvs);
        test5_tableLayout2_2.addView(row1);
//        test5_table_layout2_1.addView(row1);
//        }

        //Second section
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
                    TextView textView = addTextView(" Average ");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
                    textView.setLayoutParams(params);
                    row.addView(textView);
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


        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        TextView tvs1 = addTextView(" No. of Particles >= 5 µm/m³  ");
        tvs1.setEms(12);
        row2.addView(tvs1);
        test5_tableLayout4_2.addView(row2);


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
        for (int i = 1; i <= rows; i++) {
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
                    row.addView(addTextView("Average\nbefore Scanning(%) "));
                } else {
                    row.addView(addEditTextViewbeforestream(i));
                    //row.addView(addEditTextView(i));
                }

            }
            test4_table_layout2.addView(row);
        }

        //Third section section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("Average\nAfter Scanning(%)"));
                } else {
                    row.addView(addEditTextViewAfterStream(i));
                    //row.addView(addEditTextView(i));
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
                    row.addView(addTextView(" Variation \nin Concentration*"));
                } else {
                    row.addView(addFITConcentrationVariationTextView("0 %"));
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
                    row.addView(addTextView(" Obtained Leakage \n(% Leakage)"));
                } else {
                    //row.addView(addTextView(3+i+""));
                    row.addView(addEditTextView(i));
                }

            }
            test4_table_layout5.addView(row);
        }
        //Eighth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Test Status\n    "));
                } else {
                    //row.addView(addTextView(" Pass "));
                    row.addView(addTextPassFail("", i));
                }

            }
            test4_table_layout8.addView(row);
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

                    if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                        if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                            row.addView(addTextView(grillAndSizeFromGrill.get(i - 2).getGrillCode().toString()));
                        } else {
                            row.addView(addTextView("grillAndSizeFromGrill"));
                        }

                    } else {
                        if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
//                            HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                            row.addView(addTextView(mRoomFilterArrayList.get(i - 2).getFilterCode()));
                        } else {
                            row.addView(addTextView("grillAndSizeFromGrill"));
                        }

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
                    row.addView(addTextView("Measured Airflow Qty\n(in cfm) "));
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
                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        if (null != mAhuFilterArrayList && mAhuFilterArrayList.size() > 0) {
                            row.addView(addTextView(mAhuFilterArrayList.get(i - 2).getFilterCode()));
                        } else {
                            row.addView(addTextView("grillAndSizeFromGrill"));
                        }
                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
                                row.addView(addTextView(grillAndSizeFromGrill.get(i - 2).getGrillCode().toString()));
                            } else {
                                row.addView(addTextView("grillAndSizeFromGrill"));
                            }

                        } else {
                            if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
//                            HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                                row.addView(addTextView(mRoomFilterArrayList.get(i - 2).getFilterCode()));
                            } else {
                                row.addView(addTextView("grillAndSizeFromGrill"));
                            }

                        }

//                        //////////////
//                        HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
////                        float filterSize = 0.0f;
////                        if (!grill.isEmpty())
////                            filterSize = Float.parseFloat(grill.get(ValdocDatabaseHandler.GRILL_EFFECTIVEAREA).toString());
//                        if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
//                            row.addView(addTextView(mRoomFilterArrayList.get(i - 2).getFilterCode()));
//                        } else {
//                            row.addView(addTextView("grillAndSizeFromGrill"));
//                        }

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
                    row.addView(addTextView(" Grill/Filter\n Area"));
                } else {

                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                        double filterSize = 0.0f;
                        if (!mAhuFilterArrayList.isEmpty())
                            filterSize = mAhuFilterArrayList.get(i - 2).getEffectiveArea();
                        row.addView(addTextView("" + filterSize));

                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
                        if (mGrilFilterType.equalsIgnoreCase("Grill")) {
                            if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
//                                String grill = grillAndSizeFromGrill.get(i - 2)[1].toString();
                                float filterSize = 0.0f;
//                                if (!grill.isEmpty())
                                filterSize = (float) (grillAndSizeFromGrill.get(i - 2).getEffectiveArea());
                                row.addView(addTextViewWithTagIds(i, filterSizeIds, filterSizeTxtViewList, filterSize + ""));
                            }

                        } else {
                            double filterSize = 0.0f;
                            if (!mRoomFilterArrayList.isEmpty())
                                filterSize = mRoomFilterArrayList.get(i - 2).getEffectiveFilterArea();
                            row.addView(addTextViewWithTagIds(i, filterSizeIds, filterSizeTxtViewList, filterSize + ""));
//                        row.addView(addTextView("" + filterSize));

                        }

                    }
                    // row.addView(addTextView("1.9"));
//                    if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
//                        HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
//                        float filterSize = 0.0f;
//                        if (!grill.isEmpty())
//                            filterSize = Float.parseFloat(grill.get(ValdocDatabaseHandler.GRILL_EFFECTIVEAREA).toString());
//                        row.addView(addTextViewWithTagIds(i, filterSizeIds, filterSizeTxtViewList, filterSize + ""));
//                    }

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
                    row.addView(addTextView(" Air Flow Rate\n in cfm(AxAV)"));
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
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Average(V) "));
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
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
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
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        txtConcentrationVariationList.add(tv);
        return tv;
    }

    int idPassFailTv = 300;

    private TextView addTextPassFail(String textValue, int tagRows) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        //tv.setPadding(5, 5, 5, 5);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
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
        tv.setGravity(Gravity.CENTER);
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
        tv.setGravity(Gravity.CENTER);
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
        tv.setGravity(Gravity.CENTER);
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
        tv.setGravity(Gravity.CENTER);
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
        tv.setPadding(5, 6, 5, 6);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
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

    //Fit up stream after
    private EditText addEditTextViewAfterStream(int rowNo) {
        EditText editTv = new EditText(this);
        editTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        editTv.setBackgroundResource(R.drawable.border);
        editTv.setPadding(8, 6, 8, 6);
        editTv.setTextColor(getResources().getColor(R.color.black));
        editTv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        editTv.setGravity(Gravity.CENTER);
        //editTv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        editTv.setEms(4);
        editTv.setSingleLine(true);
        editTv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, "EditText idCountFitAftStrm " + idCountFitAftStrm);
        editTv.setId(idCountFitAftStrm);
        editTv.setTag(rowNo);
        editTv.addTextChangedListener((new TextValidator(
                DynamicTableActivity.this, idCountFitAftStrm)));
        editTextList.add(editTv);
        testReadingEditTextList.add(editTv.getText().toString());
        idCountFitAftStrm++;
        return editTv;
    }

    //Fit upstream before
    private EditText addEditTextViewbeforestream(int rowNo) {
        EditText editTv = new EditText(this);
        editTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        editTv.setBackgroundResource(R.drawable.border);
        editTv.setPadding(8, 6, 8, 6);
        editTv.setTextColor(getResources().getColor(R.color.black));
        editTv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        editTv.setGravity(Gravity.CENTER);
        //editTv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        editTv.setEms(4);
        editTv.setSingleLine(true);
        editTv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, "EditText idCountFitStrm " + idCountFitStrm);
        editTv.setId(idCountFitStrm);
        editTv.setTag(rowNo);
        editTv.addTextChangedListener((new TextValidator(
                DynamicTableActivity.this, idCountFitStrm)));
        editTextList.add(editTv);
        testReadingEditTextList.add(editTv.getText().toString());
        idCountFitStrm++;
        return editTv;
    }

    //Fit Likage
    private EditText addEditTextView(int rowNo) {
        EditText editTv = new EditText(this);
        editTv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        editTv.setBackgroundResource(R.drawable.border);
        editTv.setPadding(8, 6, 8, 6);
        editTv.setTextColor(getResources().getColor(R.color.black));
        editTv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        editTv.setGravity(Gravity.CENTER);
        //editTv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        editTv.setEms(4);
        editTv.setSingleLine(true);
        editTv.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editTv.setEllipsize(TextUtils.TruncateAt.END);
        Log.d(TAG, "EditText idCountEtv " + idCountEtv);
        editTv.setId(idCountEtv);
        editTv.setTag(rowNo);
        editTv.addTextChangedListener((new TextValidator(
                DynamicTableActivity.this, idCountEtv)));
        editTextList.add(editTv);
        testReadingEditTextList.add(editTv.getText().toString());
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
                    if (TestCreateActivity.FIT.equals(testType)) {
                        fitInputValue = fitInputValue - Double.parseDouble(charSequence.toString());
                    } else {
                        inputValue = inputValue - Integer.parseInt(charSequence.toString());
                    }
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
                    if (TestCreateActivity.FIT.equals(testType)) {
                        fitInputValue = Double.parseDouble(editable.toString());
                    } else {
                        inputValue = Integer.parseInt(editable.toString());
                    }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            Log.d(TAG, " Added inputValue " + inputValue);

            //Add EditText value in ArrayList with position
            for (int i = 0; i < editTextList.size(); i++) {
                if (editTextList.get(i).getId() == viewById) {
                    rowTagHashMap.put(editTextList.get(i).getId(), (int) editTextList.get(i).getTag());
                    if (TestCreateActivity.FIT.equals(testType)) {
                        rdFitInputDataHashMap.put(editTextList.get(i).getId(), fitInputValue);
                    } else {
                        inputDataHashMap.put(editTextList.get(i).getId(), inputValue);
                    }
                    tagF = (int) editTextList.get(i).getTag();
                }
            }

            //Calculation Test 1 Specific
            if (TestCreateActivity.AV.equals(testType)) {
                //TextView set Value;
                for (int i = 0; i < txtViewList.size(); i++) {
                    Log.d(TAG, " TextView : Tag " + txtViewList.get(i).getTag() + " tagF " + tagF);
                    if (txtViewList.get(i).getTag().equals(tagF)) {
                        TextView tvl = txtViewList.get(i);
                        tvl.setText(getRoundedAverageValue(tagF) + "");
                        resultDataHashMap.put(tvl.getId(), getRoundedAverageValue(tagF));

                        // Pass Fail Calculation
                        TextView txtPassFail = txtPassFailList.get(i);
                        if (tvl.getTag() == txtPassFail.getTag()) {
                            double minvalue = equipment.getMinVelocity();
                            double maxValue = equipment.getMaxVelocity();
//                            double percentValue = ((valocity * 20) / 100);
                            if (minvalue <= getRoundedAverageValue(tagF) && getRoundedAverageValue(tagF) <= maxValue) {
                                txtPassFail.setTextColor(getResources().getColor(R.color.blue));
                                txtPassFail.setText(" PASS ");
                                passFailHashMap.put(txtPassFail.getId(), " PASS ");
                            } else {
                                txtPassFail.setTextColor(getResources().getColor(R.color.red));
                                txtPassFail.setText(" FAIL ");
                                passFailHashMap.put(txtPassFail.getId(), " FAIL ");
                            }
                        }

                    }

                }
            }
            //calculation Test 2 specific
            if (TestCreateActivity.ACPHAV.equals(testType)) {
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
                    Log.d(TAG, " SumofAirVelocity : " + totalAirFlowRate);

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
            if (TestCreateActivity.ACPHH.equals(testType)) {

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
                        airChangeTxt.setText(AirChangeValue + "");
                        Log.d(TAG, " AirChangeValue : " + AirChangeValue);
                    }
                }
            }
            //Calculation Test 4 Specific
            if (TestCreateActivity.FIT.equals(testType)) {
                Log.d(TAG, " FIT viewById = " + viewById);
                double slpDlpValue = 0.0;
                if (viewById >= 900) {
                    for (int i = 0; i < editTextList.size(); i++) {
                        if (editTextList.get(i).getId() == (800 + (viewById - 900))) {
                            // null check
                            if (rdFitInputDataHashMap.get((800 + (viewById - 900))) != null
                                    && rdFitInputDataHashMap.get(viewById) != null) {
                                slpDlpValue = fitDiffPercent(rdFitInputDataHashMap.get((800 + (viewById - 900))),
                                        rdFitInputDataHashMap.get(viewById));
                            }
                            // Pass Fail Calculation
                            TextView txtPassFail = txtPassFailList.get(i);
                            Log.d(TAG, "slpDlpValue=" + slpDlpValue);
                            if (slpDlpValue < 15) {
                                txtPassFail.setTextColor(getResources().getColor(R.color.blue));
                                txtPassFail.setText(" PASS ");
                                passFailHashMap.put(txtPassFail.getId(), " PASS ");
                            } else if (slpDlpValue > 15) {
                                txtPassFail.setTextColor(getResources().getColor(R.color.red));
                                txtPassFail.setText(" FAIL ");
                                passFailHashMap.put(txtPassFail.getId(), " FAIL ");
                            }
                            // adding concentration Variation Data
                            txtConcentrationVariationList.get(i).setText(slpDlpValue+" %");

                        }
                    }
                }
            }

            //Calculation Test 5 Specific
            if (TestCreateActivity.PCT.equals(testType)) {
                //TextView set Value;
                for (int i = 0; i < txtViewList.size(); i++) {
                    Log.d(TAG, " TextView : Tag " + txtViewList.get(i).getTag() + " tagF " + tagF);
                    if (txtViewList.get(i).getTag().equals(tagF)) {
                        long roundedAvg = getRoundedAverageValue(tagF);
                        TextView tvl = txtViewList.get(i);
                        tvl.setText(roundedAvg+ "");

                        resultDataHashMap.put(tvl.getId(),roundedAvg);
                        //setMean(getRoundedAverageValue(tagF));
                        Log.d(TAG, "TagF : " + tagF + " rows : " + rows);
                        if (tagF <= rows) {
                            meanValue1 = getMeanAverageValue(resultDataHashMap, tagF);
                            TextView txtView = RDPC3TxtList.get(0);
                            txtView.setText(meanValue1 + "");

                           stdDev1 = getStdDev(resultDataHashMap, inputValue, tagF);
                           TextView txtView2 = RDPC3TxtList.get(1);
                            txtView2.setText(stdDev1 + "");
                        } else {
                            meanValue2 = getMeanAverageValue(resultDataHashMap, tagF);
                            TextView txtView = RDPC3TxtList2.get(0);
                            txtView.setText(meanValue2 + "");

                           stdDev2 = getStdDev(resultDataHashMap, inputValue,tagF);
                           TextView txtView2 = RDPC3TxtList2.get(1);
                            txtView2.setText(stdDev2 + "");
                        }
                    }
                }
            }
//            if (TestCreateActivity.RCT.equalsIgnoreCase(testType)) {
//                String finalvalue = finalReadingValueTv.getText().toString();
//                if (finalvalue.length() > 0 && null != inputDataHashMap && null != inputDataHashMap.get(200)) {
//                    if (Double.parseDouble(finalvalue) > inputDataHashMap.get(200)) {
////                        finalReadingValueTv.setText("");
//                        Toast.makeText(DynamicTableActivity.this, "Final reading should be less than or equal to initial value", Toast.LENGTH_LONG).show();
//                    }
//                }
//            }

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
                if ((int) m.getKey() <= rows && tagF <= rows) {
                    meanAvg = meanAvg + meanAverageList2.get(m.getKey());
                    if (meanAverageList2.get(m.getKey()) > 0)
                        tagCount = tagCount + 1;
                } else if ((int) m.getKey() >= rows && tagF > rows) {
                    meanAvg = meanAvg + meanAverageList2.get(m.getKey());
                    if (meanAverageList2.get(m.getKey()) > 0)
                        tagCount = tagCount + 1;
                }
                System.out.println(" Mean Avg Add " + meanAvg);
                System.out.println(" Mean Avg Key " + m.getKey() + " Mean Avg Value " + m.getValue());
            }
        }
        Log.d(TAG, "Mean Avg: Method sum: " + meanAvg + " count : " + tagCount);
        double abc = (double) meanAvg / (double) tagCount;
        Log.d(TAG, "Float Value :" + abc);
        meanAvg = Math.round(abc);
        Log.d(TAG, "Avg: Method Avg: " + meanAvg);

        return meanAvg;
    }

    private double getVariance(HashMap<Integer, Long> meanAverageList2, int inputValue, int tagF) {
        double temp = 0;
        int tagCount = 0;
        for (Map.Entry m : meanAverageList2.entrySet()) {
            if (meanAverageList2.get(m.getKey()) != null && !"".equals(meanAverageList2.get(m.getKey()))) {
                if ((int) m.getKey() <= rows && tagF <= rows) {
                    temp += (meanAverageList2.get(m.getKey()) - inputValue) * (meanAverageList2.get(m.getKey()) - inputValue);
                    if (meanAverageList2.get(m.getKey()) > 0)
                        tagCount = tagCount + 1;
                } else if ((int) m.getKey() >= rows && tagF > rows) {
                    temp += (meanAverageList2.get(m.getKey()) - inputValue) * (meanAverageList2.get(m.getKey()) - inputValue);
                    if (meanAverageList2.get(m.getKey()) > 0)
                        tagCount = tagCount + 1;
                }
                System.out.println(" Variance temp " + temp);
                System.out.println(" Variance Key " + m.getKey() + " Variance Value " + m.getValue());
            }
        }
        double resultVariance = 0;
        if (tagCount > 1) {
            resultVariance = temp / (double) (tagCount - 1);
        } else {
            resultVariance = temp / tagCount;
        }

        System.out.println(" Variance Result " + resultVariance + " Rounded Value " + Math.round(resultVariance));
        return Math.round(resultVariance);
    }

    private double getStdDev(HashMap<Integer, Long> meanAverageList2, int inputValue, int tagF) {
        double stdDev = Math.sqrt(getVariance(meanAverageList2,inputValue, tagF));
        System.out.println(" getStdDev Result " + stdDev + " rounded StdValue " + Math.round(stdDev));
        return Math.round(stdDev);
    }

    //Test 4 Specific
    private double getLeakageValue(int count) {
        double leakageValue = 0.0;
        if (!isClearClicked) {
            try {
                for (Map.Entry m : rowTagHashMap.entrySet()) {
                    if (m.getValue().equals(count)) {
                        if (rdFitInputDataHashMap.get(m.getKey()) != null &&
                                !"".equals(rdFitInputDataHashMap.get(m.getKey())) && ((int) (m.getKey())) < 800) {
                            leakageValue = rdFitInputDataHashMap.get(m.getKey());
                        }
                    }
                    System.out.println("inputDataHashMap.get(m.getKey())" + "count=" + count + " m.getKey()= " + m.getKey() + "=" + rdFitInputDataHashMap.get(m.getKey()).doubleValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Leakage Value : " + leakageValue);
        return leakageValue;
    }
    private double fitDiffPercent(double beforeScaning,double afterScaning) {
        Log.d(TAG,"Saurabh beforeScaning "+beforeScaning +" afterScaning "+afterScaning);
        double diffPercent = 0.00;
        DecimalFormat df2 = new DecimalFormat(".##");
        if(beforeScaning>0 && afterScaning>0){
            double diff = beforeScaning - afterScaning;
            diffPercent = (diff * 100) / beforeScaning;
            try {
                diffPercent = Double.valueOf(df2.format(diffPercent));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                diffPercent = 0.00;
            }
        }
        return diffPercent;
    }

    private long getUCLValues(long meanAverage, int noOfLocation, double stdDeviation){
        long ucl = 0;
        double multiplier = 1;
        Log.d(TAG, "meanAverage: " + meanAverage+" noOfLocation: "+noOfLocation+" stdDeviation: "+stdDeviation);
        if(noOfLocation == 2)
            multiplier = 6.3;
        else if(noOfLocation == 3)
            multiplier = 2.9;
        else if(noOfLocation == 4)
            multiplier = 2.4;
        else if(noOfLocation == 5)
            multiplier = 2.1;
        else if(noOfLocation == 6)
            multiplier = 2;
        else if(noOfLocation == 7)
            multiplier = 1.9;
        else if(noOfLocation == 8)
            multiplier = 1.9;
        else if(noOfLocation == 9)
            multiplier = 1.9;

        ucl = Math.round(meanAverage + (multiplier * (stdDeviation/Math.sqrt(noOfLocation))));
        Log.d(TAG, " UCL : " + ucl);
        return ucl;
    }

    private void initRes() {
        clear = (ImageView) findViewById(R.id.clear);
        clear.setOnClickListener(this);
        verify_btn = (ImageView) findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(this);
        addRow_Btn = (ImageView) findViewById(R.id.add_row_btn);
        addRow_Btn.setOnClickListener(this);
        deleteRow_Btn = (ImageView) findViewById(R.id.delete_row_btn);
        deleteRow_Btn.setOnClickListener(this);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                verify_btn.setEnabled(true);
                clear.setEnabled(true);
            }
        });

        sampling_flow_rate = (TableRow) findViewById(R.id.sampling_flow_rate);
        sampling_time = (TableRow) findViewById(R.id.sampling_time);
        clean_room_class = (TableRow) findViewById(R.id.clean_room_class);


        //Common Test Header and Value;
        test_header1 = (TextView) findViewById(R.id.common_dynamic_test_header1_tv);
        test_header2 = (TextView) findViewById(R.id.common_dynamic_test_header2_tv);
        test_header3 = (TextView) findViewById(R.id.common_dynamic_test_header3_tv);
        test_header4 = (TextView) findViewById(R.id.common_dynamic_test_header4_tv);
        test_header5 = (TextView) findViewById(R.id.common_dynamic_test_header5_tv);
        test_header6 = (TextView) findViewById(R.id.common_dynamic_test_header6_tv);
        test_header7 = (TextView) findViewById(R.id.common_dynamic_test_header7_tv);
        test_header8 = (TextView) findViewById(R.id.common_dynamic_test_header8_tv);
        test_header9 = (TextView) findViewById(R.id.common_dynamic_test_header9_tv);
        test_header10 = (TextView) findViewById(R.id.common_dynamic_test_header10_tv);
        test_header11 = (TextView) findViewById(R.id.common_dynamic_test_header11_tv);
        test_header12 = (TextView) findViewById(R.id.common_dynamic_test_header12_tv);
        test_value1 = (TextView) findViewById(R.id.common_dynamic_test_value1_tv);
        test_value2 = (TextView) findViewById(R.id.common_dynamic_test_value2tv);
        test_value3 = (TextView) findViewById(R.id.common_dynamic_test_value3_tv);
        test_value4 = (TextView) findViewById(R.id.common_dynamic_test_value4_tv);
        test_value5 = (TextView) findViewById(R.id.common_dynamic_test_value5_tv);
        test_value6 = (TextView) findViewById(R.id.common_dynamic_test_value6_tv);
        test_value7 = (TextView) findViewById(R.id.common_dynamic_test_value7_tv);
//        test_value7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // On button click show datepicker dialog
//                showDialog(DATE_PICKER_ID);
//            }
//        });
        test_value8 = (TextView) findViewById(R.id.common_dynamic_test_value8_tv);
        test_value9 = (TextView) findViewById(R.id.common_dynamic_test_value9_tv);
        test_value10 = (TextView) findViewById(R.id.common_dynamic_test_value10_tv);
        test_value11 = (TextView) findViewById(R.id.common_dynamic_test_value11_tv);
        test_value12 = (TextView) findViewById(R.id.common_dynamic_test_value12_tv);

        //Test 1
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.tableLayout2);
        table_layout3 = (TableLayout) findViewById(R.id.tableLayout3);
        table_layout3.setVisibility(View.GONE);
        table_layout4 = (TableLayout) findViewById(R.id.tableLayout4);
        table_layout4.setVisibility(View.GONE);
        TextView testHeaderAv = (TextView) findViewById(R.id.test_type_header_AVTest);
        if (TestCreateActivity.AV.equalsIgnoreCase(testType)) {
            findViewById(R.id.test1_dynamic_table_ll).setVisibility(View.VISIBLE);
            findViewById(R.id.test1_reading_header).setVisibility(View.VISIBLE);
        }
        //Test 2
        test2_table_layout = (TableLayout) findViewById(R.id.test2_tableLayout1);
        test2_table_layout2 = (TableLayout) findViewById(R.id.test2_tableLayout2);
        test2_table_layout2.setVisibility(View.GONE);
        test2_table_layout3 = (TableLayout) findViewById(R.id.test2_tableLayout3);
        test2_table_layout4 = (TableLayout) findViewById(R.id.test2_tableLayout4);
        test2_table_layout4.setVisibility(View.GONE);
        test2_table_layout5 = (TableLayout) findViewById(R.id.test2_tableLayout5);
        test2_table_layout5.setVisibility(View.GONE);
        test2_table_layout6 = (TableLayout) findViewById(R.id.test2_tableLayout6);
        test2_table_layout6.setVisibility(View.GONE);
        test2_table_layout7 = (TableLayout) findViewById(R.id.test2_tableLayout7);
        test2_table_layout7.setVisibility(View.GONE);
        test2_table_layout8 = (TableLayout) findViewById(R.id.test2_tableLayout8);
        test2_table_layout8.setVisibility(View.GONE);
        if (TestCreateActivity.ACPHAV.equalsIgnoreCase(testType)) {
//            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.test_table2);
//            linearLayout.setVisibility(View.VISIBLE);
            testHeaderAv.setText(R.string.header_title1);
            findViewById(R.id.test2_reading_header).setVisibility(View.VISIBLE);
        }

        //Test3
        test3_table_layout = (TableLayout) findViewById(R.id.test3_tableLayout1);
        test3_table_layout2 = (TableLayout) findViewById(R.id.test3_tableLayout2);
        test3_table_layout3 = (TableLayout) findViewById(R.id.test3_tableLayout3);
        test3_table_layout3.setVisibility(View.GONE);
        test3_table_layout4 = (TableLayout) findViewById(R.id.test3_tableLayout4);
        test3_table_layout4.setVisibility(View.GONE);
        test3_table_layout5 = (TableLayout) findViewById(R.id.test3_tableLayout5);
        test3_table_layout5.setVisibility(View.GONE);
        if (TestCreateActivity.ACPHH.equalsIgnoreCase(testType)) {
            testHeaderAv.setText("FORM: TEST RAW DATA (RD_ACPH_H)\n(Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates)");
            findViewById(R.id.test3_dynamic_table_ll).setVisibility(View.VISIBLE);

        }

        //Test4
        test4_table_layout = (TableLayout) findViewById(R.id.test4_tableLayout1);
        test4_table_layout2 = (TableLayout) findViewById(R.id.test4_tableLayout2);
        test4_table_layout3 = (TableLayout) findViewById(R.id.test4_tableLayout3);
        test4_table_layout4 = (TableLayout) findViewById(R.id.test4_tableLayout4);
        test4_table_layout5 = (TableLayout) findViewById(R.id.test4_tableLayout5);
        test4_table_layout6 = (TableLayout) findViewById(R.id.test4_tableLayout6);
        test4_table_layout7 = (TableLayout) findViewById(R.id.test4_tableLayout7);
        test4_table_layout8 = (TableLayout) findViewById(R.id.test4_tableLayout8);
        test4_table_layout8.setVisibility(View.GONE);
        if (TestCreateActivity.FIT.equalsIgnoreCase(testType)) {
//            findViewById(R.id.test1_dynamic_table_ll).setVisibility(View.GONE);
//            instrumentNo = (TextView) findViewById(R.id.instrument_no4);
//            testerName = (TextView) findViewById(R.id.tester_name_test4);
//            instrumentName = (TextView) findViewById(R.id.instrument_name4);
//            roomName = (TextView) findViewById(R.id.room_name5);
//            testerName.setText(userName);
//            if (loginUserType.equals("CLIENT")) {
//                instrumentName.setText(clientInstrument.getcInstrumentName());
//                instrumentNo.setText("" + clientInstrument.getSerialNo());
//            } else {
//                instrumentName.setText(partnerInstrument.getpInstrumentName());
//                instrumentNo.setText("" + partnerInstrument.getSerialNo());
//            }
//            test_header10.setVisibility(View.GONE);
//            test_header11.setVisibility(View.GONE);
//            test_header12.setVisibility(View.GONE);
            testHeaderAv.setText("FORM:TEST RAW DATA (FIT)\nInstalled HEPA Filter System Leakage Test by Aerosol Photometer Method");
            findViewById(R.id.test4_dynamic_table_ll).setVisibility(View.VISIBLE);
//            if (mTestBasedOn.equalsIgnoreCase("EQUIPMENT")) {
//                roomName.setText("" + roomDetails[1]);
//            } else if (mTestBasedOn.equalsIgnoreCase("AHU")) {
//                roomName.setText("dfd"+roomDetails[1]);
//            } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
//                roomName.setText("" + room.getRoomName());
//            }

        }

        //Test5
        test5_table_layout = (TableLayout) findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout) findViewById(R.id.test5_tableLayout2);
        test5_table_layout2.setVisibility(View.GONE);
        test5_table_layout2_1 = (TableLayout) findViewById(R.id.test5_tableLayout2_1);
        test5_tableLayout2_2 = (TableLayout) findViewById(R.id.test5_tableLayout2_2);
        test5_table_layout3 = (TableLayout) findViewById(R.id.test5_tableLayout3);
        test5_table_layout3.setVisibility(View.GONE);
        test5_table_layout3_1 = (TableLayout) findViewById(R.id.test5_tableLayout3_1);
        test5_table_layout3_1.setVisibility(View.GONE);
        test5_table_layout4 = (TableLayout) findViewById(R.id.test5_tableLayout4);
        test5_table_layout4.setVisibility(View.GONE);
        test5_tableLayout4_2 = (TableLayout) findViewById(R.id.test5_tableLayout4_2);
        test5_table_layout4_1 = (TableLayout) findViewById(R.id.test5_tableLayout4_1);
        test5_table_layout5 = (TableLayout) findViewById(R.id.test5_tableLayout5);
        test5_table_layout5.setVisibility(View.GONE);
        test5_table_layout5_1 = (TableLayout) findViewById(R.id.test5_tableLayout5_1);
        test5_table_layout5_1.setVisibility(View.GONE);
        if (TestCreateActivity.PCT.equalsIgnoreCase(testType)) {
//            findViewById(R.id.test1_dynamic_table_ll).setVisibility(View.GONE);
//            instrumentNo = (TextView) findViewById(R.id.instrument_no5);
//            testerName = (TextView) findViewById(R.id.tester_name_test5);
//            roomName = (TextView) findViewById(R.id.room_name5);
//            instrumentName = (TextView) findViewById(R.id.instrument_name5);
//            roomName.setText("" + room.getRoomName());
//            testerName.setText(userName);
//            if (loginUserType.equals("CLIENT")) {
//                instrumentName.setText(clientInstrument.getcInstrumentName());
//                instrumentNo.setText("" + clientInstrument.getSerialNo());
//            } else {
//                instrumentName.setText(partnerInstrument.getpInstrumentName());
//                instrumentNo.setText("" + partnerInstrument.getSerialNo());
//            }

            testHeaderAv.setText("FORM:TEST RAW DATA (RD_PCT)\nAirborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices");
            findViewById(R.id.test5_dynamic_table_ll).setVisibility(View.VISIBLE);
            sampling_flow_rate.setVisibility(View.VISIBLE);
            sampling_time.setVisibility(View.VISIBLE);
            clean_room_class.setVisibility(View.VISIBLE);
        }

        //Test6
        test6A_table_layout = (TableLayout) findViewById(R.id.test6A_tableLayout1);
        test6A_table_layout2 = (TableLayout) findViewById(R.id.test6A_tableLayout2);
        test6A_table_layout3 = (TableLayout) findViewById(R.id.test6A_tableLayout3);
        test6A_table_layout4 = (TableLayout) findViewById(R.id.test6A_tableLayout4);
        finalReadingTv = (TextView) findViewById(R.id.test6_final_reading_tv);
        finalReadingValueTv = (EditText) findViewById(R.id.test6_final_reading_value_tv);
        finalReadingValueTv.addTextChangedListener((new TextValidator(DynamicTableActivity.this, 0)));
        if (TestCreateActivity.RCT.equalsIgnoreCase(testType)) {
//            findViewById(R.id.test1_dynamic_table_ll).setVisibility(View.GONE);
//            instrumentNo = (TextView) findViewById(R.id.instrument_no6);
//            testerName = (TextView) findViewById(R.id.tester_name_test6);
//            roomName = (TextView) findViewById(R.id.room_name6);
//            instrumentName = (TextView) findViewById(R.id.instrument_name6);
//            roomName.setText("" + room.getRoomName());
//            testerName.setText(userName);
//            if (loginUserType.equals("CLIENT")) {
//                instrumentName.setText(clientInstrument.getcInstrumentName());
//                instrumentNo.setText("" + clientInstrument.getSerialNo());
//            } else {
//                instrumentName.setText(partnerInstrument.getpInstrumentName());
//                instrumentNo.setText("" + partnerInstrument.getSerialNo());
//            }
            sampling_flow_rate.setVisibility(View.VISIBLE);
            sampling_time.setVisibility(View.VISIBLE);
//            clean_room_class.setVisibility(View.VISIBLE);
            findViewById(R.id.test6A_dynamic_table_ll).setVisibility(View.VISIBLE);
            testHeaderAv.setText("FORM :TEST RAW DATA (RD_RCT)\nAirborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices");

        }
    }

    private void removeView() {
        //Test1
        if (TestCreateActivity.AV.equalsIgnoreCase(testType)) {
            table_layout.removeAllViews();
            table_layout2.removeAllViews();
            table_layout3.removeAllViews();
            table_layout4.removeAllViews();
        }

        //Test 2
        if (TestCreateActivity.ACPHAV.equalsIgnoreCase(testType)) {
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
        if (TestCreateActivity.ACPHH.equalsIgnoreCase(testType)) {
            test3_table_layout.removeAllViews();
            test3_table_layout2.removeAllViews();
            test3_table_layout3.removeAllViews();
            test3_table_layout4.removeAllViews();
            test3_table_layout5.removeAllViews();
        }

    }
}
