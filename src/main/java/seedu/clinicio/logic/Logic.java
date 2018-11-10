package seedu.clinicio.logic;

import javafx.collections.ObservableList;

import seedu.clinicio.logic.commands.CommandResult;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.logic.parser.exceptions.ParseException;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered list of staffs */
    ObservableList<Staff> getFilteredStaffList();

    /** Returns an unmodifiable view of the filtered list of appointments. */
    ObservableList<Appointment> getFilteredAppointmentList();

    /** Returns an unmodifiable view of the filtered list of patients. */
    ObservableList<Person> getAllPatientsInQueue();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
