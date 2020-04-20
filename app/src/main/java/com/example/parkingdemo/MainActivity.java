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
    public final static String EXTRA_MESSAGE = "someString";  // nu afecteaza deloc codul, dar trebuie scrise niste valori aleatoare pentru a putea trimite spre pagina urmatoare
    public int ok = 5; // ia valoarea nr. de locuri parcate ocupate
    public String[] arr_oc = new String[9]; // ia valoarea listei cu totate numele locurilor de parcare



    /* Functia onCreate se apeleaza prima, atunci cand se incarca pagina.
    Transmitem valoarea nr.locurilor de parcare ocupate si lista cu numele tuturor locurilor de parcare in variabilele ok si arr_oc.
    Dupa aceea, transformam lista cu numele locurilor de parcare intr-o lista doar cu locurile de parcare ocupate
    Dupa afisam utilizatorului toate locurile de parcare ocupate
     */
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        ok = intent.getIntExtra(Logare.EXTRA_MESSAGE1, 0);
        arr_oc = intent.getStringArrayExtra(Logare.EXTRA_MESSAGE2);

        List<String> list_without_null = new ArrayList<String>();
        for(String new_string : arr_oc ) {
            if(new_string != null && new_string.length() > 0) {
                list_without_null.add(new_string);
            }
        }
        arr_oc = list_without_null.toArray(new String[list_without_null.size()]);




        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });


        builder.setItems(arr_oc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
           builder.setTitle("Urmatoarele locuri de parcare sunt ocupate:");

        AlertDialog dialog = builder.create();
        dialog.show();



        Toast.makeText(MainActivity.this, "Sunt "+ ok +" locuri ocupate", Toast.LENGTH_LONG).show();




    }


/*Aceasta functie se apeleaza cand apasam oricare dintre butoane,
 si redirectioneaza catre pagina de platit cu cardul asociata locului de parcare respectiv
 */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DetaliiLoc.class);

        String button=((Button) view).getText().toString();

        intent.putExtra(EXTRA_MESSAGE, button);
        startActivity(intent);
    }
}
