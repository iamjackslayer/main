package seedu.clinicio.model.patientqueue;
//@@author iamjackslayer
import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.patient.exceptions.DuplicatePatientException;
import seedu.clinicio.model.person.exceptions.DuplicatePersonException;
import seedu.clinicio.model.person.exceptions.PersonNotFoundException;

/**
 * A queue containing patients. It enforces uniqueness between its elements and does not allow nulls.
 * A patient is considered unique by comparing using {@code Patient#isSamePatient(Patient)}.
 * As such, adding and updating of
 * patients uses Person#isSamePatient(Patient) for equality so as to ensure that the patient being added or updated is
 * unique in terms of identity in the UniqueQueue. However, the removal of a patient uses Patient#equals(Object) so
 * as to ensure that the patient with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *@see Patient#isSamePatient(Patient)
 */
public class UniqueQueue implements Iterable<Patient> {

    private final ObservableList<Patient> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Patient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a patient to the list.
     * The patient must not already exist in the list.
     */
    public void add(Patient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePatientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the patient {@code target} in the queue with {@code editedPatient}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPatient} must not be the same as another existing patient in the queue.
     */
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPatient) && contains(editedPatient)) {
            // boolean a = target.isSamePerson(editedPerson);
            // boolean b = contains(editedPerson);
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPatient);
    }

    /**
     * Removes the equivalent patient from the queue.
     * The patient must exist in the queue.
     */
    public void remove(Patient toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPatients(UniqueQueue replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this queue with {@code patients}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPatients(List<Patient> patients) {
        requireAllNonNull(patients);
        if (!patientsAreUnique(patients)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(patients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Patient> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Patient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueQueue // instanceof handles nulls
                && internalList.equals(((UniqueQueue) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique patients.
     */
    private boolean patientsAreUnique(List<Patient> patients) {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = i + 1; j < patients.size(); j++) {
                if (patients.get(i).isSamePatient(patients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
