package com.example.alex.levprocess.processo;

/**
 * Created by Alex on 12/11/2015.
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alex.levprocess.R;

public class ProcessoListAdapter extends BaseAdapter {
    private Context context;
    private List<Processo> lista;
    public ProcessoListAdapter(Context context, List<Processo> lista) {
        this.context = context;
        this.lista = lista;
    }
    public int getCount() {		return lista.size();	}
    public Object getItem(int position) {		return lista.get(position); 	}
    public long getItemId(int position) {		return position;	}
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recupera o Processo da posicao atual
        Processo p = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.processo_linha_tabela, null);
        // Atualiza o valor do TextView
        TextView nome = (TextView) view.findViewById(R.id.etNomeProcesso);
        nome.setText(p.nome);
        return view;
    }
}