package com.carlos.androidlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String PREFS_NAME = "CarlosLoginAppToken";

    public static final String API_ACCESS_TOKEN_KEY = "access-token";
    public static final String UID_KEY = "uid";
    public static final String EMAIL_KEY = "email";
    public static final String CLIENT_KEY = "client";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        String authToken = settings.getString(API_ACCESS_TOKEN_KEY, null);
        String uid = settings.getString(UID_KEY, null);
        String client = settings.getString(CLIENT_KEY, null);

        //if the token and uid exist, ping the app and make sure that they're valid
        if (authToken != null || uid != null || client != null) {
            AuthenticateAuthTokenInterface authenticateToken = ServiceGenerator
                    .createService(AuthenticateAuthTokenInterface.class);

            //initialize params with hashmap to send out to api
//            Map<String,String> options = new HashMap<String, String>();
//            {
//                options.put(UID_KEY,uid);
//                options.put(API_ACCESS_TOKEN_KEY,authToken);
//            }



            Call<User> call = authenticateToken.getUserToken(authToken, uid, client);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Response<User> response, Retrofit retrofit) {
                    if (response.code() == 200) {
                        Log.i(TAG, "success");
                        Log.i(TAG, response.body().toString());
                        Log.i(TAG, response.raw().toString());
                        Log.i(TAG, response.headers().toString());
                    }
                    else {
                        Log.i(TAG, "not authenticated");
                        Log.i(TAG, response.headers().toString());
                        navigatetoLogin();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    navigatetoLogin();
                }
            });
        }
        else {
            navigatetoLogin();
        }



    }

    private void navigatetoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
