package com.example.CSCommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userETLogin, passwordETLogin;
    Button loginBtn, registerBtn;

    //Firebae :
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userETLogin = findViewById(R.id.userETLogin);
        passwordETLogin = findViewById(R.id.passwordETLogin);
        loginBtn = findViewById(R.id.loginBtn);

        // Firebase Auth:
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Checking for user existence | Saving the current user:
        if(firebaseUser != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        //Register Button:
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);

            }
        });

        // Login Button *HI AGAIN*:
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = userETLogin.getText().toString();
                String password_text = passwordETLogin.getText().toString();

                // Check if the Fields are empty
                if(TextUtils.isEmpty(email_text) || TextUtils.isEmpty(password_text)){
                    Toast.makeText(LoginActivity.this,"Please Fill All the Fields",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(email_text, password_text)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Login Failed !",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
