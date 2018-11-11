package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_THIRD_MEDICINE;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Rule;
import org.junit.Test;

import seedu.clinicio.commons.core.Messages;
import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.commons.events.ui.JumpToListRequestEvent;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectMedicineCommand}.
 */
public class SelectMedicineCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void execute_validIndexUnfilteredMedicineList_success() {
        Index lastMedicineIndex = Index.fromOneBased(model.getFilteredMedicineList().size());

        assertExecutionSuccess(INDEX_FIRST_MEDICINE);
        assertExecutionSuccess(INDEX_THIRD_MEDICINE);
        assertExecutionSuccess(lastMedicineIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredMedicineList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredMedicineList_success() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        showMedicineAtIndex(expectedModel, INDEX_FIRST_MEDICINE);

        assertExecutionSuccess(INDEX_FIRST_MEDICINE);
    }

    @Test
    public void execute_invalidIndexFilteredMedicineList_failure() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        showMedicineAtIndex(expectedModel, INDEX_FIRST_MEDICINE);

        Index outOfBoundsIndex = INDEX_SECOND_MEDICINE;
        // ensures that outOfBoundIndex is still in bounds of ClinicIO list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getClinicIo().getMedicineList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectMedicineCommand selectFirstMedicineCommand = new SelectMedicineCommand(INDEX_FIRST_MEDICINE);
        SelectMedicineCommand selectSecondMedicineCommand = new SelectMedicineCommand(INDEX_SECOND_MEDICINE);

        // same object -> returns true
        assertTrue(selectFirstMedicineCommand.equals(selectFirstMedicineCommand));

        // same values -> returns true
        SelectMedicineCommand selectFirstMedicineCommandCopy = new SelectMedicineCommand(INDEX_FIRST_MEDICINE);
        assertTrue(selectFirstMedicineCommand.equals(selectFirstMedicineCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstMedicineCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstMedicineCommand == null);

        // different medicine -> returns false
        assertFalse(selectFirstMedicineCommand.equals(selectSecondMedicineCommand));
    }

    /**
     * Executes a {@code SelectMedicineCommand} with the given {@code index},
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectMedicineCommand selectMedicineCommand = new SelectMedicineCommand(index);
        String expectedMessage = String.format(SelectMedicineCommand.MESSAGE_SELECT_MEDICINE_SUCCESS,
                index.getOneBased());

        assertCommandSuccess(selectMedicineCommand, model, commandHistory, expectedMessage, expectedModel, analytics);

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectMedicineCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectMedicineCommand selectMedicineCommand = new SelectMedicineCommand(index);
        assertCommandFailure(selectMedicineCommand, model, commandHistory, analytics, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

}
