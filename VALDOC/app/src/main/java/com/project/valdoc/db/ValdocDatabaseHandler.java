package com.project.valdoc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.AhuFilter;
import com.project.valdoc.intity.AppUser;
import com.project.valdoc.intity.ApplicableTestAhu;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.Area;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.ClientInstrumentTest;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.EquipmentGrill;
import com.project.valdoc.intity.Grill;
import com.project.valdoc.intity.IsoParticleLimits;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.PartnerInstrumentTest;
import com.project.valdoc.intity.PartnerUser;
import com.project.valdoc.intity.Partners;
import com.project.valdoc.intity.Plant;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.SamplingTime;
import com.project.valdoc.intity.ServiceReport;
import com.project.valdoc.intity.ServiceReportDetail;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestMaster;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Avinash on 2/13/2016.
 */
public class ValdocDatabaseHandler extends SQLiteOpenHelper {

    // Database name
    public static final String DATABASE_NAME = "valdoc.db";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    //ahu table details
    public static final String AHU_TABLE_NAME = "ahu";
    public static final String AHU_AHUID = "ahuId";
    public static final String AHU_AHUNO = "ahuNo";
    public static final String AHU_AHUTYPE = "ahuType";
    public static final String AHU_AREAID = "areaId";
    public static final String AHU_CAPACITY = "capacity";
    public static final String AHU_RETURNAIRCFM = "returnAirCFM";
    public static final String AHU_EXHAUSTAIRCFM = "exhaustAirCFM";
    public static final String AHU_BLEEDFILTERTYPE = "bleedFilterType";
    public static final String AHU_BLEEDFILTEREFFICIENCY = "bleedFilterEfficiency";
    public static final String AHU_BLEEDAIRCFM = "bleedAirCFM";
    public static final String AHU_BLEEDFILTERQTY = "bleedFilterQty";
    public static final String AHU_BLEEDFILTERLEAK = "bleedFilterLeak";
    public static final String AHU_FRESHFILTERTYPE = "freshFilterType";
    public static final String AHU_FRESHAIRCFM = "freshAirCFM";
    public static final String AHU_FRESHFILTERQTY = "freshFilterQty";
    public static final String AHU_FRESHFILTEREFFICIENCY = "freshFilterEfficiency";
    public static final String AHU_FINALFILTERAIRFLOW = "finalFilterAirFlow";
    public static final String AHU_FINALFILTERQTY = "finalFilterQty";
    public static final String AHU_FINALFILTERTYPE = "finalFilterType";
    public static final String AHU_FINALFILTEREFFICIENCY = "finalFilterEfficiency";
    public static final String AHU_FINALFILTERLEAK = "finalFilterLeak";
    public static final String AHU_REMARKS = "remarks";
    public static final String AHU_LASTUPDATEDDATE = "lastUpdatedDate";
    public static final String AHU_FRESHPARTICLESIZE = "freshParticleSize";
    public static final String AHU_BLEEDPARTICLESIZE = "bleedParticleSize";
    public static final String AHU_FINALPARTICLESIZE = "finalParticleSize";

    // ahu table create statment
    private static final String CREATE_TABLE_AHU = "CREATE TABLE " + AHU_TABLE_NAME
            + "(" + AHU_AHUID + " INTEGER," + AHU_AHUNO + " TEXT," + AHU_AHUTYPE + " TEXT," + AHU_AREAID + " TEXT,"
            + AHU_CAPACITY + " REAL," + AHU_RETURNAIRCFM + " REAL," + AHU_EXHAUSTAIRCFM + " REAL," + AHU_BLEEDFILTERTYPE + " TEXT,"
            + AHU_BLEEDFILTEREFFICIENCY + " REAL," + AHU_BLEEDAIRCFM + " REAL," + AHU_BLEEDFILTERQTY + " INTEGER,"
            + AHU_BLEEDFILTERLEAK + " REAL," + AHU_FRESHFILTERTYPE + " TEXT," + AHU_FRESHAIRCFM + " REAL," + AHU_FRESHFILTERQTY
            + " INTEGER," + AHU_FRESHFILTEREFFICIENCY + " TEXT," + AHU_FINALFILTERAIRFLOW + " INTEGER," + AHU_FINALFILTERQTY
            + " INTEGER," + AHU_FINALFILTERTYPE + " TEXT," + AHU_FINALFILTEREFFICIENCY + " TEXT,"
            + AHU_FINALFILTERLEAK + " TEXT," + AHU_REMARKS + " TEXT," + AHU_LASTUPDATEDDATE + " TEXT," + AHU_FRESHPARTICLESIZE + " REAL,"
            + AHU_BLEEDPARTICLESIZE + " REAL," + AHU_FINALPARTICLESIZE + " REAL" + ")";

    //Applicable test ahu table details
    public static final String APLICABLE_TEST_AHU_TABLE_NAME = "aplicable_test_ahu";
    public static final String APLICABLE_TEST_AHU_APLICABLE_TESTID = "aplicable_testId";
    public static final String APLICABLE_TEST_AHU_AHUID = "ahuId";
    public static final String APLICABLE_TEST_AHU_TESTNAME = "testName";
    public static final String APLICABLE_TEST_AHU_TESTCODE = "testCode";
    public static final String APLICABLE_TEST_AHU_TESTFORMAT = "testFormat";
    public static final String APLICABLE_TEST_AHU_TESTITEM = "testItem";
    public static final String APLICABLE_TEST_AHU_TESTSPECIFICATION = "testSpecification";
    public static final String APLICABLE_TEST_AHU_OCCUPENCYSTATE = "occupencyState";
    public static final String APLICABLE_TEST_AHU_TESTREFERENCE = "testReference";
    public static final String APLICABLE_TEST_AHU_TESTPROP = "testProp";
    public static final String APLICABLE_TEST_AHU_PERIODICITY = "periodicity";
    public static final String APLICABLE_TEST_AHU_LOCATION = "location";
    public static final String APLICABLE_TEST_AHU_NOOFCYCLE = "noOfCycle";
    public static final String APLICABLE_TEST_AHU_LASTUPDATEDDATE = "lastUpdatedDate";

    // applicable test ahu table create statment
    private static final String CREATE_TABLE_APLICABLE_TEST_AHU = "CREATE TABLE " + APLICABLE_TEST_AHU_TABLE_NAME
            + "(" + APLICABLE_TEST_AHU_APLICABLE_TESTID + " INTEGER," + APLICABLE_TEST_AHU_AHUID + " INTEGER," + APLICABLE_TEST_AHU_TESTNAME + " TEXT,"
            + APLICABLE_TEST_AHU_TESTCODE + " TEXT," + APLICABLE_TEST_AHU_TESTFORMAT + " TEXT," + APLICABLE_TEST_AHU_TESTITEM + " TEXT," + APLICABLE_TEST_AHU_TESTSPECIFICATION + " TEXT,"
            + APLICABLE_TEST_AHU_OCCUPENCYSTATE + " TEXT," + APLICABLE_TEST_AHU_TESTREFERENCE + " TEXT," + APLICABLE_TEST_AHU_TESTPROP + " TEXT,"
            + APLICABLE_TEST_AHU_PERIODICITY + " TEXT," + APLICABLE_TEST_AHU_LOCATION + " INTEGER," + APLICABLE_TEST_AHU_NOOFCYCLE
            + " INTEGER," + APLICABLE_TEST_AHU_LASTUPDATEDDATE + " TEXT" + ")";

    //ahu filter table details
    public static final String AHU_FILTER_TABLE_NAME = "ahufilter";
    public static final String AHU_FILTER_FILTERID = "filterId";
    public static final String AHU_FILTER_AHUID = "ahuId";
    public static final String AHU_FILTER_FILTERCATEGORY = "filterCategory";
    public static final String AHU_FILTER_FILTERCODE = "filterCode";
    public static final String AHU_FILTER_WIDTH = "width";
    public static final String AHU_FILTER_LENGTH = "length";
    public static final String AHU_FILTER_DEPTHAREA = "depthArea";
    public static final String AHU_FILTER_AREA = "area";
    public static final String AHU_FILTER_EFFECTIVEAREA = "effectiveArea";


    // ahu filter table create statment
    private static final String CREATE_TABLE_AHU_FILTER = "CREATE TABLE " + AHU_FILTER_TABLE_NAME
            + "(" + AHU_FILTER_FILTERID + " INTEGER," + AHU_FILTER_AHUID + " INTEGER," + AHU_FILTER_FILTERCATEGORY + " TEXT,"
            + AHU_FILTER_FILTERCODE + " TEXT," + AHU_FILTER_WIDTH + " REAL," + AHU_FILTER_LENGTH + " REAL," + AHU_FILTER_DEPTHAREA + " REAL,"
            + AHU_FILTER_AREA + " REAL," + AHU_FILTER_EFFECTIVEAREA + " REAL" + ")";


    //Applicable test equipment table details
    public static final String APLICABLE_TEST_EQUIPMENT_TABLE_NAME = "aplicable_test_equipment";
    public static final String APLICABLE_TEST_EQUIPMENT_APLICABLE_TESTID = "aplicable_testId";
    public static final String APLICABLE_TEST_EQUIPMENT_EQUIPMENTID = "equipmentId";
    public static final String APLICABLE_TEST_EQUIPMENT_TESTNAME = "testName";

    public static final String APLICABLE_TEST_EQUIPMENT_TESTCODE = "testCode";
    public static final String APLICABLE_TEST_EQUIPMENT_TESTFORMAT = "testFormat";
    public static final String APLICABLE_TEST_EQUIPMENT_TESTSPECIFICATION = "testSpecification";
    public static final String APLICABLE_TEST_EQUIPMENT_OCCUPENCYSTATE = "occupencyState";
    public static final String APLICABLE_TEST_EQUIPMENT_TESTREFERENCE = "testReference";
    public static final String APLICABLE_TEST_EQUIPMENT_TESTPROP = "testProp";

    public static final String APLICABLE_TEST_EQUIPMENT_PERIODICITY = "periodicity";
    public static final String APLICABLE_TEST_EQUIPMENT_LOCATION = "location";
    public static final String APLICABLE_TEST_EQUIPMENT_NOOFCYCLE = "noOfCycle";
    public static final String APLICABLE_TEST_EQUIPMENT_CREATIONDATE = "lastUpdatedDate";

    // aplicable_test_equipment table create statment
    private static final String CREATE_TABLE_APLICABLE_TEST_EQUIPMENT = "CREATE TABLE " + APLICABLE_TEST_EQUIPMENT_TABLE_NAME
            + "(" + APLICABLE_TEST_EQUIPMENT_APLICABLE_TESTID + " INTEGER," + APLICABLE_TEST_EQUIPMENT_EQUIPMENTID + " INTEGER,"
            + APLICABLE_TEST_EQUIPMENT_TESTNAME + " TEXT," + APLICABLE_TEST_EQUIPMENT_TESTCODE + " TEXT," + APLICABLE_TEST_EQUIPMENT_TESTFORMAT + " TEXT,"
            + APLICABLE_TEST_EQUIPMENT_TESTSPECIFICATION + " TEXT," + APLICABLE_TEST_EQUIPMENT_OCCUPENCYSTATE + " TEXT," + APLICABLE_TEST_EQUIPMENT_TESTREFERENCE + " TEXT,"
            + APLICABLE_TEST_EQUIPMENT_TESTPROP + " TEXT," + APLICABLE_TEST_EQUIPMENT_PERIODICITY + " TEXT,"
            + APLICABLE_TEST_EQUIPMENT_LOCATION + " INTEGER," + APLICABLE_TEST_EQUIPMENT_NOOFCYCLE + " INTEGER,"
            + APLICABLE_TEST_EQUIPMENT_CREATIONDATE + " TEXT " + ")";

    //Applicable test equipment able details
    public static final String APLICABLE_TEST_ROOM_TABLE_NAME = "aplicable_test_room";
    public static final String APLICABLE_TEST_ROOM_APLICABLE_TESTID = "aplicable_testId";
    public static final String APLICABLE_TEST_ROOM_ROOMID = "roomId";
    public static final String APLICABLE_TEST_ROOM_TESTNAME = "testName";

    public static final String APLICABLE_TEST_ROOM_TESTCODE = "testCode";
    public static final String APLICABLE_TEST_ROOM_TESTFORMAT = "testFormat";
    public static final String APLICABLE_TEST_ROOM_TESTSPECIFICATION = "testSpecification";
    public static final String APLICABLE_TEST_ROOM_OCCUPENCYSTATE = "occupencyState";
    public static final String APLICABLE_TEST_ROOM_TESTREFERENCE = "testReference";
    public static final String APLICABLE_TEST_ROOM_TESTPROP = "testProp";

    public static final String APLICABLE_TEST_ROOM_PERIODICITY = "periodicity";
    public static final String APLICABLE_TEST_ROOM_LOCATION = "location";
    public static final String APLICABLE_TEST_ROOM_NOOFCYCLE = "noOfCycle";

    public static final String APLICABLE_TEST_ROOM_LASTUPDATEDDATE = "lastUpdatedDate";
    // aplicable_test_equipment table create statment
    private static final String CREATE_TABLE_APLICABLE_TEST_ROOM = "CREATE TABLE " + APLICABLE_TEST_ROOM_TABLE_NAME
            + "(" + APLICABLE_TEST_ROOM_APLICABLE_TESTID + " INTEGER," + APLICABLE_TEST_ROOM_ROOMID + " INTEGER,"
            + APLICABLE_TEST_ROOM_TESTNAME + " TEXT," + APLICABLE_TEST_ROOM_TESTCODE + " TEXT," + APLICABLE_TEST_ROOM_TESTFORMAT
            + " TEXT," + APLICABLE_TEST_ROOM_TESTSPECIFICATION + " TEXT," + APLICABLE_TEST_ROOM_OCCUPENCYSTATE + " TEXT,"
            + APLICABLE_TEST_ROOM_TESTREFERENCE + " TEXT," + APLICABLE_TEST_ROOM_TESTPROP + " TEXT," + APLICABLE_TEST_ROOM_PERIODICITY + " TEXT,"
            + APLICABLE_TEST_ROOM_LOCATION + " INTEGER," + APLICABLE_TEST_ROOM_NOOFCYCLE + " INTEGER,"
            + APLICABLE_TEST_ROOM_LASTUPDATEDDATE + " TEXT " + ")";

    //equipment able details
    public static final String EQUIPMENT_TABLE_NAME = "equipment";
    public static final String EQUIPMENT_EQUIPMENTID = "equipmentId";
    public static final String EQUIPMENT_ROOMID = "roomId";
    public static final String EQUIPMENT_EQUIPMENTNO = "equipmentNo";
    public static final String EQUIPMENT_EQUIPMENTNAME = "equipmentName";
    //    public static final String EQUIPMENT_OCCUPANCYSTATE = "occupancyState";
    public static final String EQUIPMENT_MINVELOCITY = "minVelocity";
    public static final String EQUIPMENT_MAXVELOCITY = "maxVelocity";
    public static final String EQUIPMENT_SUPPLYAIRFLOW = "supplyAirflow";
    public static final String EQUIPMENT_EQUIPMENTPRESSURE = "equipmentPressure";
    public static final String EQUIPMENT_EXHUSTAIRFLOW = "exhustAirflow";
    public static final String EQUIPMENT_REMARKS = "remarks";
    public static final String EQUIPMENT_LASTUPDATEDDATE = "lastUpdatedDate";

    // equipment table create statment
    private static final String CREATE_TABLE_EQUIPMENT = "CREATE TABLE " + EQUIPMENT_TABLE_NAME
            + "(" + EQUIPMENT_EQUIPMENTID + " INTEGER," + EQUIPMENT_ROOMID + " INTEGER,"
            + EQUIPMENT_EQUIPMENTNO + " TEXT," + EQUIPMENT_EQUIPMENTNAME + " TEXT," + EQUIPMENT_MINVELOCITY + " REAL,"
            + EQUIPMENT_MAXVELOCITY + " REAL," + EQUIPMENT_SUPPLYAIRFLOW + " INTEGER,"
            + EQUIPMENT_EQUIPMENTPRESSURE + " INTEGER," + EQUIPMENT_EXHUSTAIRFLOW + " INTEGER," + EQUIPMENT_REMARKS + " TEXT,"
            + EQUIPMENT_LASTUPDATEDDATE + " TEXT " + ")";


    //equipment filter able details
    public static final String EQUIPMENTFILTER_TABLE_NAME = "equipmentfilter";
    public static final String EQUIPMENTFILTER_FILTERID = "filterId";
    public static final String EQUIPMENTFILTER_FILTERCODE = "filterCode";
    public static final String EQUIPMENTFILTER_EQUIPMENTID = "equipmentId";

    public static final String EQUIPMENTFILTER_FILTERTYPE = "filterType";
    public static final String EQUIPMENTFILTER_EFFICIENCY = "efficiency";
    public static final String EQUIPMENTFILTER_PARTICLESIZE = "particleSize";
    public static final String EQUIPMENTFILTER_SPECIFICATIONLEAK = "specificationLeak";

    public static final String EQUIPMENTFILTER_WIDTH = "width";
    public static final String EQUIPMENTFILTER_LENGTH = "length";
    public static final String EQUIPMENTFILTER_GRILLAREA = "grillArea";
    public static final String EQUIPMENTFILTER_LASTUPDATEDDATE = "lastUpdatedDate";

    // equipment filer table create statment
    private static final String CREATE_TABLE_EQUIPMENTFILTER = "CREATE TABLE " + EQUIPMENTFILTER_TABLE_NAME
            + "(" + EQUIPMENTFILTER_FILTERID + " INTEGER," + EQUIPMENTFILTER_FILTERCODE + " TEXT," + EQUIPMENTFILTER_EQUIPMENTID + " INTEGER,"
            + EQUIPMENTFILTER_FILTERTYPE + " TEXT," + EQUIPMENTFILTER_EFFICIENCY + " REAL," + EQUIPMENTFILTER_PARTICLESIZE + " REAL,"
            + EQUIPMENTFILTER_SPECIFICATIONLEAK + " REAL," + EQUIPMENTFILTER_WIDTH + " REAL," + EQUIPMENTFILTER_LENGTH + " REAL,"
            + EQUIPMENTFILTER_GRILLAREA + " REAL," + EQUIPMENTFILTER_LASTUPDATEDDATE + " TEXT " + ")";

    //equipment grill table details
    public static final String EQUIPMENTGRILL_TABLE_NAME = "equipmentgrill";
    public static final String EQUIPMENTGRILL_GRILLID = "grillId";
    public static final String EQUIPMENTGRILL_GRILLCODE = "grillCode";
    public static final String EQUIPMENTGRILL_EQUIPMENTID = "equipmentId";
    public static final String EQUIPMENTGRILL_LENGTH = "length";
    public static final String EQUIPMENTGRILL_WIDTH = "width";
    public static final String EQUIPMENTGRILL_AREA = "area";
    public static final String EQUIPMENTGRILL_EFFECTIVEAREA = "effectiveArea";
    public static final String EQUIPMENTGRILL_REMARKS = "remarks";
    public static final String EQUIPMENTGRILL_LASTUPDATEDDATE = "lastUpdatedDate";

    // equipment grill table create statment
    private static final String CREATE_TABLE_EQUIPMENTGRILL = "CREATE TABLE " + EQUIPMENTGRILL_TABLE_NAME
            + "(" + EQUIPMENTGRILL_GRILLID + " INTEGER," + EQUIPMENTGRILL_GRILLCODE + " TEXT," + EQUIPMENTGRILL_EQUIPMENTID + " INTEGER,"
            + EQUIPMENTGRILL_LENGTH + " REAL," + EQUIPMENTGRILL_WIDTH + " REAL," + EQUIPMENTGRILL_AREA + " REAL,"
            + EQUIPMENTGRILL_EFFECTIVEAREA + " REAL," + EQUIPMENTGRILL_REMARKS + " TEXT," + EQUIPMENTGRILL_LASTUPDATEDDATE + " TEXT " + ")";

    //gril able details
    public static final String GRILL_TABLE_NAME = "grill";
    public static final String GRILL_GRILLID = "grillId";
    public static final String GRILL_GRILLCODE = "grillCode";
    public static final String GRILL_ROOMID = "roomId";
    public static final String GRILL_LENGTH = "length";
    public static final String GRILL_WIDTH = "width";
    public static final String GRILL_GRILLAREA = "area";
    public static final String GRILL_EFFECTIVEAREA = "effectiveArea";
    public static final String GRILL_ISSUPPLYGRILL = "isSupplyGrill";
    //    public static final String GRILL_ADDITIONALDETAIL = "additionalDetail";
    public static final String GRILL_LASTUPDATEDDATE = "lastUpdatedDate";

    // gril table create statment
    private static final String CREATE_TABLE_GRIL = "CREATE TABLE " + GRILL_TABLE_NAME
            + "(" + GRILL_GRILLID + " INTEGER," + GRILL_ROOMID + " INTEGER,"
            + GRILL_GRILLCODE + " TEXT," + GRILL_LENGTH + " REAL," + GRILL_WIDTH + " REAL," + GRILL_GRILLAREA + " REAL,"
            + GRILL_EFFECTIVEAREA + " REAL," + GRILL_ISSUPPLYGRILL + " INTEGER," + GRILL_LASTUPDATEDDATE + " TEXT " + ")";
//    + GRILL_ADDITIONALDETAIL + " INTEGER,"

    //partner instrument able details
    public static final String PARTNER_INSTRUMENT_TABLE_NAME = "partner_instrument";
    public static final String PARTNER_INSTRUMENT_PINSTRUMENTID = "pInstrumentId";
    public static final String PARTNER_INSTRUMENT_PARTNERID = "partnerId";
    public static final String PARTNER_INSTRUMENT_SERIALNO = "serialNo";
    public static final String PARTNER_INSTRUMENT_PINSTRUMENTNAME = "pInstrumentName";
    public static final String PARTNER_INSTRUMENT_MAKE = "make";
    public static final String PARTNER_INSTRUMENT_MODEL = "model";
    public static final String PARTNER_INSTRUMENT_LASTCALIBRATED = "lastCalibrated";
    public static final String PARTNER_INSTRUMENT_CALIBRATIONDUEDATE = "calibrationDueDate";
    //    public static final String PARTNER_INSTRUMENT_CURRENTLOCATION = "currentLocation";
    public static final String PARTNER_INSTRUMENT_STATUS = "status";
    //    public static final String PARTNER_INSTRUMENT_TESTID = "testId";
    public static final String PARTNER_INSTRUMENT_CERTFILENAME = "certFileName";
    public static final String PARTNER_INSTRUMENT_REMARKS = "remarks";
    public static final String PARTNER_INSTRUMENT_LASTUPDATEDDATE = "lastUpdatedDate";
    //new addition
//    public static final String PARTNER_INSTRUMENT_SAMPLINGFLOWRATE = "samplingFlowRate";
//    public static final String PARTNER_INSTRUMENT_SAMPLINGTIME = "samplingTime";
//    public static final String PARTNER_INSTRUMENT_AEROSOLUSED = "aerosolUsed";
//    public static final String PARTNER_INSTRUMENT_AEROSOLGENERATORTYPE = "aerosolGeneratorType";

    // partner instrument table create statment
    private static final String CREATE_TABLE_PARTNER_INSTRUMENT = "CREATE TABLE " + PARTNER_INSTRUMENT_TABLE_NAME
            + "(" + PARTNER_INSTRUMENT_PINSTRUMENTID + " INTEGER," + PARTNER_INSTRUMENT_PARTNERID + " INTEGER,"
            + PARTNER_INSTRUMENT_SERIALNO + " TEXT," + PARTNER_INSTRUMENT_PINSTRUMENTNAME + " TEXT," + PARTNER_INSTRUMENT_MAKE + " TEXT," + PARTNER_INSTRUMENT_MODEL + " TEXT,"
            + PARTNER_INSTRUMENT_LASTCALIBRATED + " NUMERIC," + PARTNER_INSTRUMENT_CALIBRATIONDUEDATE + " NUMERIC,"
            + PARTNER_INSTRUMENT_STATUS + " TEXT," + PARTNER_INSTRUMENT_CERTFILENAME + " TEXT," + PARTNER_INSTRUMENT_REMARKS
            + " TEXT," + PARTNER_INSTRUMENT_LASTUPDATEDDATE + " TEXT" + ")";
//            + PARTNER_INSTRUMENT_SAMPLINGFLOWRATE + " TEXT," + PARTNER_INSTRUMENT_SAMPLINGTIME + " TEXT,"
//            + PARTNER_INSTRUMENT_AEROSOLUSED + " TEXT," + PARTNER_INSTRUMENT_AEROSOLGENERATORTYPE + " TEXT" + ")";

    // plant table details
    public static final String PLANT_TABLE_NAME = "plant";
    public static final String PLANT_PLANTID = "plantId";
    public static final String PLANT_PLANTNAME = "plantName";
    public static final String PLANT_ADDRESS = "address";
    public static final String PLANT_ADDITIONALDETAILS = "additionalDetails";
    public static final String PLANT_DIRECTORNAME = "directorName";
    public static final String PLANT_DIRECTORCONTACTNO = "directorContactNo";
    public static final String PLANT_DIRECTOREMAILID = "directorEmailId";
    public static final String PLANT_CONTACTPERSONNAME = "contactPersonName";
    public static final String PLANT_CONTACTPERSONNO = "contactPersonNo";
    //
    // plant  table create statment
    private static final String CREATE_TABLE_PLANT = "CREATE TABLE " + PLANT_TABLE_NAME
            + "(" + PLANT_PLANTID + " INTEGER," + PLANT_PLANTNAME + " TEXT,"
            + PLANT_ADDRESS + " TEXT," + PLANT_ADDITIONALDETAILS + " INTEGER," + PLANT_DIRECTORNAME + " TEXT,"
            + PLANT_DIRECTORCONTACTNO + " TEXT,"
            + PLANT_DIRECTOREMAILID + " TEXT," + PLANT_CONTACTPERSONNAME + " TEXT," + PLANT_CONTACTPERSONNO + " TEXT" + ")";
//            + PARTNER_REG_EMAIL + " TEXT," + PARTNER_REG_CELL_NO + " TEXT," + PARTNER_SERVICE_INCHARGE + " TEXT,"
//            + PARTNER_EMAIL + " TEXT," + PARTNER_CELL_NO + " TEXT," + PARTNER_CREATION_DATE + " TEXT" + ")";

    //client instrument table details
    public static final String CLIENT_INSTRUMENT_TABLE_NAME = "client_instrument";
    public static final String CLIENT_INSTRUMENT_CINSTRUMENTID = "cInstrumentId";
    public static final String CLIENT_INSTRUMENT_INSTRUMENTID = "instrumentId";
    public static final String CLIENT_INSTRUMENT_SERIALNO = "serialNo";
    public static final String CLIENT_INSTRUMENT_CINSTRUMENTNAME = "cInstrumentName";
    public static final String CLIENT_INSTRUMENT_MAKE = "make";
    public static final String CLIENT_INSTRUMENT_MODEL = "model";
    public static final String CLIENT_INSTRUMENT_LASTCALIBRATED = "lastCalibrationDate";
    public static final String CLIENT_INSTRUMENT_CALIBRATIONDUEDATE = "calibrationDueDate";
    public static final String CLIENT_INSTRUMENT_RANGE = "range";
    public static final String CLIENT_INSTRUMENT_STATUS = "status";
    //    public static final String CLIENT_INSTRUMENT_TESTID = "testId";
    public static final String CLIENT_INSTRUMENT_CERTFILENAME = "certFileName";
    public static final String CLIENT_INSTRUMENT_REMARKS = "remarks";
    public static final String CLIENT_INSTRUMENT_LASTUPDATEDDATE = "lastUpdatedDate";
    //new addition
//    public static final String CLIENT_INSTRUMENT_SAMPLINGFLOWRATE = "samplingFlowRate";
//    public static final String CLIENT_INSTRUMENT_SAMPLINGTIME = "samplingTime";
//    public static final String CLIENT_INSTRUMENT_AEROSOLUSED = "aerosolUsed";
//    public static final String CLIENT_INSTRUMENT_AEROSOLGENERATORTYPE = "aerosolGeneratorType";


    // partner instrument table create statment
    private static final String CREATE_TABLE_CLIENT_INSTRUMENT = "CREATE TABLE " + CLIENT_INSTRUMENT_TABLE_NAME
            + "(" + CLIENT_INSTRUMENT_CINSTRUMENTID + " INTEGER PRIMARY KEY," + CLIENT_INSTRUMENT_INSTRUMENTID + " TEXT,"
            + CLIENT_INSTRUMENT_SERIALNO + " TEXT," + CLIENT_INSTRUMENT_CINSTRUMENTNAME + " TEXT, " + CLIENT_INSTRUMENT_MAKE + " TEXT,"
            + CLIENT_INSTRUMENT_MODEL + " TEXT," + CLIENT_INSTRUMENT_LASTCALIBRATED + " TEXT,"
            + CLIENT_INSTRUMENT_CALIBRATIONDUEDATE + " TEXT," + CLIENT_INSTRUMENT_STATUS + " TEXT," + CLIENT_INSTRUMENT_CERTFILENAME + " TEXT,"
            + CLIENT_INSTRUMENT_REMARKS + " TEXT," + CLIENT_INSTRUMENT_LASTUPDATEDDATE + " TEXT," + CLIENT_INSTRUMENT_RANGE + " TEXT" + ")";
//            + CLIENT_INSTRUMENT_CURRENTLOCATION + " TEXT,"+ CLIENT_INSTRUMENT_TESTID
//            + " INTEGER," + CLIENT_INSTRUMENT_SAMPLINGFLOWRATE + " TEXT,"
//            + CLIENT_INSTRUMENT_SAMPLINGTIME + " TEXT," + CLIENT_INSTRUMENT_AEROSOLUSED + " TEXT," + CLIENT_INSTRUMENT_AEROSOLGENERATORTYPE + " TEXT " + ")";

//Client instrument test table creation

    public static final String CLIENT_INSTRUMENT_TEST_TABLE_NAME = "client_instrument_test";
    public static final String CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_ID = "client_instrument_test_id";
    public static final String CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_ID = "client_instrument_id";
    public static final String CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_CODE = "client_instrument_test_code";
    public static final String CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_NAME = "client_instrument_test_name";
    public static final String CLIENT_INSTRUMENT_TEST_LASTUPDATEDDATE = "lastUpdatedDate";

    private static final String CREATE_TABLE_CLIENT_INSTRUMENT_TEST = "CREATE TABLE " + CLIENT_INSTRUMENT_TEST_TABLE_NAME
            + "(" + CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_ID + " INTEGER PRIMARY KEY," + CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_ID + " INTEGER,"
            + CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_CODE + " TEXT," + CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_NAME + " TEXT,"
            + CLIENT_INSTRUMENT_TEST_LASTUPDATEDDATE + " TEXT," + " FOREIGN KEY (" + CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_ID + ") REFERENCES "
            + CLIENT_INSTRUMENT_TABLE_NAME + "(" + CLIENT_INSTRUMENT_CINSTRUMENTID + ")" + ")";

    //Partner instrument test table creation

    public static final String PARTNER_INSTRUMENT_TEST_TABLE_NAME = "partner_instrument_test";
    public static final String PARTNER_INSTRUMENT_TEST_ID = "partner_instrument_test_id";
    public static final String PARTNER_INSTRUMENT_ID = "partner_instrument_id";
    public static final String PARTNER_INSTRUMENT_TEST_CODE = "partner_instrument_test_code";
    public static final String PARTNER_INSTRUMENT_TEST_NAME = "partner_instrument_test_name";
    public static final String PARTNER_INSTRUMENT_TEST_LASTUPDATEDDATE = "lastUpdatedDate";
    public static final String PARTNER_INSTRUMENT_TEST_RANGE = "range";

    private static final String CREATE_TABLE_PARTNER_INSTRUMENT_TEST = "CREATE TABLE " + PARTNER_INSTRUMENT_TEST_TABLE_NAME
            + "(" + PARTNER_INSTRUMENT_TEST_ID + " INTEGER," + PARTNER_INSTRUMENT_ID + " INTEGER,"
            + PARTNER_INSTRUMENT_TEST_CODE + " TEXT," + PARTNER_INSTRUMENT_TEST_NAME + " TEXT,"
            + PARTNER_INSTRUMENT_TEST_LASTUPDATEDDATE + " TEXT," + PARTNER_INSTRUMENT_TEST_RANGE + " TEXT" + ")";


    //roomfilter able details
    public static final String ROOMFILTER_TABLE_NAME = "roomfilter";
    public static final String ROOMFILTER_FILTERID = "filterId";
    public static final String ROOMFILTER_FILTERCODE = "filterCode";
    public static final String ROOMFILTER_ROOMID = "roomId";
    public static final String ROOMFILTER_FILTERTYPE = "filterType";
    public static final String ROOMFILTER_EFFICIENCY = "efficiency";
    public static final String ROOMFILTER_PARTICLESIZE = "particleSize";
    public static final String ROOMFILTER_SPECIFICATION = "specification";
    public static final String ROOMFILTER_WIDTH = "width";
    public static final String ROOMFILTER_LENGTH = "length";
    public static final String ROOMFILTER_AREA = "area";
    public static final String ROOMFILTER_EFFECTIVEFILTERAREA = "effectiveFilterArea";
    public static final String ROOMFILTER_FILTERLOCATION = "filterLocation";
    public static final String ROOMFILTER_ISSUPPLYFILTER = "supplyFilter";
    public static final String ROOMFILTER_LASTUPDATEDDATE = "lastUpdatedDate";

    // roomfilter table create statment
    private static final String CREATE_TABLE_ROOMFILTER = "CREATE TABLE " + ROOMFILTER_TABLE_NAME
            + "(" + ROOMFILTER_FILTERID + " INTEGER," + ROOMFILTER_FILTERTYPE + " TEXT,"
            + ROOMFILTER_EFFICIENCY + " REAL," + ROOMFILTER_FILTERCODE + " TEXT," + ROOMFILTER_PARTICLESIZE + " TEXT,"
            + ROOMFILTER_ROOMID + " INTEGER," + ROOMFILTER_SPECIFICATION + " REAL,"
            + ROOMFILTER_WIDTH + " REAL," + ROOMFILTER_LENGTH + " REAL," + ROOMFILTER_AREA + " REAL," + ROOMFILTER_EFFECTIVEFILTERAREA
            + " REAL," + ROOMFILTER_FILTERLOCATION + " TEXT," + ROOMFILTER_ISSUPPLYFILTER + " INTEGER,"
            + ROOMFILTER_LASTUPDATEDDATE + " TEXT " + ")";
//    + ROOMFILTER_GRILLAREA + " REAL,"
//            + ROOMFILTER_EFFECTIVEGRILLAREA + " REAL,"


    //room table details
    public static final String ROOM_TABLE_NAME = "room";
    public static final String ROOM_ROOMID = "roomId";
    public static final String ROOM_AREAID = "areaId";
    public static final String ROOM_AHUID = "ahuId";
    public static final String ROOM_ROOMNAME = "roomName";
    public static final String ROOM_ROOMNO = "roomNo";
    public static final String ROOM_WIDTH = "width";
    public static final String ROOM_HEIGHT = "height";
    public static final String ROOM_LENGTH = "length";
    public static final String ROOM_AREA = "area";
    public static final String ROOM_VOLUME = "volume";
    public static final String ROOM_ACPH = "acph";
    public static final String ROOM_TESTREF = "testRef";
    public static final String ROOM_ISOCLAUSE = "isoClause";
    public static final String ROOM_OCCUPANCYSTATE = "occupancyState";
    public static final String ROOM_SUPPLYAIRFLOW = "supplyAirflow";
    public static final String ROOM_AHUFLOWCFM = "ahuFlowCFM";
    public static final String ROOM_ROOMPRESSUREPA = "roomPressure";
    public static final String ROOM_FRESHAIRCFM = "freshAirCFM";
    public static final String ROOM_BLEEDAIRCFM = "bleedAirCFM";
    public static final String ROOM_EXHAUSTAIRFLOW = "exhaustAirFlow";
    public static final String ROOM_MAXTEMPERATURE = "maxTemperature";
    public static final String ROOM_MAXRH = "maxRH";
    public static final String ROOM_MINTEMPERATURE = "minTemperature";
    public static final String ROOM_MINRH = "minRH";
    public static final String ROOM_RETURNAIRFLOW = "returnAirFlow";
    //    public static final String ROOM_SUPPLYAIRGRILLQTY = "supplyAirGrillQTY";
//    public static final String ROOM_RETURNAIRGRILLQTY = "returnAirGrillQTY";
//    public static final String ROOM_SUPPLYAIRFILTERQTY = "supplyAirFilterQTY";
//    public static final String ROOM_RETURNAIRFILTERQTY = "returnAirFilterQTY";
    public static final String ROOM_REMARKS = "remarks";
    public static final String ROOM_LASTUPDATEDDATE = "lastUpdatedDate";

    // room table create statment
    private static final String CREATE_TABLE_ROOM = "CREATE TABLE " + ROOM_TABLE_NAME
            + "(" + ROOM_ROOMID + " INTEGER," + ROOM_AHUID + " INTEGER," + ROOM_AREAID + " INTEGER,"
            + ROOM_ROOMNAME + " TEXT," + ROOM_ROOMNO + " TEXT," + ROOM_WIDTH + " REAL,"
            + ROOM_HEIGHT + " REAL," + ROOM_LENGTH + " REAL," + ROOM_AREA + " REAL," + ROOM_VOLUME + " REAL,"
            + ROOM_ACPH + " INTEGER," + ROOM_TESTREF + " TEXT," + ROOM_ISOCLAUSE + " TEXT," + ROOM_OCCUPANCYSTATE + " TEXT,"
            + ROOM_SUPPLYAIRFLOW + " REAL," + ROOM_AHUFLOWCFM + " REAL," + ROOM_ROOMPRESSUREPA + " REAL,"
            + ROOM_FRESHAIRCFM + " REAL," + ROOM_BLEEDAIRCFM + " REAL," + ROOM_EXHAUSTAIRFLOW + " REAL," + ROOM_MAXTEMPERATURE + " REAL,"
            + ROOM_MAXRH + " REAL," + ROOM_MINTEMPERATURE + " REAL," + ROOM_MINRH + " REAL," + ROOM_RETURNAIRFLOW + " REAL," + ROOM_REMARKS + " TEXT,"
            + ROOM_LASTUPDATEDDATE + " TEXT " + ")";
//    + ROOM_SUPPLYAIRGRILLQTY + " INTEGER," + ROOM_RETURNAIRGRILLQTY
//            + " INTEGER," + ROOM_SUPPLYAIRFILTERQTY + " INTEGER," + ROOM_RETURNAIRFILTERQTY + " INTEGER,"

    //user table details
    public static final String USER_TABLE_NAME = "app_user";
    public static final String USER_ID = "app_user_id";
    public static final String USER_NAME = "name";
    public static final String USER_TYPE = "user_type";
    public static final String USER_EMAIL = "email";
    public static final String USER_CONTACT = "contact";
    public static final String USER_DEPARTMENT = "department";
    public static final String USER_ACTIVE = "is_active";
    public static final String USER_DELETED = "is_deleted";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PARTNERID = "partner_id";
    public static final String USER_ROLE_TYPE = "role_type";
    public static final String USER_PERMISSIONS = "permissions";
    public static final String USER_LAST_UPDATED = "last_updated";

    // user table create statment
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE_NAME
            + "(" + USER_ID + " INTEGER," + USER_NAME + " TEXT,"
            + USER_TYPE + " REAL," + USER_EMAIL + " TEXT," + USER_CONTACT + " TEXT," + USER_DEPARTMENT + " TEXT,"
            + USER_ACTIVE + " NUMERIC," + USER_DELETED + " NUMERIC," + USER_PASSWORD + " TEXT, " + USER_PARTNERID + " INTEGER,"
            + USER_ROLE_TYPE + " TEXT," + USER_PERMISSIONS + " TEXT," + USER_LAST_UPDATED + " TEXT " + ")";

    //partner user table details
    public static final String PARTNERUSER_TABLE_NAME = "partneruser";
    public static final String PARTNERUSER_PARTNER_USER_ID = "partner_user_id";
    public static final String PARTNERUSER_APP_USER_ID = "app_user_id";
    public static final String PARTNERUSER_PARTNERID = "partnerId";
    public static final String PARTNERUSER_CREATIONDATE = "creationDate";

    // partner user table create statment
    private static final String CREATE_TABLE_PARTNERUSER = "CREATE TABLE " + PARTNERUSER_TABLE_NAME
            + "(" + PARTNERUSER_PARTNER_USER_ID + " INTEGER," + PARTNERUSER_APP_USER_ID + " INTEGER,"
            + PARTNERUSER_PARTNERID + " INTEGER, " + PARTNERUSER_CREATIONDATE + " TEXT " + ")";


    //Test master table details
    public static final String TESTMASTER_TABLE_NAME = "test_master";
    public static final String TESTMASTER_TESTID = "testId";
    public static final String TESTMASTER_TESTNAME = "testName";
    public static final String TESTMASTER_SPECIFICATION = "specification";
    public static final String TESTMASTER_CREATIONDATE = "creationDate";

    // Test master table create statment
    private static final String CREATE_TABLE_TESTMASTER = "CREATE TABLE " + TESTMASTER_TABLE_NAME
            + "(" + TESTMASTER_TESTID + " INTEGER," + TESTMASTER_TESTNAME + " TEXT,"
            + TESTMASTER_SPECIFICATION + " TEXT, " + TESTMASTER_CREATIONDATE + " TEXT " + ")";

    //Area table details
    public static final String AREA_TABLE_NAME = "area";
    public static final String AREA_AREAID = "areaId";
    public static final String AREA_PLANTID = "plantId";
    public static final String AREA_AREANAME = "areaName";
    public static final String AREA_ADDITIONALDETAILS = "additionalDetails";
    public static final String AREA_CREATIONDATE = "lastUpdatedDate";

    // Area table create statment
    private static final String CREATE_TABLE_AREA = "CREATE TABLE " + AREA_TABLE_NAME
            + "(" + AREA_AREAID + " INTEGER," + AREA_PLANTID + " INTEGER,"
            + AREA_AREANAME + " TEXT, " + AREA_ADDITIONALDETAILS + " TEXT," + AREA_CREATIONDATE + " TEXT " + ")";

    //test reading table details
    public static final String TESTREADING_TABLE_NAME = "testreading";
    public static final String TESTREADING_TESTREADINGID = "testReadingId";
    public static final String TESTREADING_TEST_DETAIL_ID = "test_detail_id";
    public static final String TESTREADING_ENTITYNAME = "entityName";
    public static final String TESTREADING_VALUE = "value";

    // test reading table create statment
    private static final String CREATE_TABLE_TESTREADING = "CREATE TABLE " + TESTREADING_TABLE_NAME
            + "(" + TESTREADING_TESTREADINGID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1," + TESTREADING_TEST_DETAIL_ID + " INTEGER,"
            + TESTREADING_ENTITYNAME + " TEXT, " + TESTREADING_VALUE + " TEXT " + ")";


    //test details table details
    public static final String TEST_DETAILS_TABLE_NAME = "test_details";
    public static final String TEST_DETAILS_TEST_DETAIL_ID = "test_detail_id";
    public static final String TEST_DETAILS_TESTNAME = "testName";
    public static final String TEST_DETAILS_CUSTOMER = "customer";
    public static final String TEST_DETAILS_RAWDATANO = "rawDataNo";

    public static final String TEST_DETAILS_DATEOFTEST = "dateOfTest";
    public static final String TEST_DETAILS_INSTRUMENTUSED = "instrumentUsed";
    public static final String TEST_DETAILS_INSTRUMENTNO = "instrumentNo";
    public static final String TEST_DETAILS_MAKE = "make";
    public static final String TEST_DETAILS_MODEL = "model";
    public static final String TEST_DETAILS_CALIBRATEDON = "calibratedOn";
    public static final String TEST_DETAILS_CALIBRATEDDUEON = "calibratedDueOn";
    public static final String TEST_DETAILS_TESTSPECIFICATION = "testSpecification";
    public static final String TEST_DETAILS_TEST_TESTREFERENCE = "testReference";
    public static final String TEST_DETAILS_OCCUPENCYSTATE = "occupencyState";
    public static final String TEST_DETAILS_BLOCKNAME = "blockName";
    public static final String TEST_DETAILS_TESTAREA = "testArea";
    public static final String TEST_DETAILS_AHUNO = "ahuNo";
    public static final String TEST_DETAILS_ROOMNO = "roomNo";
    public static final String TEST_DETAILS_ROOMNAME = "roomName";
    public static final String TEST_DETAILS_EQUIPMENTNO = "equipmentNo";
    public static final String TEST_DETAILS_EQUIPMENTNAME = "equipmentName";
    public static final String TEST_DETAILS_TESTERNAME = "testerName";
    public static final String TEST_DETAILS_WITNESSNAME = "witnessName";
    public static final String TEST_DETAILS_PARTNERNAME = "partnerName";
    //new addition
    public static final String TEST_DETAILS_SAMPLINGFLOWRATE = "samplingFlowRate";
    public static final String TEST_DETAILS_SAMPLINGTIME = "samplingTime";
    public static final String TEST_DETAILS_AEROSOLUSED = "aerosolUsed";
    public static final String TEST_DETAILS_AEROSOLGENERATORTYPE = "aerosolGeneratorType";
    public static final String TEST_DETAILS_TESTCODE = "testCode";
    public static final String TEST_DETAILS_ROOMVOLUME = "roomVolume";
    public static final String TEST_DETAILS_TESTWITNESSORG = "testWitnessOrg";
    public static final String TEST_DETAILS_TESTCONDOCTORORG = "testCondoctorOrg";
    public static final String TEST_DETAILS_TESTITEM = "testItem";

    // test details table create statment
    private static final String CREATE_TABLE_TESTDETAILS = "CREATE TABLE " + TEST_DETAILS_TABLE_NAME
            + "(" + TEST_DETAILS_TEST_DETAIL_ID + " INTEGER PRIMARY KEY," + TEST_DETAILS_TESTNAME + " TEXT,"
            + TEST_DETAILS_CUSTOMER + " TEXT, " + TEST_DETAILS_RAWDATANO + " TEXT," + TEST_DETAILS_DATEOFTEST + " TEXT,"
            + TEST_DETAILS_INSTRUMENTUSED + " TEXT," + TEST_DETAILS_INSTRUMENTNO + " TEXT," + TEST_DETAILS_MAKE + " TEXT,"
            + TEST_DETAILS_MODEL + " TEXT," + TEST_DETAILS_CALIBRATEDON + " TEXT," + TEST_DETAILS_CALIBRATEDDUEON + " TEXT,"
            + TEST_DETAILS_TESTSPECIFICATION + " TEXT," + TEST_DETAILS_TEST_TESTREFERENCE + " TEXT," + TEST_DETAILS_OCCUPENCYSTATE + " TEXT,"
            + TEST_DETAILS_BLOCKNAME + " TEXT," + TEST_DETAILS_TESTAREA + " TEXT," + TEST_DETAILS_AHUNO + " TEXT," + TEST_DETAILS_ROOMNO + " TEXT,"
            + TEST_DETAILS_ROOMNAME + " TEXT," + TEST_DETAILS_EQUIPMENTNO + " TEXT," + TEST_DETAILS_EQUIPMENTNAME + " TEXT,"
            + TEST_DETAILS_TESTERNAME + " TEXT," + TEST_DETAILS_WITNESSNAME + " TEXT," + TEST_DETAILS_PARTNERNAME + " TEXT,"
            + TEST_DETAILS_SAMPLINGFLOWRATE + " TEXT," + TEST_DETAILS_SAMPLINGTIME + " TEXT," + TEST_DETAILS_AEROSOLUSED
            + " TEXT," + TEST_DETAILS_AEROSOLGENERATORTYPE + " TEXT," + TEST_DETAILS_TESTCODE + " TEXT," + TEST_DETAILS_ROOMVOLUME
            + " TEXT," + TEST_DETAILS_TESTWITNESSORG + " TEXT," + TEST_DETAILS_TESTCONDOCTORORG + " TEXT," + TEST_DETAILS_TESTITEM + " TEXT" + ")";


    //test spesification table details
    public static final String TESTSPECIFICATIONVALUE_TABLE_NAME = "test_specific_value";
    public static final String TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID = "test_specific_id";
    public static final String TESTSPECIFICATIONVALUE_TEST_DETAIL_ID = "test_detail_id";
    public static final String TESTSPECIFICATIONVALUE_FIELDNAME = "fieldName";
    public static final String TESTSPECIFICATIONVALUE_FIELDVALUE = "fieldValue";

    // test specification table create statment
    private static final String CREATE_TABLE_TESTSPECIFICATIONVALUE = "CREATE TABLE " + TESTSPECIFICATIONVALUE_TABLE_NAME
            + "(" + TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1," + TESTSPECIFICATIONVALUE_TEST_DETAIL_ID + " INTEGER,"
            + TESTSPECIFICATIONVALUE_FIELDNAME + " TEXT, " + TESTSPECIFICATIONVALUE_FIELDVALUE + " TEXT " + ")";

    //service report table creation
    public static final String SERVICE_REPORT_TABLE_NAME = "service_report";
    public static final String SERVICE_REPORT_ID = "id";
    public static final String SERVICE_REPORT_CUSTOMER = "customer";
    public static final String SERVICE_REPORT_SERVICEENGINEER = "serviceEngineer";
    public static final String SERVICE_REPORT_TIMEIN = "timeIn";
    public static final String SERVICE_REPORT_TIMEOUT = "timeOut";
    public static final String SERVICE_REPORT_SRNO = "srNo";
    public static final String SERVICE_REPORT_CUSTOMERPO = "customerPO";
    public static final String SERVICE_REPORT_PLANT = "plant";
    public static final String SERVICE_REPORT_STATUS = "status";
    public static final String SERVICE_REPORT_TESTERNAME = "testerName";
    public static final String SERVICE_REPORT_WITNESSNAME = "witnessName";
    public static final String SERVICE_REPORT_WITNESSCONTACT = "witnessContact";
    public static final String SERVICE_REPORT_WITNESSMAIL = "witnessMail";
    public static final String SERVICE_REPORT_PARTNERNAME = "partnerName";
    public static final String SERVICE_REPORT_SERVICEDATE = "serviceDate";
    public static final String SERVICE_REPORT_REMARK = "remark";
    public static final String SERVICE_REPORT_WORKORDER = "workOrder";
    public static final String SERVICE_REPORT_WITNESSDESIGNATION = "witnessDesignation";


    // test specification table create statment
    private static final String CREATE_TABLE_SERVICE_REPORT = "CREATE TABLE " + SERVICE_REPORT_TABLE_NAME
            + "(" + SERVICE_REPORT_ID + " INTEGER," + SERVICE_REPORT_CUSTOMER + " TEXT,"
            + SERVICE_REPORT_SERVICEENGINEER + " TEXT," + SERVICE_REPORT_TIMEIN + " TEXT, " + SERVICE_REPORT_TIMEOUT + " TEXT," + SERVICE_REPORT_SRNO + " TEXT,"
            + SERVICE_REPORT_CUSTOMERPO + " TEXT," + SERVICE_REPORT_PLANT + " TEXT," + SERVICE_REPORT_STATUS + " TEXT," + SERVICE_REPORT_TESTERNAME + " TEXT," + SERVICE_REPORT_WITNESSNAME + " TEXT,"
            + SERVICE_REPORT_WITNESSCONTACT + " TEXT," + SERVICE_REPORT_WITNESSMAIL + " TEXT," + SERVICE_REPORT_PARTNERNAME + " TEXT," + SERVICE_REPORT_SERVICEDATE + " TEXT,"
            + SERVICE_REPORT_REMARK + " TEXT," + SERVICE_REPORT_WORKORDER + " TEXT," + SERVICE_REPORT_WITNESSDESIGNATION + " TEXT" + ")";

    //service_report_detail table creation
    public static final String SERVICE_REPORT_DETAIL_TABLE_NAME = "service_report_detail";
    public static final String SERVICE_REPORT_DETAIL_ID = "id";
    public static final String SERVICE_REPORT_DETAIL_SERVICE_REPORT_ID = "service_report_id";
    public static final String SERVICE_REPORT_DETAIL_TYPEOFTEST = "typeOfTest";
    public static final String SERVICE_REPORT_DETAIL_AREAOFTEST = "areaOfTest";
    public static final String SERVICE_REPORT_DETAIL_EQUIPMENTAHUNO = "equipmentAhuNo";
    public static final String SERVICE_REPORT_DETAIL_NOOFLOC = "noOfLoc";
    public static final String SERVICE_REPORT_DETAIL_NOOFHOURDAYS = "noOfHourDays";


    // service_report_detail table create statment
    private static final String CREATE_TABLE_SERVICE_REPORT_DETAIL = "CREATE TABLE " + SERVICE_REPORT_DETAIL_TABLE_NAME
            + "(" + SERVICE_REPORT_DETAIL_ID + " INTEGER," + SERVICE_REPORT_DETAIL_SERVICE_REPORT_ID + " INTEGER,"
            + SERVICE_REPORT_DETAIL_TYPEOFTEST + " TEXT," + SERVICE_REPORT_DETAIL_AREAOFTEST + " TEXT," + SERVICE_REPORT_DETAIL_EQUIPMENTAHUNO + " TEXT,"
            + SERVICE_REPORT_DETAIL_NOOFLOC + " INTEGER,"
            + SERVICE_REPORT_DETAIL_NOOFHOURDAYS + " REAL" + ")";

    //partners table creation
    public static final String PARTNERS_TABLE_NAME = "partners";
    public static final String PARTNERS_ID = "partner_id";
    public static final String PARTNERS_NAME = "name";
    public static final String PARTNERS_PARTNERCODE = "partnerCode";
    public static final String PARTNERS_STATUS = "status";
    public static final String PARTNERS_APPROVEDSINCE = "approvedSince";
    public static final String PARTNERS_ADDRESS = "address";
    public static final String PARTNERS_DIRECTORNAME = "directorName";

    public static final String PARTNERS_DIRECTOREMAIL = "directorEmail";
    public static final String PARTNERS_DIRECTORCELLNO = "directorCellNo";
    public static final String PARTNERS_REGEMAIL = "regEmail";
    public static final String PARTNERS_REGCELLNO = "regCellNo";
    public static final String PARTNERS_SERVICEINCHARGE = "serviceIncharge";
    public static final String PARTNERS_EMAIL = "email";
    public static final String PARTNERS_CELLNO = "cellNo";
    public static final String PARTNERS_LASTUPDATEDDATE = "lastUpdatedDate";


    // service_report_detail table create statment
    private static final String CREATE_TABLE_PARTNERS = "CREATE TABLE " + PARTNERS_TABLE_NAME
            + "(" + PARTNERS_ID + " INTEGER," + PARTNERS_PARTNERCODE + " TEXT,"
            + PARTNERS_NAME + " TEXT," + PARTNERS_STATUS + " TEXT, " + PARTNERS_APPROVEDSINCE + " INTEGER,"
            + PARTNERS_ADDRESS + " TEXT," + PARTNERS_DIRECTORNAME + " TEXT," + PARTNERS_DIRECTOREMAIL + " TEXT," + PARTNERS_DIRECTORCELLNO + " TEXT,"
            + PARTNERS_REGEMAIL + " TEXT," + PARTNERS_REGCELLNO + " TEXT," + PARTNERS_SERVICEINCHARGE + " TEXT," + PARTNERS_EMAIL + " TEXT," + PARTNERS_CELLNO + " TEXT,"
            + PARTNERS_LASTUPDATEDDATE + " TEXT" + ")";

    //isoParticleLimits table creation
    public static final String ISOPARTICLELIMITS_TABLE_NAME = "isoParticleLimits";
    public static final String ISOPARTICLELIMITS_LIMITID = "limitId";
    public static final String ISOPARTICLELIMITS_CLASS = "particleClass";
    public static final String ISOPARTICLELIMITS_RESTSMALLPARTICLELIMIT = "restSmallParticleLimit";
    public static final String ISOPARTICLELIMITS_RESTLARGEPARTICLELIMIT = "restLargeParticleLimit";
    public static final String ISOPARTICLELIMITS_OPERATIONSMALLPARTICLELIMIT = "operationSmallParticleLimit";
    public static final String ISOPARTICLELIMITS_OPERATIONLARGEPARTICLELIMIT = "operationLargeParticleLimit";
    public static final String ISOPARTICLELIMITS_LASTUPDATEDDATE = "lastUpdatedDate";

    // service_report_detail table create statment
    private static final String CREATE_TABLE_ISOPARTICLELIMITS = "CREATE TABLE " + ISOPARTICLELIMITS_TABLE_NAME
            + "(" + ISOPARTICLELIMITS_LIMITID + " INTEGER," + ISOPARTICLELIMITS_CLASS + " TEXT,"
            + ISOPARTICLELIMITS_RESTSMALLPARTICLELIMIT + " TEXT," + ISOPARTICLELIMITS_RESTLARGEPARTICLELIMIT + " TEXT,"
            + ISOPARTICLELIMITS_OPERATIONSMALLPARTICLELIMIT + " TEXT," + ISOPARTICLELIMITS_OPERATIONLARGEPARTICLELIMIT + " TEXT,"
            + ISOPARTICLELIMITS_LASTUPDATEDDATE + " TEXT" + ")";

    //SamplingTime table table creation
    public static final String SAMPLINGTIME_TABLE_NAME = "sampling_time";
    public static final String SAMPLINGTIME_SAMPLINGTIMEID = "samplingTimeId";
    public static final String SAMPLINGTIME_CLEANROOMCLASS = "cleanroomClass";
    public static final String SAMPLINGTIME_LPM283 = "lpm283";
    public static final String SAMPLINGTIME_LPM50 = "lpm50";
    public static final String SAMPLINGTIME_LPM75 = "lpm75";
    public static final String SAMPLINGTIME_LPM100 = "lpm100";
    public static final String SAMPLINGTIME_LASTUPDATEDDATE = "lastUpdatedDate";

    // service_report_detail table create statment
    private static final String CREATE_TABLE_SAMPLINGTIME = "CREATE TABLE " + SAMPLINGTIME_TABLE_NAME
            + "(" + SAMPLINGTIME_SAMPLINGTIMEID + " INTEGER," + SAMPLINGTIME_CLEANROOMCLASS + " TEXT,"
            + SAMPLINGTIME_LPM283 + " TEXT," + SAMPLINGTIME_LPM50 + " TEXT,"
            + SAMPLINGTIME_LPM75 + " TEXT," + SAMPLINGTIME_LPM100 + " TEXT,"
            + SAMPLINGTIME_LASTUPDATEDDATE + " TEXT" + ")";
    private static final String TAG = "ValdocDatabaseHandler";


    public ValdocDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("valdoc", "db created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO table creation method
        Log.d("valdoc", "table creating...");
        db.execSQL(CREATE_TABLE_AHU);
        db.execSQL(CREATE_TABLE_APLICABLE_TEST_EQUIPMENT);
        db.execSQL(CREATE_TABLE_APLICABLE_TEST_ROOM);
        db.execSQL(CREATE_TABLE_EQUIPMENT);
        db.execSQL(CREATE_TABLE_EQUIPMENTFILTER);
        db.execSQL(CREATE_TABLE_GRIL);
        db.execSQL(CREATE_TABLE_PARTNER_INSTRUMENT);
        db.execSQL(CREATE_TABLE_ROOMFILTER);
        db.execSQL(CREATE_TABLE_ROOM);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PARTNERUSER);
        db.execSQL(CREATE_TABLE_PLANT);
        db.execSQL(CREATE_TABLE_CLIENT_INSTRUMENT);
        db.execSQL(CREATE_TABLE_CLIENT_INSTRUMENT_TEST);
        db.execSQL(CREATE_TABLE_PARTNER_INSTRUMENT_TEST);
        db.execSQL(CREATE_TABLE_TESTMASTER);
        db.execSQL(CREATE_TABLE_AREA);
        db.execSQL(CREATE_TABLE_TESTREADING);
        db.execSQL(CREATE_TABLE_TESTDETAILS);
        db.execSQL(CREATE_TABLE_TESTSPECIFICATIONVALUE);
        db.execSQL(CREATE_TABLE_SERVICE_REPORT);
        db.execSQL(CREATE_TABLE_SERVICE_REPORT_DETAIL);
        db.execSQL(CREATE_TABLE_PARTNERS);
        db.execSQL(CREATE_TABLE_APLICABLE_TEST_AHU);
        db.execSQL(CREATE_TABLE_AHU_FILTER);
        db.execSQL(CREATE_TABLE_EQUIPMENTGRILL);
        db.execSQL(CREATE_TABLE_ISOPARTICLELIMITS);
        db.execSQL(CREATE_TABLE_SAMPLINGTIME);
        Log.d("valdoc", "table created success fully");
    }

    public boolean deleteTestReportTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SERVICE_REPORT_TABLE_NAME, null, null);
        db.delete(SERVICE_REPORT_DETAIL_TABLE_NAME, null, null);
        return true;
    }

    public boolean deleteTestTableRow(int testDetailsId) {
        boolean val = false;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Log.d("Avinash", "testDetailsId" + testDetailsId);
            val = db.delete(TEST_DETAILS_TABLE_NAME, TEST_DETAILS_TEST_DETAIL_ID + " = " + testDetailsId, null) > 0;
            val = db.delete(TESTREADING_TABLE_NAME, TESTREADING_TEST_DETAIL_ID + " = " + testDetailsId, null) > 0;
            val = db.delete(TESTSPECIFICATIONVALUE_TABLE_NAME, TESTSPECIFICATIONVALUE_TEST_DETAIL_ID + " = " + testDetailsId, null) > 0;
        } catch (Exception e) {

        } finally {
            db.close();
        }
        return val;
    }

    // chek id in db table
    public int getExistingId(String tableName, String tableId, int id) {
        Cursor cursor = null;
        int count = 0;
        try {
            String selectQuery = " SELECT " + tableId + " FROM " + tableName +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                    " WHERE " + tableId + " = " + id;
            SQLiteDatabase database = this.getWritableDatabase();
            cursor = database.rawQuery(selectQuery, null);
            if (null != cursor)
                count = cursor.getCount();
            Log.d("Avinash", "db update count=" + count);
        } catch (Exception e) {
            Log.d("Avinash", "db update exception=" + e.getMessage());
        }
        return count;
    }


    //insert daa in Sampling time table
    public boolean insertSamplingTime(String tableName, ArrayList<SamplingTime> samplingTimeArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (samplingTimeArrayList.size() != 0) {
            for (SamplingTime samplingTime : samplingTimeArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(SAMPLINGTIME_SAMPLINGTIMEID, samplingTime.getSamplingTimeId());
                contentValues.put(SAMPLINGTIME_CLEANROOMCLASS, samplingTime.getCleanroomClass());
                contentValues.put(SAMPLINGTIME_LPM283, samplingTime.getLPM283());
                contentValues.put(SAMPLINGTIME_LPM50, samplingTime.getLPM50());
                contentValues.put(SAMPLINGTIME_LPM75, samplingTime.getLPM75());
                contentValues.put(SAMPLINGTIME_LPM100, samplingTime.getLPM100());
                contentValues.put(SAMPLINGTIME_LASTUPDATEDDATE, samplingTime.getLastUpdatedDate());
                if (getExistingId(tableName, SAMPLINGTIME_SAMPLINGTIMEID, samplingTime.getSamplingTimeId()) > 0) {
                    db.update(tableName, contentValues, SAMPLINGTIME_SAMPLINGTIMEID + "=" + samplingTime.getSamplingTimeId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable partners.getId()=" + partners.getPartnerId() + " partners.getName()=" + partners.getName());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    //insert daa in equipment grill filter table
    public boolean insertIsoParticleLimits(String tableName, ArrayList<IsoParticleLimits> isoParticleLimitsArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (isoParticleLimitsArrayList.size() != 0) {
            for (IsoParticleLimits isoParticleLimits : isoParticleLimitsArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ISOPARTICLELIMITS_LIMITID, isoParticleLimits.getLimitId());
                contentValues.put(ISOPARTICLELIMITS_CLASS, isoParticleLimits.getParticleClass());
                contentValues.put(ISOPARTICLELIMITS_RESTSMALLPARTICLELIMIT, isoParticleLimits.getRestSmallParticleLimit());
                contentValues.put(ISOPARTICLELIMITS_RESTLARGEPARTICLELIMIT, isoParticleLimits.getRestLargeParticleLimit());
                contentValues.put(ISOPARTICLELIMITS_OPERATIONSMALLPARTICLELIMIT, isoParticleLimits.getOperationSmallParticleLimit());
                contentValues.put(ISOPARTICLELIMITS_OPERATIONLARGEPARTICLELIMIT, isoParticleLimits.getOperationLargeParticleLimit());
                contentValues.put(ISOPARTICLELIMITS_LASTUPDATEDDATE, isoParticleLimits.getLastUpdatedDate());
                if (getExistingId(tableName, ISOPARTICLELIMITS_LIMITID, isoParticleLimits.getLimitId()) > 0) {
                    db.update(tableName, contentValues, ISOPARTICLELIMITS_LIMITID + "=" + isoParticleLimits.getLimitId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable partners.getId()=" + partners.getPartnerId() + " partners.getName()=" + partners.getName());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    //insert daa in equipment grill filter table
    public boolean insertEquipmentGrill(String tableName, ArrayList<EquipmentGrill> equipmentGrillArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (equipmentGrillArrayList.size() != 0) {
            for (EquipmentGrill equipmentGrill : equipmentGrillArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(EQUIPMENTGRILL_GRILLID, equipmentGrill.getGrillId());
                contentValues.put(EQUIPMENTGRILL_GRILLCODE, equipmentGrill.getGrillCode());
                contentValues.put(EQUIPMENTGRILL_EQUIPMENTID, equipmentGrill.getEquipmentId());
                contentValues.put(EQUIPMENTGRILL_LENGTH, equipmentGrill.getLength());
                contentValues.put(EQUIPMENTGRILL_WIDTH, equipmentGrill.getWidth());
                contentValues.put(EQUIPMENTGRILL_AREA, equipmentGrill.getArea());
                contentValues.put(EQUIPMENTGRILL_EFFECTIVEAREA, equipmentGrill.getEffectiveArea());
                contentValues.put(EQUIPMENTGRILL_REMARKS, equipmentGrill.getRemarks());
                contentValues.put(EQUIPMENTGRILL_LASTUPDATEDDATE, equipmentGrill.getLastUpdatedDate());

                if (getExistingId(tableName, AHU_FILTER_FILTERID, equipmentGrill.getGrillId()) > 0) {
                    db.update(tableName, contentValues, AHU_FILTER_FILTERID + "=" + equipmentGrill.getGrillId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable partners.getId()=" + partners.getPartnerId() + " partners.getName()=" + partners.getName());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    //insert daa in ahu filter table
    public boolean insertAhuFilter(String tableName, ArrayList<AhuFilter> ahuFilterArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (ahuFilterArrayList.size() != 0) {
            for (AhuFilter ahuFilter : ahuFilterArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(AHU_FILTER_FILTERID, ahuFilter.getFilterId());
                contentValues.put(AHU_FILTER_AHUID, ahuFilter.getAhuId());
                contentValues.put(AHU_FILTER_FILTERCATEGORY, ahuFilter.getFilterCategory());
                contentValues.put(AHU_FILTER_FILTERCODE, ahuFilter.getFilterCode());
                contentValues.put(AHU_FILTER_WIDTH, ahuFilter.getWidth());
                contentValues.put(AHU_FILTER_LENGTH, ahuFilter.getLength());
                contentValues.put(AHU_FILTER_DEPTHAREA, ahuFilter.getDepthArea());
                contentValues.put(AHU_FILTER_AREA, ahuFilter.getArea());
                contentValues.put(AHU_FILTER_EFFECTIVEAREA, ahuFilter.getEffectiveArea());

                if (getExistingId(tableName, AHU_FILTER_FILTERID, ahuFilter.getFilterId()) > 0) {
                    db.update(tableName, contentValues, AHU_FILTER_FILTERID + "=" + ahuFilter.getFilterId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable partners.getId()=" + partners.getPartnerId() + " partners.getName()=" + partners.getName());
                    db.insert(tableName, null, contentValues);
                }

            }
            return true;
        } else {
            return false;
        }
    }


    //insert daa in applicable test ahu table
    public boolean insertApplicableTestAhu(String tableName, ArrayList<ApplicableTestAhu> applicableTestAhuArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (applicableTestAhuArrayList.size() != 0) {
            for (ApplicableTestAhu applicableTestAhu : applicableTestAhuArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(APLICABLE_TEST_AHU_APLICABLE_TESTID, applicableTestAhu.getAplicable_testId());
                contentValues.put(APLICABLE_TEST_AHU_AHUID, applicableTestAhu.getAhuId());
                contentValues.put(APLICABLE_TEST_AHU_TESTNAME, applicableTestAhu.getTestName());
                contentValues.put(APLICABLE_TEST_AHU_TESTCODE, applicableTestAhu.getTestCode());
                contentValues.put(APLICABLE_TEST_AHU_TESTFORMAT, applicableTestAhu.getTestFormat());
                contentValues.put(APLICABLE_TEST_AHU_TESTITEM, applicableTestAhu.getTestItem());
                contentValues.put(APLICABLE_TEST_AHU_TESTSPECIFICATION, applicableTestAhu.getTestSpecification());

                contentValues.put(APLICABLE_TEST_AHU_OCCUPENCYSTATE, applicableTestAhu.getOccupencyState());
                contentValues.put(APLICABLE_TEST_AHU_TESTREFERENCE, applicableTestAhu.getTestReference());
                contentValues.put(APLICABLE_TEST_AHU_TESTPROP, applicableTestAhu.getTestProp());
                contentValues.put(APLICABLE_TEST_AHU_PERIODICITY, applicableTestAhu.getPeriodicity());
                contentValues.put(APLICABLE_TEST_AHU_LOCATION, applicableTestAhu.getLocation());
                contentValues.put(APLICABLE_TEST_AHU_NOOFCYCLE, applicableTestAhu.getNoOfCycle());
                contentValues.put(APLICABLE_TEST_AHU_LASTUPDATEDDATE, applicableTestAhu.getLastUpdatedDate());

                if (getExistingId(tableName, APLICABLE_TEST_AHU_APLICABLE_TESTID, applicableTestAhu.getAplicable_testId()) > 0) {
                    db.update(tableName, contentValues, APLICABLE_TEST_AHU_APLICABLE_TESTID + "=" + applicableTestAhu.getAplicable_testId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable partners.getId()=" + partners.getPartnerId() + " partners.getName()=" + partners.getName());
                    db.insert(tableName, null, contentValues);
                }

            }
            return true;
        } else {
            return false;
        }
    }


    //insert daa in servicereportdetails table
    public boolean insertPartners(String tableName, ArrayList<Partners> partnersArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (partnersArrayList.size() != 0) {
            for (Partners partners : partnersArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PARTNERS_ID, partners.getPartnerId());
                contentValues.put(PARTNERS_NAME, partners.getName());
                contentValues.put(PARTNERS_PARTNERCODE, partners.getPartnerCode());
                contentValues.put(PARTNERS_STATUS, partners.getStatus());
                contentValues.put(PARTNERS_APPROVEDSINCE, partners.getApprovedSince());
                contentValues.put(PARTNERS_ADDRESS, partners.getAddress());
                contentValues.put(PARTNERS_DIRECTORNAME, partners.getDirectorName());

                contentValues.put(PARTNERS_DIRECTOREMAIL, partners.getDirectorEmail());
                contentValues.put(PARTNERS_DIRECTORCELLNO, partners.getDirectorCellNo());
                contentValues.put(PARTNERS_REGEMAIL, partners.getRegEmail());
                contentValues.put(PARTNERS_REGCELLNO, partners.getRegCellNo());
                contentValues.put(PARTNERS_SERVICEINCHARGE, partners.getServiceIncharge());
                contentValues.put(PARTNERS_EMAIL, partners.getEmail());
                contentValues.put(PARTNERS_CELLNO, partners.getCellNo());
                contentValues.put(PARTNERS_LASTUPDATEDDATE, partners.getLastUpdatedDate());

                if (getExistingId(tableName, PARTNERS_ID, partners.getPartnerId()) > 0) {
                    db.update(tableName, contentValues, PARTNERS_ID + "=" + partners.getPartnerId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable partners.getId()=" + partners.getPartnerId() + " partners.getName()=" + partners.getName());
                    db.insert(tableName, null, contentValues);
                }

            }
            return true;
        } else {
            return false;
        }
    }


    //insert daa in servicereportdetails table
    public boolean insertServiceReportDetails(String tableName, ArrayList<ServiceReportDetail> reportDetailArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (reportDetailArrayList.size() != 0) {
            for (ServiceReportDetail serviceReportDetail : reportDetailArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(SERVICE_REPORT_DETAIL_ID, serviceReportDetail.getId());
                contentValues.put(SERVICE_REPORT_DETAIL_SERVICE_REPORT_ID, serviceReportDetail.getService_report_id());
                contentValues.put(SERVICE_REPORT_DETAIL_TYPEOFTEST, serviceReportDetail.getTypeOfTest());
                contentValues.put(SERVICE_REPORT_DETAIL_AREAOFTEST, serviceReportDetail.getAreaOfTest());

                contentValues.put(SERVICE_REPORT_DETAIL_EQUIPMENTAHUNO, serviceReportDetail.getEquipmentAhuNo());
                contentValues.put(SERVICE_REPORT_DETAIL_NOOFLOC, serviceReportDetail.getNoOfLoc());
                contentValues.put(SERVICE_REPORT_DETAIL_NOOFHOURDAYS, serviceReportDetail.getNoOfHourDays());

//                if (getExistingId(tableName, SERVICE_REPORT_DETAIL_ID, serviceReportDetail.getId()) > 0) {
                db.insert(tableName, null, contentValues);
//                } else {
//                    db.update(tableName, contentValues, SERVICE_REPORT_DETAIL_ID + "=" + serviceReportDetail.getId(),null);
//                }

            }
            return true;
        } else {
            return false;
        }
    }

    //insert daa in servicereport table
    public boolean insertServiceReport(String tableName, ServiceReport serviceReport) {
        SQLiteDatabase db = this.getWritableDatabase();
//        if (serviceReportArrayList.size() != 0) {
//            for (ServiceReport serviceReport : serviceReportArrayList) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVICE_REPORT_ID, serviceReport.getId());
        contentValues.put(SERVICE_REPORT_CUSTOMER, serviceReport.getCustomer());
        contentValues.put(SERVICE_REPORT_SERVICEENGINEER, serviceReport.getServiceEngineer());
        contentValues.put(SERVICE_REPORT_TIMEIN, serviceReport.getTimeIn());

        contentValues.put(SERVICE_REPORT_TIMEOUT, serviceReport.getTimeOut());
        contentValues.put(SERVICE_REPORT_SRNO, serviceReport.getSrNo());
        contentValues.put(SERVICE_REPORT_CUSTOMERPO, serviceReport.getCustomerPO());
        contentValues.put(SERVICE_REPORT_PLANT, serviceReport.getPlant());

        contentValues.put(SERVICE_REPORT_STATUS, serviceReport.getStatus());
        contentValues.put(SERVICE_REPORT_TESTERNAME, serviceReport.getTesterName());
        contentValues.put(SERVICE_REPORT_WITNESSNAME, serviceReport.getWitnessName());
        contentValues.put(SERVICE_REPORT_WITNESSCONTACT, serviceReport.getWitnessContact());

        contentValues.put(SERVICE_REPORT_WITNESSMAIL, serviceReport.getWitnessMail());
        contentValues.put(SERVICE_REPORT_PARTNERNAME, serviceReport.getPartnerName());
        contentValues.put(SERVICE_REPORT_SERVICEDATE, serviceReport.getServiceDate());
        contentValues.put(SERVICE_REPORT_REMARK, serviceReport.getRemark());

        contentValues.put(SERVICE_REPORT_WORKORDER, serviceReport.getWorkOrder());
        contentValues.put(SERVICE_REPORT_WITNESSDESIGNATION, serviceReport.getWitnessDesignation());

//        if (getExistingId(tableName, SERVICE_REPORT_ID, serviceReport.getId()) > 0) {
        db.insert(tableName, null, contentValues);
//        } else {
//            db.update(tableName, contentValues, SERVICE_REPORT_ID + "=" + serviceReport.getId(),null);
//        }

//            }
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }

    //insert daa in testreading table
    public boolean insertTestReading(String tableName, ArrayList<TestReading> readingArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (readingArrayList.size() != 0) {
            for (TestReading testReading : readingArrayList) {
                ContentValues contentValues = new ContentValues();
//                contentValues.put(TESTREADING_TESTREADINGID, testReading.getTestReadingID());
                contentValues.put(TESTREADING_TEST_DETAIL_ID, testReading.getTest_detail_id());
                contentValues.put(TESTREADING_ENTITYNAME, testReading.getEntityName());
                contentValues.put(TESTREADING_VALUE, testReading.getValue());

//                if (getExistingId(tableName, TESTREADING_TESTREADINGID, testReading.getId()) > 0) {
                db.insert(tableName, null, contentValues);
//                } else {
//                    db.update(tableName, contentValues, TESTREADING_TESTREADINGID + "=" + testReading.getId(),null);
//                }


            }
            return true;
        } else {
            return false;
        }
    }

    //insert daa in test details table
    public boolean insertTestDetails(String tableName, TestDetails testDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
//        if (testDetailsList.size() != 0) {
//            for (TestDetails testDetails : testDetailsList) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEST_DETAILS_TEST_DETAIL_ID, testDetails.getTest_detail_id());
        contentValues.put(TEST_DETAILS_TESTNAME, testDetails.getTestName());
        contentValues.put(TEST_DETAILS_CUSTOMER, testDetails.getCustomer());
        contentValues.put(TEST_DETAILS_RAWDATANO, testDetails.getRawDataNo());
        contentValues.put(TEST_DETAILS_DATEOFTEST, testDetails.getDateOfTest());
        contentValues.put(TEST_DETAILS_INSTRUMENTUSED, testDetails.getInstrumentUsed());
        contentValues.put(TEST_DETAILS_INSTRUMENTNO, testDetails.getInstrumentNo());
        contentValues.put(TEST_DETAILS_MAKE, testDetails.getMake());
        contentValues.put(TEST_DETAILS_MODEL, testDetails.getModel());
        contentValues.put(TEST_DETAILS_CALIBRATEDON, testDetails.getCalibratedOn());
        contentValues.put(TEST_DETAILS_CALIBRATEDDUEON, testDetails.getCalibratedDueOn());
        contentValues.put(TEST_DETAILS_TESTSPECIFICATION, testDetails.getTestSpecification());
        contentValues.put(TEST_DETAILS_TEST_TESTREFERENCE, testDetails.getTestReference());
        contentValues.put(TEST_DETAILS_OCCUPENCYSTATE, testDetails.getOccupencyState());
        contentValues.put(TEST_DETAILS_BLOCKNAME, testDetails.getBlockName());
        contentValues.put(TEST_DETAILS_TESTAREA, testDetails.getTestArea());
        contentValues.put(TEST_DETAILS_AHUNO, testDetails.getAhuNo());
        contentValues.put(TEST_DETAILS_ROOMNO, testDetails.getRoomNo());

        contentValues.put(TEST_DETAILS_ROOMNAME, testDetails.getRoomName());
        contentValues.put(TEST_DETAILS_EQUIPMENTNO, testDetails.getEquipmentNo());
        contentValues.put(TEST_DETAILS_EQUIPMENTNAME, testDetails.getEquipmentName());
        contentValues.put(TEST_DETAILS_TESTERNAME, testDetails.getTesterName());
        contentValues.put(TEST_DETAILS_WITNESSNAME, testDetails.getWitnessName());
        contentValues.put(TEST_DETAILS_PARTNERNAME, testDetails.getPartnerName());
        contentValues.put(TEST_DETAILS_SAMPLINGFLOWRATE, testDetails.getSamplingFlowRate());
        contentValues.put(TEST_DETAILS_SAMPLINGTIME, testDetails.getSamplingTime());
        contentValues.put(TEST_DETAILS_AEROSOLUSED, testDetails.getAerosolUsed());
        contentValues.put(TEST_DETAILS_AEROSOLGENERATORTYPE, testDetails.getAerosolGeneratorType());
        contentValues.put(TEST_DETAILS_TESTCODE, testDetails.getTestCode());
        contentValues.put(TEST_DETAILS_ROOMVOLUME, testDetails.getRoomVolume());
        contentValues.put(TEST_DETAILS_TESTWITNESSORG, testDetails.getTestWitnessOrg());
        contentValues.put(TEST_DETAILS_TESTCONDOCTORORG, testDetails.getTestCondoctorOrg());
        contentValues.put(TEST_DETAILS_TESTITEM, testDetails.getTestItem());

        db.insert(tableName, null, contentValues);
//            }
        return true;
//        } else {
//            return false;
//        }
    }

    //insert daa in test spesification value table
    public boolean insertTestSpesificationValue(String tableName, List<TestSpesificationValue> testSpesificationValueList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (testSpesificationValueList.size() != 0) {
            for (TestSpesificationValue testSpesificationValue : testSpesificationValueList) {
                ContentValues contentValues = new ContentValues();
//                contentValues.put(TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID, testSpesificationValue.getTest_specific_id());
                contentValues.put(TESTSPECIFICATIONVALUE_TEST_DETAIL_ID, testSpesificationValue.getTest_detail_id());
                contentValues.put(TESTSPECIFICATIONVALUE_FIELDNAME, testSpesificationValue.getFieldName());
                contentValues.put(TESTSPECIFICATIONVALUE_FIELDVALUE, testSpesificationValue.getFieldValue());
                db.insert(tableName, null, contentValues);
            }
            return true;
        } else {
            return false;
        }
    }

    //insert daa in ahu table
    public boolean insertAhu(String tableName, List<Ahu> ahuArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (ahuArrayList.size() != 0) {
            for (Ahu ahu : ahuArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(AHU_AHUID, ahu.getAhuId());
                contentValues.put(AHU_AHUNO, ahu.getAhuNo());
                contentValues.put(AHU_AHUTYPE, ahu.getAhuType());
                contentValues.put(AHU_AREAID, ahu.getAreId());
                contentValues.put(AHU_CAPACITY, ahu.getCapacity());
                contentValues.put(AHU_RETURNAIRCFM, ahu.getReturnAirCFM());
                contentValues.put(AHU_EXHAUSTAIRCFM, ahu.getExhaustAirCFM());
                contentValues.put(AHU_BLEEDFILTERTYPE, ahu.getBleedFilterType());
                contentValues.put(AHU_BLEEDFILTEREFFICIENCY, ahu.getBleedFilterEfficiency());
                contentValues.put(AHU_BLEEDAIRCFM, ahu.getBleedAirCFM());
                contentValues.put(AHU_BLEEDFILTERQTY, ahu.getBleedFilterQty());
                contentValues.put(AHU_BLEEDFILTERLEAK, ahu.getBleedFilterLeak());
                contentValues.put(AHU_FRESHFILTERTYPE, ahu.getFreshFilterType());
                contentValues.put(AHU_FRESHAIRCFM, ahu.getFreshAirCFM());
                contentValues.put(AHU_FRESHFILTERQTY, ahu.getFreshFilterQty());
                contentValues.put(AHU_FRESHFILTEREFFICIENCY, ahu.getFreshFilterEfficiency());
                contentValues.put(AHU_FINALFILTERAIRFLOW, ahu.getFinalFilterAirFlow());
                contentValues.put(AHU_FINALFILTERQTY, ahu.getFinalFilterQty());
                contentValues.put(AHU_FINALFILTERTYPE, ahu.getFinalFilterType());
                contentValues.put(AHU_FINALFILTEREFFICIENCY, ahu.getFinalFilterEfficiency());
                contentValues.put(AHU_FINALFILTERLEAK, ahu.getFinalFilterLeak());
                contentValues.put(AHU_REMARKS, ahu.getRemarks());
                contentValues.put(AHU_LASTUPDATEDDATE, ahu.getLastUpdatedDate());
                if (getExistingId(tableName, AHU_AHUID, ahu.getAhuId()) > 0) {
                    db.update(tableName, contentValues, AHU_AHUID + "=" + ahu.getAhuId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable ahu.getAhuId()=" + ahu.getAhuId() + " partners.getName()=" + ahu.getAhuNo());
                    db.insert(tableName, null, contentValues);

                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain ApplicableTestEquipment table
    public boolean insertApplicableTestEquipment(String tableName, List<ApplicableTestEquipment> applicableTestEquipmentList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (applicableTestEquipmentList.size() != 0) {
            for (ApplicableTestEquipment applicableTestEquipment : applicableTestEquipmentList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(APLICABLE_TEST_EQUIPMENT_APLICABLE_TESTID, applicableTestEquipment.getAplicable_testId());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_EQUIPMENTID, applicableTestEquipment.getEquipmentId());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_TESTNAME, applicableTestEquipment.getTestName());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_TESTCODE, applicableTestEquipment.getTestCode());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_TESTFORMAT, applicableTestEquipment.getTestFormat());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_TESTSPECIFICATION, applicableTestEquipment.getTestSpecification());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_OCCUPENCYSTATE, applicableTestEquipment.getOccupencyState());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_TESTREFERENCE, applicableTestEquipment.getTestReference());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_TESTPROP, applicableTestEquipment.getTestProp());

                contentValues.put(APLICABLE_TEST_EQUIPMENT_PERIODICITY, applicableTestEquipment.getPeriodicity());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_LOCATION, applicableTestEquipment.getLocation());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_NOOFCYCLE, applicableTestEquipment.getNoOfCycle());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_CREATIONDATE, applicableTestEquipment.getLastUpdatedDate());

                if (getExistingId(tableName, APLICABLE_TEST_EQUIPMENT_APLICABLE_TESTID, applicableTestEquipment.getAplicable_testId()) > 0) {
                    db.update(tableName, contentValues, APLICABLE_TEST_EQUIPMENT_APLICABLE_TESTID + "=" + applicableTestEquipment.getAplicable_testId(), null);
                } else {
                    Log.d("Avinash", "insert applicable applicableTestEquipment.getAplicable_testId()=" + applicableTestEquipment.getAplicable_testId() + " partners.getName()=" + applicableTestEquipment.getEquipmentId());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain ApplicableTestRoom table
    public boolean insertApplicableTestRoom(String tableName, List<ApplicableTestRoom> applicableTestRoomList) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Avinash", "insert applicable applicableTestRoom.applicableTestRoomList=" + applicableTestRoomList.size());
        if (applicableTestRoomList.size() != 0) {
            for (ApplicableTestRoom applicableTestRoom : applicableTestRoomList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(APLICABLE_TEST_ROOM_APLICABLE_TESTID, applicableTestRoom.getAplicable_testId());
                contentValues.put(APLICABLE_TEST_ROOM_ROOMID, applicableTestRoom.getRoomId());
                contentValues.put(APLICABLE_TEST_ROOM_TESTNAME, applicableTestRoom.getTestName());
                contentValues.put(APLICABLE_TEST_ROOM_TESTCODE, applicableTestRoom.getTestCode());
                contentValues.put(APLICABLE_TEST_ROOM_TESTFORMAT, applicableTestRoom.getTestFormat());
                contentValues.put(APLICABLE_TEST_ROOM_TESTSPECIFICATION, applicableTestRoom.getTestSpecification());
                contentValues.put(APLICABLE_TEST_ROOM_OCCUPENCYSTATE, applicableTestRoom.getOccupencyState());
                contentValues.put(APLICABLE_TEST_ROOM_TESTREFERENCE, applicableTestRoom.getTestReference());
                contentValues.put(APLICABLE_TEST_ROOM_TESTPROP, applicableTestRoom.getTestProp());
                contentValues.put(APLICABLE_TEST_ROOM_PERIODICITY, applicableTestRoom.getPeriodicity());
                contentValues.put(APLICABLE_TEST_ROOM_LOCATION, applicableTestRoom.getLocation());
                contentValues.put(APLICABLE_TEST_ROOM_NOOFCYCLE, applicableTestRoom.getNoOfCycle());
                contentValues.put(APLICABLE_TEST_ROOM_LASTUPDATEDDATE, applicableTestRoom.getLastUpdatedDate());

                if (getExistingId(tableName, APLICABLE_TEST_ROOM_APLICABLE_TESTID, applicableTestRoom.getAplicable_testId()) > 0) {
                    db.update(tableName, contentValues, APLICABLE_TEST_ROOM_APLICABLE_TESTID + "=" + applicableTestRoom.getAplicable_testId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable applicableTestRoom.getAplicable_testId()=" + applicableTestRoom.getAplicable_testId() + " applicableTestRoom.getRoomId()=" + applicableTestRoom.getRoomId());
                    db.insert(tableName, null, contentValues);

                }

            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain Equipment table
    public boolean insertEquipment(String tableName, List<Equipment> equipmentList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (equipmentList.size() != 0) {
            for (Equipment equipment : equipmentList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(EQUIPMENT_EQUIPMENTID, equipment.getEquipmentId());
                contentValues.put(EQUIPMENT_ROOMID, equipment.getRoomId());
                contentValues.put(EQUIPMENT_EQUIPMENTNO, equipment.getEquipmentNo());
                contentValues.put(EQUIPMENT_EQUIPMENTNAME, equipment.getEquipmentName());
                contentValues.put(EQUIPMENT_MINVELOCITY, equipment.getMinVelocity());
                contentValues.put(EQUIPMENT_MAXVELOCITY, equipment.getMaxVelocity());
                contentValues.put(EQUIPMENT_SUPPLYAIRFLOW, equipment.getSupplyAirflow());
                contentValues.put(EQUIPMENT_EQUIPMENTPRESSURE, equipment.getEquipmentPressure());
                contentValues.put(EQUIPMENT_EXHUSTAIRFLOW, equipment.getExhustAirflow());
                contentValues.put(EQUIPMENT_REMARKS, equipment.getRemarks());
                contentValues.put(EQUIPMENT_LASTUPDATEDDATE, equipment.getLastUpdatedDate());
                Log.d("Avinash", "isert db minvalocity=" + equipment.getMinVelocity() + "equipment.getMaxVelocity()=" + equipment.getMaxVelocity());
                if (getExistingId(tableName, EQUIPMENT_EQUIPMENTID, equipment.getEquipmentId()) > 0) {
                    db.update(tableName, contentValues, EQUIPMENT_EQUIPMENTID + "=" + equipment.getEquipmentId(), null);
                } else {
                    db.insert(tableName, null, contentValues);

                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain EquipmentFilter table
    public boolean insertEquipmentfilter(String tableName, List<EquipmentFilter> equipmentFilterList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (equipmentFilterList.size() != 0) {
            for (EquipmentFilter equipmentFilter : equipmentFilterList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(EQUIPMENTFILTER_FILTERID, equipmentFilter.getFilterId());
                contentValues.put(EQUIPMENTFILTER_FILTERCODE, equipmentFilter.getFilterCode());
                contentValues.put(EQUIPMENTFILTER_EQUIPMENTID, equipmentFilter.getEquipmentId());
                contentValues.put(EQUIPMENTFILTER_WIDTH, equipmentFilter.getWidth());
                contentValues.put(EQUIPMENTFILTER_LENGTH, equipmentFilter.getLength());
                contentValues.put(EQUIPMENTFILTER_GRILLAREA, equipmentFilter.getGrillArea());
                contentValues.put(EQUIPMENTFILTER_FILTERTYPE, equipmentFilter.getFilterType());
                contentValues.put(EQUIPMENTFILTER_EFFICIENCY, equipmentFilter.getEfficiency());
                contentValues.put(EQUIPMENTFILTER_PARTICLESIZE, equipmentFilter.getParticleSize());
                contentValues.put(EQUIPMENTFILTER_SPECIFICATIONLEAK, equipmentFilter.getSpecificationLeak());
                contentValues.put(EQUIPMENTFILTER_LASTUPDATEDDATE, equipmentFilter.getLastUpdatedDate());

                if (getExistingId(tableName, EQUIPMENTFILTER_FILTERID, equipmentFilter.getFilterId()) > 0) {
                    db.update(tableName, contentValues, EQUIPMENTFILTER_FILTERID + "=" + equipmentFilter.getFilterId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable equipmentFilter.getFilterId()=" + equipmentFilter.getFilterId() + " applicableTestRoom.getAplicable_testId()=" + equipmentFilter.getFilterCode());
                    db.insert(tableName, null, contentValues);

                }

            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain Grill table
    public boolean insertGrill(String tableName, List<Grill> grillList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (grillList.size() != 0) {
            for (Grill grill : grillList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(GRILL_GRILLID, grill.getGrillId());
                contentValues.put(GRILL_GRILLCODE, grill.getGrillCode());
                contentValues.put(GRILL_ROOMID, grill.getRoomId());
                contentValues.put(GRILL_LENGTH, grill.getLength());
                contentValues.put(GRILL_WIDTH, grill.getWidh());
                contentValues.put(GRILL_GRILLAREA, grill.getGrillArea());
                contentValues.put(GRILL_EFFECTIVEAREA, grill.getEffectiveArea());
                contentValues.put(GRILL_ISSUPPLYGRILL, grill.getIsSupplyGrill());
                contentValues.put(GRILL_LASTUPDATEDDATE, grill.getLastUpdatedDate());
//                contentValues.put(GRILL_ADDITIONALDETAIL, grill.getAdditionalDetail());

                if (getExistingId(tableName, GRILL_GRILLID, grill.getGrillId()) > 0) {
                    db.update(tableName, contentValues, GRILL_GRILLID + "=" + grill.getGrillId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable grill.getGrillId()=" + grill.getGrillId() + " grill.getGrillCode()=" + grill.getGrillCode());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain PartnerInstrument table
    public boolean insertPartnerInstrument(String tableName, List<PartnerInstrument> partnerInstrumentList) {
        Log.d("VALDOC", "controler response data  13=inserting=partnerInstrumentList=" + partnerInstrumentList.size());
        SQLiteDatabase db = this.getWritableDatabase();
        if (partnerInstrumentList.size() != 0) {
            for (PartnerInstrument partnerInstrument : partnerInstrumentList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PARTNER_INSTRUMENT_PINSTRUMENTID, partnerInstrument.getpInstrumentId());
                contentValues.put(PARTNER_INSTRUMENT_PARTNERID, partnerInstrument.getPartnerId());
                contentValues.put(PARTNER_INSTRUMENT_SERIALNO, partnerInstrument.getSerialNo());
                contentValues.put(PARTNER_INSTRUMENT_PINSTRUMENTNAME, partnerInstrument.getpInstrumentName());
                contentValues.put(PARTNER_INSTRUMENT_MAKE, partnerInstrument.getMake());
                contentValues.put(PARTNER_INSTRUMENT_MODEL, partnerInstrument.getModel());
                contentValues.put(PARTNER_INSTRUMENT_LASTCALIBRATED, partnerInstrument.getLastCalibrationDate());
                contentValues.put(PARTNER_INSTRUMENT_CALIBRATIONDUEDATE, partnerInstrument.getCalibrationDueDate());
                contentValues.put(PARTNER_INSTRUMENT_STATUS, partnerInstrument.getStatus());
                contentValues.put(PARTNER_INSTRUMENT_CERTFILENAME, partnerInstrument.getCertFileName());
                contentValues.put(PARTNER_INSTRUMENT_REMARKS, partnerInstrument.getRemarks());
                contentValues.put(PARTNER_INSTRUMENT_LASTUPDATEDDATE, partnerInstrument.getLastUpdatedDate());
//                contentValues.put(PARTNER_INSTRUMENT_CURRENTLOCATION, partnerInstrument.getCurrentLocation());
//                contentValues.put(PARTNER_INSTRUMENT_TESTID, partnerInstrument.getTestId());
//                contentValues.put(PARTNER_INSTRUMENT_SAMPLINGFLOWRATE, partnerInstrument.getSamplingFlowRate());
//                contentValues.put(PARTNER_INSTRUMENT_SAMPLINGTIME, partnerInstrument.getSamplingTime());
//                contentValues.put(PARTNER_INSTRUMENT_AEROSOLUSED, partnerInstrument.getAerosolUsed());
//                contentValues.put(PARTNER_INSTRUMENT_AEROSOLGENERATORTYPE, partnerInstrument.getAerosolGeneratorType());

                if (getExistingId(tableName, PARTNER_INSTRUMENT_PINSTRUMENTID, partnerInstrument.getpInstrumentId()) > 0) {
                    db.update(tableName, contentValues, PARTNER_INSTRUMENT_PINSTRUMENTID + "=" + partnerInstrument.getpInstrumentId(), null);
                } else {
                    Log.d("Avinash", "insert applicable partnerInstrument.getpInstrumentId()=" + partnerInstrument.getpInstrumentId() + " partnerInstrument.getpInstrumentId()=" + partnerInstrument.getpInstrumentId());
                    db.insert(tableName, null, contentValues);

                }

            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain ClientInstrument table
    public boolean insertClientInstrument(String tableName, List<ClientInstrument> clientInstrumentList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (clientInstrumentList.size() != 0) {
            for (ClientInstrument clientInstrument : clientInstrumentList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(CLIENT_INSTRUMENT_CINSTRUMENTID, clientInstrument.getcInstrumentId());
                contentValues.put(CLIENT_INSTRUMENT_INSTRUMENTID, clientInstrument.getInstrumentId());
                contentValues.put(CLIENT_INSTRUMENT_SERIALNO, clientInstrument.getSerialNo());
                contentValues.put(CLIENT_INSTRUMENT_CINSTRUMENTNAME, clientInstrument.getcInstrumentName());
                contentValues.put(CLIENT_INSTRUMENT_MAKE, clientInstrument.getMake());
                contentValues.put(CLIENT_INSTRUMENT_MODEL, clientInstrument.getModel());
                contentValues.put(CLIENT_INSTRUMENT_LASTCALIBRATED, clientInstrument.getLastCalibrated());
                contentValues.put(CLIENT_INSTRUMENT_CALIBRATIONDUEDATE, clientInstrument.getCalibrationDueDate());
//                contentValues.put(CLIENT_INSTRUMENT_CURRENTLOCATION, clientInstrument.getCurrentLocation());
                contentValues.put(CLIENT_INSTRUMENT_STATUS, clientInstrument.getStatus());
                contentValues.put(CLIENT_INSTRUMENT_RANGE, clientInstrument.getRange());
                contentValues.put(CLIENT_INSTRUMENT_LASTUPDATEDDATE, clientInstrument.getLastUpdatedDate());
//                contentValues.put(CLIENT_INSTRUMENT_SAMPLINGFLOWRATE, clientInstrument.getSamplingFlowRate());
//                contentValues.put(CLIENT_INSTRUMENT_SAMPLINGTIME, clientInstrument.getSamplingTime());
//                contentValues.put(CLIENT_INSTRUMENT_AEROSOLUSED, clientInstrument.getAerosolUsed());
//                contentValues.put(CLIENT_INSTRUMENT_AEROSOLGENERATORTYPE, clientInstrument.getAerosolGeneratorType());

                if (getExistingId(tableName, CLIENT_INSTRUMENT_CINSTRUMENTID, clientInstrument.getcInstrumentId()) > 0) {
                    db.update(tableName, contentValues, CLIENT_INSTRUMENT_CINSTRUMENTID + "=" + clientInstrument.getcInstrumentId(), null);
                } else {
                    Log.d("Avinash", "insert applicable clientInstrument.getcInstrumentId()=" + clientInstrument.getcInstrumentId() + " clientInstrument.getcInstrumentId()=" + clientInstrument.getcInstrumentId());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain ClientInstrumentTest table
    public boolean insertClientInstrumentTest(String tableName, List<ClientInstrumentTest> clientInstrumentTestList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (clientInstrumentTestList.size() != 0) {
            for (ClientInstrumentTest clientInstrumentTest : clientInstrumentTestList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_ID, clientInstrumentTest.getClientInstrumentTestId());
                contentValues.put(CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_ID, clientInstrumentTest.getClientInstrumentId());
                contentValues.put(CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_CODE, clientInstrumentTest.getClientInstrumentTestCode());
                contentValues.put(CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_NAME, clientInstrumentTest.getClientInstrumentTestName());
                contentValues.put(CLIENT_INSTRUMENT_TEST_LASTUPDATEDDATE, clientInstrumentTest.getLastUpdatedDate());
                if (getExistingId(tableName, CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_ID, clientInstrumentTest.getClientInstrumentTestId()) > 0) {
                    db.update(tableName, contentValues, CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_ID + "=" + clientInstrumentTest.getClientInstrumentTestId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable clientInstrument.getcInstrumentId()=" + clientInstrument.getcInstrumentId() + " clientInstrument.getcInstrumentId()=" + clientInstrument.getcInstrumentId());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain plant table
    public boolean insertPlant(String tableName, List<Plant> plantList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (plantList.size() != 0) {
            for (Plant plant : plantList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PLANT_PLANTID, plant.getPlantId());
                contentValues.put(PLANT_PLANTNAME, plant.getPlantName());
                contentValues.put(PLANT_ADDRESS, plant.getAddress());
                contentValues.put(PLANT_ADDITIONALDETAILS, plant.getAdditionalDetails());
                contentValues.put(PLANT_DIRECTORNAME, plant.getDirectorName());
                contentValues.put(PLANT_DIRECTORCONTACTNO, plant.getDirectorContactNo());
                contentValues.put(PLANT_DIRECTOREMAILID, plant.getDirectorEmailId());
                contentValues.put(PLANT_CONTACTPERSONNAME, plant.getContactPersonName());
                contentValues.put(PLANT_CONTACTPERSONNO, plant.getContactPersonNo());

                if (getExistingId(tableName, PLANT_PLANTID, plant.getPlantId()) > 0) {
                    db.update(tableName, contentValues, PLANT_PLANTID + "=" + plant.getPlantId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable plant.getcInstrumentId()=" + plant.getcInstrumentId() + " plant.getcInstrumentId()=" + clientInstrument.getcInstrumentId());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain PartnerInstrumentTest table
    public boolean insertPartnerInstrumentTest(String tableName, List<PartnerInstrumentTest> partnerInstrumentTestList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (partnerInstrumentTestList.size() != 0) {
            for (PartnerInstrumentTest partnerInstrumentTest : partnerInstrumentTestList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PARTNER_INSTRUMENT_TEST_ID, partnerInstrumentTest.getPartnerInstrumentTestId());
                contentValues.put(PARTNER_INSTRUMENT_ID, partnerInstrumentTest.getPartnerInstrumentId());
                contentValues.put(PARTNER_INSTRUMENT_TEST_CODE, partnerInstrumentTest.getPartnerInstrumentTestCode());
                contentValues.put(PARTNER_INSTRUMENT_TEST_NAME, partnerInstrumentTest.getPartnerInstrumentTestName());
                contentValues.put(PARTNER_INSTRUMENT_TEST_LASTUPDATEDDATE, partnerInstrumentTest.getLastUpdatedDate());
                contentValues.put(PARTNER_INSTRUMENT_TEST_RANGE, partnerInstrumentTest.getRange());

                if (getExistingId(tableName, PARTNER_INSTRUMENT_TEST_ID, partnerInstrumentTest.getPartnerInstrumentTestId()) > 0) {
                    db.update(tableName, contentValues, PARTNER_INSTRUMENT_TEST_ID + "=" + partnerInstrumentTest.getPartnerInstrumentTestId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable clientInstrument.getcInstrumentId()=" + clientInstrument.getcInstrumentId() + " clientInstrument.getcInstrumentId()=" + clientInstrument.getcInstrumentId());
                    db.insert(tableName, null, contentValues);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain Room table
    public boolean insertRoom(String tableName, List<Room> roomList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (roomList.size() != 0) {
            for (Room room : roomList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ROOM_ROOMID, room.getRoomId());
                contentValues.put(ROOM_AREAID, room.getAreaId());
                contentValues.put(ROOM_AHUID, room.getAhuId());
                contentValues.put(ROOM_ROOMNAME, room.getRoomName());
                contentValues.put(ROOM_ROOMNO, room.getRoomNo());
                contentValues.put(ROOM_WIDTH, room.getWidth());
                contentValues.put(ROOM_HEIGHT, room.getHeight());
                contentValues.put(ROOM_LENGTH, room.getLength());
                contentValues.put(ROOM_AREA, room.getArea());
                contentValues.put(ROOM_VOLUME, room.getVolume());
                contentValues.put(ROOM_ACPH, room.getAcph());
                contentValues.put(ROOM_TESTREF, room.getTestRef());
                contentValues.put(ROOM_ISOCLAUSE, room.getIsoClause());
                contentValues.put(ROOM_OCCUPANCYSTATE, room.getOccupancyState());
                contentValues.put(ROOM_SUPPLYAIRFLOW, room.getSupplyAirflow());
                contentValues.put(ROOM_AHUFLOWCFM, room.getAhuFlowCFM());
                contentValues.put(ROOM_ROOMPRESSUREPA, room.getRoomPressure());
                contentValues.put(ROOM_FRESHAIRCFM, room.getFreshAirCFM());
                contentValues.put(ROOM_BLEEDAIRCFM, room.getBleedAirCFM());
                contentValues.put(ROOM_EXHAUSTAIRFLOW, room.getExhaustAirFlow());
                contentValues.put(ROOM_MAXTEMPERATURE, room.getMaxTemperature());
                contentValues.put(ROOM_MAXRH, room.getMaxRH());
                contentValues.put(ROOM_MINTEMPERATURE, room.getMinTemperature());
                contentValues.put(ROOM_MINRH, room.getMinRH());
                contentValues.put(ROOM_RETURNAIRFLOW, room.getReturnAirFlow());
//                contentValues.put(ROOM_SUPPLYAIRGRILLQTY, room.getSupplyAirGrillQTY());
//                contentValues.put(ROOM_RETURNAIRGRILLQTY, room.getReturnAirGrillQTY());
//                contentValues.put(ROOM_SUPPLYAIRFILTERQTY, room.getSupplyAirFilterQTY());
//                contentValues.put(ROOM_RETURNAIRFILTERQTY, room.getReturnAirFilterQTY());
                contentValues.put(ROOM_REMARKS, room.getRemarks());
                contentValues.put(ROOM_LASTUPDATEDDATE, room.getLastUpdatedDate());

                if (getExistingId(tableName, ROOM_ROOMID, room.getRoomId()) > 0) {
                    db.update(tableName, contentValues, ROOM_ROOMID + "=" + room.getRoomId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable room.getRoomId()=" + room.getRoomId() + " room.getAreaId()=" + room.getAreaId());
                    db.insert(tableName, null, contentValues);

                }

            }
            return true;

        } else {
            return false;
        }
    }

    // insert data in RoomFilter table
    public boolean insertRoomFilter(String tableName, List<RoomFilter> roomFilterList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (roomFilterList.size() != 0) {
            for (RoomFilter roomFilter : roomFilterList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(ROOMFILTER_FILTERID, roomFilter.getFilterId());
                contentValues.put(ROOMFILTER_FILTERCODE, roomFilter.getFilterCode());
                contentValues.put(ROOMFILTER_ROOMID, roomFilter.getRoomId());
                contentValues.put(ROOMFILTER_FILTERTYPE, roomFilter.getFilterType());
                contentValues.put(ROOMFILTER_EFFICIENCY, roomFilter.getEfficiency());
                contentValues.put(ROOMFILTER_PARTICLESIZE, roomFilter.getParticleSize());
                contentValues.put(ROOMFILTER_SPECIFICATION, roomFilter.getSpecification());
                contentValues.put(ROOMFILTER_WIDTH, roomFilter.getWidth());
                contentValues.put(ROOMFILTER_LENGTH, roomFilter.getLength());
                contentValues.put(ROOMFILTER_AREA, roomFilter.getArea());
                contentValues.put(ROOMFILTER_EFFECTIVEFILTERAREA, roomFilter.getEffectiveFilterArea());
                contentValues.put(ROOMFILTER_FILTERLOCATION, roomFilter.getFilterLocation());
                contentValues.put(ROOMFILTER_ISSUPPLYFILTER, roomFilter.getIsSupplyFilter());
                contentValues.put(ROOMFILTER_LASTUPDATEDDATE, roomFilter.getLastUpdatedDate());

                if (getExistingId(tableName, ROOMFILTER_FILTERID, roomFilter.getFilterId()) > 0) {
                    db.update(tableName, contentValues, ROOMFILTER_FILTERID + "=" + roomFilter.getFilterId(), null);
                } else {
//                    Log.d("Avinash", "insert applicable roomFilter.getFilterId()=" + roomFilter.getFilterId() + " roomFilter.getFilterId()=" + roomFilter.getFilterId());
                    db.insert(tableName, null, contentValues);

                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert data in user table
    public boolean insertUser(String tableName, List<AppUser> userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (userList.size() != 0) {
            for (AppUser user : userList) {
//                Log.d("valdoc", "insert user Login method :" + user.getName() + "\n" + user.getId());
                ContentValues contentValues = new ContentValues();
                contentValues.put(USER_ID, user.getApp_user_id());
                contentValues.put(USER_NAME, user.getName());
                contentValues.put(USER_TYPE, user.getUserType());
                contentValues.put(USER_EMAIL, user.getEmail());
                contentValues.put(USER_CONTACT, user.getContact());
                contentValues.put(USER_DEPARTMENT, user.getDepartment());
                contentValues.put(USER_ACTIVE, user.getIsActive());
                contentValues.put(USER_DELETED, user.getIsDeleted());
                contentValues.put(USER_PASSWORD, user.getPassword());
                contentValues.put(USER_PARTNERID, user.getPartnerId());
                contentValues.put(USER_ROLE_TYPE, user.getRoleType());
                contentValues.put(USER_PERMISSIONS, user.getPermissions());
                contentValues.put(USER_LAST_UPDATED, user.getLastUpdated());

                if (getExistingId(tableName, USER_ID, user.getApp_user_id()) > 0) {
                    db.update(tableName, contentValues, USER_ID + "=" + user.getApp_user_id(), null);
                } else {
//                    Log.d("Avinash", "insert applicable user.getId()=" + user.getApp_user_id() + " user.getName()=" + user.getName());
                    db.insert(tableName, null, contentValues);

                }
            }
            return true;
        } else {
            return false;
        }
    }

    // insert data in partneruser table
    public boolean insertPartnerUser(String tableName, List<PartnerUser> partnerUserList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (partnerUserList.size() != 0) {
            for (PartnerUser partnerUser : partnerUserList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PARTNERUSER_PARTNER_USER_ID, partnerUser.getPartner_user_id());
                contentValues.put(PARTNERUSER_APP_USER_ID, partnerUser.getApp_user_id());
                contentValues.put(PARTNERUSER_PARTNERID, partnerUser.getPartnerId());
                contentValues.put(PARTNERUSER_CREATIONDATE, partnerUser.getCreationDate());

                if (getExistingId(tableName, PARTNERUSER_PARTNER_USER_ID, partnerUser.getPartner_user_id()) > 0) {
                    db.update(tableName, contentValues, PARTNERUSER_PARTNER_USER_ID + "=" + partnerUser.getPartner_user_id(), null);
                } else {
                    Log.d("Avinash", "insert partnerUser.getPartner_user_id()=" + partnerUser.getPartner_user_id() + " partnerUser.getApp_user_id()=" + partnerUser.getApp_user_id());
                    db.insert(tableName, null, contentValues);

                }

            }
            return true;
        } else {
            return false;
        }
    }

    // insert data in test master table
    public boolean insertTestMaster(String tableName, List<TestMaster> testMasterList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (testMasterList.size() != 0) {
            for (TestMaster testMaster : testMasterList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TESTMASTER_TESTID, testMaster.getTestid());
                contentValues.put(TESTMASTER_TESTNAME, testMaster.getTestName());
                contentValues.put(TESTMASTER_SPECIFICATION, testMaster.getSpecification());
                contentValues.put(TESTMASTER_CREATIONDATE, testMaster.getCreationDate());

                if (getExistingId(tableName, TESTMASTER_TESTID, testMaster.getTestid()) > 0) {
                    db.update(tableName, contentValues, TESTMASTER_TESTID + "=" + testMaster.getTestid(), null);
                } else {
                    Log.d("Avinash", "insert testMaster.getTestid()=" + testMaster.getTestid() + " testMaster.getTestName()=" + testMaster.getTestName());

                    db.insert(tableName, null, contentValues);

                }

            }
            return true;
        } else {
            return false;
        }
    }

    // insert data in test master table
    public boolean insertTestArea(String tableName, List<Area> areaList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (areaList.size() != 0) {
            for (Area area : areaList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(AREA_AREAID, area.getAreaId());
                contentValues.put(AREA_PLANTID, area.getPlantId());
                contentValues.put(AREA_AREANAME, area.getAreaName());
                contentValues.put(AREA_CREATIONDATE, area.getlastUpdatedDate());

                if (getExistingId(tableName, AREA_AREAID, area.getAreaId()) > 0) {
                    db.update(tableName, contentValues, AREA_AREAID + "=" + area.getAreaId(), null);
                } else {
//                    Log.d("Avinash", "insert area.getAreaId()=" + area.getAreaId() + " area.getPlantId()=" + area.getPlantId());

                    db.insert(tableName, null, contentValues);

                }

            }
            return true;
        } else {
            return false;
        }
    }

    // isoparticle
    public IsoParticleLimits getIsoParticle(String className) {
        IsoParticleLimits isoParticleLimits = new IsoParticleLimits();
        String selectQuery = "SELECT * FROM " + ISOPARTICLELIMITS_TABLE_NAME + " WHERE " + ValdocDatabaseHandler.ISOPARTICLELIMITS_CLASS + " = " + '"' + className + '"';
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "Login method :");
        if (cursor.moveToFirst()) {
            do {
                isoParticleLimits.setLimitId(cursor.getInt(0));
                isoParticleLimits.setParticleClass(cursor.getString(1));
                isoParticleLimits.setRestSmallParticleLimit(cursor.getString(2));
                isoParticleLimits.setRestLargeParticleLimit(cursor.getString(3));
                isoParticleLimits.setOperationSmallParticleLimit(cursor.getString(4));
                isoParticleLimits.setOperationLargeParticleLimit(cursor.getString(5));
                isoParticleLimits.setLastUpdatedDate(cursor.getString(6));
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return isoParticleLimits;
    }

    // select data from user table
    public ArrayList<AppUser> getUserInfo() {
        ArrayList<AppUser> userArrayList;
        userArrayList = new ArrayList<AppUser>();
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "Login method :");
        if (cursor.moveToFirst()) {
            do {
                AppUser user = new AppUser();
                user.setApp_user_id(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setUserType(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setContact(cursor.getString(4));
                user.setDepartment(cursor.getString(5));
                user.setIsActive(Integer.parseInt(cursor.getString(6)));
                user.setIsDeleted(Integer.parseInt(cursor.getString(7)));
                user.setPassword(cursor.getString(8));
                user.setPartnerId(cursor.getInt(9));
                user.setRoleType(cursor.getString(10));
                user.setPermissions(cursor.getString(11));
                user.setLastUpdated(cursor.getString(12));
                Log.d("valdoc", "Login method :" + user.getEmail() + "\n" + user.getPassword());
                userArrayList.add(user);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return userArrayList;
    }

    // select data from clientInstrument table
    public ArrayList<ClientInstrument> getClientInstrumentInfo(String testCode) {
        Log.d("Avinash", "db getClientInstrumentInfo :" + " testCode=" + testCode);
        ArrayList<ClientInstrument> clientInstrumentArrayList;
        clientInstrumentArrayList = new ArrayList<ClientInstrument>();
        String selectQuery = "SELECT * FROM " + CLIENT_INSTRUMENT_TABLE_NAME + "," + CLIENT_INSTRUMENT_TEST_TABLE_NAME + " WHERE " + CLIENT_INSTRUMENT_TABLE_NAME
                + "." + CLIENT_INSTRUMENT_CINSTRUMENTID + "=" + CLIENT_INSTRUMENT_TEST_TABLE_NAME + "." + CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_ID
                + " AND " + CLIENT_INSTRUMENT_TEST_TABLE_NAME + "." + CLIENT_INSTRUMENT_TEST_CLIENT_INSTRUMENT_TEST_CODE + "=" + '"' + testCode + '"';
//        String selectQuery1 = "SELECT * FROM " + CLIENT_INSTRUMENT_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Log.d("Avinash", "db clientInstrument selectQuery=" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("Avinash", "db cursor.getCount=" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                ClientInstrument clientInstrument = new ClientInstrument();
                clientInstrument.setcInstrumentId(cursor.getInt(0));
                clientInstrument.setInstrumentId(cursor.getString(1));
                clientInstrument.setSerialNo(cursor.getString(2));
                clientInstrument.setcInstrumentName(cursor.getString(3));
                clientInstrument.setMake(cursor.getString(4));
                clientInstrument.setModel(cursor.getString(5));
                clientInstrument.setLastCalibrated(cursor.getString(6));
                clientInstrument.setCalibrationDueDate(cursor.getString(7));
//                clientInstrument.setCurrentLocation(cursor.getString(8));
                clientInstrument.setStatus(cursor.getString(8));
                clientInstrument.setCertFileName(cursor.getString(9));
                clientInstrument.setRemarks(cursor.getString(10));
                clientInstrument.setLastUpdatedDate(cursor.getString(11));
                clientInstrument.setRange(cursor.getString(12));
//                clientInstrument.setSamplingFlowRate(cursor.getString(12));
//                clientInstrument.setSamplingTime(cursor.getString(13));
//                clientInstrument.setAerosolUsed(cursor.getString(14));
//                clientInstrument.setAerosolGeneratorType(cursor.getString(15));
                Log.d("Avinash", "db clientInstrument id" + clientInstrument.getcInstrumentId());
                Log.d("Avinash", "db clientInstrument" + clientInstrument.getSerialNo());
                clientInstrumentArrayList.add(clientInstrument);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return clientInstrumentArrayList;
    }

    // select data from partnerInstrument table
    public ArrayList<PartnerInstrument> getPartnerInstrumentInfo(int partnerId, String testCode) {
        ArrayList<PartnerInstrument> partnerInstrumentArrayList;
        partnerInstrumentArrayList = new ArrayList<PartnerInstrument>();
        String selectQuery = "SELECT * FROM " + PARTNER_INSTRUMENT_TABLE_NAME + "," + PARTNER_INSTRUMENT_TEST_TABLE_NAME + " WHERE " + PARTNER_INSTRUMENT_TABLE_NAME
                + "." + PARTNER_INSTRUMENT_PINSTRUMENTID + "=" + PARTNER_INSTRUMENT_TEST_TABLE_NAME + "." + PARTNER_INSTRUMENT_ID
                + " AND " + PARTNER_INSTRUMENT_TEST_TABLE_NAME + "." + PARTNER_INSTRUMENT_TEST_CODE + "=" + '"' + testCode + '"';
//                " WHERE " + ValdocDatabaseHandler.PARTNER_INSTRUMENT_PARTNERID + " = " + partnerId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PartnerInstrument partnerInstrument = new PartnerInstrument();
                partnerInstrument.setpInstrumentId(cursor.getInt(0));
                partnerInstrument.setPartnerId(cursor.getInt(1));
                partnerInstrument.setSerialNo(cursor.getString(2));
                partnerInstrument.setpInstrumentName(cursor.getString(3));
                partnerInstrument.setMake(cursor.getString(4));
                partnerInstrument.setModel(cursor.getString(5));
                partnerInstrument.setLastUpdatedDate(cursor.getString(6));
                partnerInstrument.setCalibrationDueDate(cursor.getString(7));
//                partnerInstrument.setCurrentLocation(cursor.getString(8));
                partnerInstrument.setStatus(cursor.getString(8));
                partnerInstrument.setCertFileName(cursor.getString(9));
                partnerInstrument.setRemarks(cursor.getString(10));
                partnerInstrument.setLastUpdatedDate(cursor.getString(11));
                partnerInstrument.setRange(cursor.getString(12));

//                partnerInstrument.setSamplingFlowRate(cursor.getString(12));
//                partnerInstrument.setSamplingTime(cursor.getString(13));
//                partnerInstrument.setAerosolUsed(cursor.getString(14));
//                partnerInstrument.setAerosolGeneratorType(cursor.getString(15));
                Log.d("valdoc", "partnerInstrument" + partnerInstrument.getpInstrumentId());
                partnerInstrumentArrayList.add(partnerInstrument);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return partnerInstrumentArrayList;
    }

    // select data from partnerInstrument table
    public String getPartnerIdFromPartnerUser(int userId) {
        String selectQuery = " SELECT " + PARTNERUSER_PARTNERID + " FROM " + PARTNERUSER_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.PARTNERUSER_APP_USER_ID + " = " + userId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String partnerId = "";
        if (cursor.moveToFirst()) {
            do {
                partnerId = cursor.getString(cursor.getColumnIndex(PARTNERUSER_PARTNERID));
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return partnerId;
    }

    // select data from user table
    public ArrayList<Ahu> getAhuInfo() {
        ArrayList<Ahu> ahuArrayList;
        ahuArrayList = new ArrayList<Ahu>();
        String selectQuery = "SELECT * FROM " + AHU_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Ahu ahu = new Ahu();
                ahu.setAhuId(cursor.getInt(0));
                ahu.setAhuNo(cursor.getString(1));
                ahu.setAhuType(cursor.getString(2));
                ahu.setAreId(cursor.getString(3));
                ahu.setCapacity(cursor.getDouble(4));
                ahu.setReturnAirCFM(cursor.getDouble(5));
                ahu.setExhaustAirCFM(cursor.getDouble(6));
                ahu.setBleedFilterType(cursor.getString(7));
                ahu.setBleedFilterEfficiency(cursor.getDouble(8));
                ahu.setBleedAirCFM(cursor.getDouble(9));
                ahu.setBleedFilterQty(cursor.getInt(10));
                ahu.setBleedFilterLeak(cursor.getInt(11));
                ahu.setFreshFilterType(cursor.getString(12));
                ahu.setFreshAirCFM(cursor.getDouble(13));
                ahu.setFreshFilterQty(cursor.getInt(14));
                ahu.setFreshFilterEfficiency(cursor.getString(15));
                ahu.setFinalFilterAirFlow(cursor.getInt(16));
                ahu.setFinalFilterQty(cursor.getInt(17));
                ahu.setFinalFilterType(cursor.getString(18));
                ahu.setFinalFilterEfficiency(cursor.getString(19));
                ahu.setFinalFilterLeak(cursor.getString(20));
                ahu.setRemarks(cursor.getString(21));
                ahu.setLastUpdatedDate(cursor.getString(22));
                ahu.setFreshParticleSize(cursor.getDouble(23));
                ahu.setBleedParticleSize(cursor.getDouble(24));
                ahu.setFinalParticleSize(cursor.getDouble(25));
                Log.d("valdoc", "TestCreateActivity : ahu1");
                ahuArrayList.add(ahu);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return ahuArrayList;
    }

    // select data from user table
    public ArrayList<String> getAhuNameInfo() {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<String>();
        String selectQuery = "SELECT " + AHU_AHUTYPE + " FROM " + AHU_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String strings = "";
        if (cursor.moveToFirst()) {
            do {
                strings = cursor.getString(cursor.getColumnIndex(AHU_AHUTYPE));
                arrayList.add(strings);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return arrayList;
    }

    // select data from user table
    public ArrayList<String> getEquipmentName() {
        ArrayList<String> equipmentNameArrayList;
        equipmentNameArrayList = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT " + EQUIPMENT_EQUIPMENTNAME + " FROM " + EQUIPMENT_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String equipmentName = "";
        if (cursor.moveToFirst()) {
            do {
                equipmentName = cursor.getString(cursor.getColumnIndex(EQUIPMENT_EQUIPMENTNAME));
                equipmentNameArrayList.add(equipmentName);
//                Equipment equipment = new Equipment();
//                equipment.setEquipmentId(cursor.getInt(0));
//                equipment.setRoomId(cursor.getInt(1));
//                equipment.setEquipmentNo(cursor.getString(2));
//                equipment.setEquipmentName(cursor.getString(3));
//                equipment.setMinVelocity(cursor.getDouble(4));
//                equipment.setMaxVelocity(cursor.getDouble(5));
//                equipment.setSupplyAirflow(cursor.getInt(6));
//                equipment.setEquipmentPressure(cursor.getInt(7));
//                equipment.setExhustAirflow(cursor.getInt(8));
//                equipment.setRemarks(cursor.getString(9));
//                equipment.setLastUpdatedDate(cursor.getString(10));
//                Log.d("Avinash", "get equipment db minvalocity=" + cursor.getDouble(5) + "equipment.getMaxVelocity()=" + cursor.getDouble(6));

//                equipmentArrayList.add(equipment);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return equipmentNameArrayList;
    }


    // select data from user table
    public ArrayList<Equipment> getEquipmentNoInfoByEquipmentName(String equipmentName) {
        ArrayList<Equipment> equipmentNoArrayList;
        equipmentNoArrayList = new ArrayList<Equipment>();
        String selectQuery = "SELECT * FROM " + EQUIPMENT_TABLE_NAME
                + " WHERE " + ValdocDatabaseHandler.EQUIPMENT_EQUIPMENTNAME + " = " + '"' + equipmentName + '"';
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
//        String equipmentNo = "";
        if (cursor.moveToFirst()) {
            do {
//                equipmentNo = cursor.getString(cursor.getColumnIndex(EQUIPMENT_EQUIPMENTNO));
//                equipmentNoArrayList.add(equipmentNo);
                Equipment equipment = new Equipment();
                equipment.setEquipmentId(cursor.getInt(0));
                equipment.setRoomId(cursor.getInt(1));
                equipment.setEquipmentNo(cursor.getString(2));
                equipment.setEquipmentName(cursor.getString(3));
                equipment.setMinVelocity(cursor.getDouble(4));
                equipment.setMaxVelocity(cursor.getDouble(5));
                equipment.setSupplyAirflow(cursor.getInt(6));
                equipment.setEquipmentPressure(cursor.getInt(7));
                equipment.setExhustAirflow(cursor.getInt(8));
                equipment.setRemarks(cursor.getString(9));
                equipment.setLastUpdatedDate(cursor.getString(10));
//                Log.d("Avinash", "get equipment db minvalocity=" + cursor.getDouble(5) + "equipment.getMaxVelocity()=" + cursor.getDouble(6));
                equipmentNoArrayList.add(equipment);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return equipmentNoArrayList;
    }

    // select data from user table
    public ArrayList<String> getEquipmentNameInfo() {
        ArrayList<String> arrayList;
        arrayList = new ArrayList<String>();
        String selectQuery = "SELECT " + EQUIPMENT_EQUIPMENTNAME + " FROM " + EQUIPMENT_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String strings = "";
        if (cursor.moveToFirst()) {
            do {
                strings = cursor.getString(cursor.getColumnIndex(EQUIPMENT_EQUIPMENTNAME));
                arrayList.add(strings);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return arrayList;
    }

    // select data from test reading table
    public JSONArray getTestReadingInfo(String idList) {
        JSONArray testReadingJsonArray;
        Log.d("Avinash", "testDetailsIdList getTestReadingInfo idList=" + idList);
        testReadingJsonArray = new JSONArray();
        String selectQuery = "SELECT * FROM " + TESTREADING_TABLE_NAME
                + " WHERE " + ValdocDatabaseHandler.TESTREADING_TEST_DETAIL_ID + " IN " + "(" + idList + ")";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(TESTREADING_TESTREADINGID, cursor.getInt(0));
                    jsonObject.put(TESTREADING_TEST_DETAIL_ID, cursor.getInt(1));
                    jsonObject.put(TESTREADING_ENTITYNAME, cursor.getString(2));
                    jsonObject.put(TESTREADING_VALUE, cursor.getString(3));
                    testReadingJsonArray.put(jsonObject);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return testReadingJsonArray;
    }


    // select data from test details table
    public JSONArray getTestDetailsInfo(String idList) {
        Log.d("Avinash", "testDetailsIdList TEST_DETAILS_TABLE_NAME1=" + idList);
        JSONArray testDetailsJsonArray;
        testDetailsJsonArray = new JSONArray();
        Log.d("Avinash", "testDetailsIdList TEST_DETAILS_TABLE_NAME2=" + idList);
        String selectQuery = "SELECT * FROM " + TEST_DETAILS_TABLE_NAME
                + " WHERE " + ValdocDatabaseHandler.TEST_DETAILS_TEST_DETAIL_ID + " IN " + "(" + idList + ")";
        Log.d("Avinash", "testDetailsIdList TEST_DETAILS_TABLE_NAME selectQuery=" + selectQuery);
//        SELECT * FROM TABLE WHERE ID IN (id1, id2, ..., idn)
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(TEST_DETAILS_TEST_DETAIL_ID, cursor.getInt(0));
                    jsonObject.put(TEST_DETAILS_TESTNAME, cursor.getString(1).toString());
                    jsonObject.put(TEST_DETAILS_CUSTOMER, cursor.getString(2).toString());
                    jsonObject.put(TEST_DETAILS_RAWDATANO, cursor.getString(3).toString());

                    jsonObject.put(TEST_DETAILS_DATEOFTEST, cursor.getInt(4));
                    jsonObject.put(TEST_DETAILS_INSTRUMENTUSED, cursor.getString(5).toString());
                    jsonObject.put(TEST_DETAILS_INSTRUMENTNO, cursor.getString(6).toString());
                    jsonObject.put(TEST_DETAILS_MAKE, cursor.getString(7).toString());
                    jsonObject.put(TEST_DETAILS_MODEL, cursor.getString(8).toString());
                    jsonObject.put(TEST_DETAILS_CALIBRATEDON, cursor.getString(9).toString());
                    jsonObject.put(TEST_DETAILS_CALIBRATEDDUEON, cursor.getString(10).toString());
                    jsonObject.put(TEST_DETAILS_TESTSPECIFICATION, cursor.getString(11).toString());
                    jsonObject.put(TEST_DETAILS_TEST_TESTREFERENCE, cursor.getString(12).toString());
                    jsonObject.put(TEST_DETAILS_OCCUPENCYSTATE, cursor.getString(13).toString());
                    jsonObject.put(TEST_DETAILS_BLOCKNAME, cursor.getString(14).toString());
                    jsonObject.put(TEST_DETAILS_TESTAREA, cursor.getString(15).toString());

                    jsonObject.put(TEST_DETAILS_AHUNO, cursor.getString(16).toString());
                    jsonObject.put(TEST_DETAILS_ROOMNO, cursor.getString(17).toString());
                    jsonObject.put(TEST_DETAILS_ROOMNAME, cursor.getString(18).toString());
                    jsonObject.put(TEST_DETAILS_EQUIPMENTNO, cursor.getString(19).toString());
                    jsonObject.put(TEST_DETAILS_EQUIPMENTNAME, cursor.getString(20).toString());

                    jsonObject.put(TEST_DETAILS_TESTERNAME, cursor.getString(21).toString());
                    jsonObject.put(TEST_DETAILS_WITNESSNAME, cursor.getString(22).toString());
                    jsonObject.put(TEST_DETAILS_PARTNERNAME, cursor.getString(23).toString());
                    jsonObject.put(TEST_DETAILS_SAMPLINGFLOWRATE, cursor.getString(24).toString());
                    jsonObject.put(TEST_DETAILS_SAMPLINGTIME, cursor.getString(25).toString());
                    jsonObject.put(TEST_DETAILS_AEROSOLUSED, cursor.getString(26).toString());
                    jsonObject.put(TEST_DETAILS_AEROSOLGENERATORTYPE, cursor.getString(27).toString());
                    jsonObject.put(TEST_DETAILS_TESTCODE, cursor.getString(28).toString());

                    jsonObject.put(TEST_DETAILS_ROOMVOLUME, cursor.getString(29).toString());
                    jsonObject.put(TEST_DETAILS_TESTWITNESSORG, cursor.getString(30).toString());
                    jsonObject.put(TEST_DETAILS_TESTCONDOCTORORG, cursor.getString(31).toString());
                    jsonObject.put(TEST_DETAILS_TESTITEM, cursor.getString(32).toString());

                    Log.d("testDetailsIdList", "getCertificateData test details=" + jsonObject.toString());
                    testDetailsJsonArray.put(jsonObject);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return testDetailsJsonArray;
    }

    // select data from test spesification value table
    public JSONArray getTestSpecificationValueInfo(String idList) {
        JSONArray testSpecificationValueJsonArray;
        testSpecificationValueJsonArray = new JSONArray();
        Log.d("Avinash", "testDetailsIdList getTestSpecificationValueInfo idList=" + idList);
        String selectQuery = "SELECT * FROM " + TESTSPECIFICATIONVALUE_TABLE_NAME
                + " WHERE " + ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TEST_DETAIL_ID + " IN " + "(" + idList + ")";
        Log.d("Avinash", "testDetailsIdList TEST_DETAILS_TABLE_NAME selectQuery=" + selectQuery);
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID, cursor.getInt(0));
                    jsonObject.put(TESTSPECIFICATIONVALUE_TEST_DETAIL_ID, cursor.getInt(1));
                    jsonObject.put(TESTSPECIFICATIONVALUE_FIELDNAME, cursor.getString(2));
                    jsonObject.put(TESTSPECIFICATIONVALUE_FIELDVALUE, cursor.getString(3));
                    testSpecificationValueJsonArray.put(jsonObject);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return testSpecificationValueJsonArray;
    }

    // select data from ServiceReport table
    public JSONArray getServiceReport() {
        JSONArray serviceReportJsonArray;
        serviceReportJsonArray = new JSONArray();
        String selectQuery = "SELECT * FROM " + SERVICE_REPORT_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(SERVICE_REPORT_ID, cursor.getInt(0));
                    jsonObject.put(SERVICE_REPORT_CUSTOMER, cursor.getString(1));
                    jsonObject.put(SERVICE_REPORT_SERVICEENGINEER, cursor.getString(2));
                    jsonObject.put(SERVICE_REPORT_TIMEIN, cursor.getString(3));
                    jsonObject.put(SERVICE_REPORT_TIMEOUT, cursor.getString(4));
                    jsonObject.put(SERVICE_REPORT_SRNO, cursor.getString(5));
                    jsonObject.put(SERVICE_REPORT_CUSTOMERPO, cursor.getString(6));
                    jsonObject.put(SERVICE_REPORT_PLANT, cursor.getString(7));
                    jsonObject.put(SERVICE_REPORT_STATUS, cursor.getString(8));
                    jsonObject.put(SERVICE_REPORT_TESTERNAME, cursor.getString(9));

                    jsonObject.put(SERVICE_REPORT_WITNESSNAME, cursor.getString(10));
                    jsonObject.put(SERVICE_REPORT_WITNESSCONTACT, cursor.getString(11));
                    jsonObject.put(SERVICE_REPORT_WITNESSMAIL, cursor.getString(12));
                    jsonObject.put(SERVICE_REPORT_PARTNERNAME, cursor.getString(13));
                    jsonObject.put(SERVICE_REPORT_SERVICEDATE, cursor.getString(14));
                    jsonObject.put(SERVICE_REPORT_REMARK, cursor.getString(15));

                    jsonObject.put(SERVICE_REPORT_WORKORDER, cursor.getString(16));
                    jsonObject.put(SERVICE_REPORT_WITNESSDESIGNATION, cursor.getString(17));
                    serviceReportJsonArray.put(jsonObject);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return serviceReportJsonArray;
    }

    // select data from ServiceReport Details table
    public JSONArray getServiceReportDetailsInfo() {
        JSONArray serviceReportJsonArray;
        serviceReportJsonArray = new JSONArray();
        String selectQuery = "SELECT * FROM " + SERVICE_REPORT_DETAIL_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(SERVICE_REPORT_DETAIL_ID, cursor.getInt(0));
                    jsonObject.put(SERVICE_REPORT_DETAIL_SERVICE_REPORT_ID, cursor.getInt(1));
                    jsonObject.put(SERVICE_REPORT_DETAIL_TYPEOFTEST, cursor.getString(2));
                    jsonObject.put(SERVICE_REPORT_DETAIL_AREAOFTEST, cursor.getString(3));
                    jsonObject.put(SERVICE_REPORT_DETAIL_EQUIPMENTAHUNO, cursor.getString(4));
                    jsonObject.put(SERVICE_REPORT_DETAIL_NOOFLOC, cursor.getInt(5));
                    jsonObject.put(SERVICE_REPORT_DETAIL_NOOFHOURDAYS, cursor.getString(6));
                    serviceReportJsonArray.put(jsonObject);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        } // return ServiceReportDetail list return
        return serviceReportJsonArray;
    }


    // select data from room table
    public String[] getRoomByEquipment(int roomId) {
        Log.d("ValdocDatabaseHandler", "roomId=" + roomId);
        String selectQuery = " SELECT " + ROOM_ROOMNO + "," + ROOM_ROOMNAME + "," + ROOM_AREAID + "," + ROOM_ACPH + "," + ROOM_VOLUME + "," + ROOM_AHUID + " FROM " + ROOM_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.ROOM_ROOMID + " = " + roomId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("ValdocDatabaseHandler", "cursor=" + cursor.getCount());
        String[] strings = new String[6];
        if (cursor.moveToFirst()) {
            do {
                Log.d("ValdocDatabaseHandler", "roomId=" + roomId);
                strings[0] = cursor.getString(cursor.getColumnIndex(ROOM_ROOMNO));
                strings[1] = cursor.getString(cursor.getColumnIndex(ROOM_ROOMNAME));
                strings[2] = cursor.getString(cursor.getColumnIndex(ROOM_AREAID));
                strings[3] = cursor.getString(cursor.getColumnIndex(ROOM_ACPH));
                strings[4] = cursor.getString(cursor.getColumnIndex(ROOM_VOLUME));
                strings[5] = cursor.getString(cursor.getColumnIndex(ROOM_AHUID));
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return strings;
    }

    // select data from room table
    public String[] getRoomByAhu(int ahuId) {
        Log.d("ValdocDatabaseHandler", "roomId=" + ahuId);
        String selectQuery = " SELECT " + ROOM_ROOMNO + "," + ROOM_ROOMNAME + "," + ROOM_AREAID + "," + ROOM_ACPH + "," + ROOM_VOLUME + "," + ROOM_AHUID + " FROM " + ROOM_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.ROOM_AHUID + " = " + ahuId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("ValdocDatabaseHandler", "cursor=" + cursor.getCount());
        String[] strings = new String[6];
        if (cursor.moveToFirst()) {
            do {
                Log.d("ValdocDatabaseHandler", "roomId=" + ahuId);
                strings[0] = cursor.getString(cursor.getColumnIndex(ROOM_ROOMNO));
                strings[1] = cursor.getString(cursor.getColumnIndex(ROOM_ROOMNAME));
                strings[2] = cursor.getString(cursor.getColumnIndex(ROOM_AREAID));
                strings[3] = cursor.getString(cursor.getColumnIndex(ROOM_ACPH));
                strings[4] = cursor.getString(cursor.getColumnIndex(ROOM_VOLUME));
                strings[5] = cursor.getString(cursor.getColumnIndex(ROOM_AHUID));

            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return strings;
    }

    // select data from area table
    public ArrayList<String> getAreaName() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String selectQuery = " SELECT " + AREA_AREANAME + " FROM " + AREA_TABLE_NAME;
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
//                " WHERE " + ValdocDatabaseHandler.AREA_AREAID + " = " + areaId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String strings = "";
        if (cursor.moveToFirst()) {
            do {
                strings = cursor.getString(cursor.getColumnIndex(AREA_AREANAME));
                arrayList.add(strings);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return arrayList;
    }

    // select data from area table
    public String getAreaByRoomAreaId(String areaId) {
        String selectQuery = " SELECT " + AREA_AREANAME + " FROM " + AREA_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.AREA_AREAID + " = " + '"' + areaId + '"';
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String strings = "";
        if (cursor.moveToFirst()) {
            do {
                strings = cursor.getString(cursor.getColumnIndex(AREA_AREANAME));
                Log.d("valdoc", "ValdocDatabaseHelper :AREA_AREANAME:=" + strings);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return strings;
    }

    // select data from room filter table
    public ArrayList<RoomFilter> getFromRoomFilter(int roomId) {
//        Log.d("valdoc", "ValdocDatabaseHelper :getFromRoomFilter:=" + roomId);
        ArrayList<RoomFilter> filterArrayList;
        filterArrayList = new ArrayList<RoomFilter>();
        String selectQuery = " SELECT * FROM " + ROOMFILTER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.ROOMFILTER_ROOMID + " = " + roomId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RoomFilter roomFilter = new RoomFilter();
                roomFilter.setFilterId(cursor.getInt(0));
                roomFilter.setFilterType(cursor.getString(1));
                roomFilter.setEfficiency(cursor.getInt(2));
                roomFilter.setFilterCode(cursor.getString(3));
                roomFilter.setParticleSize(cursor.getString(4));
                roomFilter.setRoomId(cursor.getInt(5));
                roomFilter.setSpecification(cursor.getDouble(6));
                roomFilter.setWidth(cursor.getDouble(7));
                roomFilter.setLength(cursor.getDouble(8));
                roomFilter.setArea(cursor.getDouble(9));
                roomFilter.setEffectiveFilterArea(cursor.getDouble(9));
                roomFilter.setFilterLocation(cursor.getString(9));
                roomFilter.setIsSupplyFilter(cursor.getInt(10));
                roomFilter.setLastUpdatedDate(cursor.getString(11));
                filterArrayList.add(roomFilter);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return filterArrayList;
    }


    //    // select data from grill table
//    public ArrayList<String[]> getGrillAndSizeFromGrill(int roomId) {
//        String selectQuery = " SELECT " + GRILL_GRILLCODE +"," + GRILL_EFFECTIVEAREA + " FROM " + GRILL_TABLE_NAME +
////        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
//                " WHERE " + ValdocDatabaseHandler.GRILL_ROOMID + " = " + roomId;
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        Log.d("valdoc", "ValdocDatabaseHelper :grill code grill:=" + cursor.getCount());
//        ArrayList<String[]> grillList=new ArrayList<String[]>();
//        if (cursor.moveToFirst()) {
//            do {
//                String[] strings = new String[2];
//                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(GRILL_GRILLCODE));
//                strings[0] = cursor.getString(cursor.getColumnIndex(GRILL_GRILLCODE));
//                strings[1] = cursor.getString(cursor.getColumnIndex(GRILL_EFFECTIVEAREA));
//                grillList.add(strings);
//                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + strings[0] + "\nstrings[1]=" +strings[1] );
//            } while (cursor.moveToNext());
//        } // return contact list return wordList; }
//        return grillList;
//    }
    // select data from equipment Grill table
    public ArrayList<Grill> getGrill(int roomId) {
        ArrayList<Grill> grillArrayList;
        grillArrayList = new ArrayList<Grill>();
        String selectQuery = " SELECT * FROM " + GRILL_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.GRILL_ROOMID + " = " + roomId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "ValdocDatabaseHelper :Equipment grill code equipment1:=" + cursor.getCount());
//        String[] strings = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Grill grill = new Grill();
                grill.setGrillId(cursor.getInt(0));
                grill.setRoomId(cursor.getInt(1));
                grill.setGrillCode(cursor.getString(2).toString());
                grill.setLength(cursor.getDouble(3));
                grill.setWidh(cursor.getDouble(4));
                grill.setGrillArea(cursor.getDouble(5));
                grill.setEffectiveArea(cursor.getDouble(6));
                grill.setIsSupplyGrill(cursor.getInt(7));
                grill.setLastUpdatedDate(cursor.getString(8).toString());
//                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(EQUIPMENTFILTER_FILTERCODE));
//                strings[i] = cursor.getString(cursor.getColumnIndex(EQUIPMENTGRILL_GRILLCODE));
//                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + strings[i] + "i=" + i);
//                i++;
                grillArrayList.add(grill);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return grillArrayList;
    }

//    // select data from grill table
//    public ArrayList<HashMap<String, String>> getGrillAndSizeFromGrill(int roomId) {
//        String selectQuery = " SELECT " + GRILL_GRILLCODE + "," + GRILL_EFFECTIVEAREA + " FROM " + GRILL_TABLE_NAME +
////        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
//                " WHERE " + ValdocDatabaseHandler.GRILL_ROOMID + " = " + roomId;
//        SQLiteDatabase database = this.getWritableDatabase();
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        Log.d("valdoc", "ValdocDatabaseHelper :grill code grill:=" + cursor.getCount());
//        ArrayList<HashMap<String, String>> grillList = new ArrayList<HashMap<String, String>>();
//        if (cursor.moveToFirst()) {
//            do {
//                HashMap<String, String> hashMap = new HashMap<String, String>();
////                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(GRILL_GRILLCODE));
////                Log.d("valdoc", "ValdocDatabaseHelper :GRILL_GRILLCODE:=" + cursor.getString(cursor.getColumnIndex(GRILL_GRILLCODE)).toString() + "\n hashMap.get(GRILL_EFFECTIVEAREA) =" + cursor.getDouble(cursor.getColumnIndex(GRILL_EFFECTIVEAREA)));
//                hashMap.put(GRILL_GRILLCODE, cursor.getString(cursor.getColumnIndex(GRILL_GRILLCODE)).toString());
//                hashMap.put(GRILL_EFFECTIVEAREA, "" + cursor.getDouble(cursor.getColumnIndex(GRILL_EFFECTIVEAREA)));
////                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + hashMap.get(GRILL_GRILLCODE) + "\n hashMap.get(GRILL_EFFECTIVEAREA) =" + hashMap.get(GRILL_EFFECTIVEAREA));
//                grillList.add(hashMap);
//            } while (cursor.moveToNext());
//        } // return contact list return wordList; }
//        return grillList;
//    }

    // select data from equipment filter table
    public ArrayList<EquipmentFilter> getFilterFromEquipmentFilter(int equipmentId) {
        ArrayList<EquipmentFilter> filterArrayList;
        filterArrayList = new ArrayList<EquipmentFilter>();
        String selectQuery = " SELECT * FROM " + EQUIPMENTFILTER_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.EQUIPMENTFILTER_EQUIPMENTID + " = " + equipmentId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                EquipmentFilter equipmentFilter = new EquipmentFilter();
                equipmentFilter.setFilterId(cursor.getInt(0));
                equipmentFilter.setFilterCode(cursor.getString(1).toString());
                equipmentFilter.setEquipmentId(cursor.getInt(2));
                equipmentFilter.setFilterType(cursor.getString(3).toString());
                equipmentFilter.setEfficiency(cursor.getDouble(4));
                equipmentFilter.setParticleSize(cursor.getDouble(5));
                equipmentFilter.setSpecificationLeak(cursor.getDouble(6));
                equipmentFilter.setWidth(cursor.getDouble(7));
                equipmentFilter.setLength(cursor.getDouble(8));
                equipmentFilter.setGrillArea(cursor.getDouble(9));
                equipmentFilter.setLastUpdatedDate(cursor.getString(10).toString());
                filterArrayList.add(equipmentFilter);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return filterArrayList;
    }

    // select data from equipment Grill table
    public ArrayList<EquipmentGrill> getGrillFromEquipmentGrill(int equipmentId) {
        ArrayList<EquipmentGrill> grillArrayList;
        grillArrayList = new ArrayList<EquipmentGrill>();
        String selectQuery = " SELECT * FROM " + EQUIPMENTGRILL_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.EQUIPMENTGRILL_EQUIPMENTID + " = " + equipmentId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "ValdocDatabaseHelper :Equipment grill code equipment1:=" + cursor.getCount());
//        String[] strings = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                EquipmentGrill equipmentGrill = new EquipmentGrill();
                equipmentGrill.setGrillId(cursor.getInt(0));
                equipmentGrill.setGrillCode(cursor.getString(1).toString());
                equipmentGrill.setEquipmentId(cursor.getInt(2));
                ;
                equipmentGrill.setLength(cursor.getDouble(3));
                equipmentGrill.setWidth(cursor.getDouble(4));
                equipmentGrill.setArea(cursor.getDouble(5));
                equipmentGrill.setEffectiveArea(cursor.getDouble(6));
                equipmentGrill.setRemarks(cursor.getString(7).toString());
                equipmentGrill.setLastUpdatedDate(cursor.getString(8).toString());
//                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(EQUIPMENTFILTER_FILTERCODE));
//                strings[i] = cursor.getString(cursor.getColumnIndex(EQUIPMENTGRILL_GRILLCODE));
//                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + strings[i] + "i=" + i);
//                i++;
                grillArrayList.add(equipmentGrill);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return grillArrayList;
    }

    // select data from equipment filter table
    public ArrayList<AhuFilter> getFilterFromAhuFilter(int ahuId) {
        ArrayList<AhuFilter> ahuFilterArrayList;
        ahuFilterArrayList = new ArrayList<AhuFilter>();
        String selectQuery = " SELECT * FROM " + AHU_FILTER_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.AHU_FILTER_AHUID + " = " + ahuId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "ValdocDatabaseHelper :grill code equipment1:=" + cursor.getCount());
//        String[] strings = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                AhuFilter ahuFilter = new AhuFilter();
                ahuFilter.setFilterId(cursor.getInt(0));
                ahuFilter.setAhuId(cursor.getInt(1));
                ahuFilter.setFilterCategory(cursor.getString(2));
                ahuFilter.setFilterCode(cursor.getString(3));
                ahuFilter.setWidth(cursor.getDouble(4));
                ahuFilter.setLength(cursor.getDouble(5));
                ahuFilter.setDepthArea(cursor.getDouble(6));
                ahuFilter.setArea(cursor.getDouble(7));
                ahuFilter.setEffectiveArea(cursor.getDouble(8));
//                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(EQUIPMENTFILTER_FILTERCODE));
//                strings[i] = cursor.getString(cursor.getColumnIndex(EQUIPMENTGRILL_GRILLCODE));
//                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + strings[i] + "i=" + i);
//                i++;
                ahuFilterArrayList.add(ahuFilter);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return ahuFilterArrayList;
    }

    // select data from ahu filter table
    public ArrayList<AhuFilter> getAhuFitFilterFromAhuFilter(int ahuId, String testItem) {
        ArrayList<AhuFilter> ahuFilterArrayList;
        ahuFilterArrayList = new ArrayList<AhuFilter>();
        String selectQuery = " SELECT * FROM " + AHU_FILTER_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.AHU_FILTER_AHUID + " = " + ahuId + " AND " + ValdocDatabaseHandler.AHU_FILTER_FILTERCATEGORY + "=" + '"' + testItem + '"';
        ;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "ValdocDatabaseHelper :grill code equipment1:=" + cursor.getCount());
//        String[] strings = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                AhuFilter ahuFilter = new AhuFilter();
                ahuFilter.setFilterId(cursor.getInt(0));
                ahuFilter.setAhuId(cursor.getInt(1));
                ahuFilter.setFilterCategory(cursor.getString(2));
                ahuFilter.setFilterCode(cursor.getString(3));
                ahuFilter.setWidth(cursor.getDouble(4));
                ahuFilter.setLength(cursor.getDouble(5));
                ahuFilter.setDepthArea(cursor.getDouble(6));
                ahuFilter.setArea(cursor.getDouble(7));
                ahuFilter.setEffectiveArea(cursor.getDouble(8));
//                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(EQUIPMENTFILTER_FILTERCODE));
//                strings[i] = cursor.getString(cursor.getColumnIndex(EQUIPMENTGRILL_GRILLCODE));
//                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + strings[i] + "i=" + i);
//                i++;
                ahuFilterArrayList.add(ahuFilter);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return ahuFilterArrayList;
    }

    // select data from Room info table
    public ArrayList<Room> getRoomInfoByAhu(int ahuNo) {
        ArrayList<Room> roomArrayList;
        roomArrayList = new ArrayList<Room>();
        String selectQuery = "SELECT * FROM " + ROOM_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.ROOM_AHUID + " = " + ahuNo;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setRoomId(cursor.getInt(0));
                room.setAhuId(cursor.getInt(1));
                room.setAreaId(cursor.getInt(2));
                room.setRoomName(cursor.getString(3));
                room.setRoomNo(cursor.getString(4));
                room.setWidth(cursor.getDouble(5));
                room.setHeight(cursor.getDouble(6));
                room.setLength(cursor.getDouble(7));
                room.setArea(cursor.getDouble(8));
                room.setVolume(cursor.getDouble(9));

                room.setAcph(cursor.getInt(10));
                room.setTestRef(cursor.getString(11));
                room.setIsoClause(cursor.getString(12));
                room.setOccupancyState(cursor.getString(13));
                room.setSupplyAirflow(cursor.getDouble(14));
                room.setAhuFlowCFM(cursor.getDouble(15));

                room.setRoomPressure(cursor.getDouble(16));
                room.setFreshAirCFM(cursor.getDouble(17));
                room.setBleedAirCFM(cursor.getDouble(18));
                room.setExhaustAirFlow(cursor.getDouble(19));
                room.setMaxTemperature(cursor.getDouble(20));
                room.setMaxRH(cursor.getDouble(21));
                room.setMinTemperature(cursor.getDouble(22));
                room.setMinRH(cursor.getDouble(23));
                room.setReturnAirFlow(cursor.getDouble(24));
//                room.setSupplyAirGrillQTY(cursor.getInt(23));
//                room.setReturnAirGrillQTY(cursor.getInt(24));
//                room.setSupplyAirFilterQTY(cursor.getInt(25));
//                room.setReturnAirFilterQTY(cursor.getInt(26));
                room.setRemarks(cursor.getString(25));
                room.setLastUpdatedDate(cursor.getString(26));
                roomArrayList.add(room);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return roomArrayList;
    }

    // Select data from Test Detail table based on testdetails id

    public TestDetails getTestDetailById(int id) {
        Log.d(TAG, "getTestDetailById TestCode " + id);
//        ArrayList<TestDetails> testDetailList = new ArrayList<TestDetails>();
        TestDetails TestDetails = new TestDetails();
        String selectQuery = "SELECT * FROM " + TEST_DETAILS_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.TEST_DETAILS_TEST_DETAIL_ID + " = " + id;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                TestDetails.setTest_detail_id(cursor.getInt(0));//TEST_DETAILS_TEST_DETAIL_ID
                TestDetails.setTestName(cursor.getString(1));//TEST_DETAILS_TESTNAME
                TestDetails.setCustomer(cursor.getString(2));//TEST_DETAILS_CUSTOMER
                TestDetails.setRawDataNo(cursor.getString(3));//TEST_DETAILS_RAWDATANO
                TestDetails.setDateOfTest(cursor.getString(4));//TEST_DETAILS_DATEOFTEST
                TestDetails.setInstrumentUsed(cursor.getString(5));//TEST_DETAILS_INSTRUMENTUSED
                TestDetails.setInstrumentNo(cursor.getString(6));//TEST_DETAILS_INSTRUMENTNO
                TestDetails.setMake(cursor.getString(7));//TEST_DETAILS_MAKE
                TestDetails.setModel(cursor.getString(8));//TEST_DETAILS_MODEL
                TestDetails.setCalibratedOn(cursor.getString(9));//TEST_DETAILS_CALIBRATEDON

                TestDetails.setCalibratedDueOn(cursor.getString(10));//TEST_DETAILS_CALIBRATEDDUEON
                TestDetails.setTestSpecification(cursor.getString(11));//TEST_DETAILS_TESTSPECIFICATION
                TestDetails.setTestReference(cursor.getString(12));//TEST_DETAILS_TEST_TESTREFERENCE
                TestDetails.setOccupencyState(cursor.getString(13));//TEST_DETAILS_OCCUPENCYSTATE
                TestDetails.setBlockName(cursor.getString(14));//TEST_DETAILS_BLOCKNAME

                TestDetails.setTestArea(cursor.getString(15));//TEST_DETAILS_TESTAREA
                TestDetails.setAhuNo(cursor.getString(16));//TEST_DETAILS_AHUNO
                TestDetails.setRoomNo(cursor.getString(17));//TEST_DETAILS_ROOMNO
                TestDetails.setRoomName(cursor.getString(18));//TEST_DETAILS_ROOMNAME
                TestDetails.setEquipmentNo(cursor.getString(19));//TEST_DETAILS_EQUIPMENTNO
                TestDetails.setEquipmentName(cursor.getString(20));//TEST_DETAILS_EQUIPMENTNAME

                TestDetails.setTesterName(cursor.getString(21));//TEST_DETAILS_TESTERNAME
                TestDetails.setWitnessName(cursor.getString(22));//TEST_DETAILS_WITNESSNAME
                TestDetails.setPartnerName(cursor.getString(23));//TEST_DETAILS_PARTNERNAME

                TestDetails.setSamplingFlowRate(cursor.getString(24));//TEST_DETAILS_SAMPLINGFLOWRATE
                TestDetails.setSamplingTime(cursor.getString(25));//TEST_DETAILS_SAMPLINGTIME
                TestDetails.setAerosolUsed(cursor.getString(26));//TEST_DETAILS_AEROSOLUSED
                TestDetails.setAerosolGeneratorType(cursor.getString(27));//TEST_DETAILS_AEROSOLGENERATORTYPE
                TestDetails.setTestCode(cursor.getString(28));//TEST_DETAILS_TESTCODE
                TestDetails.setRoomVolume(cursor.getString(29));
                TestDetails.setTestWitnessOrg(cursor.getString(30));
                TestDetails.setTestCondoctorOrg(cursor.getString(31));
                TestDetails.setTestItem(cursor.getString(32));
//                testDetailList.add(TestDetails);
            } while (cursor.moveToNext());
        }
        return TestDetails;
    }


    // Select data from Test Detail table

    public ArrayList<TestDetails> getTestDetailByTestCode(String testCode) {
        Log.d(TAG, "getTestDetailByTestCode TestCode " + testCode);
        ArrayList<TestDetails> testDetailList = new ArrayList<TestDetails>();
        String selectQuery = "SELECT * FROM " + TEST_DETAILS_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.TEST_DETAILS_TESTCODE + " = " + '"' + testCode + '"';
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TestDetails TestDetails = new TestDetails();
                TestDetails.setTest_detail_id(cursor.getInt(0));//TEST_DETAILS_TEST_DETAIL_ID
                TestDetails.setTestName(cursor.getString(1));//TEST_DETAILS_TESTNAME
                TestDetails.setCustomer(cursor.getString(2));//TEST_DETAILS_CUSTOMER
                TestDetails.setRawDataNo(cursor.getString(3));//TEST_DETAILS_RAWDATANO
                TestDetails.setDateOfTest(cursor.getString(4));//TEST_DETAILS_DATEOFTEST
                TestDetails.setInstrumentUsed(cursor.getString(5));//TEST_DETAILS_INSTRUMENTUSED
                TestDetails.setInstrumentNo(cursor.getString(6));//TEST_DETAILS_INSTRUMENTNO
                TestDetails.setMake(cursor.getString(7));//TEST_DETAILS_MAKE
                TestDetails.setModel(cursor.getString(8));//TEST_DETAILS_MODEL
                TestDetails.setCalibratedOn(cursor.getString(9));//TEST_DETAILS_CALIBRATEDON

                TestDetails.setCalibratedDueOn(cursor.getString(10));//TEST_DETAILS_CALIBRATEDDUEON
                TestDetails.setTestSpecification(cursor.getString(11));//TEST_DETAILS_TESTSPECIFICATION
                TestDetails.setTestReference(cursor.getString(12));//TEST_DETAILS_TEST_TESTREFERENCE
                TestDetails.setOccupencyState(cursor.getString(13));//TEST_DETAILS_OCCUPENCYSTATE
                TestDetails.setBlockName(cursor.getString(14));//TEST_DETAILS_BLOCKNAME

                TestDetails.setTestArea(cursor.getString(15));//TEST_DETAILS_TESTAREA
                TestDetails.setAhuNo(cursor.getString(16));//TEST_DETAILS_AHUNO
                TestDetails.setRoomNo(cursor.getString(17));//TEST_DETAILS_ROOMNO
                TestDetails.setRoomName(cursor.getString(18));//TEST_DETAILS_ROOMNAME
                TestDetails.setEquipmentNo(cursor.getString(19));//TEST_DETAILS_EQUIPMENTNO
                TestDetails.setEquipmentName(cursor.getString(20));//TEST_DETAILS_EQUIPMENTNAME

                TestDetails.setTesterName(cursor.getString(21));//TEST_DETAILS_TESTERNAME
                TestDetails.setWitnessName(cursor.getString(22));//TEST_DETAILS_WITNESSNAME
                TestDetails.setPartnerName(cursor.getString(23));//TEST_DETAILS_PARTNERNAME

                TestDetails.setSamplingFlowRate(cursor.getString(24));//TEST_DETAILS_SAMPLINGFLOWRATE
                TestDetails.setSamplingTime(cursor.getString(25));//TEST_DETAILS_SAMPLINGTIME
                TestDetails.setAerosolUsed(cursor.getString(26));//TEST_DETAILS_AEROSOLUSED
                TestDetails.setAerosolGeneratorType(cursor.getString(27));//TEST_DETAILS_AEROSOLGENERATORTYPE
                TestDetails.setTestCode(cursor.getString(28));//TEST_DETAILS_TESTCODE
                TestDetails.setRoomVolume(cursor.getString(29));
                TestDetails.setTestWitnessOrg(cursor.getString(30));
                TestDetails.setTestCondoctorOrg(cursor.getString(31));
                TestDetails.setTestItem(cursor.getString(32));
                testDetailList.add(TestDetails);
            } while (cursor.moveToNext());
        }
        return testDetailList;
    }

    // select data from TestSpecificationValue table
    public ArrayList<TestSpesificationValue> getTestSpecificationValueById(String testDetailId) {
        ArrayList<TestSpesificationValue> testSpecificValueList = new ArrayList<TestSpesificationValue>();
        String selectQuery = "SELECT * FROM " + TESTSPECIFICATIONVALUE_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.TESTSPECIFICATIONVALUE_TEST_DETAIL_ID
                + " = " + '"' + testDetailId + '"';
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TestSpesificationValue testSpecificValue = new TestSpesificationValue();
                testSpecificValue.setTest_specific_id(cursor.getInt(0));//TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID
                testSpecificValue.setTest_detail_id(cursor.getString(1));//TESTSPECIFICATIONVALUE_TEST_DETAIL_ID
                testSpecificValue.setFieldName(cursor.getString(2));//TESTSPECIFICATIONVALUE_FIELDNAME
                testSpecificValue.setFieldValue(cursor.getString(3));//TESTSPECIFICATIONVALUE_FIELDVALUE

                testSpecificValueList.add(testSpecificValue);
            } while (cursor.moveToNext());
        }
        return testSpecificValueList;
    }

    //Select data from TestReading table;
    public ArrayList<TestReading> getTestReadingDataById(String testDetailId) {
        ArrayList<TestReading> testReadingList = new ArrayList<TestReading>();

        String selectQuery = "SELECT * FROM " + TESTREADING_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.TESTREADING_TEST_DETAIL_ID
                + " = " + '"' + testDetailId + '"';

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TestReading testReading = new TestReading();
                testReading.setTestReadingID(cursor.getInt(0));//TESTREADING_TESTREADINGID
                testReading.setTest_detail_id(cursor.getInt(1));//TESTREADING_TEST_DETAIL_ID
                testReading.setEntityName(cursor.getString(2));//TESTREADING_ENTITYNAME
                testReading.setValue(cursor.getString(3));//TESTREADING_VALUE

                testReadingList.add(testReading);
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "getTestReadingDataById " + testReadingList.size());
        return testReadingList;
    }

    // select data from Room info table
    public ArrayList<Room> getRoomNoInfoByroomNameAndAhu(String roomName, int ahuNo) {
        ArrayList<Room> roomArrayList;
        roomArrayList = new ArrayList<Room>();
        String selectQuery = "SELECT * FROM " + ROOM_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.ROOM_ROOMNAME + " = " + '"' + roomName + '"' + " AND " + ValdocDatabaseHandler.ROOM_AHUID + " = " + '"' + ahuNo + '"';
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setRoomId(cursor.getInt(0));
                room.setAhuId(cursor.getInt(1));
                room.setAreaId(cursor.getInt(2));
                room.setRoomName(cursor.getString(3));
                room.setRoomNo(cursor.getString(4));
                room.setWidth(cursor.getDouble(5));
                room.setHeight(cursor.getDouble(6));
                room.setLength(cursor.getDouble(7));
                room.setArea(cursor.getDouble(8));
                room.setVolume(cursor.getDouble(9));

                room.setAcph(cursor.getInt(10));
                room.setTestRef(cursor.getString(11));
                room.setIsoClause(cursor.getString(12));
                room.setOccupancyState(cursor.getString(13));
                room.setSupplyAirflow(cursor.getDouble(14));
                room.setAhuFlowCFM(cursor.getDouble(15));

                room.setRoomPressure(cursor.getDouble(16));
                room.setFreshAirCFM(cursor.getDouble(17));
                room.setBleedAirCFM(cursor.getDouble(18));
                room.setExhaustAirFlow(cursor.getDouble(19));
                room.setMaxTemperature(cursor.getDouble(20));
                room.setMaxRH(cursor.getDouble(21));
                room.setMinTemperature(cursor.getDouble(22));
                room.setMinRH(cursor.getDouble(23));

                room.setReturnAirFlow(cursor.getDouble(24));
//                room.setSupplyAirGrillQTY(cursor.getInt(23));
//                room.setReturnAirGrillQTY(cursor.getInt(24));
//                room.setSupplyAirFilterQTY(cursor.getInt(25));
//                room.setReturnAirFilterQTY(cursor.getInt(26));
                room.setRemarks(cursor.getString(25));
                room.setLastUpdatedDate(cursor.getString(26));
                roomArrayList.add(room);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return roomArrayList;
    }


    // select data from applicable test room table
    public ArrayList<ApplicableTestRoom> getApplicableTestRoomInfo(int roomId, String testCode) {
        ArrayList<ApplicableTestRoom> applicableTestRoomArrayList = new ArrayList<ApplicableTestRoom>();
        String selectQuery = "SELECT * FROM " + APLICABLE_TEST_ROOM_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TESTCODE + " = " + '"' + testCode + '"' + " AND "
                + ValdocDatabaseHandler.APLICABLE_TEST_ROOM_ROOMID + " = " + roomId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ApplicableTestRoom applicableTestRoom = new ApplicableTestRoom();
                applicableTestRoom.setAplicable_testId(cursor.getInt(0));
                applicableTestRoom.setRoomId(cursor.getInt(1));
                applicableTestRoom.setTestName(cursor.getString(2));
                applicableTestRoom.setTestCode(cursor.getString(3));
                applicableTestRoom.setTestFormat(cursor.getString(4));
                applicableTestRoom.setTestSpecification(cursor.getString(5));
                applicableTestRoom.setOccupencyState(cursor.getString(6));
                applicableTestRoom.setTestReference(cursor.getString(7));
                applicableTestRoom.setTestProp(cursor.getString(8));

                applicableTestRoom.setPeriodicity(cursor.getString(9));
                applicableTestRoom.setLocation(cursor.getInt(10));
                applicableTestRoom.setNoOfCycle(cursor.getInt(11));
                applicableTestRoom.setLastUpdatedDate(cursor.getString(12));
                applicableTestRoomArrayList.add(applicableTestRoom);
                Log.d("Avinash", "applicableTestRoom=" + applicableTestRoom.getTestName());
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return applicableTestRoomArrayList;
    }


    // select data from applicable test ahu table
    public ArrayList<ApplicableTestAhu> getApplicableTestAhuInfo(int ahuId, String testCode, String testItem) {
        ArrayList<ApplicableTestAhu> applicableTestAhuArrayList = new ArrayList<ApplicableTestAhu>();
        String selectQuery = "SELECT * FROM " + APLICABLE_TEST_AHU_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.APLICABLE_TEST_AHU_TESTCODE + " = " + '"' + testCode + '"' + " AND "
                + ValdocDatabaseHandler.APLICABLE_TEST_AHU_AHUID + " = " + ahuId + " AND " +
                ValdocDatabaseHandler.APLICABLE_TEST_AHU_TESTITEM + " = " + '"' + testItem + '"';

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("Avinash", "applicableTestahu selectQuery=" + selectQuery);
        Log.d("Avinash", "applicableTestahu before=" + cursor.getCount()+" testItem="+testItem);
        if (cursor.moveToFirst()) {
            do {
                ApplicableTestAhu applicableTestAhu = new ApplicableTestAhu();
                applicableTestAhu.setAplicable_testId(cursor.getInt(0));
                applicableTestAhu.setAhuId(cursor.getInt(1));
                applicableTestAhu.setTestName(cursor.getString(2));
                applicableTestAhu.setTestCode(cursor.getString(3));
                applicableTestAhu.setTestFormat(cursor.getString(4));
                applicableTestAhu.setTestItem(cursor.getString(5));
                applicableTestAhu.setTestSpecification(cursor.getString(6));
                applicableTestAhu.setOccupencyState(cursor.getString(7));
                applicableTestAhu.setTestReference(cursor.getString(8));
                applicableTestAhu.setTestProp(cursor.getString(9));
                applicableTestAhu.setPeriodicity(cursor.getString(10));
                applicableTestAhu.setLocation(cursor.getInt(11));
                applicableTestAhu.setNoOfCycle(cursor.getInt(12));
                applicableTestAhu.setLastUpdatedDate(cursor.getString(13));
                applicableTestAhuArrayList.add(applicableTestAhu);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return applicableTestAhuArrayList;
    }

    // select data from applicable test room table
    public ArrayList<ApplicableTestEquipment> getApplicableTestEquipmentInfo(int equipmentId, String testCode) {
        ArrayList<ApplicableTestEquipment> applicableTestEquipmentArrayList = new ArrayList<ApplicableTestEquipment>();
        String selectQuery = "SELECT * FROM " + APLICABLE_TEST_EQUIPMENT_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TESTCODE + " = " + '"' + testCode + '"' + " AND "
                + ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_EQUIPMENTID + " = " + equipmentId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("Avinash", "applicableTestEquipment selectQuery=" + selectQuery);

        Log.d("Avinash", "applicableTestEquipment before=" + cursor.getCount());
        if (cursor.moveToFirst()) {
            do {
                ApplicableTestEquipment applicableTestEquipment = new ApplicableTestEquipment();
                applicableTestEquipment.setAplicable_testId(cursor.getInt(0));
                applicableTestEquipment.setEquipmentId(cursor.getInt(1));
                applicableTestEquipment.setTestName(cursor.getString(2));
                applicableTestEquipment.setTestCode(cursor.getString(3));
                applicableTestEquipment.setTestFormat(cursor.getString(4));
                applicableTestEquipment.setTestSpecification(cursor.getString(5));
                applicableTestEquipment.setOccupencyState(cursor.getString(6));
                applicableTestEquipment.setTestReference(cursor.getString(7));
                applicableTestEquipment.setTestProp(cursor.getString(8));
                applicableTestEquipment.setPeriodicity(cursor.getString(9));
                applicableTestEquipment.setLocation(cursor.getInt(10));
                applicableTestEquipment.setNoOfCycle(cursor.getInt(11));
                applicableTestEquipment.setLastUpdatedDate(cursor.getString(12));
                applicableTestEquipmentArrayList.add(applicableTestEquipment);
                Log.d("Avinash", "applicableTestEquipment.setAplicable_testId(cursor.getInt(0));=" + applicableTestEquipment.getAplicable_testId());
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return applicableTestEquipmentArrayList;
    }

    // select name data from partners table
    public String getPartnerNameInfo(int partnerId) {
        String name = "";
        String selectQuery = "SELECT " + PARTNERS_NAME + " FROM " + PARTNERS_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.PARTNERS_ID + " = " + partnerId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(PARTNERS_NAME));
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return name;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
