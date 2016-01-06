package com.example.alex.levprocess.processo;

/**
 * Created by Alex on 14/11/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.alex.levprocess.R;

/**
 * Buscar o Processo.
 *
 */

public class BuscarProcesso extends Activity implements View.OnClickListener {

    private RepositorioProcesso repositorio;
    protected static final int VOLTAR = 3;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_buscar_processo);
        ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscar);
        btBuscar.setOnClickListener(this);
    }

    public void init() {
        repositorio = new RepositorioProcesso(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, VOLTAR, 0, "Voltar").setIcon(R.drawable.limpar);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // Clicou no menu
        switch (item.getItemId()) {
            case VOLTAR:
                // Abre a tela para buscar o processo pelo nome
                startActivity(new Intent(this, CadastroProcesso.class));
                break;
        }
        return true;
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
        EditText nome = (EditText) findViewById(R.id.etNomeProcesso);
        EditText responsavel = (EditText) findViewById(R.id.etResponsavel);
        EditText papel = (EditText) findViewById(R.id.etPapel);
        EditText objetivo = (EditText) findViewById(R.id.etObjetivo);
        EditText entradas = (EditText) findViewById(R.id.etEntradas);
        EditText saidas = (EditText) findViewById(R.id.etSaidas);
        //EditText roteiro = (EditText) findViewById(R.id.etRoteiro);
        //EditText validacao = (EditText) findViewById(R.id.etValidacao);

        // Recupera o nome do processo
        String nomeProcesso = nome.getText().toString();
        // Busca o processo pelo nome
        Processo processo = buscarProcesso(nomeProcesso);
        if (processo != null) {
            // Atualiza os campos com o resultado
            nome.setText(processo.nome);
            responsavel.setText(processo.responsavel);
            papel.setText(processo.papel);
            objetivo.setText(processo.objetivo);
            entradas.setText(processo.entradas);
            saidas.setText(processo.saidas);
            //roteiro.setText(processo.roteiro);
        } else {
            // Limpa os campos
            nome.setText("");
            responsavel.setText("");
            papel.setText("");
            objetivo.setText("");
            entradas.setText("");
            saidas.setText("");
            //roteiro.setText("");

            Toast.makeText(BuscarProcesso.this, "Nenhum processo encontrado", Toast.LENGTH_SHORT).show();
        }
    }
    // Busca um processo pelo nome
    protected Processo buscarProcesso(String nomeProcesso) {
        Processo processo = repositorio.buscarProcessoPorNome(nomeProcesso);
        return processo;
    }
}
