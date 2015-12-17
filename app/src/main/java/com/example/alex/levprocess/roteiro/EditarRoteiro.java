package com.example.alex.levprocess.roteiro;

/**
 * Created by Alex on 16/12/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.roteiro.Roteiro.Roteiros;

public class EditarRoteiro extends Activity {

    static final int RESULT_SALVAR = 1;
    static final int RESULT_EXCLUIR = 2;
    private EditText campoNome;
    private EditText campoProcesso;
    private EditText campoAtividade_Condicao;
        private Long id;
    private RepositorioRoteiro repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_editar_roteiro);
        campoNome = (EditText) findViewById(R.id.campoNome);
        campoProcesso = (EditText) findViewById(R.id.campoProcesso);
        campoAtividade_Condicao = (EditText) findViewById(R.id.campoAtividade_Condicao);
        id = null;
        Bundle extras = getIntent().getExtras();
        // Se for para Editar, recuperar os valores ...
        if (extras != null) {
            id = extras.getLong(Roteiros._ID);
            if (id != null) {
                // e uma edicao, busca o roteiro...
                Roteiro roteiro = buscarRoteiro(id);
                campoNome.setText(roteiro.nome);
                campoProcesso.setText(roteiro.processo);
                campoAtividade_Condicao.setText(roteiro.atividade_condicao);
            }
        }

        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });
        // Listener para salvar o roteiro
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
            // Listener para excluir o roteiro
            btExcluir.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    excluir();
                }
            });
        }
    }

    public void init() {
        repositorio = new RepositorioRoteiro(this);
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
        Roteiro roteiro = new Roteiro();
        if (id != null) {
            roteiro.id = id;// E uma atualizacao
        }
        roteiro.nome = campoNome.getText().toString();
        roteiro.processo = campoProcesso.getText().toString();
        roteiro.atividade_condicao = campoAtividade_Condicao.getText().toString();
        salvarRoteiro(roteiro);// Salvar
        setResult(RESULT_OK, new Intent());	// OK
        // Fecha a tela
        finish();
    }

    public void excluir() {
        if (id != null) {
            excluirRoteiro(id);
        }
        // OK
        setResult(RESULT_OK, new Intent());
        // Fecha a tela
        finish();
    }

    // Buscar o roteiro pelo id
    protected Roteiro buscarRoteiro(long id) {
        return repositorio.buscarRoteiro(id);
    }

    // Salvar o roteiro
    protected void salvarRoteiro(Roteiro roteiro) {
        repositorio.salvar(roteiro);
        Toast.makeText(this, "Roteiro cadastrado com sucesso", Toast.LENGTH_LONG).show();
    }

    // Excluir o roteiro
    protected void excluirRoteiro(long id) {
        repositorio.deletar(id);
    }
}