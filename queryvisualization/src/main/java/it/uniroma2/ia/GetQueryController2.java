package it.uniroma2.ia;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.uniroma.ia.dao.QueryDao;


	public class GetQueryController2 extends HttpServlet {
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			//response.getWriter().append("Served at: ").append(request.getContextPath());
			
			//String sbj = request.getParameter("sbj");
			String pred = request.getParameter("pred");
			String obj = request.getParameter("obj");
			QueryDao dao = new QueryDao();
			
			Map<Integer, String> risultati = dao.getResult2(pred,obj);
			for (Map.Entry<Integer, String> entry : risultati.entrySet())
			{
				System.out.println("SONO NELLA MAP");
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			}
			request.setAttribute("predogg", risultati);
			
			RequestDispatcher rd = request.getRequestDispatcher("showresult2.jsp");  
			  
			rd.forward(request, response); 
		}

	}

