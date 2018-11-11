package seedu.clinicio.logic.parser;

//@@author aaronseahyh

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.clinicio.logic.commands.FindMedicineCommand;
import seedu.clinicio.model.medicine.MedicineNameContainsKeywordsPredicate;

public class FindMedicineCommandParserTest {

    private FindMedicineCommandParser parser = new FindMedicineCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindMedicineCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindMedicineCommand() {
        // no leading and trailing whitespaces
        FindMedicineCommand expectedFindMedicineCommand =
                new FindMedicineCommand(
                        new MedicineNameContainsKeywordsPredicate(Arrays.asList("Paracetamol", "Oracort")));
        assertParseSuccess(parser, "Paracetamol Oracort", expectedFindMedicineCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Paracetamol \n \t Oracort  \t", expectedFindMedicineCommand);
    }

}
