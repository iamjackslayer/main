package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;

//@@author iamjackslayer
/**
 * Shows a list of patients currently in the queue.
 */
public class ShowPatientInQueueCommand extends Command {
    public static final String COMMAND_WORD = "showqueue";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": Shows the list of patients "
            + "currently in the queue. ";
    public static final String MESSAGE_SHOW_PATIENTS_IN_QUEUE_SUCCESS = "Patients currently "
            + "in the queue successfully displayed. ";
    public static final String MESSAGE_NO_PATIENT_IN_QUEUE = "No patient in the queue. ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(model.PREDICATE_SHOW_ALL_PATIENTS_IN_QUEUE);
        model.commitClinicIo();
        model.switchTab(1);
        if (model.hasPatientInPatientQueue()) {
            return new CommandResult(String.format(MESSAGE_SHOW_PATIENTS_IN_QUEUE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_PATIENT_IN_QUEUE));
        }
    }
}
