package edu.epam.fop.web;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ValidationFilter implements Filter {

	private Pattern pattern;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String regex = filterConfig.getInitParameter("expression-regex");
		if (regex == null && filterConfig.getServletContext() != null) {
			regex = filterConfig.getServletContext().getInitParameter("expression-regex");
		}
		if (regex != null) {
			pattern = Pattern.compile(regex);
		}
	}

	@Override
	public void doFilter(ServletRequest rawRequest, ServletResponse rawResponse, FilterChain chain)
			throws IOException, ServletException {
		if (rawRequest instanceof HttpServletRequest request && rawResponse instanceof HttpServletResponse response) {
			String action = request.getParameter("action");
			if ("clearHistory".equals(action)) {
				chain.doFilter(rawRequest, rawResponse);
				return;
			}

			String expression = request.getParameter("expression");
			if (expression != null && pattern != null) {
				Matcher matcher = pattern.matcher(expression);
				if (!matcher.matches()) {
					HttpSession session = request.getSession();
					session.setAttribute(CalculatorServlet.ERROR_ATTRIBUTE,
							"Invalid expression: \"" + expression + "\"");
					response.sendRedirect(CalculatorServlet.ERROR_PAGE);
					return;
				}
			}
		}

		chain.doFilter(rawRequest, rawResponse);
	}
}