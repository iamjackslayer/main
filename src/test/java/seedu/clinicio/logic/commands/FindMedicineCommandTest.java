package seedu.clinicio.logic.commands;

//@@author aaronseahyh

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.commons.core.Messages.MESSAGE_MEDICINE_OVERVIEW;
import static seedu.clinicio.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.clinicio.testutil.TypicalPersons.ORACORT;
import static seedu.clinicio.testutil.TypicalPersons.PARACETAMOL;
import static seedu.clinicio.testutil.TypicalPersons.VENTOLIN;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.ModelManager;
import seedu.clinicio.model.UserPrefs;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.medicine.MedicineNameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindMedicineCommandTest {

    private Model model = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinicIo(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private Analytics analytics = new Analytics();

    @Test
    public void equals() {
        MedicineNameContainsKeywordsPredicate firstPredicate =
                new MedicineNameContainsKeywordsPredicate(Collections.singletonList("first"));
        MedicineNameContainsKeywordsPredicate secondPredicate =
                new MedicineNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindMedicineCommand findFirstMedicineCommand = new FindMedicineCommand(firstPredicate);
        FindMedicineCommand findSecondMedicineCommand = new FindMedicineCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstMedicineCommand.equals(findFirstMedicineCommand));

        // same values -> returns true
        FindMedicineCommand findFirstMedicineCommandCopy = new FindMedicineCommand(firstPredicate);
        assertTrue(findFirstMedicineCommand.equals(findFirstMedicineCommandCopy));

        // different types -> returns false
        assertFalse(findFirstMedicineCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstMedicineCommand == null);

        // different medicine -> returns false
        assertFalse(findFirstMedicineCommand.equals(findSecondMedicineCommand));
    }

    @Test
    public void execute_zeroKeywords_noMedicineFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINE_OVERVIEW, 0);
        MedicineNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindMedicineCommand command = new FindMedicineCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);
        assertEquals(Collections.emptyList(), model.getFilteredMedicineList());
    }

    @Test
    public void execute_multipleKeywords_multipleMedicinesFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINE_OVERVIEW, 3);
        MedicineNameContainsKeywordsPredicate predicate = preparePredicate("Paracetamol Oracort Ventolin");
        FindMedicineCommand command = new FindMedicineCommand(predicate);
        expectedModel.updateFilteredMedicineList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel, analytics);
        assertEquals(Arrays.asList(PARACETAMOL, ORACORT, VENTOLIN), model.getFilteredMedicineList());
    }

    /**
     * Parses {@code userInput} into a {@code MedicineNameContainsKeywordsPredicate}.
     */
    private MedicineNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new MedicineNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
