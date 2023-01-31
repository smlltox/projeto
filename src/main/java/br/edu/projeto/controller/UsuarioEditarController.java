package br.edu.projeto.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import br.edu.projeto.dao.UsuarioDAO;
import br.edu.projeto.model.Usuario;

@RequestScoped
//Torna classe disponível na camada de visão (html)
@Named
public class UsuarioEditarController {

	@Inject
	private FacesContext facesContext;
	
	@Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private UsuarioDAO usuarioDAO;
    
    private Usuario usuarioSelecionado;

    @PostConstruct
    public void init() {
    	if (!facesContext.getExternalContext().isUserInRole("ADMINISTRADOR")) {
    		try {
				facesContext.getExternalContext().redirect("login-error.xhtml");
			} catch (IOException e) {e.printStackTrace();}
    	}
    	inicializarPagina();
    }
    
    public void salvarAlteracao() {
    	try {
    		usuarioSelecionado.setSenha(passwordHash.generate(usuarioSelecionado.getSenha().toCharArray()));
            usuarioDAO.atualizar(usuarioSelecionado);
            facesContext.getExternalContext().redirect("cadastro_usuario.xhtml");
        } catch (Exception e) {
            String errorMessage = getMensagemErro(e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registro inválido."));
        }
    }
    
    public void inicializarPagina() {
    	try {
	    	Integer usuarioId = Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("usuarioId"));
			usuarioSelecionado = usuarioDAO.encontrarId(usuarioId);
	    	usuarioSelecionado.setSenha("");
		} catch (NumberFormatException e) {
			usuarioSelecionado = new Usuario();
		}
    }

    private String getMensagemErro(Exception e) {
        String erro = "Falha no sistema!. Contacte o administrador do sistema.";
        if (e == null) {
            return erro;
        }

        Throwable t = e;
        while (t != null) {
            erro = t.getLocalizedMessage();
            t = t.getCause();
        }
        return erro;
    }

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

}
