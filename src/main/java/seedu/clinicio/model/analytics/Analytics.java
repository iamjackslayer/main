package seedu.clinicio.model.analytics;

import javafx.collections.ObservableList;
import seedu.clinicio.model.analytics.data.StatData;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Staff;

//@@author arsalanc-v2

/**
 * Wrapper for all analytics functionality.
 * Makes use of the delegation design pattern.
 */
public class Analytics {

    private PatientStatistics patientStatistics;
    private AppointmentStatistics appointmentStatistics;
    private DoctorStatistics doctorStatistics;

    public Analytics() {
        patientStatistics = new PatientStatistics();
        appointmentStatistics = new AppointmentStatistics();
        doctorStatistics = new DoctorStatistics();
    }

    public void setPatients(ObservableList<Patient> patients) {
        patientStatistics.setPatients(patients);
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        appointmentStatistics.setAppointments(appointments);
    }

    public void setDoctors(ObservableList<Staff> doctors) {
        doctorStatistics.setDoctors(doctors);
    }

    /**
     * Sets consultations for all types of statistics that require it.
     */
    public void setConsultations(ObservableList<Consultation> consultations) {
        doctorStatistics.setConsultations(consultations);
    }

    /**
     * @return the appropriate statistics data based on the type of statistics supplied.
     */
    public StatData getAllStatisticsOfType(StatisticType type) {
        switch (type) {
        case PATIENT:
            return patientStatistics.getAllData();

        case APPOINTMENT:
            return appointmentStatistics.getAllData();

        case DOCTOR:
            return doctorStatistics.getAllData();

        default:
            return appointmentStatistics.getAllData();
        }
    }
}
