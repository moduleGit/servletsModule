package be.vdab.listeners;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener													// (1)
public class MandjeListener implements ServletContextListener,
		HttpSessionAttributeListener { 							// (2)
	private static final String MANDJE = "mandje";
	private static final String AANTAL_MANDJES = "aantalMandjes";

	@Override
	public void contextInitialized(ServletContextEvent event) { // (3)
		event.getServletContext().setAttribute(AANTAL_MANDJES,
				new AtomicInteger());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) { // (4)
		if (MANDJE.equals(event.getName())) { // (5)
			((AtomicInteger) event.getSession().getServletContext()
					.getAttribute(AANTAL_MANDJES)).getAndIncrement(); // (6)
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) { // (7)
		if (MANDJE.equals(event.getName())) {
			((AtomicInteger) event.getSession().getServletContext()
					.getAttribute(AANTAL_MANDJES)).getAndDecrement(); // (8)
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
	}
}