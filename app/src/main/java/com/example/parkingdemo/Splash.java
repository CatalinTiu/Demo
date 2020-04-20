package com.example.parkingdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Splash extends AppCompatActivity {
    public final static String EXTRA_MESSAGE1 = "someString";
    public final static String EXTRA_MESSAGE2 = "soString";

    Handler handler;
    int nr_ocupate = 0;
    int count = 0;
    String[] arr_ocupate = new String[9];

    void GoToMain(final int oke, final String[] arr)
    {
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this,Logare.class);
                intent.putExtra(EXTRA_MESSAGE1, oke);
                intent.putExtra(EXTRA_MESSAGE2,arr);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Parcare");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.e(dataSnapshot.getKey(),dataSnapshot.getChildrenCount() + "");
                // Log.e(TAG, String.valueOf(dataSnapshot.getChildrenCount()));

                // Log.e(TAG, String.valueOf(count));
                if(dataSnapshot.getChildrenCount()>1)
                {
                    arr_ocupate[nr_ocupate]=dataSnapshot.getKey();
                    nr_ocupate ++;
                }
                count++;
                if(count == 10)
                    GoToMain(nr_ocupate,arr_ocupate);
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
}
