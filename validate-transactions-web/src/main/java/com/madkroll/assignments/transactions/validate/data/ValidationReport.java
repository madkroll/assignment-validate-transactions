package com.madkroll.assignments.transactions.validate.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationReport {

    private final List<InvalidRecord> invalidRecords;
}
