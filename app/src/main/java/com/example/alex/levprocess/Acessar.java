package com.example.alex.levprocess;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Acessar extends SQLiteOpenHelper {

    //Declaracao da tabela e banco no sqlite
    private final static int versao = 1;
    private final static  String nome = "sistema.sqlite";
    private static final String create = "CREATE TABLE login(id_login INTEGER PRIMARY KEY AUTOINCREMENT , usuario VARCHAR(30) NOT NULL, senha VARCHAR(30);";
    protected SQLiteDatabase dataBase;


    public Acessar(Context context) {
        super(context, nome, null, versao);
        // TODO Auto-generated constructor stub
    }

    @Override
    //Criacao do banco
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(create);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
    public SQLiteDatabase getDatabase(){
        if(dataBase == null){
            dataBase = getWritableDatabase();
        }
        return dataBase;
    }
}
