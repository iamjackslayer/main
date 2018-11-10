package seedu.clinicio.model.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_ALEX;

import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.BRYAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.model.patient.exceptions.DuplicatePatientException;

import seedu.clinicio.model.patient.exceptions.PatientNotFoundException;

import seedu.clinicio.testutil.PatientBuilder;

public class UniquePatientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniquePatientList uniquePatientList = new UniquePatientList();

    @Test
    public void contains_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.contains(null);
    }

    @Test
    public void contains_patientNotInList_returnsFalse() {
        assertFalse(uniquePatientList.contains(ALEX));
    }

    @Test
    public void contains_patientInList_returnsTrue() {
        uniquePatientList.add(ALEX);
        assertTrue(uniquePatientList.contains(ALEX));
    }

    @Test
    public void contains_patientWithSameIdentityFieldsInList_returnsTrue() {
        uniquePatientList.add(ALEX);
        Patient editedAlex = new PatientBuilder(ALEX).withNric(VALID_NRIC_ALEX)
                .build();
        assertTrue(uniquePatientList.contains(editedAlex));
    }

    @Test
    public void add_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.add(null);
    }

    @Test
    public void add_duplicatePatient_throwsDuplicatePatientException() {
        uniquePatientList.add(ALEX);
        thrown.expect(DuplicatePatientException.class);
        uniquePatientList.add(ALEX);
    }

    @Test
    public void setPatient_nullTargetPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatient(null, ALEX);
    }

    @Test
    public void setPatient_nullEditedPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatient(ALEX, null);
    }

    @Test
    public void setPatient_targetPatientNotInList_throwsPatientNotFoundException() {
        thrown.expect(PatientNotFoundException.class);
        uniquePatientList.setPatient(ALEX, ALEX);
    }

    @Test
    public void setPatient_editedPatientIsSamePatient_success() {
        uniquePatientList.add(ALEX);
        uniquePatientList.setPatient(ALEX, ALEX);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(ALEX);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasSameIdentity_success() {
        uniquePatientList.add(ALEX);
        Patient editedAlex = new PatientBuilder(ALEX).withNric(VALID_NRIC_ALEX)
                .build();
        uniquePatientList.setPatient(ALEX, editedAlex);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(editedAlex);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasDifferentIdentity_success() {
        uniquePatientList.add(ALEX);
        uniquePatientList.setPatient(ALEX, BRYAN);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BRYAN);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatient_editedPatientHasNonUniqueIdentity_throwsDuplicatePatientException() {
        uniquePatientList.add(ALEX);
        uniquePatientList.add(BRYAN);
        thrown.expect(DuplicatePatientException.class);
        uniquePatientList.setPatient(ALEX, BRYAN);
    }

    @Test
    public void remove_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.remove(null);
    }

    @Test
    public void remove_patientDoesNotExist_throwsPatientNotFoundException() {
        thrown.expect(PatientNotFoundException.class);
        uniquePatientList.remove(ALEX);
    }

    @Test
    public void remove_existingPatient_removesPatient() {
        uniquePatientList.add(ALEX);
        uniquePatientList.remove(ALEX);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_nullUniquePatientList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatients((UniquePatientList) null);
    }

    @Test
    public void setPatients_uniquePatientList_replacesOwnListWithProvidedUniquePatientList() {
        uniquePatientList.add(ALEX);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BRYAN);
        uniquePatientList.setPatients(expectedUniquePatientList);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniquePatientList.setPatients((List<Patient>) null);
    }

    @Test
    public void setPatients_list_replacesOwnListWithProvidedList() {
        uniquePatientList.add(ALEX);
        List<Patient> patientList = Collections.singletonList(BRYAN);
        uniquePatientList.setPatients(patientList);
        UniquePatientList expectedUniquePatientList = new UniquePatientList();
        expectedUniquePatientList.add(BRYAN);
        assertEquals(expectedUniquePatientList, uniquePatientList);
    }

    @Test
    public void setPatients_listWithDuplicatePatients_throwsDuplicatePatientException() {
        List<Patient> listWithDuplicatePatients = Arrays.asList(ALEX, ALEX);
        thrown.expect(DuplicatePatientException.class);
        uniquePatientList.setPatients(listWithDuplicatePatients);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniquePatientList.asUnmodifiableObservableList().remove(0);
    }

}
