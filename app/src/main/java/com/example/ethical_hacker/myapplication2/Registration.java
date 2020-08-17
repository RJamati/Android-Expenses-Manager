package com.example.ethical_hacker.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    EditText username, mobile, password;
    Button submit, login;
    MyDbHelper_E db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registrationpage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        username = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.btn_submit);
        login = (Button) findViewById(R.id.btn_registerpage);
        login.setOnClickListener(this);
        submit.setOnClickListener(this);
        context = Registration.this;
        db = new MyDbHelper_E(context);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {

            if (mobile.length() > 10 || mobile.length() < 10 || mobile.length() == 0
                    || username.getText().toString().length() == 0 || password.getText().toString().length() == 0) {
                Toast.makeText(context, "Wrong Input Type.", Toast.LENGTH_LONG).show();
            } else {
                boolean inserted = db.setUser(username.getText().toString(), mobile.getText().toString(), password.getText().toString());
                if (inserted) {
                    Intent intent = new Intent(context, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, "Mobile number is already registered !", Toast.LENGTH_LONG).show();

                }

            }
        } else {
            Intent intent = new Intent(context, Login.class);
            startActivity(intent);
            finish();
        }
    }

}

