package me.seyrek.library_management_system.loan.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.seyrek.library_management_system.common.model.BaseEntity;
import me.seyrek.library_management_system.copy.model.Copy;
import me.seyrek.library_management_system.fine.model.Fine;
import me.seyrek.library_management_system.user.model.User;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(indexes = {
        @Index(name = "idx_loan_user", columnList = "user_id"),
        @Index(name = "idx_loan_copy", columnList = "copy_id"),
        @Index(name = "idx_loan_active", columnList = "return_date")
})
public class Loan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copy_id", nullable = false)
    private Copy copy;

    @Column(nullable = false)
    private Instant loanDate;

    @Column(nullable = false)
    private Instant dueDate;

    @Column(name = "return_date")
    private Instant returnDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Fine> fines;

    @Version
    @Column(name = "version", nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private Long version;
}
