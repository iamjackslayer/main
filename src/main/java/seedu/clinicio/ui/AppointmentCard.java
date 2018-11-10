package seedu.clinicio.ui;

//@@gingivitiss

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.clinicio.model.appointment.Appointment;

/**
 * An UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {
    private static final String FXML = "AppointmentListCard.fxml";
    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label status;
    @FXML
    private Label type;
    @FXML
    private Label staff;

    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        date.setText(appointment.getAppointmentDate().toString());
        time.setText(appointment.getAppointmentTime().toString());
        name.setText(appointment.getPatient().getName().toString());
        status.setText(appointment.statusToString());
        type.setText(appointment.typeToString());
        if (!appointment.getAssignedStaff().isPresent()) {
            staff.setText("Doctor: None assigned");
        } else {
            staff.setText("Doctor: " + appointment.getAssignedStaff().get().toString());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        // state check
        AppointmentCard card = (AppointmentCard) other;
        return id.getText().equals(card.id.getText())
                && appointment.equals(card.appointment);
    }
}
