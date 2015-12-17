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
import com.example.alex.levprocess.atividade_condicao.CadastrarAtividade_Condicao;
import com.example.alex.levprocess.login.LoginActivity;
import com.example.alex.levprocess.processo.NovoProcesso;
import com.example.alex.levprocess.roteiro.CadastrarRoteiro;

public class MenuCliente extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_cliente);

        Button botaoLogoff = (Button) findViewById(R.id.botaoLogoff);
        Button botaoNProcesso = (Button) findViewById(R.id.botaoNProcesso);


        botaoLogoff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, LoginActivity.class);
                startActivityForResult(it, 1);
            }
        });
        botaoNProcesso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, CadastrarAtividade_Condicao.class);
                startActivityForResult(it, 2);
            }
        });
    }
}