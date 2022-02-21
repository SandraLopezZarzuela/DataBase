package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseAssistant extends SQLiteOpenHelper {
    private static final String COMMENTS_TABLE_CREATE
            = "CREATE TABLE comments(_id INTEGER PRIMARY KEY AUTOINCREMENT, user TEXT, comment TEXT)";
    private static final String DB_NAME = "comments.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;

    public DataBaseAssistant(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertar(String nombre,String comentario){
        ContentValues cv = new ContentValues();
        cv.put("user", nombre);
        cv.put("comment", comentario);
        db.insert("comments", null, cv);
    }

    public void borrar(int id){
        String[] args = new String[]{String.valueOf(id)};
        db.delete("comments", "_id=?", args);
    }

    public ArrayList<Comentario> getComments(){
        ArrayList<Comentario> lista=new ArrayList<Comentario>();
        Cursor cursor = db.rawQuery("select _id, user,comment from comments", null);
        if (cursor != null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                @SuppressLint("Range") String user = cursor.getString(cursor.getColumnIndex("user"));
                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));
                @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex("_id"));
                Comentario com =new Comentario(id,user,comment);
                lista.add(com);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }
}