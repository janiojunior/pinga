package br.unitins.topicos12021.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.unitins.topicos12021.application.Session;
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

		if (usu != null) {
			// adicionando na sessao
			Session.getInstance().set("usuarioLogado", usu);
			
			Util.redirect("template.xhtml");
			
		}
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
