package br.unitins.topicos12021.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.topicos12021.application.Util;
import br.unitins.topicos12021.model.Perfil;
import br.unitins.topicos12021.model.Sexo;
import br.unitins.topicos12021.model.Telefone;
import br.unitins.topicos12021.model.Usuario;

@Named
@ViewScoped
public class UsuarioController implements Serializable {
	
	private static final long serialVersionUID = -6458187335274531216L;
	private Integer cont = 0;
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
			Connection conn = getConnection();
			if (conn == null) {
				Util.addErrorMessage("Erro ao conectar no banco de dados.");
				return;
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO USUARIO ( ");
			sql.append("  nome, ");
			sql.append("  cpf, ");
			sql.append("  email, ");
			sql.append("  senha ");
			sql.append(") VALUES (");
			sql.append("  ?,  ");
			sql.append("  ?,  ");
			sql.append("  ?,  ");
			sql.append("  ?  ");
			sql.append(") ");
			
			PreparedStatement stat = null;
			try {
				stat = conn.prepareStatement(sql.toString());
				stat.setString(1, getUsuario().getNome());
				stat.setString(2, getUsuario().getCpf());
				stat.setString(3, getUsuario().getEmail());
				stat.setString(4, getUsuario().getSenha());
				
				stat.execute();
				limpar();
				Util.addInfoMessage("Inclusao realizada com sucesso.");
				
			} catch (SQLException e) {
				Util.addErrorMessage("Erro ao salvar no banco.");
				e.printStackTrace();
			} finally {
				try {
					stat.close();
				} catch (SQLException e) {
				}
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
			
		}
	}
	
	public void editar(Usuario usu) {
		setUsuario(usu.getClone());
		if (getUsuario().getTelefone() == null)
			getUsuario().setTelefone(new Telefone());
	}
	
	public void alterar() {
		if (validarCampos()) {
			int index = getListaUsuario().indexOf(getUsuario());
			getListaUsuario().set(index, getUsuario());
			limpar();
			Util.addInfoMessage("Alteração realizada com sucesso.");
		}
		
		// outra forma de fazer
//		for (int indice = 0; indice < getListaUsuario().size(); indice++) {
//			if (getListaUsuario().get(indice).getId().equals(getUsuario().getId())) {
//				
//				limpar();
//				return;
//			}
//		}
	}
	
	public void excluir() {
		excluir(getUsuario());
		limpar();
	}
	
	public void excluir(Usuario usu) {
		getListaUsuario().remove(usu);
	}
	
	public void limpar() {
		usuario = null;
		listaUsuario = null;
	}
	
	private Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("O Driver nao foi encontrado.");
			e.printStackTrace();
			return null;
		}
		
		Connection conn = null;
		try {
			conn = DriverManager
					.getConnection("jdbc:postgresql://127.0.0.1:5432/pingadb",
							"topicos1", "123456");
		} catch (SQLException e) {
			System.out.println("Erro ao conectar no banco de dados.");
			e.printStackTrace();
		}
		
		return conn;
	}

	public List<Usuario> getListaUsuario() {
		if(listaUsuario == null) {
			
			Connection conn = getConnection();
			listaUsuario = new ArrayList<Usuario>();
			if (conn == null) {
				Util.addErrorMessage("Problemas ao conectar no banco.");
			} else {
				try {
					StringBuffer sql = new StringBuffer();
					sql.append("SELECT ");
					sql.append("  id, ");
					sql.append("  nome, ");
					sql.append("  cpf, ");
					sql.append("  email, ");
					sql.append("  senha ");
					sql.append("FROM ");
					sql.append("  usuario ");
					sql.append("ORDER BY ");
					sql.append("  nome ");
					
					ResultSet rs = conn.createStatement().executeQuery(sql.toString());
					while(rs.next()) {
						Usuario usuario = new Usuario();
						usuario.setId(rs.getInt("id"));
						usuario.setNome(rs.getString("nome"));
						usuario.setCpf(rs.getString("cpf"));
						usuario.setEmail(rs.getString("email"));
						usuario.setSenha(rs.getString("senha"));
						
						listaUsuario.add(usuario);
					}
					rs.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		return listaUsuario;
	}
	
}
