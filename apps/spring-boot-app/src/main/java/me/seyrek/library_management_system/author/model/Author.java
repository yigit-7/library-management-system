package me.seyrek.library_management_system.author.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import me.seyrek.library_management_system.common.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @NotBlank(message = "Author name cannot be blank")
    private String name;

    @Column(name = "book_count")
    private Integer bookCount = 0;
}