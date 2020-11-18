package com.example.CSCommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegActivity extends AppCompatActivity {

    //Widgets
    EditText usernameET , passwordET , emailET ;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //Initializing Widgets
        usernameET = findViewById(R.id.userEditText);
        passwordET = findViewById(R.id.passwordEditText);
        emailET = findViewById(R.id.emailEditText);
        registerBtn = findViewById(R.id.buttonRegister);
    }
}
