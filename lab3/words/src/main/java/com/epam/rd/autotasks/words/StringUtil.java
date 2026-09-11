package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.StringJoiner;

public class StringUtil {

    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (words == null || words.length == 0 || sample == null) {
            return 0;
        }
        String trimmedSample = sample.trim();
        return (int) Arrays.stream(words)
                .filter(w -> w.trim().equalsIgnoreCase(trimmedSample))
                .count();
    }

    public static String[] splitWords(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        String[] res = Arrays.stream(text.split("[,.;: ?!]+"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        return res.length == 0 ? null : res;
    }

    public static String convertPath(String path, boolean toWin) {
        if (path == null || path.isEmpty()) {
            return null;
        }

        // Проверка недопустимых комбинаций
        boolean hasSlash = path.contains("/");
        boolean hasBackslash = path.contains("\\");

        if (hasSlash && hasBackslash) {
            return null;
        }

        int tildeCount = (int) path.chars().filter(ch -> ch == '~').count();
        if (tildeCount > 1 || (tildeCount == 1 && !path.startsWith("~"))) {
            return null;
        }

        int cCount = 0;
        int idx = 0;
        while ((idx = path.indexOf("C:", idx)) != -1) {
            cCount++;
            idx += 2;
        }
        if (cCount > 1 || (cCount == 1 && !path.startsWith("C:"))) {
            return null;
        }

        if (hasBackslash && path.contains("~")) {
            return null;
        }
        if (hasSlash && path.contains("C:")) {
            return null;
        }

        boolean isUnix = hasSlash || path.startsWith("~");
        boolean isWin = hasBackslash || path.startsWith("C:");

        // Если путь относительный и без разделителей (например, "file.txt")
        if (!isUnix && !isWin) {
            return path;
        }

        // Если уже в нужном формате
        if (toWin && isWin) {
            return path;
        }
        if (!toWin && isUnix) {
            return path;
        }

        if (toWin) {
            String winPath = path;
            if (winPath.startsWith("~")) {
                winPath = "C:\\User" + winPath.substring(1);
            } else if (winPath.startsWith("/")) {
                winPath = "C:\\" + winPath.substring(1);
            }
            return winPath.replace('/', '\\');
        } else {
            String unixPath = path;
            if (unixPath.startsWith("C:\\User")) {
                unixPath = "~" + unixPath.substring("C:\\User".length());
            } else if (unixPath.startsWith("C:\\")) {
                unixPath = "/" + unixPath.substring("C:\\".length());
            }
            return unixPath.replace('\\', '/');
        }
    }

    public static String joinWords(String[] words) {
        if (words == null || words.length == 0) {
            return null;
        }

        StringJoiner sj = new StringJoiner(", ", "[", "]");
        boolean hasNonEmpty = false;

        for (String word : words) {
            if (!word.isEmpty()) {
                sj.add(word);
                hasNonEmpty = true;
            }
        }

        return hasNonEmpty ? sj.toString() : null;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS"};
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult); // 2

        System.out.println("\nTest 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult)); // [first, second, third]

        System.out.println("\nTest 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult); // C:\some\\unix\path

        System.out.println("\nTest 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult); // [go, with, the, FLOW]
    }
}