package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.clinicio.model.medicine.exceptions.DuplicateMedicineException;
import seedu.clinicio.model.medicine.exceptions.MedicineNotFoundException;

/**
 * A list of medicines that enforces uniqueness between its elements and does not allow nulls.
 * A medicine is considered unique by comparing using {@code Medicine#isSameMedicine(Medicine)}. As such, adding and
 * updating of medicines uses Medicine#isSameMedicine(Medicine) for equality so as to ensure that the medicine being
 * added or updated is unique in terms of identity in the UniqueMedicineList.
 * However, the removal of a medicine uses Medicine#equals(Object) so as to ensure that the medicine with exactly the
 * same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Medicine#isSameMedicine(Medicine)
 */
public class UniqueMedicineList implements Iterable<Medicine> {

    private final ObservableList<Medicine> internalMedicineList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent medicine as the given argument.
     */
    public boolean contains(Medicine toCheck) {
        requireNonNull(toCheck);
        return internalMedicineList.stream().anyMatch(toCheck::isSameMedicine);
    }

    /**
     * Adds a medicine to the list.
     * The medicine must not already exist in the list.
     */
    public void add(Medicine toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMedicineException();
        }
        internalMedicineList.add(toAdd);
    }

    /**
     * Update the medicine {@code target} in the list with {@code newQuantity}.
     * {@code target} must exist in the list.
     */
    public void updateMedicineQuantity(Medicine target, MedicineQuantity newQuantity) {
        requireAllNonNull(target, newQuantity);

        int index = internalMedicineList.indexOf(target);
        if (index == -1) {
            throw new MedicineNotFoundException();
        }

        Medicine toUpdate = internalMedicineList.get(index);
        toUpdate.setQuantity(newQuantity);
    }

    /**
     * Removes the equivalent medicine from the list.
     * The medicine must exist in the list.
     */
    public void remove(Medicine toDelete) {
        requireNonNull(toDelete);
        if (!internalMedicineList.remove(toDelete)) {
            throw new MedicineNotFoundException();
        }
    }

    public void setMedicines(UniqueMedicineList replacement) {
        requireNonNull(replacement);
        internalMedicineList.setAll(replacement.internalMedicineList);
    }

    /**
     * Replaces the contents of this list with {@code medicines}.
     */
    public void setMedicines(List<Medicine> medicines) {
        requireAllNonNull(medicines);
        if (!medicinesAreUnique(medicines)) {
            throw new DuplicateMedicineException();
        }

        internalMedicineList.setAll(medicines);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Medicine> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalMedicineList);
    }

    @Override
    public Iterator<Medicine> iterator() {
        return internalMedicineList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMedicineList // instanceof handles nulls
                && internalMedicineList.equals(((UniqueMedicineList) other).internalMedicineList));
    }

    @Override
    public int hashCode() {
        return internalMedicineList.hashCode();
    }

    /**
     * Returns true if {@code medicines} contains only unique medicines.
     */
    private boolean medicinesAreUnique(List<Medicine> medicines) {
        for (int i = 0; i < medicines.size() - 1; i++) {
            for (int j = i + 1; j < medicines.size(); j++) {
                if (medicines.get(i).isSameMedicine(medicines.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
