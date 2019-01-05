package com.example.sando.szd;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class RendelesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int rendeles_mennyisege = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Adatbazis db = new Adatbazis(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendeles);
        LinearLayout kepek_layout = (LinearLayout)findViewById(R.id.kepek_layout);
        final Cursor adatok = db.getAdatok("Rendelesek_tabla");
        //menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        while (adatok.moveToNext()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(100,10,0,0);
            //LINEAR LAYOUT
            final LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setId(adatok.getInt(0));

            //KÉP
            final ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(350,240));
            Picasso.get().load(adatok.getString(3)).into(image);
            //CÍM + LEÍRÁS + ÁR
            TextView text = new TextView(this);
            text.setLayoutParams(params);
            final String szoveg = "Név: " + adatok.getString(1) + "\nLeírás: "+ adatok.getString(4) +"\nÁr: "+ adatok.getString(2);
            text.setText(szoveg);

            layout.addView(image);
            layout.addView(text);
            kepek_layout.addView(layout);

            layout.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    View dialog_view = getLayoutInflater().inflate(R.layout.dialog_rendeles, null);
                    TextView cim = dialog_view.findViewById(R.id.dialog_rendeles_cim);
                    TextView leiras = dialog_view.findViewById(R.id.dialog_rendeles_leiras);
                    ImageView kep = dialog_view.findViewById(R.id.dialog_rendeles_kep);
                    Button minusz = dialog_view.findViewById(R.id.dialog_rendeles_minusz);
                    Button plusz = dialog_view.findViewById(R.id.dialog_rendeles_plusz);
                    final TextView szam = dialog_view.findViewById(R.id.dialog_rendeles_szam);




                    Toast.makeText(RendelesActivity.this, layout.getId()+"", Toast.LENGTH_SHORT).show();
                    //DIALOG BOX
                    Cursor etel = db.getAdatokWHERE("Rendelesek_tabla","ID", String.valueOf(layout.getId()));
                    AlertDialog.Builder builder = new AlertDialog.Builder(RendelesActivity.this);
                    builder.setView(dialog_view);
                    while (etel.moveToNext()){
                        Picasso.get().load(etel.getString(3)).into(kep);
                        String adatok_cim = etel.getString(1);
                        String adatok_leiras = etel.getString(4)+"\n"+etel.getString(2) + " Ft";
                        cim.setText(adatok_cim);
                        leiras.setText(adatok_leiras);
                    }
                    builder.setPositiveButton("Rendel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            db.Rendeles(String.valueOf(db.Bejelentkezett_profil_id), String.valueOf(layout.getId()));
                            rendeles_mennyisege = 1;
                        }
                    });
                    builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            rendeles_mennyisege = 1;
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    minusz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rendeles_mennyisege--;
                            szam.setText(rendeles_mennyisege+"");
                        }
                    });
                    plusz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rendeles_mennyisege++;
                            szam.setText(rendeles_mennyisege+"");
                        }
                    });
                }
            });
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
