package seedu.clinicio.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.NewResultAvailableEvent;
import seedu.clinicio.logic.ListElementPointer;
import seedu.clinicio.logic.Logic;
import seedu.clinicio.logic.commands.CommandResult;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.logic.parser.exceptions.ParseException;
import seedu.clinicio.ui.util.PasswordPrefixFormatter;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";

    private static final String FXML = "CommandBox.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;

    private ListElementPointer historySnapshot;
    private PasswordPrefixFormatter passwordFormatter;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
        passwordFormatter = new PasswordPrefixFormatter();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        String formattedText = "";

        // As up and down buttons will alter the position of the caret,
        // consuming it causes the caret's position to remain unchanged
        keyEvent.consume();

        switch (keyEvent.getCode()) {
        case UP:
            navigateToPreviousInput();
            formattedText = passwordFormatter.maskPassword(commandTextField.getText(), true, false);
            break;
        case DOWN:
            navigateToNextInput();
            formattedText = passwordFormatter.maskPassword(commandTextField.getText(), true, false);
            break;
        case LEFT: case RIGHT: case BACK_SPACE: case SPACE:
            formattedText = passwordFormatter.maskPassword(commandTextField.getText(), false, true);
            break;
        case ENTER:
            return;
        default:
            // let JavaFx handle the keypress
            formattedText = passwordFormatter.maskPassword(commandTextField.getText(), false, false);
            break;
        }

        replaceText(formattedText);
    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        passwordFormatter.resetTempPassword();
        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        passwordFormatter.resetTempPassword();
        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            replaceText(passwordFormatter.unmaskPassword(commandTextField.getText()));
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser, true));
        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            logger.info("Invalid command: " + commandTextField.getText());
            replaceText(passwordFormatter.maskPassword(commandTextField.getText(), false, false));
            setStyleToIndicateCommandFailure();
            raise(new NewResultAvailableEvent(e.getMessage(), false));
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
