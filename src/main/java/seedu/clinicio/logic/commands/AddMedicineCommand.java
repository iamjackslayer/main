package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_EFFECTIVE_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_LETHAL_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_PRICE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_QUANTITY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_TYPE;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.medicine.Medicine;

/**
 * Adds a medicine to the medicine inventory.
 */

public class AddMedicineCommand extends Command {

    public static final String COMMAND_WORD = "addmed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a medicine to the medicine inventory. "
            + "Parameters: "
            + PREFIX_MEDICINE_NAME + "MEDICINE NAME "
            + PREFIX_MEDICINE_TYPE + "MEDICINE TYPE "
            + PREFIX_MEDICINE_EFFECTIVE_DOSAGE + "EFFECTIVE DOSAGE "
            + PREFIX_MEDICINE_LETHAL_DOSAGE + "LETHAL DOSAGE "
            + PREFIX_MEDICINE_PRICE + "PRICE "
            + PREFIX_MEDICINE_QUANTITY + "QUANTITY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEDICINE_NAME + "Paracetamol "
            + PREFIX_MEDICINE_TYPE + "tablet "
            + PREFIX_MEDICINE_EFFECTIVE_DOSAGE + "2 "
            + PREFIX_MEDICINE_LETHAL_DOSAGE + "8 "
            + PREFIX_MEDICINE_PRICE + "0.10 "
            + PREFIX_MEDICINE_QUANTITY + "1000 ";

    public static final String MESSAGE_SUCCESS = "New medicine added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "This medicine already exists in the medicine inventory";

    private final Medicine toAddMedicine;

    /**
     * Creates an AddMedicineCommand to add the specified {@code Medicine}
     */
    public AddMedicineCommand(Medicine medicine) {
        requireNonNull(medicine);
        toAddMedicine = medicine;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasMedicine(toAddMedicine)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEDICINE);
        }

        model.addMedicine(toAddMedicine);
        model.commitClinicIo();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddMedicine));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMedicineCommand // instanceof handles nulls
                && toAddMedicine.equals(((AddMedicineCommand) other).toAddMedicine));
    }

}
