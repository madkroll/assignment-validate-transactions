package com.madkroll.assignments.transactions.validate.web;

import com.madkroll.assignments.transactions.validate.data.Record;
import com.madkroll.assignments.transactions.validate.data.Records;
import com.madkroll.assignments.transactions.validate.data.ValidationReport;
import com.madkroll.assignments.transactions.validate.service.RecordProcessor;
import com.madkroll.assignments.transactions.validate.web.csv.CsvRecordParser;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RequestMapping
@RestController
public class ValidateRecordsController {

    private final CsvRecordParser csvRecordParser;
    private final RecordProcessor recordProcessor;

    @PostMapping(
            path = "/validate",
            consumes = MediaType.APPLICATION_XML_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ValidationReport handleValidateXmlRequest(
            @RequestBody final Records xmlRecords
    ) {
        return recordProcessor.process(xmlRecords.getRecords());
    }

    @PostMapping(
            path = "/validate",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ValidationReport handleValidateCsvRequest(
            @RequestParam("csvRecords") final MultipartFile csvRecords
    ) {
        final List<Record> records = csvRecordParser.parse(csvRecords);
        return recordProcessor.process(records);
    }
}
