package com.yakamozapp.piano.Network;

import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkInterface {



    @GET("user/check")
    Call<JsonElement> checkUser(@Query("mobile") String mobile);

    @GET("verify")
    Call<JsonElement> verify(@Query("password") String password,@Query("mobile") String mobile);



}
