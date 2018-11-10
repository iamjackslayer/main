package seedu.clinicio.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidNric = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null Nric
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // Invalid Nric
        assertFalse(Nric.isValidNric("")); // Empty string
        assertFalse(Nric.isValidNric(" ")); // Whitespaces Only
        assertFalse(Nric.isValidNric("^")); // Only non-alphanumeric characters
        assertFalse(Nric.isValidNric("S123456@")); // Contains Non-alphanumeric characters
        assertFalse(Nric.isValidNric("S123456")); // less than 9 alphanumeric characters
        assertFalse(Nric.isValidNric("A1234567D")); // First letter is not 'S', 'T', 'F' or 'G'

        // Valid Nric
        assertTrue(Nric.isValidNric("S9523803C")); // Singaporean Nric before 1 January 2000
        assertTrue(Nric.isValidNric("T0241856G")); // Singaporean Nric after 1 January 2000
        assertTrue(Nric.isValidNric("F9141856J")); // Foreign Nric before 1 January 2000
        assertTrue(Nric.isValidNric("G1124346C")); // Foreign Nric after 1 January 2000
    }
}
