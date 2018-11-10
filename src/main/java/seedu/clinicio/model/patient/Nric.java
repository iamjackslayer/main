package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's Nric in the ClinicIO.
 * Guarantees: immutable; nric is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {
    public static final String MESSAGE_NRIC_CONSTRAINTS = "NRIC should have 9 alphanumeric characters"
            + " in the format of #0000000@";
    public static final String NRIC_VALIDATION_REGEX = "[STFG][\\p{Alnum}]{7}[A-Z]";

    public final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid NRIC.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_NRIC_CONSTRAINTS);
        value = nric;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }

}

