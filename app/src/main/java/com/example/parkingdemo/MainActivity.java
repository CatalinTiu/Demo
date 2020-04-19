package com.example.parkingdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "someString";
    private static final String TAG = "Nr. copii";
    public int ok = 1;
    public int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Parcare");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
               // Log.e(TAG, String.valueOf(dataSnapshot.getChildrenCount()));

                if(dataSnapshot.getChildrenCount()>1)
                    Toast.makeText(MainActivity.this, dataSnapshot.getKey()+" este ocupat", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, dataSnapshot.getKey()+ " nu este ocupat", Toast.LENGTH_LONG).show();


                // Log.e(TAG, String.valueOf(count));



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            //snapshot.child("Parcare").child("Locul 1").getChildrenCount()
        //Toast.makeText(MainActivity.this, String.valueOf(snapshot.child("Parcare").child("Locul 1").getChildrenCount()), Toast.LENGTH_LONG).show();




    });

    }



    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DetaliiLoc.class);

        String button=((Button) view).getText().toString();




        intent.putExtra(EXTRA_MESSAGE, button);
        startActivity(intent);
    }
}
