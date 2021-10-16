package br.unitins.topicos12021.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import br.unitins.topicos12021.model.Usuario;

public interface DAO {
	
	public boolean incluir(Usuario usuario);
	public boolean alterar(Usuario usuario);
	public boolean excluir(Integer id);
	public List<Usuario> obterTodos();
	
	public static Connection getConnection() {
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

}
