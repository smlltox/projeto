package br.edu.projeto.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import br.edu.projeto.dao.UsuarioDAO;
import br.edu.projeto.model.TipoPermissao;
import br.edu.projeto.model.Usuario;
import br.edu.projeto.util.Permissao;

//@Model equivale a essas duas anotações
@RequestScoped
//Torna classe disponível na camada de visão (html)
@Named
public class UsuarioController {

	@Inject
	//Mensagens de erro para o usuário
    private FacesContext facesContext;
	
	@Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private UsuarioDAO usuarioDAO;

    private Usuario novoUsuario;
    
    private List<Usuario> listaUsuarios;

    //Executa após instanciar classe UsuarioController, ou seja, 
    //a cada requisição (RequestScoped)
    @PostConstruct
    public void inicializarUsuario() {
        novoUsuario = new Usuario();
        TipoPermissao permissao = new TipoPermissao();
        permissao.setPermissao(Permissao.CLIENTE);
        permissao.addUsuario(novoUsuario);
        listaUsuarios = usuarioDAO.findAllHQL();
    }

    public void register(){
        try {
        	if (!usuarioDAO.isUsuarioUnique(novoUsuario.getUsuario())) 
                throw new Exception("Já existe um usuário com esse nome!");       	
        	novoUsuario.setSenha(passwordHash.generate(novoUsuario.getSenha().toCharArray()));
            usuarioDAO.save(novoUsuario);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário criado!", "Registro Salvo.");
            facesContext.addMessage(null, m);
            inicializarUsuario();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registro inválido.");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

	public Usuario getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Usuario novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

	public List<Usuario> getListaUsuarios() {
		if (listaUsuarios == null) {
			listaUsuarios = usuarioDAO.findAllHQL();
		}
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
}
