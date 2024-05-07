package com.ddangme.dmadmin.model.goods;

import com.ddangme.dmadmin.model.Admin;
import com.ddangme.dmadmin.model.AuditingFields;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "DELETED_AT IS NULL")
public class Category extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OrderBy("id DESC")
    @ToString.Exclude
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Category> childCategories = new LinkedHashSet<>();

    public Category(String name) {
        this.name = name;
    }

    public void delete(Admin admin) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = admin;
    }

    public void addChildCategories(Collection<Category> childCategories) {
        this.childCategories.addAll(childCategories);
        for (Category childCategory : childCategories) {
            childCategory.setParent(this);
        }
    }

    public void edit(String name) {
        this.name = name;
    }

}
