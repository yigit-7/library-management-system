package me.seyrek.library_management_system.dashboard.controller;

import lombok.RequiredArgsConstructor;
import me.seyrek.library_management_system.common.ApiResponse;
import me.seyrek.library_management_system.dashboard.dto.DashboardOverviewDto;
import me.seyrek.library_management_system.dashboard.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/management/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    public ApiResponse<DashboardOverviewDto> getOverview() {
        return ApiResponse.success(dashboardService.getOverview());
    }
}
