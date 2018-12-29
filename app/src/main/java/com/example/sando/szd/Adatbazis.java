package com.example.sando.szd;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Adatbazis extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Adatbazis.db";
    public static final String FELH_TABLENAME = "Felhasznalok_tabla";
    public static final String REND_TABLENAME = "Rendelesek_tabla";
    public static final String FELH_COL_1 = "ID";
    public static final String FELH_COL_2 = "FELHASZNALONEV";
    public static final String FELH_COL_3 = "JELSZO";
    public static final String FELH_COL_4 = "JOGOSULTSAG";
    public static final String REND_COL_1 = "ID";
    public static final String REND_COL_2 = "ETEL_NEV";
    public static final String REND_COL_3 = "AR";
    public static final String REND_COL_4 = "KEP";
    public static final String REND_COL_5 = "LEIRAS";

    Adatbazis(Context context){
        super(context,DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FELH_TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FELHASZNALONEV TEXT, JELSZO TEXT, JOGOSULTSAG TEXT)");
        db.execSQL("CREATE TABLE " + REND_TABLENAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, ETEL_NEV TEXT, AR DOUBLE, KEP TEXT, LEIRAS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FELH_TABLENAME);
        db.execSQL("DROP TABLE IF EXISTS " + REND_TABLENAME);
    }
    public boolean Regisztracio(String felhasznalonev, String jelszo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FELH_COL_2, felhasznalonev);
        contentValues.put(FELH_COL_3, jelszo);
        contentValues.put(FELH_COL_4, "admin");

        long eredmeny = db.insert(FELH_TABLENAME, null, contentValues);

        if (eredmeny == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean Uj_Etel(String nev, String leiras, String ar, String kep){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REND_COL_2, nev);
        contentValues.put(REND_COL_3, ar);
        contentValues.put(REND_COL_4, kep);
        contentValues.put(REND_COL_5, leiras);

        long eredmeny = db.insert(REND_TABLENAME, null, contentValues);

        if (eredmeny == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor getAdatok(String tablanev){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor adatok = db.rawQuery("Select * from " + tablanev,null);
        return adatok;
    }
}