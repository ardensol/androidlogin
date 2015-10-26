package com.carlos.androidlogin;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.QueryMap;

/**
 * Created by carlos on 10/25/15.
 */

public interface AuthenticateAuthTokenInterface {

    @GET("/api/v1/auth/validate_token")
    Call<User> getUserToken(@Header("access-token") String accessToken, @Header("uid") String uid, @Header("client") String client);
}

