package seedu.clinicio.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.core.UserSession;
import seedu.clinicio.commons.events.ui.JumpToListRequestEvent;
import seedu.clinicio.commons.events.ui.NewResultAvailableEvent;
import seedu.clinicio.commons.events.ui.PatientPanelSelectionChangedEvent;
import seedu.clinicio.model.patient.Patient;

/**
 * Panel containing the list of patients.
 */
public class PatientListPanel extends UiPart<Region> {
    private static final String FXML = "PatientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientListPanel.class);

    @FXML
    private ListView<Patient> patientListView;

    public PatientListPanel(ObservableList<Patient> patientList) {
        super(FXML);
        setConnections(patientList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Patient> patientList) {
        patientListView.setItems(patientList);
        patientListView.setCellFactory(listView -> new PatientListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        patientListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in patient list panel changed to : '" + newValue + "'");
                        raiseAppropriateEvents(newValue);
                    }
                });
    }

    /**
     * Ensure that user has the appropriate rights to view details.
     * @param newValue The selected patient.
     */
    private void raiseAppropriateEvents(Patient newValue) {
        if (UserSession.isLoginAsReceptionist()) {
            raise(new PatientPanelSelectionChangedEvent(newValue));
        } else {
            raise(new NewResultAvailableEvent("Please login as a receptionist to view more details", false));
        }
    }

    /**
     * Scrolls to the {@code PatientCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            patientListView.scrollTo(index);
            patientListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Patient} using a {@code PatientCard}.
     */
    class PatientListViewCell extends ListCell<Patient> {
        @Override
        protected void updateItem(Patient patient, boolean empty) {
            super.updateItem(patient, empty);

            if (empty || patient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PatientCard(patient, getIndex() + 1).getRoot());
            }
        }
    }

}
