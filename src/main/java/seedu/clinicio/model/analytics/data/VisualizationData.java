package seedu.clinicio.model.analytics.data;

import java.util.List;

import seedu.clinicio.model.analytics.ChartType;

//@@author arsalanc-v2

/**
 * Data structure for storing data needed to create visualizations.
 * Exposes data to be displayed through convenient methods.
 * Takes in a type parameter specifying the type of the first coordinate for each data point.
 */
public class VisualizationData<T> {

    // fields for a plot
    private boolean isContinuous;
    private String id;
    private ChartType type;
    private String chartTitle;
    private String xTitle;
    private String yTitle;
    private List<List<Tuple<T, Integer>>> dataGroups;
    private List<String> dataGroupsLabels;

    /**
     * Categorical or continuous visualizations.
     * Continuous visualizations are composed only of an Integer, Integer pairing.
     * @param type
     * @param chartTitle
     * @param xTitle
     * @param yTitle
     * @param dataGroups
     */
    public VisualizationData(String id, ChartType type, boolean isContinuous, String chartTitle, String xTitle, String
        yTitle,
        List<List<Tuple<T, Integer>>> dataGroups, List<String> dataGroupsLabels) {
        this.id = id;
        this.type = type;
        this.isContinuous = isContinuous;
        this.chartTitle = chartTitle;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.dataGroups = dataGroups;
        this.dataGroupsLabels = dataGroupsLabels;
    }

    public String getId() {
        return id;
    }

    public ChartType getChartType() {
        return type;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public String getXTitle() {
        return xTitle;
    }

    public String getYTitle() {
        return yTitle;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    /**
     * @return the title for each group of data in this visualization.
     */
    public List<String> getDataGroupsLabels() {
        return dataGroupsLabels;
    }

    /**
     * @return all the groups of data to be displayed in this visualization.
     */
    public List<List<Tuple<T, Integer>>> getDataGroups() {
        return dataGroups;
    }
}
