package br.unitins.topicos12021.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.model.Produto;
import br.unitins.topicos12021.model.Venda;

@Named
@ViewScoped
public class DetalheVendaController implements Serializable {

	private static final long serialVersionUID = -3788178920569765006L;

	private Venda venda;
	
	public DetalheVendaController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("vendaFlash");
		venda = (Venda) flash.get("vendaFlash");
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
