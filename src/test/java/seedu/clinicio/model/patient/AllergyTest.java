package seedu.clinicio.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class AllergyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergy_throwsIllegalArgumentException() {
        String invalidAllergy = "@";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Allergy(invalidAllergy));
    }

    @Test
    public void isValidAllergy() {
        // null medical problem
        Assert.assertThrows(NullPointerException.class, () -> Allergy.isValidAllergy(null));

        //invalid medical problem
        assertFalse(Allergy.isValidAllergy("@")); // Only non-alphanumeric characters
        assertFalse(Allergy.isValidAllergy("Heat*")); // contains non-alphanumeric characters

        //valid medical problem
        assertTrue(Allergy.isValidAllergy("heat")); // 1 Word
        assertTrue(Allergy.isValidAllergy("gluten products")); // Multiple words without captial
        assertTrue(Allergy.isValidAllergy("Meat And Animal Products")); // Multiple words
        assertTrue(Allergy.isValidAllergy("H1N1")); // Alphabets and Numbers

    }
}
