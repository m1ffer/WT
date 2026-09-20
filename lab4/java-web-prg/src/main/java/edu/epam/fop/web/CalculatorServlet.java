package edu.epam.fop.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Pattern PATTERN = Pattern.compile("^\\s*([+-]?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*([+-]?\\d+(?:\\.\\d+)?)\\s*$");

	private static String calculate(String expression) {
		String trimmed = expression.trim();
		Matcher matcher = PATTERN.matcher(trimmed);

		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid expression: \"" + expression + "\"");
		}

		try {
			double a = Double.parseDouble(matcher.group(1));
			char op = matcher.group(2).charAt(0);
			double b = Double.parseDouble(matcher.group(3));

			double result = switch (op) {
				case '+' -> a + b;
				case '-' -> a - b;
				case '*' -> a * b;
				case '/' -> {
					if (b == 0.0) {
						throw new IllegalArgumentException("Division by zero error!");
					}
					yield a / b;
				}
				default -> throw new IllegalArgumentException("Invalid expression: \"" + expression + "\"");
			};

			return trimmed + "=" + result;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid expression: \"" + expression + "\"");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("error", "Please use the POST method for calculations");
		response.sendRedirect("result.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if ("clearHistory".equals(request.getParameter("action"))) {
			session.removeAttribute("history");
			response.sendRedirect("result.jsp");
			return;
		}
		try {
			String res = calculate(request.getParameter("expression"));
			@SuppressWarnings("unchecked")
			List<String> history = (List<String>) session.getAttribute("history");
			if (history == null) {
				history = new ArrayList<>();
			}
			history.add(res);
			session.setAttribute("history", history);
			session.removeAttribute("error");
		} catch (IllegalArgumentException e) {
			session.setAttribute("error", e.getMessage());
		}
		response.sendRedirect("result.jsp");
	}
}