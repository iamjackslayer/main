package seedu.clinicio.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.model.staff.Role.RECEPTIONIST;

import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.logic.parser.exceptions.ParseException;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.patient.Allergy;
import seedu.clinicio.model.patient.MedicalProblem;
import seedu.clinicio.model.patient.Medication;
import seedu.clinicio.model.patient.Nric;
import seedu.clinicio.model.person.Address;
import seedu.clinicio.model.person.Email;
import seedu.clinicio.model.person.Name;
import seedu.clinicio.model.person.Phone;
import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.tag.Tag;
import seedu.clinicio.testutil.Assert;

public class ParserUtilTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String INVALID_DATE = "23 12 20 2";
    private static final String INVALID_DATE_2 = "22 222 2222";
    private static final String INVALID_DATE_3 = "222 22 2222";
    private static final String INVALID_DATE_4 = "$2 fj eiow";

    private static final String INVALID_TIME = "222 22";
    private static final String INVALID_TIME_2 = "22 222";
    private static final String INVALID_TIME_3 = "2j @@";
    private static final String INVALID_TYPE_2 = "@new";

    private static final String INVALID_ROLE = "abc";
    private static final String INVALID_PASSWORD = "";

    private static final String INVALID_NRIC = "H1231";
    private static final String INVALID_MED_PROB = "+123";
    private static final String INVALID_MED = "a@bc";
    private static final String INVALID_ALLERGY = "D^st";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_ROLE_DOCTOR = "doctor";
    private static final String VALID_ROLE_RECEPTIONIST = "receptionist";
    private static final String VALID_PASSWORD = "doctor1";

    private static final String VALID_DATE = "02 02 2222";
    private static final String VALID_TIME = "22 22";
    private static final String VALID_TYPE = "followup";

    private static final String VALID_NRIC = "S9323163I";
    private static final String VALID_MED_PROB_1 = "High Cholestrol";
    private static final String VALID_MED_PROB_2 = "Lung Cancer";
    private static final String VALID_MED_1 = "Sabuthamol";
    private static final String VALID_MED_2 = "Panadol";
    private static final String VALID_ALLERGY_1 = "Gluten Products";
    private static final String VALID_ALLERGY_2 = "Dust";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseRole_allNullFields_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRole(null);
    }

    @Test
    public void parseRole_invalidRole_throwsParseException() {
        Assert.assertThrows(ParseException.class, () ->
                ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validRole_returnsRole() throws Exception {
        assertEquals(DOCTOR,
                ParserUtil.parseRole(VALID_ROLE_DOCTOR));
        assertEquals(RECEPTIONIST,
                ParserUtil.parseRole(VALID_ROLE_RECEPTIONIST));
    }

    @Test
    public void parsePassword_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePassword(null);
    }

    @Test
    public void parsePassword_invalidPassword_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parsePassword(INVALID_PASSWORD);
    }

    @Test
    public void parsePassword_validValueWithoutWhitespace_returnsPassword() throws Exception {
        Password expectedPassword = new Password(VALID_PASSWORD, false);
        assertEquals(expectedPassword, ParserUtil.parsePassword(VALID_PASSWORD));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = Date.newDate("02 02 2222");
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = Date.newDate("02 02 2222");
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseDate_invalidFieldsWithoutWhitespace_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);

        //invalid month
        ParserUtil.parseDate(INVALID_DATE_2);

        //invalid day
        ParserUtil.parseDate(INVALID_DATE_3);

        //invalid alphanumerics
        ParserUtil.parseDate(INVALID_DATE_4);
    }

    @Test
    public void parseDate_invalidFieldsWithWhitespace_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);

        //invalid month
        ParserUtil.parseDate(WHITESPACE + INVALID_DATE_2 + WHITESPACE);

        //invalid day
        ParserUtil.parseDate(WHITESPACE + INVALID_DATE_3 + WHITESPACE);

        //invalid alphanumerics
        ParserUtil.parseDate(WHITESPACE + INVALID_DATE_4 + WHITESPACE);
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_validValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedTime = Time.newTime("22 22");
        assertEquals(expectedTime, ParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String timeWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        Time expectedTime = Time.newTime("22 22");
        assertEquals(expectedTime, ParserUtil.parseTime(timeWithWhitespace));
    }

    @Test
    public void parseTime_invalidFieldsWithoutWhitespace_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);

        //invalid hour
        ParserUtil.parseTime(INVALID_TIME);

        //invalid minute
        ParserUtil.parseTime(INVALID_TIME_2);

        //invalid alphanumerics
        ParserUtil.parseTime(INVALID_TIME_3);
    }

    @Test
    public void parseTime_invalidFieldsWithWhitespace_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);

        //invalid hour
        ParserUtil.parseTime(WHITESPACE + INVALID_TIME + WHITESPACE);

        //invalid minute
        ParserUtil.parseTime(WHITESPACE + INVALID_TIME_2 + WHITESPACE);

        //invalid alphanumerics
        ParserUtil.parseDate(WHITESPACE + INVALID_TIME_3 + WHITESPACE);
    }

    @Test
    public void parseType_nullType_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseType(null);
    }

    @Test
    public void parseType_nonExistentType_throwsNullPointerException() throws Exception {
        // TODO: Test for types that are neither followup nor new
    }

    @Test
    public void parseType_validType_returnInt() throws Exception {
        int expectedType = 1;
        assertEquals(expectedType, ParserUtil.parseType(VALID_TYPE));
    }

    @Test
    public void parseNric_nullNric_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseNric(null);
    }

    @Test
    public void parseNric_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseNric(INVALID_NRIC));
    }

    @Test
    public void parseNric_validValueWithoutWhitespace_returnsNric() throws Exception {
        Nric expectedNric = new Nric(VALID_NRIC);
        assertEquals(expectedNric, ParserUtil.parseNric(VALID_NRIC));
    }

    @Test
    public void parseMedicalProblem_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseMedicalProblem(null);
    }

    @Test
    public void parseMedicalProblem_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseMedicalProblem(INVALID_MED_PROB);
    }

    @Test
    public void parseMedicalProblem_validValueWithoutWhitespace_returnsMedicalProblem() throws Exception {
        MedicalProblem expectedMedicalProblem = new MedicalProblem(VALID_MED_PROB_1);
        assertEquals(expectedMedicalProblem, ParserUtil.parseMedicalProblem(VALID_MED_PROB_1));
    }

    @Test
    public void parseMedicalProblem_validValueWithWhitespace_returnsTrimmedMedicalProblem() throws Exception {
        String medicalProblemWithWhitespace = WHITESPACE + VALID_MED_PROB_1 + WHITESPACE;
        MedicalProblem expectedMedicalProblem = new MedicalProblem(VALID_MED_PROB_1);
        assertEquals(expectedMedicalProblem, ParserUtil.parseMedicalProblem(medicalProblemWithWhitespace));
    }

    @Test
    public void parseMedicalProblems_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseMedicalProblems(null);
    }

    @Test
    public void parseMedicalProblems_collectionWithInvalidMedicalProblems_throwsParseException()
            throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseMedicalProblems(Arrays.asList(VALID_MED_PROB_1, INVALID_MED_PROB));
    }

    @Test
    public void parseMedicalProblems_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseMedicalProblems(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseMedicalProblems_collectionWithValidMedicalProblems_returnsMedicalProblemSet()
            throws Exception {
        Set<MedicalProblem> actualMedicalProblemSet = ParserUtil.parseMedicalProblems(
                Arrays.asList(VALID_MED_PROB_1, VALID_MED_PROB_2));
        Set<MedicalProblem> expectedMedicalProblemSet = new HashSet<>(
                Arrays.asList(new MedicalProblem(VALID_MED_PROB_1), new MedicalProblem(VALID_MED_PROB_2)));

        assertEquals(expectedMedicalProblemSet, actualMedicalProblemSet);
    }

    @Test
    public void parseMedication_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseMedication(null);
    }

    @Test
    public void parseMedication_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseMedication(INVALID_MED);
    }

    @Test
    public void parseMedication_validValueWithoutWhitespace_returnsMedication() throws Exception {
        Medication expectedMedication = new Medication(VALID_MED_1);
        assertEquals(expectedMedication, ParserUtil.parseMedication(VALID_MED_1));
    }

    @Test
    public void parseMedication_validValueWithWhitespace_returnsTrimmedMedication() throws Exception {
        String medicationWithWhitespace = WHITESPACE + VALID_MED_1 + WHITESPACE;
        Medication expectedMedication = new Medication(VALID_MED_1);
        assertEquals(expectedMedication, ParserUtil.parseMedication(medicationWithWhitespace));
    }

    @Test
    public void parseMedications_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseMedications(null);
    }

    @Test
    public void parseMedications_collectionWithInvalidMedications_throwsParseException()
            throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseMedications(Arrays.asList(VALID_MED_1, INVALID_MED));
    }

    @Test
    public void parseMedications_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseMedications(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseMedications_collectionWithValidMedications_returnsMedicationSet()
            throws Exception {
        Set<Medication> actualMedicationSet = ParserUtil.parseMedications(
                Arrays.asList(VALID_MED_1, VALID_MED_2));
        Set<Medication> expectedMedicationSet = new HashSet<>(
                Arrays.asList(new Medication(VALID_MED_1), new Medication(VALID_MED_2)));

        assertEquals(expectedMedicationSet, actualMedicationSet);
    }

    @Test
    public void parseAllergy_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAllergy(null);
    }

    @Test
    public void parseAllergy_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseAllergy(INVALID_ALLERGY);
    }

    @Test
    public void parseAllergy_validValueWithoutWhitespace_returnsAllergy() throws Exception {
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY_1);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(VALID_ALLERGY_1));
    }

    @Test
    public void parseAllergy_validValueWithWhitespace_returnsTrimmedAllergy() throws Exception {
        String allergyWithWhitespace = WHITESPACE + VALID_ALLERGY_1 + WHITESPACE;
        Allergy expectedAllergy = new Allergy(VALID_ALLERGY_1);
        assertEquals(expectedAllergy, ParserUtil.parseAllergy(allergyWithWhitespace));
    }

    @Test
    public void parseAllergies_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAllergies(null);
    }

    @Test
    public void parseAllergies_collectionWithInvalidAllergies_throwsParseException()
            throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseAllergies(Arrays.asList(VALID_ALLERGY_1, INVALID_ALLERGY));
    }

    @Test
    public void parseAllergies_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseAllergies(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseAllergies_collectionWithValidAllergies_returnsAllergySet()
            throws Exception {
        Set<Allergy> actualAllergySet = ParserUtil.parseAllergies(
                Arrays.asList(VALID_ALLERGY_1, VALID_ALLERGY_2));
        Set<Allergy> expectedAllergySet = new HashSet<>(
                Arrays.asList(new Allergy(VALID_ALLERGY_1), new Allergy(VALID_ALLERGY_2)));

        assertEquals(expectedAllergySet, actualAllergySet);
    }

    @Test
    public void parsePreferredDoctor_null_throwNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePreferredDoctor(null);
    }

    @Test
    public void parsePreferredDoctor_invalidDoctor_throwParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parsePreferredDoctor(INVALID_NAME);
    }

    @Test
    public void parsePreferredDoctor_emptyDoctor_returnNull() throws Exception {
        assertNull(ParserUtil.parsePreferredDoctor(""));
    }

    @Test
    public void parsePreferredDoctor_validStaffAndNameWithoutWhitespace_returnDoctor() throws Exception {
        Staff expectedStaff = new Staff(DOCTOR, new Name(VALID_NAME));
        assertEquals(expectedStaff, ParserUtil.parsePreferredDoctor(VALID_NAME));
    }

    @Test
    public void parsePreferredDoctor_validStaffAndNameWithWhitespace_returnTrimmedDoctor() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Staff expectedStaff = new Staff(DOCTOR, new Name(VALID_NAME));
        assertEquals(expectedStaff, ParserUtil.parsePreferredDoctor(nameWithWhitespace));
    }
}
