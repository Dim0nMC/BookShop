package com.example.bookshop.controller;

import com.example.bookshop.dto.YearlyReportDTO;
import com.example.bookshop.service.ReportService;
import com.example.bookshop.util.PdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/admin/report")
public class ReportController {
    private final ReportService reportService;
    private final PdfReportService pdfReportService;

    @Autowired
    public ReportController(ReportService reportService, PdfReportService pdfReportService) {
        this.reportService = reportService;
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/yearly")
    public String yearlyReport(@RequestParam(defaultValue = "2025") int year, Model model) {
        YearlyReportDTO report = reportService.generateYearlyReport(year);
        model.addAttribute("report", report);
        model.addAttribute("activeSection", "reporting");
        return "admin-report-yearly";
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestParam int year) throws IOException {
        // Получаем данные отчёта (замените на реальный вызов сервиса получения отчёта)
        YearlyReportDTO yearlyReportDTO = reportService.generateYearlyReport(year);

        // Генерация PDF отчёта
        byte[] pdfBytes = pdfReportService.generateYearlyReportPdf(yearlyReportDTO);

        // Возвращаем PDF как файл
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"yearly_report_" + year + ".pdf\"")
                .body(pdfBytes);
    }
}
