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
class WeeklyRecurrenceTest {

	@Test
	@DisplayName("every week")
	void test_Weekly_every_week() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;count=5;");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 8), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 22), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 29), iter.next()));
	}

	@Test
	@DisplayName("every 2 weeks")
	void test_Weekly_every_2_weeks() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;INTERVAL=2;count=5;");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 29), iter.next()));
	}

	@Test
	@DisplayName("every week in February")
	void test_Weekly_every_week_in_February() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;COUNT=5;BYMONTH=2");
		LocalDate start = LocalDate.of(2020, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2020, 2, 5), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 2, 12), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 2, 19), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 2, 26), iter.next()),
				() -> assertEquals(LocalDate.of(2021, 2, 3), iter.next()));
	}

	@Test
	@DisplayName("every 2 weeks in January")
	void test_Weekly_every_2_weeks_in_February() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;COUNT=5;INTERVAL=2;BYMONTH=1");
		LocalDate start = LocalDate.of(2020, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2020, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 1, 15), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 1, 29), iter.next()),
				() -> assertEquals(LocalDate.of(2021, 1, 13), iter.next()),
				() -> assertEquals(LocalDate.of(2021, 1, 27), iter.next()));
	}

	@Test
	@DisplayName("every week in February on the 1st")
	void test_Weekly_every_week_in_February_on_the_1st() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;COUNT=5;BYMONTH=2;BYMONTHDAY=1");
		LocalDate start = LocalDate.of(2020, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2020, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2021, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2022, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2023, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2024, 2, 1), iter.next()));
	}

	@Test
	@DisplayName("every week in January on weekends")
	void test_Weekly_every_week_in_January_on_weekends() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;COUNT=5;BYMONTH=1;BYDAY=SA,SU");
		LocalDate start = LocalDate.of(2018, 1, 25);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 27), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 5), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 6), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 12), iter.next()));
	}

	@Test
	@DisplayName("every 2 weeks in January on weekdays")
	void test_Weekly_every_2_weeks_in_January_on_weekdays() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;COUNT=5;BYMONTH=1;BYDAY=MO,TU,WE,TH,FR;INTERVAL=2");
		LocalDate start = LocalDate.of(2018, 1, 19);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 19), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 29), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 1), iter.next()));
	}

	@Test
	@DisplayName("every week on the 30th")
	void test_Weekly_every_week_on_the_30th() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;COUNT=3;BYMONTHDAY=30");
		LocalDate start = LocalDate.of(2020, 1, 30);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2020, 1, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 3, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 4, 30), iter.next()));
	}

	@Test
	@DisplayName("every 2 weeks on the 30th")
	void test_Weekly_every_2_weeks_on_the_30th() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;INTERVAL=2;COUNT=3;BYMONTHDAY=30");
		LocalDate start = LocalDate.of(2020, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2020, 1, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 6, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 7, 30), iter.next()));
	}

	@Test
	@DisplayName("every week on Monday and Wednesday")
	void test_Weekly_every_week_on_Mon_Wed() {
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, "RRULE:FREQ=WEEKLY;BYDAY=MO,WE");
		assertAll(() -> assertEquals(LocalDate.of(2018, 1, 1), recurrence.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 3), recurrence.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 8), recurrence.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 10), recurrence.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), recurrence.next()));
	}

	@Test
	@DisplayName("every 2 weeks on Monday and Sunday ")
	void test_Weekly_every_2_weeks_on_Mon_Sun_10_times() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;BYDAY=MO,SU;INTERVAL=2;count=10");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(10, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 7), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 21), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 29), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 04), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 12), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 18), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 26), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 04), iter.next()));
	}

	@Test
	@DisplayName("every 2 weeks days ")
	void test_Weekly_every_2_weeks_days() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=WEEKLY;BYDAY=MO,TU,WE,TH,FR,SA,SU;INTERVAL=2;count=5");
		LocalDate start = LocalDate.of(2018, 1, 5);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 5), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 6), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 7), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 15), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 16), iter.next()));
	}
}
