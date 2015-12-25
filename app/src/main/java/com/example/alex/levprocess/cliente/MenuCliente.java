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
import com.example.alex.levprocess.login.LoginActivity;
import com.example.alex.levprocess.processo.NovoProcesso;
import com.example.alex.levprocess.roteiro.NovoRoteiro;

public class MenuCliente extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_cliente);

        Button botaoLogoff = (Button) findViewById(R.id.botaoLogoff);
        Button botaoNProcesso = (Button) findViewById(R.id.botaoNProcesso);
        Button botaoNRoteiro = (Button) findViewById(R.id.botaoNRoteiro);


        botaoLogoff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, LoginActivity.class);
                startActivityForResult(it, 1);
            }
        });
        botaoNProcesso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, NovoProcesso.class);
                startActivityForResult(it, 2);
            }
        });
        botaoNRoteiro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuCliente.this, NovoRoteiro.class);
                startActivityForResult(it, 2);
            }
        });
    }
}