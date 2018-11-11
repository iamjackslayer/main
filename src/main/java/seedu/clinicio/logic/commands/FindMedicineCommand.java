package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.medicine.MedicineNameContainsKeywordsPredicate;

/**
 * Finds medicine in medicine inventory whose name matches the keyword.
 * Keyword matching is case insensitive.
 */
public class FindMedicineCommand extends Command {

    public static final String COMMAND_WORD = "findmed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds medicine whose names matches "
            + "the specified keyword (case-insensitive) "
            + "and displays the medicine properties as a list.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " paracetamol";

    private final MedicineNameContainsKeywordsPredicate predicate;

    public FindMedicineCommand(MedicineNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredMedicineList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEDICINE_OVERVIEW, model.getFilteredMedicineList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMedicineCommand // instanceof handles nulls
                && predicate.equals(((FindMedicineCommand) other).predicate)); // state check
    }

}
