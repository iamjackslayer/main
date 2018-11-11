package seedu.clinicio.model.analytics;

import static java.lang.Math.toIntExact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.util.DateUtil;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;

//@@author arsalanc-v2

/**
 * Responsible for all statistics related to appointments.
 * Assumes there are a total of 24 appointment slots in any given day.
 */
public class AppointmentStatistics extends Statistics {

    private static final String SUMMARY_TITLE = "Number of appointments";
    private static final int NUM_APPOINTMENTS_DAY = 24;

    private List<Appointment> appointments;

    public AppointmentStatistics() {
        this.appointments = new ArrayList<>();
        initializeSummaryValues(SUMMARY_TITLE, defaultSummaryTexts);
    }

    /**
     * Enables the latest list of appointments to be kept.
     * @param appointments An updated list of appointments.
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    /**
     * Updates {@code statData} with the latest summary values.
     */
    @Override
    public void computeSummaryData() {
        List<Date> appointmentDates = getAppointmentDates();
        List<Integer> appointmentSummaryValues = computeSummaryTotals(appointmentDates);

        // update data with calculated values
        statData.updateSummary(SUMMARY_TITLE, defaultSummaryTexts, appointmentSummaryValues);
    }

    @Override
    public void computeVisualizationData() {
        plotAppointmentSupplyDemand();
        plotAppointmentsOverYear();
    }

    /**
     * @return the total number of appointments.
    */
    private int getNumberOfAppointments() {
        return appointments.size();
    }

    /**
     * @return the total number of cancelled appointments.
     */
    private int getNumberOfCancelledAppointments() {
        long cancelledCount = appointments.stream()
            .filter(appt -> appt.isCancelled())
            .count();

        return toIntExact(cancelledCount);
    }

    /**
     * @return the total number of approved appointments.
     * Depends on the fact that there are only two appointment statuses.
     */
    private int getNumberOfApprovedAppointments() {
        return getNumberOfAppointments() - getNumberOfCancelledAppointments();
    }

    /**
     * @return the total number of follow up appointments.
    */
    private int getNumberOfFollowUpAppointments() {
        long followUpCount = appointments.stream()
            .filter(appt -> appt.getAppointmentType() == 1)
            .count();

        return toIntExact(followUpCount);
    }

    /**
     * @return the dates of all appointments.
     */
    public List<Date> getAppointmentDates() {
        return appointments.stream()
            .map(appt -> appt.getAppointmentDate())
            .collect(Collectors.toList());
    }

    /**
     * Computes data to plot the supply of appointments against their demand for the following week.
     */
    public void plotAppointmentSupplyDemand() {
        // get a list of the dates for next week
        List<Date> nextWeekDates = DateUtil.getNextWeekDates();
        // get list of scheduled dates for next week
        List<Date> scheduledSlotsDates = getNextWeekScheduledDates();
        // get list available dates for next week
        List<Date> availableSlotsDates = getNextWeekAvailableDates(nextWeekDates, scheduledSlotsDates);

        List<List<Tuple<String, Integer>>> appointmentDataGroups = overNextWeekDays(Arrays.asList
            (scheduledSlotsDates, availableSlotsDates));
        List<String> appointmentLabelGroups = Arrays.asList("scheduled", "available");

        // add to statData
        statData.addVisualization("apptSupplyDemand", ChartType.STACKED_BAR, false,
            "Appointments next week", "Day of week", "Number of appointments",
            appointmentDataGroups, appointmentLabelGroups);
    }

    /**
     * Computes data to plot the number of appointments for each month of the current year.
     */
    public void plotAppointmentsOverYear() {
        // get data points
        List<Tuple<String, Integer>> monthCounts = DateUtil.eachMonthOfCurrentYearCount(getAppointmentDates());
        statData.addVisualization("apptsYear", ChartType.LINE, false,
            "Number of Appointments This Year", "Date", "Number of Appointments",
            Arrays.asList(monthCounts), Arrays.asList(""));
    }

    /**
     * Retrieves the number of appointments for each day of the present week.
     */
    private List<Tuple<String, Integer>> getNumberOfCurrentWeekAppointments() {
        return DateUtil.eachDateOfCurrentWeekCount(getAppointmentDates()).stream()
            .map(entry -> new Tuple<String, Integer>(DateUtil.getDayFromDate(entry.getKey()).name(), entry.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * @return the dates of all scheduled appointment slots for next week.
     */
    public List<Date> getNextWeekScheduledDates() {
        // get the subset of appointments that are scheduled for next week.
        return appointments.stream()
            .map(appt -> appt.getAppointmentDate())
            .filter(date -> DateUtil.isNextWeek(date))
            .collect(Collectors.toList());
    }

    /**
     * @return the dates of all available appointment slots for next week.
     */
    public List<Date> getNextWeekAvailableDates(List<Date> nextWeekDates, List<Date> scheduledSlotsDates) {
        List<Date> availableSlotsDates = new ArrayList<>();
        // for each day of next week, find the number of slots scheduled
        for (Date nextWeekDate : nextWeekDates) {
            long scheduledSlots = scheduledSlotsDates.stream()
                .filter(scheduledDate -> scheduledDate.equals(nextWeekDate))
                .count();

            // find the number of available slots for a particular day next week
            int availableSlots = NUM_APPOINTMENTS_DAY - toIntExact(scheduledSlots);
            availableSlotsDates.addAll(Collections.nCopies(availableSlots, nextWeekDate));
        }

        return availableSlotsDates;
    }

}
