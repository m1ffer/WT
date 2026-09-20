package edu.epam.fop.web;

import java.io.BufferedWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Calculation makeCalculation(HttpServletRequest request){
		double a, b;
		a = Double.parseDouble(request.getParameter("num1"));
		b = Double.parseDouble(request.getParameter("num2"));
		String op = request.getParameter("operation");
		return switch(op){
			case "add" -> new Calculation(a, b, op, a + b);
			case "subtract" -> new Calculation(a, b, op, a - b);
			case "multiply" -> new Calculation(a, b, op, a * b);
			case "divide" -> new Calculation(a, b, op, a / b);
			default -> throw new IllegalArgumentException();
		};
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Calculation calc = makeCalculation(request);
			request.setAttribute("calculation", calc);
			request.getRequestDispatcher("result.jsp")
					.forward(request, response);
		}
		catch(NumberFormatException ignored){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Invalid number");
		}
		catch(IllegalArgumentException ignored){
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Unknown operation");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}