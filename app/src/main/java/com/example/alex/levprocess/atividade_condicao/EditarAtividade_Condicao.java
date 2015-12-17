package com.example.alex.levprocess.atividade_condicao;

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
import com.example.alex.levprocess.atividade_condicao.Atividade_Condicao.Atividade_Condicaos;

public class EditarAtividade_Condicao extends Activity {

    static final int RESULT_SALVAR = 1;
    static final int RESULT_EXCLUIR = 2;
    private EditText campoNome;
    private EditText campoResponsavel;
    private EditText campoDepartamento;
    private EditText campoTipo;
    private EditText campoDetalhamento;
    private EditText campoDocumento;
    private Long id;
    private RepositorioAtividade_Condicao repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_editar_atividade_condicao);
        campoNome = (EditText) findViewById(R.id.campoNomeAtividade);
        campoResponsavel = (EditText) findViewById(R.id.campoResponsavel);
        campoDepartamento = (EditText) findViewById(R.id.campoDepartamento);
        campoTipo = (EditText) findViewById(R.id.campoTipo);
        campoDetalhamento = (EditText) findViewById(R.id.campoDetalhamento);
        campoDocumento = (EditText) findViewById(R.id.campoDocumento);
        id = null;
        Bundle extras = getIntent().getExtras();
        // Se for para Editar, recuperar os valores ...
        if (extras != null) {
            id = extras.getLong(Atividade_Condicaos._ID);
            if (id != null) {
                // e uma edicao, busca a atividade_condicao...
                Atividade_Condicao atividade_condicao = buscarAtividade_Condicao(id);
                campoNome.setText(atividade_condicao.nome);
                campoResponsavel.setText(atividade_condicao.responsavel);
                campoDepartamento.setText(atividade_condicao.departamento);
                campoTipo.setText(atividade_condicao.tipo);
                campoDetalhamento.setText(atividade_condicao.detalhamento);
                campoDocumento.setText(atividade_condicao.documento);
            }
        }

        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });
        // Listener para salvar a atividade_condicao
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
            // Listener para excluir a atividade_condicao
            btExcluir.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    excluir();
                }
            });
        }
    }

    public void init() {
        repositorio = new RepositorioAtividade_Condicao(this);
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
        Atividade_Condicao atividade_condicao = new Atividade_Condicao();
        if (id != null) {
            atividade_condicao.id = id;// E uma atualizacao
        }
        atividade_condicao.nome = campoNome.getText().toString();
        atividade_condicao.responsavel = campoResponsavel.getText().toString();
        atividade_condicao.departamento = campoDepartamento.getText().toString();
        atividade_condicao.tipo = campoTipo.getText().toString();
        atividade_condicao.detalhamento = campoDetalhamento.getText().toString();
        atividade_condicao.documento = campoDocumento.getText().toString();
        salvarAtividade_Condicao(atividade_condicao);// Salvar
        setResult(RESULT_OK, new Intent());	// OK
        // Fecha a tela
        finish();
    }

    public void excluir() {
        if (id != null) {
            excluirAtividade_Condicao(id);
        }
        // OK
        setResult(RESULT_OK, new Intent());
        // Fecha a tela
        finish();
    }

    // Buscar a atividade_condicao pelo id
    protected Atividade_Condicao buscarAtividade_Condicao(long id) {
        return repositorio.buscarAtividade_Condicao(id);
    }

    // Salvar a atividade_condicao
    protected void salvarAtividade_Condicao(Atividade_Condicao atividade_condicao) {
        repositorio.salvar(atividade_condicao);
        Toast.makeText(this, "Atividade_Condi��o cadastrado com sucesso", Toast.LENGTH_LONG).show();
    }

    // Excluir a atividade_condicao
    protected void excluirAtividade_Condicao(long id) {
        repositorio.deletar(id);
    }
}