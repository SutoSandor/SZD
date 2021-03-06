package com.example.sando.szd;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class BeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private EditText felhasznalonev, jelszo;
    private Button bejelentkezes, regisztracio;
    private TextView text;
    private Adatbazis db;
    public Toolbar toolbar;
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
        //menu
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                            db.Bejelentkezett_profil_id = adatok.getInt(0);
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
                    Toast.makeText(this, "Sikeres bejelentkezés adminisztrátorként " + db.Bejelentkezett_profil_id, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Intent i = new Intent(BeActivity.this, FoOldal.class);
                    startActivity(i);
                    Toast.makeText(this, "Sikeres bejelentkezés "  + db.Bejelentkezett_profil_id, Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_rendel){
            startRendeles();
        }
        else if(menuItem.getItemId() == R.id.nav_foglal){

        }
        else if(menuItem.getItemId() == R.id.nav_kapcsolat){
            startKapcsolat();
        }
        else if(menuItem.getItemId() == R.id.nav_login){
            startBejelentkezes();
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startRendeles(){
        Intent i = new Intent(this,RendelesActivity.class);
        startActivity(i);
        finish();
    }
    public void startKapcsolat(){
        Intent i = new Intent(this,KapcsolatActivity.class);
        startActivity(i);
        finish();
    }
    public void startBejelentkezes(){
        Intent intent = new Intent(this, BeActivity.class);
        startActivity(intent);
        finish();
    }
}
