package me.seyrek.library_management_system.fine.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.seyrek.library_management_system.common.model.BaseEntity;
import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.user.model.User;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Fine extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private FineStatus status = FineStatus.UNPAID;

    @Column(nullable = false)
    private Instant fineDate;

    private Instant paymentDate;

    @Version
    @Column(name = "version", nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private Long version;
}
