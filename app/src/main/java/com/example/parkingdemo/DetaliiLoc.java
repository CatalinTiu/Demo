package com.example.parkingdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class DetaliiLoc extends AppCompatActivity {

    private String m_Text = "";// contine nr. de ore pt. care este rezervat locul de parcare

    /* Functia onCreate se apeleaza prima, atunci cand se incarca pagina.
        CardForm este o librarie care creeaaza si afiseaza un formular de plata atunci cand apelam functia.
        Ii putem spune ce detalli trebuie cerute prin apelarea cardForm.ceva spre exemplu.
        Dupa ce am introdus toate datele si am apasat Cumpara, o alerta care ne intreaba daca suntem siguri cu datele introduse o sa apara,
        daca confirmam, rezervarea va fi inscrisa in baza de date si chitanta va fi afisata intr-un nou ecran.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalii_loc);



        // String-ul message contine numele locului de parcare pe care l-am apasat
        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);



        // accesam baza de data pentru a putea inscrie rezervarea
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Parcare");



        final CardForm cardForm = findViewById(R.id.card_form);
        Button buy = findViewById(R.id.btnBuy);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS trebuie sa fie activat")
                .setup(DetaliiLoc.this);

        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DetaliiLoc.this);
        alertBuilder.setTitle("Durata de timp stationare(ore):");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        alertBuilder.setView(input);
        alertBuilder.setPositiveButton("Confirma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                m_Text = input.getText().toString();
                dialogInterface.dismiss();
                Toast.makeText(DetaliiLoc.this, "Continuati cu plata card", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DetaliiLoc.this);
                    alertBuilder.setTitle("Confirma inainte de plata");
                    alertBuilder.setMessage("Numarul cardului: " + cardForm.getCardNumber() + "\n" +
                            "Data expirare card: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Cod Postal: " + cardForm.getPostalCode() + "\n" +
                            "Numar telefon: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirma", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            myRef.child(message).child(cardForm.getCardholderName()).setValue(m_Text);
                            dialogInterface.dismiss();
                            Toast.makeText(DetaliiLoc.this, "Rezervarea a fost facuta", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(DetaliiLoc.this , Chitanta.class);

                            startActivity(intent);
                        }
                    });
                    alertBuilder.setNegativeButton("Anuleaza", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(DetaliiLoc.this, "Va rugam completati formularul", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
