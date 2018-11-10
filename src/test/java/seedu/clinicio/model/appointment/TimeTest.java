package seedu.clinicio.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeTest {

    @Test
    public void isValidHour() {
        Time time = new Time(23, 17);

        //valid hour
        assertTrue(time.isValidHour(23));

        //invalid hour
        assertFalse(time.isValidHour(-100));
        assertFalse(time.isValidHour(100));
        assertFalse(time.isValidHour(24));
    }

    @Test
    public void isValidMinute() {
        Time time = new Time(23, 17);

        //valid minute
        assertTrue(time.isValidMinute(23));

        //invalid minute
        assertFalse(time.isValidMinute(-100));
        assertFalse(time.isValidMinute(100));
        assertFalse(time.isValidMinute(9022324));
    }

    @Test
    public void isValidTime() {
        //valid time
        assertTrue(Time.isValidTime("21 22"));

        //invalid hours
        assertFalse(Time.isValidTime("222 20"));
        assertFalse(Time.isValidTime("ee 22"));
        assertFalse(Time.isValidTime("   21"));

        //invalid minutes
        assertFalse(Time.isValidTime("12 ee"));
        assertFalse(Time.isValidTime("22 222"));
        assertFalse(Time.isValidTime("21    "));

        //both invalid
        assertFalse(Time.isValidTime("222 222"));
        assertFalse(Time.isValidTime("ee ee"));
        assertFalse(Time.isValidTime("     "));
    }

    @Test
    public void subtractMinutes() {
        Time time = new Time(0, 15);
        Time time2 = new Time(14, 9);

        assertTrue(time.subtractMinutes(time2) == (15 - (14 * 60 + 9)));
        assertTrue(time2.subtractMinutes(time) == (14 * 60 + 9 - 15));
        assertTrue(time2.subtractMinutes(time2) == 0);
    }

    @Test
    public void equals() {
        Time time = new Time(23, 17);
        Time time2 = new Time(23, 17);
        Time time3 = new Time(2, 19);

        //same obj
        assertTrue(time.equals(time));

        //same fields
        assertTrue(time.equals(time2));

        //diff
        assertFalse(time2.equals(time3));
        assertFalse(time2.equals(null));
    }

    @Test
    public void checkToString() {
        Time time = new Time(23, 17);
        String checkTime = "Time: 23 17";
        assertTrue(time.toString().equals(checkTime));
    }
}
