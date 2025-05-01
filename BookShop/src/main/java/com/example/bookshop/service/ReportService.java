package com.example.bookshop.service;

import com.example.bookshop.dto.YearlyReportDTO;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.OrderDetails;
import com.example.bookshop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.bookshop.dto.YearlyReportDTO.*;

@Service
public class ReportService {

    private final OrderRepository orderRepository;

    public ReportService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private static final Map<Month, String> RUSSIAN_MONTHS = Map.ofEntries(
            Map.entry(Month.JANUARY, "Январь"),
            Map.entry(Month.FEBRUARY, "Февраль"),
            Map.entry(Month.MARCH, "Март"),
            Map.entry(Month.APRIL, "Апрель"),
            Map.entry(Month.MAY, "Май"),
            Map.entry(Month.JUNE, "Июнь"),
            Map.entry(Month.JULY, "Июль"),
            Map.entry(Month.AUGUST, "Август"),
            Map.entry(Month.SEPTEMBER, "Сентябрь"),
            Map.entry(Month.OCTOBER, "Октябрь"),
            Map.entry(Month.NOVEMBER, "Ноябрь"),
            Map.entry(Month.DECEMBER, "Декабрь")
    );

    public YearlyReportDTO generateYearlyReport(int year) {
        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(order -> "Оплачен".equals(order.getStatus()))
                .filter(order -> order.getPayment_date() != null && order.getPayment_date().getYear() == year)
                .collect(Collectors.toList());

        Map<Month, Map<String, BookReportDTO>> monthlyBooks = new TreeMap<>();
        Map<Month, Double> monthlyIncome = new TreeMap<>();
        Map<String, BookReportDTO> yearlyBooksMap = new LinkedHashMap<>();


        Set<String> allTitles = new HashSet<>();
        int totalBookCount = 0;
        double totalBookRevenue = 0.0;
        double totalIncome = 0.0;

        for (Order order : orders) {
            Month month = order.getPayment_date().getMonth();
            double orderCost = order.getCost();

            monthlyIncome.merge(month, orderCost, Double::sum);
            totalIncome += orderCost;

            for (OrderDetails details : order.getOrderDetailsSet()) {
                String title = details.getBook().getName();
                double price = details.getPrice();

                allTitles.add(title);
                totalBookCount++;
                totalBookRevenue += price;

                Map<String, BookReportDTO> bookMap = monthlyBooks.computeIfAbsent(month, m -> new LinkedHashMap<>());
                BookReportDTO bookDTO = bookMap.computeIfAbsent(title, t -> new BookReportDTO(t, 0, price, 0.0));

                // Обновляем DTO
                bookDTO = new BookReportDTO(
                        bookDTO.getTitle(),
                        bookDTO.getCount() + 1,
                        price,
                        bookDTO.getTotalPrice() + price
                );
                bookMap.put(title, bookDTO);

                BookReportDTO yearDTO = yearlyBooksMap.computeIfAbsent(title, t -> new BookReportDTO(t, 0, price, 0.0));
                yearDTO = new BookReportDTO(
                        yearDTO.getTitle(),
                        yearDTO.getCount() + 1,
                        price,
                        yearDTO.getTotalPrice() + price
                );
                yearlyBooksMap.put(title, yearDTO);

            }
        }

        List<MonthlyReportDTO> monthlyReports = new ArrayList<>();
        for (Month month : Month.values()) {
            String monthName = RUSSIAN_MONTHS.get(month);
            List<BookReportDTO> books = new ArrayList<>(monthlyBooks.getOrDefault(month, Map.of()).values());
            double income = monthlyIncome.getOrDefault(month, 0.0);
            monthlyReports.add(new MonthlyReportDTO(month, monthName, income, books));
        }

        List<BookReportDTO> yearlyBooksSummary = new ArrayList<>(yearlyBooksMap.values());

        return new YearlyReportDTO(
                year,
                monthlyReports,
                allTitles.size(),
                totalBookCount,
                totalBookRevenue,
                totalIncome,
                yearlyBooksSummary
        );

    }
}
