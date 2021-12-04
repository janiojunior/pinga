package br.unitins.topicos12021.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// nao esquecer a barra
@WebServlet("/exemploservlet.php")
public class ExemploServlet extends HttpServlet{

	private static final long serialVersionUID = 266799267283425723L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("  <head>");
		out.println("  </head>");
		out.println("  <body>");
		out.println("    <h1> Teste de Servlet </h1>");
		out.println("  </body>");
		out.println("</html>");
		
		//response.sendRedirect("hello.xhtml");

	}
	
}
