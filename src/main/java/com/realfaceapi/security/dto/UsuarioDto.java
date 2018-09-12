package com.realfaceapi.security.dto;

public class UsuarioDto {
	
	private String nome;
	private String email;
	private String senha;
	
	public UsuarioDto() {
		
	}
	
	public UsuarioDto(String nome, String email, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
	public UsuarioDto(String email) {
		super();
		this.email = email;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
