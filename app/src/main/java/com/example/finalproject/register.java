package com.example.finalproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText username,email,password,cd;
    TextView login;
    Button Register, GoBack;
    Vibrator v;
    final String loginURL = "https://cs125.cs.illinois.edu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        username = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.confirmpassword);
        cd =  findViewById(R.id.confirmpassword);
        GoBack = findViewById(R.id.Back);
        Register = findViewById(R.id.btnRegister);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void check() {

        //find values
        final String reg_name = username.getText().toString();
        final String reg_email = email.getText().toString();
        final String reg_password = cd.getText().toString();
        final String reg_cd = cd.getText().toString();

        if (TextUtils.isEmpty(reg_name)) {
            username.setError("Name is required");
            username.requestFocus();
            v.vibrate(100);
            return;
        }

        if (TextUtils.isEmpty(reg_email)) {
            email.setError("Email is required");
            email.requestFocus();
            v.vibrate(100);
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(reg_password)) {
            password.setError("Password is needed");
            password.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            return;
        }
        //validating email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(reg_email).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            v.vibrate(100000);
            return;
        }
        if (!reg_password.equals(reg_cd)) {
            password.setError("Password Does not Match");
            password.requestFocus();
            v.vibrate(10000);
            return;
        }

        registerUser();

    }

    private void registerUser() {
        final String reg_username = username.getText().toString();
        final String reg_email = email.getText().toString();
        final String reg_password = cd.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,loginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {

                                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Connection Error"+error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username",reg_username);
                params.put("email", reg_email);
                params.put("password", reg_password);

                return params;
            }
        };
    }

}

