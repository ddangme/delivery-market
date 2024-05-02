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
    private Long parentId;

    @OrderBy("id DESC")
    @ToString.Exclude
    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL)
    private Set<Category> childCategories = new LinkedHashSet<>();

    public Category(Long id) {
        this.id = id;
    }

    public Category(Long id, String name, Long parentId, Set<Category> childCategories) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.childCategories = childCategories;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public void delete(Admin admin) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = admin;
    }

    public void addChildCategories(Collection<Category> childCategories) {
        this.childCategories.addAll(childCategories);
    }

    public void edit(String name) {
        this.name = name;
    }

}
