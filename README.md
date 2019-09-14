# Calendar recurrence for Java

[![Build Status](https://travis-ci.org/Raindy-Ye/recurrence-rule.svg?branch=master)](https://travis-ci.org/Raindy-Ye/recurrence-rule)
[![Coverage Status](https://coveralls.io/repos/github/Raindy-Ye/recurrence-rule/badge.svg?branch=master)](https://coveralls.io/github/Raindy-Ye/recurrence-rule?branch=master)
[![License](https://img.shields.io/badge/license-MIT-brightgreen.svg?style=flat-square)](LICENSE)

The recurrence-rule is a subset or partial implementation based on [iCalendar Recurrence Rule](https://tools.ietf.org/html/rfc5545#section-3.3.10). It provides most of the useful repeating pattern for recurring events, to-dos, journal entries etc.
# Usage:
recurrence-rule is easy to get started.<br>
### Firstly, create an instance of repeating rule base on the syntax of iCalendar Recurrence Rule:
```java  
RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;COUNT=2");
```
### Secondly, create an repeating calendar base on the rule and specify the repeating start date.
```java  
LocalDate start = LocalDate.of(2018, 1, 1);
RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
```
### Finally, use the recurrence calendar to iterate the recurring dates.
```java  
while (recurrence.hasNext()) {
    LocalDate date = recurrence.next();
    System.out.println(date);
}
```
### Put together
```java
RecurrenceRule rule = RecurrenceRule.getInstance("RRULE:FREQ=DAILY;COUNT=3");
LocalDate start = LocalDate.of(2018, 1, 1);
RecurrenceCalendar recurrence = RecurrenceCalendar.getInstance(start, rule);
while (recurrence.hasNext()) {
    LocalDate date = recurrence.next();
    System.out.println(date);//2018-01-01,2018-01-02,2018-01-03
}
```
NOTES: Unlike the [iCalendar Recurrence Rule](https://tools.ietf.org/html/rfc5545#section-3.3.10), recurrence-rule only implement the syntax of describing how an event recurs and when the recurrence will be ended. The repeating start date doesn't specify in the recurring rule itself, instead the start date need to be provied for a recurrence calendar instance.

# Rule Features:

<table>
    <tr>
        <td>Rule Attributes</font></td>
        <td>Value</td>
        <td>Description</td>
        <td align="center">Support</td>
    </tr>
    <tr>
        <td rowspan=7>FREQ</td>
        <td>YEARLY</td>
        <td>Repeating events based on an interval of a year or more.</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>MONTHLY</td>
        <td>Repeating events based on an interval of a month or more</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>WEEKLY</td>
        <td>Repeating events based on an interval of a week or more</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>DAILY</td>
        <td>Repeating events based on an interval of a day or more;</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>HOURLY</td>
        <td>Repeating events based on an interval of an hour or more</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>MINUTELY</td>
        <td>Repeating events based on an interval of a minute or more</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>SECONDLY</td>
        <td>Repeating events based on an interval of a second or more</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>INTERVAL</td>
        <td>Postitive Integer</td>
        <td>It works with the FREQ component part, representing at which intervals the recurrence rule repeats. For example, within a DAILY rule, a value of "3" means every 3 days.</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>UNTIL</td>
        <td>DATE Value</td>
        <td>Defines a DATE value that bounds the recurrence rule in an inclusive manner. If "COUNT" and "UNTIL" both are not present, the event is considered to repeat forever</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>COUNT</td>
        <td>Positive Integer</td>
        <td>Defines the number of occurrences at which to range-bound the recurrence.
        If "COUNT" and "UNTIL" both are not present, the event is considered to repeat forever</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>BYMONTH</td>
        <td>1 to 12</td>
        <td>Specifies a COMMA-separated list of months of the year.</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>BYYEARDAY</td>
        <td>list of 1 to 366 or -366 to -1</td>
        <td>Specifies a COMMA-separated list of days of the year. For example: 1, -1 represents the first and last day of the year. It MUST NOT be pecified when the FREQ rule part is set to DAILY, WEEKLY, or MONTHLY.</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>BYMONTHDAY</td>
        <td> 1 to 31 or -31 to -1</td>
        <td>Specifies a COMMA-separated list of days of the month. For example, -5 represents the 5th to the last day of the month.</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>BYDAY</td>
        <td>MO,TU,WE,TH,FR,SA,SU</td>
        <td>Specifies a COMMA-separated list of days of the week</td>
        <td align="center">YES</td>
    </tr>
    <tr>
        <td>BYWEEKNO</td>
        <td>1 to 53 or -53 to -1</td>
        <td>Specifies a COMMA-separated list of ordinals specifying weeks of the year.</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>BYHOUR</td>
        <td>0 to 23</td>
        <td>Specifies a COMMA-separated list of hours of the day.</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>BYMINUTE</td>
        <td>0 to 59</td>
        <td>Specifies a COMMA-separated list of minutes within an hour.</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>BYSECOND</td>
        <td>0 to 59</td>
        <td>Specifies a COMMA-separated list of seconds within a minute.</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>BYSETPOS</td>
        <td>Integer</td>
        <td>Specifies a COMMA-separated list of values that corresponds to the nth occurrence within the set of recurrence instances specified by the rule.</td>
        <td align="center">NO</td>
    </tr>
    <tr>
        <td>WKST</td>
        <td>MO,TU,WE,TH,FR,SA,SU</td>
        <td>Specifies the day on which the workweek starts. The default value is MO.</td>
        <td align="center">NO</td>
    </tr>
</table>


# Examples of recurring rules:
## Recurring frequence by daily
| Rule        | Description  |
| :------------------ |:---------------|
| RRULE:FREQ=DAILY    | everyday |
| RRULE:FREQ=DAILY;INTERVAL=2;COUNT=3 | every 2 days for for 3 times|
| RRULE:FREQ=DAILY;BYDAY=SU| every Sunday|
| RRULE:FREQ=DAILY;BYDAY=MO,TU,WE,TH,FR | every weekday|
| RRULE:FREQ=DAILY;BYDAY=SA,SU | every 3 days on Saturday, Sunday|
| RRULE:FREQ=DAILY;BYMONTH=1;COUNT=5| everyday in January for 5 times|
| RRULE:FREQ=DAILY;BYMONTH=1;BYDAY=MO| every day in January on Monday|
| RRULE:FREQ=DAILY;BYMONTH=2;BYMONTHDAY=29;BYDAY=MO| everyday in February on Monday the 29th|

## Recurring frequence by weekly
| Rule        | Description  |
| :------------------ |:---------------|
| RRULE:FREQ=WEEKLY   | every week |
| RRULE:FREQ=WEEKLY;BYMONTH=2 | every week in February|
| RRULE:FREQ=WEEKLY;BYMONTH=1;BYDAY=SA,SU| every week in January on weekends|
| RRULE:FREQ=WEEKLY;BYMONTHDAY=30;COUNT=3 | every week on the 30th for 3 times|
| RRULE:FREQ=WEEKLY;BYDAY=MO,WE;INTERVAL=2| every 2 weeks on Monday and Wednesday|

## Recurring frequence by monthly
| Rule        | Description  |
| :------------------ |:---------------|
| RRULE:FREQ=MONTHLY;BYDAY=SU;COUNT=5 | every month on Sunday for 5 times|
| RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR | every month on Weekdays(Mon to Fri)|
| RRULE:FREQ=MONTHLY;BYDAY=MO;BYMONTH=2| every February on Monday|
| RRULE:FREQ=MONTHLY;BYMONTHDAY=31 | every month on the 31st|
| RRULE:FREQ=MONTHLY;BYMONTHDAY=-1| every month on the last day| 
| RRULE:FREQ=MONTHLY;BYDAY=1MO;INTERVAL=2| every 2 months on 1st Monday|
| RRULE:FREQ=MONTHLY;BYDAY=-2MO | every month on the second to last Monday|
| RRULE:FREQ=MONTHLY;BYDAY=+1SU,-1MO| every month on 1st Sunday and last Monday|

# Requirements
The library has no dependencies. Java 8 or higher is required.