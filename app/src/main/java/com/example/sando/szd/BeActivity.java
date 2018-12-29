package com.example.sando.szd;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class BeActivity extends AppCompatActivity {

    private EditText felhasznalonev, jelszo;
    private Button bejelentkezes, regisztracio;
    private TextView text;
    private Adatbazis db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be);
        init();
        bejelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beJelentkezes(felhasznalonev.getText().toString(), jelszo.getText().toString());
            }
        });
        regisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BeActivity.this, RegisztracioActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void init(){
        felhasznalonev = findViewById(R.id.felhasznalonev);
        jelszo = findViewById(R.id.jelszo);
        bejelentkezes = findViewById(R.id.bejelentkezes_gomb);
        regisztracio = findViewById(R.id.regisztracio);
        db = new Adatbazis(this);
    }
    public void beJelentkezes(String felhasznalo, String jelszo){
        Cursor adatok = db.getAdatok("Felhasznalok_tabla");
        boolean letezo_felhasznalo = false;
        boolean helyes_jelszo = false;
        boolean admin_statusz = false;
        if (felhasznalo.length()>0&&jelszo.length()>0) {
            if (adatok != null && adatok.getCount() > 0) {
                while (adatok.moveToNext()) {
                    if (Objects.equals(adatok.getString(1), felhasznalo)) {
                        letezo_felhasznalo = true;
                        if (Objects.equals(adatok.getString(2), jelszo)) {
                            helyes_jelszo = true;
                            if (Objects.equals(adatok.getString(3), "admin")){
                                admin_statusz = true;
                            }
                        }
                    }
                }
            }
            if (letezo_felhasznalo && helyes_jelszo) {
                if (admin_statusz) {
                    Intent i = new Intent(BeActivity.this, AdminFoActivity.class);
                    startActivity(i);
                    Toast.makeText(this, "Sikeres bejelentkezés adminisztrátorként", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Intent i = new Intent(BeActivity.this, FoOldal.class);
                    startActivity(i);
                    Toast.makeText(this, "Sikeres bejelentkezés", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else if (!letezo_felhasznalo) {
                Toast.makeText(this, "Nem létezik ilyen felhasználó!", Toast.LENGTH_SHORT).show();
            } else if (!helyes_jelszo) {
                Toast.makeText(this, "Helytelen jelszó!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ha ezt írja ki nagy a baj", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "A felhasználó név és jelszó nem lehet üres!", Toast.LENGTH_SHORT).show();
        }
    }
}
