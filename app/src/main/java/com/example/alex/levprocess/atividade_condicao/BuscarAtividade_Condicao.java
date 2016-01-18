package com.example.alex.levprocess.atividade_condicao;

/**
 * Created by Alex on 30/08/2015.
 */

import android.app.Activity;
import android.content.Context;
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
import com.example.alex.levprocess.login.LoginActivity;
import com.example.alex.levprocess.processo.Processo;
import com.example.alex.levprocess.processo.ProcessoListAdapter;

import java.util.List;

/**
 * Buscar a Atividade_Condicao.
 *
 */

public class BuscarAtividade_Condicao extends Activity implements View.OnClickListener {

    private RepositorioAtividade_Condicao repositorio;
    private List<Atividade_Condicao> atividades;

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
                onBackPressed();// Fecha a tela
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
        Intent it = new Intent(BuscarAtividade_Condicao.this, CadastrarAtividade_Condicao.class);
        it.putExtra("nomeP", nomeProcesso);
        startActivityForResult(it, 2);
    }

    // Busca uma atividade_condicao pelo nome
    protected List<Atividade_Condicao> buscarProcesso(String nomeProcesso) {
        List<Atividade_Condicao> a = repositorio.buscarAtividade_CondicaoPorNomeProcesso(nomeProcesso);
        return a;
    }
}
