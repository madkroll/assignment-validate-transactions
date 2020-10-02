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
                                newRecord("one-reference"),
                                newRecord("another-reference")
                        )
        ).isFalse();
    }

    @Test
    public void shouldReturnTrueIfBothRecordsHaveEqualReferences() {
        assertThat(
                new HasSameReferenceAsPrevious()
                        .test(
                                newRecord("duplicate-reference"),
                                newRecord("duplicate-reference")
                        )
        ).isTrue();
    }

    @Test
    public void shouldReturnTrueIfBothRecordsHaveEqualReferencesIgnoringCase() {
        // So it defines that references which differ only by case - as equal references
        assertThat(
                new HasSameReferenceAsPrevious()
                        .test(
                                newRecord("DUPLICATE-REFERENCE"),
                                newRecord("duplicate-reference")
                        )
        ).isTrue();
    }

    private static Record newRecord(final String recordReference) {
        return new Record(recordReference, "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}