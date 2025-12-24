package me.seyrek.library_management_system.loan.service;

import lombok.extern.slf4j.Slf4j;
import me.seyrek.library_management_system.loan.model.Loan;
import me.seyrek.library_management_system.loan.model.LoanStatus;
import me.seyrek.library_management_system.loan.repository.LoanRepository;
import me.seyrek.library_management_system.notification.model.NotificationCategory;
import me.seyrek.library_management_system.notification.model.NotificationRequest;
import me.seyrek.library_management_system.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
public class LoanScheduler {

    private final LoanRepository loanRepository;
    private final NotificationService notificationService;
    private final int dueSoonAmount;
    private final ChronoUnit timeUnit;

    public LoanScheduler(
            LoanRepository loanRepository,
            NotificationService notificationService,
            @Value("${application.library.loan.due-soon-amount:3}") int dueSoonAmount,
            @Value("${application.library.time-unit:DAYS}") String timeUnitStr) {
        this.loanRepository = loanRepository;
        this.notificationService = notificationService;
        this.dueSoonAmount = dueSoonAmount;
        this.timeUnit = parseTimeUnit(timeUnitStr);
    }

    /**
     * Runs daily to check for overdue loans and loans due soon.
     * Updates status to OVERDUE if applicable and sends notifications.
     */
    @Scheduled(cron = "${application.library.loan.scheduler.cron}")
    @Transactional
    public void checkLoanStatuses() {
        log.info("Starting scheduled loan status check...");
        checkOverdueLoans();
        checkDueSoonLoans();
        log.info("Scheduled loan status check completed.");
    }

    private void checkOverdueLoans() {
        Instant now = Instant.now();
        // Find active loans that are past their due date
        List<Loan> overdueLoans = loanRepository.findAllByStatusAndDueDateBefore(LoanStatus.ACTIVE, now);

        for (Loan loan : overdueLoans) {
            log.info("Loan ID {} is overdue. Updating status and sending notification.", loan.getId());
            
            loan.setStatus(LoanStatus.OVERDUE);
            loanRepository.save(loan);

            String userEmail = loan.getUser().getEmail();
            String subject = "Overdue Loan Notification";
            String body = String.format("Dear %s,\n\nThe book '%s' was due on %s. Please return it as soon as possible to avoid further fines.",
                    loan.getUser().getName(),
                    loan.getCopy().getBook().getTitle(),
                    loan.getDueDate());

            NotificationRequest request = NotificationRequest.builder()
                    .userId(loan.getUser().getId())
                    .recipient(userEmail)
                    .subject(subject)
                    .message(body)
                    .category(NotificationCategory.LOAN_OVERDUE)
                    .variable("name", loan.getUser().getName())
                    .variable("bookTitle", loan.getCopy().getBook().getTitle())
                    .variable("dueDate", loan.getDueDate())
                    .variable("message", body)
                    .build();

            notificationService.send(request);
        }
    }

    private void checkDueSoonLoans() {
        Instant now = Instant.now();
        Instant dueSoonThreshold = now.plus(dueSoonAmount, timeUnit);

        // Find active loans that are due within the next X time units
        List<Loan> dueSoonLoans = loanRepository.findAllByStatusAndDueDateBetween(LoanStatus.ACTIVE, now, dueSoonThreshold);

        for (Loan loan : dueSoonLoans) {
            log.info("Loan ID {} is due soon. Sending notification.", loan.getId());

            String userEmail = loan.getUser().getEmail();
            String subject = "Loan Due Soon Notification";
            String body = String.format("Dear %s,\n\nThe book '%s' is due on %s. Please return it on time.",
                    loan.getUser().getName(),
                    loan.getCopy().getBook().getTitle(),
                    loan.getDueDate());

            NotificationRequest request = NotificationRequest.builder()
                    .userId(loan.getUser().getId())
                    .recipient(userEmail)
                    .subject(subject)
                    .message(body)
                    .category(NotificationCategory.LOAN_DUE_SOON)
                    .variable("name", loan.getUser().getName())
                    .variable("bookTitle", loan.getCopy().getBook().getTitle())
                    .variable("dueDate", loan.getDueDate())
                    .variable("message", body)
                    .build();

            notificationService.send(request);
        }
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
