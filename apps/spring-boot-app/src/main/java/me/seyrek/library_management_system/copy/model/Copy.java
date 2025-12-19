package me.seyrek.library_management_system.copy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.seyrek.library_management_system.book.model.Book;
import me.seyrek.library_management_system.common.model.BaseEntity;
import org.hibernate.annotations.NaturalId;

@Getter
@Setter
@Entity
public class Copy extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull
    private Book book;

    @NotBlank(message = "Barcode cannot be blank")
    @NaturalId(mutable = true)
    @Column(unique = true, nullable = false)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private CopyStatus status = CopyStatus.AVAILABLE;

    @Version
    @Column(name = "version", nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private Long version;
}