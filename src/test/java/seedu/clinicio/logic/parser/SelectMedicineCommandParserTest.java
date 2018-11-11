package seedu.clinicio.logic.parser;

//@@author aaronseahyh

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinicio.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;

import org.junit.Test;

import seedu.clinicio.logic.commands.SelectMedicineCommand;

public class SelectMedicineCommandParserTest {

    private SelectMedicineCommandParser parser = new SelectMedicineCommandParser();

    @Test
    public void parse_validArgs_returnsSelectMedicineCommand() {
        assertParseSuccess(parser, "1", new SelectMedicineCommand(INDEX_FIRST_MEDICINE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectMedicineCommand.MESSAGE_USAGE));
    }

}
