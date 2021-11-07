package br.unitins.topicos12021.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.dao.ProdutoDAO;
import br.unitins.topicos12021.model.Produto;

@Named
@ViewScoped
public class ConsultaProdutoController implements Serializable {

	private static final long serialVersionUID = -2590665554677754478L;
	private Integer tipoFiltro;
	private String filtro;
	private List<Produto> listaProduto;
	
	public String novoProduto() {
		return "produto.xhtml?faces-redirect=true";
	}
	
	public void pesquisar() {
		ProdutoDAO dao = new ProdutoDAO();
		listaProduto = dao.buscarPorNome(getFiltro());
	}
	
	public String editar(Integer id) {
		ProdutoDAO dao = new ProdutoDAO();
		Produto produto = dao.buscarPorId(id);
		
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("produtoFlash", produto);
		
		return "produto.xhtml?faces-redirect=true";
	}

	public Integer getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(Integer tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Produto> getListaProduto() {
		if (listaProduto == null)
			listaProduto = new ArrayList<Produto>();
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}
	
}
