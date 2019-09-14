/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.calendar.recurrence;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

class MonthlyRecurrenceGenerator implements RecurrenceGenerator {
	private int interval;
	private LocalDate dateCursor;
	private DayOfWeekValidator weekDayValidator;
	private DayOfMonthValidator dayOfMonthValidator;
	private MonthValidator monthValidator;

	MonthlyRecurrenceGenerator(LocalDate startDate, RecurrenceRule rule) {
		this.dateCursor = startDate;
		this.interval = rule.getInterval();
		this.weekDayValidator = rule.getDayOfWeekValidator();
		this.monthValidator = rule.getMonthValidator();
		this.dayOfMonthValidator = rule.getDayOfMonthValidator();
	}

	public LocalDate next() {
		while (!isValid()) {
			moveCursor();
		}
		LocalDate generatedDate = dateCursor;
		moveCursor();
		return generatedDate;
	}

	private void moveCursor() {

		if (dayOfMonthValidator == null && weekDayValidator == null) {
			// if none of verifier then simply jump by interval until the day-of-month are
			// matched
			int dayOfMonth = dateCursor.getDayOfMonth();
			int numOfMonth = interval;
			LocalDate validDate;
			do {
				validDate = dateCursor.plusMonths(numOfMonth);
				numOfMonth += interval;
			} while (dayOfMonth != validDate.getDayOfMonth());
			dateCursor = validDate;
			return;
		}

		if (!isMonthValid() || isLastDayOfMonth()) {
			// Jump by interval and adjust to the first date of the month
			dateCursor = dateCursor.plusMonths(interval);
			dateCursor = dateCursor.with(TemporalAdjusters.firstDayOfMonth());
			return;
		}

		if (this.dayOfMonthValidator != null) {
			dateCursor = dayOfMonthValidator.nextClosestValidDate(dateCursor);
			return;
		} else {
			dateCursor = dateCursor.plusDays(1);
		}
	}

	private boolean isValid() {
		return isMonthValid() && isMonthDayValid() && isDayOfWeekValid();
	}

	private boolean isDayOfWeekValid() {
		return this.weekDayValidator == null || this.weekDayValidator.isValid(dateCursor);
	}

	private boolean isMonthValid() {
		return monthValidator == null || monthValidator.isValid(dateCursor);
	}

	private boolean isMonthDayValid() {
		return this.dayOfMonthValidator == null || this.dayOfMonthValidator.isValid(dateCursor);
	}

	private boolean isLastDayOfMonth() {
		int lastDayOfMonth = dateCursor.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		return dateCursor.getDayOfMonth() == lastDayOfMonth;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	@Override
	public void setStartDate(LocalDate startDate) {
		this.dateCursor = startDate;
	}
}
