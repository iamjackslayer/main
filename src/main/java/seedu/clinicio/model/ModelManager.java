package seedu.clinicio.model;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.clinicio.commons.core.ComponentManager;
import seedu.clinicio.commons.core.LogsCenter;

import seedu.clinicio.commons.events.model.ClinicIoChangedEvent;
import seedu.clinicio.commons.events.ui.AnalyticsDisplayEvent;
import seedu.clinicio.logic.commands.DequeueCommand;
import seedu.clinicio.logic.commands.EnqueueCommand;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.analytics.StatisticType;
import seedu.clinicio.model.analytics.data.StatData;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.patientqueue.MainQueue;
import seedu.clinicio.model.patientqueue.PreferenceQueue;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.util.PatientComparator;
import seedu.clinicio.ui.Ui;
import seedu.clinicio.ui.UiManager;


/**
 * Represents the in-memory model of the ClinicIO data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedClinicIo versionedClinicIo;
    private final FilteredList<Patient> allPatientsInQueue;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Patient> filteredPatients;
    private final FilteredList<Staff> filteredStaffs;
    private final FilteredList<Appointment> filteredAppointments;
    private final FilteredList<Consultation> filteredConsultations;
    private final FilteredList<Medicine> filteredMedicines;
    private final MainQueue mainQueue;
    private final PreferenceQueue preferenceQueue;
    private final Analytics analytics;
    //@@author iamjackslayer
    private UiManager uiManager = null;

    /**
     * Initializes a ModelManager with the given ClinicIO, userPrefs and ui.
     */
    public ModelManager(ReadOnlyClinicIo clinicIo, UserPrefs userPrefs) {
        super();
        requireAllNonNull(clinicIo, userPrefs);

        logger.fine("Initializing with ClinicIO: " + clinicIo + " and user prefs " + userPrefs);

        versionedClinicIo = new VersionedClinicIo(clinicIo);
        //@@author jjlee050
        filteredPersons = new FilteredList<>(versionedClinicIo.getPersonList());
        filteredPatients = new FilteredList<>(versionedClinicIo.getPatientList());
        filteredStaffs = new FilteredList<>(versionedClinicIo.getStaffList());
        filteredAppointments = new FilteredList<>(versionedClinicIo.getAppointmentList());
        filteredConsultations = new FilteredList<>(versionedClinicIo.getConsultationList());
        //@@author aaronseahyh
        filteredMedicines = new FilteredList<>(versionedClinicIo.getMedicineList());
        //@@author iamjackslayer
        mainQueue = new MainQueue();
        preferenceQueue = new PreferenceQueue();
        allPatientsInQueue = new FilteredList<>(versionedClinicIo.getQueue());
        //@@author arsalanc-v2
        analytics = new Analytics();
    }

    public ModelManager() {
        this(new ClinicIo(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyClinicIo newData) {
        versionedClinicIo.resetData(newData);
        indicateClinicIoChanged();
    }

    @Override
    public ReadOnlyClinicIo getClinicIo() {
        return versionedClinicIo;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateClinicIoChanged() {
        raise(new ClinicIoChangedEvent(versionedClinicIo));
    }

    //========== Ui changes ======================================================================

    //@@author iamjackslayer
    public void addUi(Ui ui) {
        uiManager = (UiManager) ui;
    }

    /**
     * Switches the current tab to the tab of given index.
     * @param index the index of the tab
     */
    public void switchTab(int index) {
        // if uiManager is null, it is assumed that this method is
        // called from test classes that don't require switchTab.
        if (uiManager != null) {
            uiManager.switchTab(index);
        }
    }

    //========== Boolean check ===============================================================================

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedClinicIo.hasPerson(person);
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return versionedClinicIo.hasPatient(patient);
    }

    //@@author jjlee050
    @Override
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return versionedClinicIo.hasStaff(staff);
    }

    //@@author iamjackslayer
    @Override
    public boolean hasPatientInMainQueue() {
        return mainQueue.hasPatient();
    }

    @Override
    public boolean hasPatientInPreferenceQueue() {
        return preferenceQueue.hasPatient();
    }

    @Override
    public boolean hasPatientInPatientQueue() {
        boolean hasPatient = hasPatientInPreferenceQueue() || hasPatientInMainQueue();
        return hasPatient;
    }

    //@@author gingivitiss
    @Override
    public boolean hasAppointment(Appointment appt) {
        requireNonNull(appt);
        return versionedClinicIo.hasAppointment(appt);
    }

    @Override
    public boolean hasAppointmentClash(Appointment appt) {
        requireNonNull(appt);
        return versionedClinicIo.hasAppointmentClash(appt);
    }

    //@@author arsalanc-v2
    @Override
    public boolean hasConsultation(Consultation consultation) {
        requireNonNull(consultation);
        return versionedClinicIo.hasConsultation(consultation);
    }

    //@@author aaronseahyh
    @Override
    public boolean hasMedicine(Medicine medicine) {
        requireNonNull(medicine);
        return versionedClinicIo.hasMedicine(medicine);
    }

    //========== Delete ======================================================================================

    @Override
    public void deletePerson(Person target) {
        versionedClinicIo.removePerson(target);
        indicateClinicIoChanged();
    }

    @Override
    public void deletePatient(Patient target) {
        versionedClinicIo.removePatient(target);
        indicateClinicIoChanged();
    }

    //@@author gingivitiss
    @Override
    public void deleteAppointment(Appointment target) {
        versionedClinicIo.removeAppointment(target);
        indicateClinicIoChanged();
    }

    @Override
    public void cancelAppointment(Appointment target) {
        versionedClinicIo.cancelAppointment(target);
        target.getPatient().setAppointment(target);
        versionedClinicIo.removeAppointment(target);
        indicateClinicIoChanged();
    }

    //@@author arsalanc-v2
    @Override
    public void deleteConsultation(Consultation target) {
        versionedClinicIo.removeConsultation(target);
    }

    //@@author aaronseahyh
    @Override
    public void deleteMedicine(Medicine target) {
        versionedClinicIo.removeMedicine(target);
        indicateClinicIoChanged();
    }

    //========== Add =========================================================================================

    @Override
    public void addPerson(Person person) {
        versionedClinicIo.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateClinicIoChanged();
    }

    @Override
    public void addPatient(Patient patient) {
        versionedClinicIo.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        indicateClinicIoChanged();
    }

    @Override
    public void addStaff(Staff staff) {
        versionedClinicIo.addStaff(staff);
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFFS);
        indicateClinicIoChanged();
    }

    //@@author gingivitiss
    @Override
    public void addAppointment(Appointment appt) {
        versionedClinicIo.addAppointment(appt);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        indicateClinicIoChanged();
    }

    //@author arsalanc-v2
    @Override
    public void addConsultation(Consultation consultation) {
        versionedClinicIo.add(consultation);
        updateFilteredConsultationList(PREDICATE_SHOW_ALL_CONSULTATIONS);
    }

    //@@author aaronseahyh
    @Override
    public void addMedicine(Medicine newMedicine) {
        versionedClinicIo.addMedicine(newMedicine);
        updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        indicateClinicIoChanged();
    }

    //========== Update ======================================================================================

    //@@author iamjackslayer
    @Override
    public void enqueue(Patient patient) throws CommandException {
        if (patient.isQueuing()) {
            throw new CommandException(String.format(EnqueueCommand.MESSAGE_PATIENT_IS_CURRENTLY_QUEUING,
                    patient.getName()));
        }
        if (patient.hasPreferredDoctor()) {
            enqueueIntoPreferenceQueue(patient);
        } else {
            enqueueIntoMainQueue(patient);
        }

        ArrayList<Patient> temp = new ArrayList<>();
        temp.addAll(mainQueue.getList().subList(0, mainQueue.getList().size()));
        temp.addAll(preferenceQueue.getList().subList(0, preferenceQueue.getList().size()));
        PatientComparator<Person> comparator = new PatientComparator<>();
        //   temp.sort(comparator);

        versionedClinicIo.setQueue(temp.subList(0, temp.size()));

        updateQueue(PREDICATE_SHOW_ALL_PATIENTS);
        indicateClinicIoChanged();
        patient.setIsQueuing();
    }

    //@@author iamjackslayer
    @Override
    public void dequeue(Patient patient) throws CommandException {
        if (!patient.isQueuing()) {
            throw new CommandException(String.format(DequeueCommand.MESSAGE_PATIENT_IS_NOT_CURRENTLY_QUEUING,
                    patient.getName()));
        }

        // makes sure patient is not in both mainQueue and preferenceQueue.
        assert(!(mainQueue.getList().contains(patient) && preferenceQueue.getList().contains(patient)));

        if (mainQueue.getList().contains(patient)) {
            mainQueue.getList().remove(patient);
        } else if (preferenceQueue.getList().contains(patient)) {
            preferenceQueue.getList().remove(patient);
        }

        patient.setIsNotQueuing();
    }

    /**
     * Enqueues patient who is consulting a particular doctor into the 'main' queue.
     * @param patient
     */
    @Override
    public void enqueueIntoMainQueue(Person patient) {
        mainQueue.add((Patient) patient);
    }

    //@@author iamjackslayer
    /**
     * Enqueues patient who is consulting a particular staff into the 'special' queue.
     * @param patient
     */
    @Override
    public void enqueueIntoPreferenceQueue(Person patient) {
        preferenceQueue.add((Patient) patient);
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedClinicIo.updatePerson(target, editedPerson);
        indicateClinicIoChanged();
    }

    //@@author gingivitiss
    @Override
    public void updateAppointment(Appointment target, Appointment editedAppt) {
        requireAllNonNull(target, editedAppt);
        versionedClinicIo.updateAppointment(target, editedAppt);
        indicateClinicIoChanged();
    }

    //@@author arsalanc-v2
    @Override
    public void updateConsultation(Consultation target, Consultation editedConsultation) {
        requireAllNonNull(target, editedConsultation);
        versionedClinicIo.updateConsultation(target, editedConsultation);
    }

    //@@author aaronseahyh
    @Override
    public void updateMedicineQuantity(Medicine target, MedicineQuantity newQuantity) {
        requireAllNonNull(target, newQuantity);
        versionedClinicIo.updateMedicineQuantity(target, newQuantity);
        indicateClinicIoChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedClinicIo}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author iamjackslayer
    @Override
    public void updateQueue(Predicate<Patient> predicate) {
        requireAllNonNull(predicate);
        allPatientsInQueue.setPredicate(predicate);
    }

    //@@author iamjackslayer
    @Override
    public ObservableList<Patient> getAllPatientsInQueue() {
        allPatientsInQueue.clear();
        allPatientsInQueue.addAll(mainQueue.getList());
        allPatientsInQueue.addAll(preferenceQueue.getList());
        PatientComparator<Person> comparator = new PatientComparator<>();
        allPatientsInQueue.sort(comparator);
        return FXCollections.unmodifiableObservableList(allPatientsInQueue);
    }
    //=========== Filtered Patient List Accessors =============================================================

    //@@author jjlee050
    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedClinicIo}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return FXCollections.unmodifiableObservableList(filteredPatients);
    }

    //@@author jjlee050
    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    //=========== Filtered Staff List Accessors =============================================================

    //@@author jjlee050
    /**
     * Returns an unmodifiable view of the list of {@code Staff} backed by the internal list of
     * {@code versionedClinicIo}
     */
    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return FXCollections.unmodifiableObservableList(filteredStaffs);
    }

    //@@author jjlee050
    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        requireNonNull(predicate);
        filteredStaffs.setPredicate(predicate);
    }

    //@@author jjlee050
    @Override
    public boolean checkStaffCredentials(Staff staff) {
        requireNonNull(staff);
        return versionedClinicIo.checkStaffCredentials(staff);
    }

    //=========== Filtered Appointment List Accessors ========================================================

    //@@author gingivitiss
    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedClinicIo}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return FXCollections.unmodifiableObservableList(filteredAppointments);
    }

    //@@author gingivitiss
    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    //=========== Filtered Consultation List Accessors ========================================================

    //@@author arsalanc-v2
    /**
     * Returns an unmodifiable view of the list of {@code Consultation} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Consultation> getFilteredConsultationList() {
        return FXCollections.unmodifiableObservableList(filteredConsultations);
    }

    //@@author arsalanc-v2
    @Override
    public void updateFilteredConsultationList(Predicate<Consultation> predicate) {
        requireNonNull(predicate);
        filteredConsultations.setPredicate(predicate);
    }

    //=========== Filtered Medicine Inventory Accessors ========================================================

    //@@author aaronseahyh
    /**
     * Returns an unmodifiable view of the list of {@code Medicine} backed by the internal list of
     * {@code versionedMedicineInventory}
     */
    @Override
    public ObservableList<Medicine> getFilteredMedicineList() {
        return FXCollections.unmodifiableObservableList(filteredMedicines);
    }

    //@@author aaronseahyh
    @Override
    public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
        requireNonNull(predicate);
        filteredMedicines.setPredicate(predicate);
    }

    //=========== Export ==================================================================================

    //@@author arsalanc-v2

    /**
     * Exports all patients' personal information.
     * @return A String. The feedback message for the user.
     */
    @Override
    public String exportPatients() {
        return ExportPatientsData.exportPatients(versionedClinicIo.getPatientList());
    }

    /**
     * Exports all patients' appointments records.
     * @return A String. The feedback message for the user.
     */
    @Override
    public String exportPatientsAppointments() {
        return ExportPatientsData.exportAppointments(versionedClinicIo.getPatientList());
    }

    /**
     * Exports all patients' consultation records.
     * @return A String. The feedback message for the user.
     */
    @Override
    public String exportPatientsConsultations() {
        return ExportPatientsData.exportConsultations(versionedClinicIo.getPatientList());
    }

    //=========== Undo/Redo ==================================================================================

    @Override
    public boolean canUndoClinicIo() {
        return versionedClinicIo.canUndo();
    }

    @Override
    public boolean canRedoClinicIo() {
        return versionedClinicIo.canRedo();
    }

    @Override
    public void undoClinicIo() {
        versionedClinicIo.undo();
        indicateClinicIoChanged();
    }

    @Override
    public void redoClinicIo() {
        versionedClinicIo.redo();
        indicateClinicIoChanged();
    }

    @Override
    public void commitClinicIo() {
        versionedClinicIo.commit();
    }

    //=========== Analytics ==================================================================================
    //@@author arsalanc-v2

    /**
     * Creates an event to display a particular class of analytics.
     */
    @Override
    public void requestAnalyticsDisplay(StatisticType type) {
        raise(new AnalyticsDisplayEvent(type, retrieveAnalytics(type)));
    }

    /**
     * Updates and returns the latest statistics data.
     */
    public StatData retrieveAnalytics(StatisticType type) {
        updateAnalytics(type);
        return analytics.getAllStatisticsOfType(type);
    }

    /**
     * Updates statistics data depending on the type that is supplied.
     */
    public void updateAnalytics(StatisticType type) {
        switch (type) {
        case PATIENT:
            analytics.setPatients(versionedClinicIo.getPatientList());
            analytics.setConsultations(versionedClinicIo.getConsultationList());
            break;

        case APPOINTMENT:
            analytics.setAppointments(versionedClinicIo.getAppointmentList());
            break;

        case DOCTOR:
            analytics.setDoctors(versionedClinicIo.getStaffList());
            analytics.setPatients(versionedClinicIo.getPatientList());
            analytics.setConsultations(versionedClinicIo.getConsultationList());
            break;

        default:
            analytics.setAppointments(versionedClinicIo.getAppointmentList());
            break;
        }
    }
    //========================================================================================================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        //@@author jjlee050
        return versionedClinicIo.equals(other.versionedClinicIo)
                && filteredPersons.equals(other.filteredPersons)
                && filteredStaffs.equals(other.filteredStaffs)
                && filteredAppointments.equals(other.filteredAppointments)
                && filteredMedicines.equals(other.filteredMedicines);
    }

}
