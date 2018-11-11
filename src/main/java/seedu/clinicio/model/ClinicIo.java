package seedu.clinicio.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.UniqueAppointmentList;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.consultation.UniqueConsultationList;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.medicine.UniqueMedicineList;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.patient.UniquePatientList;
import seedu.clinicio.model.patientqueue.UniqueQueue;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.UniquePersonList;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.UniqueStaffList;

/**
 * Wraps all data at the ClinicIO level
 * Duplicates are not allowed (by .isSamePerson, .isSamePatient and .isSameStaff comparison)
 */
public class ClinicIo implements ReadOnlyClinicIo {

    private final UniquePersonList persons;
    //@@author jjlee050
    private final UniquePatientList patients;
    private final UniqueStaffList staffs;
    //@@author gingivitiss
    private final UniqueAppointmentList appointments;
    private final UniqueConsultationList consultations;
    //@@author iamjackslayer
    private final UniqueQueue queue;
    //@@author aaronseahyh
    private final UniqueMedicineList medicines;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        //@@author jjlee050
        patients = new UniquePatientList();
        staffs = new UniqueStaffList();
        //@@author gingivitiss
        appointments = new UniqueAppointmentList();
        consultations = new UniqueConsultationList();
        //@@author iamjackslayer
        queue = new UniqueQueue();
        //@@author aaronseahyh
        medicines = new UniqueMedicineList();
    }

    public ClinicIo() {}

    /**
     * Creates an ClinicIO using the Persons in the {@code toBeCopied}
     */
    public ClinicIo(ReadOnlyClinicIo toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //========== List overwrite operations ===================================================================

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    //@@author jjlee050
    /**
     * Replaces the contents of the patient list with {@code patients}.
     * {@code patients} must not contain duplicate patients.
     */
    public void setPatients(List<Patient> patients) {
        this.patients.setPatients(patients);
    }

    /**
     * Replaces the contents of the staff list with {@code staff}.
     * {@code staff} must not contain duplicate staff.
     */
    public void setStaffs(List<Staff> staff) {
        this.staffs.setStaffs(staff);
    }

    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate persons.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    //@@author aaronseahyh
    /**
     * Replaces the contents of the medicine list with {@code medicines}.
     * {@code medicines} must not contain duplicate medicines.
     */
    public void setMedicines(List<Medicine> medicines) {
        this.medicines.setMedicines(medicines);
    }

    /**
     * Resets the existing data of this {@code ClinicIo} with {@code newData}.
     */
    public void resetData(ReadOnlyClinicIo newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setPatients(newData.getPatientList());
        setStaffs(newData.getStaffList());
        setAppointments(newData.getAppointmentList());
        setMedicines(newData.getMedicineList());
    }

    //========== Person-level operations =====================================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the ClinicIO.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    //@@author jjlee050
    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the ClinicIO.
     */
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patients.contains(patient);
    }

    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the ClinicIO.
     */
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return staffs.contains(staff);
    }

    //@@author gingivitiss
    /**
     * Returns true if an appointment with the same identity as {@code appt} exists in the ClinicIO.
     */
    public boolean hasAppointment(Appointment appt) {
        requireNonNull(appt);
        return appointments.contains(appt);
    }

    /**
     * Returns true if an appointment has the same slot as {@code appt} in the ClinicIO.
     */
    public boolean hasAppointmentClash(Appointment appt) {
        requireNonNull(appt);
        return appointments.clashes(appt);
    }

    //@@author arsalanc-v2
    /**
     * Returns true if a consultation with the same identity as {@code consultation} exists in the address book.
     */
    public boolean hasConsultation(Consultation consultation) {
        requireNonNull(consultation);
        return consultations.contains(consultation);
    }

    //@@author iamjackslayer

    /**
     * Updates the queue in ClinicIO.
     * @param replacement
     */
    public void setQueue(List<Patient> replacement) {
        requireNonNull(replacement);
        queue.setPatients(replacement);
        //queue.add(replacement.get(0));
    }

    //@@author aaronseahyh
    /**
     * Returns true if a medicine with the same identity as {@code medicine} exists in the ClinicIO.
     */
    public boolean hasMedicine(Medicine medicine) {
        requireNonNull(medicine);
        return medicines.contains(medicine);
    }

    /**
     * Adds a person to the ClinicIO.
     * The person must not already exist in the ClinicIO.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds a patient to the ClinicIO.
     * The patient must not already exist in the ClinicIO.
     */
    public void addPatient(Patient p) {
        patients.add(p);
    }

    /**
     * Adds a staff to the ClinicIO.
     * The staff must not already exist in the ClinicIO.
     */
    public void addStaff(Staff s) {
        staffs.add(s);
    }

    //@@author gingivitiss
    /**
     * Adds an appointment to the ClinicIO.
     * The appointment must not already exist in the ClinicIO.
     */
    public void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

    //@@author arsalanc-v2
    /**
     * Adds a consultation to the address book.
     * The consultation must not already exist in the address book.
     */
    public void add(Consultation consultation) {
        consultations.add(consultation);
    }

    //@@author aaronseahyh
    /**
     * Adds a medicine to the ClinicIO.
     * The medicine must not already exist in the ClinicIO.
     */
    public void addMedicine(Medicine newMedicine) {
        medicines.add(newMedicine);
    }

    /**
     * Authenticate staff with staff record in ClinicIO.
     * This staff must exist inside ClinicIO.
     */
    public boolean checkStaffCredentials(Staff staff) {
        requireNonNull(staff);
        Staff staffRecord = staffs.getStaff(staff);

        return Password.verifyPassword(
                staff.getPassword().password,
                staffRecord.getPassword().password
        );
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the ClinicIO.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the ClinicIO.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    //@@author gingivitiss
    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppt}.
     * {@code target} must exist in the ClinicIO.
     * The appointment identity of {@code editedAppt} must not be the same as another existing appointment
     * in the ClinicIO.
     */
    public void updateAppointment(Appointment target, Appointment editedAppt) {
        requireNonNull(editedAppt);
        appointments.setAppointment(target, editedAppt);
    }

    //@@author arsalanc-v2
    /**
     * Replaces the given appointment {@code target} in the list with {@code editedConsultation}.
     * {@code target} must exist in the address book.
     * The consultation identity of {@code editedConsultation} must not be the same as another existing consultation
     * in the address book.
     */
    public void updateConsultation(Consultation target, Consultation editedConsultation) {
        requireNonNull(editedConsultation);
        consultations.setConsultation(target, editedConsultation);
    }

    //@@author aaronseahyh
    /**
     * Updates the given medicine {@code target} in the list with {@code newQuantity}.
     * {@code target} must exist in the ClinicIO.
     */
    public void updateMedicineQuantity(Medicine medicine, MedicineQuantity newQuantity) {
        requireNonNull(newQuantity);
        medicines.updateMedicineQuantity(medicine, newQuantity);
    }

    /**
     * Removes {@code key} from this {@code ClinicIo}.
     * {@code key} must exist in the ClinicIO.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code ClinicIo}.
     * {@code key} must exist in the ClinicIO.
     */
    public void removePatient(Patient key) {
        patients.remove(key);
    }

    //@@author gingivitiss
    /**
     * Removes {@code key} from this {@code ClinicIo}. Not to be confused with cancelling appointments.
     * {@code key} must exist in the ClinicIO.
     */
    public void removeAppointment(Appointment key) {
        appointments.remove(key);
    }

    /**
     * Cancels {@code key} by updating appointment with cancel status.
     * Calls {@code updateAppointment} to replace {@code key} with cancelled one.
     * {@code key} must exist in the ClinicIO.
     */
    public void cancelAppointment(Appointment key) {
        requireNonNull(key);
        appointments.cancelAppointment(key);
    }

    //@@author arsalanc-v2
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeConsultation(Consultation key) {
        consultations.remove(key);
    }

    //@@author aaronseahyh
    /**
     * Removes {@code medicine} from this {@code ClinicIo}.
     * {@code medicine} must exist in the ClinicIO.
     */
    public void removeMedicine(Medicine medicine) {
        medicines.remove(medicine);
    }

    //========== Util methods ================================================================================

    @Override
    public String toString() {
        //@@author jjlee050
        return persons.asUnmodifiableObservableList().size() + " persons & "
                + patients.asUnmodifiableObservableList().size() + " patients & "
                + staffs.asUnmodifiableObservableList().size() + " staffs & "
                + appointments.asUnmodifiableObservableList().size() + " appointments & "
                + medicines.asUnmodifiableObservableList().size() + " medicines";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Patient> getPatientList() {
        return patients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Staff> getStaffList() {
        return staffs.asUnmodifiableObservableList();
    }

    //@@author iamjackslayer
    @Override
    public ObservableList<Patient> getQueue() {
        return queue.asUnmodifiableObservableList();
    }

    //@@author gingivitiss
    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    //@@author arsalanc-v2
    public ObservableList<Consultation> getConsultationList() {
        return consultations.asUnmodifiableObservableList();
    }

    //@@author aaronseahyh
    @Override
    public ObservableList<Medicine> getMedicineList() {
        return medicines.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        //@@author jjlee050
        return other == this // short circuit if same object
                || (other instanceof ClinicIo // instanceof handles nulls
                && persons.equals(((ClinicIo) other).persons)
                && patients.equals(((ClinicIo) other).patients)
                && staffs.equals(((ClinicIo) other).staffs)
                && appointments.equals(((ClinicIo) other).appointments)
                && medicines.equals(((ClinicIo) other).medicines));
    }

    @Override
    public int hashCode() {
        //@@author jjlee050
        return Objects.hash(persons.hashCode(), patients.hashCode(),
                staffs.hashCode(), appointments.hashCode(), medicines.hashCode());
    }
}
