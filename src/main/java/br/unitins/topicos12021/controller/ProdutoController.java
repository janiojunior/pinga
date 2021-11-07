package br.unitins.topicos12021.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.dao.MarcaDAO;
import br.unitins.topicos12021.dao.ProdutoDAO;
import br.unitins.topicos12021.model.Marca;
import br.unitins.topicos12021.model.Produto;

@Named
@ViewScoped
public class ProdutoController implements Serializable {
	
	private static final long serialVersionUID = -7525395555473322453L;
	private Produto produto;
	private List<Marca> listaMarca;
	
	public ProdutoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("produtoFlash");
		produto = (Produto) flash.get("produtoFlash");
	}
	
	public Produto getProduto() {
		if (produto == null) {
			produto = new Produto();
			produto.setMarca(new Marca());
		}
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void incluir() {
		ProdutoDAO dao = new ProdutoDAO();
		
		if (dao.incluir(getProduto())) {
			limpar();
			Util.addInfoMessage("Inclusao realizada com sucesso.");
		} else 
			Util.addErrorMessage("Erro ao salvar no banco.");
	}
	
	public void editar(Integer id) {
		ProdutoDAO dao = new ProdutoDAO();
		produto = dao.buscarPorId(id);
	}
	
	public void alterar() {
		ProdutoDAO dao = new ProdutoDAO();
		
		if (dao.alterar(getProduto())) {
			limpar();
			Util.addInfoMessage("Alteração realizada com sucesso.");
		} else 
			Util.addErrorMessage("Erro ao alterar os dados no banco.");
	}
	
	public void excluir() {
		ProdutoDAO dao = new ProdutoDAO();
		if (dao.excluir(produto.getId())) {
			Util.addInfoMessage("Exclusão realizada com sucesso.");
		} else 
			Util.addErrorMessage("Problema ao excluir.");
		limpar();
	}
	
	public void limpar() {
		produto = null;
		listaMarca = null;
	}

	public List<Marca> getListaMarca() {
		if(listaMarca == null) {
			MarcaDAO dao = new MarcaDAO();
			listaMarca = dao.obterTodos();
			// verificando se o resultado eh nulo, se sim cria um estrutura vazia.
			if (listaMarca == null)
				listaMarca = new ArrayList<Marca>();
		}
		return listaMarca;
	}
	
}
