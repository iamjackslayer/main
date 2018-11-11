package seedu.clinicio.testutil;

//@@author aaronseahyh

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_EFFECTIVE_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_LETHAL_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_PRICE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_QUANTITY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_TYPE;

import seedu.clinicio.logic.commands.AddMedicineCommand;

import seedu.clinicio.model.medicine.Medicine;

/**
 * A utility class for Medicine.
 */
public class MedicineUtil {

    /**
     * Returns an addmed command string for adding the {@code medicine}.
     */
    public static String getAddMedicineCommand(Medicine medicine) {
        return AddMedicineCommand.COMMAND_WORD + " " + getMedicineDetails(medicine);
    }

    /**
     * Returns the part of command string for the given {@code medicine}'s details.
     */
    public static String getMedicineDetails(Medicine medicine) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MEDICINE_NAME + medicine.getMedicineName().medicineName + " ");
        sb.append(PREFIX_MEDICINE_TYPE + medicine.getMedicineType().medicineType + " ");
        sb.append(PREFIX_MEDICINE_EFFECTIVE_DOSAGE + medicine.getEffectiveDosage().medicineDosage + " ");
        sb.append(PREFIX_MEDICINE_LETHAL_DOSAGE + medicine.getLethalDosage().medicineDosage + " ");
        sb.append(PREFIX_MEDICINE_PRICE + medicine.getPrice().medicinePrice + " ");
        sb.append(PREFIX_MEDICINE_QUANTITY + medicine.getQuantity().medicineQuantity + " ");
        return sb.toString();
    }

}
