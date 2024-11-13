package com.vincentaitzz.bettermanagement.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vincentaitzz.bettermanagement.model.Schedule;
import com.vincentaitzz.bettermanagement.model.User;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String Database_Name = "BetterManagement.db";

    // Tabla de usuarios
    private static final String Table_Usuarios = "usuarios";
    private static final String Col_1 = "ID";
    private static final String Col_2 = "NOMBRE";
    private static final String Col_3 = "CONTRASENA";
    private static final String Col_4 = "CORREO";
    private static final String Col_5 = "IMAGEN";

    // Tabla de horarios
    private static final String Table_Horarios = "horarios";
    private static final String Col_Hora_Inicio = "HORA_INICIO";
    private static final String Col_Hora_Termino = "HORA_TERMINO";
    private static final String Col_Persona = "PERSONA";
    private static final String Col_Fecha = "FECHA";
    private static final String Col_Usuario_ID = "USUARIO_ID"; // Clave foránea

    public Database(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        db.execSQL(
                "CREATE TABLE " + Table_Usuarios + "(" +
                        Col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Col_2 + " TEXT," +
                        Col_3 + " TEXT," +
                        Col_4 + " TEXT," +
                        Col_5 + " TEXT DEFAULT 'default_profile.png')" // Imagen por defecto
        );

        // Crear tabla de horarios
        db.execSQL(
                "CREATE TABLE " + Table_Horarios + "(" +
                        Col_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Col_Hora_Inicio + " TEXT," +
                        Col_Hora_Termino + " TEXT," +
                        Col_Persona + " TEXT," +
                        Col_Fecha + " TEXT," +
                        Col_Usuario_ID + " INTEGER," + // Clave foránea
                        "FOREIGN KEY(" + Col_Usuario_ID + ") REFERENCES " + Table_Usuarios + "(" + Col_1 + "))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table_Usuarios);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Horarios);
        onCreate(db);
    }

    // Métodos para gestionar usuarios

    public boolean insertUser(User usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_2, usuario.getNombre());
        contentValues.put(Col_3, usuario.getContrasena());
        contentValues.put(Col_4, usuario.getCorreo());
        contentValues.put(Col_5, usuario.getImagen() != null ? usuario.getImagen() : "default_profile.png");

        long result = db.insert(Table_Usuarios, null, contentValues);
        return result != -1;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Table_Usuarios, null);

        if (res.moveToFirst()) {
            do {
                User user = new User(
                        res.getInt(0), // ID
                        res.getString(1), // Nombre
                        res.getString(2), // Contraseña
                        res.getString(3), // Correo
                        res.getString(4)  // Imagen
                );
                users.add(user);
            } while (res.moveToNext());
        }

        return users;
    }

    public void deleteUser(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Usuarios, Col_2 + " = ?", new String[]{nombre});
    }

    public void updateUser(User usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_2, usuario.getNombre());
        contentValues.put(Col_3, usuario.getContrasena());
        contentValues.put(Col_4, usuario.getCorreo());

        if (usuario.getImagen() != null && !usuario.getImagen().isEmpty()) {
            contentValues.put(Col_5, usuario.getImagen());
        }

        db.update(Table_Usuarios, contentValues, Col_1 + " = ?", new String[]{String.valueOf(usuario.getId())});
    }

    // Métodos para gestionar horarios

    public boolean insertHorario(Schedule horario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_Hora_Inicio, horario.getHoraInicio());
        contentValues.put(Col_Hora_Termino, horario.getHoraTermino());
        contentValues.put(Col_Persona, horario.getPersona());
        contentValues.put(Col_Fecha, horario.getFecha());
        contentValues.put(Col_Usuario_ID, horario.getUsuarioId()); // Asignar el ID del usuario

        long result = db.insert(Table_Horarios, null, contentValues);

        return result != -1;
    }

    public ArrayList<Schedule> getHorariosPorUsuario(int usuarioId) {
        ArrayList<Schedule> horarios = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + Table_Horarios + " WHERE " + Col_Usuario_ID + "=?", new String[]{String.valueOf(usuarioId)});

        if (res.moveToFirst()) {
            do {
                Schedule horario = new Schedule(
                        res.getInt(0), // ID
                        res.getString(1), // Hora Inicio
                        res.getString(2), // Hora Termino
                        res.getString(3), // Persona
                        res.getString(4), // Fecha
                        res.getInt(5)     // Usuario ID
                );
                horarios.add(horario);
            } while (res.moveToNext());
        }

        return horarios;
    }

    public void deleteHorario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Table_Horarios, Col_1 + " = ?", new String[]{String.valueOf(id)});
    }

    public void updateHorario(Schedule horario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Col_Hora_Inicio, horario.getHoraInicio());
        contentValues.put(Col_Hora_Termino, horario.getHoraTermino());
        contentValues.put(Col_Persona, horario.getPersona());
        contentValues.put(Col_Fecha, horario.getFecha());

        db.update(Table_Horarios, contentValues, Col_1 + " = ?", new String[]{String.valueOf(horario.getId())});
    }
}