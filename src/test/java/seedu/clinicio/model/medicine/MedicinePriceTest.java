package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class MedicinePriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicinePrice(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicinePrice(invalidPrice));
    }

    @Test
    public void isValidMedicinePrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> MedicinePrice.isValidMedicinePrice(null));

        // invalid prices
        assertFalse(MedicinePrice.isValidMedicinePrice("")); // empty string
        assertFalse(MedicinePrice.isValidMedicinePrice(" ")); // spaces only
        assertFalse(MedicinePrice.isValidMedicinePrice("12345")); // digits without decimal
        assertFalse(MedicinePrice.isValidMedicinePrice("cheese")); // non-numeric
        assertFalse(MedicinePrice.isValidMedicinePrice("9011p.041")); // alphabets within digits
        assertFalse(MedicinePrice.isValidMedicinePrice("1.1 23")); // spaces within digits
        assertFalse(MedicinePrice.isValidMedicinePrice(".10")); // no digits before decimal
        assertFalse(MedicinePrice.isValidMedicinePrice("10.")); // no digits after decimal

        // valid prices
        assertTrue(MedicinePrice.isValidMedicinePrice("0.01"));
        assertTrue(MedicinePrice.isValidMedicinePrice("9999.9999"));
    }

}
