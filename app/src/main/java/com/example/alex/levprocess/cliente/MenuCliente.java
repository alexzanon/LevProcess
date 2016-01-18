package com.example.alex.levprocess.cliente;

/**
 * Created by Alex on 17/11/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.atividade_condicao.BuscarAtividade_Condicao;
import com.example.alex.levprocess.atividade_condicao.NovaAtividade_Condicao;
import com.example.alex.levprocess.login.LoginActivity;
import com.example.alex.levprocess.processo.BuscarProcesso;
import com.example.alex.levprocess.processo.NovoProcesso;

public class MenuCliente extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_cliente);

        Button botaoLogoff = (Button) findViewById(R.id.botaoLogoff);
        Button botaoNProcesso = (Button) findViewById(R.id.botaoNProcesso);
        Button botaoBProcesso = (Button) findViewById(R.id.botaoBProcesso);
        Button botaoCAtividade_Condicao = (Button) findViewById(R.id.botaoCAtividade_Condicao);
        Button botaoVRProcesso = (Button) findViewById(R.id.botaoVRProcesso);

        botaoLogoff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, LoginActivity.class);
                startActivityForResult(it, 1);
            }
        });
        botaoBProcesso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, BuscarProcesso.class);
                startActivityForResult(it, 2);
            }
        });
        botaoNProcesso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, NovoProcesso.class);
                startActivityForResult(it, 2);
            }
        });
        botaoCAtividade_Condicao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, NovaAtividade_Condicao.class);
                startActivityForResult(it, 3);
            }
        });

        botaoVRProcesso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, BuscarAtividade_Condicao.class);
                startActivityForResult(it, 3);
            }
        });
    }
}