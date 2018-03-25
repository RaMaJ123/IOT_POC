package com.example.ramjiseetharaman.facerecogpoc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramjiseetharaman.facerecogpoc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rodrigo Augusto on 3/21/18.
 * Main Activity that calls the FaceCountUpdateService
 */

public class FaceCount extends AppCompatActivity {

    String faceCountValue;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_count);

        final TextView face_Count_textView = (TextView) findViewById(R.id.face_count_value);

        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

        // This schedule a runnable task every 1 minutes
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(FaceCount.this, "This service is running every 10 seconds", Toast.LENGTH_SHORT).show();
                        faceCountValue = dataSnapshot.child("faceCount").getValue(String.class);
                        face_Count_textView.setText(faceCountValue);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

}
