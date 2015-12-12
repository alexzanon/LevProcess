package com.example.alex.levprocess.processo;

/**
 * Created by Alex on 14/11/2015.
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
import com.example.alex.levprocess.processo.Processo.Processos;
import com.example.alex.levprocess.usuario.EditarUsuario;

public class CadastroProcesso extends ListActivity {

    protected static final int INSERIR_EDITAR = 1;
    protected static final int BUSCAR = 2;
    protected static final int VOLTAR = 3;
    private RepositorioProcesso repositorio;
    private List<Processo> processos;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        atualizarLista();
    }

    private void init() {
        repositorio = new RepositorioProcesso(this);
    }

    protected void atualizarLista() {
        // Pega a lista de processos e exibe na tela
        processos = repositorio.listarProcessos();
        // Adaptador de lista customizado para cada linha de um processo
        setListAdapter(new ProcessoListAdapter(this, processos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
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
                startActivityForResult(new Intent(this, DadosProcesso.class), INSERIR_EDITAR);
                break;
            case BUSCAR:
                // Abre a tela para buscar o processo pelo nome
                startActivity(new Intent(this, BuscarProcesso.class));
                break;
            case VOLTAR:
                // Abre a tela para buscar o processo pelo nome
                startActivity(new Intent(this, MenuModelador.class));
                break;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int posicao, long id) {
        super.onListItemClick(l, v, posicao, id);
        editarProcesso(posicao);
    }

    // Recupera o id do processo, e abre a tela de edicao
    protected void editarProcesso(int posicao) {
        // Usuario clicou em algum processo da lista e
        // Recupera o processo selecionado
        Processo processo = processos.get(posicao);
        // Cria a intent para abrir a tela de editar
        Intent it = new Intent(this, DadosProcesso.class);
        // Passa o id do processo como parametro
        it.putExtra(Processos._ID, processo.id);
        // Abre a tela de edicao
        startActivityForResult(it, INSERIR_EDITAR);
    }

    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
        super.onActivityResult(codigo, codigoRetorno, it);
        // Quando a activity EditarProcesso retornar, seja se foi para adicionar vamos atualizar a lista
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