package pl.sda.intermediate11.bookstore.categories;

import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategorySearchService {
    private CategoryDAO categoryDAO = CategoryDAO.getInstance();

    public void moveCategory(String newParent, String movedId) {
        Integer newParentId;
        if (newParent.equals("#")) {
           newParentId = null;
        } else {
            newParentId = Integer.valueOf(newParent);
        }
        categoryDAO.moveCategoryToAnotherParent(newParentId, Integer.valueOf(movedId));
    }


    public List<CategoryDTO> filterCategories(String searchedText) {
        return categoryDAO.getCategories().stream()
                //.filter(cat -> cat.getTitle().equalsIgnoreCase(searchedText.trim()))
                .map(c -> buildCategoryDTO(c))
                .peek(c -> c.setParentCat(findParent(c)))
                .peek(c -> setStateAndOpenParents(c, searchedText))
                .collect(Collectors.toList());

    }

    private void setStateAndOpenParents(CategoryDTO categoryDTO, String searchedText) {
        if (searchedText != null && categoryDTO.getText().equalsIgnoreCase(searchedText.trim())) {
            categoryDTO.getState().setSelected(true);
            categoryDTO.getState().setOpen(true);
            openParents(categoryDTO);
        }
    }

    private void openParents(CategoryDTO categoryDTO) {
        CategoryDTO parentCat = categoryDTO.getParentCat();
        if (parentCat == null) {
            return;
        }
        parentCat.getState().setOpen(true);
        openParents(parentCat);
    }

    private CategoryDTO findParent(CategoryDTO child) {
        if (child.getParentCategoryId() == null) {
            return null;
        }
        return categoryDAO.findCategoryById(Integer.valueOf(child.getParentCategoryId()))
                .map(c -> buildCategoryDTO(c))
                .orElse(null);
    }

    private CategoryDTO buildCategoryDTO(Category c) {
        return CategoryDTO.builder()
                .text(c.getTitle().trim())
                .id(c.getId().toString())
                .state(new CategoryState())
                //.parentCategoryId(c.getParentId() == null ? null : c.getParentId().toString())
                .parentCategoryId(Optional.ofNullable(c.getParentId()).map(e -> e.toString()).orElse(null))
                .build();
    }
}
