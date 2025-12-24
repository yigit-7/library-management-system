package me.seyrek.library_management_system.fine.service.strategy;

import lombok.extern.slf4j.Slf4j;
import me.seyrek.library_management_system.fine.repository.FineRepository;
import me.seyrek.library_management_system.loan.model.Loan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Primary
@Component
public class DynamicFineCalculationStrategy implements OverdueFineCalculationStrategy {

    private final FineRepository fineRepository;
    private final OverdueFineCalculationStrategy standardStrategy;
    private final OverdueFineCalculationStrategy progressiveStrategy;
    private final int userBadHistoryThreshold;

    public DynamicFineCalculationStrategy(
            FineRepository fineRepository,
            @Qualifier("standardFineCalculationStrategy") OverdueFineCalculationStrategy standardStrategy,
            @Qualifier("progressiveFineCalculationStrategy") OverdueFineCalculationStrategy progressiveStrategy,
            @Value("${application.library.fine.user-bad-history-threshold:5}") int userBadHistoryThreshold) {
        this.fineRepository = fineRepository;
        this.standardStrategy = standardStrategy;
        this.progressiveStrategy = progressiveStrategy;
        this.userBadHistoryThreshold = userBadHistoryThreshold;
    }

    @Override
    public BigDecimal calculateFine(Loan loan) {
        // Kullanıcının daha önceden cezası var mı, varsa kaç tane
        long previousFines = fineRepository.countByUserId(loan.getUser().getId());
        log.info("{} user has {} fines so far.", loan.getUser().getEmail(), previousFines);

        // Eğer tolare edilemeyecek kadar cezası varsa Progressive, yoksa Standard strateji çalışsın
        if (previousFines > userBadHistoryThreshold) {
            log.info("{} user is a very bad person.", loan.getUser().getEmail());
            return progressiveStrategy.calculateFine(loan);
        } else {
            log.info("{} user is new, tolareted.", loan.getUser().getEmail());
            return standardStrategy.calculateFine(loan);
        }
    }
}
