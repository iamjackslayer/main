package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_PARACETAMOL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

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

import seedu.clinicio.testutil.MedicineBuilder;
import seedu.clinicio.ui.Ui;

public class AddMedicineCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMedicineCommand(null);
    }

    @Test
    public void execute_medicineAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMedicineAdded modelStub = new ModelStubAcceptingMedicineAdded();
        Medicine validMedicine = new MedicineBuilder().build();

        CommandResult commandResult = new AddMedicineCommand(validMedicine).execute(modelStub, commandHistory);

        assertEquals(String.format(AddMedicineCommand.MESSAGE_SUCCESS, validMedicine), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validMedicine), modelStub.medicinesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateMedicine_throwsCommandException() throws Exception {
        Medicine validMedicine = new MedicineBuilder().build();
        AddMedicineCommand addMedicineCommand = new AddMedicineCommand(validMedicine);
        ModelStub modelStub = new ModelStubWithMedicine(validMedicine);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMedicineCommand.MESSAGE_DUPLICATE_MEDICINE);
        addMedicineCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Medicine oracort = new MedicineBuilder().withMedicineName(VALID_MEDICINENAME_ORACORT)
                .withMedicineType(VALID_MEDICINETYPE_ORACORT).build();
        Medicine paracetamol = new MedicineBuilder().withMedicineName(VALID_MEDICINENAME_PARACETAMOL)
                .withMedicineType(VALID_MEDICINETYPE_PARACETAMOL).build();
        AddMedicineCommand addOracortCommand = new AddMedicineCommand(oracort);
        AddMedicineCommand addParacetamolCommand = new AddMedicineCommand(paracetamol);

        // same object -> returns true
        assertTrue(addOracortCommand.equals(addOracortCommand));

        // same values -> returns true
        AddMedicineCommand addOracortCommandCopy = new AddMedicineCommand(oracort);
        assertTrue(addOracortCommand.equals(addOracortCommandCopy));

        // different types -> returns false
        assertFalse(addOracortCommand.equals(1));

        // null -> returns false
        assertFalse(addOracortCommand == null);

        // different person -> returns false
        assertFalse(addOracortCommand.equals(addParacetamolCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

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
        public ObservableList<Patient> getAllPatientsInQueue() {
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
        public void updateQueue(Predicate<Patient> predicate) {
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
     * A Model stub that contains a single medicine.
     */
    private class ModelStubWithMedicine extends AddMedicineCommandTest.ModelStub {
        private final Medicine medicine;

        ModelStubWithMedicine(Medicine medicine) {
            requireNonNull(medicine);
            this.medicine = medicine;
        }

        @Override
        public boolean hasMedicine(Medicine medicine) {
            requireNonNull(medicine);
            return this.medicine.isSameMedicine(medicine);
        }
    }

    /**
     * A Model stub that always accept the medicine being added.
     */
    private class ModelStubAcceptingMedicineAdded extends ModelStub {
        private final ArrayList<Medicine> medicinesAdded = new ArrayList<>();

        @Override
        public boolean hasMedicine(Medicine medicine) {
            requireNonNull(medicine);
            return medicinesAdded.stream().anyMatch(medicine::isSameMedicine);
        }

        @Override
        public void addMedicine(Medicine medicine) {
            requireNonNull(medicine);
            medicinesAdded.add(medicine);
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
