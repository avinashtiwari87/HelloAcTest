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
                if (sharedpreferences.getBoolean("login", false) == false) {
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


    //table creation

    //create ahu table
    private void insertDataInTable() {

        mValdocDatabaseHandler.insertAhu(ValdocDatabaseHandler.AHU_TABLE_NAME, createAhuData());
        mValdocDatabaseHandler.insertEquipment(ValdocDatabaseHandler.EQUIPMENT_TABLE_NAME, createEquipmentData());
        mValdocDatabaseHandler.insertRoom(ValdocDatabaseHandler.ROOM_TABLE_NAME, createRoomData());
        mValdocDatabaseHandler.insertApplicableTestRoom(ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TABLE_NAME, createApplicableTestRoomData());
        mValdocDatabaseHandler.insertApplicableTestEquipment(ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TABLE_NAME, createApplicableTestEquipmentData());
        mValdocDatabaseHandler.insertClientInstrument(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TABLE_NAME, createClientInstrumentData());
        mValdocDatabaseHandler.insertPartnerInstrument(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TABLE_NAME, createPartnerInstrumentData());
        mValdocDatabaseHandler.insertTestMaster(ValdocDatabaseHandler.TESTMASTER_TABLE_NAME, createTestMasterData());
        mValdocDatabaseHandler.insertTestArea(ValdocDatabaseHandler.AREA_TABLE_NAME, createAreaData());
        mValdocDatabaseHandler.insertEquipmentfilter(ValdocDatabaseHandler.EQUIPMENTFILTER_TABLE_NAME, createEquipmentfilterData());
        mValdocDatabaseHandler.insertGrill(ValdocDatabaseHandler.GRILL_TABLE_NAME, createGrilData());
        mValdocDatabaseHandler.insertRoomFilter(ValdocDatabaseHandler.ROOMFILTER_TABLE_NAME, createRoomFilterData());

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean("table", true);
        editor.commit();
//        if (tableExist(ValdocDatabaseHandler.USER_TABLE_NAME,valdocDatabaseHandler)==0) {

//        }
        // for (User user : mValdocDatabaseHandler.getUserInfo())
        //   Log.d("valdoc", "Login :" + "user details" + user.getId() + "\n" + user.getName() + "\n" + user.getPassword() + "\n" + user.getType());
    }

    // create roomFilter data
    private List<RoomFilter> createRoomFilterData() {
        ArrayList<RoomFilter> filterArrayList = new ArrayList<RoomFilter>();
        RoomFilter roomFilter = new RoomFilter();
        roomFilter.setFilterId(1);
        roomFilter.setFilterType("filter type");
        roomFilter.setEfficiency(12.0);
        roomFilter.setFilterCode("filter code");
        roomFilter.setParticleSize("particale size");
        roomFilter.setRoomId(1);
        roomFilter.setSpecification(14);
        roomFilter.setLength(12);
        roomFilter.setWidth(15);
        roomFilter.setGrillArea(18);
        roomFilter.setEffectiveGrillArea(3);
        roomFilter.setIsSupplyFilter(5);
        roomFilter.setCreationDate("2-feb-2016");
        filterArrayList.add(roomFilter);

        //2nd data
        RoomFilter roomFilter1 = new RoomFilter();
        roomFilter1.setFilterId(2);
        roomFilter1.setFilterType("filter type1");
        roomFilter1.setEfficiency(12.0);
        roomFilter1.setFilterCode("filter code1");
        roomFilter1.setParticleSize("particale size1");
        roomFilter1.setRoomId(2);
        roomFilter1.setSpecification(141);
        roomFilter1.setLength(121);
        roomFilter1.setWidth(151);
        roomFilter1.setGrillArea(181);
        roomFilter1.setEffectiveGrillArea(31);
        roomFilter1.setIsSupplyFilter(51);
        roomFilter1.setCreationDate("3-feb-2016");
        filterArrayList.add(roomFilter1);

        //3rd data
        RoomFilter roomFilter12 = new RoomFilter();
        roomFilter12.setFilterId(2);
        roomFilter12.setFilterType("filter type12");
        roomFilter12.setEfficiency(12.0);
        roomFilter12.setFilterCode("filter code12");
        roomFilter12.setParticleSize("particale size1");
        roomFilter12.setRoomId(2);
        roomFilter12.setSpecification(141);
        roomFilter12.setLength(121);
        roomFilter12.setWidth(151);
        roomFilter12.setGrillArea(181);
        roomFilter12.setEffectiveGrillArea(31);
        roomFilter12.setIsSupplyFilter(51);
        roomFilter12.setCreationDate("3-feb-2016");
        filterArrayList.add(roomFilter12);

        return filterArrayList;
    }

    // create grill data
    private List<Grill> createGrilData() {
        ArrayList<Grill> grillArrayList = new ArrayList<Grill>();
        Grill grill = new Grill();
        grill.setGrillId(1);
        grill.setGrillCode("GRILL Code");
        grill.setRoomId(1);
        grill.setWidh(14);
        grill.setLength(12);
        grill.setEffectiveArea(15);
        grill.setIsSupplyGrill(18);
        grill.setAdditionalDetail(3);
        grill.setCreationDate("2-feb-2016");
        grillArrayList.add(grill);

        //2nd data
        Grill grill1 = new Grill();
        grill1.setGrillId(2);
        grill1.setGrillCode("GRILL Code1");
        grill1.setRoomId(2);
        grill1.setWidh(14);
        grill1.setLength(12);
        grill1.setEffectiveArea(15);
        grill1.setIsSupplyGrill(18);
        grill1.setAdditionalDetail(3);
        grill1.setCreationDate("3-feb-2016");
        grillArrayList.add(grill1);

        //3rd data
        Grill grill12 = new Grill();
        grill12.setGrillId(2);
        grill12.setGrillCode("GRILL Code12");
        grill12.setRoomId(2);
        grill12.setWidh(14);
        grill12.setLength(12);
        grill12.setEffectiveArea(15);
        grill12.setIsSupplyGrill(18);
        grill12.setAdditionalDetail(3);
        grill12.setCreationDate("3-feb-2016");
        grillArrayList.add(grill12);

        //4th data
        Grill grill123 = new Grill();
        grill123.setGrillId(2);
        grill123.setGrillCode("GRILL Code12");
        grill123.setRoomId(2);
        grill123.setWidh(14);
        grill123.setLength(12);
        grill123.setEffectiveArea(15);
        grill123.setIsSupplyGrill(18);
        grill123.setAdditionalDetail(3);
        grill123.setCreationDate("3-feb-2016");
        grillArrayList.add(grill123);
        return grillArrayList;
    }


    // create createEquipmentfilterData
    private List<EquipmentFilter> createEquipmentfilterData() {
        ArrayList<EquipmentFilter> equipmentFilterArrayList = new ArrayList<EquipmentFilter>();
        EquipmentFilter equipmentFilter = new EquipmentFilter();
        equipmentFilter.setFilterId(1);
        equipmentFilter.setFilterCode("FilterCode");
        equipmentFilter.setEquipmentId(1);
        equipmentFilter.setWidth(14);
        equipmentFilter.setLength(12);
        equipmentFilter.setGrillArea(15);
        equipmentFilter.setEffectiveGrillArea(18);
        equipmentFilter.setIsSupplyFilter(3);
        equipmentFilter.setCreationDate("2-feb-2016");
        equipmentFilterArrayList.add(equipmentFilter);

        //2nd data
        EquipmentFilter equipmentFilter1 = new EquipmentFilter();
        equipmentFilter1.setFilterId(2);
        equipmentFilter1.setFilterCode("FilterCode1");
        equipmentFilter1.setEquipmentId(2);
        equipmentFilter1.setWidth(14);
        equipmentFilter1.setLength(12);
        equipmentFilter1.setGrillArea(15);
        equipmentFilter1.setEffectiveGrillArea(18);
        equipmentFilter1.setIsSupplyFilter(3);
        equipmentFilter1.setCreationDate("3-feb-2016");
        equipmentFilterArrayList.add(equipmentFilter1);
        return equipmentFilterArrayList;
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

    //create test master data
    private List<Area> createAreaData() {
        ArrayList<Area> areaArrayList = new ArrayList<Area>();
        Area area = new Area();
        area.setAreaId(1);
        area.setPlantId(1);
        area.setAreaName("AreaName");
        area.setAdditionalDetails("aditionaldetails");
        area.setCreationDate("2-feb-2016");
        areaArrayList.add(area);

        //2nd data
        Area area1 = new Area();
        area1.setAreaId(2);
        area1.setPlantId(2);
        area1.setAreaName("AreaName1");
        area1.setAdditionalDetails("aditionaldetails1");
        area1.setCreationDate("5-feb-2016");
        areaArrayList.add(area1);
        return areaArrayList;
    }

    //create client instrument data
    private List<ClientInstrument> createClientInstrumentData() {
        ArrayList<ClientInstrument> equipmentArrayList = new ArrayList<ClientInstrument>();
        ClientInstrument clientInstrument = new ClientInstrument();
        clientInstrument.setcInstrumentId(1);
        clientInstrument.setInstrumentId("InstrumentId");
        clientInstrument.setSerialNo("SerialNo");
        clientInstrument.setcInstrumentName("cInstrumentName");
        clientInstrument.setMake("Make");
        clientInstrument.setModel("Model");
        clientInstrument.setLastCalibrated("2-feb-2016");
        clientInstrument.setCalibrationDueDate("28-feb-2016");
        clientInstrument.setCurrentLocation("current location");
        clientInstrument.setStatus("status");
        clientInstrument.setTestId(1);
        clientInstrument.setCreationDate("2-feb-2016");
        equipmentArrayList.add(clientInstrument);

        //2nd data
        ClientInstrument clientInstrument1 = new ClientInstrument();
        clientInstrument1.setcInstrumentId(2);
        clientInstrument1.setInstrumentId("InstrumentId1");
        clientInstrument1.setSerialNo("SerialNo1");
        clientInstrument1.setcInstrumentName("cInstrumentName1");
        clientInstrument1.setMake("Make1");
        clientInstrument1.setModel("Model1");
        clientInstrument1.setLastCalibrated("5-feb-2016");
        clientInstrument1.setCalibrationDueDate("01-mar-2016");
        clientInstrument1.setCurrentLocation("current location1");
        clientInstrument1.setStatus("status1");
        clientInstrument1.setTestId(2);
        clientInstrument1.setCreationDate("3-feb-2016");
        equipmentArrayList.add(clientInstrument1);
        return equipmentArrayList;
    }


    //create partner instrument test equipment data
    private List<PartnerInstrument> createPartnerInstrumentData() {
        ArrayList<PartnerInstrument> partnerInstrumentArrayList = new ArrayList<PartnerInstrument>();
        PartnerInstrument partnerInstrument = new PartnerInstrument();
        partnerInstrument.setpInstrumentId(1);
        partnerInstrument.setPartnerId(1);
        partnerInstrument.setpInstrumentName("pInstrumentName");
        partnerInstrument.setMake("make");
        partnerInstrument.setModel("model");
        partnerInstrument.setLastCalibrated("3-feb-2016");
        partnerInstrument.setCalibrationDueDate("10-feb-2016");
        partnerInstrument.setCurrentLocation("current location");
        partnerInstrument.setStatus("status");
        partnerInstrument.setTestId(1);
        partnerInstrument.setCreationDate("2-feb-2016");
        partnerInstrumentArrayList.add(partnerInstrument);

        //2nd data
        PartnerInstrument partnerInstrument1 = new PartnerInstrument();
        partnerInstrument1.setpInstrumentId(2);
        partnerInstrument1.setPartnerId(2);
        partnerInstrument1.setpInstrumentName("pInstrumentName");
        partnerInstrument1.setMake("make");
        partnerInstrument1.setModel("model");
        partnerInstrument1.setLastCalibrated("3-feb-2016");
        partnerInstrument1.setCalibrationDueDate("10-feb-2016");
        partnerInstrument1.setCurrentLocation("current location");
        partnerInstrument1.setStatus("status");
        partnerInstrument1.setTestId(2);
        partnerInstrument1.setCreationDate("2-feb-2016");
        partnerInstrumentArrayList.add(partnerInstrument1);
        return partnerInstrumentArrayList;
    }

    //create applicable test equipment data
    private List<Equipment> createEquipmentData() {
        ArrayList<Equipment> equipmentArrayList = new ArrayList<Equipment>();
        Equipment equipment = new Equipment();
        equipment.setEquipmentId(1);
        equipment.setEquipmentNo("equipmentNo");
        equipment.setEquipmentName("Equipment name");
        equipment.setRoomId(1);
        equipment.setOccupancyState("OccupancyState");
        equipment.setVelocity(200);
        equipment.setTestReference("TestReference");
        equipment.setFilterQuantity(5);
        equipment.setEquipmentLoad(100);
        equipment.setCreationDate("2-feb-2016");
        equipmentArrayList.add(equipment);

        //2nd data
        Equipment equipment1 = new Equipment();
        equipment1.setEquipmentId(2);
        equipment1.setEquipmentNo("equipmentNo1");
        equipment1.setEquipmentName("Equipment name1");
        equipment1.setRoomId(2);
        equipment1.setOccupancyState("OccupancyState1");
        equipment1.setVelocity(2001);
        equipment1.setTestReference("TestReference1");
        equipment1.setFilterQuantity(6);
        equipment1.setEquipmentLoad(1001);
        equipment1.setCreationDate("3-feb-2016");
        equipmentArrayList.add(equipment1);
        return equipmentArrayList;
    }

    //create applicable test equipment data
    private List<Room> createRoomData() {
        ArrayList<Room> roomArrayList = new ArrayList<Room>();
        Room room = new Room();
        room.setRoomId(1);
        room.setAreaId(1);
        room.setAhuId(1);
        room.setRoomName("roomName");
        room.setRoomNo("roomNo5");
        room.setWidth(10);
        room.setHeight(10);
        room.setLength(10);
        room.setArea(100);
        room.setVolume(1000);
        room.setAcphNLT(1);
        room.setTestRef("testref");
        room.setIsoClause("isoclouse");
        room.setOccupancyState("ocupencystate");
        room.setRoomSupplyAirflowCFM(100);
        room.setAhuFlowCFM(100);
        room.setRoomPressurePA(100);
        room.setFreshAirCFM(100);
        room.setBleedAirCFM(100);
        room.setExhaustAirCFM(100);
        room.setTemperature(32.5);
        room.setRh(100);
        room.setReturnAirCFM(100);
        room.setSupplyAirGrillQTY(5);
        room.setReturnAirGrillQTY(4);
        room.setSupplyAirFilterQTY(5);
        room.setReturnAirFilterQTY(4);
        room.setRemarks("remark");
        room.setCreationDate("2-feb-2016");
        roomArrayList.add(room);

        //2nd data
        Room room1 = new Room();
        room1.setRoomId(2);
        room1.setAreaId(2);
        room1.setAhuId(2);
        room1.setRoomName("roomName1");
        room1.setRoomNo("roomNo4");
        room1.setWidth(20);
        room1.setHeight(20);
        room1.setLength(20);
        room1.setArea(400);
        room1.setVolume(4000);
        room1.setAcphNLT(2);
        room1.setTestRef("testref1");
        room1.setIsoClause("isoclouse1");
        room1.setOccupancyState("ocupencystate1");
        room1.setRoomSupplyAirflowCFM(200);
        room1.setAhuFlowCFM(200);
        room1.setRoomPressurePA(200);
        room1.setFreshAirCFM(200);
        room1.setBleedAirCFM(200);
        room1.setExhaustAirCFM(200);
        room1.setTemperature(35.5);
        room1.setRh(200);
        room1.setReturnAirCFM(200);
        room1.setSupplyAirGrillQTY(6);
        room1.setReturnAirGrillQTY(7);
        room1.setSupplyAirFilterQTY(8);
        room1.setReturnAirFilterQTY(5);
        room1.setRemarks("remark1");
        room1.setCreationDate("3-feb-2016");
        roomArrayList.add(room1);
        return roomArrayList;
    }


    //create applicable test equipment data
    private List<ApplicableTestRoom> createApplicableTestRoomData() {
        ArrayList<ApplicableTestRoom> applicableTestRoomArrayList = new ArrayList<ApplicableTestRoom>();
        ApplicableTestRoom applicableTestRoom = new ApplicableTestRoom();
        applicableTestRoom.setAplicable_testId(1);
        applicableTestRoom.setRoomId(1);
        applicableTestRoom.setTestName("RD_ACPH_AV");
        applicableTestRoom.setPeriodicity("priodicity");
        applicableTestRoom.setLocation(5);
        applicableTestRoom.setNoOfCycle(5);
        applicableTestRoom.setCreationDate("2-feb-2016");
        applicableTestRoomArrayList.add(applicableTestRoom);

        //2nd data
        ApplicableTestRoom applicableTestRoom1 = new ApplicableTestRoom();
        applicableTestRoom1.setAplicable_testId(2);
        applicableTestRoom1.setRoomId(2);
        applicableTestRoom1.setTestName("RD_ACPH_H");
        applicableTestRoom1.setPeriodicity("priodicity1");
        applicableTestRoom1.setLocation(6);
        applicableTestRoom1.setNoOfCycle(6);
        applicableTestRoom1.setCreationDate("3-feb-2016");
        applicableTestRoomArrayList.add(applicableTestRoom1);

        //3RD DATA
        ApplicableTestRoom applicableTestRoom2 = new ApplicableTestRoom();
        applicableTestRoom2.setAplicable_testId(3);
        applicableTestRoom2.setRoomId(2);
        applicableTestRoom2.setTestName("RD_FIT");
        applicableTestRoom2.setPeriodicity("priodicity1");
        applicableTestRoom2.setLocation(6);
        applicableTestRoom2.setNoOfCycle(6);
        applicableTestRoom2.setCreationDate("3-feb-2016");
        applicableTestRoomArrayList.add(applicableTestRoom2);

        ///4TH DATA
        ApplicableTestRoom applicableTestRoom3 = new ApplicableTestRoom();
        applicableTestRoom3.setAplicable_testId(4);
        applicableTestRoom3.setRoomId(2);
        applicableTestRoom3.setTestName("RD_PC_3");
        applicableTestRoom3.setPeriodicity("priodicity1");
        applicableTestRoom3.setLocation(5);
        applicableTestRoom3.setNoOfCycle(6);
        applicableTestRoom3.setCreationDate("3-feb-2016");
        applicableTestRoomArrayList.add(applicableTestRoom3);

        ///5TH DATA
        ApplicableTestRoom applicableTestRoom4 = new ApplicableTestRoom();
        applicableTestRoom4.setAplicable_testId(5);
        applicableTestRoom4.setRoomId(2);
        applicableTestRoom4.setTestName("RD_RCT");
        applicableTestRoom4.setPeriodicity("priodicity1");
        applicableTestRoom4.setLocation(7);
        applicableTestRoom4.setNoOfCycle(6);
        applicableTestRoom4.setCreationDate("3-feb-2016");
        applicableTestRoomArrayList.add(applicableTestRoom4);

        return applicableTestRoomArrayList;
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
        applicableTestEquipment.setCreationDate("2-feb-2016");
        applicableTestEquipmentArrayList.add(applicableTestEquipment);

        return applicableTestEquipmentArrayList;
    }

    //create ahu data
    private List<Ahu> createAhuData() {
        ArrayList<Ahu> ahuArrayList = new ArrayList<Ahu>();
        Ahu ahu = new Ahu();
        ahu.setAhuId(1);
        ahu.setAhuType("ahutype");
        ahu.setCapacity(100);
        ahu.setReturnAirCFM(101);
        ahu.setExhaustAirCFM(102);
        ahu.setBleedFilterType("bleedfiltertype");
        ahu.setBleedFilterEfficiency(103);
        ahu.setBleedAirCFM(104);
        ahu.setBleedFilterQty(2);
        ahu.setBleedFilterSize("20ft");
        ahu.setFreshFilterType("freshfiltertype");
        ahu.setFreshAirCFM(105);
        ahu.setFreshFilterQty(2);
        ahu.setFreshFilterSize("22ft");
        ahu.setAhuHEPAFilterQty(2);
        ahu.setHepaFilterEfficiency(106);
        ahu.setHepaParticleSize("23ft");
        ahu.setHepaFilterEfficiency(107);
        ahu.setCreationDate("2-feb-2016");
        ahuArrayList.add(ahu);
        // 2nd ahu
        Ahu ahu1 = new Ahu();
        ahu1.setAhuId(2);
        ahu1.setAhuType("ahutype1");
        ahu1.setCapacity(1001);
        ahu1.setReturnAirCFM(1011);
        ahu1.setExhaustAirCFM(1021);
        ahu1.setBleedFilterType("bleedfiltertype1");
        ahu1.setBleedFilterEfficiency(1031);
        ahu1.setBleedAirCFM(1041);
        ahu1.setBleedFilterQty(21);
        ahu1.setBleedFilterSize("201ft");
        ahu1.setFreshFilterType("freshfiltertype1");
        ahu1.setFreshAirCFM(1051);
        ahu1.setFreshFilterQty(21);
        ahu1.setFreshFilterSize("221ft");
        ahu1.setAhuHEPAFilterQty(21);
        ahu1.setHepaFilterEfficiency(1061);
        ahu1.setHepaParticleSize("231ft");
        ahu1.setHepaFilterEfficiency(1071);
        ahuArrayList.add(ahu1);
        return ahuArrayList;

    }

    @Override
    protected void onStop() {
        mValdocDatabaseHandler.close();
        super.onStop();
    }
}



