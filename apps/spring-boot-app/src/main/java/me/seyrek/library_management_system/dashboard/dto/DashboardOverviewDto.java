package me.seyrek.library_management_system.dashboard.dto;

import java.math.BigDecimal;

public record DashboardOverviewDto(
    // Temel İstatistikler
    long totalBooks,
    long activeLoans,
    long overdueLoans,
    long totalUsers,

    // Operasyonel Veriler
    long dueSoonLoans, // Önümüzdeki 3 gün
    long lostBooks,
    long damagedBooks,
    long availableCopies,
    long totalCopies,

    // Kullanıcı Verileri
    long newUsersLast30Days,
    long activeUsers,
    long inactiveUsers,

    // Finansal Veriler
    BigDecimal unpaidFinesAmount,
    BigDecimal collectedFinesThisMonth
) {}
