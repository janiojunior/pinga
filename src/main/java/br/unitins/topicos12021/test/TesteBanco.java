package br.unitins.topicos12021.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TesteBanco {
	
	public static void main(String[] args) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("O Driver nao foi encontrado.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		Connection conn = null;
		try {
			conn = DriverManager
					.getConnection("jdbc:postgresql://127.0.0.1:5432/pingadb",
							"topicos1", "123456");
		} catch (SQLException e) {
			System.out.println("Erro ao conectar no banco de dados.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		ResultSet rs = null;
		try {
			rs = conn.createStatement()
					.executeQuery("SELECT id, nome, cpf, email, senha FROM usuario ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			while(rs.next()) {
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("nome"));
				System.out.println(rs.getString("cpf"));
				System.out.println(rs.getString("email"));
				System.out.println(rs.getString("senha"));
				System.out.println("----------------------------");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
