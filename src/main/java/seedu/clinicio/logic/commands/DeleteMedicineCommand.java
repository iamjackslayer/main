package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.medicine.Medicine;

/**
 * Removes a medicine from the medicine inventory using it's displayed index from the ClinicIO.
 */

public class DeleteMedicineCommand extends Command {

    public static final String COMMAND_WORD = "deletemed";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the medicine identified by the index number used in the displayed medicine list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEDICINE_SUCCESS = "Deleted Medicine: %1$s";

    private final Index targetIndex;

    public DeleteMedicineCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Medicine> medicineList = model.getFilteredMedicineList();

        if (targetIndex.getZeroBased() >= medicineList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        Medicine medicineToDelete = medicineList.get(targetIndex.getZeroBased());
        model.deleteMedicine(medicineToDelete);
        model.commitClinicIo();
        return new CommandResult(String.format(MESSAGE_DELETE_MEDICINE_SUCCESS, medicineToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMedicineCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteMedicineCommand) other).targetIndex)); // state check
    }

}
