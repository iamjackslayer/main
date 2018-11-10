package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class MedicineTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicineType(null));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidType = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicineType(invalidType));
    }

    @Test
    public void isValidMedicineType() {
        // null type
        Assert.assertThrows(NullPointerException.class, () -> MedicineType.isValidType(null));

        // invalid types
        assertFalse(MedicineType.isValidType("")); // empty string
        assertFalse(MedicineType.isValidType(" ")); // spaces only
        assertFalse(MedicineType.isValidType("12345")); // numbers
        assertFalse(MedicineType.isValidType("cheese")); // other words
        assertFalse(MedicineType.isValidType("Inhal3r")); // alphabets within word
        assertFalse(MedicineType.isValidType("To pical")); // spaces within word

        // valid types
        assertTrue(MedicineType.isValidType("tablet"));
        assertTrue(MedicineType.isValidType("topical"));
        assertTrue(MedicineType.isValidType("Inhaler"));
        assertTrue(MedicineType.isValidType("Liquid"));
    }

}
