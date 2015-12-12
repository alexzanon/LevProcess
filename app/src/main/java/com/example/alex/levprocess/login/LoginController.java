package com.example.alex.levprocess.login;

import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.example.alex.levprocess.cliente.MenuCliente;
import com.example.alex.levprocess.usuario.EditarUsuario;

public class LoginController {
    private static LoginDao loginDao;
    private static LoginController instance;

    public static LoginController getInstance(Context context) {
        if (instance == null) {
            instance = new LoginController();
            loginDao = new LoginDao(context);
        } return instance;
    }

    public void insert(Login login) throws Exception {
        loginDao.insert(login);
    }

    public void update(Login login) throws Exception {
        loginDao.update(login);
    }

    public List findAll() throws Exception {
        return loginDao.findAll();
    }

    public boolean validaLogin(String usuario, String senha) {
        Login login = loginDao.findById(usuario, senha);
        if (login == null || login.getEtUsuario() == null || login.getEtSenha() == null) {
            return false;
        }

        String informado = usuario + senha;
        String esperado = login.getEtUsuario() + login.getEtSenha();
        if(informado.equals(esperado)){
            return true;
        } return false;
    }
}
