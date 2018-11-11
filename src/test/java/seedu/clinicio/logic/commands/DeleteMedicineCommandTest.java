package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Test;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.medicine.Medicine;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteMedicineCommand}.
 */
public class DeleteMedicineCommandTest {

    private Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        DeleteMedicineCommand deleteMedicineCommand = new DeleteMedicineCommand(INDEX_FIRST_MEDICINE);

        String expectedMessage = String.format(DeleteMedicineCommand.MESSAGE_DELETE_MEDICINE_SUCCESS, medicineToDelete);

        ModelManager expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitClinicIo();

        assertCommandSuccess(deleteMedicineCommand, model, commandHistory, expectedMessage, expectedModel, analytics);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        DeleteMedicineCommand deleteMedicineCommand = new DeleteMedicineCommand(outOfBoundIndex);

        assertCommandFailure(deleteMedicineCommand, model, commandHistory, analytics,
                Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        DeleteMedicineCommand deleteMedicineCommand = new DeleteMedicineCommand(INDEX_FIRST_MEDICINE);

        String expectedMessage = String.format(DeleteMedicineCommand.MESSAGE_DELETE_MEDICINE_SUCCESS, medicineToDelete);

        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitClinicIo();
        showNoMedicine(expectedModel);

        assertCommandSuccess(deleteMedicineCommand, model, commandHistory, expectedMessage, expectedModel, analytics);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        Index outOfBoundIndex = INDEX_SECOND_MEDICINE;
        // ensures that outOfBoundIndex is still in bounds of ClinicIO list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getClinicIo().getMedicineList().size());

        DeleteMedicineCommand deleteMedicineCommand = new DeleteMedicineCommand(outOfBoundIndex);

        assertCommandFailure(deleteMedicineCommand, model, commandHistory, analytics,
                Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        DeleteMedicineCommand deleteMedicineCommand = new DeleteMedicineCommand(INDEX_FIRST_MEDICINE);
        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitClinicIo();

        // delete -> first medicine deleted
        deleteMedicineCommand.execute(model, commandHistory);

        // undo -> reverts ClinicIO back to previous state and filtered medicine list to show all medicines
        expectedModel.undoClinicIo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);

        // redo -> same first medicine deleted again
        expectedModel.redoClinicIo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        DeleteMedicineCommand deleteMedicineCommand = new DeleteMedicineCommand(outOfBoundIndex);

        // execution failed -> ClinicIO state not added into model
        assertCommandFailure(deleteMedicineCommand, model, commandHistory, analytics,
                Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        // single ClinicIO state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, analytics, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, analytics, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Medicine} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted medicine in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the medicine object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameMedicineDeleted() throws Exception {
        DeleteMedicineCommand deleteMedicineCommand = new DeleteMedicineCommand(INDEX_FIRST_MEDICINE);
        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());

        showMedicineAtIndex(model, INDEX_SECOND_MEDICINE);
        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitClinicIo();

        // delete -> deletes second medicine in unfiltered medicine list / first medicine in filtered medicine list
        deleteMedicineCommand.execute(model, commandHistory);

        // undo -> reverts ClinicIO back to previous state and filtered medicine list to show all medicines
        expectedModel.undoClinicIo();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);

        assertNotEquals(medicineToDelete, model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased()));
        // redo -> deletes same second medicine in unfiltered medicine list
        expectedModel.redoClinicIo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel,
                analytics);
    }

    @Test
    public void equals() {
        DeleteMedicineCommand deleteFirstMedicineCommand = new DeleteMedicineCommand(INDEX_FIRST_MEDICINE);
        DeleteMedicineCommand deleteSecondMedicineCommand = new DeleteMedicineCommand(INDEX_SECOND_MEDICINE);

        // same object -> returns true
        assertTrue(deleteFirstMedicineCommand.equals(deleteFirstMedicineCommand));

        // same values -> returns true
        DeleteMedicineCommand deleteFirstMedicineCommandCopy = new DeleteMedicineCommand(INDEX_FIRST_MEDICINE);
        assertTrue(deleteFirstMedicineCommand.equals(deleteFirstMedicineCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstMedicineCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstMedicineCommand == null);

        // different medicine -> returns false
        assertFalse(deleteFirstMedicineCommand.equals(deleteSecondMedicineCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no medicines.
     */
    private void showNoMedicine(Model model) {
        model.updateFilteredMedicineList(m -> false);

        assertTrue(model.getFilteredMedicineList().isEmpty());
    }

}
