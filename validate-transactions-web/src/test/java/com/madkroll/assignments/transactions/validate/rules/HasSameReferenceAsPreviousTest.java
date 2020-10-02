package com.madkroll.assignments.transactions.validate.rules;

import com.madkroll.assignments.transactions.validate.data.Record;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class HasSameReferenceAsPreviousTest {

    @Test
    public void shouldReturnFalseIfBothRecordsHaveUniqueReferences() {
        assertThat(
                new HasSameReferenceAsPrevious()
                        .test(
                                newRecord(1),
                                newRecord(2)
                        )
        ).isFalse();
    }

    @Test
    public void shouldReturnTrueIfBothRecordsHaveEqualReferences() {
        assertThat(
                new HasSameReferenceAsPrevious()
                        .test(
                                newRecord(10),
                                newRecord(10)
                        )
        ).isTrue();
    }

    private static Record newRecord(final long recordReference) {
        return new Record(recordReference, "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}