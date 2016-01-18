package com.example.alex.levprocess.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 07/01/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "/data/data/com.example.alex.levprocess/databases/lev";

    private static final String TABLE_LABELS = "processo";

    private static final String KEY_ID = "_id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_RESPONSAVEL = "responsavel";
    private static final String KEY_PAPEL = "papel";
    private static final String KEY_OBJETIVO = "objetivo";
    private static final String KEY_CONDICAO = "condicao";
    private static final String KEY_ENTRADAS = "entradas";
    private static final String KEY_SAIDAS = "saidas";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_LABELS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOME + " TEXT)" + KEY_RESPONSAVEL + " TEXT)" + KEY_PAPEL + " TEXT)" + KEY_OBJETIVO + " TEXT)" + KEY_CONDICAO + " TEXT)"
                + KEY_ENTRADAS + " TEXT)" + KEY_SAIDAS + " TEXT)";
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LABELS);

        onCreate(db);
    }

    public void insertLabel(String label){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOME, label);
        values.put(KEY_RESPONSAVEL, label);
        values.put(KEY_PAPEL, label);
        values.put(KEY_OBJETIVO, label);
        values.put(KEY_CONDICAO, label);
        values.put(KEY_ENTRADAS, label);
        values.put(KEY_SAIDAS, label);

        db.insert(TABLE_LABELS, null, values);
        db.close();
    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT  * FROM " + TABLE_LABELS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }
}