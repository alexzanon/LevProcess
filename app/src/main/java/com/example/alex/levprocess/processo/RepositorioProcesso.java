package com.example.alex.levprocess.processo;

/**
 * Created by Alex on 12/11/2015.
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.alex.levprocess.processo.Processo.Processos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioProcesso {
    private static final String CATEGORIA = "levprocess";
    // Nome do banco
    private static final String NOME_BANCO = "lev";
    private static final String NOME_BANCO_CPY = "lev";
    private Context myCtx;
    public static String DB_PATH = "/data/data/com.example.alex.levprocess/databases/";

    // Nome da tabela
    public static final String NOME_TABELA = "processo";
    protected SQLiteDatabase db;

    public void createDataBase() throws IOException{
        // for first database;
        boolean dbExist = checkDataBase(NOME_BANCO);
        if(!dbExist){
            try {
                copyDataBase(NOME_BANCO_CPY,NOME_BANCO);
            } catch (Exception e) {
                throw new Error("Erro copiando o banco de dados");
            }
        }
    }

    private boolean checkDataBase(String DB){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,  SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){}

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase(String assetfile,String DB) {

        //Open your local db as the input stream
        InputStream myInput = null;
        //Open the empty db as the output stream
        OutputStream myOutput = null;
        try {
            myInput = myCtx.getAssets().open(assetfile);

            // Path to the just created empty db
            String outFileName = DB_PATH + DB;

            myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }


            System.out.println("***************************************");
            System.out.println("####### Data base copied ##############");
            System.out.println("***************************************");


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            //Close the streams
            try {
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }


    public RepositorioProcesso(Context ctx) {
        try {
            //Open the database
            this.myCtx = ctx;

            //createDataBase();
            // Abre o banco de dados ja existente
            String myPath = DB_PATH + NOME_BANCO;
            //db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
            db.setVersion(1);
            Log.e(CATEGORIA, db.getPath());
            db.setLockingEnabled(true);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    protected RepositorioProcesso() {
        // Apenas para criar uma subclasse...
    }
    // Salva o processo, insere um novo ou atualiza
    public long salvar(Processo processo) {
        long id = processo.id;
        if (id != 0) {
            atualizar(processo);
        } else {
            // Insere novo
            id = inserir(processo);
        }
        return id;
    }

    // Insere um novo processo
    public long inserir(Processo processo) {
        ContentValues values = new ContentValues();
        values.put(Processos.NOME, processo.nome);
        values.put(Processos.RESPONSAVEL, processo.responsavel);
        values.put(Processos.PAPEL, processo.papel);
        values.put(Processos.OBJETIVO, processo.objetivo);
        values.put(Processos.CONDICAO, processo.condicao);
        values.put(Processos.ENTRADAS, processo.entradas);
        values.put(Processos.SAIDAS, processo.saidas);
        long id = inserir(values);
        return id;
    }
    // Insere um novo processo
    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }
    // Atualiza o processo no banco. O id do processo e utilizado.
    public int atualizar(Processo processo) {
        ContentValues values = new ContentValues();
        values.put(Processos.NOME, processo.nome);
        values.put(Processos.RESPONSAVEL, processo.responsavel);
        values.put(Processos.PAPEL, processo.papel);
        values.put(Processos.OBJETIVO, processo.objetivo);
        values.put(Processos.CONDICAO, processo.condicao);
        values.put(Processos.ENTRADAS, processo.entradas);
        values.put(Processos.SAIDAS, processo.saidas);
        //values.put(Processos.ROTEIRO, processo.roteiro);
        String _id = String.valueOf(processo.id);
        String where = Processos._ID + "=?";
        String[] whereArgs = new String[] { _id };
        int count = atualizar(values, where, whereArgs);
        return count;
    }

    // Atualiza o processo com os valores abaixo
    // A clausula where e utilizada para identificar o processo a ser atualizado
    public int atualizar(ContentValues valores, String where, String[] whereArgs) {
        int count = db.update(NOME_TABELA, valores, where, whereArgs);
        Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
        return count;
    }
    // Deleta o processo com o id fornecido
    public int deletar(long id) {
        String where = Processos._ID + "=?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
        int count = deletar(where, whereArgs);
        return count;
    }
    // Deleta o processo com os argumentos fornecidos
    public int deletar(String where, String[] whereArgs) {
        int count = db.delete(NOME_TABELA, where, whereArgs);
        Log.i(CATEGORIA, "Deletou [" + count + "] registros");
        return count;
    }

    // Busca o processo pelo id
    public Processo buscarProcesso(long id) {
        // select * from processo where _id=?
        Cursor c = db.query(true, NOME_TABELA, Processo.colunas, Processos._ID + "=" + id,
                null, null, null, null, null);
        if (c.getCount() > 0) {
            // Posicinoa no primeiro elemento do cursor
            c.moveToFirst();
            Processo processo = new Processo();
            // Le os dados
            processo.id = c.getLong(0);
            processo.nome = c.getString(1);
            processo.responsavel = c.getString(2);
            processo.papel = c.getString(3);
            processo.objetivo = c.getString(4);
            processo.condicao = c.getString(5);
            processo.entradas = c.getString(6);
            processo.saidas = c.getString(7);
            //processo.roteiro = c.getString(8);
            return processo;
        }
        return null;
    }
    // Retorna um cursor com todos os processos
    public Cursor getCursor() {
        try {
            // select * from processos
            return db.query(NOME_TABELA, Processo.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar os processos: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todos os processos
    public List<Processo> listarProcessos() {
        Cursor c = getCursor();
        List<Processo> processos = new ArrayList<Processo>();
        if (c.moveToFirst()) {
            // Recupera os indices das colunas
            int idxId = c.getColumnIndex(Processos._ID);
            int idxNome = c.getColumnIndex(Processos.NOME);
            int idxResponsavel = c.getColumnIndex(Processos.RESPONSAVEL);
            // Loop ate o final
            do {
                Processo processo = new Processo();
                processos.add(processo);
                // recupera os atributos do processo
                processo.id = c.getLong(idxId);
                processo.nome = c.getString(idxNome);
                processo.responsavel = c.getString(idxResponsavel);
            } while (c.moveToNext());
        }
        return processos;
    }
    // Busca o processo pelo nome "select * from processo where nome=?"
    public Processo buscarProcessoPorNome(String nome) {
        Processo processo = null;
        try {
            // Idem a: SELECT _id,nome,responsavel,papel from PROCESSO where nome = ?
            Cursor c = db.query(NOME_TABELA, Processo.colunas, Processos.NOME + "='" + nome + "'",
                    null, null, null, null);
            // Se encontrou...
            if (c.moveToNext()) {
                processo = new Processo();
                // utiliza os metodos getLong(), getString(), getString(), etc para recuperar os valores
                processo.id = c.getLong(0);
                processo.nome = c.getString(1);
                processo.responsavel = c.getString(2);
                processo.papel = c.getString(3);
                processo.objetivo = c.getString(4);
                processo.condicao = c.getString(5);
                processo.entradas = c.getString(6);
                processo.saidas = c.getString(7);
                //processo.roteiro = c.getString(8);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar o processo pelo nome: " + e.toString());
            return null;
        }
        return processo;
    }

    // Busca um processo utilizando as configuracoes definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de processo
    public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection,
                        String selection, String[] selectionArgs,
                        String groupBy, String having, String orderBy) {
        Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs,
                groupBy, having, orderBy);
        return c;
    }

    // Fecha o banco
    public void fechar() {
        // fecha o banco de dados
        if (db != null) {
            db.close();
        }
    }
}