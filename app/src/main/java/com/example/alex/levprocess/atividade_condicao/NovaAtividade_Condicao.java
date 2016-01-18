package com.example.alex.levprocess.atividade_condicao;

/**
 * Created by Alex on 06/01/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.atividade_condicao.Atividade_Condicao.Atividade_Condicaos;
import com.example.alex.levprocess.banco.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class NovaAtividade_Condicao extends Activity {

    private EditText campoNome, campoResponsavel, campoDepartamento, campoDetalhamento, campoDocumento;
    private String campoNomeProcesso;
    private TextView campoTipo;
    private Long id;
    private RadioGroup rdGroup1;
    private RadioButton rbatividade,rbcondicao;
    private Spinner spinner;
    private RepositorioAtividade_Condicao repositorio;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        init();
        setContentView(R.layout.nova_atividade_condicao);
        campoNome = (EditText) findViewById(R.id.campoNomeAtividade);
        campoNomeProcesso = "";
        campoResponsavel = (EditText) findViewById(R.id.campoResponsavel);
        campoDepartamento = (EditText) findViewById(R.id.campoDepartamento);
        campoTipo = (TextView) findViewById(R.id.text4);
        rdGroup1 = (RadioGroup) findViewById(R.id.rdGroup1);
        rbatividade = (RadioButton) findViewById(R.id.RBAtividade);
        rbcondicao = (RadioButton) findViewById(R.id.RBCondicao);
        campoDetalhamento = (EditText) findViewById(R.id.campoDetalhamento);
        campoDocumento = (EditText) findViewById(R.id.campoDocumento);
        id = null;

        spinner = (Spinner) findViewById(R.id.spinner);
        loadSpinnerData();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                campoNomeProcesso = adapter.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        rbatividade.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                campoTipo.setText("Atividade");
            }
        });
        rbcondicao.setOnClickListener(new RadioGroup.OnClickListener() {
            public void onClick(View v) {
                campoTipo.setText("Condicao");
            }
        });

        Button btCancelar = (Button) findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();// Fecha a tela
            }
        });
        // Listener para salvar a atividade_condicao
        Button btSalvar = (Button) findViewById(R.id.btSalvar);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int selectedItemID = rdGroup1.getCheckedRadioButtonId();
                if (selectedItemID>0) {
                    if (campoNome.getText().toString().equals("")) {
                        Toast.makeText(NovaAtividade_Condicao.this, "Favor cadastrar ao menos um nome para a atividade/condicao", Toast.LENGTH_LONG).show();
                    } else {
                        salvar();
                    }
                } else {
                    Toast.makeText(NovaAtividade_Condicao.this, "Favor selecionar o Tipo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void init() {
        repositorio = new RepositorioAtividade_Condicao(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancela para nao ficar nada na tela pendente
        setResult(RESULT_CANCELED);
        // Fecha a tela
        finish();
    }

    private void loadSpinnerData() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        List<String> lables = db.getAllLabels();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void salvar() {
        Atividade_Condicao atividade_condicao = new Atividade_Condicao();
        if (id != null) {
            atividade_condicao.id = id;// E uma atualizacao
        }
        atividade_condicao.nome = campoNome.getText().toString();
        atividade_condicao.nome_processo = campoNomeProcesso.toString();
        atividade_condicao.responsavel = campoResponsavel.getText().toString();
        atividade_condicao.departamento = campoDepartamento.getText().toString();
        atividade_condicao.tipo = campoTipo.getText().toString();
        atividade_condicao.detalhamento = campoDetalhamento.getText().toString();
        atividade_condicao.documento = campoDocumento.getText().toString();
        salvarAtividade_Condicao(atividade_condicao);// Salvar
        setResult(RESULT_OK, new Intent());	// OK
        // Fecha a tela
        finish();
    }

    // Salvar a atividade_condicao
    protected void salvarAtividade_Condicao(Atividade_Condicao atividade_condicao) {
        repositorio.salvar(atividade_condicao);
        Toast.makeText(this, "Atividade/Condicao cadastrada com sucesso", Toast.LENGTH_LONG).show();
    }

}