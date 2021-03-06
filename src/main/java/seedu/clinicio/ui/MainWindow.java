package seedu.clinicio.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;

import seedu.clinicio.commons.core.Config;
import seedu.clinicio.commons.core.GuiSettings;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.AnalyticsDisplayEvent;
import seedu.clinicio.commons.events.ui.AppointmentPanelSelectionChangedEvent;
import seedu.clinicio.commons.events.ui.ExitAppRequestEvent;
import seedu.clinicio.commons.events.ui.PatientPanelSelectionChangedEvent;
import seedu.clinicio.commons.events.ui.ShowHelpRequestEvent;

import seedu.clinicio.logic.Logic;

import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.ui.analytics.AnalyticsDisplay;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BrowserPanel browserPanel;
    private PatientListPanel patientListPanel;
    private AppointmentListPanel appointmentListPanel;
    private QueuePanel queuePanel;
    private Config config;
    private UserPrefs prefs;
    private HelpWindow helpWindow;
    private AnalyticsDisplay analyticsDisplay;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane patientListPanelPlaceholder;

    @FXML
    private StackPane appointmentListPanelPlaceholder;

    @FXML
    private StackPane queuePanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane tabLists;

    @FXML
    private Tab patientTab;

    @FXML
    private Tab queueTab;

    @FXML
    private Tab appointmentTab;

    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setWindowDefaultSize(prefs);

        setAccelerators();
        registerAsAnEventHandler(this);

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        setUpTab();

        browserPanel = new BrowserPanel();
        analyticsDisplay = new AnalyticsDisplay();
        analyticsDisplay.setVisible(false);

        browserPlaceholder.setAlignment(Pos.TOP_CENTER);
        browserPlaceholder.getChildren().add(browserPanel.getRoot());
        browserPlaceholder.getChildren().add(analyticsDisplay.getRoot());

        setUpListPanel();

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter();
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Set up tabs and tab pane in {@code MainWindow.fxml}
     */
    private void setUpTab() {
        patientTab.setContent(patientListPanelPlaceholder);
        patientTab.setClosable(false);

        appointmentTab.setContent(appointmentListPanelPlaceholder);
        appointmentTab.setClosable(false);

        queueTab.setContent(queuePanelPlaceholder);
        queueTab.setClosable(false);

        tabLists = new TabPane(patientTab, appointmentTab, queueTab);
    }

    /**
     * Set up all the list panel in {@code MainWindow}.
     */
    private void setUpListPanel() {
        patientListPanel = new PatientListPanel(logic.getFilteredPatientList());
        patientListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());

        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList());
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());

        queuePanel = new QueuePanel(logic.getAllPatientsInQueue());
        queuePanelPlaceholder.getChildren().add(queuePanel.getRoot());
    }

    //@@author iamjackslayer
    /**
     * Switches the current tab to the tab of given index.
     * @param index The index position of the tab
     */
    public void switchTab(int index) {
        tabLists.getSelectionModel().clearAndSelect(index);
    }

    void hide() {
        primaryStage.hide();
    }

    public void setPatientListPanel(ObservableList<Patient> list) {
        patientListPanel = new PatientListPanel(list);
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    //@@author jjlee050
    /**
     * Set full screen in exclusive mode.
     */
    void viewInFullScreen() {
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PatientListPanel getPatientListPanel() {
        return patientListPanel;
    }

    @Subscribe
    private void handleAnalyticsDisplayEvent(AnalyticsDisplayEvent event) {
        analyticsDisplay.setVisible(true);
        browserPanel.setVisible(false);
    }

    @Subscribe
    private void handleAppointmentPanelSelectionChangedEvent(AppointmentPanelSelectionChangedEvent event) {
        analyticsDisplay.setVisible(false);
        browserPanel.setVisible(true);
    }

    @Subscribe
    private void handlePatientPanelSelectionChangedEvent(PatientPanelSelectionChangedEvent event) {
        analyticsDisplay.setVisible(false);
        browserPanel.setVisible(true);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }
}
