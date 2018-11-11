package seedu.clinicio.model.analytics.util;

//@@author arsalanc-v2

import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.appointment.Time;

/**
 * Contains utility methods for computing occurrences and retrieving time values.
 */
public class TimeUtil {

    /**
     * stores periods of time in a list, each as a tuple with start and end time
     * the 3 hour periods make up the hours in a working clinic day: 9AM - 9PM
     * 9.00AM - 11.59am
     * 12.00PM - 2.59PM
     * 3.00PM - 5.59PM
     * 6.00PM - 9PM
     */
    private static final List<Tuple<Time, Time>> timePeriods = Arrays.asList(
        new Tuple<Time, Time>(new Time(9, 0), new Time(11, 59)),
        new Tuple<Time, Time>(new Time(12, 0), new Time(14, 59)),
        new Tuple<Time, Time>(new Time(15, 0), new Time(17, 59)),
        new Tuple<Time, Time>(new Time(18, 0), new Time(21, 0))
    );

    /**
     * @return the number of occurrences for each time period.
     */
    public static List<Tuple<Tuple<Time, Time>, Integer>> getTimeGroupsCount(List<Time> times) {
        List<Tuple<Tuple<Time, Time>, Integer>> timeGroupsCount = new ArrayList<>();
        for (Tuple<Time, Time> timePeriod : timePeriods) {
            long count = times.stream()
                .filter(time -> isAfterAndBeforeInclusive(time, timePeriod.getKey(), timePeriod.getValue()))
                .count();

            timeGroupsCount.add(new Tuple<Tuple<Time, Time>, Integer>(timePeriod, toIntExact(count)));
        }

        return timeGroupsCount;
    }

    /**
     * Checks if {@code time} occurs before or equal to {@code before} AND occurs after or equal to {@code after}
     */
    public static boolean isAfterAndBeforeInclusive(Time time, Time after, Time before) {
        return isAfterOrEqual(time, after) && isBeforeOrEqual(time, before);
    }

    /**
     * Checks if {@code time1} occurs after {@code time2} or is the same.
     * Assumes both times are from the same date.
     */
    public static boolean isAfterOrEqual(Time time1, Time time2) {
        if (time1.equals(time2)) {
            return true;
        }

        if (time1.getHour() < time2.getHour()) {
            return false;
        } else if (time1.getHour() > time2.getHour()) {
            return true;
        } else {
            return time1.getMinute() >= time2.getMinute();
        }
    }

    /**
     * Checks if {@code time1} occurs before {@code time2} or is the same.
     * Assumes both times are from the same date.
     */
    public static boolean isBeforeOrEqual(Time time1, Time time2) {
        if (time1.equals(time2)) {
            return true;
        }

        if (time1.getHour() > time2.getHour()) {
            return false;
        } else if (time1.getHour() < time2.getHour()) {
            return true;
        } else {
            return time1.getMinute() <= time2.getMinute();
        }
    }
}
