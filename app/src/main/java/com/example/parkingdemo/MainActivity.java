package com.example.parkingdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "someString";
    private static final String TAG = "Nr. copii";
    public int ok = 5;
    public String[] arr_oc = new String[9];




    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        ok = intent.getIntExtra(Splash.EXTRA_MESSAGE1, 0);
        arr_oc = intent.getStringArrayExtra(Splash.EXTRA_MESSAGE2);

        List<String> list_without_null = new ArrayList<String>();
        for(String new_string : arr_oc ) {
            if(new_string != null && new_string.length() > 0) {
                list_without_null.add(new_string);
            }
        }
        arr_oc = list_without_null.toArray(new String[list_without_null.size()]);



        Log.i("Array-ul este ", Arrays.toString(arr_oc));

        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
            }
        });

// 2. Chain together various setter methods to set the dialog characteristics

        builder.setItems(arr_oc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
           builder.setTitle("Urmatoarele locuri de parcare sunt ocupate:");

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
        dialog.show();



        Toast.makeText(MainActivity.this, "Sunt "+ ok +" locuri ocupate", Toast.LENGTH_LONG).show();




    }



    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DetaliiLoc.class);

        String button=((Button) view).getText().toString();




        intent.putExtra(EXTRA_MESSAGE, button);
        startActivity(intent);
    }
}
