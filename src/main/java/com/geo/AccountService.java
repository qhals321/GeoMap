package com.geo;

public class AccountService {
    private String clientId;
    private String clientSecret;

    public AccountService(String propertiesName) {
        PropertiesLoader propertiesLoader = new PropertiesLoader(propertiesName);
        String ID_KEY_NAME = "client.id";
        String SECRET_KEY_NAME = "client.secret";
        this.clientId = propertiesLoader.getValue(ID_KEY_NAME);
        this.clientSecret = propertiesLoader.getValue(SECRET_KEY_NAME);
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }


}
