package seedu.clinicio.model.appointment;

//@@author gingivitiss

import java.util.function.Predicate;

/**
 * Tests that an {@code Appointment}'s {@code Date} matches any of the keywords given.
 */
public class AppointmentContainsDatePredicate implements Predicate<Appointment> {
    private final Date date;

    public AppointmentContainsDatePredicate(String[] date) {
        this.date = new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
    }

    @Override
    public boolean test(Appointment appointment) {
        return date.equals(appointment.getAppointmentDate());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AppointmentContainsDatePredicate
                && date.equals(((AppointmentContainsDatePredicate) other).date));
    }
}
