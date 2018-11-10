package seedu.clinicio.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.clinicio.model.staff.Role.DOCTOR;
import static seedu.clinicio.model.staff.Role.RECEPTIONIST;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.clinicio.commons.core.index.Index;

import seedu.clinicio.commons.util.StringUtil;

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
import seedu.clinicio.model.staff.Role;
import seedu.clinicio.model.staff.Staff;
import seedu.clinicio.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String clinicio} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code clinicio} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String password} into a {@code Password}.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        if (!Password.isValidPassword(password)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new Password(password, false);
    }

    /**
     * Parse {@code role} into either doctor or receptionist
     * @param role The type of user (doctor, receptionist)
     * @return A valid person with its role object.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        if (role.equals("doctor")) {
            return DOCTOR;
        } else if (role.equals("receptionist")) {
            return RECEPTIONIST;
        } else {
            throw new ParseException("You have entered invalid role. Please try with either doctor or receptionist.");
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String date} into an {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return Date.newDate(trimmedDate);
    }

    /**
     * Parses a {@code String time} into an {@code Time}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        return Time.newTime(trimmedTime);
    }

    /**
     * Parses a {@code String type} into an int.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static int parseType(String type) throws ParseException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (trimmedType.matches("followup")) {
            return 1;
        }
        return 0;
    }

    /**
     * Parses a {@code String nric} into an int.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        return new Nric(nric);
    }

    /**
     * Parses {@code Collection<String> medicalProblems} into a {@code Set<MedicalProblem>}.
     */
    public static Set<MedicalProblem> parseMedicalProblems(Collection<String> medicalProblems) throws ParseException {
        requireNonNull(medicalProblems);
        final Set<MedicalProblem> medicalProblemSet = new HashSet<>();
        for (String medicalProblem : medicalProblems) {
            medicalProblemSet.add(parseMedicalProblem(medicalProblem));
        }
        return medicalProblemSet;
    }

    /**
     * Parses a {@code String medicalProblem} into a {@code MedicalProblem}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medicalProblem} is invalid.
     */
    public static MedicalProblem parseMedicalProblem(String medicalProblem) throws ParseException {
        requireNonNull(medicalProblem);
        String trimmedMedicalProblem = medicalProblem.trim();
        if (!MedicalProblem.isValidMedProb(trimmedMedicalProblem)) {
            throw new ParseException(MedicalProblem.MESSAGE_MED_PROB_CONSTRAINTS);
        }
        return new MedicalProblem(trimmedMedicalProblem);
    }

    /**
     * Parses {@code Collection<String> medications} into a {@code Set<Medication>}.
     */
    public static Set<Medication> parseMedications(Collection<String> medications) throws ParseException {
        requireNonNull(medications);
        final Set<Medication> medicationSet = new HashSet<>();
        for (String medication : medications) {
            medicationSet.add(parseMedication(medication));
        }
        return medicationSet;
    }

    /**
     * Parses a {@code String medication} into a {@code Medication}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code medication} is invalid.
     */
    public static Medication parseMedication(String medication) throws ParseException {
        requireNonNull(medication);
        String trimmedMedications = medication.trim();
        if (!Medication.isValidMed(trimmedMedications)) {
            throw new ParseException(Medication.MESSAGE_MED_CONSTRAINTS);
        }
        return new Medication(trimmedMedications);
    }

    /**
     * Parses {@code Collection<String> allergies} into a {@code Set<Allergy>}.
     */
    public static Set<Allergy> parseAllergies(Collection<String> allergies) throws ParseException {
        requireNonNull(allergies);
        final Set<Allergy> allergiesSet = new HashSet<>();
        for (String allergy : allergies) {
            allergiesSet.add(parseAllergy(allergy));
        }
        return allergiesSet;
    }

    /**
     * Parses a {@code String allergy} into a {@code Allergy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     */
    public static Allergy parseAllergy(String allergy) throws ParseException {
        requireNonNull(allergy);
        String trimmedAllergy = allergy.trim();
        if (!Allergy.isValidAllergy(trimmedAllergy)) {
            throw new ParseException(Allergy.MESSAGE_ALLERGY_CONSTRAINTS);
        }
        return new Allergy(trimmedAllergy);
    }

    /**
     * Parses a {@code String doctorName} into a {@code Staff}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code doctorName} is invalid.
     */
    public static Staff parsePreferredDoctor(String doctorName) throws ParseException {
        requireNonNull(doctorName);
        String trimmedDoctorName = doctorName.trim();
        if (doctorName.isEmpty()) {
            return null;
        } else if (!Name.isValidName(trimmedDoctorName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Staff(DOCTOR, new Name(trimmedDoctorName));
    }
}
