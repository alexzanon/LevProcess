package com.example.alex.levprocess.roteiro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.alex.levprocess.R;

/**
 * Created by Alex on 24/12/2015.
 */
public class NovoRoteiro extends Activity {

    private EditText nome, processo, atividade_condicao;
    private Long id;
    private RepositorioRoteiro repositorio;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.novo_roteiro);
        nome = (EditText) findViewById(R.id.etNomeRoteiro);
        processo = (EditText) findViewById(R.id.etNomeProcesso);
        id = null;

        ImageButton btCancelar = (ImageButton) findViewById(R.id.btCancelar);
        //Button cdsRoteiro = (Button) findViewById(R.id.botaoRoteiro);

        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });

        ImageButton btSalvar = (ImageButton) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void init() {
        repositorio = new RepositorioRoteiro(this);
    }

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
        roteiro.nome = nome.getText().toString();
        roteiro.processo = processo.getText().toString();
        salvarRoteiro(roteiro);// Salvar
        setResult(RESULT_OK, new Intent());	// OK
        // Fecha a tela
        finish();
    }

    protected void salvarRoteiro(Roteiro roteiro) {
        repositorio.salvar(roteiro);
    }
}
