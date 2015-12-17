package com.example.alex.levprocess.roteiro;

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

import com.example.alex.levprocess.roteiro.Roteiro.Roteiros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * <pre>
 * Repositorio para roteiros que utiliza o SQLite internamente
 *
 * Para visualizar o banco pelo adb shell:
 *
 * &gt;&gt; sqlite3 /data/data/br.livro.android.exemplos.banco/databases/BancoRoteiros
 *
 * &gt;&gt; Mais info dos comandos em: http://www.sqlite.org/sqlite.html
 *
 * &gt;&gt; .exit para sair
 *
 * </pre>
 *
 *
 */
public class RepositorioRoteiro {
    private static final String CATEGORIA = "levprocess";
    // Nome do banco
    private static final String NOME_BANCO = "lev";
    private static final String NOME_BANCO_CPY = "lev";
    private Context myCtx;
    public static String DB_PATH = "/data/data/com.example.alex.levprocess/databases/";

    // Nome da tabela
    public static final String NOME_TABELA = "roteiro";
    protected SQLiteDatabase db;



    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
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

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
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

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
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


    public RepositorioRoteiro(Context ctx) {
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
    protected RepositorioRoteiro() {
        // Apenas para criar uma subclasse...
    }
    // Salva o roteiro, insere um novo ou atualiza
    public long salvar(Roteiro roteiro) {
        long id = roteiro.id;
        if (id != 0) {
            atualizar(roteiro);
        } else {
            // Insere novo
            id = inserir(roteiro);
        }
        return id;
    }

    // Insere um novo roteiro
    public long inserir(Roteiro roteiro) {
        ContentValues values = new ContentValues();
        values.put(Roteiros.NOME, roteiro.nome);
        values.put(Roteiros.PROCESSO, roteiro.processo);
        values.put(Roteiros.ATIVIDADE_CONDICAO, roteiro.atividade_condicao);
        long id = inserir(values);
        return id;
    }
    // Insere um novo roteiro
    public long inserir(ContentValues valores) {
        long id = db.insert(NOME_TABELA, "", valores);
        return id;
    }
    // Atualiza o roteiro no banco. O id do roteiro e utilizado.
    public int atualizar(Roteiro roteiro) {
        ContentValues values = new ContentValues();
        values.put(Roteiros.NOME, roteiro.nome);
        values.put(Roteiros.PROCESSO, roteiro.processo);
        values.put(Roteiros.ATIVIDADE_CONDICAO, roteiro.atividade_condicao);
        String _id = String.valueOf(roteiro.id);
        String where = Roteiros._ID + "=?";
        String[] whereArgs = new String[] { _id };
        int count = atualizar(values, where, whereArgs);
        return count;
    }

    // Atualiza o roteiro com os valores abaixo
    // A clausula where e utilizada para identificar o roteiro a ser atualizado
    public int atualizar(ContentValues valores, String where, String[] whereArgs) {
        int count = db.update(NOME_TABELA, valores, where, whereArgs);
        Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
        return count;
    }
    // Deleta o roteiro com o id fornecido
    public int deletar(long id) {
        String where = Roteiros._ID + "=?";
        String _id = String.valueOf(id);
        String[] whereArgs = new String[] { _id };
        int count = deletar(where, whereArgs);
        return count;
    }
    // Deleta o roteiro com os argumentos fornecidos
    public int deletar(String where, String[] whereArgs) {
        int count = db.delete(NOME_TABELA, where, whereArgs);
        Log.i(CATEGORIA, "Deletou [" + count + "] registros");
        return count;
    }

    // Busca o roteiro pelo id
    public Roteiro buscarRoteiro(long id) {
        // select * from roteiro where _id=?
        Cursor c = db.query(true, NOME_TABELA, Roteiro.colunas, Roteiros._ID + "=" + id,
                null, null, null, null, null);
        if (c.getCount() > 0) {
            // Posicinoa no primeiro elemento do cursor
            c.moveToFirst();
            Roteiro roteiro = new Roteiro();
            // Le os dados
            roteiro.id = c.getLong(0);
            roteiro.nome = c.getString(1);
            roteiro.processo = c.getString(2);
            roteiro.atividade_condicao = c.getString(3);
            return roteiro;
        }
        return null;
    }
    // Retorna um cursor com todos os roteiros
    public Cursor getCursor() {
        try {
            // select * from roteiros
            return db.query(NOME_TABELA, Roteiro.colunas, null, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar os roteiros: " + e.toString());
            return null;
        }
    }

    // Retorna uma lista com todos os roteiros
    public List<Roteiro> listarRoteiros() {
        Cursor c = getCursor();
        List<Roteiro> roteiros = new ArrayList<Roteiro>();
        if (c.moveToFirst()) {
            // Recupera os indices das colunas
            int idxId = c.getColumnIndex(Roteiros._ID);
            int idxNome = c.getColumnIndex(Roteiros.NOME);
            int idxProcesso = c.getColumnIndex(Roteiros.PROCESSO);
            int idxAtividade_Condicao = c.getColumnIndex(Roteiros.ATIVIDADE_CONDICAO);
            // Loop ate o final
            do {
                Roteiro roteiro = new Roteiro();
                roteiros.add(roteiro);
                // recupera os atributos do roteiro
                roteiro.id = c.getLong(idxId);
                roteiro.nome = c.getString(idxNome);
                roteiro.processo = c.getString(idxProcesso);
                roteiro.atividade_condicao = c.getString(idxAtividade_Condicao);
            } while (c.moveToNext());
        }
        return roteiros;
    }
    // Busca o roteiro pelo nome "select * from roteiro where nome=?"
    public Roteiro buscarRoteiroPorNome(String nome) {
        Roteiro roteiro = null;
        try {
            // Idem a: SELECT _id,nome,processo,atividade_condicao from ROTEIRO where nome = ?
            Cursor c = db.query(NOME_TABELA, Roteiro.colunas, Roteiros.NOME + "='" + nome + "'",
                    null, null, null, null);
            // Se encontrou...
            if (c.moveToNext()) {
                roteiro = new Roteiro();
                // utiliza os metodos getLong(), getString(), getString(), etc para recuperar os valores
                roteiro.id = c.getLong(0);
                roteiro.nome = c.getString(1);
                roteiro.processo = c.getString(2);
                roteiro.atividade_condicao = c.getString(3);
            }
        } catch (SQLException e) {
            Log.e(CATEGORIA, "Erro ao buscar o roteiro pelo nome: " + e.toString());
            return null;
        }
        return roteiro;
    }

    // Busca um roteiro utilizando as configuracoes definidas no
    // SQLiteQueryBuilder
    // Utilizado pelo Content Provider de roteiro
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