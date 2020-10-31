package com.geo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GeoService {
    private static final String API_URL="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
    private AccountService accountService;

    public GeoService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void getLocation(String address){
        HttpURLConnection connection;
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String method = "GET";
            String requestUrl = API_URL+encodedAddress;
            URL url = new URL(requestUrl);
            connection = connect(url, method);
            String json = getJson(connection);


        } catch (IOException e) {
            e.printStackTrace();
        }



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
