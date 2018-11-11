package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_MEDICINES;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;

/**
 * Lists all medicines in the medicine inventory to the user.
 */
public class ListMedicineCommand extends Command {

    public static final String COMMAND_WORD = "listmed";

    public static final String MESSAGE_SUCCESS = "Listed all medicines";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
