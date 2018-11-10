package seedu.clinicio.testutil;

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICAL_PROBLEM;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PREFERRED_DOCTOR;

import seedu.clinicio.logic.commands.AddPatientCommand;

import seedu.clinicio.model.patient.Patient;

/**
 * A utility class for Patient.
 */
public class PatientUtil {

    /**
     * Returns an add patient command string for adding the {@code patient}.
     */
    public static String getAddPatientCommand(Patient patient) {
        return AddPatientCommand.COMMAND_WORD + " " + getPatientDetails(patient);
    }

    /**
     * Returns the part of command string for the given {@code patient}'s details.
     */
    public static String getPatientDetails(Patient patient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + patient.getName().fullName + " ");
        sb.append(PREFIX_IC + patient.getNric().value + " ");
        sb.append(PREFIX_PHONE + patient.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + patient.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + patient.getAddress().value + " ");
        patient.getMedicalProblems().stream().forEach(
            s -> sb.append(PREFIX_MEDICAL_PROBLEM + s.medProb + " ")
        );
        patient.getMedications().stream().forEach(
            s -> sb.append(PREFIX_MEDICATION + s.value + " ")
        );
        patient.getAllergies().stream().forEach(
            s -> sb.append(PREFIX_ALLERGY + s.allergy + " ")
        );
        patient.getPreferredDoctor().ifPresent(
            s -> sb.append(PREFIX_PREFERRED_DOCTOR + s.getName().fullName + " ")
        );
        return sb.toString();
    }

}
