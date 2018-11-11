package seedu.clinicio.model.util;

import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.model.staff.Role.RECEPTIONIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.clinicio.commons.util.HashUtil;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.ReadOnlyClinicIo;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineDosage;
import seedu.clinicio.model.medicine.MedicineName;
import seedu.clinicio.model.medicine.MedicinePrice;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.medicine.MedicineType;
import seedu.clinicio.model.patient.Allergy;
import seedu.clinicio.model.patient.MedicalProblem;
import seedu.clinicio.model.patient.Medication;
import seedu.clinicio.model.patient.Nric;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ClinicIo} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Ang Chen Mee"), new Nric("S8919203A"),
                    new Phone("80199102"), new Email("acm@live.com"),
                    new Address("ACM Ave 1, #02-12"),
                    getMedicalProblemSet(), getMedicationSet(), getAllergySet(),
                    getSampleStaffs().get(0)),
            new Patient(new Name("Benny Low Tin"), new Nric("T1152901A"),
                    new Phone("93112443"), new Email("bennylt@live.com"),
                    new Address("331, Jurong Ave 1, #12-36"),
                    getMedicalProblemSet(), getMedicationSet(), getAllergySet("dairy products"),
                    getSampleStaffs().get(0)),
            new Patient(new Name("Chen Ah Seng"), new Nric("S6600122J"),
                    new Phone("91028233"), new Email("cas@live.com"),
                    new Address("221, Bishan Street 12, #01-12"),
                    getMedicalProblemSet("Diabetes"), getMedicationSet(), getAllergySet(),
                    getSampleStaffs().get(1))
        };
    }

    public static List<Staff> getSampleStaffs() {
        return new ArrayList<>(Arrays.asList(
                new Staff(DOCTOR, new Name("Adam Bell"),
                        new Password(HashUtil.hashToString("doctor1"), true)),
                new Staff(DOCTOR, new Name("Chip Dale"),
                        new Password(HashUtil.hashToString("doctor2"), true)),
                new Staff(RECEPTIONIST, new Name("Alan Lee"),
                        new Password(HashUtil.hashToString("reception1"), true)),
                new Staff(RECEPTIONIST, new Name("Frank Tay"),
                        new Password(HashUtil.hashToString("reception2"), true))));
    }

    //@@author aaronseahyh
    public static Medicine[] getSampleMedicines() {
        return new Medicine[]{
            new Medicine(new MedicineName("Paracetamol"), new MedicineType("Tablet"),
                    new MedicineDosage("2"), new MedicineDosage("8"),
                    new MedicinePrice("0.05"), new MedicineQuantity("5000")),
            new Medicine(new MedicineName("Chlorpheniramine"), new MedicineType("Liquid"),
                    new MedicineDosage("4"), new MedicineDosage("32"),
                    new MedicinePrice("18.90"), new MedicineQuantity("500")),
            new Medicine(new MedicineName("Oracort"), new MedicineType("Topical"),
                    new MedicineDosage("5"), new MedicineDosage("30"),
                    new MedicinePrice("8.90"), new MedicineQuantity("100")),
            new Medicine(new MedicineName("Ventolin"), new MedicineType("Inhaler"),
                    new MedicineDosage("2"), new MedicineDosage("10"),
                    new MedicinePrice("13.55"), new MedicineQuantity("200"))
        };
    }

    public static ReadOnlyClinicIo getSampleClinicIo() {
        ClinicIo sampleClinicIo = new ClinicIo();

        for (Person samplePerson : getSamplePersons()) {
            sampleClinicIo.addPerson(samplePerson);
        }

        for (Patient samplePatient: getSamplePatients()) {
            sampleClinicIo.addPatient(samplePatient);
        }

        for (Staff sampleStaff : getSampleStaffs()) {
            sampleClinicIo.addStaff(sampleStaff);
        }

        //@@author aaronseahyh
        for (Medicine sampleMedicine : getSampleMedicines()) {
            sampleClinicIo.addMedicine(sampleMedicine);
        }

        return sampleClinicIo;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a medical problem set containing the list of strings given.
     */
    public static Set<MedicalProblem> getMedicalProblemSet(String... strings) {
        return Arrays.stream(strings)
                .map(MedicalProblem::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a medication set containing the list of strings given.
     */
    public static Set<Medication> getMedicationSet(String... strings) {
        return Arrays.stream(strings)
                .map(Medication::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a allergy set containing the list of strings given.
     */
    public static Set<Allergy> getAllergySet(String... strings) {
        return Arrays.stream(strings)
                .map(Allergy::new)
                .collect(Collectors.toSet());
    }
}
