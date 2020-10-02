package com.madkroll.assignments.transactions.validate.service;

import com.google.common.collect.ImmutableList;
import com.madkroll.assignments.transactions.validate.data.Record;
import com.madkroll.assignments.transactions.validate.data.ValidationReport;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class RecordProcessor {

    private final SortRecords sortRecords;
    private final RecordValidatorFactory recordValidatorFactory;

    public ValidationReport process(final List<Record> records) {
        if (CollectionUtils.isEmpty(records)) {
            log.warn("No any input records found.");
            return new ValidationReport(ImmutableList.of());
        }

        final Iterator<Record> sortedByReference = sortRecords.byReference(records);
        return recordValidatorFactory.recordValidator().validate(sortedByReference);
    }
}
