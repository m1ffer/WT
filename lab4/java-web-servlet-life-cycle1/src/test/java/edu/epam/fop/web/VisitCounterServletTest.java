package edu.epam.fop.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Набор модульных тестов для проверки жизненного цикла и логики работы {@link VisitCounterServlet}.
 */
@DisplayNameGeneration(ReplaceCamelCase.class)
public class VisitCounterServletTest {

	private static final String VISIT_COUNTER_ATTRIBUTE_NAME = "visitCount";

	@Mock
	private ServletConfig servletConfigMock;

	@Mock
	private ServletContext servletContextMock;

	@Mock
	private HttpServletRequest httpServletRequestMock;

	@Mock
	private HttpServletResponse httpServletResponseMock;

	@Mock
	private PrintWriter printWriterMock;

	@Mock
	private AtomicInteger visitCounterMock;

	@Mock
	System.Logger loggerMock;

	@Mock
	private CounterFileHelper fileHelperMock;

	private VisitCounterServlet visitCounterServlet;

	private final static int INITIAL_VISIT_COUNT = ThreadLocalRandom.current().nextInt(10, 99);

	/**
	 * Настройка тестового окружения перед каждым тестом:
	 * инициализация мок-объектов и внедрение их в экземпляр тестируемого сервлета.
	 *
	 * @throws Exception если произошла ошибка при инициализации
	 */
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		when(servletContextMock.getRealPath(anyString())).thenReturn("");
		when(servletContextMock.getAttribute(VISIT_COUNTER_ATTRIBUTE_NAME)).thenReturn(visitCounterMock);
		when(servletConfigMock.getServletContext()).thenReturn(servletContextMock);
		when(fileHelperMock.restoreCount(anyString())).thenReturn(INITIAL_VISIT_COUNT);

		visitCounterServlet = new VisitCounterServlet();
		visitCounterServlet.logger = loggerMock;
		visitCounterServlet.fileHelper = fileHelperMock;
	}

	/**
	 * Проверяет наличие аннотации {@link WebServlet} на классе сервлета
	 * и корректность URL-паттерна ("/VisitCounter").
	 */
	@Test
	void testAnnotation() {
		WebServlet annotation = VisitCounterServlet.class.getAnnotation(WebServlet.class);
		assertEquals("/VisitCounter", annotation.value()[0]);
	}

	/**
	 * Проверяет метод {@link VisitCounterServlet#init()}:
	 * случай, когда счётчик ещё не был сохранён в контексте сервлета.
	 * Ожидается чтение значения из файла, регистрация счетчика в контексте и вызов логгера.
	 *
	 * @throws Exception при возникновении исключения во время инициализации
	 */
	@Test
	void testInitWhenAttributeDoesNotExist() throws Exception {
		when(servletContextMock.getAttribute(VISIT_COUNTER_ATTRIBUTE_NAME)).thenReturn(null);

		visitCounterServlet.init(servletConfigMock);
		verify(fileHelperMock).restoreCount(anyString());
		ArgumentCaptor<AtomicInteger> visitCountCaptor = ArgumentCaptor.forClass(AtomicInteger.class);
		verify(servletContextMock).setAttribute(eq(VISIT_COUNTER_ATTRIBUTE_NAME), visitCountCaptor.capture());

		assertEquals(visitCounterServlet.visitCount, visitCountCaptor.getValue());
		assertEquals(INITIAL_VISIT_COUNT, visitCounterServlet.visitCount.get());

		boolean logIsCalled = Mockito.mockingDetails(loggerMock).getInvocations().stream()
				.anyMatch(invocation -> invocation.getMethod().getName().equals("log"));

		assertTrue(logIsCalled, "None of the log methods were called");
	}

	/**
	 * Проверяет метод {@link VisitCounterServlet#init()}:
	 * случай, когда счётчик уже существует в контексте сервлета.
	 * Ожидается, что чтение из файла и повторная установка атрибута вызваны не будут.
	 *
	 * @throws Exception при возникновении исключения во время инициализации
	 */
	@Test
	void testInitWhenAttributeExists() throws Exception {
		visitCounterServlet.init(servletConfigMock);
		verify(fileHelperMock, never()).restoreCount(anyString());
		verify(servletContextMock, never()).setAttribute(eq(VISIT_COUNTER_ATTRIBUTE_NAME), any());

		boolean logIsCalled = Mockito.mockingDetails(loggerMock).getInvocations().stream()
				.anyMatch(invocation -> invocation.getMethod().getName().equals("log"));

		assertTrue(logIsCalled, "None of the log methods were called");
	}

	/**
	 * Проверяет метод {@link VisitCounterServlet#service(HttpServletRequest, HttpServletResponse)}:
	 * счётчик должен инкрементироваться на 1, content type должен быть установлен в "text/html",
	 * в ответ должен быть выведен HTML-текст, а также должно выполниться логирование.
	 *
	 * @throws Exception при возникновении исключения во время обработки запроса
	 */
	@Test
	void testService() throws Exception {
		when(httpServletResponseMock.getWriter()).thenReturn(printWriterMock);

		visitCounterServlet.init(servletConfigMock);
		visitCounterServlet.visitCount = new AtomicInteger(INITIAL_VISIT_COUNT);
		visitCounterServlet.service(httpServletRequestMock, httpServletResponseMock);

		assertEquals(INITIAL_VISIT_COUNT + 1, visitCounterServlet.visitCount.get());
		
		verify(httpServletResponseMock).setContentType("text/html");

		boolean result = Mockito.mockingDetails(printWriterMock).getInvocations().stream()
				.anyMatch(invocation -> invocation.getMethod().getName().equals("print")
						|| invocation.getMethod().getName().equals("println")
						|| invocation.getMethod().getName().equals("write")
						|| invocation.getMethod().getName().equals("append")
						|| invocation.getMethod().getName().equals("format")
						|| invocation.getMethod().getName().equals("printf"));

		assertTrue(result, "None of the print, println, write, append, format, printf methods were called");

		boolean logIsCalled = Mockito.mockingDetails(loggerMock).getInvocations().stream()
				.anyMatch(invocation -> invocation.getMethod().getName().equals("log"));

		assertTrue(logIsCalled, "None of the log methods were called");
	}

	/**
	 * Проверяет метод {@link VisitCounterServlet#destroy()}:
	 * сервлет должен сохранить значение счётчика в файл через {@link CounterFileHelper#saveCount(String, int)}
	 * и вызвать логгер.
	 *
	 * @throws ServletException при возникновении исключения
	 */
	@Test
	void testDestroy() throws ServletException {
		visitCounterServlet.init(servletConfigMock);
		visitCounterServlet.destroy();

		verify(fileHelperMock).saveCount(anyString(), anyInt());

		boolean logIsCalled = Mockito.mockingDetails(loggerMock).getInvocations().stream()
				.anyMatch(invocation -> invocation.getMethod().getName().equals("log"));

		assertTrue(logIsCalled, "None of the log methods were called");
	}
}