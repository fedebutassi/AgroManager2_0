package com.example.agromanager2_0.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.agromanager2_0.lotes.Lote;
import com.google.android.gms.maps.model.LatLng;

import java.util.*;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =8;
    private static final String DATABASE_NOMBRE = "agromanager.db";

    private static final String TABLE_USUARIOS = "Usuarios";
    public static final String TABLE_CAMPOS = "Campos";
    private static final String TABLE_CULTIVOS = "Cultivos";
    private static final String TABLE_LABORES = "Labores";
    private static final String TABLE_PRODUCTOS_APLICADOS = "Productos_Aplicados";


    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USUARIOS = "CREATE TABLE " + TABLE_USUARIOS + " ("
                + "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT NOT NULL, "
                + "apellido TEXT NOT NULL, "
                + "fecha_nacimiento DATE, "
                + "localidad TEXT, "
                + "email TEXT UNIQUE NOT NULL, "
                + "password TEXT NOT NULL"
                + ");";
        db.execSQL(CREATE_TABLE_USUARIOS);

        String CREATE_TABLE_CAMPOS = "CREATE TABLE " + TABLE_CAMPOS + " ("
                + "id_campo INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre_campo TEXT NOT NULL, "
                + "hectareas REAL, "
                + "latitud DECIMAL(10,8),"
                + "longitud DECIMAL(10,8),"
                + "id_usuario INTEGER, "
                + "FOREIGN KEY (id_usuario) REFERENCES " + TABLE_USUARIOS + "(id_usuario)"
                + ");";
        db.execSQL(CREATE_TABLE_CAMPOS);


        String CREATE_TABLE_CULTIVOS = "CREATE TABLE " + TABLE_CULTIVOS + " ("
                + "id_cultivo INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre_cultivo TEXT NOT NULL, "
                + "id_campo INTEGER, "
                + "area_cubierta REAL, "
                + "fecha_cultivo DATE, "
                + "descripcion_cultivo TEXT NOT NULL, "
                + "FOREIGN KEY (id_campo) REFERENCES " + TABLE_CAMPOS + "(id_campo)"
                + ");";
        db.execSQL(CREATE_TABLE_CULTIVOS);

        String CREATE_TABLE_LABORES = "CREATE TABLE " + TABLE_LABORES + " ("
                + "id_labor INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre_labor TEXT NOT NULL, "
                + "descripcion_labor TEXT, "
                + "fecha_labor DATE, "
                + "id_campo INTEGER, "
                + "FOREIGN KEY (id_campo) REFERENCES " + TABLE_CAMPOS + "(id_campo)"
                + ");";
        db.execSQL(CREATE_TABLE_LABORES);

        String CREATE_TABLE_PRODUCTOS_APLICADOS = "CREATE TABLE " + TABLE_PRODUCTOS_APLICADOS + " ("
                + "id_aplicacion INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre_producto TEXT NOT NULL, "
                + "cantidad_aplicada REAL, "
                + "fecha_aplicacion DATE, "
                + "id_campo INTEGER, "
                + "zona_cubierta_hectareas REAL, "
                + "descripcion_aplicacion TEXT, "
                + "FOREIGN KEY (id_campo) REFERENCES " + TABLE_CAMPOS + "(id_campo)"
                + ");";
        db.execSQL(CREATE_TABLE_PRODUCTOS_APLICADOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMPOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CULTIVOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LABORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS_APLICADOS);
        onCreate(db);
    }

    public boolean insertarDatosUsuario(String nombre, String apellido, String fechaNacimiento, String localidad, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre", nombre);
        contentValues.put("apellido", apellido);
        contentValues.put("fecha_nacimiento", fechaNacimiento);
        contentValues.put("localidad", localidad);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = db.insert(TABLE_USUARIOS, null, contentValues);
        return result != -1; // Retorna true si la inserción fue exitosa
    }

    public boolean insertarDatosLotes(String nombre_campo, double hectareas, double latitud, double longitud){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre_campo",nombre_campo);
        contentValues.put("hectareas",hectareas);
        contentValues.put("latitud", latitud);
        contentValues.put("longitud", longitud);
        long result = db.insert(TABLE_CAMPOS, null, contentValues);
        return result != -1;
    }


    public boolean insertarDatosCultivos(String nombre_cultivo, String fecha_cultivo, String area_cubierta, String descripcion_cultivo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre_cultivo", nombre_cultivo);
        contentValues.put("fecha_cultivo", fecha_cultivo);
        contentValues.put("area_cubierta", area_cubierta);
        contentValues.put("descripcion_cultivo", descripcion_cultivo);

        long result = db.insert(TABLE_CULTIVOS, null, contentValues);
        return result != -1;
    }

    public boolean insertarDatosLabores(String nombre_labor, String descripcion_labor,String fecha_labor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre_labor", nombre_labor);
        contentValues.put("descripcion_labor", descripcion_labor);
        contentValues.put("fecha_labor", fecha_labor);

        long result = db.insert(TABLE_LABORES, null, contentValues);
        return result != -1;
    }

    public boolean insertarDatosAplicaciones(String nombre_producto, String cantidad_aplicada, String fecha_aplicacion, String zona_cubierta_hectareas, String descripcion_aplicacion){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("nombre_producto", nombre_producto);
        contentValues.put("cantidad_aplicada", cantidad_aplicada);
        contentValues.put("fecha_aplicacion", fecha_aplicacion);
        contentValues.put("zona_cubierta_hectareas", zona_cubierta_hectareas);
        contentValues.put("descripcion_aplicacion", descripcion_aplicacion);

        long result = db.insert(TABLE_PRODUCTOS_APLICADOS, null, contentValues);
        return result != -1;
    }

    public boolean validarUsuario(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Usuarios WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }



    public Cursor obtenerDatosUsuario(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT nombre, apellido, fecha_nacimiento, localidad, email FROM " + TABLE_USUARIOS + " WHERE email = ?";
        return db.rawQuery(query, new String[]{email});
    }

    public Cursor obtenerLotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Campos", null); // Ajusta la consulta según tu estructura de base de datos
    }
    public Cursor obtenerLabores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Labores", null); // Ajusta la consulta según tu estructura de base de datos
    }

    public Cursor obtenerCultivos(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Cultivos", null);
    }

    public Cursor obtenerAplicaciones(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Productos_Aplicados", null);
    }


    public List<Lote> obtenerLotesLista() {
        List<Lote> listaLotes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CAMPOS, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String nombreCampo = cursor.getString(1); // nombre_campo está en la columna 1
                double hectareas = cursor.getDouble(2); // hectareas está en la columna 2
                double latitud = cursor.getDouble(3); // latitud está en la columna 3
                double longitud = cursor.getDouble(4); // longitud está en la columna 4
                LatLng ubicacion = new LatLng(latitud, longitud);

                // Crear un nuevo objeto Lote (o Campo, si ese es el nombre adecuado) y agregarlo a la lista
                Lote lote = new Lote(nombreCampo, hectareas, ubicacion);
                listaLotes.add(lote);
            }
            cursor.close();
        }

        return listaLotes;
    }

}
