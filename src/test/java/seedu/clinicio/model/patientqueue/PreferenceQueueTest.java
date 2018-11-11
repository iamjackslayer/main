package seedu.clinicio.model.patientqueue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;

import org.junit.Test;

import seedu.clinicio.model.ModelManager;

public class PreferenceQueueTest {
    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPatient_noPatientInPreferenceQueue_returnsFalse() {
        assertFalse(modelManager.hasPatientInPreferenceQueue());
    }

    @Test
    public void hasPatient_patientInPreferenceQueue_returnTrue() {
        modelManager.enqueueIntoPreferenceQueue(ALEX);
        assertTrue(modelManager.hasPatientInPreferenceQueue());
    }
}
