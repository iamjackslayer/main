package seedu.clinicio.model.analytics;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.clinicio.model.analytics.data.CircularList;
import seedu.clinicio.model.analytics.data.StatData;
import seedu.clinicio.model.analytics.data.SummaryData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;
import seedu.clinicio.model.analytics.util.DateUtil;
import seedu.clinicio.model.appointment.Date;

//@@author arsalanc-v2

/**
 * Represents statistics of a general type.
 * Contains method for calculate statistics and represent them as data to be displayed.
 */
public abstract class Statistics {

    public static final int NUM_SUMMARY_ELEMENTS = 4;
    public static final int DEFAULT_SUMMARY_VALUE = 0;

    // summary fields
    private static final String SUMMARY_TODAY = "Today";
    private static final String SUMMARY_WEEK = "This Week";
    private static final String SUMMARY_MONTH = "This Month";
    private static final String SUMMARY_YEAR = "This Year";

    // protected so subclasses can access these fields
    protected final List<String> defaultSummaryTexts = Arrays.asList(SUMMARY_TODAY, SUMMARY_WEEK, SUMMARY_MONTH,
        SUMMARY_YEAR);
    protected final List<Integer> defaultSummaryValues = Arrays.asList(0, 0, 0, 0);
    protected StatData statData;

    /**
     * Initializes maps to store statistic names as keys and their corresponding values.
     */
    public Statistics() {
        statData = new StatData();
    }

    public abstract void computeSummaryData();
    public abstract void computeVisualizationData();

    public void initializeSummaryValues(String summaryTitle, List<String> summaryTexts) {
        statData.initializeSummary(summaryTitle, summaryTexts);
    }

    /**
     * @return A list of the number of occurrences of dates for each summary field.
     */
    public List<Integer> computeSummaryTotals(List<Date> dates) {
        int todayCount = DateUtil.todayCount(dates);
        int weekCount = DateUtil.currentWeekCount(dates);
        int monthCount = DateUtil.currentMonthCount(dates);
        int yearCount = DateUtil.currentYearCount(dates);

        return Arrays.asList(todayCount, weekCount, monthCount, yearCount);
    }

    /**
     * Calculates the range of an axis given {@code values} based on an {@code offset} from the minimum and maximum
     * values.
     * Assumes offset is not negative.
     */
    public Tuple<Integer, Integer> calculateAxisRange(List<Integer> values, int offset) {
        requireNonNull(values);
        // separate assertions for greater clarity in debugging
        assert offset >= 0 : "Offset must not be negative";
        assert offset >= 5 : "Offset must be at least five";

        if (values.isEmpty()) {
            return new Tuple<Integer, Integer>(0, offset);
        }

        int min = values.stream()
            .mapToInt(v -> v)
            .min()
            .orElseGet(() -> 0);
        min = Math.max(0, min - offset);

        int max = values.stream()
            .mapToInt(v -> v)
            .max()
            .orElseGet(() -> offset);
        max = max + offset;

        return new Tuple<Integer, Integer>(min, max);
    }

    /**
     * Transforms a map to a list of tuples for categorical data.
     * Considers categorical data to mean having a String, Integer pairing.
     */
    public List<Tuple<String, Integer>> getTupleDataCategorical(Map<String, Integer> map) {
        return map.entrySet().stream()
            .map(entry -> new Tuple<String, Integer>(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * Transforms a map to a list of tuples for continuous data.
     * Considers continuous data to mean having an Integer, Integer pairing.
     */
    public List<Tuple<Integer, Integer>> getTupleDataContinuous(Map<Integer, Integer> map) {
        return map.entrySet().stream()
            .map(entry -> new Tuple<Integer, Integer>(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * Constructs data for a categorical plot for the days of next week.
     * For each group of dates provided, calculates the count of the dates provided for each day next week.
     * Returns counts in the order they were provided as inputs.
     * Assumes at least one group of dates is supplied.
     * @return a list of list of tuples with the day as key and its count as value. Each sub-list is for a single group
     * of data.
     */
    public List<List<Tuple<String, Integer>>> overNextWeekDays(List<List<Date>> datesGroups) {
        assert datesGroups.size() >= 1 : "Invalid number of groups of dates supplied.";

        List<List<Tuple<String, Integer>>> dataGroups = new ArrayList<>();
        for (List<Date> datesGroup : datesGroups) {
            // get counts for dates of next week
            List<Tuple<Date, Integer>> nextWeekDateCounts = DateUtil.eachDateOfNextWeekCount(datesGroup);

            // convert count to using days instead of dates as keys
            List<Tuple<String, Integer>> dataGroup = nextWeekDateCounts.stream()
                .map(entry -> new Tuple<DayOfWeek, Integer>(DateUtil.getDayFromDate(entry.getKey()), entry.getValue()))
                .map(tuple -> new Tuple<String, Integer>(tuple.getKey().name(), tuple.getValue()))
                .collect(Collectors.toList());

            dataGroups.add(dataGroup);
        }

        return dataGroups;
    }

    /**
     * @return All data to be displayed.
     */
    public StatData getAllData() {
        computeSummaryData();
        computeVisualizationData();
        return statData;
    }

    /**
     * @return All data pertaining to summaries to be displayed.
     */
    public SummaryData getSummaryData() {
        computeSummaryData();
        return statData.getSummaryData();
    }

    /**
     * @return All data pertaining to visualizations to be displayed.
     */
    public CircularList<VisualizationData> getVisualizationData() {
        computeVisualizationData();
        return statData.getVisualizationData();
    }
}
