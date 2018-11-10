package seedu.clinicio.model.patient;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.consultation.Consultation;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.tag.Tag;

//@@author iamjackslayer
/**
 * Represents a patient in ClinicIO
 * Guarantees: Details inherited from Person, as well as NRIC must be present and not null.
 * A patient may or may not have a preferredDoctor, consultation, medical history and appointment.
 */
public class Patient extends Person {

    public static final String DEFAULT_NRIC = "S1234567A";

    // Identity fields
    private final Nric nric;

    // Data fields
    private Set<MedicalProblem> medicalProblems;
    private Set<Medication> medications;
    private Set<Allergy> allergies;
    private Optional<Staff> preferredDoctor;
    private boolean isQueuing = false;
    private Optional<Appointment> appointment;
    private Time arrivalTime;
    private List<Appointment> appointmentHistory;
    private List<Consultation> consultationHistory;

    /**
     * Constructor from buildFromPerson.
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
        nric = new Nric(DEFAULT_NRIC);
        medicalProblems = new HashSet<>();
        medications = new HashSet<>();
        allergies = new HashSet<>();
        preferredDoctor = Optional.empty();
        appointment = Optional.empty();
        appointmentHistory = new ArrayList<>();
        consultationHistory = new ArrayList<>();
    }

    /**
     * Every field must be present and not null.
     */
    public Patient(Person person, Nric nric, Set<MedicalProblem> medicalProblems, Set<Medication> medications,
            Set<Allergy> allergies) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), new HashSet<>());
        this.nric = nric;
        this.medicalProblems = medicalProblems;
        this.medications = medications;
        this.allergies = allergies;
        this.preferredDoctor = Optional.empty();
        appointment = Optional.empty();
        appointmentHistory = new ArrayList<>();
        consultationHistory = new ArrayList<>();
    }

    /**
     * Every field must be present and not null.
     */
    public Patient(Person person, Nric nric, Set<MedicalProblem> medicalProblems, Set<Medication> medications,
            Set<Allergy> allergies, Staff preferredDoctor) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), new HashSet<>());
        this.nric = nric;
        this.medicalProblems = medicalProblems;
        this.medications = medications;
        this.allergies = allergies;
        this.preferredDoctor = Optional.ofNullable(preferredDoctor);
        appointment = Optional.empty();
        appointmentHistory = new ArrayList<>();
        consultationHistory = new ArrayList<>();
    }

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Nric nric, Phone phone, Email email,
            Address address, Set<MedicalProblem> medicalProblems, Set<Medication> medications,
            Set<Allergy> allergies, Staff preferredDoctor) {
        super(name, phone, email, address, new HashSet<>());
        this.nric = nric;
        this.medicalProblems = medicalProblems;
        this.medications = medications;
        this.allergies = allergies;
        this.preferredDoctor = Optional.ofNullable(preferredDoctor);
        appointment = Optional.empty();
        appointmentHistory = new ArrayList<>();
        consultationHistory = new ArrayList<>();
    }

    public Nric getNric() {
        return nric;
    }

    /**
     * Returns an immutable medical problems set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MedicalProblem> getMedicalProblems() {
        return Collections.unmodifiableSet(medicalProblems);
    }

    /**
     * Returns an immutable medications set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Medication> getMedications() {
        return Collections.unmodifiableSet(medications);
    }

    /**
     * Returns an immutable allergies set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies);
    }

    /**
     * Returns the patient's preferred doctor wrapped in {@link Optional}. The patient may not have one.
     * Returns the patient's preferred staff wrapped in {@link Optional}. The patient may not have one.
     * @return an Optional {@link Staff}.
     */
    public Optional<Staff> getPreferredDoctor() {
        return preferredDoctor;
    }

    /**
     * Returns patient's appointment. The patient may not have one.
     * @return an Optional {@link Appointment}
     */
    public Optional<Appointment> getAppointment() {
        return appointment;
    }

    /**
     * Marks the patient with "isQueing" status.
     */
    public void setIsQueuing() {
        isQueuing = true;
    }

    /**
     * Marks the patient with "isNotQueing" status.
     */
    public void setIsNotQueuing() {
        isQueuing = false;
    }

    /**
     * Assigns a specific staff for the patient.
     * @param staff
     */
    public void setPreferredDoctor(Staff staff) {
        requireNonNull(staff);

        preferredDoctor = Optional.ofNullable(staff);
    }

    /**
     * Sets an appointment for the patient.
     * @param appointment
     */
    public void setAppointment(Appointment appointment) {
        requireNonNull(appointment);
        appointmentHistory.add(appointment);
        this.appointment = Optional.ofNullable(appointment);
    }

    /**
     * Adds a {@code Consultation} to the patient's record.
     * A consultation must be set whenever a Patient obj is created.
     * @param consultation consultation of the patient.
     */
    public void addConsultation(Consultation consultation) {
        requireNonNull(consultation);
        this.consultationHistory.add(consultation);
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @return A list of all of the patient's appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentHistory;
    }

    /**
     * @return A list of all of the patient's consultations.
     */
    public List<Consultation> getAllConsultations() {
        return consultationHistory;
    }

    /**
     * Checks if the patient has preferred doctor.
     */
    public boolean hasPreferredDoctor() {
        return preferredDoctor.isPresent();
    }

    /**
     * Checks if patient has an appointment.
     */
    public boolean hasAppointment() {
        return appointment.isPresent();
    }

    /**
     * Returns true if both patients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient other) {
        if (other == this) {
            return true;
        }

        boolean isSame = other != null
                && other.getName().equals(getName())
                && other.getNric().equals(getNric())
                && (other.getPhone().equals(getPhone()) || other.getEmail().equals(getEmail()))
                && other.getPreferredDoctor().equals(getPreferredDoctor());

        return isSame;
    }

    /**
     * Checks if the patient is in the queue. Note that there is only one queue on a higher conceptual level.
     * @return true if the patient is in the queue. Otherwise returns false.
     */
    public boolean isQueuing() {
        return isQueuing;
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getNric().equals(getNric())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getEmail().equals(getEmail())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getMedicalProblems().equals(getMedicalProblems())
                && otherPatient.getMedications().equals(getMedications())
                && otherPatient.getAllergies().equals(getAllergies())
                && otherPatient.getPreferredDoctor().equals(getPreferredDoctor());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        Optional<Staff> preferredDoctor = getPreferredDoctor();
        Optional<Appointment> appointment = getAppointment();
        Name name = getName();
        Phone phone = getPhone();
        Email email = getEmail();
        Address address = getAddress();
        Set<Tag> tags = getTags();

        return Objects.hash(name, phone, email, address, tags, preferredDoctor, appointment);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" NRIC: ")
                .append(getNric())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Medical Problems: ");
        getMedicalProblems().forEach(builder::append);
        builder.append(" Medications: ");
        getMedications().forEach(builder::append);
        builder.append(" Allergies: ");
        getAllergies().forEach(builder::append);
        builder.append(" Preference Doctor: ")
                .append(getPreferredDoctor().map(x -> x.getName().fullName)
                        .orElse("-"));
        return builder.toString();
    }
}
