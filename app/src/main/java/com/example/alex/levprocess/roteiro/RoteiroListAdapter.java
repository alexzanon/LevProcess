package com.example.alex.levprocess.roteiro;

/**
 * Created by Alex on 16/12/2015.
 */

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.roteiro.Roteiro;

/**
 * Adapter customizado que exibe o layout definido em smile_row.xml
 *
 * As imagens sao exibidas no widget ImageView
 *
 *
 */
public class RoteiroListAdapter extends BaseAdapter {
    private Context context;
    private List<Roteiro> lista;
    public RoteiroListAdapter(Context context, List<Roteiro> lista) {
        this.context = context;
        this.lista = lista;
    }
    public int getCount() {		return lista.size();	}
    public Object getItem(int position) {		return lista.get(position); 	}
    public long getItemId(int position) {		return position;	}
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recupera o Processo da posicao atual
        Roteiro r = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.roteiro_linha_tabela, null);
        // Atualiza o valor do TextView
        TextView nome = (TextView) view.findViewById(R.id.etNomeRoteiro);
        nome.setText(r.nome);
        TextView processo = (TextView) view.findViewById(R.id.etProcesso);
        processo.setText(r.processo);
        return view;
    }
}