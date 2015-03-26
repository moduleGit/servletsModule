package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.dao.PizzaDAO;
import be.vdab.entities.Pizza;

@WebServlet("/pizzas/toevoegen.htm")
public class PizzaToevoegenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/pizzatoevoegen.jsp";
	private static final String SUCCESS_VIEW = "/WEB-INF/JSP/pizzas.jsp";
	private final PizzaDAO pizzaDAO = new PizzaDAO();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException { // (1)
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException { // (2)
		Map<String, String> fouten = new HashMap<>();
		String naam = request.getParameter("naam");
		if (!Pizza.isNaamValid(naam)) {
			fouten.put("naam", "verplicht");
		}
		BigDecimal prijs = null;
		try {
			prijs = new BigDecimal(request.getParameter("prijs"));
			if (!Pizza.isPrijsValid(prijs)) {
				fouten.put("prijs", "tik een positief getal");
			}
		} catch (NumberFormatException ex) {
			fouten.put("prijs", "tik een getal");
		}
		if (fouten.isEmpty()) {
			boolean pikant = "pikant".equals(request.getParameter("pikant"));
			pizzaDAO.create(new Pizza(naam, prijs, pikant));
			request.setAttribute("pizzas", pizzaDAO.findAll()); 				// (3)
			request.getRequestDispatcher(SUCCESS_VIEW).forward(request,
					response);
		} else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}
}

//(1) De gebruiker doet een GET request naar de servlet om de lege HTML form te zien.
//(2) De gebruiker tikt in die form de gegevens van de nieuwe pizza en submit de form.
//De browser doet op dat moment een POST request naar de servlet.
//(3) De nieuwe pizza is toegevoegd aan de database. Je leest alle pizza�s uit de database
//(ook de nieuwe pizza) en toont die aan de gebruiker.