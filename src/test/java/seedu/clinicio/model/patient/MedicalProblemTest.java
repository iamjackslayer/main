package seedu.clinicio.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.clinicio.testutil.Assert;

public class MedicalProblemTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicalProblem(null));
    }

    @Test
    public void constructor_invalidMedicalProblem_throwsIllegalArgumentException() {
        String invalidMedicalProblem = "@";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicalProblem(invalidMedicalProblem));
    }

    @Test
    public void isValidMedProb() {
        // null medical problem
        Assert.assertThrows(NullPointerException.class, () -> MedicalProblem.isValidMedProb(null));

        //invalid medical problem
        assertFalse(MedicalProblem.isValidMedProb("@")); // Only non-alphanumeric characters
        assertFalse(MedicalProblem.isValidMedProb("Asthma*")); // contains non-alphanumeric characters

        //valid medical problem
        assertTrue(MedicalProblem.isValidMedProb("Asthma")); // 1 Word
        assertTrue(MedicalProblem.isValidMedProb("high blood pressure")); // Multiple words without captial
        assertTrue(MedicalProblem.isValidMedProb("High Blood Pressure")); // Multiple words
        assertTrue(MedicalProblem.isValidMedProb("H1N1")); // Alphabets and Numbers

    }
}
