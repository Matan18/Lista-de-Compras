package br.com.andriola.listadecompras.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Produto implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id=0;
    private String nome;
    private double preco = 0;
    private boolean marcado= false;

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Produto(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

}