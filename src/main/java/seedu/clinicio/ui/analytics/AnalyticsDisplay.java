package seedu.clinicio.ui.analytics;

import java.util.Arrays;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import seedu.clinicio.commons.events.ui.AnalyticsDisplayEvent;
import seedu.clinicio.model.analytics.data.CircularList;
import seedu.clinicio.model.analytics.data.StatData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;
import seedu.clinicio.ui.UiPart;

//@@author arsalanc-v2

/**
 * A ui for displaying statistics and visualizations.
 */
public class AnalyticsDisplay extends UiPart<Region> {

    private static final String FXML = "Analytics.fxml";

    @FXML
    private Label summaryBar;
    @FXML
    private Label summaryTextOne;
    @FXML
    private Label summaryValueOne;
    @FXML
    private Label summaryTextTwo;
    @FXML
    private Label summaryValueTwo;
    @FXML
    private Label summaryTextThree;
    @FXML
    private Label summaryValueThree;
    @FXML
    private Label summaryTextFour;
    @FXML
    private Label summaryValueFour;

    @FXML
    private AnchorPane analyticsPane;
    @FXML
    private AnchorPane visualizationPane;
    @FXML
    private AnchorPane chartPane;

    private List<Tuple<Label, Label>> summaryLabels;
    private StatData allDataToDisplay;

    public AnalyticsDisplay() {
        super(FXML);
        registerAsAnEventHandler(this);

        summaryLabels = Arrays.asList(
            new Tuple(summaryTextOne, summaryValueOne),
            new Tuple(summaryTextTwo, summaryValueTwo),
            new Tuple(summaryTextThree, summaryValueThree),
            new Tuple(summaryTextFour,
            summaryValueFour)
        );
    }

    /**
     * Plots the next visualization in the case where the "Previous" button is clicked.
     */
    @FXML
    public void handleNextVisualization() {
        chartPane.getChildren().clear();
        Plot.plotChart(allDataToDisplay.getVisualizationData().getNext(), chartPane);
    }

    /**
     * Plots the previous visualization in the case where the "Next" button is clicked.
     */
    @FXML
    public void handlePreviousVisualization() {
        chartPane.getChildren().clear();
        Plot.plotChart(allDataToDisplay.getVisualizationData().getPrevious(), chartPane);
    }

    @Subscribe
    public void handleAnalyticsDisplayEvent(AnalyticsDisplayEvent event) {
        allDataToDisplay = event.getAllData();
        chartPane.getChildren().clear();
        Plot.fillSummary(allDataToDisplay.getSummaryData(), summaryBar, summaryLabels);
        CircularList<VisualizationData> allVisualizationData = allDataToDisplay.getVisualizationData();
        allVisualizationData.reset();
        Plot.plotChart(allVisualizationData.getNext(), chartPane);
    }

    public void setVisible(boolean isVisible) {
        analyticsPane.setVisible(isVisible);
    }
}
