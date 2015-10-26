package com.carlos.androidlogin;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

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

                        String accessToken = response.headers().get("Access-Token").toString();
                        

                        Log.i(TAG, "success");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.i(TAG, "Failure");
                        finish();
                    }
                });

            }
        });

    }



}
