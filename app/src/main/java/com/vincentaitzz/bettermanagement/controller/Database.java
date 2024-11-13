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
    private static final String Table_Name = "usuarios"; // Cambiado a "usuarios"
    private static final String Col_1 = "ID";
    private static final String Col_2 = "NOMBRE"; // Cambiado a "NOMBRE"
    private static final String Col_3 = "CONTRASENA"; // Nueva columna para contraseña
    private static final String Col_4 = "CORREO"; // Nueva columna para correo electrónico
    private static final String Col_5 = "IMAGEN"; // Nueva columna para la imagen de perfil

    public Database(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Table_Name + "(" +
                        Col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Col_2 + " TEXT," +
                        Col_3 + " TEXT," +
                        Col_4 + " TEXT," +
                        Col_5 + " TEXT DEFAULT 'default_profile.png')" // Imagen por defecto
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
        onCreate(db);
    }

    public boolean insertUser(String nombre, String contrasena, String correo, String imagen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_2, nombre);
        contentValues.put(Col_3, contrasena);
        contentValues.put(Col_4, correo);
        contentValues.put(Col_5, imagen != null ? imagen : "default_profile.png"); // Usa imagen por defecto si es null
        long result = db.insert(Table_Name, null, contentValues);

        return result != -1;
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> users = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Name, null);
        if (res.moveToFirst()) {
            do {
                users.add(res.getString(1)); // Agregar solo el nombre
            } while (res.moveToNext());
        }
        return users;
    }

    public void deleteUser(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Name, Col_2 + " = ?", new String[]{nombre});
    }

    public void updateUser(String oldNombre, String newNombre, String newContrasena, String newCorreo, String newImagen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_2, newNombre);
        contentValues.put(Col_3, newContrasena);
        contentValues.put(Col_4, newCorreo);

        // Si se proporciona una nueva imagen, actualizarla; de lo contrario, mantener la actual
        if (newImagen != null && !newImagen.isEmpty()) {
            contentValues.put(Col_5, newImagen);
        }

        db.update(Table_Name, contentValues, Col_2 + " = ?", new String[]{oldNombre});
    }
}