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
import java.time.temporal.ChronoUnit;

class DailyRecurrenceGenerator implements RecurrenceGenerator {
	private int interval = 1;
	private LocalDate dateCursor;
	private DayOfWeekValidator dayOfWeekValidator;
	private DayOfMonthValidator dayOfMonthValidator;
	private MonthValidator monthValidator;

	DailyRecurrenceGenerator(RecurrenceRule rule) {
		this.dayOfWeekValidator = rule.getDayOfWeekValidator();
		this.dayOfMonthValidator = rule.getDayOfMonthValidator();
		this.monthValidator = rule.getMonthValidator();
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
		if (!isMonthValid()) {// jump over the invalid months fast
			LocalDate closestValidDate = monthValidator.nextClosestValidDate(dateCursor);
			moveCloseTo(closestValidDate);
			return;
		}

		if (!isDayOfMonthValid()) {
			LocalDate closestValidDate = dayOfMonthValidator.nextClosestValidDate(dateCursor);
			moveCloseTo(closestValidDate);
			return;
		}
		
		if (!isDayOfWeekValid()) {
			LocalDate closestValidDate = dayOfWeekValidator.nextClosestValidDate(dateCursor);
			moveCloseTo(closestValidDate);
			return;
		}
		
		dateCursor = dateCursor.plusDays(interval);
	}

	private void moveCloseTo(LocalDate closestValidDate) {
		if (closestValidDate.isEqual(dateCursor)) {
			dateCursor = dateCursor.plusDays(interval);
			return;
		}
		if (interval > 1) {
			int intervalDays = (int)ChronoUnit.DAYS.between(dateCursor, closestValidDate);
			int remainder = intervalDays % interval;
			dateCursor = closestValidDate.plusDays(interval - remainder);
		} else {
			dateCursor = closestValidDate;
		}
	}

	private boolean isValid() {
		return isDayOfWeekValid() && isDayOfMonthValid() && isMonthValid();
	}

	private boolean isDayOfWeekValid() {
		return dayOfWeekValidator == null || dayOfWeekValidator.isValid(dateCursor);
	}

	private boolean isDayOfMonthValid() {
		return this.dayOfMonthValidator == null || this.dayOfMonthValidator.isValid(dateCursor);
	}

	private boolean isMonthValid() {
		return monthValidator == null || monthValidator.isValid(dateCursor);
	}

	@Override
	public void setStartDate(LocalDate startDate) {
		this.dateCursor = startDate;
	}

	@Override
	public void setInterval(int interval) {
		this.interval = interval;
	}
}
