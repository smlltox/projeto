package br.edu.projeto.security;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.projeto.model.Usuario;

@Named 
@RequestScoped
public class LoginController {

	@Inject
    private FacesContext facesContext;

    @Inject
    private SecurityContext securityContext;

    private Usuario usuario;

    @PostConstruct
    public void inicializarUsuario() {
        usuario = new Usuario();
    }

    public void login(){
    	Credential credential = new UsernamePasswordCredential(usuario.getUsuario(), new Password(usuario.getSenha()));
    	AuthenticationStatus status = securityContext.authenticate(
    	        	(HttpServletRequest)facesContext.getExternalContext().getRequest(),
    	        	(HttpServletResponse)facesContext.getExternalContext().getResponse(),
    	            AuthenticationParameters.withParams().credential(credential));
    	if (status.equals(AuthenticationStatus.SUCCESS))
    		facesContext.responseComplete();
    	else if (status.equals(AuthenticationStatus.SEND_FAILURE)) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Inválido", "Usuário ou senha incorretos"));
            usuario = new Usuario();
    	}
    	
    }
    
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
