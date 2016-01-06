package com.example.alex.levprocess.processo;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Alex on 12/11/2015.
 */

public class Processo {

    public static String[] colunas = new String[] { Processos._ID, Processos.NOME, Processos.RESPONSAVEL, Processos.PAPEL, Processos.OBJETIVO, Processos.CONDICAO, Processos.ENTRADAS, Processos.SAIDAS, Processos.ROTEIRO, Processos.VALIDACAO };

    public static final String AUTHORITY = "br.livro.android.provider.processos";

    public long id;
    public String nome;
    public String responsavel;
    public String papel;
    public String objetivo;
    public String condicao;
    public String entradas;
    public String saidas;
    public String roteiro;
    public String validacao;

    public Processo() {

    }

    public Processo(String nome, String responsavel, String papel, String objetivo, String condicao, String entradas, String saidas) {
        super();
        this.nome = nome;
        this.responsavel = responsavel;
        this.papel = papel;
        this.objetivo = objetivo;
        this.condicao = condicao;
        this.entradas = entradas;
        this.saidas = saidas;
    }

    public Processo(long id, String nome, String responsavel) {
        super();
        this.id = id;
        this.nome = nome;
        this.responsavel = responsavel;
    }

    public static final class Processos implements BaseColumns {

        // Nao pode instanciar esta Classe
        private Processos() {
        }

        // content://br.livro.android.provider.processo/processos
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/processos");
        // Mime Type para todos os processo
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.processos";
        // Mime Type para um unico processo
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.processos";
        // Ordenacao default para inserir no order by
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";
        public static final String RESPONSAVEL = "responsavel";
        public static final String PAPEL = "papel";
        public static final String OBJETIVO = "objetivo";
        public static final String CONDICAO = "condicao";
        public static final String ENTRADAS = "entradas";
        public static final String SAIDAS = "saidas";
        public static final String ROTEIRO = "roteiro";
        public static final String VALIDACAO = "validacao";

        // Metodo que constroi uma Uri para um Processo especifico, com o seu id
        // A Uri e no formato "content://br.livro.android.provider.processo/processos/id"
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /processos
            Uri uriProcesso = ContentUris.withAppendedId(Processos.CONTENT_URI, id);
            return uriProcesso;
        }
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Responsável: " + responsavel + ", Papel: " + papel + ", Objetivo: " + objetivo + ", Condição: " + condicao + ", Entradas: " + entradas + ", Saídas: " + saidas;
    }
}