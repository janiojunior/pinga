package br.unitins.topicos12021.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.dao.UsuarioDAO;
import br.unitins.topicos12021.model.Usuario;

@Named
@RequestScoped
public class LoginController {
	
	private Usuario usuario;
	
	public String entrar() {
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usu = dao.verificarUsuario(
				usuario.getEmail(), 
				Util.hash(usuario));

		if (usu != null)
			return "hello.xhtml";
		Util.addErrorMessage("Usuario, cpf ou senha inválido.");
		return null;
	}
	
	public void limpar() {
		usuario = null;
	}

	public Usuario getUsuario() {
		if (usuario == null)
			usuario = new Usuario();
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

	
}
