package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Medicine quantity in the ClinicIO.
 * Guarantees: mutable, is valid as declared in {@link #isValidMedicineQuantity(String)}
 */
public class MedicineQuantity {

    public static final String MESSAGE_MEDICINE_QUANTITY_CONSTRAINTS =
            "Medicine quantities should only contain numbers, and it should be between 1 to 4 digits long";
    public static final String MEDICINE_QUANTITY_VALIDATION_REGEX = "\\d{1,4}";
    public final String medicineQuantity;

    /**
     * Constructs a {@code MedicineQuantity}.
     *
     * @param quantity A valid medicine quantity.
     */
    public MedicineQuantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidMedicineQuantity(quantity), MESSAGE_MEDICINE_QUANTITY_CONSTRAINTS);
        medicineQuantity = quantity;
    }

    /**
     * Returns true if a given string is a valid medicine quantity.
     */
    public static boolean isValidMedicineQuantity(String test) {
        return test.matches(MEDICINE_QUANTITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return medicineQuantity;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicineQuantity // instanceof handles nulls
                && medicineQuantity.equals(((MedicineQuantity) other).medicineQuantity)); // state check
    }

    @Override
    public int hashCode() {
        return medicineQuantity.hashCode();
    }

}
