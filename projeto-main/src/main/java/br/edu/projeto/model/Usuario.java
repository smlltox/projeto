package br.edu.projeto.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private Integer id;

    private String usuario;

    private String senha;
    
    private String email;
    
    private List<TipoPermissao> permissoes = new ArrayList<TipoPermissao>();
    
    private List<Nacionalidades> nacs = new ArrayList<Nacionalidades>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TipoPermissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<TipoPermissao> permissoes) {
		this.permissoes = permissoes;
	}
	
	public List<Nacionalidades> getNacionalidades() {
		return nacs;
	}

	public void setNacionalidades(List<Nacionalidades> nacs) {
		this.nacs = nacs;
	}
}
