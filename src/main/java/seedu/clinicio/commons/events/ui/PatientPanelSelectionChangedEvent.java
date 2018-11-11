package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;
import seedu.clinicio.model.patient.Patient;

/**
 * Represents a selection change in the Person List Panel
 */
public class PatientPanelSelectionChangedEvent extends BaseEvent {


    private final Patient newSelection;

    public PatientPanelSelectionChangedEvent(Patient newSelection) {
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
