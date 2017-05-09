package com.project.valdoc.controler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.project.valdoc.db.ValdocDatabaseHandler;
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
import com.project.valdoc.intity.Partners;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.SamplingTime;
import com.project.valdoc.task.HttpConnection;
import com.project.valdoc.task.HttpConnectionTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Avinash on 3/14/2016.
 */
public class ValdocControler {
    Context mContext = null;
    ControlerResponse controlerResponse;
    SharedPreferences sharedpreferences;
    private String lastSyncDate;
    private String baseUrl;
    private ValdocDatabaseHandler mValdocDatabaseHandler;
    //    private String url = "http://valdoc.in:8080/valdoc/sync/getTableData?date=";  //2015-11-12
//    private String postUrl = "http://valdoc.in:8080/valdoc/sync/postTableData";
//    private String url = "http://valdoc.in:8080/valdoctest/sync/getTableData?date=";  //2015-11-12
//    private String postUrl = "http://valdoc.in:8080/valdoctest/sync/postTableData";
//    private String url = "http://192.169.143.229:8080/valdoctest/sync/getTableData?date=";
//    private String postUrl = "http://192.169.143.229:8080/valdoctest/sync/postTableData";
    private String url = "/sync/getTableData?date=";  //2015-11-12
    private String postUrl = "/sync/postTableData";

    public void getHttpConectionforSync(Context context, String method) {
        mContext = context;
        controlerResponse = (ControlerResponse) context;
        mValdocDatabaseHandler = new ValdocDatabaseHandler(mContext);
        getConection(method);
    }

    public void httpCertificatePostSyncData(Context context, String method, String testDetailsIdList) {
        mContext = context;
        mValdocDatabaseHandler = new ValdocDatabaseHandler(mContext);
        Log.d("Avinash", "testDetailsIdList=" + testDetailsIdList);
        JSONObject jsonObject = getCertificateData(testDetailsIdList);
        postConnection(method, jsonObject);
    }

    public void httpServiceReportPostSyncData(Context context, String method) {
        mContext = context;
        mValdocDatabaseHandler = new ValdocDatabaseHandler(mContext);
//        Log.d("Avinash","testDetailsIdList="+testDetailsIdList);
        JSONObject jsonObject = getServiceReportData();
        postConnection(method, jsonObject);
    }

    private JSONObject getCertificateData(String testDetailsIdList) {
        JSONObject jsonObject = new JSONObject();
        JSONArray testDetailsJsonArray = null;
        JSONArray testReadingJsonArray = null;
        JSONArray testSpecificationValueJsonArray = null;
        JSONArray serviceReportJsonArray = null;
        JSONArray serviceReportDetailsJsonArray = null;

//        jsonObject = new JSONObject();
        try {
            Log.d("Avinash", "testDetailsIdList2=" + testDetailsIdList);
            testDetailsJsonArray = mValdocDatabaseHandler.getTestDetailsInfo(testDetailsIdList);
            testReadingJsonArray = mValdocDatabaseHandler.getTestReadingInfo(testDetailsIdList);
            testSpecificationValueJsonArray = mValdocDatabaseHandler.getTestSpecificationValueInfo(testDetailsIdList);
            serviceReportJsonArray = new JSONArray();
            serviceReportDetailsJsonArray = new JSONArray();
            jsonObject.put("testDetailDTOs", testDetailsJsonArray);
            jsonObject.put("testReadingDTOs", testReadingJsonArray);
            jsonObject.put("testSpecificValueDTOs", testSpecificationValueJsonArray);
            jsonObject.put("serviceReportDTOs", serviceReportJsonArray);
            jsonObject.put("serviceReportDetailDTOs", serviceReportDetailsJsonArray);
        } catch (Exception e) {
            Log.d("getCertificateData", "certificate json exception=" + e.getMessage());
        }
        Log.d("getCertificateData", "certificate json=" + jsonObject.toString());
        return jsonObject;
    }

    public JSONObject getServiceReportData() {
        JSONObject jsonObject = null;
        JSONArray serviceReportJsonArray = null;
        JSONArray serviceReportDetailsJsonArray = null;
        JSONArray testDetailsJsonArray = null;
        JSONArray testReadingJsonArray = null;
        JSONArray testSpecificationValueJsonArray = null;
        jsonObject = new JSONObject();
        try {
            testDetailsJsonArray = new JSONArray();
            testReadingJsonArray = new JSONArray();
            testSpecificationValueJsonArray = new JSONArray();
            serviceReportJsonArray = mValdocDatabaseHandler.getServiceReport();
            serviceReportDetailsJsonArray = mValdocDatabaseHandler.getServiceReportDetailsInfo();
            jsonObject.put("testDetailDTOs", testDetailsJsonArray);
            jsonObject.put("testReadingDTOs", testReadingJsonArray);
            jsonObject.put("testSpecificValueDTOs", testSpecificationValueJsonArray);
            jsonObject.put("serviceReportDTOs", serviceReportJsonArray);
            jsonObject.put("serviceReportDetailDTOs", serviceReportDetailsJsonArray);
        } catch (Exception e) {
            Log.d("getCertificateData", "certificate json exception=" + e.getMessage());
        }
        Log.d("getCertificateData", "certificate json=" + jsonObject.toString());
        return jsonObject;
    }

    private void postConnection(String method, JSONObject jsonDATA) {
        Log.d("valdocControler", "post data json=" + jsonDATA.toString());
        HttpConnectionTask httpConnectionTask = new HttpConnectionTask(mContext, method, jsonDATA);
        baseUrl = sharedpreferences.getString("URL", "");
//        httpConnectionTask.execute(postUrl);
        httpConnectionTask.execute(baseUrl+postUrl);
    }

    private void getConection(String method) {
        sharedpreferences = mContext.getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        HttpConnectionTask httpConnectionTask = new HttpConnectionTask(mContext, method, new JSONObject());
        lastSyncDate = sharedpreferences.getString("lastSyncDate", "");
        baseUrl = sharedpreferences.getString("URL", "");
        String query = "";
        query = lastSyncDate.replace(" ", "%20");
//        httpConnectionTask.execute(url + query);
        httpConnectionTask.execute(baseUrl+url + query);
    }

    public void getAllDb(int statusCode, String resultData) {
        Log.d("valdocControler", "resultData=" + resultData);
        if (statusCode == HttpURLConnection.HTTP_OK) {
            Log.d("valdocControler", "resultData2=" + resultData);
            HashMap<String, ArrayList> arrayListHashMap = parseResponse(resultData);
            controlerResponse.controlerResult(arrayListHashMap, "Data synced successfully");
        }

    }

    private HashMap<String, ArrayList> parseResponse(String resultData) {
        JSONObject jsonRootObject = null;
        HashMap<String, ArrayList> arrayListHashMap = new HashMap<String, ArrayList>();
        try {
            jsonRootObject = new JSONObject(resultData);

            //Get the instance of JSONArray that contains JSONObjects
            //user data parsing
            ArrayList userArrayList = userData(jsonRootObject.optJSONArray("users"));
            arrayListHashMap.put(ValdocDatabaseHandler.USER_TABLE_NAME, userArrayList);

            //AHU data parsing
            ArrayList ahuArrayList = ahuData(jsonRootObject.optJSONArray("ahus"));
            arrayListHashMap.put(ValdocDatabaseHandler.AHU_TABLE_NAME, ahuArrayList);

            //Area data parsing
            ArrayList areasArrayList = areaData(jsonRootObject.optJSONArray("areas"));
            arrayListHashMap.put(ValdocDatabaseHandler.AREA_TABLE_NAME, areasArrayList);

            //room data parsing
            ArrayList roomArrayList = roomsData(jsonRootObject.optJSONArray("rooms"));
            arrayListHashMap.put(ValdocDatabaseHandler.ROOM_TABLE_NAME, roomArrayList);

            //roomFilters data parsing
            ArrayList roomFiltersArrayList = roomFiltersData(jsonRootObject.optJSONArray("roomFilters"));
            arrayListHashMap.put(ValdocDatabaseHandler.ROOMFILTER_TABLE_NAME, roomFiltersArrayList);

            //grills data parsing
            ArrayList grillsArrayList = grillsData(jsonRootObject.optJSONArray("grills"));
            arrayListHashMap.put(ValdocDatabaseHandler.GRILL_TABLE_NAME, grillsArrayList);

            //applicableTestRooms data parsing
            ArrayList applicableTestRoomsList = applicableTestRoomsData(jsonRootObject.optJSONArray("applicableTestRooms"));
            arrayListHashMap.put(ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TABLE_NAME, applicableTestRoomsList);

            //equipments data parsing
            ArrayList equipmentsList = equipmentsData(jsonRootObject.optJSONArray("equipments"));
            arrayListHashMap.put(ValdocDatabaseHandler.EQUIPMENT_TABLE_NAME, equipmentsList);

            //equipmentFilters data parsing
            ArrayList equipmentFiltersList = equipmentFiltersData(jsonRootObject.optJSONArray("equipmentFilters"));
            arrayListHashMap.put(ValdocDatabaseHandler.EQUIPMENTFILTER_TABLE_NAME, equipmentFiltersList);

            //equipmentFilters data parsing
            ArrayList applicableTestEquipmentsList = applicableTestEquipmentsData(jsonRootObject.optJSONArray("applicableTestEquipments"));
            arrayListHashMap.put(ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TABLE_NAME, applicableTestEquipmentsList);


            //client instrument data parsing
            ArrayList clientInstrumentsList = clientInstrumentsData(jsonRootObject.optJSONArray("clientInstruments"));
            arrayListHashMap.put(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TABLE_NAME, clientInstrumentsList);

            //client instrument Test data parsing
            ArrayList clientInstrumentsTestList = clientInstrumentsTestData(jsonRootObject.optJSONArray("clientInstrumentTests"));
            arrayListHashMap.put(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TEST_TABLE_NAME, clientInstrumentsTestList);

            //partner instrument data parsing
            ArrayList partnerInstrumentsList = partnerInstrumentsData(jsonRootObject.optJSONArray("partnerInstruments"));
            arrayListHashMap.put(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TABLE_NAME, partnerInstrumentsList);

            //partner data parsing
            ArrayList partnerList = partnersData(jsonRootObject.optJSONArray("partners"));
            arrayListHashMap.put(ValdocDatabaseHandler.PARTNERS_TABLE_NAME, partnerList);

            //ahu filter data parsing
            ArrayList ahuFilterList = ahuFilterData(jsonRootObject.optJSONArray("ahuFilters"));
            arrayListHashMap.put(ValdocDatabaseHandler.AHU_FILTER_TABLE_NAME, ahuFilterList);

            //applicableTestAhu data parsing
            ArrayList applicableTestAhuList = applicableTestAhuData(jsonRootObject.optJSONArray("applicableTestAhu"));
            arrayListHashMap.put(ValdocDatabaseHandler.APLICABLE_TEST_AHU_TABLE_NAME, applicableTestAhuList);

            //equipmentGrills data parsing
            ArrayList equipmentGrillsList = equipmentGrillsData(jsonRootObject.optJSONArray("equipmentGrills"));
            arrayListHashMap.put(ValdocDatabaseHandler.EQUIPMENTGRILL_TABLE_NAME, equipmentGrillsList);

            //partner instrument data parsing
            ArrayList partnerInstrumentsTestList = partnerInstrumentsTestData(jsonRootObject.optJSONArray("partnerInstrumentTests"));
            arrayListHashMap.put(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TEST_TABLE_NAME, partnerInstrumentsTestList);

            //isoparticle data parsing
            ArrayList isoParticleLimitsList = isoParticleLimitsData(jsonRootObject.optJSONArray("isoParticleLimits"));
            arrayListHashMap.put(ValdocDatabaseHandler.ISOPARTICLELIMITS_TABLE_NAME, isoParticleLimitsList);

            //samplingtime data parsing
            ArrayList samplingTime = samplingTimeData(jsonRootObject.optJSONArray("sampling_time"));
            arrayListHashMap.put(ValdocDatabaseHandler.SAMPLINGTIME_TABLE_NAME, samplingTime);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("lastSyncDate", jsonRootObject.optString("lastSyncDate"));
            editor.commit();
        } catch (Exception e) {
            Log.d("valdoc", "parse exception" + e.getMessage());
        }
        return arrayListHashMap;
    }

    private ArrayList samplingTimeData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            SamplingTime samplingTime = new SamplingTime();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                samplingTime.setSamplingTimeId(jsonObject.optInt("samplingTimeId"));
                samplingTime.setCleanroomClass(jsonObject.optString("cleanroomClass").toString());
                samplingTime.setLPM283(jsonObject.optString("lpm283").toString());
                samplingTime.setLPM50(jsonObject.optString("lpm50").toString());
                samplingTime.setLPM75(jsonObject.optString("lpm75").toString());
                samplingTime.setLPM100(jsonObject.optString("lpm100").toString());
                samplingTime.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                Log.d("valdoc", "parse isoParticleLimitsData");
                arrayList.add(samplingTime);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList isoParticleLimitsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            IsoParticleLimits isoParticleLimits = new IsoParticleLimits();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                isoParticleLimits.setLimitId(jsonObject.optInt("limitId"));
                isoParticleLimits.setParticleClass(jsonObject.optString("particleClass").toString());
                isoParticleLimits.setRestSmallParticleLimit(jsonObject.optString("restSmallParticleLimit").toString());
                isoParticleLimits.setRestLargeParticleLimit(jsonObject.optString("restLargeParticleLimit").toString());
                isoParticleLimits.setOperationSmallParticleLimit(jsonObject.optString("operationSmallParticleLimit").toString());
                isoParticleLimits.setOperationLargeParticleLimit(jsonObject.optString("operationLargeParticleLimit").toString());
                isoParticleLimits.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                Log.d("6", "parse isoParticleLimitsData");
                arrayList.add(isoParticleLimits);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList partnerInstrumentsTestData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            PartnerInstrumentTest partnerInstrumentTest = new PartnerInstrumentTest();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                partnerInstrumentTest.setPartnerInstrumentTestId(jsonObject.optInt("partner_instrument_test_id"));
                partnerInstrumentTest.setPartnerInstrumentId(jsonObject.optInt("partner_instrument_id"));
                partnerInstrumentTest.setPartnerInstrumentTestCode(jsonObject.optString("partner_instrument_test_code").toString());
                partnerInstrumentTest.setPartnerInstrumentTestName(jsonObject.optString("partner_instrument_test_name").toString());
                partnerInstrumentTest.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                partnerInstrumentTest.setRange(jsonObject.optString("range").toString());
                Log.d("valdoc", "parse partnerInstrumentsTestData");
                arrayList.add(partnerInstrumentTest);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList equipmentGrillsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            EquipmentGrill equipmentGrill = new EquipmentGrill();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                equipmentGrill.setGrillId(jsonObject.optInt("grillId"));
                equipmentGrill.setGrillCode(jsonObject.optString("grillCode").toString());
                equipmentGrill.setEquipmentId(jsonObject.optInt("equipmentId"));
                equipmentGrill.setLength(jsonObject.optDouble("length"));
                equipmentGrill.setWidth(jsonObject.optDouble("width"));
                equipmentGrill.setArea(jsonObject.optDouble("area"));
                equipmentGrill.setEffectiveArea(jsonObject.optDouble("effectiveArea"));
                equipmentGrill.setRemarks(jsonObject.optString("remarks").toString());
                equipmentGrill.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                Log.d("valdoc", "parse equipmentGrillsData");
                arrayList.add(equipmentGrill);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList applicableTestAhuData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            ApplicableTestAhu applicableTestAhu = new ApplicableTestAhu();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                applicableTestAhu.setAplicable_testId(jsonObject.optInt("aplicableTestId"));
                applicableTestAhu.setAhuId(jsonObject.optInt("ahuId"));
                applicableTestAhu.setTestName(jsonObject.optString("testName").toString());
                applicableTestAhu.setTestCode(jsonObject.optString("testCode").toString());
                applicableTestAhu.setTestFormat(jsonObject.optString("testFormat").toString());
                applicableTestAhu.setTestItem(jsonObject.optString("testItem").toString());
                applicableTestAhu.setTestSpecification(jsonObject.optString("testSpecification").toString());
                applicableTestAhu.setOccupencyState(jsonObject.optString("occupencyState").toString());
                applicableTestAhu.setTestReference(jsonObject.optString("testReference").toString());

                applicableTestAhu.setTestProp(jsonObject.optString("testProp").toString());
                applicableTestAhu.setPeriodicity(jsonObject.optString("periodicity").toString());
                applicableTestAhu.setLocation(jsonObject.optInt("location"));
                applicableTestAhu.setNoOfCycle(jsonObject.optInt("noOfCycle"));
                applicableTestAhu.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                Log.d("valdoc", "parse applicableTestAhu");
                arrayList.add(applicableTestAhu);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList ahuFilterData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            AhuFilter ahuFilter = new AhuFilter();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ahuFilter.setFilterId(jsonObject.optInt("filterId"));
                ahuFilter.setAhuId(jsonObject.optInt("ahuId"));
                ahuFilter.setFilterCategory(jsonObject.optString("filterCategory").toString());
                ahuFilter.setFilterCode(jsonObject.optString("filterCode").toString());
                ahuFilter.setWidth(jsonObject.optDouble("width"));
                ahuFilter.setLength(jsonObject.optDouble("length"));
                ahuFilter.setDepthArea(jsonObject.optDouble("depthArea"));
                ahuFilter.setArea(jsonObject.optDouble("area"));
                ahuFilter.setEffectiveArea(jsonObject.optDouble("effectiveArea"));
                ahuFilter.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate"));
                Log.d("valdoc", "parse ahuFilter");
                arrayList.add(ahuFilter);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList partnersData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            Partners partners = new Partners();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                partners.setPartnerId(jsonObject.optInt("partner_id"));
                partners.setName(jsonObject.optString("name"));
                partners.setPartnerCode(jsonObject.optString("partner_code").toString());
                partners.setStatus(jsonObject.optString("status").toString());
                partners.setApprovedSince(jsonObject.optInt("approved_since"));
                partners.setAddress(jsonObject.optString("address").toString());
                partners.setDirectorName(jsonObject.optString("director_name").toString());
                partners.setDirectorEmail(jsonObject.optString("director_email").toString());
                partners.setDirectorCellNo(jsonObject.optString("director_cell_no").toString());
                partners.setRegEmail(jsonObject.optString("reg_email").toString());
                partners.setRegCellNo(jsonObject.optString("reg_cell_no").toString());
                partners.setServiceIncharge(jsonObject.optString("service_incharge").toString());
                partners.setEmail(jsonObject.optString("email").toString());
                partners.setCellNo(jsonObject.optString("cell_no").toString());
                partners.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                Log.d("valdoc", "parse partner");
                arrayList.add(partners);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList clientInstrumentsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            ClientInstrument clientInstrument = new ClientInstrument();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                clientInstrument.setcInstrumentId(jsonObject.optInt("cInstrumentId"));
                clientInstrument.setInstrumentId(jsonObject.optString("instrumentId", ""));
                clientInstrument.setSerialNo(jsonObject.optString("serialNo", "").toString());
                clientInstrument.setcInstrumentName(jsonObject.optString("cInstrumentName").toString());
                clientInstrument.setMake(jsonObject.optString("make").toString());
                clientInstrument.setModel(jsonObject.optString("model").toString());
                clientInstrument.setLastCalibrated(jsonObject.optString("lastCalibrationDate").toString());
                clientInstrument.setCalibrationDueDate(jsonObject.optString("calibrationDueDate").toString());
                clientInstrument.setCertFileName(jsonObject.optString("certFileName").toString());
                clientInstrument.setStatus(jsonObject.optString("status").toString());
                clientInstrument.setRemarks(jsonObject.optString("remarks").toString());
                clientInstrument.setRange(jsonObject.optString("range"));
                clientInstrument.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
//                clientInstrument.setSamplingFlowRate(jsonObject.optString("samplingFlowRate").toString());
//                clientInstrument.setSamplingTime(jsonObject.optString("samplingTime").toString());
//                clientInstrument.setAerosolUsed(jsonObject.optString("aerosolUsed").toString());
//                clientInstrument.setAerosolGeneratorType(jsonObject.optString("aerosolGeneratorType").toString());
                Log.d("valdoc", "parse clientInstrument");
                arrayList.add(clientInstrument);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList clientInstrumentsTestData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            ClientInstrumentTest clientInstrumentTest = new ClientInstrumentTest();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                clientInstrumentTest.setClientInstrumentTestId(jsonObject.optInt("client_instrument_test_id"));
                clientInstrumentTest.setClientInstrumentId(jsonObject.optInt("client_instrument_id"));
                clientInstrumentTest.setClientInstrumentTestCode(jsonObject.optString("client_instrument_test_code", "").toString());
                clientInstrumentTest.setClientInstrumentTestName(jsonObject.optString("client_instrument_test_name").toString());
                clientInstrumentTest.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(clientInstrumentTest);
                Log.d("valdoc", "parse clientInstrumentsTestData");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList partnerInstrumentsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            PartnerInstrument partnerInstrument = new PartnerInstrument();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                partnerInstrument.setpInstrumentId(jsonObject.optInt("pInstrumentId"));
                partnerInstrument.setPartnerId(jsonObject.optInt("partnerId"));
                partnerInstrument.setSerialNo(jsonObject.optString("serialNo").toString());
                partnerInstrument.setpInstrumentName(jsonObject.optString("pInstrumentName").toString());
                partnerInstrument.setMake(jsonObject.optString("make").toString());
                partnerInstrument.setModel(jsonObject.optString("model").toString());
                partnerInstrument.setLastCalibrationDate(jsonObject.optString("lastCalibrationDate").toString());
                partnerInstrument.setCalibrationDueDate(jsonObject.optString("calibrationDueDate").toString());
                partnerInstrument.setRange(jsonObject.optString("range").toString());
                partnerInstrument.setStatus(jsonObject.optString("status").toString());
                partnerInstrument.setCertFileName(jsonObject.optString("certFileName"));
                partnerInstrument.setRemarks(jsonObject.optString("remarks"));
//                partnerInstrument.setTestId(jsonObject.optInt("testId"));
                partnerInstrument.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
//                partnerInstrument.setSamplingFlowRate(jsonObject.optString("samplingFlowRate").toString());
//                partnerInstrument.setSamplingTime(jsonObject.optString("samplingTime").toString());
//                partnerInstrument.setAerosolUsed(jsonObject.optString("aerosolUsed").toString());
//                partnerInstrument.setAerosolGeneratorType(jsonObject.optString("aerosolGeneratorType").toString());
                Log.d("valdoc", "parse partnerInstrument");
                arrayList.add(partnerInstrument);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList applicableTestEquipmentsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            ApplicableTestEquipment applicableTestEquipment = new ApplicableTestEquipment();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                applicableTestEquipment.setAplicable_testId(jsonObject.optInt("aplicable_testId"));
                applicableTestEquipment.setTestName(jsonObject.optString("testName").toString());
                applicableTestEquipment.setEquipmentId(jsonObject.optInt("equipmentId"));

                applicableTestEquipment.setTestCode(jsonObject.optString("testCode").toString());
                applicableTestEquipment.setTestFormat(jsonObject.optString("testFormat"));
                applicableTestEquipment.setTestSpecification(jsonObject.optString("testSpecification"));
                applicableTestEquipment.setOccupencyState(jsonObject.optString("occupencyState").toString());
                applicableTestEquipment.setTestReference(jsonObject.optString("testReference").toString());
                applicableTestEquipment.setTestProp(jsonObject.optString("testProp"));

                applicableTestEquipment.setPeriodicity(jsonObject.optString("periodicity").toString());
                applicableTestEquipment.setLocation(jsonObject.optInt("location"));
                applicableTestEquipment.setNoOfCycle(jsonObject.optInt("noOfCycle"));
                applicableTestEquipment.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(applicableTestEquipment);
                Log.d("valdoc", "parse applicableTestEquipmentsData");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList equipmentFiltersData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            EquipmentFilter equipmentFilter = new EquipmentFilter();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                equipmentFilter.setFilterId(jsonObject.optInt("filterId"));
                equipmentFilter.setFilterCode(jsonObject.optString("filterCode").toString());
                equipmentFilter.setEquipmentId(jsonObject.optInt("equipmentId"));
                equipmentFilter.setFilterType(jsonObject.optString("filterType").toString());
                equipmentFilter.setEfficiency(jsonObject.optDouble("efficiency"));
                equipmentFilter.setParticleSize(jsonObject.optDouble("particleSize"));
                equipmentFilter.setSpecificationLeak(jsonObject.optDouble("specificationLeak"));
                equipmentFilter.setWidth(jsonObject.optDouble("width"));
                equipmentFilter.setLength(jsonObject.optDouble("length"));
                equipmentFilter.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(equipmentFilter);
                Log.d("valdoc", "parse equipmentFiltersData");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList equipmentsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            Equipment equipment = new Equipment();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                equipment.setEquipmentId(jsonObject.optInt("equipmentId"));
                equipment.setEquipmentNo(jsonObject.optString("equipmentNo").toString());
                equipment.setEquipmentName(jsonObject.optString("equipmentName").toString());
                equipment.setRoomId(jsonObject.optInt("roomId"));
//                equipment.setOccupancyState(jsonObject.optString("occupancyState").toString());
                equipment.setMinVelocity(jsonObject.optDouble("minVelocity"));
                equipment.setMaxVelocity(jsonObject.optDouble("maxVelocity"));
                equipment.setSupplyAirflow(jsonObject.optInt("supplyAirflow"));
                equipment.setEquipmentPressure(jsonObject.optInt("equipmentPressure"));
                equipment.setExhustAirflow(jsonObject.optInt("exhustAirflow"));
                equipment.setRemarks(jsonObject.optString("remarks").toString());
                equipment.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
//                Log.d("Avinash", "parsing equipment db minvalocity=" + equipment.getMinVelocity() + "equipment.getMaxVelocity()=" + equipment.getMaxVelocity());
                arrayList.add(equipment);
                Log.d("valdoc", "parse equipmentsData");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList applicableTestRoomsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            ApplicableTestRoom applicableTestRoom = new ApplicableTestRoom();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                applicableTestRoom.setAplicable_testId(jsonObject.optInt("aplicable_testId"));
                applicableTestRoom.setRoomId(jsonObject.optInt("roomId"));
                applicableTestRoom.setTestName(jsonObject.optString("testName").toString());

                applicableTestRoom.setTestCode(jsonObject.optString("testCode").toString());
                applicableTestRoom.setTestFormat(jsonObject.optString("testFormat"));
                applicableTestRoom.setTestSpecification(jsonObject.optString("testSpecification"));
                applicableTestRoom.setOccupencyState(jsonObject.optString("occupencyState").toString());
                applicableTestRoom.setTestReference(jsonObject.optString("testReference"));
                applicableTestRoom.setTestProp(jsonObject.optString("testProp"));

                applicableTestRoom.setPeriodicity(jsonObject.optString("periodicity").toString());
                applicableTestRoom.setLocation(jsonObject.optInt("location"));
                applicableTestRoom.setNoOfCycle(jsonObject.optInt("noOfCycle"));
                applicableTestRoom.setDiffAVinFilter(jsonObject.optInt("diffAVinFilter"));
                applicableTestRoom.setDiffAVbetweenFilter(jsonObject.optInt("diffAVbetweenFilter"));

                applicableTestRoom.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(applicableTestRoom);
                Log.d("valdoc", "parse applicableTestRoomsData");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList grillsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            Grill grill = new Grill();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                grill.setGrillId(jsonObject.optInt("grillId"));
                grill.setGrillCode(jsonObject.optString("grillCode").toString());
                grill.setRoomId(jsonObject.optInt("roomId"));
                grill.setWidh(jsonObject.optDouble("width"));
                grill.setLength(jsonObject.optDouble("length"));
                grill.setGrillArea(jsonObject.optDouble("area"));
                grill.setEffectiveArea(jsonObject.optDouble("effectiveArea"));

                if (jsonObject.optBoolean("supplyGrill")) {
                    grill.setIsSupplyGrill(1);
                } else {
                    grill.setIsSupplyGrill(0);
                }
//                if (jsonObject.optBoolean("additionalDetail")) {
//                    grill.setAdditionalDetail(1);
//                } else {
//                    grill.setAdditionalDetail(0);
//                }
                grill.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(grill);
                Log.d("valdoc", "parse grillsData");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList roomFiltersData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            RoomFilter roomFilter = new RoomFilter();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                roomFilter.setFilterId(jsonObject.optInt("filterId"));
                roomFilter.setFilterCode(jsonObject.optString("filterCode").toString());
                roomFilter.setRoomId(jsonObject.optInt("roomId"));
                roomFilter.setFilterType(jsonObject.optString("filterType").toString());
                roomFilter.setEfficiency(jsonObject.optDouble("efficiency"));
                roomFilter.setParticleSize(jsonObject.optString("particleSize").toString());
                roomFilter.setSpecification(jsonObject.optDouble("specification"));
                roomFilter.setWidth(jsonObject.optDouble("width"));
                roomFilter.setLength(jsonObject.optDouble("length"));
                roomFilter.setArea(jsonObject.optDouble("area"));
                roomFilter.setEffectiveFilterArea(jsonObject.optDouble("effectiveFilterArea"));
                roomFilter.setFilterLocation(jsonObject.optString("filterLocation"));

                if (jsonObject.optBoolean("supplyFilter")) {
                    roomFilter.setIsSupplyFilter(1);
                } else {
                    roomFilter.setIsSupplyFilter(0);
                }

                roomFilter.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(roomFilter);
                Log.d("valdoc", "parse roomFiltersData");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }


    private ArrayList roomsData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            Room room = new Room();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                room.setRoomId(jsonObject.optInt("roomId"));
                room.setAreaId(jsonObject.optInt("areaId"));
                room.setAhuId(jsonObject.optInt("ahuId"));
                room.setRoomName(jsonObject.optString("roomName").toString());
                room.setRoomNo(jsonObject.optString("roomNo").toString());
                room.setWidth(jsonObject.optDouble("width"));
                room.setHeight(jsonObject.optDouble("height"));
                room.setLength(jsonObject.optDouble("length"));
                room.setArea(jsonObject.optDouble("area"));
                room.setVolume(jsonObject.optDouble("volume"));
                room.setAcph(jsonObject.optInt("acph"));
                room.setTestRef(jsonObject.optString("testRef"));
                room.setIsoClause(jsonObject.optString("isoClause").toString());
                room.setOccupancyState(jsonObject.optString("occupancyState").toString());
                room.setSupplyAirflow(jsonObject.optDouble("supplyAirflow"));
                room.setAhuFlowCFM(jsonObject.optDouble("ahuFlowCFM"));
                room.setRoomPressure(jsonObject.optDouble("roomPressure"));

                room.setFreshAirCFM(jsonObject.optDouble("freshAirCFM"));
                room.setBleedAirCFM(jsonObject.optDouble("bleedAirCFM"));
                room.setExhaustAirFlow(jsonObject.optDouble("exhaustAirFlow"));
                room.setMaxTemperature(jsonObject.optDouble("maxTemperature"));
                room.setMaxRH(jsonObject.optDouble("maxRH"));
                room.setMinTemperature(jsonObject.optDouble("minTemperature"));
                room.setMinRH(jsonObject.optDouble("minRH"));
                room.setReturnAirFlow(jsonObject.optDouble("returnAirFlow"));
//                room.setSupplyAirGrillQTY(jsonObject.optInt("supplyAirGrillQTY"));
//                room.setReturnAirGrillQTY(jsonObject.optInt("returnAirGrillQTY"));
//                room.setSupplyAirFilterQTY(jsonObject.optInt("supplyAirFilterQTY"));
//
//                room.setReturnAirFilterQTY(jsonObject.optInt("returnAirFilterQTY"));
                room.setRemarks(jsonObject.optString("remarks").toString());
                room.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(room);
                Log.d("valdoc", "parse room");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList areaData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            Area area = new Area();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                area.setAreaId(jsonObject.optInt("areaId"));
                area.setPlantId(jsonObject.optInt("plantId"));
                area.setAreaName(jsonObject.optString("areaName").toString());
                area.setAdditionalDetails(jsonObject.optString("additionalDetails").toString());
                area.setlastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(area);
                Log.d("valdoc", "parse area");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList ahuData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            Ahu ahu = new Ahu();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ahu.setAhuId(jsonObject.optInt("ahuId"));
                ahu.setAhuNo(jsonObject.optString("ahuNo").toString());
                ahu.setAhuType(jsonObject.optString("ahuType").toString());
                ahu.setAreId(jsonObject.optString("areaId").toString());
                ahu.setCapacity(jsonObject.optDouble("capacity"));
                ahu.setReturnAirCFM(jsonObject.optDouble("returnAirCFM"));
                ahu.setExhaustAirCFM(jsonObject.optDouble("exhaustAirCFM"));
                ahu.setBleedFilterType(jsonObject.optString("bleedFilterType").toString());
                ahu.setBleedFilterEfficiency(jsonObject.optDouble("bleedFilterEfficiency"));
                ahu.setBleedAirCFM(jsonObject.optDouble("bleedAirCFM"));
                ahu.setBleedFilterQty(jsonObject.optInt("bleedFilterQty"));
                ahu.setBleedFilterLeak(jsonObject.optDouble("bleedFilterLeak"));
                ahu.setFreshFilterType(jsonObject.optString("freshFilterType").toString());
                ahu.setFreshAirCFM(jsonObject.optDouble("freshAirCFM"));
                ahu.setFreshFilterQty(jsonObject.optInt("freshFilterQty"));

                ahu.setFreshFilterEfficiency(jsonObject.optString("freshFilterEfficiency").toString());
                ahu.setFinalFilterAirFlow(jsonObject.optInt("finalFilterAirFlow"));
                ahu.setFinalFilterQty(jsonObject.optInt("finalFilterQty"));
                ahu.setFinalFilterType(jsonObject.optString("finalFilterType").toString());
                ahu.setFinalFilterEfficiency(jsonObject.optString("finalFilterEfficiency").toString());
                ahu.setFinalFilterLeak(jsonObject.optString("finalFilterLeak").toString());
                ahu.setRemarks(jsonObject.optString("remarks").toString());
                ahu.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                ahu.setFreshParticleSize(jsonObject.optDouble("freshParticleSize"));
                ahu.setBleedParticleSize(jsonObject.optDouble("bleedParticleSize"));
                ahu.setFinalParticleSize(jsonObject.optDouble("finalParticleSize"));
                ahu.setFreshAirflowTolerance(jsonObject.optDouble("freshAirflowTolerance"));
                ahu.setBleedAirflowTolerance(jsonObject.optDouble("bleedAirflowTolerance"));
                ahu.setFinalAirflowTolerance(jsonObject.optDouble("finalAirflowTolerance"));
                arrayList.add(ahu);
                Log.d("valdoc", "parse ahu");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    private ArrayList userData(JSONArray jsonArray) {
        ArrayList arrayList = new ArrayList();
        //Iterate the jsonArray and print the info of JSONObjects
        for (int i = 0; i < jsonArray.length(); i++) {
            AppUser user = new AppUser();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                user.setApp_user_id(jsonObject.optInt("app_user_id"));
                user.setName(jsonObject.optString("fName").toString());
                user.setPartnerId(jsonObject.optInt("partner_id"));
                if (jsonObject.optString("user_type").equalsIgnoreCase("PARTNER")) {
                    user.setUserType("PARTNER");
                } else {
                    user.setUserType("CLIENT");
                }
                user.setEmail(jsonObject.optString("email").toString());
                user.setContact(jsonObject.optString("contact").toString());
                user.setDepartment(jsonObject.optString("department").toString());
                user.setIsActive(jsonObject.optInt("is_active"));
                user.setIsDeleted(jsonObject.optInt("is_deleted"));
                user.setPassword(passwordDecryption(jsonObject.optString("password").toString()));
                user.setRoleType(jsonObject.optString("role_type").toString());
                user.setPermissions(jsonObject.optString("permissions").toString());
                user.setLastUpdated(jsonObject.optString("last_updated").toString());
                arrayList.add(user);
                Log.d("valdoc", "parse user");
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    public String passwordDecryption(String password) {
        String text = "";
        if (!"".equalsIgnoreCase(password)) {
            try {
                byte[] data = Base64.decode(password, Base64.DEFAULT);
                text = new String(data, "UTF-8");
                Log.d("Avinash", "decrypted password" + text);
            } catch (Exception e) {

            }
        } else {
            text = password;
        }

        return text;
    }

    public interface ControlerResponse {
        public void controlerResult(HashMap<String, ArrayList> arrayListHashMap, String message);
    }
}
