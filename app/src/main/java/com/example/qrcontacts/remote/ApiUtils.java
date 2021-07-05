package com.example.qrcontacts.remote;

public class ApiUtils {

    public static final String BASE_URL = "https://api-iiatmd.tychovanveen.nl/public/api/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
