package com.madkroll.assignments.transactions.validate.service;

import com.madkroll.assignments.transactions.validate.rules.HasIncorrectEndBalance;
import com.madkroll.assignments.transactions.validate.rules.HasSameReferenceAsPrevious;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SequentialSortingRecordValidatorFactoryTest {

    @Mock
    private SortRecords sortRecords;

    @Mock
    private HasSameReferenceAsPrevious hasSameReferenceAsPrevious;

    @Mock
    private HasIncorrectEndBalance hasIncorrectEndBalance;

    @Test
    public void shouldCreateRecordValidatorInstance() {
        assertThat(new RecordValidatorFactory(sortRecords, hasIncorrectEndBalance, hasSameReferenceAsPrevious).recordValidator())
                .isNotNull();
    }
}