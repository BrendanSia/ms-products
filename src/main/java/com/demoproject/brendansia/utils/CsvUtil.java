package com.demoproject.brendansia.utils;

import com.demoproject.brendansia.entity.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {
    public static String TYPE = "text/csv";
    static String[] HEADERs = { "id", "code", "name", "category", "brand", "type", "description" };
    public static boolean hasCsvFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Product> csvToStuList(InputStream is) {
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(bReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Product> stuList = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Product product = new Product();
                product.setId(Integer.parseInt(csvRecord.get("id")));
                product.setCode(csvRecord.get("code"));
                product.setName(csvRecord.get("name"));
                product.setCategory(csvRecord.get("category"));
                product.setBrand(csvRecord.get("brand"));
                product.setType(csvRecord.get("type"));
                product.setDescription(csvRecord.get("description"));
                stuList.add(product);
            }
            return stuList;
        } catch (IOException e) {
            throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
        }
    }
}
