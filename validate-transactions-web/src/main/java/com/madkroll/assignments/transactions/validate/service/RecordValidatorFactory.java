package com.madkroll.assignments.transactions.validate.service;

import com.madkroll.assignments.transactions.validate.data.Record;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Component
@AllArgsConstructor
public class RecordValidatorFactory {

    private final Predicate<Record> hasIncorrectEndBalance;
    private final BiPredicate<Record, Record> hasSameReferenceAsPrevious;

    public RecordValidator recordValidator() {
        return new RecordValidator(
                hasSameReferenceAsPrevious,
                hasIncorrectEndBalance
        );
    }
}
