package seedu.clinicio.logic.commands;

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.logic.commands.CommandTestUtil.deleteFirstMedicine;
import static seedu.clinicio.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();
    private final Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstMedicine(model);
        model.undoClinicIo();
        model.undoClinicIo();

        deleteFirstPerson(expectedModel);
        deleteFirstMedicine(expectedModel);
        expectedModel.undoClinicIo();
        expectedModel.undoClinicIo();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoClinicIo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
            analytics);

        // single redoable state in model
        expectedModel.redoClinicIo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
            analytics);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, analytics, RedoCommand.MESSAGE_FAILURE);
    }
}
