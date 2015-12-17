package com.example.alex.levprocess.atividade_condicao;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Alex on 08/12/2015.
 */
public class Atividade_Condicao {

    public static String[] colunas = new String[]{Atividade_Condicaos._ID, Atividade_Condicaos.NOME, Atividade_Condicaos.RESPONSAVEL, Atividade_Condicaos.DEPARTAMENTO, Atividade_Condicaos.TIPO, Atividade_Condicaos.DETALHAMENTO, Atividade_Condicaos.DOCUMENTO};

    public static final String AUTHORITY = "br.livro.android.provider.atividade_condicao";

    public long id;
    public String nome;
    public String responsavel;
    public String departamento;
    public String tipo;
    public String detalhamento;
    public String documento;

    public Atividade_Condicao() {

    }

    public Atividade_Condicao(String nome, String responsavel, String departamento, String tipo, String detalhamento, String documento) {
        super();
        this.nome = nome;
        this.responsavel = responsavel;
        this.departamento = departamento;
        this.tipo = tipo;
        this.detalhamento = detalhamento;
        this.documento = documento;
    }

    public Atividade_Condicao(long id, String nome, String tipo) {
        super();
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public static final class Atividade_Condicaos implements BaseColumns {

        // Nao pode instanciar esta Classe
        private Atividade_Condicaos() {
        }

        // content://br.livro.android.provider.atividade_condicao/atividade_condicaos
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/atividade_condicaos");
        // Mime Type para todos as atividade_condicaos
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.atividade_condicaos";
        // Mime Type para uma unica atividade_condicao
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.atividade_condicaos";
        // Ordenacao default para inserir no order by
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";
        public static final String RESPONSAVEL = "responsavel";
        public static final String DEPARTAMENTO = "departamento";
        public static final String TIPO = "tipo";
        public static final String DETALHAMENTO = "detalhamento";
        public static final String DOCUMENTO = "documento";

        // Metodo que constroi uma Uri para uma Atividade_Condicao especifico, com o seu id
        // A Uri e no formato "content://br.livro.android.provider.atividade_condicao/atividade_condicaos/id"
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /atividade_condicaos
            Uri uriAtividade_condicao = ContentUris.withAppendedId(Atividade_Condicaos.CONTENT_URI, id);
            return uriAtividade_condicao;
        }
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Responsável: " + responsavel + ", Departamento: " + departamento + ", Tipo: " + tipo + ", Detalhamento: " + detalhamento + ", Documento: " + documento;
    }
}