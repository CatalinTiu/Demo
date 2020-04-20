package com.example.parkingdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Logare extends AppCompatActivity {

    public final static String EXTRA_MESSAGE1 = "someString";
    public final static String EXTRA_MESSAGE2 = "soString";
    public final static String EXTRA_MESSAGE3 = "Valoare";


    public int ok = 5;
    public int okey = 0;
    public String[] arr_oc = new String[9];

    void getlogged(int oke)
    {
            if(oke==1) {
                Intent intent = new Intent(Logare.this, MainActivity.class);
                intent.putExtra(EXTRA_MESSAGE1, ok);
                intent.putExtra(EXTRA_MESSAGE2, arr_oc);
                startActivity(intent);
            }
            else
                Toast.makeText(Logare.this, "Va rugam introduceti un nr. de telefon asociat complexului", Toast.LENGTH_LONG).show();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logare);

        final EditText password = (EditText)findViewById(R.id.editText);
        Button b1 = (Button)findViewById(R.id.button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Telefoane");

        Intent intent = getIntent();
        ok = intent.getIntExtra(Splash.EXTRA_MESSAGE1, 0);
        arr_oc = intent.getStringArrayExtra(Splash.EXTRA_MESSAGE2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String ed_text = password.getText().toString().trim();

                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text == null || ed_text.length()!= 10)
                {
                    //EditText is  empty
                    Toast.makeText(Logare.this, "Va rugam introduceti un nr. de telefon corect", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //EditText is not empty
                    final String message = password.getText().toString();

                    myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                           if(message.equals(dataSnapshot.getValue()))
                           {
                               okey = 1;
                           }

                           // Log.e(EXTRA_MESSAGE3, String.valueOf(dataSnapshot.getValue()));
                            Log.e(EXTRA_MESSAGE3, message);


                            //if(okey==1)
                               getlogged(okey);
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
        });
    }
}
