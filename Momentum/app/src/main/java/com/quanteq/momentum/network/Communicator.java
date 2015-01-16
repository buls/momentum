package com.quanteq.momentum.network;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;


import android.location.Location;
import android.util.Log;

import com.quanteq.momentum.PollingUnit;


public class Communicator {

    //private static String HOST = "http://10.0.2.2:8888/pua?a=";
    //private static String HOST = "http://172.16.3.68:8888/pua?a=";
    private static String HOST = "http://pua-2015.appspot.com/pua?a=";

    private static String GET_CURRENT_LOCATION_PUS = "locationpus";
    private static String GET_LOCATIONS = "getstatelocations";
    private static String MOBILE_INIT = "mobileinit";
    private static String UPDATE = "update";

	/*public byte[] getImage(String url) throws Exception{
        if(url.contains("127.0.0.1")){
            url = url.replace("127.0.0.1", "10.0.2.2");
            url = url.replace("=s0", "");
        }
		InputStream is =  new HttpRequest().getImage(url);
        byte[] imageData = IOUtils.toByteArray(is);
        return imageData;
	}*/

    public List<PollingUnit> getPollingUnitsAround(String lat, String lng) throws Exception{
        String url = "";
        List<PollingUnit> pus = new ArrayList<PollingUnit>();
        //try{
            url = HOST + GET_CURRENT_LOCATION_PUS + "&lt=" +
            URLEncoder.encode(lat, "utf-8") + "&ln=" +
                    URLEncoder.encode(lng, "utf-8");
        //} catch(Exception e){

        //}
        String response;
        HttpData d;
        d = HttpRequest.get(url);
        response = d.getResponse();
        if(response != null){
            Log.i("Communicator: get pus", response);
        } else{
            throw new Exception();
        }
        JSONObject jObject;
        //try{
            jObject = new JSONObject(response);
            String status = jObject.getString("status");
            if(status.equals("ok")){
                JSONArray payload = jObject.getJSONArray("payload");

                JSONArray pusArray = payload.getJSONArray(0);
                Location location = null;
                for(int i = 0; i < pusArray.length(); i++){
                    PollingUnit pu = new PollingUnit();
                    JSONObject object = pusArray.getJSONObject(i);
                    pu.setLocationId(object.getString("locationId"));
                    pu.setDesc(object.getString("desc"));
                    location = new Location("");
                    location.setLatitude(Double.parseDouble(object.getString("lat")));
                    location.setLongitude(Double.parseDouble(object.getString("lng")));
                    pu.setLocation(location);
                    pus.add(pu);
                }
            }
        //} catch(Exception e){
          //  System.out.println("JSON: " + e);
        //}
        return pus;
    }




}