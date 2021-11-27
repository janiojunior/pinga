package br.unitins.topicos12021.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Session;
import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.dao.VendaDAO;
import br.unitins.topicos12021.model.Usuario;
import br.unitins.topicos12021.model.Venda;

@Named
@ViewScoped
public class HistoricoController implements Serializable{

	private static final long serialVersionUID = -5454521485423660446L;
	private List<Venda> listaVenda;

	public List<Venda> getListaVenda() {
		if (listaVenda == null) {
			Usuario usuario = (Usuario) Session.getInstance().get("usuarioLogado");
			VendaDAO dao = new VendaDAO();
			listaVenda = dao.obterTodos(usuario);
		}
		return listaVenda;
	}
	
	public void detalhes(Venda venda) {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("vendaFlash", venda);
		Util.redirect("detalhesvenda.xhtml");
	}

	public void setListaVenda(List<Venda> listaVenda) {
		this.listaVenda = listaVenda;
	}

}
