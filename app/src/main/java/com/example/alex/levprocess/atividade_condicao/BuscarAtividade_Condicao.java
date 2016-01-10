package com.example.alex.levprocess.atividade_condicao;

/**
 * Created by Alex on 30/08/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.banco.DatabaseHandler;
import com.example.alex.levprocess.cliente.MenuCliente;

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
        Button btBuscar = (Button) findViewById(R.id.btBuscar);
        btBuscar.setOnClickListener(this);

        Button btCancelar = (Button) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });
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
        EditText nome_processo = (EditText) findViewById(R.id.etNome_processo);

        // Recupera o nome do Processo
        String nomeProcesso = nome_processo.getText().toString();
        // Busca o Processo pelo nome

        Atividade_Condicao a = buscarProcesso(nomeProcesso);
        if (a != null) {
            // Atualiza os campos com o resultado
            //nome.setText(a.nome);
            nome_processo.setText(a.nome_processo);
            /*responsavel.setText(a.responsavel);
            departamento.setText(a.departamento);
            tipo.setText(a.tipo);
            detalhamento.setText(a.detalhamento);
            documento.setText(a.documento);*/
        } else {
            // Limpa os campos
            //nome.setText("");
            nome_processo.setText("");
            /*responsavel.setText("");
            departamento.setText("");
            tipo.setText("");
            detalhamento.setText("");
            documento.setText("");*/

            Toast.makeText(BuscarAtividade_Condicao.this, "Nenhuma processo encontrado, para listar as atividades/condicoes", Toast.LENGTH_SHORT).show();
        }
    }
    // Busca uma atividade_condicao pelo nome
    protected Atividade_Condicao buscarProcesso(String nomeProcesso) {
        Atividade_Condicao a = repositorio.buscarAtividade_CondicaoPorNomeProcesso(nomeProcesso);
        return a;
    }
}
