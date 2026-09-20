package edu.epam.fop.web;

import java.io.IOException;
import java.util.Random;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GuessNumberCookieController extends HttpServlet {
	private static final long serialVersionUID = -7299290054922386973L;

	private static final int MAX_NUMBER_OF_ATTEMPTS = 10;

	private static final String HOME_PAGE = "/index.html";
	private static final String GAME_START_PAGE = "/WEB-INF/start.jsp";
	private static final String GAME_ATTEMPT_PAGE = "/WEB-INF/tryIt.jsp";
	private static final String GAME_END_PAGE = "/WEB-INF/finish.jsp";

	private static final String START_COMMAND = "start";
	private static final String TRY_IT_COMMAND = "tryIt";

	private static final String MIN_NUMBER_PARAMETER = "minNumber";
	private static final String MAX_NUMBER_PARAMETER = "maxNumber";
	private static final String COMMAND_PARAMETER = "command";
	private static final String NUMBER_PARAMETER = "number";
	private static final String USER_NAME_PARAMETER = "name";

	private static final String MIN_NUMBER_ATTRIBUTE = "minNumber";
	private static final String MAX_NUMBER_ATTRIBUTE = "maxNumber";
	private static final String RESULT_ATTRIBUTE = "result";
	private static final String TRY_COUNT_ATTRIBUTE = "tryCount";
	private static final String MAX_NUMBER_OF_ATTEMPTS_ATTRIBUTE = "maxAttempts";
	private static final String RANDOM_NUMBER_ATTRIBUTE = "randomNumber";

	private static final String USER_NAME_COOKIE = "name";
	private static final String RANDOM_NUMBER_COOKIE = "randomNumber";
	private static final String TRY_COUNT_COOKIE = "tryCount";

	private static final String MISSING_MESSAGE = "Please enter a number";
	private static final String LESS_MESSAGE = "Your number is less than the number being guessed";
	private static final String WIN_MESSAGE = "You win!";
	private static final String GREATER_MESSAGE = "Your number is greater than the number being guessed";
	private static final String INVALID_MESSAGE = "Invalid value entered";
	private static final String LOSE_MESSAGE = "You lose";

	enum Status {
		MISSING, LESS, WIN, GREATER, INVALID, LOSE
	}

	System.Logger logger = System.getLogger(GuessNumberCookieController.class.getName());

	int minNumber;
	int maxNumber;

	private void saveCookies(HttpServletRequest request, HttpServletResponse response) {
		Object attr = request.getAttribute(USER_NAME_PARAMETER);
		if (attr != null) {
			response.addCookie(new Cookie(USER_NAME_COOKIE, attr.toString()));
		}
		attr = request.getAttribute(TRY_COUNT_ATTRIBUTE);
		if (attr != null) {
			response.addCookie(new Cookie(TRY_COUNT_COOKIE, attr.toString()));
		}
		attr = request.getAttribute(RANDOM_NUMBER_ATTRIBUTE);
		if (attr != null) {
			response.addCookie(new Cookie(RANDOM_NUMBER_COOKIE, attr.toString()));
		}
	}

	@Override
	public void init() throws ServletException {
		ServletContext context = getServletContext();
		context.setAttribute(MAX_NUMBER_OF_ATTEMPTS_ATTRIBUTE, MAX_NUMBER_OF_ATTEMPTS);

		String minParam = getInitParameter(MIN_NUMBER_PARAMETER);
		String maxParam = getInitParameter(MAX_NUMBER_PARAMETER);

		this.minNumber = (minParam != null && !minParam.isBlank()) ? Integer.parseInt(minParam) : 1;
		this.maxNumber = (maxParam != null && !maxParam.isBlank()) ? Integer.parseInt(maxParam) : 50;

		context.setAttribute(MIN_NUMBER_ATTRIBUTE, this.minNumber);
		context.setAttribute(MAX_NUMBER_ATTRIBUTE, this.maxNumber);

		logger.log(System.Logger.Level.DEBUG, "Initialized with minNumber: {0}, maxNumber: {1}", this.minNumber, this.maxNumber);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = request.getParameter(COMMAND_PARAMETER);
		if (command == null) {
			command = "";
		}
		String pathToForward = switch (command) {
            case START_COMMAND -> handleStart(request, response);
            case TRY_IT_COMMAND -> handleTry(request, response);
            default -> handleDefault();
        };
        request.getRequestDispatcher(pathToForward).forward(request, response);
		logger.log(System.Logger.Level.INFO, "minNumber: {0}, maxNumber: {1}, command: {2}, page: {3}", minNumber,
				maxNumber, command, pathToForward);
	}

	String handleDefault() {
		return HOME_PAGE;
	}

	String handleStart(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter(USER_NAME_PARAMETER);
		int generatedNumber = minNumber + new Random().nextInt(maxNumber - minNumber + 1);
		request.setAttribute(USER_NAME_PARAMETER, name);
		request.setAttribute(RANDOM_NUMBER_ATTRIBUTE, generatedNumber);
		request.setAttribute(TRY_COUNT_ATTRIBUTE, 0);
		saveCookies(request, response);
		return GAME_START_PAGE;
	}

	String handleTry(HttpServletRequest request, HttpServletResponse response) {
		String enteredString = request.getParameter(NUMBER_PARAMETER);
		int randomNumber = 0;
		int tryCount = 0;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (var i : cookies) {
				if (i.getName().equals(USER_NAME_COOKIE))
					request.setAttribute(USER_NAME_PARAMETER, i.getValue());
				if (i.getName().equals(RANDOM_NUMBER_COOKIE)) {
					request.setAttribute(RANDOM_NUMBER_ATTRIBUTE, i.getValue());
					randomNumber = Integer.parseInt(i.getValue());
				}
				if (i.getName().equals(TRY_COUNT_COOKIE))
					tryCount = Integer.parseInt(i.getValue());
			}
		}
		Status status = checkGameStatus(enteredString, randomNumber, tryCount);
        String page = switch (status) {
            case MISSING -> {
                request.setAttribute(RESULT_ATTRIBUTE, MISSING_MESSAGE);
                yield GAME_ATTEMPT_PAGE;
            }
            case INVALID -> {
                request.setAttribute(RESULT_ATTRIBUTE, INVALID_MESSAGE);
                yield GAME_ATTEMPT_PAGE;
            }
            case LESS -> {
                request.setAttribute(RESULT_ATTRIBUTE, LESS_MESSAGE);
                yield GAME_ATTEMPT_PAGE;
            }
            case GREATER -> {
                request.setAttribute(RESULT_ATTRIBUTE, GREATER_MESSAGE);
                yield GAME_ATTEMPT_PAGE;
            }
            case WIN -> {
                request.setAttribute(RESULT_ATTRIBUTE, WIN_MESSAGE);
                yield GAME_END_PAGE;
            }
            case LOSE -> {
                request.setAttribute(RESULT_ATTRIBUTE, LOSE_MESSAGE);
                yield GAME_END_PAGE;
            }
        };
		tryCount++;
		request.setAttribute(TRY_COUNT_ATTRIBUTE, tryCount);
		saveCookies(request, response);
		return page;
	}

	private Status checkGameStatus(String enteredString, int randomNumber, int tryCount) {
		Status status;

		if (enteredString == null || enteredString.trim().isEmpty()) {
			status = Status.MISSING;
		} else {
			try {
				int enteredNumber = Integer.parseInt(enteredString);
				status = (enteredNumber < randomNumber) ? Status.LESS
						: ((enteredNumber == randomNumber) ? Status.WIN : Status.GREATER);
			} catch (NumberFormatException e) {
				status = Status.INVALID;
			}
		}
		if ((tryCount > MAX_NUMBER_OF_ATTEMPTS) || (tryCount == MAX_NUMBER_OF_ATTEMPTS && status != Status.WIN)) {
			status = Status.LOSE;
		}
		return status;
	}
}