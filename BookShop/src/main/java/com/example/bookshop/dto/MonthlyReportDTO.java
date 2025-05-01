package com.example.bookshop.dto;

import java.time.Month;
import java.util.List;

public class MonthlyReportDTO {
    private Month month;
    private String monthName;
    private double totalIncome;
    private List<YearlyReportDTO.BookReportDTO> books;

    public MonthlyReportDTO(Month month, String monthName, double totalIncome, List<YearlyReportDTO.BookReportDTO> books) {
        this.month = month;
        this.monthName = monthName;
        this.totalIncome = totalIncome;
        this.books = books;
    }

    public String getMonthName() {
        return monthName;
    }
    // геттеры и сеттеры
}
