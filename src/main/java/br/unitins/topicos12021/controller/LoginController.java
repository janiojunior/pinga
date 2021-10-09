package br.unitins.topicos12021.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginController {
	
	private String usuario = "teste";
	private String senha = "123";
	
	public String entrar() {
		System.out.println("executou o metodo Entrar");
		System.out.println(getUsuario());
		System.out.println(getSenha());
		
		if (getUsuario().equals("janio@gmail.com") && getSenha().equals("123")) {
//			return "hello.xhtml";
			return "usuario.xhtml?faces-redirect=true";
		}
		return null;
	}
	
	public void limpar() {
		usuario = "";
		senha = "";
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
