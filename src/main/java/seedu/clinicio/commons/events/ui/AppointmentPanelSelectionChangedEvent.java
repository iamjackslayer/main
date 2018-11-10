package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;
import seedu.clinicio.model.appointment.Appointment;

/**
 * Represents a selection change in the Appointment List Panel.
 */
public class AppointmentPanelSelectionChangedEvent extends BaseEvent {

    private final Appointment newSelection;

    public AppointmentPanelSelectionChangedEvent(Appointment newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Appointment getNewSelection() {
        return newSelection;
    }
}
