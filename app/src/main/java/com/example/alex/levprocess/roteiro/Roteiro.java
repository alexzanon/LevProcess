package com.example.alex.levprocess.roteiro;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by Alex on 16/12/2015.
 */

public class Roteiro {

    public static String[] colunas = new String[] { Roteiros._ID, Roteiros.NOME, Roteiros.PROCESSO };

    public static final String AUTHORITY = "br.livro.android.provider.roteiro";

    public long id;
    public String nome;
    public String processo;

    public Roteiro() {

            }

    public Roteiro(String nome, String processo) {
            super();
            this.nome = nome;
            this.processo = processo;
            }

    public Roteiro(long id, String nome, String processo) {
            super();
            this.id = id;
            this.nome = nome;
            this.processo = processo;
            }

    public static final class Roteiros implements BaseColumns {

        // Nao pode instanciar esta Classe
        private Roteiros() {
        }

        // content://br.livro.android.provider.roteiro/roteiros
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/roteiros");
        // Mime Type para todos os roteiros
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.roteiros";
        // Mime Type para um unico roteiro
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.roteiros";
        // Ordenacao default para inserir no order by
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";
        public static final String PROCESSO = "processo";

        // Metodo que constroi uma Uri para um Roteiro especifico, com o seu id
        // A Uri e no formato "content://br.livro.android.provider.roteiro/roteiros/id"
        public static Uri getUriId(long id) {
            // Adiciona o id na URI default do /roteiros
            Uri uriRoteiro = ContentUris.withAppendedId(Roteiros.CONTENT_URI, id);
            return uriRoteiro;
        }
    }

        @Override
        public String toString() {
            return "Nome: " + nome + ", Processo: " + processo;
        }
    }