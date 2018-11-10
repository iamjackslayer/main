package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Medicine name in the ClinicIO.
 * Guarantees: immutable; is valid as declared in {@link #isValidMedicineName(String)}
 */
public class MedicineName {

    public static final String MESSAGE_MEDICINE_NAME_CONSTRAINTS =
            "Medicine names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the medicine name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MEDICINE_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String medicineName;

    /**
     * Constructs a {@code MedicineName}.
     *
     * @param name A valid name.
     */
    public MedicineName(String name) {
        requireNonNull(name);
        checkArgument(isValidMedicineName(name), MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        medicineName = name.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid medicine name.
     */
    public static boolean isValidMedicineName(String test) {
        return test.matches(MEDICINE_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return medicineName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineName // instanceof handles nulls
                && medicineName.equals(((MedicineName) other).medicineName)); // state check
    }

    @Override
    public int hashCode() {
        return medicineName.hashCode();
    }

}
