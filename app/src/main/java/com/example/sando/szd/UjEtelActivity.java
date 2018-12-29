package com.example.sando.szd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UjEtelActivity extends AppCompatActivity {
    private EditText nev,leiras,ar,kep;
    private Button felvetel;
    private Adatbazis db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uj_etel);
        init();
        felvetel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ujetel();
            }
        });
    }

    public void init(){
        nev = findViewById(R.id.nev);
        leiras = findViewById(R.id.leiras);
        ar = findViewById(R.id.ar);
        kep = findViewById(R.id.kep);
        db = new Adatbazis(this);
        felvetel = findViewById(R.id.uj_etel);
    }
    public void ujetel(){
        String etel_nev = nev.getText().toString();
        String etel_leiras = leiras.getText().toString();
        String etel_ar = ar.getText().toString();
        String etel_kep = kep.getText().toString();

        boolean eredmeny = db.Uj_Etel(etel_nev,etel_leiras,etel_ar,etel_kep);
        if (eredmeny){
            Toast.makeText(this, "Sikeres felvétel!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(UjEtelActivity.this, RendelesActivity.class);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(this, "Sikertelen felvétel!", Toast.LENGTH_SHORT).show();
        }
    }
}
