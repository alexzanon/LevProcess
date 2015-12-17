package com.example.alex.levprocess.roteiro;

/**
 * Created by Alex on 16/12/2015.
 */

import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.alex.levprocess.R;

import com.example.alex.levprocess.modelador.MenuModelador;
import com.example.alex.levprocess.roteiro.Roteiro.Roteiros;

public class CadastrarRoteiro extends ListActivity {

    protected static final int INSERIR_EDITAR = 1;
    protected static final int BUSCAR = 2;
    protected static final int VOLTAR = 3;
    private RepositorioRoteiro repositorio;
    private List<Roteiro> roteiros;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
           atualizarLista();
    }

    private void init() {
        repositorio = new RepositorioRoteiro(this);
    }

    protected void atualizarLista() {
        // Pega a lista de roteiros e exibe na tela
        roteiros = repositorio.listarRoteiros();
        // Adaptador de lista customizado para cada linha de um roteiro
        setListAdapter(new RoteiroListAdapter(this, roteiros));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
        menu.add(0, BUSCAR, 0, "Buscar").setIcon(R.drawable.pesquisar);
        menu.add(0, VOLTAR, 0, "Voltar").setIcon(R.drawable.limpar);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // Clicou no menu
        switch (item.getItemId()) {
            case INSERIR_EDITAR:
                // Abre a tela com o formulario para adicionar
                startActivityForResult(new Intent(this, EditarRoteiro.class), INSERIR_EDITAR);
                break;
            case BUSCAR:
                // Abre a tela para buscar o roteiro pelo nome
                startActivity(new Intent(this, BuscarRoteiro.class));
                break;
            case VOLTAR:
                // Abre a tela para buscar o roteiro pelo nome
                startActivity(new Intent(this, MenuModelador.class));
                break;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int posicao, long id) {
        super.onListItemClick(l, v, posicao, id);
        editarRoteiro(posicao);
    }

    // Recupera o id do processo, e abre a tela de edicao
    protected void editarRoteiro(int posicao) {
        // Usuario clicou em algum roteiro da lista e
        // Recupera o roteiro selecionado
        Roteiro roteiro = roteiros.get(posicao);
        // Cria a intent para abrir a tela de editar
        Intent it = new Intent(this, EditarRoteiro.class);
        // Passa o id do processo como parametro
        it.putExtra(Roteiros._ID, roteiro.id);
        // Abre a tela de edicao
        startActivityForResult(it, INSERIR_EDITAR);
    }

    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
        super.onActivityResult(codigo, codigoRetorno, it);
        // Quando a activity EditarRoteiro retornar, seja se foi para adicionar vamos atualizar a lista
        if (codigoRetorno == RESULT_OK) {
            // atualiza a lista na tela
            //atualizarLista();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fecha o banco
        repositorio.fechar();
    }
}

