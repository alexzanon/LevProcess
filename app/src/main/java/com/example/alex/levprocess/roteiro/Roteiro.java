package com.example.alex.levprocess.roteiro;

import com.example.alex.levprocess.processo.Processo;

/**
 * Created by Alex on 08/12/2015.
 */


public class Roteiro {

    private long id;
    private String nome;
    private Processo processo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

}