package br.edu.projeto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.edu.projeto.util.Permissao;

@Entity
@Table(name = "tipo_permissao")
public class TipoPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_permissao")
    private Integer id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Permissao permissao;
    
    @ManyToMany(mappedBy = "permissoes", fetch = FetchType.EAGER)
    private List<Usuario> usuarios = new ArrayList<Usuario>();

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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
		usuario.getPermissoes().add(this);
	}
	
}

