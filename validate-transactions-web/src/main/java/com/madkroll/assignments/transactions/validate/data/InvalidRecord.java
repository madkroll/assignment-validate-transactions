package com.madkroll.assignments.transactions.validate.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class InvalidRecord {

    private final long reference;
    private final String description;
}
