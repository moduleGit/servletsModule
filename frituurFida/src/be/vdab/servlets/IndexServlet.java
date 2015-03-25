package be.vdab.servlets;

import java.io.IOException;
import java.util.Calendar;


import javax.servlet.ServletContext;
//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Adres;
import be.vdab.entities.Gemeente;


@WebServlet(urlPatterns = "/index.htm",name = "indexservlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	
	private final Adres adres = new Adres();
	
	/*public IndexServlet() {
		adres.setStraat("Pizzalaan");
		adres.setHuisNr("18");
		Gemeente gemeente = new Gemeente();
		gemeente.setPostcode(3600);
		gemeente.setNaam("Genk");
		adres.setGemeente(gemeente);
	}*/
	
	@Override
	public void init() throws ServletException {
		ServletContext context = this.getServletContext();
		adres.setStraat(context.getInitParameter("straat"));
		adres.setHuisNr(context.getInitParameter("huisNr"));
		Gemeente gemeente = new Gemeente();
		gemeente.setPostcode(Integer.parseInt(context.getInitParameter("postcode")));
		gemeente.setNaam(context.getInitParameter("gemeente"));
		adres.setGemeente(gemeente);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int dagVanDeWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		// request attribuut aanmaken
		request.setAttribute("openGesloten", dagVanDeWeek==Calendar.MONDAY 
				|| dagVanDeWeek==Calendar.THURSDAY ? "gesloten":"open");
		// request van servlet doorgeven aan JSP file
		request.setAttribute("adres", adres);
		/*RequestDispatcher dispatcher = request.getRequestDispatcher(VIEW);
		dispatcher.forward(request, response);*/
		//		analoog
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	
}
