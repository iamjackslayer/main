package seedu.clinicio.model.medicine;

//@@author aaronseahyh

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_QUANTITY_PARACETAMOL;
import static seedu.clinicio.testutil.TypicalPersons.ORACORT;
import static seedu.clinicio.testutil.TypicalPersons.PARACETAMOL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.model.medicine.exceptions.DuplicateMedicineException;
import seedu.clinicio.model.medicine.exceptions.MedicineNotFoundException;
import seedu.clinicio.testutil.MedicineBuilder;

public class UniqueMedicineListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMedicineList uniqueMedicineList = new UniqueMedicineList();

    @Test
    public void contains_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.contains(null);
    }

    @Test
    public void contains_medicineNotInList_returnsFalse() {
        assertFalse(uniqueMedicineList.contains(PARACETAMOL));
    }

    @Test
    public void contains_medicineInList_returnsTrue() {
        uniqueMedicineList.add(PARACETAMOL);
        assertTrue(uniqueMedicineList.contains(PARACETAMOL));
    }

    @Test
    public void contains_medicineWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMedicineList.add(ORACORT);
        Medicine editedOracort = new MedicineBuilder(ORACORT).withMedicinePrice(VALID_PRICE_PARACETAMOL)
                .withMedicineQuantity(VALID_QUANTITY_PARACETAMOL).build();
        assertTrue(uniqueMedicineList.contains(editedOracort));
    }

    @Test
    public void add_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.add(null);
    }

    @Test
    public void add_duplicateMedicine_throwsDuplicateMedicineException() {
        uniqueMedicineList.add(ORACORT);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.add(ORACORT);
    }

    @Test
    public void updateMedicineQuantity_nullTargetMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.updateMedicineQuantity(null, ORACORT.getQuantity());
    }

    @Test
    public void updateMedicineQuantity_nullUpdatedQuantity_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.updateMedicineQuantity(ORACORT, null);
    }

    @Test
    public void updateMedicineQuantity_targetMedicineNotInList_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.updateMedicineQuantity(ORACORT, PARACETAMOL.getQuantity());
    }

    @Test
    public void remove_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.remove(null);
    }

    @Test
    public void remove_medicineDoesNotExist_throwsMedicineNotFoundException() {
        thrown.expect(MedicineNotFoundException.class);
        uniqueMedicineList.remove(ORACORT);
    }

    @Test
    public void remove_existingMedicine_removesPerson() {
        uniqueMedicineList.add(ORACORT);
        uniqueMedicineList.remove(ORACORT);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_nullUniqueMedicineList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicines((UniqueMedicineList) null);
    }

    @Test
    public void setMedicines_uniqueMedicineList_replacesOwnListWithProvidedUniqueMedicineList() {
        uniqueMedicineList.add(ORACORT);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(PARACETAMOL);
        uniqueMedicineList.setMedicines(expectedUniqueMedicineList);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMedicineList.setMedicines((List<Medicine>) null);
    }

    @Test
    public void setMedicines_list_replacesOwnListWithProvidedList() {
        uniqueMedicineList.add(ORACORT);
        List<Medicine> medicineList = Collections.singletonList(PARACETAMOL);
        uniqueMedicineList.setMedicines(medicineList);
        UniqueMedicineList expectedUniqueMedicineList = new UniqueMedicineList();
        expectedUniqueMedicineList.add(PARACETAMOL);
        assertEquals(expectedUniqueMedicineList, uniqueMedicineList);
    }

    @Test
    public void setMedicines_listWithDuplicateMedicines_throwsDuplicateMedicineException() {
        List<Medicine> listWithDuplicateMedicines = Arrays.asList(ORACORT, ORACORT);
        thrown.expect(DuplicateMedicineException.class);
        uniqueMedicineList.setMedicines(listWithDuplicateMedicines);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMedicineList.asUnmodifiableObservableList().remove(0);
    }

}
