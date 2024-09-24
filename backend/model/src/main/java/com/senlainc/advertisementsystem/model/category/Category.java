package com.senlainc.advertisementsystem.model.category;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@NamedEntityGraph(
        name = "category_entity_graph",
        attributeNodes = {
                @NamedAttributeNode("parentCategory"),
        }
)
public class Category extends AbstractModel {

    @EqualsAndHashCode.Include
    private String name;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @ToString.Exclude
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Advertisement> advertisements = new ArrayList<>();
}
