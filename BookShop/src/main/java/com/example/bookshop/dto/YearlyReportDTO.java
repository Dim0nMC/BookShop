package com.example.bookshop.dto;

import java.time.Month;
import java.util.List;

public class YearlyReportDTO {
    private int year;
    private List<MonthlyReportDTO> months;

    private int totalUniqueTitles;
    private int totalBookCount;
    private double totalBookRevenue;
    private double totalIncome;
    private List<BookReportDTO> yearlyBooksSummary;


    public YearlyReportDTO(int year,
                           List<MonthlyReportDTO> months,
                           int totalUniqueTitles,
                           int totalBookCount,
                           double totalBookRevenue,
                           double totalIncome,
                           List<BookReportDTO> yearlyBooksSummary) {
        this.year = year;
        this.months = months;
        this.totalUniqueTitles = totalUniqueTitles;
        this.totalBookCount = totalBookCount;
        this.totalBookRevenue = totalBookRevenue;
        this.totalIncome = totalIncome;
        this.yearlyBooksSummary = yearlyBooksSummary;
    }

    public List<BookReportDTO> getYearlyBooksSummary() {
        return yearlyBooksSummary;
    }

    public int getYear() {
        return year;
    }

    public List<MonthlyReportDTO> getMonths() {
        return months;
    }

    public int getTotalUniqueTitles() {
        return totalUniqueTitles;
    }

    public int getTotalBookCount() {
        return totalBookCount;
    }

    public double getTotalBookRevenue() {
        return totalBookRevenue;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public static class MonthlyReportDTO {
        private Month month;
        private String monthName;
        private double totalIncome;
        private List<BookReportDTO> books;

        public MonthlyReportDTO(Month month, String monthName, double totalIncome, List<BookReportDTO> books) {
            this.month = month;
            this.monthName = monthName;
            this.totalIncome = totalIncome;
            this.books = books;
        }

        public Month getMonth() {
            return month;
        }
        public String getMonthName() {
            return monthName;
        }

        public double getTotalIncome() {
            return totalIncome;
        }

        public List<BookReportDTO> getBooks() {
            return books;
        }
    }

    public static class BookReportDTO {
        private String title;
        private int count;
        private double unitPrice;
        private double totalPrice;

        public BookReportDTO(String title, int count, double unitPrice, double totalPrice) {
            this.title = title;
            this.count = count;
            this.unitPrice = unitPrice;
            this.totalPrice = totalPrice;
        }

        public String getTitle() {
            return title;
        }

        public int getCount() {
            return count;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public double getTotalPrice() {
            return totalPrice;
        }
    }
}
