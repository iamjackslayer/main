package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICAL_PROBLEM;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PREFERRED_DOCTOR;

import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;

import seedu.clinicio.model.Model;
import seedu.clinicio.model.patient.Patient;

//@@author jjlee050
/**
 * Adds a patient to ClinicIO.
 */
public class AddPatientCommand extends Command {

    public static final String COMMAND_WORD = "addpatient";

    public static final String MESSAGE_NOT_LOGIN = "You are not logged in as a receptionist";
    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the ClinicIO";
    public static final String MESSAGE_SUCCESS = "New patient added: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient to the ClinicIO. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_IC + " IC "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_MEDICAL_PROBLEM + "MEDICAL_PROBLEMS]..."
            + "[" + PREFIX_MEDICATION + "MEDICATIONS]... "
            + "[" + PREFIX_ALLERGY + "ALLERGIES]... "
            + "[" + PREFIX_PREFERRED_DOCTOR + "PREFERRED_DOC]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_IC + " S1234567A "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_MEDICAL_PROBLEM + "High Blood Pressure "
            + PREFIX_MEDICAL_PROBLEM + "Asthma ";

    private Patient toAdd;

    public AddPatientCommand(Patient toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException {
        requireNonNull(model);

        if (!UserSession.isLogin()) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        } else if (model.hasPatient(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.addPatient(toAdd);
        model.commitClinicIo();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPatientCommand // instanceof handles nulls
                && toAdd.equals(((AddPatientCommand) other).toAdd));
    }
}
