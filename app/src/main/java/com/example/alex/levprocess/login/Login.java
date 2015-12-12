package com.example.alex.levprocess.login;

public class Login {

    private Integer id_login;
    private String etUsuario;
    private String etSenha;

    public Login(Integer id_login, String etUsuario, String etSenha) {
        this.setId_login(id_login);
        this.setEtUsuario(etUsuario);
        this.setEtSenha(etSenha);

    }

    public Integer getId_login() {
        return id_login;
    }

    public void setId_login(Integer id_login) {
        this.id_login = id_login;
    }

    public String getEtUsuario() {
        return etUsuario;
    }

    public void setEtUsuario(String etUsuario) {
        this.etUsuario = etUsuario;
    }

    public String getEtSenha() {
        return etSenha;
    }

    public void setEtSenha(String etSenha) {
        this.etSenha = etSenha;
    }
}