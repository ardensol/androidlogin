package com.carlos.androidlogin;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String PREFS_NAME = "CarlosLoginAppToken";

    public static final String API_ACCESS_TOKEN_KEY = "access-token";
    public static final String UID_KEY = "uid";
    public static final String CLIENT_KEY = "client";
    ;


    protected EditText mEmail;
    protected EditText mPassword;
    protected Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmail = (EditText) findViewById(R.id.loginEmailText);
        mPassword = (EditText) findViewById(R.id.loginPassword);
        mLoginButton = (Button) findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                email = email.trim();
                password = password.trim();

                ApiInterface login = ServiceGenerator
                        .createService(ApiInterface.class);

//                User user = new User(email, password);

                Call<User> call = login.getUserToken(email, password);


                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {

                        if (response.code() == 200 ) {

                            //get token from header
                            String accessToken = response.headers().get("Access-Token").toString();
                            String uid = response.headers().get("Uid").toString();
                            String client = response.headers().get("Client").toString();

                            //Save Token
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString(API_ACCESS_TOKEN_KEY, accessToken);
                            editor.putString(UID_KEY, uid);
                            editor.putString(CLIENT_KEY, client);

                            // Commit the edits!
                            editor.commit();


                            Log.i(TAG, "success");
                            Log.i(TAG, response.body().toString());
                            Log.i(TAG, response.raw().toString());
                            Log.i(TAG, response.headers().toString());
                        }
                        else {
                            Log.i(TAG, "Response Code Failure");
                            Toast.makeText(LoginActivity.this,
                                    R.string.standard_error_message,
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i(TAG, "Failure");
                        Toast.makeText(LoginActivity.this,
                                R.string.standard_error_message,
                                Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

            }
        });

    }



}
