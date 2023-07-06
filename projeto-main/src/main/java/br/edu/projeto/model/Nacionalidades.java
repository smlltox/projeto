package br.edu.projeto.model;

import br.edu.projeto.util.Nacionalidade;

public class Nacionalidades {

    private Integer id;
    
    private Nacionalidade nacionalidade;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Nacionalidade getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Nacionalidade nacionalidade) {
		this.nacionalidade = nacionalidade;
	}	
}

