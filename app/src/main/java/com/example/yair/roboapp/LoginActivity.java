package com.example.yair.roboapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yair.roboapp.classes.Utils;

import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    public static String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        Utils.loadData();
        try {
            findViewById(R.id.login).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ip = ((EditText) findViewById(R.id.ipbox)).getText().toString();
                    String username = ((EditText) findViewById(R.id.usernamebox)).getText().toString();
                    String password = ((EditText) findViewById(R.id.passwordbox)).getText().toString();
//                    ip="79.178.101.120";
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    String url = "http://" + ip + ":8080/LoginActivity?user=" + username + "&pass=" + password;

                    /*
                    //TODO: uncomment to test without server
                    Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
                    startActivity(intent);
                    */

                    //TODO: comment to test without server
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("ok")) {

                                        Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
                                        intent.putExtra("Ip",ip);
                                        startActivity(intent);

                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "UserName or Password or ip are invalid", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //For debugging only
                            //Toast.makeText(LoginActivity.this, "NOP!" + error, Toast.LENGTH_LONG).show();
                        }
                    });
                    queue.add(stringRequest);

                }
            });
        } catch (Exception e) {
            //For debugging only
            //Toast.makeText(LoginActivity.this, "NOP!" + e.toString(), Toast.LENGTH_LONG).show();
        }


    }
}