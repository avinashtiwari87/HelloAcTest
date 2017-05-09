package com.project.valdoc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.Area;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.Grill;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.TestMaster;
import com.project.valdoc.utility.Utilityies;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends Activity {
    SharedPreferences sharedpreferences;
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(SplashScreen.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        // Execute some code after 2 seconds have passed
//        if (sharedpreferences.getBoolean("table", false) == false)
//            insertDataInTable();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (sharedpreferences.getBoolean(Utilityies.SERVER_SETTING, false) == false) {
                    Intent intent = new Intent(SplashScreen.this, ServerSettingActivity.class);
                    startActivity(intent);
                    finish();
                } else if (sharedpreferences.getBoolean("login", false) == false) {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreen.this, AfterLoginActivity.class);
                    intent.putExtra("USERNAME", sharedpreferences.getString("USERNAME", ""));
                    intent.putExtra("USERTYPE", sharedpreferences.getString("USERTYPE", ""));
                    intent.putExtra("APPUSERID", sharedpreferences.getInt("APPUSERID", 0));
                    startActivity(intent);
                }
            }
        }, 5000);
    }


    //create test master data
    private List<TestMaster> createTestMasterData() {
        ArrayList<TestMaster> testMasterArrayList = new ArrayList<TestMaster>();
        TestMaster testMaster = new TestMaster();
        testMaster.setTestid(1);
        testMaster.setTestName("testName");
        testMaster.setSpecification("testSpesification");
        testMaster.setCreationDate("2-feb-2016");
        testMasterArrayList.add(testMaster);

        //2nd data
        TestMaster testMaster1 = new TestMaster();
        testMaster1.setTestid(2);
        testMaster1.setTestName("testName1");
        testMaster1.setSpecification("testSpesification1");
        testMaster1.setCreationDate("4-feb-2016");
        testMasterArrayList.add(testMaster1);
        return testMasterArrayList;
    }


    //create applicable test equipment data
    private List<ApplicableTestEquipment> createApplicableTestEquipmentData() {
        ArrayList<ApplicableTestEquipment> applicableTestEquipmentArrayList = new ArrayList<ApplicableTestEquipment>();
        ApplicableTestEquipment applicableTestEquipment = new ApplicableTestEquipment();
        applicableTestEquipment.setAplicable_testId(1);
        applicableTestEquipment.setEquipmentId(1);
        applicableTestEquipment.setTestName("RD_AV_5");
        applicableTestEquipment.setPeriodicity("priodicity");
        applicableTestEquipment.setLocation(4);
        applicableTestEquipment.setNoOfCycle(5);
        applicableTestEquipment.setLastUpdatedDate("2-feb-2016");
        applicableTestEquipmentArrayList.add(applicableTestEquipment);

        return applicableTestEquipmentArrayList;
    }

    //create ahu data
//    private List<Ahu> createAhuData() {
//        ArrayList<Ahu> ahuArrayList = new ArrayList<Ahu>();
//        Ahu ahu = new Ahu();
//        ahu.setAhuId(1);
//        ahu.setAhuType("ahutype");
//        ahu.setCapacity(100);
//        ahu.setReturnAirCFM(101);
//        ahu.setExhaustAirCFM(102);
//        ahu.setBleedFilterType("bleedfiltertype");
//        ahu.setBleedFilterEfficiency(103);
//        ahu.setBleedAirCFM(104);
//        ahu.setBleedFilterQty(2);
//        ahu.setBleedFilterSize("20ft");
//        ahu.setFreshFilterType("freshfiltertype");
//        ahu.setFreshAirCFM(105);
//        ahu.setFreshFilterQty(2);
//        ahu.setFreshFilterSize("22ft");
//        ahu.setAhuHEPAFilterQty(2);
//        ahu.setHepaFilterEfficiency(106);
//        ahu.setHepaParticleSize("23ft");
//        ahu.setHepaFilterEfficiency(107);
//        ahu.setCreationDate("2-feb-2016");
//        ahuArrayList.add(ahu);
//        // 2nd ahu
//        Ahu ahu1 = new Ahu();
//        ahu1.setAhuId(2);
//        ahu1.setAhuType("ahutype1");
//        ahu1.setCapacity(1001);
//        ahu1.setReturnAirCFM(1011);
//        ahu1.setExhaustAirCFM(1021);
//        ahu1.setBleedFilterType("bleedfiltertype1");
//        ahu1.setBleedFilterEfficiency(1031);
//        ahu1.setBleedAirCFM(1041);
//        ahu1.setBleedFilterQty(21);
//        ahu1.setBleedFilterSize("201ft");
//        ahu1.setFreshFilterType("freshfiltertype1");
//        ahu1.setFreshAirCFM(1051);
//        ahu1.setFreshFilterQty(21);
//        ahu1.setFreshFilterSize("221ft");
//        ahu1.setAhuHEPAFilterQty(21);
//        ahu1.setHepaFilterEfficiency(1061);
//        ahu1.setHepaParticleSize("231ft");
//        ahu1.setHepaFilterEfficiency(1071);
//        ahuArrayList.add(ahu1);
//        return ahuArrayList;
//
//    }

    @Override
    protected void onStop() {
        mValdocDatabaseHandler.close();
        super.onStop();
    }
}



