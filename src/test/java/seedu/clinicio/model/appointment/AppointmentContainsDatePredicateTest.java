package seedu.clinicio.model.appointment;

//@@author gingivitiss

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.AppointmentBuilder;

public class AppointmentContainsDatePredicateTest {

    @Test
    public void equals() {
        String[] date1 = {"12", "12", "2012"};
        String[] date2 = {"4", "4", "2012"};

        AppointmentContainsDatePredicate firstPredicate = new AppointmentContainsDatePredicate(date1);
        AppointmentContainsDatePredicate secondPredicate = new AppointmentContainsDatePredicate(date2);
        AppointmentContainsDatePredicate secondPredicateCopy = new AppointmentContainsDatePredicate(date2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        assertTrue(secondPredicate.equals(secondPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_appointmentContainsDate_returnsTrue() {
        String[] date1 = {"12", "12", "2012"};
        AppointmentContainsDatePredicate firstPredicate = new AppointmentContainsDatePredicate(date1);

        assertTrue(firstPredicate.test(new AppointmentBuilder().withDate(12, 12, 2012).build()));
    }

    @Test
    public void test_appointmentDoesNotContainDate_returnsFalse() {
        String[] date1 = {"12", "12", "2012"};
        AppointmentContainsDatePredicate firstPredicate = new AppointmentContainsDatePredicate(date1);

        assertFalse(firstPredicate.test(new AppointmentBuilder().build()));
    }
}
