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
import java.util.Optional;
import java.util.TreeSet;
import java.util.regex.Pattern;
/**
 * 
 * @author <a href="mailto:raindy.ye@outlook.com">Raindy, Ye</a>
 *
 */
public class DayOfMonthValidator implements Validator {

	private static final String DELIMITER = ",";
	private static final Pattern ORDINAL_DAY_PATTERN = Pattern.compile("^[-+]?0*((?:[12]?\\d)|(?:3[01]))$");
	private boolean[] validDays;// day-of-month, from 1 to 31
	private TreeSet<Integer> ordinalDays = new TreeSet<>();

	public static DayOfMonthValidator createInstance(String rule) {
		DayOfMonthValidator validator = new DayOfMonthValidator();
		String[] dayStrArr = rule.split(DELIMITER);
		for (String dayStr : dayStrArr) {
			String dayTrimed = dayStr.trim();
			if (!ORDINAL_DAY_PATTERN.matcher(dayTrimed).find()) {
				throw new RRuleException("Invalid day of month:" + rule);
			}
			short oridnalDay = Short.parseShort(dayTrimed);
			if (oridnalDay < 0) {
				// reversed order i.e. -1 => the last day of month
				validator.ordinalDays.add(oridnalDay + 1);
			} else {
				if (validator.validDays == null) {
					validator.validDays = new boolean[32];// day-of-month, from 1 to 31
				}
				validator.validDays[oridnalDay] = true;
			}
		}
		return validator;
	}

	@Override
	public boolean isValid(LocalDate date) {
		int dayOfMonth = date.getDayOfMonth();
		if (validDays != null && validDays[dayOfMonth]) {
			return true;
		}
		// The number of last day of the Month.
		// For example:
		// reversedOrder=0 => the last day of the Month
		// reversedOrder=1 => the second of last day of the Month
		LocalDate lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
		return ordinalDays.stream()
				.anyMatch(reversedOrder -> dayOfMonth == lastDayOfMonth.plusDays(reversedOrder).getDayOfMonth());
	}

	/**
	 * find the next valid day until reach to the last day of month of {@code currentDate}.
	 * or return the last day of month if no valid day found.
	 * @param currentDate
	 * @return the next valid day or the last day of month
	 */
	public LocalDate nextClosestValidDate(LocalDate currentDate) {
		int curDayOfMonth = currentDate.getDayOfMonth();
		int dayOfMonth = curDayOfMonth;
		int lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		if (validDays != null) {
			// find the first valid day from (currentDayOfMonth, lastDayOfMonth]
			while(dayOfMonth < lastDayOfMonth) {
				dayOfMonth++;
				if (validDays[dayOfMonth]) {
					break;
				}
			}
		} else {
			//find if exist a valid day more close to current day
			Optional<Integer> reversedDay = ordinalDays.stream().filter(reversed -> (lastDayOfMonth + reversed) > curDayOfMonth).findFirst();
			if (reversedDay.isPresent()) {
				return currentDate.plusDays(lastDayOfMonth + reversedDay.get() - curDayOfMonth);
			}
			return currentDate.withDayOfMonth(lastDayOfMonth);
		}
		int interval = dayOfMonth - curDayOfMonth;

		//find if exist a valid day more close to current day
		Optional<Integer> closest = ordinalDays.stream().filter(reversedOrdinal -> {
			int day = lastDayOfMonth + reversedOrdinal - curDayOfMonth;
			return day > 0 && day < interval;
		}).findFirst();
		
		if (closest.isPresent()) {
			return currentDate.plusDays(lastDayOfMonth + closest.get() - curDayOfMonth);
		}
		return currentDate.plusDays(interval);
	}
}
