package seedu.clinicio.model.appointment;

//@@author gingivitiss

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;
import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

/**
 *  Represents the date in dd/mm/yyyy format to be used in appointment.
 */
public class Date {
    public static final String MESSAGE_DAY_CONSTRAINTS =
            "The day should be numeric and not exceed the number of days in specified month."
                    + "It should not be blank.";
    public static final String MESSAGE_MONTH_CONSTRAINTS =
            "The month should be numeric (1 starting in Jan) and not exceed the number of months in a year."
                    + "It should not be blank.";
    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "The month should be numeric and it should not be blank.";
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "The date should be in dd mm yyyy format and it should not be blank.";
    public static final String YEAR_VALIDATION_REGEX = "\\d{4}";
    public static final String DATE_VALIDATION_REGEX = "(\\d{1,2})(\\s+)(\\d{1,2})(\\s+)(\\d{4})";

    private final int day;
    private final int month;
    private final int year;

    /**
     * Constructs a {@code Date}.
     * @param day A valid day.
     * @param month A valid month.
     * @param year A valid year.
     */
    public Date(int day, int month, int year) {
        requireAllNonNull(day, month, year);
        checkArgument(isValidDay(day, month, year), MESSAGE_DAY_CONSTRAINTS);
        checkArgument(isValidMonth(month), MESSAGE_MONTH_CONSTRAINTS);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Constructs a {@code Date} from String.
     * @param date A valid date.
     */
    public static Date newDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        String[] splitDate = date.split("\\s+");
        return new Date(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]));
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public static boolean isValidDate(String string) {
        return string.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Returns true if such a day exists.
     * @param day The day to validate.
     * @param month Determines validity of day.
     * @param year Determines validity of day.
     * @return Validity of day.
     */
    public static boolean isValidDay(int day, int month, int year) {
        if (day <= 0 || day > 31) {
            return false;
        }
        if (month == 2) {
            return isValidFebDay(day, year);
        }
        if (month % 2 == 1 && month >= 9 && day > 30) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if it is a valid February date.
     * @param day The day to validate.
     * @param year Determines validity of day.
     */
    public static boolean isValidFebDay(int day, int year) {
        return (day <= 29 && isLeapYear(year)) || (day <= 28 && !isLeapYear(year));
    }

    /**
     * Returns true if it is a Gregorian leap year.
     * @param year The year to check if leap year.
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * Returns true if such a month exists.
     * @param month The month to validate.
     * @return Validity of month.
     */
    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * Returns true if such a year exists.
     * @param year The year to validate.
     * @return Validity of month.
     */
    public static boolean isValidYear(int year) {
        String string = String.valueOf(year);
        return string.matches(YEAR_VALIDATION_REGEX);
    }

    /**
     * Checks if this {@code Date} falls in the current real life week.
     * @return {@code true} if date is after or equals to current week's Monday AND before next week's monday.
     * {@code false} otherwise.
     * @@author arsalanc-v2
     */
    public boolean isCurrentWeek() {
        LocalDate today = LocalDate.now();
        // get this week's Monday's date
        LocalDate currentWeekMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // get next week's Monday's date
        LocalDate nextWeekMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate targetDate = LocalDate.of(this.year, this.month, this.day);

        return (targetDate.isEqual(currentWeekMonday) || targetDate.isAfter(currentWeekMonday))
            && targetDate.isBefore(nextWeekMonday);
    }

    /**
     * Returns a simple string
     * @param other
     * @return
     */

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Date otherDate = (Date) other;
        return (otherDate.getDay() == getDay())
                && (otherDate.getMonth() == getMonth())
                && (otherDate.getYear() == getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear());
        return builder.toString();
    }
}
