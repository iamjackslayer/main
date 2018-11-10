package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;

//@@author jjlee050
/**
 * Indicates that user logout successfully.
 */
public class LogoutClinicIoEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
