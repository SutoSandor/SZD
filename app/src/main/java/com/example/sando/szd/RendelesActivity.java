package com.example.sando.szd;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RendelesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Adatbazis db = new Adatbazis(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendeles);
        LinearLayout kepek_layout = (LinearLayout)findViewById(R.id.kepek_layout);
        Cursor adatok = db.getAdatok("Rendelesek_tabla");
        while (adatok.moveToNext()){
            //LINEAR LAYOUT
            LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            //KÉP
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(300,240));
            image.setImageResource(R.drawable.slider1);
            //CÍM + LEÍRÁS + ÁR
            TextView text = new TextView(this);
            String szoveg = "Név: " + adatok.getString(1) + "\nLeírás: "+ adatok.getString(4) +"\nÁr: "+ adatok.getString(2);
            text.setText(szoveg);

            layout.addView(image);
            layout.addView(text);
            kepek_layout.addView(layout);
        }
    }
}
