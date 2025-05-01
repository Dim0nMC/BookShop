package com.example.bookshop.controller;

import com.example.bookshop.dto.YearlyReportDTO;
import com.example.bookshop.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/report")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/yearly")
    public String yearlyReport(@RequestParam(defaultValue = "2025") int year, Model model) {
        YearlyReportDTO report = reportService.generateYearlyReport(year);
        model.addAttribute("report", report);
        model.addAttribute("activeSection", "reporting");
        return "admin-report-yearly";
    }
}
