package seedu.clinicio.storage;

//@@author aaronseahyh

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineDosage;
import seedu.clinicio.model.medicine.MedicineName;
import seedu.clinicio.model.medicine.MedicinePrice;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.medicine.MedicineType;

/**
 * JAXB-friendly version of the Medicine.
 */
public class XmlAdaptedMedicine {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medicine's %s field is missing!";

    @XmlElement(required = true)
    private String medicineName;
    @XmlElement(required = true)
    private String medicineType;
    @XmlElement(required = true)
    private String effectiveDosage;
    @XmlElement(required = true)
    private String lethalDosage;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String quantity;

    /**
     * Constructs an XmlAdaptedMedicine.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMedicine() {}

    /**
     * Constructs an {@code XmlAdaptedMedicine} with the given medicine details.
     */
    public XmlAdaptedMedicine(String medicineName, String medicineType, String effectiveDosage, String lethalDosage,
                              String price, String quantity) {
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.effectiveDosage = effectiveDosage;
        this.lethalDosage = lethalDosage;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Converts a given Medicine into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMedicine
     */
    public XmlAdaptedMedicine(Medicine source) {
        medicineName = source.getMedicineName().medicineName;
        medicineType = source.getMedicineType().toString();
        effectiveDosage = source.getEffectiveDosage().medicineDosage;
        lethalDosage = source.getLethalDosage().medicineDosage;
        price = source.getPrice().medicinePrice;
        quantity = source.getQuantity().medicineQuantity;
    }

    /**
     * Converts this jaxb-friendly adapted medicine object into the model's Medicine object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medicine
     */
    public Medicine toModelType() throws IllegalValueException {
        if (medicineName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicineName.class.getSimpleName()));
        }
        if (!MedicineName.isValidMedicineName(medicineName)) {
            throw new IllegalValueException(MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        }
        final MedicineName modelName = new MedicineName(medicineName);

        if (medicineType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicineType.class.getSimpleName()));
        }
        if (!MedicineType.isValidType(medicineType)) {
            throw new IllegalValueException(MedicineType.MESSAGE_MEDICINE_TYPE_CONSTRAINTS);
        }
        final MedicineType modelType = new MedicineType(medicineType);

        if (effectiveDosage == null || lethalDosage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicineDosage.class.getSimpleName()));
        }
        if (!MedicineDosage.isValidMedicineDosage(effectiveDosage)
                || !MedicineDosage.isValidMedicineDosage(lethalDosage)) {
            throw new IllegalValueException(MedicineDosage.MESSAGE_MEDICINE_DOSAGE_CONSTRAINTS);
        }
        final MedicineDosage modelEffectiveDosage = new MedicineDosage(effectiveDosage);
        final MedicineDosage modelLethalDosage = new MedicineDosage(lethalDosage);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicinePrice.class.getSimpleName()));
        }
        if (!MedicinePrice.isValidMedicinePrice(price)) {
            throw new IllegalValueException(MedicinePrice.MESSAGE_MEDICINE_PRICE_CONSTRAINTS);
        }
        final MedicinePrice modelPrice = new MedicinePrice(price);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MedicineQuantity.class.getSimpleName()));
        }
        if (!MedicineQuantity.isValidMedicineQuantity(quantity)) {
            throw new IllegalValueException(MedicineQuantity.MESSAGE_MEDICINE_QUANTITY_CONSTRAINTS);
        }
        final MedicineQuantity modelQuantity = new MedicineQuantity(quantity);

        return new Medicine(modelName, modelType, modelEffectiveDosage, modelLethalDosage, modelPrice,
                modelQuantity);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMedicine)) {
            return false;
        }

        XmlAdaptedMedicine otherMedicine = (XmlAdaptedMedicine) other;
        return Objects.equals(medicineName, otherMedicine.medicineName)
                && Objects.equals(medicineType, otherMedicine.medicineType)
                && Objects.equals(effectiveDosage, otherMedicine.effectiveDosage)
                && Objects.equals(lethalDosage, otherMedicine.lethalDosage)
                && Objects.equals(price, otherMedicine.price)
                && Objects.equals(quantity, otherMedicine.quantity);
    }

}
