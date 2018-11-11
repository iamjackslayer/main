package seedu.clinicio.logic.parser;

//@@author aaronseahyh

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.EFFECTIVEDOSAGE_DESC_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.EFFECTIVEDOSAGE_DESC_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_EFFECTIVEDOSAGE_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_LETHALDOSAGE_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_MEDICINENAME_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_MEDICINETYPE_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.LETHALDOSAGE_DESC_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.LETHALDOSAGE_DESC_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.MEDICINENAME_DESC_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.MEDICINENAME_DESC_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.MEDICINETYPE_DESC_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.MEDICINETYPE_DESC_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.clinicio.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinicio.logic.commands.CommandTestUtil.PRICE_DESC_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.PRICE_DESC_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.QUANTITY_DESC_ORACORT;
import static seedu.clinicio.logic.commands.CommandTestUtil.QUANTITY_DESC_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_EFFECTIVEDOSAGE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_LETHALDOSAGE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINENAME_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_MEDICINETYPE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_PRICE_PARACETAMOL;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_QUANTITY_PARACETAMOL;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinicio.testutil.TypicalPersons.PARACETAMOL;

import org.junit.Test;

import seedu.clinicio.logic.commands.AddMedicineCommand;
import seedu.clinicio.model.medicine.Medicine;
import seedu.clinicio.model.medicine.MedicineDosage;
import seedu.clinicio.model.medicine.MedicineName;
import seedu.clinicio.model.medicine.MedicinePrice;
import seedu.clinicio.model.medicine.MedicineQuantity;
import seedu.clinicio.model.medicine.MedicineType;
import seedu.clinicio.testutil.MedicineBuilder;

public class AddMedicineCommandParserTest {

    private AddMedicineCommandParser parser = new AddMedicineCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Medicine expectedMedicine = new MedicineBuilder(PARACETAMOL).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MEDICINENAME_DESC_PARACETAMOL
                + MEDICINETYPE_DESC_PARACETAMOL + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL
                + PRICE_DESC_PARACETAMOL + QUANTITY_DESC_PARACETAMOL, new AddMedicineCommand(expectedMedicine));

        // multiple names - last medicine name accepted
        assertParseSuccess(parser, MEDICINENAME_DESC_ORACORT + MEDICINENAME_DESC_PARACETAMOL
                + MEDICINETYPE_DESC_PARACETAMOL + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL
                + PRICE_DESC_PARACETAMOL + QUANTITY_DESC_PARACETAMOL, new AddMedicineCommand(expectedMedicine));

        // multiple types - last medicine type accepted
        assertParseSuccess(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_ORACORT
                + MEDICINETYPE_DESC_PARACETAMOL + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL
                + PRICE_DESC_PARACETAMOL + QUANTITY_DESC_PARACETAMOL, new AddMedicineCommand(expectedMedicine));

        // multiple effective dosages - last effective dosage accepted
        assertParseSuccess(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                + EFFECTIVEDOSAGE_DESC_ORACORT + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL
                + PRICE_DESC_PARACETAMOL + QUANTITY_DESC_PARACETAMOL, new AddMedicineCommand(expectedMedicine));

        // multiple lethal dosages - last lethal dosage accepted
        assertParseSuccess(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_ORACORT + LETHALDOSAGE_DESC_PARACETAMOL
                + PRICE_DESC_PARACETAMOL + QUANTITY_DESC_PARACETAMOL, new AddMedicineCommand(expectedMedicine));

        // multiple prices - last price accepted
        assertParseSuccess(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_ORACORT
                + PRICE_DESC_PARACETAMOL + QUANTITY_DESC_PARACETAMOL, new AddMedicineCommand(expectedMedicine));

        // multiple quantities - last quantity accepted
        assertParseSuccess(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                + QUANTITY_DESC_ORACORT + QUANTITY_DESC_PARACETAMOL, new AddMedicineCommand(expectedMedicine));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE);

        // missing medicine name prefix
        assertParseFailure(parser, VALID_MEDICINENAME_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                expectedMessage);

        // missing medicine type prefix
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + VALID_MEDICINETYPE_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                expectedMessage);

        // missing effective dosage prefix
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + VALID_EFFECTIVEDOSAGE_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                expectedMessage);

        // missing lethal dosage prefix
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + VALID_LETHALDOSAGE_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                expectedMessage);

        // missing price prefix
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + VALID_PRICE_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                expectedMessage);

        // missing medicine name prefix
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + VALID_QUANTITY_PARACETAMOL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_MEDICINENAME_PARACETAMOL + VALID_MEDICINETYPE_PARACETAMOL
                        + VALID_EFFECTIVEDOSAGE_PARACETAMOL + VALID_LETHALDOSAGE_PARACETAMOL + VALID_PRICE_PARACETAMOL
                        + VALID_QUANTITY_PARACETAMOL,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid medicine name
        assertParseFailure(parser, INVALID_MEDICINENAME_DESC + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);

        // invalid medicine type
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + INVALID_MEDICINETYPE_DESC
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                MedicineType.MESSAGE_MEDICINE_TYPE_CONSTRAINTS);

        // invalid effective dosage
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + INVALID_EFFECTIVEDOSAGE_DESC + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                MedicineDosage.MESSAGE_MEDICINE_DOSAGE_CONSTRAINTS);

        // invalid effective dosage
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + INVALID_LETHALDOSAGE_DESC + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                MedicineDosage.MESSAGE_MEDICINE_DOSAGE_CONSTRAINTS);

        // invalid price
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + INVALID_PRICE_DESC
                        + QUANTITY_DESC_PARACETAMOL,
                MedicinePrice.MESSAGE_MEDICINE_PRICE_CONSTRAINTS);

        // invalid quantity
        assertParseFailure(parser, MEDICINENAME_DESC_PARACETAMOL + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL
                        + INVALID_QUANTITY_DESC,
                MedicineQuantity.MESSAGE_MEDICINE_QUANTITY_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_MEDICINENAME_DESC + MEDICINETYPE_DESC_PARACETAMOL
                        + EFFECTIVEDOSAGE_DESC_PARACETAMOL + INVALID_LETHALDOSAGE_DESC + PRICE_DESC_PARACETAMOL
                        + QUANTITY_DESC_PARACETAMOL,
                MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEDICINENAME_DESC_PARACETAMOL
                        + MEDICINETYPE_DESC_PARACETAMOL + EFFECTIVEDOSAGE_DESC_PARACETAMOL
                        + LETHALDOSAGE_DESC_PARACETAMOL + PRICE_DESC_PARACETAMOL + QUANTITY_DESC_PARACETAMOL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
    }

}
