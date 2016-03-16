package com.project.valdoc.controler;

import android.content.Context;

import com.project.valdoc.db.ValdocDatabaseHandler;
import com.project.valdoc.intity.Ahu;
import com.project.valdoc.intity.ApplicableTestEquipment;
import com.project.valdoc.intity.ApplicableTestRoom;
import com.project.valdoc.intity.Area;
import com.project.valdoc.intity.Equipment;
import com.project.valdoc.intity.EquipmentFilter;
import com.project.valdoc.intity.Grill;
import com.project.valdoc.intity.Room;
import com.project.valdoc.intity.RoomFilter;
import com.project.valdoc.intity.User;
import com.project.valdoc.task.HttpConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Avinash on 3/14/2016.
 */
public class ValdocControler implements HttpConnection.HttpUrlConnectionResponce {
    Context mContext = null;
    ControlerResponse controlerResponse;

    public void getAllDb(Context context) {
        mContext = context;
        controlerResponse = (ControlerResponse) context;
    }

    @Override
    public void httpResponceResult(String resultData, int statusCode) {
        if (statusCode == HttpURLConnection.HTTP_OK) {
            HashMap<String, ArrayList> arrayListHashMap = praseResponse(resultData);
            controlerResponse.controlerResult(arrayListHashMap, statusCode);
        }

    }

    private HashMap<String, ArrayList> praseResponse(String resultData) {
        JSONObject jsonRootObject = null;
        HashMap<String, ArrayList> arrayListHashMap = new HashMap<String, ArrayList>();
        try {
            jsonRootObject = new JSONObject(resultData);

            //Get the instance of JSONArray that contains JSONObjects
            //user data parsing
            ArrayList userArrayList=userData(jsonRootObject.optJSONArray("users"));
            arrayListHashMap.put(ValdocDatabaseHandler.USER_TABLE_NAME,userArrayList);

            //AHU data parsing
            ArrayList ahuArrayList=ahuData(jsonRootObject.optJSONArray("ahus"));
            arrayListHashMap.put(ValdocDatabaseHandler.AREA_TABLE_NAME,ahuArrayList);

            //Area data parsing
            ArrayList areasArrayList=roomsData(jsonRootObject.optJSONArray("rooms"));
            arrayListHashMap.put(ValdocDatabaseHandler.ROOM_TABLE_NAME,areasArrayList);

            //roomFilters data parsing
            ArrayList roomFiltersArrayList=roomFiltersData(jsonRootObject.optJSONArray("roomFilters"));
            arrayListHashMap.put(ValdocDatabaseHandler.ROOMFILTER_TABLE_NAME,roomFiltersArrayList);

            //grills data parsing
            ArrayList grillsArrayList=grillsData(jsonRootObject.optJSONArray("grills"));
            arrayListHashMap.put(ValdocDatabaseHandler.GRILL_TABLE_NAME,roomFiltersArrayList);

            //applicableTestRooms data parsing
            ArrayList applicableTestRoomsList=applicableTestRoomsData(jsonRootObject.optJSONArray("applicableTestRooms"));
            arrayListHashMap.put(ValdocDatabaseHandler.APLICABLE_TEST_ROOM_TABLE_NAME,applicableTestRoomsList);
            //equipments data parsing
            ArrayList equipmentsList=equipmentsData(jsonRootObject.optJSONArray("equipments"));
            arrayListHashMap.put(ValdocDatabaseHandler.EQUIPMENT_TABLE_NAME,equipmentsList);

            //equipmentFilters data parsing
            ArrayList equipmentFiltersList=equipmentFiltersData(jsonRootObject.optJSONArray("equipmentFilters"));
            arrayListHashMap.put(ValdocDatabaseHandler.EQUIPMENTFILTER_TABLE_NAME,equipmentFiltersList);

            //equipmentFilters data parsing
            ArrayList applicableTestEquipmentsList=applicableTestEquipmentsData(jsonRootObject.optJSONArray("applicableTestEquipments"));
            arrayListHashMap.put(ValdocDatabaseHandler.APLICABLE_TEST_EQUIPMENT_TABLE_NAME,applicableTestEquipmentsList);



        } catch (Exception e) {

        }
        return arrayListHashMap;
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
                applicableTestEquipment.setEquipmentId(jsonObject.optInt("equipment"));
                applicableTestEquipment.setPeriodicity(jsonObject.optString("periodicity").toString());
                applicableTestEquipment.setLocation(jsonObject.optInt("location"));
                applicableTestEquipment.setNoOfCycle(jsonObject.optInt("noOfCycle"));
                applicableTestEquipment.setCreationDate(jsonObject.optString("createdDate").toString());
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
                equipmentFilter.setEquipmentId(jsonObject.optInt("equipment"));
                equipmentFilter.setWidth(jsonObject.optDouble("width"));
                equipmentFilter.setLength(jsonObject.optDouble("length"));
                equipmentFilter.setGrillArea(jsonObject.optDouble("grillArea"));
                equipmentFilter.setEffectiveGrillArea(jsonObject.optDouble("effectiveGrillArea"));
                if(jsonObject.optBoolean("supplyFilter")) {
                    equipmentFilter.setIsSupplyFilter(1);
                }else{
                    equipmentFilter.setIsSupplyFilter(0);
                }
                equipmentFilter.setCreationDate(jsonObject.optString("createdDate").toString());
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
                equipment.setRoomId(jsonObject.optInt("room"));
                equipment.setOccupancyState(jsonObject.optString("occupancyState").toString());
                equipment.setVelocity(jsonObject.optDouble("velocity"));
                equipment.setFilterQuantity(jsonObject.optInt("filterQuantity"));
                equipment.setEquipmentLoad(jsonObject.optDouble("equipmentLoad"));
                equipment.setEquipmentName(jsonObject.optString("equipmentName").toString());
                equipment.setTestReference(jsonObject.optString("testReference").toString());
                equipment.setEquipmentNo(jsonObject.optString("equipmentNo").toString());

                equipment.setCreationDate(jsonObject.optString("createdDate").toString());
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
                applicableTestRoom.setAplicable_testId(jsonObject.optInt("aplicable_testId"));
                applicableTestRoom.setRoomId(jsonObject.optInt("room"));
                applicableTestRoom.setTestName(jsonObject.optString("testName").toString());

                applicableTestRoom.setPeriodicity(jsonObject.optString("periodicity").toString());
                applicableTestRoom.setLocation(jsonObject.optInt("location"));
                applicableTestRoom.setNoOfCycle(jsonObject.optInt("noOfCycle"));

                applicableTestRoom.setCreationDate(jsonObject.optString("createdDate").toString());
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
                grill.setRoomId(jsonObject.optInt("room"));
                grill.setWidh(jsonObject.optDouble("width"));
                grill.setLength(jsonObject.optDouble("length"));
                grill.setGrillArea(jsonObject.optDouble("grillArea"));
                grill.setEffectiveArea(jsonObject.optDouble("effectiveArea"));

                if(jsonObject.optBoolean("supplyGrill")) {
                    grill.setIsSupplyGrill(1);
                }else{
                    grill.setIsSupplyGrill(0);
                }
                if(jsonObject.optBoolean("additionalDetail")) {
                    grill.setAdditionalDetail(1);
                }else{
                    grill.setAdditionalDetail(0);
                }
                grill.setCreationDate(jsonObject.optString("createdDate").toString());
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
                roomFilter.setRoomId(jsonObject.optInt("room"));
                roomFilter.setFilterType(jsonObject.optString("filterType").toString());
                roomFilter.setEfficiency(jsonObject.optDouble("efficiency"));
                roomFilter.setParticleSize(jsonObject.optString("particleSize").toString());
                roomFilter.setSpecification(jsonObject.optDouble("specification"));
                roomFilter.setWidth(jsonObject.optDouble("width"));
                roomFilter.setLength(jsonObject.optDouble("length"));
                roomFilter.setGrillArea(jsonObject.optDouble("grillArea"));
                roomFilter.setEffectiveGrillArea(jsonObject.optDouble("effectiveGrillArea"));
                if(jsonObject.optBoolean("supplyFilter")) {
                    roomFilter.setIsSupplyFilter(1);
                }else{
                    roomFilter.setIsSupplyFilter(0);
                }

                roomFilter.setCreationDate(jsonObject.optString("createdDate").toString());
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
                room.setAhuId(jsonObject.optInt("ahu"));
                room.setRoomName(jsonObject.optString("roomName").toString());
                room.setRoomNo(jsonObject.optString("roomNo").toString());
                room.setWidth(jsonObject.optDouble("width"));
                room.setHeight(jsonObject.optDouble("height"));
                room.setLength(jsonObject.optDouble("length"));
                room.setArea(jsonObject.optDouble("area"));
                room.setVolume(jsonObject.optDouble("volume"));
                room.setAcphNLT(jsonObject.optInt("acphNLT"));
                room.setTestRef(jsonObject.optString("testRef"));
                room.setIsoClause(jsonObject.optString("isoClause").toString());
                room.setOccupancyState(jsonObject.optString("occupancyState").toString());
                room.setRoomSupplyAirflowCFM(jsonObject.optDouble("roomSupplyAirflowCFM"));
                room.setAhuFlowCFM(jsonObject.optDouble("ahuFlowCFM"));
                room.setRoomPressurePA(jsonObject.optDouble("roomPressurePA"));

                room.setFreshAirCFM(jsonObject.optDouble("freshAirCFM"));
                room.setBleedAirCFM(jsonObject.optDouble("bleedAirCFM"));
                room.setExhaustAirCFM(jsonObject.optDouble("exhaustAirCFM"));
                room.setTemperature(jsonObject.optDouble("temperature"));
                room.setRh(jsonObject.optDouble("rh"));
                room.setReturnAirCFM(jsonObject.optDouble("returnAirCFM"));
                room.setSupplyAirGrillQTY(jsonObject.optInt("supplyAirGrillQTY"));
                room.setReturnAirGrillQTY(jsonObject.optInt("returnAirGrillQTY"));
                room.setSupplyAirFilterQTY(jsonObject.optInt("supplyAirFilterQTY"));

                room.setReturnAirFilterQTY(jsonObject.optInt("returnAirFilterQTY"));
                room.setRemarks(jsonObject.optString("remarks").toString());
                room.setCreationDate(jsonObject.optString("createdDate").toString());
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
                area.setPlantId(jsonObject.optInt("plant"));
                area.setAreaName(jsonObject.optString("areaName").toString());
                area.setAdditionalDetails(jsonObject.optString("additionalDetails").toString());
                area.setCreationDate(jsonObject.optString("createdDate").toString());
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
                ahu.setBleedFilterSize(jsonObject.optString("bleedFilterSize").toString());
                ahu.setFreshFilterType(jsonObject.optString("freshFilterType").toString());
                ahu.setFreshAirCFM(jsonObject.optDouble("freshAirCFM"));
                ahu.setFreshFilterQty(jsonObject.optInt("freshFilterQty"));
                ahu.setFreshFilterSize(jsonObject.optString("freshFilterSize").toString());
                ahu.setAhuHEPAFilterQty(jsonObject.optInt("ahuHEPAFilterQty"));
                ahu.setHepaFilterEfficiency(jsonObject.optDouble("hepaFilterEfficiency"));
                ahu.setHepaParticleSize(jsonObject.optString("hepaParticleSize").toString());
                ahu.setHepaFilterEfficiency(jsonObject.optDouble("hepaFilterSpecification"));
                ahu.setCreationDate(jsonObject.optString("creationDate").toString());
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
            User user = new User();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                user.setId(jsonObject.optInt("id"));
                user.setName(jsonObject.optString("name").toString());
                if (null == jsonObject.optString("partner").toString() || "".equals(jsonObject.optString("partner").toString())) {
                    user.setType("CLIENT");
                } else {
                    user.setType("PARTNER");
                }
                user.setEmail(jsonObject.optString("email").toString());
                user.setContact(jsonObject.optString("contact").toString());
                user.setDepartment(jsonObject.optString("department").toString());
                user.setActive(jsonObject.optInt("active"));
                user.setDeleted(jsonObject.optInt("deleted"));
                user.setPassword(jsonObject.optString("password").toString());
                user.setCreationDate(jsonObject.optString("lastUpdated").toString());
                arrayList.add(user);
            } catch (Exception e) {

            }
        }
        return arrayList;
    }

    public interface ControlerResponse {
        public void controlerResult( HashMap<String, ArrayList> arrayListHashMap, int statusCode);
    }
}
