package com.example.qrcontacts.remote;

import com.example.qrcontacts.model.ResObj;
import com.google.gson.JsonElement;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("login")
    Call<ResponseBody> login(@Query("username") String username, @Query("password") String password);
}
