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
import android.view.View;
import android.view.WindowManager;
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
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RDACPHAVPostViewActivity extends AppCompatActivity {

    private static final String TAG = "RDACPHAV";
    TextView headerText;
    TableLayout test2_table_layout, test2_table_layout2, test2_table_layout3, test2_table_layout4,
            test2_table_layout5, test2_table_layout6, test2_table_layout7, test2_table_layout8;

    int rows, cols;
    String mTestType;
    ProgressDialog pr;
    LinearLayout test_table_1_footer, test_table_1_header_l, test_table_1_header_2;
    //Test 2 Ids variable
    int filterSizeIds = 100, airFlowRateIds = 300,
            totalAirFlowRateIds = 400;
    ArrayList<TextView> airFlowRateTxtViewList;
    ArrayList<TextView> totalAirFlowRateTxtList;
    ArrayList<TextView> roomVolumeTxtList;
    ArrayList<TextView> airChangeTxtList;
    SharedPreferences sharedpreferences;
    // bundel data specification
    private String loginUserType = "";
    private ClientInstrument clientInstrument;
    private PartnerInstrument partnerInstrument;
    private String ahuNumber;
    private String[] roomDetails;
    private Room room;
    private ApplicableTestAhu mApplicableTestAhu = null;
    private ApplicableTestRoom mApplicableTestRoom = null;
    private ArrayList<AhuFilter> mAhuFilterArrayList = null;
    private ArrayList<RoomFilter> mRoomFilterArrayList;
    //    private ArrayList<HashMap<String, String>> grillAndSizeFromGrill;
//    private int applicableTestRoomLocation;
    private String areaName;
    private TextView infarance;
    private String witnessFirst;
    private String witnessSecond;
    private String witnessThird;
    private TextView testWitnessOrg;
    private TextView testCondoctorOrg;
    private String userName = "";
    private int newCertificateNo = 0;
    //certificate view id creation
    private TextView instrumentUsed;
    private TextView TFRTxtv;
    private TextView TFTAVTxtv;
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
    private TextView roomNo;
    private TextView ahuNo;
    private TextView ahuNoText;
    private TextView roomVolume;
    private TextView roomVolumeText;
    private TextView testCundoctor;
    private TextView testWitness;
    private TextView dateTextView;
    private TextView customerName;
    private TextView certificateNo;

    ArrayList<TextView> txtViewList;
    private double totalAirFlowRate = 0;
    private double airChangeValue;
    HashMap<Integer, Long> airFlowRateMap;
    HashMap<Integer, Float> totalAirFlowRateMap;
    private ImageView submit;
    private ImageView clear;
    private ImageView cancel;
    private String mPartnerName;
    private String mTestCode;
    private String mTestBasedOn;
    ArrayList<TextView> resultTextViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDACPHAVPostViewActivity.this);
    int testDetailsId = 0;
    private HashMap<Integer, Integer> userEnterdValue;
    private int testDetailId = 1;
    private ArrayList<TestReading> testReadingArrayList;
    private ArrayList<TestSpesificationValue> testSpesificationValueArrayList;
    private TestDetails mTestDetails;
    String spiltValue[] = null;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rdacphavpost_view);
//    }
//}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdacphavuser_entry);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        pr = ProgressDialog.show(this, "Please Wait", "Loading...");

//        testDetailsId = (sharedpreferences.getInt("TESTDETAILSID", 0) + 1);

        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();
        airFlowRateTxtViewList = new ArrayList<TextView>();
        totalAirFlowRateTxtList = new ArrayList<TextView>();
        roomVolumeTxtList = new ArrayList<TextView>();
        airChangeTxtList = new ArrayList<TextView>();

        //text view initialization
        initTextView();
        mTestType = getIntent().getStringExtra("TestType");
        testDetailId = getIntent().getIntExtra("testDetailId", 1);
        testReadingArrayList = mValdocDatabaseHandler.getTestReadingDataById(testDetailId + "");
        mTestDetails = mValdocDatabaseHandler.getTestDetailById(testDetailId);
        testSpesificationValueArrayList = mValdocDatabaseHandler.getTestSpecificationValueById(testDetailId + "");
        spiltValue = testReadingArrayList.get(0).getValue().split(",");
//        for (int i = 0; i <spiltValue.length-2; i++) {
//            txtViewList.get(textId).setText(""+spiltValue[i]);
//            Log.d(TAG, "CodeFlow : InnerForLoop I: " + i+" textId "+textId);
//            textId++;
//
//        }
        textViewValueAssignment();
        initRes();
//        datePicker();
        if (mTestType.contains(TestCreateActivity.ACPHAV)) {
            BuildTableTest2(testReadingArrayList.size() + 1, spiltValue.length - 3);
        }

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDACPHAVPostViewActivity.this, mActionBar, userName);
    }


    private void textViewValueAssignment() {
        dateTextView.setText("" + mTestDetails.getDateOfTest());
        instrumentUsed.setText(mTestDetails.getInstrumentUsed());
//            make.setText(clientInstrument.getMake());
        instrumentSerialNo.setText("" + mTestDetails.getInstrumentNo());
        calibrationOn.setText(Utilityies.parseDateToddMMyyyy(mTestDetails.getCalibratedOn()));
        calibrationDueOn.setText(Utilityies.parseDateToddMMyyyy(mTestDetails.getCalibratedDueOn()));
        testCundoctor.setText(mTestDetails.getTesterName());
        testSpecification.setText(mTestDetails.getTestSpecification());
        occupancyState.setText(mTestDetails.getOccupencyState());
        testRefrance.setText(mTestDetails.getTestReference());
        areaOfTest.setText(mTestDetails.getTestArea());
        roomName.setText(mTestDetails.getRoomName());
        // room no not needed
        roomNo.setText(mTestDetails.getRoomNo());
        ahuNo.setText(mTestDetails.getAhuNo());
        roomVolume.setText(mTestDetails.getRoomVolume());
        testCondoctorOrg.setText(mTestDetails.getTestCondoctorOrg());
        testWitnessOrg.setText(mTestDetails.getTestWitnessOrg());
        testWitness.setText(mTestDetails.getWitnessName());
        certificateNo.setText("" + mTestDetails.getRawDataNo());
    }

    private void initTextView() {
        // layout data which is not in use
        roomVolumeText = (TextView) findViewById(R.id.room_volume_text);
        roomVolumeText.setVisibility(View.VISIBLE);
        roomVolume = (TextView) findViewById(R.id.room_volume);
        roomVolume.setVisibility(View.VISIBLE);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        ahuNo.setVisibility(View.VISIBLE);
        ahuNoText = (TextView) findViewById(R.id.ahu_no_text);
        ahuNoText.setVisibility(View.VISIBLE);

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
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        equipmentNameText = (TextView) findViewById(R.id.equiment_name_text);
        equipmentNameText.setVisibility(View.INVISIBLE);
        equipmentNoText = (TextView) findViewById(R.id.equiment_no_text);
        equipmentNoText.setVisibility(View.INVISIBLE);
        roomName = (TextView) findViewById(R.id.room_name);
        roomNo = (TextView) findViewById(R.id.room_no);
        infarance = (TextView) findViewById(R.id.infarance);
        testCundoctor = (TextView) findViewById(R.id.testcunducter);
        testWitness = (TextView) findViewById(R.id.testwitness);
        testCondoctorOrg = (TextView) findViewById(R.id.test_condoctor_org);
        testWitnessOrg = (TextView) findViewById(R.id.testwitness_org);
//        submit = (ImageView) findViewById(R.id.submit);
//        clear = (ImageView) findViewById(R.id.clear);
//        clear.setVisibility(View.INVISIBLE);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

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
//                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
                    if (null != testReadingArrayList && testReadingArrayList.size() > 0) {
//                            HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
//                            Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
                        row.addView(addTextView(testReadingArrayList.get(i - 2).getEntityName()));
                    } else {
                        row.addView(addTextView("grillAndSizeFromGrill"));
                    }
//                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
//                        if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
////                            HashMap<String, String> grill = (HashMap<String, String>) grillAndSizeFromGrill.get(i - 2);
////                            Log.d("valdoc", "DynamicTableActivity grillAndSizeFromGrill=" + grillAndSizeFromGrill.size() + "i=" + i);
//                            row.addView(addTextView(mRoomFilterArrayList.get(i - 2).getFilterCode()));
//                        } else {
//                            row.addView(addTextView("grillAndSizeFromGrill"));
//                        }
//                    }

//                    row.addView(addTextView("AHU 2031/0.3MICRON/" + i));
                }

            }
            test2_table_layout.addView(row);

        }
//        spiltValue =testReadingArrayList.get(0).getValue().split(",");
//        for (int i = 0; i <spiltValue.length-2; i++) {
//            txtViewList.get(textId).setText(""+spiltValue[i]);
//            Log.d(TAG, "CodeFlow : InnerForLoop I: " + i+" textId "+textId);
//            textId++;
//
//        }

        //Second section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
//            String[] spiltValue1
//            if (i < rows){

//        }
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Grill/Filter Size\n in ft2(A)"));
                } else {
//                    if (mTestBasedOn.equalsIgnoreCase("AHU")) {
//                        double filterSize = 0.0f;
                    int index = i - 2;
                    String[] spiltValue1 = testReadingArrayList.get(index).getValue().split(",");
//                    if (spiltValue1.length <= index) {
                        row.addView(addTextView("" + spiltValue1[index]));
//                    }
//
//                    } else if (mTestBasedOn.equalsIgnoreCase("ROOM")) {
//                        double filterSize = 0.0f;
//                        if (!mRoomFilterArrayList.isEmpty()) {
//                            filterSize = mRoomFilterArrayList.get(i - 2).getEffectiveFilterArea();
//                            Log.d("rdacphav", "filterSize=" + filterSize);
//                            row.addView(addTextView("" + filterSize));
//                        }
//                    }
                }
            }
            test2_table_layout2.addView(row);

        }

        //Third section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            String[] spiltValue2 = null;
            if (i > 1)
                spiltValue2 = testReadingArrayList.get(i - 2).getValue().split(",");
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    row.addView(addTextView(" V " + j + "\n "));
                } else {
                    //row.addView(addEditTextView());

                    row.addView(addTextView(spiltValue2[j]));
                }
            }
            test2_table_layout3.addView(row);
        }
        //Fourth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            String[] spiltValue3 = null;
            int length = 0;
            if (i > 1) {
                spiltValue3 = testReadingArrayList.get(i - 2).getValue().split(",");
                length = spiltValue3.length;
            }
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Avg Velocity in\n fpm(AV)"));
                } else {
                    //result data  set
                    row.addView(addTextView(spiltValue3[length - 2]));
                }
            }
            test2_table_layout4.addView(row);

        }
        //Fifth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            String[] spiltValue4 = null;
            int length = 0;
            if (i > 1) {
                spiltValue4 = testReadingArrayList.get(i - 2).getValue().split(",");
                length = spiltValue4.length;
            }
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView(" Air Flow Rate\n in cfm(AxAv)"));
                } else {
                    //row.addView(addTextView("490"));
                    row.addView(addTextView(spiltValue4[length - 1]));
                    airFlowRateIds++;
                }
            }
            test2_table_layout5.addView(row);

        }

        //Sixth section
        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Total Air Flow Rate\n in cfm (TFR)"));
//                } else {
//                    //row.addView(addTextViewWithoutBorder("490"));
//                    row.addView(addTextViewWithIdsNoBorder(i, totalAirFlowRateIds, totalAirFlowRateTxtList));
//                }
//            }
//            test2_table_layout6.addView(row);
//        }
//
//        //Seventh section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView(" Room Volume in\n ft3(RV)"));
//                } else {
//                    //row.addView(addTextViewWithoutBorder("490"));
//                    row.addView(addTextViewWithIdsNoBorder(i, 0, roomVolumeTxtList));
//                }
//            }
//            test2_table_layout7.addView(row);
//        }
//
//        //Eight section
//        // outer for loop
//        for (int i = 1; i <= rows; i++) {
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
//                    TableRow.LayoutParams.WRAP_CONTENT));
//            // inner for loop
//            for (int j = 1; j <= 1; j++) {
//                if (i == 1 && j == 1) {
//                    row.addView(addTextView("No. of Air Changes/Hr\n ((TFR/RV)x60))"));
//                } else {
//                    //row.addView(addTextViewWithoutBorder("490"));
//                    row.addView(addTextViewWithIdsNoBorder(i, 0, airChangeTxtList));
//                }
//            }
//            test2_table_layout8.addView(row);
//        }

        //dismiss progressbar
//        if (pr.isShowing())
//            pr.dismiss();

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
        tv.setPadding(5, 6, 5, 6);
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

    private TextView addTextViewWithTagIds(int Tag, int Ids,
                                           ArrayList<TextView> txtViewList, float value) {
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
        tv.setText(value + "");
        Log.d(TAG, "TAG & idCountTv " + Ids);
        tv.setId(Ids);
        tv.setTag(Tag);
        Ids++;
        txtViewList.add(tv);
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
        headerText = (TextView) findViewById(R.id.common_header_tv);
//        headerText.setText("* Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates  *");
        //Test 2
        test2_table_layout = (TableLayout) findViewById(R.id.test2_tableLayout1);
        test2_table_layout2 = (TableLayout) findViewById(R.id.test2_tableLayout2);
        test2_table_layout3 = (TableLayout) findViewById(R.id.test2_tableLayout3);
        test2_table_layout4 = (TableLayout) findViewById(R.id.test2_tableLayout4);
        test2_table_layout5 = (TableLayout) findViewById(R.id.test2_tableLayout5);
        test2_table_layout6 = (TableLayout) findViewById(R.id.test2_tableLayout6);
        test2_table_layout6.setVisibility(View.GONE);
        test2_table_layout7 = (TableLayout) findViewById(R.id.test2_tableLayout7);
        test2_table_layout7.setVisibility(View.GONE);
        test2_table_layout8 = (TableLayout) findViewById(R.id.test2_tableLayout8);
        test2_table_layout8.setVisibility(View.GONE);
        TFRTxtv = (TextView) findViewById(R.id.acph_av_tfr_value_tv);
        TFTAVTxtv = (TextView) findViewById(R.id.acph_av_tfrby_av_value_tv);
        //Hide view coming form test tabl 1
        test_table_1_header_l = (LinearLayout) findViewById(R.id.test_table_2_header_l_ll);
        test_table_1_header_2 = (LinearLayout) findViewById(R.id.test_table_2_header_2_ll);
        test_table_1_header_l.setVisibility(View.GONE);
        test_table_1_header_2.setVisibility(View.GONE);
        findViewById(R.id.test_interference).setVisibility(View.GONE);
        findViewById(R.id.acph_av_final_calc_ll).setVisibility(View.VISIBLE);
        TextView TestHeader = (TextView) findViewById(R.id.common_header_tv);
        TestHeader.setText("TEST RAW DATA (RD_ACPH_AV)\n(Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates)");
    }
}
