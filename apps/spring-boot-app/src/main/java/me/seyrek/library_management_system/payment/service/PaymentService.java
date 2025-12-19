package me.seyrek.library_management_system.payment.service;

import java.math.BigDecimal;

public interface PaymentService {
    String processPayment(BigDecimal amount, String paymentToken);
}