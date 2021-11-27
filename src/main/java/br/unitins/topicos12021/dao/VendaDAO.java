package br.unitins.topicos12021.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.topicos12021.model.ItemVenda;
import br.unitins.topicos12021.model.Marca;
import br.unitins.topicos12021.model.Produto;
import br.unitins.topicos12021.model.Usuario;
import br.unitins.topicos12021.model.Venda;

public class VendaDAO implements DAO<Venda> {
	
	@Override
	public boolean incluir(Venda venda) {
		Connection conn = DAO.getConnection();
		if (conn == null) 
			return false;
		
		// controlando a transacao de forma manual
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		boolean resultado = true;
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO venda ( ");
		sql.append("  data, ");
		sql.append("  id_usuario ");
		sql.append(") VALUES (");
		sql.append("  ?,  ");
		sql.append("  ?  ");
		sql.append(") ");
		
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			stat.setDate(1, Date.valueOf(venda.getData()));
			stat.setInt(2, venda.getUsuario().getId());
			
			stat.execute();
			
			// obtendo o id gerado pelo banco
			ResultSet rs = stat.getGeneratedKeys();
			if (rs.next()) {
				venda.setId(rs.getInt("id"));
			}
			
			salvarItensVenda(venda, conn);
			
			// salvando por definitivo os dados no banco
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
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

	private void salvarItensVenda(Venda venda, Connection conn) throws SQLException{
		
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO item_venda ( ");
		sql.append("  valor, ");
		sql.append("  quantidade, ");
		sql.append("  id_venda, ");
		sql.append("  id_produto ");
		sql.append(") VALUES (");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?,  ");
		sql.append("  ?  ");
		sql.append(") ");
		
		PreparedStatement stat = null;
		for (ItemVenda itemVenda : venda.getListaItemVenda()) {
			stat = conn.prepareStatement(sql.toString());
			stat.setDouble(1, itemVenda.getValor());
			stat.setInt(2, itemVenda.getQuantidade());
			stat.setInt(3, venda.getId());
			stat.setInt(4, itemVenda.getProduto().getId());
			
			stat.execute();
		}
		
	}

	@Override
	public boolean alterar(Venda venda) {
		return false;
	}

	@Override
	public boolean excluir(Integer id) {
		return false;
	}
	
	@Override
	public List<Venda> obterTodos() {
		return null;
	}

	public List<Venda> obterTodos(Usuario usuario) {
		Connection conn = DAO.getConnection();
		
		List<Venda> listaVenda = new ArrayList<Venda>();
		
		if (conn == null) 
			return null;
			
		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("  v.id, ");
			sql.append("  v.data ");
			sql.append("FROM ");
			sql.append("  venda v ");
			sql.append("WHERE  ");
			sql.append("  v.id_usuario = ? ");
			sql.append("ORDER BY ");
			sql.append("  v.data DESC ");
			
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, usuario.getId());
			
			rs = stat.executeQuery();
			while(rs.next()) {
				Venda venda = new Venda();
				venda.setId(rs.getInt("id"));
				//venda.setData(LocalDateTime.of(rs.getDate("data").toLocalDate(), null));
				venda.setUsuario(usuario);
				venda.setListaItemVenda(obterItensVenda(venda.getId()));
				
				listaVenda.add(venda);
			}
			
		} catch (SQLException e) {
			listaVenda = null;
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
		
		return listaVenda;
		
	}

	private List<ItemVenda> obterItensVenda(Integer id) {
	
		Connection conn = DAO.getConnection();
		
		List<ItemVenda> lista = new ArrayList<ItemVenda>();
		
		if (conn == null) 
			return null;
			
		ResultSet rs = null;
		PreparedStatement stat = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT  ");
			sql.append("  i.id, ");
			sql.append("  i.valor, ");
			sql.append("  i.quantidade, ");
			sql.append("  p.id AS id_produto, ");
			sql.append("  p.nome AS nome_produto, ");
			sql.append("  p.descricao AS descricao_produto, ");
			sql.append("  p.valor AS valor_produto, ");
			sql.append("  p.estoque AS estoque_produto, ");
			sql.append("  m.id AS id_marca, ");
			sql.append("  m.nome AS nome_marca ");
			sql.append("FROM  ");
			sql.append("	item_venda i, ");
			sql.append("	produto p, ");
			sql.append("	marca m ");
			sql.append("WHERE  ");
			sql.append("	i.id_produto = p.id AND ");
			sql.append("	p.id_marca = m.id AND ");
			sql.append("	i.id_venda = ? ");
			
			stat = conn.prepareStatement(sql.toString());
			stat.setInt(1, id);
			
			rs = stat.executeQuery();
			while(rs.next()) {
				ItemVenda item = new ItemVenda();
				item.setId(rs.getInt("id"));
				item.setValor(rs.getDouble("valor"));
				item.setQuantidade(rs.getInt("quantidade"));
				
				item.setProduto(new Produto());
				item.getProduto().setId(rs.getInt("id_produto"));
				item.getProduto().setNome(rs.getString("nome_produto"));
				item.getProduto().setDescricao(rs.getString("descricao_produto"));
				item.getProduto().setValor(rs.getDouble("valor_produto"));
				item.getProduto().setEstoque(rs.getInt("estoque_produto"));
				
				item.getProduto().setMarca(new Marca());
				item.getProduto().getMarca().setId(rs.getInt("id_marca"));
				item.getProduto().getMarca().setNome(rs.getString("nome_marca"));
				
				lista.add(item);
			}
			
		} catch (SQLException e) {
			lista = null;
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
		
		return lista;	
		
	}
	
//	public Venda buscarPorId(Integer id) {
//		Connection conn = DAO.getConnection();
//		
//		if (conn == null) 
//			return null;
//			
//		PreparedStatement stat = null;
//		ResultSet rs = null;
//		Venda venda = null;
//		
//		try {
//			StringBuffer sql = new StringBuffer();
//			sql.append("SELECT ");
//			sql.append("  p.id, ");
//			sql.append("  p.nome, ");
//			sql.append("  p.descricao, ");
//			sql.append("  p.valor, ");
//			sql.append("  p.estoque, ");
//			sql.append("  m.id AS id_marca, ");
//			sql.append("  m.nome AS nome_marca ");
//			sql.append("FROM ");
//			sql.append("  venda p INNER JOIN marca m ON p.id_marca = m.id ");
//			sql.append("WHERE ");
//			sql.append(" p.id = ? ");
//			
//			stat = conn.prepareStatement(sql.toString());
//			stat.setInt(1, id);
//			
//			rs = stat.executeQuery();
//			
//			if(rs.next()) {
//				venda = new Venda();
//				venda.setId(rs.getInt("id"));
//				venda.setNome(rs.getString("nome"));
//				venda.setDescricao(rs.getString("descricao"));
//				venda.setValor(rs.getDouble("valor"));
//				venda.setEstoque(rs.getInt("estoque"));
//				
//				venda.setMarca(new Marca());
//				venda.getMarca().setId(rs.getInt("id_marca"));
//				venda.getMarca().setNome(rs.getString("nome_marca"));
//			}
//			
//		} catch (SQLException e) {
//			venda = null;
//			e.printStackTrace();
//		} finally {
//			try {
//				stat.close();
//			} catch (SQLException e) {
//			}
//			try {
//				rs.close();
//			} catch (SQLException e) {
//			}
//			try {
//				conn.close();
//			} catch (SQLException e) {
//			}
//		}
//		
//		return venda;
//			
//	}
	

	
}
