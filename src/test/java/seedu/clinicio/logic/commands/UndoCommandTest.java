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

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstMedicine(model);

        deleteFirstPerson(expectedModel);
        deleteFirstMedicine(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoClinicIo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
            analytics);

        // single undoable state in model
        expectedModel.undoClinicIo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
            analytics);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, analytics, UndoCommand.MESSAGE_FAILURE);
    }
}
