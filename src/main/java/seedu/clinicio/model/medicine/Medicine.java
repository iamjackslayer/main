package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Medicine in the ClinicIO
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Medicine {

    // Identity fields
    private final MedicineName name;
    private final MedicineType type;

    // Data fields
    private final MedicineDosage effectiveDosage;
    private final MedicineDosage lethalDosage;
    private final MedicinePrice price;

    private MedicineQuantity quantity;

    /**
     * Every field must be present and not null.
     */
    public Medicine(MedicineName name, MedicineType type, MedicineDosage effectiveDosage,
                    MedicineDosage lethalDosage, MedicinePrice price, MedicineQuantity quantity) {
        requireAllNonNull(name, type, effectiveDosage, lethalDosage, quantity);
        this.name = name;
        this.type = type;
        this.effectiveDosage = effectiveDosage;
        this.lethalDosage = lethalDosage;
        this.price = price;
        this.quantity = quantity;
    }

    public MedicineName getMedicineName() {
        return name;
    }

    public MedicineType getMedicineType() {
        return type;
    }

    public MedicineDosage getEffectiveDosage() {
        return effectiveDosage;
    }

    public MedicineDosage getLethalDosage() {
        return lethalDosage;
    }

    public MedicinePrice getPrice() {
        return price;
    }

    public MedicineQuantity getQuantity() {
        return quantity;
    }

    public void setQuantity(MedicineQuantity newQuantity) {
        this.quantity = newQuantity;
    }

    /**
     * Returns true if both medicines have the same name, type, ed, and ld.
     * This defines a weaker notion of equality between two medicines.
     */
    public boolean isSameMedicine(Medicine other) {
        if (other == this) {
            return true;
        }

        boolean isSame = other != null
                && other.getMedicineName().equals(getMedicineName())
                && other.getMedicineType().equals(getMedicineType())
                && other.getEffectiveDosage().equals(getEffectiveDosage())
                && other.getLethalDosage().equals(getLethalDosage());
        return isSame;
    }

    /**
     * Returns true if both medicines have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Medicine)) {
            return false;
        }

        Medicine otherMedicine = (Medicine) other;
        return otherMedicine.getMedicineName().equals(getMedicineName())
                && otherMedicine.getMedicineType().equals(getMedicineType())
                && otherMedicine.getEffectiveDosage().equals(getEffectiveDosage())
                && otherMedicine.getLethalDosage().equals(getLethalDosage())
                && otherMedicine.getPrice().equals(getPrice())
                && otherMedicine.getQuantity().equals(getQuantity());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, type, effectiveDosage, lethalDosage, price, quantity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getMedicineName())
                .append(" Type: ")
                .append(getMedicineType())
                .append(" Effective Dosage: ")
                .append(getEffectiveDosage())
                .append(" Lethal Dosage: ")
                .append(getLethalDosage())
                .append(" Price: ")
                .append(getPrice())
                .append(" Quantity: ")
                .append(getQuantity());
        return builder.toString();
    }

}
