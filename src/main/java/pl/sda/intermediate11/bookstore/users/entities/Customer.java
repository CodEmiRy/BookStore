package pl.sda.intermediate11.bookstore.users.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class Customer {
    private Integer id;
    private String firstName;
    private String surname;
    private Integer age;
    private BigDecimal salary;
    private static AtomicInteger idCounter = new AtomicInteger(1);


    public Customer(String firstName, String surname, Integer age, Integer salary) {
        this.id = idCounter.getAndIncrement();
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
        this.salary = BigDecimal.valueOf(salary);
    }

    public Customer(String firstName, String surname, Integer age, String salary) {
        this(firstName, surname, age, BigDecimal.valueOf(Double.parseDouble(salary)).setScale(2, RoundingMode.HALF_UP).intValue());
    }
}
