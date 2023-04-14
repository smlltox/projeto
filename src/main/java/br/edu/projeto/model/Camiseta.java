package br.edu.projeto.model;

public class Camiseta {
	
	//Atributos
	private Long idCamiseta;
	private String descricao;
	private String tamanho;
	
	//GET e SET
	public Long getIdCamiseta() {
		return idCamiseta;
	}
	public void setIdCamiseta(Long idCamiseta) {
		this.idCamiseta = idCamiseta;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

}
