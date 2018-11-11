package seedu.clinicio.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTest {

    @Test
    public void newDate() {
        //valid args
        Date toCompare = new Date(2, 2, 2222);
        assertTrue(toCompare.equals(Date.newDate("02 02 2222")));
        assertFalse(toCompare.equals(Date.newDate("02 02 2002")));
    }

    @Test
    public void isValidDay() {
        //valid day
        assertTrue(Date.isValidDay(2, 2, 2018));
        assertTrue(Date.isValidDay(12, 8, 2018));
        assertTrue(Date.isValidDay(29, 2, 2020)); //leap day

        //invalid day
        assertFalse(Date.isValidDay(30, 2, 2020));
        assertFalse(Date.isValidDay(32, 4, 2018));
        assertFalse(Date.isValidDay(679, 12, 2000));
        assertFalse(Date.isValidDay(31, 9, 2000));
        assertFalse(Date.isValidDay(30, 2, 1977));
        assertFalse(Date.isValidDay(-9, 5, 2018));
        assertFalse(Date.isValidDay(29, 2, 2019)); //invalid leap day
    }

    @Test
    public void isLeapYear() {
        //is a leap year
        assertTrue(Date.isLeapYear(1600));
        assertTrue(Date.isLeapYear(2020));

        //is not
        assertFalse(Date.isLeapYear(1989));
        assertFalse(Date.isLeapYear(2030));
        assertFalse(Date.isLeapYear(1700));
    }

    @Test
    public void isValidMonth() {
        //valid month
        assertTrue(Date.isValidMonth(12));
        assertTrue(Date.isValidMonth(1));

        //invalid month
        assertFalse(Date.isValidMonth(0));
        assertFalse(Date.isValidMonth(-1));
        assertFalse(Date.isValidMonth(2929));
    }

    @Test
    public void isValidYear() {
        //valid year
        assertTrue(Date.isValidYear(2018));
        assertTrue(Date.isValidYear(1018));
        assertTrue(Date.isValidYear(4000));

        //invalid year
        assertFalse(Date.isValidYear(-1000));
        assertFalse(Date.isValidYear(1));
    }

    @Test
    public void isValidDate() {
        //valid date
        assertTrue(Date.isValidDate("12 08 2019"));

        //invalid day
        assertFalse(Date.isValidDate("123 03 2019"));

        //invalid month
        assertFalse(Date.isValidDate("12 033 2019"));

        //invalid year
        assertFalse(Date.isValidDate("12 03 222222"));
    }

    @Test
    public void isValidFebDay() {
        assertTrue(Date.isValidFebDay(22, 2000));
    }

    @Test
    public void equals() {
        Date date1 = new Date(1, 1, 3000);
        Date date2 = new Date(1, 1, 3000);
        Date date3 = new Date(2, 4, 9000);

        //same obj
        assertTrue(date1.equals(date1));

        //same fields
        assertTrue(date1.equals(date2));

        //different
        assertFalse(date3.equals(date2));
        assertFalse(date2.equals(null));
    }

    @Test
    public void checkToString() {
        Date date1 = new Date(1, 1, 1000);
        assertTrue(("Date: 1/1/1000".equals(date1.toString())));
    }
}
