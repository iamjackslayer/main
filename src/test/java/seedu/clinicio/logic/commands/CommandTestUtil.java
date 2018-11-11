package seedu.clinicio.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICAL_PROBLEM;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_EFFECTIVE_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_LETHAL_DOSAGE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_PRICE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_QUANTITY;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_MEDICINE_TYPE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PREFERRED_DOCTOR;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.clinicio.commons.core.index.Index;
import seedu.clinicio.logic.CommandHistory;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.model.ClinicIo;
import seedu.clinicio.model.Model;
import seedu.clinicio.model.analytics.Analytics;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineNameContainsKeywordsPredicate;
import seedu.clinicio.model.person.NameContainsKeywordsPredicate;
import seedu.clinicio.model.person.Person;
import seedu.clinicio.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_BENSON = "Benson Boy";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_NAME_ALEX = "Alex X";
    public static final String VALID_NAME_BRYAN = "Bryan James";
    public static final String VALID_NAME_CANDY = "Candy";
    public static final String VALID_NRIC_ALEX = "S9951423J";
    public static final String VALID_NRIC_BRYAN = "S9106823A";
    public static final String VALID_NRIC_CANDY = "T0190128B";
    public static final String VALID_PHONE_ALEX = "91023456";
    public static final String VALID_PHONE_BRYAN = "89087654";
    public static final String VALID_EMAIL_ALEX = "alex@example.com";
    public static final String VALID_EMAIL_BRYAN = "bryan@example.com";
    public static final String VALID_ADDRESS_ALEX = "Block 312, Alex Street 2";
    public static final String VALID_ADDRESS_BRYAN = "Block 113, Bryan Street 4";
    public static final String VALID_MED_PROB_STROKE = "stroke";
    public static final String VALID_MED_PROB_PNEUMONIA = "pneumonia";
    public static final String VALID_MED_INHALER = "inhaler";
    public static final String VALID_MED_PANADOL = "panadol";
    public static final String VALID_ALLERGIES_DUST = "dust";
    public static final String VALID_ALLERGIES_HEAT = "heat";

    public static final String VALID_NAME_ADAM = "Adam Bell";
    public static final String VALID_NAME_BEN = "Ben Hill";
    public static final String VALID_NAME_CAT = "Cat Johnson";
    public static final String VALID_PASSWORD_ADAM = "doctor1";
    public static final String VALID_PASSWORD_BEN = "doctor2";
    public static final String VALID_PASSWORD_CAT = "doctor3";
    public static final String VALID_HASH_PASSWORD_ADAM = "$2b$08$kjCPCAJ5eEpDD5qh49JNMuG7DmWmmJLuKAWtegLty9T"
            + ".ri/9bF3ti";
    public static final String VALID_HASH_PASSWORD_BEN = "$2b$08$KyxDFM2P0SF3KE5QjJ6si.cyZ4jMRuTxBvg2.Ly7pxUz"
        + "hQrCLGvUW";
    public static final String VALID_HASH_PASSWORD_CAT = "123";

    public static final String VALID_NAME_ALAN = "Alan Lee";
    public static final String VALID_NAME_FRANK = "Frank Tay";
    public static final String VALID_NAME_DAISY = "Daisy Johnson";
    public static final String VALID_PASSWORD_ALAN = "reception1";
    public static final String VALID_PASSWORD_FRANK = "reception2";
    public static final String VALID_PASSWORD_DAISY = "reception3";
    public static final String VALID_HASH_PASSWORD_ALAN = "$2a$08$L0jw35.qOdt7p5TdMQ4bIuSPvvX8D3UOGY4wCaWSWNUQ"
            + "jsnsJWcSO";
    public static final String VALID_HASH_PASSWORD_FRANK = "$2a$08$y9vtjkky9/YxP3WRXtgb3.7ECtblD8c0YsNXX4CrM8p49X"
            + "jwUkwh6";
    public static final String VALID_HASH_PASSWORD_DAISY = "123";

    public static final String VALID_DATE_AMY = "02 10 2018";
    public static final String VALID_DATE_BENSON = "03 10 2018";
    public static final String VALID_TIME_AMY = "13 00";
    public static final String VALID_TIME_BENSON = "15 00";

    public static final String VALID_MEDICINENAME_PARACETAMOL = "Paracetamol";
    public static final String VALID_MEDICINENAME_VENTOLIN = "Ventolin";
    public static final String VALID_MEDICINENAME_ORACORT = "Oracort";
    public static final String VALID_MEDICINENAME_CHLORPHENIRAMINE = "Chlorpheniramine";
    public static final String VALID_MEDICINETYPE_PARACETAMOL = "Tablet";
    public static final String VALID_MEDICINETYPE_VENTOLIN = "Inhaler";
    public static final String VALID_MEDICINETYPE_ORACORT = "Topical";
    public static final String VALID_MEDICINETYPE_CHLORPHENIRAMINE = "Liquid";
    public static final String VALID_EFFECTIVEDOSAGE_PARACETAMOL = "2";
    public static final String VALID_EFFECTIVEDOSAGE_VENTOLIN = "3";
    public static final String VALID_EFFECTIVEDOSAGE_ORACORT = "4";
    public static final String VALID_EFFECTIVEDOSAGE_CHLORPHENIRAMINE = "5";
    public static final String VALID_LETHALDOSAGE_PARACETAMOL = "8";
    public static final String VALID_LETHALDOSAGE_VENTOLIN = "10";
    public static final String VALID_LETHALDOSAGE_ORACORT = "30";
    public static final String VALID_LETHALDOSAGE_CHLORPHENIRAMINE = "32";
    public static final String VALID_PRICE_PARACETAMOL = "0.05";
    public static final String VALID_PRICE_VENTOLIN = "13.55";
    public static final String VALID_PRICE_ORACORT = "8.90";
    public static final String VALID_PRICE_CHLORPHENIRAMINE = "18.90";
    public static final String VALID_QUANTITY_PARACETAMOL = "1000";
    public static final String VALID_QUANTITY_VENTOLIN = "500";
    public static final String VALID_QUANTITY_ORACORT = "200";
    public static final String VALID_QUANTITY_CHLORPHENIRAMINE = "700";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String NAME_DESC_ALEX = " " + PREFIX_NAME + VALID_NAME_ALEX;
    public static final String NAME_DESC_BRYAN = " " + PREFIX_NAME + VALID_NAME_BRYAN;
    public static final String NRIC_DESC_ALEX = " " + PREFIX_IC + VALID_NRIC_ALEX;
    public static final String NRIC_DESC_BRYAN = " " + PREFIX_IC + VALID_NRIC_BRYAN;
    public static final String PHONE_DESC_ALEX = " " + PREFIX_PHONE + VALID_PHONE_ALEX;
    public static final String PHONE_DESC_BRYAN = " " + PREFIX_PHONE + VALID_PHONE_BRYAN;
    public static final String EMAIL_DESC_ALEX = " " + PREFIX_EMAIL + VALID_EMAIL_ALEX;
    public static final String EMAIL_DESC_BRYAN = " " + PREFIX_EMAIL + VALID_EMAIL_BRYAN;
    public static final String ADDRESS_DESC_ALEX = " " + PREFIX_ADDRESS + VALID_ADDRESS_ALEX;
    public static final String ADDRESS_DESC_BRYAN = " " + PREFIX_ADDRESS + VALID_ADDRESS_BRYAN;
    public static final String MED_PROB_DESC_STROKE = " " + PREFIX_MEDICAL_PROBLEM + VALID_MED_PROB_STROKE;
    public static final String MED_PROB_DESC_PNEUMONIA = " " + PREFIX_MEDICAL_PROBLEM + VALID_MED_PROB_PNEUMONIA;
    public static final String MED_DESC_INHALER = " " + PREFIX_MEDICATION + VALID_MED_INHALER;
    public static final String MED_DESC_PANADOL = " " + PREFIX_MEDICATION + VALID_MED_PANADOL;
    public static final String ALLERGIES_DESC_DUST = " " + PREFIX_ALLERGY + VALID_ALLERGIES_DUST;
    public static final String ALLERGIES_DESC_HEAT = " " + PREFIX_ALLERGY + VALID_ALLERGIES_HEAT;
    public static final String PREFERRED_DOC_DESC_ADAM = " " + PREFIX_PREFERRED_DOCTOR + VALID_NAME_ADAM;
    public static final String PREFERRED_DOC_DESC_BEN = " " + PREFIX_PREFERRED_DOCTOR + VALID_NAME_BEN;

    public static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String DATE_DESC_BENSON = " " + PREFIX_DATE + VALID_DATE_BENSON;
    public static final String TIME_DESC_AMY = " " + PREFIX_TIME + VALID_TIME_AMY;
    public static final String TIME_DESC_BENSON = " " + PREFIX_TIME + VALID_DATE_BENSON;

    public static final String MEDICINENAME_DESC_PARACETAMOL = " " + PREFIX_MEDICINE_NAME
            + VALID_MEDICINENAME_PARACETAMOL;
    public static final String MEDICINENAME_DESC_ORACORT = " " + PREFIX_MEDICINE_NAME
            + VALID_MEDICINENAME_ORACORT;
    public static final String MEDICINETYPE_DESC_PARACETAMOL = " " + PREFIX_MEDICINE_TYPE
            + VALID_MEDICINETYPE_PARACETAMOL;
    public static final String MEDICINETYPE_DESC_ORACORT = " " + PREFIX_MEDICINE_TYPE
            + VALID_MEDICINETYPE_ORACORT;
    public static final String EFFECTIVEDOSAGE_DESC_PARACETAMOL = " " + PREFIX_MEDICINE_EFFECTIVE_DOSAGE
            + VALID_EFFECTIVEDOSAGE_PARACETAMOL;
    public static final String EFFECTIVEDOSAGE_DESC_ORACORT = " " + PREFIX_MEDICINE_EFFECTIVE_DOSAGE
            + VALID_EFFECTIVEDOSAGE_ORACORT;
    public static final String LETHALDOSAGE_DESC_PARACETAMOL = " " + PREFIX_MEDICINE_LETHAL_DOSAGE
            + VALID_LETHALDOSAGE_PARACETAMOL;
    public static final String LETHALDOSAGE_DESC_ORACORT = " " + PREFIX_MEDICINE_LETHAL_DOSAGE
            + VALID_LETHALDOSAGE_ORACORT;
    public static final String PRICE_DESC_PARACETAMOL = " " + PREFIX_MEDICINE_PRICE
            + VALID_PRICE_PARACETAMOL;
    public static final String PRICE_DESC_ORACORT = " " + PREFIX_MEDICINE_PRICE
            + VALID_PRICE_ORACORT;
    public static final String QUANTITY_DESC_PARACETAMOL = " " + PREFIX_MEDICINE_QUANTITY
            + VALID_QUANTITY_PARACETAMOL;
    public static final String QUANTITY_DESC_ORACORT = " " + PREFIX_MEDICINE_QUANTITY
            + VALID_QUANTITY_ORACORT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC =
            " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_NRIC_DESC = " " + PREFIX_IC + "A12345%"; // '%' not allowed in nrics
    public static final String INVALID_MED_PROB_DESC = " " + PREFIX_MEDICAL_PROBLEM
            + "Dia*"; // '*' not allowed in medical problems
    public static final String INVALID_MED_DESC = " " + PREFIX_MEDICATION + "Ibupr*"; // '*' not allowed in medications
    public static final String INVALID_ALLERGY_DESC = " " + PREFIX_ALLERGY + "Sa*"; // '*' not allowed in allergies

    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "*2 8u 2000";
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "j0 39";

    public static final String INVALID_MEDICINENAME_DESC = " " + PREFIX_MEDICINE_NAME + "Cheese&";
    public static final String INVALID_MEDICINETYPE_DESC = " " + PREFIX_MEDICINE_TYPE + "Cheese1";
    public static final String INVALID_EFFECTIVEDOSAGE_DESC = " " + PREFIX_MEDICINE_EFFECTIVE_DOSAGE + "Cheese2";
    public static final String INVALID_LETHALDOSAGE_DESC = " " + PREFIX_MEDICINE_LETHAL_DOSAGE + "Cheese3";
    public static final String INVALID_PRICE_DESC = " " + PREFIX_MEDICINE_PRICE + "Cheese4";
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_MEDICINE_QUANTITY + "Cheese5";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br> - the result message matches {@code
     * expectedMessage} <br> - the {@code actualModel} matches {@code expectedModel} <br> - the {@code
     * actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel, Analytics analytics) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br> - a {@code CommandException} is thrown <br> -
     * the CommandException message matches {@code expectedMessage} <br> - the ClinicIO and the filtered
     * person list in the {@code actualModel} remain unchanged <br> - {@code actualCommandHistory} remains
     * unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            Analytics analytics, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ClinicIo expectedClinicIo = new ClinicIo(actualModel.getClinicIo());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedClinicIo, actualModel.getClinicIo());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s ClinicIO.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s ClinicIO.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitClinicIo();
    }

    //@@author aaronseahyh
    /**
     * Updates {@code model}'s filtered list to show only the medicine at the given {@code targetIndex} in the
     * {@code model}'s ClinicIO.
     */
    public static void showMedicineAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMedicineList().size());

        Medicine medicine = model.getFilteredMedicineList().get(targetIndex.getZeroBased());
        final String[] splitName = medicine.getMedicineName().medicineName.split("\\s+");
        model.updateFilteredMedicineList(new MedicineNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMedicineList().size());
    }

    //@@author aaronseahyh
    /**
     * Deletes the first medicine in {@code model}'s filtered medicine list from {@code model}'s ClinicIO.
     */
    public static void deleteFirstMedicine(Model model) {
        Medicine firstMedicine = model.getFilteredMedicineList().get(0);
        model.deleteMedicine(firstMedicine);
        model.commitClinicIo();
    }

}
