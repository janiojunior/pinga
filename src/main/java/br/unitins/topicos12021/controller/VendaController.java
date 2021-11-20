package br.unitins.topicos12021.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Session;
import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.dao.ProdutoDAO;
import br.unitins.topicos12021.model.ItemVenda;
import br.unitins.topicos12021.model.Produto;

@Named
@ViewScoped
public class VendaController implements Serializable {

	private static final long serialVersionUID = -2841445525320968197L;
	private int tipoFiltro;
	private String filtro;
	private List<Produto> listaProduto;

	// metodo utilizado para adicionar o produto no carrinhoi
	public void comprar(Produto produto) {
		
		@SuppressWarnings("unchecked")
		List<ItemVenda> carrinho =(List<ItemVenda>) Session.getInstance().get("carrinho");
		// caso nao exista o carrinho, criar um espaco de memoria
		if (carrinho == null)
			carrinho = new ArrayList<ItemVenda>();
		
		ItemVenda item = new ItemVenda();
		item.setProduto(produto);
		item.setValor(produto.getValor());
		item.setQuantidade(1);
		
		// se existe no carrinho, atualizar a quantidade
		if (carrinho.contains(item)) {
			int index = carrinho.indexOf(item);
			int quantidade = carrinho.get(index).getQuantidade() + 1;
			carrinho.get(index).setQuantidade(quantidade);
			
		} else {
			// adicionando o novo item no carrinho
			carrinho.add(item);
		}
		
		// adicionando / atualizando o carrinho na sessao
		Session.getInstance().set("carrinho", carrinho);
		
		Util.addInfoMessage("Produto adicionado na sessão.");
		
	}
	
	public void pesquisar() {
		ProdutoDAO dao = new ProdutoDAO();
		setListaProduto(dao.buscarPorNome(getFiltro()));
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public int getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(int tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

}
