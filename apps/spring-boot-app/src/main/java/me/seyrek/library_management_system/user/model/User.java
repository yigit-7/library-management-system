package me.seyrek.library_management_system.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import me.seyrek.library_management_system.common.model.BaseEntity;
import org.hibernate.annotations.NaturalId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
public class User extends BaseEntity {
    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 8, max = 128)
    private String password;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 50)
    private String lastName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    @NotEmpty
    private Set<Role> roles = new HashSet<>();
}