package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.AppUtil.checkArgument;

/**
 * Represents a Medicine price in the ClinicIO.
 * Guarantees: mutable, is valid as declared in {@link #isValidMedicinePrice(String)}
 */
public class MedicinePrice {

    public static final String MESSAGE_MEDICINE_PRICE_CONSTRAINTS =
            "Medicine prices should only contain numbers, with a decimal place separating dollars and cents.";
    public static final String MEDICINE_PRICE_VALIDATION_REGEX = "[\\d]+\\.[\\d]+";
    public final String medicinePrice;

    /**
     * Constructs a {@code MedicinePrice}.
     *
     * @param price A valid medicine price.
     */
    public MedicinePrice(String price) {
        requireNonNull(price);
        checkArgument(isValidMedicinePrice(price), MESSAGE_MEDICINE_PRICE_CONSTRAINTS);
        medicinePrice = price;
    }

    /**
     * Returns true if a given string is a valid medicine price.
     */
    public static boolean isValidMedicinePrice(String test) {
        return test.matches(MEDICINE_PRICE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return medicinePrice;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MedicinePrice // instanceof handles nulls
                && medicinePrice.equals(((MedicinePrice) other).medicinePrice)); // state check
    }

    @Override
    public int hashCode() {
        return medicinePrice.hashCode();
    }

}
