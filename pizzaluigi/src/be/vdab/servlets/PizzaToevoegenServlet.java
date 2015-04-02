package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import be.vdab.dao.PizzaDAO;
import be.vdab.entities.Pizza;

@WebServlet("/pizzas/toevoegen.htm")
@MultipartConfig
public class PizzaToevoegenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/pizzatoevoegen.jsp";
//	private static final String SUCCESS_VIEW = "/WEB-INF/JSP/pizzas.jsp";
	private static final String REDIRECT_URL = "%s/pizzas.htm";
	private final transient PizzaDAO pizzaDAO = new PizzaDAO();     ///transient dwz 
    //	Als Java de servlet via serialization wegschrijft, moet Java het DAO object niet meeschrijven

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException { // (1)
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException { // (2)
		
//		request.setCharacterEncoding("UTF-8");	// via filter
		
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
		/*if (fouten.isEmpty()) {
			boolean pikant = "pikant".equals(request.getParameter("pikant"));
			pizzaDAO.create(new Pizza(naam, prijs, pikant));
			request.setAttribute("pizzas", pizzaDAO.findAll()); 				// (3)
			request.getRequestDispatcher(SUCCESS_VIEW).forward(request,
					response);
		} else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}*/
		
		Part fotoPart = request.getPart("foto"); //(1)
		boolean fotoIsOpgeladen = fotoPart != null && fotoPart.getSize() != 0; //(2)
		if (fotoIsOpgeladen && ! fotoPart.getContentType().contains("jpeg")) {
		fouten.put("foto", "geen JPEG foto");
		}
		if (fouten.isEmpty()) {
		boolean pikant = "pikant".equals(request.getParameter("pikant"));
		Pizza pizza = new Pizza(naam, prijs, pikant);
		pizzaDAO.create(pizza);
		if (fotoIsOpgeladen) {
		String pizzaFotosPad =
		this.getServletContext().getRealPath("/pizzafotos"); //(3)
		fotoPart.write(String.format("%s/%d.jpg", pizzaFotosPad, pizza.getId())); //(4)
		}
		response.sendRedirect(String.format(REDIRECT_URL, request.getContextPath()));
		} else {
		request.setAttribute("fouten", fouten);
		request.getRequestDispatcher(VIEW).forward(request, response);
		}			
	}
	
	@Resource(name = PizzaDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		pizzaDAO.setDataSource(dataSource);
	}
}
