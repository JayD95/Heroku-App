package nl.hu.v1wac.firstapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/DynamicServletCalculator.do")
public class DynamicServletCalculator extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String stnm1 = req.getParameter("invoer1");
		String stnm2 = req.getParameter("invoer2");
		int nummer1 = Integer.parseInt(stnm1);
		int nummer2 = Integer.parseInt(stnm2);
		int uitkomst = 0;
		
		if(req.getParameter("uitvoeren").equals("Optellen")) {
			uitkomst = nummer1 + nummer2;
		}
		
		if(req.getParameter("uitvoeren").equals("Aftrekken")) {
			uitkomst = nummer1 - nummer2;
		}
		
		if(req.getParameter("uitvoeren").equals("Vermenigvuldigen")) {
			uitkomst = nummer1 * nummer2;
		}
		
		if(req.getParameter("uitvoeren").equals("Delen")) {
			uitkomst = nummer1 / nummer2;
		}
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println(" <title>Dynamic Example</title>");
		out.println(" <body>");
		out.println(" <h2>Dynamic webapplication example</h2>");
		out.println(" <h2>Antwoord: " + uitkomst + "!</h2>");
		out.println(" </body>");
		out.println("</html>");
		
		
		
		
		
		
	}

}
