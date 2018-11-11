package seedu.clinicio.ui.analytics;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import seedu.clinicio.model.analytics.ChartType;
import seedu.clinicio.model.analytics.data.SummaryData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;

//@@author arsalanc-v2

/**
 * Contains methods to represent analytics data onto a user interface.
 */
public class Plot {
    private static final int NUM_SUMMARY_ELEMENTS = 4;

    /**
     * Edits ui labels to show summary texts and corresponding values.
     */
    public static void fillSummary(SummaryData summaryData, Label summaryBar, List<Tuple<Label, Label>> summaryLabels) {
        if (summaryData == null) {
            summaryBar.setText("No summary data available");
            return;
        }

        summaryBar.setText(summaryData.getTitle());
        for (int i = 0; i < NUM_SUMMARY_ELEMENTS; i++) {
            Tuple<String, Integer> summaryElementI = summaryData.getSummaryElement(i);
            // set text for summary text label
            summaryLabels.get(i).getKey().setText(summaryElementI.getKey().toString());
            // set text for summary value label
            summaryLabels.get(i).getValue().setText(summaryElementI.getValue().toString());
        }
    }

    /**
     * Plots a chart using {@code data} onto a pane by forming the various components.
     * Components that make up a plot: axes, chart, series
     */
    public static void plotChart(VisualizationData data, Pane chartPane) {
        Tuple<Axis, Axis> axes = getAxes(data.getXTitle(), data.getYTitle(), data.isContinuous());

        XYChart chart = getChart(data.getChartType(), axes);
        chart.setId(data.getId());
        chart.setTitle(data.getChartTitle());

        List<XYChart.Series> allSeries = getSeries(data.getDataGroups(), data.getDataGroupsLabels(), data
            .getChartType().equals(ChartType.HORIZONTAL_BAR));
        chart.getData().addAll(allSeries);

        if (chart != null) {
            chart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
            chartPane.getChildren().add(chart);
        }
    }

    /**
     * @return a pair of axes for a chart.
     */
    public static Tuple<Axis, Axis> getAxes(String xTitle, String yTitle, boolean isContinuous) {
        Axis xAxis;
        if (isContinuous) {
            xAxis = new NumberAxis();
        } else {
            xAxis = new CategoryAxis();
        }
        xAxis.setLabel(xTitle);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yTitle);

        return new Tuple<>(xAxis, yAxis);
    }

    /**
     * Decides which chart type to plot.
     * @param axes The two axes of the chart..
     */
    public static XYChart getChart(ChartType type, Tuple<Axis, Axis> axes) {
        switch (type) {
        case VERTICAL_BAR:
            return new BarChart<String, Number>(axes.getKey(), axes.getValue());
        case HORIZONTAL_BAR:
            return new BarChart<>(axes.getValue(), axes.getKey());
        case STACKED_BAR:
            return new StackedBarChart<String, Number>(axes.getKey(), axes.getValue());
        case LINE:
            return new LineChart<>(axes.getKey(), axes.getValue());
        default:
            return null;
        }
    }

    /**
     * @return one or more series of data to be plotted on a chart.
     */
    public static List<XYChart.Series> getSeries(List<List<Tuple>> dataGroups, List<String> dataGroupLabels, boolean
        isHorizontal) {
        List<XYChart.Series> allSeries = new ArrayList<>();
        for (int i = 0; i < dataGroups.size(); i++) {
            XYChart.Series series = new XYChart.Series<>();
            series.setName(dataGroupLabels.get(i));

            List<Tuple> seriesData = dataGroups.get(i);
            for (Tuple dataPoint : seriesData) {
                if (isHorizontal) {
                    series.getData().add(new XYChart.Data<>(dataPoint.getValue(), dataPoint.getKey()));
                } else {
                    series.getData().add(new XYChart.Data<>(dataPoint.getKey(), dataPoint.getValue()));
                }
            }

            allSeries.add(series);
        }

        return allSeries;
    }
}

