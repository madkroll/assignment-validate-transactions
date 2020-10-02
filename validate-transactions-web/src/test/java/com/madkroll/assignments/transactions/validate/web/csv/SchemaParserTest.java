package com.madkroll.assignments.transactions.validate.web.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.MapEntry.entry;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SchemaParserTest {

    private static final String CSV_HEADER_LINE = "csv-header-line";

    private static final String[] ORDERED_HEADERS = {
            "FIRST_HEADER", "second_header", "third_HEADER"
    };

    @Mock
    private CSVParserBuilder csvParserBuilder;

    @Mock
    private CSVParser csvParser;

    @Before
    public void setUp() {
        given(csvParserBuilder.build()).willReturn(csvParser);
    }

    @Test
    public void shouldParseCsvSchema() throws IOException {
        given(csvParser.parseLine(CSV_HEADER_LINE)).willReturn(ORDERED_HEADERS);

        final Map<String, Integer> result = new SchemaParser(csvParserBuilder).parse(CSV_HEADER_LINE);

        assertThat(result)
                .containsOnly(
                        entry(ORDERED_HEADERS[0], 0),
                        entry(ORDERED_HEADERS[1], 1),
                        entry(ORDERED_HEADERS[2], 2)
                );

        verify(csvParserBuilder).build();
        verify(csvParser).parseLine(CSV_HEADER_LINE);
    }

    @Test
    public void shouldThrowExceptionIfFailedToParse() throws IOException {
        given(csvParser.parseLine(CSV_HEADER_LINE)).willThrow(IOException.class);

        assertThatThrownBy(() -> new SchemaParser(csvParserBuilder).parse(CSV_HEADER_LINE))
                .isInstanceOf(IOException.class);

        verify(csvParserBuilder).build();
        verify(csvParser).parseLine(CSV_HEADER_LINE);
    }
}