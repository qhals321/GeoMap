package com.geo;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GeoService {
    private static final String API_URL_GEO_CODE ="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
    private AccountService accountService;

    public GeoService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Address getLocation(String address){
        HttpURLConnection connection;
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String method = "GET";
            String requestUrl = API_URL_GEO_CODE +encodedAddress;
            URL url = new URL(requestUrl);
            connection = connect(url, method);
            String json = getJson(connection);

            Address finalAddress = mapJson(json);

            return finalAddress;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Address mapJson(String json){
        JSONTokener jsonTokener = new JSONTokener(json);
        JSONObject jsonObject = new JSONObject(jsonTokener);

        JSONArray addresses = jsonObject.getJSONArray("addresses");
        Address address = new Address();
        for (int i = 0; i < addresses.length(); i++) {
            JSONObject obj = (JSONObject) addresses.get(i);
            address.setLatitude(obj.get("x").toString());
            address.setLongitude(obj.get("y").toString());
            address.setRoadAddress(obj.get("roadAddress").toString());
            address.setJibunAddress(obj.get("jibunAddress").toString());
        }

        return address;
    }




    private String getJson(HttpURLConnection connection){
        int responseNumber = 0;
        StringBuffer response = new StringBuffer();
        BufferedReader br;
        try {
            responseNumber = connection.getResponseCode();
            if(responseNumber == 200){
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            }else{
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            while ((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    private HttpURLConnection connect(URL url, String method){
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accountService.getClientId());
            httpURLConnection.setRequestProperty("X-NCP-APIGW-API-KEY", accountService.getClientSecret());
            return httpURLConnection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
