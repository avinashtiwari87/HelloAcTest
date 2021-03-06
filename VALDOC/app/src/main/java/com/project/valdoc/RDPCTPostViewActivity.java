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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.utility.Utilityies;

import java.util.ArrayList;

public class RDPCTPostViewActivity extends AppCompatActivity {
    private static final String TAG = "RDPCT_Post_View";
    TextView headerText;
    //Test 5 View ...
    TableLayout test5_table_layout, test5_table_layout2, test5_table_layout2_1,
            test5_table_layout3, test5_tableLayout2_2, test5_table_layout4, test5_tableLayout4_2, test5_table_layout4_1,
            test5_table_layout5, test5_table_layout5_1, test5_table_layout3_1;
    // PCT new footer
    TextView meanValue1_tv, meanValue2_tv, stdDev1_tv, stdDev2_tv, ucl1_tv, ucl2_tv, minimumValue1_tv, minimumValue2_tv, maximumValue1_tv, maximumValue2_tv;
    //Header TextView
    TextView roomVolume, roomVolumeText, ahuNo, ahuNoText, equipmentNameText,equipmentNoText, dateTextView;
    TextView plantName, areaOfTest, roomName, occupancyState, testRefrance,roomNo, testCundoctor,testWitness,testCondoctorOrg,testWitnessOrg;
    TextView customerName, certificateNo, instrumentUsed, instrumentSerialNo, calibrationOn,calibrationDueOn, testSpecification;
    private ImageView cancel;
    SharedPreferences sharedpreferences;
    private String userName = "";
    private int rows, cols, testDetailId = 1, est5CommonFormulaIds2 = 600;
    String mTestType;
    ProgressDialog pr;
    int test5CommonFormulaIds1 = 500, test5CommonFormulaIds2 = 600;
    long meanValue1 = 0l, meanValue2 = 0l;
    double stdDev1 = 0.0, stdDev2 = 0.0;
    ArrayList<TextView> RDPC3TxtList, RDPC3TxtList2;
    ArrayList<TextView> resultTextViewList;
    ArrayList<TextView> txtViewList;
    private ValdocDatabaseHandler mValdocDatabaseHandler;
    private ArrayList<TestReading> testReadingArrayList;
    private ArrayList<TestSpesificationValue> testSpecificationValueArrayList;
    private TestDetails mTestDetails;
    String spiltValue[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdpctpost_view);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pr = ProgressDialog.show(this, "Please Wait", "Loading...");
        pr.setCanceledOnTouchOutside(true);
        pr.setCancelable(true);

        mValdocDatabaseHandler = new ValdocDatabaseHandler(RDPCTPostViewActivity.this);

        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        userName = sharedpreferences.getString("USERNAME", "");
        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(RDPCTPostViewActivity.this, mActionBar, userName);

        //arrayList init
        txtViewList = new ArrayList<TextView>();
        resultTextViewList = new ArrayList<TextView>();
        RDPC3TxtList = new ArrayList<TextView>();
        RDPC3TxtList2 = new ArrayList<TextView>();

        // init view
        initRes();
        //Header text view initialization
        initTextView();

        mTestType = getIntent().getStringExtra("TestType");
        testDetailId = getIntent().getIntExtra("testDetailId", 1);
        Log.d(TAG, " TestType : " + mTestType+" testDetailId "+testDetailId);

        //Reading Data from DB
        testReadingArrayList = mValdocDatabaseHandler.getTestReadingDataById(testDetailId + "");
        mTestDetails = mValdocDatabaseHandler.getTestDetailById(testDetailId);
        testSpecificationValueArrayList = mValdocDatabaseHandler.getTestSpecificationValueById(testDetailId + "");
        spiltValue = testReadingArrayList.get(0).getValue().split(",");
        rows = testReadingArrayList.size()/2+1;
        cols = (spiltValue.length-1);
        Log.d(TAG, " rows "+rows+" cols "+cols);
        // setting teast header value
        textViewValueAssignment();


        if (mTestType.contains(TestCreateActivity.PCT)) {
            BuildTableTest5(rows, cols);
//            BuildTableTest5(7, 3);
        }

        // Setting calculation value in PCT
        if(testSpecificationValueArrayList!= null && testSpecificationValueArrayList.size()>=4){
            meanValue1_tv.setText(testSpecificationValueArrayList.get(0).getFieldValue().toString());
            meanValue2_tv.setText(testSpecificationValueArrayList.get(01).getFieldValue().toString());
            stdDev1_tv.setText(testSpecificationValueArrayList.get(2).getFieldValue().toString());
            stdDev2_tv.setText(testSpecificationValueArrayList.get(3).getFieldValue().toString());
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
                if (i == 1 && j == 1) {

                    TextView textView = addTextView(" Location ");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height2);
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
        if(5*cols>13){
            tvs.setEms(5*cols);
        }else{
            tvs.setEms(13);
        }
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
                    spiltValue = testReadingArrayList.get(i-2).getValue().split(",");
                    row.addView(addTextView(""+spiltValue[j-1]));
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
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height2);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    spiltValue = testReadingArrayList.get(i-2).getValue().split(",");
                    row.addView(addTextView(""+spiltValue[spiltValue.length-1]));
                }

            }
            test5_table_layout3.addView(row);
        }
        //Footer Rows....
        for (int sk = 0; sk < 3; sk++) {
            TableRow rowFooter = new TableRow(this);
            rowFooter.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            rowFooter.addView(addTextViewWithTagIds(sk, test5CommonFormulaIds1, RDPC3TxtList, 0));
            test5_table_layout3_1.addView(rowFooter);
            test5CommonFormulaIds1++;
        }


        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT));
        TextView tvs1 = addTextView(" No. of Particles >= 5 µm/m³  ");
        if(5*cols>13){
            tvs1.setEms(5*cols);
        }else{
            tvs1.setEms(13);
        }
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
                    spiltValue = testReadingArrayList.get(((rows+i)-3)).getValue().split(",");
                    row.addView(addTextView(""+spiltValue[j-1]));
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
                    TextView textView = addTextView("Average");
                    ViewGroup.LayoutParams params = textView.getLayoutParams();
                    params.height = getResources().getDimensionPixelSize(R.dimen.text_view_height2);
                    textView.setLayoutParams(params);
                    row.addView(textView);
                } else {
                    spiltValue = testReadingArrayList.get(((rows+i)-3)).getValue().split(",");
                    row.addView(addTextView(""+spiltValue[spiltValue.length-1]));
                }

            }
            test5_table_layout5.addView(row);
        }
        //Footer Rows....
        for (int sk = 0; sk < 3; sk++) {
            TableRow rowFooter = new TableRow(this);
            rowFooter.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            rowFooter.addView(addTextViewWithTagIds(sk, test5CommonFormulaIds2, RDPC3TxtList2, 0));
            test5_table_layout5_1.addView(rowFooter);
            test5CommonFormulaIds2++;
        }

        TextView txtView = RDPC3TxtList.get(0);
        txtView.setText(meanValue1 + "");
        TextView txtView2 = RDPC3TxtList2.get(0);
        txtView2.setText(meanValue2 + "");

        TextView txtView3 = RDPC3TxtList.get(1);
        txtView3.setText(stdDev1 + "");
        TextView txtView4 = RDPC3TxtList2.get(1);
        txtView4.setText(stdDev2 + "");

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
        tv.setPadding(3, 3, 3, 3);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size));
        tv.setGravity(Gravity.CENTER);
        //tv.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEms(5);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
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
        plantName.setText("from cofig screen");
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
        headerText = (TextView) findViewById(R.id.common_header_tv);
        headerText.setText("TEST RAW DATA\nAirborne Particle Count Test for Classification of Cleanrooms/zones and Clean Air Devices");
        findViewById(R.id.submit).setVisibility(View.GONE);
        findViewById(R.id.clear).setVisibility(View.GONE);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        //Test5
        test5_table_layout = (TableLayout) findViewById(R.id.test5_tableLayout1);
        test5_table_layout2 = (TableLayout) findViewById(R.id.test5_tableLayout2);
        test5_table_layout2.setVisibility(View.GONE);
        test5_table_layout2_1 = (TableLayout) findViewById(R.id.test5_tableLayout2_1);
        test5_tableLayout2_2 = (TableLayout) findViewById(R.id.test5_tableLayout2_2);
        test5_table_layout3 = (TableLayout) findViewById(R.id.test5_tableLayout3);
        test5_table_layout3_1 = (TableLayout) findViewById(R.id.test5_tableLayout3_1);
        test5_table_layout3_1.setVisibility(View.GONE);
        test5_table_layout4 = (TableLayout) findViewById(R.id.test5_tableLayout4);
        test5_table_layout4.setVisibility(View.GONE);
        test5_table_layout4_1 = (TableLayout) findViewById(R.id.test5_tableLayout4_1);
        test5_tableLayout4_2 = (TableLayout) findViewById(R.id.test5_tableLayout4_2);
        test5_table_layout5 = (TableLayout) findViewById(R.id.test5_tableLayout5);
        test5_table_layout5_1 = (TableLayout) findViewById(R.id.test5_tableLayout5_1);
        test5_table_layout5_1.setVisibility(View.GONE);
        findViewById(R.id.test_table_5_header_l_ll).setVisibility(View.GONE);
        findViewById(R.id.test_table_5_header_2_ll).setVisibility(View.VISIBLE);
        findViewById(R.id.test_interference).setVisibility(View.GONE);
        // PCT new footer
        meanValue1_tv = (TextView)findViewById(R.id.pct_mean_value1);
        meanValue2_tv = (TextView)findViewById(R.id.pct_mean_value2);
        stdDev1_tv = (TextView)findViewById(R.id.pct_std_dev1);
        stdDev2_tv = (TextView)findViewById(R.id.pct_std_dev2);
        ucl1_tv = (TextView)findViewById(R.id.pct_95_ucl_1);
        ucl2_tv= (TextView)findViewById(R.id.pct_95_ucl_2);
        minimumValue1_tv= (TextView)findViewById(R.id.pct_minimum_value_1);
        minimumValue2_tv= (TextView)findViewById(R.id.pct_minimum_value_2);
        maximumValue1_tv= (TextView)findViewById(R.id.pct_maximum_value_1);
        maximumValue2_tv= (TextView)findViewById(R.id.pct_maximum_value_2);
    }

}
