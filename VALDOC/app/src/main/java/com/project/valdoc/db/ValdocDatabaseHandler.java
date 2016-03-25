package com.project.valdoc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.Area;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.Grill;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.PartnerUser;
import com.project.valdoc.intity.Partners;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.ServiceReport;
import com.project.valdoc.intity.ServiceReportDetail;
import com.project.valdoc.intity.TestDetails;
import com.project.valdoc.intity.TestMaster;
import com.project.valdoc.intity.TestReading;
import com.project.valdoc.intity.TestSpesificationValue;
import com.project.valdoc.intity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Avinash on 2/13/2016.
 */
public class ValdocDatabaseHandler extends SQLiteOpenHelper {

    // Database name
    public static final String DATABASE_NAME = "VolDoc.db";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    //ahu table details
    public static final String AHU_TABLE_NAME = "ahu";
    public static final String AHU_AHUID = "ahuId";
    public static final String AHU_AHUNO = "ahuNo";
    public static final String AHU_AHUTYPE = "ahuType";
    public static final String AHU_CAPACITY = "capacity";
    public static final String AHU_RETURNAIRCFM = "returnAirCFM";
    public static final String AHU_EXHAUSTAIRCFM = "exhaustAirCFM";
    public static final String AHU_BLEEDFILTERTYPE = "bleedFilterType";
    public static final String AHU_BLEEDFILTEREFFICIENCY = "bleedFilterEfficiency";
    public static final String AHU_BLEEDAIRCFM = "bleedAirCFM";
    public static final String AHU_BLEEDFILTERQTY = "bleedFilterQty";
    public static final String AHU_BLEEDFILTERSIZE = "bleedFilterSize";
    public static final String AHU_FRESHFILTERTYPE = "freshFilterType";
    public static final String AHU_FRESHAIRCFM = "freshAirCFM";
    public static final String AHU_FRESHFILTERQTY = "freshFilterQty";
    public static final String AHU_FRESHFILTERSIZE = "freshFilterSize";
    public static final String AHU_AHUHEPAFILTERQTY = "ahuHEPAFilterQty";
    public static final String AHU_HEPAFILTEREFFICIENCY = "hepaFilterEfficiency";
    public static final String AHU_HEPAPARTICLESIZE = "hepaParticleSize";
    public static final String AHU_HEPAFILTERSPECIFICATION = "hepaFilterSpecification";
    public static final String AHU_CREATIONDATE = "creationDate";
    // ahu table create statment
    private static final String CREATE_TABLE_AHU = "CREATE TABLE " + AHU_TABLE_NAME
            + "(" + AHU_AHUID + " INTEGER," + AHU_AHUNO + " TEXT," + AHU_AHUTYPE + " TEXT,"
            + AHU_CAPACITY + " REAL," + AHU_RETURNAIRCFM + " REAL," + AHU_EXHAUSTAIRCFM + " REAL," + AHU_BLEEDFILTERTYPE + " TEXT,"
            + AHU_BLEEDFILTEREFFICIENCY + " REAL," + AHU_BLEEDAIRCFM + " REAL," + AHU_BLEEDFILTERQTY + " INTEGER,"
            + AHU_BLEEDFILTERSIZE + " TEXT," + AHU_FRESHFILTERTYPE + " TEXT," + AHU_FRESHAIRCFM + " REAL," + AHU_FRESHFILTERQTY
            + " INTEGER," + AHU_FRESHFILTERSIZE + " TEXT," + AHU_AHUHEPAFILTERQTY + " INTEGER," + AHU_HEPAFILTEREFFICIENCY
            + " REAL," + AHU_HEPAPARTICLESIZE + " TEXT," + AHU_HEPAFILTERSPECIFICATION + " REAL,"
            + AHU_CREATIONDATE + " TEXT " + ")";

    //Applicable test equipment able details
    public static final String APLICABLE_TEST_EQUIPMENT_TABLE_NAME = "aplicable_test_equipment";
    public static final String APLICABLE_TEST_EQUIPMENT_APLICABLE_TESTID = "aplicable_testId";
    public static final String APLICABLE_TEST_EQUIPMENT_EQUIPMENTID = "equipmentId";
    public static final String APLICABLE_TEST_EQUIPMENT_TESTNAME = "testName";
    public static final String APLICABLE_TEST_EQUIPMENT_PERIODICITY = "periodicity";
    public static final String APLICABLE_TEST_EQUIPMENT_LOCATION = "location";
    public static final String APLICABLE_TEST_EQUIPMENT_NOOFCYCLE = "noOfCycle";
    public static final String APLICABLE_TEST_EQUIPMENT_CREATIONDATE = "creationDate";
    // aplicable_test_equipment table create statment
    private static final String CREATE_TABLE_APLICABLE_TEST_EQUIPMENT = "CREATE TABLE " + APLICABLE_TEST_EQUIPMENT_TABLE_NAME
            + "(" + APLICABLE_TEST_EQUIPMENT_APLICABLE_TESTID + " INTEGER," + APLICABLE_TEST_EQUIPMENT_EQUIPMENTID + " INTEGER,"
            + APLICABLE_TEST_EQUIPMENT_TESTNAME + " TEXT," + APLICABLE_TEST_EQUIPMENT_PERIODICITY + " TEXT,"
            + APLICABLE_TEST_EQUIPMENT_LOCATION + " INTEGER," + APLICABLE_TEST_EQUIPMENT_NOOFCYCLE + " INTEGER,"
            + APLICABLE_TEST_EQUIPMENT_CREATIONDATE + " TEXT " + ")";

    //Applicable test equipment able details
    public static final String APLICABLE_TEST_ROOM_TABLE_NAME = "aplicable_test_room";
    public static final String APLICABLE_TEST_ROOM_APLICABLE_TESTID = "aplicable_testId";
    public static final String APLICABLE_TEST_ROOM_ROOMID = "roomId";
    public static final String APLICABLE_TEST_ROOM_TESTNAME = "testName";
    public static final String APLICABLE_TEST_ROOM_PERIODICITY = "periodicity";
    public static final String APLICABLE_TEST_ROOM_LOCATION = "location";
    public static final String APLICABLE_TEST_ROOM_NOOFCYCLE = "noOfCycle";
    public static final String APLICABLE_TEST_ROOM_CREATIONDATE = "creationDate";
    // aplicable_test_equipment table create statment
    private static final String CREATE_TABLE_APLICABLE_TEST_ROOM = "CREATE TABLE " + APLICABLE_TEST_ROOM_TABLE_NAME
            + "(" + APLICABLE_TEST_ROOM_APLICABLE_TESTID + " INTEGER," + APLICABLE_TEST_ROOM_ROOMID + " INTEGER,"
            + APLICABLE_TEST_ROOM_TESTNAME + " TEXT," + APLICABLE_TEST_ROOM_PERIODICITY + " TEXT,"
            + APLICABLE_TEST_ROOM_LOCATION + " INTEGER," + APLICABLE_TEST_ROOM_NOOFCYCLE + " INTEGER,"
            + APLICABLE_TEST_ROOM_CREATIONDATE + " TEXT " + ")";

    //equipment able details
    public static final String EQUIPMENT_TABLE_NAME = "equipment";
    public static final String EQUIPMENT_EQUIPMENTID = "equipmentId";
    public static final String EQUIPMENT_ROOMID = "roomId";
    public static final String EQUIPMENT_EQUIPMENTNO = "equipmentNo";
    public static final String EQUIPMENT_EQUIPMENTNAME = "equipmentName";
    public static final String EQUIPMENT_OCCUPANCYSTATE = "occupancyState";
    public static final String EQUIPMENT_VELOCITY = "velocity";
    public static final String EQUIPMENT_TESTREFERENCE = "testReference";
    public static final String EQUIPMENT_FILTERQUANTITY = "filterQuantity";
    public static final String EQUIPMENT_EQUIPMENTLOAD = "equipmentLoad";
    public static final String EQUIPMENT_CREATIONDATE = "creationDate";
    // equipment table create statment
    private static final String CREATE_TABLE_EQUIPMENT = "CREATE TABLE " + EQUIPMENT_TABLE_NAME
            + "(" + EQUIPMENT_EQUIPMENTID + " INTEGER," + EQUIPMENT_ROOMID + " INTEGER,"
            + EQUIPMENT_OCCUPANCYSTATE + " TEXT," + EQUIPMENT_EQUIPMENTNO + " TEXT," + EQUIPMENT_EQUIPMENTNAME + " TEXT,"
            + EQUIPMENT_VELOCITY + " REAL," + EQUIPMENT_TESTREFERENCE + " TEXT," + EQUIPMENT_FILTERQUANTITY + " INTEGER," + EQUIPMENT_EQUIPMENTLOAD + " REAL,"
            + EQUIPMENT_CREATIONDATE + " TEXT " + ")";


    //equipment filter able details
    public static final String EQUIPMENTFILTER_TABLE_NAME = "equipmentfilter";
    public static final String EQUIPMENTFILTER_FILTERID = "filterId";
    public static final String EQUIPMENTFILTER_FILTERCODE = "filterCode";
    public static final String EQUIPMENTFILTER_EQUIPMENTID = "equipmentId";
    public static final String EQUIPMENTFILTER_WIDTH = "width";
    public static final String EQUIPMENTFILTER_LENGTH = "length";
    public static final String EQUIPMENTFILTER_GRILLAREA = "grillArea";
    public static final String EQUIPMENTFILTER_EFFECTIVEGRILLAREA = "effectiveGrillArea";
    public static final String EQUIPMENTFILTER_ISSUPPLYFILTER = "isSupplyFilter";
    public static final String EQUIPMENTFILTER_CREATIONDATE = "creationDate";

    // equipment filer table create statment
    private static final String CREATE_TABLE_EQUIPMENTFILTER = "CREATE TABLE " + EQUIPMENTFILTER_TABLE_NAME
            + "(" + EQUIPMENTFILTER_FILTERID + " INTEGER," + EQUIPMENTFILTER_FILTERCODE + " TEXT,"
            + EQUIPMENTFILTER_WIDTH + " REAL," + EQUIPMENTFILTER_LENGTH + " REAL," + EQUIPMENTFILTER_GRILLAREA + " REAL,"
            + EQUIPMENTFILTER_EQUIPMENTID + " INTEGER," + EQUIPMENTFILTER_EFFECTIVEGRILLAREA + " REAL,"
            + EQUIPMENTFILTER_ISSUPPLYFILTER + " INTEGER," + EQUIPMENTFILTER_CREATIONDATE + " TEXT " + ")";

    //gril able details
    public static final String GRILL_TABLE_NAME = "grill";
    public static final String GRILL_GRILLID = "grillId";
    public static final String GRILL_GRILLCODE = "grillCode";
    public static final String GRILL_ROOMID = "roomId";
    public static final String GRILL_LENGTH = "length";
    public static final String GRILL_WIDTH = "widh";
    public static final String GRILL_GRILLAREA = "grillArea";
    public static final String GRILL_EFFECTIVEAREA = "effectiveArea";
    public static final String GRILL_ISSUPPLYGRILL = "isSupplyGrill";
    public static final String GRILL_ADDITIONALDETAIL = "additionalDetail";
    public static final String GRILL_CREATIONDATE = "creationDate";

    // gril table create statment
    private static final String CREATE_TABLE_GRIL = "CREATE TABLE " + GRILL_TABLE_NAME
            + "(" + GRILL_GRILLID + " INTEGER," + GRILL_ROOMID + " INTEGER,"
            + GRILL_GRILLCODE + " TEXT," + GRILL_LENGTH + " REAL," + GRILL_WIDTH + " REAL," + GRILL_GRILLAREA + " REAL,"
            + GRILL_EFFECTIVEAREA + " REAL," + GRILL_ISSUPPLYGRILL + " INTEGER," + GRILL_ADDITIONALDETAIL + " INTEGER,"
            + GRILL_CREATIONDATE + " TEXT " + ")";


    //partner instrument able details
    public static final String PARTNER_INSTRUMENT_TABLE_NAME = "partner_instrument";
    public static final String PARTNER_INSTRUMENT_PINSTRUMENTID = "pInstrumentId";
    public static final String PARTNER_INSTRUMENT_PARTNERID = "partnerId";
    public static final String PARTNER_INSTRUMENT_PINSTRUMENTNAME = "pInstrumentName";
    public static final String PARTNER_INSTRUMENT_MAKE = "make";
    public static final String PARTNER_INSTRUMENT_MODEL = "model";
    public static final String PARTNER_INSTRUMENT_LASTCALIBRATED = "lastCalibrated";
    public static final String PARTNER_INSTRUMENT_CALIBRATIONDUEDATE = "calibrationDueDate";
    public static final String PARTNER_INSTRUMENT_CURRENTLOCATION = "currentLocation";
    public static final String PARTNER_INSTRUMENT_STATUS = "status";
    public static final String PARTNER_INSTRUMENT_TESTID = "testId";
    public static final String PARTNER_INSTRUMENT_CREATIONDATE = "creationDate";

    // partner instrument table create statment
    private static final String CREATE_TABLE_PARTNER_INSTRUMENT = "CREATE TABLE " + PARTNER_INSTRUMENT_TABLE_NAME
            + "(" + PARTNER_INSTRUMENT_PINSTRUMENTID + " INTEGER," + PARTNER_INSTRUMENT_PARTNERID + " INTEGER,"
            + PARTNER_INSTRUMENT_PINSTRUMENTNAME + " TEXT," + PARTNER_INSTRUMENT_MAKE + " TEXT," + PARTNER_INSTRUMENT_MODEL + " TEXT,"
            + PARTNER_INSTRUMENT_LASTCALIBRATED + " NUMERIC," + PARTNER_INSTRUMENT_CALIBRATIONDUEDATE + " NUMERIC,"
            + PARTNER_INSTRUMENT_CURRENTLOCATION + " TEXT," + PARTNER_INSTRUMENT_STATUS + " TEXT," + PARTNER_INSTRUMENT_TESTID
            + " INTEGER," + PARTNER_INSTRUMENT_CREATIONDATE + " TEXT " + ")";


    //client instrument able details
    public static final String CLIENT_INSTRUMENT_TABLE_NAME = "client_instrument";
    public static final String CLIENT_INSTRUMENT_CINSTRUMENTID = "cInstrumentId";
    public static final String CLIENT_INSTRUMENT_INSTRUMENTID = "instrumentId";
    public static final String CLIENT_INSTRUMENT_SERIALNO = "serialNo";
    public static final String CLIENT_INSTRUMENT_CINSTRUMENTNAME = "cInstrumentName";
    public static final String CLIENT_INSTRUMENT_MAKE = "make";
    public static final String CLIENT_INSTRUMENT_MODEL = "model";
    public static final String CLIENT_INSTRUMENT_LASTCALIBRATED = "lastCalibrated";
    public static final String CLIENT_INSTRUMENT_CALIBRATIONDUEDATE = "calibrationDueDate";
    public static final String CLIENT_INSTRUMENT_CURRENTLOCATION = "currentLocation";
    public static final String CLIENT_INSTRUMENT_STATUS = "status";
    public static final String CLIENT_INSTRUMENT_TESTID = "testId";
    public static final String CLIENT_INSTRUMENT_CREATIONDATE = "creationDate";

    // partner instrument table create statment
    private static final String CREATE_TABLE_CLIENT_INSTRUMENT = "CREATE TABLE " + CLIENT_INSTRUMENT_TABLE_NAME
            + "(" + CLIENT_INSTRUMENT_CINSTRUMENTID + " INTEGER," + CLIENT_INSTRUMENT_INSTRUMENTID + " TEXT,"
            + CLIENT_INSTRUMENT_SERIALNO + " TEXT," + CLIENT_INSTRUMENT_CINSTRUMENTNAME + " TEXT, " + CLIENT_INSTRUMENT_MAKE + " TEXT,"
            + CLIENT_INSTRUMENT_MODEL + " TEXT," + CLIENT_INSTRUMENT_LASTCALIBRATED + " TEXT,"
            + CLIENT_INSTRUMENT_CALIBRATIONDUEDATE + " TEXT," + CLIENT_INSTRUMENT_CURRENTLOCATION + " TEXT,"
            + CLIENT_INSTRUMENT_STATUS + " TEXT," + CLIENT_INSTRUMENT_TESTID
            + " INTEGER," + CLIENT_INSTRUMENT_CREATIONDATE + " TEXT " + ")";


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
    public static final String ROOMFILTER_GRILLAREA = "grillArea";
    public static final String ROOMFILTER_EFFECTIVEGRILLAREA = "effectiveGrillArea";
    public static final String ROOMFILTER_ISSUPPLYFILTER = "isSupplyFilter";
    public static final String ROOMFILTER_CREATIONDATE = "creationDate";

    // roomfilter table create statment
    private static final String CREATE_TABLE_ROOMFILTER = "CREATE TABLE " + ROOMFILTER_TABLE_NAME
            + "(" + ROOMFILTER_FILTERID + " INTEGER," + ROOMFILTER_FILTERTYPE + " TEXT,"
            + ROOMFILTER_EFFICIENCY + " REAL," + ROOMFILTER_FILTERCODE + " TEXT," + ROOMFILTER_PARTICLESIZE + " TEXT,"
            + ROOMFILTER_ROOMID + " INTEGER," + ROOMFILTER_SPECIFICATION + " REAL,"
            + ROOMFILTER_WIDTH + " REAL," + ROOMFILTER_LENGTH + " REAL," + ROOMFILTER_GRILLAREA + " REAL,"
            + ROOMFILTER_EFFECTIVEGRILLAREA + " REAL," + ROOMFILTER_ISSUPPLYFILTER + " INTEGER,"
            + ROOMFILTER_CREATIONDATE + " TEXT " + ")";


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
    public static final String ROOM_ACPHNLT = "acphNLT";
    public static final String ROOM_TESTREF = "testRef";
    public static final String ROOM_ISOCLAUSE = "isoClause";
    public static final String ROOM_OCCUPANCYSTATE = "occupancyState";
    public static final String ROOM_ROOMSUPPLYAIRFLOWCFM = "roomSupplyAirflowCFM";
    public static final String ROOM_AHUFLOWCFM = "ahuFlowCFM";
    public static final String ROOM_ROOMPRESSUREPA = "roomPressurePA";
    public static final String ROOM_FRESHAIRCFM = "freshAirCFM";
    public static final String ROOM_BLEEDAIRCFM = "bleedAirCFM";
    public static final String ROOM_EXHAUSTAIRCFM = "exhaustAirCFM";
    public static final String ROOM_TEMPERATURE = "temperature";
    public static final String ROOM_RH = "rh";
    public static final String ROOM_RETURNAIRCFM = "returnAirCFM";
    public static final String ROOM_SUPPLYAIRGRILLQTY = "supplyAirGrillQTY";
    public static final String ROOM_RETURNAIRGRILLQTY = "returnAirGrillQTY";
    public static final String ROOM_SUPPLYAIRFILTERQTY = "supplyAirFilterQTY";
    public static final String ROOM_RETURNAIRFILTERQTY = "returnAirFilterQTY";
    public static final String ROOM_REMARKS = "remarks";
    public static final String ROOM_CREATIONDATE = "creationDate";

    // room table create statment
    private static final String CREATE_TABLE_ROOM = "CREATE TABLE " + ROOM_TABLE_NAME
            + "(" + ROOM_ROOMID + " INTEGER," + ROOM_AHUID + " INTEGER," + ROOM_AREAID + " INTEGER,"
            + ROOM_ROOMNAME + " TEXT," + ROOM_ROOMNO + " TEXT," + ROOM_WIDTH + " REAL,"
            + ROOM_HEIGHT + " REAL," + ROOM_LENGTH + " REAL," + ROOM_AREA + " REAL," + ROOM_VOLUME + " REAL,"
            + ROOM_ACPHNLT + " INTEGER," + ROOM_TESTREF + " TEXT," + ROOM_ISOCLAUSE + " TEXT," + ROOM_OCCUPANCYSTATE + " TEXT,"
            + ROOM_ROOMSUPPLYAIRFLOWCFM + " REAL," + ROOM_AHUFLOWCFM + " REAL," + ROOM_ROOMPRESSUREPA + " REAL,"
            + ROOM_FRESHAIRCFM + " REAL," + ROOM_BLEEDAIRCFM + " REAL," + ROOM_EXHAUSTAIRCFM + " REAL," + ROOM_TEMPERATURE + " REAL,"
            + ROOM_RH + " REAL," + ROOM_RETURNAIRCFM + " REAL," + ROOM_SUPPLYAIRGRILLQTY + " INTEGER," + ROOM_RETURNAIRGRILLQTY
            + " INTEGER," + ROOM_SUPPLYAIRFILTERQTY + " INTEGER," + ROOM_RETURNAIRFILTERQTY + " INTEGER," + ROOM_REMARKS + " TEXT,"
            + ROOM_CREATIONDATE + " TEXT " + ")";

    //user table details
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_TYPE = "type";
    public static final String USER_EMAIL = "email";
    public static final String USER_CONTACT = "contact";
    public static final String USER_DEPARTMENT = "department";
    public static final String USER_ACTIVE = "active";
    public static final String USER_DELETED = "deleted";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PARTNERID = "partnerId";
    public static final String USER_CREATIONDATE = "creationDate";

    // user table create statment
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE_NAME
            + "(" + USER_ID + " INTEGER," + USER_NAME + " TEXT,"
            + USER_TYPE + " REAL," + USER_EMAIL + " TEXT," + USER_CONTACT + " TEXT," + USER_DEPARTMENT + " TEXT,"
            + USER_ACTIVE + " NUMERIC," + USER_DELETED + " NUMERIC," + USER_PASSWORD + " TEXT, " + USER_PARTNERID + " INTEGER,"
            + USER_CREATIONDATE + " TEXT " + ")";

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
    public static final String AREA_CREATIONDATE = "creationDate";

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
            + "(" + TESTREADING_TESTREADINGID + " INTEGER," + TESTREADING_TEST_DETAIL_ID + " INTEGER,"
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

    // test details table create statment
    private static final String CREATE_TABLE_TESTDETAILS = "CREATE TABLE " + TEST_DETAILS_TABLE_NAME
            + "(" + TEST_DETAILS_TEST_DETAIL_ID + " INTEGER," + TEST_DETAILS_TESTNAME + " TEXT,"
            + TEST_DETAILS_CUSTOMER + " TEXT, " + TEST_DETAILS_RAWDATANO + " TEXT," + TEST_DETAILS_DATEOFTEST + " TEXT,"
            + TEST_DETAILS_INSTRUMENTUSED + " TEXT," + TEST_DETAILS_INSTRUMENTNO + " TEXT," + TEST_DETAILS_MAKE + " TEXT,"
            + TEST_DETAILS_MODEL + " TEXT," + TEST_DETAILS_CALIBRATEDON + " TEXT," + TEST_DETAILS_CALIBRATEDDUEON + " TEXT,"
            + TEST_DETAILS_TESTSPECIFICATION + " TEXT," + TEST_DETAILS_TEST_TESTREFERENCE + " TEXT," + TEST_DETAILS_OCCUPENCYSTATE + " TEXT,"
            + TEST_DETAILS_BLOCKNAME + " TEXT," + TEST_DETAILS_TESTAREA + " TEXT," + TEST_DETAILS_AHUNO + " TEXT," + TEST_DETAILS_ROOMNO + " TEXT,"
            + TEST_DETAILS_ROOMNAME + " TEXT," + TEST_DETAILS_EQUIPMENTNO + " TEXT," + TEST_DETAILS_EQUIPMENTNAME + " TEXT," +
            TEST_DETAILS_TESTERNAME + " TEXT," + TEST_DETAILS_WITNESSNAME + " TEXT," + TEST_DETAILS_PARTNERNAME + " TEXT" + ")";


    //test spesification table details
    public static final String TESTSPECIFICATIONVALUE_TABLE_NAME = "test_specific_value";
    public static final String TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID = "test_specific_id";
    public static final String TESTSPECIFICATIONVALUE_TEST_DETAIL_ID = "test_detail_id";
    public static final String TESTSPECIFICATIONVALUE_FIELDNAME = "fieldName";
    public static final String TESTSPECIFICATIONVALUE_FIELDVALUE = "fieldValue";

    // test specification table create statment
    private static final String CREATE_TABLE_TESTSPECIFICATIONVALUE = "CREATE TABLE " + TESTSPECIFICATIONVALUE_TABLE_NAME
            + "(" + TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID + " INTEGER," + TESTSPECIFICATIONVALUE_TEST_DETAIL_ID + " INTEGER,"
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
            + SERVICE_REPORT_REMARK + " TEXT," + SERVICE_REPORT_WORKORDER + " TEXT," + SERVICE_REPORT_WITNESSDESIGNATION + " TEXT " + ")";

    //service_report_detail table creation
    public static final String SERVICE_REPORT_DETAIL_TABLE_NAME = "service_report_detail";
    public static final String SERVICE_REPORT_DETAIL_ID = "id";
    public static final String SERVICE_REPORT_DETAIL_SERVICE_REPORT_ID = "service_report_id";
    public static final String SERVICE_REPORT_DETAIL_TYPEOFTEST = "typeOfTest";
    public static final String SERVICE_REPORT_DETAIL_AREAOFTEST = "areaOfTest";
    public static final String SERVICE_REPORT_DETAIL_EQUIPMENTAHUNO = "equipmentAhuNo";
    public static final String SERVICE_REPORT_DETAIL_NOOFLOC = "NoOfLoc";
    public static final String SERVICE_REPORT_DETAIL_NOOFHOURDAYS = "NoOfHourDays";


    // service_report_detail table create statment
    private static final String CREATE_TABLE_SERVICE_REPORT_DETAIL = "CREATE TABLE " + SERVICE_REPORT_DETAIL_TABLE_NAME
            + "(" + SERVICE_REPORT_DETAIL_ID + " INTEGER," + SERVICE_REPORT_DETAIL_SERVICE_REPORT_ID + " INTEGER,"
            + SERVICE_REPORT_DETAIL_TYPEOFTEST + " TEXT," + SERVICE_REPORT_DETAIL_AREAOFTEST + " TEXT, " + SERVICE_REPORT_DETAIL_EQUIPMENTAHUNO + " TEXT,"
            + SERVICE_REPORT_DETAIL_NOOFLOC + " INTEGER,"
            + SERVICE_REPORT_DETAIL_NOOFHOURDAYS + " REAL" + ")";

    //partners table creation
    public static final String PARTNERS_TABLE_NAME = "partners";
    public static final String PARTNERS_ID = "id";
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
    public static final String PARTNERS_CREATIONDATE = "creationDate";


    // service_report_detail table create statment
    private static final String CREATE_TABLE_PARTNERS = "CREATE TABLE " + PARTNERS_TABLE_NAME
            + "(" + PARTNERS_ID + " INTEGER," + PARTNERS_PARTNERCODE + " TEXT,"
            + PARTNERS_NAME + " TEXT," + PARTNERS_STATUS + " TEXT, " + PARTNERS_APPROVEDSINCE + " INTEGER,"
            + PARTNERS_ADDRESS + " TEXT," + PARTNERS_DIRECTORNAME + " TEXT," + PARTNERS_DIRECTOREMAIL + " TEXT," + PARTNERS_DIRECTORCELLNO + " TEXT,"
            + PARTNERS_REGEMAIL + " TEXT," + PARTNERS_REGCELLNO + " TEXT," + PARTNERS_SERVICEINCHARGE + " TEXT," + PARTNERS_EMAIL + " TEXT," + PARTNERS_CELLNO + " TEXT,"
            + PARTNERS_CREATIONDATE + " TEXT" + ")";


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
        db.execSQL(CREATE_TABLE_CLIENT_INSTRUMENT);
        db.execSQL(CREATE_TABLE_TESTMASTER);
        db.execSQL(CREATE_TABLE_AREA);
        db.execSQL(CREATE_TABLE_TESTREADING);
        db.execSQL(CREATE_TABLE_TESTDETAILS);
        db.execSQL(CREATE_TABLE_TESTSPECIFICATIONVALUE);
        db.execSQL(CREATE_TABLE_SERVICE_REPORT);
        db.execSQL(CREATE_TABLE_SERVICE_REPORT_DETAIL);
        db.execSQL(CREATE_TABLE_PARTNERS);
        Log.d("valdoc", "table created success fully");
    }


    //insert daa in servicereportdetails table
    public boolean insertPartners(String tableName, ArrayList<Partners> partnersArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (partnersArrayList.size() != 0) {
            for (Partners partners : partnersArrayList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PARTNERS_ID, partners.getId());
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
                contentValues.put(PARTNERS_CREATIONDATE, partners.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                db.insert(tableName, null, contentValues);
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
        db.insert(tableName, null, contentValues);
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
                contentValues.put(TESTREADING_TESTREADINGID, testReading.getTestReadingID());
                contentValues.put(TESTREADING_TEST_DETAIL_ID, testReading.getTest_detail_id());
                contentValues.put(TESTREADING_ENTITYNAME, testReading.getEntityName());
                contentValues.put(TESTREADING_VALUE, testReading.getValue());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(TESTSPECIFICATIONVALUE_TEST_SPECIFIC_ID, testSpesificationValue.getTest_specific_id());
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
                contentValues.put(AHU_CAPACITY, ahu.getCapacity());
                contentValues.put(AHU_RETURNAIRCFM, ahu.getReturnAirCFM());
                contentValues.put(AHU_EXHAUSTAIRCFM, ahu.getExhaustAirCFM());
                contentValues.put(AHU_BLEEDFILTERTYPE, ahu.getBleedFilterType());
                contentValues.put(AHU_BLEEDFILTEREFFICIENCY, ahu.getBleedFilterEfficiency());
                contentValues.put(AHU_BLEEDAIRCFM, ahu.getBleedAirCFM());
                contentValues.put(AHU_BLEEDFILTERQTY, ahu.getBleedFilterQty());
                contentValues.put(AHU_BLEEDFILTERSIZE, ahu.getBleedFilterSize());
                contentValues.put(AHU_FRESHFILTERTYPE, ahu.getFreshFilterType());
                contentValues.put(AHU_FRESHAIRCFM, ahu.getFreshAirCFM());
                contentValues.put(AHU_FRESHFILTERQTY, ahu.getFreshFilterQty());
                contentValues.put(AHU_FRESHFILTERSIZE, ahu.getFreshFilterSize());
                contentValues.put(AHU_AHUHEPAFILTERQTY, ahu.getAhuHEPAFilterQty());
                contentValues.put(AHU_HEPAFILTEREFFICIENCY, ahu.getHepaFilterEfficiency());
                contentValues.put(AHU_HEPAPARTICLESIZE, ahu.getHepaParticleSize());
                contentValues.put(AHU_HEPAFILTERSPECIFICATION, ahu.getHepaFilterSpecification());
                contentValues.put(AHU_CREATIONDATE, ahu.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(APLICABLE_TEST_EQUIPMENT_PERIODICITY, applicableTestEquipment.getPeriodicity());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_LOCATION, applicableTestEquipment.getLocation());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_NOOFCYCLE, applicableTestEquipment.getNoOfCycle());
                contentValues.put(APLICABLE_TEST_EQUIPMENT_CREATIONDATE, applicableTestEquipment.getCreationDate());
                db.insert(tableName, null, contentValues);
            }
            return true;
        } else {
            return false;
        }
    }

    // insert datain ApplicableTestRoom table
    public boolean insertApplicableTestRoom(String tableName, List<ApplicableTestRoom> applicableTestRoomList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (applicableTestRoomList.size() != 0) {
            for (ApplicableTestRoom applicableTestRoom : applicableTestRoomList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(APLICABLE_TEST_ROOM_APLICABLE_TESTID, applicableTestRoom.getAplicable_testId());
                contentValues.put(APLICABLE_TEST_ROOM_ROOMID, applicableTestRoom.getRoomId());
                contentValues.put(APLICABLE_TEST_ROOM_TESTNAME, applicableTestRoom.getTestName());
                contentValues.put(APLICABLE_TEST_ROOM_PERIODICITY, applicableTestRoom.getPeriodicity());
                contentValues.put(APLICABLE_TEST_ROOM_LOCATION, applicableTestRoom.getLocation());
                contentValues.put(APLICABLE_TEST_ROOM_NOOFCYCLE, applicableTestRoom.getNoOfCycle());
                contentValues.put(APLICABLE_TEST_ROOM_CREATIONDATE, applicableTestRoom.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(EQUIPMENT_OCCUPANCYSTATE, equipment.getOccupancyState());
                contentValues.put(EQUIPMENT_VELOCITY, equipment.getVelocity());
                contentValues.put(EQUIPMENT_TESTREFERENCE, equipment.getTestReference());
                contentValues.put(EQUIPMENT_FILTERQUANTITY, equipment.getFilterQuantity());
                contentValues.put(EQUIPMENT_EQUIPMENTLOAD, equipment.getEquipmentLoad());
                contentValues.put(EQUIPMENT_CREATIONDATE, equipment.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(EQUIPMENTFILTER_EFFECTIVEGRILLAREA, equipmentFilter.getEffectiveGrillArea());
                contentValues.put(EQUIPMENTFILTER_ISSUPPLYFILTER, equipmentFilter.getIsSupplyFilter());
                contentValues.put(EQUIPMENTFILTER_CREATIONDATE, equipmentFilter.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(GRILL_ADDITIONALDETAIL, grill.getAdditionalDetail());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(PARTNER_INSTRUMENT_PINSTRUMENTNAME, partnerInstrument.getpInstrumentName());
                contentValues.put(PARTNER_INSTRUMENT_MAKE, partnerInstrument.getMake());
                contentValues.put(PARTNER_INSTRUMENT_MODEL, partnerInstrument.getModel());
                contentValues.put(PARTNER_INSTRUMENT_LASTCALIBRATED, partnerInstrument.getLastCalibrated());
                contentValues.put(PARTNER_INSTRUMENT_CALIBRATIONDUEDATE, partnerInstrument.getCalibrationDueDate());
                contentValues.put(PARTNER_INSTRUMENT_CURRENTLOCATION, partnerInstrument.getCurrentLocation());
                contentValues.put(PARTNER_INSTRUMENT_STATUS, partnerInstrument.getStatus());
                contentValues.put(PARTNER_INSTRUMENT_TESTID, partnerInstrument.getTestId());
                contentValues.put(PARTNER_INSTRUMENT_CREATIONDATE, partnerInstrument.getCreationDate());
                Log.d("VALDOC", "controler response data  13=inserting=");
                db.insert(tableName, null, contentValues);
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
                contentValues.put(CLIENT_INSTRUMENT_CURRENTLOCATION, clientInstrument.getCurrentLocation());
                contentValues.put(CLIENT_INSTRUMENT_STATUS, clientInstrument.getStatus());
                contentValues.put(CLIENT_INSTRUMENT_TESTID, clientInstrument.getTestId());
                contentValues.put(CLIENT_INSTRUMENT_CREATIONDATE, clientInstrument.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(ROOM_ACPHNLT, room.getAcphNLT());
                contentValues.put(ROOM_TESTREF, room.getTestRef());
                contentValues.put(ROOM_ISOCLAUSE, room.getIsoClause());
                contentValues.put(ROOM_OCCUPANCYSTATE, room.getOccupancyState());
                contentValues.put(ROOM_ROOMSUPPLYAIRFLOWCFM, room.getRoomSupplyAirflowCFM());
                contentValues.put(ROOM_AHUFLOWCFM, room.getAhuFlowCFM());
                contentValues.put(ROOM_ROOMPRESSUREPA, room.getRoomPressurePA());
                contentValues.put(ROOM_FRESHAIRCFM, room.getFreshAirCFM());
                contentValues.put(ROOM_BLEEDAIRCFM, room.getBleedAirCFM());
                contentValues.put(ROOM_EXHAUSTAIRCFM, room.getExhaustAirCFM());
                contentValues.put(ROOM_TEMPERATURE, room.getTemperature());
                contentValues.put(ROOM_RH, room.getRh());
                contentValues.put(ROOM_RETURNAIRCFM, room.getReturnAirCFM());
                contentValues.put(ROOM_SUPPLYAIRGRILLQTY, room.getSupplyAirGrillQTY());
                contentValues.put(ROOM_RETURNAIRGRILLQTY, room.getReturnAirGrillQTY());
                contentValues.put(ROOM_SUPPLYAIRFILTERQTY, room.getSupplyAirFilterQTY());
                contentValues.put(ROOM_RETURNAIRFILTERQTY, room.getReturnAirFilterQTY());
                contentValues.put(ROOM_REMARKS, room.getRemarks());
                contentValues.put(ROOM_CREATIONDATE, room.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                contentValues.put(ROOMFILTER_EFFECTIVEGRILLAREA, roomFilter.getEffectiveGrillArea());
                contentValues.put(ROOMFILTER_ISSUPPLYFILTER, roomFilter.getIsSupplyFilter());
                contentValues.put(ROOMFILTER_CREATIONDATE, roomFilter.getCreationDate());
                db.insert(tableName, null, contentValues);
            }
            return true;
        } else {
            return false;
        }
    }

    // insert data in user table
    public boolean insertUser(String tableName, List<User> userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (userList.size() != 0) {
            for (User user : userList) {
                Log.d("valdoc", "insert user Login method :" + user.getName() + "\n" + user.getId());
                ContentValues contentValues = new ContentValues();
                contentValues.put(USER_ID, user.getId());
                contentValues.put(USER_NAME, user.getName());
                contentValues.put(USER_TYPE, user.getType());
                contentValues.put(USER_EMAIL, user.getEmail());
                contentValues.put(USER_CONTACT, user.getContact());
                contentValues.put(USER_DEPARTMENT, user.getDepartment());
                contentValues.put(USER_ACTIVE, user.getActive());
                contentValues.put(USER_DELETED, user.getDeleted());
                contentValues.put(USER_PASSWORD, user.getPassword());
                contentValues.put(USER_PARTNERID, user.getPartnerId());
                contentValues.put(USER_CREATIONDATE, user.getCreationDate());
                db.insert(tableName, null, contentValues);
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
                db.insert(tableName, null, contentValues);
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
                db.insert(tableName, null, contentValues);
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
                contentValues.put(AREA_CREATIONDATE, area.getCreationDate());
                contentValues.put(AREA_CREATIONDATE, area.getCreationDate());
                db.insert(tableName, null, contentValues);
            }
            return true;
        } else {
            return false;
        }
    }


    // select data from user table
    public ArrayList<User> getUserInfo() {
        ArrayList<User> userArrayList;
        userArrayList = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + USER_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "Login method :");
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setType(cursor.getString(2));
                user.setEmail(cursor.getString(3));
                user.setContact(cursor.getString(4));
                user.setDepartment(cursor.getString(5));
                user.setActive(Integer.parseInt(cursor.getString(6)));
                user.setDeleted(Integer.parseInt(cursor.getString(7)));
                user.setPassword(cursor.getString(8));
                user.setPartnerId(cursor.getInt(9));
                user.setCreationDate(cursor.getString(10));
                Log.d("valdoc", "Login method :" + user.getName() + "\n" + user.getPassword());
                userArrayList.add(user);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return userArrayList;
    }

    // select data from clientInstrument table
    public ArrayList<ClientInstrument> getClientInstrumentInfo() {
        ArrayList<ClientInstrument> clientInstrumentArrayList;
        clientInstrumentArrayList = new ArrayList<ClientInstrument>();
        String selectQuery = "SELECT * FROM " + CLIENT_INSTRUMENT_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
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
                clientInstrument.setCurrentLocation(cursor.getString(8));
                clientInstrument.setStatus(cursor.getString(9));
                clientInstrument.setTestId(cursor.getInt(10));
                clientInstrument.setCreationDate(cursor.getString(11));
                clientInstrumentArrayList.add(clientInstrument);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return clientInstrumentArrayList;
    }

    // select data from partnerInstrument table
    public ArrayList<PartnerInstrument> getPartnerInstrumentInfo(int partnerId) {
        ArrayList<PartnerInstrument> partnerInstrumentArrayList;
        partnerInstrumentArrayList = new ArrayList<PartnerInstrument>();
        String selectQuery = "SELECT * FROM " + PARTNER_INSTRUMENT_TABLE_NAME;
//                " WHERE " + ValdocDatabaseHandler.PARTNER_INSTRUMENT_PARTNERID + " = " + partnerId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PartnerInstrument partnerInstrument = new PartnerInstrument();
                partnerInstrument.setpInstrumentId(cursor.getInt(0));
                partnerInstrument.setPartnerId(cursor.getInt(1));
                partnerInstrument.setpInstrumentName(cursor.getString(2));
                partnerInstrument.setMake(cursor.getString(3));
                partnerInstrument.setModel(cursor.getString(4));
                partnerInstrument.setLastCalibrated(cursor.getString(5));
                partnerInstrument.setCalibrationDueDate(cursor.getString(6));
                partnerInstrument.setCurrentLocation(cursor.getString(7));
                partnerInstrument.setStatus(cursor.getString(8));
                partnerInstrument.setTestId(cursor.getInt(9));
                partnerInstrument.setCreationDate(cursor.getString(10));
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
                ahu.setCapacity(cursor.getDouble(3));
                ahu.setReturnAirCFM(cursor.getDouble(4));
                ahu.setExhaustAirCFM(cursor.getDouble(5));
                ahu.setBleedFilterType(cursor.getString(6));
                ahu.setBleedFilterEfficiency(cursor.getDouble(7));
                ahu.setBleedAirCFM(cursor.getDouble(8));
                ahu.setBleedFilterQty(cursor.getInt(9));
                ahu.setBleedFilterSize(cursor.getString(10));
                ahu.setFreshFilterType(cursor.getString(11));
                ahu.setFreshAirCFM(cursor.getDouble(12));
                ahu.setFreshFilterQty(cursor.getInt(13));
                ahu.setFreshFilterSize(cursor.getString(14));
                ahu.setAhuHEPAFilterQty(cursor.getInt(15));
                ahu.setHepaFilterEfficiency(cursor.getDouble(16));
                ahu.setHepaParticleSize(cursor.getString(17));
                ahu.setHepaFilterSpecification(cursor.getDouble(18));
                ahu.setCreationDate(cursor.getString(19));
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
    public ArrayList<Equipment> getEquipmentInfo() {
        ArrayList<Equipment> equipmentArrayList;
        equipmentArrayList = new ArrayList<Equipment>();
        String selectQuery = "SELECT * FROM " + EQUIPMENT_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Equipment equipment = new Equipment();
                equipment.setEquipmentId(cursor.getInt(0));
                equipment.setRoomId(cursor.getInt(1));
                equipment.setOccupancyState(cursor.getString(2));
                equipment.setEquipmentNo(cursor.getString(3));
                equipment.setEquipmentName(cursor.getString(4));
                equipment.setVelocity(cursor.getDouble(5));
                equipment.setTestReference(cursor.getString(6));
                equipment.setFilterQuantity(cursor.getInt(7));
                equipment.setEquipmentLoad(cursor.getInt(8));
                equipment.setCreationDate(cursor.getString(9));
                equipmentArrayList.add(equipment);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return equipmentArrayList;
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
    public JSONArray getTestReadingInfo() {
        JSONArray testReadingJsonArray;
        testReadingJsonArray = new JSONArray();
        String selectQuery = "SELECT * FROM " + TESTREADING_TABLE_NAME;
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
    public JSONArray getTestDetailsInfo() {
        JSONArray testDetailsJsonArray;
        testDetailsJsonArray = new JSONArray();
        String selectQuery = "SELECT * FROM " + TEST_DETAILS_TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(TEST_DETAILS_TEST_DETAIL_ID, cursor.getInt(0));
                    jsonObject.put(TEST_DETAILS_TESTNAME, cursor.getString(1));
                    jsonObject.put(TEST_DETAILS_CUSTOMER, cursor.getString(2));
                    jsonObject.put(TEST_DETAILS_RAWDATANO, cursor.getString(3));

                    jsonObject.put(TEST_DETAILS_DATEOFTEST, cursor.getInt(4));
                    jsonObject.put(TEST_DETAILS_INSTRUMENTUSED, cursor.getString(5));
                    jsonObject.put(TEST_DETAILS_INSTRUMENTNO, cursor.getString(6));
                    jsonObject.put(TEST_DETAILS_MAKE, cursor.getString(7));
                    jsonObject.put(TEST_DETAILS_MODEL, cursor.getString(8));
                    jsonObject.put(TEST_DETAILS_CALIBRATEDON, cursor.getString(9));
                    jsonObject.put(TEST_DETAILS_CALIBRATEDDUEON, cursor.getString(10));
                    jsonObject.put(TEST_DETAILS_TESTSPECIFICATION, cursor.getString(11));
                    jsonObject.put(TEST_DETAILS_TEST_TESTREFERENCE, cursor.getString(12));
                    jsonObject.put(TEST_DETAILS_OCCUPENCYSTATE, cursor.getString(13));
                    jsonObject.put(TEST_DETAILS_BLOCKNAME, cursor.getString(14));
                    jsonObject.put(TEST_DETAILS_TESTAREA, cursor.getString(15));

                    jsonObject.put(TEST_DETAILS_AHUNO, cursor.getString(16));
                    jsonObject.put(TEST_DETAILS_ROOMNO, cursor.getString(17));
                    jsonObject.put(TEST_DETAILS_ROOMNAME, cursor.getString(18));
                    jsonObject.put(TEST_DETAILS_EQUIPMENTNO, cursor.getString(19));
                    jsonObject.put(TEST_DETAILS_EQUIPMENTNAME, cursor.getString(20));

                    jsonObject.put(TEST_DETAILS_TESTERNAME, cursor.getString(21));
                    jsonObject.put(TEST_DETAILS_WITNESSNAME, cursor.getString(22));
                    jsonObject.put(TEST_DETAILS_PARTNERNAME, cursor.getString(23));
                    Log.d("getCertificateData", "test details=" + jsonObject.toString());
                    testDetailsJsonArray.put(jsonObject);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return testDetailsJsonArray;
    }

    // select data from test spesification value table
    public JSONArray getTestSpecificationValueInfo() {
        JSONArray testSpecificationValueJsonArray;
        testSpecificationValueJsonArray = new JSONArray();
        String selectQuery = "SELECT * FROM " + TESTSPECIFICATIONVALUE_TABLE_NAME;
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
        String selectQuery = " SELECT " + ROOM_ROOMNO + "," + ROOM_ROOMNAME + "," + ROOM_AREAID + " FROM " + ROOM_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.ROOM_ROOMID + " = " + roomId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String[] strings = new String[3];
        if (cursor.moveToFirst()) {
            do {
                strings[0] = cursor.getString(cursor.getColumnIndex(ROOM_ROOMNO));
                strings[1] = cursor.getString(cursor.getColumnIndex(ROOM_ROOMNAME));
                strings[2] = cursor.getString(cursor.getColumnIndex(ROOM_AREAID));
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
                " WHERE " + ValdocDatabaseHandler.AREA_AREAID + " = " + areaId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String strings = "";
        if (cursor.moveToFirst()) {
            do {
                strings = cursor.getString(cursor.getColumnIndex(AREA_AREANAME));
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return strings;
    }

    // select data from room filter table
    public ArrayList<RoomFilter> getFromRoomFilter(int roomId) {
        Log.d("valdoc", "ValdocDatabaseHelper :getFromRoomFilter:=" + roomId);
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
                roomFilter.setGrillArea(cursor.getDouble(9));
                roomFilter.setEffectiveGrillArea(cursor.getInt(10));
                roomFilter.setIsSupplyFilter(cursor.getInt(11));
                roomFilter.setCreationDate(cursor.getString(12));
                filterArrayList.add(roomFilter);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return filterArrayList;
    }

    // select data from grill table
    public ArrayList<HashMap<String, String>> getGrillAndSizeFromGrill(int roomId) {
        String selectQuery = " SELECT " + GRILL_GRILLCODE + "," + GRILL_EFFECTIVEAREA + " FROM " + GRILL_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.GRILL_ROOMID + " = " + roomId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "ValdocDatabaseHelper :grill code grill:=" + cursor.getCount());
        ArrayList<HashMap<String, String>> grillList = new ArrayList<HashMap<String, String>>();
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(GRILL_GRILLCODE));
                Log.d("valdoc", "ValdocDatabaseHelper :GRILL_GRILLCODE:=" + cursor.getString(cursor.getColumnIndex(GRILL_GRILLCODE)).toString() + "\n hashMap.get(GRILL_EFFECTIVEAREA) =" + cursor.getDouble(cursor.getColumnIndex(GRILL_EFFECTIVEAREA)));
                hashMap.put(GRILL_GRILLCODE, cursor.getString(cursor.getColumnIndex(GRILL_GRILLCODE)).toString());
                hashMap.put(GRILL_EFFECTIVEAREA, "" + cursor.getDouble(cursor.getColumnIndex(GRILL_EFFECTIVEAREA)));
                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + hashMap.get(GRILL_GRILLCODE) + "\n hashMap.get(GRILL_EFFECTIVEAREA) =" + hashMap.get(GRILL_EFFECTIVEAREA));
                grillList.add(hashMap);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return grillList;
    }

    // select data from equipment filter table
    public String[] getFilterFromEquipmentFilter(int equipmentId) {
        String selectQuery = " SELECT " + EQUIPMENTFILTER_FILTERCODE + " FROM " + EQUIPMENTFILTER_TABLE_NAME +
//        String selectQuery = " SELECT * FROM " + PARTNERUSER_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.EQUIPMENTFILTER_EQUIPMENTID + " = " + equipmentId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getCount());
        String[] strings = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                Log.d("valdoc", "ValdocDatabaseHelper :filter code equipment1:=" + cursor.getColumnIndex(EQUIPMENTFILTER_FILTERCODE));
                strings[i] = cursor.getString(cursor.getColumnIndex(EQUIPMENTFILTER_FILTERCODE));
                Log.d("valdoc", "ValdocDatabaseHelper :equipment1:=" + strings[i] + "i=" + i);
                i++;
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return strings;
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
                room.setAreaId(cursor.getInt(1));
                room.setAhuId(cursor.getInt(2));
                room.setRoomName(cursor.getString(3));
                room.setRoomNo(cursor.getString(4));
                room.setWidth(cursor.getDouble(5));
                room.setHeight(cursor.getDouble(6));
                room.setLength(cursor.getDouble(7));
                room.setArea(cursor.getDouble(8));
                room.setVolume(cursor.getDouble(9));

                room.setAcphNLT(cursor.getInt(10));
                room.setTestRef(cursor.getString(11));
                room.setIsoClause(cursor.getString(12));
                room.setOccupancyState(cursor.getString(13));
                room.setRoomSupplyAirflowCFM(cursor.getDouble(14));
                room.setAhuFlowCFM(cursor.getDouble(15));

                room.setRoomPressurePA(cursor.getDouble(16));
                room.setFreshAirCFM(cursor.getDouble(17));
                room.setBleedAirCFM(cursor.getDouble(18));
                room.setExhaustAirCFM(cursor.getDouble(19));
                room.setTemperature(cursor.getDouble(20));
                room.setRh(cursor.getDouble(21));

                room.setReturnAirCFM(cursor.getDouble(22));
                room.setSupplyAirGrillQTY(cursor.getInt(23));
                room.setReturnAirGrillQTY(cursor.getInt(24));
                room.setSupplyAirFilterQTY(cursor.getInt(25));
                room.setReturnAirFilterQTY(cursor.getInt(26));
                room.setRemarks(cursor.getString(27));
                room.setCreationDate(cursor.getString(28));
                roomArrayList.add(room);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return roomArrayList;
    }

    // select data from applicable test room table
    public ArrayList<ApplicableTestRoom> getApplicableTestRoomInfo(int roomId) {
        ArrayList<ApplicableTestRoom> applicableTestRoomArrayList;
        applicableTestRoomArrayList = new ArrayList<ApplicableTestRoom>();
        String selectQuery = "SELECT * FROM " + APLICABLE_TEST_ROOM_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.APLICABLE_TEST_ROOM_ROOMID + " = " + roomId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ApplicableTestRoom applicableTestRoom = new ApplicableTestRoom();
                applicableTestRoom.setAplicable_testId(cursor.getInt(0));
                applicableTestRoom.setRoomId(cursor.getInt(1));
                applicableTestRoom.setTestName(cursor.getString(2));
                applicableTestRoom.setPeriodicity(cursor.getString(3));
                applicableTestRoom.setLocation(cursor.getInt(4));
                applicableTestRoom.setNoOfCycle(cursor.getInt(5));
                applicableTestRoom.setCreationDate(cursor.getString(6));
                applicableTestRoomArrayList.add(applicableTestRoom);
            } while (cursor.moveToNext());
        } // return contact list return wordList; }
        return applicableTestRoomArrayList;
    }

    // select data from applicable test room table
    public ArrayList<ApplicableTestEquipment> getApplicableTestEquipmentInfo(int equipmentId) {
        ArrayList<ApplicableTestEquipment> applicableTestEquipmentArrayList;
        applicableTestEquipmentArrayList = new ArrayList<ApplicableTestEquipment>();
        String selectQuery = "SELECT * FROM " + APLICABLE_TEST_EQUIPMENT_TABLE_NAME +
                " WHERE " + ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_EQUIPMENTID + " = " + equipmentId;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ApplicableTestEquipment applicableTestEquipment = new ApplicableTestEquipment();
                applicableTestEquipment.setAplicable_testId(cursor.getInt(0));
                applicableTestEquipment.setEquipmentId(cursor.getInt(1));
                applicableTestEquipment.setTestName(cursor.getString(2));
                applicableTestEquipment.setPeriodicity(cursor.getString(3));
                applicableTestEquipment.setLocation(cursor.getInt(4));
                applicableTestEquipment.setNoOfCycle(cursor.getInt(5));
                applicableTestEquipment.setCreationDate(cursor.getString(6));
                applicableTestEquipmentArrayList.add(applicableTestEquipment);
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
