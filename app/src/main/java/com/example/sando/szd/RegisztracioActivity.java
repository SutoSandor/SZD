package com.example.sando.szd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisztracioActivity extends AppCompatActivity {

    private EditText felhasznalo, jelszo;
    private Button regisztracio;
    private Adatbazis db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisztracio);
        init();
        regisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regisztracio();
            }
        });
    }
    public void init(){
        felhasznalo = findViewById(R.id.felhasznalonev_text);
        jelszo = findViewById(R.id.jelszo_text);
        regisztracio = findViewById(R.id.regisztracio_gomb);
        db = new Adatbazis(this);
    }
    public void Regisztracio(){
        String felh = felhasznalo.getText().toString();
        String jelsz = jelszo.getText().toString();
        
        boolean eredmeny = db.Regisztracio(felh,jelsz);
        
        if (eredmeny){
            Toast.makeText(this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegisztracioActivity.this, BeActivity.class);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(this, "Sikertelen regisztráció", Toast.LENGTH_SHORT).show();
        }
    }
}
