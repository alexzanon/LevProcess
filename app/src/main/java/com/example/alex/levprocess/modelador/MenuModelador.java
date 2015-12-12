package com.example.alex.levprocess.modelador;

/**
 * Created by Alex on 22/09/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.login.LoginActivity;
import com.example.alex.levprocess.processo.CadastroProcesso;

public class MenuModelador extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_modelador);

        Button botaoLogoff = (Button) findViewById(R.id.botaoLogoff);
        Button botaoPPendentes = (Button) findViewById(R.id.botaoPPendentes);

        botaoLogoff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuModelador.this, LoginActivity.class);
                startActivityForResult(it, 1);
            }
        });

        botaoPPendentes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MenuModelador.this, CadastroProcesso.class);
                startActivityForResult(it, 2);
            }
        });
    }
}