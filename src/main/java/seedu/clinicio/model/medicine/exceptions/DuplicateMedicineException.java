package seedu.clinicio.model.medicine.exceptions;

//@@author aaronseahyh
/**
 * Signals that the operation will result in duplicate Medicine
 * Medicines are considered duplicates if they have the same identity
 */
public class DuplicateMedicineException extends RuntimeException {
    public DuplicateMedicineException() {
        super("Operation would result in duplicate medicines");
    }
}
