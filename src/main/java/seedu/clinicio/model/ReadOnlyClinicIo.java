package seedu.clinicio.model;

import javafx.collections.ObservableList;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

/**
 * Unmodifiable view of a ClinicIO
 */
public interface ReadOnlyClinicIo {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();
    /**
     * Returns an unmodifiable view of the patients list.
     * This list will not contain any duplicate patients.
     */
    ObservableList<Patient> getPatientList();

    /**
     * Returns an unmodifiable view of the staffs list.
     * This list will not contain any duplicate staffs.
     */
    ObservableList<Staff> getStaffList();

    /**
     * Returns an unmodifiable view of the appointment list.
     * This list will not contain any duplicate appointments.
     */
    ObservableList<Appointment> getAppointmentList();

    //@@author aaronseahyh
    /**
     * Returns an unmodifiable view of the medicine list.
     * This list will not contain any duplicate medicines.
     */
    ObservableList<Medicine> getMedicineList();

}
