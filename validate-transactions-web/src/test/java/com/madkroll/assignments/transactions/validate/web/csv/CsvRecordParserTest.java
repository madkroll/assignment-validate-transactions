package com.madkroll.assignments.transactions.validate.web.csv;

import com.google.common.collect.ImmutableList;
import com.madkroll.assignments.transactions.validate.data.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CsvRecordParserTest {

    @Mock
    private SchemaParser schemaParser;

    @Mock
    private RowParser rowParser;

    @Mock
    private MultipartFile csvFile;

    @Test
    public void shouldFailIfUnableToReadFile() throws IOException {
        given(csvFile.getInputStream()).willThrow(IOException.class);

        assertThatThrownBy(
                () -> new CsvRecordParser(schemaParser, rowParser).parse(csvFile)
        )
                .isExactlyInstanceOf(UncheckedIOException.class)
                .hasMessage("Unable to parse CSV records")
                .hasRootCauseExactlyInstanceOf(IOException.class);

        verifyNoInteractions(schemaParser, rowParser);
    }

    @Test
    public void shouldReturnNoRecordsIfNoLinesFound() throws IOException {
        given(csvFile.getInputStream()).willReturn(new ByteArrayInputStream("".getBytes()));
        final List<Record> records = new CsvRecordParser(schemaParser, rowParser).parse(csvFile);
        assertThat(records).isEmpty();
        verifyNoInteractions(rowParser);
    }

    @Test
    public void shouldReturnNoRecordsIfHeaderLineFoundOnly() throws IOException {
        given(csvFile.getInputStream()).willReturn(new ByteArrayInputStream("header-line".getBytes()));
        final List<Record> records = new CsvRecordParser(schemaParser, rowParser).parse(csvFile);
        assertThat(records).isEmpty();
        verifyNoInteractions(rowParser);
    }

    @Test
    public void shouldReturnAllLines() throws IOException {
        final List<Record> expectedRecords =
                ImmutableList.of(
                        new Record(1, "", "first-record", null, null, null),
                        new Record(2, "", "second-record", null, null, null)
                );
        given(csvFile.getInputStream())
                .willReturn(new ByteArrayInputStream("header-line\nfirst-record\nsecond-record".getBytes()));
        given(rowParser.parse(anyMap(), eq("first-record"))).willReturn(expectedRecords.get(0));
        given(rowParser.parse(anyMap(), eq("second-record"))).willReturn(expectedRecords.get(1));

        final List<Record> records = new CsvRecordParser(schemaParser, rowParser).parse(csvFile);
        assertThat(records).containsExactlyInAnyOrderElementsOf(expectedRecords);
    }

}