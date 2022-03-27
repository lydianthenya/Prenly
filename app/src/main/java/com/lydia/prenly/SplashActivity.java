package com.lydia.prenly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lydia.prenly.utils.MyPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    MyPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = new MyPreferences(SplashActivity.this);

        pref.setSelectedTopic(getString(R.string.default_topic));
        new CountDownTimer(4000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
            }

        }.start();
    }


}