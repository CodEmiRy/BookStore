package pl.sda.intermediate11.bookstore.categories;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import pl.sda.intermediate11.bookstore.database.HibernateConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.*;

public class CategoryDAO {

    private CategoryDAO() {
    }

    public void moveCategoryToAnotherParent(Integer newParentId, Integer movedId){

        EntityManager entityManager = HibernateConfiguration.getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Category newParent;
        if(newParentId == null){
            newParent = null;
        } else {
            newParent = entityManager.find(Category.class, newParentId);
        }
        Category movedCategory = entityManager.find(Category.class, movedId);

        movedCategory.setParent(newParent);
        entityTransaction.begin();
        entityManager.merge(movedCategory);
        entityTransaction.commit();

    }

    private static CategoryDAO instance;


    public static CategoryDAO getInstance() {
        if (instance == null) {
            synchronized (CategoryDAO.class) {
                if (instance == null) {
                    instance = new CategoryDAO();
                }
            }
        }
        return instance;

    }


    public Optional<Category> findCategoryById(Integer id) {

        EntityManager entityManager = HibernateConfiguration.getEntityManager();
        return Optional.ofNullable(entityManager.find(Category.class, id));

    }

    private void populateParentId(int currentDepth, Map<Integer, List<Category>> categoryMap) {
        List<Category> categories = categoryMap.get(currentDepth);
        if (categories == null) {
            return;
        }
        for (Category category : categories) {
            category.setParent(currentDepth == 0 ? null : matchParent(currentDepth, categoryMap, category));
        }
        populateParentId(currentDepth + 1, categoryMap);
    }

    private Category matchParent(int currentDepth, Map<Integer, List<Category>> categoryMap, Category category) {
        List<Category> potentialParentCategories = categoryMap.get(currentDepth - 1);
        Integer idOfChildWaitingForPapa = category.getId();
        return potentialParentCategories.stream()
                .filter(n -> n.getId() < idOfChildWaitingForPapa)
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .orElse(null);
    }

    private int getDepth(Category category) {
        return category.getTitle().split("\\S")[0].length();
    }

    public List<Category> getCategories() {
        EntityManager entityManager = HibernateConfiguration.getEntityManager();

        List<Category> categoryList = entityManager.createQuery("FROM Category", Category.class).getResultList();

        if (isNotEmpty(categoryList)) {
            return categoryList;
        }

        List<String> lines = readLinesFromFile();
        List<Category> categories = prepareCategoriesList(lines);

        Map<Integer, List<Category>> categoryMap = populateCategoriesMap(categories);
        populateParentId(0, categoryMap);
        categoryList = categoryMap.values().stream()
                .flatMap(n -> n.stream())
                .collect(Collectors.toList());

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        categoryList.forEach(n -> entityManager.persist(n));  // lub (entityManager::persist)
        entityTransaction.commit();
        return categoryList;
    }

    private List<Category> prepareCategoriesList(List<String> lines) {
        List<Category> categories = Lists.newArrayList();
        int counter = 1;
        for (String line : lines) {
            categories.add(new Category(counter++, line));
        }
        return categories;
    }

    private List<String> readLinesFromFile() {
        URI uri = null;
        try {
            uri = this.getClass().getClassLoader().getResource("kategorie2.txt")
                    .toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(uri), Charset.forName("UNICODE"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private Map<Integer, List<Category>> populateCategoriesMap(List<Category> categories) {
        Map<Integer, List<Category>> categoryMap = Maps.newHashMap();
        for (Category category : categories) {
            int depth = (category.getTitle().startsWith(" ") || category.getTitle().startsWith("\t"))
                    ? getDepth(category) : 0;
            if (categoryMap.containsKey(depth)) {
                categoryMap.get(depth).add(category);
            } else {
                categoryMap.put(depth, Lists.newArrayList(category));
            }
        }
        return categoryMap;
    }


}
