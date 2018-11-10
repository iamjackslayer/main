package seedu.clinicio.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.AMY_APPT;
import static seedu.clinicio.testutil.TypicalPersons.BEN;
import static seedu.clinicio.testutil.TypicalPersons.BRYAN;
import static seedu.clinicio.testutil.TypicalPersons.CANDY;
import static seedu.clinicio.testutil.TypicalPersons.DAISY;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.clinicio.testutil.PatientBuilder;

public class PatientTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Patient patient = new PatientBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        patient.getMedicalProblems().remove(0);
    }

    @Test
    public void isSamePatient() {

        // same name, different preferred staff -> returns false
        Patient editedAlexWithPreferredDoctor = new PatientBuilder(ALEX)
                .withPreferredDoctor(ADAM).build();
        Patient editedAlexWithAnotherPreferredDoctor = new PatientBuilder(ALEX)
                .withPreferredDoctor(BEN).build();
        assertFalse(editedAlexWithPreferredDoctor
                .isSamePatient(editedAlexWithAnotherPreferredDoctor));

        // same name, one with preferred staff, one without -> returns false
        editedAlexWithPreferredDoctor = new PatientBuilder(ALEX)
                .withPreferredDoctor(DAISY).build();
        Patient editedAlexWithoutPreferredDoctor = new PatientBuilder(ALEX).build();
        assertFalse(editedAlexWithPreferredDoctor.isSamePatient(editedAlexWithoutPreferredDoctor));

        // same name, same preferred staff, different attributes -> returns true
        Patient editedAlexWithPreferredDoctorAndBobAddress = new PatientBuilder(ALEX)
                .withPreferredDoctor(ADAM)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        Patient editedAlexWithPreferredDoctorAndAliceAddress = new PatientBuilder(ALEX)
                .withPreferredDoctor(ADAM)
                .withAddress(ALICE.getAddress().toString())
                .build();
        assertTrue(editedAlexWithPreferredDoctorAndBobAddress
                .isSamePatient(editedAlexWithPreferredDoctorAndAliceAddress));

        // same name, different appointment -> returns false
        /*Patient editedAlexWithAppointment = new PatientBuilder(ALEX)
                .withAppointment(AMY_APPT).build();
        Patient editedAlexWithAnotherAppointment = new PatientBuilder(ALEX)
                .withAppointment(BENSON_APPT).build();
        assertFalse(editedAlexWithAppointment.isSamePatient(editedAlexWithAnotherAppointment));*/

        // same name, one with appointment, one without -> returns false
        // TODO: Add back later
        /*editedAlexWithAppointment = new PatientBuilder(ALEX).withAppointment(AMY_APPT).build();
        Patient editedAlexWithoutAppointment = new PatientBuilder(ALEX).build();
        assertFalse(editedAlexWithAppointment.isSamePatient(editedAlexWithoutAppointment));*/

        // same name, same appointment, different attributes -> returns true
        /*Patient editedAlexWithAppointmentAndAmyAddress = new PatientBuilder(ALEX)
                .withAppointment(AMY_APPT)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        Patient editedAlexWithAppointmentAndBobAddress = new PatientBuilder(ALEX)
                .withAppointment(AMY_APPT)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        assertTrue(editedAlexWithAppointmentAndAmyAddress
                .isSamePatient(editedAlexWithAppointmentAndBobAddress));*/
    }

    @Test
    public void equals() {
        // same values -> returns true
        Patient alexCopy = new PatientBuilder(ALEX).build();
        assertTrue(ALEX.equals(alexCopy));

        // same object -> returns true
        assertTrue(ALEX.equals(ALEX));

        // different type -> returns false
        assertFalse(ALEX.equals(5));

        // different person -> returns false
        assertFalse(ALEX.equals(BRYAN));

        // different name -> returns false
        Patient editedAlex = new PatientBuilder(ALEX).withName(VALID_NAME_BOB).build();
        assertFalse(ALEX.equals(editedAlex));

        // different phone -> returns false
        editedAlex = new PatientBuilder(ALEX).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALEX.equals(editedAlex));

        // different email -> returns false
        editedAlex = new PatientBuilder(ALEX).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALEX.equals(editedAlex));

        // different address -> returns false
        editedAlex = new PatientBuilder(ALEX).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALEX.equals(editedAlex));

        // different nric -> returns false
        Patient editedAlexWithNric = new PatientBuilder(ALEX)
                .withNric(VALID_NRIC_ALEX).build();
        Patient editedAlexWithAnotherNric = new PatientBuilder(ALEX)
                .withNric(VALID_NRIC_BRYAN).build();
        assertFalse(editedAlexWithNric.equals(editedAlexWithAnotherNric));

        // different medical problems -> returns false
        Patient editedAlexWithMedProbs = new PatientBuilder(ALEX)
                .withMedicalProblems("Diabetes").build();
        Patient editedAlexWithAnotherMedProbs = new PatientBuilder(ALEX)
                .withMedicalProblems("Stroke", "Gout").build();
        assertFalse(editedAlexWithMedProbs.equals(editedAlexWithAnotherMedProbs));

        // different medications -> returns false
        Patient editedAlexWithMeds = new PatientBuilder(ALEX)
                .withMedications("Cough Syrup").build();
        Patient editedAlexWithAnotherMeds = new PatientBuilder(ALEX)
                .withMedications("Pantanomine", "Ibuprofen").build();
        assertFalse(editedAlexWithMeds.equals(editedAlexWithAnotherMeds));

        // different medications -> returns false
        Patient editedAlexWithAllergies = new PatientBuilder(ALEX)
                .withAllergies("Vegetables").build();
        Patient editedAlexWithAnotherAllergies = new PatientBuilder(ALEX)
                .withAllergies("Diary Products", "bee stings").build();
        assertFalse(editedAlexWithAllergies.equals(editedAlexWithAnotherAllergies));

        // different preferred doctor -> returns false
        Patient editedAlexWithPreferredDoctor = new PatientBuilder(ALEX)
                .withPreferredDoctor(ADAM).build();
        Patient editedAlexWithAnotherPreferredDoctor = new PatientBuilder(ALEX)
                .withPreferredDoctor(BEN).build();
        assertFalse(editedAlexWithPreferredDoctor.equals(editedAlexWithAnotherPreferredDoctor));

        // different appointment -> returns false
        /*Patient editedAlexWithAppointment = new PatientBuilder(ALEX)
                .withAppointment(AMY_APPT).build();
        Patient editedAlexWithAnotherAppointment = new PatientBuilder(ALEX)
                .withAppointment(BENSON_APPT).build();
        assertFalse(editedAlexWithAppointment.equals(editedAlexWithAnotherAppointment));*/
    }

    @Test
    public void hasPreferredDoctor() {
        // patient without preferred staff -> returns false
        assertFalse(CANDY.hasPreferredDoctor());

        // patient with a preferred staff -> returns true
        Patient alexWithPreferredDoctor = ALEX;
        alexWithPreferredDoctor.setPreferredDoctor(BEN);
        assertTrue(alexWithPreferredDoctor.hasPreferredDoctor());
    }

    @Test
    public void hasAppointment() {
        // patient without an appointment -> returns false
        assertFalse(ALEX.hasAppointment());

        // patient with an appointment -> returns true
        Patient amyAsPatientWithAppointment = ALEX;
        amyAsPatientWithAppointment.setAppointment(AMY_APPT);
        assertTrue(amyAsPatientWithAppointment.hasAppointment());
    }
}
