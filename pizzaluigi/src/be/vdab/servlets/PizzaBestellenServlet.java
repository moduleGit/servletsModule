package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.dao.PizzaDAO;
import be.vdab.entities.Pizza;

@WebServlet("/pizzas/bestellen.htm")
public class PizzaBestellenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/pizzabestellen.jsp";
	private static final String MANDJE = "mandje";
	private final PizzaDAO pizzaDAO = new PizzaDAO();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException { // (1)
		request.setAttribute("allePizzas", pizzaDAO.findAll()); // (2)
		HttpSession session = request.getSession(false); // (3)
		if (session != null) {
			@SuppressWarnings("unchecked")
			// (4)
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE); // (5)
			if (mandje != null) { // (6)
				List<Pizza> pizzasInMandje = new ArrayList<>();
				for (long id : mandje) { // (7)
					pizzasInMandje.add(pizzaDAO.read(id));
				}
				request.setAttribute("pizzasInMandje", pizzasInMandje); // (8)
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameterValues("id") != null) {
			HttpSession session = request.getSession(); // (9)
			@SuppressWarnings("unchecked")
			Set<Long> pizzaIdsInMandje = (Set<Long>) session
					.getAttribute(MANDJE); // (10)
			if (pizzaIdsInMandje == null) { // (11)
				pizzaIdsInMandje = new LinkedHashSet<>(); // (12)
			}
			for (String id : request.getParameterValues("id")) {
				pizzaIdsInMandje.add(Long.parseLong(id)); // (13)
			}
			session.setAttribute(MANDJE, pizzaIdsInMandje);// (14)
		}
		response.sendRedirect(response.encodeRedirectURL(request
				.getRequestURI()));
	}
}