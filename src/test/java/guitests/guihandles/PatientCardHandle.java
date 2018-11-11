package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.clinicio.model.patient.Patient;

/**
 * Provides a handle to a patient card in the patient list panel.
 */
public class PatientCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";

    private final Label idLabel;
    private final Label nameLabel;

    public PatientCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code patient}.
     */
    public boolean equals(Patient patient) {
        return getName().equals(patient.getName().fullName);
    }
}
