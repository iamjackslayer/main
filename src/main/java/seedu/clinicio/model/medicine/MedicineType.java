package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Medicine type in the ClinicIO.
 * Guarantees: immutable, is valid as declared in {@link #isValidType(String)}
 */
public class MedicineType {

    public static final String MESSAGE_MEDICINE_TYPE_CONSTRAINTS =
            "Medicine types should be one of the following common types: "
            + "Tablet, Liquid, Topical, Inhaler";

    /*
     * The first character of the medicine name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MEDICINE_TYPE_VALIDATION_REGEX = "^[a-zA-Z]+$*";

    public final String medicineType;

    /**
     * Constructs a {@code MedicineType}.
     *
     * @param type A valid type.
     */
    public MedicineType(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_MEDICINE_TYPE_CONSTRAINTS);
        medicineType = type.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid medicine type.
     */
    public static boolean isValidType(String test) {
        String toTest = test.toLowerCase();
        if ("tablet".equals(toTest) || "liquid".equals(toTest) || "topical".equals(toTest)
                || "inhaler".equals(toTest)) {
            return test.matches(MEDICINE_TYPE_VALIDATION_REGEX);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return medicineType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineType // instanceof handles nulls
                && medicineType.equals(((MedicineType) other).medicineType)); // state check
    }

    @Override
    public int hashCode() {
        return medicineType.hashCode();
    }

}
