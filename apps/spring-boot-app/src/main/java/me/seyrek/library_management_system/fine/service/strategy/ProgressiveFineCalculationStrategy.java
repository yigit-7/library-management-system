package me.seyrek.library_management_system.fine.service.strategy;

import me.seyrek.library_management_system.loan.model.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component("progressiveFineCalculationStrategy")
public class ProgressiveFineCalculationStrategy implements OverdueFineCalculationStrategy {

    private final BigDecimal baseAmountPerUnit;
    private final BigDecimal multiplier;
    private final BigDecimal bookPriceMultiplier;
    private final int progressiveTimeThreshold;
    private final ChronoUnit timeUnit;

    public ProgressiveFineCalculationStrategy(
            @Value("${application.library.fine.amount-per-time-unit}") BigDecimal baseAmountPerUnit,
            @Value("${application.library.fine.progressive-multiplier:2.0}") BigDecimal multiplier,
            @Value("${application.library.fine.book-price-multiplier:1.0}") BigDecimal bookPriceMultiplier,
            @Value("${application.library.fine.progressive-time-threshold:7}") int progressiveTimeThreshold,
            @Value("${application.library.time-unit:DAYS}") String timeUnitStr) {
        this.baseAmountPerUnit = baseAmountPerUnit;
        this.multiplier = multiplier;
        this.bookPriceMultiplier = bookPriceMultiplier;
        this.progressiveTimeThreshold = progressiveTimeThreshold;
        this.timeUnit = parseTimeUnit(timeUnitStr);
    }

    @Override
    public BigDecimal calculateFine(Loan loan) {
        // Eğer kitap iade edilmediyse (returnDate null), şu anki zamanı baz al
        Instant effectiveReturnDate = loan.getReturnDate() != null ? loan.getReturnDate() : Instant.now();
        
        long overdueUnits = timeUnit.between(loan.getDueDate(), effectiveReturnDate);
        
        if (overdueUnits <= 0) return BigDecimal.ZERO;

        BigDecimal calculatedFine;
        if (overdueUnits <= progressiveTimeThreshold) {
            calculatedFine = baseAmountPerUnit.multiply(BigDecimal.valueOf(overdueUnits));
        } else {
            BigDecimal firstPeriodFine = baseAmountPerUnit.multiply(BigDecimal.valueOf(progressiveTimeThreshold));
            BigDecimal lateUnits = BigDecimal.valueOf(overdueUnits - progressiveTimeThreshold);
            BigDecimal lateFine = baseAmountPerUnit.multiply(multiplier).multiply(lateUnits);
            calculatedFine = firstPeriodFine.add(lateFine);
        }

        BigDecimal maxFine = loan.getCopy().getBook().getPrice().multiply(bookPriceMultiplier);
        return calculatedFine.min(maxFine);
    }

    private ChronoUnit parseTimeUnit(String timeUnitStr) {
        try {
            return ChronoUnit.valueOf(timeUnitStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Default to DAYS if invalid config
            return ChronoUnit.DAYS;
        }
    }
}
