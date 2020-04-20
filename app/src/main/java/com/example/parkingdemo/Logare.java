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

    public final static String EXTRA_MESSAGE1 = "someString"; // nu afecteaza deloc codul, dar trebuie scrise niste valori aleatoare pentru a putea trimite spre pagina urmatoare
    public final static String EXTRA_MESSAGE2 = "soString";  // nu afecteaza deloc codul, dar trebuie scrise niste valori aleatoare pentru a putea trimite spre pagina urmatoare
    public final static String EXTRA_MESSAGE3 = "Valoare";  // nu afecteaza deloc codul, dar trebuie scrise niste valori aleatoare pentru a putea trimite spre pagina urmatoare


    public int ok = 5;// primeste nr. locuri ocupate in parcare
    public int okey = 0;//verifica daca s-a gasit numarul de telefon in lista rezidentilor complexului de pe firebase
    public String[] arr_oc = new String[9];//primeste lista cu numele locurilor din parcare

    /* Se apeleaza odata ce s-a verificat ca parola introdusa este un numar de telefon scris corect,
    dupa care, in interiorul functiei, se verifica daca numarul apartine complexului de locatari,
    daca da, se merge la urmatoare pagina impreuna cu nr.locuri ocupate in parcare si lista cu numele tuturor locurilor
    daca nu, se afiseaza un mesaj care afirma ca numarul nu apartine complexului
     */
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

    /* Functia onCreate se apeleaza prima, atunci cand se incarca pagina.
    Luam lista cu numerele de telefoane ale complexului din firebase.
    In interiorul functiei onClick luma parola introdusa de utilizator, o convertim in string,
    dupa care verificam daca este valida sau nu(daca e goala, daca nu are 10 cifre ca intr-un nr. de telefon etc.)
    daca este valida, apelam functia getlogged(),
    daca nu, afisam un mesaj care sa spuna ca nr. de telefon nu este corect
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logare);

        final EditText password = (EditText)findViewById(R.id.editText);//luam valoarea introdusa de utilizator ca parola
        Button b1 = (Button)findViewById(R.id.button);// accesam butonul din cod pentru a-l da ca parametru in functia onClick ca sa stim ce buton am apasat

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
                    //Parola este goala
                    Toast.makeText(Logare.this, "Va rugam introduceti un nr. de telefon corect", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Parola nu este goala
                    final String message = password.getText().toString();

                    myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                           if(message.equals(dataSnapshot.getValue()))
                           {
                               okey = 1;
                           }

                            Log.e(EXTRA_MESSAGE3, message);


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
