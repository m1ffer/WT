package edu.epam.fop.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Сервлет для подсчёта количества обращений (посещений) к веб-странице.
 * Демонстрирует основные фазы жизненного цикла сервлета:
 * инициализацию ({@link #init()}), обработку запросов ({@link #service(HttpServletRequest, HttpServletResponse)})
 * и завершение работы ({@link #destroy()}).
 */
@WebServlet("/VisitCounter")
public class VisitCounterServlet extends HttpServlet {
	private static final long serialVersionUID = 7485830631456155452L;

	private static final String VISIT_COUNTER_ATTRIBUTE_NAME = "visitCount";
	private static final String COUNTER_PATH = "/WEB-INF/visitCount.txt";

	// Use this object for logging.
	System.Logger logger = System.getLogger(VisitCounterServlet.class.getName());

	// Use this object to save and restore a visit counter.
	CounterFileHelper fileHelper = new CounterFileHelper();

	// Use this object to place a visit counter in a servlet context as an
	// attribute.
	AtomicInteger visitCount = new AtomicInteger();

	private String counterRealPath;

	private ServletContext context;

	/**
	 * Инициализирует сервлет и настраивает счётчик посещений в контексте сервлета.
	 * Если атрибут счётчика уже присутствует в {@link ServletContext}, используется он.
	 * В противном случае счётчик создаётся, регистрируется в контексте, и его значение
	 * восстанавливается из файла на диске с помощью {@link CounterFileHelper#restoreCount(String)}.
	 * При отсутствии файла или ошибке чтения значение сбрасывается в 0.
	 *
	 * @throws ServletException если при инициализации сервлета возникла ошибка
	 */
	@Override
	public void init() throws ServletException {
		context = getServletContext();
		counterRealPath = context.getRealPath(COUNTER_PATH);
		if (context.getAttribute(VISIT_COUNTER_ATTRIBUTE_NAME) != null)
			visitCount = (AtomicInteger) context.getAttribute(VISIT_COUNTER_ATTRIBUTE_NAME);
		else {
			context.setAttribute(VISIT_COUNTER_ATTRIBUTE_NAME, visitCount);
			try {
				visitCount.set(fileHelper.restoreCount(counterRealPath));
			} catch (ServletException e) {
				visitCount.set(0);
			}
		}
		logger.log(System.Logger.Level.DEBUG, "Servlet was initialized");
	}

	/**
	 * Обрабатывает входящий HTTP-запрос.
	 * Инкрементирует потокобезопасный счётчик посещений, формирует HTML-ответ
	 * со значением счётчика и записывает отладочное сообщение в лог.
	 *
	 * @param request  объект {@link HttpServletRequest}, содержащий запрос клиента
	 * @param response объект {@link HttpServletResponse}, содержащий ответ сервлета
	 * @throws ServletException если при обработке запроса произошла сервлетная ошибка
	 * @throws IOException      если произошла ошибка ввода-вывода при записи ответа
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int n = visitCount.incrementAndGet();
		String html = "<!DOCTYPE html><html lang=\"en\"><head><title>Visit Counter</title></head><body><h2>Visit counter: " + n + "</h2></body></html>";
		response.setContentType("text/html");
		response.getWriter().print(html);
		logger.log(System.Logger.Level.DEBUG, "One more request! Total: " + n);
	}

	/**
	 * Завершает работу сервлета.
	 * Вызывается контейнером сервлетов перед выгрузкой сервлета из памяти.
	 * Сохраняет текущее значение счётчика посещений в файл на диске с помощью
	 * {@link CounterFileHelper#saveCount(String, int)} и логирует завершение работы.
	 */
	@Override
	public void destroy() {
        try {
			logger.log(System.Logger.Level.DEBUG, "Destroyed");
            fileHelper.saveCount(counterRealPath, visitCount.get());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
	}
}