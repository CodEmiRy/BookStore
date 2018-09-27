package pl.sda.intermediate11.bookstore;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class StringCalculator {

    public static int adding(String exampleData) {
        if (StringUtils.isBlank(exampleData)) {
            return 0;
        }
        return calculate(exampleData);
    }

    private static int calculate(String exampleData) {
        if (StringUtils.startsWith(exampleData.trim(), "//")) {
            char delimiter = exampleData.trim().charAt(2);
            String[] splitted = exampleData.split("\n");
            String[] numbersInString = splitted[1].split(String.valueOf(delimiter));
            return sumNumbers(numbersInString);
        } else {
            return sumNumbers(exampleData.split("[,\n]"));
        }
    }

    private static Integer sumNumbers(String[] split) {
        return Arrays.stream(split)
                .filter(w -> StringUtils.isNotBlank(w))
                .map(w -> w.trim())
                .map(w -> Integer.valueOf(w))
                .reduce((a, b) -> a + b)
                .orElse(0);
    }
}
