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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
/**
 * 
 * Represents an instance of recurrent rule which create base on iCalendar string.
 * <p>
 * It is a subset or partial implementation of <a href="https://tools.ietf.org/html/rfc5545#section-3.3.10">iCalendar Recurrence Rule:</a>
 * <ul>
 * 	<li>It only support the frequency of [DAILY, WEEKLY, MONTHLY]</li>
 * 	<li>It DO NOT support start date and time zone directly</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:raindy.ye@outlook.com">Raindy, Ye</a>
 *
 */
public class RecurrenceRule {
	private static final String EMPTY = "";
	private static final String SPACE = " ";
	private Frequency freq;
	private DayOfWeekValidator dayOfWeekValidator;
	private DayOfMonthValidator dayOfMonthValidator;
	private MonthValidator monthValidator;
	private static final Pattern SEMICOLON = Pattern.compile(";");
	private static final Pattern PARAMETER_PATTERN = Pattern.compile("(\\w+)=([a-zA-Z0-9,+\\-]+)");
	private int count;
	private int interval = 1;// default should be one
	private Date until;
	private TreeSet<Integer> setPos;

	private static final Pattern RRULE_PARTS = Pattern.compile("^RRULE:(?:FREQ|UNTIL|COUNT|INTERVAL|BYDAY|BYMONTHDAY|"
			+ "BYWEEKDAY|BYWEEKNO|BYMONTH|BYSETPOS|WKST|X-[A-Z0-9\\-]+)\\s*=.+", Pattern.CASE_INSENSITIVE);

	private RecurrenceRule() {
		super();
	}

	public static RecurrenceRule getInstance(String icalString) {
		String refineRule = icalString.replaceAll(SPACE, EMPTY);
		if (!RRULE_PARTS.matcher(refineRule).find()) {
			throw new RRuleException("The rule is not valid:" + icalString);
		}
		RecurrenceRule rrule = new RecurrenceRule();
		String paraCharSequence = refineRule.substring(6);// length of "RRULE:"
		String[] paraKeyValuePairs = SEMICOLON.split(paraCharSequence);
		for (String parameter : paraKeyValuePairs) {
			Matcher matcher = PARAMETER_PATTERN.matcher(parameter);
			if (!matcher.find()) {
				throw new RRuleException("The rule is not valid:" + icalString);
			}
			String paraName = matcher.group(1);
			String paraValue = matcher.group(2);
			switch (paraName.toUpperCase()) {
			case "FREQ":
				Optional<Frequency> freq = Stream.of(Frequency.values()).filter(f -> f.name().equalsIgnoreCase(paraValue)).findFirst();
				if (!freq.isPresent()) {
					throw new RRuleException("The recurrent frequency[" + paraValue + "] is invalid, it only support " + Arrays.asList(Frequency.values()) + ":\n" + icalString);
				}
				rrule.setFreq(freq.get());
				break;
			case "COUNT":
				int count = Integer.parseInt(paraValue);
				rrule.setCount(count);
				break;
			case "UNTIL":
				Date until;
				try {
					until = new SimpleDateFormat("yyyyMMdd'T'Hmmss'Z'").parse(paraValue);
				} catch (ParseException e) {
					throw new RRuleException("The recurence until date[" + paraValue + "] is not valid, it does not follow the pattern of \"yyyyMMdd'T'Hmmss'Z'\"");
				}
				rrule.setUntil(until);
				break;
			case "INTERVAL":
				int interval = Integer.parseInt(paraValue);
				rrule.setInterval(interval);
				break;
			case "BYDAY":
				rrule.dayOfWeekValidator = new DayOfWeekValidator(paraValue);
				break;
			case "BYMONTH":
				rrule.monthValidator = MonthValidator.createInstance(paraValue);
				break;
			case "BYMONTHDAY":
				rrule.dayOfMonthValidator = DayOfMonthValidator.createInstance(paraValue);
				break;
			case "BYSETPOS":
				rrule.setPos = new TreeSet<>();
				Stream.of(paraValue.split(",")).forEach(p -> rrule.setPos.add(Integer.parseInt(p)));
				break;
			default:
				break;
			}
		}
		if (rrule.getFreq() == null) {
			throw new RRuleException("The recurrent frequency must be specified:\n" + icalString);
		}
		return rrule;
	}

	/** the frequency of repetition */
	public Frequency getFreq() {
		return this.freq;
	}

	public void setFreq(Frequency freq) {
		this.freq = freq;
	}

	public Date getUntil() {
		return this.until;
	}

	public void setUntil(Date until) {
		this.until = until;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getInterval() {
		return this.interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public TreeSet<Integer> getSetPos() {
		return setPos;
	}

	public DayOfWeekValidator getDayOfWeekValidator() {
		return dayOfWeekValidator;
	}

	public MonthValidator getMonthValidator() {
		return monthValidator;
	}

	public DayOfMonthValidator getDayOfMonthValidator() {
		return dayOfMonthValidator;
	}
}
