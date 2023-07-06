package br.edu.projeto.util;

//Classe enumeradora, responsável por criar um tipo customizado/complexo
public enum Nacionalidade {
    CLIENTE(2),
    SERVIDOR(3);

    public int id;

    Nacionalidade(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

