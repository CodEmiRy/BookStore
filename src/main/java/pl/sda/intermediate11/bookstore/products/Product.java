package pl.sda.intermediate11.bookstore.products;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String title;
    private Integer id;
    private String description;

    public Product(String title, Integer id) {
        this.title = title;
        this.id = id;
    }
}
