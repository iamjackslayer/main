package seedu.clinicio.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.analytics.StatisticType;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Patient> PREDICATE_SHOW_ALL_PATIENTS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Staff> PREDICATE_SHOW_ALL_STAFFS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /** {@code Predicate} that always evaluates to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PATIENTS_IN_QUEUE = (person) -> {
        if (!(person instanceof Patient)) {
            return false;
        }
        return ((Patient) person).isQueuing();
    };

    /** {@code Predicate} that always evaluate to true */
    Predicate<Consultation> PREDICATE_SHOW_ALL_CONSULTATIONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Medicine> PREDICATE_SHOW_ALL_MEDICINES = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyClinicIo newData);

    /** Returns the ClinicIo */
    ReadOnlyClinicIo getClinicIo();

    /** */
    void requestAnalyticsDisplay(StatisticType statisticType);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the ClinicIO.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the ClinicIO.
     */
    boolean hasPatient(Patient patient);

    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the ClinicIO.
     */
    boolean hasStaff(Staff staff);

    /**
     * Deletes the given person.
     * The person must exist in the ClinicIO.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the ClinicIO.
     */
    void addPerson(Person person);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the ClinicIO.
     */
    void addPatient(Patient patient);

    /**
     * Adds the given staff.
     * {@code staff} must not already exist in the ClinicIO.
     */
    void addStaff(Staff staff);

    /**
     * Authenticate staff with staff record in ClinicIO.
     * {@code staff} must exist in ClinicIO.
     */
    boolean checkStaffCredentials(Staff staff);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the ClinicIO.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the ClinicIO.
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered patient list */
    ObservableList<Patient> getFilteredPatientList();

    /** Returns an unmodifiable view of the filtered staff list */
    ObservableList<Staff> getFilteredStaffList();

    // @@author iamjackslayer
    ObservableList<Person> getAllPatientsInQueue();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStaffList(Predicate<Staff> predicate);

    /**
     * Returns true if the model has previous ClinicIO states to restore.
     */
    boolean canUndoClinicIo();

    /**
     * Returns true if the model has undone ClinicIO states to restore.
     */
    boolean canRedoClinicIo();

    /**
     * Restores the model's ClinicIO to its previous state.
     */
    void undoClinicIo();

    /**
     * Restores the model's ClinicIO to its previously undone state.
     */
    void redoClinicIo();

    /**
     * Saves the current ClinicIO state for undo/redo.
     */
    void commitClinicIo();

    /**
     * Returns true if an appointment with the same identity as {@code appointment} exists in the schedule.
     */
    boolean hasAppointment(Appointment appt);

    /**
     * Returns true if an appointment clashes with another appointment.
     */
    boolean hasAppointmentClash(Appointment appt);
    /**
     * Deletes the given appointment.
     * Not to be directly accessed by the user.
     */
    void deleteAppointment(Appointment target);

    /**
     * Cancels the given appointment.
     * The appointment must exist in the schedule.
     */
    void cancelAppointment(Appointment target);

    /**
     * Adds the given appointment.
     * {@code appt} must not already exist in the schedule.
     */
    void addAppointment(Appointment appt);

    /**
     * Replaces the given appointment {@code appt} with {@code editedAppt}.
     * {@code appt} must exist in the schedule.
     * The appointment timing of {@code editedAppt} must not be the same as another existing appointment.
     */
    void updateAppointment(Appointment appt, Appointment editedAppt);

    /** Returns an unmodifiable view of the appointment list. */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    //@@author iamjackslayer
    /**
     * Enqueues the given person.
     */
    void enqueue(Patient patient) throws CommandException;

    //@@author iamjackslayer
    /**
     * Removes the given patient from the queue.
     * @param patient
     * @throws CommandException
     */
    void dequeue(Patient patient) throws CommandException;

    //@@author iamjackslayer
    /**
     * Enqueues the given patient into main queue.
     * @param patient
     */
    void enqueueIntoMainQueue(Person patient);

    //@@author iamjackslayer
    /**
     * Enqueues the given person into preference queue.
     */
    void enqueueIntoPreferenceQueue(Person patient);

    //@@author iamjackslayer
    /**
     * Check if patient exists in the patient queue.
     */
    boolean hasPatientInMainQueue();

    //@@author iamjackslayer
    /**
     * Check if patient exists in the patient queue.
     */
    boolean hasPatientInPreferenceQueue();

    //@@author iamjackslayer
    /**
     * Check if patient exists in the patient queue.
     */
    boolean hasPatientInPatientQueue();

    //@@author arsalanc-v2
    /**
     * Returns true if a consultation with the same identity as {@code consultation}.
     */
    boolean hasConsultation(Consultation consultation);

    //@@author arsalanc-v2
    /**
     * Deletes the given consultation.
     * Not to be directly accessed by the user.
     */
    void deleteConsultation(Consultation target);

    //@@author arsalanc-v2
    /**
     * Adds the given consultation.
     * {@code consultation} must not already exist.
     */
    void addConsultation(Consultation consultation);

    //@@author arsalanc-v2
    /**
     * Replaces the given consultation {@code consultation} with {@code editedConsultation}.
     * {@code consultation} must exist in the schedule.
     */
    void updateConsultation(Consultation target, Consultation editedConsultation);

    //@@author arsalanc-v2
    /** Returns an unmodifiable view of the consultation list. */
    ObservableList<Consultation> getFilteredConsultationList();

    //@@author arsalanc-v2
    /**
     * Updates the filter of the filtered consultation list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredConsultationList(Predicate<Consultation> predicate);

    //@@author arsalanc-v2
    /** Exports all patients' personal information.
     * Returns feedback message for user.
     */
    String exportPatients();

    //@@author arsalanc-v2
    /** Exports all patients' appointment records.
     * Returns feedback message for user.
     */
    String exportPatientsAppointments();

    //@@author arsalanc-v2
    /** Exports all patients' consultation records.
     * Returns feedback message for user.
     */
    String exportPatientsConsultations();

    //@@author aaronseahyh
    /**
     * Returns true if a medicine with the same identity as {@code medicine} exists in the medicine inventory.
     */
    boolean hasMedicine(Medicine medicine);

    //@@author aaronseahyh
    /**
     * Deletes the given medicine.
     * The medicine must exist in the medicine inventory.
     */
    void deleteMedicine(Medicine medicine);

    //@@author aaronseahyh
    /**
     * Adds the given medicine.
     * {@code medicine} must not already exist in the medicine inventory.
     */
    void addMedicine(Medicine medicine);

    //@@author aaronseahyh
    /**
     * Replaces the quantity of target medicine {@code target} with {@code newQuantity}.
     * {@code target} must exist in the medicine inventory.
     */
    void updateMedicineQuantity(Medicine target, MedicineQuantity newQuantity);

    //@@author aaronseahyh
    /** Returns an unmodifiable view of the filtered medicine list */
    ObservableList<Medicine> getFilteredMedicineList();

    //@@author aaronseahyh
    /**
     * Updates the filter of the filtered medicine list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMedicineList(Predicate<Medicine> predicate);

}
