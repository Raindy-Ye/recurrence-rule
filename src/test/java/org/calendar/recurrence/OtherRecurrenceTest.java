package org.calendar.recurrence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class OtherRecurrenceTest {

	@Test
	@DisplayName("start exceeds recurrent end date")
	void test_Daily_no_recur_if_start_exceed_recurrent_end_date() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;INTERVAL=2;UNTIL=20180101T000000Z");
		LocalDate start = LocalDate.of(2018, 1, 2);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		assertFalse(recurrence.hasNext(), "No recurrence as start exceeds recurrent end date");
	}

	@Test
	@DisplayName("no side effect when invoke hasNext() method continuously")
	void test_invoke_hasNext_method_continuously() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;COUNT=1;BYMONTH=1");
		LocalDate start = LocalDate.of(2020, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		recurrence.hasNext();
		recurrence.hasNext();
		recurrence.hasNext();
		// even though invoke the invoke hasNext() method 3 times continuously
		// the first recurrence date still be the 2020-01-01
		assertEquals(LocalDate.of(2020, 1, 1), recurrence.next());
	}

	@Test
	@DisplayName("throw excpetion when day of Month exceeds 31")
	void test_invalid_day_of_month_exception() {
		Throwable exception = assertThrows(RRuleException.class, () -> {
			RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=32");
		});
		assertEquals("Invalid day of month:32", exception.getMessage());
	}

	@Test
	@DisplayName("throw excpetion when month value exceeds 12")
	void test_invalid_month_exception() {
		Throwable exception = assertThrows(RRuleException.class, () -> {
			RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTH=13");
		});
		assertEquals("Invalid month:13", exception.getMessage());
	}

	@DisplayName("throw exception when rule is invalid")
	@ParameterizedTest
	@ValueSource(strings = { "FREQ=MONTHLY;BYMONTHDAY=15", // not start with "RRULE:"
			"RRULE:BYMONTHDAY=15", // not contains "FREQ"
			"RRULE:FREQ=MONTHLY;BYMONTHDAY=", // invalid parameter pair
			"RRULE:FREQ=MONTHLY;BYDAY=M"// invalid day of week
	})
	void test_invalid_rule_exception(String rrule) {
		assertThrows(RRuleException.class, () -> {
			RecurrenceRule.getInstance(rrule);
		});
	}

	@Test
	@DisplayName("throw exception when infinite loop detected")
	void test_infinite_loop_detected_exception() {
		Throwable exception = assertThrows(InfiniteLoopException.class, () -> {
			RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=15");
			LocalDate start = LocalDate.of(1997, 9, 13);
			RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
			while (recurrence.hasNext()) {
				recurrence.next();
			}
		});
		assertEquals("Infinite loop detected!", exception.getMessage());
	}

	@Test
	@DisplayName("throw exception when invalid until date")
	void test_throw_exception_when_there_is_no_more_recurrence() {
		Throwable exception = assertThrows(NoSuchElementException.class, () -> {
			RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=15;COUNT=1");
			LocalDate start = LocalDate.of(1997, 9, 13);
			RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
			while (true) {
				recurrence.next();
			}
		});
		assertEquals("No more recurrence!", exception.getMessage());
	}

	@Test
	@DisplayName("throw exception when invalid until date")
	void test_throw_exception_when_the_until_date_is_invalid() {
		Throwable exception = assertThrows(RRuleException.class, () -> {
			RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=15;UNTIL=2018");
		});
		assertEquals(
				"The recurence until date[2018] is not valid, it does not follow the pattern of \"yyyyMMdd'T'Hmmss'Z'\"",
				exception.getMessage());
	}

	@DisplayName("throw exception when BYMONTHDAY is not in the range of [-31, 31]")
	@ParameterizedTest
	@ValueSource(strings = { "RRULE:FREQ=MONTHLY;BYMONTHDAY=32", "RRULE:BYMONTHDAY=-32" })
	void test_invalid_BYMONTHDAY_exception(String rrule) {
		assertThrows(RRuleException.class, () -> {
			RecurrenceRule.getInstance(rrule);
		});
	}

	@Test
	@DisplayName("throw exception when no specified recurrent frequence")
	void test_throw_exception_when_no_specified_recurrent_frequence() {
		assertAll(() -> {
			String rule = "RRULE:BYMONTHDAY=15";
			Throwable exception = assertThrows(RRuleException.class, () -> RecurrenceRule.getInstance(rule));
			assertEquals("The recurrent frequency must be specified:\n" + rule, exception.getMessage());
		}, () -> {
			String rule = "RRULE:FREQ=YEAR;BYMONTHDAY=15";
			Throwable exception = assertThrows(RRuleException.class, () -> RecurrenceRule.getInstance(rule));
			assertEquals("The recurrent frequency[YEAR] is invalid, it only support "
					+ Arrays.asList(Frequency.values()) + ":\n" + rule, exception.getMessage());
		});
	}
}
