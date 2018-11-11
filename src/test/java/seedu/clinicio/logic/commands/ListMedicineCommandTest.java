package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListMedicineCommand.
 */
public class ListMedicineCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
        expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
    }

    @Test
    public void execute_medicineListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListMedicineCommand(), model, commandHistory, ListMedicineCommand.MESSAGE_SUCCESS,
                expectedModel, analytics);
    }

    @Test
    public void execute_medicineListIsFiltered_showsEverything() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);
        assertCommandSuccess(new ListMedicineCommand(), model, commandHistory, ListMedicineCommand.MESSAGE_SUCCESS,
                expectedModel, analytics);
    }

}
