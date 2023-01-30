package br.edu.projeto.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Usuario;

@Stateful
public class UsuarioDAO {

	@Inject
    private EntityManager em;
	
	public Usuario findById(Long id) {
        return em.find(Usuario.class, id);
    }
	
	public Boolean isUsuarioUnique(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = criteria.from(Usuario.class);
        criteria.select(usuario);
        criteria.where(cb.like(usuario.get("usuario"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	public List<Usuario> findAllHQL() {
	    return em.createQuery("SELECT a FROM Usuario a ", Usuario.class).getResultList();      
	}
	
	public void save(Usuario u) {
		em.persist(u);
	}
	
}
