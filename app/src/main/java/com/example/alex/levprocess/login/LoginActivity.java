package com.example.alex.levprocess.login;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alex.levprocess.R;
import com.example.alex.levprocess.atividade_condicao.CadastrarAtividade_Condicao;
import com.example.alex.levprocess.cliente.MenuCliente;
import com.example.alex.levprocess.modelador.MenuModelador;
import com.example.alex.levprocess.usuario.CadastroUsuarios;
import com.example.alex.levprocess.usuario.EditarUsuario;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText edtUser;
    private EditText edtPassword;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar);
        initViews();
    }

    /**
     * Recupera as views e configura os listeners para os campos editáveis e para o botão de entrar
     */
    private void initViews() {
        resources = getResources();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callClearErrors(s);
            }
        };

        edtUser = (EditText) findViewById(R.id.etUsuario);
        edtUser.addTextChangedListener(textWatcher);
        edtPassword = (EditText) findViewById(R.id.etSenha);
        edtPassword.addTextChangedListener(textWatcher);
        Button btnEnter = (Button) findViewById(R.id.botaoLogar);
        btnEnter.setOnClickListener(this);
        Button btnCds = (Button) findViewById(R.id.botaoCadastrar);
        btnCds.setOnClickListener(this);
        Button btnLmp = (Button) findViewById(R.id.botaoLimpar);
        btnLmp.setOnClickListener(this);
    }

    /**
     * Chama o método para limpar erros
     *
     * @param s Editable
     */
    private void callClearErrors(Editable s) {
        if (!s.toString().isEmpty()) {
            clearErrorFields(edtUser);
        }
    }

    @Override
    public void onClick(View v) {
        String usuario = edtUser.getText().toString();
        String senha = edtPassword.getText().toString();
        if (v.getId() == R.id.botaoLogar) {
            if (validateFields()) {
                if (usuario.equals("daian") && senha.equals("123456")) {
                    /**
                     * Um trabalho futuro será implementar uma funcionalidade para validar login e senha
                     */
                    Toast.makeText(this, resources.getString(R.string.login_auth_okm), Toast.LENGTH_LONG).show();
                    Intent it = new Intent(LoginActivity.this, MenuModelador.class);
                    startActivityForResult(it, 1);
                } else if (usuario.equals("lucas") && senha.equals("123456")){
                    Toast.makeText(this, resources.getString(R.string.login_auth_okc), Toast.LENGTH_LONG).show();
                    Intent it = new Intent(LoginActivity.this, MenuCliente.class);
                    startActivityForResult(it, 1);
                } else {
                    Toast.makeText(LoginActivity.this, "Login/ ou Senha Invalido", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (v.getId() == R.id.botaoCadastrar) {

            Intent it = new Intent(LoginActivity.this, EditarUsuario.class);
            startActivityForResult(it, 2);
        }
        if (v.getId() == R.id.botaoLimpar) {

            edtUser.setText("");
            edtPassword.setText("");

            Toast.makeText(LoginActivity.this, "Formulario Limpo", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Efetua a validação dos campos.Nesse caso, valida se os campos não estão vazios e se tem
     * tamanho permitido.
     *
     * @return boolean que indica se os campos foram validados com sucesso ou não
     */
    private boolean validateFields() {
        String user = edtUser.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        return (!isEmptyFields(user, pass) && hasSizeValid(user, pass));
    }

    private boolean isEmptyFields(String user, String pass) {
        if (TextUtils.isEmpty(user)) {
            edtUser.requestFocus(); //seta o foco para o campo usuário
            edtUser.setError(resources.getString(R.string.login_user_required));
            return true;
        } else if (TextUtils.isEmpty(pass)) {
            edtPassword.requestFocus(); //seta o foco para o campo senha
            edtPassword.setError(resources.getString(R.string.login_password_required));
            return true;
        }
        return false;
    }

    private boolean hasSizeValid(String user, String pass) {

        if (!(user.length() > 3)) {
            edtUser.requestFocus();
            edtUser.setError(resources.getString(R.string.login_user_size_invalid));
            return false;
        } else if (!(pass.length() > 5)) {
            edtPassword.requestFocus();
            edtPassword.setError(resources.getString(R.string.login_pass_size_invalid));
            return false;
        }
        return true;
    }

    /**
     * Limpa os ícones e as mensagens de erro dos campos desejados
     *
     * @param editTexts lista de campos do tipo EditText
     */
    private void clearErrorFields(EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setError(null);
        }
    }
}