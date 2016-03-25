package com.project.valdoc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.ServiceReport;
import com.project.valdoc.intity.ServiceReportDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceReportActivity extends AppCompatActivity implements View.OnClickListener {
    private ValdocDatabaseHandler mValdocDatabaseHandler = new ValdocDatabaseHandler(ServiceReportActivity.this);
    //?
    private String userName = "";
    SharedPreferences sharedpreferences;
    //certificate view id creation
    private EditText plantName;
    private TextView serviceEngineer;
    private EditText timeIn;
    private EditText timeOut;
    private TextView serialNo;
    private TextView date;
    private EditText customerPo;
    private EditText workOrder;
    private TextView customerAddress;//customer_address

    private CheckBox airVelocity;//air_velocity
    private CheckBox airFlow;//air_flow
    private CheckBox hepaFilter;//hepa_filter
    private CheckBox hepaIntegrity;//hepa_integrity
    private CheckBox nonViable_28;//non_viable_28
    private CheckBox nonViable50;//non_viable_50
    private CheckBox nonViable75;//non_viable_75
    private CheckBox nonViable100;//non_viable_100
    private CheckBox areaRecovery;//area_recovery
    private CheckBox airVisualization;//air_visualization
    private CheckBox pressure;//pressure
    private CheckBox airPressure;//air_pressure
    private CheckBox temprature;//temprature
    private CheckBox light;//light
    private CheckBox sound;//sound

    private TextView airVelocityLable;//air_velocity
    private TextView airFlowLable;//air_flow
    private TextView hepaFilterLable;//hepa_filter
    private TextView hepaIntegrityLable;//hepa_integrity
    private TextView nonViable_28Lable;//non_viable_28
    private TextView nonViable50Lable;//non_viable_50
    private TextView nonViable75Lable;//non_viable_75
    private TextView nonViable100Lable;//non_viable_100
    private TextView areaRecoveryLable;//area_recovery
    private TextView airVisualizationLable;//air_visualization
    private TextView pressureLable;//pressure
    private TextView airPressureLable;//air_pressure
    private TextView tempratureLable;//temprature
    private TextView lightLable;//light
    private TextView soundLable;//sound


    private TextView areaofTest1;//areaoftest1
    private TextView areaofTest2;
    private TextView areaofTest3;
    private TextView areaofTest4;
    private TextView areaofTest5;
    private TextView areaofTest6;
    private TextView areaofTest7;
    private TextView areaofTest8;
    private TextView areaofTest9;
    private TextView areaofTest10;
    private TextView areaofTest11;
    private TextView areaofTest12;
    private TextView areaofTest13;
    private TextView areaofTest14;
    private TextView areaofTest15;

    private TextView equipmentAhu1;
    private TextView equipmentAhu2;
    private TextView equipmentAhu3;
    private TextView equipmentAhu4;
    private TextView equipmentAhu5;
    private TextView equipmentAhu6;
    private TextView equipmentAhu7;
    private TextView equipmentAhu8;
    private TextView equipmentAhu9;
    private TextView equipmentAhu10;
    private TextView equipmentAhu11;
    private TextView equipmentAhu12;
    private TextView equipmentAhu13;
    private TextView equipmentAhu14;
    private TextView equipmentAhu15;

    private EditText noOfLocation1;
    private EditText noOfLocation2;
    private EditText noOfLocation3;
    private EditText noOfLocation4;
    private EditText noOfLocation5;
    private EditText noOfLocation6;
    private EditText noOfLocation7;
    private EditText noOfLocation8;
    private EditText noOfLocation9;
    private EditText noOfLocation10;
    private EditText noOfLocation11;
    private EditText noOfLocation12;
    private EditText noOfLocation13;
    private EditText noOfLocation14;
    private EditText noOfLocation15;

    private EditText noOfDaysHrs1;
    private EditText noOfDaysHrs2;
    private EditText noOfDaysHrs3;
    private EditText noOfDaysHrs4;
    private EditText noOfDaysHrs5;
    private EditText noOfDaysHrs6;
    private EditText noOfDaysHrs7;
    private EditText noOfDaysHrs8;
    private EditText noOfDaysHrs9;
    private EditText noOfDaysHrs10;
    private EditText noOfDaysHrs11;
    private EditText noOfDaysHrs12;
    private EditText noOfDaysHrs13;
    private EditText noOfDaysHrs14;
    private EditText noOfDaysHrs15;

    private EditText workStatus;//work_status
    private EditText remark;//remark
    private EditText testerName;//tester_name
    private EditText witnessName;//witness_name
    private EditText witnessDesignation;//witness_designation
    private EditText witnessContact;//witness_contact
    private EditText witnessEmail;//witness_email
    private String mPartnerName;
    private Button submit;

    //    date picker
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_report_entry);
        sharedpreferences = getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.service_report_entry);
        // ui value initialization and assignment
        initTextView();
        textViewValueAssignment();
        datePicker();
        mPartnerName = getPartnerName();
    }


    public void areaPopupList(final TextView date) {
        PopupMenu popup = new PopupMenu(ServiceReportActivity.this, date);
        //Inflating the Popup using xml file
//        arrayList.add("a1");
//        arrayList.add("a2");
//        arrayList.add("a3");
//        arrayList.add("a4");
//        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        List<String> list = mValdocDatabaseHandler.getAreaName();
        for (String s : list) {
            popup.getMenu().add(s.toString());
        }
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                date.setText("" + item.getTitle());
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    public void equipmentAhuList(final TextView date) {
        PopupMenu popup = new PopupMenu(ServiceReportActivity.this, date);
        List<String> list = mValdocDatabaseHandler.getAhuNameInfo();
        List<String> listEquipment = mValdocDatabaseHandler.getEquipmentNameInfo();
        List<String> combined = new ArrayList<String>();
        combined.addAll(listEquipment);
        combined.addAll(list);
        for (String s : combined) {
            popup.getMenu().add(s.toString());
        }
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                date.setText("" + item.getTitle());
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    private void textViewValueAssignment() {
        serviceEngineer.setText("" + sharedpreferences.getString("USERNAME", ""));
        serialNo = (TextView) findViewById(R.id.sr_no);
    }

    private void initTextView() {
        plantName = (EditText) findViewById(R.id.plantname);
        serviceEngineer = (TextView) findViewById(R.id.service_engineer_name);
        timeIn = (EditText) findViewById(R.id.time_in);
        timeOut = (EditText) findViewById(R.id.time_out);
        serialNo = (TextView) findViewById(R.id.sr_no);
        date = (TextView) findViewById(R.id.date);
        customerPo = (EditText) findViewById(R.id.customer_po);
        workOrder = (EditText) findViewById(R.id.work_order);
        customerAddress = (TextView) findViewById(R.id.customer_address);

        airVelocity = (CheckBox) findViewById(R.id.air_velocity);
        airFlow = (CheckBox) findViewById(R.id.air_flow);
        hepaFilter = (CheckBox) findViewById(R.id.hepa_filter);
        hepaIntegrity = (CheckBox) findViewById(R.id.hepa_integrity);
        nonViable_28 = (CheckBox) findViewById(R.id.non_viable_28);
        nonViable50 = (CheckBox) findViewById(R.id.non_viable_50);
        nonViable75 = (CheckBox) findViewById(R.id.non_viable_75);
        nonViable100 = (CheckBox) findViewById(R.id.non_viable_100);
        areaRecovery = (CheckBox) findViewById(R.id.area_recovery);
        airVisualization = (CheckBox) findViewById(R.id.air_visualization);
        pressure = (CheckBox) findViewById(R.id.pressure);
        airPressure = (CheckBox) findViewById(R.id.air_pressure);
        temprature = (CheckBox) findViewById(R.id.temprature);
        light = (CheckBox) findViewById(R.id.light);
        sound = (CheckBox) findViewById(R.id.sound);

        airVelocityLable = (TextView) findViewById(R.id.air_velocity_lable);
        airFlowLable = (TextView) findViewById(R.id.air_flow_lable);
        hepaFilterLable = (TextView) findViewById(R.id.hepa_filter_lable);
        hepaIntegrityLable = (TextView) findViewById(R.id.hepa_integrity_lable);
        nonViable_28Lable = (TextView) findViewById(R.id.non_viable_28_lable);
        nonViable50Lable = (TextView) findViewById(R.id.non_viable_50_lable);
        nonViable75Lable = (TextView) findViewById(R.id.non_viable_75_lable);
        nonViable100Lable = (TextView) findViewById(R.id.non_viable_100_lable);
        areaRecoveryLable = (TextView) findViewById(R.id.area_recovery_lable);
        airVisualizationLable = (TextView) findViewById(R.id.air_visualization_lable);
        pressureLable = (TextView) findViewById(R.id.pressure_lable);
        airPressureLable = (TextView) findViewById(R.id.air_pressure_lable);
        tempratureLable = (TextView) findViewById(R.id.temprature_lable);
        lightLable = (TextView) findViewById(R.id.light_lable);
        soundLable = (TextView) findViewById(R.id.sound_lable);

        areaofTest1 = (TextView) findViewById(R.id.areaoftest1);//
        areaofTest1.setOnClickListener(this);
        areaofTest2 = (TextView) findViewById(R.id.areaoftest2);
        areaofTest2.setOnClickListener(this);
        areaofTest3 = (TextView) findViewById(R.id.areaoftest3);
        areaofTest3.setOnClickListener(this);
        areaofTest4 = (TextView) findViewById(R.id.areaoftest4);
        areaofTest4.setOnClickListener(this);
        areaofTest5 = (TextView) findViewById(R.id.areaoftest5);
        areaofTest5.setOnClickListener(this);
        areaofTest6 = (TextView) findViewById(R.id.areaoftest6);
        areaofTest6.setOnClickListener(this);
        areaofTest7 = (TextView) findViewById(R.id.areaoftest7);
        areaofTest7.setOnClickListener(this);
        areaofTest8 = (TextView) findViewById(R.id.areaoftest8);
        areaofTest8.setOnClickListener(this);
        areaofTest9 = (TextView) findViewById(R.id.areaoftest9);
        areaofTest9.setOnClickListener(this);
        areaofTest10 = (TextView) findViewById(R.id.areaoftest10);
        areaofTest10.setOnClickListener(this);
        areaofTest11 = (TextView) findViewById(R.id.areaoftest11);
        areaofTest11.setOnClickListener(this);
        areaofTest12 = (TextView) findViewById(R.id.areaoftest12);
        areaofTest12.setOnClickListener(this);
        areaofTest13 = (TextView) findViewById(R.id.areaoftest13);
        areaofTest13.setOnClickListener(this);
        areaofTest14 = (TextView) findViewById(R.id.areaoftest14);
        areaofTest14.setOnClickListener(this);
        areaofTest15 = (TextView) findViewById(R.id.areaoftest15);
        areaofTest15.setOnClickListener(this);

        equipmentAhu1 = (TextView) findViewById(R.id.equipmen_ahu1);
        equipmentAhu1.setOnClickListener(this);
        equipmentAhu2 = (TextView) findViewById(R.id.equipmen_ahu2);
        equipmentAhu2.setOnClickListener(this);
        equipmentAhu3 = (TextView) findViewById(R.id.equipmen_ahu3);
        equipmentAhu3.setOnClickListener(this);
        equipmentAhu4 = (TextView) findViewById(R.id.equipmen_ahu4);
        equipmentAhu4.setOnClickListener(this);
        equipmentAhu5 = (TextView) findViewById(R.id.equipmen_ahu5);
        equipmentAhu5.setOnClickListener(this);
        equipmentAhu6 = (TextView) findViewById(R.id.equipmen_ahu6);
        equipmentAhu6.setOnClickListener(this);
        equipmentAhu7 = (TextView) findViewById(R.id.equipmen_ahu7);
        equipmentAhu7.setOnClickListener(this);
        equipmentAhu8 = (TextView) findViewById(R.id.equipmen_ahu8);
        equipmentAhu8.setOnClickListener(this);
        equipmentAhu9 = (TextView) findViewById(R.id.equipmen_ahu9);
        equipmentAhu9.setOnClickListener(this);
        equipmentAhu10 = (TextView) findViewById(R.id.equipmen_ahu10);
        equipmentAhu10.setOnClickListener(this);
        equipmentAhu11 = (TextView) findViewById(R.id.equipmen_ahu11);
        equipmentAhu11.setOnClickListener(this);
        equipmentAhu12 = (TextView) findViewById(R.id.equipmen_ahu12);
        equipmentAhu12.setOnClickListener(this);
        equipmentAhu13 = (TextView) findViewById(R.id.equipmen_ahu13);
        equipmentAhu13.setOnClickListener(this);
        equipmentAhu14 = (TextView) findViewById(R.id.equipmen_ahu14);
        equipmentAhu14.setOnClickListener(this);
        equipmentAhu15 = (TextView) findViewById(R.id.equipmen_ahu15);
        equipmentAhu15.setOnClickListener(this);

        noOfLocation1 = (EditText) findViewById(R.id.no_of_location1);
        noOfLocation2 = (EditText) findViewById(R.id.no_of_location2);
        noOfLocation3 = (EditText) findViewById(R.id.no_of_location3);
        noOfLocation4 = (EditText) findViewById(R.id.no_of_location4);
        noOfLocation5 = (EditText) findViewById(R.id.no_of_location5);
        noOfLocation6 = (EditText) findViewById(R.id.no_of_location6);
        noOfLocation7 = (EditText) findViewById(R.id.no_of_location7);
        noOfLocation8 = (EditText) findViewById(R.id.no_of_location8);
        noOfLocation9 = (EditText) findViewById(R.id.no_of_location9);
        noOfLocation10 = (EditText) findViewById(R.id.no_of_location10);
        noOfLocation11 = (EditText) findViewById(R.id.no_of_location11);
        noOfLocation12 = (EditText) findViewById(R.id.no_of_location12);
        noOfLocation13 = (EditText) findViewById(R.id.no_of_location13);
        noOfLocation14 = (EditText) findViewById(R.id.no_of_location14);
        noOfLocation15 = (EditText) findViewById(R.id.no_of_location15);

        noOfDaysHrs1 = (EditText) findViewById(R.id.no_of_days_hrs1);
        noOfDaysHrs2 = (EditText) findViewById(R.id.no_of_days_hrs2);
        noOfDaysHrs3 = (EditText) findViewById(R.id.no_of_days_hrs3);
        noOfDaysHrs4 = (EditText) findViewById(R.id.no_of_days_hrs4);
        noOfDaysHrs5 = (EditText) findViewById(R.id.no_of_days_hrs5);
        noOfDaysHrs6 = (EditText) findViewById(R.id.no_of_days_hrs6);
        noOfDaysHrs7 = (EditText) findViewById(R.id.no_of_days_hrs7);
        noOfDaysHrs8 = (EditText) findViewById(R.id.no_of_days_hrs8);
        noOfDaysHrs9 = (EditText) findViewById(R.id.no_of_days_hrs9);
        noOfDaysHrs10 = (EditText) findViewById(R.id.no_of_days_hrs10);
        noOfDaysHrs11 = (EditText) findViewById(R.id.no_of_days_hrs11);
        noOfDaysHrs12 = (EditText) findViewById(R.id.no_of_days_hrs12);
        noOfDaysHrs13 = (EditText) findViewById(R.id.no_of_days_hrs13);
        noOfDaysHrs14 = (EditText) findViewById(R.id.no_of_days_hrs14);
        noOfDaysHrs15 = (EditText) findViewById(R.id.no_of_days_hrs15);

        workStatus = (EditText) findViewById(R.id.work_status);
        remark = (EditText) findViewById(R.id.remark);
        testerName = (EditText) findViewById(R.id.tester_name);
        witnessName = (EditText) findViewById(R.id.witness_name);
        witnessDesignation = (EditText) findViewById(R.id.witness_designation);
        witnessContact = (EditText) findViewById(R.id.witness_contact);
        witnessEmail = (EditText) findViewById(R.id.witness_email);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // On button click show datepicker dialog
                showDialog(DATE_PICKER_ID);
            }
        });
    }

    private ServiceReport serviceReportCreation() {
        ServiceReport serviceReport = new ServiceReport();
        serviceReport.setId(1);
        serviceReport.setCustomer("" + customerAddress.getText().toString());
        serviceReport.setServiceEngineer("" + serviceEngineer.getText().toString());
        serviceReport.setTimeIn("" + timeIn.getText().toString());
        serviceReport.setTimeOut("" + timeOut.getText().toString());
        serviceReport.setSrNo("" + serialNo.getText().toString());
        serviceReport.setCustomerPO("" + customerPo.getText().toString());
        serviceReport.setPlant("" + plantName.getText().toString());
        serviceReport.setStatus("" + workStatus.getText().toString());
        serviceReport.setTesterName("" + testerName.getText().toString());
        serviceReport.setWitnessName("" + witnessName.getText().toString());
        serviceReport.setWitnessContact("" + witnessContact.getText().toString());
        serviceReport.setWitnessMail("" + witnessEmail.getText().toString());
        serviceReport.setWitnessDesignation("" + witnessDesignation.getText().toString());
        serviceReport.setPartnerName("" + mPartnerName);
        serviceReport.setServiceDate("" + date.getText().toString());
        serviceReport.setRemark("" + remark.getText().toString());
        serviceReport.setWorkOrder("" + workOrder.getText().toString());
        return serviceReport;
    }

    private ArrayList serviceReportDetailsCreation() {
        ArrayList serviceReportDetailsList = new ArrayList();

//        test 1
        if (airVelocity.isChecked()) {
            ServiceReportDetail serviceReportDetail = new ServiceReportDetail();
            serviceReportDetail.setId(1);
            serviceReportDetail.setService_report_id(1);
            serviceReportDetail.setTypeOfTest("" + airVelocityLable.getText().toString());
            serviceReportDetail.setAreaOfTest("" + areaofTest1.getText().toString());
            serviceReportDetail.setEquipmentAhuNo("" + equipmentAhu1.getText().toString());
            String noOflocation = noOfLocation1.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation1.getText().toString());
                serviceReportDetail.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail.setNoOfHourDays("" + noOfDaysHrs1.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail);
        }
//       test 2
        if (airFlow.isChecked()) {
            ServiceReportDetail serviceReportDetail2 = new ServiceReportDetail();
            serviceReportDetail2.setId(2);
            serviceReportDetail2.setService_report_id(1);
            serviceReportDetail2.setTypeOfTest("" + airFlowLable.getText().toString());
            serviceReportDetail2.setAreaOfTest("" + areaofTest2.getText().toString());
            serviceReportDetail2.setEquipmentAhuNo("" + equipmentAhu2.getText().toString());
            String noOflocation = noOfLocation2.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation2.getText().toString());
                serviceReportDetail2.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail2.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail2.setNoOfHourDays("" + noOfDaysHrs2.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail2);
        }

//        /        test 3
        if (hepaFilter.isChecked()) {
            ServiceReportDetail serviceReportDetail3 = new ServiceReportDetail();
            serviceReportDetail3.setId(3);
            serviceReportDetail3.setService_report_id(1);
            serviceReportDetail3.setTypeOfTest("" + hepaFilterLable.getText().toString());
            serviceReportDetail3.setAreaOfTest("" + areaofTest3.getText().toString());
            serviceReportDetail3.setEquipmentAhuNo("" + equipmentAhu3.getText().toString());
            String noOflocation = noOfLocation3.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation3.getText().toString());
                serviceReportDetail3.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail3.setNoOfLoc(noOfLoc);
            }

            serviceReportDetail3.setNoOfHourDays("" + noOfDaysHrs3.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail3);
        }

//        /        test 4
        if (hepaIntegrity.isChecked()) {
            ServiceReportDetail serviceReportDetail4 = new ServiceReportDetail();
            serviceReportDetail4.setId(4);
            serviceReportDetail4.setService_report_id(1);
            serviceReportDetail4.setTypeOfTest("" + hepaIntegrityLable.getText().toString());
            serviceReportDetail4.setAreaOfTest("" + areaofTest4.getText().toString());
            serviceReportDetail4.setEquipmentAhuNo("" + equipmentAhu4.getText().toString());
            String noOflocation = noOfLocation4.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation4.getText().toString());
                serviceReportDetail4.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail4.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail4.setNoOfLoc(Integer.parseInt(noOfLocation4.getText().toString()));
            serviceReportDetail4.setNoOfHourDays("" + noOfDaysHrs4.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail4);
        }

//        /        test 5
        if (nonViable_28.isChecked()) {
            ServiceReportDetail serviceReportDetail5 = new ServiceReportDetail();
            serviceReportDetail5.setId(5);
            serviceReportDetail5.setService_report_id(1);
            serviceReportDetail5.setTypeOfTest("" + nonViable_28Lable.getText().toString());
            serviceReportDetail5.setAreaOfTest("" + areaofTest5.getText().toString());
            serviceReportDetail5.setEquipmentAhuNo("" + equipmentAhu5.getText().toString());
            String noOflocation = noOfLocation5.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation5.getText().toString());
                serviceReportDetail5.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail5.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail5.setNoOfHourDays("" + noOfDaysHrs5.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail5);
        }

//        /        test 6
        if (nonViable50.isChecked()) {
            ServiceReportDetail serviceReportDetail6 = new ServiceReportDetail();
            serviceReportDetail6.setId(6);
            serviceReportDetail6.setService_report_id(1);
            serviceReportDetail6.setTypeOfTest("" + nonViable50Lable.getText().toString());
            serviceReportDetail6.setAreaOfTest("" + areaofTest6.getText().toString());
            serviceReportDetail6.setEquipmentAhuNo("" + equipmentAhu6.getText().toString());
            String noOflocation = noOfLocation6.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation6.getText().toString());
                serviceReportDetail6.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail6.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail6.setNoOfLoc(Integer.parseInt(noOfLocation6.getText().toString()));
            serviceReportDetail6.setNoOfHourDays("" + noOfDaysHrs6.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail6);
        }

//        /        test 7
        if (nonViable75.isChecked()) {
            ServiceReportDetail serviceReportDetail7 = new ServiceReportDetail();
            serviceReportDetail7.setId(7);
            serviceReportDetail7.setService_report_id(1);
            serviceReportDetail7.setTypeOfTest("" + nonViable75Lable.getText().toString());
            serviceReportDetail7.setAreaOfTest("" + areaofTest7.getText().toString());
            serviceReportDetail7.setEquipmentAhuNo("" + equipmentAhu7.getText().toString());
            String noOflocation = noOfLocation7.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation7.getText().toString());
                serviceReportDetail7.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail7.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail7.setNoOfLoc(Integer.parseInt(noOfLocation7.getText().toString()));
            serviceReportDetail7.setNoOfHourDays("" + noOfDaysHrs7.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail7);
        }

//        /        test 8
        if (nonViable100.isChecked()) {
            ServiceReportDetail serviceReportDetail8 = new ServiceReportDetail();
            serviceReportDetail8.setId(8);
            serviceReportDetail8.setService_report_id(1);
            serviceReportDetail8.setTypeOfTest("" + nonViable100Lable.getText().toString());
            serviceReportDetail8.setAreaOfTest("" + areaofTest8.getText().toString());
            serviceReportDetail8.setEquipmentAhuNo("" + equipmentAhu8.getText().toString());
            String noOflocation = noOfDaysHrs8.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfDaysHrs8.getText().toString());
                serviceReportDetail8.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail8.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail8.setNoOfLoc(Integer.parseInt(noOfLocation8.getText().toString()));
            serviceReportDetail8.setNoOfHourDays("" + noOfDaysHrs8.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail8);
        }

//        /        test 9
        if (areaRecovery.isChecked()) {
            ServiceReportDetail serviceReportDetail9 = new ServiceReportDetail();
            serviceReportDetail9.setId(9);
            serviceReportDetail9.setService_report_id(1);
            serviceReportDetail9.setTypeOfTest("" + areaRecoveryLable.getText().toString());
            serviceReportDetail9.setAreaOfTest("" + areaofTest9.getText().toString());
            serviceReportDetail9.setEquipmentAhuNo("" + equipmentAhu9.getText().toString());
            String noOflocation = noOfDaysHrs9.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfDaysHrs9.getText().toString());
                serviceReportDetail9.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail9.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail9.setNoOfHourDays("" + noOfDaysHrs9.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail9);
        }

//        /        test 10
        if (airVisualization.isChecked()) {
            ServiceReportDetail serviceReportDetail10 = new ServiceReportDetail();
            serviceReportDetail10.setId(10);
            serviceReportDetail10.setService_report_id(1);
            serviceReportDetail10.setTypeOfTest("" + airVisualizationLable.getText().toString());
            serviceReportDetail10.setAreaOfTest("" + areaofTest10.getText().toString());
            serviceReportDetail10.setEquipmentAhuNo("" + equipmentAhu10.getText().toString());
            String noOflocation = noOfLocation10.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation10.getText().toString());
                serviceReportDetail10.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail10.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail10.setNoOfHourDays("" + noOfDaysHrs10.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail10);
        }

//        /        test 11
        if (pressure.isChecked()) {
            ServiceReportDetail serviceReportDetail11 = new ServiceReportDetail();
            serviceReportDetail11.setId(11);
            serviceReportDetail11.setService_report_id(1);
            serviceReportDetail11.setTypeOfTest("" + pressureLable.getText().toString());
            serviceReportDetail11.setAreaOfTest("" + areaofTest11.getText().toString());
            serviceReportDetail11.setEquipmentAhuNo("" + equipmentAhu11.getText().toString());
            String noOflocation = noOfLocation11.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation11.getText().toString());
                serviceReportDetail11.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail11.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail11.setNoOfHourDays("" + noOfDaysHrs11.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail11);
        }

//        /        test 12
        if (airPressure.isChecked()) {
            ServiceReportDetail serviceReportDetail12 = new ServiceReportDetail();
            serviceReportDetail12.setId(12);
            serviceReportDetail12.setService_report_id(1);
            serviceReportDetail12.setTypeOfTest("" + airPressureLable.getText().toString());
            serviceReportDetail12.setAreaOfTest("" + areaofTest12.getText().toString());
            serviceReportDetail12.setEquipmentAhuNo("" + equipmentAhu12.getText().toString());
            String noOflocation = noOfLocation12.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation12.getText().toString());
                serviceReportDetail12.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail12.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail12.setNoOfHourDays("" + noOfDaysHrs12.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail12);
        }

//        /        test 13
        if (temprature.isChecked()) {
            ServiceReportDetail serviceReportDetail13 = new ServiceReportDetail();
            serviceReportDetail13.setId(13);
            serviceReportDetail13.setService_report_id(1);
            serviceReportDetail13.setTypeOfTest("" + tempratureLable.getText().toString());
            serviceReportDetail13.setAreaOfTest("" + areaofTest13.getText().toString());
            serviceReportDetail13.setEquipmentAhuNo("" + equipmentAhu13.getText().toString());
            String noOflocation = noOfLocation13.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation13.getText().toString());
                serviceReportDetail13.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail13.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail13.setNoOfHourDays("" + noOfDaysHrs13.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail13);
        }

//        /        test 14
        if (light.isChecked()) {
            ServiceReportDetail serviceReportDetail14 = new ServiceReportDetail();
            serviceReportDetail14.setId(14);
            serviceReportDetail14.setService_report_id(1);
            serviceReportDetail14.setTypeOfTest("" + lightLable.getText().toString());
            serviceReportDetail14.setAreaOfTest("" + areaofTest14.getText().toString());
            serviceReportDetail14.setEquipmentAhuNo("" + equipmentAhu14.getText().toString());
            String noOflocation = noOfLocation14.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation14.getText().toString());
                serviceReportDetail14.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail14.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail14.setNoOfHourDays("" + noOfDaysHrs14.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail14);
        }

//        /        test 15
        if (sound.isChecked()) {
            ServiceReportDetail serviceReportDetail15 = new ServiceReportDetail();
            serviceReportDetail15.setId(15);
            serviceReportDetail15.setService_report_id(1);
            serviceReportDetail15.setTypeOfTest("" + soundLable.getText().toString());
            serviceReportDetail15.setAreaOfTest("" + areaofTest15.getText().toString());
            serviceReportDetail15.setEquipmentAhuNo("" + equipmentAhu15.getText().toString());
            String noOflocation = noOfLocation15.getText().toString();
            int noOfLoc=0;
            if (null != noOflocation || noOflocation.length() > 0) {
                noOfLoc = Integer.parseInt(noOfLocation15.getText().toString());
                serviceReportDetail15.setNoOfLoc(noOfLoc);
            }else{
                serviceReportDetail15.setNoOfLoc(noOfLoc);
            }
            serviceReportDetail15.setNoOfHourDays("" + noOfDaysHrs15.getText().toString());
            serviceReportDetailsList.add(serviceReportDetail15);
        }

        return serviceReportDetailsList;
    }

    private String getPartnerName() {
        String partnerName = "";
        int partnerId = sharedpreferences.getInt("PARTNERID", 0);
        if (partnerId != 0)
            partnerName = mValdocDatabaseHandler.getPartnerNameInfo(partnerId);
        return partnerName;
    }

    private void datePicker() {
        // Get current date by calender
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

//        Sr No
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
        int mon = month + 1;
        serialNo.setText("SR/" + mon + "/" + year+"/"+formattedDate);
        // Show current date

        date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
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

            // Show selected date
            date.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.areaoftest1:
                areaPopupList(areaofTest1);
                break;
            case R.id.areaoftest2:
                areaPopupList(areaofTest2);
                break;
            case R.id.areaoftest3:
                areaPopupList(areaofTest3);
                break;
            case R.id.areaoftest4:
                areaPopupList(areaofTest4);
                break;
            case R.id.areaoftest5:
                areaPopupList(areaofTest5);
                break;
            case R.id.areaoftest6:
                areaPopupList(areaofTest6);
                break;
            case R.id.areaoftest7:
                areaPopupList(areaofTest7);
                break;
            case R.id.areaoftest8:
                areaPopupList(areaofTest8);
                break;
            case R.id.areaoftest9:
                areaPopupList(areaofTest9);
                break;
            case R.id.areaoftest10:
                areaPopupList(areaofTest10);
                break;
            case R.id.areaoftest11:
                areaPopupList(areaofTest11);
                break;
            case R.id.areaoftest12:
                areaPopupList(areaofTest12);
                break;
            case R.id.areaoftest13:
                areaPopupList(areaofTest13);
                break;
            case R.id.areaoftest14:
                areaPopupList(areaofTest14);
                break;
            case R.id.areaoftest15:
                areaPopupList(areaofTest15);
                break;
            case R.id.equipmen_ahu1:
                equipmentAhuList(equipmentAhu1);
                break;
            case R.id.equipmen_ahu2:
                equipmentAhuList(equipmentAhu2);
                break;
            case R.id.equipmen_ahu3:
                equipmentAhuList(equipmentAhu3);
                break;
            case R.id.equipmen_ahu4:
                equipmentAhuList(equipmentAhu4);
                break;
            case R.id.equipmen_ahu5:
                equipmentAhuList(equipmentAhu5);
                break;
            case R.id.equipmen_ahu6:
                equipmentAhuList(equipmentAhu6);
                break;
            case R.id.equipmen_ahu7:
                equipmentAhuList(equipmentAhu7);
                break;
            case R.id.equipmen_ahu8:
                equipmentAhuList(equipmentAhu8);
                break;
            case R.id.equipmen_ahu9:
                equipmentAhuList(equipmentAhu9);
                break;
            case R.id.equipmen_ahu10:
                equipmentAhuList(equipmentAhu10);
                break;
            case R.id.equipmen_ahu11:
                equipmentAhuList(equipmentAhu11);
                break;
            case R.id.equipmen_ahu12:
                equipmentAhuList(equipmentAhu12);
                break;
            case R.id.equipmen_ahu13:
                equipmentAhuList(equipmentAhu13);
                break;
            case R.id.equipmen_ahu14:
                equipmentAhuList(equipmentAhu14);
                break;
            case R.id.equipmen_ahu15:
                equipmentAhuList(equipmentAhu15);
                break;
            case R.id.submit:

                if (mValdocDatabaseHandler.insertServiceReport(ValdocDatabaseHandler.SERVICE_REPORT_TABLE_NAME, serviceReportCreation())) {
                    if (mValdocDatabaseHandler.insertServiceReportDetails(ValdocDatabaseHandler.SERVICE_REPORT_DETAIL_TABLE_NAME, serviceReportDetailsCreation())) {
                        Toast.makeText(ServiceReportActivity.this, "Data saved sussessfully", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(ServiceReportActivity.this, AfterLoginActivity.class);
//                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ServiceReportActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(ServiceReportActivity.this, "Data not saved", Toast.LENGTH_LONG).show();
                }
                break;
            //handle multiple view click events
        }
    }
}
