package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Testing extends AppCompatActivity {
    EditText mMusic, mLike, murl;
    Button mLoginBtn;
    String email = Login.user;

    DatabaseReference databaseReference;

    int songNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        mMusic = findViewById(R.id.search);
        mLike = findViewById(R.id.search1);
        mLoginBtn = findViewById(R.id.button);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        murl = findViewById(R.id.url);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String music = mMusic.getText().toString();
                String like = mLike.getText().toString();
                String url = murl.getText().toString();

                Testing_HelperClass helperClass = new Testing_HelperClass(music, like, url);

                databaseReference.child(email).child(music).setValue(helperClass);
                songNumber++;
            }
        });

    }
}