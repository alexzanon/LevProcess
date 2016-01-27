package com.example.alex.levprocess.atividade_condicao;

/**
 * Created by Alex on 16/01/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.atividade_condicao.Atividade_Condicao.Atividade_Condicaos;
import com.example.alex.levprocess.modelador.MenuModelador;

public class DadosAtividade_Condicao extends Activity {

    private TextView campoNome, campoNomeProcesso, campoResponsavel, campoDepartamento, campoDetalhamento, campoDocumento;
    private TextView campoTipo;
    private Long id;
    private RepositorioAtividade_Condicao repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_dados_atividade_condicao);
        campoNome = (TextView) findViewById(R.id.campoNomeAtividade);
        campoNomeProcesso = (TextView) findViewById(R.id.campoNomeProcesso);
        campoResponsavel = (TextView) findViewById(R.id.campoResponsavel);
        campoDepartamento = (TextView) findViewById(R.id.campoDepartamento);
        campoTipo = (TextView) findViewById(R.id.campoTipo);
        campoDetalhamento = (TextView) findViewById(R.id.campoDetalhamento);
        campoDocumento = (TextView) findViewById(R.id.campoDocumento);
        id = null;

        Bundle extras = getIntent().getExtras();
        // Se for para Editar, recuperar os valores ...
        if (extras != null) {
            id = extras.getLong(Atividade_Condicaos._ID);
            if (id != null) {
                // e uma edicao, busca a atividade_condicao...
                Atividade_Condicao atividade_condicao = buscarAtividade_Condicao(id);
                campoNome.setText(atividade_condicao.nome);
                campoNomeProcesso.setText(atividade_condicao.nome_processo);
                campoResponsavel.setText(atividade_condicao.responsavel);
                campoDepartamento.setText(atividade_condicao.departamento);
                campoTipo.setText(atividade_condicao.tipo);
                campoDetalhamento.setText(atividade_condicao.detalhamento);
                campoDocumento.setText(atividade_condicao.documento);
            }
        }

        Button btCancelar = (Button) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });
        Button btVoltarMenu = (Button) findViewById(R.id.btVoltarMenu);
        btVoltarMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(DadosAtividade_Condicao.this, MenuModelador.class);
                startActivityForResult(it, 2);
            }
        });
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

    // Buscar a atividade_condicao pelo id
    protected Atividade_Condicao buscarAtividade_Condicao(long id) {
        return repositorio.buscarAtividade_Condicao(id);
    }
}