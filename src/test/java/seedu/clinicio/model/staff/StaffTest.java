package seedu.clinicio.model.staff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.BEN;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.clinicio.testutil.StaffBuilder;

//@@author jjlee050
public class StaffTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameStaff() {
        // same object -> returns true
        assertTrue(ADAM.isSameStaff(ADAM));

        // null -> returns false
        assertFalse(ADAM.isSameStaff(null));

        // different password and name -> returns false
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_BEN)
                .withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertFalse(ADAM.isSameStaff(editedAdam));

        // same password, different attributes -> returns false
        editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_BEN).build();
        assertFalse(ADAM.isSameStaff(editedAdam));

        // same name, different attributes -> returns true
        editedAdam = new StaffBuilder(ADAM)
                .withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertTrue(ADAM.isSameStaff(editedAdam));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Staff adamCopy = new StaffBuilder(ADAM).build();
        assertTrue(ADAM.equals(adamCopy));

        // same object -> returns true
        assertTrue(ADAM.equals(ADAM));

        // null -> returns false
        assertFalse(ADAM.equals(null));

        // different type -> returns false
        assertFalse(ADAM.equals(5));

        // different person -> returns false
        assertFalse(ADAM.equals(BEN));

        // different name -> returns false
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_BOB).build();
        assertFalse(ADAM.equals(editedAdam));

        // different password -> returns false
        editedAdam = new StaffBuilder(ADAM).withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertFalse(ADAM.equals(editedAdam));

    }
}
