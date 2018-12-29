package com.example.sando.szd;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class RendelesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Adatbazis db = new Adatbazis(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendeles);
        LinearLayout kepek_layout = (LinearLayout)findViewById(R.id.kepek_layout);
        final Cursor adatok = db.getAdatok("Rendelesek_tabla");
        while (adatok.moveToNext()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(100,10,0,0);
            //LINEAR LAYOUT
            final LinearLayout layout = new LinearLayout(this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layout.setId(adatok.getInt(0));
            layout.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    Toast.makeText(RendelesActivity.this, layout.getId()+" ", Toast.LENGTH_SHORT).show();
                }
            });
            //KÉP
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(350,240));
            Picasso.get().load(adatok.getString(3)).into(image);
            //CÍM + LEÍRÁS + ÁR
            TextView text = new TextView(this);
            text.setLayoutParams(params);
            String szoveg = "Név: " + adatok.getString(1) + "\nLeírás: "+ adatok.getString(4) +"\nÁr: "+ adatok.getString(2);
            text.setText(szoveg);

            layout.addView(image);
            layout.addView(text);
            kepek_layout.addView(layout);
        }
    }
}
