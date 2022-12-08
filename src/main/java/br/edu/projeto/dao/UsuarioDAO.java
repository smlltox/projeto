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

	//Criar os objetos sob demanda de forma automática
	@Inject
    private EntityManager em;
	
	public Usuario findById(Long id) {
        return em.find(Usuario.class, id);
    }
	
	public List<Usuario> findAll(){
		//Cria objeto que fará consulta
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//Retorno é da classe Usuario
        CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
        //From usuario
        Root<Usuario> usuario = criteria.from(Usuario.class);
        //Select todos os usuários
        criteria.select(usuario);//.orderBy(cb.asc(usuario.get("usuario")));
        //Executa a consulta e traz o retorno
        return em.createQuery(criteria).getResultList();
	}
	
	public List<Usuario> findAllHQL() {
	    return em.createQuery("SELECT a FROM Usuario a ", 
	    		Usuario.class).getResultList();      
	}
	
	public void save(Usuario u) {
		em.persist(u);
	}
	
}
