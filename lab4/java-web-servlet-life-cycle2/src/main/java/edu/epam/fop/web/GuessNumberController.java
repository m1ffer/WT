package edu.epam.fop.web;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GuessNumber")
public class GuessNumberController extends HttpServlet {
	private static final long serialVersionUID = -1963426021633719325L;

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
	private static final String USER_NAME_ATTRIBUTE = "name";
	private static final String RESULT_ATTRIBUTE = "result";
	private static final String TRY_COUNT_ATTRIBUTE = "tryCount";
	private static final String MAX_NUMBER_OF_ATTEMPTS_ATTRIBUTE = "maxAttempts";

	private static final String MISSING_MESSAGE = "Please enter a number";
	private static final String LESS_MESSAGE = "Your number is less than the number being guessed";
	private static final String WIN_MESSAGE = "You win!";
	private static final String GREATER_MESSAGE = "Your number is greater than the number being guessed";
	private static final String INVALID_MESSAGE = "Invalid value entered";
	private static final String LOSE_MESSAGE = "You lose";

	enum Status {
		MISSING(MISSING_MESSAGE, GAME_ATTEMPT_PAGE),
		LESS(LESS_MESSAGE, GAME_ATTEMPT_PAGE),
		WIN(WIN_MESSAGE, GAME_END_PAGE),
		GREATER(GREATER_MESSAGE, GAME_ATTEMPT_PAGE),
		INVALID(INVALID_MESSAGE, GAME_ATTEMPT_PAGE),
		LOSE(LOSE_MESSAGE, GAME_END_PAGE);
		private final String message, page;
		Status(String message, String page){
			this.message = message;
			this.page = page;
		}
		String getMessage(){
			return message;
		}
		String getPage(){
			return page;
		}
	}

	// Use this object for logging.
	System.Logger logger = System.getLogger(GuessNumberController.class.getName());

	// Use this field to store user name.
	String name;

	// Use this field to store the lower limit of a random number range.
	int minNumber;

	// Use this field to store the upper limit of a random number range.
	int maxNumber;

	// Use this field to store a random (secret) number.
	int randomNumber;

	// Use this field as the counter of attempts.
	int tryCount;

	ServletContext context;

	@Override
	public void init() throws ServletException {
		context = getServletContext();
		if (context.getAttribute(MAX_NUMBER_OF_ATTEMPTS_ATTRIBUTE) == null)
			context.setAttribute(MAX_NUMBER_OF_ATTEMPTS_ATTRIBUTE, MAX_NUMBER_OF_ATTEMPTS);

		String minS = getInitParameter(MIN_NUMBER_PARAMETER);
		if (context.getAttribute(MIN_NUMBER_ATTRIBUTE) == null) {
			try {
				minNumber = minS == null ? 1 : Integer.parseInt(minS);
			} catch (NumberFormatException ignored) {
				minNumber = 1;
			}
			context.setAttribute(MIN_NUMBER_ATTRIBUTE, minNumber);
		}
		else
			minNumber = (Integer) context.getAttribute(MIN_NUMBER_ATTRIBUTE);

		String maxS = getInitParameter(MAX_NUMBER_PARAMETER);
		if (context.getAttribute(MAX_NUMBER_ATTRIBUTE) == null) {
			try {
				maxNumber = maxS == null ? 50 : Integer.parseInt(maxS);
			} catch (NumberFormatException ignored) {
				maxNumber = 50;
			}
			context.setAttribute(MAX_NUMBER_ATTRIBUTE, maxNumber);
		}
		else
			maxNumber = (Integer) context.getAttribute(MAX_NUMBER_ATTRIBUTE);
		logger.log(System.Logger.Level.DEBUG, "Init");
	}

	@Override
	public void destroy() {
		logger.log(System.Logger.Level.DEBUG, "Destroy");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getParameter(COMMAND_PARAMETER);
		if (command == null)
			command = "";
		String pathToForward = switch (command) {
            case START_COMMAND -> handleStart(request);
            case TRY_IT_COMMAND -> handleTry(request);
            default -> handleDefault();
        };
        request.getRequestDispatcher(pathToForward).forward(request, response);
		logger.log(System.Logger.Level.DEBUG,
				"minNumber: {0}, maxNumber: {1}, randomNumber: {2}, name: {3}, tryCount: {4}, command: {5}, page: {6}",
				minNumber, maxNumber, randomNumber, name, tryCount, command, pathToForward);
	}

	String handleDefault() {
		return HOME_PAGE;
	}

	String handleStart(HttpServletRequest request) {
		tryCount = 0;
		name = request.getParameter(USER_NAME_PARAMETER);
		RandomGenerator generator = RandomGenerator.getDefault();
		randomNumber = generator.nextInt(minNumber, maxNumber + 1);
		request.setAttribute(USER_NAME_ATTRIBUTE, name);
		return GAME_START_PAGE;
	}

	String handleTry(HttpServletRequest request) {
		tryCount++;
		String enteredString = request.getParameter(NUMBER_PARAMETER);
		Status status = handleEnteredNumber(enteredString);
		request.setAttribute(RESULT_ATTRIBUTE, status.getMessage());
		request.setAttribute(TRY_COUNT_ATTRIBUTE, tryCount);
		return status.getPage();
	}

	private Status handleEnteredNumber(String enteredString) {
		Status status;
		if (enteredString == null || enteredString.isBlank()) {
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