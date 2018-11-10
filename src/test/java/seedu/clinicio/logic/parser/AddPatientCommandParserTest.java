package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.ADDRESS_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.ADDRESS_DESC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.ALLERGIES_DESC_DUST;
import static seedu.clinicio.logic.commands.CommandTestUtil.ALLERGIES_DESC_HEAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.EMAIL_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.EMAIL_DESC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_ALLERGY_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_MED_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_MED_PROB_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.MED_DESC_INHALER;
import static seedu.clinicio.logic.commands.CommandTestUtil.MED_DESC_PANADOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.MED_PROB_DESC_PNEUMONIA;
import static seedu.clinicio.logic.commands.CommandTestUtil.MED_PROB_DESC_STROKE;
import static seedu.clinicio.logic.commands.CommandTestUtil.NAME_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.NAME_DESC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.NRIC_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.NRIC_DESC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.PHONE_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.PHONE_DESC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.clinicio.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinicio.logic.commands.CommandTestUtil.PREFERRED_DOC_DESC_ADAM;
import static seedu.clinicio.logic.commands.CommandTestUtil.PREFERRED_DOC_DESC_BEN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ADDRESS_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ALLERGIES_DUST;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_ALLERGIES_HEAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EMAIL_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MED_INHALER;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MED_PANADOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MED_PROB_PNEUMONIA;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MED_PROB_STROKE;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NRIC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PHONE_BRYAN;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinicio.testutil.TypicalPersons.ALEX;
import static seedu.clinicio.testutil.TypicalPersons.BEN_1;
import static seedu.clinicio.testutil.TypicalPersons.BRYAN;

import org.junit.Test;

import seedu.clinicio.logic.commands.AddPatientCommand;
import seedu.clinicio.model.patient.Allergy;
import seedu.clinicio.model.patient.MedicalProblem;
import seedu.clinicio.model.patient.Medication;
import seedu.clinicio.model.patient.Nric;
import seedu.clinicio.model.patient.Patient;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.testutil.PatientBuilder;

public class AddPatientCommandParserTest {
    private AddPatientCommandParser parser = new AddPatientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BRYAN)
                .withPhone(VALID_PHONE_BRYAN).withEmail(VALID_EMAIL_BRYAN).withAddress(VALID_ADDRESS_BRYAN)
                .withMedicalProblems(VALID_MED_PROB_STROKE).withMedications(VALID_MED_PANADOL)
                .withAllergies(VALID_ALLERGIES_DUST)
                .withPreferredDoctor(BEN_1)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN
                + EMAIL_DESC_BRYAN + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL + ALLERGIES_DESC_DUST
                + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPatient));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_ALEX + NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN
                + EMAIL_DESC_BRYAN + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPatient));

        // multiple nrics - last nric accepted
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_ALEX + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN
                + EMAIL_DESC_BRYAN + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPatient));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_ALEX + PHONE_DESC_BRYAN
                + EMAIL_DESC_BRYAN + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPatient));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_ALEX
                + EMAIL_DESC_BRYAN + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPatient));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_ALEX + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPatient));

        // multiple medical problems - all accepted
        Patient expectedPersonMultipleMedProbs = new PatientBuilder(BRYAN)
                .withMedicalProblems(VALID_MED_PROB_STROKE, VALID_MED_PROB_PNEUMONIA)
                .withPreferredDoctor(BEN_1)
                .build();
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_PROB_DESC_PNEUMONIA
                + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPersonMultipleMedProbs));

        // multiple medications - all accepted
        Patient expectedPersonMultipleMeds = new PatientBuilder(BRYAN)
                .withMedications(VALID_MED_INHALER, VALID_MED_PANADOL)
                .withPreferredDoctor(BEN_1)
                .build();
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + MED_DESC_INHALER + MED_DESC_PANADOL
                + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPersonMultipleMeds));

        // multiple allergies - all accepted
        Patient expectedPersonMultipleAllergies = new PatientBuilder(BRYAN)
                .withAllergies(VALID_ALLERGIES_DUST, VALID_ALLERGIES_HEAT)
                .withPreferredDoctor(BEN_1)
                .build();
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + ALLERGIES_DESC_DUST + ALLERGIES_DESC_HEAT
                + PREFERRED_DOC_DESC_BEN, new AddPatientCommand(expectedPersonMultipleAllergies));

        // multiple preferred doctor - last preferred doctor accepted
        assertParseSuccess(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_ALEX + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_ADAM + PREFERRED_DOC_DESC_BEN,
                new AddPatientCommand(expectedPatient));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero medical problems & zero medications & zero allergies
        Patient expectedPatient = new PatientBuilder(ALEX).withMedicalProblems()
                .withMedications().withAllergies().withPreferredDoctor(null).build();
        assertParseSuccess(parser, NAME_DESC_ALEX + NRIC_DESC_ALEX + PHONE_DESC_ALEX + EMAIL_DESC_ALEX
                + ADDRESS_DESC_ALEX, new AddPatientCommand(expectedPatient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN, expectedMessage);

        // missing nric prefix
        assertParseFailure(parser, NAME_DESC_BRYAN + VALID_NRIC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + VALID_PHONE_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + VALID_EMAIL_BRYAN
                + ADDRESS_DESC_BRYAN, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + VALID_ADDRESS_BRYAN, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BRYAN + VALID_NRIC_BRYAN + VALID_PHONE_BRYAN + VALID_EMAIL_BRYAN
                + VALID_ADDRESS_BRYAN, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid nric
        assertParseFailure(parser, NAME_DESC_BRYAN + INVALID_NRIC_DESC + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, Nric.MESSAGE_NRIC_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + INVALID_PHONE_DESC + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + INVALID_ADDRESS_DESC + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid medical problems
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + INVALID_MED_PROB_DESC + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, MedicalProblem.MESSAGE_MED_PROB_CONSTRAINTS);

        // invalid medications
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + INVALID_MED_DESC
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN, Medication.MESSAGE_MED_CONSTRAINTS);

        // invalid allergies
        assertParseFailure(parser, NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + INVALID_ALLERGY_DESC + PREFERRED_DOC_DESC_BEN, Allergy.MESSAGE_ALLERGY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN + EMAIL_DESC_BRYAN
                + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BRYAN + NRIC_DESC_BRYAN + PHONE_DESC_BRYAN
                + EMAIL_DESC_BRYAN + ADDRESS_DESC_BRYAN + MED_PROB_DESC_STROKE + MED_DESC_PANADOL
                + ALLERGIES_DESC_DUST + PREFERRED_DOC_DESC_BEN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPatientCommand.MESSAGE_USAGE));
    }
}
