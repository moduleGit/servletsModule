package be.vdab.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebFilter;

//@WebFilter("*.htm")
public class ServletFilter implements Filter {

	private String encoding;
	

	/**
	 * Default constructor.
	 */
	public ServletFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// request.setCharacterEncoding("UTF-8"); // (1)  nu niet meer in servlet
		request.setCharacterEncoding(encoding); // (1)  nu niet meer in servlet
		chain.doFilter(request, response); // (2) doorgeven aan servlet

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");  
		// doen we omdat we niet hard codereen in Servlet
		// parameter uit web.xml halen
	}

	
}
