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

public class MonthValidator implements Validator {
	private static final String DELIMITER = ",";
	private boolean[] validMonths = new boolean[13];// length is 13, because month-of-year field from 1 to 12.

	public static MonthValidator createInstance(String rule) {
		MonthValidator vaidator = new MonthValidator();
		String[] monthStrArr = rule.split(DELIMITER);
		try {
			for (String monthStr : monthStrArr) {
				vaidator.validMonths[Integer.parseInt(monthStr)] = true;
			}
		} catch (Exception e) {
			throw new RRuleException("Invalid month:" + rule);
		}
		return vaidator;
	}

	@Override
	public boolean isValid(LocalDate date) {
		return validMonths[date.getMonthValue()];
	}

	/**
	 * find the first day of next valid month。
	 * or return the last day of year if no valid day found.
	 * @param currentDate
	 * @return the first day of next valid month。
	 */
	public LocalDate nextClosestValidDate(LocalDate currentDate) {
		int month = currentDate.getMonthValue();
		int interval = 0;
		do {
			if (month == 12) {
				month = 1;
			} else {
				month++;
			}
			interval++;
		} while (!validMonths[month]);
		
		return currentDate.plusMonths(interval).with(TemporalAdjusters.firstDayOfMonth());
	}
}
