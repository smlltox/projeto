package br.edu.projeto.util;

public enum Permissao { 	
    ADMINISTRADOR(1), CLIENTE(2), SERVIDOR(3);
	
	public int id;
	
	Permissao(int id){
		this.id = id;
	}
}
