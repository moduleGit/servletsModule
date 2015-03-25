package be.vdab.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.vdab.entities.Saus;

/**
 * Servlet implementation class SauzenServlet
 */
@WebServlet("/sauzen")
public class SauzenServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/sauzen.jsp";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Saus> sauzen = Arrays.asList(
				new Saus(3L, "cocktail", Arrays.asList("mayonaise", "ketchup",
						"cognac")),
				new Saus(6L, "mayonaise", Arrays.asList("ei", "mosterd")),
				new Saus(7L, "mosterd", Arrays.asList("mosterd", "azijn",
						"witte wijn")),
				new Saus(12L, "tartare", Arrays.asList("mayonaise", "augurk",
						"tabasco")),
				new Saus(44L, "vinaigrette", Arrays.asList("olijfolie",
						"mosterd", "azijn")));
		request.setAttribute("sauzen", sauzen);
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
