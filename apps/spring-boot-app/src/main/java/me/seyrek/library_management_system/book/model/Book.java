package me.seyrek.library_management_system.book.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import me.seyrek.library_management_system.author.model.Author;
import me.seyrek.library_management_system.category.model.Category;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    @NotBlank(message = "ISBN cannot be blank")
    @ISBN(message = "ISBN should be valid")
    private String isbn;

    @NotBlank(message = "Title cannot be blank")
    @Column(nullable = false)
    private String title;

    @Size(max = 5000, message = "Description can be at most 5000 characters")
    private String description;

    @URL(message = "Cover image URL should be valid")
    private String coverImageUrl;

    @NotEmpty(message = "Authors cannot be empty")
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    @NotNull(message = "Category cannot be null")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @PrePersist
    @PreUpdate
    private void normalizeIsbn() {
        if (this.isbn != null) {
            this.isbn = this.isbn.replaceAll("[^0-9X]", "");
        }
    }
}