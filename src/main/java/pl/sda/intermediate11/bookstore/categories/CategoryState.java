package pl.sda.intermediate11.bookstore.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryState {
    private boolean open;
    private boolean selected;
    private boolean disabled;
}

