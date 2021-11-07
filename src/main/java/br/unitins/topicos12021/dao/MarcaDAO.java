package br.unitins.topicos12021.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.topicos12021.model.Marca;

public class MarcaDAO implements DAO<Marca> {

	@Override
	public boolean incluir(Marca obj) {
		return false;
	}

	@Override
	public boolean alterar(Marca obj) {
		return false;
	}

	@Override
	public boolean excluir(Integer id) {
		return false;
	}

	@Override
	public List<Marca> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Marca> listaMarca = new ArrayList<Marca>();
		
		if (conn == null) 
			return null;
			
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  m.id, ");
			sql.append("  m.nome ");
			sql.append("FROM ");
			sql.append("  marca m ");
			sql.append("ORDER BY ");
			sql.append("  m.nome ");
			
			rs = conn.createStatement().executeQuery(sql.toString());
			while(rs.next()) {
				Marca marca = new Marca();
				marca.setId(rs.getInt("id"));
				marca.setNome(rs.getString("nome"));
				
				listaMarca.add(marca);
			}
			
		} catch (SQLException e) {
			listaMarca = null;
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
		
		return listaMarca;
	}
	
	public Marca buscarPorId(Integer id) {
		Connection conn = DAO.getConnection();
		
		if (conn == null) 
			return null;
			
		PreparedStatement stat = null;
		ResultSet rs = null;
		Marca marca = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  m.id, ");
			sql.append("  m.nome ");
			sql.append("FROM ");
			sql.append("  marca m ");
			sql.append("WHERE ");
			sql.append(" m.id = ? ");
			
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			
			if(rs.next()) {
				marca = new Marca();
				marca.setId(rs.getInt("id"));
				marca.setNome(rs.getString("nome"));
			}
			
		} catch (SQLException e) {
			marca = null;
			e.printStackTrace();
		} finally {
			try {
				stat.close();
			} catch (SQLException e) {
			}
			try {
				rs.close();
			} catch (SQLException e) {
			}
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return marca;
	}

}
