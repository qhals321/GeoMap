package com.geo;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private static final String RESOURCES_DIRECTORY = "/Users/bomin/Desktop/develop/backend/java/workspace/GeoMap/src/main/resources/";
    private Properties properties;

    public PropertiesLoader(String propertiesName){
        this.properties = new Properties();
        try {
            properties.load(new FileReader(RESOURCES_DIRECTORY + propertiesName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String keyName){
        return properties.getProperty(keyName);
    }
}
