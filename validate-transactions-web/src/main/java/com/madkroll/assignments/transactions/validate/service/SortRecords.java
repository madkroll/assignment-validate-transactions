package com.madkroll.assignments.transactions.validate.service;

import com.madkroll.assignments.transactions.validate.data.Record;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SortRecords {

    public Iterator<Record> byReference(final List<Record> records) {
        return records.stream()
                .sorted(Comparator.comparing(Record::getReference))
                .collect(Collectors.toUnmodifiableList())
                .iterator();
    }
}
