package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import org.junit.Before;
import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.testutil.MedicineBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddMedicineCommand}.
 */
public class AddMedicineCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    }

    @Test
    public void execute_newMedicine_success() {
        Medicine validMedicine = new MedicineBuilder().build();

        Model expectedModel = new ModelManager(model.getClinicIo(), new UserPrefs());
        expectedModel.addMedicine(validMedicine);
        expectedModel.commitClinicIo();

        assertCommandSuccess(new AddMedicineCommand(validMedicine), model, commandHistory,
                String.format(AddMedicineCommand.MESSAGE_SUCCESS, validMedicine), expectedModel, analytics);
    }

    @Test
    public void execute_duplicateMedicine_throwsCommandException() {
        Medicine medicineInList = model.getClinicIo().getMedicineList().get(0);
        assertCommandFailure(new AddMedicineCommand(medicineInList), model, commandHistory,
                analytics, AddMedicineCommand.MESSAGE_DUPLICATE_MEDICINE);
    }

}
