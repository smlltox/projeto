package br.edu.projeto.model;

import br.edu.projeto.util.Permissao;

public class TipoPermissao {

    private Integer id;
    
    private Permissao permissao;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}	
}

