package com.example.alex.levprocess.roteiro;

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
 * Buscar o Roteiro.
 *
 */
public class BuscarRoteiro extends Activity implements View.OnClickListener {
    private RepositorioRoteiro repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_buscar_roteiro);
        ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscar);
        btBuscar.setOnClickListener(this);
    }

    public void init() {
        repositorio = new RepositorioRoteiro(this);
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
        EditText processo = (EditText) findViewById(R.id.etProcesso);

        // Recupera o nome do roteiro
        String nomeRoteiro = nome.getText().toString();
        // Busca o roteiro pelo nome
        Roteiro r = buscarNome(nomeRoteiro);
        if (r != null) {
            // Atualiza os campos com o resultado
            nome.setText(r.nome);
            processo.setText(r.processo);
        } else {
            // Limpa os campos
            nome.setText("");
            processo.setText("");

            Toast.makeText(BuscarRoteiro.this, "Nenhum roteiro encontrado", Toast.LENGTH_SHORT).show();
        }
    }
    // Busca um roteiro pelo nome
    protected Roteiro buscarNome(String nomeRoteiro) {
        Roteiro r = repositorio.buscarRoteiroPorNome(nomeRoteiro);
        return r;
    }
}
