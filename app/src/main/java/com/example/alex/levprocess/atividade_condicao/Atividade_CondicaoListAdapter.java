package com.example.alex.levprocess.atividade_condicao;

/**
 * Created by Alex on 16/12/2015.
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.atividade_condicao.Atividade_Condicao;

public class Atividade_CondicaoListAdapter extends BaseAdapter {
    private Context context;
    private List<Atividade_Condicao> lista;
    public Atividade_CondicaoListAdapter(Context context, List<Atividade_Condicao> lista) {
        this.context = context;
        this.lista = lista;
    }
    public int getCount() {		return lista.size();	}
    public Object getItem(int position) {		return lista.get(position); 	}
    public long getItemId(int position) {		return position;	}
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recupera a Atividade_Condicao da posicao atual
        Atividade_Condicao a = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.atividade_condicao_linha_tabela, null);

        // Atualiza o valor do TextView
        TextView nome = (TextView) view.findViewById(R.id.etNomeAtividade_Condicao);
        nome.setText(a.nome);
        return view;
    }
}