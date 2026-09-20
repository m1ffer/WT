package edu.epam.fop.web;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

/**
 * Генератор отображаемых имен (Display Names) для тестов JUnit 5.
 * Преобразует имена классов и методов из CamelCase в читаемые строки с пробелами.
 */
public class ReplaceCamelCase extends DisplayNameGenerator.Standard {

	/**
	 * Генерирует читаемое имя для тестового класса.
	 *
	 * @param testClass целевой тестовый класс
	 * @return преобразованное имя класса
	 */
	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {
		return replaceCamelCase(super.generateDisplayNameForClass(testClass));
	}

	/**
	 * Генерирует читаемое имя для вложенного тестового класса.
	 *
	 * @param nestedClass целевой вложенный класс
	 * @return преобразованное имя вложенного класса
	 */
	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		return replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
	}

	/**
	 * Генерирует читаемое имя для тестового метода.
	 *
	 * @param testClass  класс, содержащий тестовый метод
	 * @param testMethod тестовый метод
	 * @return преобразованное имя метода
	 */
	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		return this.replaceCamelCase(testMethod.getName());
	}

	/**
	 * Вспомогательный метод для разбиения строки в формате CamelCase на слова, разделённые пробелами.
	 * Первая буква строки переводится в верхний регистр, а остальные заглавные буквы — в нижний с добавлением пробела.
	 *
	 * @param camelCase исходная строка в CamelCase
	 * @return преобразованная читаемая строка
	 */
	private String replaceCamelCase(String camelCase) {
		StringBuilder result = new StringBuilder();
		result.append(Character.toUpperCase(camelCase.charAt(0)));
		for (int i = 1, len = camelCase.length(); i < len; i++) {
			if (Character.isUpperCase(camelCase.charAt(i))) {
				result.append(' ');
				result.append(Character.toLowerCase(camelCase.charAt(i)));
			} else {
				result.append(camelCase.charAt(i));
			}
		}
		return result.toString();
	}
}