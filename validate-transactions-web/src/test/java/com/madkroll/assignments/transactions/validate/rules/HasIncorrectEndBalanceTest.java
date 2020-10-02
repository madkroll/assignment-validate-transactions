package com.madkroll.assignments.transactions.validate.rules;

import com.madkroll.assignments.transactions.validate.data.Record;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class HasIncorrectEndBalanceTest {

    private static final Record RECORD_VALID_BOTH_POSITIVE = newRecord("2.3", "2.7", "5.00");
    private static final Record RECORD_VALID_START_NEGATIVE = newRecord("-2.5", "5.0", "2.5");
    private static final Record RECORD_VALID_START_MUTATION_NEGATIVE = newRecord("5.0", "-2.5", "2.5");
    private static final Record RECORD_VALID_START_BOTH_NEGATIVE = newRecord("-2.5", "-2.5", "-5.0");
    private static final Record RECORD_VALID_START_ALL_ZEROS = newRecord("0", "0", "0");

    private static final Record RECORD_INVALID_BOTH_POSITIVE = newRecord("2.3", "2.8", "5.00");
    private static final Record RECORD_INVALID_START_NEGATIVE = newRecord("-2.4", "5.0", "2.5");
    private static final Record RECORD_INVALID_START_MUTATION_NEGATIVE = newRecord("5.0", "-2.4", "2.5");
    private static final Record RECORD_INVALID_START_BOTH_NEGATIVE = newRecord("-2.4", "-2.5", "-5.0");

    @Test
    public void shouldReturnFalseWhenEndBalanceIsCorrect() {
        final HasIncorrectEndBalance checkIfInvalid = new HasIncorrectEndBalance();

        assertThat(checkIfInvalid.test(RECORD_VALID_BOTH_POSITIVE)).isFalse();
        assertThat(checkIfInvalid.test(RECORD_VALID_START_NEGATIVE)).isFalse();
        assertThat(checkIfInvalid.test(RECORD_VALID_START_MUTATION_NEGATIVE)).isFalse();
        assertThat(checkIfInvalid.test(RECORD_VALID_START_BOTH_NEGATIVE)).isFalse();
        assertThat(checkIfInvalid.test(RECORD_VALID_START_ALL_ZEROS)).isFalse();
    }

    @Test
    public void shouldReturnTrueWhenEndBalanceIsIncorrect() {
        final HasIncorrectEndBalance checkIfInvalid = new HasIncorrectEndBalance();

        assertThat(checkIfInvalid.test(RECORD_INVALID_BOTH_POSITIVE)).isTrue();
        assertThat(checkIfInvalid.test(RECORD_INVALID_START_NEGATIVE)).isTrue();
        assertThat(checkIfInvalid.test(RECORD_INVALID_START_MUTATION_NEGATIVE)).isTrue();
        assertThat(checkIfInvalid.test(RECORD_INVALID_START_BOTH_NEGATIVE)).isTrue();
    }

    private static Record newRecord(final String startBalance, final String mutation, final String endBalance) {
        return new Record(0, "", "", new BigDecimal(startBalance), new BigDecimal(mutation), new BigDecimal(endBalance));
    }

}