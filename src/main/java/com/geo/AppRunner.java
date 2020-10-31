package com.geo;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

public class AppRunner {

    public static void main(String[] args) {
        AccountService accountService = new AccountService("account.properties");
        System.out.println(accountService.getClientId());
        GeoService geoService = new GeoService(accountService);

    }
}
