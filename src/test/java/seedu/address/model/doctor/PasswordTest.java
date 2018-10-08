package seedu.address.model.doctor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.HashUtil;
import seedu.address.testutil.Assert;

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
    public void isSameAsHashPassword() {
        String password = "peter12";
        //null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isSameAsHashPassword(password, null));

        //empty string
        Assert.assertThrows(IllegalArgumentException.class, () -> Password.isSameAsHashPassword(password, ""));

        //invalid password
        assertFalse(Password.isSameAsHashPassword("peter13", HashUtil.hashToString(password)));
        assertFalse(Password.isSameAsHashPassword("peter13", HashUtil.hashToString(password)));
        assertFalse(Password.isSameAsHashPassword("peter12", " ")); //Only spaces password hash string
        assertFalse(Password.isSameAsHashPassword("", " ")); //Empty string and only spaces password hash string

        //valid password
        assertTrue(Password.isSameAsHashPassword(password, HashUtil.hashToString(password)));

    }
}
