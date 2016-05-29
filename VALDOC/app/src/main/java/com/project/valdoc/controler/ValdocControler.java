package com.project.valdoc.controler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.AppUser;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.Area;
import com.project.valdoc.intity.ClientInstrument;
import com.project.valdoc.intity.ClientInstrumentTest;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.Grill;
import com.project.valdoc.intity.PartnerInstrument;
import com.project.valdoc.intity.Partners;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.task.HttpConnection;
import com.project.valdoc.task.HttpConnectionTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
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
    private ValdocDatabaseHandler mValdocDatabaseHandler;
    private String url = "http://valdoc.in:8080/valdoc/sync/getTableData?date=";  //2015-11-12
    private String postUrl = "http://valdoc.in:8080/valdoc/sync/postTableData";

    public void getHttpConectionforSync(Context context, String method) {
        mContext = context;
        controlerResponse = (ControlerResponse) context;
        mValdocDatabaseHandler = new ValdocDatabaseHandler(mContext);
        getConection(method);
    }

    public void httpPostSyncData(Context context, String method) {
        mContext = context;
        JSONObject jsonObject = getCertificateData();
        postConnection(method, jsonObject);
    }

    private JSONObject getCertificateData() {
        JSONObject jsonObject = null;
        JSONArray testDetailsJsonArray = null;
        JSONArray testReadingJsonArray = null;
        JSONArray testSpecificationValueJsonArray = null;
        JSONArray serviceReportJsonArray = null;
        JSONArray serviceReportDetailsJsonArray = null;
        jsonObject = new JSONObject();
        try {
            testDetailsJsonArray = mValdocDatabaseHandler.getTestDetailsInfo();
            testReadingJsonArray = mValdocDatabaseHandler.getTestReadingInfo();
            testSpecificationValueJsonArray = mValdocDatabaseHandler.getTestSpecificationValueInfo();
            serviceReportJsonArray = mValdocDatabaseHandler.getServiceReport();
            serviceReportDetailsJsonArray = mValdocDatabaseHandler.getServiceReportDetailsInfo();

            jsonObject.put("serviceReportDTOs", serviceReportJsonArray);
            jsonObject.put("serviceReportDetailDTOs", serviceReportDetailsJsonArray);
            jsonObject.put("testDetailDTOs", testDetailsJsonArray);
            jsonObject.put("testReadingDTOs", testReadingJsonArray);
            jsonObject.put("testSpecificValueDTOs", testSpecificationValueJsonArray);
        } catch (Exception e) {
            Log.d("getCertificateData", "certificate json exception=" + e.getMessage());
        }

        Log.d("getCertificateData", "certificate json=" + jsonObject.toString());
        return jsonObject;
    }


    private void postConnection(String method, JSONObject jsonDATA) {
        HttpConnectionTask httpConnectionTask = new HttpConnectionTask(mContext, method, jsonDATA);
//        lastSyncDate = sharedpreferences.getString("lastSyncDate", "");
        httpConnectionTask.execute(postUrl);
    }

    private void getConection(String method) {
        sharedpreferences = mContext.getSharedPreferences("valdoc", Context.MODE_PRIVATE);
        HttpConnectionTask httpConnectionTask = new HttpConnectionTask(mContext, method, new JSONObject());
        lastSyncDate = sharedpreferences.getString("lastSyncDate", "");
        httpConnectionTask.execute(url + lastSyncDate);
    }

    public void getAllDb(int statusCode, String resultData) {
        if (statusCode == HttpURLConnection.HTTP_OK) {
            HashMap<String, ArrayList> arrayListHashMap = parseResponse(resultData);
            controlerResponse.controlerResult(arrayListHashMap, "Data synked successfully");
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
            ArrayList clientInstrumentsTestList = clientInstrumentsTestData(jsonRootObject.optJSONArray("insertClientInstrumentTest"));
            arrayListHashMap.put(ValdocDatabaseHandler.CLIENT_INSTRUMENT_TEST_TABLE_NAME, clientInstrumentsList);

            //partner instrument data parsing
            ArrayList partnerInstrumentsList = partnerInstrumentsData(jsonRootObject.optJSONArray("partnerInstruments"));
            arrayListHashMap.put(ValdocDatabaseHandler.PARTNER_INSTRUMENT_TABLE_NAME, partnerInstrumentsList);

            //partner data parsing
            ArrayList partnerList = partnersData(jsonRootObject.optJSONArray("partners"));
            arrayListHashMap.put(ValdocDatabaseHandler.PARTNERS_TABLE_NAME, partnerList);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("lastSyncDate", jsonRootObject.optString("lastSyncDate"));
            editor.commit();

        } catch (Exception e) {

        }
        return arrayListHashMap;
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
                partners.setPartnerCode(jsonObject.optString("partnerCode").toString());
                partners.setStatus(jsonObject.optString("status").toString());
                partners.setApprovedSince(jsonObject.optInt("approvedSince"));
                partners.setAddress(jsonObject.optString("address").toString());
                partners.setDirectorName(jsonObject.optString("directorName").toString());
                partners.setDirectorEmail(jsonObject.optString("directorEmail").toString());
                partners.setDirectorCellNo(jsonObject.optString("directorCellNo").toString());
                partners.setRegEmail(jsonObject.optString("regEmail").toString());
                partners.setRegCellNo(jsonObject.optString("regCellNo").toString());
                partners.setServiceIncharge(jsonObject.optString("serviceIncharge").toString());
                partners.setEmail(jsonObject.optString("email").toString());
                partners.setCellNo(jsonObject.optString("cellNo").toString());
                partners.setCreationDate(jsonObject.optString("creationDate").toString());
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
                clientInstrument.setSerialNo(jsonObject.optString("serialNo","").toString());
                clientInstrument.setcInstrumentName(jsonObject.optString("cInstrumentName").toString());
                clientInstrument.setMake(jsonObject.optString("make").toString());
                clientInstrument.setModel(jsonObject.optString("model").toString());
                clientInstrument.setLastCalibrated(jsonObject.optString("lastCalibrationDate").toString());
                clientInstrument.setCalibrationDueDate(jsonObject.optString("calibrationDueDate").toString());
//                clientInstrument.setCurrentLocation(jsonObject.optString("currentLocation").toString());
                clientInstrument.setStatus(jsonObject.optString("status").toString());
//                clientInstrument.setTestId(jsonObject.optInt("testId"));
                clientInstrument.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
//                clientInstrument.setSamplingFlowRate(jsonObject.optString("samplingFlowRate").toString());
//                clientInstrument.setSamplingTime(jsonObject.optString("samplingTime").toString());
//                clientInstrument.setAerosolUsed(jsonObject.optString("aerosolUsed").toString());
//                clientInstrument.setAerosolGeneratorType(jsonObject.optString("aerosolGeneratorType").toString());
                Log.d("valdoc", "parse partner");
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
                clientInstrumentTest.setClientInstrumentTestId(jsonObject.optInt("clientInstrumentTestId"));
                clientInstrumentTest.setClientInstrumentId(jsonObject.optInt("clientInstrumentId"));
                clientInstrumentTest.setClientInstrumentTestCode(jsonObject.optString("clientInstrumentTestCode","").toString());
                clientInstrumentTest.setClientInstrumentTestName(jsonObject.optString("clientInstrumentTestName").toString());
                arrayList.add(clientInstrumentTest);
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
//                partnerInstrument.setCurrentLocation(jsonObject.optString("currentLocation").toString());
                partnerInstrument.setStatus(jsonObject.optString("status").toString());
                partnerInstrument.setCertFileName(jsonObject.optString("certFileName"));
                partnerInstrument.setRemarks(jsonObject.optString("remarks"));
//                partnerInstrument.setTestId(jsonObject.optInt("testId"));
                partnerInstrument.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
//                partnerInstrument.setSamplingFlowRate(jsonObject.optString("samplingFlowRate").toString());
//                partnerInstrument.setSamplingTime(jsonObject.optString("samplingTime").toString());
//                partnerInstrument.setAerosolUsed(jsonObject.optString("aerosolUsed").toString());
//                partnerInstrument.setAerosolGeneratorType(jsonObject.optString("aerosolGeneratorType").toString());
                Log.d("valdoc", "parse partner");
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
                applicableTestEquipment.setAplicable_testId(jsonObject.optInt("aplicableTestId"));
                applicableTestEquipment.setTestName(jsonObject.optString("testName").toString());
                applicableTestEquipment.setEquipmentId(jsonObject.optInt("equipmentId"));
                applicableTestEquipment.setPeriodicity(jsonObject.optString("periodicity").toString());
                applicableTestEquipment.setLocation(jsonObject.optInt("location"));
                applicableTestEquipment.setNoOfCycle(jsonObject.optInt("noOfCycle"));
                applicableTestEquipment.setLastUpdatedDate(jsonObject.optString("creationDate").toString());
                arrayList.add(applicableTestEquipment);
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
                equipmentFilter.setWidth(jsonObject.optDouble("width"));
                equipmentFilter.setLength(jsonObject.optDouble("length"));
                equipmentFilter.setGrillArea(jsonObject.optDouble("grillArea"));
//                equipmentFilter.setEffectiveGrillArea(jsonObject.optDouble("effectiveGrillArea"));
//                if (jsonObject.optBoolean("supplyFilter")) {
//                    equipmentFilter.setIsSupplyFilter(1);
//                } else {
//                    equipmentFilter.setIsSupplyFilter(0);
//                }
                equipmentFilter.setLastUpdatedDate(jsonObject.optString("creationDate").toString());
                arrayList.add(equipmentFilter);
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
                equipment.setRoomId(jsonObject.optInt("roomId"));
                equipment.setOccupancyState(jsonObject.optString("occupancyState").toString());
                equipment.setMinVelocity(jsonObject.optDouble("minVelocity"));
                equipment.setMaxVelocity(jsonObject.optDouble("maxVelocity"));
                equipment.setFilterQuantity(jsonObject.optInt("filterQuantity"));
                equipment.setEquipmentLoad(jsonObject.optDouble("equipmentLoad"));
                equipment.setEquipmentName(jsonObject.optString("equipmentName").toString());
                equipment.setTestReference(jsonObject.optString("testReference").toString());
                equipment.setEquipmentNo(jsonObject.optString("equipmentNo").toString());
                equipment.setIsoClause(jsonObject.optString("isoClause").toString());
                equipment.setCreationDate(jsonObject.optString("creationDate").toString());
//                Log.d("Avinash", "parsing equipment db minvalocity=" + equipment.getMinVelocity() + "equipment.getMaxVelocity()=" + equipment.getMaxVelocity());

                arrayList.add(equipment);
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
                applicableTestRoom.setAplicable_testId(jsonObject.optInt("aplicableTestId"));
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

                applicableTestRoom.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(applicableTestRoom);
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
//                roomFilter.setGrillArea(jsonObject.optDouble("grillArea"));
//                roomFilter.setEffectiveGrillArea(jsonObject.optDouble("effectiveGrillArea"));
                if (jsonObject.optBoolean("isSupplyFilter")) {
                    roomFilter.setIsSupplyFilter(1);
                } else {
                    roomFilter.setIsSupplyFilter(0);
                }

                roomFilter.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(roomFilter);
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
                room.setTemperature(jsonObject.optDouble("temperature"));
                room.setRh(jsonObject.optDouble("rh"));
                room.setReturnAirFlow(jsonObject.optDouble("returnAirFlow"));
//                room.setSupplyAirGrillQTY(jsonObject.optInt("supplyAirGrillQTY"));
//                room.setReturnAirGrillQTY(jsonObject.optInt("returnAirGrillQTY"));
//                room.setSupplyAirFilterQTY(jsonObject.optInt("supplyAirFilterQTY"));
//
//                room.setReturnAirFilterQTY(jsonObject.optInt("returnAirFilterQTY"));
                room.setRemarks(jsonObject.optString("remarks").toString());
                room.setLastUpdatedDate(jsonObject.optString("lastUpdatedDate").toString());
                arrayList.add(room);
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
                ahu.setCapacity(jsonObject.optDouble("capacity"));
                ahu.setReturnAirCFM(jsonObject.optDouble("returnAirCFM"));
                ahu.setExhaustAirCFM(jsonObject.optDouble("exhaustAirCFM"));
                ahu.setBleedFilterType(jsonObject.optString("bleedFilterType").toString());
                ahu.setBleedFilterEfficiency(jsonObject.optDouble("bleedFilterEfficiency"));
                ahu.setBleedAirCFM(jsonObject.optDouble("bleedAirCFM"));
                ahu.setBleedFilterQty(jsonObject.optInt("bleedFilterQty"));
//                ahu.setBleedFilterSize(jsonObject.optString("bleedFilterSize").toString());
                ahu.setFreshFilterType(jsonObject.optString("freshFilterType").toString());
                ahu.setFreshAirCFM(jsonObject.optDouble("freshAirCFM"));
                ahu.setFreshFilterQty(jsonObject.optInt("freshFilterQty"));
//                ahu.setFreshFilterSize(jsonObject.optString("freshFilterSize").toString());
//                ahu.setAhuHEPAFilterQty(jsonObject.optInt("ahuHEPAFilterQty"));
//                ahu.setHepaFilterEfficiency(jsonObject.optDouble("hepaFilterEfficiency"));
//                ahu.setHepaParticleSize(jsonObject.optString("hepaParticleSize").toString());
//                ahu.setHepaFilterEfficiency(jsonObject.optDouble("hepaFilterSpecification"));
//                ahu.setCreationDate(jsonObject.optString("creationDate").toString());
                arrayList.add(ahu);
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

                user.setApp_user_id(jsonObject.optInt("id"));
                user.setName(jsonObject.optString("name").toString());
                user.setPartnerId(jsonObject.optInt("partnerId"));
                if (jsonObject.optString("userType").equalsIgnoreCase("PARTNER")) {
                    user.setUserType("PARTNER");
                } else {
                    user.setUserType("CLIENT");
                }
                user.setEmail(jsonObject.optString("email").toString());
                user.setContact(jsonObject.optString("contact").toString());
                user.setDepartment(jsonObject.optString("department").toString());
                user.setIsActive(jsonObject.optInt("isActive"));
                user.setIsDeleted(jsonObject.optInt("isDeleted"));
                user.setPassword(passwordDecryption(jsonObject.optString("password").toString()));
                user.setRoleType(jsonObject.optString("roleType").toString());
                user.setPermissions(jsonObject.optString("permissions").toString());
                user.setLastUpdated(jsonObject.optString("lastUpdated").toString());
                arrayList.add(user);
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
