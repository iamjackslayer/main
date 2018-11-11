package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

import static java.lang.Math.toIntExact;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.staff.Staff;

/**
 * Responsible for all statistics related to doctors.
 */
public class DoctorStatistics extends Statistics {

    private static final String SUMMARY_TITLE = "Average number of consultations per doctor";

    private List<Staff> doctors;
    private List<Consultation> consultations;
    private List<Patient> patients;

    public DoctorStatistics() {
        doctors = new ArrayList<>();
        consultations = new ArrayList<>();
        patients = new ArrayList<>();
        initializeSummaryValues(SUMMARY_TITLE, defaultSummaryTexts);
    }

    /**
     * Sets the internal list of doctors as the latest one.
     * Ensure staff besides doctors are not included.
     * @param allStaff The latest staff list.
     */
    public void setDoctors(ObservableList<Staff> allStaff) {
        this.doctors = allStaff.stream()
            .filter(staff -> staff.getRole().equals(Role.DOCTOR))
            .collect(Collectors.toList());
    }

    /**
     * Sets the internal list of consultations as the latest one.
     * Ensure staff besides doctors are not included.
     * @param consultations The latest consultations list.
     */
    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    /**
     * Sets the internal list of patients as the latest one.
     * Ensure staff besides doctors are not included.
     * @param patients The latest patient list.
     */
    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }

    /**
     * Calculate the average number of consultations per doctor for various time periods
     * Add these to be displayed as a summary.
     */
    @Override
    public void computeSummaryData() {
        List<Date> consultationDates = getConsultationDates();
        List<Integer> consultaionTotalValues = computeSummaryTotals(consultationDates);

        int numberOfDoctors = doctors.size();
        if (numberOfDoctors > 0) {
            List<Integer> consultationAverageValues = consultaionTotalValues.stream()
                .map(value -> value / numberOfDoctors)
                .collect(Collectors.toList());

            // update store of calculated values
            statData.updateSummary(SUMMARY_TITLE, defaultSummaryTexts, consultationAverageValues);
        }
    }

    @Override
    public void computeVisualizationData() {
        plotPreferencesPerDoctorCount();
        plotConsultationsPerDoctorCount();
    }

    /**
     * @return a list of all consultation dates.
     */
    public List<Date> getConsultationDates() {
        return consultations.stream()
            .map(consultation -> consultation.getConsultationDate())
            .collect(Collectors.toList());
    }

    /**
     * Add each doctor's patient preference count to be visualized.
     */
    public void plotPreferencesPerDoctorCount() {
        List<Tuple<String, Integer>> preferencesDataPoints = preferencesPerDoctorCount();

        statData.addVisualization("doctorPreferences", ChartType.HORIZONTAL_BAR, false,
            "Number of patient preferences for each doctor", "Doctor", "Number of patients",
            Arrays.asList(preferencesDataPoints), Arrays.asList(""));
    }

    /**
     * Add each doctor's number of consultations to be visualized.
     */
    public void plotConsultationsPerDoctorCount() {
        statData.addVisualization("doctorConsultations", ChartType.HORIZONTAL_BAR, false,
            "Number of consultations for each doctor", "Doctor", "Number of consultations",
            Arrays.asList(getConsultationsPerDoctorCount()), Arrays.asList(""));
    }

    /**
     * Counts the number of patients who prefer each doctor.
     * @return A list of tuples with the key being a doctor's name and the value being the number of patients who have
     * a preference for that particular doctor.
     */
    public List<Tuple<String, Integer>> preferencesPerDoctorCount() {
        List<Tuple<String, Integer>> prefCounts = new ArrayList<Tuple<String, Integer>>();
        for (Staff doctor : doctors) {
            long count = patients.stream()
                .filter(patient -> patient.getPreferredDoctor()
                    .map(prefDoc -> prefDoc.equals(doctor))
                    .orElse(false))
                .count();

            prefCounts.add(new Tuple<String, Integer>(doctor.getName().toString(), toIntExact(count)));
        }

        return prefCounts;
    }

    /**
     * Computes the number of consultations per doctor.
     */
    public List<Tuple<String, Integer>> getConsultationsPerDoctorCount() {
        Map<String, Integer> consultationCounts = consultations.stream()
            .map(consultation -> consultation.getDoctor())
            // remove non doctor staff
            .filter(staffOptional -> staffOptional
                .map(staff -> staff.getRole().equals(Role.DOCTOR))
                .orElse(false))
            // count the number of consultations for each doctor
            .collect(groupingBy(Function.identity(), summingInt(doctor -> 1)))
            .entrySet()
            .stream()
            .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey()
                // convert the {@code Staff} object to its name
                .map(doctor -> doctor.getName().toString()).orElse("otherstaff"), entry
                .getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // remove non doctor staff
        consultationCounts.remove("otherstaff");
        return getTupleDataCategorical(consultationCounts);
    }

    /**
     * @return A list of the names of all the doctors.
     */
    public List<String> getAllDoctorNames() {
        return doctors.stream()
            // remove non doctor staff
            .filter(staff -> staff.getRole().equals(Role.DOCTOR))
            .map(doctor -> doctor.getName().toString())
            .collect(Collectors.toList());
    }
}
