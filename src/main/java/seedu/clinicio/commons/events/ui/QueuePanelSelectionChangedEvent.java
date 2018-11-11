package seedu.clinicio.commons.events.ui;
//@@iamjackslayer
import seedu.clinicio.commons.events.BaseEvent;
import seedu.clinicio.model.patient.Patient;

/**
 * Represents a selection change in the Queue Panel.
 */
public class QueuePanelSelectionChangedEvent extends BaseEvent {

    private final Patient newSelection;

    public QueuePanelSelectionChangedEvent(Patient newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Patient getNewSelection() {
        return newSelection;
    }
}
