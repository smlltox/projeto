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

import br.edu.projeto.dao.PessoaDAO;
import br.edu.projeto.model.Pessoa;

@ViewScoped
@Named
public class CrudPessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	//Anotação que marca atributo para ser gerenciado pelo CDI
	//O CDI criará uma instância do objeto automaticamente quando necessário
	@Inject
	private FacesContext facesContext;
	
	@Inject
    private PessoaDAO pessoaDAO;
	
	private Pessoa pessoa;
	
	private List<Pessoa> listaPessoa;
	
	private Boolean rendNovoCadastro;
	
	//Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Inicializa elementos importantes
    	this.setListaPessoa(pessoaDAO.listAll());
    }
	
    //Chamado pelo botão novo
	public void novoCadastro() {
        this.setPessoa(new Pessoa());
        this.setRendNovoCadastro(true);
    }
	
	//Chamado pelo botão alterar
	public void alterarCadastro() {
        this.setRendNovoCadastro(false);
    }
	
	//Chamado pelo botão remover da tabela
	public void remover() {
		if (this.pessoaDAO.delete(this.pessoa)) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta Removida", null));
			this.listaPessoa.remove(this.pessoa);
		} else 
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Remover Conta", null));
		//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
		//this.listaPessoa = pessoaDAO.listAll();
        //Limpa seleção de usuário
		this.pessoa = null;
        PrimeFaces.current().ajax().update("form:messages", "form:dt-pessoa");
	}	
	
	//Chamado ao salvar cadastro de usuário (novo)
	public void salvarNovo() {
		if (this.pessoa.getGenero().equals("F") || this.pessoa.getGenero().equals("M") || this.pessoa.getGenero().equals("N"))
		{
			if (this.pessoaDAO.insert(this.pessoa)) {
				this.getListaPessoa().add(this.pessoa);
				PrimeFaces.current().executeScript("PF('pessoaDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-pessoa");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pessoa Criada", null));
			} else
        		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Criar Pessoa", null));
		} else {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Genero inválido, deve ser F, M ou N!", null));
    	}   
		PrimeFaces.current().ajax().update("form:messages");
	}
	
	//Chamado ao salvar cadastro de usuário (alteracao)
	public void salvarAlteracao() {
		if (this.pessoa.getGenero().equals("F") || this.pessoa.getGenero().equals("M") || this.pessoa.getGenero().equals("N"))
		{
			if (this.pessoaDAO.update(this.pessoa)) {
				PrimeFaces.current().executeScript("PF('camisetaDialog').hide()");
				PrimeFaces.current().ajax().update("form:dt-camiseta");
				this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta Atualizada", null));
			} else
        		this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha ao Atualizar Cadastro", null));
		} else {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Genero inválido, deve ser F, M ou N!", null));
    	}
		this.setListaPessoa(pessoaDAO.listAll());
		PrimeFaces.current().ajax().update("form:messages");
	}
	
	//GETs e SETs
	//GETs são necessários para elementos visíveis em tela
	//SETs são necessários para elementos que serão editados em tela
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}

	public Boolean getRendNovoCadastro() {
		return rendNovoCadastro;
	}

	public void setRendNovoCadastro(Boolean rendNovoCadastro) {
		this.rendNovoCadastro = rendNovoCadastro;
	}
}
