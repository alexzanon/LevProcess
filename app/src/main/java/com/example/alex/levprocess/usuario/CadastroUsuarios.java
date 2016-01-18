package com.example.alex.levprocess.usuario;

/**
 * Created by Alex on 30/08/2015.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.usuario.Usuario.Usuarios;

public class CadastroUsuarios extends ListActivity {
    protected static final int INSERIR_EDITAR = 1;
    private RepositorioUsuario repositorio;
    private List<Usuario> usuarios;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        atualizarLista();
    }
    protected void atualizarLista() {
        // Pega a lista de usuarios e exibe na tela
        usuarios = repositorio.listarUsuarios();
        // Adaptador de lista customizado para cada linha de um usuario
        setListAdapter(new UsuarioListAdapter(this, usuarios));
    }

    public void init() {
        repositorio = new RepositorioUsuario(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // Clicou no menu
        switch (item.getItemId()) {
            case INSERIR_EDITAR:
                // Abre a tela com o formulario para adicionar
                startActivityForResult(new Intent(this, EditarUsuario.class), INSERIR_EDITAR);
                break;
            /*case BUSCAR:
                // Abre a tela para buscar o usuario pelo nome
                startActivity(new Intent(this, BuscarUsuario.class));
                break;*/

        }
        return true;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int posicao, long id) {
        super.onListItemClick(l, v, posicao, id);
        editarUsuario(posicao);
    }
    // Recupera o id do usuario, e abre a tela de edicao
    protected void editarUsuario(int posicao) {
        // Usuario clicou em algum usuario da lista e
        // Recupera o usuario selecionado
        Usuario usuario = usuarios.get(posicao);
        // Cria a intent para abrir a tela de editar
        Intent it = new Intent(this, EditarUsuario.class);
        // Passa o id do usuario como parametro
        it.putExtra(Usuarios._ID, usuario.id);
        // Abre a tela de edicao
        startActivityForResult(it, INSERIR_EDITAR);
    }
    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
        super.onActivityResult(codigo, codigoRetorno, it);
        // Quando a activity EditarUsuario retornar, seja se foi para adicionar vamos atualizar a lista
        if (codigoRetorno == RESULT_OK) {
            // atualiza a lista na tela
            atualizarLista();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fecha o banco
        repositorio.fechar();
    }
}