package com.madkroll.assignments.transactions.validate.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.madkroll.assignments.transactions.validate.data.InvalidRecord;
import com.madkroll.assignments.transactions.validate.data.Record;
import com.madkroll.assignments.transactions.validate.data.ValidationReport;
import lombok.AllArgsConstructor;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

@NotThreadSafe
@AllArgsConstructor
public class SequentialSortingRecordValidator {

    private final ListMultimap<Long, InvalidRecord> invalidRecords = ArrayListMultimap.create();

    private final SortRecords sortRecords;
    private final BiPredicate<Record, Record> hasSameReferenceAsPrevious;
    private final Predicate<Record> hasIncorrectEndBalance;

    public ValidationReport validate(final List<Record> records) {
        final Iterator<Record> recordsSortedByReference = sortRecords.byReference(records);

        Record previousRecord = null;
        while (recordsSortedByReference.hasNext()) {
            final Record nextRecord = recordsSortedByReference.next();
            validateSequentialRecords(previousRecord, nextRecord);

            previousRecord = nextRecord;
        }

        return new ValidationReport(ImmutableList.copyOf(invalidRecords.values()));
    }

    private void validateSequentialRecords(final Record previousRecord, final Record nextRecord) {
        if (invalidRecords.containsKey(nextRecord.getReference())) {
            thisRecordIsInvalid(nextRecord);
            return;
        }

        if (hasSameReferenceAsPrevious.test(previousRecord, nextRecord)) {
            thisRecordIsInvalid(previousRecord);
            thisRecordIsInvalid(nextRecord);
            return;
        }

        if (hasIncorrectEndBalance.test(nextRecord)) {
            thisRecordIsInvalid(nextRecord);
        }
    }

    private void thisRecordIsInvalid(final Record record) {
        invalidRecords.put(
                record.getReference(),
                new InvalidRecord(
                        record.getReference(),
                        record.getDescription()
                )
        );
    }
}
