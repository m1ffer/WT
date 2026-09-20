package edu.epam.fop.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class CalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String RESULT_PAGE = "result.jsp";
	public static final String ERROR_PAGE = "error.jsp";
	public static final String ERROR_ATTRIBUTE = "error";
	public static final String HISTORY_ATTRIBUTE = "history";

	private Pattern pattern;

	@Override
	public void init() throws ServletException {
		String regex = getInitParameter("expression-regex");
		if (regex == null && getServletContext() != null) {
			regex = getServletContext().getInitParameter("expression-regex");
		}
		if (regex != null) {
			pattern = Pattern.compile(regex);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute(ERROR_ATTRIBUTE,
				"Please use the POST method for calculations");
		response.sendRedirect(ERROR_PAGE);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if ("clearHistory"
				.equals(request.getParameter("action"))) {
			session.removeAttribute(HISTORY_ATTRIBUTE);
			response.sendRedirect(RESULT_PAGE);
			return;
		}

		String expression = request.getParameter("expression");
		try {
			String res = doExpression(expression);
			session.removeAttribute(ERROR_ATTRIBUTE);
			@SuppressWarnings("unchecked")
			ArrayList<String> history =
					Objects.requireNonNullElse(
							(ArrayList<String>) session
									.getAttribute(HISTORY_ATTRIBUTE),
							new ArrayList<>());
			history.add(res);
			session.setAttribute(HISTORY_ATTRIBUTE, history);
			response.sendRedirect(RESULT_PAGE);
		}
		catch(IllegalArgumentException e){
			session.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
			response.sendRedirect(ERROR_PAGE);
		}
	}

	private String doExpression(String expression){
		if (expression == null) {
			throw new IllegalArgumentException("Invalid expression: \"null\"");
		}
		Matcher matcher = pattern.matcher(expression);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid expression: \"" + expression + "\"");
		}
		String rawOperand1 = matcher.group("operand1"),
				rawOperation = matcher.group("operation"),
				rawOperand2 = matcher.group("operand2");
		double op1 = Double.parseDouble(rawOperand1),
				op2 = Double.parseDouble(rawOperand2);
		double result = switch(rawOperation){
			case "+" -> op1 + op2;
			case "-" -> op1 - op2;
			case "*" -> op1 * op2;
			case "/" -> {
				if (op2 == 0)
					throw new IllegalArgumentException
							("Division by zero!");
				yield op1 / op2;
			}
			default -> throw new IllegalArgumentException("Invalid expression: \"" + expression + "\"");
		};
		return expression + "=" + result;
	}
}