package br.unitins.topicos12021.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.dao.UsuarioDAO;
import br.unitins.topicos12021.model.Perfil;
import br.unitins.topicos12021.model.Sexo;
import br.unitins.topicos12021.model.Telefone;
import br.unitins.topicos12021.model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable {
	
	private static final long serialVersionUID = -6458187335274531216L;
	private Usuario usuario;
	private List<Usuario> listaUsuario;

	public Perfil[] getListaPerfil() {
		return Perfil.values();
	}
	
	public Sexo[] getListaSexo() {
		return Sexo.values();
	}
	
	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
			usuario.setTelefone(new Telefone());
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void validarNome() {
		if (getUsuario().getNome() == null || getUsuario().getNome().trim().equals("")) {
			Util.addErrorMessage("O nome deve ser informado.");
		}
	}
	
	public boolean validarCampos() {
		boolean retorno = true;
		
		if (getUsuario().getNome() == null || getUsuario().getNome().trim().equals("")) {
			Util.addErrorMessage("O nome deve ser informado.");
			retorno = false;
		}
		if (getUsuario().getSenha() == null || getUsuario().getSenha().trim().equals("")) {
			Util.addErrorMessage("A senha deve ser informada.");
			retorno = false;
		}
		return retorno;
	}
	
	public void incluir() {
		if (validarCampos()) {
			UsuarioDAO dao = new UsuarioDAO();
			
			// gerando o hash da senha
			getUsuario().setSenha(Util.hash(getUsuario()));
			
			if (dao.incluir(getUsuario())) {
				limpar();
				Util.addInfoMessage("Inclusao realizada com sucesso.");
			} else 
				Util.addErrorMessage("Erro ao salvar no banco.");
		}
	}
	
	public void editar(Usuario usu) {
		setUsuario(usu.getClone());
		if (getUsuario().getTelefone() == null)
			getUsuario().setTelefone(new Telefone());
	}
	
	public void alterar() {
		if (validarCampos()) {
			UsuarioDAO dao = new UsuarioDAO();
			
			// gerando o hash da senha
			getUsuario().setSenha(Util.hash(getUsuario()));
			
			if (dao.alterar(getUsuario())) {
				limpar();
				Util.addInfoMessage("Alteração realizada com sucesso.");
			} else 
				Util.addErrorMessage("Erro ao alterar os dados no banco.");
		}
		
	}
	
	// do botao principal
	public void excluir() {
		excluir(getUsuario());
		limpar();
	}
	
	// do botao do datatable
	public void excluir(Usuario usu) {
		UsuarioDAO dao = new UsuarioDAO();
		if (dao.excluir(usu.getId())) {
			Util.addInfoMessage("Exclusão realizada com sucesso.");
			listaUsuario = null;
		} else 
			Util.addErrorMessage("Problema ao excluir.");
		
	}
	
	public void limpar() {
		usuario = null;
		listaUsuario = null;
	}
	

	public List<Usuario> getListaUsuario() {
		if(listaUsuario == null) {
			UsuarioDAO dao = new UsuarioDAO();
			listaUsuario = dao.obterTodos();
			
			// verificando se o resultado eh nulo, se sim cria um estrutura vazia.
			if (listaUsuario == null)
				listaUsuario = new ArrayList<Usuario>();
			
		}
		return listaUsuario;
	}
	
}
