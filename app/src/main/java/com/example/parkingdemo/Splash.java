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
    public final static String EXTRA_MESSAGE1 = "someString";  // nu afecteaza deloc codul, dar trebuie scrise niste valori aleatoare pentru a putea trimite spre pagina urmatoare
    public final static String EXTRA_MESSAGE2 = "soString";  // nu afecteaza deloc codul, dar trebuie scrise niste valori aleatoare pentru a putea trimite spre pagina urmatoare

    Handler handler;// folosit pentru a incepe tranzitita catre urmatoarea pagina
    int nr_ocupate = 0;//folosit pentru a retine numarul de locuri ocupate
    int count = 0;//numaram cate locuri sunt in total in parcare ca sa stim atunci cand ajungem la ultimul loc sa trecem la urmatoarea pagina cu datele obtinute
    String[] arr_ocupate = new String[9];//contine numele locurilor de parcare


/*Functia GoToMain redirectioneaza utilizatorul spre pagina de logare odata ce reuseste
conexiunea la baza de date Firebase si afla cate locuri sunt ocupate si lista cu numele tuturor locurilor de parcare,
care apoi sunt transmise prin intermediul variabilei nr_ocupate si vectorului arr_ocupate la urmatoarea pagina
 */
    void GoToMain(final int oke, final String[] arr)
    {
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this,Logare.class);
                intent.putExtra(EXTRA_MESSAGE1,oke);
                intent.putExtra(EXTRA_MESSAGE2,arr);
                startActivity(intent);
                finish();
            }
        },3000);
    }


/* Functia onCreate se apeleaza prima, atunci cand se incarca pagina.
    Mai intai facem conexiunea la Firebase si luam lista cu locuri din parcare,
    dupa aceea ca sa verificam cate locuri sunt ocupate si care sunt acestea.
    Ca sa  realizam asta, folosim functia addChildEventListener, care vine cu libraria firebase
    Mai multe detalii se gasesc aici: https://firebase.google.com/docs/reference/android/com/google/firebase/database/ChildEventListener
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Parcare");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
        });



    }
}
