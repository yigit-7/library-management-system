package me.seyrek.library_management_system.fine.service.strategy;

import me.seyrek.library_management_system.loan.model.Loan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component("standardFineCalculationStrategy")
public class StandardFineCalculationStrategy implements OverdueFineCalculationStrategy {

    private final BigDecimal amountPerUnit;
    private final BigDecimal bookPriceMultiplier;
    private final ChronoUnit timeUnit;

    public StandardFineCalculationStrategy(
            @Value("${application.library.fine.amount-per-time-unit}") BigDecimal amountPerUnit,
            @Value("${application.library.fine.book-price-multiplier:1.0}") BigDecimal bookPriceMultiplier,
            @Value("${application.library.time-unit:DAYS}") String timeUnitStr) {
        this.amountPerUnit = amountPerUnit;
        this.bookPriceMultiplier = bookPriceMultiplier;
        this.timeUnit = parseTimeUnit(timeUnitStr);
    }

    @Override
    public BigDecimal calculateFine(Loan loan) {
        // Eğer kitap iade edilmediyse (returnDate null), şu anki zamanı baz al (Scheduler desteği)
        Instant effectiveReturnDate = loan.getReturnDate() != null ? loan.getReturnDate() : Instant.now();
        
        long overdueUnits = timeUnit.between(loan.getDueDate(), effectiveReturnDate);
        
        // Eğer gecikme yoksa veya negatifse 0 dön
        if (overdueUnits <= 0) return BigDecimal.ZERO;

        BigDecimal calculatedFine = amountPerUnit.multiply(BigDecimal.valueOf(overdueUnits));
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
