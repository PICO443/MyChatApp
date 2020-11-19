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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegActivity extends AppCompatActivity {

    //Widgets
    EditText usernameET , passwordET , emailET ;
    Button registerBtn;

    //Firebase
    FirebaseAuth auth;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        //Initializing Widgets
        usernameET = findViewById(R.id.userEditText);
        passwordET = findViewById(R.id.passwordEditText);
        emailET = findViewById(R.id.emailEditText);
        registerBtn = findViewById(R.id.buttonRegister);

        //Firebase Auth
        auth = FirebaseAuth.getInstance();

        //Adding Event Listener to the Button Register
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = usernameET.getText().toString();
                String email_text = emailET.getText().toString();
                String password_text = passwordET.getText().toString();

                if(TextUtils.isEmpty(username_text) || TextUtils.isEmpty(email_text)
                        || TextUtils.isEmpty(password_text)){
                    Toast.makeText(RegActivity.this, "Please fill all the Fields",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    RegisterNow(username_text, email_text, password_text);
                }
            }
        });
    }
    private void RegisterNow(final String username, String email , String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseuser = auth.getCurrentUser();
                            String userid = firebaseuser.getUid();

                            myRef = FirebaseDatabase.getInstance().getReference("MyUsers")
                                    .child(userid);

                            //HashMaps
                            HashMap<String , String> hashMap = new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("imageURL","default");

                            //Opening the Main Activity after success Registration
                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent i = new Intent(RegActivity.this,MainActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });

                        }
                        else{
                            Toast.makeText(RegActivity.this, "Invalid Email or Password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}
