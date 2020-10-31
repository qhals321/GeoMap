package com.geo;

import java.io.IOException;
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
        int responseNumber = 0;
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String method = "GET";
            String requestUrl = API_URL+encodedAddress;
            URL url = new URL(requestUrl);
            responseNumber = connect(url, method);
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            e.printStackTrace();
        }


    }



    private int connect(URL url, String method){
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accountService.getClientId());
            httpURLConnection.setRequestProperty("X-NCP-APIGW-API-KEY", accountService.getClientSecret());
            return httpURLConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
