package com.madkroll.assignments.transactions.validate.rules;

import com.madkroll.assignments.transactions.validate.data.Record;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class HasIncorrectEndBalance implements Predicate<Record> {

    @Override
    public boolean test(final Record record) {
        return record
                .getStartBalance()
                .add(record.getMutation())
                .compareTo(record.getEndBalance()) != 0;
    }
}
