package me.seyrek.library_management_system.auth.repository;

import me.seyrek.library_management_system.auth.model.RefreshToken;
import me.seyrek.library_management_system.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);
    void deleteByUser(User user);

    /**
     * @Modifying anotasyonu, Spring Data JPA'ya şu mesajı verir:
     * "Bu bir veri okuma (SELECT) işlemi değil, veriyi değiştirme
     * (INSERT, UPDATE, DELETE) işlemidir."
     */
    @Modifying
    void deleteByExpiryDateBefore(Instant expiryDate);
}
