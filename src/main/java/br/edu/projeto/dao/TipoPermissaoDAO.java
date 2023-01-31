package br.edu.projeto.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.edu.projeto.model.TipoPermissao;
import br.edu.projeto.util.Permissao;

@Stateful
public class TipoPermissaoDAO {

	@Inject
    private EntityManager em;
	
	public TipoPermissao encontrarPermissao(Permissao permissao) {
        return em.find(TipoPermissao.class, permissao.id);
    }
	
	public List<TipoPermissao> listarTodos() {
	    return em.createQuery("SELECT a FROM TipoPermissao a ", TipoPermissao.class).getResultList();      
	}
}
