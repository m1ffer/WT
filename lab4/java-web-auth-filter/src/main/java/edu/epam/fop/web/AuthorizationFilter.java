package edu.epam.fop.web;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizationFilter implements Filter {

	public static final String COMMAND_PARAMETER_NAME = "command";
	public static final String USER_ROLE_ATTRIBUTE_NAME = "role";

	public static final String COMMAND_LOGIN = "login";
	public static final String COMMAND_LOGOUT = "logout";

	Map<String, Set<String>> roleCommands;

	@Override
	public void init(FilterConfig config) throws ServletException {
		roleCommands = new HashMap<>();
		Enumeration<String> paramNames = config.getInitParameterNames();
			while (paramNames.hasMoreElements()) {
				String role = paramNames.nextElement();
				String commandsStr = config.getInitParameter(role);
				Set<String> commands = Arrays.stream(commandsStr.trim().split("\\s+"))
						.filter(cmd -> !cmd.isBlank())
						.collect(Collectors.toSet());
				roleCommands.put(role, commands);
			}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String command = httpRequest.getParameter(COMMAND_PARAMETER_NAME);

		HttpSession session = httpRequest.getSession();
		String role = (String) session.getAttribute(USER_ROLE_ATTRIBUTE_NAME);
		boolean isLoggedIn = role != null;

		if (COMMAND_LOGIN.equalsIgnoreCase(command)) {
			if (isLoggedIn) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			chain.doFilter(request, response);
			return;
		}

		if (COMMAND_LOGOUT.equalsIgnoreCase(command)) {
			if (!isLoggedIn) {
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			chain.doFilter(request, response);
			return;
		}

		if (!isLoggedIn) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		Set<String> allowedCommands = roleCommands.get(role);

		if (allowedCommands != null && allowedCommands.contains(command))
			chain.doFilter(request, response);
		else
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}