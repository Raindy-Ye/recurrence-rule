package org.calendar.recurrence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class DailyRecurrenceTest {
	@Test
	@DisplayName("every day")
	void test_Daily_Daily_every_day_for_3_times() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;COUNT=3");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 2), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 3), iter.next()));
	}

	@Test
	@DisplayName("every 2 days")
	void test_Daily_every_2_days_for_3_times() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;COUNT=3;INTERVAL=2");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()), () -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 3), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 5), iter.next()));
	}

	@Test
	@DisplayName("every day on Saturday")
	void test_Daily_every_day_on_Sat_3_times() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;count=3;BYDAY=SA");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 6), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 13), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 20), iter.next()));
	}

	@Test
	@DisplayName("every 2 days on Monday")
	void test_Daily_every_2_days_on_Mon_3_times() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;INTERVAL=2;count=3;BYDAY=MO");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 29), iter.next()));
	}

	@Test
	@DisplayName("every 2 days on Monday and Wednesday")
	void test_Daily_every_2_days_on_Mon_Wed_3_times() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;INTERVAL=2;count=3;BYDAY=MO,WE");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 3), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), iter.next()));
	}

	@Test
	@DisplayName("every 3 days on Monday and Wednesday")
	void test_Daily_every_3_days_on_Mon_Wed_3_times() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;INTERVAL=3;count=3;BYDAY=MO,WE");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 10), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 22), iter.next()));
	}

	@Test
	@DisplayName("every day in January")
	void test_Daily_everyday_in_January() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;BYMONTH=1;count=3");
		LocalDate start = LocalDate.of(2018, 1, 30);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 1), iter.next()));
	}

	@Test
	@DisplayName("every day in January on Monday")
	void test_Daily_everyday_in_January_on_Monday() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;BYMONTH=1;BYDAY=MO;count=3");
		LocalDate start = LocalDate.of(2018, 1, 20);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 22), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 29), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 7), iter.next()));
	}

	@Test
	@DisplayName("everyday in February on Monday the 29th")
	void test_Daily_everyday_in_February_on_Monday_the_29th() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;BYMONTH=2;BYMONTHDAY=29;BYDAYa=MO;count=3");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2020, 2, 29), iter.next()),
				() -> assertEquals(LocalDate.of(2024, 2, 29), iter.next()),
				() -> assertEquals(LocalDate.of(2028, 2, 29), iter.next()));
	}

	@Test
	@DisplayName("every 2 days in January on Monday")
	void test_Daily_every_2_days_in_January_on_Monday() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;BYMONTH=1;BYDAY=MO;INTERVAL=2;count=3");
		LocalDate start = LocalDate.of(2018, 1, 15);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 29), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 14), iter.next()));
	}
}
