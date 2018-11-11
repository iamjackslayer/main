package seedu.clinicio.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.testutil.TypicalPersons.ALAN;
import static seedu.clinicio.testutil.TypicalPersons.AMY_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CARL_APPT;

import java.util.ArrayList;
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
import seedu.clinicio.testutil.AppointmentBuilder;
import seedu.clinicio.ui.Ui;


public class AddApptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddApptCommand(null);
    }

    //@@author iamjackslayer
    @Test
    public void execute_apptAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingApptAdded modelStub = new ModelStubAcceptingApptAdded();
        Appointment appointment = AMY_APPT;

        UserSession.destroy();
        UserSession.create(ALAN);

        CommandResult commandResult = new AddApptCommand(appointment).execute(modelStub, commandHistory);

        assertEquals(String.format(AddApptCommand.MESSAGE_SUCCESS, appointment), commandResult.feedbackToUser);
    }

    @Test
    public void execute_duplicateAppt_throwsCommandException() throws Exception {
        UserSession.destroy();
        UserSession.create(ALAN);

        Appointment appointment = AMY_APPT;
        AddApptCommand addApptCommand = new AddApptCommand(appointment);
        ModelStubWithAppt modelStub = new ModelStubWithAppt(appointment);

        thrown.expect(CommandException.class);
        addApptCommand.execute(modelStub, commandHistory);
    }


    @Test
    public void equals() {
        Appointment amy = new AppointmentBuilder(AMY_APPT).build();
        Appointment carl = new AppointmentBuilder(CARL_APPT).build();
        AddApptCommand addApptAmyCommand = new AddApptCommand(amy);
        AddApptCommand addApptCarlCommand = new AddApptCommand(carl);

        // same object -> returns true
        assertTrue(addApptAmyCommand.equals(addApptAmyCommand));

        // same values -> returns true
        AddApptCommand addApptAmyCommandCopy = new AddApptCommand(amy);
        assertTrue(addApptAmyCommand.equals(addApptAmyCommandCopy));

        // different types -> returns false
        assertFalse(addApptAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addApptAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addApptAmyCommand.equals(addApptCarlCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        //@@author iamjackslayer
        @Override
        public void updateQueue(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getAllPatientsInQueue() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMedicineQuantity(Medicine target, MedicineQuantity newQuantity) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Medicine> getFilteredMedicineList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMedicine(Medicine medicine) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUi(Ui ui) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void switchTab(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMedicine(Medicine medicine) {
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
        public void deletePatient(Patient target) {
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
    }

    //@@author iamjackslayer
    /**
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithAppt extends AddApptCommandTest.ModelStub {
        private final Appointment appt;

        ModelStubWithAppt(Appointment appt) {
            requireNonNull(appt);
            this.appt = appt;
        }

        @Override
        public boolean hasAppointmentClash(Appointment appt) {
            return this.appt.isSameAppointment(appt);
        }

        @Override
        public boolean hasAppointment(Appointment appt) {
            return false;
        }

        @Override
        public void switchTab(int index) {
            // do nothing since it is ui change.
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingApptAdded extends AddApptCommandTest.ModelStub {
        final ArrayList<Appointment> apptsAdded = new ArrayList<>();

        @Override
        public boolean hasAppointment(Appointment appt) {
            return apptsAdded.size() > 0;
        }

        @Override
        public void addAppointment(Appointment appt) {
            requireNonNull(appt);
            apptsAdded.add(appt);
        }

        @Override
        public boolean hasAppointmentClash(Appointment appt) {
            return false;
        }

        @Override
        public void commitClinicIo() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyClinicIo getClinicIo() {
            return new ClinicIo();
        }

        @Override
        public void switchTab(int index) {
            // do nothing since it is ui change.
        }
    }

}
