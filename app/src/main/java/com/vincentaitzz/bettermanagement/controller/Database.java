package com.vincentaitzz.bettermanagement.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String Database_Name = "BetterManagement.db";
    private static final String Table_Name = "compania";
    private static final String Col_1 = "ID";
    private static final String Col_2 = "NAME";

    public Database(Context context){
        super(context,Database_Name,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Table_Name + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ Table_Name);
        onCreate(db);
    }

    public boolean insertCompany(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_2,nombre);
        long result = db.insert(Table_Name,null,contentValues);

        return result != -1;
    }

    public ArrayList<String> getAllCompany(){
        ArrayList<String> company = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Name, null);
        if(res.moveToFirst()){
            do{
                company.add(res.getString(1));
            }while (res.moveToNext());
        }
        return company;
    }

    public void deleteCompany(String company){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table_Name,"NAME = ?", new String[]{company});
    }

    public void updateCompany(String oldCompany, String newCompany){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_2,newCompany);
        db.update(Table_Name,contentValues,"NAME = ?",new String[]{oldCompany});
    }
}
