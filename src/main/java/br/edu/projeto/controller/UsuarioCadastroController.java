package br.edu.projeto.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import br.edu.projeto.dao.TipoPermissaoDAO;
import br.edu.projeto.dao.UsuarioDAO;
import br.edu.projeto.model.TipoPermissao;
import br.edu.projeto.model.Usuario;
import br.edu.projeto.util.Permissao;

@RequestScoped
//Torna classe disponível na camada de visão (html)
@Named
public class UsuarioCadastroController {

	@Inject
	private FacesContext facesContext;
	
	@Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private UsuarioDAO usuarioDAO;
    
    @Inject
    private TipoPermissaoDAO tipoPermissaoDAO;

    private Usuario novoUsuario;
    
    private Usuario usuarioSelecionado;
    
    private List<Usuario> listaUsuarios;

    //Executa após instanciar classe UsuarioController
    @PostConstruct
    public void init() {
    	if (!facesContext.getExternalContext().isUserInRole("ADMINISTRADOR")) {
    		try {
				facesContext.getExternalContext().redirect("login-error.xhtml");
			} catch (IOException e) {e.printStackTrace();}
    	}
    	inicializarPagina();
    }

    public void registrar(){
        try {
        	if (!usuarioDAO.ehUsuarioUnico(novoUsuario.getUsuario())) 
                throw new Exception("Já existe um usuário com esse nome!");       	
        	novoUsuario.setSenha(passwordHash.generate(novoUsuario.getSenha().toCharArray()));
            usuarioDAO.salvar(novoUsuario);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário criado!", "Registro Salvo."));
            inicializarPagina();
        } catch (Exception e) {
            String errorMessage = getMensagemErro(e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registro inválido."));
        }
    }
    
    public void excluir() {
    	usuarioDAO.excluir(usuarioSelecionado);
    	recarregarLista();
    }
    
    public void editar() {
    	try {
			facesContext.getExternalContext().redirect("editar.xhtml?usuarioId="+usuarioSelecionado.getId());
		} catch (IOException e) {e.printStackTrace();}
    }
    
    public void inicializarPagina() {
    	novoUsuario = new Usuario();
        TipoPermissao permissao = tipoPermissaoDAO.encontrarPermissao(Permissao.CLIENTE);
        permissao.addUsuario(novoUsuario);
        recarregarLista();
    }
    
    public void recarregarLista() {
    	listaUsuarios = usuarioDAO.listarTodos();
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

	public Usuario getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Usuario novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

}
