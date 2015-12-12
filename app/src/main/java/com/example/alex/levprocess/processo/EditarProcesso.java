package com.example.alex.levprocess.processo;

/**
 * Created by Alex on 14/11/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.processo.Processo.Processos;

public class EditarProcesso extends Activity {

    static final int RESULT_SALVAR = 1;
    static final int RESULT_EXCLUIR = 2;
    private EditText nome, responsavel, papel, objetivo, condicao, entradas, saidas, roteiro, validacao;
    private Long id;
    private RepositorioProcesso repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_editar_processo);
        nome = (EditText) findViewById(R.id.etNomeProcesso);
        responsavel = (EditText) findViewById(R.id.etResponsavel);
        papel = (EditText) findViewById(R.id.etPapel);
        objetivo = (EditText) findViewById(R.id.etObjetivo);
        condicao = (EditText) findViewById(R.id.etCondicao);
        entradas = (EditText) findViewById(R.id.etEntradas);
        saidas = (EditText) findViewById(R.id.etSaidas);
        roteiro = (EditText) findViewById(R.id.etRoteiro);
        validacao = (EditText) findViewById(R.id.etValidacao);
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
                roteiro.setText(c.roteiro);
                validacao.setText(c.validacao);
            }
        }

        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });
        // Listener para salvar o processo
        ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                salvar();
            }
        });
        ImageButton btExcluir = (ImageButton) findViewById(R.id.btExcluir);
        if (id == null) {
            // Se id esta nulo, nao pode excluir
            btExcluir.setVisibility(View.INVISIBLE);
        } else {
            // Listener para excluir o processo
            btExcluir.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    excluir();
                }
            });
        }
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
        processo.roteiro = roteiro.getText().toString();
        processo.validacao = validacao.getText().toString();
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