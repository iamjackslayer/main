package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EFFECTIVEDOSAGE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_LETHALDOSAGE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_QUANTITY_PARACETAMOL;
import static seedu.clinicio.testutil.TypicalPersons.ORACORT;
import static seedu.clinicio.testutil.TypicalPersons.PARACETAMOL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.testutil.MedicineBuilder;

public class MedicineTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameMedicine() {
        // same object -> returns true
        assertTrue(PARACETAMOL.isSameMedicine(PARACETAMOL));

        // null -> returns false
        assertFalse(PARACETAMOL.isSameMedicine(null));

        // different medicine name, type, ed, ld -> returns false
        Medicine editedOracort = new MedicineBuilder(ORACORT).withMedicineName(VALID_MEDICINENAME_PARACETAMOL)
                .withMedicineType(VALID_MEDICINETYPE_PARACETAMOL).withEffectiveDosage(VALID_EFFECTIVEDOSAGE_PARACETAMOL)
                .withLethalDosage(VALID_LETHALDOSAGE_PARACETAMOL).build();
        assertFalse(ORACORT.isSameMedicine(editedOracort));

        // same name, different type, ed, ld -> returns false
        editedOracort = new MedicineBuilder(ORACORT).withMedicineType(VALID_MEDICINETYPE_PARACETAMOL)
                .withEffectiveDosage(VALID_EFFECTIVEDOSAGE_PARACETAMOL).withLethalDosage(VALID_LETHALDOSAGE_PARACETAMOL)
                .build();
        assertFalse(ORACORT.isSameMedicine(editedOracort));

        // different medicine name -> returns false
        editedOracort = new MedicineBuilder(ORACORT).withMedicineName(VALID_MEDICINENAME_PARACETAMOL).build();
        assertFalse(ORACORT.isSameMedicine(editedOracort));

        // same medicine name, same type, same ed and ld, different price, different quantity -> returns true
        editedOracort = new MedicineBuilder(ORACORT).withMedicinePrice(VALID_PRICE_PARACETAMOL)
                .withMedicineQuantity(VALID_QUANTITY_PARACETAMOL).build();
        assertTrue(editedOracort.isSameMedicine(editedOracort));

        // same medicine name, same type, same ed and ld, same price, different quantity -> returns true
        editedOracort = new MedicineBuilder(ORACORT).withMedicineQuantity(VALID_QUANTITY_PARACETAMOL).build();
        assertTrue(editedOracort.isSameMedicine(editedOracort));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Medicine paracetamolCopy = new MedicineBuilder(PARACETAMOL).build();
        assertTrue(PARACETAMOL.equals(paracetamolCopy));

        // same object -> returns true
        assertTrue(PARACETAMOL.equals(PARACETAMOL));

        // null -> returns false
        assertFalse(PARACETAMOL.equals(null));

        // different type -> returns false
        assertFalse(PARACETAMOL.equals(5));

        // different medicine -> returns false
        assertFalse(PARACETAMOL.equals(ORACORT));

        // different medicine name -> returns false
        Medicine editedOracort = new MedicineBuilder(ORACORT).withMedicineName(VALID_MEDICINENAME_PARACETAMOL).build();
        assertFalse(ORACORT.equals(editedOracort));

        // different medicine type -> returns false
        editedOracort = new MedicineBuilder(ORACORT).withMedicineType(VALID_MEDICINETYPE_PARACETAMOL).build();
        assertFalse(ORACORT.equals(editedOracort));

        // different effective dosage -> returns false
        editedOracort = new MedicineBuilder(ORACORT).withEffectiveDosage(VALID_EFFECTIVEDOSAGE_PARACETAMOL).build();
        assertFalse(ORACORT.equals(editedOracort));

        // different lethal dosage -> returns false
        editedOracort = new MedicineBuilder(ORACORT).withLethalDosage(VALID_LETHALDOSAGE_PARACETAMOL).build();
        assertFalse(ORACORT.equals(editedOracort));

        // different price -> returns false
        editedOracort = new MedicineBuilder(ORACORT).withMedicinePrice(VALID_PRICE_PARACETAMOL).build();
        assertFalse(ORACORT.equals(editedOracort));

        // different quantity -> returns false
        editedOracort = new MedicineBuilder(ORACORT).withMedicineQuantity(VALID_QUANTITY_PARACETAMOL).build();
        assertFalse(ORACORT.equals(editedOracort));

    }

}
