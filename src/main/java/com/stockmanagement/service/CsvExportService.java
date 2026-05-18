package com.stockmanagement.service;

import com.opencsv.CSVWriter;
import com.stockmanagement.entity.Product;
import com.stockmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvExportService {

    private final ProductRepository productRepository;

    public String exportProductsToCsv() {

        List<Product> products =
                productRepository.findAll();

        StringWriter stringWriter =
                new StringWriter();

        CSVWriter csvWriter =
                new CSVWriter(stringWriter);

        String[] header = {
                "ID",
                "Product Code",
                "Product Name",
                "Product Type",
                "Rate",
                "Volume",
                "Quantity"
        };

        csvWriter.writeNext(header);

        products.stream()
                .filter(product ->
                        !Boolean.TRUE.equals(
                                product.getDeleted()
                        )
                )
                .forEach(product -> {

                    String[] data = {
                            String.valueOf(product.getId()),
                            product.getProductCode(),
                            product.getProductName(),
                            product.getProductType(),
                            String.valueOf(product.getRate()),
                            String.valueOf(product.getVolume()),
                            String.valueOf(product.getQuantity())
                    };

                    csvWriter.writeNext(data);
                });

        return stringWriter.toString();
    }
}