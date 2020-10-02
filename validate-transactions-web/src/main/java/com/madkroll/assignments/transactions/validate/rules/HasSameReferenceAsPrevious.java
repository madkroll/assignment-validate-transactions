package com.madkroll.assignments.transactions.validate.rules;

import com.madkroll.assignments.transactions.validate.data.Record;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;

@Component
public class HasSameReferenceAsPrevious implements BiPredicate<Record, Record> {

    @Override
    public boolean test(final Record previousRecord, final Record nextRecord) {
        return previousRecord != null && previousRecord.getReference().equals(nextRecord.getReference());
    }
}
