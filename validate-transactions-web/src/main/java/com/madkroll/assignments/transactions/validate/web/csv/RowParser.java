package com.madkroll.assignments.transactions.validate.web.csv;

import com.madkroll.assignments.transactions.validate.data.Record;
import com.opencsv.CSVParserBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Map;

@Component
@AllArgsConstructor
public class RowParser {

    private final CSVParserBuilder csvParserBuilder;

    public Record parse(final Map<String, Integer> csvSchema, final String recordRow) {
        try {
            final String[] csvFields = csvParserBuilder.build().parseLine(recordRow);

            return new Record(
                    Long.parseLong(required(csvFields[csvSchema.get(Fields.REFERENCE)])),
                    required(csvFields[csvSchema.get(Fields.ACCOUNT_NUMBER)]),
                    required(csvFields[csvSchema.get(Fields.DESCRIPTION)]),
                    new BigDecimal(required(csvFields[csvSchema.get(Fields.START_BALANCE)])),
                    new BigDecimal(required(csvFields[csvSchema.get(Fields.MUTATION)])),
                    new BigDecimal(required(csvFields[csvSchema.get(Fields.END_BALANCE)]))
            );
        } catch (Exception e) {
            throw new IllegalStateException("Unable to parse a record.", e);
        }
    }

    private String required(final String fieldValue) {
        if (!StringUtils.hasText(fieldValue)) {
            throw new IllegalStateException("Field is empty");
        }

        return fieldValue;
    }
}
