package seedu.clinicio.testutil;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EFFECTIVEDOSAGE_CHLORPHENIRAMINE;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EFFECTIVEDOSAGE_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EFFECTIVEDOSAGE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EFFECTIVEDOSAGE_VENTOLIN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_ALAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_CAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_DAISY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_FRANK;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_LETHALDOSAGE_CHLORPHENIRAMINE;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_LETHALDOSAGE_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_LETHALDOSAGE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_LETHALDOSAGE_VENTOLIN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_CHLORPHENIRAMINE;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_VENTOLIN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_CHLORPHENIRAMINE;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_VENTOLIN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ALAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_CANDY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_CAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_DAISY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_FRANK;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_CANDY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_CHLORPHENIRAMINE;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_VENTOLIN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_QUANTITY_CHLORPHENIRAMINE;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_QUANTITY_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_QUANTITY_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_QUANTITY_VENTOLIN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.model.staff.Role.RECEPTIONIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Staff;

/**
 * A utility class containing a list of {@code Person} and {@code Medicine} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    // Manually added (Patient)
    /*public static final Patient ALICE_AS_PATIENT = PatientBuilder.buildFromPerson(ALICE)
            .build();
    public static final Patient BENSON_AS_PATIENT = PatientBuilder.buildFromPerson(BENSON)
            .build();
    public static final Patient CARL_AS_PATIENT = PatientBuilder.buildFromPerson(CARL)
            .build();
    public static final Patient DANIEL_AS_PATIENT = PatientBuilder.buildFromPerson(DANIEL)
            .build();
    public static final Patient ELLE_AS_PATIENT = PatientBuilder.buildFromPerson(ELLE)
            .build();
    public static final Patient FIONA_AS_PATIENT = PatientBuilder.buildFromPerson(FIONA)
            .build();
    public static final Patient GEORGE_AS_PATIENT = PatientBuilder.buildFromPerson(GEORGE)
            .build();
    public static final Patient AMY_AS_PATIENT = PatientBuilder.buildFromPerson(AMY)
            .build();*/

    // Manually added (Staff)
    public static final Staff ADAM = new StaffBuilder().withRole(DOCTOR).withName(VALID_NAME_ADAM)
            .withPassword(VALID_HASH_PASSWORD_ADAM, true).build();
    public static final Staff BEN = new StaffBuilder().withRole(DOCTOR).withName(VALID_NAME_BEN)
            .withPassword(VALID_HASH_PASSWORD_BEN, true).build();

    // Manually added (Staff without hash password)
    public static final Staff ADAM_1 = new StaffBuilder().withRole(DOCTOR).withName(VALID_NAME_ADAM)
            .withPassword("123456", false).build();
    public static final Staff BEN_1 = new StaffBuilder().withRole(DOCTOR).withName(VALID_NAME_BEN)
            .withPassword("123456", false).build();

    // Manually added (Patient)
    public static final Patient ALEX = new PatientBuilder().withName(VALID_NAME_ALEX).withNric(VALID_NRIC_ALEX)
            .withPhone(VALID_PHONE_ALEX).withEmail(VALID_EMAIL_ALEX).withAddress(VALID_ADDRESS_ALEX)
            .withPreferredDoctor(new Staff(DOCTOR, ADAM.getName(), new Password("123456", false))).build();
    public static final Patient BRYAN = new PatientBuilder().withName(VALID_NAME_BRYAN).withNric(VALID_NRIC_BRYAN)
            .withPhone(VALID_PHONE_BRYAN).withEmail(VALID_EMAIL_BRYAN).withAddress(VALID_ADDRESS_BRYAN)
            .withPreferredDoctor(new Staff(DOCTOR, ADAM.getName(), new Password("123456", false))).build();
    public static final Patient DANNY = new PatientBuilder().withName("Danny Shaw").withNric("S9741314I")
            .withPhone("90192910").withEmail("ds@live.com").withAddress("780, Woodlands Ave 12, #12-683")
            .withPreferredDoctor(new Staff(DOCTOR, ADAM.getName(), new Password("123456", false))).build();
    //Not inside ClinicIO
    public static final Patient CANDY = new PatientBuilder().withName(VALID_NAME_CANDY)
            .withNric(VALID_NRIC_CANDY).build();

    //Not inside ClinicIO
    public static final Staff CAT = new StaffBuilder().withRole(DOCTOR).withName(VALID_NAME_CAT)
            .withPassword(VALID_HASH_PASSWORD_CAT, true).build();

    public static final Staff ALAN = new StaffBuilder().withRole(RECEPTIONIST).withName(VALID_NAME_ALAN)
            .withPassword(VALID_HASH_PASSWORD_ALAN, true).build();
    public static final Staff FRANK = new StaffBuilder().withRole(RECEPTIONIST).withName(VALID_NAME_FRANK)
            .withPassword(VALID_HASH_PASSWORD_FRANK, true).build();

    //Not inside ClinicIO
    public static final Staff DAISY = new StaffBuilder().withRole(RECEPTIONIST).withName(VALID_NAME_DAISY)
            .withPassword(VALID_HASH_PASSWORD_DAISY, true).build();


    // Appointments
    public static final Appointment AMY_APPT = new AppointmentBuilder().withDate(2, 10, 2018)
            .withTime(13, 00).withType(1).withPatient(ALEX).withStaff(ADAM).build();
    public static final Appointment BENSON_APPT = new AppointmentBuilder().withDate(3, 10, 2018)
            .withTime(17, 45).withType(1).withPatient(BRYAN).withStaff(ADAM).build();
    public static final Appointment CARL_APPT = new AppointmentBuilder().withDate(2, 10, 2018)
            .withTime(18, 00).withType(1).withPatient(ALEX).withStaff(ADAM).build();

    // Medicines
    public static final Medicine PARACETAMOL = new MedicineBuilder().withMedicineName(VALID_MEDICINENAME_PARACETAMOL)
            .withMedicineType(VALID_MEDICINETYPE_PARACETAMOL).withEffectiveDosage(VALID_EFFECTIVEDOSAGE_PARACETAMOL)
            .withLethalDosage(VALID_LETHALDOSAGE_PARACETAMOL).withMedicinePrice(VALID_PRICE_PARACETAMOL)
            .withMedicineQuantity(VALID_QUANTITY_PARACETAMOL).build();
    public static final Medicine CHLORPHENIRAMINE = new MedicineBuilder()
            .withMedicineName(VALID_MEDICINENAME_CHLORPHENIRAMINE).withMedicineType(VALID_MEDICINETYPE_CHLORPHENIRAMINE)
            .withEffectiveDosage(VALID_EFFECTIVEDOSAGE_CHLORPHENIRAMINE)
            .withLethalDosage(VALID_LETHALDOSAGE_CHLORPHENIRAMINE).withMedicinePrice(VALID_PRICE_CHLORPHENIRAMINE)
            .withMedicineQuantity(VALID_QUANTITY_CHLORPHENIRAMINE).build();
    public static final Medicine ORACORT = new MedicineBuilder().withMedicineName(VALID_MEDICINENAME_ORACORT)
            .withMedicineType(VALID_MEDICINETYPE_ORACORT).withEffectiveDosage(VALID_EFFECTIVEDOSAGE_ORACORT)
            .withLethalDosage(VALID_LETHALDOSAGE_ORACORT).withMedicinePrice(VALID_PRICE_ORACORT)
            .withMedicineQuantity(VALID_QUANTITY_ORACORT).build();
    public static final Medicine VENTOLIN = new MedicineBuilder().withMedicineName(VALID_MEDICINENAME_VENTOLIN)
            .withMedicineType(VALID_MEDICINETYPE_VENTOLIN).withEffectiveDosage(VALID_EFFECTIVEDOSAGE_VENTOLIN)
            .withLethalDosage(VALID_LETHALDOSAGE_VENTOLIN).withMedicinePrice(VALID_PRICE_VENTOLIN)
            .withMedicineQuantity(VALID_QUANTITY_VENTOLIN).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

    /**
     * Returns an {@code ClinicIo} with all the typical persons.
     */
    public static ClinicIo getTypicalClinicIo() {
        ClinicIo clinicIo = new ClinicIo();
        for (Person person : getTypicalPersons()) {
            clinicIo.addPerson(person);
        }
        /*for (Patient patient: getTypicalPatients()) {
            clinicIo.addPatient(patient);
        }*/
        for (Staff staff : getTypicalStaffs()) {
            clinicIo.addStaff(staff);
        }
        /*
        for (Appointment appointment : getTypicalAppointments()) {
            clinicIo.addAppointment(appointment);
        }
        */

        for (Medicine medicine : getTypicalMedicines()) {
            clinicIo.addMedicine(medicine);
        }

        return clinicIo;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALEX, BRYAN));
    }

    public static List<Staff> getTypicalStaffs() {
        return new ArrayList<>(Arrays.asList(ADAM, BEN, ALAN, FRANK));
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(AMY_APPT, BENSON_APPT, CARL_APPT));
    }

    public static List<Medicine> getTypicalMedicines() {
        return new ArrayList<>(Arrays.asList(PARACETAMOL, CHLORPHENIRAMINE, ORACORT, VENTOLIN));
    }

}
