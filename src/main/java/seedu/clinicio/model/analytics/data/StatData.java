package seedu.clinicio.model.analytics.data;

import static seedu.clinicio.model.analytics.Statistics.DEFAULT_SUMMARY_VALUE;
import static seedu.clinicio.model.analytics.Statistics.NUM_SUMMARY_ELEMENTS;

import java.util.ArrayList;
import java.util.List;

import seedu.clinicio.model.analytics.ChartType;

//@@author arsalanc-v2

/**
 * A convenient wrapper for summary and visualization data for a particular class of statistics.
 * There can only be one set of summary data.
 * There can be multiple visualizations.
 * Lists of tuples are used to represent ordering.
 */
public class StatData {

    private SummaryData summaryData;
    private CircularList<VisualizationData> allVisualizationData;

    public StatData() {
        allVisualizationData = new CircularList<>();
    }

    /**
     * Initializes summary data to default texts and values.
     * @param title
     * @param summaryTexts
     */
    public void initializeSummary(String title, List<String> summaryTexts) {
        assert summaryTexts.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary texts";

        List<Tuple<String, Integer>> newSummaryElements = new ArrayList<>();
        for (int i = 0; i < NUM_SUMMARY_ELEMENTS; i++) {
            // create a pair of text and value, and add it to the list
            newSummaryElements.add(new Tuple<String, Integer>(summaryTexts.get(i), DEFAULT_SUMMARY_VALUE));
        }

        summaryData = new SummaryData(title, newSummaryElements);
    }

    /**
     * Updates summary data with supplied texts and values.
     */
    public void updateSummary(String title, List<String> summaryTexts, List<Integer> summaryValues) {
        assert summaryTexts.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary texts";
        assert summaryValues.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary values";

        List<Tuple<String, Integer>> newSummaryElements = new ArrayList<>();
        for (int i = 0; i < NUM_SUMMARY_ELEMENTS; i++) {
            // create a tuple of text and value, and add it to the list
            newSummaryElements.add(new Tuple<String, Integer>(summaryTexts.get(i), summaryValues.get(i)));
        }

        summaryData = new SummaryData(title, newSummaryElements);
    }

    /**
     * Resets all summary values to zero.
     */
    public void resetSummaryValues() {
        summaryData.resetSummaryValues();
    }

    /**
     * Creates a visualization with
     * Requires the minimum amount of information from statistics classes to represent data to be visualized.
     * @param type
     * @param chartTitle
     * @param xTitle
     * @param yTitle
     * @param dataGroups
     */
    public void addVisualization(String id, ChartType type, boolean isContinuous, String chartTitle, String xTitle,
        String yTitle, List<List<Tuple<String, Integer>>> dataGroups, List<String> dataGroupsLabels) {
        assert dataGroups.size() == dataGroupsLabels.size() : "Each data group should have one label";
        allVisualizationData.add(new VisualizationData<String>(id, type, isContinuous, chartTitle, xTitle,
            yTitle, dataGroups, dataGroupsLabels));
    }

    public SummaryData getSummaryData() {
        return summaryData;
    }

    public CircularList<VisualizationData> getVisualizationData() {
        return allVisualizationData;
    }
}
