package com.madkroll.assignments.transactions.validate.service;

import com.google.common.collect.ImmutableList;
import com.madkroll.assignments.transactions.validate.data.Record;
import com.madkroll.assignments.transactions.validate.data.ValidationReport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SequentialSortingRecordValidatorTest {

    private static final List<Record> ANY_INPUT_RECORDS = ImmutableList.of();
    private static final Record RECORD_VALID = newRecord("valid-record", "valid-record");
    private static final Record RECORD_WRONG_END_BALANCE_FIRST = newRecord("wrong-balance", "wrong-balance-first");
    private static final Record RECORD_WRONG_END_BALANCE_SECOND = newRecord("wrong-balance", "wrong-balance-second");
    private static final Record RECORD_SAME_REFERENCE_FIRST = newRecord("same-reference", "same-reference-first");
    private static final Record RECORD_SAME_REFERENCE_SECOND = newRecord("same-reference", "same-reference-second");

    @Mock
    private SortRecords sortRecords;

    @Mock
    private BiPredicate<Record, Record> hasSameReferenceAsPrevious;

    @Mock
    private Predicate<Record> hasIncorrectEndBalance;

    @Mock
    private Iterator<Record> recordIterator;

    @Before
    public void setUp() {
        given(sortRecords.byReference(ANY_INPUT_RECORDS)).willReturn(recordIterator);
        given(hasIncorrectEndBalance.test(RECORD_WRONG_END_BALANCE_FIRST)).willReturn(true);
        given(hasSameReferenceAsPrevious.test(RECORD_SAME_REFERENCE_FIRST, RECORD_SAME_REFERENCE_SECOND)).willReturn(true);
    }

    @Test
    public void shouldFindInvalidRecordIfEndBalanceIsIncorrect() {
        given(recordIterator.hasNext()).willReturn(true, true, false);
        given(recordIterator.next()).willReturn(RECORD_VALID, RECORD_WRONG_END_BALANCE_FIRST);

        final ValidationReport report = new SequentialSortingRecordValidator(sortRecords, hasSameReferenceAsPrevious, hasIncorrectEndBalance)
                .validate(ANY_INPUT_RECORDS);

        assertThat(report).isNotNull();
        assertThat(report.getInvalidRecords()).hasSize(1);
        assertThat(report.getInvalidRecords().get(0).getDescription()).isEqualTo(RECORD_WRONG_END_BALANCE_FIRST.getDescription());
    }

    @Test
    public void shouldFindInvalidRecordsIfBothHaveSameReference() {
        given(recordIterator.hasNext()).willReturn(true, true, true, false);
        given(recordIterator.next()).willReturn(RECORD_SAME_REFERENCE_FIRST, RECORD_SAME_REFERENCE_SECOND, RECORD_VALID);

        final ValidationReport report = new SequentialSortingRecordValidator(sortRecords, hasSameReferenceAsPrevious, hasIncorrectEndBalance)
                .validate(ANY_INPUT_RECORDS);

        assertThat(report).isNotNull();
        assertThat(report.getInvalidRecords()).hasSize(2);
        assertThat(report.getInvalidRecords().get(0).getDescription()).isEqualTo(RECORD_SAME_REFERENCE_FIRST.getDescription());
        assertThat(report.getInvalidRecords().get(1).getDescription()).isEqualTo(RECORD_SAME_REFERENCE_SECOND.getDescription());
    }

    @Test
    public void shouldFindInvalidRecordIfAlreadyFoundInvalidOneWithSuchReference() {
        given(recordIterator.hasNext()).willReturn(true, true, true, false);
        given(recordIterator.next()).willReturn(RECORD_WRONG_END_BALANCE_FIRST, RECORD_WRONG_END_BALANCE_SECOND, RECORD_VALID);

        final ValidationReport report = new SequentialSortingRecordValidator(sortRecords, hasSameReferenceAsPrevious, hasIncorrectEndBalance)
                .validate(ANY_INPUT_RECORDS);

        assertThat(report).isNotNull();
        assertThat(report.getInvalidRecords()).hasSize(2);
        assertThat(report.getInvalidRecords().get(0).getDescription()).isEqualTo(RECORD_WRONG_END_BALANCE_FIRST.getDescription());
        assertThat(report.getInvalidRecords().get(1).getDescription()).isEqualTo(RECORD_WRONG_END_BALANCE_SECOND.getDescription());
    }

    @Test
    public void shouldReturnEmptyReportIfNoInputRecords() {
        given(recordIterator.hasNext()).willReturn(false);

        final ValidationReport report = new SequentialSortingRecordValidator(sortRecords, hasSameReferenceAsPrevious, hasIncorrectEndBalance)
                .validate(ANY_INPUT_RECORDS);

        assertThat(report).isNotNull();
        assertThat(report.getInvalidRecords()).isEmpty();
    }

    @Test
    public void shouldReturnEmptyReportIfRecordsAreValid() {
        given(recordIterator.hasNext()).willReturn(true, false);
        given(recordIterator.next()).willReturn(RECORD_VALID);

        final ValidationReport report = new SequentialSortingRecordValidator(sortRecords, hasSameReferenceAsPrevious, hasIncorrectEndBalance)
                .validate(ANY_INPUT_RECORDS);

        assertThat(report).isNotNull();
        assertThat(report.getInvalidRecords()).isEmpty();
    }

    private static Record newRecord(final String reference, final String description) {
        return new Record(reference, "", description, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}