package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.AddPatientCommand.MESSAGE_DUPLICATE_PATIENT;
import static seedu.clinicio.logic.commands.AddPatientCommand.MESSAGE_NOT_LOGIN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_BRYAN;
import static seedu.clinicio.testutil.TypicalPersons.ALAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.logic.CommandHistory;

import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.analytics.StatisticType;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

import seedu.clinicio.testutil.PatientBuilder;

public class AddPatientCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPatientCommand(null);
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPatientAdded modelStub = new ModelStubAcceptingPatientAdded();
        Patient validPatient = new PatientBuilder().build();

        UserSession.destroy();
        UserSession.create(ALAN);

        CommandResult commandResult = new AddPatientCommand(validPatient).execute(modelStub, commandHistory);

        assertEquals(String.format(AddPatientCommand.MESSAGE_SUCCESS, validPatient), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPatient), modelStub.patientsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() throws Exception {
        UserSession.destroy();
        UserSession.create(ALAN);

        Patient validPatient = new PatientBuilder().build();
        AddPatientCommand addCommand = new AddPatientCommand(validPatient);
        ModelStub modelStub = new ModelStubWithPatient(validPatient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_PATIENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_staffNotLogin_throwsCommandException() throws Exception {
        UserSession.destroy();

        Patient validPatient = new PatientBuilder().build();
        AddPatientCommand addCommand = new AddPatientCommand(validPatient);
        ModelStub modelStub = new ModelStubWithPatient(validPatient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_NOT_LOGIN);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Patient alex = new PatientBuilder().withName(VALID_NAME_ALEX).withNric(VALID_NRIC_ALEX).build();
        Patient bryan = new PatientBuilder().withName(VALID_NAME_BRYAN).withNric(VALID_NRIC_BRYAN).build();
        AddPatientCommand addAlexCommand = new AddPatientCommand(alex);
        AddPatientCommand addBryanCommand = new AddPatientCommand(bryan);

        // same object -> returns true
        assertTrue(addAlexCommand.equals(addAlexCommand));

        // same values -> returns true
        AddPatientCommand addAlexCommandCopy = new AddPatientCommand(alex);
        assertTrue(addAlexCommand.equals(addAlexCommandCopy));

        // different types -> returns false
        assertFalse(addAlexCommand.equals(1));

        // null -> returns false
        assertFalse(addAlexCommand.equals(null));

        // different person -> returns false
        assertFalse(addAlexCommand.equals(addBryanCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStaff(Staff staff) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author jjlee050
        @Override
        public boolean checkStaffCredentials(Staff staff) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyClinicIo newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyClinicIo getClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStaff(Staff staff) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getAllPatientsInQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Staff> getFilteredStaffList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author jjlee050
        @Override
        public void updateFilteredStaffList(Predicate<Staff> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitClinicIo() {
            throw new AssertionError("This method should not be called.");
        }

        //@@author iamjackslayer
        @Override
        public void enqueue(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void dequeue(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void enqueueIntoMainQueue(Person patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void enqueueIntoPreferenceQueue(Person patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatientInMainQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatientInPreferenceQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatientInPatientQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasConsultation(Consultation consultation) {
            return false;
        }

        @Override
        public void deleteConsultation(Consultation target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addConsultation(Consultation consultation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateConsultation(Consultation target, Consultation editedConsultation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Consultation> getFilteredConsultationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredConsultationList(Predicate<Consultation> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String exportPatients() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String exportPatientsAppointments() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String exportPatientsConsultations() {
            throw new AssertionError("This method should not be called.");
        }

        //@@author gingivitiss
        @Override
        public boolean hasAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointmentClash(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cancelAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAppointment(Appointment appt, Appointment editedAppt) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void requestAnalyticsDisplay(StatisticType statisticType) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author aaronseahyh
        @Override
        public boolean hasMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author aaronseahyh
        @Override
        public void deleteMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author aaronseahyh
        @Override
        public void addMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author aaronseahyh
        @Override
        public void updateMedicineQuantity(Medicine medicine, MedicineQuantity newQuantity) {
            throw new AssertionError("This method should not be called.");
        }

        //@@author aaronseahyh
        @Override
        public ObservableList<Medicine> getFilteredMedicineList() {
            throw new AssertionError("This method should not be called.");
        }

        //@@author aaronseahyh
        @Override
        public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithPatient extends AddPatientCommandTest.ModelStub {
        private final Patient patient;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return this.patient.isSamePatient(patient);
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private class ModelStubAcceptingPatientAdded extends AddPatientCommandTest.ModelStub {
        final ArrayList<Patient> patientsAdded = new ArrayList<>();

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return patientsAdded.stream().anyMatch(patient::isSamePatient);
        }

        @Override
        public void addPatient(Patient patient) {
            requireNonNull(patient);
            patientsAdded.add(patient);
        }

        @Override
        public void commitClinicIo() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyClinicIo getClinicIo() {
            return new ClinicIo();
        }
    }

}
