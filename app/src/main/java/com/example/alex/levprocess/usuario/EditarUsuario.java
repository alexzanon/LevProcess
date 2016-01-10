package com.example.alex.levprocess.usuario;

/**
 * Created by Alex on 30/08/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.usuario.Usuario.Usuarios;

import org.w3c.dom.Text;

public class EditarUsuario extends Activity {

    static final int RESULT_SALVAR = 1;
    static final int RESULT_EXCLUIR = 2;
    private EditText campoLogin, campoSenha, campoNome, campoCpf, campoDataNascimento, campoTelefone, campoEmail;
    private TextView campoTipo;
    private RadioButton rbcliente, rbmodelador, rbadministrador;
    private Long id;
    private RepositorioUsuario repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.form_editar_usuario);
        campoLogin = (EditText) findViewById(R.id.campoLogin);
        campoSenha = (EditText) findViewById(R.id.campoSenha);
        campoNome = (EditText) findViewById(R.id.campoNome);
        campoCpf = (EditText) findViewById(R.id.campoCpf);
        campoDataNascimento = (EditText) findViewById(R.id.campoDataNascimento);
        campoTelefone = (EditText) findViewById(R.id.campoTelefone);
        campoEmail = (EditText) findViewById(R.id.campoEmail);
        campoTipo = (TextView) findViewById(R.id.text8);
        rbcliente = (RadioButton) findViewById(R.id.RBCliente);
        rbmodelador = (RadioButton) findViewById(R.id.RBModelador);
        rbadministrador = (RadioButton) findViewById(R.id.RBAdministrador);
        id = null;

        rbcliente.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                campoTipo.setText("Cliente");
            }
        });
        rbmodelador.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                campoTipo.setText("Modelador");
            }
        });
        rbadministrador.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                campoTipo.setText("Administrador");
            }
        });

        Bundle extras = getIntent().getExtras();
        // Se for para Editar, recuperar os valores ...
        if (extras != null) {
            id = extras.getLong(Usuarios._ID);
            if (id != null) {
                // e uma edicao, busca o usuário...
                Usuario usuario = buscarUsuario(id);
                campoLogin.setText(usuario.login);
                campoSenha.setText(usuario.senha);
                campoNome.setText(usuario.nome);
                campoCpf.setText(usuario.cpf);
                campoDataNascimento.setText(usuario.dataNascimento);
                campoTelefone.setText(usuario.telefone);
                campoEmail.setText(usuario.email);
                campoTipo.setText(usuario.tipo);
            }
        }

        Button btCancelar = (Button) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });
        // Listener para salvar o usuario
        Button btSalvar = (Button) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                salvar();
            }
        });

        Button btExcluir = (Button) findViewById(R.id.btExcluir);
        if (id == null) {
            // Se id esta nulo, nao pode excluir
            btExcluir.setVisibility(View.INVISIBLE);
        } else {
            // Listener para excluir o usuario
            btExcluir.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    excluir();
                }
            });
        }
    }

    public void init() {
        repositorio = new RepositorioUsuario(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancela para nao ficar nada na tela pendente
        setResult(RESULT_CANCELED);
        // Fecha a tela
        finish();
    }

    public void salvar() {
        Usuario usuario = new Usuario();
        if (id != null) {
            usuario.id = id;// E uma atualizacao
        }
        usuario.login = campoLogin.getText().toString();
        usuario.senha = campoSenha.getText().toString();
        usuario.nome = campoNome.getText().toString();
        usuario.cpf = campoCpf.getText().toString();
        usuario.dataNascimento = campoDataNascimento.getText().toString();
        usuario.telefone = campoTelefone.getText().toString();
        usuario.email = campoEmail.getText().toString();
        usuario.tipo = campoTipo.getText().toString();
        salvarUsuario(usuario);// Salvar
        setResult(RESULT_OK, new Intent());	// OK
        // Fecha a tela
        finish();
    }

    public void excluir() {
        if (id != null) {
            excluirUsuario(id);
        }
        // OK
        setResult(RESULT_OK, new Intent());
        // Fecha a tela
        finish();
    }

    // Buscar o usuario pelo id
    protected Usuario buscarUsuario(long id) {
        return repositorio.buscarUsuario(id);
    }

    // Salvar o usuario
    protected void salvarUsuario(Usuario usuario) {
        repositorio.salvar(usuario);
        Toast.makeText(this, "Usuario cadastrado com sucesso", Toast.LENGTH_LONG).show();
    }

    // Excluir o usuario
    protected void excluirUsuario(long id) {
        repositorio.deletar(id);
    }
}