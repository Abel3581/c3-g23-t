package com.estore.ecomerce.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Category SET soft_delete = true WHERE id=?")
@Where(clause = "soft_deleted = false")
@Entity
public class Category extends Base {

    @NotEmpty(message = "Nombre Obligatorio")
    @Column(name = "name", nullable = false, updatable = true, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    private List<Product> products;

    @Column(name = "soft_deleted")
    private boolean softDeleted = Boolean.FALSE;


    public boolean isEnabled() {
        return !this.softDeleted;
    }
}
