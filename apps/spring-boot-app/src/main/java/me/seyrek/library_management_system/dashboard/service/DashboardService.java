package me.seyrek.library_management_system.dashboard.service;

import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.book.repository.BookRepository;
import me.seyrek.library_management_system.copy.model.CopyStatus;
import me.seyrek.library_management_system.copy.repository.CopyRepository;
import me.seyrek.library_management_system.dashboard.dto.DashboardOverviewDto;
import me.seyrek.library_management_system.fine.model.FineStatus;
import me.seyrek.library_management_system.fine.repository.FineRepository;
import me.seyrek.library_management_system.loan.model.LoanStatus;
import me.seyrek.library_management_system.loan.repository.LoanRepository;
import me.seyrek.library_management_system.user.model.UserStatus;
import me.seyrek.library_management_system.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final CopyRepository copyRepository;
    private final FineRepository fineRepository;

    @Transactional(readOnly = true)
    public DashboardOverviewDto getOverview() {
        Instant threeDaysLater = Instant.now().plus(3, ChronoUnit.DAYS);
        Instant thirtyDaysAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        
        // Calculate start of current month
        Instant startOfMonth = ZonedDateTime.now(ZoneId.systemDefault())
                .withDayOfMonth(1)
                .truncatedTo(ChronoUnit.DAYS)
                .toInstant();

        return new DashboardOverviewDto(
                // Temel İstatistikler
                bookRepository.count(),
                loanRepository.countByStatus(LoanStatus.ACTIVE),
                loanRepository.countOverdueLoans(),
                userRepository.count(),

                // Operasyonel Veriler
                loanRepository.countDueSoonLoans(threeDaysLater),
                copyRepository.countByStatus(CopyStatus.LOST), // Changed to CopyStatus
                copyRepository.countByStatus(CopyStatus.MAINTENANCE), // Changed to CopyStatus (Damaged)
                copyRepository.countByStatus(CopyStatus.AVAILABLE),
                copyRepository.countByStatus(CopyStatus.LOANED), // New
                copyRepository.countByStatus(CopyStatus.MAINTENANCE), // New (Same as damaged for now, or separate logic)
                copyRepository.countByStatus(CopyStatus.RETIRED), // New
                copyRepository.count(),

                // Kullanıcı Verileri
                userRepository.countByCreatedAtAfter(thirtyDaysAgo),
                userRepository.countByStatus(UserStatus.ACTIVE),
                userRepository.countByStatus(UserStatus.INACTIVE),

                // Finansal Veriler
                fineRepository.sumAmountByStatus(FineStatus.UNPAID),
                fineRepository.sumAmountByStatusAndPaymentDateAfter(FineStatus.PAID, startOfMonth)
        );
    }
}
