package com.example.bookshop.util;

import com.example.bookshop.dto.YearlyReportDTO;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



@Service
public class PdfReportService {

    public byte[] generateYearlyReportPdf(YearlyReportDTO yearlyReportDTO) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);

        PdfFont font = PdfFontFactory.createFont("Bookshop/src/main/resources/fonts/arial.ttf", PdfEncodings.IDENTITY_H, true);
        document.setFont(font);

        // Заголовок
        Paragraph title = new Paragraph("Годовой отчет за " + yearlyReportDTO.getYear() + " год")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Суммарные данные
        document.add(new Paragraph("Общая выручка: " + yearlyReportDTO.getTotalIncome() + " руб."));
        document.add(new Paragraph("Количество уникальных книг: " + yearlyReportDTO.getTotalUniqueTitles()));
        document.add(new Paragraph("Количество проданных книг: " + yearlyReportDTO.getTotalBookCount()));
        //document.add(new Paragraph("Общая выручка по книгам: " + yearlyReportDTO.getTotalBookRevenue() + " руб."));

        document.add(new Paragraph("\nДанные по месяцам:").setBold().setFontSize(14));

        // Таблица по месяцам
        Table monthTable = new Table(UnitValue.createPercentArray(new float[]{2, 4, 2, 2}));
        monthTable.setWidth(UnitValue.createPercentValue(100));
        monthTable.addHeaderCell("Месяц");
        monthTable.addHeaderCell("Название книги");
        monthTable.addHeaderCell("Количество");
        monthTable.addHeaderCell("Итого");

        for (YearlyReportDTO.MonthlyReportDTO monthReportDTO : yearlyReportDTO.getMonths()) {
            boolean firstBook = true;
            monthTable.addCell(new Cell().add(new Paragraph(monthReportDTO.getMonthName())));
            monthTable.addCell("");
            monthTable.addCell("");
            monthTable.addCell("");
            for (YearlyReportDTO.BookReportDTO bookReportDTO : monthReportDTO.getBooks()) {
                if (firstBook) {
                    //monthTable.addCell(new Cell(bookReportDTO.getCount(), 1).add(new Paragraph(String.valueOf(monthReportDTO.getTotalIncome()))));
                    firstBook = false;
                }
                monthTable.addCell("");
                monthTable.addCell(bookReportDTO.getTitle());
                monthTable.addCell(String.valueOf(bookReportDTO.getCount()));
                monthTable.addCell(String.valueOf(bookReportDTO.getTotalPrice()));
            }
            monthTable.addCell("ИТОГО");
            monthTable.addCell("");
            monthTable.addCell("");
            monthTable.addCell(String.valueOf(monthReportDTO.getTotalIncome()));
            monthTable.addCell("\u00A0");
            monthTable.addCell("\u00A0");
            monthTable.addCell("\u00A0");
            monthTable.addCell("\u00A0");
        }

        document.add(monthTable);

        document.add(new Paragraph("\nИтоги по книгам за год:").setBold().setFontSize(14));

        // Таблица с итогами за год
        Table summaryTable = new Table(UnitValue.createPercentArray(new float[]{4, 2, 2, 2}));
        summaryTable.setWidth(UnitValue.createPercentValue(100));
        summaryTable.addHeaderCell("Название книги");
        summaryTable.addHeaderCell("Количество");
        summaryTable.addHeaderCell("Цена за единицу");
        summaryTable.addHeaderCell("Итого");

        for (YearlyReportDTO.BookReportDTO bookReportDTO : yearlyReportDTO.getYearlyBooksSummary()) {
            summaryTable.addCell(bookReportDTO.getTitle());
            summaryTable.addCell(String.valueOf(bookReportDTO.getCount()));
            summaryTable.addCell(String.valueOf(bookReportDTO.getUnitPrice()));
            summaryTable.addCell(String.valueOf(bookReportDTO.getTotalPrice()));
        }

        PdfFont boldFont = PdfFontFactory.createFont("fonts/arialbd.ttf", PdfEncodings.IDENTITY_H, true);
        summaryTable.addCell(new Paragraph("ИТОГО").setFont(boldFont));
        summaryTable.addCell(new Paragraph(String.valueOf(yearlyReportDTO.getTotalBookCount())).setFont(boldFont));
        summaryTable.addCell("");
        summaryTable.addCell(new Paragraph(String.valueOf(yearlyReportDTO.getTotalBookRevenue())).setFont(boldFont));

        document.add(summaryTable);
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
