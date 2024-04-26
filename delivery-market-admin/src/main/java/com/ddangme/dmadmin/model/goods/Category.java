package com.ddangme.dmadmin.model.goods;

import com.ddangme.dmadmin.model.Admin;
import com.ddangme.dmadmin.model.AuditingFields;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE category SET deleted_at = NOW() WHERE category_id = ?")
public class Category extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @Setter
    private Long parentId;

    @ToString.Exclude
    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL)
    private Set<Category> childCategories = new LinkedHashSet<>();

    public Category(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public void delete(Admin admin) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = admin;
    }

}
