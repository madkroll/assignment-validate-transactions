package com.madkroll.assignments.transactions.validate.web.csv;

import com.opencsv.CSVParserBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvRecordParserConfiguration {

    private static final char CSV_SEPARATOR = ',';

    @Bean
    public CSVParserBuilder csvParserBuilder() {
        return new CSVParserBuilder().withSeparator(CSV_SEPARATOR);
    }
}
