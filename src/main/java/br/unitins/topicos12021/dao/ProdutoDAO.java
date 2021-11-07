package br.unitins.topicos12021.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.topicos12021.model.Marca;
import br.unitins.topicos12021.model.Perfil;
import br.unitins.topicos12021.model.Produto;
import br.unitins.topicos12021.model.Sexo;
import br.unitins.topicos12021.model.Telefone;

public class ProdutoDAO implements DAO<Produto> {
	
	@Override
	public boolean incluir(Produto produto) {
		Connection conn = DAO.getConnection();
		if (conn == null) 
			return false;
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO produto ( ");
		sql.append("  nome, ");
		sql.append("  descricao, ");
		sql.append("  valor, ");
		sql.append("  estoque, ");
		sql.append("  id_marca ");
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
			stat.setString(1, produto.getNome());
			stat.setString(2, produto.getDescricao());
			stat.setDouble(3, produto.getValor());
			stat.setInt(4, produto.getEstoque());
			stat.setInt(5, produto.getMarca().getId());
			
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
	public boolean alterar(Produto produto) {
		Connection conn = DAO.getConnection();
		if (conn == null) 
			return false;
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE produto SET ");
		sql.append("  nome = ?, ");
		sql.append("  descricao = ?, ");
		sql.append("  valor = ?, ");
		sql.append("  estoque = ?, ");
		sql.append("  id_marca = ? ");
		sql.append("WHERE ");
		sql.append("  id = ? ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, produto.getNome());
			stat.setString(2, produto.getDescricao());
			stat.setDouble(3, produto.getValor());
			stat.setInt(4, produto.getEstoque());
			stat.setInt(5, produto.getMarca().getId());
			
			stat.setInt(6, produto.getId());
			
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
		sql.append("DELETE FROM produto WHERE id = ? ");
		
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
	public List<Produto> obterTodos() {
		Connection conn = DAO.getConnection();
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		
		if (conn == null) 
			return null;
			
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  p.id, ");
			sql.append("  p.nome, ");
			sql.append("  p.descricao, ");
			sql.append("  p.valor, ");
			sql.append("  p.estoque, ");
			sql.append("  m.id AS id_marca, ");
			sql.append("  m.nome AS nome_marca ");
			sql.append("FROM ");
			sql.append("  produto p INNER JOIN marca m ON p.id_marca = m.id ");
			sql.append("ORDER BY ");
			sql.append("  p.nome ");
			
			rs = conn.createStatement().executeQuery(sql.toString());
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getDouble("valor"));
				produto.setEstoque(rs.getInt("estoque"));
				
				produto.setMarca(new Marca());
				produto.getMarca().setId(rs.getInt("id_marca"));
				produto.getMarca().setNome(rs.getString("nome_marca"));
				
				listaProduto.add(produto);
			}
			
		} catch (SQLException e) {
			listaProduto = null;
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
		
		return listaProduto;
		
	}
	
	public Produto buscarPorId(Integer id) {
		Connection conn = DAO.getConnection();
		
		if (conn == null) 
			return null;
			
		PreparedStatement stat = null;
		ResultSet rs = null;
		Produto produto = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  p.id, ");
			sql.append("  p.nome, ");
			sql.append("  p.descricao, ");
			sql.append("  p.valor, ");
			sql.append("  p.estoque, ");
			sql.append("  m.id AS id_marca, ");
			sql.append("  m.nome AS nome_marca ");
			sql.append("FROM ");
			sql.append("  produto p INNER JOIN marca m ON p.id_marca = m.id ");
			sql.append("WHERE ");
			sql.append(" p.id = ? ");
			
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			
			if(rs.next()) {
				produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getDouble("valor"));
				produto.setEstoque(rs.getInt("estoque"));
				
				produto.setMarca(new Marca());
				produto.getMarca().setId(rs.getInt("id_marca"));
				produto.getMarca().setNome(rs.getString("nome_marca"));
			}
			
		} catch (SQLException e) {
			produto = null;
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
		
		return produto;
			
	}
	
	public List<Produto> buscarPorNome(String nome) {
		Connection conn = DAO.getConnection();
		
		List<Produto> listaProduto = new ArrayList<Produto>();
		
		if (conn == null) 
			return null;
			
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  p.id, ");
			sql.append("  p.nome, ");
			sql.append("  p.descricao, ");
			sql.append("  p.valor, ");
			sql.append("  p.estoque, ");
			sql.append("  m.id AS id_marca, ");
			sql.append("  m.nome AS nome_marca ");
			sql.append("FROM ");
			sql.append("  produto p INNER JOIN marca m ON p.id_marca = m.id ");
			sql.append("WHERE ");
			sql.append("  p.nome iLIKE ? ");
			sql.append("ORDER BY ");
			sql.append("  p.nome ");
			
			stat = conn.prepareStatement(sql.toString());
			stat.setString(1, "%"+nome+"%");
			
			rs = stat.executeQuery();
			while(rs.next()) {
				Produto produto = new Produto();
				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getDouble("valor"));
				produto.setEstoque(rs.getInt("estoque"));
				
				produto.setMarca(new Marca());
				produto.getMarca().setId(rs.getInt("id_marca"));
				produto.getMarca().setNome(rs.getString("nome_marca"));
				
				listaProduto.add(produto);
			}
			
		} catch (SQLException e) {
			listaProduto = null;
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
		
		return listaProduto;
		
	}
	
}
