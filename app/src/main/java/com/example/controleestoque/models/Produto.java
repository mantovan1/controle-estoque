package com.example.controleestoque.models;

import android.net.Uri;

public class Produto {

    public String id;
    public String nome;
    public double preco;
    public int estoqueAtual;
    public int estoqueIdeal;
    public String imagePath;

    public Produto(){};

    public Produto(String id, String nome, double preco, int estoqueAtual, int estoqueIdeal, String imagePath) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoqueAtual = estoqueAtual;
        this.estoqueIdeal = estoqueIdeal;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public int getEstoqueIdeal() {
        return estoqueIdeal;
    }

    public void setEstoqueIdeal(int estoqueIdeal) {
        this.estoqueIdeal = estoqueIdeal;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

