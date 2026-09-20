package edu.epam.fop.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import jakarta.servlet.ServletException;

/**
 * Вспомогательный класс для сохранения и восстановления значения счётчика посещений в файловой системе.
 */
public class CounterFileHelper {

	/**
	 * Считывает сохранённое значение счётчика из указанного файла.
	 * Если файл существует и содержит непустую строку, преобразует её в целое число.
	 * Если файл отсутствует, возвращает 0.
	 *
	 * @param fileName абсолютный или относительный путь к файлу со счётчиком
	 * @return считанное значение счётчика или 0, если файл не найден
	 * @throws ServletException если произошла ошибка ввода-вывода или ошибка парсинга числа
	 */
	public int restoreCount(String fileName) throws ServletException {
		int counter = 0;

		File file = new File(fileName);
		if (file.exists()) {
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String countString = reader.readLine();
				if (countString != null && !countString.isEmpty()) {
					counter = Integer.parseInt(countString);
				}
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		return counter;
	}

	/**
	 * Сохраняет переданное значение счётчика в указанный файл.
	 * Если файл не существует, он будет создан; существующий файл перезаписывается.
	 *
	 * @param fileName абсолютный или относительный путь к файлу для сохранения
	 * @param counter  текущее значение счётчика посещений
	 * @throws ServletException если произошла ошибка ввода-вывода при записи в файл
	 */
	public void saveCount(String fileName, int counter) throws ServletException {
		File file = new File(fileName);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(String.valueOf(counter));
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}