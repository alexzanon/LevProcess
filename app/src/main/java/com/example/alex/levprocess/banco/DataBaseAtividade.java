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

public class DataBaseAtividade extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "/data/data/com.example.alex.levprocess/databases/lev";

    // Labels table name
    private static final String TABLE_LABELS = "atividade_condicao";

    // Labels Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_NOME_PROCESSO = "nome_processo";
    private static final String KEY_RESPONSAVEL = "responsavel";
    private static final String KEY_DEPARTAMENTO = "departamento";
    private static final String KEY_TIPO = "tipo";
    private static final String KEY_DETALHAMENTO = "detalhamento";
    private static final String KEY_DOCUMENTO = "documento";

    public DataBaseAtividade (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_LABELS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOME + " TEXT)" + KEY_NOME_PROCESSO + " TEXT)" + KEY_RESPONSAVEL + " TEXT)" + KEY_DEPARTAMENTO + " TEXT)" + KEY_TIPO + " TEXT)"
                + KEY_DETALHAMENTO + " TEXT)" + KEY_DOCUMENTO + " TEXT)";
        db.execSQL(CREATE_CATEGORIES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LABELS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Inserting new lable into lables table
     * */
    public void insertLabel(String label){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOME, label);
        values.put(KEY_NOME_PROCESSO, label);
        values.put(KEY_RESPONSAVEL, label);
        values.put(KEY_DEPARTAMENTO, label);
        values.put(KEY_TIPO, label);
        values.put(KEY_DETALHAMENTO, label);
        values.put(KEY_DOCUMENTO, label);

        // Inserting Row
        db.insert(TABLE_LABELS, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LABELS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}