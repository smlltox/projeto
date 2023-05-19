package br.edu.projeto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.CamisetaDAO;
import br.edu.projeto.model.Camiseta;

//Escopo do objeto da classe (Bean)
//ApplicationScoped é usado quando o objeto é único na aplicação (compartilhado entre usuários), permanece ativo enquanto a aplicação estiver ativa
//SessionScoped é usado quando o objeto é único por usuário, permanece ativo enquanto a sessão for ativa
//ViewScoped é usado quando o objeto permanece ativo enquanto não houver um redirect (acesso a nova página)
//RequestScoped é usado quando o objeto só existe naquela requisição específica
//Quanto maior o escopo, maior o uso de memória no lado do servidor (objeto permanece ativo por mais tempo)
//Escopos maiores que Request exigem que classes sejam serializáveis assim como todos os seus atributos (recurso de segurança)
@ViewScoped
//Torna classe disponível na camada de visão (html) - são chamados de Beans ou ManagedBeans (gerenciados pelo JSF/EJB)
@Named
public class CrudCamisetaController implements Serializable {
	private static final long serialVersionUID = 1L;

	//Anotação que marca atributo para ser gerenciado pelo CDI
	//O CDI criará uma instância do objeto automaticamente quando necessário
	@Inject
	private FacesContext facesContext;
	
	@Inject
    private CamisetaDAO camisetaDAO;
	
	private Camiseta camiseta;
	
	private List<Camiseta> listaCamiseta;
	
	private Boolean rendNovoCadastro;
	
	//Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Inicializa elementos importantes
    	this.setListaCamiseta(camisetaDAO.listAll());
    }
	
    //Chamado pelo botão novo
	public void novoCadastro() {
        this.setCamiseta(new Camiseta());
        this.setRendNovoCadastro(true);
    }
	
	//Chamado pelo botão alterar
	public void alterarCadastro() {
        this.setRendNovoCadastro(false);
    }
	
	//Chamado pelo botão remover da tabela
	public void remover() {
		if (this.camisetaDAO.delete(this.camiseta)) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Camiseta Removida", null));
			this.listaCamiseta.remove(this.camiseta);
		} else 
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover Camiseta", null));
		//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
		//this.listaCamiseta = camisetaDAO.listAll();
        //Limpa seleção de usuário
		this.camiseta = null;
        PrimeFaces.current().ajax().update("form:messages", "form:dt-camisetas");
	}	
	
	//Chamado ao salvar cadastro de usuário (novo)
	public void salvarNovo() {
		if (this.camiseta.getTamanho().equals("P") || this.camiseta.getTamanho().equals("M") || this.camiseta.getTamanho().equals("G"))
		{
			if (this.camisetaDAO.insert(this.camiseta)) {
				this.getListaCamiseta().add(this.camiseta);
				PrimeFaces.current().executeScript("PF('camisetaDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-camiseta");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Camiseta Criada", null));
			} else
        		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Criar Camiseta", null));
		} else {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tamanho da camiseta inválido, deve ser P, M ou G!", null));
    	}   
		PrimeFaces.current().ajax().update("form:messages");
	}
	
	//Chamado ao salvar cadastro de usuário (alteracao)
	public void salvarAlteracao() {
		if (this.camiseta.getTamanho().equals("P") || this.camiseta.getTamanho().equals("M") || this.camiseta.getTamanho().equals("G"))
		{
			if (this.camisetaDAO.update(this.camiseta)) {
				PrimeFaces.current().executeScript("PF('camisetaDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-camiseta");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Camiseta Atualizada", null));
			} else
        		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Camiseta", null));
		} else {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Tamanho da camiseta inválido, deve ser P, M ou G!", null));
    	}
		this.setListaCamiseta(camisetaDAO.listAll());
		PrimeFaces.current().ajax().update("form:messages");
	}
	
	//GETs e SETs
	//GETs são necessários para elementos visíveis em tela
	//SETs são necessários para elementos que serão editados em tela
	public Camiseta getCamiseta() {
		return camiseta;
	}

	public void setCamiseta(Camiseta camiseta) {
		this.camiseta = camiseta;
	}

	public List<Camiseta> getListaCamiseta() {
		return listaCamiseta;
	}

	public void setListaCamiseta(List<Camiseta> listaCamiseta) {
		this.listaCamiseta = listaCamiseta;
	}

	public Boolean getRendNovoCadastro() {
		return rendNovoCadastro;
	}

	public void setRendNovoCadastro(Boolean rendNovoCadastro) {
		this.rendNovoCadastro = rendNovoCadastro;
	}
}
