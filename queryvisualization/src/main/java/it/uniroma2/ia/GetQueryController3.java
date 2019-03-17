package it.uniroma2.ia;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.uniroma.ia.dao.QueryDao;

/**
 * Servlet implementation class GetQueryController3
 */

	
	
	public class GetQueryController3 extends HttpServlet {
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			//response.getWriter().append("Served at: ").append(request.getContextPath());
			
			String pred = request.getParameter("pred");
			//String pred = request.getParameter("pred");
			//String obj = request.getParameter("obj");
			QueryDao dao = new QueryDao();
			
			Map<Integer, String> risultati = dao.getResult3(pred);
			for (Map.Entry<Integer, String> entry : risultati.entrySet())
			{
				System.out.println("SONO NELLA MAP");
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			}
			request.setAttribute("a", risultati);
			
			RequestDispatcher rd = request.getRequestDispatcher("showresult3.jsp");  
			  
			rd.forward(request, response); 
		}

	}


