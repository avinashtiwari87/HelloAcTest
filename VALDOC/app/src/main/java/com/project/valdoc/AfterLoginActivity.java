package com.project.valdoc;

import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.valdoc.controler.ValdocControler;
import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.task.HttpConnection;
import com.project.valdoc.task.HttpPostConnection;
import com.project.valdoc.utility.Utilityies;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class AfterLoginActivity extends AppCompatActivity implements HttpConnection.HttpUrlConnectionResponce, ValdocControler.ControlerResponse, HttpPostConnection.HttpUrlConnectionPostResponce {

    private String userName = "";
    private String loginUserType = "";
    private int appUserId;
    SharedPreferences sharedpreferences;
    ValdocControler mValdocControler;
    private ValdocDatabaseHandler mValdocDatabaseHandler;
    //?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        mValdocControler = new ValdocControler();
        mValdocDatabaseHandler = new ValdocDatabaseHandler(AfterLoginActivity.this);
        userName = sharedpreferences.getString("USERNAME", "");
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            loginUserType = null;
        } else {
            loginUserType = extras.getString("USERTYPE");
            appUserId = extras.getInt("APPUSERID");
        }

        if (getIntent().hasExtra("serviceReport")) {
//            getSupportActionBar().hide();
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//            setContentView(R.layout.service_report_entry);
//            // ui value initialization and assignment
//            initTextView();
//            textViewValueAssignment();
//            datePicker();

        } else {
            setContentView(R.layout.activity_after_login);

            findViewById(R.id.service_report_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AfterLoginActivity.this, ServiceReportActivity.class);
                    intent.putExtra("serviceReport", true);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });

            findViewById(R.id.test_certificate_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AfterLoginActivity.this, TestCreateActivity.class);
                    intent.putExtra("USERNAME", sharedpreferences.getString("USERNAME", ""));
                    intent.putExtra("USERTYPE", sharedpreferences.getString("USERTYPE", ""));
                    intent.putExtra("APPUSERID", sharedpreferences.getInt("APPUSERID", 0));
                    intent.putExtra("PARTNERID", sharedpreferences.getInt("PARTNERID", 0));
                    startActivity(intent);
                    finish();
                }
            });

            findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("login", false);
                    editor.remove("USERNAME"); // will delete key key_name3
                    editor.remove("USERTYPE");
                    editor.remove("APPUSERID");
                    editor.commit();
                    Intent intent = new Intent(AfterLoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });


            findViewById(R.id.sync_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    copyDb();
                    if (Utilityies.checkInternetConnection(AfterLoginActivity.this)) {
                        mValdocControler.getHttpConectionforSync(AfterLoginActivity.this, "GET");
                        mValdocControler.httpServiceReportPostSyncData(AfterLoginActivity.this, "POST");
                    } else {
                        aleartDialog("Please check your internate connection !");
                    }
                }
            });

            findViewById(R.id.sync_data_table_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(AfterLoginActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AfterLoginActivity.this, ShowSelectedTestActivity.class);
                    startActivity(intent);
                }
            });

        }

        //Custom Action Bar
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null)
            Utilityies.setCustomActionBar(AfterLoginActivity.this, mActionBar, userName);
/*        ImageView imgView = (ImageView)findViewById(R.id.imageView1);
        imgView.requestLayout();
        imgView.getLayoutParams().width = 60;*/

    }
    private void copyDb() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            Log.d("copydb", "db creted start");
            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/valdoc.db";
                String backupDBPath = "valdocbackupname.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd + "/avinash", backupDBPath);
                Log.d("copydb", "db creted start1currentDB.exists()=" + currentDB.exists());
                if (currentDB.exists()) {
                    Log.d("copydb", "db creted start2");
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Log.d("copydb", "db creted succesfully");
                }
            }
        } catch (Exception e) {
            Log.d("copydb", "db exception" + e.getMessage());
        }
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
        if (statusCode == HttpURLConnection.HTTP_OK) {
            Log.d("VALDOC", "response data=" + resultData);
//            generateNoteOnSD(AfterLoginActivity.this,resultData);
            mValdocControler.getAllDb(statusCode, resultData);
        }
    }

    public void generateNoteOnSD(Context context, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, "json_data.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Log.d("VALDOC", "controler response data= file saved");
        } catch (IOException e) {
            Log.d("VALDOC", "controler response data= file exception=" + e.getMessage());
        }
    }

    @Override
    public void controlerResult(HashMap<String, ArrayList> arrayListHashMap, String message) {
        Log.d("VALDOC", "controler response data  message=" + message);
        {
            Log.d("VALDOC", "controler response data  message=" + message);
            boolean isertFlag = insertDataInTable(arrayListHashMap);
            Log.d("VALDOC", "controler response data  isertFlag=" + isertFlag);
            if (isertFlag) {
                aleartDialog(""+message);
            } else {
                aleartDialog("Table is not created successfully,Please sync again !");
            }
        }
    }

    private boolean insertDataInTable(HashMap<String, ArrayList> arrayListHashMap) {
        boolean isertFlag = true;
        Log.d("VALDOC", "controler response data  insert table hasmap size=" + arrayListHashMap.size());
        ArrayList userArrayList = null;
        try {
            userArrayList = (ArrayList) arrayListHashMap.get(ValdocDatabaseHandler.USER_TABLE_NAME);
            Log.d("VALDOC", "controler response data  userArrayList.size()=" + userArrayList.size());
            if (null != userArrayList || userArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  1");
                isertFlag = mValdocDatabaseHandler.insertUser(ValdocDatabaseHandler.USER_TABLE_NAME, userArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList ahusArrayList = arrayListHashMap.get(ValdocDatabaseHandler.AHU_TABLE_NAME);
            if (null != ahusArrayList || ahusArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  2");
                isertFlag = mValdocDatabaseHandler.insertAhu(ValdocDatabaseHandler.AHU_TABLE_NAME, ahusArrayList);
            }
        } catch (Exception e) {

        }
//        ArrayList ahusArrayList = arrayListHashMap.get("ahus");
//        if (null != ahusArrayList || ahusArrayList.size() > 0)
//            isertFlag=mValdocDatabaseHandler.insertPartnerUser(ValdocDatabaseHandler.PARTNERUSER_TABLE_NAME, createPartnerUserData());
        try {
            ArrayList equipmentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.EQUIPMENT_TABLE_NAME);
            if (null != equipmentsArrayList || equipmentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  3");
                isertFlag = mValdocDatabaseHandler.insertEquipment(ValdocDatabaseHandler.EQUIPMENT_TABLE_NAME, equipmentsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList roomsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.ROOM_TABLE_NAME);
            if (null != roomsArrayList || roomsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  4");
                isertFlag = mValdocDatabaseHandler.insertRoom(ValdocDatabaseHandler.ROOM_TABLE_NAME, roomsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList applicableTestRoomsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TABLE_NAME);
            if (null != applicableTestRoomsArrayList || applicableTestRoomsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  5");
                isertFlag = mValdocDatabaseHandler.insertApplicableTestRoom(ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TABLE_NAME, applicableTestRoomsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList applicableTestEquipmentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TABLE_NAME);
            if (null != applicableTestEquipmentsArrayList || applicableTestEquipmentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  6 hello");
                isertFlag = mValdocDatabaseHandler.insertApplicableTestEquipment(ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TABLE_NAME, applicableTestEquipmentsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList clientInstrumentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TABLE_NAME);
            if (null != clientInstrumentsArrayList || clientInstrumentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  7");
                isertFlag = mValdocDatabaseHandler.insertClientInstrument(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TABLE_NAME, clientInstrumentsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList clientInstrumentsTestArrayList = arrayListHashMap.get(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TEST_TABLE_NAME);
            if (null != clientInstrumentsTestArrayList || clientInstrumentsTestArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  7");
                isertFlag = mValdocDatabaseHandler.insertClientInstrumentTest(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TEST_TABLE_NAME, clientInstrumentsTestArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList partnerInstrumentsTestArrayList = arrayListHashMap.get(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TEST_TABLE_NAME);
            if (null != partnerInstrumentsTestArrayList || partnerInstrumentsTestArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  8");
                isertFlag = mValdocDatabaseHandler.insertPartnerInstrumentTest(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TEST_TABLE_NAME, partnerInstrumentsTestArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList partnerInstrumentsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TABLE_NAME);
            if (null != partnerInstrumentsArrayList || partnerInstrumentsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  8");
                isertFlag = mValdocDatabaseHandler.insertPartnerInstrument(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TABLE_NAME, partnerInstrumentsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList testMasterArrayList = arrayListHashMap.get(ValdocDatabaseHandler.TESTMASTER_TABLE_NAME);
            if (null != testMasterArrayList || testMasterArrayList.size() > 0)
                isertFlag = mValdocDatabaseHandler.insertTestMaster(ValdocDatabaseHandler.TESTMASTER_TABLE_NAME, testMasterArrayList);
        } catch (Exception e) {

        }
        try {
            ArrayList areasArrayList = arrayListHashMap.get(ValdocDatabaseHandler.AREA_TABLE_NAME);
            if (null != areasArrayList || areasArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  9");
                isertFlag = mValdocDatabaseHandler.insertTestArea(ValdocDatabaseHandler.AREA_TABLE_NAME, areasArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList equipmentFiltersArrayList = arrayListHashMap.get(ValdocDatabaseHandler.EQUIPMENTFILTER_TABLE_NAME);
            if (null != equipmentFiltersArrayList || equipmentFiltersArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  10");
                isertFlag = mValdocDatabaseHandler.insertEquipmentfilter(ValdocDatabaseHandler.EQUIPMENTFILTER_TABLE_NAME, equipmentFiltersArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList grillsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.GRILL_TABLE_NAME);
            if (null != grillsArrayList || grillsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  11");
                isertFlag = mValdocDatabaseHandler.insertGrill(ValdocDatabaseHandler.GRILL_TABLE_NAME, grillsArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList roomFiltersArrayList = arrayListHashMap.get(ValdocDatabaseHandler.ROOMFILTER_TABLE_NAME);
            if (null != roomFiltersArrayList || roomFiltersArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  12");
                isertFlag = mValdocDatabaseHandler.insertRoomFilter(ValdocDatabaseHandler.ROOMFILTER_TABLE_NAME, roomFiltersArrayList);
            }
        } catch (Exception e) {

        }
        try {
            ArrayList partnerArrayList = arrayListHashMap.get(ValdocDatabaseHandler.PARTNERS_TABLE_NAME);
            Log.d("VALDOC", "controler response data  13=partnerArrayList=" + partnerArrayList.size());
            if (null != partnerArrayList || partnerArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  13=partnerInstrumentArrayList=" + partnerArrayList.size());
                isertFlag = mValdocDatabaseHandler.insertPartners(ValdocDatabaseHandler.PARTNERS_TABLE_NAME, partnerArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList ahuFilterArrayList = arrayListHashMap.get(ValdocDatabaseHandler.AHU_FILTER_TABLE_NAME);
            if (null != ahuFilterArrayList || ahuFilterArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  13=partnerInstrumentArrayList=" + ahuFilterArrayList.size());
                isertFlag = mValdocDatabaseHandler.insertAhuFilter(ValdocDatabaseHandler.AHU_FILTER_TABLE_NAME, ahuFilterArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList applicableTestAhuArrayList = arrayListHashMap.get(ValdocDatabaseHandler.APLICABLE_TEST_AHU_TABLE_NAME);
            if (null != applicableTestAhuArrayList || applicableTestAhuArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  13=partnerInstrumentArrayList=" + applicableTestAhuArrayList.size());
                isertFlag = mValdocDatabaseHandler.insertApplicableTestAhu(ValdocDatabaseHandler.APLICABLE_TEST_AHU_TABLE_NAME, applicableTestAhuArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList equipmentgrillArrayList = arrayListHashMap.get(ValdocDatabaseHandler.EQUIPMENTGRILL_TABLE_NAME);
            if (null != equipmentgrillArrayList || equipmentgrillArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  13=partnerInstrumentArrayList=" + equipmentgrillArrayList.size());
                isertFlag = mValdocDatabaseHandler.insertEquipmentGrill(ValdocDatabaseHandler.EQUIPMENTGRILL_TABLE_NAME, equipmentgrillArrayList);
            }
        } catch (Exception e) {

        }

        try {
            ArrayList isoParticleLimitsArrayList = arrayListHashMap.get(ValdocDatabaseHandler.ISOPARTICLELIMITS_TABLE_NAME);
            if (null != isoParticleLimitsArrayList || isoParticleLimitsArrayList.size() > 0) {
                Log.d("VALDOC", "controler response data  13=partnerInstrumentArrayList=" + isoParticleLimitsArrayList.size());
                isertFlag = mValdocDatabaseHandler.insertIsoParticleLimits(ValdocDatabaseHandler.ISOPARTICLELIMITS_TABLE_NAME, isoParticleLimitsArrayList);
            }
        } catch (Exception e) {

        }

        return isertFlag;
    }


    @Override
    public void httpPostResponceResult(final String resultData, int statusCode) {
        final int statuscode = statusCode;
        Log.d("VALDOC", "controler httpPostResponceResult response data1  statusCode=" + statusCode);
        Log.d("VALDOC", "controler httpPostResponceResult response data1  resultData=" + resultData);
        AfterLoginActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (statuscode == HttpURLConnection.HTTP_OK) {
                    JSONObject response;
                    try {
                        response = new JSONObject(resultData);
                        if (response.getString("status").equalsIgnoreCase("success")) {
                            if (mValdocDatabaseHandler.deleteTestReportTable()) {
                                aleartDialog("Data synced successfully");
                            } else {
                                aleartDialog("Post Data not synced successfully,Please sync again !");
                            }
                        }
                    } catch (Exception e) {
                        Log.d("valdoc", "AfterLoginActivity reponse exception=" + e.getMessage());
                        aleartDialog("Post Data not synced successfully,Please sync again !");
                    }

                } else {
                    aleartDialog("Post Data not synced successfully,Please sync again !");
                }
            }
        });
    }
}
