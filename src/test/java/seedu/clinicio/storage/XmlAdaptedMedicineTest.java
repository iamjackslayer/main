package seedu.clinicio.storage;

//@@author aaronseahyh

import static org.junit.Assert.assertEquals;
import static seedu.clinicio.storage.XmlAdaptedMedicine.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinicio.testutil.TypicalPersons.PARACETAMOL;

import org.junit.Test;

import seedu.clinicio.commons.exceptions.IllegalValueException;
import seedu.clinicio.model.medicine.MedicineDosage;
import seedu.clinicio.model.medicine.MedicineName;
import seedu.clinicio.model.medicine.MedicinePrice;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.medicine.MedicineType;
import seedu.clinicio.testutil.Assert;

public class XmlAdaptedMedicineTest {

    private static final String INVALID_MEDICINE_NAME = "Ch@@se";
    private static final String INVALID_MEDICINE_TYPE = "Drug";
    private static final String INVALID_EFFECTIVE_DOSAGE = " ";
    private static final String INVALID_LETHAL_DOSAGE = "12345";
    private static final String INVALID_PRICE = "12.";
    private static final String INVALID_QUANTITY = "23%";

    private static final String VALID_MEDICINE_NAME = PARACETAMOL.getMedicineName().toString();
    private static final String VALID_MEDICINE_TYPE = PARACETAMOL.getMedicineType().toString();
    private static final String VALID_EFFECTIVE_DOSAGE = PARACETAMOL.getEffectiveDosage().toString();
    private static final String VALID_LETHAL_DOSAGE = PARACETAMOL.getLethalDosage().toString();
    private static final String VALID_PRICE = PARACETAMOL.getPrice().toString();
    private static final String VALID_QUANTITY = PARACETAMOL.getQuantity().toString();

    @Test
    public void toModelType_validMedicineDetails_returnsMedicine() throws Exception {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(PARACETAMOL);
        assertEquals(PARACETAMOL, medicine.toModelType());
    }

    @Test
    public void toModelType_invalidMedicineName_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine =
                new XmlAdaptedMedicine(INVALID_MEDICINE_NAME, VALID_MEDICINE_TYPE, VALID_EFFECTIVE_DOSAGE,
                        VALID_LETHAL_DOSAGE, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullMedicineName_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(null, VALID_MEDICINE_TYPE,
                VALID_EFFECTIVE_DOSAGE, VALID_LETHAL_DOSAGE, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicineName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidMedicineType_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine =
                new XmlAdaptedMedicine(VALID_MEDICINE_NAME, INVALID_MEDICINE_TYPE, VALID_EFFECTIVE_DOSAGE,
                        VALID_LETHAL_DOSAGE, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = MedicineType.MESSAGE_MEDICINE_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullMedicineType_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, null,
                VALID_EFFECTIVE_DOSAGE, VALID_LETHAL_DOSAGE, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicineType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidEffectiveDosage_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine =
                new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE, INVALID_EFFECTIVE_DOSAGE,
                        VALID_LETHAL_DOSAGE, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = MedicineDosage.MESSAGE_MEDICINE_DOSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullEffectiveDosage_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE,
                null, VALID_LETHAL_DOSAGE, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicineDosage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidLethalDosage_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine =
                new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE, VALID_EFFECTIVE_DOSAGE,
                        INVALID_LETHAL_DOSAGE, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = MedicineDosage.MESSAGE_MEDICINE_DOSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullLethalDosage_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE,
                VALID_EFFECTIVE_DOSAGE, null, VALID_PRICE, VALID_QUANTITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicineDosage.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine =
                new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE, VALID_EFFECTIVE_DOSAGE,
                        VALID_LETHAL_DOSAGE, INVALID_PRICE, VALID_QUANTITY);
        String expectedMessage = MedicinePrice.MESSAGE_MEDICINE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE,
                VALID_EFFECTIVE_DOSAGE, VALID_LETHAL_DOSAGE, null, VALID_QUANTITY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicinePrice.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine =
                new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE, VALID_EFFECTIVE_DOSAGE,
                        VALID_LETHAL_DOSAGE, VALID_PRICE, INVALID_QUANTITY);
        String expectedMessage = MedicineQuantity.MESSAGE_MEDICINE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedMedicine medicine = new XmlAdaptedMedicine(VALID_MEDICINE_NAME, VALID_MEDICINE_TYPE,
                VALID_EFFECTIVE_DOSAGE, VALID_LETHAL_DOSAGE, VALID_PRICE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, MedicineQuantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicine::toModelType);
    }

}
