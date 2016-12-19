package com.project.valdoc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.controler.ValdocControler;
import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.task.HttpConnection;
import com.project.valdoc.task.HttpPostConnection;
import com.project.valdoc.utility.Utilityies;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class SyncSelectedDataActivity extends AppCompatActivity implements HttpPostConnection.HttpUrlConnectionPostResponce, HttpConnection.HttpUrlConnectionResponce {
    private static final String TAG = "SyncSelectedData";
    TableLayout table_layout1, table_layout2, table_layout3, table_layout4, table_layout5,
            table_layout6, table_layout7, table_layout8, table_layout9, table_layout10;
    TextView table_header_tv;
    private String mTestBasedOn;
    int rows = 5, colos = 5;
    private String userName = "", testType = "";
    SharedPreferences sharedpreferences;
    private ValdocDatabaseHandler mValdocDatabaseHandler;
    private ArrayList<TestDetails> testDetailList;
    private HashMap<Integer, Integer> selectePosition = new HashMap<Integer, Integer>();
    Button syncSelectedButton;
    ValdocControler mValdocControler;
    CheckBox selectAll_cb;
    private ArrayList<CheckBox>checkBoxArrayList;
//    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(TestCreateActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_selected_data);

        checkBoxArrayList = new ArrayList<CheckBox>();

        //Init Layout res
        initRes();

        //Common Object init
        mValdocControler = new ValdocControler();
        mValdocDatabaseHandler = new ValdocDatabaseHandler(SyncSelectedDataActivity.this);
        testDetailList = new ArrayList<TestDetails>();
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        userName = sharedpreferences.getString("USERNAME", "");

        if (getIntent().hasExtra("rows")) {
            rows = getIntent().getIntExtra("rows", 6);
            colos = getIntent().getIntExtra("cols", 6);
        }
        mTestBasedOn=getIntent().getStringExtra("TestBasedOn");
        testType = getIntent().getStringExtra("TestType");
        Log.d(TAG, " Code TestType : " + testType);
        table_header_tv.setText(testType);

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(SyncSelectedDataActivity.this, mActionBar, userName);


        if (getTestDataByTestCode(testType).size() > 0) {
            BuildSyncDataTable(getTestDataByTestCode(testType).size() + 1, colos + 1);
        } else {
            findViewById(R.id.no_data_found_tv).setVisibility(View.VISIBLE);
        }
    }

    private void syncTestData() {
        mValdocControler.httpCertificatePostSyncData(SyncSelectedDataActivity.this, "POST", getTestDetailsIdList());
//        if(selectePosition.size()>0) {
//            for (Integer value : selectePosition.values()) {
//                mValdocDatabaseHandler.deleteTestTableRow(testDetailList.get(value).getTest_detail_id());
//            }
//        }
    }

    private String getTestDetailsIdList() {
        StringBuilder idList = new StringBuilder();
        int flag = 0;
        if (selectePosition.size() > 0) {
            for (Integer value : selectePosition.values()) {
                if (flag == 0) {
                    idList.append("" + testDetailList.get(value).getTest_detail_id());
                    flag = 1;
                } else
                    idList.append("," + testDetailList.get(value).getTest_detail_id());
            }
        }
        return idList.toString();
    }

    private ArrayList<TestDetails> getTestDataByTestCode(String testType) {
        if (!testType.equals(null) && testType.length() > 0) {
            if (testType.equalsIgnoreCase("ACPH_AV")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ACPH_AV");
            } else if (testType.equalsIgnoreCase("ACPH_H")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ACPH_H");
            } else if (testType.equalsIgnoreCase("ERD_FIT")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ERD_FIT");
            }else if (testType.equalsIgnoreCase("FIT")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("FIT");
            }else if (testType.equalsIgnoreCase("ARD_FIT_AHU")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ARD_FIT_AHU");
            }else if (testType.equalsIgnoreCase("PCT")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("PCT");
            } else if (testType.equalsIgnoreCase("RCT")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("RCT");
            } else if (testType.equalsIgnoreCase("ARD_AF_AHU")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ARD_AF_AHU");
            } else if (testType.equalsIgnoreCase("ARD_FIT_AHU")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ARD_FIT_AHU");
            } else if (testType.equalsIgnoreCase("ERD_AV")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ERD_AV");
            } else if (testType.equalsIgnoreCase("ERD_FIT")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ERD_FIT");
            } else if (testType.equalsIgnoreCase("ERD_PCT")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ERD_PCT");
            } else if (testType.equalsIgnoreCase("ERD_RCT")) {
                testDetailList = mValdocDatabaseHandler.getTestDetailByTestCode("ERD_RCT");
            }
        }
        return testDetailList;
    }

    private void BuildSyncDataTable(int rows, int cols) {
        //first section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("SI # "));
                } else {
                    row.addView(addTextView(i - 1 + ""));
                }

            }

            table_layout1.addView(row);
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
                    row.addView(addTextView("Select "));
                } else {
                    row.addView(addCheckBox(1 + i));
                }

            }
            table_layout2.addView(row);
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
                    row.addView(addTextView("    RD NO     "));
                } else {
                    if (testDetailList.size() > 0)
                        row.addView(addTextView("" + testDetailList.get(i - 2).getRawDataNo()));
                    else
                        row.addView(addTextView(""));
                }

            }

            table_layout3.addView(row);
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
                    row.addView(addTextView("   Date       "));
                } else {
                    if (testDetailList.size() > 0)
                        row.addView(addTextView("" + testDetailList.get(i - 2).getDateOfTest()));
                    else
                        row.addView(addTextView(""));
                }

            }

            table_layout4.addView(row);
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
                    row.addView(addTextView("   Area Name    "));
                } else {
                    if (testDetailList.size() > 0)
                        row.addView(addTextView("" + testDetailList.get(i - 2).getTestArea()));
                    else
                        row.addView(addTextView(""));
                }

            }

            table_layout5.addView(row);
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
                    row.addView(addTextView("   AHU No   "));
                } else {
                    if (testDetailList.size() > 0)
                        row.addView(addTextView("" + testDetailList.get(i - 2).getAhuNo()));
                    else
                        row.addView(addTextView(""));
                }

            }

            table_layout6.addView(row);
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
                    row.addView(addTextView("   Room Name    "));
                } else {
                    if (testDetailList.size() > 0)
                        row.addView(addTextView("" + testDetailList.get(i - 2).getRoomName()));
                    else
                        row.addView(addTextView(""));
                }

            }

            table_layout7.addView(row);
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
                    row.addView(addTextView("   Room No   "));
                } else {
                    if (testDetailList.size() > 0)
                        row.addView(addTextView("" + testDetailList.get(i - 2).getRoomNo()));
                    else
                        row.addView(addTextView(""));
                }

            }

            table_layout8.addView(row);
        }
        //Ninth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("  Results  "));
                } else {
                    row.addView(addTextView(""));
                }

            }

            table_layout9.addView(row);
        }
        //Tenth section
        // outer for loop
        for (int i = 1; i <= rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 1; j <= 1; j++) {
                if (i == 1 && j == 1) {
                    row.addView(addTextView("  Action   "));
                } else {
                    row.addView(addActionTextView("View", i));
                }

            }

            table_layout10.addView(row);
        }


    }

    private void initRes() {
        table_header_tv = (TextView)findViewById(R.id.header_sunc_data);
        syncSelectedButton = (Button) findViewById(R.id.sync_selected_button);
        table_layout1 = (TableLayout) findViewById(R.id.syncData_tableLayout1);
        table_layout2 = (TableLayout) findViewById(R.id.syncData_tableLayout2);
        table_layout3 = (TableLayout) findViewById(R.id.syncData_tableLayout3);
        table_layout4 = (TableLayout) findViewById(R.id.syncData_tableLayout4);
        table_layout5 = (TableLayout) findViewById(R.id.syncData_tableLayout5);
        table_layout6 = (TableLayout) findViewById(R.id.syncData_tableLayout6);
        table_layout7 = (TableLayout) findViewById(R.id.syncData_tableLayout7);
        table_layout8 = (TableLayout) findViewById(R.id.syncData_tableLayout8);
        table_layout9 = (TableLayout) findViewById(R.id.syncData_tableLayout9);
        table_layout10 = (TableLayout) findViewById(R.id.syncData_tableLayout10);

        selectAll_cb = (CheckBox) findViewById(R.id.select_all_checkbox);
        selectAll_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){

                    for (int i = 0; i < checkBoxArrayList.size(); i++) {
                       checkBoxArrayList.get(i).setChecked(true);
                    }

                }else{
                    for (int i = 0; i < checkBoxArrayList.size(); i++) {
                        checkBoxArrayList.get(i).setChecked(false);
                    }
                }

            }
        });


        syncSelectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncTestData();
//                finish();
            }
        });

    }

    private TextView addTextView(String textValue) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        //tv.setPadding(5,5,5,5);
        tv.setHeight(70);
        tv.setTextColor(getResources().getColor(R.color.black));
        //tv.setTextSize(getResources().getDimension(R.dimen.normal_text_size1));
        tv.setSingleLine(false);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setText(textValue);
        return tv;
    }

    private TextView addActionTextView(String view, int rowId) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.border1);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(R.color.black));
        tv.setSingleLine(false);
        tv.setHeight(70);
        tv.setMaxLines(3);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setOnClickListener(new ViewActionValidator(SyncSelectedDataActivity.this, rowId));
        tv.setText(view);
        return tv;
    }

    private CheckBox addCheckBox(int row) {
        CheckBox cb = new CheckBox(this);
        cb.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        cb.setBackgroundResource(R.drawable.border1);
        cb.setHeight(70);
        cb.setGravity(Gravity.CENTER);
        cb.setId(row);
        cb.setTag(row + 1);
        cb.setOnCheckedChangeListener(new CheckedValidator(SyncSelectedDataActivity.this, row));
        checkBoxArrayList.add(cb);
        return cb;
    }

    @Override
    public void httpPostResponceResult(String resultData, int statusCode) {

        final int statuscode = statusCode;
        Log.d("VALDOC", "controler httpPostResponceResult response data1  statusCode=" + statusCode);
        Log.d("VALDOC", "controler httpPostResponceResult response data1  resultData=" + resultData);
        SyncSelectedDataActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (statuscode == HttpURLConnection.HTTP_OK) {
                    if (selectePosition.size() > 0) {
                        for (Integer value : selectePosition.values()) {
                            mValdocDatabaseHandler.deleteTestTableRow(testDetailList.get(value).getTest_detail_id());
                        }
                    }
                    aleartDialog("Data synced successfully");
                    finish();
                } else {
                    aleartDialog("Post Data not synced successfully,Please sync again !");
                }
            }
        });

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

    @Override
    public void httpResponceResult(String resultData, int statusCode) {

    }


    private class CheckedValidator implements CompoundButton.OnCheckedChangeListener {
        int checkBpxId;
        Context mContext;

        public CheckedValidator(Context context, int row) {
            this.checkBpxId = row;
            this.mContext = context;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (isChecked) {
                selectePosition.put(checkBpxId, checkBpxId - 3);
                Toast.makeText(mContext, "CheckBox : " + checkBpxId + " Checked ", Toast.LENGTH_SHORT).show();
            } else {
                selectePosition.remove(checkBpxId);
//                Toast.makeText(mContext, "CheckBox : " + checkBpxId + " Unchecked", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class ViewActionValidator implements View.OnClickListener {
        int viewTextId;
        Context mContext;

        public ViewActionValidator(Context context, int rowId) {
            this.mContext = context;
            this.viewTextId = rowId;

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, testType + "View Row : " + viewTextId + " Test", Toast.LENGTH_SHORT).show();
            if (testType != null && testType.contains("ACPH_AV")) {
                Intent intent = new Intent(SyncSelectedDataActivity.this, CommonTestViewActivity.class);
                intent.putExtra("testDetailId", testDetailList.get(viewTextId - 2).getTest_detail_id());
                intent.putExtra("TestType", testType);
                intent.putExtra("TestBasedOn", mTestBasedOn);
                startActivity(intent);
            }else if(testType != null && testType.contains("ACPH_H")){
                Intent intent = new Intent(SyncSelectedDataActivity.this, RDACPHhPostViewActivity.class);
                intent.putExtra("testDetailId", testDetailList.get(viewTextId - 2).getTest_detail_id());
                intent.putExtra("TestType", testType);
                intent.putExtra("TestBasedOn", mTestBasedOn);
                startActivity(intent);
            }else if(testType != null && testType.contains("FIT")){
                Intent intent = new Intent(SyncSelectedDataActivity.this, RDFITPostViewActivity.class);
                intent.putExtra("testDetailId", testDetailList.get(viewTextId - 2).getTest_detail_id());
                intent.putExtra("TestType", testType);
                intent.putExtra("TestBasedOn", mTestBasedOn);
                startActivity(intent);
                Log.d(TAG, " testDetailId : " + testDetailList.get(viewTextId - 2).getTest_detail_id()+" TestType "+testType);
            }else if(testType != null && testType.contains("PCT")){
                Intent intent = new Intent(SyncSelectedDataActivity.this, RDPCTPostViewActivity.class);
                intent.putExtra("testDetailId", testDetailList.get(viewTextId - 2).getTest_detail_id());
                intent.putExtra("TestType", testType);
                intent.putExtra("TestBasedOn", mTestBasedOn);
                startActivity(intent);
            }else if(testType != null && testType.contains("RCT")){
                Intent intent = new Intent(SyncSelectedDataActivity.this, RDRCTPostViewActivity.class);
                intent.putExtra("testDetailId", testDetailList.get(viewTextId - 2).getTest_detail_id());
                intent.putExtra("TestType", testType);
                intent.putExtra("TestBasedOn", mTestBasedOn);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(SyncSelectedDataActivity.this, CommonTestViewActivity.class);
                intent.putExtra("TestType", testType);
                intent.putExtra("testDetailId", testDetailList.get(viewTextId - 2).getTest_detail_id());
                intent.putExtra("rows", 6);
                intent.putExtra("cols", 5);
                intent.putExtra("TestBasedOn", mTestBasedOn);
                startActivity(intent);
            }
        }
    }
}
