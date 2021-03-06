package com.madkroll.assignments.transactions.validate.service;

import com.google.common.collect.ImmutableList;
import com.madkroll.assignments.transactions.validate.data.Record;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

public class SortRecordsTest {

    private static final Record RECORD_FIRST = newRecord(1);
    private static final Record RECORD_MIDDLE = newRecord(500);
    private static final Record RECORD_LAST = newRecord(1500);

    @Test
    public void shouldSortRecordsByReference() {
        final Iterator<Record> recordIterator = new SortRecords().byReference(
                ImmutableList.of(
                        RECORD_MIDDLE,
                        RECORD_LAST,
                        RECORD_FIRST
                )
        );

        // order-dependent assertions
        assertThat(recordIterator.next()).isEqualTo(RECORD_FIRST);
        assertThat(recordIterator.next()).isEqualTo(RECORD_MIDDLE);
        assertThat(recordIterator.next()).isEqualTo(RECORD_LAST);
    }

    public static Record newRecord(final long reference) {
        return new Record(reference, "", "", BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}