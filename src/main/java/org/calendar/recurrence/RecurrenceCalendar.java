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
import java.time.ZoneId;
import java.util.NoSuchElementException;
import java.util.Optional;
/**
 * 
 * It is used to get the recurrence date base on the {@link RecurrenceRule}
 *  * 
 * @author <a href="mailto:raindy.ye@outlook.com">Raindy, Ye</a>
 * 
 * @see RecurrenceRule
 *
 */
public class RecurrenceCalendar {
	private RecurrenceRule rule;
	private int cursor;
	private LocalDate next;
	private LocalDate dateCursor;
	private LocalDate end;
	private boolean generated;
	private RecurrenceGenerator generator;
	private InfiniteLoopDetector infiniteLoopDetector = InfiniteLoopDetector.create(1000, 10000);
	
	/**
	 * To create an instance of RecurrenceCalendar base on iCalendar string rules and specify the recurrence start date.
	 * <p>Notes: the recurrence start date, is not the first recurrence instance, unless it does fit in the specified rules.</p>
	 * @param startLocalDate the recurrence start date
	 * @param icalString the rules of recurrence
	 * @return an instance of RecurrenceCalendar 
	 */
	public static RecurrenceCalendar getInstance(LocalDate startLocalDate, String icalString) {
		RecurrenceRule rule = RecurrenceRule.getInstance(icalString);
		return getInstance(startLocalDate, rule);
	}
	
	/**
	 * To create an instance of RecurrenceCalendar base on the instance of {@link RecurrenceRule} and specify the recurrence start date.
	 * <p>Notes: the recurrence start date, is not the first recurrence instance, unless it does fit in the specified rules.</p>
	 * @param startLocalDate the recurrence start date
	 * @param rule an instance of RecurrenceRule
	 * @return an instance of RecurrenceCalendar 
	 */
	public static RecurrenceCalendar getInstance(LocalDate startLocalDate, RecurrenceRule rule) {
		RecurrenceCalendar instance = new RecurrenceCalendar();
		instance.rule = rule;
		instance.dateCursor = startLocalDate;
		instance.end = Optional.ofNullable(rule.getUntil())
				.map(until -> until.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()).orElse(null);
		switch (rule.getFreq()) {
		case DAILY:
			instance.generator = new DailyRecurrenceGenerator(rule);
			break;
		case WEEKLY:
			instance.generator =new WeeklyRecurrenceGenerator(rule);
			break;
		default:
			MonthlyRecurrenceGenerator monthlyGenerator = new MonthlyRecurrenceGenerator(instance.dateCursor, rule);
			instance.generator = monthlyGenerator;
			break;
		}
		instance.generator.setInterval(rule.getInterval());
		instance.generator.setStartDate(startLocalDate);
		return instance;
	}
	
	/**
     * Returns {@code true} if it has more recurrent dates.
     * (In other words, returns {@code true} if {@link #next} would
     * return an {@code LocalDate} rather than throwing an exception.)
     *
     * @return {@code true} if it has more recurrent dates
     */
	public boolean hasNext() {
		infiniteLoopDetector.start();
		if (generated) {
			return true;
		}
		if (isCountExceeded()) {
			return false;
		}
		dateCursor = generator.next();
		if (isExceededEndDate(dateCursor)) {
			this.next = null;
			return false;
		} else {
			this.next = dateCursor;
			cursor++;
		}
		this.generated = true;
		infiniteLoopDetector.throwExcpetionIfDetected("Infinite loop detected!");
		return true;
	}

	/**
	 * Returns next recurrent LocalDate。
	 * 
	 * @return the next recurrent LocalDate
     * @throws NoSuchElementException if it has no more recurrent dates
     */
	public LocalDate next() {
		if (generated) {
			this.generated = false;
			return this.next;
		} else if (this.hasNext()) {
			this.generated = false;
			return this.next;
		} else {
			throw new NoSuchElementException("No more recurrence!"); 
		}
	}

	private boolean isCountExceeded() {
		return rule.getCount() > 0 && cursor >= rule.getCount();
	}

	private boolean isExceededEndDate(LocalDate date) {
		return Optional.ofNullable(end).map(end -> date.isAfter(end)).orElse(false);
	}
}
