package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class MedicineNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicineName(null));
    }

    @Test
    public void constructor_invalidMedicineName_throwsIllegalArgumentException() {
        String invalidMedicineName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicineName(invalidMedicineName));
    }

    @Test
    public void isValidName() {
        // null medicine name
        Assert.assertThrows(NullPointerException.class, () -> MedicineName.isValidMedicineName(null));

        // invalid medicine name
        assertFalse(MedicineName.isValidMedicineName("")); // empty string
        assertFalse(MedicineName.isValidMedicineName(" ")); // spaces only
        assertFalse(MedicineName.isValidMedicineName("^")); // only non-alphanumeric characters
        assertFalse(MedicineName.isValidMedicineName("peter*")); // contains non-alphanumeric characters

        // valid medicine name
        assertTrue(MedicineName.isValidMedicineName("paracetamol")); // alphabets only
        assertTrue(MedicineName.isValidMedicineName("12345")); // numbers only
        assertTrue(MedicineName.isValidMedicineName("oracort2")); // alphanumeric characters
        assertTrue(MedicineName.isValidMedicineName("Paracetamol")); // with capital letters
        assertTrue(MedicineName.isValidMedicineName("OnabotulinumtoxinA AbobotulinumtoxinA")); // long names
    }

}
