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

import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    Button submit, new_signup;
    MyDbHelper_E db;
    Context context;
    ArrayList<Model> arrayList;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.un);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.btn_submit);
        new_signup = (Button) findViewById(R.id.btn_registerpage);
        new_signup.setOnClickListener(this);

        submit.setOnClickListener(this);
        context = Login.this;
        db = new MyDbHelper_E(context);
        sessionManager = new SessionManager(context);
        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(context, HomePage.class);
            intent.putExtra("userid", sessionManager.get_User_Id());
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            if (username.length() > 10 || username.length() < 10 || username.getText().toString().length() == 0
                    || password.getText().toString().length() == 0) {
                Toast.makeText(context, "Wrong Input Type.", Toast.LENGTH_LONG).show();
            } else {
                arrayList = new ArrayList<>();
                arrayList = db.getLoginData(username.getText().toString(), password.getText().toString());
                if (arrayList.size() > 0) {
                    Intent intent = new Intent(context, HomePage.class);
                    intent.putExtra("userid", arrayList.get(0).getId());
                    startActivity(intent);
                    sessionManager.saveLoggedIn(true);
                    sessionManager.set_User_Id(arrayList.get(0).getId(),arrayList.get(0).getUsername(),arrayList.get(0).getMobile());
                    finish();
                } else {
                    Toast.makeText(context, "Invalid UserName or Password", Toast.LENGTH_LONG).show();
                }
            }


        }
        else
        {
            Intent intent = new Intent(context, Registration.class);
            startActivity(intent);
        }
    }


}

