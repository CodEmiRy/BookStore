package pl.sda.intermediate11.bookstore.categories;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "categories")
public class Category implements Comparable<Category>{

    @Id
    @Column(name = "category_id")
    private Integer id;
    private String title;

    @JoinColumn(name = "parent_id")
    @ManyToOne
    private Category parent;

    public Integer getParentId (){
        return parent == null ? null : parent.getId();
    }


    public Category(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Category() {
    }

    @Override
    public int compareTo(Category o) {
        return id.compareTo(o.getId());
    }
}
