package com.madkroll.assignments.transactions.validate.web.csv;

import com.madkroll.assignments.transactions.validate.data.Record;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class CsvRecordParser {

    private final SchemaParser schemaParser;
    private final RowParser rowParser;

    public List<Record> parse(final MultipartFile csvRecords) {
        try (final BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvRecords.getInputStream()))) {
            final Map<String, Integer> csvSchema = schemaParser.parse(csvReader.readLine());
            final List<Record> records = new ArrayList<>();

            String nextRow;
            while ((nextRow = csvReader.readLine()) != null) {
                records.add(rowParser.parse(csvSchema, nextRow));
            }

            return records;
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to parse CSV records", e);
        }
    }
}
