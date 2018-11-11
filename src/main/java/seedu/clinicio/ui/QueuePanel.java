package seedu.clinicio.ui;

//@@iamjackslayer

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.JumpToListRequestEvent;
import seedu.clinicio.commons.events.ui.QueuePanelSelectionChangedEvent;
import seedu.clinicio.model.patient.Patient;

/**
 * A panel containing a list of appointments.
 */
public class QueuePanel extends UiPart<Region> {
    private static final String FXML = "QueuePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);

    @FXML
    private ListView<Patient> queueListView;

    public QueuePanel(ObservableList<Patient> patients) {
        super(FXML);
        setConnections(patients);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Patient> patients) {
        queueListView.setItems(patients);
        queueListView.setCellFactory(listView -> new QueueListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        queueListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in queue panel changed to : '" + newValue + "'");
                        raise(new QueuePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code AppointmentCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            queueListView.scrollTo(index);
            queueListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class QueueListViewCell extends ListCell<Patient> {
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
