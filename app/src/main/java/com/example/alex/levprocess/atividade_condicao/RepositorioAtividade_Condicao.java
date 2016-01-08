package com.example.alex.levprocess.atividade_condicao;

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
import java.util.concurrent.atomic.AtomicBoolean;

import com.example.alex.levprocess.atividade_condicao.Atividade_Condicao.Atividade_Condicaos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioAtividade_Condicao {
    private static final String CATEGORIA = "levprocess";
    // Nome do banco
    private static final String NOME_BANCO = "lev";
    private static final String NOME_BANCO_CPY = "lev";
    private Context myCtx;
    public static String DB_PATH = "/data/data/com.example.alex.levprocess/databases/";

    // Nome da tabela
    public static final String NOME_TABELA = "atividade_condicao";
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


    public RepositorioAtividade_Condicao(Context ctx) {
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
    protected RepositorioAtividade_Condicao() {
        // Apenas para criar uma subclasse...
    }
    // Salva a atividade_condicao, insere um novo ou atualiza
    public long salvar(Atividade_Condicao atividade_condicao) {
        long id = atividade_condicao.id;
        if (id != 0) {
            atualizar(atividade_condicao);
        } else {
            // Insere novo
            id = inserir(atividade_condicao);
        }
        return id;
    }

    // Insere uma nova atividade_condicao
    public long inserir(Atividade_Condicao atividade_condicao) {
        ContentValues values = new ContentValues();
        values.put(Atividade_Condicaos.NOME, atividade_condicao.nome);
        values.put(Atividade_Condicaos.NOME_PROCESSO, atividade_condicao.nome_processo);
        values.put(Atividade_Condicaos.RESPONSAVEL, atividade_condicao.responsavel);
        values.put(Atividade_Condicaos.DEPARTAMENTO, atividade_condicao.departamento);
        values.put(Atividade_Condicaos.TIPO, atividade_condicao.tipo);
        values.put(Atividade_Condicaos.DETALHAMENTO, atividade_condicao.detalhamento);
        values.put(Atividade_Condicaos.DOCUMENTO, atividade_condicao.documento);

        long id = inserir(values);
        return id;
    }
    // Insere uma nova atividade_condicao
    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }
    // Atualiza a atividade_condicao no banco. O id da atividade_condicao e utilizado.
    public int atualizar(Atividade_Condicao atividade_condicao) {
        ContentValues values = new ContentValues();
        values.put(Atividade_Condicaos.NOME, atividade_condicao.nome);
        values.put(Atividade_Condicaos.NOME_PROCESSO, atividade_condicao.nome_processo);
        values.put(Atividade_Condicaos.RESPONSAVEL, atividade_condicao.responsavel);
        values.put(Atividade_Condicaos.DEPARTAMENTO, atividade_condicao.departamento);
        values.put(Atividade_Condicaos.TIPO, atividade_condicao.tipo);
        values.put(Atividade_Condicaos.DETALHAMENTO, atividade_condicao.detalhamento);
        values.put(Atividade_Condicaos.DOCUMENTO, atividade_condicao.documento);
        String _id = String.valueOf(atividade_condicao.id);
        String where = Atividade_Condicaos._ID + "=?";
        String[] whereArgs = new String[] { _id };
        int count = atualizar(values, where, whereArgs);
        return count;
    }

    // Atualiza a atividade_condicao com os valores abaixo
    // A clausula where e utilizada para identificar a atividade_condicao a ser atualizado
    public int atualizar(ContentValues valores, String where, String[] whereArgs) {
        int count = db.update(NOME_TABELA, valores, where, whereArgs);
        Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
        return count;
    }
    // Deleta a atividade_condicao com o id fornecido
    public int deletar(long id) {
        String where = Atividade_Condicaos._ID + "=?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
        int count = deletar(where, whereArgs);
        return count;
    }
    // Deleta a atividade_condicao com os argumentos fornecidos
    public int deletar(String where, String[] whereArgs) {
        int count = db.delete(NOME_TABELA, where, whereArgs);
        Log.i(CATEGORIA, "Deletou [" + count + "] registros");
        return count;
    }

    // Busca a atividade_condicao pelo id
    public Atividade_Condicao buscarAtividade_Condicao(long id) {
        // select * from atividade_condicao where _id=?
        Cursor c = db.query(true, NOME_TABELA, Atividade_Condicao.colunas, Atividade_Condicaos._ID + "=" + id,
                null, null, null, null, null);
        if (c.getCount() > 0) {
            // Posicinoa no primeiro elemento do cursor
            c.moveToFirst();
            Atividade_Condicao atividade_condicao = new Atividade_Condicao();
            // Le os dados
            atividade_condicao.id = c.getLong(0);
            atividade_condicao.nome = c.getString(1);
            atividade_condicao.nome_processo = c.getString(2);
            atividade_condicao.responsavel = c.getString(3);
            atividade_condicao.departamento = c.getString(4);
            atividade_condicao.tipo = c.getString(5);
            atividade_condicao.detalhamento = c.getString(6);
            atividade_condicao.documento = c.getString(7);
            return atividade_condicao;
        }
        return null;
    }
    // Retorna um cursor com todas as atividades_condicaos
    public Cursor getCursor() {
        try {
            // select * from atividade_condicao
            return db.query(NOME_TABELA, Atividade_Condicao.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar as Atividade_Condicao: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todas as atividade_condicao
    public List<Atividade_Condicao> listarAtividade_Condicao() {
        Cursor c = getCursor();
        List<Atividade_Condicao> atividade_condicaos = new ArrayList<Atividade_Condicao>();
        if (c.moveToFirst()) {
            // Recupera os indices das colunas
            int idxId = c.getColumnIndex(Atividade_Condicaos._ID);
            int idxNome = c.getColumnIndex(Atividade_Condicaos.NOME);
            int idxProcesso = c.getColumnIndex(Atividade_Condicaos.NOME_PROCESSO);
            int idxResponsavel = c.getColumnIndex(Atividade_Condicaos.RESPONSAVEL);
            int idxDepartamento = c.getColumnIndex(Atividade_Condicaos.DEPARTAMENTO);
            int idxTipo = c.getColumnIndex(Atividade_Condicaos.TIPO);
            int idxDetalhamento = c.getColumnIndex(Atividade_Condicaos.DETALHAMENTO);
            int idxDocumento = c.getColumnIndex(Atividade_Condicaos.DOCUMENTO);
            // Loop ate o final
            do {
                Atividade_Condicao atividade_condicao = new Atividade_Condicao();
                atividade_condicaos.add(atividade_condicao);
                // recupera os atributos da atividade_condicao
                atividade_condicao.id = c.getLong(idxId);
                atividade_condicao.nome = c.getString(idxNome);
                atividade_condicao.nome_processo = c.getString(idxProcesso);
                atividade_condicao.responsavel = c.getString(idxResponsavel);
                atividade_condicao.departamento = c.getString(idxDetalhamento);
                atividade_condicao.tipo = c.getString(idxTipo);
                atividade_condicao.detalhamento = c.getString(idxDepartamento);
                atividade_condicao.documento = c.getString(idxDocumento);
            } while (c.moveToNext());
        }
        return atividade_condicaos;
    }
    // Busca a atividade_condicao pelo nome "select * from atividade_condicao where nome=?"
    public Atividade_Condicao buscarAtividade_CondicaoPorNomeProcesso(String nomeProcesso) {
        Atividade_Condicao atividade_condicao = null;
        try {
            // Idem a: SELECT _id,nome,responsavel,departamento,tipo,detalhamento,documento from ATIVIDADE_CONDICAO where nome = ?
            Cursor c = db.query(NOME_TABELA, Atividade_Condicao.colunas, Atividade_Condicaos.NOME_PROCESSO + "='" + nomeProcesso + "'",
                    null, null, null, null);
            // Se encontrou...
            if (c.moveToNext()) {
                atividade_condicao = new Atividade_Condicao();
                // utiliza os metodos getLong(), getString(), getString(), etc para recuperar os valores
                atividade_condicao.id = c.getLong(0);
                atividade_condicao.nome = c.getString(1);
                atividade_condicao.nome_processo = c.getString(2);
                atividade_condicao.responsavel = c.getString(3);
                atividade_condicao.departamento = c.getString(4);
                atividade_condicao.tipo = c.getString(5);
                atividade_condicao.detalhamento = c.getString(6);
                atividade_condicao.documento = c.getString(7);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar a atividade/condicao pelo nome: " + e.toString());
            return null;
        }
        return atividade_condicao;
    }

    // Busca uma atividade_condicao utilizando as configuracoes definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de atividade_condicao
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