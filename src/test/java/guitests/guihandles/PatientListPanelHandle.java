package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.clinicio.model.patient.Patient;

/**
 * Provides a handle for {@code PatientListPanel} containing the list of {@code PatientCard}.
 */
public class PatientListPanelHandle extends NodeHandle<ListView<Patient>> {
    public static final String PATIENT_LIST_VIEW_ID = "#patientListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Patient> lastRememberedSelectedPatientCard;

    public PatientListPanelHandle(ListView<Patient> patientListPanelNode) {
        super(patientListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PatientCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PatientCardHandle getHandleToSelectedCard() {
        List<Patient> selectedPatientList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedPatientList.size() != 1) {
            throw new AssertionError("Patient list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(PatientCardHandle::new)
                .filter(handle -> handle.equals(selectedPatientList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Patient> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code patient}.
     */
    public void navigateToCard(Patient patient) {
        if (!getRootNode().getItems().contains(patient)) {
            throw new IllegalArgumentException("Patient does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(patient);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code PatientCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the patient card handle of a patient associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public PatientCardHandle getPatientCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(PatientCardHandle::new)
                .filter(handle -> handle.equals(getPatient(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Patient getPatient(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code PatientCard} in the list.
     */
    public void rememberSelectedPatientCard() {
        List<Patient> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedPatientCard = Optional.empty();
        } else {
            lastRememberedSelectedPatientCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PatientCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPatientCard()} call.
     */
    public boolean isSelectedPatientCardChanged() {
        List<Patient> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedPatientCard.isPresent();
        } else {
            return !lastRememberedSelectedPatientCard.isPresent()
                    || !lastRememberedSelectedPatientCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
