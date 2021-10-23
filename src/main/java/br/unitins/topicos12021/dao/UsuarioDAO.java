package br.unitins.topicos12021.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.topicos12021.model.Perfil;
import br.unitins.topicos12021.model.Sexo;
import br.unitins.topicos12021.model.Telefone;
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
		sql.append("  senha, ");
		sql.append("  perfil, ");
		sql.append("  sexo ");
		sql.append(") VALUES (");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?  ");
		sql.append(") ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, usuario.getNome());
			stat.setString(2, usuario.getCpf());
			stat.setString(3, usuario.getEmail());
			stat.setDate(4, usuario.getDataNascimento() == null ? null : Date.valueOf(usuario.getDataNascimento()));
			stat.setString(5, usuario.getSenha());
			
			if (usuario.getPerfil() == null)
				stat.setObject(6, null);
			else
				stat.setInt(6, usuario.getPerfil().getId());
			
			if (usuario.getSexo() == null)
				stat.setObject(7, null);
			else
				stat.setInt(7, usuario.getSexo().getId());
			
			stat.execute();
			
			ResultSet rs = stat.getGeneratedKeys();
			
			if (rs.next()) {
				Integer id = rs.getInt("id");
				
				sql = new StringBuffer();
				sql.append("INSERT INTO telefone ( ");
				sql.append("  id, ");
				sql.append("  codigo_area, ");
				sql.append("  numero ");
				sql.append(") VALUES (");
				sql.append("  ?,  ");
				sql.append("  ?,  ");
				sql.append("  ?  ");
				sql.append(") ");
				
				stat.close();
				stat = conn.prepareStatement(sql.toString());
				stat.setInt(1, id);
				stat.setString(2, usuario.getTelefone().getCodigoArea());
				stat.setString(3, usuario.getTelefone().getNumero());
				
				stat.execute();
			}
			
			
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
		sql.append("  senha = ?, ");
		sql.append("  perfil = ?, ");
		sql.append("  sexo = ? ");
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
			if (usuario.getPerfil() == null)
				stat.setObject(6, null);
			else
				stat.setInt(6, usuario.getPerfil().getId());
			
			if (usuario.getSexo() == null)
				stat.setObject(7, null);
			else
				stat.setInt(7, usuario.getSexo().getId());
			
			stat.setInt(8, usuario.getId());
			
			stat.execute();
			
			sql = new StringBuffer();
			if (usuario.getTelefone().getId() == null) {
				sql.append("INSERT INTO telefone ( ");
				sql.append("  codigo_area, ");
				sql.append("  numero, ");
				sql.append("  id ");
				sql.append(") VALUES (");
				sql.append("  ?,  ");
				sql.append("  ?,  ");
				sql.append("  ?  ");
				sql.append(") ");
			} else {
				sql.append("UPDATE telefone SET ");
				sql.append("  codigo_area = ?, ");
				sql.append("  numero = ? ");
				sql.append("WHERE ");
				sql.append("  id = ? ");
			}
			
			stat.close();
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, usuario.getTelefone().getCodigoArea());
			stat.setString(2, usuario.getTelefone().getNumero());
			stat.setInt(3, usuario.getId());
			
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
			sql.append("  u.id, ");
			sql.append("  u.nome, ");
			sql.append("  u.cpf, ");
			sql.append("  u.email, ");
			sql.append("  u.data_nascimento, ");
			sql.append("  u.senha, ");
			sql.append("  u.perfil, ");
			sql.append("  u.sexo, ");
			sql.append("  t.id AS id_telefone, ");
			sql.append("  t.codigo_area, ");
			sql.append("  t.numero ");
			sql.append("FROM ");
			sql.append("  usuario u LEFT JOIN telefone t ON u.id = t.id ");
			sql.append("ORDER BY ");
			sql.append("  u.nome ");
			
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
				
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuario.setSexo(Sexo.valueOf(rs.getInt("sexo")));
				
				usuario.setTelefone(new Telefone());
				
				// esse codigo eh necessario por conta de o id estar nulo
				Object idTelefone = rs.getObject("id_telefone");
						
				usuario.getTelefone().setId(idTelefone == null ? null : (Integer) idTelefone);
				usuario.getTelefone().setCodigoArea(rs.getString("codigo_area"));
				usuario.getTelefone().setNumero(rs.getString("numero"));
				
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
