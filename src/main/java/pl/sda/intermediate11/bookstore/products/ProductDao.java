package pl.sda.intermediate11.bookstore.products;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProductDao {

    List<Product> list = new ArrayList<>();

    public List<Product> getProductList(String searchText, int maxSize) {
        if (list.isEmpty()) {
            list.addAll(populateProductList());
        }
        if (StringUtils.isBlank(searchText)) {
            return list.stream()
                    .limit(maxSize)
                    .collect(Collectors.toList());
        }
        return list.stream()
                .filter(n -> n.getTitle().contains(searchText))
                .limit(maxSize)
                .collect(Collectors.toList());
    }

    private List<Product> populateProductList() {
        Pattern pattern = Pattern.compile("^([\\w]+.+[\\w\\.\\?!])(\\W+)([0-9]+)$");
        List<Product> products = Lists.newArrayList();

        try {

            List<String> lines = Files.readAllLines(Paths.get(this
                    .getClass().getClassLoader().getResource("GUTINDEX.ALL").toURI()));
            boolean readingProductsActive = false;
            boolean readAdditionalDescription = false;
            Product product = null;
            for (String line : lines.stream().map(l -> l.trim()).collect(Collectors.toList())) {
                if (line.startsWith("TITLE and AUTHOR")) {
                    readingProductsActive = true;
                }
                if (line.endsWith("<==End of GUTINDEX.ALL")) {
                    readingProductsActive = false;
                }
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches() && readAdditionalDescription) {
                    readAdditionalDescription = false;
                }
                if (readingProductsActive && matcher.matches()) {
                    String title = matcher.group(1);
                    String id = matcher.group(3);
                    product = new Product(title, Integer.parseInt(id));
                    products.add(product);
                    readAdditionalDescription = true;
                }
                if (readAdditionalDescription && readingProductsActive && !matcher.matches() && StringUtils.isNotBlank(line)) {
                    if (StringUtils.isBlank(product.getDescription())) {
                        product.setDescription(line);
                    } else {
                        product.setDescription(product.getDescription() + line);
                    }
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return products;
    }
}
