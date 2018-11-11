package seedu.clinicio.model.analytics.util;

import static java.lang.Math.toIntExact;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.appointment.Date;

//@@author arsalanc-v2

/**
 * Contains utility methods for computing occurrences and retrieving date values.
 * Lists of tuples are used instead of maps to preserve ordering.
 */
public class DateUtil {

    /**
     * @return a list of the number of dates that occur in various time periods.
     */
    public static List<Integer> todayWeekMonthYear(List<Date> dates) {
        return Arrays.asList(todayCount(dates), currentWeekCount(dates), currentMonthCount(dates),
            currentYearCount(dates));
    }

    /**
     * @return the number of dates that match todayCount's real life date.
     */
    public static int todayCount(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).isEqual(todayDate))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates occurring in the current real life week.
     */
    public static int currentWeekCount(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> isCurrentWeek(date))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates occurring in the current real life month.
     */
    public static int currentMonthCount(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).getMonth().equals(todayDate.getMonth()))
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates occurring in the current real life year.
     */
    public static int currentYearCount(List<Date> dates) {
        LocalDate todayDate = LocalDate.now();
        long numberOfMatchingDates = dates.stream()
            .filter(date -> getLocalDate(date).getYear() == todayDate.getYear())
            .count();

        return toIntExact(numberOfMatchingDates);
    }

    /**
     * @return the number of dates that match each of the 7 days, in totality.
     */
    public static List<Tuple<String, Integer>> eachDayCount(List<Date> dates) {
        List<Tuple<String, Integer>> daysCount = new ArrayList<>();
        for (DayOfWeek dayOfWeek : getDaysOfWeek()) {
            long count = dates.stream()
                .map(date -> getDayFromDate(date))
                .filter(day-> dayOfWeek.equals(day))
                .count();

            Tuple<String, Integer> tuple = new Tuple<>(dayOfWeek.name(), toIntExact(count));
        }
        return daysCount;
    }

    /**
     * @return the number of dates that match each date in the current week.
     * Returns {@code Date} for flexibility as the day of the week can be derived from it.
     */
    public static List<Tuple<Date, Integer>> eachDateOfCurrentWeekCount(List<Date> dates) {
        List<Tuple<Date, Integer>> currentWeekCount = new ArrayList<>();
        for (Date currentWeekDate : getCurrentWeekDates()) {
            int dateCount = 0;
            for (Date date : dates) {
                if (currentWeekDate.equals(date)) {
                    dateCount++;
                }
            }
            Tuple<Date, Integer> tuple = new Tuple<>(currentWeekDate, dateCount);
            currentWeekCount.add(tuple);
        }

        return currentWeekCount;
    }

    /**
     * @return the number of dates that match each date in the next week, in order.
     * Returns {@code Date} for flexibility as the day of the week can be derived from it.
     * Useful for categorical plots.
     */
    public static List<Tuple<Date, Integer>> eachDateOfNextWeekCount(List<Date> dates) {
        List<Tuple<Date, Integer>> nextWeekCount = new ArrayList<>();
        for (Date nextWeekDate : getNextWeekDates()) {
            int dateCount = 0;
            for (Date date : dates) {
                if (nextWeekDate.equals(date)) {
                    dateCount++;
                }
            }
            Tuple<Date, Integer> tuple = new Tuple<>(nextWeekDate, dateCount);
            nextWeekCount.add(tuple);
        }

        return nextWeekCount;
    }

    /**
     * @return A Tuple of the number of dates that occur for each month of the current year, in order of
     * earliest to latest date.
     * Useful for categorical plots over the months of a year.
     */
    public static List<Tuple<String, Integer>> eachMonthOfCurrentYearCount(List<Date> dates) {
        List<Tuple<String, Integer>> monthCounts = new ArrayList<>();
        for (Month month : getMonthsOfYear()) {
            long count = dates.stream()
                .map(date -> getLocalDate(date))
                .filter(localDate -> localDate.getMonth().equals(month))
                .count();

            monthCounts.add(new Tuple<String, Integer>(month.name(), toIntExact(count)));
        }

        return monthCounts;
    }

    /**
     * @return the number of dates that match each date in the current year.
     * Useful for continuous plots over the course of a year.
     */
    public static List<Tuple<Date, Integer>> eachDateOfCurrentYearCount(List<Date> dates) {
        List<Tuple<Date, Integer>> yearDateCounts = new ArrayList<>();
        for (Date date : getCurrentYearDates()) {
            int dateCount = 0;
            for (Date providedDate : dates) {
                if (date.equals(providedDate)) {
                    dateCount++;
                }
            }
            Tuple<Date, Integer> tuple = new Tuple<>(date, dateCount);
            yearDateCounts.add(tuple);
        }

        return yearDateCounts;
    }

    /**
     * Checks if this {@code Date} falls in the current real life week.
     * @return {@code true} if date is after or equals to current week's Monday AND before next week's monday.
     * {@code false} otherwise.
     */
    public static boolean isCurrentWeek(Date toCheck) {
        LocalDate today = LocalDate.now();
        // get this week's Monday's date
        LocalDate currentWeekMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // get next week's Monday's date
        LocalDate nextWeekMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate target = getLocalDate(toCheck);

        return (target.isEqual(currentWeekMonday) || target.isAfter(currentWeekMonday))
            && target.isBefore(nextWeekMonday);
    }

    /**
     * Checks if this {@code Date} falls in the real life week after the current one.
     * @return {@code true} if date is after or equals to next week's Monday AND before next next week's monday.
     * {@code false} otherwise.
     */
    public static boolean isNextWeek(Date toCheck) {
        LocalDate today = LocalDate.now();
        // get next week's Monday's date
        LocalDate nextWeekMonday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        // get next next week's Monday's date
        LocalDate nextNextWeekMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(
            TemporalAdjusters.next(DayOfWeek.MONDAY)
        );

        LocalDate target = getLocalDate(toCheck);
        return (target.isEqual(nextWeekMonday) || target.isAfter(nextWeekMonday))
            && target.isBefore(nextNextWeekMonday);
    }

    /**
     * @return A list of each date in the current week.
     */
    public static List<Date> getCurrentWeekDates() {
        List<Date> thisWeekDates = new ArrayList<>();
        LocalDate thisMonday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        for (DayOfWeek day : getDaysOfWeek()) {
            LocalDate nextWeekDate = thisMonday.with(TemporalAdjusters.next(day));
            thisWeekDates.add(getDate(nextWeekDate));
        }

        return thisWeekDates;
    }

    /**
     * @return A list of each date in the current year.
     */
    public static List<Date> getCurrentYearDates() {
        LocalDate today = LocalDate.now();
        // iterate from first date of the year to the last
        LocalDate startDate = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastDateOfYear = today.with(TemporalAdjusters.lastDayOfYear());

        List<Date> currentYearDates = new ArrayList<>();

        // ! .isAfter used instead of .before for inclusivity of the endDate
        while (!startDate.isAfter(lastDateOfYear)) {
            currentYearDates.add(getDate(startDate));
            startDate = startDate.plusDays(1);
        }

        return currentYearDates;
    }

    /**
     * @return a list of the first date of each month in the current year.
     */
    public static List<Date> getFirstDatesOfCurrentYear() {
        LocalDate today = LocalDate.now();
        // iterate from first date of the first month to the first date of the last month
        LocalDate startDate = today.with(TemporalAdjusters.firstDayOfYear());
        LocalDate endDate = today.with(TemporalAdjusters.lastDayOfYear()).with(TemporalAdjusters.firstDayOfMonth());

        List<Date> firstDates = new ArrayList<>();

        // ! .isAfter used instead of .before for inclusivity of the endDate
        while (!startDate.isAfter(endDate)) {
            firstDates.add(getDate(startDate));
            startDate = startDate.with(TemporalAdjusters.firstDayOfNextMonth());
        }

        return firstDates;
    }


    /**
     * @return A list of each date in the next week in order of earliest to latest date.
     */
    public static List<Date> getNextWeekDates() {
        List<Date> nextWeekDates = new ArrayList<>();
        LocalDate nextMonday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        for (DayOfWeek day : getDaysOfWeek()) {
            LocalDate nextWeekDate = nextMonday.with(TemporalAdjusters.nextOrSame(day));
            nextWeekDates.add(getDate(nextWeekDate));
        }

        return nextWeekDates;
    }

    /**
     * @return a list of each {@code DayOfWeek}.
     */
    public static List<DayOfWeek> getDaysOfWeek() {
        return Arrays.asList(DayOfWeek.values());
    }

    /**
     * @return a list of each month.
     */
    public static List<Month> getMonthsOfYear() {
        return Arrays.asList(Month.values());
    }

    /**
     * Get the day of the week enumerated value based on a {@code Date}.
     */
    public static DayOfWeek getDayFromDate(Date date) {
        LocalDate localDate = getLocalDate(date);
        return localDate.getDayOfWeek();
    }

    /**
     * @return A {@code LocalDate} from a {@code Date}.
     */
    public static LocalDate getLocalDate(Date date) {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
    }

    /**
     * @return A {@code Date} from a {@code LocalDate}.
     */
    public static Date getDate(LocalDate localDate) {
        return new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }
}
