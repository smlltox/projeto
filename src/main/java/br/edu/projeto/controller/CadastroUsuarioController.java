package br.edu.projeto.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.TipoPermissaoDAO;
import br.edu.projeto.dao.UsuarioDAO;
import br.edu.projeto.model.TipoPermissao;
import br.edu.projeto.model.Usuario;

@ViewScoped
//Torna classe disponível na camada de visão (html)
@Named
public class CadastroUsuarioController implements Serializable {

	@Inject
	private FacesContext facesContext;
	
	@Inject
    transient private Pbkdf2PasswordHash passwordHash;
	
	@Inject
    private UsuarioDAO usuarioDAO;
	
	@Inject
    private TipoPermissaoDAO tipoPermissaoDAO;
	
	private Usuario usuario;
	
	private List<Usuario> listaUsuarios;
	
	private List<SelectItem> permissoes;
	
	private List<Integer> permissoesSelecionadas;
	
    @PostConstruct
    public void init() {
    	if (!this.facesContext.getExternalContext().isUserInRole("ADMINISTRADOR")) {
    		try {
				this.facesContext.getExternalContext().redirect("login-error.xhtml");
			} catch (IOException e) {e.printStackTrace();}
    	}
    	this.permissoesSelecionadas = new ArrayList<Integer>();
    	this.listaUsuarios = usuarioDAO.listarTodos();
    	this.permissoes = new ArrayList<SelectItem>();
    	for (TipoPermissao p: this.tipoPermissaoDAO.listarTodos()) {
    		SelectItem i = new SelectItem(p.getPermissao().id, p.getPermissao().name());		
    		this.permissoes.add(i);
    	}
    }
	
	public void novoCadastro() {
        this.setUsuario(new Usuario());
    }
	
	public void salvar() {
        if (usuarioValido()) {
    		this.usuario.getPermissoes().clear();
    		for (Integer id: this.permissoesSelecionadas) {
    			TipoPermissao permissao = tipoPermissaoDAO.encontrarPermissao(id);
    			permissao.addUsuario(this.usuario);	
    		}
        	try {
		        if (this.usuario.getId() == null) {
		        	this.usuario.setSenha(this.passwordHash.generate(this.usuario.getSenha().toCharArray()));
		        	this.usuarioDAO.salvar(this.usuario);
		        	this.listaUsuarios = usuarioDAO.listarTodos();
		        	this.facesContext.addMessage(null, new FacesMessage("Usuário Criado"));
		        } else {
		        	this.usuarioDAO.atualizar(this.usuario);
		        	this.facesContext.addMessage(null, new FacesMessage("Usuário Atualizado"));
		        }
			    PrimeFaces.current().executeScript("PF('usuarioDialog').hide()");
			    PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
	        } catch (Exception e) {
	            String errorMessage = getMensagemErro(e);
	            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
	        }
        }
	}	
	
	private boolean usuarioValido() {
		if (this.permissoesSelecionadas.isEmpty()) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione ao menos uma permissão para o novo usuário.", null));
			return false;
		}			
		if (this.usuario.getId() == null && !this.usuarioDAO.ehUsuarioUnico(this.usuario.getUsuario())) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Este nome de usuário já está em uso.", null));
			return false;
		}
		return true;
	}
	
	public void remover() {
		try {
			this.usuarioDAO.excluir(this.usuario);
			this.listaUsuarios = usuarioDAO.listarTodos();
	        this.usuario = null;
	        this.facesContext.addMessage(null, new FacesMessage("Usuário Removido"));
	        PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
        } catch (Exception e) {
            String errorMessage = getMensagemErro(e);
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
        }
	}
	
	public void alterar() {
		this.permissoesSelecionadas.clear();
		for (TipoPermissao p: this.usuario.getPermissoes())
			this.permissoesSelecionadas.add(p.getId());
		this.usuario.setSenha("");
	}
	
	private String getMensagemErro(Exception e) {
        String erro = "Falha no sistema!. Contacte o administrador do sistema.";
        if (e == null) 
            return erro;
        Throwable t = e;
        while (t != null) {
            erro = t.getLocalizedMessage();
            t = t.getCause();
        }
        return erro;
    }
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<SelectItem> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<SelectItem> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Integer> getPermissoesSelecionadas() {
		return permissoesSelecionadas;
	}

	public void setPermissoesSelecionadas(List<Integer> permissoesSelecionadas) {
		this.permissoesSelecionadas = permissoesSelecionadas;
	}

}
