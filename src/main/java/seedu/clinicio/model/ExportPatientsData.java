package seedu.clinicio.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.util.ExportUtil;

//@@author arsalanc-v2

/**
 * Contains methods to export patient related data to csv files.
 */
public class ExportPatientsData {

    // file name variables
    private static final String FILE_NAME_PERSONAL_INFO = "ClinicIO_patientdata.csv";
    private static final String FILE_NAME_APPOINTMENTS = "ClinicIO_patientsappointments.csv";
    private static final String FILE_NAME_CONSULTATIONS = "ClinicIO_patientsconsultations.csv";

    // feedback message variables
    private static final String MESSAGE_SUCCESS = "All patient data successfully exported.";
    private static final String MESSAGE_FAILURE = "An error occurred while exporting patient data.";
    private static final String MESSAGE_EMPTY = "No patient data found.";

    // patient fields
    private static final String NAME = "Name";
    private static final String ADDRESS = "Address";
    private static final String PHONE = "Phone";
    private static final String EMAIL = "Email";

    /**
     * Exports all patients' personal information to a csv file.
     * Each row contains the information of a single patient.
     */
    public static String exportPatients(List<Patient> patients) {
        List<String> rows = new ArrayList<>();

        String header = getCsvRow(NAME, ADDRESS, PHONE, EMAIL);
        rows.add(header);

        for (Patient patient : patients) {
            String name = patient.getName().toString();
            String address = patient.getAddress().toString();
            String phone = patient.getPhone().toString();
            String email = patient.getEmail().toString();

            String row = getCsvRow(name, address, phone, email);
            rows.add(row);
        }

        return execute(rows, FILE_NAME_PERSONAL_INFO);
    }

    /**
     * Exports all patients' appointment records to a csv file.
     * Each row contains the information of a single appointment.
     */
    public static String exportAppointments(List<Patient> patients) {
        List<String> rows = new ArrayList<>();

        String header = getCsvRow(NAME, ADDRESS, PHONE, EMAIL, "Date", "Time", "Status", "Type");
        rows.add(header);

        for (Patient patient : patients) {
            String name = patient.getName().toString();
            String address = patient.getAddress().toString();
            String phone = patient.getPhone().toString();
            String email = patient.getEmail().toString();
            for (Appointment appt : patient.getAllAppointments()) {
                String date = appt.getAppointmentDate().toString();
                String time = appt.getAppointmentTime().toString();
                String status = appt.statusToString();
                String type = appt.typeToString();

                String row = getCsvRow(name, address, phone, email, date, time, status, type);
                rows.add(row);
            }
        }

        return execute(rows, FILE_NAME_APPOINTMENTS);
    }

    /**
     * Exports all patients' consultation records to a csv file.
     * Each row contains the information of a single consultation.
     */
    public static String exportConsultations(List<Patient> patients) {
        List<String> rows = new ArrayList<>();

        String header = getCsvRow(NAME, ADDRESS, PHONE, EMAIL, "Date", "Arrival Time", "Consultation Time",
            "End Time", "Doctor", "Prescription", "Description", "Appointment", "Appointment Date", "Appointment Time");
        rows.add(header);

        for (Patient patient : patients) {
            String name = patient.getName().toString();
            String address = patient.getAddress().toString();
            String phone = patient.getPhone().toString();
            String email = patient.getEmail().toString();
            for (Consultation consultation : patient.getAllConsultations()) {
                String date = consultation.getConsultationDate().toString();
                String arrivalTime = consultation.getArrivalTime().toString();
                String consultationTime = consultation.getConsultationTime().map(time -> time.toString())
                    .orElse("");
                String endTime = consultation.getEndTime().map(time -> time.toString()).orElse("");
                String doctorName = consultation.getDoctor().map(doc -> doc.getName().toString()).orElse("");
                String prescription = consultation.getPrescription().map(prescript -> prescript.toString())
                    .orElse("");
                String description = consultation.getDescription().map(descript -> descript.toString()).orElse
                    ("");
                String isAppt = consultation.getAppointment().map(appt -> "YES").orElse("NO");
                String apptDate = consultation.getAppointment().map(appt -> appt.getAppointmentDate().toString())
                    .orElse("");
                String apptTime = consultation.getAppointment().map(appt -> appt.getAppointmentTime().toString())
                    .orElse("");

                String row = getCsvRow(name, address, phone, email, date, arrivalTime, consultationTime, endTime,
                    doctorName, prescription, description, isAppt, apptDate, apptTime);
                rows.add(row);
            }
        }

        return execute(rows, FILE_NAME_CONSULTATIONS);
    }

    /**
     * Exports csv rows to a file.
     * @param rows A list of Strings, each a csv row.
     */
    private static String execute(List<String> rows, String fileName) {
        if (rows.size() < 2) {
            return MESSAGE_EMPTY;
        }

        try {
            ExportUtil.writeLines(new FileWriter(fileName), rows);
            return MESSAGE_SUCCESS;
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            return MESSAGE_FAILURE;
        }
    }

    /**
     * Concatenates String values with a comma to form a csv row.
     * @param values A variable length array of Strings, each denoting a csv column value for a row.
     * @return A single csv row.
     */
    private static String getCsvRow(String... values) {
        List<String> commaFreeValues = replaceCommas(values);
        return String.join(", ", commaFreeValues);
    }

    /**
     * Replaces commas in Strings to prevent breaking the csv format.
     * Uses | as an alternative.
     * @param values A variable number of Strings.
     */
    public static List<String> replaceCommas(String... values) {
        List<String> commaFreeValues = new ArrayList<>();
        for (String value : values) {
            commaFreeValues.add(value.replaceAll(",", "|"));
        }

        return commaFreeValues;
    }
}
