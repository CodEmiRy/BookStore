package pl.sda.intermediate11.bookstore;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsTest {
    private List<String> animals = Arrays.asList("cat", "dog", "mouse", "rat", "pig", "rabbit"
            , "hamster", "parrot", "cat", "dog", "cat", null);

    @Test
    void streamFiltering() {

        Predicate<String> stringPredicate = e -> e.length() < 4;
        Function<String, String> stringStringFunction = e -> e + "!";
        String animalsResult = animals.stream()
                .filter(e -> StringUtils.isNotBlank(e))
                .filter(stringPredicate)
                .distinct()
                .map(stringStringFunction)
                .collect(Collectors.joining(", "));
        System.out.println(animalsResult);
    }

    @Test
    void reduce() {
        Integer s = animals.stream()
                .filter(e -> Objects.nonNull(e))
                .distinct()
                .map(e -> e.length())
                .reduce((a, b) -> a + b)
                .get();
        System.out.println(s);

        Integer sum = Stream.iterate(1, e -> e + 1)
                .limit(9)
                //.filter(e -> e % 2 == 0)
                .reduce((a, b) -> a + b)
                .get()
                -
                Stream.iterate(1, e -> e + 1)
                .limit(7)
                .reduce((a,b) -> a + b)
                .get();
        System.out.println(sum);
    }
}
