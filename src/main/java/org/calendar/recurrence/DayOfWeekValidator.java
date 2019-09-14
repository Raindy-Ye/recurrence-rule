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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author <a href="mailto:raindy.ye@outlook.com">Raindy, Ye</a>
 *
 */
public class DayOfWeekValidator implements Validator {
	private static final String DELIMITER = ",";
	private static final Pattern ORDINAL_DAY_PATTERN = Pattern.compile("^([-+]?\\d)(\\w{2})$");
	private boolean[] validWeekDays;
	private List<OrdinalDayOfWeekInMonth> ordinalDayOfWeeks = new ArrayList<>(0);

	public DayOfWeekValidator(String rule) {
		String[] WeekDayStrArr = rule.split(DELIMITER);
		try {
			for (String weekDayStr : WeekDayStrArr) {
				Matcher ordinalDay = ORDINAL_DAY_PATTERN.matcher(weekDayStr);
				if (ordinalDay.find()) {
					short ordinalNum = Short.parseShort(ordinalDay.group(1));
					DayOfWeek weekDay = getByShortName(ordinalDay.group(2));
					ordinalDayOfWeeks.add(new OrdinalDayOfWeekInMonth(ordinalNum, weekDay));
				} else {
					if (this.validWeekDays == null) {
						// length is 8, as Monday to Sunday is from 1 to 7
						validWeekDays = new boolean[8];
					}
					DayOfWeek weekDay = getByShortName(weekDayStr);
					this.validWeekDays[weekDay.getValue()] = true;
				}
			}
		} catch (Exception e) {
			throw new RRuleException("Invalid day of week[" + rule + "]");
		}
	}
	
	public LocalDate nextClosestValidDate(LocalDate currentDate) {
		int dayOfWeek = currentDate.getDayOfWeek().getValue();
		while(dayOfWeek < 7) {
			dayOfWeek++;
			if (validWeekDays[dayOfWeek]) {
				break;
			}
		}
		int interval = dayOfWeek - currentDate.getDayOfWeek().getValue();
		return currentDate.plusDays(interval);
	}

	private static DayOfWeek getByShortName(String shortName) {
		String abbrName = shortName.toUpperCase();
		for (DayOfWeek day : DayOfWeek.values()) {
			if (day.name().substring(0, 2).equalsIgnoreCase(abbrName)) {
				return day;
			}
		}
		throw new NoSuchElementException();
	}

	@Override
	public boolean isValid(LocalDate date) {
		if (validWeekDays != null) {
			if (this.validWeekDays[date.getDayOfWeek().getValue()]) {
				return true;//just return, no need to check ordinal day of week
			}
		}
		return ordinalDayOfWeeks.stream().anyMatch(ordinalDay -> {
				TemporalAdjuster adjuster = TemporalAdjusters.dayOfWeekInMonth(ordinalDay.getOrdinal(), ordinalDay.getDay());
				LocalDate expectedDate = date.with(adjuster);
				return expectedDate.equals(date);
			});
	}

	class OrdinalDayOfWeekInMonth {
		private short ordinal;
		private DayOfWeek day;

		public OrdinalDayOfWeekInMonth(short ordinal, DayOfWeek day) {
			this.ordinal = ordinal;
			this.day = day;
		}

		public short getOrdinal() {
			return ordinal;
		}

		public DayOfWeek getDay() {
			return day;
		}
	}
}
