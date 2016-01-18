package com.example.alex.levprocess.processo;

/**
 * Created by Alex on 12/12/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.atividade_condicao.CadastrarAtividade_Condicao;
import com.example.alex.levprocess.processo.Processo.Processos;

public class DadosProcesso extends Activity {

    static final int RESULT_SALVAR = 1;
    static final int RESULT_EXCLUIR = 2;
    private TextView nome, responsavel, papel, objetivo, condicao, entradas, saidas;
    private Long id;
    private RepositorioProcesso repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_dados_processo);
        nome = (TextView) findViewById(R.id.tvNomeProcesso);
        responsavel = (TextView) findViewById(R.id.tvResponsavel);
        papel = (TextView) findViewById(R.id.tvPapel);
        objetivo = (TextView) findViewById(R.id.tvObjetivo);
        condicao = (TextView) findViewById(R.id.tvCondicao);
        entradas = (TextView) findViewById(R.id.tvEntradas);
        saidas = (TextView) findViewById(R.id.tvSaidas);
        id = null;
        Bundle extras = getIntent().getExtras();
        // Se for para Editar, recuperar os valores ...
        if (extras != null) {
            id = extras.getLong(Processos._ID);
            if (id != null) {
                // e uma edicao, busca o processo...
                Processo c = buscarProcesso(id);
                nome.setText(c.nome);
                responsavel.setText(c.responsavel);
                papel.setText(c.papel);
                objetivo.setText(c.objetivo);
                condicao.setText(c.condicao);
                entradas.setText(c.entradas);
                saidas.setText(c.saidas);
            }
        }

        Button btVoltar = (Button) findViewById(R.id.btVoltar);
        btVoltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });

        Button btListarAtividadeCondicao = (Button) findViewById(R.id.lAtividade_Condicao);
        btListarAtividadeCondicao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String nomeP = nome.getText().toString();
                Intent it = new Intent(DadosProcesso.this, CadastrarAtividade_Condicao.class);
                it.putExtra("nomeP", nomeP);
                startActivityForResult(it, 2);
            }
        });
    }

    public void init() {
        repositorio = new RepositorioProcesso(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancela para nao ficar nada na tela pendente
        setResult(RESULT_CANCELED);
        // Fecha a tela
        finish();
    }

    public void salvar() {
        Processo processo = new Processo();
        if (id != null) {
            processo.id = id;// E uma atualizacao
        }
        processo.nome = nome.getText().toString();
        processo.responsavel = responsavel.getText().toString();
        processo.papel = papel.getText().toString();
        processo.objetivo = objetivo.getText().toString();
        processo.condicao = condicao.getText().toString();
        processo.entradas = entradas.getText().toString();
        processo.saidas = saidas.getText().toString();
        salvarProcesso(processo);// Salvar
        setResult(RESULT_OK, new Intent());	// OK
        // Fecha a tela
        finish();
    }

    public void excluir() {
        if (id != null) {
            excluirProcesso(id);
        }
        // OK
        setResult(RESULT_OK, new Intent());
        // Fecha a tela
        finish();
    }

    // Buscar o processo pelo id
    protected Processo buscarProcesso(long id) {
        return repositorio.buscarProcesso(id);
    }

    // Salvar o processo
    protected void salvarProcesso(Processo processo) {
        repositorio.salvar(processo);
    }

    // Excluir o processo
    protected void excluirProcesso(long id) {
        repositorio.deletar(id);
    }
}