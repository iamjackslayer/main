package seedu.clinicio.model.staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.CAT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.commons.util.HashUtil;
import seedu.clinicio.model.staff.exceptions.DuplicateStaffException;
import seedu.clinicio.model.staff.exceptions.StaffNotFoundException;

import seedu.clinicio.testutil.StaffBuilder;

//@@author jjlee050
public class UniqueStaffListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueStaffList uniqueStaffList = new UniqueStaffList();

    @Test
    public void contains_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.contains(null);
    }

    @Test
    public void contains_staffNotInList_returnsFalse() {
        assertFalse(uniqueStaffList.contains(ADAM));
    }

    @Test
    public void contains_staffInList_returnsTrue() {
        uniqueStaffList.add(ADAM);
        assertTrue(uniqueStaffList.contains(ADAM));
    }

    @Test
    public void contains_staffWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStaffList.add(ADAM);
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_ADAM)
                .build();
        assertTrue(uniqueStaffList.contains(editedAdam));
    }

    @Test
    public void add_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.add(null);
    }

    @Test
    public void add_duplicateStaff_throwsDuplicateStaffException() {
        uniqueStaffList.add(ADAM);
        thrown.expect(DuplicateStaffException.class);
        uniqueStaffList.add(ADAM);
    }

    @Test
    public void setStaff_nullTargetStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaff(null, ADAM);
    }

    @Test
    public void setStaff_nullEditedStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaff(ADAM, null);
    }

    @Test
    public void setStaff_targetStaffNotInList_throwsStaffNotFoundException() {
        thrown.expect(StaffNotFoundException.class);
        uniqueStaffList.setStaff(ADAM, ADAM);
    }

    @Test
    public void setStaff_editedStaffIsSameStaff_success() {
        uniqueStaffList.add(ADAM);
        uniqueStaffList.setStaff(ADAM, ADAM);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(ADAM);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasSameIdentity_success() {
        uniqueStaffList.add(ADAM);
        Staff editedAdam = new StaffBuilder(ADAM).withPassword(HashUtil.hashToString(VALID_PASSWORD_ADAM), true)
                .build();
        uniqueStaffList.setStaff(ADAM, editedAdam);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(editedAdam);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasDifferentIdentity_success() {
        uniqueStaffList.add(ADAM);
        uniqueStaffList.setStaff(ADAM, BEN);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(BEN);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaff_editedStaffHasNonUniqueIdentity_throwsDuplicateStaffException() {
        uniqueStaffList.add(ADAM);
        uniqueStaffList.add(BEN);
        thrown.expect(DuplicateStaffException.class);
        uniqueStaffList.setStaff(ADAM, BEN);
    }

    @Test
    public void remove_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.remove(null);
    }

    @Test
    public void remove_staffDoesNotExist_throwsStaffNotFoundException() {
        thrown.expect(StaffNotFoundException.class);
        uniqueStaffList.remove(ADAM);
    }

    @Test
    public void remove_existingStaff_removesStaff() {
        uniqueStaffList.add(ADAM);
        uniqueStaffList.remove(ADAM);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_nullUniqueStaffList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaffs((UniqueStaffList) null);
    }

    @Test
    public void setStaffs_uniqueStaffList_replacesOwnListWithProvidedUniqueStaffList() {
        uniqueStaffList.add(ADAM);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(BEN);
        uniqueStaffList.setStaffs(expectedUniqueStaffList);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.setStaffs((List<Staff>) null);
    }

    @Test
    public void setStaffs_list_replacesOwnListWithProvidedList() {
        uniqueStaffList.add(ADAM);
        List<Staff> staffList = Collections.singletonList(BEN);
        uniqueStaffList.setStaffs(staffList);
        UniqueStaffList expectedUniqueStaffList = new UniqueStaffList();
        expectedUniqueStaffList.add(BEN);
        assertEquals(expectedUniqueStaffList, uniqueStaffList);
    }

    @Test
    public void setStaffs_listWithDuplicateStaffs_throwsDuplicateStaffException() {
        List<Staff> listWithDuplicateStaffs = Arrays.asList(ADAM, ADAM);
        thrown.expect(DuplicateStaffException.class);
        uniqueStaffList.setStaffs(listWithDuplicateStaffs);
    }

    @Test
    public void getStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueStaffList.getStaff(null);
    }

    @Test
    public void getStaff_cannotFindStaff_throwsStaffNotFoundException() {
        thrown.expect(StaffNotFoundException.class);
        uniqueStaffList.getStaff(CAT);
    }

    @Test
    public void getStaff_validStaff_returnStaff() {
        uniqueStaffList.add(BEN);
        assertNotNull(uniqueStaffList.getStaff(BEN));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueStaffList.asUnmodifiableObservableList().remove(0);
    }
}
