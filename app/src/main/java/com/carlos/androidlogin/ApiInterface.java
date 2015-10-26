package com.carlos.androidlogin;

import retrofit.Call;
import retrofit.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by carlos on 10/25/15.
 */
public interface ApiInterface {
    @FormUrlEncoded
    @POST("/api/v1/auth/sign_in")
    Call<User> getUserToken(@Field("email") String email, @Field("password") String password);
}
