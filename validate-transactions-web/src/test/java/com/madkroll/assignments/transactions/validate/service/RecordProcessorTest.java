package com.madkroll.assignments.transactions.validate.service;

import com.google.common.collect.ImmutableList;
import com.madkroll.assignments.transactions.validate.data.InvalidRecord;
import com.madkroll.assignments.transactions.validate.data.Record;
import com.madkroll.assignments.transactions.validate.data.ValidationReport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class RecordProcessorTest {

    private static final List<Record> INPUT_RECORDS_EMPTY = ImmutableList.of();
    private static final List<Record> INPUT_RECORDS_NON_EMPTY = ImmutableList.of(newRecord("input-record"));
    private static final List<InvalidRecord> INVALID_RECORDS_EMPTY = ImmutableList.of();
    private static final List<InvalidRecord> INVALID_RECORDS_NON_EMPTY = ImmutableList.of(newInvalidRecord("invalid-record"));

    @Mock
    private RecordValidatorFactory recordValidatorFactory;

    @Mock
    private SequentialSortingRecordValidator sequentialSortingRecordValidator;

    @Before
    public void setUp() {
        given(recordValidatorFactory.recordValidator()).willReturn(sequentialSortingRecordValidator);
    }

    @Test
    public void shouldReturnEmptyReportIfNoInputRecords() {
        assertThat(
                new RecordProcessor(recordValidatorFactory)
                        .process(INPUT_RECORDS_EMPTY)
                        .getInvalidRecords()
        ).isEmpty();

        verifyNoInteractions(recordValidatorFactory, sequentialSortingRecordValidator);
    }

    @Test
    public void shouldReturnReportWithFoundInvalidRecords() {
        given(sequentialSortingRecordValidator.validate(INPUT_RECORDS_NON_EMPTY))
                .willReturn(new ValidationReport(INVALID_RECORDS_NON_EMPTY));

        assertThat(
                new RecordProcessor(recordValidatorFactory)
                        .process(INPUT_RECORDS_NON_EMPTY)
                        .getInvalidRecords()
        ).containsExactlyInAnyOrderElementsOf(INVALID_RECORDS_NON_EMPTY);

        verify(recordValidatorFactory).recordValidator();
        verify(sequentialSortingRecordValidator).validate(INPUT_RECORDS_NON_EMPTY);
    }

    @Test
    public void shouldReturnEmptyReportWhenNoInvalidRecordsFound() {
        given(sequentialSortingRecordValidator.validate(INPUT_RECORDS_NON_EMPTY))
                .willReturn(new ValidationReport(INVALID_RECORDS_EMPTY));

        assertThat(
                new RecordProcessor(recordValidatorFactory)
                        .process(INPUT_RECORDS_NON_EMPTY)
                        .getInvalidRecords()
        ).isEmpty();

        verify(recordValidatorFactory).recordValidator();
        verify(sequentialSortingRecordValidator).validate(INPUT_RECORDS_NON_EMPTY);
    }

    private static Record newRecord(final String description) {
        return new Record("", "", description, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private static InvalidRecord newInvalidRecord(final String description) {
        return new InvalidRecord("", description);
    }
}