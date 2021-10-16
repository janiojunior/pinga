package br.unitins.topicos12021.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.topicos12021.model.Usuario;

public class UsuarioDAO implements DAO {
	
	@Override
	public boolean incluir(Usuario usuario) {
		Connection conn = DAO.getConnection();
		if (conn == null) 
			return false;
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO usuario ( ");
		sql.append("  nome, ");
		sql.append("  cpf, ");
		sql.append("  email, ");
		sql.append("  data_nascimento, ");
		sql.append("  senha ");
		sql.append(") VALUES (");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?  ");
		sql.append(") ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, usuario.getNome());
			stat.setString(2, usuario.getCpf());
			stat.setString(3, usuario.getEmail());
			stat.setDate(4, usuario.getDataNascimento() == null ? null : Date.valueOf(usuario.getDataNascimento()));
			stat.setString(5, usuario.getSenha());
			
			stat.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
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
		return resultado;
	}

	@Override
	public boolean alterar(Usuario usuario) {
		Connection conn = DAO.getConnection();
		if (conn == null) 
			return false;
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE usuario SET ");
		sql.append("  nome = ?, ");
		sql.append("  cpf = ?, ");
		sql.append("  email = ?, ");
		sql.append("  data_nascimento = ?, ");
		sql.append("  senha = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, usuario.getNome());
			stat.setString(2, usuario.getCpf());
			stat.setString(3, usuario.getEmail());
			stat.setDate(4, usuario.getDataNascimento() == null ? null : Date.valueOf(usuario.getDataNascimento()));
			stat.setString(5, usuario.getSenha());
			stat.setInt(6, usuario.getId());
			
			stat.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
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
		return resultado;
	}

	@Override
	public boolean excluir(Integer id) {
		Connection conn = DAO.getConnection();
		if (conn == null) 
			return false;
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM usuario WHERE id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			stat.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			resultado = false;
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
		return resultado;
	}

	@Override
	public List<Usuario> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Usuario> listaUsuario = new ArrayList<Usuario>();
		
		if (conn == null) 
			return null;
			
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  id, ");
			sql.append("  nome, ");
			sql.append("  cpf, ");
			sql.append("  email, ");
			sql.append("  data_nascimento, ");
			sql.append("  senha ");
			sql.append("FROM ");
			sql.append("  usuario ");
			sql.append("ORDER BY ");
			sql.append("  nome ");
			
			rs = conn.createStatement().executeQuery(sql.toString());
			while(rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				
				Date data = rs.getDate("data_nascimento");
				if (data == null)
					usuario.setDataNascimento(null);
				else
					usuario.setDataNascimento(data.toLocalDate());
				
				usuario.setSenha(rs.getString("senha"));
				
				listaUsuario.add(usuario);
			}
			
		} catch (SQLException e) {
			listaUsuario = null;
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return listaUsuario;
		
	}

}
