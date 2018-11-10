package seedu.clinicio.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ALAN;
import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.model.staff.Role.RECEPTIONIST;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALAN;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.logic.commands.AddCommand;
import seedu.clinicio.logic.commands.AddPatientCommand;
import seedu.clinicio.logic.commands.AppointmentStatisticsCommand;
import seedu.clinicio.logic.commands.CancelApptCommand;
import seedu.clinicio.logic.commands.ClearCommand;
import seedu.clinicio.logic.commands.DeleteCommand;
import seedu.clinicio.logic.commands.DequeueCommand;
import seedu.clinicio.logic.commands.DoctorStatisticsCommand;
import seedu.clinicio.logic.commands.EditCommand;
import seedu.clinicio.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.clinicio.logic.commands.EnqueueCommand;
import seedu.clinicio.logic.commands.ExitCommand;
import seedu.clinicio.logic.commands.ExportPatientsAppointmentsCommand;
import seedu.clinicio.logic.commands.ExportPatientsCommand;
import seedu.clinicio.logic.commands.ExportPatientsConsultationsCommand;
import seedu.clinicio.logic.commands.FindCommand;
import seedu.clinicio.logic.commands.HelpCommand;
import seedu.clinicio.logic.commands.HistoryCommand;
import seedu.clinicio.logic.commands.ListApptCommand;
import seedu.clinicio.logic.commands.ListCommand;
import seedu.clinicio.logic.commands.LoginCommand;
import seedu.clinicio.logic.commands.LogoutCommand;
import seedu.clinicio.logic.commands.PatientStatisticsCommand;
import seedu.clinicio.logic.commands.RedoCommand;
import seedu.clinicio.logic.commands.SelectCommand;
import seedu.clinicio.logic.commands.ShowPatientInQueueCommand;
import seedu.clinicio.logic.commands.UndoCommand;
import seedu.clinicio.logic.parser.exceptions.ParseException;
import seedu.clinicio.model.appointment.AppointmentContainsDatePredicate;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.NameContainsKeywordsPredicate;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.testutil.EditPersonDescriptorBuilder;
import seedu.clinicio.testutil.PatientBuilder;
import seedu.clinicio.testutil.PatientUtil;
import seedu.clinicio.testutil.PersonBuilder;
import seedu.clinicio.testutil.PersonUtil;

public class ClinicIoParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClinicIoParser parser = new ClinicIoParser();

    @Test
    public void parseCommand_listAppt() throws Exception {
        String[] dates = {"02", "03", "2017"};
        ListApptCommand command = (ListApptCommand) parser.parseCommand(
                ListApptCommand.COMMAND_WORD + " " + dates[0] + " " + dates[1] + " " + dates[2]);
        assertEquals(new ListApptCommand(new AppointmentContainsDatePredicate(dates)), command);
    }

    @Test
    public void parseCommand_cancelAppt() throws Exception {
        CancelApptCommand command = (CancelApptCommand) parser.parseCommand(
                CancelApptCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new CancelApptCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addPatient() throws Exception {
        Patient patient = new PatientBuilder().build();
        AddPatientCommand command = (AddPatientCommand) parser
                .parseCommand(PatientUtil.getAddPatientCommand(patient));
        assertEquals(new AddPatientCommand(patient), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil
                .getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_exportPatients() throws Exception {
        assertTrue(parser.parseCommand(ExportPatientsCommand.COMMAND_WORD) instanceof ExportPatientsCommand);
        assertTrue(parser.parseCommand(
                ExportPatientsCommand.COMMAND_WORD + " 3") instanceof ExportPatientsCommand);
    }

    @Test
    public void parseCommand_exportPatientsAppointments() throws Exception {
        assertTrue(parser
                .parseCommand(
                        ExportPatientsAppointmentsCommand.COMMAND_WORD) instanceof ExportPatientsAppointmentsCommand);
        assertTrue(parser
                .parseCommand(ExportPatientsAppointmentsCommand.COMMAND_WORD
                        + " 3") instanceof ExportPatientsAppointmentsCommand);
    }

    @Test
    public void parseCommand_exportPatientsConsultations() throws Exception {
        assertTrue(parser
                .parseCommand(
                        ExportPatientsConsultationsCommand
                                .COMMAND_WORD) instanceof ExportPatientsConsultationsCommand);
        assertTrue(parser
                .parseCommand(ExportPatientsConsultationsCommand.COMMAND_WORD
                        + " 3") instanceof ExportPatientsConsultationsCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_patientStatistics() throws Exception {
        assertTrue(parser
                .parseCommand(PatientStatisticsCommand.COMMAND_WORD) instanceof PatientStatisticsCommand);
        assertTrue(parser
                .parseCommand(PatientStatisticsCommand.COMMAND_WORD + " 3") instanceof PatientStatisticsCommand);
    }

    @Test
    public void parseCommand_appointmentStatistics() throws Exception {
        assertTrue(parser
                .parseCommand(AppointmentStatisticsCommand.COMMAND_WORD) instanceof AppointmentStatisticsCommand);
        assertTrue(parser
                .parseCommand(AppointmentStatisticsCommand
                        .COMMAND_WORD + " 3")instanceof AppointmentStatisticsCommand);
    }

    @Test
    public void parseCommand_doctorStatistics() throws Exception {
        assertTrue(parser
                .parseCommand(DoctorStatisticsCommand.COMMAND_WORD) instanceof DoctorStatisticsCommand);
        assertTrue(parser
                .parseCommand(DoctorStatisticsCommand.COMMAND_WORD + " 3") instanceof DoctorStatisticsCommand);
    }

    @Test
    public void parseCommand_login() throws Exception {
        LoginCommand command = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_WORD + " r/doctor n/" + ADAM.getName().fullName
                        + " pass/" + VALID_PASSWORD_ADAM);
        assertEquals(new LoginCommand(new Staff(DOCTOR,
                        ADAM.getName(),
                        new Password(VALID_PASSWORD_ADAM, false))),
                command);

        command = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_WORD + " r/receptionist n/" + ALAN.getName().fullName
                        + " pass/" + VALID_PASSWORD_ALAN);
        assertEquals(new LoginCommand(new Staff(RECEPTIONIST,
                        ALAN.getName(),
                        new Password(VALID_PASSWORD_ALAN, false))),
                command);

    }

    @Test
    public void parseCommand_logout() throws Exception {
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD) instanceof LogoutCommand);
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD + " 3") instanceof LogoutCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parserCommand_enqueueCommand() throws Exception {
        EnqueueCommand command = (EnqueueCommand) parser.parseCommand(
                EnqueueCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new EnqueueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parserCommand_dequeueCommand() throws Exception {
        DequeueCommand command = (DequeueCommand) parser.parseCommand(
                DequeueCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DequeueCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_showPatientInQueue() throws Exception {
        assertTrue(parser
                .parseCommand(ShowPatientInQueueCommand.COMMAND_WORD) instanceof ShowPatientInQueueCommand);
        assertTrue(parser
                .parseCommand(
                        ShowPatientInQueueCommand.COMMAND_WORD + " 3") instanceof ShowPatientInQueueCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
