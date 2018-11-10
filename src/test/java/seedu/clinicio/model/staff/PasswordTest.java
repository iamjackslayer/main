package seedu.clinicio.model.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.commons.util.HashUtil;

import seedu.clinicio.testutil.Assert;

//@@author jjlee050
public class PasswordTest {

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null, false));
    }

    @Test
    public void constructor_nullHashedPassword_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null, true));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidName, false));
    }

    @Test
    public void constructor_invalidHashedPassword_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidName, true));
    }

    @Test
    public void isValidPassword() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid name
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("^")); // only non-alphanumeric characters
        assertFalse(Password.isValidPassword("peter*")); // contains non-alphanumeric characters
        assertFalse(Password.isValidPassword("pete")); // less than 6 characters
        assertFalse(Password.isValidPassword("Capital Tan")); // with spaces
        assertFalse(Password.isValidPassword("David Roger Jackson Ray Jr 2nd")); // more than 12 characters

        // valid name
        assertTrue(Password.isValidPassword("joseph")); // 6 alphabets only
        assertTrue(Password.isValidPassword("peterjack")); // lower-case alphabets only
        assertTrue(Password.isValidPassword("81920543")); // numbers only
        assertTrue(Password.isValidPassword("123456789012")); // 12 numbers only
        assertTrue(Password.isValidPassword("CapitalTan")); // mix of upper and lower case alphabets
        assertTrue(Password.isValidPassword("Capital123")); // alphanuemic characters only

    }

    @Test
    public void isValidHashedPassword() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidHashedPassword(null));

        // Invalid hashed password
        assertFalse(Password.isValidHashedPassword("")); //Empty string

        // Valid hashed password
        //TODO: Am i suppose to assert false if string is "" or spaces?
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString(""))); // empty string
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString(" "))); // spaces only
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString("joseph")));
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString("peterjack")));
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString("81920543")));
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString("123456789012")));
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString("CapitalTan")));
        assertTrue(Password.isValidHashedPassword(HashUtil.hashToString("Capital123")));

    }


    @Test
    public void verifyPassword() {
        String password = "peter12";
        //null password
        Assert.assertThrows(NullPointerException.class, () -> Password.verifyPassword(password, null));

        //empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> Password.verifyPassword(password, ""));

        //invalid password
        assertFalse(Password.verifyPassword("peter13", HashUtil.hashToString(password)));
        assertFalse(Password.verifyPassword("peter13", HashUtil.hashToString(password)));
        assertFalse(Password.verifyPassword("peter12", " ")); //Only spaces password hash string
        assertFalse(Password.verifyPassword("", " ")); //Empty string and only spaces password hash string
        assertFalse(Password.verifyPassword("\\s", " ")); //Empty string and only spaces password hash string

        //valid password
        assertTrue(Password.verifyPassword(password, HashUtil.hashToString(password)));

    }
}
