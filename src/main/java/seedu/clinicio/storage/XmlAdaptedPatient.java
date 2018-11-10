package seedu.clinicio.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.clinicio.commons.exceptions.IllegalValueException;

import seedu.clinicio.model.patient.Allergy;
import seedu.clinicio.model.patient.MedicalProblem;
import seedu.clinicio.model.patient.Medication;
import seedu.clinicio.model.patient.Nric;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.model.staff.Staff;

/**
 * This class constructs a JAXB-friendly version of the Patient.
 */
public class XmlAdaptedPatient extends XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Patient's %s field is missing!";

    @XmlElement(required = true)
    private String nric;
    @XmlElement
    private List<XmlAdaptedMedicalProblem> medicalProblems = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedMedication> medications = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedAllergy> allergies = new ArrayList<>();

    @XmlElement
    private boolean isQueuing;
    @XmlElement
    private XmlAdaptedStaff preferredDoctor;
    @XmlElement
    private XmlAdaptedAppointment appointment;

    /**
     * Constructs an XmlAdaptedAppointment. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPatient() {}

    public XmlAdaptedPatient(String name, String nric, String phone, String email,
            String address, List<XmlAdaptedMedicalProblem> medicalProblems, List<XmlAdaptedMedication> medications,
            List<XmlAdaptedAllergy> allergies, boolean isQueuing,
            XmlAdaptedStaff preferredDoctor, XmlAdaptedAppointment appointment) {
        super(name, phone, email, address, new ArrayList<>());
        this.nric = nric;
        if (medicalProblems != null) {
            this.medicalProblems = new ArrayList<>(medicalProblems);
        }
        if (medications != null) {
            this.medications = new ArrayList<>(medications);
        }
        if (allergies != null) {
            this.allergies = new ArrayList<>(allergies);
        }
        this.isQueuing = isQueuing;
        this.preferredDoctor = preferredDoctor;
        this.appointment = appointment;
    }

    public XmlAdaptedPatient(Patient patient) {
        super(patient);
        nric = patient.getNric().value;
        medicalProblems = patient.getMedicalProblems().stream().map(XmlAdaptedMedicalProblem::new)
                .collect(Collectors.toList());
        medications = patient.getMedications().stream().map(XmlAdaptedMedication::new)
                .collect(Collectors.toList());
        allergies = patient.getAllergies().stream().map(XmlAdaptedAllergy::new)
                .collect(Collectors.toList());
        isQueuing = patient.isQueuing();

        if (patient.getPreferredDoctor().isPresent()) {
            preferredDoctor = new XmlAdaptedStaff(patient.getPreferredDoctor().get());
        }
        if (patient.getAppointment().isPresent()) {
            appointment = new XmlAdaptedAppointment(patient.getAppointment().get());
        }
    }

    /**
     * Converts this jaxb-friendly adapted patient object into the model's Patient object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    @Override
    public Patient toModelType() throws IllegalValueException {
        Person person = super.toModelType();
        //Patient patient = Patient.buildFromPerson(person);
        final List<MedicalProblem> patientMedicalProblems = new ArrayList<>();
        final List<Medication> patientMedications = new ArrayList<>();
        final List<Allergy> patientAllergies = new ArrayList<>();

        for (XmlAdaptedMedicalProblem medicalProblem : medicalProblems) {
            patientMedicalProblems.add(medicalProblem.toModelType());
        }
        for (XmlAdaptedMedication medication : medications) {
            patientMedications.add(medication.toModelType());
        }
        for (XmlAdaptedAllergy allergy: allergies) {
            patientAllergies.add(allergy.toModelType());
        }

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);
        final Set<MedicalProblem> modelMedicalProblems = new HashSet<>(patientMedicalProblems);
        final Set<Medication> modelMedications = new HashSet<>(patientMedications);
        final Set<Allergy> modelAllergies = new HashSet<>(patientAllergies);

        Staff modelPreferredDoc = null;
        if (preferredDoctor != null) {
            modelPreferredDoc = preferredDoctor.toModelType();
        }

        Patient patient = new Patient(person, modelNric,
                modelMedicalProblems, modelMedications,
                modelAllergies, modelPreferredDoc);

        if (isQueuing) {
            patient.isQueuing();
        } else {
            patient.setIsNotQueuing();
        }

        if (appointment != null) {
            patient.setAppointment(appointment.toModelType());
        }

        return patient;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPatient)) {
            return false;
        }

        XmlAdaptedPatient otherPatient = (XmlAdaptedPatient) other;
        return super.equals(other)
                && Objects.equals(nric, otherPatient.nric)
                && medicalProblems.equals(otherPatient.medicalProblems)
                && medications.equals(otherPatient.medications)
                && allergies.equals(otherPatient.allergies)
                && appointment.equals(otherPatient.appointment)
                && preferredDoctor.equals(otherPatient.preferredDoctor);
    }
}
