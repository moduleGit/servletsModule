package be.vdab.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;



import javax.servlet.ServletContext;
//import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import vdab.be.entities.Adres;


import be.vdab.entities.Persoon;

//@WebServlet("/index.htm")
@WebServlet(urlPatterns = "/index.htm", name = "indexservlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/index.jsp";

	private final Persoon zaakvoerder = new Persoon();
	private final AtomicInteger aantalKeerBekeken = new AtomicInteger();
	// automatisch op 0 geïnitialiseerd

	private static final String INDEX_REQUESTS = "indexRequests";	
	
	/*
	 * public IndexServlet() { zaakvoerder.setVoornaam("Luigi");
	 * zaakvoerder.setFamilienaam("Peperone"); zaakvoerder.setAantalKinderen(7);
	 * zaakvoerder.setGehuwd(true); Adres adres = new Adres();
	 * adres.setStraat("Grote markt"); adres.setHuisNr("3");
	 * adres.setPostcode(9700); adres.setGemeente("Oudenaarde");
	 * zaakvoerder.setAdres(adres); }
	 */

	@Override
	public void init() throws ServletException {
		ServletContext context = this.getServletContext();
		
		zaakvoerder.setVoornaam(context.getInitParameter("voornaam"));
		zaakvoerder.setFamilienaam(context.getInitParameter("familienaam"));
		zaakvoerder.setAantalKinderen(Integer.parseInt(context
				.getInitParameter("aantalKinderen")));
		zaakvoerder.setGehuwd(Boolean.parseBoolean(context
				.getInitParameter("gehuwd")));
		
		context.setAttribute(INDEX_REQUESTS, new AtomicInteger());
		
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("aantalKeerBekeken",
				aantalKeerBekeken.incrementAndGet());

		int uur = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		request.setAttribute("begroeting",
				uur >= 6 && uur < 12 ? "Goede morgen"
						: uur >= 12 && uur < 18 ? "Goede middag"
								: "Goede avond");
		request.setAttribute("zaakvoerder", zaakvoerder);
		request.setAttribute("nu", Calendar.getInstance().getTime());
		request.setAttribute("aantalPizzasVerkocht", 23000);
		request.getRequestDispatcher(VIEW).forward(request, response);

		((AtomicInteger) this.getServletContext().getAttribute(INDEX_REQUESTS))
		.incrementAndGet();
		
		
	}
}
