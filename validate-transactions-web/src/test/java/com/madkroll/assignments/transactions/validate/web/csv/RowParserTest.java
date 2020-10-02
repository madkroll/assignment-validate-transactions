package com.madkroll.assignments.transactions.validate.web.csv;

import com.google.common.collect.ImmutableMap;
import com.madkroll.assignments.transactions.validate.data.Record;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static com.madkroll.assignments.transactions.validate.web.csv.Fields.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RowParserTest {

    private static final String CSV_RECORD = "csv-line-record";

    private static final Map<String, Integer> CSV_SCHEMA =
            ImmutableMap.<String, Integer>builder()
                    .put(REFERENCE, 0)
                    .put(ACCOUNT_NUMBER, 1)
                    .put(DESCRIPTION, 2)
                    .put(START_BALANCE, 3)
                    .put(MUTATION, 4)
                    .put(END_BALANCE, 5)
                    .build();

    private static final Map<String, String> EXPECTED_VALUES =
            ImmutableMap.<String, String>builder()
                    .put(REFERENCE, "1")
                    .put(ACCOUNT_NUMBER, "account-number")
                    .put(DESCRIPTION, "description")
                    .put(START_BALANCE, "2.5")
                    .put(MUTATION, "2.5")
                    .put(END_BALANCE, "5.0")
                    .build();

    private static final String[] CSV_FIELDS = {
            EXPECTED_VALUES.get(REFERENCE),
            EXPECTED_VALUES.get(ACCOUNT_NUMBER),
            EXPECTED_VALUES.get(DESCRIPTION),
            EXPECTED_VALUES.get(START_BALANCE),
            EXPECTED_VALUES.get(MUTATION),
            EXPECTED_VALUES.get(END_BALANCE)
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
    public void shouldParseCsvLineAccordingToSchema() throws IOException {
        given(csvParser.parseLine(CSV_RECORD)).willReturn(CSV_FIELDS);

        final Record record = new RowParser(csvParserBuilder).parse(CSV_SCHEMA, CSV_RECORD);

        assertThat(record).isEqualToComparingFieldByField(
                new Record(1, "account-number", "description", new BigDecimal("2.5"), new BigDecimal("2.5"), new BigDecimal("5.0"))
        );

        verify(csvParserBuilder).build();
        verify(csvParser).parseLine(CSV_RECORD);
    }

    @Test
    public void shouldThrowExceptionIfFailedToParse() throws IOException {
        given(csvParser.parseLine(CSV_RECORD)).willThrow(IOException.class);

        assertThatThrownBy(() -> new RowParser(csvParserBuilder).parse(CSV_SCHEMA, CSV_RECORD))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Unable to parse a record.");

        verify(csvParserBuilder).build();
        verify(csvParser).parseLine(CSV_RECORD);
    }

    @Test
    public void shouldThrowExceptionIfAnyRequiredFieldIsEmpty() throws IOException {
        for (int i = 0; i < CSV_FIELDS.length; i++) {
            String[] fieldsToTestOnEmptyValue = Stream.of(CSV_FIELDS).toArray(String[]::new);
            fieldsToTestOnEmptyValue[i] = null;

            given(csvParser.parseLine(CSV_RECORD)).willReturn(fieldsToTestOnEmptyValue);

            assertThatThrownBy(() -> new RowParser(csvParserBuilder).parse(CSV_SCHEMA, CSV_RECORD))
                    .isExactlyInstanceOf(IllegalStateException.class)
                    .hasMessage("Unable to parse a record.")
                    .hasCauseExactlyInstanceOf(IllegalStateException.class);
        }
    }

    @Test
    public void shouldThrowExceptionIfUnableToParseBalance() throws IOException {
        String[] fieldsWithNotANumberBalance = Stream.of(CSV_FIELDS).toArray(String[]::new);
        fieldsWithNotANumberBalance[CSV_SCHEMA.get(START_BALANCE)] = "not-a-number";

        given(csvParser.parseLine(CSV_RECORD)).willReturn(fieldsWithNotANumberBalance);

        assertThatThrownBy(() -> new RowParser(csvParserBuilder).parse(CSV_SCHEMA, CSV_RECORD))
                .isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("Unable to parse a record.");
    }
}