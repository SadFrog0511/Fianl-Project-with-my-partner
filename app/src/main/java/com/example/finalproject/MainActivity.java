package com.example.finalproject;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText e, p;
    Button Login, Exit, Register;
    Vibrator a;
    final String loginURL = "https://cs125.cs.illinois.edu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e = findViewById(R.id.email);
        p = findViewById(R.id.Password);
        Login = findViewById(R.id.Login);
        Exit = findViewById(R.id.Exit);
        Register = findViewById(R.id.Register);
        a = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent);
            }
        });

    }

    private void test() {

        final String email = e.getText().toString();
        final String password = p.getText().toString();


        if (TextUtils.isEmpty(email)) {
            e.setError("Nahhh! Show me ur email");
            e.requestFocus();
            a.vibrate(1000);
            Login.setEnabled(true);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            p.setError("Come on! Wrong password");
            p.requestFocus();
            a.vibrate(1000);
            Login.setEnabled(true);
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            e.setError("Wrong email, man! Do it again.");
            e.requestFocus();
            a.vibrate(1000);
            Login.setEnabled(true);
            return;
        }
        loginUser();

    }
    private void loginUser() {
        Uri uri = Uri.parse(loginURL);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}
