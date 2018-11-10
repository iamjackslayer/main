package seedu.clinicio.commons.util;

import static org.junit.Assert.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

//@@author jjlee050
public class HashUtilTest {

    @Test
    public void hashToString() {
        //Null password
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.hashToString(null));

        assertNotNull(HashUtil.hashToString("123"));
    }

    @Test
    public void verifyPassword() {

        //Null password and/or hashed password
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.verifyPassword(null, null));
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.verifyPassword(null, "1231"));
        Assert.assertThrows(NullPointerException.class, () -> HashUtil.verifyPassword("131", null));

        // Illegal value for empty password hash string
        Assert.assertThrows(IllegalArgumentException.class, () -> HashUtil.verifyPassword(
                HashUtil.hashToString("124"), "")); // Empty password hash string only
        Assert.assertThrows(IllegalArgumentException.class, () -> HashUtil.verifyPassword(
                "", "")); // Empty password and password hash string

        // Verify Password -> returns false
        assertFalse(HashUtil.verifyPassword(HashUtil.hashToString("123"),
                HashUtil.hashToString("124"))); // Both hashed password
        assertFalse(HashUtil.verifyPassword("", HashUtil.hashToString("124"))); // Empty password

        // Verify Password -> returns true
        assertTrue(HashUtil.verifyPassword("abc123", HashUtil.hashToString("abc123"))); // alphanumeric
        assertTrue(HashUtil.verifyPassword("123", HashUtil.hashToString("123"))); // only numbers
        assertTrue(HashUtil.verifyPassword("abc 123", HashUtil.hashToString("abc 123"))); // alphanumeric + spaces.

    }
}
