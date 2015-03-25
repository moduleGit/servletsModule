package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
//import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.dao.PizzaDAO;
import be.vdab.entities.Pizza;

//test
@WebServlet("/pizzas.htm")
public class PizzasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/pizzas.jsp";
	private final PizzaDAO pizzaDAO = new PizzaDAO();
	private static final String PIZZAS_REQUESTS = "pizzasRequests";
	
	@Override
	public void init() throws ServletException {
	this.getServletContext().setAttribute(PIZZAS_REQUESTS, new AtomicInteger());
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * request.setAttribute("pizzas", Arrays.asList( new Pizza(12,
		 * "Prosciutto", BigDecimal.valueOf(4), true), new Pizza(14,
		 * "Margherita", BigDecimal.valueOf(5), false), new Pizza(17, "Calzone",
		 * BigDecimal.valueOf(4), false))); /// een list declareren
		 */

		Map<Long, Pizza> pizzas = new LinkedHashMap<>(); // keys zijn
															// pizzanummers
		pizzas.put(12L,
				new Pizza(12, "Prosciutto", BigDecimal.valueOf(4), true));
		pizzas.put(14L, new Pizza(14, "Margehrita", BigDecimal.valueOf(5),
				false));
		pizzas.put(17L, new Pizza(17, "Calzone", BigDecimal.valueOf(4), false));
		pizzas.put(23L, new Pizza(23, "Fungi & Olive", BigDecimal.valueOf(5),
				false));

		request.setAttribute("pizzas", pizzas);
		request.getRequestDispatcher(VIEW).forward(request, response);
		
		((AtomicInteger) this.getServletContext().getAttribute(PIZZAS_REQUESTS)).incrementAndGet();
				request.setAttribute("pizzas", pizzaDAO.findAll());
				request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
