package com.example.alex.levprocess.usuario;

/**
 * Created by Alex on 30/08/2015.
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alex.levprocess.R;

public class UsuarioListAdapter extends BaseAdapter {
    private Context context;
    private List<Usuario> lista;
    public UsuarioListAdapter(Context context, List<Usuario> lista) {
        this.context = context;
        this.lista = lista;
    }
    public int getCount() {		return lista.size();	}
    public Object getItem(int position) {		return lista.get(position); 	}
    public long getItemId(int position) {		return position;	}
    public View getView(int position, View convertView, ViewGroup parent) {
        // Recupera o Usuario da posicao atual
        Usuario u = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.usuario_linha_tabela, null);
        // Atualiza o valor do TextView
        TextView login = (TextView) view.findViewById(R.id.etUsuario);
        login.setText(u.login);
        TextView senha = (TextView) view.findViewById(R.id.etSenha);
        senha.setText(u.senha);
        login.setText(u.login);
        return view;
    }
}
