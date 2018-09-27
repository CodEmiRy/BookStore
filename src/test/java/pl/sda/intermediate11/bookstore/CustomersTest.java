package pl.sda.intermediate11.bookstore;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.sda.intermediate11.bookstore.users.entities.Customer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomersTest {
    Customer[] people = new Customer[]{
            new Customer("Anna", "Nowak   ", 33, "1200"),
            new Customer("Beata", "Kowalska", 22, "1200"),
            new Customer("Marek", " Nowak", 25, "1250"),
            new Customer("Adam", "Twardowski", 33, "1100"),
            new Customer("Monika  ", "Kos", 25, "2500"),
            new Customer("Adam ", "Rudy", 45, "3333"),
            new Customer("Marek", "Rudy", 15, 2210),
            new Customer("Adam", "Madej", 15, 3333)
    };

    @Test
    void shouldCreateStatisticsWithSalary() {
        Map<BigDecimal, Integer> resultMap = Maps.newHashMap();
        for (Customer customer : people) {
            if (resultMap.containsKey(customer.getSalary())) {
                int innerCounter = resultMap.get(customer.getSalary());
                resultMap.put(customer.getSalary(), innerCounter + 1);
            } else {
                resultMap.put(customer.getSalary(), 1);
            }
        }
        Assertions.assertEquals(2, resultMap.get(BigDecimal.valueOf(1200)).intValue());
    }

    @Test
    void shouldCreateStatisticsWithSalaryStream() {
        Map<BigDecimal, Long> resultMap = Arrays.stream(people)
                .collect(Collectors.groupingBy(c -> c.getSalary(), Collectors.counting()));

        Assertions.assertEquals(2, resultMap.get(BigDecimal.valueOf(1200)).intValue());

    }

    @Test
    void shouldCreateStatisticsMap() {
        Map<String, Map<BigDecimal, Integer>> resultMap = Maps.newHashMap();
        for (Customer customer : people) {
            String trimmedFirstName = customer.getFirstName().trim();
            if (resultMap.containsKey(trimmedFirstName)) {
                handleInnerMap(resultMap, customer, trimmedFirstName, 1);
            } else {
                Map<BigDecimal, Integer> innerMap = new HashMap<>();
                innerMap.put(customer.getSalary(), 1);
                resultMap.put(trimmedFirstName, innerMap);
            }
        }
        Assertions.assertEquals(2, resultMap.get("Adam").get(BigDecimal.valueOf(3333)).intValue());
    }

    private void handleInnerMap(Map<String, Map<BigDecimal, Integer>> resultMap, Customer customer, String trimmedFirstName, int i) {
        Map<BigDecimal, Integer> innerMap = resultMap.get(trimmedFirstName);
        if (innerMap.containsKey(customer.getSalary())) {
            Integer counter = innerMap.get(customer.getSalary());
            innerMap.put(customer.getSalary(), counter + i);
        } else {
            innerMap.put(customer.getSalary(), i);
        }
    }

    @Test
    void shouldCreateStatisticsMapStream() {
        Map<String, Map<BigDecimal, Long>> resultMap = Arrays.stream(people)
                .collect(Collectors
                        .groupingBy(c -> c.getFirstName().trim(), Collectors
                                .groupingBy(s -> s.getSalary(), Collectors.counting())));

        Assertions.assertEquals(2, resultMap.get("Adam").get(BigDecimal.valueOf(3333)).intValue());


        Map<String, List<Customer>> collect = Arrays.stream(people).collect(Collectors.groupingBy(e -> e.getSurname()));

    }
}
