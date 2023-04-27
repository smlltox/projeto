package br.edu.projeto.model;

public class Pessoa {
	//atribu
	
    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String nome;
	
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String nome_sc;
	
    @NotNull
    
    private String cpf;
	
    @NotNull
    private int height;
	
    @NotNull
    private int peso;
	
    @NotNull
    private int idade;
	
    @NotNull
    private String genero;
	
    @NotNull
    private String email;
	
	
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
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomesc() {
        return nome_sc;
    }
    
    public void setNomesc(String nome_sc) {
        this.nome = nome_sc;
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
    
    public String getTelefone() {
        return telf;
    }
    
    public void setTelefone(String telf) {
        this.telf = telf;
    }
}
