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
class MonthlyRecurrenceTest {

	@Test
	@DisplayName("every month(start from the first day of month)")
	void test_Monthly_every_month_start_from_first_day_3_times() {
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, "RRULE:FREQ=MONTHLY;COUNT=3");
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 1), iter.next()));
	}

	@Test
	@DisplayName("every month (start from max day of month)")
	void test_Monthly_every_month_start_from_max_day() {
		LocalDate start = LocalDate.of(2018, 1, 31);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, "RRULE:FREQ=MONTHLY;COUNT=3");
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 5, 31), iter.next()));
	}

	@Test
	@DisplayName("every month on Sunday")
	void test_Monthly_every_month_on_Mon() {
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, "RRULE:FREQ=MONTHLY;BYDAY=SU;COUNT=5");
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 7), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 14), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 21), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 4), iter.next()));
	}

	@Test
	@DisplayName("every month on Weekend(Sat, Sun)")
	void test_Monthly_every_month_on_Weekend() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYDAY=SA,SU;COUNT=5");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 6), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 7), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 13), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 14), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 20), iter.next()));
	}

	@Test
	@DisplayName("every month on Weekdays(Mon to Fri)")
	void test_Monthly_every_month_on_Weekdays() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;COUNT=5");
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
				() -> assertEquals(LocalDate.of(2018, 1, 2), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 3), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 4), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 5), iter.next()));
	}

	@Test
	@DisplayName("every February on Weekdays(Mon to Fri)")
	void test_Monthly_every_January_on_Weekdays() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYMONTH=2;COUNT=5");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		// 2018-02-01 is Thursday
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 2), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 5), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 6), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 7), iter.next()));
	}

	@Test
	@DisplayName("every month on the 31st")
	void test_Monthly_every_Month_on_the_31st() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=31;COUNT=5");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		// 2018-01-01 is Tuesday
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 5, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 7, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 8, 31), iter.next()));
	}

	@Test
	@DisplayName("every month on the last day")
	void test_Monthly_every_Month_on_the_last_day() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=-1;COUNT=3");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		// 2018-01-01 is Tuesday
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 31), iter.next()));
	}

	@Test
	@DisplayName("event month on the last 31th day")
	void test_Monthly_every_Month_on_the_last_31th_day() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=-31;COUNT=3");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		// 2018-01-01 is Tuesday
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 5, 1), iter.next()));
	}

	@Test
	@DisplayName("every 2 months on the last 15th and the last day")
	void test_Monthly_every_2_Months_on_the_last_15th_and_the_last_day() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;INTERVAL=2;BYMONTHDAY=-1,-15;COUNT=4");
		LocalDate start = LocalDate.of(2018, 2, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		// 2018-01-01 is Tuesday
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(4, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 2, 14), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 4, 16), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 4, 30), iter.next()));
	}

	@Test
	@DisplayName("every January on the 1st and 31st")
	void test_Monthly_every_January_on_the_1st_and_31st() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTH=1;BYMONTHDAY=31,1;COUNT=3");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		// 2018-01-01 is Tuesday
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 1, 1), iter.next()));
	}

	@Test
	@DisplayName("every Month on the 1st and the last 15th")
	void test_Monthly_every_January_on_the_1st_and_the_last_15th() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1,-15;COUNT=5");
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
				() -> assertEquals(LocalDate.of(2018, 1, 17), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 14), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 1), iter.next()));
	}

	@Test
	@DisplayName("every month on 1st Monday")
	void test_Monthly_every_month_on_1st_Monday() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;INTERVAL=1;BYDAY=1MO;COUNT=5");
		LocalDate start = LocalDate.of(2019, 6, 17);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2019, 7, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 8, 5), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 9, 2), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 10, 7), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 11, 4), iter.next()));
	}

	@Test
	@DisplayName("every month on the second to last Monday")
	void test_Monthly_every_month_on_the_second_to_last_Monday() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYDAY=-2MO;COUNT=3");
		LocalDate start = LocalDate.of(2020, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(3, recurDates.size()),
				() -> assertEquals(LocalDate.of(2020, 1, 20), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 2, 17), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 3, 23), iter.next()));
	}

	@Test
	@DisplayName("every 2 months on every Tuesday")
	void test_Monthly_every_2_months_on_every_Tuesday() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;INTERVAL=2;BYDAY=TU;COUNT=8");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(8, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 2), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 9), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 16), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 23), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 1, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 6), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 13), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 20), iter.next()));
	}

	@Test
	@DisplayName("every 2 months")
	void test_Monthly_every_2_months() {
		LocalDate start = LocalDate.of(2018, 12, 31);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, "RRULE:FREQ=MONTHLY;INTERVAL=2;COUNT=5");
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 12, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 8, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 10, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 12, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 8, 31), iter.next()));
	}

	@Test
	@DisplayName("every February on the 29th")
	void test_Monthly_every_February_on_the_29th() {
		RecurrenceRule rule = RecurrenceRule
				.getInstance("RRULE:FREQ=MONTHLY;INTERVAL=1;BYMONTH=2;BYMONTHDAY=29;COUNT=3");
		LocalDate start = LocalDate.of(2018, 12, 31);
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
	@DisplayName("every month on 1st Monday and last Friday")
	void test_Monthly_every_month_on_1st_Monday_and_last_Friday() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;INTERVAL=1;BYDAY=1MO,-1FR;COUNT=5");
		LocalDate start = LocalDate.of(2019, 6, 28);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2019, 6, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 7, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 7, 26), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 8, 5), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 8, 30), iter.next()));
	}

	@Test
	@DisplayName("every 2 months on 1st Sunday and last Monday")
	void test_Monthly_every_2_months_on_1st_Sunday_and_last_Monday() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:INTERVAL=2;FREQ=MONTHLY;BYDAY=+1SU,-1MO;COUNT=5");
		LocalDate start = LocalDate.of(2019, 6, 28);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2019, 8, 4), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 8, 26), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 10, 6), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 10, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2019, 12, 1), iter.next()));
	}

	@Test
	@DisplayName("every month on Monday the 31st")
	void test_Monthly_every_month_on_Monday_the_31st() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYDAY=MO;BYMONTHDAY=31;COUNT=5");
		LocalDate start = LocalDate.of(2018, 1, 1);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 12, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2020, 8, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2021, 5, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2022, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2022, 10, 31), iter.next()));
	}

	@Test
	@DisplayName("every month on the first and last day of the month")
	void test_Monthly_every_month_on_the_first_and_last_day() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYMONTHDAY=1,-1;COUNT=5");
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
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 1), iter.next()));
	}

	@Test
	@DisplayName("every 18 months on the 10th thru 15th of the month")
	void test_Monthly_every_18_months_on_the_10th_thru_15th_of_the_month() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;INTERVAL=18;BYMONTHDAY=10,11,12,13,14,15;COUNT=5");
		LocalDate start = LocalDate.of(1997, 9, 13);
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(1997, 9, 13), iter.next()),
				() -> assertEquals(LocalDate.of(1997, 9, 14), iter.next()),
				() -> assertEquals(LocalDate.of(1997, 9, 15), iter.next()),
				() -> assertEquals(LocalDate.of(1999, 3, 10), iter.next()),
				() -> assertEquals(LocalDate.of(1999, 3, 11), iter.next()));
	}
	
	@Test
	@DisplayName("the first work day of the month")
	void test_the_first_work_day_of_the_month() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=1;COUNT=5");
		LocalDate start = LocalDate.of(2018, 1, 1);//2018-01-01 is Monday
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 1), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 4, 2), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 5, 1), iter.next()));
	}

	@Test
	@DisplayName("the last work day of the month")
	void test_the_last_work_day_of_the_month() {
		RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1;COUNT=5");
		LocalDate start = LocalDate.of(2018, 1, 1);//2018-01-01 is Monday
		RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
		List<LocalDate> recurDates = new ArrayList<>();
		while (recurrence.hasNext()) {
			LocalDate date = recurrence.next();
			recurDates.add(date);
		}
		Iterator<LocalDate> iter = recurDates.iterator();
		assertAll(() -> assertEquals(5, recurDates.size()),
				() -> assertEquals(LocalDate.of(2018, 1, 31), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 2, 28), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 3, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 4, 30), iter.next()),
				() -> assertEquals(LocalDate.of(2018, 5, 31), iter.next()));
	}
}
