package com.madkroll.assignments.transactions.validate.web.csv;

import com.opencsv.CSVParserBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toUnmodifiableMap;

@Component
@AllArgsConstructor
public class SchemaParser {

    private final CSVParserBuilder csvParserBuilder;

    public Map<String, Integer> parse(final String readLine) throws IOException {
        final String[] orderedHeaders = csvParserBuilder.build().parseLine(readLine);

        return IntStream.range(0, orderedHeaders.length)
                .boxed()
                .collect(toUnmodifiableMap(headerIndex -> orderedHeaders[headerIndex], Function.identity()));
    }
}
