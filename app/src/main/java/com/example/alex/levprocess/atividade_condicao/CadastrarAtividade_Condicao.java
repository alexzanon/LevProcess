package com.example.alex.levprocess.atividade_condicao;

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

import com.example.alex.levprocess.cliente.MenuCliente;
import com.example.alex.levprocess.modelador.MenuModelador;
import com.example.alex.levprocess.atividade_condicao.Atividade_Condicao.Atividade_Condicaos;

public class CadastrarAtividade_Condicao extends ListActivity {

    protected static final int INSERIR_EDITAR = 1;
    protected static final int VOLTAR = 3;
    private RepositorioAtividade_Condicao repositorio;
    private List<Atividade_Condicao> atividade_condicaos;
    String nomeP;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent it = getIntent();
        nomeP = it.getStringExtra("nomeP");
        init();
        atualizarLista();
    }

    private void init() {
        repositorio = new RepositorioAtividade_Condicao(this);
    }

    protected void atualizarLista() {
        // Pega a lista de atividade_condicaos e exibe na tela
        atividade_condicaos = repositorio.buscarAtividade_CondicaoPorNomeProcesso(nomeP);
        // Adaptador de lista customizado para cada linha de uma atividade_condicao
        setListAdapter(new Atividade_CondicaoListAdapter(this, atividade_condicaos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERIR_EDITAR, 0, "Inserir Novo").setIcon(R.drawable.novo);
        menu.add(0, VOLTAR, 0, "Voltar").setIcon(R.drawable.limpar);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // Clicou no menu
        switch (item.getItemId()) {
            case INSERIR_EDITAR:
                // Abre a tela com o formulario para adicionar
                startActivityForResult(new Intent(this, EditarAtividade_Condicao.class), INSERIR_EDITAR);
                break;
            case VOLTAR:
                // Abre a tela para buscar a atividade_condicao pelo nome
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int posicao, long id) {
        super.onListItemClick(l, v, posicao, id);
        editarAtividade_Condicao(posicao);
    }

    // Recupera o id da atividade_condicao, e abre a tela de edicao
    protected void editarAtividade_Condicao(int posicao) {
        // Usuario clicou em alguma atividade_condicao da lista e
        // Recupera a atividade_condicao selecionado
        Atividade_Condicao atividade_condicao = atividade_condicaos.get(posicao);
        // Cria a intent para abrir a tela de editar
        Intent it = new Intent(this, EditarAtividade_Condicao.class);
        // Passa o id da atividade_condicao como parametro
        it.putExtra(Atividade_Condicaos._ID, atividade_condicao.id);
        // Abre a tela de edicao
        startActivityForResult(it, INSERIR_EDITAR);
    }

    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
        super.onActivityResult(codigo, codigoRetorno, it);
        // Quando a activity EditarAtividade_Condicao retornar, seja se foi para adicionar vamos atualizar a lista
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