package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class MedicineDosageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicineDosage(null));
    }

    @Test
    public void constructor_invalidDosage_throwsIllegalArgumentException() {
        String invalidDosage = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicineDosage(invalidDosage));
    }

    @Test
    public void isValidMedicineDosage() {
        // null dosage
        Assert.assertThrows(NullPointerException.class, () -> MedicineDosage.isValidMedicineDosage(null));

        // invalid dosages
        assertFalse(MedicineDosage.isValidMedicineDosage("")); // empty string
        assertFalse(MedicineDosage.isValidMedicineDosage(" ")); // spaces only
        assertFalse(MedicineDosage.isValidMedicineDosage("12345")); // more than 4 numbers
        assertFalse(MedicineDosage.isValidMedicineDosage("cheese")); // non-numeric
        assertFalse(MedicineDosage.isValidMedicineDosage("9011p041")); // alphabets within digits
        assertFalse(MedicineDosage.isValidMedicineDosage("90^11.041")); // symbols within digits
        assertFalse(MedicineDosage.isValidMedicineDosage("11 23")); // spaces within digits

        // valid dosages
        assertTrue(MedicineDosage.isValidMedicineDosage("1")); // exactly 1 number
        assertTrue(MedicineDosage.isValidMedicineDosage("123")); // exactly 3 numbers
        assertTrue(MedicineDosage.isValidMedicineDosage("9999")); // exactly 4 numbers
    }

}
