package com.example.sando.szd;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FoOldal extends AppCompatActivity {

    private ViewPager viewPager;
    private Button  foglalas, rendeles, kapcsolat, bejelentkezes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fo_oldal);
        init();
        bejelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoOldal.this, BeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        kapcsolat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoOldal.this, KapcsolatActivity.class);
                startActivity(i);
                finish();
            }
        });
        rendeles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoOldal.this,RendelesActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void init(){
        viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        foglalas = findViewById(R.id.foglalas);
        rendeles = findViewById(R.id.rendeles);
        kapcsolat = findViewById(R.id.kapcsolat);
        bejelentkezes = findViewById(R.id.bejelentkezes);
    }
}
