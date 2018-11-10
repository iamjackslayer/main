package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Medicine dosage in the ClinicIO.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedicineDosage(String)}
 */
public class MedicineDosage {

    public static final String MESSAGE_MEDICINE_DOSAGE_CONSTRAINTS =
            "Medicine dosages should only contain numbers, and it should be between 1 to 4 digits long";
    public static final String MEDICINE_DOSAGE_VALIDATION_REGEX = "\\d{1,4}";
    public final String medicineDosage;

    /**
     * Constructs a {@code MedicineDosage}.
     *
     * @param dosage A valid medicine dosage.
     */
    public MedicineDosage(String dosage) {
        requireNonNull(dosage);
        checkArgument(isValidMedicineDosage(dosage), MESSAGE_MEDICINE_DOSAGE_CONSTRAINTS);
        medicineDosage = dosage;
    }

    /**
     * Returns true if a given string is a valid medicine dosage.
     */
    public static boolean isValidMedicineDosage(String test) {
        return test.matches(MEDICINE_DOSAGE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return medicineDosage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineDosage // instanceof handles nulls
                && medicineDosage.equals(((MedicineDosage) other).medicineDosage)); // state check
    }

    @Override
    public int hashCode() {
        return medicineDosage.hashCode();
    }


}
