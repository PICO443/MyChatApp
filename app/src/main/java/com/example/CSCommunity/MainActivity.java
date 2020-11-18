package com.example.CSCommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    private FirebaseAnalytics analytic;
    private DatabaseReference mydatabase;
    private TextView myTextbox;
    private EditText myEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        analytic = FirebaseAnalytics.getInstance(this);
        mydatabase = FirebaseDatabase.getInstance().getReference("Message ");
        myTextbox = findViewById(R.id.textbox);

        mydatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myTextbox.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myTextbox.setText("CANCELED !");
            }
        });
    }

    public void sendMessege(View view){
        myEditText = findViewById(R.id.myidit);
        mydatabase.push().setValue(myEditText.getText().toString());
        myEditText.setText("");
        
    }
}
