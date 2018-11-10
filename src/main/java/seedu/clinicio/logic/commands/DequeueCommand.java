package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;

//@@author iamjackslayer
/**
 * Removes a given patient from the queue.
 */
public class DequeueCommand extends Command {
    public static final String COMMAND_WORD = "dequeuepatient";

    public static final String COMMAND_USAGE = COMMAND_WORD + ": Removes (from the queue) the selected patient "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: INDEX (must be a positive integer)";

    public static final String MESSAGE_DEQUEUE_PATIENT_SUCCESS = "Patient %1$s successfully removed from the queue. ";

    public static final String MESSAGE_PERSON_NOT_PATIENT = "Person %1$s is not a patient. ";

    public static final String MESSAGE_PATIENT_IS_NOT_CURRENTLY_QUEUING =
            "Patient %1$s is not currently in the queue. ";

    private final Index index;

    /**
     * @param index The index of the patient as in the displayed patient list.
     */
    public DequeueCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person patientToDequeue = lastShownList.get(index.getZeroBased());

        if (!(patientToDequeue instanceof Patient)) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_PATIENT, patientToDequeue.getName()));
        }

        model.dequeue((Patient) patientToDequeue);

        // This line ensures Observer of the list gets updated on UI.
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredPersonList(model.PREDICATE_SHOW_ALL_PATIENTS_IN_QUEUE);
        model.commitClinicIo();
        return new CommandResult(String.format(MESSAGE_DEQUEUE_PATIENT_SUCCESS, patientToDequeue.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DequeueCommand // instanceof handles nulls
                && index.equals(((DequeueCommand) other).index)); // state check
    }
}
