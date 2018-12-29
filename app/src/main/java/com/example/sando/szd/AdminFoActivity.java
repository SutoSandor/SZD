package com.example.sando.szd;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminFoActivity extends AppCompatActivity {

    private TextView lista;
    private Button lista_felhasznalok, lista_rendelesek, lista_foglalasok, etel_felvetel;
    private Adatbazis db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fo);
        init();
        lista_felhasznalok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                felhasznalok_listaja();
            }
        });
        lista_rendelesek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rendelesek_listaja();
            }
        });
        lista_foglalasok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foglalasok_listaja();
            }
        });
        etel_felvetel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminFoActivity.this, UjEtelActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    public void init(){
        lista = findViewById(R.id.lista);
        lista_felhasznalok = findViewById(R.id.lista_felhasznalok);
        lista_foglalasok = findViewById(R.id.lista_foglalasok);
        lista_rendelesek = findViewById(R.id.lista_rendelesek);
        etel_felvetel = findViewById(R.id.etel_felvetel_intent);
        db = new Adatbazis(this);
    }
    public void felhasznalok_listaja(){
        Cursor adatok = db.getAdatok("Felhasznalok_tabla");
        String szoveg = "Felhasnálók listája: \n";
        while (adatok.moveToNext()){
            szoveg += " ID: ["+adatok.getString(0)+"]\n Felhasználónév: [" +adatok.getString(1) +"]\n " +
                      "Jelszó: [" + adatok.getString(2) +"]\n Jogosultság: [" + adatok.getString(3) +"]\n\n";
        }
        lista.setText(szoveg);
    }
    public void rendelesek_listaja(){
        String szoveg = "Rendelések listája: \n";

        lista.setText(szoveg);
    }
    public void foglalasok_listaja(){
        String szoveg = "Asztalfoglalások listája: \n";

        lista.setText(szoveg);
    }
}
