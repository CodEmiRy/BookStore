package pl.sda.intermediate11.bookstore.products;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ProductDaoTest {

    @Test
    void shouldPopulateProductsList() {
        Product product = new Product("Liika viisas, by Maiju Lassila", 16365);
        product.setDescription("[Subtitle: Viisaudenkirja eli kertomus Sakari Kolistajasta]" +
                "[Language: Finnish]");

        ProductDao productDao = new ProductDao();
        List<Product> productList = productDao.getProductList("", Integer.MAX_VALUE);

        Product productToMatch = productList.stream().filter(p -> p.getId() == 16365)
                .findFirst().orElseThrow(() -> new RuntimeException("Nie znaleziono produktu"));

        Assertions.assertEquals(productToMatch.getDescription(),product.getDescription());
        Assertions.assertEquals(productToMatch.getTitle(),product.getTitle());
    }
}