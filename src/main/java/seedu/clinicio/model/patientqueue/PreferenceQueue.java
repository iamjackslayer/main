//@@author iamjackslayer
package seedu.clinicio.model.patientqueue;

import seedu.clinicio.model.patient.Patient;

/**
 * Represents the "special" patient queue in the clinic. Each patient in the queue has
 * a preferred/pre-assigned staff with whom he is consulting.
 * TODO Change Person object into Patient object (await Patient model to be created).
 */
public class PreferenceQueue extends PatientQueue<Patient> {
    @Override
    public void add(Patient person) {
        list.add(person);
    }
}
