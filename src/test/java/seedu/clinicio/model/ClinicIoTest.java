package seedu.clinicio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PASSWORD_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import static seedu.clinicio.model.staff.Role.DOCTOR;

import static seedu.clinicio.testutil.Assert.assertThrows;
import static seedu.clinicio.testutil.TypicalPersons.ADAM;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.ALICE;
import static seedu.clinicio.testutil.TypicalPersons.AMY_APPT;
import static seedu.clinicio.testutil.TypicalPersons.BENSON_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CARL_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CHLORPHENIRAMINE;
import static seedu.clinicio.testutil.TypicalPersons.ORACORT;
import static seedu.clinicio.testutil.TypicalPersons.PARACETAMOL;
import static seedu.clinicio.testutil.TypicalPersons.VENTOLIN;
import static seedu.clinicio.testutil.TypicalPersons.getTypicalClinicIo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.exceptions.AppointmentClashException;
import seedu.clinicio.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.exceptions.DuplicateMedicineException;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.patient.exceptions.DuplicatePatientException;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.exceptions.DuplicatePersonException;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.staff.exceptions.DuplicateStaffException;
import seedu.clinicio.model.staff.exceptions.StaffNotFoundException;

import seedu.clinicio.testutil.AppointmentBuilder;
import seedu.clinicio.testutil.MedicineBuilder;
import seedu.clinicio.testutil.PatientBuilder;
import seedu.clinicio.testutil.PersonBuilder;
import seedu.clinicio.testutil.StaffBuilder;

public class ClinicIoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final ClinicIo clinicIo = new ClinicIo();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), clinicIo.getPersonList());
        assertEquals(Collections.emptyList(), clinicIo.getPatientList());
        assertEquals(Collections.emptyList(), clinicIo.getStaffList());
        assertEquals(Collections.emptyList(), clinicIo.getMedicineList());
        assertEquals(Collections.emptyList(), clinicIo.getAppointmentList());
        assertEquals(Collections.emptyList(), clinicIo.getConsultationList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyClinicIo_replacesData() {
        ClinicIo newData = getTypicalClinicIo();
        clinicIo.resetData(newData);
        assertEquals(newData, clinicIo);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Appointment> newAppointments = Arrays.asList(BENSON_APPT, AMY_APPT);
        List<Medicine> newMedicines = Arrays.asList(PARACETAMOL, VENTOLIN, CHLORPHENIRAMINE, ORACORT);
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons,
                new ArrayList<>(), new ArrayList<>(), newMedicines);

        thrown.expect(DuplicatePersonException.class);
        clinicIo.resetData(newData);
    }

    @Test
    public void resetData_withDuplicatePatients_throwsDuplicatePatientException() {
        // Two persons with the same identity fields
        Patient editedAlex = new PatientBuilder(ALEX).withAddress(VALID_ADDRESS_BOB)
                .build();
        List<Patient> newPatients = Arrays.asList(ALEX, editedAlex);
        List<Medicine> newMedicines = Arrays.asList(PARACETAMOL, VENTOLIN, CHLORPHENIRAMINE, ORACORT);
        ClinicIoStub newData = new ClinicIoStub(new ArrayList<>(), new ArrayList<>(),
                newPatients, new ArrayList<>(), newMedicines);

        thrown.expect(DuplicatePatientException.class);
        clinicIo.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateStaffs_throwsDuplicateStaffException() {
        // Two staff with the same identity fields
        List<Appointment> newAppointments = Arrays.asList(BENSON_APPT, AMY_APPT);
        List<Medicine> newMedicines = Arrays.asList(PARACETAMOL, VENTOLIN, CHLORPHENIRAMINE, ORACORT);
        Staff editedAdam = new StaffBuilder(ADAM).withName(VALID_NAME_ADAM).build();
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Staff> newStaffs = Arrays.asList(ADAM, editedAdam);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons,
                new ArrayList<>(), newStaffs, newMedicines);
        thrown.expect(DuplicateStaffException.class);
        clinicIo.resetData(newData);
    }

    //@@author gingivitiss
    @Test
    public void resetData_withDuplicateAppointments_throwsDuplicateAppointmentException() {
        //Two appointments with the same identity fields
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withPatient(ALEX).build();
        List<Appointment> newAppointments = Arrays.asList(AMY_APPT, editedAmy);
        List<Medicine> newMedicines = Arrays.asList(PARACETAMOL, VENTOLIN, CHLORPHENIRAMINE, ORACORT);
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Staff> newStaffs = Arrays.asList(ADAM);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons, new ArrayList<>(), newStaffs,
                newMedicines);
        thrown.expect(DuplicateAppointmentException.class);
        clinicIo.resetData(newData);
    }

    @Test
    public void resetData_withClashingAppointments_throwsClashingAppointmentException() {
        //Two appointments with clashing slots
        Appointment clashingAppt = new AppointmentBuilder(CARL_APPT)
                .withTime(13, 30).build();
        List<Appointment> newAppointments = Arrays.asList(AMY_APPT, clashingAppt);
        List<Medicine> newMedicines = Arrays.asList(PARACETAMOL, VENTOLIN, CHLORPHENIRAMINE, ORACORT);
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Staff> newStaffs = Arrays.asList(ADAM);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons, new ArrayList<>(), newStaffs,
                newMedicines);
        thrown.expect(AppointmentClashException.class);
        clinicIo.resetData(newData);
    }

    //@@author aaronseahyh
    @Test
    public void resetData_withDuplicateMedicines_throwsDuplicateMedicineException() {
        //Two medicines with the same identity fields
        List<Appointment> newAppointments = Arrays.asList(AMY_APPT);
        Medicine editedMedicine = new MedicineBuilder(PARACETAMOL).withMedicinePrice(VALID_PRICE_PARACETAMOL)
                .build();
        List<Medicine> newMedicines = Arrays.asList(editedMedicine, PARACETAMOL, VENTOLIN, CHLORPHENIRAMINE, ORACORT);
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Staff> newStaffs = Arrays.asList(ADAM);
        ClinicIoStub newData = new ClinicIoStub(newAppointments, newPersons, new ArrayList<>(), newStaffs,
                newMedicines);
        thrown.expect(DuplicateMedicineException.class);
        clinicIo.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasPerson(null);
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasPatient(null);
    }

    @Test
    public void hasStaff_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasStaff(null);
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasAppointment(null);
    }

    //@@author aaronseahyh
    @Test
    public void hasMedicine_nullMedicine_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasMedicine(null);
    }

    @Test
    public void hasConsultation_nullConsultation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.hasConsultation(null);
    }

    @Test
    public void hasPerson_personNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasPerson(ALICE));
    }

    @Test
    public void hasPatient_patientNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasPatient(ALEX));
    }

    @Test
    public void hasStaff_staffNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasStaff(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasAppointment(AMY_APPT));
    }

    //@@author aaronseahyh
    @Test
    public void hasMedicine_medicineNotInClinicIo_returnsFalse() {
        assertFalse(clinicIo.hasMedicine(ORACORT));
    }

    // TODO: Add consultation test case

    @Test
    public void hasPerson_personInClinicIo_returnsTrue() {
        clinicIo.addPerson(ALICE);
        assertTrue(clinicIo.hasPerson(ALICE));
    }

    @Test
    public void hasPatient_patientInClinicIo_returnsTrue() {
        clinicIo.addPatient(ALEX);
        assertTrue(clinicIo.hasPatient(ALEX));
    }

    @Test
    public void hasStaff_staffInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        assertTrue(clinicIo.hasStaff(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentInClinicIo_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        assertTrue(clinicIo.hasAppointment(AMY_APPT));
    }

    //@@author aaronseahyh
    @Test
    public void hasMedicine_medicineInClinicIo_returnsTrue() {
        clinicIo.addMedicine(PARACETAMOL);
        assertTrue(clinicIo.hasMedicine(PARACETAMOL));
    }

    // TODO: Add consultation test case

    @Test
    public void hasPerson_personWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(clinicIo.hasPerson(editedAlice));
    }

    @Test
    public void hasPatient_patientWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addPatient(ALEX);
        Patient editedAlex = new PatientBuilder(ALEX).withNric(VALID_NRIC_ALEX)
                .build();
        assertTrue(clinicIo.hasPatient(editedAlex));
    }

    @Test
    public void hasStaff_staffWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addStaff(ADAM);
        Staff editedAdam = new StaffBuilder(ADAM).withRole(Role.RECEPTIONIST)
                .build();
        assertTrue(clinicIo.hasStaff(editedAdam));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(13, 00).build();
        assertTrue(clinicIo.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointment_appointmentWithDifferentIdentityFieldsInClinicIo_returnsFalse() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment editedAmy = new AppointmentBuilder(AMY_APPT).withTime(12, 00).build();
        assertFalse(clinicIo.hasAppointment(editedAmy));
    }

    @Test
    public void hasAppointmentClash_appointmentWithSameTimingsInClinicIo_returnsTrue() {
        clinicIo.addAppointment(AMY_APPT);
        Appointment newAppt = new AppointmentBuilder(CARL_APPT).withTime(13, 00).build();
        assertTrue(clinicIo.hasAppointmentClash(newAppt));
    }

    //@@author aaronseahyh
    @Test
    public void hasMedicine_medicineWithSameIdentityFieldsInClinicIo_returnsTrue() {
        clinicIo.addMedicine(PARACETAMOL);
        Medicine editedParacetamol = new MedicineBuilder(PARACETAMOL).withMedicinePrice(VALID_PRICE_ORACORT)
                .build();
        assertTrue(clinicIo.hasMedicine(editedParacetamol));
    }

    // TODO: Add consultation test case

    //@@author jjlee050
    @Test
    public void checkStaffCredentials_nullStaff_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        clinicIo.checkStaffCredentials(null);
    }

    @Test
    public void checkStaffCredentials_staffNotInClinicIo_throwStaffNotFoundException() {
        Staff staffToCheck = new Staff(DOCTOR, new Name(VALID_NAME_ADAM),
                new Password(VALID_PASSWORD_ADAM, false));
        assertThrows(StaffNotFoundException.class, () -> clinicIo.checkStaffCredentials(staffToCheck));
    }

    @Test
    public void checkStaffCredentials_invalidStaff_returnFalse() {
        clinicIo.addStaff(ADAM);
        assertFalse(clinicIo.checkStaffCredentials(ADAM));
    }

    @Test
    public void checkStaffCredentials_validStaff_returnTrue() {
        clinicIo.addStaff(ADAM);
        Staff staffToCheck = new Staff(DOCTOR, new Name(VALID_NAME_ADAM),
                new Password(VALID_PASSWORD_ADAM, false));
        assertTrue(clinicIo.checkStaffCredentials(staffToCheck));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getPersonList().remove(0);
    }

    @Test
    public void getPatientList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getPatientList().remove(0);
    }

    @Test
    public void getStaffList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getStaffList().remove(0);
    }

    //@@author gingivitiss
    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getAppointmentList().remove(0);
    }

    //@@author aaronseahyh
    @Test
    public void getMedicineList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getMedicineList().remove(0);
    }

    @Test
    public void getConsultationList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        clinicIo.getConsultationList().remove(0);
    }

    /**
     * A stub ReadOnlyClinicIo whose persons list and staff list can violate interface constraints.
     */
    private static class ClinicIoStub implements ReadOnlyClinicIo {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        private final ObservableList<Patient> patients = FXCollections.observableArrayList();
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Staff> staffs = FXCollections.observableArrayList();
        private final ObservableList<Medicine> medicines = FXCollections.observableArrayList();

        ClinicIoStub(Collection<Appointment> appointments, Collection<Person> persons,
                Collection<Patient> patients, Collection<Staff> staffs, Collection<Medicine> medicines) {
            this.appointments.setAll(appointments);
            this.persons.setAll(persons);
            this.patients.setAll(patients);
            this.staffs.setAll(staffs);
            this.medicines.setAll(medicines);
        }

        @Override
        public ObservableList<Patient> getQueue() {
            return patients;
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Patient> getPatientList() {
            return patients;
        }

        @Override
        public ObservableList<Staff> getStaffList() {
            return staffs;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }

        @Override
        public ObservableList<Medicine> getMedicineList() {
            return medicines;
        }

    }

}
