package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.clinicio.commons.core.EventsCenter;
import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.commons.events.ui.JumpToListRequestEvent;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;

import seedu.clinicio.model.medicine.Medicine;

/**
 * Selects a medicine identified using it's displayed index from the medicine inventory.
 */
public class SelectMedicineCommand extends Command {

    public static final String COMMAND_WORD = "selectmed";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the medicine identified by the index number used in the displayed medicine list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_MEDICINE_SUCCESS = "Selected Medicine: %1$s";

    private final Index targetIndex;

    public SelectMedicineCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Medicine> filteredMedicineList = model.getFilteredMedicineList();

        if (targetIndex.getZeroBased() >= filteredMedicineList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_MEDICINE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectMedicineCommand // instanceof handles nulls
                && targetIndex.equals(((SelectMedicineCommand) other).targetIndex)); // state check
    }
}
