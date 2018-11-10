package seedu.clinicio.model.appointment;

//@@author gingivitiss

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BRYAN;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null, null, null, 0));
    }

    @Test
    public void cancelAppointment() {
        Date validDate = new Date(2, 2, 2002);
        Time validTime = new Time(12, 20);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 1);
        Appointment appt2 = new Appointment(validDate, validTime, ALEX, 1);

        appt.cancelAppointment();

        //0 means cancelled
        assertTrue(appt.getAppointmentStatus() == 0);
        assertFalse(appt.getAppointmentStatus() == appt2.getAppointmentStatus());
    }

    @Test
    public void isSameSlot() {
        Date validDate = new Date(2, 2, 2002);
        Date otherDate = new Date(3, 12, 2002);

        Time validTime = new Time(12, 20);
        Time otherTime = new Time(23, 10);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 1);
        Appointment appt2 = new Appointment(validDate, validTime, BRYAN, 1);
        Appointment appt3 = new Appointment(otherDate, validTime, ALEX, 1);
        Appointment appt4 = new Appointment(otherDate, otherTime, ALEX, 1);

        //same obj
        assertTrue(appt.isSameSlot(appt));

        //same date and time
        assertTrue(appt.isSameSlot(appt2));

        //different date
        assertFalse(appt.isSameSlot(appt3));

        //different time
        assertFalse(appt3.isSameSlot(appt4));

        //different date and time
        assertFalse(appt.isSameSlot(appt4));
    }

    @Test
    public void isSamePatient() {
        Date validDate = new Date(2, 2, 2222);

        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BRYAN, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BRYAN, 0);

        //same obj
        assertTrue(appt.isSamePatient(appt));

        //same patient
        assertTrue(appt3.isSamePatient(appt2));

        //diff patient
        assertFalse(appt.isSamePatient(appt2));
    }

    @Test
    public void isSameDoctor() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BRYAN, 1);
        Appointment appt3 = new Appointment(validDate, validTime, ALEX, 0);

        //same obj
        assertTrue(appt.isSameDoctor(appt));

        //diff fields, same staff
        assertTrue(appt.isSameDoctor(appt2));

        //diff staff
        appt3.setAssignedStaff(BEN);
        assertFalse(appt2.isSameDoctor(appt3));
        assertFalse(appt.isSameDoctor(appt3));
    }

    @Test
    public void isSameAppointment() {
        Date validDate = new Date(2, 2, 2222);
        Date otherDate = new Date(2, 3, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BRYAN, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BRYAN, 0);
        Appointment appt4 = new Appointment(validDate, otherTime, ALEX, 0);
        Appointment appt5 = new Appointment(otherDate, otherTime, ALEX, 0);
        Appointment appt6 = new Appointment(otherDate, otherTime, ALEX, 0);

        //same obj
        assertTrue(appt.isSameAppointment(appt));

        //same fields
        assertTrue(appt2.equals(appt3));

        //diff patient
        assertFalse(appt.isSameAppointment(appt2));

        //diff time
        assertFalse(appt.isSameAppointment(appt4));

        //diff date
        assertFalse(appt5.isSameAppointment(appt4));

        //diff staff - 1 null
        appt6.setAssignedStaff(BEN);
        assertFalse(appt6.isSameAppointment(appt5));

        //diff staff
        appt5.setAssignedStaff(ADAM);
        assertFalse(appt5.isSameAppointment(appt6));

        //diff status
        //status change has no effect
        appt3.cancelAppointment();
        assertTrue(appt3.isSameAppointment(appt2));
    }

    @Test
    public void isOverlapAppointment() {
        Date validDate = new Date(2, 2, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);
        Time otherTime2 = new Time(12, 30);
        Time otherTime3 = new Time(13, 30);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BRYAN, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BRYAN, 0);
        Appointment appt4 = new Appointment(validDate, otherTime, ALEX, 0);
        Appointment appt5 = new Appointment(validDate, otherTime2, ALEX, 0);
        Appointment appt6 = new Appointment(validDate, otherTime3, ALEX, 0);

        //same obj
        assertTrue(appt.isOverlapAppointment(appt));

        //same timing
        assertTrue(appt2.isOverlapAppointment(appt3));
        assertTrue(appt.isOverlapAppointment(appt2));

        //this is later than appt but within overlap
        assertTrue(appt4.isOverlapAppointment(appt5));

        //this is earlier than appt but within overlap
        assertTrue(appt5.isOverlapAppointment(appt4));

        //1 hour from prev appt
        assertFalse(appt5.isOverlapAppointment(appt6));
    }

    @Test
    public void isCancelled() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);

        appt.cancelAppointment();
        assertTrue(appt.isCancelled());
    }

    @Test
    public void statusToString() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);
        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);

        assertEquals(appt.statusToString(), "Status: APPROVED");

        appt.cancelAppointment();

        assertEquals(appt.statusToString(), "Status: CANCELLED");

    }

    @Test
    public void typeToString() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);
        Appointment appt2 = new Appointment(validDate, validTime, ALEX, 1);

        assertEquals(appt.typeToString(), "Type: NEW");
        assertEquals(appt2.typeToString(), "Type: FOLLOW-UP");
    }

    @Test
    public void equals() {
        Date validDate = new Date(2, 2, 2222);
        Date otherDate = new Date(2, 3, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);

        Appointment appt = new Appointment(validDate, validTime, ALEX, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BRYAN, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BRYAN, 0);
        Appointment appt4 = new Appointment(validDate, otherTime, ALEX, 0);
        Appointment appt5 = new Appointment(otherDate, otherTime, ALEX, 0);
        Appointment appt6 = new Appointment(otherDate, otherTime, ALEX, 0);

        //same obj
        assertTrue(appt.equals(appt));

        //same fields
        assertTrue(appt3.equals(appt2));

        //diff patient
        assertFalse(appt.equals(appt2));

        //diff date
        assertFalse(appt4.equals(appt5));

        //diff time
        assertFalse(appt.equals(appt4));

        //diff staff - 1 null
        appt5.setAssignedStaff(BEN);
        assertFalse(appt6.equals(appt5));

        //diff staff
        appt6.setAssignedStaff(ADAM);
        assertFalse(appt6.equals(appt5));

        //diff status
        appt2.cancelAppointment();
        assertFalse(appt2.equals(appt3));

        //instance of
        assertFalse(appt2.equals(validDate));
    }
}
