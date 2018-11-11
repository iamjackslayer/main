package seedu.clinicio.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;
import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents the time in military time to be used in appointment.
 */
public class Time {
    public static final String MESSAGE_HOUR_CONSTRAINTS =
            "The hours should be 2 digits max and range from 0 to 23. It should not be blank.";
    public static final String MESSAGE_MINUTE_CONSTRAINTS =
            "The minutes should be 2 digits max and range from 0 to 59. It should not be blank.";
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "The time should be in format hh mm and should not be blank.";
    public static final String HOUR_MIN_VALIDATION_REGEX = "\\d{1,2}";
    public static final String TIME_VALIDATION_REGEX = "(\\d{1,2})(\\s+)(\\d{1,2})";

    private final int hour;
    private final int minute;

    /**
     * Constructs a {@code Time}.
     * @param hour A valid hour.
     * @param minute A valid minute.
     */
    public Time(int hour, int minute) {
        requireAllNonNull(hour, minute);
        checkArgument(isValidHour(hour), MESSAGE_HOUR_CONSTRAINTS);
        checkArgument(isValidMinute(hour), MESSAGE_MINUTE_CONSTRAINTS);
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Constructs a {@code Time} from String.
     * @param time A valid time.
     * @return A constructed time.
     */
    public static Time newTime(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        String[] splitTime = time.split("\\s+");
        return new Time(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    /**
     * Returns true if hour exists.
     * @param hour The hour to validate.
     * @return Validity of the hour.
     */
    public boolean isValidHour(int hour) {
        String string = String.valueOf(hour);
        if (hour > 23 || hour < 0) {
            return false;
        }
        return string.matches(HOUR_MIN_VALIDATION_REGEX);
    }

    /**
     * Returns true if minute exists.
     * @param minute The minute to validate.
     * @return The validity of the minute.
     */
    public boolean isValidMinute(int minute) {
        String string = String.valueOf(minute);
        if (minute < 0 || minute > 59) {
            return false;
        }
        return string.matches(HOUR_MIN_VALIDATION_REGEX);
    }

    /**
     * Returns true if time matches regex.
     * @param time The time to validate.
     * @return The validity of {@code time}.
     */
    public static boolean isValidTime(String time) {
        return time.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Calculates the difference in minutes between {@code this} and {@code time}.
     * @param time Timing to subtract with.
     * @return The difference between two timings.
     */
    public int subtractMinutes(Time time) {
        requireNonNull(time);
        return (this.hour * 60 + this.minute) - (time.hour * 60 + time.minute);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Time otherTime = (Time) other;
        return (otherTime.getHour() == getHour()) && (otherTime.getMinute() == getMinute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Time: ")
                .append(getHour())
                .append(" ")
                .append(getMinute());
        return builder.toString();
    }

    /**
     * @return the time without labels.
     */
    public String toStringNoLabel() {
        final StringBuilder builder = new StringBuilder();
        builder
            .append(getHour())
            .append(" ")
            .append(getMinute());

        return builder.toString();
    }
}
