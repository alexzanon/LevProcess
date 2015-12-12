package com.example.alex.levprocess.login;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.alex.levprocess.banco.DataBase;

public class LoginDao extends DataBase {

    private final String TABLE = "usuario";

    public LoginDao(Context context) {
        super(context);
    }

    public void insert(Login login) throws Exception {
        ContentValues values = new ContentValues();
        values.put("usuario", login.getEtUsuario());
        values.put("senha", login.getEtSenha());
        getDatabase().insert(TABLE, null, values);
    }

    public void update(Login login) throws Exception {
        ContentValues values = new ContentValues();
        values.put("usuario", login.getEtUsuario());
        values.put("senha", login.getEtSenha());
        getDatabase().update(TABLE, values, "id = ?",
                new String[] { "" + login.getId_login() });
    }

    public Login findById(Integer id) {
        String sql = "SELECT * FROM" + TABLE + "WHERE id = ?";
        String[] selectionArgs = new String[] { "" + id };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaLogin(cursor);
    }

    public List<Login> findAll() throws Exception {
        List<Login> retorno = new ArrayList<Login>();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaLogin(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

    private Login montaLogin(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
        String senha = cursor.getString(cursor.getColumnIndex("senha"));

        return new Login(id, usuario, senha);

    }
    public Login findById(String usuario , String senha) {
        String sql = "SELECT * FROM login" + TABLE + "WHERE usuario = ? AND senha = ?";
        String[] selectionArgs = new String[]{usuario , senha};
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaLogin(cursor);
    }
}
