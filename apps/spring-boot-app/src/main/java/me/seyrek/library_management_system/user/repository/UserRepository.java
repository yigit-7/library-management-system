package me.seyrek.library_management_system.user.repository;

import me.seyrek.library_management_system.user.model.User;
import me.seyrek.library_management_system.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // TODO: add UserSpecification
    Optional<User> findByEmail(String email);
    
    long countByCreatedAtAfter(Instant date);
    long countByStatus(UserStatus status);
}
