package edu.epam.fop.web;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

public class MyHttpSessionListener implements HttpSessionListener {

	private static final String ACTIVE_USERS_COUNTER_ATTRIBUTE = "activeUsersCounter";

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		AtomicInteger counter = (AtomicInteger) sessionEvent.getSession()
				.getServletContext().getAttribute(ACTIVE_USERS_COUNTER_ATTRIBUTE);
		counter.incrementAndGet();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		AtomicInteger counter = (AtomicInteger) sessionEvent.getSession()
				.getServletContext().getAttribute(ACTIVE_USERS_COUNTER_ATTRIBUTE);
		counter.decrementAndGet();
	}
}