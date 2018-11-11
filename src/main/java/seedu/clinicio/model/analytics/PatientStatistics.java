package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.util.DateUtil;
import seedu.clinicio.model.analytics.util.TimeUtil;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.patient.Patient;

/**
 * Responsible for all statistics related to patients.
 */
public class PatientStatistics extends Statistics {

    private static final String SUMMARY_TITLE = "Number of patients";

    private List<Patient> patients;
    private List<Consultation> consultations;

    public PatientStatistics() {
        this.patients = new ArrayList<>();
        this.consultations = new ArrayList<>();
        initializeSummaryValues(SUMMARY_TITLE, defaultSummaryTexts);
    }

    public void setPatients(ObservableList<Patient> patients) {
        this.patients = patients;
    }
    public void setConsultations(ObservableList<Consultation> consultations) {
        this.consultations = consultations;
    }

    /**
     * Calculate the number of non-unique patients (consultations) over various time periods.
     */
    @Override
    public void computeSummaryData() {
        List<Date> consultationDates = getConsultationDates();

        List<Integer> consultationSummaryValues = computeSummaryTotals(consultationDates);
        statData.updateSummary(SUMMARY_TITLE, defaultSummaryTexts, consultationSummaryValues);
    }


    @Override
    public void computeVisualizationData() {
        plotPatientsOverDayOfWeek();
        plotPatientsOverTimeOfDay();
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
     * @return a list of all consultation times.
     */
    public List<Time> getConsultationTimes() {
        return consultations.stream()
            .map(consultation -> consultation.getConsultationTime())
            .filter(timeOptional -> timeOptional.map(time -> true).orElse(false))
            .map(timeOptional -> timeOptional.get())
            .collect(Collectors.toList());
    }

    /**
     * Calculate data to plot the total number of non-unique patients (consultations) for each day of the week.
     */
    public void plotPatientsOverDayOfWeek() {
        List<Date> consultationDates = getConsultationDates();
        List<Tuple<String, Integer>> dataPoints = DateUtil.eachDayCount(consultationDates);
;
        statData.addVisualization("patientsDayOfWeek", ChartType.VERTICAL_BAR, false,
            "Number of patients for each day of the week", "Day of Week", "Number of Patients",
            Arrays.asList(dataPoints), Arrays.asList(""));
    }

    /**
     * Calculate data to plot the total number of non-unique patients (consultations) for the time periods in a
     * working day.
     */
    public void plotPatientsOverTimeOfDay() {
        List<Time> consultationTimes = getConsultationTimes();
        List<Tuple<Tuple<Time, Time>, Integer>> timeGroupsCount = TimeUtil.getTimeGroupsCount(consultationTimes);
        List<Tuple<String, Integer>> timeGroupsCountToDisplay = timeGroupsCount.stream()
            .map(timeGroupCount -> new Tuple<String, Integer>(
                    timeGroupCount.getKey().getKey().toStringNoLabel() + " - " + timeGroupCount.getKey().getValue()
                        .toStringNoLabel(),
                    timeGroupCount.getValue()
                )
            ).collect(Collectors.toList());

        statData.addVisualization("patientsTimeOfDay", ChartType.VERTICAL_BAR, false,
            "Number of patients for various time periods in a day", "Time Period",
            "Number of Patients", Arrays.asList(timeGroupsCountToDisplay), Arrays.asList(""));
    }
}
