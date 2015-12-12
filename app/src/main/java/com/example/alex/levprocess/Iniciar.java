package com.example.alex.levprocess;

/**
 * Created by Alex on 30/08/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.alex.levprocess.modelador.MenuModelador;

public class Iniciar extends Activity {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_iniciar);

        Button botaoLogin = (Button) findViewById(R.id.botaoLogar);

        botaoLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(Iniciar.this, MenuModelador.class);
                startActivityForResult(it, 1);
            }
        });
    }
}