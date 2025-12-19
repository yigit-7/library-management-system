package me.seyrek.library_management_system.author.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import me.seyrek.library_management_system.common.model.BaseEntity;

@Getter
@Setter
@Entity
public class Author extends BaseEntity {
    @NotBlank(message = "Author name cannot be blank")
    private String name;
}