package br.edu.projeto.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;


public class Pessoa {
	//atributos
	
	@NotNull
	private int id;
	
    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String nome;
	
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String nomesc;
	
    @NotNull
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF inválido")
    private String cpf;
	
    @NotNull
    @Size(max = 2)
    @Positive(message = "O valor deve ser maior que zero")
    private int height;
	
    @NotNull
    @Positive(message = "O valor deve ser maior que zero")
    @Min(value = 1, message = "O valor deve ser um número inteiro")
    private int peso;
	
    @NotNull
    @Positive(message = "O valor deve ser maior que zero")
    @Min(value = 1, message = "O valor deve ser um número inteiro")
    private int idade;
	
    @NotNull
    private String genero;
	
    @NotNull
    @Email
    private String email;
	
    @Pattern(regexp = "(\\d{2}\\)\\d{5}\\-\\d{4}", message = "Número de telefone inválido")
    private String telf;
	
    private String endereco;
    
    
    //public Pessoa(String nome, String nome_sc, int idade, String genero, String endereco, String cpf, int height, int peso, String telf) {
    //    this.nome = nome;
    //    this.nome_sc = nome_sc;
    //    this.idade = idade;
    //    this.genero = genero;
    //    this.endereco = endereco;
    //    this.cpf = cpf;
    //    this.height = height;
    //    this.peso = peso;
    //    this.telf = telf;
    //}
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomesc() {
        return nomesc;
    }
    
    public void setNomesc(String nomesc) {
        this.nomesc = nomesc;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    

    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getPeso() {
        return peso;
    }
    
    public void setPeso(int peso) {
        this.peso = peso;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelf() {
        return telf;
    }
    
    public void setTelf(String telf) {
        this.telf = telf;
    }
}