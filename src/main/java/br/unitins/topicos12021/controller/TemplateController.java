package br.unitins.topicos12021.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Session;
import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.model.Usuario;

@Named
@ViewScoped
public class TemplateController implements Serializable {

	private static final long serialVersionUID = -6595103747941557604L;
	
	private Usuario usuarioLogado;
	
	public Usuario getUsuarioLogado() {
		if (usuarioLogado == null)
			usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public void encerrarSessao() {
		Session.getInstance().invalidateSession();
		Util.redirect("login2.xhtml");
	}

}
