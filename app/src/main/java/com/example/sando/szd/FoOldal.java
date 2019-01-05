package com.example.sando.szd;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

public class FoOldal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    public Toolbar toolbar;
    public TabLayout tabLayout;
    public ViewPager pager;
    private ViewPager viewPager;
    private Button  foglalas, rendeles, kapcsolat, bejelentkezes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fo_oldal);
        init();

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


        bejelentkezes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBejelentkezes();
            }
        });
        kapcsolat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startKapcsolat();
            }
        });
        rendeles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRendeles();
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
        Intent i = new Intent(FoOldal.this,RendelesActivity.class);
        startActivity(i);
        finish();
    }
    public void startKapcsolat(){
        Intent i = new Intent(FoOldal.this,KapcsolatActivity.class);
        startActivity(i);
        finish();
    }
    public void startBejelentkezes(){
        Intent intent = new Intent(FoOldal.this, BeActivity.class);
        startActivity(intent);
        finish();
    }
}
