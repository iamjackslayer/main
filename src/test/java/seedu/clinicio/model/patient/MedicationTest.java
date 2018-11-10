package seedu.clinicio.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class MedicationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Medication(null));
    }

    @Test
    public void constructor_invalidMedication_throwsIllegalArgumentException() {
        String invalidMed = "/";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Medication(invalidMed));
    }

    @Test
    public void isValidMedication() {
        // null medication
        Assert.assertThrows(NullPointerException.class, () -> Medication.isValidMed(null));

        // invalid medication
        assertFalse(Medication.isValidMed("^")); // only non-alphanumeric characters
        assertFalse(Medication.isValidMed("panadol*")); // contains non-alphanumeric characters
        assertFalse(Medication.isValidMed("^panadol*"));

        // valid medication
        assertTrue(Medication.isValidMed("panadol")); // alphabets only
        assertTrue(Medication.isValidMed("Levetiracetam")); // with capital letters
        assertTrue(Medication.isValidMed("Isavuconazonium Sulfate")); // multiple words
    }
}
