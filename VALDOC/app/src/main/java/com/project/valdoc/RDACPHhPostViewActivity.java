package com.project.valdoc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class RDACPHhPostViewActivity extends AppCompatActivity {
    private static final String TAG = "RDACPHhPostView";
    private String userName = "";
    SharedPreferences sharedpreferences;

    TableLayout test3_table_layout, test3_table_layout2, test3_table_layout3, test3_table_layout4,
            test3_table_layout5;
    TextView TFRtv, TFTByRvTv;
    //Header TextView
    TextView roomVolume, roomVolumeText, ahuNo, ahuNoText, equipmentNameText,equipmentNoText, dateTextView;
    TextView plantName, areaOfTest, roomName, occupancyState, testRefrance,roomNo, testCundoctor,testWitness,testCondoctorOrg,testWitnessOrg;
    TextView customerName, certificateNo, instrumentUsed, instrumentSerialNo, calibrationOn,calibrationDueOn, testSpecification;
    private ImageView cancel;

    ArrayList<TextView> txtViewList;
    ArrayList<TextView>avgTxtViewList;
    ArrayList<TextView> roomVolumeTxtList;
    ArrayList<TextView> totalAirFlowRateTxtList;
    ArrayList<TextView> airChangeTxtList;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(RDACPHhPostViewActivity.this);
    String mTestType;
    private int testDetailId = 1;
    private ArrayList<TestReading> testReadingArrayList;
    private ArrayList<TestSpesificationValue> testSpecificationValueArrayList;
    private TestDetails mTestDetails;
    String spiltValue[] = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdacphh_post_view);

        //aRRAYlIST INIT
        txtViewList = new ArrayList<TextView>();
        avgTxtViewList = new ArrayList<TextView>();
        roomVolumeTxtList = new ArrayList<TextView>();
        totalAirFlowRateTxtList = new ArrayList<TextView>();
        airChangeTxtList = new ArrayList<TextView>();

        //init Layout res
        initRes();

        // Getting UserName
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        userName = sharedpreferences.getString("USERNAME", "");
        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDACPHhPostViewActivity.this, mActionBar, userName);

        //text view initialization
        initTextView();
        //Getting TestType and ID
        mTestType = getIntent().getStringExtra("TestType");
        testDetailId = getIntent().getIntExtra("testDetailId", 1);
        Log.d(TAG," mTestType "+mTestType+" testDetailId "+testDetailId);

        //Reading Data from DB
        testReadingArrayList = mValdocDatabaseHandler.getTestReadingDataById(testDetailId + "");
        mTestDetails = mValdocDatabaseHandler.getTestDetailById(testDetailId);
        testSpecificationValueArrayList = mValdocDatabaseHandler.getTestSpecificationValueById(testDetailId + "");
        spiltValue = testReadingArrayList.get(0).getValue().split(",");
        Log.d(TAG," testReadingArrayList "+testReadingArrayList.size()+" testSpecificationValueArrayList "+testSpecificationValueArrayList.size());
        // setting teast header value
        textViewValueAssignment();

        if (mTestType.contains(TestCreateActivity.ACPHH)) {
            BuildTableTest3(testReadingArrayList.size() + 1, spiltValue.length );
        }

        //Setting TFR and TFR*AV/60
        if(testSpecificationValueArrayList != null && testSpecificationValueArrayList.size()>=3){
            Log.d(TAG," testSpesificationValueArrayList "+testSpecificationValueArrayList.size());
            DecimalFormat df2 = new DecimalFormat(".##");
            try {
                TFRtv.setText(""+df2.format(Double.valueOf(testSpecificationValueArrayList.get(0).getFieldValue())));
                TFTByRvTv.setText(""+df2.format(Double.valueOf(testSpecificationValueArrayList.get(2).getFieldValue())));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

    }
//latest method
//private void BuildTableTest3(int rows, int cols) {
//    //first section
//    // outer for loop
//    for (int i = 1; i <= rows; i++) {
//        TableRow row = new TableRow(this);
//        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        // inner for loop
//        for (int j = 1; j <= 1; j++) {
//            if (i == 1 && j == 1) {
//                TextView grillTV = addTextView(" Grill/Filter No ");
//                ViewGroup.LayoutParams params = grillTV.getLayoutParams();
//                params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height_h);
//                grillTV.setLayoutParams(params);
//                grillTV.setEms(8+Utilityies.getPctCellWidth(cols));
//                row.addView(grillTV);
//            } else {
//
//                if (mGrilFilterType.equalsIgnoreCase("Grill")) {
//                    if (null != grillAndSizeFromGrill && grillAndSizeFromGrill.size() > 0) {
//                        TextView textView = addTextView(grillAndSizeFromGrill.get(i - 2).getGrillCode().toString());
//                        textView.setEms(8+Utilityies.getPctCellWidth(cols));
//                        row.addView(textView);
//                    } else {
//                        row.addView(addTextView("grillAndSizeFromGrill"));
//                    }
//
//                } else {
//                    if (null != mRoomFilterArrayList && mRoomFilterArrayList.size() > 0) {
//                        TextView textView = addTextView(mRoomFilterArrayList.get(i - 2).getFilterCode());
//                        textView.setEms(8+Utilityies.getPctCellWidth(cols));
//                        row.addView(textView);
//                    } else {
//                        row.addView(addTextView("grillAndSizeFromGrill"));
//                    }
//
//                }
//
//            }
//        }
//        test3_table_layout.addView(row);
//    }
//
//    //Second section
//    // outer for loop
//    for (int i = 1; i <= rows; i++) {
//
//        TableRow row = new TableRow(this);
//        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//
//        // inner for loop
//        for (int j = 1; j <= cols; j++) {
//            if (i == 1 && j <= cols) {
//                TextView textView = addTextView(" Q " + j);
//                if(cols>4)
//                    textView.setEms(4+Utilityies.getPctCellWidth(cols));
//                else
//                    textView.setEms(8+Utilityies.getPctCellWidth(cols));
//                row.addView(textView);
//            } else {
//                TextView editText = addInputDataTextView();
//                if(cols>4)
//                    editText.setEms(4+Utilityies.getPctCellWidth(cols));
//                else
//                    editText.setEms(8+Utilityies.getPctCellWidth(cols));
//                row.addView(editText);
//            }
//        }
//        test3_table_layout2.addView(row);
//    }
//
//    //Third section
//    // outer for loop
//    for (int i = 1; i <= rows; i++) {
//        TableRow row = new TableRow(this);
//        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        // inner for loop
//        if (i == 1) {
//            TextView textView = addTextView(" Average ");
//            if(cols>4)
//                textView.setEms(7+Utilityies.getPctCellWidth(cols));
//            else
//                textView.setEms(9+Utilityies.getPctCellWidth(cols));
//            row.addView(textView);
//        }else{
//            TextView textView = addResultTextView(i);
//            if(cols>4)
//                textView.setEms(7+Utilityies.getPctCellWidth(cols));
//            else
//                textView.setEms(9+Utilityies.getPctCellWidth(cols));
//            row.addView(textView);
//        }
//        test3_table_layout3.addView(row);
//    }
//
//    //dismiss progressbar
////    if (pr.isShowing())
////        pr.dismiss();
//
//
//}
//old method
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
                    TextView grillTV = addTextView(" Grill/Filter No ");
                    ViewGroup.LayoutParams params = grillTV.getLayoutParams();
                    //params.height = getResources().getDimensionPixelSize(R.dimen.common_txt_header_height);
                    grillTV.setLayoutParams(params);
                    grillTV.setEms(Utilityies.getPctCellWidth(cols));
                    row.addView(grillTV);
                } else {
                    if (null != testReadingArrayList && testReadingArrayList.size() > 0) {
                        TextView textView =addTextView(testReadingArrayList.get(i - 2).getEntityName());
                                textView.setEms(Utilityies.getPctCellWidth(cols));
                        row.addView(textView);

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
            for (int j = 1; j <= cols; j++) {
                if (i == 1 && j <= cols) {
                    TextView textView = addTextView(" Q " + j);
                    //if(cols>4)
                    textView.setEms(Utilityies.getPctCellWidth(cols));
//                    else
//                    textView.setEms(8+Utilityies.getPctCellWidth(cols));
                    row.addView(textView);
                } else {
                    TextView editText = addTextView(""+testReadingArrayList.get(i-2).getValue());
                    if(cols>4)
                    editText.setEms(Utilityies.getPctCellWidth(cols));
                    else
                        editText.setEms(8+Utilityies.getPctCellWidth(cols));
                    row.addView(editText);
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
            if (i == 1) {
                TextView textView = addTextView(" Average ");
                //if(cols>4)
                textView.setEms(Utilityies.getPctCellWidth(cols));
//                else
//                    textView.setEms(9+Utilityies.getPctCellWidth(cols));
                row.addView(textView);
            }else{
                TextView textView = addTextView(""+testReadingArrayList.get(i-2).getValue());
               // if(cols>4)
                    textView.setEms(Utilityies.getPctCellWidth(cols));
//                else
//                    textView.setEms(9+Utilityies.getPctCellWidth(cols));
                row.addView(textView);
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
                    //row.addView(addTextViewWithoutBorder(""+room.getVolume()));
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
    }


    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        ViewGroup.LayoutParams params = tv.getLayoutParams();
        params.height = getResources().getDimensionPixelSize(R.dimen.pct_text_cell_height);
        tv.setLayoutParams(params);
        tv.setPadding(15,0,15,10);
        tv.setBackgroundResource(R.drawable.border1);
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

    private TextView addTextViewWithIdsNoBorder(int Tag, int ids, ArrayList<TextView> txtViewList) {
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
        Log.d(TAG, "No Border idCountTv " + ids);
        if (ids > 0)
            tv.setId(ids);
        tv.setTag(Tag);
        ids++;
        txtViewList.add(tv);
        return tv;
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

        String clientOrg = sharedpreferences.getString("CLIENTORG", "");
        String prtnerOrg = sharedpreferences.getString("PARTNERORG", "");
        if (sharedpreferences.getString("USERTYPE", "").equalsIgnoreCase("CLIENT")) {
            testCondoctorOrg.setText("(" + clientOrg + ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText(""+clientOrg);
        } else {
            testCondoctorOrg.setText("(" + prtnerOrg + ")");
            testWitnessOrg.setText("(" + clientOrg + ")");
            customerName.setText(""+prtnerOrg);
        }
        plantName.setText(""+mTestDetails.getBlockName());
    }


    private void initTextView() {
        roomVolumeText = (TextView) findViewById(R.id.room_volume_text);
        roomVolumeText.setVisibility(View.VISIBLE);
        roomVolume = (TextView) findViewById(R.id.room_volume);
        roomVolume.setVisibility(View.VISIBLE);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        ahuNo.setVisibility(View.VISIBLE);
        ahuNoText = (TextView) findViewById(R.id.ahu_no_text);
        ahuNoText.setVisibility(View.VISIBLE);

        equipmentNameText = (TextView) findViewById(R.id.equiment_name_text);
        equipmentNameText.setVisibility(View.INVISIBLE);
        equipmentNoText = (TextView) findViewById(R.id.equiment_no_text);
        equipmentNoText.setVisibility(View.INVISIBLE);

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
        occupancyState = (TextView) findViewById(R.id.ocupancystate);
        testRefrance = (TextView) findViewById(R.id.testrefrence);
        roomNo = (TextView) findViewById(R.id.room_no);
        ahuNo = (TextView) findViewById(R.id.ahu_no);
        testCundoctor = (TextView) findViewById(R.id.testcunducter);
        testWitness = (TextView) findViewById(R.id.testwitness);
        testCondoctorOrg = (TextView) findViewById(R.id.test_condoctor_org);
        testWitnessOrg = (TextView) findViewById(R.id.testwitness_org);

    }

    private void initRes() {
        findViewById(R.id.ahu_no_lable).setVisibility(View.VISIBLE);
        findViewById(R.id.room_volume_table).setVisibility(View.VISIBLE);
        findViewById(R.id.submit).setVisibility(View.GONE);
        findViewById(R.id.clear).setVisibility(View.GONE);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        test3_table_layout = (TableLayout) findViewById(R.id.test3_tableLayout1);
        test3_table_layout2 = (TableLayout) findViewById(R.id.test3_tableLayout2);
        test3_table_layout3 = (TableLayout) findViewById(R.id.test3_tableLayout3);
        test3_table_layout4 = (TableLayout) findViewById(R.id.test3_tableLayout4);
        test3_table_layout4.setVisibility(View.GONE);
        test3_table_layout5 = (TableLayout) findViewById(R.id.test3_tableLayout5);
        test3_table_layout5.setVisibility(View.GONE);
        TFRtv = (TextView)findViewById(R.id.acph_h_tfr_value_tv);
        TFTByRvTv = (TextView)findViewById(R.id.acph_h_tfrby_av_value_tv);
        findViewById(R.id.test_table_3_header_l_ll).setVisibility(View.GONE);
        findViewById(R.id.test_table_3_header_2_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.acph_h_final_calc_ll).setVisibility(View.VISIBLE);
        TextView TestHeader = (TextView)findViewById(R.id.common_header_tv);
        TextView TestHeader2 = (TextView)findViewById(R.id.common_header_2_tv);
        TestHeader2.setVisibility(View.VISIBLE);
        TestHeader.setText("TEST RAW DATA");
        TestHeader2.setText("(Air Flow Velocity, Volume Testing and Determination of Air Changes per Hour Rates by Air Capture Hood)");
    }
}
