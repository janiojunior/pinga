package br.unitins.topicos12021.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Session;
import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.dao.VendaDAO;
import br.unitins.topicos12021.model.ItemVenda;
import br.unitins.topicos12021.model.Usuario;
import br.unitins.topicos12021.model.Venda;

@Named
@ViewScoped
public class CarrinhoController implements Serializable {

	private static final long serialVersionUID = -8507025255836333166L;
	private List<ItemVenda> listaItemVenda = null;

	@SuppressWarnings("unchecked")
	public List<ItemVenda> getListaItemVenda() {
		listaItemVenda = (List<ItemVenda>) Session.getInstance().get("carrinho");
		
		return listaItemVenda;
	}

	public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}
	
	public void finalizar() {
		// verificar se existe um usuario logado no sistema
		Usuario usuarioLogado = (Usuario) Session.getInstance().get("usuarioLogado");
		if (usuarioLogado == null) {
			Util.addErrorMessage("Faça o Login para concluir a venda.");
			return;
		}
		
		// verificar se existe algum produto no carrinho (sessao)
		List<ItemVenda> carrinho = (List<ItemVenda>) Session.getInstance().get("carrinho");
		if (carrinho == null || carrinho.isEmpty() ) {
			Util.addErrorMessage("Não existem produtos no carrinho");
			return;
		}
		
		Venda venda = new Venda();
		venda.setData(LocalDate.now());
		venda.setUsuario(usuarioLogado);
		venda.setListaItemVenda(carrinho);
		
	//	 salvando no banco de dados
		VendaDAO dao = new VendaDAO();
		if (dao.incluir(venda)) 
			Util.addInfoMessage("Venda realizada com sucesso.");
		else 
			Util.addErrorMessage("Problemas ao realizar a venda.");
		
	}

}
