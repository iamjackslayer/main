package seedu.clinicio.testutil;

import java.util.HashSet;
import java.util.Optional;

import java.util.Set;

import seedu.clinicio.model.appointment.Appointment;

import seedu.clinicio.model.patient.Allergy;
import seedu.clinicio.model.patient.MedicalProblem;
import seedu.clinicio.model.patient.Medication;
import seedu.clinicio.model.patient.Nric;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.util.SampleDataUtil;

/**
 * A utility class to build {@link Patient} objects.
 */
public class PatientBuilder extends PersonBuilder {

    public static final String DEFAULT_NRIC = "S1234567A";

    private Nric nric;
    private Set<MedicalProblem> medicalProblems;
    private Set<Medication> medications;
    private Set<Allergy> allergies;
    private Optional<Staff> preferredDoctor;
    private Optional<Appointment> appointment;

    public PatientBuilder() {
        super();
        nric = new Nric(DEFAULT_NRIC);
        medicalProblems = new HashSet<>();
        medications = new HashSet<>();
        allergies = new HashSet<>();
        preferredDoctor = Optional.empty();
        appointment = Optional.empty();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        super(patientToCopy);
        nric = patientToCopy.getNric();
        medicalProblems = new HashSet<>(patientToCopy.getMedicalProblems());
        medications = new HashSet<>(patientToCopy.getMedications());
        allergies = new HashSet<>(patientToCopy.getAllergies());
        preferredDoctor = patientToCopy.getPreferredDoctor();
        appointment = patientToCopy.getAppointment();
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     * @return a PatientBuilder
     */
    public PatientBuilder withName(String name) {
        super.withName(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     * @return a PatientBuilder
     */
    public PatientBuilder withAddress(String address) {
        super.withAddress(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     * @return a PatientBuilder
     */
    public PatientBuilder withPhone(String phone) {
        super.withPhone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     * @return a PatientBuilder
     */
    public PatientBuilder withEmail(String email) {
        super.withEmail(email);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     * @return a PatientBuilder
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Parses the {@code medicalProblems} into a {@code Set<MedicalProblem>} and
     * set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withMedicalProblems(String ... medicalProblems) {
        this.medicalProblems = SampleDataUtil.getMedicalProblemSet(medicalProblems);
        return this;
    }

    /**
     * Parses the {@code medications} into a {@code Set<Medication>} and
     * set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withMedications(String ... medications) {
        this.medications = SampleDataUtil.getMedicationSet(medications);
        return this;
    }

    /**
     * Parses the {@code allergies} into a {@code Set<Allergy>} and
     * set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withAllergies(String ... allergies) {
        this.allergies = SampleDataUtil.getAllergySet(allergies);
        return this;
    }

    /**
     * Sets the staff as preferred by the patient.
     * @return a PersonBuilder
     */
    public PatientBuilder withPreferredDoctor(Staff staff) {
        preferredDoctor = Optional.ofNullable(staff);
        return this;
    }

    /**
     * Sets the appointment for the patient.
     * @return a PersonBuilder
     */
    public PatientBuilder withAppointment(Appointment appointment) {
        this.appointment = Optional.ofNullable(appointment);
        return this;
    }

    /**
     * Contructs a Patient based on the PatientBuilder.
     * @return a patient.
     */
    @Override
    public Patient build() {
        Person person = super.build();
        Patient patient = new Patient(person, nric, medicalProblems,
                medications, allergies);

        preferredDoctor.ifPresent(patient::setPreferredDoctor);
        appointment.ifPresent(patient::setAppointment);

        return patient;
    }
}
