package com.example.alex.levprocess.atividade_condicao;

/**
 * Created by Alex on 30/08/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alex.levprocess.R;

/**
 * Buscar a Atividade_Condicao.
 *
 */
public class BuscarAtividade_Condicao extends Activity implements View.OnClickListener {
    private RepositorioAtividade_Condicao repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_buscar_atividade_condicao);
        ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscar);
        btBuscar.setOnClickListener(this);
    }

    public void init() {
        repositorio = new RepositorioAtividade_Condicao(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Cancela para nao ficar nada pendente na tela
        setResult(RESULT_CANCELED);
        // Fecha a tela
        finish();
    }
    public void onClick(View view) {
        EditText nome = (EditText) findViewById(R.id.etNomeRoteiro);
        EditText responsavel = (EditText) findViewById(R.id.etResponsavel);
        EditText departamento = (EditText) findViewById(R.id.etDepartamento);
        EditText tipo = (EditText) findViewById(R.id.etTipo);
        EditText detalhamento = (EditText) findViewById(R.id.etDetalhamento);
        EditText documento = (EditText) findViewById(R.id.etDocumento);

        // Recupera o nome da Atividade_Condicao
        String nomeAtividade_Condicao = nome.getText().toString();
        // Busca a Atividade_Condicao pelo nome
        Atividade_Condicao a = buscarNome(nomeAtividade_Condicao);
        if (a != null) {
            // Atualiza os campos com o resultado
            nome.setText(a.nome);
            responsavel.setText(a.responsavel);
            departamento.setText(a.departamento);
            tipo.setText(a.tipo);
            detalhamento.setText(a.detalhamento);
            documento.setText(a.documento);
        } else {
            // Limpa os campos
            nome.setText("");
            responsavel.setText("");
            departamento.setText("");
            tipo.setText("");
            detalhamento.setText("");
            documento.setText("");

            Toast.makeText(BuscarAtividade_Condicao.this, "Nenhuma atividade_condicao encontrado", Toast.LENGTH_SHORT).show();
        }
    }
    // Busca uma atividade_condicao pelo nome
    protected Atividade_Condicao buscarNome(String nomeAtividade_Condicao) {
        Atividade_Condicao a = repositorio.buscarAtividade_CondicaoPorNome(nomeAtividade_Condicao);
        return a;
    }
}
