package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class MedicineQuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicineQuantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicineQuantity(invalidQuantity));
    }

    @Test
    public void isValidMedicineQuantity() {
        // null quantity
        Assert.assertThrows(NullPointerException.class, () -> MedicineQuantity.isValidMedicineQuantity(null));

        // invalid quantities
        assertFalse(MedicineQuantity.isValidMedicineQuantity("")); // empty string
        assertFalse(MedicineQuantity.isValidMedicineQuantity(" ")); // spaces only
        assertFalse(MedicineQuantity.isValidMedicineQuantity("12345")); // more than 4 numbers
        assertFalse(MedicineQuantity.isValidMedicineQuantity("cheese")); // non-numeric
        assertFalse(MedicineQuantity.isValidMedicineQuantity("9011p041")); // alphabets within digits
        assertFalse(MedicineDosage.isValidMedicineDosage("90^11.041")); // symbols within digits
        assertFalse(MedicineQuantity.isValidMedicineQuantity("11 23")); // spaces within digits

        // valid quantities
        assertTrue(MedicineQuantity.isValidMedicineQuantity("1")); // exactly 1 number
        assertTrue(MedicineQuantity.isValidMedicineQuantity("123")); // exactly 3 numbers
        assertTrue(MedicineQuantity.isValidMedicineQuantity("9999")); // exactly 4 numbers
    }

}
