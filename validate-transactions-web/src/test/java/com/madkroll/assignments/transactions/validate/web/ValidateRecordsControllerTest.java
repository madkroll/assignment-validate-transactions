package com.madkroll.assignments.transactions.validate.web;

import com.madkroll.assignments.transactions.validate.data.Records;
import com.madkroll.assignments.transactions.validate.data.ValidationReport;
import com.madkroll.assignments.transactions.validate.service.RecordProcessor;
import com.madkroll.assignments.transactions.validate.web.csv.CsvRecordParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ValidateRecordsControllerTest {

    private static final Records RECORDS = new Records(List.of());
    private static final ValidationReport REPORT = new ValidationReport(List.of());

    @Mock
    private CsvRecordParser csvRecordParser;

    @Mock
    private RecordProcessor recordProcessor;

    @Before
    public void setUp() {
        given(recordProcessor.process(any())).willReturn(REPORT);
    }

    @Test
    public void shouldReturnReportForXml() {
        assertThat(new ValidateRecordsController(csvRecordParser, recordProcessor).handleValidateXmlRequest(RECORDS))
                .isEqualTo(REPORT);
    }

    @Test
    public void shouldReturnReportForCsv() {
        assertThat(new ValidateRecordsController(csvRecordParser, recordProcessor).handleValidateXmlRequest(RECORDS))
                .isEqualTo(REPORT);
    }
}