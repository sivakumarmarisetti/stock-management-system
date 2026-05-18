package com.stockmanagement.controller;

import com.stockmanagement.service.CsvExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/export")
@RequiredArgsConstructor
public class ExportController {

    private final CsvExportService csvExportService;

    @GetMapping("/products")
    public ResponseEntity<String> exportProducts() {

        String csvData =
                csvExportService.exportProductsToCsv();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=products.csv"
                )
                .contentType(MediaType.TEXT_PLAIN)
                .body(csvData);
    }
}