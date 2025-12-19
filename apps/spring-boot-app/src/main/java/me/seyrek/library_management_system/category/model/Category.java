package me.seyrek.library_management_system.category.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import me.seyrek.library_management_system.common.model.BaseEntity;

@Getter
@Setter
@Entity
public class Category extends BaseEntity {
    @NotBlank(message = "Category name cannot be blank")
    private String name;
}