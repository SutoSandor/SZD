package com.example.sando.szd;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminFoActivity extends AppCompatActivity {

    private TextView lista;
    private Button lista_felhasznalok, lista_rendelesek, lista_foglalasok, etel_felvetel, lista_etelek;
    private Adatbazis db;
    private EditText nev,leiras,ar,kep;
    private View view;
    private LinearLayout layout;
    public static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
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

                view = getLayoutInflater().inflate(R.layout.dialog_ujetel, null);
                nev = view.findViewById(R.id.nev);
                leiras = view.findViewById(R.id.leiras);
                ar = view.findViewById(R.id.ar);
                kep = view.findViewById(R.id.kep);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminFoActivity.this);
                builder.setView(view);
                builder.setPositiveButton("Felvesz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        String etel_kep = kep.getText().toString();
                        Pattern p = Pattern.compile(URL_REGEX);
                        Matcher m = p.matcher(etel_kep);//replace with string to compare

                        if(m.find()){
                        ujetel();
                        }
                        else{
                            Toast.makeText(AdminFoActivity.this, "Hibás kép URL", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialo
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        lista_etelek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etelupdate();
            }
        });

    }
    public void init(){
        lista = findViewById(R.id.lista);
        lista_felhasznalok = findViewById(R.id.lista_felhasznalok);
        lista_foglalasok = findViewById(R.id.lista_foglalasok);
        lista_rendelesek = findViewById(R.id.lista_rendelesek);
        etel_felvetel = findViewById(R.id.etel_felvetel_intent);
        lista_etelek = findViewById(R.id.etelek_lista);
        db = new Adatbazis(this);
        layout = findViewById(R.id.update);



    }
    public void felhasznalok_listaja(){
        layout.removeAllViews();
        lista.setText("");
        final Cursor adatok = db.getAdatok("Felhasznalok_tabla");
        String szoveg = "Felhasnálók listája: \n";
        while (adatok.moveToNext()){
            final LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams(params);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            szoveg = " ID: ["+adatok.getString(0)+"]\n Felhasználónév: [" +adatok.getString(1) +"]\n " +
                      "Jelszó: [" + adatok.getString(2) +"]\n Jogosultság: [" + adatok.getString(3) +"]\n\n";
            tv.setText(szoveg);
            tv.setPadding(100,0,100,0);
            final Button adminna_tesz = new Button(this);
            adminna_tesz.setId(adatok.getInt(0));
            adminna_tesz.setLayoutParams(params);
            adminna_tesz.setGravity(Gravity.CENTER);
            if (Objects.equals(adatok.getString(3), "felhasznalo")){
                adminna_tesz.setText("Legyen admin");
            }
            else {
                adminna_tesz.setText("Legyen felhasználó");
            }
            ll.addView(tv);
            ll.addView(adminna_tesz);
            layout.addView(ll);
            adminna_tesz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor adatok2 = db.getAdatokWHERE("Felhasznalok_tabla","id", String.valueOf(adminna_tesz.getId()));
                    while (adatok2.moveToNext()) {
                        if (Objects.equals(adatok2.getString(3), "felhasznalo")) {
                            if (db.jogosultsagUpdate(adminna_tesz.getId() + "", "admin")){
                                Toast.makeText(AdminFoActivity.this, "Sikerült dik", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AdminFoActivity.this, "szar", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if(db.jogosultsagUpdate(adminna_tesz.getId() + "", "felhasznalo")){
                                Toast.makeText(AdminFoActivity.this, "Sikerült dik", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(AdminFoActivity.this, "szar", Toast.LENGTH_SHORT).show();
                            }
                        }
                        felhasznalok_listaja();
                    }
                }
            });
        }
    }
    public void rendelesek_listaja(){
        layout.removeAllViews();
        lista.setText("");
        Cursor adatok = db.getAdatok("LeadottRendeles_tabla");
        String szoveg = "Rendelések listája: \n";
        while (adatok.moveToNext()){
            szoveg += " ÉTEL_ID: ["+adatok.getString(1)+"]\n FELH_ID: [" +adatok.getString(2) +"]\n " +
                    "DÁTUM: [" + adatok.getString(3) +"]\n\n";
        }

        lista.setText(szoveg);
    }
    public void foglalasok_listaja(){
        layout.removeAllViews();
        lista.setText("");
        String szoveg = "Asztalfoglalások listája: \n";

        lista.setText(szoveg);
    }
    public void ujetel(){

        String etel_nev = nev.getText().toString();
        String etel_leiras = leiras.getText().toString();
        String etel_ar = ar.getText().toString();
        String etel_kep = kep.getText().toString();



            boolean eredmeny = db.Uj_Etel(etel_nev,etel_leiras,etel_ar,etel_kep);
            if (eredmeny){
                Toast.makeText(this, "Sikeres felvétel!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AdminFoActivity.this, RendelesActivity.class);
                startActivity(i);
                finish();
            }
            else{
                Toast.makeText(this, "Sikertelen felvétel!", Toast.LENGTH_SHORT).show();
            }

    }
    public void etelupdate(){
        final Cursor adatok = db.getAdatok("Rendelesek_tabla");
        layout.removeAllViews();
        lista.setText("");
        while (adatok.moveToNext()){

            final LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(params);
            TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(adatok.getString(1)+"\n" + adatok.getString(2)+"\n" + adatok.getString(3) +"\n" + adatok.getString(4)+"\n\n");
            ll.addView(tv);
            ll.setId(adatok.getInt(0));
            layout.addView(ll);
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View dialog = getLayoutInflater().inflate(R.layout.etel_update, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminFoActivity.this);
                    builder.setView(dialog);

                    final EditText update_nev, update_leiras, update_ar, update_url;
                    update_nev = dialog.findViewById(R.id.update_nev);
                    update_leiras = dialog.findViewById(R.id.update_leiras);
                    update_ar = dialog.findViewById(R.id.update_ar);
                    update_url = dialog.findViewById(R.id.update_url);
                    final Cursor adatok_dialog = db.getAdatokWHERE("Rendelesek_tabla","ID", String.valueOf(ll.getId()));
                    while (adatok_dialog.moveToNext()){
                        update_nev.setText(adatok_dialog.getString(1));
                        update_leiras.setText(adatok_dialog.getString(4));
                        update_ar.setText(adatok_dialog.getString(2));
                        update_url.setText(adatok_dialog.getString(3));
                    }
                    builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (db.EtelUpdate(String.valueOf(ll.getId()),update_nev.getText().toString(),update_leiras.getText().toString(),update_ar.getText().toString(),update_url.getText().toString())){
                                Toast.makeText(AdminFoActivity.this, "Sikerült more", Toast.LENGTH_SHORT).show();
                                etelupdate();
                            }
                            else{
                                Toast.makeText(AdminFoActivity.this, "bajvan", Toast.LENGTH_SHORT).show();
                            }
                            
                        }
                    });
                    builder.setNegativeButton("Mégsem", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

    }
}
